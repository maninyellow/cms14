package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 显示墙服务器实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:05:27
 */
public class Dws extends PlatformServer implements Serializable {
	private static final long serialVersionUID = -482251706645138418L;
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}
}
