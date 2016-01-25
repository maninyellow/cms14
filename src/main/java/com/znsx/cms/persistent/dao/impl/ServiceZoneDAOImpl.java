package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.ServiceZoneDAO;
import com.znsx.cms.persistent.model.ServiceZone;

/**
 * ServiceZoneDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:54:29
 */
public class ServiceZoneDAOImpl extends BaseDAOImpl<ServiceZone, String>
		implements ServiceZoneDAO {

	@Override
	public Integer serviceZoneTotalCount(String name, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ServiceZone.class);

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ServiceZone> listServiceZone(String name, Integer startIndex,
			Integer limit, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ServiceZone.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();

	}

}
