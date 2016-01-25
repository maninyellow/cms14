package com.znsx.cms.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.iface.DasDataManager;

/**
 * 开关量设备状态数据定期移除任务
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-10 下午3:23:37
 */
public class SwitchDataRemoveTask {
	@Autowired
	private DasDataManager dasDataManager;
	
	public void removeSwitchData() {
		try {
			dasDataManager.removeSwitchData();
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println(e.toString());
			System.err.println(e.getStackTrace()[0].toString());
		}
	}
}
