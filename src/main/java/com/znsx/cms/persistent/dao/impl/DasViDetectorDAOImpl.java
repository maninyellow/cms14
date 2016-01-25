package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasViDetectorDAO;
import com.znsx.cms.persistent.model.DasViDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasViDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:07:10
 */
public class DasViDetectorDAOImpl extends BaseDAOImpl<DasViDetector, String>
		implements DasViDetectorDAO {

	@Override
	public void batchInsert(List<DasViDetector> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_VID);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

}
