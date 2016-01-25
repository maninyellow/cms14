package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.CenterWorkRecord;

/**
 * CenterWorkRecordDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午9:42:18
 */
public interface CenterWorkRecordDAO extends BaseDAO<CenterWorkRecord, String> {
	/**
	 * 查询中心当班情况记录
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 中心当班情况记录列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午2:59:18
	 */
	public List<CenterWorkRecord> listVisitRecord(String userName,
			Timestamp begin, Timestamp end, int start, int limit);

	/**
	 * 统计满足条件的中心当班情况记录
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 满足条件的中心当班情况记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午3:00:49
	 */
	public int countVisitRecord(String userName, Timestamp begin, Timestamp end);
}
