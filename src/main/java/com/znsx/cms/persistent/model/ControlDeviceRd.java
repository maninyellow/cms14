package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceRd
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:20:27
 */
public class ControlDeviceRd extends ControlDevice implements Serializable {

	private static final long serialVersionUID = -4142394445346360517L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_RD + "";
	}
}
