package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 风速风向检测器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-19 下午4:13:00
 */
public class WindSpeed implements Serializable {
	private static final long serialVersionUID = 8250479667643553078L;
	private String id;
	private String name;
	private Das das;
	private String standardNumber;
	private Organ organ;
	private Integer period;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private Short direction;
	private Integer wUpLimit;
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

	public Short getDirection() {
		return direction;
	}

	public void setDirection(Short direction) {
		this.direction = direction;
	}

	public Integer getwUpLimit() {
		return wUpLimit;
	}

	public void setwUpLimit(Integer wUpLimit) {
		this.wUpLimit = wUpLimit;
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
