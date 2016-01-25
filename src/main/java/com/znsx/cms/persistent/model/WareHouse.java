package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 物资仓库
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:54:38
 */
public class WareHouse extends EmUnit implements Serializable {

	private static final long serialVersionUID = -6351419850566895970L;

	private String managerUnit;

	public String getManagerUnit() {
		return managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
	}

}
