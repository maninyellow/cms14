package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 预案实例对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午1:39:33
 */
public class SchemeInstance implements Serializable {
	private static final long serialVersionUID = -2667609179427134663L;
	private String id;
	private String templetId;
	private String name;
	private String eventId;
	private Long createTime;
	private String createUserId;
	private String createUserName;
	private Short actionStatus;
	private Long updateTime;
	private Set<SchemeStepInstance> steps = new HashSet<SchemeStepInstance>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Short getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(Short actionStatus) {
		this.actionStatus = actionStatus;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Set<SchemeStepInstance> getSteps() {
		return steps;
	}

	public void setSteps(Set<SchemeStepInstance> steps) {
		this.steps = steps;
	}

}
