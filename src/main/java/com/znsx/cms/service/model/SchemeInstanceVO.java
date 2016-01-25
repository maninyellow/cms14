package com.znsx.cms.service.model;

import java.util.LinkedList;
import java.util.List;

/**
 * SchemeInstanceVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-5 下午8:13:25
 */
public class SchemeInstanceVO {
	private String id;
	private String schemeTempletId;
	private String name;
	private String eventId;
	private String userId;
	private String userName;
	private List<Step> steps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchemeTempletId() {
		return schemeTempletId;
	}

	public void setSchemeTempletId(String schemeTempletId) {
		this.schemeTempletId = schemeTempletId;
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

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public class Step {
		private String seq;
		private String type;
		private List<Item> items = new LinkedList<SchemeInstanceVO.Item>();

		public String getSeq() {
			return seq;
		}

		public void setSeq(String seq) {
			this.seq = seq;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		// // 按照步骤分类
		// @Override
		// public boolean equals(Object obj) {
		// if (obj instanceof Step) {
		// return this.seq.equals(((Step) obj).seq);
		// } else {
		// return false;
		// }
		// }

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Step) {
				return this.type.equals(((Step) obj).type);
			} else {
				return false;
			}
		}
	}

	public class Item {
		private String id;
		private String targetId;
		private String targetName;
		private String targetType;
		private String telephone;
		private String linkMan;
		private String requestContent;
		private String responseContent;
		private String beginTime;
		private String arriveTime;
		private String endTime;
		private String actionStatus;
		private String note;
		private String content;
		private String color;
		private String font;
		private String size;
		private String space;
		private String duration;
		private String status;
		private String number;
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

		public String getTargetType() {
			return targetType;
		}

		public void setTargetType(String targetType) {
			this.targetType = targetType;
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

		public String getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public String getArriveTime() {
			return arriveTime;
		}

		public void setArriveTime(String arriveTime) {
			this.arriveTime = arriveTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getActionStatus() {
			return actionStatus;
		}

		public void setActionStatus(String actionStatus) {
			this.actionStatus = actionStatus;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
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

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		public String getSpace() {
			return space;
		}

		public void setSpace(String space) {
			this.space = space;
		}

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
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
}
