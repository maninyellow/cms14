package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.MonitorDAO;
import com.znsx.cms.persistent.model.Monitor;

/**
 * 数据库视频输出实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:35:26
 */
public class MonitorDAOImpl extends BaseDAOImpl<Monitor, String> implements
		MonitorDAO {

	@Override
	public Integer monitorTotalCount(String wallId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Monitor.class);
		criteria.add(Restrictions.eq("displayWall.id", wallId));
		criteria.setProjection(Projections.rowCount());
		Integer totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<Monitor> listMonitor(String wallId, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Monitor.class);
		criteria.add(Restrictions.eq("displayWall.id", wallId));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
