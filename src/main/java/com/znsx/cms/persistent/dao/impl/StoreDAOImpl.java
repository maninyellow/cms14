package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.StoreDAO;
import com.znsx.cms.persistent.model.Store;

/**
 * StoreDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午1:52:51
 */
public class StoreDAOImpl extends BaseDAOImpl<Store, String> implements
		StoreDAO {

	@Override
	public Integer countTotalStore(String unitId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Store.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<Store> listStore(String unitId, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Store.class);
		if (StringUtils.isNotBlank(unitId)) {
			criteria.add(Restrictions.eq("unit.id", unitId));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

}
