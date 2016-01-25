package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 机构实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:08:33
 */
public class Organ implements Serializable {

	private static final long serialVersionUID = 7722049132338189667L;
	private String id;
	private String standardNumber;
	private String name;
	private Organ parent;
	private String path;
	private String fax;
	private String contact;
	private String phone;
	private String address;
	private String areaCode;
	private String imageId;
	private Long createTime;
	private String note;
	private Integer deep;
	private String type;
	private String frontOrganId;
	private String backOrganId;
	private String stakeNumber;
	private String longitude;
	private String latitude;

	public String getFrontOrganId() {
		return frontOrganId;
	}

	public void setFrontOrganId(String frontOrganId) {
		this.frontOrganId = frontOrganId;
	}

	public String getBackOrganId() {
		return backOrganId;
	}

	public void setBackOrganId(String backOrganId) {
		this.backOrganId = backOrganId;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public Organ getParent() {
		return parent;
	}

	public void setParent(Organ parent) {
		this.parent = parent;
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

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
