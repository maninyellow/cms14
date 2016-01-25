package com.znsx.cms.persistent.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.PresetDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 预置点数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:26:04
 */
public class PresetDAOImpl extends BaseDAOImpl<Preset, String> implements
		PresetDAO {
	@Override
	public void deleteByCamera(String cameraId) throws BusinessException {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.deletePresetByCamera());
		query.setString(0, cameraId);
		query.executeUpdate();
	}

	@Override
	public void removeDefault(String cameraId) {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.removeCameraDefaultPreset());
		query.setString(0, cameraId);
		query.executeUpdate();
	}
}
