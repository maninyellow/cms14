package com.znsx.cms.persistent.dao.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.SysLogDAO;

/**
 * 用户日志定时保存任务
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-4-10 下午02:02:21
 */
public class SysLogTask {
	@Autowired
	private SysLogDAO sysLogDAO;

	/**
	 * 保存日志到数据库
	 */
	public void saveLog() {
		try {
			sysLogDAO.saveTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
