package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 厂商实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:27:30
 */
public class Manufacturer implements Serializable {
	private static final long serialVersionUID = 8106472995496266454L;
	private String id;
	private String name;
	private String protocol;
	private String contact;
	private String address;
	private String phone;
	private Long createTime;
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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
