package com.znsx.cms.service.model;

/**
 * 照明灯业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-27 上午11:10:53
 */
public class LightVO {
	private String id;
	private String name;
	private String standardNumber;
	private String status;

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
		if (null == name) {
			this.name = "";
		}
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
		if (null == standardNumber) {
			this.standardNumber = "";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		if (null == status) {
			this.status = "";
		}
	}

}
