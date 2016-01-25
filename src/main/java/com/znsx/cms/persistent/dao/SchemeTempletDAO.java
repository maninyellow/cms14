package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SchemeTemplet;

/**
 * SchemeTempletDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午3:30:06
 */
public interface SchemeTempletDAO extends BaseDAO<SchemeTemplet, String> {
	/**
	 * 查询预案库列表
	 * 
	 * @param organId
	 *            所属机构ID
	 * @param type
	 *            事件类型
	 * @return 预案库列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 下午5:08:23
	 */
	public List<SchemeTemplet> listSchemeTemplet(String organId, Short type);
}
