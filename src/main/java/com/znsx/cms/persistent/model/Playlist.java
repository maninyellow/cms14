package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 播放方案实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 上午10:03:05
 */
public class Playlist implements Serializable {
	private static final long serialVersionUID = -4525123680602293054L;
	private String id;
	private Organ organ;
	private String name;
	private PlaylistFolder folder;
	private Short type;
	private Set<PlayItem> items = new LinkedHashSet<PlayItem>();
	private String cmsSize;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Set<PlayItem> getItems() {
		return items;
	}

	public void setItems(Set<PlayItem> items) {
		this.items = items;
	}

	public PlaylistFolder getFolder() {
		return folder;
	}

	public void setFolder(PlaylistFolder folder) {
		this.folder = folder;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getCmsSize() {
		return cmsSize;
	}

	public void setCmsSize(String cmsSize) {
		this.cmsSize = cmsSize;
	}

}
