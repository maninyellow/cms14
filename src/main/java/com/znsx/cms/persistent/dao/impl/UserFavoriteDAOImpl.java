package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.UserFavoriteDAO;
import com.znsx.cms.persistent.model.UserFavorite;
import com.znsx.cms.service.model.CameraVO;

/**
 * 用户收藏夹数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:12:59
 */
public class UserFavoriteDAOImpl extends BaseDAOImpl<UserFavorite, String>
		implements UserFavoriteDAO {
	@Override
	public void deleteByUser(String userId) {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.deleteFavoriteByUser());
		query.setString(0, userId);
		query.executeUpdate();
	}

	@Override
	public List<CameraVO> listFavoriteCamera(String favoriteId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listFavoritesCamera());
		query.setString(0, favoriteId);
		List<Object[]> rows = query.list();
		List<CameraVO> list = new LinkedList<CameraVO>();
		for (Object[] row : rows) {
			CameraVO vo = new CameraVO();
			vo.setId(row[0].toString());
			vo.setStandardNumber(row[1].toString());
			vo.setName(row[2].toString());
			vo.setType(row[3].toString());
			vo.setSubType(row[4].toString());
			vo.setParentId(row[5].toString());
			vo.setManufacturerId(row[6] == null ? "" : row[6].toString());
			vo.setLocation(row[7] == null ? "" : row[7].toString());
			vo.setChannelNumber(row[8] == null ? "" : row[8].toString());
			vo.setTransport(row[9] == null ? "" : row[9].toString());
			vo.setMode(row[10] == null ? "" : row[10].toString());
			list.add(vo);
		}
		return list;
	}
}
