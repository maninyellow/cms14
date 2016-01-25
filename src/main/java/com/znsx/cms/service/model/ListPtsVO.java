package com.znsx.cms.service.model;

/**
 * 列表查询协转服务器业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:25:19
 */
public class ListPtsVO {
	private String id;
	private String type;
	private String standardNumber;
	private String name;
	private String ccsId;
	private String ccsName;
	private String location;
	private String lanIp;
	private String wanIp;
	private String configValue;
	private String createTime;
	private String port;
	private String telnetPort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}

}
