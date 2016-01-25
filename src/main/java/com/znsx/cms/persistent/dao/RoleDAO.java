package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * 用户角色数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:18:27
 */
public interface RoleDAO extends BaseDAO<Role, String> {
	/**
	 * 查询用户角色列表
	 * 
	 * @param organIds
	 *            查询机构及其下级所有机构的ID集合,不能为空至少得有一个ID存在
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
	public List<Role> listRole(String[] organIds, Integer startIndex,
			Integer limit) throws BusinessException;

	/**
	 * 
	 * 查询角色总计数
	 * 
	 * @param organIds
	 *            查询机构及其下级所有机构的ID集合,不能为空至少得有一个ID存在
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:17:28
	 */
	public Integer roleTotalCount(String[] organIds);

	/**
	 * 统计机构下的角色数量
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构下的角色数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:01:47
	 */
	public Integer countOrganRole(String organId);

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
	 * 获取上级平台角色
	 * 
	 * @return 上级平台角色
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 下午3:56:44
	 */
	public Role getPlatformRole();

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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
	 */
	public List<DevicePermissionVO> listOrganFDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的一氧化碳\能见度检测器数量
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
	 */
	public List<DevicePermissionVO> listOrganCoviWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的氮氧化合物数量
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
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
	 *         Create at 2013 上午10:02:11
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
	 *         Create at 2013 下午1:46:18
	 */
	public List<DevicePermissionVO> listOrganPBWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

	/**
	 * 
	 * 统计机构及所有子机构下面的可变信息标志数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            设备名称
	 * @param type
	 *            控制设备类型
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:02:11
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
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 控制设备针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:46:18
	 */
	public List<DevicePermissionVO> listOrganControlDeviceWithPermission(
			String organId, String roleId, String name, String type,
			int startIndex, int limit);

	/**
	 * 
	 * 根据角色id查询所有资源id、类型
	 * 
	 * @param roleId
	 *            角色id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 资源角色关联关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:15:17
	 */
	public List<RoleResourcePermission> listAllResources(String roleId,
			int startIndex, int limit);

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
	 *         Create at 2014 上午9:55:55
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
	 *         Create at 2014 上午9:59:47
	 */
	public List<DevicePermissionVO> listOrganBTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

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
	 *         Create at 2014 上午11:34:19
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
	 *         Create at 2014 上午11:36:41
	 */
	public List<DevicePermissionVO> listOrganViDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit);

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
	 *         Create at 2014 上午11:45:59
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
	 *         Create at 2014 上午11:51:21
	 */
	public List<DevicePermissionVO> listOrganRoadDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

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
	 * @return 桥面针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午12:01:05
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
	 * @return 桥面检测器 针对给定角色的权限关系
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:50:58
	 */
	public List<DevicePermissionVO> listOrganBridgeDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 分页查询机构及所有子机构下面的紧急电话针对给定角色的权限关系
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
	 *         Create at 2014 下午3:24:01
	 */
	public int countOrganUrgentPhoneWithPermission(String organId,
			String roleId, String name);

	/**
	 * 分页查询机构及所有子机构下面的桥面检测器针对给定角色的权限关系
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
	 *         Create at 2014 下午3:25:03
	 */
	public List<DevicePermissionVO> listOrganUrgentPhoneWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 分页查询机构及所有子机构下面的电视墙针对给定角色的权限关系
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
	 *            名称
	 * @param startIndex
	 *            开始查询条数
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
	 * 查询外平台设备
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
	 */
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit);

	/**
	 * 根据调教获取下级平台资源数量
	 * 
	 * @param organId
	 *            机构id
	 * @param roleId
	 *            角色id
	 * @param name
	 *            名称
	 * @return 下级平台数量
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
}
