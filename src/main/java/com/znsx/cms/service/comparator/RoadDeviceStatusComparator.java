package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.RoadDeviceStatusVO;

/**
 * RoadDeviceStatusComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 下午4:49:03
 */
public class RoadDeviceStatusComparator implements
		Comparator<RoadDeviceStatusVO> {
	public int compare(RoadDeviceStatusVO o1, RoadDeviceStatusVO o2) {
		int result = o1.getOrganId().compareTo(o2.getOrganId());
		// 先按机构排序，再按名称进行排序
		if (0 == result) {
			return o1.getName().compareTo(o2.getName());
		} else {
			return result;
		}
	};
}
