package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Monitor;

/**
 * 数据库视频输出接口类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:28:06
 */
public interface MonitorDAO extends BaseDAO<Monitor, String> {

	/**
	 * 
	 * 根据电视墙ID查询视频输出总计数
	 * 
	 * @param wallId
	 *             电视墙ID
	 * @return 视频输出总计数
	 * @author wangbinyu <p />
	 * Create at 2013 下午7:20:39
	 */
	public Integer monitorTotalCount(String wallId);

	/**
	 * 
	 * 根据电视墙ID查询视频输出列表
	 * 
	 * @param wallId
	 *              电视墙ID
	 * @param startIndex
	 *              开始查询条数
	 * @param limit
	 *              需要查询条数
	 * @return 视频输出列表
	 * @author wangbinyu <p />
	 * Create at 2013 下午8:16:48
	 */
	public List<Monitor> listMonitor(String wallId, Integer startIndex,
			Integer limit);

}
