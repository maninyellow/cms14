package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.Classes;

/**
 * ClassesDAO
 * 
 * @author sjt
 *         <p />
 *         Create at 2015 上午9:41:35
 */
public interface ClassesDAO extends BaseDAO<Classes, String> {

	/**
	 * 查询值班班次
	 * 
	 * @param userName
	 *            班次人员
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param start
	 *            要查询的开始行数
	 * @param limit
	 *            要查询的行数
	 * @return 值班班次列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午11:31:04
	 */
	public List<Classes> listClasses(String userName, Long beginTime,
			Long endTime, int start, int limit);

	/**
	 * 统计满足条件的班次条数
	 * 
	 * @param userName
	 *            班次人员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 满足条件的班次条数
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午11:32:53
	 */
	public int countClasses(String userName, Long begin, Long end);

	/**
	 * 
	 * 查询数据库最后一条数据
	 * 
	 * @return 班次
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-24 下午5:22:15
	 */
	public Classes getClasses();

}
