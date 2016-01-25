package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * 气象检测器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:27:03
 */
public class WeatherStatDAOImpl extends BaseDAOImpl<WeatherStat, String>
		implements WeatherStatDAO {
	@Override
	public Map<String, WeatherStat> mapWSTByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.setCacheable(false);
		criteria.setCacheMode(CacheMode.IGNORE);
		criteria.addOrder(Order.desc("createTime"));
		List<WeatherStat> list = criteria.list();
		Map<String, WeatherStat> map = new LinkedHashMap<String, WeatherStat>();
		for (WeatherStat wst : list) {
			map.put(wst.getId().toString(), wst);
		}
		return map;
	}

	@Override
	public Integer countWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<WeatherStat> listWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public List<WeatherStat> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, WeatherStat> mapWSTBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<WeatherStat> list = criteria.list();
		Map<String, WeatherStat> map = new LinkedHashMap<String, WeatherStat>();
		for (WeatherStat wst : list) {
			map.put(wst.getStandardNumber(), wst);
		}
		return map;
	}

	@Override
	public void deleteRoleWSTPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_WST);
		q.executeUpdate();

	}

	@Override
	public List<WeatherStat> wstInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions.like("name", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(navigation)) {
			criteria.add(Restrictions.eq("navigation", navigation));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.eq("stakeNumber", stakeNumber));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(start.intValue());
		criteria.setMaxResults(limit.intValue());
		return criteria.list();
	}

	@Override
	public Integer countWstInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions.like("name", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(navigation)) {
			criteria.add(Restrictions.eq("navigation", navigation));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.eq("stakeNumber", stakeNumber));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countWST(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria.setProjection(Projections.groupProperty("organ.id"));
		criteria.add(Property.forName("organ.id").in(organIds));
		List<String> list = criteria.list();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}
}
