package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.persistent.model.Organ;

/**
 * OrganDeepComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-12-2 下午1:54:00
 */
public class OrganDeepComparator implements Comparator<Organ> {
	public int compare(Organ o1, Organ o2) {
		if (o1.getDeep().intValue() > o2.getDeep().intValue()) {
			return -1;
		} else if (o1.getDeep().intValue() == o2.getDeep().intValue()) {
			return 0;
		} else {
			return 1;
		}
	};
}
