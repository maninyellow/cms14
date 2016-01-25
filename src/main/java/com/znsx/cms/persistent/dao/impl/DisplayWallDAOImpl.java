package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DisplayWallDAO;
import com.znsx.cms.persistent.model.DisplayWall;

/**
 * 数据库电视墙实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:34:17
 */
public class DisplayWallDAOImpl extends BaseDAOImpl<DisplayWall, String>
		implements DisplayWallDAO {

	@Override
	public Integer wallTotalCount(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DisplayWall.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		Integer totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<DisplayWall> listWall(String organId, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DisplayWall.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Map<String, DisplayWall> mapWallByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DisplayWall.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<DisplayWall> list = criteria.list();
		Map<String, DisplayWall> map = new LinkedHashMap<String, DisplayWall>();
		for (DisplayWall wall : list) {
			map.put(wall.getId().toString(), wall);
		}
		return map;
	}

}
