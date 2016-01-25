package com.znsx.cms.service.model;

/**
 * PTS管辖的摄像头业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:48:17
 */
public class PtsCameraVO {
	private String standardNumber;
	private String channelNumber;
	private String storeType;
	private String cRSStandardNumber;
	private String mSSStandardNumber;
	private String rMSStandardNumber;
	private String streamType;
	private String expand;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getCRSStandardNumber() {
		return cRSStandardNumber;
	}

	public void setCRSStandardNumber(String cRSStandardNumber) {
		this.cRSStandardNumber = cRSStandardNumber;
	}

	public String getMSSStandardNumber() {
		return mSSStandardNumber;
	}

	public void setMSSStandardNumber(String mSSStandardNumber) {
		this.mSSStandardNumber = mSSStandardNumber;
	}

	public String getStreamType() {
		return streamType;
	}

	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public void setRMSStandardNumber(String rMSStandardNumber) {
		this.rMSStandardNumber = rMSStandardNumber;
	}

	public String getRMSStandardNumber() {
		return rMSStandardNumber;
	}
}
