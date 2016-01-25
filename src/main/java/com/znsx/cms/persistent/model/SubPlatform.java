package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 下级平台实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:10:22
 */
public class SubPlatform implements Serializable {
	private static final long serialVersionUID = -2542601805021237274L;
	private String id;
	private String name;
	private String standardNumber;
	private String sipIp;
	private Integer sipPort;
	private Long updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSipIp() {
		return sipIp;
	}

	public void setSipIp(String sipIp) {
		this.sipIp = sipIp;
	}

	public Integer getSipPort() {
		return sipPort;
	}

	public void setSipPort(Integer sipPort) {
		this.sipPort = sipPort;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
