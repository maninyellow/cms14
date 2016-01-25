package com.znsx.cms.service.model;

/**
 * GisLogonVO
 * @author wangbinyu <p />
 * Create at 2014 下午4:27:38
 */
public class GisLogonVO {
	private String ip;
	private String port;
	private String wmsUrl;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getWmsUrl() {
		return wmsUrl;
	}

	public void setWmsUrl(String wmsUrl) {
		this.wmsUrl = wmsUrl;
	}
}
