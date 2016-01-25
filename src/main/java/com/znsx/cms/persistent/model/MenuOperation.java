package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 视图操作权限实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:40:03
 */
public class MenuOperation implements Serializable {
	private static final long serialVersionUID = 8985243850506672104L;
	private String id;
	private String name;
	private String menuCode;
	private String menuAction;
	private MenuOperation parent;
	private String note;

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

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public MenuOperation getParent() {
		return parent;
	}

	public void setParent(MenuOperation parent) {
		this.parent = parent;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
