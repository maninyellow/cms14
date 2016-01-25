package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionRoadD
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午11:16:04
 */
public class RoleResourcePermissionRoadD extends RoleResourcePermission {

	private static final long serialVersionUID = 2248076131425597047L;
	private RoadDetector roadDetector;

	public RoadDetector getRoadDetector() {
		return roadDetector;
	}

	public void setRoadDetector(RoadDetector roadDetector) {
		this.roadDetector = roadDetector;
	}

}
