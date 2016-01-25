package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DeviceOnlineDAO;
import com.znsx.cms.persistent.model.DeviceOnline;

/**
 * DeviceOnlineDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-15 下午2:04:42
 */
public class DeviceOnlineDAOImpl extends BaseDAOImpl<DeviceOnline, String>
		implements DeviceOnlineDAO {

	@Override
	public List<DeviceOnline> listDeviceOnline(String[] standardNumber,
			Long begin, Long end) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceOnline.class);
		if (standardNumber.length > 0) {
			criteria.add(Restrictions.in("standardNumber", standardNumber));
		}
		criteria.add(Restrictions.le("onlineTime", end));
		criteria.add(Restrictions.gt("offlineTime", begin));
		return criteria.list();
	}

	@Override
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceOnline.class);
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.eq("standardNumber", standardNumber));
		}
		criteria.add(Restrictions.le("onlineTime", endTime));
		criteria.add(Restrictions.gt("offlineTime", beginTime));
		criteria.setProjection(Projections.rowCount());
		Integer total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public void batchInsert(List<DeviceOnline> list) {
		Session session = getSession();
		super.batchInsert(list, session);
	}
}
