package com.znsx.cms.service.iface;

import java.util.Collection;
import java.util.List;

import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.GetOrganBridgeVO;
import com.znsx.cms.service.model.GetOrganRoadVO;
import com.znsx.cms.service.model.GetOrganTollgateVO;
import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.service.model.GetRoadMouthVO;
import com.znsx.cms.service.model.GetServiceZoneVO;
import com.znsx.cms.service.model.GetTollgateVO;
import com.znsx.cms.service.model.ListOrganDeviceTreeVO;
import com.znsx.cms.service.model.ListOrganVO;
import com.znsx.cms.service.model.OrganVO;
import com.znsx.cms.service.model.UserResourceVO;
import com.znsx.cms.web.dto.omc.ListOrganByNameDTO;
import com.znsx.cms.web.dto.omc.ListOrganByParentIdDTO;

/**
 * 机构业务接口
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 上午10:35:24
 */
public interface OrganManager extends BaseManager {
	/**
	 * 创建机构
	 * 
	 * @param name
	 *            机构名称
	 * @param standardNumber
	 *            机构标准号
	 * @param parentId
	 *            父节点机构ID
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param areaCode
	 *            地域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型:1-高速公路,2-干线普通公路,3-公路交通枢纽,4-道路运输,5-水路运输,6-城市交通,0-行政管理单位
	 * @param frontOrganId
	 *            只有高速公路和普通公路才有前一段路id
	 * @param backOrganId
	 *            后一段id
	 * @param stakeNumber
	 *            桩号
	 * @param height
	 *            高度
	 * @param length
	 *            长度
	 * @param laneNumber
	 *            车道数量
	 * @return 创建成功的机构ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:51:17
	 */
	@LogMethod(targetType = "Organ", operationType = "create", name = "创建机构", code = "2010")
	public String createOrgan(@LogParam("name") String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String frontOrganId, String backOrganId,
			String stakeNumber) throws BusinessException;

	/**
	 * 修改机构
	 * 
	 * @param id
	 *            要修改的机构ID
	 * @param name
	 *            机构名称
	 * @param standardNumber
	 *            机构标准号
	 * @param parentId
	 *            父节点机构ID
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param areaCode
	 *            地域编码
	 * @param note
	 *            备注
	 * @param frontOrganId
	 *            只有高速公路和普通公路才有前一段路id
	 * @param backOrganId
	 *            后一段id
	 * @param stakeNumber
	 *            桩号
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:57:44
	 */
	@LogMethod(targetType = "Organ", operationType = "update", name = "修改机构", code = "2011")
	public void updateOrgan(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String frontOrganId,
			String backOrganId, String stakeNumbe) throws BusinessException;

	/**
	 * 删除机构
	 * 
	 * @param id
	 *            机构ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:04:53
	 */
	@LogMethod(targetType = "Organ", operationType = "delete", name = "删除机构", code = "2012")
	public void deleteOrgan(@LogParam("id") String id) throws BusinessException;

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
	 * @return 自身和子机构合并的列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:47:25
	 */
	public List<GetOrganVO> listOrgan(String parentId, String name,
			Integer startIndex, Integer limit) throws BusinessException;

	/**
	 * 给机构关联图片.在机构和图片创建后调用此接口
	 * 
	 * @param organId
	 *            机构ID
	 * @param imageId
	 *            图片ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:18:58
	 */
	public void bindImage(String organId, String imageId)
			throws BusinessException;

	/**
	 * 获取机构树
	 * 
	 * @param organId
	 *            根节点机构ID
	 * @return 机构树
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:58:58
	 */
	public OrganVO getOrganTree(String organId) throws BusinessException;

	// /**
	// *
	// * listOrganDevice 获取机构设备树
	// *
	// * @param organId
	// * 机构ID
	// * @param isRec
	// * 是否递归.
	// * @param cameras
	// * 设备ID集合
	// * @param isAdmin
	// * 是否管理员
	// * @return Element
	// * @throws BusinessException
	// * @author wangbinyu
	// * <p />
	// * Create at 2013 下午7:08:53
	// */
	// public Element listOrganDevice(String organId, boolean isRec,
	// List<RoleResourcePermissionVO> cameras, boolean isAdmin)
	// throws Exception;

