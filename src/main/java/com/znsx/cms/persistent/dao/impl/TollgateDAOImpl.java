package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.TollgateDAO;
import com.znsx.cms.persistent.model.Tollgate;

/**
 * TollgateDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:50:53
 */
public class TollgateDAOImpl extends BaseDAOImpl<Tollgate, String> implements
		TollgateDAO {

	@Override
	public List<Tollgate> listTollgate(String name, Integer startIndex,
			Integer limit, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Tollgate.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer tollgateTotalCount(String name, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Tollgate.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<Tollgate> listTollgates(List<String> organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Tollgate.class);
		criteria.add(Restrictions.in("parent.id", organIds));
		return criteria.list();
	}

	@Override
	public List<String> listByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Tollgate.class);
		criteria.add(Restrictions.like("name", "%" + name + "%"));
		List<Tollgate> list = criteria.list();
		List<String> rtn = new LinkedList<String>();
		for (Tollgate t : list) {
			rtn.add(t.getId());
		}
		return rtn;
	}

}
