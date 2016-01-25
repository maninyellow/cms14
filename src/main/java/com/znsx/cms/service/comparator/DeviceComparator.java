package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.AuthDeviceVO;

/**
 * DeviceComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-8 下午3:44:28
 */
public class DeviceComparator implements Comparator<AuthDeviceVO> {
	@Override
	public int compare(AuthDeviceVO o1, AuthDeviceVO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
