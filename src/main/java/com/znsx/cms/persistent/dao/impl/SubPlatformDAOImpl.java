package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SubPlatformDAO;
import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 下级平台数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:53:08
 */
public class SubPlatformDAOImpl extends BaseDAOImpl<SubPlatform, String>
		implements SubPlatformDAO {
	@Override
	public SubPlatform getByStandardNumber(String standardNumber)
			throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatform.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Object o = criteria.uniqueResult();
		return (SubPlatform) o;
	}

	@Override
	public Map<String, SubPlatform> mapSubPlatform() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatform.class);
		List<SubPlatform> list = criteria.list();
		Map<String, SubPlatform> map = new HashMap<String, SubPlatform>();
		for (SubPlatform platform : list) {
			map.put(platform.getStandardNumber(), platform);
		}
		return map;
	}
}
