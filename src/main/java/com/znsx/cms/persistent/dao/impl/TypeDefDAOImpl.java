package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.TypeDefDAO;
import com.znsx.cms.persistent.model.TypeDef;

/**
 * TypeDefDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-1 上午11:06:06
 */
public class TypeDefDAOImpl extends BaseDAOImpl<TypeDef, String> implements
		TypeDefDAO {
	@Override
	public List<TypeDef> listTypeDef(Integer type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(TypeDef.class);
		if (null != type) {
			criteria.add(Restrictions.eq("type", type));
		}
		criteria.addOrder(Order.asc("type"));
		return criteria.list();
	}
}