	/**
	 * 根据机构ID查询机构详细信息
	 * 
	 * @param id
	 *            机构ID
	 * @return 机构详细信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:26:53
	 */
	public GetOrganVO getOrgan(String id) throws BusinessException;

	/**
	 * 
	 * 根据机构ID查询机构以及子机构
	 * 
	 * @param organId
	 *            机构ID
	 * @return List<ListOrganVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:50:51
	 */
	public List<ListOrganVO> listOrganById(String organId);

	/**
	 * 
	 * 获取根机构
	 * 
	 * @return 根机构
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:19:40
	 */
	public Organ getRootOrgan();

	/**
	 * 
	 * 查询机构总计数
	 * 
	 * @param parentId
	 *            父机构ID
	 * @param organName
	 *            机构名称
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:08:27
	 */
	public Integer organTotalCount(String parentId, String organName);

	/**
	 * 
	 * 根据机构名称模糊查询机构以及子机构列表
	 * 
	 * @param name
	 *            机构名称
	 * @param logonUserId
	 *            登录用户ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构ID
	 * @param standardNumber
	 *            标准号
	 * @param type
	 *            机构类型
	 * @return ListOrganByNameDTO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:46:54
	 */
	public ListOrganByNameDTO listOrganByName(String name, String logonUserId,
			Integer startIndex, Integer limit, String organId,
			String standardNumber, String type);

	/**
	 * 获取机构及其下级所有机构的ID集合
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构及其下级所有机构的ID集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:09:57
	 */
	public String[] listOrganAllChildren(String organId);

	/**
	 * 递归所有的机构设备
	 * 
	 * @param organId
	 *            顶级机构ID
	 * @param devices
	 *            用户的设备权限集合
	 * @param isRec
	 *            是否递归
	 * @return 机构及子机构下的所有权限设备
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 上午9:36:24
	 */
	public Element recursiveOrganDevice(String organId,
			List<AuthCameraVO> devices, boolean isRec) throws BusinessException;

	/**
	 * 
	 * 根据ID查询机构
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:45:50
	 */
	public Organ getOrganById(String organId);

	/**
	 * 根据父机构id查询路段列表
	 * 
	 * @param parentId
	 *            父机构id
	 * @return 路段列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:22:55
	 */
	public ListOrganByParentIdDTO listOrganByParentId(String parentId);

	/**
	 * 
	 * 判断该机构前一路段和后一路段是否被占用
	 * 
	 * @param id
	 *            机构id
	 * @param frontOrganId
	 *            前一路段id
	 * @param backOrganId
	 *            后一路段id
	 * @param createOrUpdate
	 *            判断是创建还是修改时检验
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:50:48
	 */
	public void isRoadExist(String id, String frontOrganId, String backOrganId,
			String createOrUpdate);

	/**
	 * 
	 * createOrganTunnel方法说明
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            机构父id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            地址
	 * @param areaCode
	 *            区域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型
	 * @param stakeNumber
	 *            桩号
	 * @param height
	 *            隧道高度
	 * @param length
	 *            隧道宽度
	 * @param laneNumber
	 *            车道数
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            同行能力
	 * @param leftDirection
	 *            左洞方向
	 * @param rightDirection
	 *            右洞方向
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:36:14
	 */
	@LogMethod(targetType = "OrganTunnel", operationType = "create", name = "创建隧道", code = "2010")
	public String createOrganTunnel(@LogParam("name") String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String stakeNumber, String height, String length,
			Integer laneNumber, Integer limitSpeed, Integer capacity,
			String leftDirection, String rightDirection);

	/**
	 * 
	 * 更新隧道
	 * 
	 * @param id
	 *            隧道id
	 * @param name
	 *            隧道名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param areaCode
	 *            地域编码
	 * @param note
	 *            备注
	 * @param stakeNumber
	 *            桩号
	 * @param height
	 *            隧道高度
	 * @param length
	 *            隧道长度
	 * @param laneNumber
	 *            车道数量
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            通行能力
	 * @param leftDirection
	 *            左洞方向
	 * @param rightDirection
	 *            右洞方向
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:31:11
	 */
	@LogMethod(targetType = "OrganTunnel", operationType = "update", name = "修改隧道", code = "2011")
	public void updateOrganTunnel(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String stakeNumber,
			String height, String length, Integer laneNumber,
			Integer limitSpeed, Integer capacity, String leftDirection,
			String rightDirection);

