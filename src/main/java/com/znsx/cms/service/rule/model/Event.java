package com.znsx.cms.service.rule.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 事件对象,规则输入参数
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-26 上午9:50:17
 */
public class Event {
	private String id;
	private int type;
	private int eventLevel;
	private String beginStake;
	private String endStake;
	private int laneNumber;
	private int roadType;
	private int navigation;
	private String impactProvince;
	private long recoverTime;
	private String description;
	private int hurtNumber;
	private int deathNumber;
	private int delayHumanNumber;
	private int delayCarNumber;
	private int damageCarNumber;
	private int crowdMeter;
	private int lossAmount;
	private int visibility;
	private int qc;// 通行能力(-1:交通量>通行能力，0:交通量=通行能力,1:交通量<通行能力)
	private boolean isFire;
	private boolean oppositeSupport;// 允许借用对侧车道，通过对侧车道通行能力与实际交通量计算得到
	private List<String> reasons = new ArrayList<String>();
	private List<String> vmsActions = new LinkedList<String>();
	private List<String> fireActions = new LinkedList<String>();
	private List<String> hospitalActions = new LinkedList<String>();
	private List<String> policeActions = new LinkedList<String>();
	private Set<String> resourceActions = new LinkedHashSet<String>();
	private List<String> roadActions = new LinkedList<String>();
	private List<String> roadAdminActions = new LinkedList<String>();
	private List<String> smsActions = new LinkedList<String>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(int eventLevel) {
		this.eventLevel = eventLevel;
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

	public int getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(int laneNumber) {
		this.laneNumber = laneNumber;
	}

	public int getRoadType() {
		return roadType;
	}

	public void setRoadType(int roadType) {
		this.roadType = roadType;
	}

	public int getNavigation() {
		return navigation;
	}

	public void setNavigation(int navigation) {
		this.navigation = navigation;
	}

	public String getImpactProvince() {
		return impactProvince;
	}

	public void setImpactProvince(String impactProvince) {
		this.impactProvince = impactProvince;
	}

	public long getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(long recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHurtNumber() {
		return hurtNumber;
	}

	public void setHurtNumber(int hurtNumber) {
		this.hurtNumber = hurtNumber;
	}

	public int getDeathNumber() {
		return deathNumber;
	}

	public void setDeathNumber(int deathNumber) {
		this.deathNumber = deathNumber;
	}

	public int getDelayHumanNumber() {
		return delayHumanNumber;
	}

	public void setDelayHumanNumber(int delayHumanNumber) {
		this.delayHumanNumber = delayHumanNumber;
	}

	public int getDelayCarNumber() {
		return delayCarNumber;
	}

	public void setDelayCarNumber(int delayCarNumber) {
		this.delayCarNumber = delayCarNumber;
	}

	public int getDamageCarNumber() {
		return damageCarNumber;
	}

	public void setDamageCarNumber(int damageCarNumber) {
		this.damageCarNumber = damageCarNumber;
	}

	public int getCrowdMeter() {
		return crowdMeter;
	}

	public void setCrowdMeter(int crowdMeter) {
		this.crowdMeter = crowdMeter;
	}

	public int getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(int lossAmount) {
		this.lossAmount = lossAmount;
	}

	public boolean isFire() {
		return isFire;
	}

	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}

	public List<String> getVmsActions() {
		return vmsActions;
	}

	public void setVmsActions(List<String> vmsActions) {
		this.vmsActions = vmsActions;
	}

	public List<String> getFireActions() {
		return fireActions;
	}

	public void setFireActions(List<String> fireActions) {
		this.fireActions = fireActions;
	}

	public List<String> getHospitalActions() {
		return hospitalActions;
	}

	public void setHospitalActions(List<String> hospitalActions) {
		this.hospitalActions = hospitalActions;
	}

	public List<String> getPoliceActions() {
		return policeActions;
	}

	public void setPoliceActions(List<String> policeActions) {
		this.policeActions = policeActions;
	}

	public List<String> getReasons() {
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}

	public Set<String> getResourceActions() {
		return resourceActions;
	}

	public void setResourceActions(Set<String> resourceActions) {
		this.resourceActions = resourceActions;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getQc() {
		return qc;
	}

	public void setQc(int qc) {
		this.qc = qc;
	}

	public boolean isOppositeSupport() {
		return oppositeSupport;
	}

	public void setOppositeSupport(boolean oppositeSupport) {
		this.oppositeSupport = oppositeSupport;
	}

	public List<String> getRoadActions() {
		return roadActions;
	}

	public void setRoadActions(List<String> roadActions) {
		this.roadActions = roadActions;
	}

	public List<String> getRoadAdminActions() {
		return roadAdminActions;
	}

	public void setRoadAdminActions(List<String> roadAdminActions) {
		this.roadAdminActions = roadAdminActions;
	}

	public List<String> getSmsActions() {
		return smsActions;
	}

	public void setSmsActions(List<String> smsActions) {
		this.smsActions = smsActions;
	}

}
