package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasGpsRealDAO;
import com.znsx.cms.persistent.model.DasGpsReal;

/**
 * DasGpsRealDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 下午2:48:19
 */
public class DasGpsRealDAOImpl extends BaseDAOImpl<DasGpsReal, String>
		implements DasGpsRealDAO {
	@Override
	public void batchInsert(List<DasGpsReal> gpsList) {
		Session session = getSessionDas();
		batchInsert(gpsList, session);
	}
}
