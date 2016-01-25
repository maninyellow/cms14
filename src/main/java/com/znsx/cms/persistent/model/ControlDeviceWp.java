package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceWp
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:21:45
 */
public class ControlDeviceWp extends ControlDevice implements Serializable {

	private static final long serialVersionUID = 888435193875125557L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_WP + "";
	}
}
