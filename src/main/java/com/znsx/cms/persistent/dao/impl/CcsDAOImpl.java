package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 通信服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:49:13
 */
public class CcsDAOImpl extends BaseDAOImpl<Ccs, String> implements CcsDAO {

	@Override
	public List<Ccs> findPlatformServer(Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ccs.class);
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer platformServerTotalCount() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ccs.class);
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public Ccs getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ccs.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Ccs ccs = (Ccs) criteria.uniqueResult();
		if (null == ccs) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"CCS standardNumber[" + standardNumber + "] not found !");
		}
		return ccs;
	}

	@Override
	public List<Ccs> findCcsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ccs.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
}
