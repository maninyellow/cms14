package test.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.dao.MenuOperationDAO;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.RoleManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.ListRoleVO;
import com.znsx.cms.service.model.RoleVO;
import com.znsx.cms.service.model.UserRoleVO;

/**
 * 用户角色业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:13:49
 */
public class RoleManagerTest extends BaseTest {
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private MenuOperationDAO menuOperationDAO;
	@Autowired
	private UserManager userManager;

	private String id;

	@Test(priority = 0)
	public void testCreateRole() {
		id = roleManager.createRole("测试角色", TypeDefinition.ROLE_TYPE_SELF,
				"10010000000000000000", "备注");
	}

	@Test(priority = 1)
	public void testUpdateRole() {
		roleManager.updateRole(id, "XXX角色", null, null, "测试");
	}

	@Test(priority = 2)
	public void testBindRoleResource() {
		roleManager.bindRoleResources(id, "2" ,"[{'resourceType':'2',"
				+ "'resourceId':'10040000000000000001','privilege':'1,2,4'}]");
	}

	@Test(priority = 3)
	public void testBindUserRoles() {
		userManager.bindUserRoles("10020000000000000001", id + "");
	}

	@Test(priority = 4)
	public void testBindRoleMenus() {
		roleManager.bindRoleMenus(id, "1,3");
	}

	@Test(priority = 10)
	public void testListRole() {
		List<ListRoleVO> roles = roleManager.listRole(
				new String[] { "10010000000000000000" }, 0, 100);
		Assert.assertNotNull(roles);
		ListRoleVO role = roles.get(0);
		Assert.assertNotNull(role.getId());
	}

	@Test(priority = 100)
	public void testDeleteRole() {
		roleManager.deleteRole(id);
	}

	@Test(priority = 11)
	public void testListRoleResources() {
		RoleVO role = roleManager.listRoleResources(id, 0, 10);
		Assert.assertNotNull(role);
	}

	@Test(priority = 12)
	public void testListRoleMenus() {
		String rtn = roleManager.listRoleMenus(id);
		Assert.assertTrue(StringUtils.isNotBlank(rtn));
	}

	@Test(priority = 13)
	public void testListUserRole() {
		List<UserRoleVO> list = roleManager
				.listUserRole("10020000000000000001");
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 20)
	public void testListOrganCameraWithPermission() {
		List<DevicePermissionVO> list = roleManager
				.listOrganCameraWithPermission("10010000000000000000", id,
						null, 0, 100);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 21)
	public void testCountOrganCameraWithPermission() {
		int count = roleManager.countOrganCameraWithPermission(
				"10010000000000000000", id, null);
		Assert.assertTrue(count > 0);
	}
}
