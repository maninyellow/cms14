package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.EnsDAO;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 事件服务器数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:59:56
 */
public class EnsDAOImpl extends BaseDAOImpl<Ens, String> implements EnsDAO {

	@Override
	public List<Ens> findEnsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ens.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Ens getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ens.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Ens ens = (Ens) criteria.uniqueResult();
		if (null == ens) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"ENS standardNumber[" + standardNumber + "] not found !");
		}
		return ens;
	}
}
