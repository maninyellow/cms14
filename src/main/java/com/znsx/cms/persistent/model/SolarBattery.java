package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * SolarBattery
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:07:18
 */
public class SolarBattery implements Serializable {

	private static final long serialVersionUID = 8063969637344725983L;

	private String id;
	private Organ organ;
	private Das das;
	private String standardNumber;
	private String name;
	private String maxVoltage;
	private String minVoltage;
	private String batteryCapacity;
	private String storePlan;
	private Long createTime;
	private String note;
	private String navigation;
	private String stakeNumber;
	private String period;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaxVoltage() {
		return maxVoltage;
	}

	public void setMaxVoltage(String maxVoltage) {
		this.maxVoltage = maxVoltage;
	}

	public String getMinVoltage() {
		return minVoltage;
	}

	public void setMinVoltage(String minVoltage) {
		this.minVoltage = minVoltage;
	}

	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public String getStorePlan() {
		return storePlan;
	}

	public void setStorePlan(String storePlan) {
		this.storePlan = storePlan;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
