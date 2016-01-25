package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * OrganTunnel
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:06:16
 */
public class OrganTunnel extends Organ implements Serializable {
	private static final long serialVersionUID = 7309983267342723623L;
	private String height;
	private String length;
	private Integer laneNumber;
	private Integer limitSpeed;
	private Integer capacity;
	private String leftDirection;
	private String rightDirection;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Integer getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Integer laneNumber) {
		this.laneNumber = laneNumber;
	}

	public Integer getLimitSpeed() {
		return limitSpeed;
	}

	public void setLimitSpeed(Integer limitSpeed) {
		this.limitSpeed = limitSpeed;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getLeftDirection() {
		return leftDirection;
	}

	public void setLeftDirection(String leftDirection) {
		this.leftDirection = leftDirection;
	}

	public String getRightDirection() {
		return rightDirection;
	}

	public void setRightDirection(String rightDirection) {
		this.rightDirection = rightDirection;
	}
}
