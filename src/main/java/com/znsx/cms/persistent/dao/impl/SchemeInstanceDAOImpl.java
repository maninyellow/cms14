package com.znsx.cms.persistent.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SchemeInstanceDAO;
import com.znsx.cms.persistent.model.SchemeInstance;

/**
 * SchemeInstanceDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午2:23:07
 */
public class SchemeInstanceDAOImpl extends BaseDAOImpl<SchemeInstance, String>
		implements SchemeInstanceDAO {
	@Override
	public SchemeInstance getSchemeInstance(String eventId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SchemeInstance.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		return (SchemeInstance) criteria.uniqueResult();
	}
}
