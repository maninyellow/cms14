package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.SrsDAO;
import com.znsx.cms.persistent.model.Srs;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 录音服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:51:10
 */
public class SrsDAOImpl extends BaseDAOImpl<Srs, String> implements SrsDAO {
	@Override
	public Srs getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Srs.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Srs srs = (Srs) criteria.uniqueResult();
		if (null == srs) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"CRS standardNumber[" + standardNumber + "] not found !");
		}
		return srs;
	}

	@Override
	public Map<String, Srs> mapByIdNoTransaction() {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(Srs.class);
		try {
			List<Srs> list = criteria.list();
			Map<String, Srs> map = new HashMap<String, Srs>();
			for (Srs srs : list) {
				map.put(srs.getId(), srs);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Srs> findSrsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Srs.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}
}
