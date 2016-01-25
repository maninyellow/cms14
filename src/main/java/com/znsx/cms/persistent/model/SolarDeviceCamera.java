package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * SolarDeviceCamera
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:58:34
 */
public class SolarDeviceCamera extends SolarDevice implements Serializable {

	private static final long serialVersionUID = 4140890291949239215L;
	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
