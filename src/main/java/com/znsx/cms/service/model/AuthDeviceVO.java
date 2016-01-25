package com.znsx.cms.service.model;

/**
 * 权限设备业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-19 下午3:12:35
 */
public class AuthDeviceVO {
	private String id;
	private String organId;
	private String standardNumber;
	private String name;
	private String auth;
	private String longitude;
	private String latitude;
	private String stakeNumber;
	private String type;
	private String subType;
	private String status;
	private String imageId;
	private String channelNumber;
	private String location;
	private String dasSN;
	private String navigation;
	private String width;
	private String height;
	private String manufacture;
	private String linkMan;
	private String telephone;
	private String managerUnit;
	private String solarSN;
	private String solarName;
	private String solarStakeNumber;
	private String solarNavigation;
	private String batteryCapacity;
	private String ccsSN;
	private String speedUpLimit;
	private String speedLowLimit;
	private String occUpLimit;
	private String occLowLimit;
	private String volumeUpLimit;
	private String volumeLowLimit;
	private String windUpLimit;
	private String viLowLimit;
	private String rainUpLimit;
	private String snowUpLimit;
	private String loLumiMax;
	private String loLumiMin;
	private String liLumiMax;
	private String liLumiMin;
	private String coConctLimit;
	private String noConctLimit;
	private String nooConctLimit;
//	private String deviceModel;
//	private String 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (null == name) {
			this.name = "";
		}
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
		if (null == longitude) {
			this.longitude = "";
		}
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
		if (null == latitude) {
			this.latitude = "";
		}
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
		if (null == stakeNumber) {
			this.stakeNumber = "";
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
		if (null == subType) {
			this.subType = "";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		if (null == status) {
			this.status = "";
		}
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
		if (null == imageId) {
			this.imageId = "";
		}
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
		if (null == channelNumber) {
			this.channelNumber = "";
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
		if (null == location) {
			this.location = "";
		}
	}

	public String getDasSN() {
		return dasSN;
	}

	public void setDasSN(String dasSN) {
		this.dasSN = dasSN;
		if (null == dasSN) {
			this.dasSN = "";
		}
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
		if (null == navigation) {
			this.navigation = "";
		}
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
		if (null == width) {
			this.width = "";
		}
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
		if (null == height) {
			this.height = "";
		}
	}

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
		if (null == manufacture) {
			this.manufacture = "";
		}
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
		if (null == linkMan) {
			this.linkMan = "";
		}
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
		if (null == telephone) {
			this.telephone = "";
		}
	}

	public String getManagerUnit() {
		return managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
		if (null == managerUnit) {
			this.managerUnit = "";
		}
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

	public String getCcsSN() {
		return ccsSN;
	}

	public void setCcsSN(String ccsSN) {
		this.ccsSN = ccsSN;
	}

	public String getSpeedUpLimit() {
		return speedUpLimit;
	}

	public void setSpeedUpLimit(String speedUpLimit) {
		this.speedUpLimit = speedUpLimit;
	}

	public String getSpeedLowLimit() {
		return speedLowLimit;
	}

	public void setSpeedLowLimit(String speedLowLimit) {
		this.speedLowLimit = speedLowLimit;
	}

	public String getOccUpLimit() {
		return occUpLimit;
	}

	public void setOccUpLimit(String occUpLimit) {
		this.occUpLimit = occUpLimit;
	}

	public String getOccLowLimit() {
		return occLowLimit;
	}

	public void setOccLowLimit(String occLowLimit) {
		this.occLowLimit = occLowLimit;
	}

	public String getVolumeUpLimit() {
		return volumeUpLimit;
	}

	public void setVolumeUpLimit(String volumeUpLimit) {
		this.volumeUpLimit = volumeUpLimit;
	}

	public String getVolumeLowLimit() {
		return volumeLowLimit;
	}

	public void setVolumeLowLimit(String volumeLowLimit) {
		this.volumeLowLimit = volumeLowLimit;
	}

	public String getWindUpLimit() {
		return windUpLimit;
	}

	public void setWindUpLimit(String windUpLimit) {
		this.windUpLimit = windUpLimit;
	}

	public String getViLowLimit() {
		return viLowLimit;
	}

	public void setViLowLimit(String viLowLimit) {
		this.viLowLimit = viLowLimit;
	}

	public String getRainUpLimit() {
		return rainUpLimit;
	}

	public void setRainUpLimit(String rainUpLimit) {
		this.rainUpLimit = rainUpLimit;
	}

	public String getSnowUpLimit() {
		return snowUpLimit;
	}

	public void setSnowUpLimit(String snowUpLimit) {
		this.snowUpLimit = snowUpLimit;
	}

	public String getLoLumiMax() {
		return loLumiMax;
	}

	public void setLoLumiMax(String loLumiMax) {
		this.loLumiMax = loLumiMax;
	}

	public String getLoLumiMin() {
		return loLumiMin;
	}

	public void setLoLumiMin(String loLumiMin) {
		this.loLumiMin = loLumiMin;
	}

	public String getLiLumiMax() {
		return liLumiMax;
	}

	public void setLiLumiMax(String liLumiMax) {
		this.liLumiMax = liLumiMax;
	}

	public String getLiLumiMin() {
		return liLumiMin;
	}

	public void setLiLumiMin(String liLumiMin) {
		this.liLumiMin = liLumiMin;
	}

	public String getCoConctLimit() {
		return coConctLimit;
	}

	public void setCoConctLimit(String coConctLimit) {
		this.coConctLimit = coConctLimit;
	}

	public String getNoConctLimit() {
		return noConctLimit;
	}

	public void setNoConctLimit(String noConctLimit) {
		this.noConctLimit = noConctLimit;
	}

	public String getNooConctLimit() {
		return nooConctLimit;
	}

	public void setNooConctLimit(String nooConctLimit) {
		this.nooConctLimit = nooConctLimit;
	}

}
