package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.SchemeInstanceVO;
import com.znsx.cms.service.model.SchemeInstanceVO.Item;

/**
 * SchemeInstanceVOItemComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-23 上午10:54:31
 */
public class SchemeInstanceVOItemComparator implements
		Comparator<SchemeInstanceVO.Item> {
	@Override
	public int compare(Item o1, Item o2) {
		if (Long.parseLong(o1.getBeginTime()) < Long.parseLong(o2
				.getBeginTime())) {
			return -1;
		} else if (Long.parseLong(o1.getBeginTime()) < Long.parseLong(o2
				.getBeginTime())) {
			return 0;
		} else {
			return 1;
		}
	}
}