	/**
	 * 
	 * 根据id获取隧道
	 * 
	 * @param id
	 *            隧道id
	 * @return 隧道信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:56:01
	 */
	public GetOrganVO getOrganTunnel(String id);

	/**
	 * 创建路段
	 * 
	 * @param name
	 *            路段名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构ID
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param areaCode
	 *            地域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型
	 * @param stakeNumber
	 *            桩号
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            通行能力
	 * @param laneNumber
	 *            车道数量
	 * @param beginStakeNumber
	 *            开始桩号
	 * @param endStakeNumber
	 *            结束桩号
	 * @param laneWidth
	 *            车道宽度
	 * @param leftEdge
	 *            左边缘
	 * @param rightEdge
	 *            右边缘
	 * @param centralReserveWidth
	 *            中央可行宽度
	 * @param roadNumber
	 *            路段编号
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:44:54
	 */
	@LogMethod(targetType = "OrganRoad", operationType = "create", name = "创建路段", code = "2010")
	public String createOrganRoad(@LogParam("name") String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String stakeNumber, Integer limitSpeed,
			Integer capacity, Integer laneNumber, String beginStakeNumber,
			String endStakeNumber, Integer laneWidth, Integer leftEdge,
			Integer rightEdge, Integer centralReserveWidth, String roadNumber);

	/**
	 * 修改路段信息
	 * 
	 * @param id
	 *            路段id
	 * @param name
	 *            路段名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构ID
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param areaCode
	 *            地域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型
	 * @param stakeNumber
	 *            桩号
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            通行能力
	 * @param laneNumber
	 *            车道数量
	 * @param beginStakeNumber
	 *            开始桩号
	 * @param endStakeNumber
	 *            结束桩号
	 * @param laneWidth
	 *            车道宽度
	 * @param leftEdge
	 *            左边缘
	 * @param rightEdge
	 *            右边缘
	 * @param centralReserveWidth
	 *            中央可行宽度
	 * @param roadNumber
	 *            路段编号
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:18:20
	 */
	@LogMethod(targetType = "OrganRoad", operationType = "update", name = "修改路段", code = "2011")
	public void updateOrganRoad(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, Integer limitSpeed, Integer capacity,
			Integer laneNumber, String beginStakeNumber, String endStakeNumber,
			Integer laneWidth, Integer leftEdge, Integer rightEdge,
			Integer centralReserveWidth, String roadNumber);

	/**
	 * 根据路段id查询路段信息
	 * 
	 * @param id
	 *            路段id
	 * @return 路段信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:58:55
	 */
	public GetOrganRoadVO getOrganRoad(String id);

	/**
	 * 创建匝道
	 * 
	 * @param name
	 *            匝道名称
	 * @param limitSpeed
	 *            限制速度
	 * @param navigation
	 *            匝道方向
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:09:52
	 */
	@LogMethod(targetType = "RoadMouth", operationType = "create", name = "创建匝道", code = "2110")
	public String createRoadMouth(@LogParam("name") String name,
			Integer limitSpeed, String navigation, String organId,
			String stakeNumber, String longitude, String latitude);

	/**
	 * 更新匝道
	 * 
	 * @param id
	 *            匝道id
	 * @param name
	 *            名称
	 * @param limitSpeed
	 *            限制速度
	 * @param navigation
	 *            匝道方向
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            标准号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:26:16
	 */
	@LogMethod(targetType = "RoadMouth", operationType = "update", name = "更新匝道", code = "2111")
	public void updateRoadMouth(@LogParam("id") String id,
			@LogParam("name") String name, Integer limitSpeed,
			String navigation, String organId, String stakeNumber,
			String longitude, String latitude);

