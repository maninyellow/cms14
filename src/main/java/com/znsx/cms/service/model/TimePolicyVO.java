package com.znsx.cms.service.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 定时策略业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-28 上午10:13:07
 */
public class TimePolicyVO {
	private String id;
	private String name;
	private List<TimePolicyItemVO> items = new LinkedList<TimePolicyItemVO>();

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

	public List<TimePolicyItemVO> getItems() {
		return items;
	}

	public void setItems(List<TimePolicyItemVO> items) {
		this.items = items;
	}

}
