package com.znsx.cms.persistent.model;

/**
 * SubVehicleDetector
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:22:00
 */
public class SubVehicleDetector extends FVehicleDetector {

	private static final long serialVersionUID = -4607305821502580033L;
	private VehicleDetector parent;

	public VehicleDetector getParent() {
		return parent;
	}

	public void setParent(VehicleDetector parent) {
		this.parent = parent;
	}
}
