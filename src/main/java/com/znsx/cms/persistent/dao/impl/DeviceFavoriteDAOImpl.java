package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DeviceFavoriteDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DeviceFavorite;

/**
 * DeviceFavoriteDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-12 上午10:39:46
 */
public class DeviceFavoriteDAOImpl extends BaseDAOImpl<DeviceFavorite, String>
		implements DeviceFavoriteDAO {
	@Override
	public List<DeviceFavorite> listByFavoriteId(String favoriteId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceFavorite.class);
		if (StringUtils.isNotBlank(favoriteId)) {
			criteria.add(Restrictions.eq("favoriteId", favoriteId));
		}
		return criteria.list();
	}

	@Override
	public void deleteByFavoriteId(String favoriteId) {
		Session session = getSession();
		Query q = session.createSQLQuery(SqlFactory.getInstance()
				.deleteByfavoriteId());
		q.setString(0, favoriteId);
		q.executeUpdate();
	}

	@Override
	public DeviceFavorite get(String favoriteId, String deviceId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DeviceFavorite.class);
		criteria.add(Restrictions.eq("favoriteId", favoriteId));
		criteria.add(Restrictions.eq("deviceId", deviceId));
		return (DeviceFavorite) criteria.uniqueResult();
	}
}
