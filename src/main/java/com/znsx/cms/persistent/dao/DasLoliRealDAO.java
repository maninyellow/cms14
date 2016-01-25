package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasLoliReal;

/**
 * DasLoliRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:11:33
 */
public interface DasLoliRealDAO extends BaseDAO<DasLoliReal, String> {

	/**
	 * 
	 * 删除光强实时表
	 * 
	 * @param table
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:01:57
	 */
	public void deleteAll(String table);

	/**
	 * 
	 * 批量创建实时表数据
	 * 
	 * @param loliList
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:02:46
	 */
	public void batchInsert(List<DasLoliReal> loliList);

	/**
	 * 
	 * 根据sn查询光强集合
	 * 
	 * @param loliSNs
	 *            标准号
	 * @return map
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:23:45
	 */
	public Map<String, DasLoliReal> mapLoliBySN(String[] loliSNs);

	/**
	 * 
	 * 删除光强时间变化实时表数据
	 * 
	 * @param lolis
	 *            光强检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:34:55
	 */
	public void deleteAll(List<DasLoli> lolis);

	/**
	 * 
	 * 根据sn删除实时表
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:37:05
	 */
	public void deleteBySN(String sn);

}
