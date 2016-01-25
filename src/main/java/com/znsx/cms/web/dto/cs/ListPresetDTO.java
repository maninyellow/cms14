package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.PresetVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据摄像头ID查询预置点接口返回对象
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:09:58
 */
public class ListPresetDTO extends BaseDTO {
	private List<PresetVO> preset = new ArrayList<PresetVO>();

	public List<PresetVO> getPreset() {
		return preset;
	}

	public void setPreset(List<PresetVO> preset) {
		this.preset = preset;
	}

}
