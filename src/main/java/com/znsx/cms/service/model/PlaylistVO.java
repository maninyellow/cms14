package com.znsx.cms.service.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 情报板播放方案业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午1:40:08
 */
public class PlaylistVO {
	private String id;
	private String name;
	private List<PlayItemVO> items = new LinkedList<PlayItemVO>();

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
		if (null == name) {
			this.name = "";
		}
	}

	public List<PlayItemVO> getItems() {
		return items;
	}

	public void setItems(List<PlayItemVO> items) {
		this.items = items;
	}
}
