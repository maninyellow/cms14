package com.znsx.cms.service.model;

import java.util.List;

/**
 * ListOrganDeviceTree
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015-11-27 下午3:20:16
 */
public class ListOrganDeviceTreeVO {
	private String code;
	private String text;
	private List<ListOrganDeviceTreeVO> children;
	private boolean leaf;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ListOrganDeviceTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<ListOrganDeviceTreeVO> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
