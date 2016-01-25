package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 录像转发服务器
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:03:43
 */
public class Rms extends PlatformServer implements Serializable {

	private static final long serialVersionUID = -5028439865303736323L;
	
	private Ccs ccs;

	public Ccs getCcs() {
		return ccs;
	}

	public void setCcs(Ccs ccs) {
		this.ccs = ccs;
	}

}
