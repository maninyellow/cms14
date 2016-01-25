package com.znsx.cms.service.model;

/**
 * service层查询路由资源信息返回实体
 * @author wangbinyu <p />
 * Create at 2013 下午4:14:50
 */
public class CcsVO {
	private String lanIp;
	private String port;

	public String getLanIp() {
		return lanIp;
	}

	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
