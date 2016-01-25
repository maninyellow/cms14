package com.znsx.cms.service.model;

/**
 * StatCoviVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-9-8 下午3:38:20
 */
public class StatCoviVO {
	private String standardNumber;
	private Float coAvg;
	private Float coMax;
	private Float coMin;
	private Float viAvg;
	private Float viMax;
	private Float viMin;
	private Long dateTime;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Float getCoAvg() {
		return coAvg;
	}

	public void setCoAvg(Float coAvg) {
		this.coAvg = coAvg;
	}

	public Float getCoMax() {
		return coMax;
	}

	public void setCoMax(Float coMax) {
		this.coMax = coMax;
	}

	public Float getCoMin() {
		return coMin;
	}

	public void setCoMin(Float coMin) {
		this.coMin = coMin;
	}

	public Float getViAvg() {
		return viAvg;
	}

	public void setViAvg(Float viAvg) {
		this.viAvg = viAvg;
	}

	public Float getViMax() {
		return viMax;
	}

	public void setViMax(Float viMax) {
		this.viMax = viMax;
	}

	public Float getViMin() {
		return viMin;
	}

	public void setViMin(Float viMin) {
		this.viMin = viMin;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
