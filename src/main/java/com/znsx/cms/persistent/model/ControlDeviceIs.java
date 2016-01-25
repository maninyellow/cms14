package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceIs
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午3:23:20
 */
public class ControlDeviceIs extends ControlDevice implements Serializable {

	private static final long serialVersionUID = 8356094161849899826L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_IS + "";
	}
}
