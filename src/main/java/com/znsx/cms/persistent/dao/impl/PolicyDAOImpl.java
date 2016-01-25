package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.PolicyDAO;
import com.znsx.cms.persistent.model.Policy;

/**
 * 策略数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:23:20
 */
public class PolicyDAOImpl extends BaseDAOImpl<Policy, String> implements
		PolicyDAO {
	public List<Policy> listByOrgan(String organId, short type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Policy.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.add(Restrictions.eq("type", type));
		return criteria.list();
	}
}
