package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 协助单位-消防
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午1:56:33
 */
public class Fire extends EmUnit implements Serializable {
	private static final long serialVersionUID = 2773619848814189187L;
	private Short rescueCapability;
	private Short fireEngineNumber;

	public Short getRescueCapability() {
		return rescueCapability;
	}

	public void setRescueCapability(Short rescueCapability) {
		this.rescueCapability = rescueCapability;
	}

	public Short getFireEngineNumber() {
		return fireEngineNumber;
	}

	public void setFireEngineNumber(Short fireEngineNumber) {
		this.fireEngineNumber = fireEngineNumber;
	}

}
