package com.znsx.cms.persistent.model;

import java.io.Serializable;

import com.znsx.cms.service.common.TypeDefinition;

/**
 * 摄像头实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:53:53
 */
public class Camera extends Device implements Serializable {
	private static final long serialVersionUID = -5067755724254406109L;
	private Dvr parent;
	private Mss mss;
	private Crs crs;
	private Rms rms;
	private Short channelNumber;

	public Dvr getParent() {
		return parent;
	}

	public void setParent(Dvr parent) {
		this.parent = parent;
	}

	public Mss getMss() {
		return mss;
	}

	public void setMss(Mss mss) {
		this.mss = mss;
	}

	public Crs getCrs() {
		return crs;
	}

	public void setCrs(Crs crs) {
		this.crs = crs;
	}

	public Rms getRms() {
		return rms;
	}

	public void setRms(Rms rms) {
		this.rms = rms;
	}

	public Short getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(Short channelNumber) {
		this.channelNumber = channelNumber;
	}

	@Override
	public Integer getType() {
		return TypeDefinition.DEVICE_TYPE_CAMERA;
	}

}
