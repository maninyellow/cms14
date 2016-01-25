package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * 车道指示灯
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-16 上午10:41:56
 */
public class ControlDeviceLil extends ControlDevice implements Serializable {
	private static final long serialVersionUID = -1924932231830325916L;

	@Override
	public String getType() {
		return TypeDefinition.DEVICE_TYPE_LIL + "";
	}
}
