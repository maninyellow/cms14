package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * 交通信号灯
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-16 上午10:37:33
 */
public class ControlDeviceTsl extends ControlDevice implements Serializable {
	private static final long serialVersionUID = 3530842234052922894L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_TSL + "";
	}
}
