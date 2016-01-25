package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 预案字典
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午2:51:50
 */
public class SchemeDictionary implements Serializable {
	private static final long serialVersionUID = 4898459443606587644L;
	private String id;
	private String targetClass;
	private Short targetType;
	private String description;
	private Set<TempletDictionary> templetDictionarys = new HashSet<TempletDictionary>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<TempletDictionary> getTempletDictionarys() {
		return templetDictionarys;
	}

	public void setTempletDictionarys(Set<TempletDictionary> templetDictionarys) {
		this.templetDictionarys = templetDictionarys;
	}

	public Short getTargetType() {
		return targetType;
	}

	public void setTargetType(Short targetType) {
		this.targetType = targetType;
	}

}
