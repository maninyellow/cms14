package com.znsx.cms.service.model;

/**
 * 根据机构ID查询机构详细信息业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:18:27
 */
public class GetOrganVO {
	private String id;
	private String standardNumber;
	private String name;
	private String parentId;
	private String parentName;
	private String path;
	private String fax;
	private String contact;
	private String phone;
	private String address;
	private String areaCode;
	private String imageId;
	private String createTime;
	private String note;
	private String type;
	private String deep;
	private String frontOrganId;
	private String backOrganId;
	private String stakeNumber;
	private String height;
	private String length;
	private String laneNumber;
	private String limitSpeed;
	private String capacity;
	private String leftDirection;
	private String rightDirection;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}

	public String getLimitSpeed() {
		return limitSpeed;
	}

	public void setLimitSpeed(String limitSpeed) {
		this.limitSpeed = limitSpeed;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getLeftDirection() {
		return leftDirection;
	}

	public void setLeftDirection(String leftDirection) {
		this.leftDirection = leftDirection;
	}

	public String getRightDirection() {
		return rightDirection;
	}

	public void setRightDirection(String rightDirection) {
		this.rightDirection = rightDirection;
	}

}
