package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 协转服务器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:42:02
 */
public class Pts extends PlatformServer implements Serializable {
	private static final long serialVersionUID = -8857275325471964300L;
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

}
