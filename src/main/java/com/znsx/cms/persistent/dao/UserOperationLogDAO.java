package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.UserOperationLog;

/**
 * UserOperationLogDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:38:02
 */
public interface UserOperationLogDAO extends BaseDAO<UserOperationLog, String> {

	/**
	 * 
	 * 统计用户操作日志数量
	 * 
	 * @param operationType
	 *            操作类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 日志数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 上午10:16:15
	 */
	public Integer findTotalCount(String operationType, Long beginTime,
			Long endTime);

	/**
	 * 条件查询用户操作日志
	 * 
	 * @param operationType
	 *            操作类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 用户操作日志集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 上午10:42:11
	 */
	public List<UserOperationLog> listUserOperationLog(String operationType,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);

}
