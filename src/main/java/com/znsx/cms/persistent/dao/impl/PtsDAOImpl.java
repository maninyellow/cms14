package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.PtsDAO;
import com.znsx.cms.persistent.model.Pts;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 协转服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:54:20
 */
public class PtsDAOImpl extends BaseDAOImpl<Pts, String> implements PtsDAO {
	@Override
	public Pts getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Pts.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Pts pts = (Pts) criteria.uniqueResult();
		if (null == pts) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"PTS standardNumber[" + standardNumber + "] not found !");
		}
		return pts;
	}

	@Override
	public List<Pts> findPtsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Pts.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
}
