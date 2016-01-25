package com.znsx.cms.service.iface;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.AddressBook;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.Event;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.RoadMouth;
import com.znsx.cms.persistent.model.SchemeStepInstance;
import com.znsx.cms.persistent.model.StakeNumberLib;
import com.znsx.cms.persistent.model.Tollgate;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WareHouse;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.SchemeInstanceVO;
import com.znsx.cms.service.model.SchemeTempletVO;
import com.znsx.cms.service.model.UnitFireVO;
import com.znsx.cms.service.model.UnitHospitalVO;
import com.znsx.cms.service.model.UnitRoadAdminVO;
import com.znsx.cms.service.model.UnitVO;
import com.znsx.cms.service.model.UnitWareHouseVO;
import com.znsx.cms.web.dto.omc.GetAddressBookVO;
import com.znsx.cms.web.dto.omc.ResourceUserVO;
import com.znsx.cms.web.dto.omc.ResourceVO;
import com.znsx.cms.web.dto.omc.TeamVO;
import com.znsx.cms.web.dto.omc.VehicleVO;

/**
 * 应急预案业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午3:48:01
 */
public interface EmManager extends BaseManager {

	/**
	 * 创建预案字典
	 * 
	 * @param className
	 *            对象名称
	 * @param description
	 *            预案文字描述
	 * @return 创建成功的字典ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-29 下午3:50:46
	 */
	public String createDictionary(String className, String description)
			throws BusinessException;

	/**
	 * 绑定预案库与预案字典
	 * 
	 * @param templetId
	 *            预案库ID
	 * @param dictionaryId
	 *            预案字典ID
	 * @param seq
	 *            步骤序号
	 * @param range
	 *            搜索范围
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-29 下午3:52:32
	 */
	public void bindTempletDictionary(String templetId, String dictionaryId,
			Short seq, Integer range) throws BusinessException;

	/**
	 * 创建预案库
	 * 
	 * @param name
	 *            预案库名称
	 * @param eventType
	 *            事件类型
	 * @param eventLevel
	 *            事件级别
	 * @param organId
	 *            所属机构ID
	 * @return 创建成功的预案ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-29 下午3:55:39
	 */
	public String createTemplet(String name, Short eventType, Short eventLevel,
			String organId) throws BusinessException;

	/**
	 * 删除预案库
	 * 
	 * @param templetId
	 *            预案库ID
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-29 下午4:27:47
	 */
	@LogMethod(name = "删除预案库", targetType = "SchemeTemplet", operationType = "DeleteSchemeTemplet", code = "1231")
	public void deleteTemplet(@LogParam("id") String templetId)
			throws BusinessException;

	/**
	 * 保存预案库
	 * 
	 * @param name
	 *            预案库名称
	 * @param organId
	 *            所属机构ID
	 * @param schemeTemplet
	 *            xml格式的预案库:
	 * 
	 *            <pre>
	 * {@code
	 *   <SchemeTemplet Id="" Name="交通事故预案" Type="1" Level="2">}
	 *   <Step Seq="1" Content="通知附近 {路政}}[[RoadAdmin(1000)]]单位" />  
	 *   <Step Seq="2" Content="通知附近{{交警}}[[Police(3000)]]" /> 
	 *   <Step Seq="3" Content="通知周围{{医院}}[[Hospital(3000)]]、{{消防}}[[Fire(2000)]]" />
	 *   <Step Seq="4" Content="发布{{情报板}}[[Cms(5000)]]信息" />
	 * {@code 
	 * </SchemeTemplet>}
	 * </pre>
	 * @return 保存成功的预案库ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-4 上午9:59:44
	 */
	@LogMethod(name = "保存预案库", targetType = "SchemeTemplet", operationType = "SaveSchemeTemplet", code = "1230")
	public String saveSchemeTemplet(@LogParam("name") String name,
			String organId, Element schemeTemplet) throws BusinessException;

	/**
	 * 查询预案库列表
	 * 
	 * @param organId
	 *            所属机构ID
	 * @param type
	 *            事件类型
	 * @return 预案库列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 下午5:05:29
	 */
	public List<SchemeTempletVO> listSchemeTemplet(String organId, Short type);

