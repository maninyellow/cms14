package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.VisitRecordDAO;
import com.znsx.cms.persistent.model.VisitRecord;

/**
 * VisitRecordDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午9:43:07
 */
public class VisitRecordDAOImpl extends BaseDAOImpl<VisitRecord, String>
		implements VisitRecordDAO {
	@Override
	public List<VisitRecord> listVisitRecord(String userName, Timestamp begin,
			Timestamp end, int start, int limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VisitRecord.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.eq("userName", userName));
		}
		criteria.add(Restrictions.ge("recordTime", begin));
		criteria.add(Restrictions.lt("recordTime", end));
		criteria.addOrder(Order.desc("recordTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countVisitRecord(String userName, Timestamp begin, Timestamp end) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VisitRecord.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.eq("userName", userName));
		}
		criteria.add(Restrictions.ge("recordTime", begin));
		criteria.add(Restrictions.lt("recordTime", end));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}
}
