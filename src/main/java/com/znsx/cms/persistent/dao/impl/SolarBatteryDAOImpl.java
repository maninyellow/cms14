package com.znsx.cms.persistent.dao.impl;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SolarBatteryDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.SolarBattery;
import com.znsx.cms.service.model.DeviceSolarVO;
import com.znsx.cms.service.model.TimePolicyItemVO;

/**
 * SolarBatteryDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:04:48
 */
public class SolarBatteryDAOImpl extends BaseDAOImpl<SolarBattery, String>
		implements SolarBatteryDAO {

	@Override
	public Integer solarBatteryTotalCount(String name, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SolarBattery.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<SolarBattery> listSolarBattery(String name, String organId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SolarBattery.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public List<DeviceSolarVO> listDeviceSolar(String[] organIds) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listDeviceSolar(organIds));
		for (int i = 0; i < organIds.length; i++) {
			query.setString(i, organIds[i]);
		}
		List<Object[]> rows = query.list();
		List<DeviceSolarVO> list = new LinkedList<DeviceSolarVO>();
		for (Object[] row : rows) {
			DeviceSolarVO vo = new DeviceSolarVO();
			vo.setId(row[0].toString());
			vo.setSolarName(row[1].toString());
			vo.setSolarSN(row[2].toString());
			vo.setSolarNavigation(row[3].toString());
			vo.setSolarStakeNumber(row[4].toString());
			vo.setBatteryCapacity(row[5].toString());
			vo.setDeviceId(row[6].toString());
			vo.setType(row[7].toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<DeviceSolarVO> listDeviceSolar() {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listDeviceSolar());
		List<Object[]> rows = query.list();
		List<DeviceSolarVO> list = new LinkedList<DeviceSolarVO>();
		for (Object[] row : rows) {
			DeviceSolarVO vo = new DeviceSolarVO();
			vo.setId(row[0].toString());
			vo.setSolarName(row[1].toString());
			vo.setSolarSN(row[2].toString());
			vo.setSolarNavigation(row[3].toString());
			vo.setSolarStakeNumber(row[4].toString());
			vo.setBatteryCapacity(row[5].toString());
			vo.setDeviceId(row[6].toString());
			vo.setType(row[7].toString());
			list.add(vo);
		}
		return list;
	}
}
