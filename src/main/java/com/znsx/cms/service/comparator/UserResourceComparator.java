package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.service.model.UserResourceVO;

/**
 * UserResourceComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-4 上午10:32:25
 */
public class UserResourceComparator implements Comparator<UserResourceVO> {
	@Override
	public int compare(UserResourceVO o1, UserResourceVO o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
