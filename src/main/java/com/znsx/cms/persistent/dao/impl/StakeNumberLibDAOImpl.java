package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.StakeNumberLibDAO;
import com.znsx.cms.persistent.model.StakeNumberLib;

/**
 * 桩号坐标映射对象数据库接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-24 下午5:46:33
 */
public class StakeNumberLibDAOImpl extends BaseDAOImpl<StakeNumberLib, String>
		implements StakeNumberLibDAO {
	@Override
	public StakeNumberLib findByStakeNumber(String stakeNumber, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(StakeNumberLib.class);
		criteria.add(Restrictions.eq("stakeNumber", stakeNumber));
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		}
		List<StakeNumberLib> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new StakeNumberLib();
		}
	}
}
