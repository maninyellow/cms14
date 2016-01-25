package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 设备实体, 拥有Dvr, Camera子类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:43:49
 */
public class Device implements Serializable {
	private static final long serialVersionUID = -722084148555148441L;
	private String id;
	private String standardNumber;
	private Integer type;
	private String subType;
	private String name;
	private Long createTime;
	private Organ organ;
	private Manufacturer manufacturer;
	private String location;
	private String note;
	private VideoDeviceProperty property;
	private Integer channelAmount;
	private DeviceModel deviceModel;
	private Integer status;
	private String longitude;
	private String latitude;
	private String stakeNumber;
	private String navigation;

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public VideoDeviceProperty getProperty() {
		return property;
	}

	public void setProperty(VideoDeviceProperty property) {
		this.property = property;
	}

	public Integer getChannelAmount() {
		return channelAmount;
	}

	public void setChannelAmount(Integer channelAmount) {
		this.channelAmount = channelAmount;
	}

	public DeviceModel getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

}
