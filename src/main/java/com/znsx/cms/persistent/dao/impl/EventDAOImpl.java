package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.EventDAO;
import com.znsx.cms.persistent.model.Event;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * EventDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:35:52
 */
public class EventDAOImpl extends BaseDAOImpl<Event, String> implements
		EventDAO {

	@Override
	public List<Event> listEventByOrganId(String[] organs, Long beginTime,
			Long endTime, Short type, Short status) {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session.createCriteria(Event.class);
			criteria.add(Restrictions.in("organ.id", organs));
			if (null != beginTime) {
				criteria.add(Restrictions.ge("createTime", beginTime));
			}
			if (null != endTime) {
				criteria.add(Restrictions.lt("createTime", endTime));
			}
			if (null != type) {
				criteria.add(Restrictions.eq("type", type));
			}
			if (null != status) {
				criteria.add(Restrictions.eq("status", status));
			}
			criteria.addOrder(Order.desc("createTime"));
			return criteria.list();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}
}
