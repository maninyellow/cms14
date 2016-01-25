/**
 * 
 */
package com.znsx.cms.service.task;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.file.Configuration;

/**
 * @author znsx
 * 
 */
public class CheckPlatformTask {
	@Autowired
	private ConnectManager connectManager;
	/**
	 * 任务执行的锁，在集群环境下只有获得锁的服务器才会执行该检查会话任务
	 */
	private static final String LOCK = "check_platform_task_lock";
	private static Long cacheTime = Long.valueOf(0l);

	public void checkPlatform() {
		String isPushDevice = Configuration.getInstance().loadProperties(
				"push_device_status");
		if (StringUtils.isNotBlank(isPushDevice)) {
			if (isPushDevice.equals("true")) {
				Long value = null;
				try {
					value = (Long) CacheUtil.getCache(LOCK,
							ConnectManager.REGION);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				if (null == value || cacheTime.longValue() == value.longValue()) {
					cacheTime = Long.valueOf(System.currentTimeMillis());
					System.out
							.println("Get platform check LOCK, set time to : "
									+ cacheTime);
					try {
						CacheUtil.putCache(LOCK, cacheTime,
								ConnectManager.REGION);
					} catch (BusinessException e) {
						e.printStackTrace();
					}
					connectManager.checkPlatform();
				}
			}
		}
	}
}
