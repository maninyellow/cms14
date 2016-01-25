package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 设备类型实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:03:56
 */
public class DeviceModel implements Serializable {
	private static final long serialVersionUID = 8527056039513282227L;
	private String id;
	private String name;
	private String type;
	private Manufacturer manufacturer;
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
