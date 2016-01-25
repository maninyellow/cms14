package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 平台服务器实体,拥有Ccs, Crs, Mss, Pts子类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:12:03
 */
public class PlatformServer implements Serializable {

	private static final long serialVersionUID = -472730545567361841L;
	private String id;
	private String standardNumber;
	private String name;
	private String location;
	private String lanIp;
	private String wanIp;
	private String port;
	private String configValue;
	private Long createTime;
	private String telnetPort;
	private Short forward;

	public Short getForward() {
		return forward;
	}

	public void setForward(Short forward) {
		this.forward = forward;
	}

	// private PlatformServerProperty property;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLanIp() {
		return lanIp;
	}

	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}

	public String getWanIp() {
		return wanIp;
	}

	public void setWanIp(String wanIp) {
		this.wanIp = wanIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}

	// public PlatformServerProperty getProperty() {
	// return property;
	// }
	//
	// public void setProperty(PlatformServerProperty property) {
	// this.property = property;
	// }

}
