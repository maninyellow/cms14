/**
 * 
 */
package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 电视墙控制方案各输出通道内容
 * 
 * @author huangbuji
 *         <p />
 *         2014-11-18 下午2:55:13
 */
public class WallSchemeItem implements Serializable {
	private static final long serialVersionUID = -1900685529444257535L;
	private String id;
	private WallScheme wallScheme;
	private Monitor monitor;
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WallScheme getWallScheme() {
		return wallScheme;
	}

	public void setWallScheme(WallScheme wallScheme) {
		this.wallScheme = wallScheme;
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
