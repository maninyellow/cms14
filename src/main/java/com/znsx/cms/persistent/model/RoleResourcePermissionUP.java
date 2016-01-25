package com.znsx.cms.persistent.model;

/**
 * RoleResourcePermissionUP
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:58:00
 */
public class RoleResourcePermissionUP extends RoleResourcePermission {

	private static final long serialVersionUID = -6875088415541227519L;
	private UrgentPhone urgentPhone;

	public UrgentPhone getUrgentPhone() {
		return urgentPhone;
	}

	public void setUrgentPhone(UrgentPhone urgentPhone) {
		this.urgentPhone = urgentPhone;
	}

}
