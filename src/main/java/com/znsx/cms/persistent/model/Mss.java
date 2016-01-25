package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 流媒体服务器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:39:42
 */
public class Mss extends PlatformServer implements Serializable {
	private static final long serialVersionUID = 2422747853440562104L;
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

}
