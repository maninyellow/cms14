package com.znsx.cms.service.model;

/**
 * servcie层返回的单个氮氧化合物检测器实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:55:35
 */
public class GetNoDetectorVO {
	private String createTime;
	private String id;
	private String period;
	private String noConctLimit;
	private String nooConctLimit;
	private String stakeNumber;
	private String dasId;
	private String dasName;
	private String organId;
	private String longitude;
	private String latitude;
	private String reserve;
	private String name;
	private String note;
	private String standardNumber;
	private String navigation;
	private String ip;
	private String port;

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getDasName() {
		return dasName;
	}

	public void setDasName(String dasName) {
		this.dasName = dasName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getDasId() {
		return dasId;
	}

	public void setDasId(String dasId) {
		this.dasId = dasId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
