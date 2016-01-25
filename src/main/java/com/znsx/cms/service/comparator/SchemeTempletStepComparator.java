package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.SchemeTempletVO;
import com.znsx.cms.service.model.SchemeTempletVO.Step;

/**
 * SchemeTempletStepComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-13 上午9:42:13
 */
public class SchemeTempletStepComparator implements
		Comparator<SchemeTempletVO.Step> {
	@Override
	public int compare(Step o1, Step o2) {
		if (Integer.parseInt(o1.getSeq()) < Integer.parseInt(o2.getSeq())) {
			return -1;
		} else if (Integer.parseInt(o1.getSeq()) == Integer.parseInt(o2
				.getSeq())) {
			return 0;
		} else {
			return 1;
		}
	}
}
