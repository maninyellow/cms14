package com.znsx.cms.service.model;

/**
 * 用户视图权限对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午6:34:10
 */
public class UserViewVO {
	private String video = "0";
	private String gis = "0";
	private String alarm = "0";
	private String query = "0";
	private String display = "0";

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getGis() {
		return gis;
	}

	public void setGis(String gis) {
		this.gis = gis;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
