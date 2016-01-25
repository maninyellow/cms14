package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.VisitRecord;

/**
 * VisitRecordDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午9:41:35
 */
public interface VisitRecordDAO extends BaseDAO<VisitRecord, String> {

	/**
	 * 查询来电来访记录
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
	 * @return 来电来访记录列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 上午11:31:04
	 */
	public List<VisitRecord> listVisitRecord(String userName, Timestamp begin,
			Timestamp end, int start, int limit);

	/**
	 * 统计满足条件的来电来访记录条数
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 满足条件的来电来访记录条数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 上午11:32:53
	 */
	public int countVisitRecord(String userName, Timestamp begin, Timestamp end);
}
