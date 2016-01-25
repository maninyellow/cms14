package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasLilRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasLilReal;

/**
 * DasLilRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:20:33
 */
public class DasLilRealDAOImpl extends BaseDAOImpl<DasLilReal, String>
		implements DasLilRealDAO {

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteLilReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasLilReal> lilList) {
		Session session = getSessionDas();
		batchInsert(lilList, session);
	}

}
