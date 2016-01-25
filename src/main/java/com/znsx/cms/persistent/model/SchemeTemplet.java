package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 预案库
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午2:55:33
 */
public class SchemeTemplet implements Serializable {
	private static final long serialVersionUID = -6897295962783265200L;
	private String id;
	private String name;
	private Short eventType;
	private Short eventLevel;
	private Organ organ;
	private Set<TempletDictionary> templetDictionarys = new HashSet<TempletDictionary>();

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

	public Short getEventType() {
		return eventType;
	}

	public void setEventType(Short eventType) {
		this.eventType = eventType;
	}

	public Short getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Short eventLevel) {
		this.eventLevel = eventLevel;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Set<TempletDictionary> getTempletDictionarys() {
		return templetDictionarys;
	}

	public void setTempletDictionarys(Set<TempletDictionary> templetDictionarys) {
		this.templetDictionarys = templetDictionarys;
	}

}
