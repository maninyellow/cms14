package com.znsx.cms.web.dto.cs;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * CreatePresetDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:57:14
 */
public class CreatePresetDTO extends BaseDTO {
	private String presetId;
	private String id;

	public String getPresetId() {
		return presetId;
	}

	public void setPresetId(String presetId) {
		this.presetId = presetId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
