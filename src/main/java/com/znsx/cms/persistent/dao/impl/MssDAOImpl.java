package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.MssDAO;
import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 流媒体服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:52:49
 */
public class MssDAOImpl extends BaseDAOImpl<Mss, String> implements MssDAO {
	@Override
	public Mss getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Mss.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Mss mss = (Mss) criteria.uniqueResult();
		if (null == mss) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"MSS standardNumber[" + standardNumber + "] not found !");
		}
		return mss;
	}

	@Override
	public List<Mss> findMssByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Mss.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
}
