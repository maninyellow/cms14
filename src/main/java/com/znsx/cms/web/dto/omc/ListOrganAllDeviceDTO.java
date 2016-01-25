package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.ResourceOperationVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询机构及所有子机构下的设备针对给定角色的权限关系接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-19 下午2:11:34
 */
public class ListOrganAllDeviceDTO extends BaseDTO {
	private String totalCount;
	private List<DevicePermissionVO> deviceList;
	private List<ResourceOperationVO> permissionList;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DevicePermissionVO> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DevicePermissionVO> deviceList) {
		this.deviceList = deviceList;
	}

	public List<ResourceOperationVO> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<ResourceOperationVO> permissionList) {
		this.permissionList = permissionList;
	}

}
