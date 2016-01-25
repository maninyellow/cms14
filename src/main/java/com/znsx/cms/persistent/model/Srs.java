package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 录音服务器实体sound recording server
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 下午3:38:48
 */
public class Srs extends PlatformServer implements Serializable {

	private static final long serialVersionUID = -3725027418787478410L;
	
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}
	
}
