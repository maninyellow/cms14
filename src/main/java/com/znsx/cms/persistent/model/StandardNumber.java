package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 标准号实体对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-23 下午3:28:17
 */
public class StandardNumber implements Serializable {
	private static final long serialVersionUID = 6972422079601798512L;
	private String id;
	private String sn;
	private String classType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

}
