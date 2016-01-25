package com.znsx.cms.service.model;

import java.io.Serializable;

/**
 * CCS管辖的用户会话对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午8:45:05
 */
public class CcsUserSessionVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8108694997578204353L;
	private String id;
	private String standardNumber;
	private String priority;

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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
