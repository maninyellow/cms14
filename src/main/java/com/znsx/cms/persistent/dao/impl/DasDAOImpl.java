package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasDAO;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 数据采集服务器数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:27:03
 */
public class DasDAOImpl extends BaseDAOImpl<Das, String> implements DasDAO {

	@Override
	public List<Das> findDasByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Das.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Das getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Das.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Das das = (Das) criteria.uniqueResult();
		if (null == das) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"DAS standardNumber[" + standardNumber + "] not found !");
		}
		return das;
	}
}
