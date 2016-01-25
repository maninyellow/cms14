package com.znsx.cms.service.model;

/**
 * 根据用户ID查询用户详细信息业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:17:37
 */
public class GetUserVO {
	private String id;
	private String standardNumber;
	private String ccsId;
	private String ccsName;
	private String name;
	private String password;
	private String logonName;
	private String sex;
	private String email;
	private String phone;
	private String address;
	private String organId;
	private String organName;
	private String imageId;
	private String priority;
	private String createTime;
	private String note;
	private String status;
	private String maxConnect;
	private String roleNames;
	private String onlineStatus;

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

	public String getCcsId() {
		return ccsId;
	}

	public void setCcsId(String ccsId) {
		this.ccsId = ccsId;
	}

	public String getCcsName() {
		return ccsName;
	}

	public void setCcsName(String ccsName) {
		this.ccsName = ccsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxConnect() {
		return maxConnect;
	}

	public void setMaxConnect(String maxConnect) {
		this.maxConnect = maxConnect;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

}
