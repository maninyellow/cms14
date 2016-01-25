package com.znsx.cms.web.dto.omc;

/**
 * StoreVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:17:10
 */
public class StoreVO {
	private String id;
	private String minNumber;
	private String resourceNumber;
	private String status;
	private String name;
	private String note;
	private String resourceId;
	private String unitId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(String minNumber) {
		this.minNumber = minNumber;
	}

	public String getResourceNumber() {
		return resourceNumber;
	}

	public void setResourceNumber(String resourceNumber) {
		this.resourceNumber = resourceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
