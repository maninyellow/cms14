package com.znsx.cms.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.BoxTransformerDAO;
import com.znsx.cms.persistent.dao.BridgeDetectorDAO;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.DisplayWallDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.MenuOperationDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.ResourceOperationDAO;
import com.znsx.cms.persistent.dao.RoadDetectorDAO;
import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.dao.RoleResourcePermissionDAO;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.UrgentPhoneDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.ViDetectorDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.BoxTransformer;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceIs;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceRail;
import com.znsx.cms.persistent.model.ControlDeviceRd;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.MenuOperation;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.ResourceOperation;
import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionBT;
import com.znsx.cms.persistent.model.RoleResourcePermissionBridgeD;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.persistent.model.RoleResourcePermissionCms;
import com.znsx.cms.persistent.model.RoleResourcePermissionCovi;
import com.znsx.cms.persistent.model.RoleResourcePermissionFan;
import com.znsx.cms.persistent.model.RoleResourcePermissionFd;
import com.znsx.cms.persistent.model.RoleResourcePermissionIs;
import com.znsx.cms.persistent.model.RoleResourcePermissionLight;
import com.znsx.cms.persistent.model.RoleResourcePermissionLoli;
import com.znsx.cms.persistent.model.RoleResourcePermissionNod;
import com.znsx.cms.persistent.model.RoleResourcePermissionPb;
import com.znsx.cms.persistent.model.RoleResourcePermissionRail;
import com.znsx.cms.persistent.model.RoleResourcePermissionRd;
import com.znsx.cms.persistent.model.RoleResourcePermissionRoadD;
import com.znsx.cms.persistent.model.RoleResourcePermissionSubResource;
import com.znsx.cms.persistent.model.RoleResourcePermissionUP;
import com.znsx.cms.persistent.model.RoleResourcePermissionVd;
import com.znsx.cms.persistent.model.RoleResourcePermissionViD;
import com.znsx.cms.persistent.model.RoleResourcePermissionWall;
import com.znsx.cms.persistent.model.RoleResourcePermissionWp;
import com.znsx.cms.persistent.model.RoleResourcePermissionWs;
import com.znsx.cms.persistent.model.RoleResourcePermissionWst;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.RoleManager;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.GetRoleVO;
import com.znsx.cms.service.model.ListRoleVO;
import com.znsx.cms.service.model.ResourceOperationVO;
import com.znsx.cms.service.model.ResourcePermissionVO;
import com.znsx.cms.service.model.RoleVO;
import com.znsx.cms.service.model.UserRoleVO;

/**
 * 用户角色业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:49:46
 */
public class RoleManagerImpl extends BaseManagerImpl implements RoleManager {
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private RoleResourcePermissionDAO roleResourcePermissionDAO;
	@Autowired
	private MenuOperationDAO menuOperationDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ResourceOperationDAO resourceOperationDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private FireDetectorDAO fireDetectorDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private NoDetectorDAO noDetectorDAO;
	@Autowired
	private PushButtonDAO pushButtonDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private WindSpeedDAO wsDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private BoxTransformerDAO boxTransformerDAO;
	@Autowired
	private ViDetectorDAO viDetectorDAO;
	@Autowired
	private RoadDetectorDAO roadDetectorDAO;
	@Autowired
	private BridgeDetectorDAO bridgeDetectorDAO;
	@Autowired
	private UrgentPhoneDAO urgentPhoneDAO;
	@Autowired
	private DisplayWallDAO displayWallDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;

