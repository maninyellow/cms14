package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * FVehicleDetector
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:21:29
 */
public class FVehicleDetector implements Serializable {
	private static final long serialVersionUID = -8185324481031215884L;
	private String id;
	private String name;
	private Das das;
	private String standardNumber;
	private Organ organ;
	private Integer period;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private Integer sUpLimit;
	private Integer sLowLimit;
	private Integer oUpLimit;
	private Integer oLowLimit;
	private Integer vUpLimit;
	private Integer vLowLimit;
	private String note;
	private Long createTime;
	private String reserve;
	private String navigation;
	private String port;
	private String ip;
	private String laneNumber;

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

	public Das getDas() {
		return das;
	}

	public void setDas(Das das) {
		this.das = das;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
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

	public Integer getsUpLimit() {
		return sUpLimit;
	}

	public void setsUpLimit(Integer sUpLimit) {
		this.sUpLimit = sUpLimit;
	}

	public Integer getsLowLimit() {
		return sLowLimit;
	}

	public void setsLowLimit(Integer sLowLimit) {
		this.sLowLimit = sLowLimit;
	}

	public Integer getoUpLimit() {
		return oUpLimit;
	}

	public void setoUpLimit(Integer oUpLimit) {
		this.oUpLimit = oUpLimit;
	}

	public Integer getoLowLimit() {
		return oLowLimit;
	}

	public void setoLowLimit(Integer oLowLimit) {
		this.oLowLimit = oLowLimit;
	}

	public Integer getvUpLimit() {
		return vUpLimit;
	}

	public void setvUpLimit(Integer vUpLimit) {
		this.vUpLimit = vUpLimit;
	}

	public Integer getvLowLimit() {
		return vLowLimit;
	}

	public void setvLowLimit(Integer vLowLimit) {
		this.vLowLimit = vLowLimit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}
}
