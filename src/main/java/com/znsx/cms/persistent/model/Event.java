package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 事件对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午2:29:43
 */
public class Event implements Serializable {
	private static final long serialVersionUID = 8173355809995829519L;
	private String id;
	private Short type;
	private String subType;
	private Short eventLevel;
	private String name;
	private Organ organ;
	private Short roadType;
	private String location;
	private String beginStake;
	private String endStake;
	private Short navigation;
	private String sendUser;
	private String phone;
	private String impactProvince;
	private Long createTime;
	private Long estimatesRecoverTime;
	private Long recoverTime;
	private String description;
	private Short hurtNumber;
	private Short deathNumber;
	private Integer delayHumanNumber;
	private Integer delayCarNumber;
	private Short damageCarNumber;
	private Integer crowdMeter;
	private Integer lossAmount;
	private Short isFire;
	private Short laneNumber;
	private String note;
	private String administration;
	private String managerUnit;
	private String roadName;
	private String recoverStatus;
	private Short status;
	private String longitude;
	private String latitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Short getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Short eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Short getRoadType() {
		return roadType;
	}

	public void setRoadType(Short roadType) {
		this.roadType = roadType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Short getNavigation() {
		return navigation;
	}

	public void setNavigation(Short navigation) {
		this.navigation = navigation;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImpactProvince() {
		return impactProvince;
	}

	public void setImpactProvince(String impactProvince) {
		this.impactProvince = impactProvince;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getEstimatesRecoverTime() {
		return estimatesRecoverTime;
	}

	public void setEstimatesRecoverTime(Long estimatesRecoverTime) {
		this.estimatesRecoverTime = estimatesRecoverTime;
	}

	public Long getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Long recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getHurtNumber() {
		return hurtNumber;
	}

	public void setHurtNumber(Short hurtNumber) {
		this.hurtNumber = hurtNumber;
	}

	public Short getDeathNumber() {
		return deathNumber;
	}

	public void setDeathNumber(Short deathNumber) {
		this.deathNumber = deathNumber;
	}

	public Integer getDelayHumanNumber() {
		return delayHumanNumber;
	}

	public void setDelayHumanNumber(Integer delayHumanNumber) {
		this.delayHumanNumber = delayHumanNumber;
	}

	public Integer getDelayCarNumber() {
		return delayCarNumber;
	}

	public void setDelayCarNumber(Integer delayCarNumber) {
		this.delayCarNumber = delayCarNumber;
	}

	public Short getDamageCarNumber() {
		return damageCarNumber;
	}

	public void setDamageCarNumber(Short damageCarNumber) {
		this.damageCarNumber = damageCarNumber;
	}

	public Integer getCrowdMeter() {
		return crowdMeter;
	}

	public void setCrowdMeter(Integer crowdMeter) {
		this.crowdMeter = crowdMeter;
	}

	public Integer getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Integer lossAmount) {
		this.lossAmount = lossAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public String getManagerUnit() {
		return managerUnit;
	}

	public void setManagerUnit(String managerUnit) {
		this.managerUnit = managerUnit;
	}

	public Short getIsFire() {
		return isFire;
	}

	public void setIsFire(Short isFire) {
		this.isFire = isFire;
	}

	public Short getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(Short laneNumber) {
		this.laneNumber = laneNumber;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRecoverStatus() {
		return recoverStatus;
	}

	public void setRecoverStatus(String recoverStatus) {
		this.recoverStatus = recoverStatus;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
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
