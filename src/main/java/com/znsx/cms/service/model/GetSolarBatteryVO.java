package com.znsx.cms.service.model;

/**
 * GetSolarBatteryVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:16:08
 */
public class GetSolarBatteryVO {
	private String id;
	private String organId;
	private String standardNumber;
	private String name;
	private String maxVoltage;
	private String minVoltage;
	private String batteryCapacity;
	private String storePlan;
	private String createTime;
	private String dasId;
	private String note;
	private String navigation;
	private String stakeNumber;
	private String period;
	private String dasName;

	public String getDasName() {
		return dasName;
	}

	public void setDasName(String dasName) {
		this.dasName = dasName;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDasId() {
		return dasId;
	}

	public void setDasId(String dasId) {
		this.dasId = dasId;
	}

}
