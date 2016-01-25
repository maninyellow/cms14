package com.znsx.cms.service.model;

/**
 * StatWstVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-26 上午11:47:42
 */
public class StatWstVO {
	private String standardNumber;
	private Float visAvg;
	private Float wsAvg;
	private Float airTempMax;
	private Float airTempMin;
	private Float airTempAvg;
	private Float roadTempMax;
	private Float roadTempMin;
	private Float roadTempAvg;
	private Long dateTime;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Float getVisAvg() {
		return visAvg;
	}

	public void setVisAvg(Float visAvg) {
		this.visAvg = visAvg;
	}

	public Float getWsAvg() {
		return wsAvg;
	}

	public void setWsAvg(Float wsAvg) {
		this.wsAvg = wsAvg;
	}

	public Float getAirTempMax() {
		return airTempMax;
	}

	public void setAirTempMax(Float airTempMax) {
		this.airTempMax = airTempMax;
	}

	public Float getAirTempMin() {
		return airTempMin;
	}

	public void setAirTempMin(Float airTempMin) {
		this.airTempMin = airTempMin;
	}

	public Float getAirTempAvg() {
		return airTempAvg;
	}

	public void setAirTempAvg(Float airTempAvg) {
		this.airTempAvg = airTempAvg;
	}

	public Float getRoadTempMax() {
		return roadTempMax;
	}

	public void setRoadTempMax(Float roadTempMax) {
		this.roadTempMax = roadTempMax;
	}

	public Float getRoadTempMin() {
		return roadTempMin;
	}

	public void setRoadTempMin(Float roadTempMin) {
		this.roadTempMin = roadTempMin;
	}

	public Float getRoadTempAvg() {
		return roadTempAvg;
	}

	public void setRoadTempAvg(Float roadTempAvg) {
		this.roadTempAvg = roadTempAvg;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
