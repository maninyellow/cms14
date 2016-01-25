package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 中心存储服务器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:40:23
 */
public class Crs extends PlatformServer implements Serializable {
	private static final long serialVersionUID = -7480369735312027039L;
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

}
