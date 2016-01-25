package com.znsx.cms.service.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 收藏夹业务对象
 * @author wangbinyu <p />
 * Create at 2013 上午9:31:15
 */
public class FavoriteVO {
	private String id;
	private String name;
	private List<CameraVO> channel = new ArrayList<CameraVO>();
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
	public List<CameraVO> getChannel() {
		return channel;
	}
	public void setChannel(List<CameraVO> channel) {
		this.channel = channel;
	}
	
}
