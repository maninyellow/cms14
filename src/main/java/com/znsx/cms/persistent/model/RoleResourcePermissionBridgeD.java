package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionBridgeD
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:20:12
 */
public class RoleResourcePermissionBridgeD extends RoleResourcePermission {

	private static final long serialVersionUID = -947337709388471509L;
	private BridgeDetector bridgeDetector;

	public BridgeDetector getBridgeDetector() {
		return bridgeDetector;
	}

	public void setBridgeDetector(BridgeDetector bridgeDetector) {
		this.bridgeDetector = bridgeDetector;
	}
}
