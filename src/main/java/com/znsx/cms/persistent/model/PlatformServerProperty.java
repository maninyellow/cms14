package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 平台服务器属性实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:24:25
 */
public class PlatformServerProperty implements Serializable {
	private static final long serialVersionUID = -5690341232960810332L;
	private String id;
//	private PlatformServer server;
	private Integer memory;
	private Integer hardpan;
	private String cpu;
	private String networkCard;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// public PlatformServer getServer() {
	// return server;
	// }
	//
	// public void setServer(PlatformServer server) {
	// this.server = server;
	// }

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getHardpan() {
		return hardpan;
	}

	public void setHardpan(Integer hardpan) {
		this.hardpan = hardpan;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getNetworkCard() {
		return networkCard;
	}

	public void setNetworkCard(String networkCard) {
		this.networkCard = networkCard;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
