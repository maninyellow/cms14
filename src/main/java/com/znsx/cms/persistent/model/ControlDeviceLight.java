package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceLight
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:19:19
 */
public class ControlDeviceLight extends ControlDevice implements Serializable {

	private static final long serialVersionUID = -3118565475936996495L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_LIGHT + "";
	}
}
