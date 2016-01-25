package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.ResourceUserDAO;
import com.znsx.cms.persistent.model.ResourceUser;

/**
 * ResourceUserDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午7:09:52
 */
public class ResourceUserDAOImpl extends BaseDAOImpl<ResourceUser, String>
		implements ResourceUserDAO {

	@Override
	public Integer countTotalResourceUser(String name, String type,
			String teamId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ResourceUser.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (StringUtils.isNotBlank(teamId)) {
			criteria.add(Restrictions.eq("team.id", teamId));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ResourceUser> listResourceUser(String name, String type,
			String teamId, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ResourceUser.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (StringUtils.isNotBlank(teamId)) {
			criteria.add(Restrictions.eq("team.id", teamId));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
