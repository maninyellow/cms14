package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 用户会话实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午8:48:12
 */
public class UserSession implements Serializable {
	private static final long serialVersionUID = 6639071521124663853L;
	private String id;
	private String ticket;
	private String logonName;
	private String standardNumber;
	private Long logonTime;
	private String ip;
	private String clientType;
	private String organId;
	private String organName;
	private String path;
	private Long updateTime;

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

	public Long getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(Long logonTime) {
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
