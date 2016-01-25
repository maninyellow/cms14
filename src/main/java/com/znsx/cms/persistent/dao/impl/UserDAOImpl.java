package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.model.User;

/**
 * 用户数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:37:19
 */
public class UserDAOImpl extends BaseDAOImpl<User, String> implements UserDAO {
	@Override
	public List<User> listUser(String[] organs, String userName,
			String logonName, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		if (organs.length > 0) {
			criteria.add(Restrictions.in("organ.id", organs));
		}
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.like("name", "%" + userName + "%"));
		}
		if (StringUtils.isNotBlank(logonName)) {
			criteria.add(Restrictions.like("logonName", "%" + logonName + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer userTotalCount(String[] organIds, String userName,
			String logonName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		if (organIds.length > 0) {
			criteria.add(Restrictions.in("organ.id", organIds));
		}
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.like("name", "%" + userName + "%"));
		}
		if (StringUtils.isNotBlank(logonName)) {
			criteria.add(Restrictions.like("logonName", "%" + logonName + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}
}
