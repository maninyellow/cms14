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
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * 风速风向检测器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:26:25
 */
public class WindSpeedDAOImpl extends BaseDAOImpl<WindSpeed, String> implements
		WindSpeedDAO {
	@Override
	public Map<String, WindSpeed> mapWSByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.setCacheable(false);
		criteria.setCacheMode(CacheMode.IGNORE);
		criteria.addOrder(Order.desc("createTime"));
		List<WindSpeed> list = criteria.list();
		Map<String, WindSpeed> map = new LinkedHashMap<String, WindSpeed>();
		for (WindSpeed ws : list) {
			map.put(ws.getId().toString(), ws);
		}
		return map;
	}

	@Override
	public Integer countWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);

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
	public List<WindSpeed> listWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);

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
	public List<WindSpeed> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, WindSpeed> mapWSBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<WindSpeed> list = criteria.list();
		Map<String, WindSpeed> map = new LinkedHashMap<String, WindSpeed>();
		for (WindSpeed ws : list) {
			map.put(ws.getStandardNumber(), ws);
		}
		return map;
	}

	@Override
	public void deleteRoleWSPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_WS);
		q.executeUpdate();
	}

	@Override
	public List<WindSpeed> wsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
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
	public Integer countWsInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
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
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countWindSpeed(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
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
