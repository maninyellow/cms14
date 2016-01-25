package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.DasCmsReal;
import com.znsx.cms.persistent.model.DasControlDeviceReal;
import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasCoviReal;
import com.znsx.cms.persistent.model.DasWstReal;

/**
 * DasCoviRealDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:08:44
 */
public interface DasCoviRealDAO extends BaseDAO<DasCoviReal, String> {

	/**
	 * 
	 * 批量创建covi
	 * 
	 * @param coviList
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:49:08
	 */
	public void batchInsert(List<DasCoviReal> coviList);

	/**
	 * 
	 * 根据sn查询covi实时数据
	 * 
	 * @param coviSNs
	 *            covi标准号
	 * @return coviMap
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:57:46
	 */
	public Map<String, DasCoviReal> mapCoviBySN(String[] coviSNs);

	/**
	 * 
	 * 删除实时数据
	 * 
	 * @param covis
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:35:58
	 */
	public void deleteAll(List<DasCovi> covis);

	/**
	 * 
	 * 根据sn删除实时数据
	 * 
	 * @param sn
	 *            标准号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:16:51
	 */
	public void deleteBySN(String sn);

}
