package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 角色资源权限实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:00:44
 */
public class RoleResourcePermission implements Serializable {
	private static final long serialVersionUID = 5620079723269095186L;
	private String id;
	private Role role;
	private String privilege;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
