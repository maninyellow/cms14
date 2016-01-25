package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.WfsRoadAdminVO;

/**
 * RoadAdminComparator
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:49:54
 */
public class RoadAdminComparator implements Comparator<WfsRoadAdminVO> {

	@Override
	public int compare(WfsRoadAdminVO o1, WfsRoadAdminVO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
