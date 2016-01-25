package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 图片实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午7:07:14
 */
public class Image implements Serializable {
	private static final long serialVersionUID = 4137303836618856673L;
	private String id;
	private String name;
	private Long size;
	private String format;
	private Blob content;
	private String userId;
	private Long createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
