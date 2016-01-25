package com.znsx.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.SysLogDAO;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.SysLogManager;

/**
 * SysLogManagerImpl(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:20:03
 */
public class SysLogManagerImpl extends BaseManagerImpl implements SysLogManager {
	@Autowired
	private SysLogDAO sysLogDAO;

	public void log(SysLog record) throws BusinessException {
		sysLogDAO.save(record);
	}

	public void batchLog(SysLog record) throws BusinessException {
		sysLogDAO.batchInsert(record);
	}

	public String getNameByIdAndType(Object id, String type)
			throws BusinessException {
		Object name = sysLogDAO.getNameByIdAndType(id, type);
		return (String) name;
	}

	@Override
	public Object getEntityByIdAndType(Object id, String type)
			throws BusinessException {
		return sysLogDAO.getEntityByIdAndType(id, type);
	}
}
