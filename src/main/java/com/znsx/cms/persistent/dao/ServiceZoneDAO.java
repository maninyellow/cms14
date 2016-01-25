package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.ServiceZone;

/**
 * ServiceZoneDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:53:43
 */
public interface ServiceZoneDAO extends BaseDAO<ServiceZone, String> {

	/**
	 * 获取服务区数量
	 * 
	 * @param name
	 *            服务区名称
	 * @param organId
	 *            机构id
	 * @return 服务区数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:09:37
	 */
	public Integer serviceZoneTotalCount(String name, String organId);

	/**
	 * 查询服务区列表
	 * 
	 * @param name
	 *            服务区名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 服务区列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:14:06
	 */
	public List<ServiceZone> listServiceZone(String name, Integer startIndex,
			Integer limit, String organId);

}