	@Override
	public List<ListRoleVO> listRole(String[] organIds, Integer startIndex,
			Integer limit) throws BusinessException {
		List<Role> roles = roleDAO.listRole(organIds, startIndex, limit);
		List<ListRoleVO> list = new ArrayList<ListRoleVO>();
		for (Role role : roles) {
			ListRoleVO vo = new ListRoleVO();
			vo.setId(role.getId().toString());
			vo.setCreateTime(role.getCreateTime().toString());
			vo.setName(role.getName());
			vo.setNote(role.getNote());
			vo.setOrganId(role.getOrgan().getId());
			vo.setType(role.getType());
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createRole(@LogParam("name") String name, String type,
			String organId, String note) throws BusinessException {
		// 判断名称重复性
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// params.put("organ.id", organId);
		// params.put("name", name);
		// List<Role> roles = roleDAO.findByPropertys(params);
		// params.clear();
		// if (roles.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		Role role = new Role();
		role.setCreateTime(System.currentTimeMillis());
		role.setName(name);
		role.setNote(note);
		role.setType(type);
		role.setOrgan(organDAO.findById(organId));
		roleDAO.save(role);

		// 设置视图权限(暂时默认为所有角色创建的时候都有3个权限)
		bindRoleMenus(role.getId(), "1,2,3");
		return role.getId();
	}

	@Override
	public void updateRole(@LogParam("id") String id,
			@LogParam("name") String name, String type, String organId,
			String note) throws BusinessException {
		// 判断名称重复性
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// params.put("organ.id", organId);
		// params.put("name", name);
		// List<Role> roles = roleDAO.findByPropertys(params);
		// params.clear();
		// if (roles.size() > 0) {
		// if (!roles.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }

		Role role = roleDAO.findById(id);
		if (null != name) {
			role.setName(name);
		}
		if (null != type) {
			role.setType(type);
		}
		if (null != organId) {
			role.setOrgan(organDAO.findById(organId));
		}
		if (null != note) {
			role.setNote(note);
		}
	}

	@Override
	public void deleteRole(@LogParam("id") String id) throws BusinessException {
		// 删除角色关联的资源
		roleResourcePermissionDAO.deleteByRole(id);

		Role role = roleDAO.findById(id);
		// 删除角色自身
		roleDAO.delete(role);
	}

	@Override
	public void bindRoleResources(@LogParam("id") String roleId, String type,
			String json) throws BusinessException {
		// 需要新增加的关联集合
		List<RoleResourcePermission> list = new LinkedList<RoleResourcePermission>();
		// 解析
		try {
			JSONArray array = JSONArray.fromObject(json);
			// 要删除的角色设备权限的设备ID集合
			String[] resourceIds = new String[array.size()];
			if (resourceIds.length <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"resourceId can not be null !");
			}
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				resourceIds[i] = obj.getString("resourceId");
			}
			// 删除以前的关系
			roleResourcePermissionDAO.deleteByRoleResource(roleId, type,
					resourceIds);

			Role role = roleDAO.findById(roleId);

			if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionCamera record = new RoleResourcePermissionCamera();
						record.setRole(role);
						record.setCamera(cameraDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);

					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionCovi record = new RoleResourcePermissionCovi();
						record.setRole(role);
						record.setCovi(coviDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_CMS + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionCms record = new RoleResourcePermissionCms();
						record.setRole(role);
						record.setCms((ControlDeviceCms) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_FAN + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionFan record = new RoleResourcePermissionFan();
						record.setRole(role);
						record.setFan((ControlDeviceFan) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionFd record = new RoleResourcePermissionFd();
						record.setRole(role);
						record.setFd(fireDetectorDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_IS + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionIs record = new RoleResourcePermissionIs();
						record.setRole(role);
						record.setIs((ControlDeviceIs) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_LIGHT + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionLight record = new RoleResourcePermissionLight();
						record.setRole(role);
						record.setLight((ControlDeviceLight) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionLoli record = new RoleResourcePermissionLoli();
						record.setRole(role);
						record.setLoli(loliDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionNod record = new RoleResourcePermissionNod();
						record.setRole(role);
						record.setNod(noDetectorDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionPb record = new RoleResourcePermissionPb();
						record.setRole(role);
						record.setPb(pushButtonDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_RAIL + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionRail record = new RoleResourcePermissionRail();
						record.setRole(role);
						record.setRail((ControlDeviceRail) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_RD + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionRd record = new RoleResourcePermissionRd();
						record.setRole(role);
						record.setRd((ControlDeviceRd) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionVd record = new RoleResourcePermissionVd();
						record.setRole(role);
						record.setVd(vdDAO.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_WP + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionWp record = new RoleResourcePermissionWp();
						record.setRole(role);
						record.setWp((ControlDeviceWp) controlDeviceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionWs record = new RoleResourcePermissionWs();
						record.setRole(role);
						record.setWs(wsDAO.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionWst record = new RoleResourcePermissionWst();
						record.setRole(role);
						record.setWst(wstDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionBT record = new RoleResourcePermissionBT();
						record.setRole(role);
						record.setBoxTransformer(boxTransformerDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionViD record = new RoleResourcePermissionViD();
						record.setRole(role);
						record.setViDetector(viDetectorDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionRoadD record = new RoleResourcePermissionRoadD();
						record.setRole(role);
						record.setRoadDetector(roadDetectorDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionBridgeD record = new RoleResourcePermissionBridgeD();
						record.setRole(role);
						record.setBridgeDetector(bridgeDetectorDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionUP record = new RoleResourcePermissionUP();
						record.setRole(role);
						record.setUrgentPhone(urgentPhoneDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_DISPLAY_WALL + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionWall record = new RoleResourcePermissionWall();
						record.setRole(role);
						record.setDisplayWall(displayWallDAO.findById(obj
								.getString("resourceId")));
						record.setPrivilege("1,2,4");
						roleResourcePermissionDAO.save(record);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "")
					.equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (StringUtils.isNotBlank(obj.getString("privilege"))) {
						RoleResourcePermissionSubResource record = new RoleResourcePermissionSubResource();
						record.setRole(role);
						record.setSubResource(subPlatformResourceDAO
								.findById(obj.getString("resourceId")));
						record.setPrivilege(obj.getString("privilege"));
						roleResourcePermissionDAO.save(record);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter json[" + json + "] invalid !");
		}
	}

	@Override
	public void bindRoleMenus(@LogParam("id") String roleId, String menuIds)
			throws BusinessException {
		Role role = roleDAO.findById(roleId);
		// 移除已存在的关联
		Set<MenuOperation> menus = role.getMenuOperations();
		menus.clear();

		// 关联新的
		if (StringUtils.isNotBlank(menuIds)) {
			String[] array = menuIds.split(",");
			for (int i = 0; i < array.length; i++) {
				try {
					String menuId = array[i];
					MenuOperation menu = menuOperationDAO.findById(menuId);
					menus.add(menu);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.PARAMETER_INVALID,
							"Parameter menuIds[" + menuIds + "] invalid !");
				}
			}
		}
	}

	@Override
	public String listRoleMenus(String roleId) throws BusinessException {
		Role role = roleDAO.findById(roleId);
		Set<MenuOperation> menus = role.getMenuOperations();

		StringBuffer sb = new StringBuffer();

		for (MenuOperation menu : menus) {
			sb.append(menu.getId().toString());
			sb.append(",");
		}
		String rtn = "";
		if (sb.length() > 0) {
			rtn = sb.substring(0, sb.length() - 1);
		}
		return rtn;
	}

	@Override
	public RoleVO listRoleResources(String roleId, int start, int limit)
			throws BusinessException {
		RoleVO vo = new RoleVO();
		Role role = roleDAO.findById(roleId);
		vo.setId(role.getId().toString());
		vo.setName(role.getName());
		vo.setType(role.getType());
		// 只有自定义的类型才需要查询关联表
		if (role.getType().equals(TypeDefinition.ROLE_TYPE_SELF)) {
			List<ResourcePermissionVO> list = roleResourcePermissionDAO
					.listByRoleId(roleId, start, limit);
			vo.setDeviceList(list);
		}
		return vo;
	}

	@Override
	public int countRoleResources(String roleId) throws BusinessException {
		Role role = roleDAO.findById(roleId);
		// 只有自定义的类型才需要查询关联表
		if (role.getType().equals(TypeDefinition.ROLE_TYPE_SELF)) {
			int count = roleResourcePermissionDAO.countByRoleId(roleId);
			return count;
		}
		return 0;
	}

	@Override
	public List<UserRoleVO> listUserRole(String userId)
			throws BusinessException {
		User user = userDAO.findById(userId);
		Set<Role> roles = user.getRoles();
		List<UserRoleVO> list = new ArrayList<UserRoleVO>();
		for (Role role : roles) {
			UserRoleVO vo = new UserRoleVO();
			vo.setId(role.getId().toString());
			vo.setName(role.getName());
			vo.setType(role.getType());
			list.add(vo);
		}
		return list;
	}

	@Override
	public Integer roleTotalCount(String[] organIds) {
		return roleDAO.roleTotalCount(organIds);
	}

	@Override
	public List<DevicePermissionVO> listOrganCameraWithPermission(
			String organId, String roleId, String name, int start, int limit)
			throws BusinessException {
		return roleDAO.listOrganCameraWithPermission(organId, roleId, name,
				start, limit);
	}

	@Override
	public int countOrganCameraWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganCameraWithPermission(organId, roleId, name);
	}

	@Override
	public List<ResourceOperationVO> listResourceOperation(String resourceType) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("resourceType", resourceType);
		List<ResourceOperation> resourceOperations = resourceOperationDAO
				.findByPropertys(params);
		List<ResourceOperationVO> list = new LinkedList<ResourceOperationVO>();
		for (ResourceOperation record : resourceOperations) {
			ResourceOperationVO vo = new ResourceOperationVO();
			vo.setId(record.getOperationCode());
			vo.setName(record.getOperationName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public GetRoleVO getRole(String id) throws BusinessException {
		Role role = roleDAO.findById(id);
		GetRoleVO vo = new GetRoleVO();
		vo.setId(id.toString());
		vo.setCreateTime(role.getCreateTime().toString());
		vo.setName(role.getName());
		vo.setNote(role.getNote());
		vo.setOrganId(role.getOrgan().getId());
		vo.setOrganName(role.getOrgan().getName());
		vo.setType(role.getType());
		return vo;
	}

	@Override
	public void removeRoleResources(String roleId) throws BusinessException {
		roleResourcePermissionDAO.deleteByRole(roleId);
	}

	@Override
	public int countOrganVDWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganVDWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganVDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit)
			throws BusinessException {
		return roleDAO.listOrganVDWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganWSWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganWSWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganWSWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganWSWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganWSTWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganWSTWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganWSTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganWSTWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganLoLiWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganLoLiWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganLoLiWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganLoLiWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganFDWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganFDWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganFDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganFDWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganCoviWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganCoviWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganCoviWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganCoviWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganNODWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganNODWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganNODWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganNODWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganPBWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganPBWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganPBWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganPBWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganControlDeviceWithPermission(String organId,
			String roleId, String name, String type) {
		return roleDAO.countOrganControlDeviceWithPermission(organId, roleId,
				name, type);
	}

	@Override
	public List<DevicePermissionVO> listOrganControlDeviceWithPermission(
			String organId, String roleId, String name, String type,
			int startIndex, int limit) {
		return roleDAO.listOrganControlDeviceWithPermission(organId, roleId,
				name, type, startIndex, limit);
	}

	@Override
	public int countRoleAllResources(String roleId) {
		Role role = roleDAO.findById(roleId);
		// 只有自定义的类型才需要查询关联表
		if (role.getType().equals(TypeDefinition.ROLE_TYPE_SELF)
				|| role.getType().equals(TypeDefinition.ROLE_TYPE_PLATFORM)) {
			int count = roleResourcePermissionDAO
					.countAllResourcesByRoleId(roleId);
			return count;
		}
		return 0;
	}

	@Override
	public RoleVO listRoleAllResources(String roleId, int startIndex, int limit) {
		RoleVO vo = new RoleVO();
		Role role = roleDAO.findById(roleId);
		vo.setId(role.getId().toString());
		vo.setName(role.getName());
		vo.setType(role.getType());
		List<ResourcePermissionVO> deviceList = new ArrayList<ResourcePermissionVO>();
		// 只有自定义的类型才需要查询关联表
		if (role.getType().equals(TypeDefinition.ROLE_TYPE_SELF)
				|| role.getType().equals(TypeDefinition.ROLE_TYPE_PLATFORM)) {
			List<RoleResourcePermission> list = roleDAO.listAllResources(
					roleId, startIndex, limit);
			for (RoleResourcePermission permission : list) {
				ResourcePermissionVO rpVO = new ResourcePermissionVO();
				if (permission instanceof RoleResourcePermissionCamera) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionCamera) permission)
									.getCamera().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_CAMERA + "");
				} else if (permission instanceof RoleResourcePermissionCovi) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionCovi) permission).getCovi()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_COVI + "");
				} else if (permission instanceof RoleResourcePermissionCms) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionCms) permission).getCms()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_CMS + "");
				} else if (permission instanceof RoleResourcePermissionFd) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionFd) permission).getFd()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_FD + "");
				} else if (permission instanceof RoleResourcePermissionFan) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionFan) permission).getFan()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_FAN + "");
				} else if (permission instanceof RoleResourcePermissionIs) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionIs) permission).getIs()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_IS + "");
				} else if (permission instanceof RoleResourcePermissionLight) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionLight) permission)
									.getLight().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_LIGHT + "");
				} else if (permission instanceof RoleResourcePermissionLoli) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionLoli) permission).getLoli()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_LOLI + "");
				} else if (permission instanceof RoleResourcePermissionNod) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionNod) permission).getNod()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_NOD + "");
				} else if (permission instanceof RoleResourcePermissionPb) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionPb) permission).getPb()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_PB + "");
				} else if (permission instanceof RoleResourcePermissionRail) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionRail) permission).getRail()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_RAIL + "");
				} else if (permission instanceof RoleResourcePermissionRd) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionRd) permission).getRd()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_RD + "");
				} else if (permission instanceof RoleResourcePermissionVd) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionVd) permission).getVd()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_VD + "");
				} else if (permission instanceof RoleResourcePermissionWst) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionWst) permission).getWst()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_WST + "");
				} else if (permission instanceof RoleResourcePermissionWp) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionWp) permission).getWp()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_WP + "");
				} else if (permission instanceof RoleResourcePermissionWs) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionWs) permission).getWs()
									.getId(), roleId,
							TypeDefinition.DEVICE_TYPE_WS + "");
				} else if (permission instanceof RoleResourcePermissionBT) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionBT) permission)
									.getBoxTransformer().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_BT + "");
				} else if (permission instanceof RoleResourcePermissionViD) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionViD) permission)
									.getViDetector().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "");
				} else if (permission instanceof RoleResourcePermissionRoadD) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionRoadD) permission)
									.getRoadDetector().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "");
				} else if (permission instanceof RoleResourcePermissionBridgeD) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionBridgeD) permission)
									.getBridgeDetector().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "");
				} else if (permission instanceof RoleResourcePermissionUP) {
					rpVO = roleResourcePermissionDAO.findResourceByRoleId(
							((RoleResourcePermissionUP) permission)
									.getUrgentPhone().getId(), roleId,
							TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "");
				}

				deviceList.add(rpVO);
			}
			vo.setDeviceList(deviceList);
		}
		return vo;
	}

	@Override
	public int countOrganBTWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganBTWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganBTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganBTWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganViDWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganViDWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganViDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		return roleDAO.listOrganViDWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganRoadDWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganRoadDWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganRoadDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganRoadDWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganBridgeDWithPermission(String organId, String roleId,
			String name) {
		return roleDAO.countOrganBridgeDWithPermission(organId, roleId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganBridgeDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganBridgeDWithPermission(organId, roleId, name,
				startIndex, limit);
	}

	@Override
	public int countOrganUrgentPhoneWithPermission(String organId,
			String roleId, String name) {
		return roleDAO.countOrganUrgentPhoneWithPermission(organId, roleId,
				name);
	}

	@Override
	public List<DevicePermissionVO> listOrganUrgentPhoneWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganUrgentPhoneWithPermission(organId, roleId,
				name, startIndex, limit);
	}

	@Override
	public int countOrganDisPlayWallWithPermission(String organId,
			String roleId, String name) {
		return roleDAO.countOrganDisPlayWallWithPermission(organId, roleId,
				name);
	}

	@Override
	public List<DevicePermissionVO> listOrganDisPlayWallWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganDisPlayWallWithPermission(organId, roleId,
				name, startIndex, limit);
	}

	@Override
	public int countOrganSubPlatformWithPermission(String organId,
			String roleId, String name) {
		return roleDAO.countOrganSubPlatformWithPermission(organId, roleId,
				name);
	}

	@Override
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganSubPlatformWithPermission(organId, roleId,
				name, startIndex, limit);
	}

	@Override
	public int countOrganSubPlatformWithPermission1(String organId,
			String roleId, String name) {
		return roleDAO.countOrganSubPlatformWithPermission1(organId, roleId,
				name);
	}

	@Override
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission1(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		return roleDAO.listOrganSubPlatformWithPermission1(organId, roleId,
				name, startIndex, limit);
	}

	@Override
	public void bindRoleResourcesAll(String roleId, String type,
			String organId, String authType) {
		if (type.equals(TypeDefinition.DEVICE_TYPE_CAMERA + "")) {
			List<String> cameras = cameraDAO.listCameraIdInOrgan(organId);
			String[] resourceIds = new String[cameras.size()];
			if (resourceIds.length <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"resourceId can not be null !");
			}
			for (int i = 0; i < cameras.size(); i++) {
				resourceIds[i] = cameras.get(i);
			}
			// 删除以前的关系
			roleResourcePermissionDAO.deleteByRoleResource(roleId, type,
					resourceIds);

			if (!authType.equals("2")) {
				Role role = roleDAO.findById(roleId);
				List<RoleResourcePermission> list = new ArrayList<RoleResourcePermission>();
				for (int i = 0; i < cameras.size(); i++) {
					RoleResourcePermissionCamera record = new RoleResourcePermissionCamera();
					record.setRole(role);
					record.setCamera(cameraDAO.findById(cameras.get(i)));
					if (authType.equals("0")) {
						record.setPrivilege(TypeDefinition.ALL_AUTH);
					} else if (authType.equals("1")) {
						record.setPrivilege(TypeDefinition.SEARCH_AUTH);
					}
					list.add((RoleResourcePermission) record);
				}
				if (list.size() > 0) {
					roleResourcePermissionDAO.batchInsert(list);
				}
			}
		} else if ((TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "").equals(type)) {
			List<SubPlatformResource> resources = subPlatformResourceDAO
					.listSubPlatformResourceByOrganId(organId);
			String[] resourceIds = new String[resources.size()];
			if (resourceIds.length <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"resourceId can not be null !");
			}
			for (int i = 0; i < resources.size(); i++) {
				resourceIds[i] = resources.get(i).getId();
			}
			// 删除以前的关系
			roleResourcePermissionDAO.deleteByRoleResource(roleId, type,
					resourceIds);
			if (!authType.equals("2")) {
				Role role = roleDAO.findById(roleId);
				List<RoleResourcePermission> list = new ArrayList<RoleResourcePermission>();
				for (int i = 0; i < resources.size(); i++) {
					RoleResourcePermissionSubResource record = new RoleResourcePermissionSubResource();
					record.setRole(role);
					record.setSubResource(subPlatformResourceDAO
							.findById(resources.get(i).getId()));
					if (authType.equals("0")) {
						record.setPrivilege(TypeDefinition.ALL_AUTH);
					} else if (authType.equals("1")) {
						record.setPrivilege(TypeDefinition.SEARCH_AUTH);
					}
					list.add((RoleResourcePermission) record);
				}
				if (list.size() > 0) {
					roleResourcePermissionDAO.batchInsert(list);
				}
			}
		}
		// 数据设备..........
	}
}
