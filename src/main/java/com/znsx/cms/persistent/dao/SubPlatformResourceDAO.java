package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 下级平台资源数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:48:35
 */
public interface SubPlatformResourceDAO extends
		BaseDAO<SubPlatformResource, String> {
	/**
	 * 查询指定平台的全部资源映射表
	 * 
	 * @param parentId
	 *            平台ID
	 * @return 平台的全部资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-16 下午5:24:02
	 */
	public Map<String, SubPlatformResource> mapPlatformResourceById(
			String parentId);

	/**
	 * 强制清除hibernate一级缓存，释放内存空间
	 * 
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-23 下午1:47:36
	 */
	public void clearCache() throws BusinessException;

	/**
	 * 根据平台标准号删除该平台下方的所有资源
	 * 
	 * @param standardNumber
	 *            平台标准号
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-23 下午3:28:18
	 */
	public void deleteByPlatform(String standardNumber);

	/**
	 * 根据标准号查询资源对象
	 * 
	 * @param standardNumber
	 *            资源标准号
	 * @return 资源对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-24 上午11:25:03
	 */
	public SubPlatformResource getByStandardNumber(String standardNumber);

	/**
	 * 查询给定资源下的子节点列表
	 * 
	 * @param parentId
	 *            父资源ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-23 下午1:38:59
	 */
	public List<SubPlatformResource> listSubPlatformResource(String parentId);

	/**
	 * 获取下级平台顶级机构列表
	 * 
	 * @return 多个下级平台顶级机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-9 下午4:49:57
	 */
	public List<SubPlatformResource> listRoots();

	/**
	 * 获取给定ID的下级机构
	 * 
	 * @return 下级机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-9 下午5:32:36
	 */
	public List<SubPlatformResource> listSubOrgan(String parentId);

	/**
	 * 获取给定机构ID的下级所有机构
	 * 
	 * @param parentId
	 *            父机构ID
	 * @return 下级所有机构
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-27 下午5:14:29
	 */
	public List<SubPlatformResource> listOrgan(String parentId);

	/**
	 * 获取给定机构ID的下级所有摄像头
	 * 
	 * @return 下级的所有摄像头
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-27 下午5:02:05
	 */
	public List<SubPlatformResource> listCamera(String parentId);

	/**
	 * 获取给定根ID下的所有资源的SN与自身对象的映射
	 * 
	 * @param rootId
	 *            根节点ID，为空将查询所有的平台资源
	 * @return 资源的SN与自身对象的映射
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-31 下午3:27:09
	 */
	public Map<String, SubPlatformResource> mapSubPlatformResource(String rootId);

	/**
	 * 查询指定角色关联的下级资源列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @param parentId
	 *            父资源ID
	 * @return 角色关联的下级资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-2 上午10:06:12
	 */
	public List<SubPlatformResource> listRoleSubResource(String roleId,
			String parentId);

	/**
	 * 查询角色关联的下级平台根机构列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色关联的下级平台根机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-2 下午1:55:52
	 */
	public List<SubPlatformResource> listRoleRoots(String roleId);

	/**
	 * 查询角色关联的指定机构的下级机构列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @param parentId
	 *            父机构ID
	 * @return 角色关联的指定机构的下级机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-2 下午1:57:28
	 */
	public List<SubPlatformResource> listRoleSubOrgan(String roleId,
			String parentId);

	/**
	 * 根据机构id查询下级平台设备以及子机构设备
	 * 
	 * @param organId
	 * 机构id
	 * @return
	 */
	public List<SubPlatformResource> listSubPlatformResourceByOrganId(
			String organId);
}
