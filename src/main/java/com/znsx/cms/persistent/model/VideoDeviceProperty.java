package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 视频服务器属性实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:17:44
 */
public class VideoDeviceProperty implements Serializable {
	private static final long serialVersionUID = 3050400350113891889L;
	private String id;
	private Device device;
	private String userName;
	private String password;
	private Integer heartCycle;
	private Short storeType;
	private String localStorePlan;
	private String centerStorePlan;
	private String protocol;
	private String streamType;
	private String expand;
	private String imageId;
	private String decode;
	private String storeStream;
	/**
	 * 设备归属
	 */
	private String owner;
	/**
	 * 行政区域
	 */
	private String civilCode;
	/**
	 * 警区
	 */
	private Double block;
	/**
	 * 证书序列号
	 */
	private String certNum;
	/**
	 * 证书有效标识（有证书必选） 缺省为0：无效 1有效 //默认为1
	 */
	private Integer certifiable;
	/**
	 * 无效原因码
	 */
	private Integer errCode;
	/**
	 * 有效日期
	 */
	private Long endTime;
	/**
	 * 保密属性 缺省0:不涉密 1：涉密
	 */
	private Integer secrecy;

	public String getDecode() {
		return decode;
	}

	public void setDecode(String decode) {
		this.decode = decode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getHeartCycle() {
		return heartCycle;
	}

	public void setHeartCycle(Integer heartCycle) {
		this.heartCycle = heartCycle;
	}

	public Short getStoreType() {
		return storeType;
	}

	public void setStoreType(Short storeType) {
		this.storeType = storeType;
	}

	public String getLocalStorePlan() {
		return localStorePlan;
	}

	public void setLocalStorePlan(String localStorePlan) {
		this.localStorePlan = localStorePlan;
	}

	public String getCenterStorePlan() {
		return centerStorePlan;
	}

	public void setCenterStorePlan(String centerStorePlan) {
		this.centerStorePlan = centerStorePlan;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCivilCode() {
		return civilCode;
	}

	public void setCivilCode(String civilCode) {
		this.civilCode = civilCode;
	}

	public Double getBlock() {
		return block;
	}

	public void setBlock(Double block) {
		this.block = block;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public Integer getCertifiable() {
		return certifiable;
	}

	public void setCertifiable(Integer certifiable) {
		this.certifiable = certifiable;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(Integer secrecy) {
		this.secrecy = secrecy;
	}

	public String getStoreStream() {
		return storeStream;
	}

	public void setStoreStream(String storeStream) {
		this.storeStream = storeStream;
	}

}
