package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 
 * @author huangbuji
 *         <p />
 *         2014-12-3 上午9:58:34
 */
public interface WorkPlanDAO extends BaseDAO<WorkPlan, String> {
	public void batchInsert(List<WorkPlan> list) throws BusinessException;

	/**
	 * 获取当日的值班计划
	 * 
	 * @param type
	 *            0-交警，1-路政
	 * @param today
	 *            当日时间
	 * @author huangbuji
	 *         <p />
	 *         2014-12-9 上午9:38:10
	 */
	public WorkPlan getCurrentWorkPlan(String type, Timestamp today);

	/**
	 * 根据类型删除值班计划
	 * 
	 * @param type
	 *            0-交警，1-路政
	 * @author huangbuji
	 *         <p />
	 *         2014-12-11 下午4:00:19
	 */
	public void deleteByType(String type);
}
