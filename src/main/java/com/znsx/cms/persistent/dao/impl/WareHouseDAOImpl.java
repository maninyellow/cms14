package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.WareHouseDAO;
import com.znsx.cms.persistent.model.DeviceAlarm;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.WareHouse;

/**
 * WareHouseDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午12:02:47
 */
public class WareHouseDAOImpl extends BaseDAOImpl<WareHouse, String> implements
		WareHouseDAO {

	@Override
	public List<WareHouse> listWareHouse(String[] organs) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WareHouse.class);
		criteria.add(Restrictions.in("organ.id", organs));
		return criteria.list();
	}

	@Override
	public Map<String, WareHouse> mapWHByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WareHouse.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<WareHouse> list = criteria.list();
		Map<String, WareHouse> map = new LinkedHashMap<String, WareHouse>();
		for (WareHouse wh : list) {
			map.put(wh.getId().toString(), wh);
		}
		return map;
	}

}
