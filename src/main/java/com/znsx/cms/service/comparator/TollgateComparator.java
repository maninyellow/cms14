package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.WfsTollgateVO;

/**
 * TollgateComparator
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:55:08
 */
public class TollgateComparator implements Comparator<WfsTollgateVO> {
	@Override
	public int compare(WfsTollgateVO o1, WfsTollgateVO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
