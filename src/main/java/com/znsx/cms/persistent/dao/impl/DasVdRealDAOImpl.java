package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasVdRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasVdReal;

/**
 * DasVdRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:24:10
 */
public class DasVdRealDAOImpl extends BaseDAOImpl<DasVdReal, String> implements
		DasVdRealDAO {

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteVdReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasVdReal> vdList) {
		Session session = getSessionDas();
		batchInsert(vdList, session);
	}

}
