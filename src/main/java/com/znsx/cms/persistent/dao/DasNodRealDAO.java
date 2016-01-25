package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.persistent.model.DasNodReal;

/**
 * DasNodRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:12:10
 */
public interface DasNodRealDAO extends BaseDAO<DasNodReal, String> {

	/**
	 * 
	 * 根据sn查询氮氧化物集合
	 * 
	 * @param nodSNs
	 *            标准号
	 * @return map
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:03:12
	 */
	public Map<String, DasNodReal> mapNodBySN(String[] nodSNs);

	/**
	 * 
	 * 删除采集时间不一致实时表数据
	 * 
	 * @param nods
	 *            氮氧化物集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:08:13
	 */
	public void deleteAll(List<DasNod> nods);

	/**
	 * 
	 * 批量创建氮氧化物
	 * 
	 * @param nodReals
	 *            氮氧化物实时数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:08:50
	 */
	public void batchInsert(List<DasNodReal> nodReals);

	/**
	 * 
	 * 根据sn删除实时表数据
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:30:39
	 */
	public void deleteBySN(String sn);

}
