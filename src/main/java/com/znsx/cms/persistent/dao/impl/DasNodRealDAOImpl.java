package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasNodRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.persistent.model.DasNodReal;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * DasNodRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:22:10
 */
public class DasNodRealDAOImpl extends BaseDAOImpl<DasNodReal, String>
		implements DasNodRealDAO {

	@Override
	public Map<String, DasNodReal> mapNodBySN(String[] nodSNs) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasNodReal.class);
		criteria.add(Restrictions.in("standardNumber", nodSNs));
		List<DasNodReal> list = criteria.list();
		Map<String, DasNodReal> map = new LinkedHashMap<String, DasNodReal>();
		for (DasNodReal nod : list) {
			map.put(nod.getStandardNumber(), nod);
		}
		return map;
	}

	@Override
	public void deleteAll(List<DasNod> nods) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteBySN(nods, TypeDefinition.TABLE_NAME_NOD));
		for (int i = 0; i < nods.size(); i++) {
			query.setString(i, nods.get(i).getStandardNumber());
		}
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasNodReal> nodReals) {
		Session session = getSessionDas();
		batchInsert(nodReals, session);
	}

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteNodReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

}
