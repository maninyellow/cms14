package com.znsx.cms.persistent.dao;

import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 预置点数据库实现接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:24:36
 */
public interface PresetDAO extends BaseDAO<Preset, String> {
	/**
	 * 删除摄像头预置点
	 * 
	 * @param cameraId
	 *            摄像头ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午11:25:23
	 */
	public void deleteByCamera(String cameraId) throws BusinessException;

	/**
	 * 移除指定摄像头的默认预置点
	 * 
	 * @param cameraId
	 *            摄像头ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:09:39
	 */
	public void removeDefault(String cameraId);
}
