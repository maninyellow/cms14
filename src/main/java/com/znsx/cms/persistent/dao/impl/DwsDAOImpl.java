package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DwsDAO;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 显示墙服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:16:35
 */
public class DwsDAOImpl extends BaseDAOImpl<Dws, String> implements DwsDAO {
	@Override
	public Dws getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dws.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Dws dws = (Dws) criteria.uniqueResult();
		if (null == dws) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"DWS standardNumber[" + standardNumber + "] not found !");
		}
		return dws;
	}

	@Override
	public List<Dws> findDwsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dws.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
}
