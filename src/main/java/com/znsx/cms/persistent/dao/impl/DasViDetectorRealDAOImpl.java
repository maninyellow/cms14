package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasViDetectorRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasViDetectorReal;

/**
 * DasViDetectorRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:08:03
 */
public class DasViDetectorRealDAOImpl extends
		BaseDAOImpl<DasViDetectorReal, String> implements DasViDetectorRealDAO {

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteViDetectorReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasViDetectorReal> vidList) {
		Session session = getSessionDas();
		batchInsert(vidList, session);
	}

}
