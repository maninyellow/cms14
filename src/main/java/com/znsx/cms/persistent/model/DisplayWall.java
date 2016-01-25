package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 电视墙实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:44:14
 */
public class DisplayWall implements Serializable {

	private static final long serialVersionUID = 4510181897947070211L;
	private String id;
	private String name;
	private Organ organ;
	private String note;
	private Set<Monitor> monitors = new HashSet<Monitor>();

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

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(Set<Monitor> monitors) {
		this.monitors = monitors;
	}

}
