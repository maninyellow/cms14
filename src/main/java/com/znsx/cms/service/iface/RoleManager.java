package com.znsx.cms.service.iface;

import java.util.List;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.GetRoleVO;
import com.znsx.cms.service.model.ListRoleVO;
import com.znsx.cms.service.model.ResourceOperationVO;
import com.znsx.cms.service.model.RoleVO;
import com.znsx.cms.service.model.UserRoleVO;

/**
 * 用户角色业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午1:48:17
 */
public interface RoleManager extends BaseManager {
	/**
	 * 查询用户角色列表
	 * 
	 * @param organIds
	 *            机构ID集合，用户所属机构及其下级所有机构ID的集合
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 用户角色列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:52:55
	 */
	public List<ListRoleVO> listRole(String[] organIds, Integer startIndex,
			Integer limit) throws BusinessException;

	/**
	 * 创建用户角色
	 * 
	 * @param name
	 *            角色名称
	 * @param type
	 *            角色类型,具体参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_ADMIN}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_JUNIOR}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_SENIOR}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_SELF}
	 * @param organId
	 *            所属机构ID
	 * @param note
	 *            备注
	 * @return 创建成功的角色ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:42:26
	 */
	@LogMethod(targetType = "Role", operationType = "create", name = "创建用户角色", code = "2070")
	public String createRole(@LogParam("name") String name, String type,
			String organId, String note) throws BusinessException;

	/**
	 * 修改用户角色
	 * 
	 * @param id
	 *            要修改的用户角色ID
	 * @param name
	 *            角色名称
	 * @param type
	 *            角色类型,具体参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_ADMIN}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_JUNIOR}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_SENIOR}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#ROLE_TYPE_SELF}
	 * @param organId
	 *            所属机构ID
	 * @param note
	 *            备注
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:43:31
	 */
	@LogMethod(targetType = "Role", operationType = "update", name = "修改用户角色", code = "2071")
	public void updateRole(@LogParam("id") String id,
			@LogParam("name") String name, String type, String organId,
			String note) throws BusinessException;

	/**
	 * 删除用户角色
	 * 
	 * @param id
	 *            用户角色ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:43:00
	 */
	@LogMethod(targetType = "Role", operationType = "delete", name = "删除用户角色", code = "2072")
	public void deleteRole(@LogParam("id") String id) throws BusinessException;

	/**
	 * 设置角色的资源操作权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param type
	 *            设备类型
	 * @param json
	 *            资源权限对象数组.形如:<br />
	 *            [{"resourceType":"1","resourceId":
	 *            "100000000000000000001","privilege":"1,2,4"}]
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:53:48
	 */
	@LogMethod(targetType = "Role", operationType = "bindResource", name = "设置角色的资源操作权限", code = "2074")
	public void bindRoleResources(@LogParam("id") String roleId, String type,
			String json) throws BusinessException;

	/**
	 * 设置角色的视图权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param menuIds
	 *            视图权限ID集合,以逗号间隔开.1-视频模块,2-报警模块,3-地图模块.如:
	 *            “1,3”表示该角色拥有视频模块和地图模块的视图权限.
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:15:04
	 */
	@LogMethod(targetType = "Role", operationType = "bindMenu", name = "设置角色的视图权限", code = "2076")
	public void bindRoleMenus(@LogParam("id") String roleId, String menuIds)
			throws BusinessException;

	/**
	 * 查询角色的视图权限列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 以逗号间隔开的视图权限ID列表,如: "1,2,3"
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:39:13
	 */
	public String listRoleMenus(String roleId) throws BusinessException;

	/**
	 * 
	 * 查询角色的资源操作权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 角色对象,包涵资源操作权限集合
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:57:24
	 */
	public RoleVO listRoleResources(String roleId, int start, int limit)
			throws BusinessException;

	/**
	 * 统计角色的资源操作权限数量
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色的资源操作权限数量
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-24 下午6:35:07
	 */
	public int countRoleResources(String roleId) throws BusinessException;

	/**
	 * 查询用户关联的角色列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:08:30
	 */
	public List<UserRoleVO> listUserRole(String userId)
			throws BusinessException;

	/**
	 * 
	 * 查询角色总计数
	 * 
	 * @param organIds
	 *            机构ID集合
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:15:30
	 */
	public Integer roleTotalCount(String[] organIds);

	/**
	 * 分页查询机构及所有子机构下面的摄像头针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构ID
	 * @param roleId
	 *            角色ID
	 * @param name
	 *            查询条件,设备名称
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 摄像头针对给定角色的权限关系
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-19 上午11:04:31
	 */
	public List<DevicePermissionVO> listOrganCameraWithPermission(
			String organId, String roleId, String name, int start, int limit)
			throws BusinessException;

	/**
	 * 统计机构及所有子机构下面的摄像头数量
	 * 
	 * @param organId
	 *            机构ID
	 * @param roleId
	 *            角色ID
	 * @param name
	 *            查询条件,设备名称
	 * @return 摄像头数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-19 上午11:14:51
	 */
	public int countOrganCameraWithPermission(String organId, String roleId,
			String name);

	/**
	 * 查询给定资源类型的权限操作项列表
	 * 
	 * @param resourceType
	 *            资源类型，具体参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#DEVICE_TYPE_CAMERA}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#DEVICE_TYPE_AI}
	 *            {@link com.znsx.cms.service.common.TypeDefinition#DEVICE_TYPE_AO}
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-19 下午2:39:59
	 */
	public List<ResourceOperationVO> listResourceOperation(String resourceType);

