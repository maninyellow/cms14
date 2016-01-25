package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * SolarDevice
 * @author wangbinyu <p />
 * Create at 2014 上午11:57:11
 */
public class SolarDevice implements Serializable {

	private static final long serialVersionUID = 2775427710801331460L;
	private String id;
	private SolarBattery solarBattery;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SolarBattery getSolarBattery() {
		return solarBattery;
	}

	public void setSolarBattery(SolarBattery solarBattery) {
		this.solarBattery = solarBattery;
	}
}
