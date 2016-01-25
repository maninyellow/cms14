package com.znsx.cms.service.model;

/**
 * DeviceSolarVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:52:08
 */
public class DeviceSolarVO {
	private String id;
	private String solarSN;
	private String solarName;
	private String solarStakeNumber;
	private String solarNavigation;
	private String batteryCapacity;
	private String deviceId;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSolarSN() {
		return solarSN;
	}

	public void setSolarSN(String solarSN) {
		this.solarSN = solarSN;
	}

	public String getSolarName() {
		return solarName;
	}

	public void setSolarName(String solarName) {
		this.solarName = solarName;
	}

	public String getSolarStakeNumber() {
		return solarStakeNumber;
	}

	public void setSolarStakeNumber(String solarStakeNumber) {
		this.solarStakeNumber = solarStakeNumber;
	}

	public String getSolarNavigation() {
		return solarNavigation;
	}

	public void setSolarNavigation(String solarNavigation) {
		this.solarNavigation = solarNavigation;
	}

	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
