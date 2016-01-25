package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.ResourcePermissionVO;

/**
 * 角色资源权限数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:11:45
 */
public interface RoleResourcePermissionDAO extends
		BaseDAO<RoleResourcePermission, String> {
	/**
	 * 根据角色查询角色资源集合
	 * 
	 * @param roleId
	 *            角色ID
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 角色资源集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:40:10
	 */
	public List<ResourcePermissionVO> listByRoleId(String roleId, int start,
			int limit);

	/**
	 * 统计角色资源数量
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色资源数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-24 下午6:37:28
	 */
	public int countByRoleId(String roleId);

	/**
	 * 删除角色的资源集合
	 * 
	 * @param roleId
	 *            角色ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:41:07
	 */
	public void deleteByRole(String roleId);

	/**
	 * 删除角色下指定资源集合的权限关联
	 * 
	 * @param roleId
	 *            角色ID
	 * @param type
	 *            资源类型
	 * @param resourceIds
	 *            资源ID集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:33:16
	 */
	public void deleteByRoleResource(String roleId, String type,
			String[] resourceIds);

	/**
	 * 获取指定角色ID列表的所有设备集合
	 * 
	 * @param roleIds
	 *            角色ID列表
	 * @param resourceType
	 *            设备类型
	 * @return 角色ID列表的所有设备集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午2:39:27
	 */
	public List<RoleResourcePermission> listByRoleIds(List<String> roleIds,
			String resoureType);

	/**
	 * 统计角色所有资源数量
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色资源数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-24 下午6:37:28
	 */
	public int countAllResourcesByRoleId(String roleId);

	/**
	 * 
	 * 根据类型去不同的表查询资源
	 * 
	 * @param resourceId
	 *            资源id
	 * @param roleId
	 *            角色id
	 * @param type
	 *            类型
	 * @return 返回资源
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:48:32
	 */
	public ResourcePermissionVO findResourceByRoleId(String resourceId,
			String roleId, String type);

	/**
	 * 查询指定角色的绑定的资源ID
	 * 
	 * @param roleId
	 *            角色ID
	 * @param resourceType
	 *            资源类型
	 * @return 角色的绑定的资源ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-22 下午3:34:23
	 */
	public List<String> listRoleResourceId(String roleId,
			List<String> resourceType);

	/**
	 * 批量插入数据
	 * 
	 * @param list
	 */
	public void batchInsert(List<RoleResourcePermission> list)
			throws BusinessException;
}
