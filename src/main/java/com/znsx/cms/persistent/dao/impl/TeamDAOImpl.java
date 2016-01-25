package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.TeamDAO;
import com.znsx.cms.persistent.model.Team;

/**
 * TeamDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:15:14
 */
public class TeamDAOImpl extends BaseDAOImpl<Team, String> implements TeamDAO {

	@Override
	public Integer countTotalTeam(String unitId, String type, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Team.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Team> listTeam(String unitId, String type, String name,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Team.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
