package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.TunnelDeviceStatusVO;

/**
 * TunnelDeviceStatusComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-9 下午2:10:56
 */
public class TunnelDeviceStatusComparator implements
		Comparator<TunnelDeviceStatusVO> {
	public int compare(TunnelDeviceStatusVO o1, TunnelDeviceStatusVO o2) {
		int result = o1.getOrganId().compareTo(o2.getOrganId());
		// 先按机构排序，再按名称进行排序
		if (0 == result) {
			return o1.getName().compareTo(o2.getName());
		} else {
			return result;
		}
	};
}
