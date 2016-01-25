package com.znsx.cms.service.rule.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 道路事件条件
 * 
 * @author huangbuji
 *         <p />
 *         2014-11-30 下午2:32:26
 */
public class RoadEvent {
	public static final String FRONT_SEARCH_RANGE = "frontSearchRange";
	public static final String BACK_SEARCH_RANGE = "backSearchRange";

	private String name; // 长潭高速、长永高速、长沙收费站、雨花收费站、李家塘收费站、马家河收费站、三一收费站、黄花收费站、永安收费站
	private List<String> cameraTypes = new LinkedList<String>(); // 搜索的摄像头类型
	private Map<String, String> map = new HashMap<String, String>(); // "frontSearchRange"事件发生前方的搜索范围;"backSearchRange"事件发生后方的搜索范围
	private List<String> tollgates = new LinkedList<String>(); // 需要搜索的收费站

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCameraTypes() {
		return cameraTypes;
	}

	public void setCameraTypes(List<String> cameraTypes) {
		this.cameraTypes = cameraTypes;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<String> getTollgates() {
		return tollgates;
	}

	public void setTollgates(List<String> tollgates) {
		this.tollgates = tollgates;
	}

	public int getFrontSearchRange() {
		return Integer.parseInt(this.map.get(FRONT_SEARCH_RANGE));
	}

	public int getBackSearchRange() {
		return Integer.parseInt(this.map.get(BACK_SEARCH_RANGE));
	}

}
