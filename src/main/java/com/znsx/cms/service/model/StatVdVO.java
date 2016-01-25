package com.znsx.cms.service.model;

/**
 * StatVdByHourVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-14 下午4:19:25
 */
public class StatVdVO {
	private String standardNumber;
	private Float upOcc;
	private Float dwOcc;
	private Float upSpeed;
	private Float dwSpeed;
	private String upFlow;
	private String dwFlow;
	private Float upHeadway;
	private Float dwHeadway;
	private Long dateTime;

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Float getUpOcc() {
		return upOcc;
	}

	public void setUpOcc(Float upOcc) {
		this.upOcc = upOcc;
	}

	public Float getDwOcc() {
		return dwOcc;
	}

	public void setDwOcc(Float dwOcc) {
		this.dwOcc = dwOcc;
	}

	public Float getUpSpeed() {
		return upSpeed;
	}

	public void setUpSpeed(Float upSpeed) {
		this.upSpeed = upSpeed;
	}

	public Float getDwSpeed() {
		return dwSpeed;
	}

	public void setDwSpeed(Float dwSpeed) {
		this.dwSpeed = dwSpeed;
	}

	public String getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(String upFlow) {
		this.upFlow = upFlow;
	}

	public String getDwFlow() {
		return dwFlow;
	}

	public void setDwFlow(String dwFlow) {
		this.dwFlow = dwFlow;
	}

	public Float getUpHeadway() {
		return upHeadway;
	}

	public void setUpHeadway(Float upHeadway) {
		this.upHeadway = upHeadway;
	}

	public Float getDwHeadway() {
		return dwHeadway;
	}

	public void setDwHeadway(Float dwHeadway) {
		this.dwHeadway = dwHeadway;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
