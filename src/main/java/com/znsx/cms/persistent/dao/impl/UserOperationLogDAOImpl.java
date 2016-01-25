package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.UserOperationLogDAO;
import com.znsx.cms.persistent.model.UserOperationLog;

/**
 * UserOperationLogDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:38:47
 */
public class UserOperationLogDAOImpl extends
		BaseDAOImpl<UserOperationLog, String> implements UserOperationLogDAO {

	@Override
	public Integer findTotalCount(String operationType, Long beginTime,
			Long endTime) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserOperationLog.class);
		if (StringUtils.isNotBlank(operationType)) {
			criteria.add(Restrictions.eq("operationType", operationType));
		}
		if (null != beginTime) {
			criteria.add(Restrictions.ge("createTime", beginTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.le("createTime", endTime));
		}
		criteria.setProjection(Projections.rowCount());
		Integer total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public List<UserOperationLog> listUserOperationLog(String operationType,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserOperationLog.class);
		if (StringUtils.isNotBlank(operationType)) {
			criteria.add(Restrictions.eq("operationType", operationType));
		}
		if (null != beginTime) {
			criteria.add(Restrictions.ge("createTime", beginTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.le("createTime", endTime));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
