package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 气象检测器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-19 下午4:57:08
 */
public class WeatherStat implements Serializable {
	private static final long serialVersionUID = -8766871134424277207L;
	private String id;
	private String name;
	private Das das;
	private String standardNumber;
	private Organ organ;
	private Integer period;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private Short vLowLimit;
	private Short wUpLimit;
	private Short rUpLimit;
	private Short sUpLimit;
	private String note;
	private Long createTime;
	private String reserve;
	private String navigation;
	private String ip;
	private Integer port;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Short getvLowLimit() {
		return vLowLimit;
	}

	public void setvLowLimit(Short vLowLimit) {
		this.vLowLimit = vLowLimit;
	}

	public Short getwUpLimit() {
		return wUpLimit;
	}

	public void setwUpLimit(Short wUpLimit) {
		this.wUpLimit = wUpLimit;
	}

	public Short getrUpLimit() {
		return rUpLimit;
	}

	public void setrUpLimit(Short rUpLimit) {
		this.rUpLimit = rUpLimit;
	}

	public Short getsUpLimit() {
		return sUpLimit;
	}

	public void setsUpLimit(Short sUpLimit) {
		this.sUpLimit = sUpLimit;
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

	public Das getDas() {
		return das;
	}

	public void setDas(Das das) {
		this.das = das;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
