package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 用户登录接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:43:08
 */
public class UserLoginDTO extends BaseDTO {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public class User {
		private String sessionId;
		private String id;
		private String standardNumber;
		private String ccsId;
		private String name;
		private String sex;
		private String email;
		private String phone;
		private String address;
		private String organId;
		private String priority;
		private String note;

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getStandardNumber() {
			return standardNumber;
		}

		public void setStandardNumber(String standardNumber) {
			this.standardNumber = standardNumber;
		}

		public String getCcsId() {
			return ccsId;
		}

		public void setCcsId(String ccsId) {
			this.ccsId = ccsId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getOrganId() {
			return organId;
		}

		public void setOrganId(String organId) {
			this.organId = organId;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}
	}

}
