package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.DeviceOnlineRealDAO;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * DeviceOnlineRealDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-17 上午10:30:29
 */
public class DeviceOnlineRealDAOImpl extends
		BaseDAOImpl<DeviceOnlineReal, String> implements DeviceOnlineRealDAO {
	@Override
	public Map<String, DeviceOnlineReal> mapBySns(Set<String> sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceOnlineReal.class);
		if (sns != null && sns.size() > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
		}
		List<DeviceOnlineReal> list = criteria.list();
		Map<String, DeviceOnlineReal> map = new HashMap<String, DeviceOnlineReal>();
		for (DeviceOnlineReal deviceOnlineReal : list) {
			map.put(deviceOnlineReal.getStandardNumber(), deviceOnlineReal);
		}
		return map;
	}

	@Override
	public List<DeviceOnlineReal> listDeviceOnline(String[] deviceSN) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceOnlineReal.class);
		if (deviceSN.length > 0) {
			criteria.add(Restrictions.in("standardNumber", deviceSN));
		}
		return criteria.list();
	}

	@Override
	public void batchInsert(List<DeviceOnlineReal> list) {
		Session session = getSession();
		super.batchInsert(list, session);
	}

	@Override
	public List<String> listOnlineDeviceSn() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceOnlineReal.class);
		List<DeviceOnlineReal> list = criteria.list();
		List<String> sns = new LinkedList<String>();
		for (DeviceOnlineReal real : list) {
			sns.add(real.getStandardNumber());
		}
		return sns;
	}

	@Override
	public List<DeviceOnlineReal> listDeviceStatusNoTransaction(Set<String> sns) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(DeviceOnlineReal.class);
		if (sns.size() > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
			try {
				return criteria.list();
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						e.getMessage());
			} finally {
				session.close();
			}
		}
		return new LinkedList<DeviceOnlineReal>();
	}
}
