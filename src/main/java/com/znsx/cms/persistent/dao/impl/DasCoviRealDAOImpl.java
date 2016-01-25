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

import com.znsx.cms.persistent.dao.DasCoviRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasCoviReal;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * DasCoviRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:18:38
 */
public class DasCoviRealDAOImpl extends BaseDAOImpl<DasCoviReal, String>
		implements DasCoviRealDAO {

	@Override
	public void batchInsert(List<DasCoviReal> coviList) {
		Session session = getSessionDas();
		batchInsert(coviList, session);

	}

	@Override
	public Map<String, DasCoviReal> mapCoviBySN(String[] coviSNs) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasCoviReal.class);
		criteria.add(Restrictions.in("standardNumber", coviSNs));
		List<DasCoviReal> list = criteria.list();
		Map<String, DasCoviReal> map = new LinkedHashMap<String, DasCoviReal>();
		for (DasCoviReal covi : list) {
			map.put(covi.getStandardNumber(), covi);
		}
		return map;
	}

	@Override
	public void deleteAll(List<DasCovi> covis) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteBySN(covis, TypeDefinition.TABLE_NAME_COVI));
		for (int i = 0; i < covis.size(); i++) {
			query.setString(i, covis.get(i).getStandardNumber());
		}
		query.executeUpdate();
	}

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteCoviReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

}
