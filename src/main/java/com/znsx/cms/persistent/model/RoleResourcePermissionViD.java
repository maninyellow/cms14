package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionViD
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:14:55
 */
public class RoleResourcePermissionViD extends RoleResourcePermission {

	private static final long serialVersionUID = -321059165848287281L;
	private ViDetector viDetector;

	public ViDetector getViDetector() {
		return viDetector;
	}

	public void setViDetector(ViDetector viDetector) {
		this.viDetector = viDetector;
	}

}
