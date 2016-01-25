package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasWsRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasWsReal;

/**
 * DasWsRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:24:59
 */
public class DasWsRealDAOImpl extends BaseDAOImpl<DasWsReal, String> implements
		DasWsRealDAO {

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteWsReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasWsReal> wsList) {
		Session session = getSessionDas();
		batchInsert(wsList, session);
	}

}
