package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Gis;

/**
 * GisDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:53:14
 */
public interface GisDAO extends BaseDAO<Gis, String> {

	/**
	 * 根据名称查询gis服务器列表
	 * 
	 * @param name
	 *            gis名称
	 * @return gis服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:00:09
	 */
	public List<Gis> findGisByName(String name);

}
