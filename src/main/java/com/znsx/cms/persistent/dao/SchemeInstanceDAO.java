package com.znsx.cms.persistent.dao;

import com.znsx.cms.persistent.model.SchemeInstance;

/**
 * SchemeInstanceDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-30 下午2:19:19
 */
public interface SchemeInstanceDAO extends BaseDAO<SchemeInstance, String> {
	/**
	 * 获取事件预案详情
	 * 
	 * @param eventId
	 *            事件ID
	 * @return 事件预案详情
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 下午8:26:29
	 */
	public SchemeInstance getSchemeInstance(String eventId);
}
