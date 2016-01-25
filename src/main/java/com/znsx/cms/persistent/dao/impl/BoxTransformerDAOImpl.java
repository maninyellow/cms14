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

import com.znsx.cms.persistent.dao.BoxTransformerDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.BoxTransformer;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * BoxTransformerDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:22:52
 */
public class BoxTransformerDAOImpl extends BaseDAOImpl<BoxTransformer, String>
		implements BoxTransformerDAO {

	@Override
	public void deleteRoleBTPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_BT);
		q.executeUpdate();

	}

	@Override
	public Integer countBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BoxTransformer.class);

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
	public List<BoxTransformer> listBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BoxTransformer.class);

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
	public Map<String, BoxTransformer> mapBTByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BoxTransformer.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<BoxTransformer> list = criteria.list();
		Map<String, BoxTransformer> map = new LinkedHashMap<String, BoxTransformer>();
		for (BoxTransformer bt : list) {
			map.put(bt.getId().toString(), bt);
		}
		return map;
	}

	@Override
	public String[] countBT(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BoxTransformer.class);
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
		Criteria criteria = session.createCriteria(BoxTransformer.class);
		criteria.add(Restrictions.eq("organ.id", organ));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

}
