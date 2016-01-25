package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.PlaySchemeDAO;
import com.znsx.cms.persistent.model.PlayScheme;

/**
 * 播放方案数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:17:27
 */
public class PlaySchemeDAOImpl extends BaseDAOImpl<PlayScheme, String>
		implements PlaySchemeDAO {

	@Override
	public List<PlayScheme> findByOrgans(String[] organs) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PlayScheme.class);
		criteria.add(Restrictions.in("organId", organs));
		return criteria.list();
	}

}
