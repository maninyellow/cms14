package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.ClassesDAO;
import com.znsx.cms.persistent.model.Classes;

/**
 * ClassesDAOImpl
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午9:43:07
 */
public class ClassesDAOImpl extends BaseDAOImpl<Classes, String> implements
		ClassesDAO {

	@Override
	public List<Classes> listClasses(String userName, Long beginTime,
			Long endTime, int start, int limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Classes.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.like("userName", "%"+userName+"%"));
		}
		if (null != beginTime) {
			criteria.add(Restrictions.ge("beginTime", beginTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.lt("beginTime", endTime));
		}
		criteria.addOrder(Order.desc("beginTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countClasses(String userName, Long begin, Long end) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Classes.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.eq("userName", userName));
		}
		if (null != begin) {
			criteria.add(Restrictions.ge("beginTime", begin));
		}
		if (null != end) {
			criteria.add(Restrictions.lt("endTime", end));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public Classes getClasses() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Classes.class);
		criteria.addOrder(Order.desc("beginTime"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		return (Classes) criteria.uniqueResult();
	}

}
