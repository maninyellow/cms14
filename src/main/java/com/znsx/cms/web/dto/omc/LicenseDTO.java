package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 获取License接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:16:15
 */
public class LicenseDTO extends BaseDTO {
	private String userAmount;
	private String cameraAmount;
	private String deviceAmount;
	private String expireTime;
	private String projectName;
	private String linkMan;
	private String contact;

	public String getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(String userAmount) {
		this.userAmount = userAmount;
	}

	public String getCameraAmount() {
		return cameraAmount;
	}

	public void setCameraAmount(String cameraAmount) {
		this.cameraAmount = cameraAmount;
	}

	public String getDeviceAmount() {
		return deviceAmount;
	}

	public void setDeviceAmount(String deviceAmount) {
		this.deviceAmount = deviceAmount;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
