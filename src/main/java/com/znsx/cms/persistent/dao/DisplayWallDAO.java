package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.DisplayWall;

/**
 * 数据库电视墙接口类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:29:25
 */
public interface DisplayWallDAO extends BaseDAO<DisplayWall, String> {

	/**
	 * 
	 * 电视墙总计数
	 * 
	 * @param organId
	 *            机构ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:12:26
	 */
	public Integer wallTotalCount(String organId);

	/**
	 * 
	 * 根据机构ID查询电视墙列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 电视墙列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:21:11
	 */
	public List<DisplayWall> listWall(String organId, Integer startIndex,
			Integer limit);

	/**
	 * 返回以电视墙ID为键，电视墙对象为值的Map映射表
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-14 下午2:54:25
	 */
	public Map<String, DisplayWall> mapWallByOrganIds(String[] organIds);

}
