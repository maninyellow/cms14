package com.znsx.cms.service.model;

/**
 * sevice层返回的用户会话历史实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:43:34
 */
public class ListUserSessionHistoryVO {
	private String id;
	private String ticket;
	private String userId;
	private String logonName;
	private String userStandardNo;
	private String logonTime;
	private String logoffTime;
	private String ip;
	private String clientType;
	private String organId;
	private String organName;

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

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getUserStandardNo() {
		return userStandardNo;
	}

	public void setUserStandardNo(String userStandardNo) {
		this.userStandardNo = userStandardNo;
	}

	public String getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(String logonTime) {
		this.logonTime = logonTime;
	}

	public String getLogoffTime() {
		return logoffTime;
	}

	public void setLogoffTime(String logoffTime) {
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
}
