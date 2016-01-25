package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 预置点实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:00:23
 */
public class Preset implements Serializable {

	private static final long serialVersionUID = 4009427280267633549L;

	private String id;
	private String name;
	private String deviceId;
	private Integer presetValue;
	private String imageId;
	private Short isDefault;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getPresetValue() {
		return presetValue;
	}

	public void setPresetValue(Integer presetValue) {
		this.presetValue = presetValue;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

}
