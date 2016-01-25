package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * DeviceFavorite
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-12 上午10:29:19
 */
public class DeviceFavorite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375671355672024500L;
	private String id;
	private String favoriteId;
	private String deviceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
