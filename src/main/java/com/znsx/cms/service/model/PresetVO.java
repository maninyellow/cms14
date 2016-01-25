package com.znsx.cms.service.model;

/**
 * 预置点业务对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:08:26
 */
public class PresetVO {
	private String id;
	private String name;
	private String number;
	private String imageId;
	private String isDefault;

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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

}
