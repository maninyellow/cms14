package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Team;

/**
 * TeamDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:14:24
 */
public interface TeamDAO extends BaseDAO<Team, String> {

	/**
	 * 
	 * 统计救援队伍总数量
	 * 
	 * @param unitId
	 *            救援单位id
	 * @param type
	 *            队伍类型
	 * @param name
	 *            队伍名称
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:50:09
	 */
	public Integer countTotalTeam(String unitId, String type, String name);

	/**
	 * 
	 * 条件查询救援队伍列表
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param type
	 *            队伍类型
	 * @param name
	 *            队伍名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 救援队伍列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:55:15
	 */
	public List<Team> listTeam(String unitId, String type, String name,
			Integer startIndex, Integer limit);

}