	/**
	 * 保存预案实例
	 * 
	 * @param id
	 *            预案实例ID，如果为空表示新增，否则表示修改
	 * @param name
	 *            预案实例名称
	 * @param organId
	 *            所属机构ID
	 * @param templetId
	 *            预案库ID
	 * @param eventId
	 *            事件ID
	 * @param createUserId
	 *            创建用户ID
	 * @param createUserName
	 *            创建用户名称
	 * @param scheme
	 *            xml格式的预案实例:
	 * 
	 *            <pre>
	 * {@code
	 * <Scheme Id="402881ef45a6dfbc0145a6dfd12e0015" SchemeTempletId="402881ef45a6dfbc0145a6dfd12e0005" Name="XX交通事故预案" EventId="402881ef45a6dfbc0145a6dfd12e0006">
	 *     <Step Seq="1" Type="Police">
	 *       <Item Id="402881ef45a6dfbc0145a6dfd12e0011" TargetId="402881ef45a6dfbc0145a6dfd12e0007" TargetName="XX交警一大队" Telephone="02888888888" LinkMan="黄尚" RequestContent="XX高速公路K200+100上行方向发生交通事故" ResponseContent="警员王明，编号9527，电话13900002399已前往事发地点" BeginTime="1378565420145" ArriveTime="1378565420145" EndTime="1378565420145" ActionStatus="2" Note=""/>
	 *     </Step>
	 *     <Step Seq="2" Type="Hospital">
	 *       <Item Id="402881ef45a6dfbc0145a6dfd12e0012" TargetId="402881ef45a6dfbc0145a6dfd12e0008" TargetName="XX第一人民医院" Telephone="02888888888" LinkMan="何旭" RequestContent="XX高速公路K200+100上行方向发生交通事故，3人受伤，需两辆救护车协助救援" ResponseContent="救护车川A01234，川A01235已出发前往事发地点" BeginTime="1378565420145" ArriveTime="1378565420145" EndTime="" ActionStatus="1" Note=""/>
	 *     </Step>
	 * </Scheme>
	 * }
	 * </pre>
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 下午6:49:48
	 */
	@LogMethod(name = "保存预案", targetType = "SchemeInstance", operationType = "SaveScheme", code = "1233")
	public String saveScheme(@LogParam("id") String id,
			@LogParam("name") String name, String organId, String templetId,
			String eventId, String createUserId, String createUserName,
			Element scheme) throws BusinessException;

	/**
	 * 获取事件预案详情
	 * 
	 * @param eventId
	 *            事件ID
	 * @return 事件预案详情
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 下午8:23:51
	 */
	public SchemeInstanceVO getSchemeInstance(String eventId);

	/**
	 * 
	 * 创建应急单位
	 * 
	 * @param name
	 *            单位名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @return 单位id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:08:21
	 */
	public String createUnit(String name, String linkMan, String mobile,
			String location, String longitude, String latitude, String fax,
			String email, String telephone, String note, String organId,
			String standardNumber);

	/**
	 * 
	 * 更新应急单位
	 * 
	 * @param id
	 *            应急单位id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            单位名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:09:50
	 */
	public void updateUnit(String id, String standardNumber, String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId);

	/**
	 * 
	 * 删除应急单位
	 * 
	 * @param id
	 *            应急单位id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:19:12
	 */
	@LogMethod(targetType = "EmUnit", operationType = "delete", name = "删除单位", code = "2312")
	public void deleteUnit(@LogParam("id") String id);

	/**
	 * 
	 * 根据id获取应急单位
	 * 
	 * @param id
	 *            应急单位id
	 * @return 应急单位
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:32:48
	 */
	public UnitVO getUnit(String id);

