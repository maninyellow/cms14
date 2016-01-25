package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasRoadDetectorRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasRoadDetectorReal;

/**
 * DasRoadDetectorRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:09:58
 */
public class DasRoadDetectorRealDAOImpl extends
		BaseDAOImpl<DasRoadDetectorReal, String> implements
		DasRoadDetectorRealDAO {

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteRdReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();

	}

	@Override
	public void batchInsert(List<DasRoadDetectorReal> rdList) {
		Session session = getSessionDas();
		batchInsert(rdList, session);
	}

}
