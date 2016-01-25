package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.DasLil;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasLilDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-9-29 下午1:32:52
 */
public interface DasLilDAO extends BaseDAO<DasLil, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的Lil数据列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-29 下午1:33:30
	 */
	public void batchInsert(List<DasLil> list) throws BusinessException;

	/**
	 * 查询指定sn列表的车道指示灯最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-8 上午10:32:01
	 */
	public List<DasLil> listLilInfo(List<String> sns);
}