	/**
	 * 
	 * 查询应急单位列表
	 * 
	 * @param name
	 *            应急单位名称
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询总条数
	 * @return 应急单位列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:22:44
	 */
	public List<UnitVO> listUnit(String name, String organId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计应急单位数量
	 * 
	 * @param name
	 *            应急单位名称
	 * @param organId
	 *            机构id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:41:03
	 */
	public Integer countTotalUnit(String name, String organId);

	/**
	 * 
	 * 创建应急物资
	 * 
	 * @param name
	 *            物资名称
	 * @param unitId
	 *            应急单位id
	 * @param standardNumber
	 *            标准号
	 * @param abilityType
	 *            功能类型
	 * @param note
	 *            备注
	 * @param unitType
	 *            单位类型
	 * @return 物资id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:43:03
	 */
	@LogMethod(targetType = "Resource", operationType = "create", name = "创建救援物资", code = "2320")
	public String createResource(@LogParam("name") String name, String unitId,
			String standardNumber, String abilityType, String note,
			String unitType, String minNumber, String resourceNumber);

	/**
	 * 
	 * 更新应急物资
	 * 
	 * @param id
	 *            物资id
	 * @param name
	 *            物资名称
	 * @param unitId
	 *            应急单位id
	 * @param standardNumber
	 *            标准号
	 * @param abilityType
	 *            功能类型
	 * @param note
	 *            备注
	 * @param unitType
	 *            单位类型
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:56:50
	 */
	@LogMethod(targetType = "Resource", operationType = "update", name = "修改救援物资", code = "2321")
	public void updateResource(@LogParam("id") String id,
			@LogParam("name") String name, String unitId,
			String standardNumber, String abilityType, String note,
			String unitType, String minNumber, String resourceNumber);

	/**
	 * 
	 * 根据id获取应急物资
	 * 
	 * @param id
	 *            物资id
	 * @return 物资实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:18:09
	 */
	public ResourceVO getResource(String id);

	/**
	 * 
	 * 统计物资数量
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            物资功能类型
	 * @param unitType
	 *            物资单位类型
	 * @return 物资总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:51:12
	 */
	public Integer countTotalResource(String unitId, String abilityType,
			String unitType);

	/**
	 * 
	 * 条件查询应急物资列表
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param unitType
	 *            单位类型
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 应急物资列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:59:35
	 */
	public List<ResourceVO> listResource(String unitId, String abilityType,
			String unitType, Integer startIndex, Integer limit);

	/**
	 * 
	 * 删除物资
	 * 
	 * @param id
	 *            物资id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:04:53
	 */
	@LogMethod(targetType = "Resource", operationType = "delete", name = "删除救援物资", code = "2322")
	public void deleteResource(@LogParam("id") String id);

	/**
	 * 
	 * 创建救援车辆
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param name
	 *            车辆名称
	 * @param standardNumber
	 *            标准号
	 * @param abilityType
	 *            功能类型
	 * @param seatNumber
	 *            座位数量
	 * @param vehicleNumber
	 *            车辆数量
	 * @param status
	 *            车辆状态
	 * @param note
	 *            备注
	 * @return 车辆id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:52:31
	 */
	@LogMethod(targetType = "Vehicle", operationType = "create", name = "创建救援车辆", code = "2340")
	public String createVehicle(String unitId, @LogParam("name") String name,
			String standardNumber, String abilityType, String seatNumber,
			String vehicleNumber, String status, String note);

	/**
	 * 
	 * 更新应急救援车辆
	 * 
	 * @param id
	 *            车辆id
	 * @param unitId
	 *            应急单位id
	 * @param name
	 *            车辆名称
	 * @param standardNumber
	 *            标准号
	 * @param abilityType
	 *            功能类型
	 * @param seatNumber
	 *            座位数量
	 * @param vehicleNumber
	 *            车辆数量
	 * @param status
	 *            车辆状态
	 * @param note
	 *            备注
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:06:16
	 */
	@LogMethod(targetType = "Vehicle", operationType = "update", name = "修改救援车辆", code = "2341")
	public void updateVehicle(@LogParam("id") String id, String unitId,
			@LogParam("name") String name, String standardNumber,
			String abilityType, String seatNumber, String vehicleNumber,
			String status, String note);

	/**
	 * 删除应急救援车辆
	 * 
	 * @param id
	 *            车辆id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:17:42
	 */
	@LogMethod(targetType = "Vehicle", operationType = "delete", name = "删除救援车辆", code = "2342")
	public void deleteVehicle(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询救援车辆
	 * 
	 * @param id
	 *            车辆id
	 * @return VehicleVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:21:34
	 */
	public VehicleVO getVehicle(String id);

	/**
	 * 
	 * 统计车辆数量
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param name
	 *            车辆名称
	 * @return 车辆总数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:32:43
	 */
	public Integer countTotalVehilce(String unitId, String abilityType,
			String name);

	/**
	 * 
	 * 根据条件查询救援车辆列表
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param abilityType
	 *            功能类型
	 * @param name
	 *            车辆名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 救援车辆列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:37:55
	 */
	public List<VehicleVO> listVehicle(String unitId, String abilityType,
			String name, Integer startIndex, Integer limit);

	/**
	 * 
	 * 创建救援队伍
	 * 
	 * @param name
	 *            队伍名称
	 * @param standardNumber
	 *            标准号
	 * @param unitId
	 *            应急单位id
	 * @param type
	 *            队伍类型
	 * @param note
	 *            备注
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:08:54
	 */
	@LogMethod(targetType = "Team", operationType = "create", name = "创建救援队伍", code = "2330")
	public String createTeam(@LogParam("name") String name,
			String standardNumber, String unitId, String type, String note);

	/**
	 * 
	 * 更新救援队伍
	 * 
	 * @param id
	 *            队伍id
	 * @param name
	 *            队伍名称
	 * @param standardNumber
	 *            标准号
	 * @param unitId
	 *            应急单位id
	 * @param type
	 *            队伍类型
	 * @param note
	 *            备注
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:26:07
	 */
	@LogMethod(targetType = "Team", operationType = "update", name = "修改救援队伍", code = "2331")
	public void updateTeam(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber,
			String unitId, String type, String note);

	/**
	 * 
	 * 删除救援队伍
	 * 
	 * @param id
	 *            队伍id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:56:53
	 */
	@LogMethod(targetType = "Team", operationType = "delete", name = "删除救援队伍", code = "2332")
	public void deleteTeam(@LogParam("id") String id);

	/**
	 * 根据id查询救援队伍
	 * 
	 * @param id
	 *            队伍id
	 * @return 救援队伍信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:03:31
	 */
	public TeamVO getTeam(String id);

	/**
	 * 
	 * 救援队伍总数量
	 * 
	 * @param unitId
	 *            应急救援单位id
	 * @param type
	 *            救援队伍类型
	 * @param name
	 *            队伍名称
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:48:56
	 */
	public Integer countTotalTeam(String unitId, String type, String name);

	/**
	 * 
	 * 条件查询救援队伍
	 * 
	 * @param unitId
	 *            应急单位id
	 * @param type
	 *            队伍类型
	 * @param name
	 *            队伍名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 救援队伍列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午6:53:33
	 */
	public List<TeamVO> listTeam(String unitId, String type, String name,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 创建队伍队员
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param teamId
	 *            救援队伍id
	 * @param telephone
	 *            电话
	 * @param name
	 *            队员名称
	 * @param type
	 *            队员类型
	 * @param isAdmin
	 *            是否队长
	 * @param speciality
	 *            队员专长
	 * @param note
	 *            备注
	 * @return 队员id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:03:44
	 */
	@LogMethod(targetType = "ResourceUser", operationType = "create", name = "创建救援队员", code = "2350")
	public String createResourceUser(String standardNumber, String teamId,
			String telephone, @LogParam("name") String name, String type,
			String isAdmin, String speciality, String note);

	/**
	 * 
	 * 更新队伍人员信息
	 * 
	 * @param id
	 *            队员id
	 * @param standardNumber
	 *            标准号
	 * @param teamId
	 *            队伍id
	 * @param telephone
	 *            电话
	 * @param name
	 *            名称
	 * @param type
	 *            队员类型
	 * @param isAdmin
	 *            是否队长
	 * @param speciality
	 *            专长
	 * @param note
	 *            备注
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:18:13
	 */
	@LogMethod(targetType = "ResourceUser", operationType = "update", name = "修改救援队员", code = "2351")
	public void updateResourceUser(@LogParam("id") String id,
			String standardNumber, String teamId, String telephone,
			@LogParam("name") String name, String type, String isAdmin,
			String speciality, String note);

	/**
	 * 
	 * 根据id查询队伍人员
	 * 
	 * @param id
	 *            人员id
	 * @return 人员信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:26:44
	 */
	public ResourceUserVO getResourceUser(String id);

	/**
	 * 
	 * 条件查询队伍人员
	 * 
	 * @param name
	 *            人员名称
	 * @param type
	 *            人员类型
	 * @param teamId
	 *            队伍id
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:35:15
	 */
	public Integer countTotalResourceUser(String name, String type,
			String teamId);

	/**
	 * 
	 * 根据条件查询队员列表
	 * 
	 * @param name
	 *            队员名称
	 * @param type
	 *            队员类型
	 * @param teamId
	 *            队伍id
	 * @param startIndex
	 *            开始查询条件
	 * @param limit
	 *            需要查询条数
	 * @return 队员列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:38:58
	 */
	public List<ResourceUserVO> listResourceUser(String name, String type,
			String teamId, Integer startIndex, Integer limit);

	/**
	 * 删除救援人员
	 * 
	 * @param id
	 *            人员id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:08:33
	 */
	@LogMethod(targetType = "ResourceUser", operationType = "delete", name = "删除救援队员", code = "2352")
	public void deleteResourceUser(@LogParam("id") String id);

	/**
	 * 创建消防队
	 * 
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param rescueCapability
	 *            救援能力
	 * @param fireEngineNumber
	 *            消防车数量
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:27:57
	 */
	@LogMethod(targetType = "Fire", operationType = "create", name = "创建消防队", code = "2310")
	public String createUnitFire(@LogParam("name") String name, String linkMan,
			String mobile, String location, String longitude, String latitude,
			String fax, String email, String telephone, String note,
			String organId, String standardNumber, Short rescueCapability,
			Short fireEngineNumber, String gisId);

	/**
	 * 创建医院
	 * 
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param ambulanceNumber
	 *            救护车数量
	 * @param unitLevel
	 *            医院等级
	 * @param rescueCapability
	 *            救援能力
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:41:53
	 */
	@LogMethod(targetType = "Hospital", operationType = "create", name = "创建医院", code = "2310")
	public String createUnitHospital(@LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, String standardNumber,
			Short ambulanceNumber, Short unitLevel, Short rescueCapability,
			String gisId);

	/**
	 * 创建交警支队
	 * 
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:52:08
	 */
	@LogMethod(targetType = "Police", operationType = "create", name = "创建交警支队", code = "2310")
	public String createUnitPolice(@LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, String standardNumber, String gisId);

	/**
	 * 创建路政
	 * 
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param carNumber
	 *            车辆数量
	 * @param teamNumber
	 *            队伍数量
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:59:31
	 */
	@LogMethod(targetType = "RoadAdmin", operationType = "create", name = "创建路政局", code = "2310")
	public String createUnitRoadAdmin(@LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, String standardNumber,
			Short carNumber, Short teamNumber, String gisId);

	/**
	 * 修改消防队
	 * 
	 * @param id
	 *            id
	 * @param standardNumber
	 *            标准阿訇
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param rescueCapability
	 *            救援能力
	 * @param fireEngineNumber
	 *            消防车数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:10:21
	 */
	@LogMethod(targetType = "Fire", operationType = "update", name = "修改消防队", code = "2311")
	public void updateUnitFire(@LogParam("id") String id,
			String standardNumber, @LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, Short rescueCapability,
			Short fireEngineNumber);

	/**
	 * 修改医院
	 * 
	 * @param id
	 *            id
	 * @param standardNumber
	 *            标准号
	 * @parzm name 名称
	 * @param linkMan
	 *            联系人
	 * @paraz mobile 移动电话
	 * @paraz location 地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param ambulanceNumber
	 *            救护车数量
	 * @param unitLevel
	 *            医院等级
	 * @param rescueCapability
	 *            救援能力
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:15:21
	 */
	@LogMethod(targetType = "Hospital", operationType = "update", name = "修改医院", code = "2311")
	public void updateUnitHospital(@LogParam("id") String id,
			String standardNumber, @LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, Short ambulanceNumber,
			Short unitLevel, Short rescueCapability);

	/**
	 * 修改交警支队
	 * 
	 * @param id
	 *            id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:25:50
	 */
	@LogMethod(targetType = "Police", operationType = "update", name = "修改交警支队", code = "2311")
	public void updateUnitPolice(@LogParam("id") String id,
			String standardNumber, @LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId);

	/**
	 * 修改路政
	 * 
	 * @param id
	 *            id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param carNumber
	 *            车辆数量
	 * @param teamNumber
	 *            队伍数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:29:35
	 */
	@LogMethod(targetType = "RoadAdmin", operationType = "update", name = "修改路政局", code = "2311")
	public void updateUnitRoadAdmin(@LogParam("id") String id,
			String standardNumber, @LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, Short carNumber, Short teamNumber);

	/**
	 * 根据id获取消防队
	 * 
	 * @param id
	 *            id
	 * @return UnitFireVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:41:39
	 */
	public UnitFireVO getUnitFire(String id);

	/**
	 * 根据id获取医院信息
	 * 
	 * @param id
	 *            id
	 * @return UnitHospitalVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:45:25
	 */
	public UnitHospitalVO getHospital(String id);

	/**
	 * 根据id获取路政
	 * 
	 * @param id
	 *            id
	 * @return UnitRoadAdminVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:54:35
	 */
	public UnitRoadAdminVO getRoadAdmin(String id);

	/**
	 * 事件录入
	 * 
	 * @param name
	 *            名称
	 * @param organId
	 *            机构ID
	 * @param type
	 *            事件类型
	 * @param subType
	 *            事件子类型
	 * @param eventLevel
	 *            事件级别
	 * @param roadType
	 *            道路类型
	 * @param location
	 *            阻断位置
	 * @param beginStake
	 *            开始桩号
	 * @param endStake
	 *            结束桩号
	 * @param navigation
	 *            阻断方向
	 * @param sendUser
	 *            填报然
	 * @param phone
	 *            联系电话
	 * @param impactProvince
	 *            受影响省份
	 * @param createTime
	 *            发生时间
	 * @param estimatesRecoverTime
	 *            预计恢复时间
	 * @param hurtNumber
	 *            受伤人数
	 * @param deathNumber
	 *            死亡人数
	 * @param delayHumanNumber
	 *            滞留人数
	 * @param crowdMeter
	 *            拥堵情况
	 * @param damageCarNumber
	 *            损坏车辆
	 * @param delayCarNumber
	 *            滞留车辆
	 * @param lossAmount
	 *            损失金额
	 * @param recoverTime
	 *            恢复时间
	 * @param description
	 *            现场情况
	 * @param isFire
	 *            是否发生火灾
	 * @param laneNumber
	 *            受损车道
	 * @param note
	 *            处理设施
	 * @param administration
	 *            行政区域
	 * @param managerUnit
	 *            管理单位
	 * @param recoverStatus
	 *            恢复状态
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return 事件id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:57:40
	 */
	@LogMethod(targetType = "Event", operationType = "create", name = "录入事件", code = "1240")
	public String eventEntry(@LogParam("name") String name, String organId,
			Short type, String subType, Short eventLevel, Short roadType,
			String location, String beginStake, String endStake,
			Short navigation, String sendUser, String phone,
			String impactProvince, Long createTime, Long estimatesRecoverTime,
			Short hurtNumber, Short deathNumber, Integer delayHumanNumber,
			Integer crowdMeter, Short damageCarNumber, Integer delayCarNumber,
			Integer lossAmount, Long recoverTime, String description,
			Short isFire, Short laneNumber, String note, String administration,
			String managerUnit, String roadName, String recoverStatus,
			String longitude, String latitude);

	/**
	 * 编辑事件信息
	 * 
	 * @param id
	 *            事件id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            机构sn
	 * @param type
	 *            事件类型
	 * @param subType
	 *            事件子类型
	 * @param eventLevel
	 *            事件级别
	 * @param roadType
	 *            道路类型
	 * @param location
	 *            阻断位置
	 * @param beginStake
	 *            开始桩号
	 * @param endStake
	 *            结束桩号
	 * @param navigation
	 *            阻断方向
	 * @param sendUser
	 *            填报然
	 * @param phone
	 *            联系电话
	 * @param impactProvince
	 *            受影响省份
	 * @param createTime
	 *            发生时间
	 * @param estimatesRecoverTime
	 *            预计恢复时间
	 * @param hurtNumber
	 *            受伤人数
	 * @param deathNumber
	 *            死亡人数
	 * @param delayHumanNumber
	 *            滞留人数
	 * @param crowdMeter
	 *            拥堵情况
	 * @param damageCarNumber
	 *            损坏车辆
	 * @param delayCarNumber
	 *            滞留车辆
	 * @param lossAmount
	 *            损失金额
	 * @param recoverTime
	 *            恢复时间
	 * @param description
	 *            现场情况
	 * @param isFire
	 *            是否发生火灾
	 * @param laneNumber
	 *            受损车道
	 * @param note
	 *            处理设施
	 * @param administration
	 *            行政区域
	 * @param managerUnit
	 *            管理单位
	 * @param flag
	 *            是否修改过图片标志
	 * @param roadName
	 *            路段名称
	 * @param recoverStatus
	 *            恢复状态
	 * @param eventStatus
	 *            事件状态
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:44:43
	 */
	@LogMethod(targetType = "Event", operationType = "update", name = "编辑事件", code = "1240")
	public void updateEvent(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, Short type,
			String subType, Short eventLevel, Short roadType, String location,
			String beginStake, String endStake, Short navigation,
			String sendUser, String phone, String impactProvince,
			Long createTime, Long estimatesRecoverTime, Short hurtNumber,
			Short deathNumber, Integer delayHumanNumber, Integer crowdMeter,
			Short damageCarNumber, Integer delayCarNumber, Integer lossAmount,
			Long recoverTime, String description, Short isFire,
			Short laneNumber, String note, String administration,
			String managerUnit, Short flag, String roadName,
			String recoverStatus, Short eventStatus);

	/**
	 * 事件关联图片
	 * 
	 * @param resourceId
	 *            事件id
	 * @return 图片id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:29:35
	 */
	public Image getResourceImageId(String resourceId);

	/**
	 * 图片存入数据库
	 * 
	 * @param id
	 *            图片id
	 * @param in
	 *            图片流
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:52:32
	 */
	public void upload(String id, InputStream in);

	/**
	 * 获取事件信息
	 * 
	 * @param id
	 *            事件id
	 * @return 事件信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:59:07
	 */
	public Element getEvent(String id);

	/**
	 * 根据事件id获取图片id集合
	 * 
	 * @param id
	 *            事件id
	 * @return 图片id集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:39:55
	 */
	public Element getImageIds(String id);

	/**
	 * 创建物资仓库
	 * 
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param managerUnit
	 *            管理单位
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:59:34
	 */
	@LogMethod(targetType = "WareHouse", operationType = "create", name = "创建物资仓库", code = "2310")
	public String createUnitWareHouse(@LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, String standardNumber, String gisId,
			String managerUnit);

	/**
	 * 修改物资仓库
	 * 
	 * @param id
	 *            仓库id
	 * @param name
	 *            名称
	 * @param linkMan
	 *            联系人
	 * @param mobile
	 *            移动电话
	 * @param location
	 *            地址
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param fax
	 *            传真
	 * @param email
	 *            电子邮件
	 * @param telephone
	 *            联系电话
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param managerUnit
	 *            管理单位
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:32:46
	 */
	@LogMethod(targetType = "WareHouse", operationType = "update", name = "修改物资仓库", code = "2311")
	public void updateUnitWareHouse(@LogParam("id") String id,
			String standardNumber, @LogParam("name") String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, String managerUnit);

	/**
	 * 根据ID查询事件
	 * 
	 * @param id
	 *            事件ID
	 * @return 事件对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-15 下午5:23:05
	 */
	public Event getEmEvent(String id);

	/**
	 * 根据给定范围搜索情报板
	 * 
	 * @param beginStake
	 *            起始桩号（小）
	 * @param endStake
	 *            结束桩号（大）
	 * @param range
	 * @param navigation
	 *            参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_UPLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_DOWNLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_ALL}
	 * @param organId
	 *            所属机构ID
	 * @return 情报板列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-16 上午9:44:43
	 */
	public List<ControlDeviceCms> listCmsByRange(String beginStake,
			String endStake, int range, int navigation, String organId);

	/**
	 * 根据给定范围搜索收费站
	 * 
	 * @param beginStake
	 *            起始桩号（小）
	 * @param endStake
	 *            结束桩号（大）
	 * @param range
	 * @param navigation
	 *            参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_UPLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_DOWNLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_ALL}
	 * @param organId
	 *            所属机构ID
	 * @return 收费站列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-16 下午5:52:39
	 */
	public List<Tollgate> listTollgateByRange(String beginStake,
			String endStake, int range, int navigation, String organId);

	/**
	 * 
	 * 根据给定范围搜索出口匝道
	 * 
	 * @param beginStake
	 *            起始桩号（小）
	 * @param endStake
	 *            结束桩号（大）
	 * @param range
	 * @param navigation
	 *            参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_UPLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_DOWNLOAD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#NAVIGATION_ALL}
	 * @param organId
	 *            所属机构ID
	 * @return 出口匝道列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-17 上午9:18:36
	 */
	public List<RoadMouth> listRoadMouthByRange(String beginStake,
			String endStake, int range, int navigation, String organId);

	/**
	 * 保存事件预案
	 * 
	 * @param schemeId
	 *            事件预案ID，为空表示新建，存在表示修改
	 * @param name
	 *            事件预案名称
	 * @param eventId
	 *            对应的事件ID
	 * @param templetId
	 *            使用的预案模板ID，自动生成预案此项为空
	 * @param createUserId
	 *            创建用户ID
	 * @param createUserName
	 *            创建用户名称
	 * @param scheme
	 *            预案XML内容,格式为：
	 * 
	 *            <pre>
	 * {@code
	 * <Scheme Id="402881ef45a6dfbc0145a6dfd12e0015" SchemeTempletId="402881ef45a6dfbc0145a6dfd12e0005" Name="XX交通事故预案" EventId="402881ef45a6dfbc0145a6dfd12e0006">
	 *     <Step Seq="1" Type="Police">
	 *       <Item Id="402881ef45a6dfbc0145a6dfd12e0011" TargetId="402881ef45a6dfbc0145a6dfd12e0007" TargetName="XX交警一大队" Telephone="02888888888" LinkMan="黄尚" RequestContent="XX高速公路K200+100上行方向发生交通事故" ResponseContent="警员王明，编号9527，电话13900002399已前往事发地点" BeginTime="1378565420145" ArriveTime="1378565420145" EndTime="1378565420145" ActionStatus="2" Note=""/>
	 *     </Step>
	 *     <Step Seq="2" Type="Hospital">
	 *       <Item Id="402881ef45a6dfbc0145a6dfd12e0012" TargetId="402881ef45a6dfbc0145a6dfd12e0008" TargetName="XX第一人民医院" Telephone="02888888888" LinkMan="何旭" RequestContent="XX高速公路K200+100上行方向发生交通事故，3人受伤，需两辆救护车协助救援" ResponseContent="救护车川A01234，川A01235已出发前往事发地点" BeginTime="1378565420145" ArriveTime="1378565420145" EndTime="" ActionStatus="1" Note=""/>
	 *     </Step>
	 * </Scheme>
	 * }
	 * </pre>
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-17 上午10:17:30
	 */
	@LogMethod(targetType = "SchemeInstance", operationType = "save", name = "保存事件应急预案", code = "1245")
	public String saveEventScheme(@LogParam("id") String schemeId,
			@LogParam("name") String name, String eventId, String templetId,
			String createUserId, String createUserName, Element scheme);

	/**
	 * 获取物资仓库信息
	 * 
	 * @param id
	 *            仓库id
	 * @return 仓库信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:10:47
	 */
	public UnitWareHouseVO getWareHouse(String id);

	/**
	 * 查询事件列表
	 * 
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @param type
	 *            事件类型
	 * @param status
	 *            事件状态
	 * @return 事件列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:15:41
	 */
	public Element listEvent(String organId, Long beginTime, Long endTime,
			Short type, Short status);

	/**
	 * 根据物资仓库id查询物资列表
	 * 
	 * @param id
	 *            仓库id
	 * @return 物资列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:42:36
	 */
	public Element listResourceById(String id);

	/**
	 * 根据机构id查询物资仓库
	 * 
	 * @param organId
	 *            机构id
	 * @return 物资仓库列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:46:18
	 */
	public List<WareHouse> listWareHouse(String organId);

	/**
	 * 字符串类型转为平台定义类型
	 * 
	 * @param clazz
	 *            字符串类型
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-22 下午2:33:09
	 */
	public Short class2Type(String clazz) throws BusinessException;

	/**
	 * 保存预案执行动作
	 * 
	 * @param steps
	 *            动作列表
	 * @param eventId
	 *            事件ID
	 * @param userId
	 *            执行用户ID
	 * @param userName
	 *            执行用户名称
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-22 下午3:08:08
	 */
	public void saveEventAction(List<SchemeStepInstance> steps, String eventId,
			String userId, String userName) throws BusinessException;

	/**
	 * 查找桩号对应的坐标
	 * 
	 * @param stakeNumber
	 *            桩号
	 * @param organId
	 *            机构ID
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-24 下午6:00:52
	 */
	public StakeNumberLib getStakeNumberLib(String stakeNumber, String organId)
			throws BusinessException;

	/**
	 * 修改通讯录
	 * 
	 * @param id
	 *            通讯录id
	 * @param linkMan
	 *            联系人
	 * @param phone
	 *            联系电话
	 * @param sex
	 *            性别
	 * @param address
	 *            联系地址
	 * @param note
	 *            备注
	 * @param email
	 *            邮件
	 * @param fax
	 *            传真
	 * @param organId
	 *            机构id
	 * @param position
	 *            职务
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:48:17
	 */
	@LogMethod(targetType = "AddressBook", operationType = "update", name = "修改通讯录", code = "2370")
	public void updateAddressBook(@LogParam("id") String id,
			@LogParam("name") String linkMan, String phone, String sex,
			String address, String note, String email, String fax,
			String organId, String position);

	/**
	 * 删除通讯录
	 * 
	 * @param id
	 *            通讯录id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:13:57
	 */
	@LogMethod(targetType = "AddressBook", operationType = "delete", name = "删除通讯录", code = "2371")
	public void deleteAddressBook(@LogParam("id") String id);

	/**
	 * 根据通讯录id获取信息
	 * 
	 * @param id
	 * @return 通讯录信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:17:59
	 */
	public GetAddressBookVO getAddressBook(String id);

	/**
	 * 获取通讯录总数
	 * 
	 * @param linkMan
	 *            联系人
	 * @param organId
	 *            机构id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:26:59
	 */
	public Integer addressBookTotalCount(String linkMan, String organId);

	/**
	 * 获取通讯录列表
	 * 
	 * @param linkMan
	 *            联系人
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:31:18
	 */
	public List<GetAddressBookVO> listAddressBook(String linkMan,
			String organId, Integer startIndex, Integer limit);

	/**
	 * 从工作薄读取通讯录名单返回
	 * 
	 * @param organId
	 * @param wb
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:22:24
	 */
	public List<AddressBook> readAddressBookWb(String organId, Workbook wb);

	/**
	 * 批量插入通讯录
	 * 
	 * @param abs
	 *            通讯录集合
	 * @param organId
	 *            机构id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:02:41
	 */
	public void batchInsertAddressBook(List<AddressBook> abs, String organId);

	/**
	 * 根据GisId获取应急单位列表
	 * 
	 * @param listElement
	 *            gisId，type集合
	 * @return 应急单位列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:29:43
	 */
	public Element listUnitByGisId(List<Element> listElement);

	/**
	 * 查询道路上的车检器集合
	 * 
	 * @param organId
	 *            路段ID或高速公路ID
	 * @return 道路上的车检器集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-14 下午3:07:22
	 */
	public List<VehicleDetector> listVd(String organId);

	/**
	 * 查询满足事件条件摄像头列表
	 * 
	 * @param cameraTypes
	 *            摄像头类型列表
	 * @param tollgates
	 *            收费站列表
	 * @param beginStake
	 *            事件开始桩号
	 * @param endStake
	 *            事件结束桩号
	 * @param frontSearchRange
	 *            事件上游搜索范围
	 * @param backSearchRange
	 *            事件下游搜索范围
	 * @param navigation
	 *            事件方向：0-上行，1-下行
	 * @param organId
	 *            事件所属机构ID
	 * @return 事件下用户电视墙投墙方案的摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         2014-12-1 下午3:08:15
	 */
	public List<Camera> listEventCamera(List<String> cameraTypes,
			List<String> tollgates, float beginStake, float endStake,
			int frontSearchRange, int backSearchRange, int navigation,
			String organId);

	/**
	 * 获取具体事件条件需要投墙
	 * 
	 * @param organId
	 *            事件发生路段ID
	 * @param beginStake
	 *            事件开始桩号
	 * @param endStake
	 *            事件结束桩号
	 * @param navigation
	 *            车行方向
	 * @param location
	 *            entrance,exit,road,oppEntrance,oppRoad
	 * @return 满足条件的情报板
	 * @author huangbuji
	 *         <p />
	 *         2014-12-9 下午3:09:56
	 */
	public ControlDevice getEventVms(String organId, String beginStake,
			String endStake, int navigation, String location);

	/**
	 * 采集服務器事件上報
	 * 
	 * @param standardNumber
	 *            設備標準號
	 * @param type
	 *            事件主類型
	 * @param subType
	 *            子類型，多個子類型用逗號間隔，比如：7-1,7-2,7-9
	 * @param createTime
	 *            事件發生時間
	 * @return 創建成功的事件ID
	 * @throws BusinessException
	 */
	public String createEvent(String standardNumber, Short type,
			String subType, Long createTime) throws BusinessException;
}
