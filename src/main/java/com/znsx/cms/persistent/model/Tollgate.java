package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * Tollgate
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:41:04
 */
public class Tollgate extends Organ implements Serializable {

	private static final long serialVersionUID = 8714776966554578714L;
	private Integer entranceNumber;
	private Integer exitNumber;
	private String navigation;

	public Integer getEntranceNumber() {
		return entranceNumber;
	}

	public void setEntranceNumber(Integer entranceNumber) {
		this.entranceNumber = entranceNumber;
	}

	public Integer getExitNumber() {
		return exitNumber;
	}

	public void setExitNumber(Integer exitNumber) {
		this.exitNumber = exitNumber;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

}
