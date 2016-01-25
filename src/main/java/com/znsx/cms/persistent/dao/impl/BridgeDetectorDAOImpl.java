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

import com.znsx.cms.persistent.dao.BridgeDetectorDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.BridgeDetector;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * BridgeDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午10:04:17
 */
public class BridgeDetectorDAOImpl extends BaseDAOImpl<BridgeDetector, String>
		implements BridgeDetectorDAO {

	@Override
	public void deleteRoleBridgeDetectorPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR);
		q.executeUpdate();

	}

	@Override
	public Integer countBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BridgeDetector.class);

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
	public List<BridgeDetector> listBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BridgeDetector.class);

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
	public Map<String, BridgeDetector> mapBDByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BridgeDetector.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<BridgeDetector> list = criteria.list();
		Map<String, BridgeDetector> map = new LinkedHashMap<String, BridgeDetector>();
		for (BridgeDetector bd : list) {
			map.put(bd.getId().toString(), bd);
		}
		return map;
	}

	@Override
	public String[] countBD(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BridgeDetector.class);
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
		Criteria criteria = session.createCriteria(BridgeDetector.class);
		criteria.add(Restrictions.eq("organ.id", organ));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

}
