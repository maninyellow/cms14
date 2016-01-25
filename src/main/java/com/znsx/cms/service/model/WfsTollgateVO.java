package com.znsx.cms.service.model;

/**
 * WfsTollgateVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:04:59
 */
public class WfsTollgateVO {
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
