package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasDeviceStatusReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:00:14
 */
public class DasDeviceStatusReal implements Serializable {

	private static final long serialVersionUID = 974240280638486303L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Integer type;
	private Short status;
	private Short commStatus;
	private String organ;
	private String faultMessage;

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

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}
}
