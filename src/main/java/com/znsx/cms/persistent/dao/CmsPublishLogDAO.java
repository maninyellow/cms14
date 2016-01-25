package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.CmsPublishLog;

/**
 * 情报板发布日志记录数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-14 下午3:19:04
 */
public interface CmsPublishLogDAO extends BaseDAO<CmsPublishLog, String> {

	/**
	 * 获取指定SN的情报板最近的发布记录
	 * 
	 * @param standardNumber
	 *            情报板SN
	 * @return 最近的发布记录,如果是发布的播放方案存在多条
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-30 下午7:25:24
	 */
	public List<CmsPublishLog> listLatestRecord(String standardNumber);

	/**
	 * 查询情报板日志记录数量
	 * 
	 * @param cmsSNs
	 *            情报板sn
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 数量
	 */
	public Integer countTotalCMSLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime);

	/**
	 * 查询情报板日志发布信息
	 * 
	 * @param cmsSNs
	 *            情报板sn
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始查询时间
	 * @param endTime
	 *            结束查询时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 情报板发布信息
	 */
	public List<CmsPublishLog> listCmsLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);

	public void batchInsert(List<CmsPublishLog> logs);

	/**
	 * 查询情报板日志记录数量(合并多条)
	 * 
	 * @param cmsSNs
	 *            情报板sn
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 数量
	 */
	public int countCmsPublishLog(String[] cmsSNs, String userName,
			Long beginTime, Long endTime);

	/**
	 * 查询情报板日志发布信息(合并多条)
	 * 
	 * @param cmsSns
	 *            情报板sn
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始查询时间
	 * @param endTime
	 *            结束查询时间
	 * @return 情报板发布信息
	 */
	public List<CmsPublishLog> listCmsPublishLog(String[] cmsSns,
			String userName, Long beginTime, Long endTime);

}
