package com.znsx.cms.service.model;

/**
 * UnitFireVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:47:06
 */
public class UnitFireVO extends UnitVO {
	private String rescueCapability;
	private String fireEngineNumber;

	public String getRescueCapability() {
		return rescueCapability;
	}

	public void setRescueCapability(String rescueCapability) {
		this.rescueCapability = rescueCapability;
	}

	public String getFireEngineNumber() {
		return fireEngineNumber;
	}

	public void setFireEngineNumber(String fireEngineNumber) {
		this.fireEngineNumber = fireEngineNumber;
	}
}
