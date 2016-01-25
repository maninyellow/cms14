package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceRail
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:22:36
 */
public class ControlDeviceRail extends ControlDevice implements Serializable {

	private static final long serialVersionUID = -6960786926761140722L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_RAIL + "";
	}
}
