package com.znsx.cms.service.task;

import org.apache.commons.lang.StringUtils;

import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.util.file.Configuration;

/**
 * 下级向上级平台注册的接口线程
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 下午2:29:20
 */
public class PlatformRegisterThread implements Runnable {

	private ConnectManager connectManager;
	private String address;
	/**
	 * 尝试重新注册的次数，超过7次不在发起注册
	 */
	private static int REPEAT_REGISTER_COUNT = 1;

	public PlatformRegisterThread(ConnectManager connectManager) {
		this.connectManager = connectManager;
		this.address = Configuration.getInstance().loadProperties(
				"higher_platform_address");
	}

	@Override
	public void run() {
		// 如果没有上级平台，不需要注册
		if (StringUtils.isBlank(address)) {
			return;
		}
		String code = null;
		try {
			code = connectManager.sendRegister(address);
		} catch (Exception e) {
			e.printStackTrace();
			repeatRegister();
			return;
		}
		// 注册成功，发起资源推送请求
		if (ErrorCode.SUCCESS.equals(code)) {
			Thread t = new Thread(new PushResourceThread(connectManager,
					address), "PushResourceThread");
			t.setDaemon(false);
			t.start();
		}
		// 注册失败尝试重新发起注册
		else {
			repeatRegister();
		}
	}

	/**
	 * 重新注册
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 上午10:38:18
	 */
	private void repeatRegister() {
		if (REPEAT_REGISTER_COUNT < 7) {
			synchronized (this) {
				REPEAT_REGISTER_COUNT++;
				try {
					this.wait(REPEAT_REGISTER_COUNT * 5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread t = new Thread(
						new PlatformRegisterThread(connectManager),
						"RegisterThread-" + REPEAT_REGISTER_COUNT);
				t.setDaemon(false);
				t.start();
			}

		}
	}

}
