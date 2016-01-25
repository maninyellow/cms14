package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SchemeTempletDAO;
import com.znsx.cms.persistent.model.SchemeTemplet;

/**
 * SchemeTempletDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午3:40:38
 */
public class SchemeTempletDAOImpl extends BaseDAOImpl<SchemeTemplet, String>
		implements SchemeTempletDAO {
	@Override
	public List<SchemeTemplet> listSchemeTemplet(String organId, Short type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SchemeTemplet.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (null != type) {
			criteria.add(Restrictions.eq("eventType", type));
		}
		return criteria.list();
	}
}
