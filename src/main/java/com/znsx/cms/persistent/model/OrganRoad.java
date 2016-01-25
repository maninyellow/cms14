package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * OrganRoad
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:07:37
 */
public class OrganRoad extends Organ implements Serializable {

	private static final long serialVersionUID = 8636386114039270612L;
	private Integer limitSpeed;
	private Integer capacity;
	private Integer laneNumber;
	private String beginStakeNumber;
	private String endStakeNumber;
	private Integer laneWidth;
	private Integer leftEdge;
	private Integer rightEdge;
	private Integer centralReserveWidth;
	private String roadNumber;

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

	public Integer getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Integer laneNumber) {
		this.laneNumber = laneNumber;
	}

	public String getBeginStakeNumber() {
		return beginStakeNumber;
	}

	public void setBeginStakeNumber(String beginStakeNumber) {
		this.beginStakeNumber = beginStakeNumber;
	}

	public String getEndStakeNumber() {
		return endStakeNumber;
	}

	public void setEndStakeNumber(String endStakeNumber) {
		this.endStakeNumber = endStakeNumber;
	}

	public Integer getLaneWidth() {
		return laneWidth;
	}

	public void setLaneWidth(Integer laneWidth) {
		this.laneWidth = laneWidth;
	}

	public Integer getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(Integer leftEdge) {
		this.leftEdge = leftEdge;
	}

	public Integer getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(Integer rightEdge) {
		this.rightEdge = rightEdge;
	}

	public Integer getCentralReserveWidth() {
		return centralReserveWidth;
	}

	public void setCentralReserveWidth(Integer centralReserveWidth) {
		this.centralReserveWidth = centralReserveWidth;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

}
