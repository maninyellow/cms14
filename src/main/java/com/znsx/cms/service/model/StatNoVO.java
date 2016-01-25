package com.znsx.cms.service.model;

/**
 * StatNoVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:47:29
 */
public class StatNoVO {
	private String id;
	private String standardNumber;
	private Float noAvg;
	private Float noMax;
	private Float noMin;
	private Float no2Avg;
	private Float no2Max;
	private Float no2Min;
	private Long dateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Float getNoAvg() {
		return noAvg;
	}

	public void setNoAvg(Float noAvg) {
		this.noAvg = noAvg;
	}

	public Float getNoMax() {
		return noMax;
	}

	public void setNoMax(Float noMax) {
		this.noMax = noMax;
	}

	public Float getNoMin() {
		return noMin;
	}

	public void setNoMin(Float noMin) {
		this.noMin = noMin;
	}

	public Float getNo2Avg() {
		return no2Avg;
	}

	public void setNo2Avg(Float no2Avg) {
		this.no2Avg = no2Avg;
	}

	public Float getNo2Max() {
		return no2Max;
	}

	public void setNo2Max(Float no2Max) {
		this.no2Max = no2Max;
	}

	public Float getNo2Min() {
		return no2Min;
	}

	public void setNo2Min(Float no2Min) {
		this.no2Min = no2Min;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}
}
