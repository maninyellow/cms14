package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasLilReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:01:55
 */
public class DasLilReal implements Serializable {

	private static final long serialVersionUID = 4997936118227409933L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private Short backArrowStatus;
	private Short backXStatus;
	private Short frontArrowStatus;
	private Short frontXStatus;
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

	public Short getBackArrowStatus() {
		return backArrowStatus;
	}

	public void setBackArrowStatus(Short backArrowStatus) {
		this.backArrowStatus = backArrowStatus;
	}

	public Short getBackXStatus() {
		return backXStatus;
	}

	public void setBackXStatus(Short backXStatus) {
		this.backXStatus = backXStatus;
	}

	public Short getFrontArrowStatus() {
		return frontArrowStatus;
	}

	public void setFrontArrowStatus(Short frontArrowStatus) {
		this.frontArrowStatus = frontArrowStatus;
	}

	public Short getFrontXStatus() {
		return frontXStatus;
	}

	public void setFrontXStatus(Short frontXStatus) {
		this.frontXStatus = frontXStatus;
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
