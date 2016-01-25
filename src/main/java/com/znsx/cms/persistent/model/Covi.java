package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 一氧化碳/能见度检测器实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:04:43
 */
public class Covi implements Serializable {

	private static final long serialVersionUID = 7536217997737497988L;

	private Long createTime;
	private String id;
	private Integer period;
	private Short coConctLimit;
	private Short visibilityLimit;
	private String stakeNumber;
	private Das das;
	private Organ organ;
	private String longitude;
	private String latitude;
	private String reserve;
	private String name;
	private String note;
	private String standardNumber;
	private String navigation;
	private String ip;
	private Integer port;

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Short getCoConctLimit() {
		return coConctLimit;
	}

	public void setCoConctLimit(Short coConctLimit) {
		this.coConctLimit = coConctLimit;
	}

	public Short getVisibilityLimit() {
		return visibilityLimit;
	}

	public void setVisibilityLimit(Short visibilityLimit) {
		this.visibilityLimit = visibilityLimit;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public Das getDas() {
		return das;
	}

	public void setDas(Das das) {
		this.das = das;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
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

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
