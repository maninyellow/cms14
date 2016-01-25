package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.WorkPlanDAO;
import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 
 * @author huangbuji
 *         <p />
 *         2014-12-3 上午9:59:14
 */
public class WorkPlanDAOImpl extends BaseDAOImpl<WorkPlan, String> implements
		WorkPlanDAO {
	@Override
	public void batchInsert(List<WorkPlan> list) throws BusinessException {
		Session session = getSession();
		batchInsert(list, session);
	}
	
	@Override
	public WorkPlan getCurrentWorkPlan(String type, Timestamp today) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WorkPlan.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("workDate", today));
		List<WorkPlan> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void deleteByType(String type) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteWorkPlan();
		Query q = session.createSQLQuery(sql);
		q.setString(0, type);
		q.executeUpdate();
		session.evict(WorkPlan.class);
	}
}
