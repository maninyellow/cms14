package com.znsx.cms.service.model;

/**
 * 获取机构设备树列表的机构业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:07:14
 */
public class CsOrganVO {
	private String id;
	private String standardNumber;
	private String name;
	private String type;
	private String imageId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
