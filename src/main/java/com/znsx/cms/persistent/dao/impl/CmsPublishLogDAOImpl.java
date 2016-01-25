package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.znsx.cms.persistent.dao.CmsPublishLogDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.CmsPublishLog;

/**
 * 情报板发布日志记录数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-14 下午3:20:42
 */
public class CmsPublishLogDAOImpl extends BaseDAOImpl<CmsPublishLog, String>
		implements CmsPublishLogDAO {
	@Override
	public List<CmsPublishLog> listLatestRecord(String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(CmsPublishLog.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		DetachedCriteria subCriteria = DetachedCriteria
				.forClass(CmsPublishLog.class);
		subCriteria.add(Restrictions.eq("standardNumber", standardNumber));
		subCriteria.setProjection(Projections.max("sendTime"));
		criteria.add(Subqueries.propertyEq("sendTime", subCriteria));
		return criteria.list();
	}

	@Override
	public Integer countTotalCMSLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime) {
		Timestamp beginTimeT = new Timestamp(beginTime);
		Timestamp endTimeT = new Timestamp(endTime);
		Session session = getSession();
		Criteria criteria = session.createCriteria(CmsPublishLog.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions
					.like("sendUserName", "%" + userName + "%"));
		}
		criteria.add(Restrictions.in("standardNumber", cmsSNs));
		criteria.add(Restrictions.between("sendTime", beginTimeT, endTimeT));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<CmsPublishLog> listCmsLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		Timestamp beginTimeT = new Timestamp(beginTime);
		Timestamp endTimeT = new Timestamp(endTime);
		Session session = getSession();
		Criteria criteria = session.createCriteria(CmsPublishLog.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions
					.like("sendUserName", "%" + userName + "%"));
		}
		criteria.add(Restrictions.in("standardNumber", cmsSNs));
		criteria.addOrder(Order.desc("sendTime"));
		criteria.add(Restrictions.between("sendTime", beginTimeT, endTimeT));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public void batchInsert(List<CmsPublishLog> logs) {
		Session session = getSession();
		batchInsert(logs, session);
	}

	@Override
	public int countCmsPublishLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime) {
		Timestamp beginTimeT = new Timestamp(beginTime);
		Timestamp endTimeT = new Timestamp(endTime);
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countCmsPublishLog(cmsSNs, userName));
		int index = 0;
		for (String sn : cmsSNs) {
			query.setString(index++, sn);
		}
		query.setTimestamp(index++, beginTimeT);
		query.setTimestamp(index++, endTimeT);
		if (StringUtils.isNotBlank(userName)) {
			query.setString(index++, "%" + userName + "%");
		}
		Number totalCount = (Number) query.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<CmsPublishLog> listCmsPublishLog(String[] cmsSns,
			String userName, Long beginTime, Long endTime) {
		Timestamp beginTimeT = new Timestamp(beginTime);
		Timestamp endTimeT = new Timestamp(endTime);
		Session session = getSession();
		Criteria criteria = session.createCriteria(CmsPublishLog.class);
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions
					.like("sendUserName", "%" + userName + "%"));
		}
		criteria.add(Restrictions.in("standardNumber", cmsSns));
		criteria.addOrder(Order.desc("sendTime"));
		criteria.add(Restrictions.between("sendTime", beginTimeT, endTimeT));
		return criteria.list();
	}
}
