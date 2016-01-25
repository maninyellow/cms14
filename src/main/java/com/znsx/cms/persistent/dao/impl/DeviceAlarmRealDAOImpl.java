package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DeviceAlarmRealDAO;
import com.znsx.cms.persistent.model.DeviceAlarmReal;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * DeviceAlarmRealDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-29 上午10:28:04
 */
public class DeviceAlarmRealDAOImpl extends
		BaseDAOImpl<DeviceAlarmReal, String> implements DeviceAlarmRealDAO {
	@Override
	public void batchInsert(List<DeviceAlarmReal> list) {
		Session session = getSession();
		super.batchInsert(list, session);
	}

	@Override
	public List<DeviceAlarmReal> listUseLock() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarmReal.class);
		criteria.setLockMode(LockMode.UPGRADE);
		return criteria.list();
	}

	@Override
	public Integer deviceAlarmRealTotalCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarmReal.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions
					.like("deviceName", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		criteria.add(Restrictions.eq("alarmType",
				TypeDefinition.ALARM_TYPE_OFFLINE));
		if (null != beginTime && null != endTime) {
			criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DeviceAlarmReal> listDeviceAlarmReal(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarmReal.class);
		criteria.addOrder(Order.desc("alarmTime"));
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions
					.like("deviceName", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		criteria.add(Restrictions.eq("alarmType",
				TypeDefinition.ALARM_TYPE_OFFLINE));
		if (null != beginTime && null != endTime) {
			criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		}
		if (null != startIndex && null != limit) {
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(limit);
		}
		return criteria.list();
	}

	@Override
	public Integer deviceAlarmTotalCount(String deviceId, String deviceType,
			String[] organs, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarmReal.class);
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		criteria.add(Restrictions.eq("alarmType", alarmType));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DeviceAlarmReal> listDeviceAlarmReal(String deviceId,
			String deviceType, String[] organs, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarmReal.class);
		criteria.addOrder(Order.desc("alarmTime"));
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		criteria.add(Restrictions.eq("alarmType", alarmType));
		return criteria.list();
	}
}
