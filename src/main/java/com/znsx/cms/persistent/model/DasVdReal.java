package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasVdReal
 * @author wangbinyu <p />
 * Create at 2015 下午5:13:59
 */
public class DasVdReal implements Serializable {

	private static final long serialVersionUID = -5038548953739023775L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Integer upFluxb;
	private Integer upFluxs;
	private Integer upFlux;
	private Integer dwFluxb;
	private Integer dwFluxs;
	private Integer dwFlux;
	private Integer upSpeed;
	private Integer dwSpeed;
	private Integer upOcc;
	private Integer dwOcc;
	private Short laneNumber;
	private Short status;
	private Short commStatus;
	private String reserve;
	private String organ;
	private Integer upFluxm;
	private Integer upFluxms;
	private Integer dwFluxm;
	private Integer dwFluxms;
	private Integer upHeadway;
	private Integer dwHeadway;
	private Integer upSpeedb;
	private Integer upSpeeds;
	private Integer upSpeedm;
	private Integer upSpeedms;
	private Integer dwSpeedb;
	private Integer dwSpeeds;
	private Integer dwSpeedm;
	private Integer dwSpeedms;
	private Integer upOccb;
	private Integer upOccs;
	private Integer upOccm;
	private Integer upOccms;
	private Integer dwOccb;
	private Integer dwOccs;
	private Integer dwOccm;
	private Integer dwOccms;
	private Short type;

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

	public Integer getUpFluxb() {
		return upFluxb;
	}

	public void setUpFluxb(Integer upFluxb) {
		this.upFluxb = upFluxb;
	}

	public Integer getUpFluxs() {
		return upFluxs;
	}

	public void setUpFluxs(Integer upFluxs) {
		this.upFluxs = upFluxs;
	}

	public Integer getUpFlux() {
		return upFlux;
	}

	public void setUpFlux(Integer upFlux) {
		this.upFlux = upFlux;
	}

	public Integer getDwFluxb() {
		return dwFluxb;
	}

	public void setDwFluxb(Integer dwFluxb) {
		this.dwFluxb = dwFluxb;
	}

	public Integer getDwFluxs() {
		return dwFluxs;
	}

	public void setDwFluxs(Integer dwFluxs) {
		this.dwFluxs = dwFluxs;
	}

	public Integer getDwFlux() {
		return dwFlux;
	}

	public void setDwFlux(Integer dwFlux) {
		this.dwFlux = dwFlux;
	}

	public Integer getUpSpeed() {
		return upSpeed;
	}

	public void setUpSpeed(Integer upSpeed) {
		this.upSpeed = upSpeed;
	}

	public Integer getDwSpeed() {
		return dwSpeed;
	}

	public void setDwSpeed(Integer dwSpeed) {
		this.dwSpeed = dwSpeed;
	}

	public Integer getUpOcc() {
		return upOcc;
	}

	public void setUpOcc(Integer upOcc) {
		this.upOcc = upOcc;
	}

	public Integer getDwOcc() {
		return dwOcc;
	}

	public void setDwOcc(Integer dwOcc) {
		this.dwOcc = dwOcc;
	}

	public Short getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Short laneNumber) {
		this.laneNumber = laneNumber;
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

	public Integer getUpFluxm() {
		return upFluxm;
	}

	public void setUpFluxm(Integer upFluxm) {
		this.upFluxm = upFluxm;
	}

	public Integer getUpFluxms() {
		return upFluxms;
	}

	public void setUpFluxms(Integer upFluxms) {
		this.upFluxms = upFluxms;
	}

	public Integer getDwFluxm() {
		return dwFluxm;
	}

	public void setDwFluxm(Integer dwFluxm) {
		this.dwFluxm = dwFluxm;
	}

	public Integer getDwFluxms() {
		return dwFluxms;
	}

	public void setDwFluxms(Integer dwFluxms) {
		this.dwFluxms = dwFluxms;
	}

	public Integer getUpHeadway() {
		return upHeadway;
	}

	public void setUpHeadway(Integer upHeadway) {
		this.upHeadway = upHeadway;
	}

	public Integer getDwHeadway() {
		return dwHeadway;
	}

	public void setDwHeadway(Integer dwHeadway) {
		this.dwHeadway = dwHeadway;
	}

	public Integer getUpSpeedb() {
		return upSpeedb;
	}

	public void setUpSpeedb(Integer upSpeedb) {
		this.upSpeedb = upSpeedb;
	}

	public Integer getUpSpeeds() {
		return upSpeeds;
	}

	public void setUpSpeeds(Integer upSpeeds) {
		this.upSpeeds = upSpeeds;
	}

	public Integer getUpSpeedm() {
		return upSpeedm;
	}

	public void setUpSpeedm(Integer upSpeedm) {
		this.upSpeedm = upSpeedm;
	}

	public Integer getUpSpeedms() {
		return upSpeedms;
	}

	public void setUpSpeedms(Integer upSpeedms) {
		this.upSpeedms = upSpeedms;
	}

	public Integer getDwSpeedb() {
		return dwSpeedb;
	}

	public void setDwSpeedb(Integer dwSpeedb) {
		this.dwSpeedb = dwSpeedb;
	}

	public Integer getDwSpeeds() {
		return dwSpeeds;
	}

	public void setDwSpeeds(Integer dwSpeeds) {
		this.dwSpeeds = dwSpeeds;
	}

	public Integer getDwSpeedm() {
		return dwSpeedm;
	}

	public void setDwSpeedm(Integer dwSpeedm) {
		this.dwSpeedm = dwSpeedm;
	}

	public Integer getDwSpeedms() {
		return dwSpeedms;
	}

	public void setDwSpeedms(Integer dwSpeedms) {
		this.dwSpeedms = dwSpeedms;
	}

	public Integer getUpOccb() {
		return upOccb;
	}

	public void setUpOccb(Integer upOccb) {
		this.upOccb = upOccb;
	}

	public Integer getUpOccs() {
		return upOccs;
	}

	public void setUpOccs(Integer upOccs) {
		this.upOccs = upOccs;
	}

	public Integer getUpOccm() {
		return upOccm;
	}

	public void setUpOccm(Integer upOccm) {
		this.upOccm = upOccm;
	}

	public Integer getUpOccms() {
		return upOccms;
	}

	public void setUpOccms(Integer upOccms) {
		this.upOccms = upOccms;
	}

	public Integer getDwOccb() {
		return dwOccb;
	}

	public void setDwOccb(Integer dwOccb) {
		this.dwOccb = dwOccb;
	}

	public Integer getDwOccs() {
		return dwOccs;
	}

	public void setDwOccs(Integer dwOccs) {
		this.dwOccs = dwOccs;
	}

	public Integer getDwOccm() {
		return dwOccm;
	}

	public void setDwOccm(Integer dwOccm) {
		this.dwOccm = dwOccm;
	}

	public Integer getDwOccms() {
		return dwOccms;
	}

	public void setDwOccms(Integer dwOccms) {
		this.dwOccms = dwOccms;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
}
