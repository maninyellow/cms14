package com.znsx.cms.service.model;

/**
 * service层统计设备计数类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:33:53
 */
public class CountDataTypeVO {
	private String name;
	private int[] data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}


}
