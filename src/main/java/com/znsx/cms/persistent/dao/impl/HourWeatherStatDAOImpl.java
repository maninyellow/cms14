package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.HourWeatherStatDAO;
import com.znsx.cms.persistent.model.HourWeatherStat;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * HourWeatherStatDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-21 上午10:13:19
 */
public class HourWeatherStatDAOImpl extends
		BaseDAOImpl<HourWeatherStat, String> implements HourWeatherStatDAO {
	@Override
	public HourWeatherStat getLastRecord() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(HourWeatherStat.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("dateTime"));
		return (HourWeatherStat) criteria.uniqueResult();
	}

	@Override
	public void batchInsert(List<HourWeatherStat> list) {
		Session session = getSession();
		batchInsert(list, session);
	}

	@Override
	public List<HourWeatherStat> list(String sn, Long beginTime, Long endTime,
			int start, int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session.createCriteria(HourWeatherStat.class);
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
			Criteria criteria = session.createCriteria(HourWeatherStat.class);
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
