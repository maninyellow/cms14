package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.MonthWeatherStatDAO;
import com.znsx.cms.persistent.model.MonthWeatherStat;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * MonthWeatherStatDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-25 下午5:20:06
 */
public class MonthWeatherStatDAOImpl extends
		BaseDAOImpl<MonthWeatherStat, String> implements MonthWeatherStatDAO {
	@Override
	public MonthWeatherStat getLastRecord() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(MonthWeatherStat.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("dateTime"));
		return (MonthWeatherStat) criteria.uniqueResult();
	}

	@Override
	public void batchInsert(List<MonthWeatherStat> list) {
		Session session = getSession();
		batchInsert(list, session);
	}

	@Override
	public List<MonthWeatherStat> list(String sn, Long beginTime, Long endTime,
			int start, int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session.createCriteria(MonthWeatherStat.class);
			if (StringUtils.isNotBlank(sn)) {
				criteria.add(Restrictions.eq("standardNumber", sn));
			}
			criteria.add(Restrictions.ge("dateTime", beginTime));
			criteria.add(Restrictions.lt("dateTime", endTime));
			criteria.setFirstResult(start);
			criteria.setMaxResults(limit);
			criteria.addOrder(Order.asc("dateTime"));
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}

	@Override
	public int count(String sn, Long beginTime, Long endTime) {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session.createCriteria(MonthWeatherStat.class);
			if (StringUtils.isNotBlank(sn)) {
				criteria.add(Restrictions.eq("standardNumber", sn));
			}
			criteria.add(Restrictions.ge("dateTime", beginTime));
			criteria.add(Restrictions.lt("dateTime", endTime));
			criteria.setProjection(Projections.rowCount());
			Number totalCount = (Number) criteria.uniqueResult();
			return totalCount.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}
}
