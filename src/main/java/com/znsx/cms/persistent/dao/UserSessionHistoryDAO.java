package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.UserSessionHistory;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;

/**
 * 用户历史会话数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:14:47
 */
public interface UserSessionHistoryDAO extends
		BaseDAO<UserSessionHistory, String> {
	/**
	 * 
	 * 查询用户会话历史
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            传入的机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            查询条数
	 * @param limit
	 *            用户当前机构
	 * @return List<ListUserSessionHistoryVO>
	 * @deprecated
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:55:42
	 */
	public List<ListUserSessionHistoryVO> findUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String userOrganId);

	/**
	 * 
	 * 返回查询历史会话总数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            传入的机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param userOrganId
	 *            用户当前机构
	 * @return
	 * @deprecated
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:20:57
	 */
	public int selectTotalCount(String userId, String userName, String organId,
			Long startTime, Long endTime, String userOrganId);

	/**
	 * 获取历史用户会话列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @param userOrganId
	 *            用户所属机构
	 * @return 历史用户会话列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:02:58
	 */
	public List<UserSessionHistory> listUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String userOrganId);

	/**
	 * 统计满足条件的用户历史会话数量
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param userOrganId
	 *            用户所属机构
	 * @return 满足条件的用户历史会话数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:07:03
	 */
	public int countUserSessionHistory(String userId, String userName,
			String organId, Long startTime, Long endTime, String userOrganId);

	/**
	 * 获取最近更新的会话时间
	 * 
	 * @return 最近更新的会话时间
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-26 上午9:44:30
	 */
	public long getLatestSession();
}
