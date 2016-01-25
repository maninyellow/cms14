package com.znsx.cms.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构树对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:49:34
 */
public class OrganVO {
	private String id;
	private String standardNumber;
	private String name;
	private String parentId;
	private String path;
	private String fax;
	private String contact;
	private String phone;
	private String address;
	private String areaCode;
	private String imageId;
	private String createTime;
	private String note;
	private List<OrganVO> children = new ArrayList<OrganVO>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<OrganVO> getChildren() {
		return children;
	}

	public void setChildren(List<OrganVO> children) {
		this.children = children;
	}

}
