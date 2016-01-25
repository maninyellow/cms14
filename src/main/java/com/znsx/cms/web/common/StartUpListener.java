package com.znsx.cms.web.common;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.znsx.cms.service.iface.PlatformServerManager;

/**
 * web应用启动初始化类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-2-10 上午10:53:25
 */
public class StartUpListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		try {
			PlatformServerManager platformServerManager = (PlatformServerManager) getCurrentWebApplicationContext()
					.getBean("platformServerManager");

			platformServerManager.writeHardwareInfoToDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}
