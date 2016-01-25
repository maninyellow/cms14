package com.znsx.cms.aop.advice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.controller.BaseController;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 接口调用切面
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 下午02:36:43
 */
public class InterfaceAdvice implements MethodInterceptor {
	@Autowired
	private UserManager userManager;

	public static Logger log = Logger.getLogger(InterfaceAdvice.class);

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

		HttpServletRequest request = null;
		HttpServletResponse response = null;

		// 出现异常的返回对象
		BaseDTO dto = new BaseDTO();
		try {
			request = (HttpServletRequest) args[0];
			response = (HttpServletResponse) args[1];
			// 根据注解判断是否需要执行切面动作，@RequestMapping是必须的，所以接口描述注解存在的话，注解数组长度应该>=2
			if (methodAnnotations.length >= 2) {
				for (int i = 0; i < methodAnnotations.length; i++) {
					if (methodAnnotations[i] instanceof InterfaceDescription) {
						InterfaceDescription interfaceDescription = (InterfaceDescription) methodAnnotations[i];
						dto.setMethod(interfaceDescription.method());
						dto.setCmd(interfaceDescription.cmd());
						// 判断是否需要登陆，不需要登陆的情况
						if (!interfaceDescription.logon()) {
							return invocation.proceed();
						}
					}
				}
			} else {
				return invocation.proceed();
			}
			// 需要登陆的情况
			ResourceVO resource = checkSession(request, methodAnnotations);
			// 请求对象会话信息加入ThreadLocal
			BaseController.resource.set(resource);
			result = invocation.proceed();
			return result;
		} catch (BusinessException e) {
			e.printStackTrace();
//			System.err.println(e.toString());
//			System.err.println(e.getStackTrace()[0].toString());
			dto.setCode(e.getCode());
			dto.setMessage(e.getMessage());
			if (isXMLInterface(methodAnnotations)) {
				writePageWithContentLength(response, dto);
			} else {
				writePage(response, dto);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			dto.setCode(ErrorCode.DATABASE_ACCESS_ERROR);
			dto.setMessage(e.getMessage());
			if (isXMLInterface(methodAnnotations)) {
				writePageWithContentLength(response, dto);
			} else {
				writePage(response, dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setCode(ErrorCode.ERROR);
			dto.setMessage(e.getMessage());
			if (isXMLInterface(methodAnnotations)) {
				writePageWithContentLength(response, dto);
			} else {
				writePage(response, dto);
			}
		} finally {
			// 接口执行完毕，移除ThreadLocal
			BaseController.resource.remove();
		}
		return null;
	}

	/**
	 * 判断是否xml接口,xml接口的返回不同
	 * 
	 * @param annotations
	 *            接口方法注解数组
	 * @return
	 */
	private boolean isXMLInterface(Annotation[] annotations) {
		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i] instanceof RequestMapping) {
				RequestMapping requestMapping = (RequestMapping) annotations[i];
				String path = requestMapping.value()[0];
				if (path.endsWith("xml")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查请求对象的会话
	 * 
	 * @param request
	 *            http请求
	 * @return 通过验证的请求对象的会话
	 * @throws BusinessException
	 */
	private ResourceVO checkSession(HttpServletRequest request,
			Annotation[] annotations) throws BusinessException {
		String sessionId = request.getParameter("sessionId");

		if ("POST".equals(request.getMethod())) {
			// 如果没有拿到sessionId,同时又是POST方式的xml接口,说明sessionId在body体的标准xml中
			if (StringUtils.isBlank(sessionId) && isXMLInterface(annotations)) {
				// 解析request body,得到sessionId
				Document doc;
				try {
					doc = RequestUtil.parseRequest(request);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(
							ErrorCode.PARAMETER_NOT_FOUND,
							"Could not parse XML request, maybe missing [sessionId] or Content-Type is not right !");
				}
				request.setAttribute("RequestDocument", doc);
				Element root = doc.getRootElement();
				Element sessionElement = root.getChild("SessionId");
				if (null == sessionElement) {
					throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
							"missing [SessionId]");
				}
				sessionId = sessionElement.getText();
			}
		}
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [sessionId]");
		}
		// 验证sessionId
		ResourceVO resource = userManager.checkSession(sessionId);

		return resource;
	}

	/**
	 * 返回xml，带有Content-length头
	 * 
	 * @param response
	 * @param doc
	 *            xml内容
	 * @throws ServletException
	 * @throws IOException
	 */
	public void writePageWithContentLength(HttpServletResponse response,
			BaseDTO dto) throws Exception {
		Document doc = new Document();
		doc.setRootElement(ElementUtil.createElement("Response", dto, null,
				null));
		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		String body = out.outputString(doc);
		if (log.isDebugEnabled()) {
			log.debug(body);
		}
		response.setContentLength(body.getBytes("UTF-8").length);
		response.setContentType("application/xml");
		response.getWriter().write(body);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 返回json
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param dto
	 *            返回对象
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:14:12
	 */
	public void writePage(HttpServletResponse response, BaseDTO dto)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String rtn = JSONObject.fromObject(dto).toString();
		response.setContentLength(rtn.getBytes("UTF-8").length);
		response.getWriter().write(rtn);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
