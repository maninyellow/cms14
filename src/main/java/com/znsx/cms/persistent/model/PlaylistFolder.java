package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 存放情报板播放方案的文件夹
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-31 下午3:55:45
 */
public class PlaylistFolder implements Serializable {
	private static final long serialVersionUID = 9173575386768562227L;
	private String id;
	private String name;
	private Integer subType;
	private Set<Playlist> playlists = new HashSet<Playlist>();

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

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Set<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
	}

}
