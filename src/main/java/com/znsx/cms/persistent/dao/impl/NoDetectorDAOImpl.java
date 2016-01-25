package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * 氮氧化合物检测器数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:06:00
 */
public class NoDetectorDAOImpl extends BaseDAOImpl<NoDetector, String>
		implements NoDetectorDAO {
	@Override
	public Map<String, NoDetector> mapNODByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<NoDetector> list = criteria.list();
		Map<String, NoDetector> map = new LinkedHashMap<String, NoDetector>();
		for (NoDetector nod : list) {
			map.put(nod.getId().toString(), nod);
		}
		return map;
	}

	@Override
	public Integer countNoDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);

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
	public List<NoDetector> listNoDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);

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
	public List<NoDetector> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, NoDetector> mapNODBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<NoDetector> list = criteria.list();
		Map<String, NoDetector> map = new LinkedHashMap<String, NoDetector>();
		for (NoDetector nod : list) {
			map.put(nod.getStandardNumber(), nod);
		}
		return map;
	}

	@Override
	public void deleteRoleNODPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_NOD);
		q.executeUpdate();
	}

	@Override
	public List<NoDetector> nodInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
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
	public Integer countNodInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
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
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countND(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
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
