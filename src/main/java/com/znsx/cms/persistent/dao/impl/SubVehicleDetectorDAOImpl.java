package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SubVehicleDetectorDAO;
import com.znsx.cms.persistent.model.SubVehicleDetector;
import com.znsx.cms.persistent.model.VehicleDetector;

/**
 * SubVehicleDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:44:30
 */
public class SubVehicleDetectorDAOImpl extends
		BaseDAOImpl<SubVehicleDetector, String> implements
		SubVehicleDetectorDAO {

	@Override
	public Integer countSubVehicleDetector(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubVehicleDetector.class);

		criteria.add(Restrictions.eq("parent.id", parentId));

		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<SubVehicleDetector> listSubVehicleDetector(String parentId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubVehicleDetector.class);

		criteria.add(Restrictions.eq("parent.id", parentId));

		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
