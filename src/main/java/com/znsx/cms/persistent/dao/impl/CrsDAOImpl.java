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

import com.znsx.cms.persistent.dao.CrsDAO;
import com.znsx.cms.persistent.model.Crs;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 中心存储服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:51:10
 */
public class CrsDAOImpl extends BaseDAOImpl<Crs, String> implements CrsDAO {
	@Override
	public Crs getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Crs.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Crs crs = (Crs) criteria.uniqueResult();
		if (null == crs) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"CRS standardNumber[" + standardNumber + "] not found !");
		}
		return crs;
	}

	@Override
	public List<Crs> findCrsByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Crs.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, Crs> mapByIdNoTransaction() {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(Crs.class);
		try {
			List<Crs> list = criteria.list();
			Map<String, Crs> map = new HashMap<String, Crs>();
			for (Crs crs : list) {
				map.put(crs.getId(), crs);
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
}