	/**
	 * 根据机构id查询匝道信息
	 * 
	 * @param id
	 *            匝道id
	 * @return 匝道信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:35:57
	 */
	public GetRoadMouthVO getRoadMouth(String id);

	/**
	 * 根据条件查询匝道列表
	 * 
	 * @param name
	 *            匝道名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 匝道列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:58:35
	 */
	public List<GetRoadMouthVO> listRoadMouth(String name, Integer startIndex,
			Integer limit, String organId);

	/**
	 * 删除匝道
	 * 
	 * @param id
	 *            匝道名称
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:37:46
	 */
	@LogMethod(targetType = "RoadMouth", operationType = "delete", name = "删除匝道", code = "2114")
	public void deleteRoadMouth(@LogParam("id") String id);

	/**
	 * 根据id查询收费站信息
	 * 
	 * @param id
	 *            收费站id
	 * @return 收费站信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:49:44
	 */
	public GetTollgateVO getTollgate(String id);

	/**
	 * 根据条件查询收费站列表信息
	 * 
	 * @param name
	 *            收费站名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 收费站列表信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:02:13
	 */
	public List<GetTollgateVO> listTollgate(String name, Integer startIndex,
			Integer limit, String organId);

	/**
	 * 统计收费站数量
	 * 
	 * @param name
	 *            收费站名称
	 * @param organId
	 *            机构id
	 * @return 收费站数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:58:59
	 */
	public Integer tollgateTotalCount(String name, String organId);

	/**
	 * 统计匝道数量
	 * 
	 * @param name
	 *            匝道名称
	 * @param organId
	 *            机构id
	 * @return 匝道数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:51:44
	 */
	public Integer roadMouthTotalCount(String name, String organId);

	/**
	 * 创建服务区
	 * 
	 * @param name
	 *            服务区名称
	 * @param level
	 *            服务区等级
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            桩号
	 * @param linkMan
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param navigation
	 *            方向
	 * @return 服务区id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:33:08
	 */
	@LogMethod(targetType = "ServiceZone", operationType = "create", name = "创建服务区", code = "2370")
	public String createServiceZone(@LogParam("name") String name,
			String level, String organId, String stakeNumber, String linkMan,
			String phone, String longitude, String latitude, String navigation);

	/**
	 * 修改服务区信息
	 * 
	 * @param id
	 *            服务区id
	 * @param name
	 *            服务区名称
	 * @param level
	 *            服务区等级
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            桩号
	 * @param linkMan
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param navigation
	 *            方向
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:11:11
	 */
	@LogMethod(targetType = "ServiceZone", operationType = "update", name = "修改服务区", code = "2371")
	public void updateServiceZone(@LogParam("id") String id,
			@LogParam("name") String name, String level, String organId,
			String stakeNumber, String linkMan, String phone, String longitude,
			String latitude, String navigation);

	/**
	 * 删除服务区
	 * 
	 * @param id
	 *            服务区id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:06:04
	 */
	@LogMethod(targetType = "ServiceZone", operationType = "delete", name = "删除服务区", code = "2372")
	public void deleteServiceZone(@LogParam("id") String id);

	/**
	 * 根据id获取服务区信息
	 * 
	 * @param id
	 * @return 服务区信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:03:30
	 */
	public GetServiceZoneVO getServiceZone(String id);

	/**
	 * 根据条件获取服务区数量
	 * 
	 * @param name
	 *            服务区名称
	 * @param organId
	 *            机构id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:08:35
	 */
	public Integer serviceZoneTotalCount(String name, String organId);

	/**
	 * 查询服务区列表
	 * 
	 * @param name
	 *            服务区名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organId
	 *            机构id
	 * @return 服务区列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:12:42
	 */
	public List<GetServiceZoneVO> listServiceZone(String name,
			Integer startIndex, Integer limit, String organId);

	/**
	 * 根据SN查找机构
	 * 
	 * @param standardNumber
	 *            机构标准号
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-24 下午6:14:26
	 */
	public Organ findBySn(String standardNumber) throws BusinessException;

