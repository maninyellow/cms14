package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 定时策略关联实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:41:05
 */
public class TimePolicy implements Serializable {
	private static final long serialVersionUID = -267721993166186783L;
	private String id;
	private String timePolicyId;
	private String policyId;
	private Long beginDate;
	private Long endDate;
	private Integer beginTime;
	private Integer endTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimePolicyId() {
		return timePolicyId;
	}

	public void setTimePolicyId(String timePolicyId) {
		this.timePolicyId = timePolicyId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Integer getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

}
