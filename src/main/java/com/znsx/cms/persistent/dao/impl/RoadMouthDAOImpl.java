package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.RoadMouthDAO;
import com.znsx.cms.persistent.model.RoadMouth;

/**
 * RoadMouthDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:39:10
 */
public class RoadMouthDAOImpl extends BaseDAOImpl<RoadMouth, String> implements
		RoadMouthDAO {

	@Override
	public List<RoadMouth> listRoadMouth(String name, Integer startIndex,
			Integer limit, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RoadMouth.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer roadMouthTotalCount(String name, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RoadMouth.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<RoadMouth> listRoadMouths(List<String> organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RoadMouth.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		return criteria.list();
	}

}
