package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.StatCameraStatusVO;

/**
 * CameraStatusComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-9 下午5:12:05
 */
public class CameraStatusComparator implements Comparator<StatCameraStatusVO> {
	public int compare(StatCameraStatusVO o1, StatCameraStatusVO o2) {
		int result = o1.getOrganId().compareTo(o2.getOrganId());
		// 先按机构排序，再按名称进行排序
		if (0 == result) {
			return o1.getName().compareTo(o2.getName());
		} else {
			return result;
		}
	};
}
