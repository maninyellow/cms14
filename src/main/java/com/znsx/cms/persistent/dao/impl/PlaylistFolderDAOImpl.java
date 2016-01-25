package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.PlaylistFolderDAO;
import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.persistent.model.PlaylistFolder;

/**
 * 存放情报板播放方案的文件夹对象数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-31 下午4:07:12
 */
public class PlaylistFolderDAOImpl extends BaseDAOImpl<PlaylistFolder, String>
		implements PlaylistFolderDAO {
	@Override
	public List<PlaylistFolder> listFolder(Integer subType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PlaylistFolder.class);
		if (null != subType) {
			criteria.add(Restrictions.eq("subType", subType));
		}
		return criteria.list();
	}
}
