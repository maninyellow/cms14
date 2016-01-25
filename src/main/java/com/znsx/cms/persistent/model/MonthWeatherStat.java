package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * MonthWeatherStat
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-25 下午5:01:11
 */
public class MonthWeatherStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4598798844922111983L;
	private String id;
	private String standardNumber;
	private Float visMax;
	private Float visMin;
	private Float visAvg;
	private Float wsMax;
	private Float wsMin;
	private Float wsAvg;
	private Float winDir;
	private Float airTempMax;
	private Float airTempMin;
	private Float airTempAvg;
	private Float roadTempMax;
	private Float roadTempMin;
	private Float roadTempAvg;
	private Float humiMax;
	private Float humiMin;
	private Float humiAvg;
	private Float rainVol;
	private Float snowVol;
	private Integer roadSurface;
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

	public Float getVisMax() {
		return visMax;
	}

	public void setVisMax(Float visMax) {
		this.visMax = visMax;
	}

	public Float getVisMin() {
		return visMin;
	}

	public void setVisMin(Float visMin) {
		this.visMin = visMin;
	}

	public Float getVisAvg() {
		return visAvg;
	}

	public void setVisAvg(Float visAvg) {
		this.visAvg = visAvg;
	}

	public Float getWsMax() {
		return wsMax;
	}

	public void setWsMax(Float wsMax) {
		this.wsMax = wsMax;
	}

	public Float getWsMin() {
		return wsMin;
	}

	public void setWsMin(Float wsMin) {
		this.wsMin = wsMin;
	}

	public Float getWsAvg() {
		return wsAvg;
	}

	public void setWsAvg(Float wsAvg) {
		this.wsAvg = wsAvg;
	}

	public Float getWinDir() {
		return winDir;
	}

	public void setWinDir(Float winDir) {
		this.winDir = winDir;
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

	public Float getHumiMax() {
		return humiMax;
	}

	public void setHumiMax(Float humiMax) {
		this.humiMax = humiMax;
	}

	public Float getHumiMin() {
		return humiMin;
	}

	public void setHumiMin(Float humiMin) {
		this.humiMin = humiMin;
	}

	public Float getHumiAvg() {
		return humiAvg;
	}

	public void setHumiAvg(Float humiAvg) {
		this.humiAvg = humiAvg;
	}

	public Float getRainVol() {
		return rainVol;
	}

	public void setRainVol(Float rainVol) {
		this.rainVol = rainVol;
	}

	public Float getSnowVol() {
		return snowVol;
	}

	public void setSnowVol(Float snowVol) {
		this.snowVol = snowVol;
	}

	public Integer getRoadSurface() {
		return roadSurface;
	}

	public void setRoadSurface(Integer roadSurface) {
		this.roadSurface = roadSurface;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
