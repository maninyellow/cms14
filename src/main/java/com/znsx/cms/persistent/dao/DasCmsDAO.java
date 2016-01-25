package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasCms;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasCmsDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:49:18
 */
public interface DasCmsDAO extends BaseDAO<DasCms, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的CMS列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasCms> list) throws BusinessException;

	/**
	 * 查询指定sn列表的CMS最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasCms> listCmsInfo(List<String> sns);
}