package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasRoadDetectorReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午9:34:51
 */
public class DasRoadDetectorReal implements Serializable {
	private static final long serialVersionUID = 1756683591675166379L;

	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private String roadTempAvg;
	private String rainVol;
	private String snowVol;
	private Short roadSurface;
	private Short status;
	private Short commStatus;
	private String reserve;
	private String organ;

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

	public Timestamp getRecTime() {
		return recTime;
	}

	public void setRecTime(Timestamp recTime) {
		this.recTime = recTime;
	}

	public String getRoadTempAvg() {
		return roadTempAvg;
	}

	public void setRoadTempAvg(String roadTempAvg) {
		this.roadTempAvg = roadTempAvg;
	}

	public String getRainVol() {
		return rainVol;
	}

	public void setRainVol(String rainVol) {
		this.rainVol = rainVol;
	}

	public String getSnowVol() {
		return snowVol;
	}

	public void setSnowVol(String snowVol) {
		this.snowVol = snowVol;
	}

	public Short getRoadSurface() {
		return roadSurface;
	}

	public void setRoadSurface(Short roadSurface) {
		this.roadSurface = roadSurface;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Short commStatus) {
		this.commStatus = commStatus;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

}
