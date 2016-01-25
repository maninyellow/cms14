package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * GPSDeviceCamera
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午11:30:28
 */
public class GPSDeviceCamera extends GPSDevice implements Serializable {

	private static final long serialVersionUID = 1592597351047791021L;
	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
