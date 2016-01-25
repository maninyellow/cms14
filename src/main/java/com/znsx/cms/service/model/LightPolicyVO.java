package com.znsx.cms.service.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 照明策略业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-27 上午11:13:33
 */
public class LightPolicyVO {
	private String id;
	private String name;
	private List<LightVO> lights = new LinkedList<LightVO>();

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

	public List<LightVO> getLights() {
		return lights;
	}

	public void setLights(List<LightVO> lights) {
		this.lights = lights;
	}

}
