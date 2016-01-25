package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.service.model.ListCameraVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 根据dvrId查询摄像头接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午5:15:19
 */
public class ListCameraDTO extends BaseDTO {
	private List<ListCameraVO> cameraList = new ArrayList<ListCameraVO>();

	private String totalCount;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<ListCameraVO> getCameraList() {
		return cameraList;
	}

	public void setCameraList(List<ListCameraVO> cameraList) {
		this.cameraList = cameraList;
	}

}
