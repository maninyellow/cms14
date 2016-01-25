package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 许可证实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:21:37
 */
public class License implements Serializable {
	private static final long serialVersionUID = 5411303435066349495L;
	private String id;
	private String macList;
	private String cpuidList;
	private String motherBoardList;
	private String cameraAmount;
	private String userAmount;
	private String deviceAmount;
	private String expireTime;
	private String signature;
	private String projectName;
	private String linkMan;
	private String contact;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMacList() {
		return macList;
	}

	public void setMacList(String macList) {
		this.macList = macList;
	}

	public String getCpuidList() {
		return cpuidList;
	}

	public void setCpuidList(String cpuidList) {
		this.cpuidList = cpuidList;
	}

	public String getMotherBoardList() {
		return motherBoardList;
	}

	public void setMotherBoardList(String motherBoardList) {
		this.motherBoardList = motherBoardList;
	}

	public String getCameraAmount() {
		return cameraAmount;
	}

	public void setCameraAmount(String cameraAmount) {
		this.cameraAmount = cameraAmount;
	}

	public String getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(String userAmount) {
		this.userAmount = userAmount;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("user_amount = ");
		sb.append(userAmount);
		sb.append("\r\n");
		sb.append("camera_amount = ");
		sb.append(cameraAmount);
		sb.append("\r\n");
		sb.append("device_amount = ");
		sb.append(deviceAmount);
		sb.append("\r\n");
		sb.append("expire_time = ");
		sb.append(expireTime);
		sb.append("\r\n");
		sb.append("mother_board_list = ");
		if (motherBoardList != null) {
			sb.append(motherBoardList);
		}
		sb.append("\r\n");
		sb.append("cpuid_list = ");
		if (cpuidList != null) {
			sb.append(cpuidList);
		}
		sb.append("\r\n");
		sb.append("mac_list = ");
		if (macList != null) {
			sb.append(macList);
		}
		sb.append("\r\n");
		return sb.toString();
	}

}
