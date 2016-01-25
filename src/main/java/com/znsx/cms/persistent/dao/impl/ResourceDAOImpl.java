package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.ResourceDAO;
import com.znsx.cms.persistent.model.Resource;

/**
 * 应急物资数据库接口实现
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:46:34
 */
public class ResourceDAOImpl extends BaseDAOImpl<Resource, String> implements
		ResourceDAO {

	@Override
	public Integer countTotalResource(String unitId, String abilityType,
			String unitType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Resource.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(abilityType)) {
			criteria.add(Restrictions.eq("abilityType", abilityType));
		}
		if (StringUtils.isNotBlank(unitType)) {
			criteria.add(Restrictions.eq("unitType", unitType));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Resource> listResource(String unitId, String abilityType,
			String unitType, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Resource.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		if (StringUtils.isNotBlank(abilityType)) {
			criteria.add(Restrictions.eq("abilityType", abilityType));
		}
		if (StringUtils.isNotBlank(unitType)) {
			criteria.add(Restrictions.eq("unitType", unitType));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
