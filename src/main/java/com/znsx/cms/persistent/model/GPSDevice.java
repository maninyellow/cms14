package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * GPSDevice
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015-6-4 上午11:27:06
 */
public class GPSDevice implements Serializable {

	private static final long serialVersionUID = 4754757514705045437L;
	private String id;
	private TmDevice gps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TmDevice getGps() {
		return gps;
	}

	public void setGps(TmDevice gps) {
		this.gps = gps;
	}
}
