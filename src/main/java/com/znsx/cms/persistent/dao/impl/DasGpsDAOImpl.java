package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasGpsDAO;
import com.znsx.cms.persistent.model.DasGps;
import com.znsx.cms.service.common.TypeDefinition;

/**
 * DasGpsDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-31 上午11:54:56
 */
public class DasGpsDAOImpl extends BaseDAOImpl<DasGps, String> implements
		DasGpsDAO {
	@Override
	public void batchInsert(List<DasGps> listGpsHistory) {
		checkTablePartition(TypeDefinition.TABLE_NAME_GPS);
		Session session = getSessionDas();
		batchInsert(listGpsHistory, session);
	}
}
