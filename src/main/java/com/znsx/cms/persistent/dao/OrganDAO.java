package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;

/**
 * 机构数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:46:14
 */
public interface OrganDAO extends BaseDAO<Organ, String> {
	/**
	 * 根据父机构ID或者机构名称查询机构列表
	 * 
	 * @param parentId
	 *            父机构ID
	 * @param name
	 *            机构名称,模糊查询
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:03:06
	 */
	public List<Organ> listOrgan(String parentId, String name,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 根据机构ID查询当前机构以及子机构ID
	 * 
	 * @param organId
	 *            机构ID
	 * @return String[]
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:37:44
	 */
	public String[] findOrgansByOrganId(String organId);

	/**
	 * 获取平台的根机构
	 * 
	 * @return 平台的根机构
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:18:25
	 */
	public Organ getRootOrgan();

	/**
	 * 
	 * 根据机构ID查询当前机构以及子机构
	 * 
	 * @param organId
	 *            机构ID
	 * @return List<Organ>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:52:12
	 */
	public List<Organ> listOrganById(String organId);

	/**
	 * 
	 * 查询机构总计数
	 * 
	 * @param parentId
	 *            父机构ID
	 * @param organName
	 *            机构名称
	 * @return 机构总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:09:27
	 */
	public Integer organTotalCount(String parentId, String organName);

	/**
	 * 
	 * 根据机构名称模糊查询机构列表
	 * 
	 * @param organIds
	 *            机构ID
	 * @param name
	 *            机构名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param type
	 *            机构类型
	 * @return 机构列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:55:55
	 */
	public List<Organ> listOrganByName(String[] organIds, String name,
			Integer startIndex, Integer limit, String standardNumber,
			String type);

	/**
	 * 
	 * 根据机构名称查询机构总计数
	 * 
	 * @param organIds
	 *            机构ID
	 * @param name
	 *            机构名称
	 * @param standardNumber
	 *            标准号
	 * @param type
	 *            机构类型
	 * @return 机构总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:28:53
	 */
	public Integer listOrganTotalCount(String[] organIds, String name,
			String standardNumber, String type);

	/**
	 * 根据父id查询路段列表
	 * 
	 * @param parentId
	 *            父机构id
	 * @return 路段列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:24:09
	 */
	public List<Organ> listOrganByParentId(String parentId);

	/**
	 * 查询事故发生的路段，尽量按照路段名称进行匹配，如果没有找到，则返回所属高速公路管辖范围内的第一个路段
	 * 
	 * @param roadName
	 *            路段名称，模糊查询
	 * @param parentId
	 *            路段所属高速公路ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-14 下午2:37:21
	 */
	public OrganRoad getEventRoad(String roadName, String parentId);

	/**
	 * 所有的机构SN与自身对象的映射表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-1 上午10:59:03
	 */
	public Map<String, Organ> mapOrganBySn(String parentId);

	/**
	 * 根据机构类型查询机构列表
	 * 
	 * @param types
	 *            机构类型，参见{@link com.znsx.cms.service.common.TypeDefinition}
	 *            中的ORGAN_TYPE类型
	 * @return 机构列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-8 上午11:45:53
	 */
	public List<Organ> listOrganByTypesNoTransaction(String[] types);
}
