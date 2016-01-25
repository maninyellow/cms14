package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 用户历史会话实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:03:08
 */
public class UserSessionHistory implements Serializable {
	private static final long serialVersionUID = -8874486359900060291L;
	private String id;
	private String ticket;
	private String userId;
	private String organId;
	private String organName;
	private String path;
	private String logonName;
	private String standardNumber;
	private Long logonTime;
	private Long logoffTime;
	private String ip;
	private String clientType;
	private Short kickFlag;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Long getLogoffTime() {
		return logoffTime;
	}

	public void setLogoffTime(Long logoffTime) {
		this.logoffTime = logoffTime;
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

	public Short getKickFlag() {
		return kickFlag;
	}

	public void setKickFlag(Short kickFlag) {
		this.kickFlag = kickFlag;
	}

}
