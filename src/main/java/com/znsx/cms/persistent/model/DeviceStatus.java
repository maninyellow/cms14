package com.znsx.cms.persistent.model;

import java.sql.Timestamp;

/**
 * 设备状态
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-12 下午5:21:27
 */
public class DeviceStatus {
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Integer type;
	private Short status;
	private Short commStatus;
	private String organSN;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getOrganSN() {
		return organSN;
	}

	public void setOrganSN(String organSN) {
		this.organSN = organSN;
	}
}
