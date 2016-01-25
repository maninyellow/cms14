package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * 视频服务器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:24:48
 */
public class Dvr extends Device implements Serializable {

	private static final long serialVersionUID = -7018634326517367147L;
	private Ccs ccs;
	private Pts pts;
	private String transport;
	private String mode;
	private Integer maxConnect;
	private String linkType;
	private String lanIp;
	private String port;
	private Integer aicAmount;
	private Integer aocAmount;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

	public Pts getPts() {
		return pts;
	}

	public void setPts(Pts pts) {
		this.pts = pts;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getMaxConnect() {
		return maxConnect;
	}

	public void setMaxConnect(Integer maxConnect) {
		this.maxConnect = maxConnect;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
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

	public Integer getAicAmount() {
		return aicAmount;
	}

	public void setAicAmount(Integer aicAmount) {
		this.aicAmount = aicAmount;
	}

	public Integer getAocAmount() {
		return aocAmount;
	}

	public void setAocAmount(Integer aocAmount) {
		this.aocAmount = aocAmount;
	}

	@Override
	public Integer getType() {
		return TypeDefinition.DEVICE_TYPE_DVR;
	}
}
