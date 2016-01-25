package com.znsx.cms.service.model;

/**
 * sevice层返回的视频输出实体对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午7:03:46
 */
public class MonitorVO {
	private String id;
	private String dwsId;
	private String dwsName;
	private String channelNumber;
	private String standardNumber;
	private String name;
	private String wallId;
	private String wallName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDwsId() {
		return dwsId;
	}

	public void setDwsId(String dwsId) {
		this.dwsId = dwsId;
	}

	public String getDwsName() {
		return dwsName;
	}

	public void setDwsName(String dwsName) {
		this.dwsName = dwsName;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWallId() {
		return wallId;
	}

	public void setWallId(String wallId) {
		this.wallId = wallId;
	}

	public String getWallName() {
		return wallName;
	}

	public void setWallName(String wallName) {
		this.wallName = wallName;
	}
}
