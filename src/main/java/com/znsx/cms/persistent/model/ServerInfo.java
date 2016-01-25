package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 服务器硬件信息
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-2-13 下午3:27:11
 */
public class ServerInfo implements Serializable {
	private static final long serialVersionUID = -5531143769018674176L;
	private String id;
	private String motherBoardSN;
	private String cpuId;
	private String mac;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotherBoardSN() {
		return motherBoardSN;
	}

	public void setMotherBoardSN(String motherBoardSN) {
		this.motherBoardSN = motherBoardSN;
	}

	public String getCpuId() {
		return cpuId;
	}

	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
