package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.znsx.cms.persistent.dao.ManufacturerDAO;
import com.znsx.cms.persistent.model.Manufacturer;

/**
 * 生产厂商数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:17:15
 */
public class ManufacturerDAOImpl extends BaseDAOImpl<Manufacturer, String>
		implements ManufacturerDAO {

	public List<Manufacturer> listManufacturer(Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Manufacturer.class);
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public Integer manufacturerTotalCount() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Manufacturer.class);
		criteria.setProjection(Projections.rowCount());
		Integer totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

}
