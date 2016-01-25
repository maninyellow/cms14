package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 箱变电力监控实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:55:25
 */
public class BoxTransformer implements Serializable {
	private static final long serialVersionUID = 3081477497619063727L;
	private String id;
	private String name;
	private Das das;
	private String standardNumber;
	private Organ organ;
	private Integer period;
	private String navigation;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private String note;
	private String maxVoltage;
	private String maxCurrents;
	private String maxCapacity;
	private Long createTime;
	private String reserve;
	private String ip;
	private Integer port;

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

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMaxVoltage() {
		return maxVoltage;
	}

	public void setMaxVoltage(String maxVoltage) {
		this.maxVoltage = maxVoltage;
	}

	public String getMaxCurrents() {
		return maxCurrents;
	}

	public void setMaxCurrents(String maxCurrents) {
		this.maxCurrents = maxCurrents;
	}

	public String getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
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
