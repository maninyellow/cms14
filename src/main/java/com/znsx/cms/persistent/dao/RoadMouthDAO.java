package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.RoadMouth;

/**
 * RoadMouthDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:37:36
 */
public interface RoadMouthDAO extends BaseDAO<RoadMouth, String> {

	/**
	 * 根据条件查询匝道列表
	 * 
	 * @param name
	 *            名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 匝道列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:00:24
	 */
	public List<RoadMouth> listRoadMouth(String name, Integer startIndex,
			Integer limit, String organId);

	/**
	 * 统计匝道数量
	 * 
	 * @param name
	 *            匝道名称
	 * @param organId
	 *            机构id
	 * @return 匝道数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:52:34
	 */
	public Integer roadMouthTotalCount(String name, String organId);

	/**
	 * 查询给定机构集合下面的出口匝道
	 * 
	 * @param organIds
	 *            机构ID集合
	 * @return 出口匝道列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-17 上午9:22:13
	 */
	public List<RoadMouth> listRoadMouths(List<String> organIds);

}
