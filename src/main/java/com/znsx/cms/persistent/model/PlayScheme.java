package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 播放方案实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:35:06
 */
public class PlayScheme implements Serializable {

	private static final long serialVersionUID = 6618488509442018593L;

	private String id;
	private String userId;
	private String organId;
	private String name;
	private String schemeConfig;
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

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchemeConfig() {
		return schemeConfig;
	}

	public void setSchemeConfig(String schemeConfig) {
		this.schemeConfig = schemeConfig;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
