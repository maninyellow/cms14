package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasTslReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:13:12
 */
public class DasTslReal implements Serializable {

	private static final long serialVersionUID = 1900287992683877983L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Short greenStatus;
	private Short redStatus;
	private Short yellowStatus;
	private Short turnStatus;
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

	public Short getGreenStatus() {
		return greenStatus;
	}

	public void setGreenStatus(Short greenStatus) {
		this.greenStatus = greenStatus;
	}

	public Short getRedStatus() {
		return redStatus;
	}

	public void setRedStatus(Short redStatus) {
		this.redStatus = redStatus;
	}

	public Short getYellowStatus() {
		return yellowStatus;
	}

	public void setYellowStatus(Short yellowStatus) {
		this.yellowStatus = yellowStatus;
	}

	public Short getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(Short turnStatus) {
		this.turnStatus = turnStatus;
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
