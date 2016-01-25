package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * ControlDeviceCms
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-4-30 下午2:55:20
 */
public class ControlDeviceCms extends ControlDevice implements Serializable {

	private static final long serialVersionUID = -687468799112060526L;
	
	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_CMS + "";
	}
}
