package com.znsx.cms.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.controller.BaseController;

/**
 * 日志记录切面
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:08:45
 */
public class LogAdvice implements MethodInterceptor {

	public static final String SUCCESS = "200";
	@Autowired
	private SysLogManager sysLogManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 目标类方法返回值
		Object result = null;
		// 目标类名
		// String objName = invocation.getThis().getClass().getName();
		// 目标方法
		Method method = invocation.getMethod();
		// 目标方法名
		// String methodName = method.getName();
		// 目标方法参数
		Object[] args = invocation.getArguments();
		// 方法注解
		Annotation[] methodAnnotations = method.getAnnotations();
		// 方法参数注解
		Annotation[][] paramAnnotations = method.getParameterAnnotations();
		// 方法执行成功失败标志
		String successFlag = SUCCESS;
		// 要记录的日志对象
		SysLog sysLog = null;

		try {
			// 如果要记录日志
			if (methodAnnotations.length > 0) {
				sysLog = before(methodAnnotations, paramAnnotations, args);
				// 通过反射机制调用目标方法
				result = invocation.proceed();
				if (StringUtils.isBlank(sysLog.getTargetId())) {
					// 没有获取到操作目标对象ID的，通过方法的返回得到
					if (null != result) {
						sysLog.setTargetId(result.toString());
					}
				}
			} else {
				result = invocation.proceed();
			}
		} catch (BusinessException e) {
//			e.printStackTrace();
			successFlag = e.getCode();
			throw e;
		} catch (Exception e) {
//			e.printStackTrace();
			successFlag = ErrorCode.ERROR;
			throw e;
		} finally {
			// 检查是否满足记录日志的必要条件
			if (checkLogCondition(sysLog)) {
				sysLog.setSuccessFlag(successFlag);
				// 记录日志
				log(sysLog);
			}
		}
		return result;
	}

	/**
	 * 方法执行前的动作，构建创建日志的基本数据
	 * 
	 * @param methodAnnotations
	 *            方法注解
	 * @param paramAnnotations
	 *            方法参数注解
	 * @return
	 * @throws BusinessException
	 */
	private SysLog before(Annotation[] methodAnnotations,
			Annotation[][] paramAnnotations, Object[] args)
			throws BusinessException {
		SysLog sysLog = new SysLog();
		Object targetId = null;

		// 获取日志方法注解
		LogMethod logMethod = null;
		for (int i = 0; i < methodAnnotations.length; i++) {
			if (methodAnnotations[i] instanceof LogMethod) {
				logMethod = (LogMethod) methodAnnotations[i];
				break;
			}
		}

		if (null != logMethod) {
			sysLog.setLogTime(System.currentTimeMillis());
			sysLog.setCreateTime(System.currentTimeMillis());
			sysLog.setOperationName(logMethod.name());
			sysLog.setOperationCode(logMethod.code());
			sysLog.setOperationType(logMethod.operationType());
			sysLog.setTargetType(logMethod.targetType());

			// 获取参数方法注解得到targetName,targetId等参数
			for (int i = 0; i < paramAnnotations.length; i++) {
				if (paramAnnotations[i].length > 0) {
					LogParam logParam = (LogParam) paramAnnotations[i][0];
					if (logParam.value().equals("name")) {
						if (null != args[i]) {
							sysLog.setTargetName(args[i].toString());
						}
					} else if (logParam.value().equals("id")) {
						targetId = args[i];
						sysLog.setTargetId(targetId.toString());
					}
				}
			}

			// 根据上层controller接口调用的对象获取resourceId,resourceType,resourceName
			ResourceVO resource = BaseController.resource.get();
			if (null != resource) {
				sysLog.setResourceId(resource.getId());
				sysLog.setResourceType(resource.getType());
				sysLog.setResourceName(resource.getName());
				sysLog.setOrganId(resource.getOrganId());
			}

			// 如果没有获取到targetName，并且存在targetId，判定是修改和删除类的方法，通过ID查询得到targetName
			if (StringUtils.isBlank(sysLog.getTargetName()) && null != targetId) {
				sysLog.setTargetName(sysLogManager.getNameByIdAndType(targetId,
						sysLog.getTargetType()));
			}
		}
		return sysLog;
	}

	/**
	 * 检查是否满足日志记录的必要值
	 * 
	 * @param sysLog
	 * @return true-满足，false-不满足
	 */
	private boolean checkLogCondition(SysLog sysLog) {
		if (null == sysLog) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getOperationCode())) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getOperationName())) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getOperationType())) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getResourceId())) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getResourceType())) {
			return false;
		}
		if (StringUtils.isBlank(sysLog.getResourceName())) {
			return false;
		}
		if (null == sysLog.getLogTime()) {
			return false;
		}
		return true;
	}

	/**
	 * 记录日志
	 * 
	 * @param sysLog
	 *            要记录的日志对象
	 */
	private void log(SysLog sysLog) {
		sysLogManager.batchLog(sysLog);
	}
}
