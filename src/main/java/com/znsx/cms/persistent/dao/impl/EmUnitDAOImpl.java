package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.EmUnitDAO;
import com.znsx.cms.persistent.model.EmUnit;
import com.znsx.cms.persistent.model.Fire;
import com.znsx.cms.persistent.model.Hospital;
import com.znsx.cms.persistent.model.Police;
import com.znsx.cms.persistent.model.RoadAdmin;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * EmUnitDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午4:43:01
 */
public class EmUnitDAOImpl extends BaseDAOImpl<EmUnit, String> implements
		EmUnitDAO {

	@Override
	public List<EmUnit> listUnit(String name, String organId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EmUnit.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer countTotalUnit(String name, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EmUnit.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public EmUnit getUnit(String type, String gisId) {
		Session session = getSession();
		Criteria criteria = null;
		if (type.equals(TypeDefinition.DEVICE_TYPE_FIRE + "")) {
			criteria = session.createCriteria(Fire.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_POLICE + "")) {
			criteria = session.createCriteria(Police.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_HOSPITAL + "")) {
			criteria = session.createCriteria(Hospital.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_ROAD_ADMIN + "")) {
			criteria = session.createCriteria(RoadAdmin.class);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Unit type[" + type + "] not support !");
		}
		criteria.add(Restrictions.eq("gisId", gisId));
		return (EmUnit) criteria.uniqueResult();
	}

}
