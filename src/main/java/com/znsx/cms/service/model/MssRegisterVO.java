package com.znsx.cms.service.model;

/**
 * 流媒体服务器注册业务返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午5:05:41
 */
public class MssRegisterVO {
	private String wanIp;
	private String config;

	public String getWanIp() {
		return wanIp;
	}

	public void setWanIp(String wanIp) {
		this.wanIp = wanIp;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

}
