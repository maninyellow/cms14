package com.znsx.cms.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.DeviceManager;

/**
 * 设备更新时间变化的监听入库任务
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:07:50
 */
public class UpdateDeviceListenerTask {
	@Autowired
	private DeviceManager deviceManager;

	public void updateTime() {
		try {
			deviceManager.updateDeviceUpdateListener();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
