package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 策略与设备的关联实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:26:36
 */
public class PolicyDevice implements Serializable {
	private static final long serialVersionUID = -7497529496225157979L;
	private String id;
	private String policyId;
	private String deviceId;
	private Short type;
	private Short status;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
