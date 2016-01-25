package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.RssDAO;
import com.znsx.cms.persistent.model.Rms;
import com.znsx.cms.persistent.model.Rss;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * RssDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-27 上午9:58:05
 */
public class RssDAOImpl extends BaseDAOImpl<Rss, String> implements RssDAO {

	@Override
	public List<Rss> findRssByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Rss.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

}
