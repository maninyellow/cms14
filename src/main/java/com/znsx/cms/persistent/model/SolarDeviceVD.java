package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * SolarDeviceVehilce
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:12:36
 */
public class SolarDeviceVD extends SolarDevice implements Serializable {

	private static final long serialVersionUID = 887073575596975008L;
	private VehicleDetector vd;

	public VehicleDetector getVd() {
		return vd;
	}

	public void setVd(VehicleDetector vd) {
		this.vd = vd;
	}

}
