package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 预案步骤实例
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午1:54:27
 */
public class SchemeStepInstance implements Serializable {
	private static final long serialVersionUID = -6384270725594541984L;
	private String id;
	private SchemeInstance schemeInstance;
	private String targetId;
	private String targetName;
	private Short targetType;
	private Integer targetNumber;
	private String telephone;
	private String linkMan;
	private Short seq;
	private String requestContent;
	private String responseContent;
	private Long beginTime;
	private Long arriveTime;
	private Long endTime;
	private Short actionStatus;
	private Short state;
	private String content;
	private String color;
	private String font;
	private String playSize;
	private Short space;
	private Integer duration;
	private String note;
	private Short x;
	private Short y;
	private String beginStake;
	private String endStake;
	private String navigation;
	private String managerUnit;
	private String location;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SchemeInstance getSchemeInstance() {
		return schemeInstance;
	}

	public void setSchemeInstance(SchemeInstance schemeInstance) {
		this.schemeInstance = schemeInstance;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Short getTargetType() {
		return targetType;
	}

	public void setTargetType(Short targetType) {
		this.targetType = targetType;
	}

	public Integer getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public Short getSeq() {
		return seq;
	}

	public void setSeq(Short seq) {
		this.seq = seq;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Long arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Short getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(Short actionStatus) {
		this.actionStatus = actionStatus;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getPlaySize() {
		return playSize;
	}

	public void setPlaySize(String playSize) {
		this.playSize = playSize;
	}

	public Short getSpace() {
		return space;
	}

	public void setSpace(Short space) {
		this.space = space;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Short getX() {
		return x;
	}

	public void setX(Short x) {
		this.x = x;
	}

	public Short getY() {
		return y;
	}

	public void setY(Short y) {
		this.y = y;
	}

	public String getBeginStake() {
		return beginStake;
	}

	public void setBeginStake(String beginStake) {
		this.beginStake = beginStake;
	}

	public String getEndStake() {
		return endStake;
	}

	public void setEndStake(String endStake) {
		this.endStake = endStake;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getManagerUnit() {
		return managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
