/**
 * 
 */
package com.znsx.cms.service.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.util.file.Configuration;

/**
 * @author znsx
 * 
 */
public class UpdateLowerDeviceStatusTask {

	@Autowired
	private ConnectManager connectManager;

	public void pushDeviceStatus() {
		String isPushDevice = Configuration.getInstance().loadProperties(
				"push_device_status");
		if (StringUtils.isNotBlank(isPushDevice)) {
			if (isPushDevice.equals("true")) {
				try {
					connectManager.pushDeviceStatus();
				} catch (BusinessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
