package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.SchemeInstanceVO;
import com.znsx.cms.service.model.SchemeInstanceVO.Step;

/**
 * SchemeInstanceStepComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-17 下午2:42:54
 */
public class SchemeInstanceStepComparator implements
		Comparator<SchemeInstanceVO.Step> {
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
