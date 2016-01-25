package com.znsx.cms.service.task;

import com.znsx.cms.service.iface.ConnectManager;

/**
 * 平台资源推送线程
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-18 上午10:48:27
 */
public class PushResourceThread implements Runnable {

	private ConnectManager connectManager;
	private String address;

	public PushResourceThread(ConnectManager connectManager, String address) {
		this.connectManager = connectManager;
		this.address = address;
	}

	@Override
	public void run() {
		try {
			connectManager.pushResource(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
