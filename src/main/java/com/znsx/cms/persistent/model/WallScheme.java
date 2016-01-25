package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 电视墙控制方案
 * 
 * @author huangbuji
 *         <p />
 *         2014-11-18 下午2:52:12
 */
public class WallScheme implements Serializable {
	private static final long serialVersionUID = -5866548759179354808L;
	private String id;
	private String name;
	private DisplayWall wall;
	private User user;
	private Organ organ;
	private Set<WallSchemeItem> items = new HashSet<WallSchemeItem>();

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

	public DisplayWall getWall() {
		return wall;
	}

	public void setWall(DisplayWall wall) {
		this.wall = wall;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Set<WallSchemeItem> getItems() {
		return items;
	}

	public void setItems(Set<WallSchemeItem> items) {
		this.items = items;
	}

}
