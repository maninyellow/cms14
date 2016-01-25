package com.znsx.cms.service.comparator;

import java.util.Comparator;

import com.znsx.cms.persistent.model.Camera;

/**
 * CameraComparator
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-3-30 下午8:00:35
 */
public class CameraComparator implements Comparator<Camera> {
	@Override
	public int compare(Camera o1, Camera o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
