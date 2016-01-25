package com.znsx.cms.service.model;

/**
 * service层返回车辆检测器对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:26:58
 */
public class GetVehicleDetectorVO {
	private String id;
	private String name;
	private String dasId;
	private String dasName;
	private String standardNumber;
	private String organId;
	private String period;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private String sUpLimit;
	private String sLowLimit;
	private String oUpLimit;
	private String oLowLimit;
	private String vUpLimit;
	private String vLowLimit;
	private String note;
	private String createTime;
	private String reserve;
	private String navigation;
	private String ip;
	private String port;
	private String laneNumber;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDasId() {
		return dasId;
	}

	public void setDasId(String dasId) {
		this.dasId = dasId;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
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

	public String getsUpLimit() {
		return sUpLimit;
	}

	public void setsUpLimit(String sUpLimit) {
		this.sUpLimit = sUpLimit;
	}

	public String getsLowLimit() {
		return sLowLimit;
	}

	public void setsLowLimit(String sLowLimit) {
		this.sLowLimit = sLowLimit;
	}

	public String getoUpLimit() {
		return oUpLimit;
	}

	public void setoUpLimit(String oUpLimit) {
		this.oUpLimit = oUpLimit;
	}

	public String getoLowLimit() {
		return oLowLimit;
	}

	public void setoLowLimit(String oLowLimit) {
		this.oLowLimit = oLowLimit;
	}

	public String getvUpLimit() {
		return vUpLimit;
	}

	public void setvUpLimit(String vUpLimit) {
		this.vUpLimit = vUpLimit;
	}

	public String getvLowLimit() {
		return vLowLimit;
	}

	public void setvLowLimit(String vLowLimit) {
		this.vLowLimit = vLowLimit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
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

	public String getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}
}
