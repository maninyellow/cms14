package com.znsx.cms.service.model;

/**
 * 定时策略的策略执行计划业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-28 上午10:30:40
 */
public class TimePolicyItemVO {
	private String lightPolicyId;
	private String lightPolicyName;
	private String beginDate;
	private String endDate;
	private String beginTime;
	private String endTime;

	public String getLightPolicyId() {
		return lightPolicyId;
	}

	public void setLightPolicyId(String lightPolicyId) {
		this.lightPolicyId = lightPolicyId;
	}

	public String getLightPolicyName() {
		return lightPolicyName;
	}

	public void setLightPolicyName(String lightPolicyName) {
		this.lightPolicyName = lightPolicyName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
