package com.znsx.cms.service.model;

/**
 * WFS请求获取到的路政信息
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-14 下午2:08:21
 */
public class WfsRoadAdminVO {
	private String id;
	private String name;
	private String longitude;
	private String latitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
