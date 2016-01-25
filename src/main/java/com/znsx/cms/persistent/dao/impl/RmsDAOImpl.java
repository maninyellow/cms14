package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.RmsDAO;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.Rms;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * RmsDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:12:47
 */
public class RmsDAOImpl extends BaseDAOImpl<Rms, String> implements RmsDAO {

	@Override
	public List<Rms> findRmsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Rms.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Rms getByStandardNumber(String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Rms.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Rms rms = (Rms) criteria.uniqueResult();
		if (null == rms) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"RMS standardNumber[" + standardNumber + "] not found !");
		}
		return rms;
	}

}
