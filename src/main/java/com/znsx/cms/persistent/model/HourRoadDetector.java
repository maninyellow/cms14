package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * HourRoadDetector
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-31 下午6:56:31
 */
public class HourRoadDetector implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9041847502204759293L;
	private String id;
	private String standardNumber;
	private Float roadTempMax;
	private Float roadTempMin;
	private Float roadTempAvg;
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
