package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.persistent.model.SubPlatformResource;

/**
 * SubPlatformResourceComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-3-30 下午8:02:30
 */
public class SubPlatformResourceComparator implements
		Comparator<SubPlatformResource> {
	@Override
	public int compare(SubPlatformResource o1, SubPlatformResource o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
