package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户角色实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:10:14
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 5416101884662548761L;
	private String id;
	private String type;
	private Organ organ;
	private String name;
	private Long createTime;
	private String note;
	private Set<MenuOperation> MenuOperations = new HashSet<MenuOperation>();
	private Set<User> users = new HashSet<User>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
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

	public Set<MenuOperation> getMenuOperations() {
		return MenuOperations;
	}

	public void setMenuOperations(Set<MenuOperation> menuOperations) {
		MenuOperations = menuOperations;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
