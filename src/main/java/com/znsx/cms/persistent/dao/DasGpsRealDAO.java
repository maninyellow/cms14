package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasGpsReal;

/**
 * DasGpsRealDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 下午2:47:41
 */
public interface DasGpsRealDAO extends BaseDAO<DasGpsReal, String> {

	/**
	 * 批量插入实时表数据
	 * 
	 * @param gpsList
	 *            GPS设备列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-31 下午2:51:35
	 */
	public void batchInsert(List<DasGpsReal> gpsList);
}