	/**
	 * 根据ID查询角色信息
	 * 
	 * @param id
	 *            角色ID
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-19 下午4:11:54
	 */
	public GetRoleVO getRole(String id) throws BusinessException;

	/**
	 * 移除角色的所有资源权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-20 上午11:15:11
	 */
	@LogMethod(targetType = "Role", operationType = "removeRoleResources", name = "移除角色的所有资源权限", code = "2282")
	public void removeRoleResources(@LogParam("id") String roleId)
			throws BusinessException;

	/**
	 * 
	 * 统计机构及所有子机构下面的车辆检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganVDWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的车辆检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车辆检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganVDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的风速风向检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganWSWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的风速风向检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 风速风向检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganWSWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的气象检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganWSTWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的气象检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 气象检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganWSTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的光强检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganLoLiWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的光强检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 光强检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganLoLiWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的火灾检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganFDWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的火灾检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 火灾检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganFDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的一氧化碳/能见度检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganCoviWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的一氧化碳\能见度检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 一氧化碳\能见度检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganCoviWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的氮氧化合物检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganNODWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的氮氧化合物检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 氮氧化合物检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganNODWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的手动报警按钮数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganPBWithPermission(String organId, String roleId,
			String name);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的手动报警按钮针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 手动报警按钮针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganPBWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 根据type统计机构及所有子机构下面的控制设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:00:24
	 */
	public int countOrganControlDeviceWithPermission(String organId,
			String roleId, String name, String type);

	/**
	 * 
	 * 分页查询机构及所有子机构下面的控制设备针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param type
	 *            控制设备类型
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 控制设备针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:41:28
	 */
	public List<DevicePermissionVO> listOrganControlDeviceWithPermission(
			String organId, String roleId, String name, String type,
			int startIndex, int limit);

	/**
	 * 统计角色的所有资源操作权限数量
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色的资源操作权限数量
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-24 下午6:35:07
	 */
	public int countRoleAllResources(String roleId);

	/**
	 * 
	 * 查询角色的所有资源操作权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 角色对象,包涵资源操作权限集合
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:57:24
	 */
	public RoleVO listRoleAllResources(String roleId, int startIndex, int limit);

	/**
	 * 根据type统计机构及所有子机构下面的箱变电力监控数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:53:47
	 */
	public int countOrganBTWithPermission(String organId, String roleId,
			String name);

	/**
	 * 分页查询机构及所有子机构下面的箱变电力监控针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 箱变电力监控针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:57:54
	 */
	public List<DevicePermissionVO> listOrganBTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 根据type统计机构及所有子机构下面的能见度仪数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:33:06
	 */
	public int countOrganViDWithPermission(String organId, String roleId,
			String name);

	/**
	 * 分页查询机构及所有子机构下面的能见度仪针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 能见度仪针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:35:31
	 */
	public List<DevicePermissionVO> listOrganViDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 根据type统计机构及所有子机构下面的路面检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:45:12
	 */
	public int countOrganRoadDWithPermission(String organId, String roleId,
			String name);

	/**
	 * 分页查询机构及所有子机构下面的路面检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 路面检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:50:12
	 */
	public List<DevicePermissionVO> listOrganRoadDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 根据type统计机构及所有子机构下面的桥面检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午12:00:20
	 */
	public int countOrganBridgeDWithPermission(String organId, String roleId,
			String name);

	/**
	 * 分页查询机构及所有子机构下面的桥面检测器针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 桥面检测器针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:46:56
	 */
	public List<DevicePermissionVO> listOrganBridgeDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 根据type统计机构及所有子机构下面的紧急电话数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:21:37
	 */
	public int countOrganUrgentPhoneWithPermission(String organId,
			String roleId, String name);

	/**
	 * 分页查询机构及所有子机构下面的紧急电话针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 设备列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:22:21
	 */
	public List<DevicePermissionVO> listOrganUrgentPhoneWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 根据type统计机构及所有子机构下面的电视墙数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @return 数量
	 */
	public int countOrganDisPlayWallWithPermission(String organId,
			String roleId, String name);

	/**
	 * 分页查询机构及所有子机构下面的电视墙针对给定角色的权限关系
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            电视墙名称
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return 设备列表
	 */
	public List<DevicePermissionVO> listOrganDisPlayWallWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 统计外平台设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @return 数量
	 */
	public int countOrganSubPlatformWithPermission(String organId,
			String roleId, String name);

	/**
	 * 
	 * 查询外平台设备列表
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 外平台设备列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:06:11
	 */
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 条件查询下级平台设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @return 下级平台资源数量
	 */
	public int countOrganSubPlatformWithPermission1(String organId,
			String roleId, String name);

	/**
	 * 条件查询下级平台资源
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 下级平台资源
	 */
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission1(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 根据SN查询该机构下的设备，绑定角色和设备的关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param type
	 *            类型
	 * @param organId
	 *            机构id
	 * @param authType
	 *          0：授权当前机构下设备的所有权限，1：授权设备的查看权限 ,2：取消当前机构下设备权限
	 */
	public void bindRoleResourcesAll(String roleId, String type,
			String organId,String authType);
}
