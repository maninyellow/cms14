package com.znsx.cms.service.model;

import java.util.List;

/**
 * PTS管辖的视频服务器业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:59:53
 */
public class PtsDvrVO {
	private String standardNumber;
	private String transport;
	private String maxConnect;
	private String lanIp;
	private String port;
	private String userName;
	private String password;
	private String heartCycle;
	private String protocol;
	private String expand;
	private List<PtsCameraVO> cameras;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getMaxConnect() {
		return maxConnect;
	}

	public void setMaxConnect(String maxConnect) {
		this.maxConnect = maxConnect;
	}

	public String getLanIp() {
		return lanIp;
	}

	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeartCycle() {
		return heartCycle;
	}

	public void setHeartCycle(String heartCycle) {
		this.heartCycle = heartCycle;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public List<PtsCameraVO> getCameras() {
		return cameras;
	}

	public void setCameras(List<PtsCameraVO> cameras) {
		this.cameras = cameras;
	}

}
