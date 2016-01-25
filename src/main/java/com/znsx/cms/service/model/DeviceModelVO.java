package com.znsx.cms.service.model;

/**
 * sevice层查询厂商设备型号实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:01:44
 */
public class DeviceModelVO {
	private String id;
	private String name;
	private String type;
	private String manufacturerId;
	private String manufacturerName;
	private String note;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
