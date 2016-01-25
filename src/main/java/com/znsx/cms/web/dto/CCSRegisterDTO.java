package com.znsx.cms.web.dto;

/**
 * CCS注册接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午8:07:36
 */
public class CCSRegisterDTO extends BaseDTO {
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
