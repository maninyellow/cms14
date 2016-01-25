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

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.ViDetectorDAO;
import com.znsx.cms.persistent.model.ViDetector;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * ViDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:35:17
 */
public class ViDetectorDAOImpl extends BaseDAOImpl<ViDetector, String>
		implements ViDetectorDAO {

	@Override
	public void deleteRoleViDetectorPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_VI_DETECTOR);
		q.executeUpdate();

	}

	@Override
	public Integer countViDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);

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
	public List<ViDetector> listViDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);

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
	public Map<String, ViDetector> mapVDByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<ViDetector> list = criteria.list();
		Map<String, ViDetector> map = new LinkedHashMap<String, ViDetector>();
		for (ViDetector vd : list) {
			map.put(vd.getId().toString(), vd);
		}
		return map;
	}

	@Override
	public String[] countVID(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria.setProjection(Projections.groupProperty("organ.id"));
		criteria.add(Property.forName("organ.id").in(organIds));
		List<String> list = criteria.list();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}

	@Override
	public int countByOrganId(String organ) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria.add(Restrictions.eq("organ.id", organ));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ViDetector> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

}
