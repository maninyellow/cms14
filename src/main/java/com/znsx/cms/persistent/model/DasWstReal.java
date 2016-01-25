package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasWstReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:16:00
 */
public class DasWstReal implements Serializable {

	private static final long serialVersionUID = -772448879808676197L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Integer visMax;
	private Integer visMin;
	private Integer visAvg;
	private String wsMax;
	private String wsMin;
	private String wsAvg;
	private Integer windDir;
	private String airTempMax;
	private String airTempMin;
	private String airTempAvg;
	private String roadTempMax;
	private String roadTempMin;
	private String roadTempAvg;
	private Short humiMax;
	private Short humiMin;
	private String humiAvg;
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

	public Integer getVisMax() {
		return visMax;
	}

	public void setVisMax(Integer visMax) {
		this.visMax = visMax;
	}

	public Integer getVisMin() {
		return visMin;
	}

	public void setVisMin(Integer visMin) {
		this.visMin = visMin;
	}

	public Integer getVisAvg() {
		return visAvg;
	}

	public void setVisAvg(Integer visAvg) {
		this.visAvg = visAvg;
	}

	public String getWsMax() {
		return wsMax;
	}

	public void setWsMax(String wsMax) {
		this.wsMax = wsMax;
	}

	public String getWsMin() {
		return wsMin;
	}

	public void setWsMin(String wsMin) {
		this.wsMin = wsMin;
	}

	public String getWsAvg() {
		return wsAvg;
	}

	public void setWsAvg(String wsAvg) {
		this.wsAvg = wsAvg;
	}

	public Integer getWindDir() {
		return windDir;
	}

	public void setWindDir(Integer windDir) {
		this.windDir = windDir;
	}

	public String getAirTempMax() {
		return airTempMax;
	}

	public void setAirTempMax(String airTempMax) {
		this.airTempMax = airTempMax;
	}

	public String getAirTempMin() {
		return airTempMin;
	}

	public void setAirTempMin(String airTempMin) {
		this.airTempMin = airTempMin;
	}

	public String getAirTempAvg() {
		return airTempAvg;
	}

	public void setAirTempAvg(String airTempAvg) {
		this.airTempAvg = airTempAvg;
	}

	public String getRoadTempMax() {
		return roadTempMax;
	}

	public void setRoadTempMax(String roadTempMax) {
		this.roadTempMax = roadTempMax;
	}

	public String getRoadTempMin() {
		return roadTempMin;
	}

	public void setRoadTempMin(String roadTempMin) {
		this.roadTempMin = roadTempMin;
	}

	public String getRoadTempAvg() {
		return roadTempAvg;
	}

	public void setRoadTempAvg(String roadTempAvg) {
		this.roadTempAvg = roadTempAvg;
	}

	public Short getHumiMax() {
		return humiMax;
	}

	public void setHumiMax(Short humiMax) {
		this.humiMax = humiMax;
	}

	public Short getHumiMin() {
		return humiMin;
	}

	public void setHumiMin(Short humiMin) {
		this.humiMin = humiMin;
	}

	public String getHumiAvg() {
		return humiAvg;
	}

	public void setHumiAvg(String humiAvg) {
		this.humiAvg = humiAvg;
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
