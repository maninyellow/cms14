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

import com.znsx.cms.persistent.dao.DasLoliRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasLoliReal;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * DasLoliRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:21:21
 */
public class DasLoliRealDAOImpl extends BaseDAOImpl<DasLoliReal, String>
		implements DasLoliRealDAO {

	@Override
	public void deleteAll(String table) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance().delete(
				table));
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasLoliReal> loliList) {
		// TODO Auto-generated method stub
		Session session = getSessionDas();
		batchInsert(loliList, session);
	}

	@Override
	public Map<String, DasLoliReal> mapLoliBySN(String[] loliSNs) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasLoliReal.class);
		criteria.add(Restrictions.in("standardNumber", loliSNs));
		List<DasLoliReal> list = criteria.list();
		Map<String, DasLoliReal> map = new LinkedHashMap<String, DasLoliReal>();
		for (DasLoliReal loli : list) {
			map.put(loli.getStandardNumber(), loli);
		}
		return map;
	}

	@Override
	public void deleteAll(List<DasLoli> lolis) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteBySN(lolis, TypeDefinition.TABLE_NAME_LOLI));
		for (int i = 0; i < lolis.size(); i++) {
			query.setString(i, lolis.get(i).getStandardNumber());
		}
		query.executeUpdate();
	}

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteLoliReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();

	}

}
