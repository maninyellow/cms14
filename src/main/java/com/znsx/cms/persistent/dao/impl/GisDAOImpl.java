package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.GisDAO;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.Gis;

/**
 * GisDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:53:57
 */
public class GisDAOImpl extends BaseDAOImpl<Gis, String> implements GisDAO {

	@Override
	public List<Gis> findGisByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Gis.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

}
