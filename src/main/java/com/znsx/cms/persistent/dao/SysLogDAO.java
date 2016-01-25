package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.TopRealPlayLog;

/**
 * 系统日志数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:07:52
 */
public interface SysLogDAO extends BaseDAO<SysLog, String> {
	/**
	 * 定时定量批量插入日志
	 * 
	 * @param record
	 *            要插入的日志对象
	 */
	public void batchInsert(SysLog record) throws BusinessException;

	/**
	 * 批量插入日志的数据库操作任务
	 * 
	 * @throws BusinessException
	 */
	public void saveTask() throws BusinessException;

	/**
	 * 根据对象ID和类型,获取对象名称
	 * 
	 * @param id
	 *            对象ID
	 * @param type
	 *            对象类型
	 * @return 对象实体
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:31:46
	 */
	public Object getNameByIdAndType(Object id, String type)
			throws BusinessException;

	/**
	 * 
	 * 根据系统管理员查询系统日志列表
	 * 
	 * @param resourceName
	 *            操作人名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param targetName
	 *            被操作名称
	 * @param operationCode
	 *            操作编码
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @return List<SysLog>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:56:06
	 */
	public List<SysLog> listSysLogByAdmin(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String targetName,
			String operationCode, String operationType, String resourceType);

	/**
	 * 
	 * 普通人员查询系统日志列表
	 * 
	 * @param organId
	 *            传入的机构ID
	 * @param resourceName
	 *            操作人名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organIds
	 *            当前机构以及子机构
	 * @param targetName
	 *            被操作名称
	 * @param operationCode
	 *            操作编码
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @param type
	 *            用户选择查询类型 1：客户端日志 2：服务器日志
	 * @return List<SysLog>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:41:15
	 */
	public List<SysLog> listSysLog(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String[] organIds,
			String targetName, String operationCode, String operationType,
			String resourceType, String type);

	/**
	 * 
	 * 查询系统日志数量
	 * 
	 * @param organId
	 *            传入的机构ID
	 * @param resourceName
	 *            操作人员名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organIds
	 *            当前人员机构以及子机构
	 * @param targetName
	 *            被操作名称
	 * @param operationCode
	 *            操作编码
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @param type
	 *            用户选择查询类型 1：客户端日志 2：服务器日志
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:53:31
	 */
	public Integer findTotalCount(String organId, String resourceName,
			Long startTime, Long endTime, String[] organIds, String targetName,
			String operationCode, String operationType, String resourceType,
			String type);

	/**
	 * 通过对象ID和类型查找对象
	 * 
	 * @param id
	 *            对象ID
	 * @param type
	 *            对象类型
	 * @return 对象实体
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-3 下午2:44:54
	 */
	public Object getEntityByIdAndType(Object id, String type)
			throws BusinessException;

	/**
	 * 
	 * topRealPlay方法说明
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-20 上午11:23:49
	 */
	public List<TopRealPlayLog> topRealPlay();
}
