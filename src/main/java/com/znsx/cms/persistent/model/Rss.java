package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * Rss
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-27 上午9:56:03
 */
public class Rss extends PlatformServer implements Serializable {

	private static final long serialVersionUID = -7813848201581049053L;
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}
}
