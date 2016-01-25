package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 值班计划
 * 
 * @author huangbuji
 *         <p />
 *         2014-12-3 上午9:53:28
 */

public class WorkPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7186858177188485103L;
	private String id;
	private String name;
	private Timestamp workDate;
	private String phone;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Timestamp workDate) {
		this.workDate = workDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
