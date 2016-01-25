package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasViDetector;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasViDetectorDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午9:56:16
 */
public interface DasViDetectorDAO extends BaseDAO<DasViDetector, String> {

	/**
	 * 
	 * 批量插入能见度仪历史表
	 * 
	 * @param listVidHistory
	 *            能见度仪列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:45:11
	 */
	public void batchInsert(List<DasViDetector> listVidHistory)
			throws BusinessException;

}
