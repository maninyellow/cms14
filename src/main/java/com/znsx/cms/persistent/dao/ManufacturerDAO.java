package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Manufacturer;

/**
 * 生产厂商数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:16:01
 */
public interface ManufacturerDAO extends BaseDAO<Manufacturer, String> {

	/**
	 * 
	 * 厂商列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 厂商列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:51:30
	 */
	public List<Manufacturer> listManufacturer(Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询厂商总计数
	 * 
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:56:21
	 */
	public Integer manufacturerTotalCount();

}
