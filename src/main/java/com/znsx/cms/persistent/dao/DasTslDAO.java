package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasTsl;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasTslDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-9-29 上午11:51:48
 */
public interface DasTslDAO extends BaseDAO<DasTsl, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的Tsl数据列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-29 上午11:52:57
	 */
	public void batchInsert(List<DasTsl> list) throws BusinessException;

	/**
	 * 查询指定sn列表的交通信号灯最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-8 上午10:43:37
	 */
	public List<DasTsl> listTslInfo(List<String> sns);
}
