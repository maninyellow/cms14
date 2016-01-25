package com.znsx.cms.service.model;

/**
 * UserSessionVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午9:50:52
 */
public class UserSessionVO {
	private String id;
	private String ticket;
	private String logonName;
	private String standardNumber;
	private String logonTime;
	private String ip;
	private String clientType;
	private String organId;
	private String organName;
	private String path;
	private String updateTime;
	private String ccsStandardNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(String logonTime) {
		this.logonTime = logonTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCcsStandardNumber() {
		return ccsStandardNumber;
	}

	public void setCcsStandardNumber(String ccsStandardNumber) {
		this.ccsStandardNumber = ccsStandardNumber;
	}
}
