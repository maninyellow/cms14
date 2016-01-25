package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.VehicleDAO;
import com.znsx.cms.persistent.model.Vehicle;

/**
 * VehicleDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:55:02
 */
public class VehicleDAOImpl extends BaseDAOImpl<Vehicle, String> implements
		VehicleDAO {

	@Override
	public Integer countTotalVehilce(String unitId, String abilityType,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Vehicle.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(abilityType)) {
			criteria.add(Restrictions.eq("abilityType", abilityType));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Vehicle> listVehicle(String unitId, String abilityType,
			String name, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Vehicle.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(abilityType)) {
			criteria.add(Restrictions.eq("abilityType", abilityType));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
