package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

/**
 * OrganNode
 * @author wangbinyu <p />
 * Create at 2013 上午11:28:53
 */
public class OrganNode {

	private String id;
	private String standardNumber;
	private String name;
	private String parentId;
	private String fax;
	private String contact;
	private String phone;
	private String address;
	private String areaCode;
	private String imageId;
	private String createTime;
	private String note;
	private List<DeviceNode> deviceNode = new ArrayList<DeviceNode>();
	private List<OrganNode> children = new ArrayList<OrganNode>();
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
	public List<DeviceNode> getDeviceNode() {
		return deviceNode;
	}
	public void setDeviceNode(List<DeviceNode> deviceNode) {
		this.deviceNode = deviceNode;
	}
	public List<OrganNode> getChildren() {
		return children;
	}
	public void setChildren(List<OrganNode> children) {
		this.children = children;
	}
}

