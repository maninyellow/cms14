package com.znsx.cms.service.model;

/**
 * 道路设备状态
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 上午11:18:49
 */
public class RoadDeviceStatusVO {
	private String organId;
	private String organName;
	private String organSn;
	private String name;
	private String standardNumber;
	private String type;
	private String stakeNumber;
	private String ip;
	private String status;
	private String commStatus;

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

	public String getOrganSn() {
		return organSn;
	}

	public void setOrganSn(String organSn) {
		this.organSn = organSn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
	}

}
