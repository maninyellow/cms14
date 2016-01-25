package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceFan
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:17:48
 */
public class ControlDeviceFan extends ControlDevice implements Serializable {

	private static final long serialVersionUID = -2703670291992044065L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_FAN + "";
	}
}
