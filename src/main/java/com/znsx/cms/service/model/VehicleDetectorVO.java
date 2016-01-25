package com.znsx.cms.service.model;

/**
 * 车辆检测器数据统计
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:19:31
 */
public class VehicleDetectorVO {
	private String name;
	private String upFlux;
	private String dwFlux;
	private String upSpeed;
	private String dwSpeed;
	private String totalFlux;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpFlux() {
		return upFlux;
	}

	public void setUpFlux(String upFlux) {
		this.upFlux = upFlux;
	}

	public String getDwFlux() {
		return dwFlux;
	}

	public void setDwFlux(String dwFlux) {
		this.dwFlux = dwFlux;
	}

	public String getUpSpeed() {
		return upSpeed;
	}

	public void setUpSpeed(String upSpeed) {
		this.upSpeed = upSpeed;
	}

	public String getDwSpeed() {
		return dwSpeed;
	}

	public void setDwSpeed(String dwSpeed) {
		this.dwSpeed = dwSpeed;
	}

	public String getTotalFlux() {
		return totalFlux;
	}

	public void setTotalFlux(String totalFlux) {
		this.totalFlux = totalFlux;
	}

}
