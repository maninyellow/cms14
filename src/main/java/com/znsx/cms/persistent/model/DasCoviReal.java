package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DasCoviReal
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:59:21
 */
public class DasCoviReal implements Serializable {

	private static final long serialVersionUID = -8966919800542247645L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private String co;
	private String vi;
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

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getVi() {
		return vi;
	}

	public void setVi(String vi) {
		this.vi = vi;
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
