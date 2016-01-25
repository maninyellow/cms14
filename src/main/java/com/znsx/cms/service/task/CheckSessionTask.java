package com.znsx.cms.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.util.database.CacheUtil;

/**
 * 定时检查用户会话任务
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午5:11:30
 */
public class CheckSessionTask {
	@Autowired
	private UserManager userManager;
	/**
	 * 任务执行的锁，在集群环境下只有获得锁的服务器才会执行该检查会话任务
	 */
	private static final String LOCK = "check_session_task_lock";
	private static Long cacheTime = Long.valueOf(0l);

	public void checkSession() {
		Long value = null;
		try {
			value = (Long) CacheUtil.getCache(LOCK, UserManager.REGION);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if (null == value || cacheTime.longValue() == value.longValue()) {
			cacheTime = Long.valueOf(System.currentTimeMillis());
			System.out.println("Get session check LOCK, set time to : "
					+ cacheTime);
			try {
				CacheUtil.putCache(LOCK, cacheTime, UserManager.REGION);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			userManager.regularSessionCheck();
		}
	}
}
