package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Event;

/**
 * EventDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:34:46
 */
public interface EventDAO extends BaseDAO<Event, String> {

	/**
	 * 根据机构数组查询件事
	 * 
	 * @param organs
	 *            机构id数组
	 * @return 事件
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:36:06
	 */
	public List<Event> listEventByOrganId(String[] organs, Long beginTime,
			Long endTime, Short type, Short status);

}
