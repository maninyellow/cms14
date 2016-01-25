package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasCmsRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasCmsReal;

/**
 * DasCmsRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:16:37
 */
public class DasCmsRealDAOImpl extends BaseDAOImpl<DasCmsReal, String>
		implements DasCmsRealDAO {

	@Override
	public void deleteAll(String table) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance().delete(
				table));
		query.executeUpdate();

	}

	@Override
	public void batchInsert(List<DasCmsReal> cmsList) {
		Session session = getSessionDas();
		batchInsert(cmsList, session);
	}

	@Override
	public void deleteBySN(String standardNumber) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteCmsReal(standardNumber));
		if (StringUtils.isNotBlank(standardNumber)) {
			query.setString(0, standardNumber);
		}
		query.executeUpdate();
	}

	@Override
	public void deleteCms(String[] cmsIds) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteCmsRealByCmsIds(cmsIds));
		int i = 0;
		if (cmsIds.length > 0) {
			for (String id : cmsIds) {
				query.setString(i, id);
				i++;
			}
		}
		query.executeUpdate();
	}

}
