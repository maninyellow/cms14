package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasWsReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:15:26
 */
public class DasWsReal implements Serializable {

	private static final long serialVersionUID = 2356043553667642553L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Short direction;
	private Integer speed;
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

	public Short getDirection() {
		return direction;
	}

	public void setDirection(Short direction) {
		this.direction = direction;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
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
