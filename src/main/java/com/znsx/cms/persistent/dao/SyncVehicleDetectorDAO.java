package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.service.model.VdFluxByMonthVO;

/**
 * SyncVehicleDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:16:54
 */
public interface SyncVehicleDetectorDAO extends
		BaseDAO<SyncVehicleDetector, String> {

	/**
	 * 
	 * 统计车检器数据批量插入
	 * 
	 * @param list
	 *            统计车检器数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:58:45
	 */
	public void batchInsertSyncVd(List<SyncVehicleDetector> list);

	/**
	 * 按月统计车检器流量数据
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-21 下午2:54:39
	 */
	public List<VdFluxByMonthVO> listVdFluxByMonth();

}
