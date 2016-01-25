package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 桩号坐标映射对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-24 下午5:39:56
 */
public class StakeNumberLib implements Serializable {
	private static final long serialVersionUID = 3028619326758477572L;
	private String id;
	private String stakeNumber;
	private String organId;
	private String longitude;
	private String latitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
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
