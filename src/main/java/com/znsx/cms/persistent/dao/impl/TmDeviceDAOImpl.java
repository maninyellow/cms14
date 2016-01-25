package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.TmDeviceDAO;
import com.znsx.cms.persistent.model.TmDevice;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * TmDeviceDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 上午11:54:14
 */
public class TmDeviceDAOImpl extends BaseDAOImpl<TmDevice, String> implements
		TmDeviceDAO {

	@Override
	public Integer countGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(TmDevice.class);
		criteria.add(Restrictions.eq("type", TypeDefinition.DEVICE_TYPE_GPS
				+ ""));
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<TmDevice> listGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(TmDevice.class);
		criteria.add(Restrictions.eq("type", TypeDefinition.DEVICE_TYPE_GPS
				+ ""));
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Map<String, TmDevice> mapDeviceByOrgansNoTransaction(
			List<String> organIds, String name, String type) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(TmDevice.class);
		if (organIds.size() > 0) {
			criteria.add(Restrictions.in("organ.id", organIds));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}

		try {
			List<TmDevice> list = criteria.list();
			Map<String, TmDevice> map = new HashMap<String, TmDevice>();
			for (TmDevice d : list) {
				map.put(d.getStandardNumber(), d);
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
