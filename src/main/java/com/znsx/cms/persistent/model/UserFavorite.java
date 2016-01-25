package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 用户收藏夹实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:02:25
 */
public class UserFavorite implements Serializable {
	private static final long serialVersionUID = 4845225562824930780L;
	private String id;
	private String userId;
	private String name;
	private Long createTime;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
