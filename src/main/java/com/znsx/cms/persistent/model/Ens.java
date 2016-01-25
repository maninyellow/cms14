package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 事件服务器实体类
 * @author wangbinyu <p />
 * Create at 2013 下午1:54:49
 */
public class Ens extends PlatformServer implements Serializable {

	private static final long serialVersionUID = -5613452407821169509L;
	
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

}