	/**
	 * 创建机构收费站
	 * 
	 * @param name
	 *            收费名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系方式
	 * @param phone
	 *            移动电话
	 * @param address
	 *            地址
	 * @param areaCode
	 *            邮编
	 * @param note
	 *            备注
	 * @param type
	 *            类型
	 * @param stakeNumber
	 *            桩号
	 * @param entranceNumber
	 *            入口数
	 * @param exitNumber
	 *            出口数
	 * @param navigation
	 *            行车方向
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return 收费站id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:36:35
	 */
	@LogMethod(targetType = "Tollgate", operationType = "create", name = "创建收费站", code = "2010")
	public String createOrganTollgate(@LogParam("name") String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String stakeNumber, Integer entranceNumber,
			Integer exitNumber, String navigation, String longitude,
			String latitude);

	/**
	 * 修改收费站
	 * 
	 * @param id
	 *            收费站id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系方式
	 * @param phone
	 *            移动电话
	 * @param address
	 *            地址
	 * @param areaCode
	 *            邮编
	 * @param note
	 *            备注
	 * @param stakeNumber
	 *            桩号
	 * @param entranceNumber
	 *            入口数
	 * @param exitNumber
	 *            出口数
	 * @param navigation
	 *            行车方向 * @param longitude 经度 * @param latitude 纬度
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:51:19
	 */
	@LogMethod(targetType = "Tollgate", operationType = "update", name = "修改收费站", code = "2011")
	public void updateOrganTollgate(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String stakeNumber,
			Integer entranceNumber, Integer exitNumber, String navigation,
			String longitude, String latitude);

	/**
	 * 根据id查询收费站
	 * 
	 * @param id
	 *            收费站id
	 * @return 获取收费站信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:07:54
	 */
	public GetOrganTollgateVO getOrganTollgate(String id);

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
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            地址
	 * @param areaCode
	 *            区域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            通行能力
	 * @param width
	 *            桥梁宽度
	 * @param length
	 *            桥梁长度
	 * @return id
	 */
	public String createOrganBridge(String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, String longitude, String latitude,
			Integer limitSpeed, Integer capacity, String width, String length);

	/**
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param parentId
	 *            父机构id
	 * @param fax
	 *            传真
	 * @param contact
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param address
	 *            地址
	 * @param areaCode
	 *            区域编码
	 * @param note
	 *            备注
	 * @param type
	 *            机构类型
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param limitSpeed
	 *            限制速度
	 * @param capacity
	 *            通行能力
	 * @param width
	 *            桥梁宽度
	 * @param length
	 *            桥梁长度
	 */
	public void updateOrganBridge(String id, String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String stakeNumber, String longitude, String latitude,
			Integer limitSpeed, Integer capacity, String width, String length);

	/**
	 * 
	 * @param id
	 *            桥梁id
	 * @return 桥梁信息
	 */
	public GetOrganBridgeVO getOrganBridge(String id);

	/**
	 * 根据SN查询机构，本级和下级平台的都会进行查找
	 * 
	 * @param sn
	 *            机构SN
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-30 上午10:32:18
	 */
	public Organ findOrganResource(String sn) throws BusinessException;

	/**
	 * 生成机构树
	 * 
	 * @param organId
	 *            根机构ID
	 * @param devices
	 *            用户具有权限的所有资源
	 * @return 机构树
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-4 上午10:35:32
	 */
	public Element generateOrganTree(String organId,
			Collection<UserResourceVO> devices) throws BusinessException;

	/**
	 * 查询本级机构以及下级机构列表
	 * 
	 * @param organId
	 *            机构id
	 * @return 机构列表
	 */
	public List<ListOrganVO> listOrganTreeAll(String organId);

	/**
	 * 根据机构SN查询机构ID，如果本级机构不存在则查询下级机构ID
	 * 
	 * @param standardNumber
	 *            机构sn
	 * @return 机构id
	 */
	public String getOrganIdBySN(String standardNumber);

	/**
	 * 根据传入的机构SN，构建摄像头机构树，包含下级资源
	 * 
	 * @param organSn
	 *            机构SN，如果为空生成全部的数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-12-2 下午2:17:32
	 */
	public ListOrganDeviceTreeVO treeOrganCamera(String organSn);
}
