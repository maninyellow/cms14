package com.znsx.cms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.RoleManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.GetRoleVO;
import com.znsx.cms.service.model.ListRoleVO;
import com.znsx.cms.service.model.ResourceOperationVO;
import com.znsx.cms.service.model.RoleVO;
import com.znsx.cms.service.model.UserRoleVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.GetRoleDTO;
import com.znsx.cms.web.dto.omc.ListOrganAllDeviceDTO;
import com.znsx.cms.web.dto.omc.ListRoleDTO;
import com.znsx.cms.web.dto.omc.ListRoleMenusDTO;
import com.znsx.cms.web.dto.omc.ListRoleResourcesDTO;
import com.znsx.cms.web.dto.omc.ListUserRoleDTO;
import com.znsx.util.request.SimpleRequestReader;

/**
 * 用户角色对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:51:37
 */
@Controller
public class RoleController extends BaseController {
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private OrganManager organManager;

	@InterfaceDescription(logon = true, method = "Create_Role", cmd = "2070")
	@RequestMapping("/create_role.json")
	public void createRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			type = TypeDefinition.ROLE_TYPE_SELF;
		}

		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String note = request.getParameter("note");

		String id = roleManager.createRole(name, type, organId, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2070");
		dto.setMethod("Create_Role");
		dto.setMessage(id.toString());

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Role", cmd = "2071")
	@RequestMapping("/update_role.json")
	public void updateRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String type = request.getParameter("type");

		String organId = request.getParameter("organId");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		String note = request.getParameter("note");

		roleManager.updateRole(id, name, type, organId, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2071");
		dto.setMethod("Update_Role");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Role", cmd = "2072")
	@RequestMapping("/delete_role.json")
	public void deleteRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		roleManager.deleteRole(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2072");
		dto.setMethod("Delete_Role");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Role", cmd = "2073")
	@RequestMapping("/list_role.json")
	public void listRole(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");

		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		if (StringUtils.isBlank(organId)) {
			organId = resource.get().getOrganId();
		}
		// 目前只查询本级机构的角色，不过跨机构授权的功能
		// String[] organIds = organManager.listOrganAllChildren(organId);
		String[] organIds = new String[] { organId };

		Integer totalCount = roleManager.roleTotalCount(organIds);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<ListRoleVO> roleList = roleManager.listRole(organIds, startIndex,
				limit);

		ListRoleDTO dto = new ListRoleDTO();
		dto.setCmd("2073");
		dto.setMethod("List_Role");
		dto.setTotalCount(totalCount + "");
		dto.setRoleList(roleList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_Role_Resources", cmd = "2074")
	@RequestMapping("/bind_role_resources.json")
	public void bindRoleResoures(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [type]");
		}

		String json = request.getParameter("json");
		if (StringUtils.isBlank(json)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [json]");
		}

		roleManager.bindRoleResources(roleId, type, json);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2074");
		dto.setMethod("Bind_Role_Resources");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_User_Roles", cmd = "2075")
	@RequestMapping("/bind_user_roles.json")
	public void bindUserRoles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userId]");
		}

		String roleIds = request.getParameter("roleIds");

		userManager.bindUserRoles(userId, roleIds);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2075");
		dto.setMethod("Bind_User_Roles");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_Role_Menus", cmd = "2076")
	@RequestMapping("/bind_role_menus.json")
	public void bindRoleMenus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		String menuIds = request.getParameter("menuIds");

		roleManager.bindRoleMenus(roleId, menuIds);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2076");
		dto.setMethod("Bind_Role_Menus");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Role_Menus", cmd = "2077")
	@RequestMapping("/list_role_menus.json")
	public void listRoleMenus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		String menuIds = roleManager.listRoleMenus(roleId);

		ListRoleMenusDTO dto = new ListRoleMenusDTO();
		dto.setCmd("2077");
		dto.setMethod("List_Role_Menus");
		dto.setMenuIds(menuIds);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Role_Resources", cmd = "2078")
	@RequestMapping("/list_role_resources.json")
	public void listRoleResources(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		int startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}

		int limit = 10;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		int totalCount = roleManager.countRoleResources(roleId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount != 0) {
			if (startIndex >= totalCount) {
				startIndex -= ((startIndex - totalCount) / limit + 1) * limit;
			}
		}
		RoleVO role = roleManager.listRoleResources(roleId, startIndex, limit);

		ListRoleResourcesDTO dto = new ListRoleResourcesDTO();
		dto.setCmd("2078");
		dto.setMethod("List_Role_Resources");
		dto.setTotalCount(totalCount + "");
		dto.setRole(role);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_User_Roles", cmd = "2079")
	@RequestMapping("/list_user_roles.json")
	public void listUserRoles(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userId]");
		}

		List<UserRoleVO> roleList = roleManager.listUserRole(userId);

		ListUserRoleDTO dto = new ListUserRoleDTO();
		dto.setCmd("2079");
		dto.setMethod("List_User_Roles");
		dto.setRoleList(roleList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_All_Device", cmd = "2280")
	@RequestMapping("/list_all_device.json")
	public void listAllDeviceWithPermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [type]");
		}

		String name = request.getParameter("name");

		int startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}

		int limit = 10;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		List<DevicePermissionVO> deviceList = null;
		int totalCount = 0;
		if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
			totalCount = roleManager.countOrganCameraWithPermission(organId,
					roleId, name);
			deviceList = roleManager.listOrganCameraWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
			totalCount = roleManager.countOrganVDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganVDWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(type)) {
			totalCount = roleManager.countOrganWSWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganWSWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(type)) {
			totalCount = roleManager.countOrganWSTWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganWSTWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(type)) {
			totalCount = roleManager.countOrganLoLiWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganLoLiWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(type)) {
			totalCount = roleManager.countOrganFDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganFDWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(type)) {
			totalCount = roleManager.countOrganCoviWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganCoviWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(type)) {
			totalCount = roleManager.countOrganNODWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganNODWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(type)) {
			totalCount = roleManager.countOrganPBWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganPBWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(type)) {
			totalCount = roleManager.countOrganBTWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganBTWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "").equals(type)) {
			totalCount = roleManager.countOrganViDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganViDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "").equals(type)) {
			totalCount = roleManager.countOrganRoadDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganRoadDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
				.equals(type)) {
			totalCount = roleManager.countOrganBridgeDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganBridgeDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
				.equals(type)) {
			totalCount = roleManager.countOrganUrgentPhoneWithPermission(
					organId, roleId, name);

			deviceList = roleManager.listOrganUrgentPhoneWithPermission(
					organId, roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_DISPLAY_WALL + "").equals(type)) {
			totalCount = roleManager.countOrganDisPlayWallWithPermission(
					organId, roleId, name);

			deviceList = roleManager.listOrganDisPlayWallWithPermission(
					organId, roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "").equals(type)) {
			totalCount = roleManager.countOrganSubPlatformWithPermission(
					organId, roleId, name);

			deviceList = roleManager.listOrganSubPlatformWithPermission(
					organId, roleId, name, startIndex, limit);
		} else {
			totalCount = roleManager.countOrganControlDeviceWithPermission(
					organId, roleId, name, type);

			deviceList = roleManager.listOrganControlDeviceWithPermission(
					organId, roleId, name, type, startIndex, limit);
		}

		List<ResourceOperationVO> permissionList = roleManager
				.listResourceOperation(type);

		ListOrganAllDeviceDTO dto = new ListOrganAllDeviceDTO();
		dto.setCmd("2280");
		dto.setMethod("List_All_Device");
		dto.setPermissionList(permissionList);
		dto.setTotalCount(totalCount + "");
		dto.setDeviceList(deviceList);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Role", cmd = "2281")
	@RequestMapping("/get_role.json")
	public void getRole(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		GetRoleVO role = roleManager.getRole(roleId);

		GetRoleDTO dto = new GetRoleDTO();
		dto.setRole(role);
		dto.setCmd("2281");
		dto.setMethod("Get_Role");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Remove_Role_Resources", cmd = "2282")
	@RequestMapping("/remove_role_resources.json")
	public void removeRoleResources(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		roleManager.removeRoleResources(roleId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2282");
		dto.setMethod("Remove_Role_Resources");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Role_All_Resources", cmd = "2283")
	@RequestMapping("/list_role_all_resources.json")
	public void listRoleAllResources(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(roleId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		int startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}

		int limit = 10;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		int totalCount = roleManager.countRoleAllResources(roleId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount != 0) {
			if (startIndex >= totalCount) {
				startIndex -= ((startIndex - totalCount) / limit + 1) * limit;
			}
		}
		RoleVO role = roleManager.listRoleAllResources(roleId, startIndex,
				limit);

		ListRoleResourcesDTO dto = new ListRoleResourcesDTO();
		dto.setCmd("2283");
		dto.setMethod("List_Role_All_Resources");
		dto.setTotalCount(totalCount + "");
		dto.setRole(role);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_All_Device_By_SN", cmd = "2280")
	@RequestMapping("/list_all_device_by_sn.json")
	public void listAllDeviceWithPermissionBySN(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String standardNumber = reader.getString("standardNumber", false);
		String roleId = reader.getString("roleId", false);
		String type = reader.getString("type", false);
		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		int startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}

		int limit = 10;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		List<DevicePermissionVO> deviceList = null;
		String organId = organManager.getOrganIdBySN(standardNumber);
		int totalCount = 0;
		if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
			totalCount = roleManager.countOrganCameraWithPermission(organId,
					roleId, name);
			deviceList = roleManager.listOrganCameraWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
			totalCount = roleManager.countOrganVDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganVDWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(type)) {
			totalCount = roleManager.countOrganWSWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganWSWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(type)) {
			totalCount = roleManager.countOrganWSTWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganWSTWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(type)) {
			totalCount = roleManager.countOrganLoLiWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganLoLiWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(type)) {
			totalCount = roleManager.countOrganFDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganFDWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(type)) {
			totalCount = roleManager.countOrganCoviWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganCoviWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(type)) {
			totalCount = roleManager.countOrganNODWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganNODWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(type)) {
			totalCount = roleManager.countOrganPBWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganPBWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(type)) {
			totalCount = roleManager.countOrganBTWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganBTWithPermission(organId, roleId,
					name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "").equals(type)) {
			totalCount = roleManager.countOrganViDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganViDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "").equals(type)) {
			totalCount = roleManager.countOrganRoadDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganRoadDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
				.equals(type)) {
			totalCount = roleManager.countOrganBridgeDWithPermission(organId,
					roleId, name);

			deviceList = roleManager.listOrganBridgeDWithPermission(organId,
					roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
				.equals(type)) {
			totalCount = roleManager.countOrganUrgentPhoneWithPermission(
					organId, roleId, name);

			deviceList = roleManager.listOrganUrgentPhoneWithPermission(
					organId, roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_DISPLAY_WALL + "").equals(type)) {
			totalCount = roleManager.countOrganDisPlayWallWithPermission(
					organId, roleId, name);

			deviceList = roleManager.listOrganDisPlayWallWithPermission(
					organId, roleId, name, startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "").equals(type)) {
			totalCount = roleManager.countOrganSubPlatformWithPermission1(
					organId, roleId, name);

			deviceList = roleManager.listOrganSubPlatformWithPermission1(
					organId, roleId, name, startIndex, limit);
		} else {
			totalCount = roleManager.countOrganControlDeviceWithPermission(
					organId, roleId, name, type);

			deviceList = roleManager.listOrganControlDeviceWithPermission(
					organId, roleId, name, type, startIndex, limit);
		}

		List<ResourceOperationVO> permissionList = roleManager
				.listResourceOperation(type);

		ListOrganAllDeviceDTO dto = new ListOrganAllDeviceDTO();
		dto.setCmd("2280");
		dto.setMethod("List_All_Device");
		dto.setPermissionList(permissionList);
		dto.setTotalCount(totalCount + "");
		dto.setDeviceList(deviceList);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_Role_Resources_By_Organ", cmd = "2074")
	@RequestMapping("/bind_role_resources_by_organ.json")
	public void bindRoleResouresAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String roleId = reader.getString("roleId", false);
		String type = reader.getString("type", false);
		String standardNumber = reader.getString("standardNumber", false);
		String organId = organManager.getOrganIdBySN(standardNumber);
		String authType = reader.getString("authType", false);
		roleManager.bindRoleResourcesAll(roleId, type, organId, authType);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2074");
		dto.setMethod("Bind_Role_Resources");

		writePage(response, dto);
	}
}
