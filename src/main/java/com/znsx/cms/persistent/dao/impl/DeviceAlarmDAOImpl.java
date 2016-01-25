package com.znsx.cms.persistent.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DeviceAlarmDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DeviceAlarm;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DeviceAlarmNumberVO;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * 设备告警数据库实现
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午6:42:40
 */
public class DeviceAlarmDAOImpl extends BaseDAOImpl<DeviceAlarm, String>
		implements DeviceAlarmDAO {

	@Override
	public List<DeviceAlarm> listDeviceAlarm(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		} else {
			criteria.add(Restrictions.in("organId", organIds));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			deviceName = MyStringUtil.escapeSQLLike(deviceName);
			// criteria.add(Restrictions.like("deviceName", "%" + deviceName
			// + "% escape '/'"));
			criteria.add(Restrictions.sqlRestriction(
					"{alias}.device_name like ? escape '/'", "%" + deviceName
							+ "%", Hibernate.STRING));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("alarmTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.lt("alarmTime", endTime));
		}
		criteria.addOrder(Order.desc("alarmTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer countDeviceAlarm(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		} else {
			criteria.add(Restrictions.in("organId", organIds));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions
					.like("deviceName", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("alarmTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.lt("alarmTime", endTime));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public Integer deviceAlarmTotalCount(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
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
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		if (null != beginTime && null != endTime) {
			criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DeviceAlarm> listDeviceAlarmByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
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
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		criteria.addOrder(Order.desc("alarmTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public void batchInsert(List<DeviceAlarm> list) throws BusinessException {
		Session session = getSession();
		batchInsert(list, session);
	}

	@Override
	public List<DeviceAlarm> listDeviceAlarmBySN(String standardNumber,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.eq("standardNumber", standardNumber));
		}
		criteria.add(Restrictions.eq("alarmType",
				TypeDefinition.ALARM_TYPE_OFFLINE));
		criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		criteria.addOrder(Order.desc("alarmTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.eq("standardNumber", standardNumber));
		}
		criteria.add(Restrictions.eq("alarmType",
				TypeDefinition.ALARM_TYPE_OFFLINE));
		criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		criteria.setProjection(Projections.rowCount());
		Integer total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public List<DeviceAlarmNumberVO> listDeviceAlarmNumber(Long beginTime,
			Long endTime) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listDeviceAlarmNumber(beginTime, endTime));
		if (null != beginTime && null != endTime) {
			query.setLong(0, beginTime);
			query.setLong(1, endTime);
		}
		List<Object[]> objects = query.list();
		List<DeviceAlarmNumberVO> list = new ArrayList<DeviceAlarmNumberVO>();
		for (Object[] obj : objects) {
			int i = 0;
			DeviceAlarmNumberVO vo = new DeviceAlarmNumberVO();
			vo.setFaultNumber(NumberUtil.getInteger(obj[i++]));
			vo.setStandardNumber(MyStringUtil.object2String(obj[i++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<DeviceAlarm> listFlagNull(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, Integer startIndex,
			Integer limit, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		criteria.add(Restrictions.not(Restrictions.eq("status",
				TypeDefinition.HISTORY_ALARM_TRUE)));
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
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
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
	public Integer deviceAlarmTotalFlagNullCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		criteria.add(Restrictions.not(Restrictions.eq("status",
				TypeDefinition.HISTORY_ALARM_TRUE)));
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
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		if (null != beginTime && null != endTime) {
			criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public Integer deviceAlarmTotalFlagNullCount(String deviceId,
			String deviceType, String[] organs, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		criteria.add(Restrictions.not(Restrictions.eq("status",
				TypeDefinition.HISTORY_ALARM_TRUE)));
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DeviceAlarm> listFlagNull(String deviceId, String deviceType,
			String[] organs, String alarmType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		criteria.add(Restrictions.not(Restrictions.eq("status",
				TypeDefinition.HISTORY_ALARM_TRUE)));
		criteria.addOrder(Order.desc("alarmTime"));
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		if (StringUtils.isNotBlank(deviceType)) {
			criteria.add(Restrictions.eq("deviceType", deviceType));
		}
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		return criteria.list();
	}

	@Override
	public Integer deviceHistoryTotalCount(String[] organs, String deviceId,
			String deviceType, String alarmType, String type, Long beginTime,
			Long endTime) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(type)) {
			if (type.equals(TypeDefinition.HISTORY_ALARM_FALSE)) {
				criteria.add(Restrictions.not(Restrictions.eq("status",
						TypeDefinition.HISTORY_ALARM_TRUE)));
			} else if (type.equals(TypeDefinition.HISTORY_ALARM_TRUE)) {
				criteria.add(Restrictions.eq("status",
						TypeDefinition.HISTORY_ALARM_TRUE));
			}
		}
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		criteria.add(Restrictions.eq("deviceType",
				TypeDefinition.DEVICE_TYPE_CAMERA + ""));
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DeviceAlarm> listDeviceAlarmHistory(String[] organs,
			String deviceId, String deviceType, String alarmType, String type,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceAlarm.class);
		if (StringUtils.isNotBlank(type)) {
			if (type.equals(TypeDefinition.HISTORY_ALARM_FALSE)) {
				criteria.add(Restrictions.not(Restrictions.eq("status",
						TypeDefinition.HISTORY_ALARM_TRUE)));
			} else if (type.equals(TypeDefinition.HISTORY_ALARM_TRUE)) {
				criteria.add(Restrictions.eq("status",
						TypeDefinition.HISTORY_ALARM_TRUE));
			}
		}
		if (null != organs) {
			criteria.add(Restrictions.in("organId", organs));
		} else {
			criteria.add(Restrictions.eq("deviceId", deviceId));
		}
		criteria.add(Restrictions.eq("deviceType",
				TypeDefinition.DEVICE_TYPE_CAMERA + ""));
		if (StringUtils.isNotBlank(alarmType)) {
			criteria.add(Restrictions.eq("alarmType", alarmType));
		}
		criteria.add(Restrictions.between("alarmTime", beginTime, endTime));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
