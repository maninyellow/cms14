package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 值班班次实体类
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午11:04:43
 */
public class Classes implements Serializable {

	private static final long serialVersionUID = 2817336610185470048L;
	
	private String id;
	private String userName;
	private String maintainer;
	private String captain;
	private Long beginTime;
	private Long endTime;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public String getCaptain() {
		return captain;
	}
	public void setCaptain(String captain) {
		this.captain = captain;
	}
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
