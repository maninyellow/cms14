package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.RoadFluxStatVO;

/**
 * RoadFluxComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-22 上午9:24:58
 */
public class RoadFluxComparator implements Comparator<RoadFluxStatVO> {
	@Override
	public int compare(RoadFluxStatVO o1, RoadFluxStatVO o2) {
		return o1.getName().compareTo(o2.getName());
	}
}
