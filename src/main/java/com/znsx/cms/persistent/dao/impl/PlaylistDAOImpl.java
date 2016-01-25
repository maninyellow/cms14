package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.PlaylistDAO;
import com.znsx.cms.persistent.model.Playlist;

/**
 * 播放方案数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 上午10:54:29
 */
public class PlaylistDAOImpl extends BaseDAOImpl<Playlist, String> implements
		PlaylistDAO {
	public List<Playlist> listByOrgan(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Playlist.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		return criteria.list();
	}

	@Override
	public List<Playlist> listPlaylist(String folderId, Short type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Playlist.class);
		criteria.add(Restrictions.eq("folder.id", folderId));
		if (null != type) {
			criteria.add(Restrictions.eq("type", type));
		}
		return criteria.list();
	}
}
