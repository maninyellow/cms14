package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.WallSchemeDAO;
import com.znsx.cms.persistent.model.WallScheme;

/**
 * @author huangbuji
 *         <p />
 *         2014-11-18 下午3:23:07
 */
public class WallSchemeDAOImpl extends BaseDAOImpl<WallScheme, String>
		implements WallSchemeDAO {
	@Override
	public List<WallScheme> listWallScheme(String userId, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WallScheme.class);
		if (StringUtils.isNotBlank(userId)) {
			criteria.add(Restrictions.eq("user.id", userId));
		} else {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		return criteria.list();
	}
}
