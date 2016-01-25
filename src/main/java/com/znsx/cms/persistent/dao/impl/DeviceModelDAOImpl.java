package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DeviceModelDAO;
import com.znsx.cms.persistent.model.DeviceModel;

/**
 * 设备类型数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:17:46
 */
public class DeviceModelDAOImpl extends BaseDAOImpl<DeviceModel, String>
		implements DeviceModelDAO {

	@Override
	public List<DeviceModel> listDeviceModel(String manufacturerId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceModel.class);
		if (null != manufacturerId) {
			criteria.add(Restrictions.eq("manufacturer.id", manufacturerId));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer deviceModeTotalCount(String manufacturerId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceModel.class);
		if (null != manufacturerId) {
			criteria.add(Restrictions.eq("manufacturer.id", manufacturerId));
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

}
