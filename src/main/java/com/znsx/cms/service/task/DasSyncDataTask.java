package com.znsx.cms.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.DasDataManager;

/**
 * 同步DAS数据
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:52:50
 */
public class DasSyncDataTask {

	@Autowired
	private DasDataManager dasDataManager;

	public void syncVehicleDetector() {
		try {
			dasDataManager.vdStatByMonth();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
