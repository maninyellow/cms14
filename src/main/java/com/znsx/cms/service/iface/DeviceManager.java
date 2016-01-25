package com.znsx.cms.service.iface;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.DeviceAlarmReal;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.Manufacturer;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.CameraStatusVO;
import com.znsx.cms.service.model.DeviceAlarmStatusVO;
import com.znsx.cms.service.model.DeviceAlarmVO;
import com.znsx.cms.service.model.DeviceGPSVO;
import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.service.model.DeviceOnlineHistroyVO;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.DvrVO;
import com.znsx.cms.service.model.GetBoxTransformerVO;
import com.znsx.cms.service.model.GetBridgeDetectorVO;
import com.znsx.cms.service.model.GetCameraVO;
import com.znsx.cms.service.model.GetControlDeviceVO;
import com.znsx.cms.service.model.GetCoviVO;
import com.znsx.cms.service.model.GetDvrVO;
import com.znsx.cms.service.model.GetFireDetectorVO;
import com.znsx.cms.service.model.GetLoliVO;
import com.znsx.cms.service.model.GetNoDetectorVO;
import com.znsx.cms.service.model.GetPushButtonVO;
import com.znsx.cms.service.model.GetRoadDetectorVO;
import com.znsx.cms.service.model.GetSolarBatteryVO;
import com.znsx.cms.service.model.GetUrgentPhoneVO;
import com.znsx.cms.service.model.GetVehicleDetectorVO;
import com.znsx.cms.service.model.GetViDetectorVO;
import com.znsx.cms.service.model.GetWeatherStatVO;
import com.znsx.cms.service.model.GetWindSpeedVO;
import com.znsx.cms.service.model.ListCameraVO;
import com.znsx.cms.service.model.ListDeviceAlarmVO;
import com.znsx.cms.service.model.ListOrganDeviceTreeVO;
import com.znsx.cms.service.model.OrganDeviceCheck;
import com.znsx.cms.service.model.OrganDeviceOnline;
import com.znsx.cms.service.model.PresetVO;
import com.znsx.cms.service.model.PtsDvrVO;
import com.znsx.cms.service.model.RoleResourcePermissionVO;
import com.znsx.cms.service.model.TopRealPlayLog;
import com.znsx.cms.service.model.UserResourceVO;
import com.znsx.cms.web.dto.omc.CountDeviceDTO;
import com.znsx.cms.web.dto.omc.ListDvrDTO;

/**
 * 设备业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:38:36
 */
public interface DeviceManager extends BaseManager {

	/**
	 * 创建摄像头
	 * 
	 * @param standardNumber
	 *            摄像头标准号
	 * @param subType
	 *            摄像头子类型
	 * @param name
	 *            摄像头名称
	 * @param organId
	 *            设备所属机构ID
	 * @param manufacturerId
	 *            生产厂商ID
	 * @param location
	 *            安装位置
	 * @param note
	 *            备注
	 * @param storeType
	 *            存储类型.0-前端存储，1-中心存储，2-前端和中心存储
	 * @param localStorePlan
	 *            7*24小时前端存储标志，0-不存储，1-存储
	 * @param centerStorePlan
	 *            7*24小时中心存储标志，0-不存储，1-存储
	 * @param streamType
	 *            码流类型．CIF,4CIF,D1等
	 * @param parentId
	 *            所属DVR的ID
	 * @param mssId
	 *            流媒体服务器ID
	 * @param crsId
	 *            存储服务器ID
	 * @param channelNumber
	 *            所处DVR上的通道号
	 * @param deviceModelId
	 *            设备型号ID
	 * @param expand
	 *            扩展属性
	 * @param navigation
	 *            设备方向
	 * @param stakeNumber
	 *            桩号
	 * @param owner
	 *            设备归属
	 * @param civilCode
	 *            行政区域
	 * @param block
	 *            警区
	 * @param certNum
	 *            证书序列号
	 * @param certifiable
	 *            证书有效标识（有证书必选） 缺省为0：无效 1有效 //默认为1
	 * @param errCode
	 *            无效原因榜
	 * @param endTime
	 *            有效日期时间
	 * @param rmsId
	 *            录像转发服务器id
	 * @param storeStream
	 *            需要返回给CRS的主子码流
	 * @return 创建成功后生成的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:46:55
	 */
	@LogMethod(targetType = "Camera", operationType = "create", name = "创建摄像头", code = "2060")
	public String createCamera(String standardNumber, String subType,
			@LogParam("name") String name, String organId,
			String manufacturerId, String location, String note,
			Short storeType, String localStorePlan, String centerStorePlan,
			String streamType, String parentId, String mssId, String crsId,
			Short channelNumber, String deviceModelId, String expand,
			String navigation, String stakeNumber, String owner,
			String civilCode, Double block, String certNum,
			Integer certifiable, Integer errCode, Long endTime, String rmsId,
			String storeStream) throws BusinessException;

	/**
	 * 根据摄像头ID获取摄像头所有信息
	 * 
	 * @param id
	 *            摄像头ID
	 * @return GetCameraVO
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:32:16
	 */
	public GetCameraVO getCamera(String id) throws BusinessException;

	/**
	 * 修改摄像头
	 * 
	 * @param id
	 *            要修改的摄像头ID
	 * @param standardNumber
	 *            摄像头标准号
	 * @param subType
	 *            摄像头子类型
	 * @param name
	 *            摄像头名称
	 * @param organId
	 *            设备所属机构ID
	 * @param manufacturerId
	 *            生产厂商ID
	 * @param location
	 *            安装位置
	 * @param note
	 *            备注
	 * @param storeType
	 *            存储类型.0-前端存储，1-中心存储，2-前端和中心存储
	 * @param localStorePlan
	 *            7*24小时前端存储标志，0-不存储，1-存储
	 * @param centerStorePlan
	 *            7*24小时中心存储标志，0-不存储，1-存储
	 * @param streamType
	 *            码流类型．CIF,4CIF,D1等
	 * @param parentId
	 *            所属DVR的ID
	 * @param mssId
	 *            流媒体服务器ID
	 * @param crsId
	 *            存储服务器ID
	 * @param channelNumber
	 *            所处DVR上的通道号
	 * @param deviceModelId
	 *            设备型号ID
	 * @param expand
	 *            扩展属性
	 * @param navigation
	 *            设备方向
	 * @param stakeNumber
	 *            桩号
	 * @param owner
	 *            设备归属
	 * @param civilCode
	 *            行政区域
	 * @param block
	 *            警区
	 * @param certNum
	 *            证书序列号
	 * @param certifiable
	 *            证书有效标识（有证书必选） 缺省为0：无效 1有效 //默认为1
	 * @param errCode
	 *            无效原因榜
	 * @param endTime
	 *            有效日期时间
	 * @param rmsId
	 *            录像转发服务器id
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:02:03
	 */
	@LogMethod(targetType = "Camera", operationType = "update", name = "修改摄像头", code = "2061")
	public void updateCamera(@LogParam("id") String id, String standardNumber,
			String subType, String name, String organId, String manufacturerId,
			String location, String note, Short storeType,
			String localStorePlan, String centerStorePlan, String streamType,
			String parentId, String mssId, String crsId, Short channelNumber,
			String deviceModelId, String expand, String navigation,
			String stakeNumber, String owner, String civilCode, Double block,
			String certNum, Integer certifiable, Integer errCode, Long endTime,
			String rmsId, String storeStream) throws BusinessException;

	/**
	 * 删除摄像头
	 * 
	 * @param id
	 *            摄像头ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:00:08
	 */
	@LogMethod(targetType = "Camera", operationType = "delete", name = "删除摄像头", code = "2062")
	public void deleteCamera(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 
	 * 创建预置点
	 * 
	 * @param vicId
	 *            摄像头ID
	 * @param presetNumber
	 *            预置点位号
	 * @param presetName
	 *            预置点名称
	 * @return Long
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:17:43
	 */
	public String createPreset(String vicId, Integer presetNumber,
			@LogParam("name") String presetName);

	/**
	 * 
	 * 更新预置点
	 * 
	 * @param presetId
	 *            预置点ID
	 * @param presetNumber
	 *            预置点位号
	 * @param presetName
	 *            预置点名称
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:11:37
	 */
	public void updatePreset(@LogParam("id") String presetId,
			Integer presetNumber, @LogParam("name") String presetName);

	/**
	 * 
	 * 删除预置点
	 * 
	 * @param presetId
	 *            预置点ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:35:28
	 */
	public void deletePreset(@LogParam("id") String presetId);

	/**
	 * 根据ID获取预置点对象
	 * 
	 * @param id
	 *            预置点ID
	 * @return 预置点对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-3 下午3:45:42
	 */
	public Preset getPreset(String id);

	/**
	 * 
	 * 根据摄像头ID查询预置点
	 * 
	 * @param vicId
	 *            摄像头ID
	 * @return List<PresetVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:13:16
	 */
	public List<PresetVO> listVicPreset(String vicId);

	/**
	 * 
	 * 根据用户ID查询角色所属的所有设备
	 * 
	 * @param id
	 *            用户ID
	 * @return List<RoleResourcePermissionVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:59:52
	 */
	public List<RoleResourcePermissionVO> listDeviceByOperation(String id);

	/**
	 * 
	 * 是否管理员
	 * 
	 * @param id
	 *            用户ID
	 * @return Boolean
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:42:40
	 */
	public boolean isAdmin(String id);

	/**
	 * 统计平台中的摄像头数量
	 * 
	 * @return 摄像头数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午11:32:19
	 */
	public int countCamera();

	/**
	 * 创建视频服务器
	 * 
	 * @param standardNumber
	 *            设备标准号
	 * @param subType
	 *            设备类型
	 * @param name
	 *            设备名称
	 * @param ptsId
	 *            PTS服务器ID
	 * @param transport
	 *            TCP-TCP流传输方式, RTP-RTP流传输方式
	 * @param mode
	 *            compatible-兼容, standard-标准
	 * @param maxConnect
	 *            最大连接数，决定走流媒体服务器的规则
	 * @param channelAmount
	 *            通道数量,CMS会根据这个数值来自动创建下面的摄像头.同时如果受License的限制,则最终只会自动创建部分通道的摄像头
	 * @param organId
	 *            设备所属机构ID
	 * @param linkType
	 *            保留字段，adsl等连接方式
	 * @param lanIp
	 *            IP地址
	 * @param port
	 *            连接端口
	 * @param manufacturerId
	 *            设备厂家ID
	 * @param deviceModelId
	 *            视频服务器型号ID
	 * @param location
	 *            安装位置
	 * @param note
	 *            备注
	 * @param userName
	 *            设备登录用户名
	 * @param password
	 *            设备登录密码
	 * @param heartCycle
	 *            心跳周期，单位：秒
	 * @param expand
	 *            扩展字段
	 * @param aicAmount
	 *            报警输入通道数量
	 * @param aocAmount
	 *            报警输出通道数量
	 * @param decode
	 *            解码器
	 * @return 创建成功后生成的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:21:42
	 */
	@LogMethod(targetType = "Dvr", operationType = "create", name = "创建视频服务器", code = "2050")
	public String createDvr(String standardNumber, String subType,
			@LogParam("name") String name, String ptsId, String transport,
			String mode, Integer maxConnect, Integer channelAmount,
			String organId, String linkType, String lanIp, Integer port,
			String manufacturerId, String deviceModelId, String location,
			String note, String userName, String password, Integer heartCycle,
			String expand, Integer aicAmount, Integer aocAmount, String decode)
			throws BusinessException;

	/**
	 * 
	 * 查询告警信息
	 * 
	 * @param organId
	 *            机构ID
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param userId
	 *            用户ID
	 * @return List<ListDeviceAlarmVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:14:11
	 */
	public List<ListDeviceAlarmVO> listDeviceAlarm(String organId,
			String deviceName, String deviceType, String alarmType,
			Long startTime, Long endTime, Integer startIndex, Integer limit,
			String userId);

	/**
	 * 
	 * 返回查询设备告警总条数
	 * 
	 * @param organId
	 *            传入机构ID
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param userId
	 *            用户ID
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:20:55
	 */
	public int selectTotalCount(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			String userId);

	/**
	 * 
	 * 查询厂商列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * 
	 * @return List<Manufacturer>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:52:59
	 */
	public List<Manufacturer> listManufacturer(Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询厂商设备型号
	 * 
	 * @param manufacturerId
	 *            厂商ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<DeviceModelVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:13:16
	 */
	public List<DeviceModelVO> listDeviceModel(String manufacturerId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 根据视频服务器ID查询摄像头列表
	 * 
	 * @param dvrId
	 *            视频服务器ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询的总条数
	 * @return List<ListCameraVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:18:45
	 */
	public List<ListCameraVO> listCamera(String dvrId, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 更新视频服务器
	 * 
	 * @param id
	 *            视频服务器ID
	 * @param standardNumber
	 *            设备标准号
	 * @param subType
	 *            设备类型
	 * @param name
	 *            设备名称
	 * @param ptsId
	 *            PTS服务器ID
	 * @param transport
	 *            TCP-TCP流传输方式, RTP-RTP流传输方式
	 * @param mode
	 *            compatible-兼容, standard-标准
	 * @param maxConnect
	 *            最大连接数，决定走流媒体服务器的规则
	 * @param channelAmount
	 *            通道数量,CMS会根据这个数值来自动创建下面的摄像头.同时如果受License的限制,则最终只会自动创建部分通道的摄像头
	 * @param organId
	 *            设备所属机构ID
	 * @param linkType
	 *            保留字段，adsl等连接方式
	 * @param lanIp
	 *            IP地址
	 * @param port
	 *            连接端口
	 * @param manufacturerId
	 *            设备厂家ID
	 * @param deviceModelId
	 *            视频服务器型号ID
	 * @param location
	 *            安装位置
	 * @param note
	 *            备注
	 * @param userName
	 *            设备登录用户名
	 * @param password
	 *            设备登录密码
	 * @param heartCycle
	 *            心跳周期，单位：秒
	 * @param expand
	 *            扩展字段
	 * @param aicAmount
	 *            报警输入通道数量
	 * @param aocAmount
	 *            报警输出通道数量
	 * @param channelAmount
	 *            视频输入通道数量
	 * @param decode
	 *            解码器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:46:27
	 */
	@LogMethod(targetType = "Dvr", operationType = "update", name = "创建视频服务器", code = "2051")
	public void updateDvr(@LogParam("id") String id, String standardNumber,
			String subType, @LogParam("name") String name, String ptsId,
			String transport, String mode, Integer maxConnect, String organId,
			String linkType, String lanIp, Integer port, String manufacturerId,
			String deviceModelId, String location, String note,
			String userName, String password, Integer heartCycle,
			String expand, Integer aicAmount, Integer aocAmount,
			Integer channelAmount, String decode);

	/**
	 * 
	 * deleteDvr方法说明
	 * 
	 * @param id
	 *            视频服务器ID
	 * @param force
	 *            是否强制删除
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:39:47
	 */
	@LogMethod(targetType = "Dvr", operationType = "delete", name = "删除视频服务器", code = "2052")
	public void deleteDvr(@LogParam("id") String id, Boolean force);

	/**
	 * 
	 * 根据机构Id查询视频服务器列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return List<DvrVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:06:04
	 */
	public List<DvrVO> listDvr(String organId, Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询视频服务器
	 * 
	 * @param id
	 *            视频服务器ID
	 * @return GetDvrVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:38:25
	 */
	public GetDvrVO getDvr(String id);

	/**
	 * 
	 * 根据视频服务器ID查询摄像头
	 * 
	 * @param id
	 *            视频服务器ID
	 * @return List<Camera>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:32:03
	 */
	public List<Camera> findCameraByDvrId(String id);

	/**
	 * 
	 * 根据摄像头ID查询预置点
	 * 
	 * @param id
	 *            预置点ID
	 * @return List<Preset>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:01:48
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:13:16
	 */
	public List<Preset> findPresetByCameraId(String id);

	/**
	 * 
	 * 根据摄像头ID查询角色设备关联关系
	 * 
	 * @param id
	 *            摄像头ID
	 * @return List<RoleResourcePermission>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:43:04
	 */
	public List<RoleResourcePermission> findRRPByCameraId(String id);

	/**
	 * 
	 * 绑定预置点和图片
	 * 
	 * @param presetId
	 *            预置点ID
	 * @param imageId
	 *            图片ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:38:37
	 */
	public void bindImage(String presetId, String imageId);

	/**
	 * 
	 * 删除角色和摄像头关联
	 * 
	 * @param rrp
	 *            关联表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:43:44
	 */
	public void deleteRRPById(RoleResourcePermission rrp);

	/**
	 * 
	 * 创建厂商设备型号
	 * 
	 * @param name
	 *            设备名称
	 * @param type
	 *            设备类型
	 * @param manufacturerId
	 *            厂商型号
	 * @param note
	 *            备注
	 * @return Long
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:32:38
	 */
	@LogMethod(targetType = "DeviceModel", operationType = "create", name = "创建厂商设备型号", code = "2042")
	public String createDeviceModel(@LogParam("name") String name, String type,
			String manufacturerId, String note);

	/**
	 * 
	 * 更新厂商设备型号
	 * 
	 * @param id
	 *            设备型号ID
	 * @param name
	 *            设备名称
	 * @param type
	 *            设备类型
	 * @param manufacturerId
	 *            厂商ID
	 * @param note
	 *            备注
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:05:51
	 */
	@LogMethod(targetType = "DeviceModel", operationType = "update", name = "更新厂商设备型号", code = "2043")
	public void updateDeviceModel(@LogParam("id") String id,
			@LogParam("name") String name, String type, String manufacturerId,
			String note);

	/**
	 * 
	 * 删除厂商设备型号
	 * 
	 * @param id
	 *            设备型号ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:20:36
	 */
	@LogMethod(targetType = "DeviceModel", operationType = "delete", name = "删除厂商设备型号", code = "2044")
	public void deleteDeviceModel(@LogParam("id") String id);

	/**
	 * 
	 * 根据ID查询厂商设备型号
	 * 
	 * @param id
	 *            厂商设备型号ID
	 * @return DeviceModelVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:57:14
	 */
	public DeviceModelVO getDeviceModel(String id);

	/**
	 * 获取CCS管辖的设备列表
	 * 
	 * @param ccsId
	 *            CCS_ID
	 * @param start
	 *            DVR索引起始行
	 * @param limit
	 *            要查询的DVR数量
	 * @return 设备列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:12:10
	 */
	public List<PtsDvrVO> listDvrByCcs(String ccsId, int start, int limit);

	/**
	 * 
	 * 根据dvrId查询摄像头总数
	 * 
	 * @param dvrId
	 *            视频服务器ID
	 * @return 总数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:08:40
	 */
	public Integer cameraTotalCount(String dvrId);

	/**
	 * 统计CCS管辖的DVR数量
	 * 
	 * @param ccsId
	 *            PTS_ID
	 * @return DVR的数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:30:35
	 */
	public int countDvrByCcs(String ccsId);

	/**
	 * 
	 * 根据厂商ID查询设备型号总数
	 * 
	 * @param manufacturerId
	 *            厂商ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:18:37
	 */
	public Integer deviceModeTotalCount(String manufacturerId);

	/**
	 * 
	 * 根据机构ID查询DVR总计数
	 * 
	 * @param organId
	 *            机构ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:46:32
	 */
	public Integer dvrTotalCount(String organId);

	/**
	 * 自动批量创建视频服务器下面的摄像头通道
	 * 
	 * @param cameras
	 *            待创建的摄像头集合
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:56:21
	 */
	public void batchCreateDvrCameras(Camera[] cameras)
			throws BusinessException;

	/**
	 * 更新设备变化时间入库
	 * 
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:57:51
	 */
	public void updateDeviceUpdateListener() throws BusinessException;

	/**
	 * 
	 * 根据设备条件查询DVR
	 * 
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param ip
	 *            lanIp
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param logonUserId
	 *            用户登录ID
	 * @param organId
	 *            机构ID
	 * @return ListDvrDTO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:38:51
	 */
	public ListDvrDTO listDvrByDevice(String name, String standardNumber,
			String ip, Integer startIndex, Integer limit, String logonUserId,
			String organId);

	/**
	 * 设置摄像头的默认预置点
	 * 
	 * @param presetId
	 *            预置点ID
	 * @param set
	 *            true-设置，false-取消设置
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:15:18
	 */
	@LogMethod(targetType = "Preset", operationType = "setDefault", name = "设置默认预置点", code = "1019")
	public void setCameraDefaultPreset(@LogParam("id") String presetId,
			boolean set) throws BusinessException;

	/**
	 * 
	 * 查询厂商总计数
	 * 
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:55:21
	 */
	public Integer manufacturerTotalCount();

	/**
	 * 查询用户具有的所有摄像头的权限列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户具有的所有摄像头的权限列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 上午11:03:33
	 */
	public List<AuthCameraVO> listUserAuthCamera(String userId)
			throws BusinessException;

	/**
	 * 
	 * 设备统计
	 * 
	 * @param organId
	 *            机构ID
	 * @param deviceType
	 *            设备类型 1：所有设备类型 2：视频服务器 3：摄像头
	 * @return CountDeviceDTO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:25:03
	 */
	public CountDeviceDTO countDevice(String organId, String deviceType);

	/**
	 * 
	 * 检测数据流
	 * 
	 * @param in
	 *            Excel流
	 * @param organId
	 *            机构ID
	 * @param License
	 *            license文件
	 * @return Workbook
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:08:13
	 */
	public Workbook checkoutIo(InputStream in);

	/**
	 * 
	 * 读取excel中的dvr
	 * 
	 * @param wb
	 *            workbook
	 * @param organ
	 *            机构
	 * @return dvr列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:40:33
	 */
	public List<Dvr> readDvrWb(Workbook wb, Organ organ);

	/**
	 * 
	 * 读取excel中的摄像头
	 * 
	 * @param wb
	 *            workbook
	 * @param organ
	 *            机构
	 * @param license
	 *            许可证
	 * @param dvrs
	 *            dvr列表
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:27:51
	 */
	public List<Camera> readCameraWB(Workbook wb, Organ organ, License license,
			List<Dvr> dvrs);

	/**
	 * 
	 * 批量插入dvr和摄像头
	 * 
	 * @param dvrs
	 *            dvr列表
	 * @param cameras
	 *            摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:45:45
	 */
	public void batchInsertDvrAndCamera(List<Dvr> dvrs, List<Camera> cameras);

	/**
	 * 生成length数量的对象ID
	 * 
	 * @param className
	 *            对象名称，对应于hbm.xml文件中的Class.Name
	 * @param length
	 *            要生成的ID数量
	 * @return lenght长度的ID数组
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-8-29 上午9:16:13
	 */
	public String[] batchGenerateId(String className, int length);

	/**
	 * 
	 * 生成length数量的对象ID
	 * 
	 * @param className
	 *            dvr名称，对应于hbm.xml文件中的Class.Name
	 * @param length
	 *            要生成的ID数量
	 * @return lenght长度的ID数组
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:47:47
	 */
	public String[] batchDvrId(String className, int length);

	/**
	 * 
	 * 批量插入dvr
	 * 
	 * @param dvrs
	 *            dvr集合
	 * @param dvrIds
	 *            dvrId数组
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:50:52
	 */
	public void batchInsertDvr(List<Dvr> dvrs);

	/**
	 * 
	 * 批量插入camera
	 * 
	 * @param cameras
	 *            摄像头集合
	 * @param cameraIds
	 *            摄像头ids
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:02:00
	 */
	public void batchInsertCamera(List<Camera> cameras);

	/**
	 * 
	 * 根据传入的条数查询设备列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询总条数
	 * @param devices
	 *            所有设备
	 * @return 返回xml格式设备
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:47:12
	 */
	public Element listMcuDevice(Integer startIndex, Integer limit,
			List<AuthCameraVO> devices);

	/**
	 * 
	 * 创建火灾检测器
	 * 
	 * @param name
	 *            火灾检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            数采id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            端口
	 * @return 火灾检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:20:29
	 */
	@LogMethod(targetType = "FireDetector", operationType = "create", name = "创建火灾检测器", code = "2230")
	public String createFireDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 
	 * 更新火灾检测器
	 * 
	 * @param id
	 *            火灾检测器id
	 * @param name
	 *            火灾检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:34:26
	 */
	@LogMethod(targetType = "FireDetector", operationType = "update", name = "修改火灾检测器", code = "2231")
	public void updateFireDetector(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除火灾检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:18:29
	 */
	@LogMethod(targetType = "FireDetector", operationType = "delete", name = "删除火灾检测器", code = "2232")
	public void deleteFireDetector(@LogParam("id") String id);

	/**
	 * 
	 * 查询单个火灾检测器
	 * 
	 * @param id
	 *            火灾检测器id
	 * @return 火灾检测器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:26:02
	 */
	public GetFireDetectorVO getFireDetector(String id);

	/**
	 * 
	 * 根据条件查询火灾检测器总数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            火灾检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:43:08
	 */
	public Integer countFireDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件获取火灾检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return 火灾检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:55:10
	 */
	public List<GetFireDetectorVO> listFireDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 创建一氧化碳\能见度检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param coConctValue
	 *            一氧化碳阀值
	 * @param visibilityValue
	 *            能见度阀值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:12:01
	 */
	@LogMethod(targetType = "Covi", operationType = "create", name = "创建一氧化碳检测器", code = "2240")
	public String createCovi(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short coConctLimit, Short visibilityLimit,
			String note, String navigation, String reserve, String ip,
			Integer port);

	/**
	 * 
	 * 修改一氧化碳\能见度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param coConctValue
	 *            一氧化碳阀值
	 * @param visibilityValue
	 *            能见度阀值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:28:51
	 */
	@LogMethod(targetType = "Covi", operationType = "update", name = "修改一氧化碳检测器", code = "2241")
	public void updateCovi(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short coConctLimit,
			Short visibilityLimit, String note, String navigation,
			String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除一氧化碳\能见度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:42:36
	 */
	@LogMethod(targetType = "Covi", operationType = "delete", name = "删除一氧化碳检测器", code = "2242")
	public void deleteCovi(@LogParam("id") String id);

	/**
	 * 
	 * 获取单个一氧化碳\能见度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 检测器实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:52:04
	 */
	public GetCoviVO getCovi(String id);

	/**
	 * 
	 * 统计一氧化碳\能见度检测器总数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:02:24
	 */
	public Integer countCovi(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询一氧化碳\能见度检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return 一氧化碳\能见度检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:15:07
	 */
	public List<GetCoviVO> listCovi(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建手动报警按钮
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:49:01
	 */
	@LogMethod(targetType = "PushButton", operationType = "create", name = "创建手动报警按钮", code = "2260")
	public String createPushButton(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 
	 * 修改手动报警按钮
	 * 
	 * @param id
	 *            按钮id
	 * @param name
	 *            按钮名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            数据采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:00:55
	 */
	@LogMethod(targetType = "PushButton", operationType = "update", name = "修改手动报警按钮", code = "2261")
	public void updatePushButton(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除报警按钮
	 * 
	 * @param id
	 *            按钮id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:25:41
	 */
	@LogMethod(targetType = "PushButton", operationType = "delete", name = "删除手动报警按钮", code = "2262")
	public void deletePushButton(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询手动报警按钮
	 * 
	 * @param id
	 *            按钮id
	 * @return 手动报警按钮对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:40:01
	 */
	public GetPushButtonVO getPushButton(String id);

	/**
	 * 
	 * 根据条件查询手动报警按钮器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            按钮名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 手动报警按钮列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:48:12
	 */
	public List<GetPushButtonVO> listPushButton(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 查询手动报警按钮总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            按钮名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:49:50
	 */
	public Integer countPushButton(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 创建氮氧化合物检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param noConctValue
	 *            一氧化氮浓度阀值
	 * @param nooConctValue
	 *            二氧化氮浓度阀值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:45:23
	 */
	@LogMethod(targetType = "NoDetector", operationType = "create", name = "创建氮氧化合物", code = "2250")
	public String createNoDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short noConctLimit, Short nooConctLimit,
			String note, String navigation, String reserve, String ip,
			Integer port);

	/**
	 * 
	 * 修改氮氧化合物检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param noConctValue
	 *            一氧化氮浓度阀值
	 * @param nooConctValue
	 *            二氧化氮浓度阀值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:59:40
	 */
	@LogMethod(targetType = "NoDetector", operationType = "update", name = "修改氮氧化合物", code = "2251")
	public void updateNoDetector(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short noConctLimit,
			Short nooConctLimit, String note, String navigation,
			String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除氮氧化合物检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:08:19
	 */
	@LogMethod(targetType = "NoDetector", operationType = "delete", name = "删除氮氧化合物", code = "2252")
	public void deleteNoDetector(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询氮氧化合物检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 氮氧化合物实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:00:14
	 */
	public GetNoDetectorVO getNoDetector(String id);

	/**
	 * 
	 * 统计氮氧化合物检测器总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:04:17
	 */
	public Integer countNoDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询氮氧化合物检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 氮氧化合物检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:10:15
	 */
	public List<GetNoDetectorVO> listNoDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建光照强度检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param loLumiMax
	 *            洞外光照强度最大值
	 * @param loLumiMin
	 *            洞外光照强度最小值
	 * @param liLumiMax
	 *            洞内光照强度最大值
	 * @param liLumiMin
	 *            洞内光照强度最小值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:59:24
	 */
	@LogMethod(targetType = "LoLi", operationType = "create", name = "创建光强检测器", code = "2220")
	public String createLoli(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short loLumiMax, Short loLumiMin, Short liLumiMax,
			Short liLumiMin, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 
	 * 修改光照强度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param loLumiMax
	 *            洞外光照强度最大值
	 * @param loLumiMin
	 *            洞外光照强度最小值
	 * @param liLumiMax
	 *            洞内光照强度最大值
	 * @param liLumiMin
	 *            洞内光照强度最小值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:13:56
	 */
	@LogMethod(targetType = "LoLi", operationType = "update", name = "修改光强检测器", code = "2221")
	public void updateLoli(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short loLumiMax,
			Short loLumiMin, Short liLumiMax, Short liLumiMin, String note,
			String navigation, String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除光照强度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:23:09
	 */
	@LogMethod(targetType = "LoLi", operationType = "delete", name = "删除光强检测器", code = "2222")
	public void deleteLoli(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询光照强度检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 检测器实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:33:21
	 */
	public GetLoliVO getLoli(String id);

	/**
	 * 
	 * 统计光照强度检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            光照强度检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:50:02
	 */
	public Integer countLoli(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 获取光照强度检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:56:12
	 */
	public List<GetLoliVO> listLoli(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建气象检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param vLowLimit
	 *            能见度下限值
	 * @param wUpLimit
	 *            风速上限值
	 * @param rUpLimit
	 *            降雪量上限值
	 * @param sUpLimit
	 *            降雨量下限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:38:20
	 */
	@LogMethod(targetType = "WeatherStat", operationType = "create", name = "创建气象检测器", code = "2210")
	public String createWeatherStat(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short vLowLimit, Short wUpLimit, Short rUpLimit,
			Short sUpLimit, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 
	 * 修改气象检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param vLowLimit
	 *            能见度下限值
	 * @param wUpLimit
	 *            风速上限值
	 * @param rUpLimit
	 *            降雨量上限值
	 * @param sUpLimit
	 *            降雪量上限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:57:43
	 */
	@LogMethod(targetType = "WeatherStat", operationType = "update", name = "修改气象检测器", code = "2211")
	public void updateWeatherStat(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short vLowLimit, Short wUpLimit,
			Short rUpLimit, Short sUpLimit, String note, String navigation,
			String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除气象检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:05:11
	 */
	@LogMethod(targetType = "WeatherStat", operationType = "delete", name = "删除气象检测器", code = "2212")
	public void deleteWeatherStat(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询气象检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 气象检测器实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:11:22
	 */
	public GetWeatherStatVO getWeatherStat(String id);

	/**
	 * 
	 * 气象检测器总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:48:37
	 */
	public Integer countWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 查询气象检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:56:13
	 */
	public List<GetWeatherStatVO> listWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建车辆检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param sUpLimit
	 *            速度上限值
	 * @param sLowLimit
	 *            速度下限值
	 * @param oUpLimit
	 *            占有率上限值
	 * @param oLowLimit
	 *            占有率下限值
	 * @param vUpLimit
	 *            交通量上限值
	 * @param vLowLimit
	 *            交通量下限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param ip
	 *            ip地址
	 * @param port
	 *            端口
	 * @param laneNumber
	 *            车道数量
	 * @param reserve
	 *            厂商
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:44:23
	 */
	@LogMethod(targetType = "VehicleDetector", operationType = "create", name = "创建车辆检测器", code = "2200")
	public String createVehicleDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Integer sUpLimit, Integer sLowLimit,
			Integer oUpLimit, Integer oLowLimit, Integer vUpLimit,
			Integer vLowLimit, String note, String navigation, String ip,
			String port, String laneNumber, String reserve);

	/**
	 * 
	 * 修改车辆检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param sUpLimit
	 *            速度上限值
	 * @param sLowLimit
	 *            速度下限值
	 * @param oUpLimit
	 *            占有率上限值
	 * @param oLowLimit
	 *            占有率下限值
	 * @param vUpLimit
	 *            交通量上限值
	 * @param vLowLimit
	 *            交通量下限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param ip
	 *            ip地址
	 * @param port
	 *            通信端口
	 * @param laneNumber
	 *            车道数量
	 * @param reserve
	 *            厂商
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:54:00
	 */
	@LogMethod(targetType = "VehicleDetector", operationType = "update", name = "修改车辆检测器", code = "2201")
	public void updateVehicleDetector(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Integer sUpLimit,
			Integer sLowLimit, Integer oUpLimit, Integer oLowLimit,
			Integer vUpLimit, Integer vLowLimit, String note,
			String navigation, String ip, String port, String laneNumber,
			String reserve);

	/**
	 * 
	 * 删除车辆检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:07:42
	 */
	@LogMethod(targetType = "VehicleDetector", operationType = "delete", name = "删除车辆检测器", code = "2202")
	public void deleteVehicleDetector(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询车辆检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 检测器实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:42:42
	 */
	public GetVehicleDetectorVO getVehicleDetector(String id);

	/**
	 * 
	 * 条件查询车辆检测器总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:02:36
	 */
	public Integer countVehicleDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询车辆检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车辆检测器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:05:27
	 */
	public List<GetVehicleDetectorVO> listVehicleDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 创建风速风向检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param direction
	 *            风向
	 * @param wUpLimit
	 *            风速阀值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:53:29
	 */
	@LogMethod(targetType = "WindSpeed", operationType = "create", name = "创建风速风向检测器", code = "2190")
	public String createWindSpeed(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short direction, Integer wUpLimit, String note,
			String navigation, String reserve, String ip, Integer port);

	/**
	 * 
	 * 修改风速风向检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param direction
	 *            风向
	 * @param wUpLimit
	 *            风速最大上限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param reserve
	 *            厂商
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:35:48
	 */
	@LogMethod(targetType = "WindSpeed", operationType = "update", name = "修改风速风向检测器", code = "2191")
	public void updateWindSpeed(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short direction,
			Integer wUpLimit, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 
	 * 删除风速风向检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:41:08
	 */
	@LogMethod(targetType = "WindSpeed", operationType = "delete", name = "删除风速风向检测器", code = "2192")
	public void deleteWindSpeed(@LogParam("id") String id);

	/**
	 * 
	 * 根据id查询风速风向检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 检测器实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:50:14
	 */
	public GetWindSpeedVO getWindSpeed(String id);

	/**
	 * 
	 * 统计风速风向检测器计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 检测器总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:53:53
	 */
	public Integer countWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 条件查询风速风向检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 风速风向检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:01:55
	 */
	public List<GetWindSpeedVO> listWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建控制设备
	 * 
	 * @param name
	 *            控制设备名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param height
	 *            情报板高度
	 * @param width
	 *            情报板宽度
	 * @param sectionType
	 *            照明回路段类型
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 设备id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:20:23
	 */
	@LogMethod(targetType = "ControlDevice", operationType = "create", name = "创建控制设备", code = "2270")
	public String createControlDevice(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short type, Short subType, String note,
			String navigation, Integer height, Integer width,
			Short sectionType, String reserve, String ip, Integer port);

	/**
	 * 
	 * 修改控制设备
	 * 
	 * @param id
	 *            设备id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param height
	 *            情报板高度
	 * @param width
	 *            情报板宽度
	 * @param sectionType
	 *            照明回路段类型
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:33:35
	 */
	@LogMethod(targetType = "ControlDevice", operationType = "update", name = "修改控制设备", code = "2271")
	public void updateControlDevice(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short type, Short subType,
			String note, String navigation, Integer height, Integer width,
			Short sectionType, String reserve, String ip, Integer port);

	/**
	 * 
	 * 删除控制设备
	 * 
	 * @param id
	 *            控制设备id
	 * @param type
	 *            控制设备类型
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:02:11
	 */
	@LogMethod(targetType = "ControlDevice", operationType = "delete", name = "删除控制设备", code = "2272")
	public void deleteControlDevice(@LogParam("id") String id, String type);

	/**
	 * 
	 * 根据id查询控制设备
	 * 
	 * @param id
	 *            设备id
	 * @return 控制设备
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:26:27
	 */
	public GetControlDeviceVO getControlDevice(String id);

	/**
	 * 
	 * 根据条件查询控制设备列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询的条数
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @return 设备列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:39:26
	 */
	public List<GetControlDeviceVO> listControlDevice(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit, Short type, Short subType);

	/**
	 * 
	 * 统计控制设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:55:41
	 */
	public Integer countControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Short type, Short subType);

	/**
	 * 
	 * 根据条件查询摄像头列表
	 * 
	 * @param organs
	 *            机构以及子机构id
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param manufacturerId
	 *            厂商id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param standardNumber
	 *            标准号
	 * @return 摄像头集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:27:06
	 */
	public List<ListCameraVO> listCameraByDevice(String[] organs, String name,
			String stakeNumber, String manufacturerId, Integer startIndex,
			Integer limit, String standardNumber);

	/**
	 * 
	 * 根据条件查询摄像头数量
	 * 
	 * @param organs
	 *            机构以及机构id
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param manufacturerId
	 *            厂商id
	 * @param standardNumber
	 *            标准号
	 * @return 摄像头数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:28:21
	 */
	public Integer cameraByDeviceTotalCount(String[] organs, String name,
			String stakeNumber, String manufacturerId, String standardNumber);

	/**
	 * 创建太阳能电池
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param organId
	 *            机构id
	 * @param maxVoltage
	 *            最大电压
	 * @param minVoltage
	 *            最小电压
	 * @param batteryCapacity
	 *            电池电量
	 * @param storePlan
	 *            存储计划
	 * @param note
	 *            备注
	 * @param dasId
	 *            采集服务器id
	 * @param navigation
	 *            行车方向
	 * @param stakeNumber
	 *            桩号
	 * @param period
	 *            采集周期
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:58:07
	 */
	@LogMethod(targetType = "SolarBattery", operationType = "create", name = "创建太阳能电池", code = "2390")
	public String createSolarBattery(@LogParam("name") String name,
			String standardNumber, String organId, String maxVoltage,
			String minVoltage, String batteryCapacity, String storePlan,
			String note, String dasId, String navigation, String stakeNumber,
			String period);

	/**
	 * 修改太阳能电池
	 * 
	 * @param id
	 *            太阳能电池id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param organId
	 *            机构id
	 * @param maxVoltage
	 *            最大电压
	 * @param minVoltage
	 *            最小电压
	 * @param batteryCapacity
	 *            电池电量
	 * @param storePlan
	 *            存储计划
	 * @param note
	 *            备注
	 * @param dasId
	 *            采集服务器id
	 * @param navigation
	 *            行车方向
	 * @param stakeNumber
	 *            桩号
	 * @param period
	 *            采集周期
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:39:22
	 */
	@LogMethod(targetType = "SolarBattery", operationType = "update", name = "修改太阳能电池", code = "2391")
	public void updateSolarBattery(@LogParam("id") String id, String name,
			String standardNumber, String organId, String maxVoltage,
			String minVoltage, String batteryCapacity, String storePlan,
			String note, String dasId, String navigation, String stakeNumber,
			String period);

	/**
	 * 删除太阳能电池
	 * 
	 * @param id
	 *            电池id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:09:30
	 */
	@LogMethod(targetType = "SolarBattery", operationType = "delete", name = "删除太阳能电池", code = "2392")
	public void deleteSolarBattery(@LogParam("id") String id);

	/**
	 * 根据id获取太阳能电池
	 * 
	 * @param id
	 *            电池id
	 * @return 太阳能电池
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:19:35
	 */
	public GetSolarBatteryVO getSolarBattery(String id);

	/**
	 * 统计太阳能电池总数
	 * 
	 * @param name
	 *            名称
	 * @param organId
	 *            机构id
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:30:24
	 */
	public Integer solarBatteryTotalCount(String name, String organId);

	/**
	 * 条件查询太阳能电池列表
	 * 
	 * @param name
	 *            名称
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 太阳能电池列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:36:50
	 */
	public List<GetSolarBatteryVO> listSolarBattery(String name,
			String organId, Integer startIndex, Integer limit);

	/**
	 * 绑定太阳能电池设备
	 * 
	 * @param type
	 *            设备类型
	 * @param solarId
	 *            太阳能id
	 * @param json
	 *            设备json
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:56:40
	 */
	public void bindDeviceSolar(String type, String solarId, String json);

	/**
	 * 统计太阳能电池关联的摄像头
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            摄像头名称
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:41:05
	 */
	public int countOrganCamera(String organId, String solarId, String name);

	/**
	 * 查询太阳能电池关联的摄像头
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            摄像头名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 摄像头
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:45:22
	 */
	public List<DevicePermissionVO> listOrganCamera(String organId,
			String solarId, String name, int startIndex, int limit);

	/**
	 * 取消太阳能电池关联设备
	 * 
	 * @param solarId
	 *            太阳能电池id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:53:18
	 */
	public void removeSolarDevice(String solarId);

	/**
	 * 统计太阳能电池关联的车检器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            车检器名称
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:20:08
	 */
	public int countOrganVD(String organId, String solarId, String name);

	/**
	 * 查询太阳能电池关联车检器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param solarId
	 *            太阳能电池id
	 * @param name
	 *            车检器名称
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:25:37
	 */
	public List<DevicePermissionVO> listOrganVD(String organId, String solarId,
			String name, int startIndex, int limit);

	/**
	 * 根据桩号查询附近摄像头
	 * 
	 * @param stakeNumber
	 *            桩号
	 * @param navigation
	 *            行车方向
	 * @param organId
	 *            机构id
	 * @return 附近的摄像头
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:31:54
	 */
	public List<ListCameraVO> getNearCamera(String stakeNumber,
			String navigation, String organId);

	/**
	 * 创建箱变电力监测
	 * 
	 * @param name
	 *            箱变名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param maxVoltage
	 *            最大电压
	 * @param maxCurrents
	 *            最大电流
	 * @param maxCapacity
	 *            最大容量
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:54:57
	 */
	@LogMethod(targetType = "BoxTransformer", operationType = "create", name = "创建箱变电力监控", code = "2400")
	public String createBoxTransformer(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String maxVoltage,
			String maxCurrents, String maxCapacity, String reserve, String ip,
			Integer port);

	/**
	 * 更新箱变电力监测
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            箱变名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param maxVoltage
	 *            最大电压
	 * @param maxCurrents
	 *            最大电流
	 * @param maxCapacity
	 *            最大容量
	 * @param reserve
	 *            厂商
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:13:02
	 */
	@LogMethod(targetType = "BoxTransformer", operationType = "update", name = "修改箱变电力监控", code = "2401")
	public void updateBoxTransformer(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String maxVoltage,
			String maxCurrents, String maxCapacity, String reserve, String ip,
			Integer port);

	/**
	 * 删除箱变电力监测
	 * 
	 * @param id
	 *            id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:20:06
	 */
	@LogMethod(targetType = "BoxTransformer", operationType = "delete", name = "删除箱变电力监控", code = "2402")
	public void deleteBoxTransformer(@LogParam("id") String id);

	/**
	 * 根据id查询箱变电力监控
	 * 
	 * @param id
	 *            id
	 * @return 箱变电力监控
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:31:34
	 */
	public GetBoxTransformerVO getBoxTransformer(String id);

	/**
	 * 条件查询箱变电力监控
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:44:04
	 */
	public Integer countBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询箱变电力监控列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 箱变电力监控列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:48:38
	 */
	public List<GetBoxTransformerVO> listBoxTransformer(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 保存设备故障信息
	 * 
	 * @param id
	 *            故障记录对象ID，为空表示新增
	 * @param deviceName
	 *            设备名称
	 * @param standardNumber
	 *            设备编号
	 * @param deviceType
	 *            设备类型，参见{@link com.znsx.cms.service.common.TypeDefinition} 中的定义
	 * @param detectTime
	 *            发现时间
	 * @param status
	 *            故障状态，0-未确认，1-确认，2-处理中，3-已恢复，4-忽略
	 * @param confirmUser
	 *            确认人员
	 * @param maintainUser
	 *            维护人员
	 * @param reason
	 *            故障原因说明
	 * @param recoverTime
	 *            恢复时间
	 * @param organId
	 *            录入人员机构ID
	 * @param stakeNumber
	 *            设备桩号
	 * @param navigation
	 *            方向，0-上行，1-下行
	 * @return 故障记录对象ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-3 下午3:26:30
	 */
	public String saveDeviceFault(String id, String deviceName,
			String standardNumber, String deviceType, Long detectTime,
			Integer status, String confirmUser, String maintainUser,
			String reason, Long recoverTime, String organId,
			String stakeNumber, String navigation) throws BusinessException;

	/**
	 * 创建能见度仪
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param visibilityLimit
	 *            能见度阀值
	 * @param reserve
	 *            厂商
	 * @return 能见度仪id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:44:08
	 */
	@LogMethod(targetType = "ViDetector", operationType = "create", name = "创建能见度仪", code = "2410")
	public String createViDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer visibilityLimit, String reserve, String ip, Integer port);

	/**
	 * 更新能见度仪
	 * 
	 * @param id
	 *            能见度仪id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param visibilityLimit
	 *            能见度阀值
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:57:20
	 */
	@LogMethod(targetType = "ViDetector", operationType = "update", name = "修改能见度仪", code = "2411")
	public void updateViDetector(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer visibilityLimit, String reserve, String ip, Integer port);

	/**
	 * 删除能见度仪
	 * 
	 * @param id
	 *            能见度仪id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:04:49
	 */
	@LogMethod(targetType = "ViDetector", operationType = "delete", name = "删除能见度仪", code = "2412")
	public void deleteViDetector(@LogParam("id") String id);

	/**
	 * 根据id查询能见度仪
	 * 
	 * @param id
	 *            能见度仪id
	 * @return 能见度仪
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:32:32
	 */
	public GetViDetectorVO getViDetector(String id);

	/**
	 * 条件统计能见度仪数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 统计的数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:28:56
	 */
	public Integer countViDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 根据条件查询能见度仪列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 能见度仪列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:40:34
	 */
	public List<GetViDetectorVO> listViDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 创建路面检测器
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param roadTemperature
	 *            路面温度阀值
	 * @param waterThickness
	 *            水膜厚度阀值
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:55:36
	 */
	@LogMethod(targetType = "RoadDetector", operationType = "create", name = "创建路面检测器", code = "2420")
	public String createRoadDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer roadTemperature, Integer waterThickness, String reserve,
			String ip, Integer port);

	/**
	 * 修改路面检测器
	 * 
	 * @param id
	 *            检测器id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param roadTemperature
	 *            路面温度阀值
	 * @param waterThickness
	 *            水膜厚度阀值
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:53:59
	 */
	@LogMethod(targetType = "RoadDetector", operationType = "update", name = "修改路面检测器", code = "2421")
	public void updateRoadDetector(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer roadTemperature, Integer waterThickness, String reserve,
			String ip, Integer port);

	/**
	 * 删除路面检测器
	 * 
	 * @param id
	 *            检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:02:26
	 */
	@LogMethod(targetType = "RoadDetector", operationType = "delete", name = "删除路面检测器", code = "2422")
	public void deleteRoadDetector(@LogParam("id") String id);

	/**
	 * 根据id查询路面检测器
	 * 
	 * @param id
	 *            检测器id
	 * @return 路面检测器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:15:08
	 */
	public GetRoadDetectorVO getRoadDetector(String id);

	/**
	 * 统计路面检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 路面检测器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:22:54
	 */
	public Integer countRoadDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询路面检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:26:46
	 */
	public List<GetRoadDetectorVO> listRoadDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 创建桥面检测器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param bridgeTemperature
	 *            桥面温度阀值
	 * @param saltConcentration
	 *            盐浓度阀值
	 * @param mist
	 *            水雾阀值
	 * @param freezeTemperature
	 *            冰点温度阀值
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:07:22
	 */
	@LogMethod(targetType = "BridgeDetector", operationType = "create", name = "创建桥面检测器", code = "2430")
	public String createBridgeDetector(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer bridgeTemperature, Integer saltConcentration, Integer mist,
			Integer freezeTemperature, String reserve, String ip, Integer port);

	/**
	 * 修改桥面检测器
	 * 
	 * @param id
	 *            桥面检测器id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            行车方向
	 * @param bridgeTemperature
	 *            桥面温度阀值
	 * @param saltConcentration
	 *            盐浓度阀值
	 * @param mist
	 *            水雾阀值
	 * @param freezeTemperature
	 *            冰点温度阀值
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:22:55
	 */
	@LogMethod(targetType = "BridgeDetector", operationType = "update", name = "修改桥面检测器", code = "2431")
	public void updateBridgeDetector(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer bridgeTemperature, Integer saltConcentration, Integer mist,
			Integer freezeTemperature, String reserve, String ip, Integer port);

	/**
	 * 删除桥面检测器
	 * 
	 * @param id
	 *            桥面检测器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:29:28
	 */
	@LogMethod(targetType = "BridgeDetector", operationType = "delete", name = "删除桥面检测器", code = "2432")
	public void deleteBridgeDetector(@LogParam("id") String id);

	/**
	 * 根据id获取桥面检测器
	 * 
	 * @param id
	 *            桥面检测器id
	 * @return 桥面检测器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:46:48
	 */
	public GetBridgeDetectorVO getBridgeDetector(String id);

	/**
	 * 统计桥面检测器数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 统计数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:59:17
	 */
	public Integer countBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 条件查询桥面检测器列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 桥面检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:05:50
	 */
	public List<GetBridgeDetectorVO> listBridgeDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit);

	/**
	 * 创建子车检器
	 * 
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param sUpLimit
	 *            速度上限值
	 * @param sLowLimit
	 *            速度下限值
	 * @param oUpLimit
	 *            占有率上限值
	 * @param oLowLimit
	 *            占有率下限值
	 * @param vUpLimit
	 *            交通量上限值
	 * @param vLowLimit
	 *            交通量下限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param ip
	 *            ip
	 * @param port
	 *            端口
	 * @param laneNumber
	 *            车道数量
	 * @param parentId
	 *            车检器组id
	 * @param reserve
	 *            厂商
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:46:30
	 */
	public String createSubVehicleDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Integer sUpLimit,
			Integer sLowLimit, Integer oUpLimit, Integer oLowLimit,
			Integer vUpLimit, Integer vLowLimit, String note,
			String navigation, String ip, String port, String laneNumber,
			String parentId, String reserve);

	/**
	 * 修改子车检器
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            检测器名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param sUpLimit
	 *            速度上限值
	 * @param sLowLimit
	 *            速度下限值
	 * @param oUpLimit
	 *            占有率上限值
	 * @param oLowLimit
	 *            占有率下限值
	 * @param vUpLimit
	 *            交通量上限值
	 * @param vLowLimit
	 *            交通量下限值
	 * @param note
	 *            备注
	 * @param navigation
	 *            设备方向
	 * @param ip
	 *            ip
	 * @param port
	 *            端口
	 * @param laneNumber
	 *            车道数量
	 * @param parentId
	 *            车检器组id
	 * @param reserve
	 *            厂商
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:42:19
	 */
	public void updateSubVehicleDetector(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Integer sUpLimit, Integer sLowLimit,
			Integer oUpLimit, Integer oLowLimit, Integer vUpLimit,
			Integer vLowLimit, String note, String navigation, String ip,
			String port, String laneNumber, String parentId, String reserve);

	/**
	 * 删除子车检器
	 * 
	 * @param id
	 *            id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:49:44
	 */
	public void deleteSubVehicleDetector(String id);

	/**
	 * 根据id获取车车检器
	 * 
	 * @param id
	 *            id
	 * @return 子车检器
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:00:05
	 */
	public GetVehicleDetectorVO getSubVehicleDetector(String id);

	/**
	 * 统计子车检器数量
	 * 
	 * @param parentId
	 *            车检器组id
	 * @return 子车检器数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:09:19
	 */
	public Integer countSubVehicleDetector(String parentId);

	/**
	 * 条件查询子车检器列表
	 * 
	 * @param parentId
	 *            车检器组id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 子车检器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:13:37
	 */
	public List<GetVehicleDetectorVO> listSubVehicleDetector(String parentId,
			Integer startIndex, Integer limit);

	/**
	 * 创建紧急电话
	 * 
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集周期
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            车行方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @return 紧急电话id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:24:02
	 */
	@LogMethod(targetType = "UrgentPhone", operationType = "create", name = "创建紧急电话", code = "2440")
	public String createUrgentPhone(@LogParam("name") String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 修改紧急电话
	 * 
	 * @param id
	 *            紧急电话id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param dasId
	 *            采集服务器id
	 * @param organId
	 *            机构id
	 * @param period
	 *            采集时间
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            车行方向
	 * @param reserve
	 *            厂商
	 * @param ip
	 *            设备ip
	 * @param port
	 *            设备端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:35:47
	 */
	@LogMethod(targetType = "UrgentPhone", operationType = "update", name = "更新紧急电话", code = "2441")
	public void updateUrgentPhone(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port);

	/**
	 * 删除紧急电话
	 * 
	 * @param id
	 *            紧急电话id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:41:05
	 */
	@LogMethod(targetType = "UrgentPhone", operationType = "delete", name = "删除紧急电话", code = "2442")
	public void deleteUrgentPhone(@LogParam("id") String id);

	/**
	 * 根据id获取紧急电话
	 * 
	 * @param id
	 *            紧急电话id
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:50:11
	 */
	public GetUrgentPhoneVO getUrgentPhone(String id);

	/**
	 * 统计紧急电话数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:20:24
	 */
	public Integer countUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 查询紧急电话列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 紧急电话列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:24:56
	 */
	public List<GetUrgentPhoneVO> listUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 保存设备告警
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param status
	 *            告警状态0-未处理、1-正在处理、2-完成、3-忽略
	 * @param alarmTime
	 *            报警时间
	 * @param confirmTime
	 *            确认时间
	 * @param alarmContent
	 *            报警内容
	 * @param alarmType
	 *            报警类型 1-火灾报警、2-阀值报警
	 * @param deviceType
	 *            设备类型
	 * @param deviceId
	 *            设备id
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param currentValue
	 *            报警值
	 * @param threshold
	 *            对应阀值
	 * @param stakeNumber
	 *            桩号
	 * @return id
	 */
	public String saveDeviceAlarm(String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber);

	/**
	 * 修改设备告警
	 * 
	 * @param id
	 *            告警id
	 * @param deviceName
	 *            设备名称
	 * @param status
	 *            告警状态0-未处理、1-正在处理、2-完成、3-忽略
	 * @param alarmTime
	 *            报警时间
	 * @param confirmTime
	 *            确认时间
	 * @param alarmContent
	 *            报警内容
	 * @param alarmType
	 *            报警类型 1-火灾报警、2-阀值报警
	 * @param deviceType
	 *            设备类型
	 * @param deviceId
	 *            设备id
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param currentValue
	 *            报警值
	 * @param threshold
	 *            对应阀值
	 * @param stakeNumber
	 *            桩号
	 */
	public void updateDeviceAlarm(String id, String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber);

	/**
	 * 统计报警数量
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param alarmType
	 *            告警类型
	 * @return 统计数
	 */
	public Integer deviceAlarmTotalCount(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, String alarmType);

	/**
	 * 查询设备告警
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @param alarmType
	 * 
	 * @return 设备告警列表
	 */
	public List<DeviceAlarmVO> listDeviceAlarmByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType);

	/**
	 * 批量保存设备报警信息
	 * 
	 * @param list
	 *            设备报警信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-15 下午3:14:30
	 */
	public void batchSaveDeviceAlarm(List<DeviceAlarmReal> list)
			throws BusinessException;

	/**
	 * 更新设备在线离线记录
	 * 
	 * @param list
	 *            设备报警信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-16 下午3:45:08
	 */
	public void updateDeviceOnline(List<DeviceAlarmReal> list)
			throws BusinessException;

	/**
	 * 统计指定设备在线时长(单位：毫秒)
	 * 
	 * @param standardNumber
	 *            设备SN
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 设备在线时长(单位：毫秒)
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-17 下午2:35:32
	 */
	public long statDeviceOnlineTime(String standardNumber, Long begin, Long end);

	/**
	 * 设备播放排行
	 * 
	 * @param userId
	 *            用户ID
	 * @param limit
	 *            返回的最大记录数
	 * @return 设备播放排行
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-22 上午11:56:34
	 */
	public List<TopRealPlayLog> topRealPlay(String userId, int limit);

	/**
	 * 
	 * 统计机构下设备在线数
	 * 
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 在线数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:06:03
	 */
	public List<OrganDeviceOnline> listOrganDeviceOnline(String organId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计机构数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 机构数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午2:35:06
	 */
	public Integer organTotalCount(String organId);

	/**
	 * 
	 * 统计设备检测列表
	 * 
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param deviceName
	 *            设备名称
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午11:22:10
	 */
	public List<OrganDeviceCheck> listOrganDeviceCheck(String organId,
			Long beginTime, Long endTime, String deviceName,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 根据sn统计设备在线历史数量
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param beginTime
	 *            开始查询时间
	 * @param endTime
	 *            结束时间
	 * @return 设备在线历史列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:37:15
	 */
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime);

	/**
	 * 
	 * 根据sn查询设备在线历史列表
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 设备在线历史列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:53:20
	 */
	public List<DeviceOnlineHistroyVO> listDeviceOnlineHistory(
			String standardNumber, Long beginTime, Long endTime,
			Integer startIndex, Integer limit);

	/**
	 * 修改设备告警
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param status
	 *            告警状态0-未处理、1-正在处理、2-完成、3-忽略
	 * @param alarmTime
	 *            报警时间
	 * @param confirmTime
	 *            确认时间
	 * @param alarmContent
	 *            报警内容
	 * @param alarmType
	 *            报警类型 1-火灾报警、2-阀值报警
	 * @param deviceType
	 *            设备类型
	 * @param deviceId
	 *            设备id
	 * @param organId
	 *            机构id
	 * @param standardNumber
	 *            标准号
	 * @param currentValue
	 *            报警值
	 * @param threshold
	 *            对应阀值
	 * @param stakeNumber
	 *            桩号
	 * @param confirmUser
	 *            监控员(登录账号)
	 * @param maintainUser
	 *            确认人
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:55:23
	 */
	public void updateAlarmBySN(String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber, String confirmUser,
			String maintainUser);

	/**
	 * 查询用户具有权限的所有资源列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户具有权限的所有资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-04-29 下午3:31:49
	 */
	public Collection<UserResourceVO> listUserResource(String userId);

	/**
	 * 
	 * 条件查询告警历史数量
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param alarmType
	 *            告警类型
	 * @return 告警历史数量
	 * @author wanngbinyu
	 *         <p />
	 *         Create at 2015 下午4:27:16
	 */
	public Integer deviceAlarmRealTotalCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType);

	/**
	 * 
	 * 根据条件查询告警历史列表
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @param alarmType
	 *            告警类型
	 * @return 历史告警列表s
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:30:27
	 */
	public List<DeviceAlarmVO> listDeviceAlarmRealByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType);

	/**
	 * 
	 * 设备在线报表
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organId
	 *            机构id
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @return 报表json数据
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午6:02:02
	 */
	public CountDeviceDTO countDeviceOnline(Long beginTime, Long endTime,
			String organId, String deviceType, String alarmType);

	/**
	 * 
	 * 统计机构下的dvr设备数量
	 * 
	 * @param alarmType
	 *            报警类型
	 * @param deviceName
	 *            设备名称
	 * @param organId
	 *            机构id
	 * @return dvr数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午1:58:19
	 */
	public Integer deviceCheckTotalCount(String alarmType, String deviceName,
			String[] organs);

	/**
	 * 查询设备状态，包括下级平台
	 * 
	 * @param sns
	 *            设备SN列表
	 * @return 设备状态
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-8 上午11:26:09
	 */
	public List<Element> listDeviceStatus(List<String> sns);

	/**
	 * 查询本平台摄像头状态
	 * 
	 * @return 摄像头状态列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-27 上午11:20:09
	 */
	public List<CameraStatusVO> listLocalCameraStatus();

	/**
	 * 
	 * createGPSDevice方法说明
	 * 
	 * @param name
	 *            设备名称
	 * @param port
	 *            端口
	 * @param standardNumber
	 *            标准号
	 * @param location
	 *            地址
	 * @param dasId
	 *            采集服务器id
	 * @param deviceModelId
	 *            厂商设备型号id
	 * @param manufacturerId
	 *            厂商id
	 * @param ccsId
	 *            通信服务器id
	 * @param lanIp
	 *            内外ip
	 * @param wanIp
	 *            外网ip
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            方向
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2下午4:34:48
	 */
	@LogMethod(targetType = "GPSDevice", operationType = "create", name = "创建GPS设备", code = "2450")
	public String createGPSDevice(@LogParam("name") String name, Integer port,
			String standardNumber, String location, String dasId,
			String deviceModelId, String manufacturerId, String ccsId,
			String lanIp, String wanIp, String organId, String stakeNumber,
			String longitude, String latitude, String note, String navigation);

	/**
	 * 
	 * 更新gps设备
	 * 
	 * @param id
	 *            设备id
	 * @param name
	 *            设备名称
	 * @param port
	 *            端口
	 * @param standardNumber
	 *            标准号
	 * @param location
	 *            地址
	 * @param dasId
	 *            采集服务器id
	 * @param deviceModelId
	 *            厂商设备型号id
	 * @param manufacturerId
	 *            厂商id
	 * @param ccsId
	 *            通信服务器id
	 * @param lanIp
	 *            内外ip
	 * @param wanIp
	 *            外网ip
	 * @param organId
	 *            机构id
	 * @param stakeNumber
	 *            桩号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param note
	 *            备注
	 * @param navigation
	 *            方向
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午5:15:47
	 */
	@LogMethod(targetType = "GPSDevice", operationType = "update", name = "更新GPS设备", code = "2451")
	public void updateGPSDevice(@LogParam("id") String id, String name,
			Integer port, String standardNumber, String location, String dasId,
			String deviceModelId, String manufacturerId, String ccsId,
			String lanIp, String wanIp, String organId, String stakeNumber,
			String longitude, String latitude, String note, String navigation);

	/**
	 * 删除gps设备
	 * 
	 * @param id
	 *            设备id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午5:29:08
	 */
	@LogMethod(targetType = "GPSDevice", operationType = "delete", name = "删除GPS设备", code = "2452")
	public void deleteGPSDevice(@LogParam("id") String id);

	/**
	 * 
	 * 根据id获取gps设备
	 * 
	 * @param id
	 *            设备id
	 * @return gps设备
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午5:38:38
	 */
	public DeviceGPSVO getGPSDevice(String id);

	/**
	 * 
	 * 条件查询gps设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 设备数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午5:52:53
	 */
	public Integer countGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 条件查询gps设备列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 设备数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-2 下午6:02:57
	 */
	public List<DeviceGPSVO> listGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * bindDeviceGPS方法说明
	 * 
	 * @param type
	 *            设备类型
	 * @param gpsId
	 *            gps设备id
	 * @param json
	 *            绑定id数组
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-4 上午10:57:27
	 */
	public void bindDeviceGPS(String type, String gpsId, String json);

	/**
	 * 
	 * 查询绑定摄像头
	 * 
	 * @param organId
	 *            机构id
	 * @param gpsId
	 *            gps设备id
	 * @param name
	 *            摄像头名称
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-4 下午2:40:27
	 */
	public List<DevicePermissionVO> listOrganCameraByGPS(String organId,
			String gpsId, String name, int startIndex, int limit);

	/**
	 * 
	 * 取消gps和摄像头关联
	 * 
	 * @param gpsId
	 *            gps设备id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-4 下午3:01:25
	 */
	public void removeGPSDevice(String gpsId);

	/**
	 * 
	 * 统计告警历史表确认为null的条数
	 * 
	 * @param deviceName
	 *            设备名称
	 * @param deviceType
	 *            设备类型
	 * @param organId
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param alarmType
	 *            报警类型
	 * @return 条数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-10 下午2:03:57
	 */
	public Integer deviceAlarmTotalFlagNullCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType);

	/**
	 * 
	 * 条件查询告警数量
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构ID数组
	 * @param alarmType
	 *            报警类型
	 * @return 告警历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-17 下午2:19:22
	 */
	public Integer deviceAlarmRealTotalCount(String deviceId,
			String deviceType, String[] organs, String alarmType);

	/**
	 * 
	 * 条件查询告警历史数量
	 * 
	 * @param deviceId
	 *            设备ID
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构ID数组
	 * @param alarmType
	 *            报警类型
	 * @return 告警历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-17 下午3:20:09
	 */
	public Integer deviceAlarmTotalFlagNullCount(String deviceId,
			String deviceType, String[] organs, String alarmType);

	/**
	 * 
	 * 根据条件查询告警列表
	 * 
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param organs
	 *            机构id
	 * @param alarmType
	 *            报警类型
	 * @return 告警列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-17 下午3:25:05
	 */
	public List<DeviceAlarmVO> listDeviceAlarmRealByOrganId(String deviceId,
			String deviceType, String[] organs, String alarmType);

	/**
	 * 
	 * 条件统计设备报警历史数量[全部报警、已确认报警、未确认报警数量]
	 * 
	 * @param organs
	 *            机构id
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param type
	 *            是否确认类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 报警历史数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午11:03:34
	 */
	public int[] deviceHistoryTotalCount(String[] organs, String deviceId,
			String deviceType, String alarmType, String type, Long beginTime,
			Long endTime);

	/**
	 * 
	 * 条件统计设备报警历史列表
	 * 
	 * @param organs
	 *            机构id
	 * @param deviceId
	 *            设备id
	 * @param deviceType
	 *            设备类型
	 * @param alarmType
	 *            报警类型
	 * @param type
	 *            是否确认类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 报警历史列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-18 上午11:20:13
	 */
	public List<DeviceOnlineHistroyVO> listDeviceAlarmHistory(String[] organs,
			String deviceId, String deviceType, String alarmType, String type,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询设备报警记录
	 * 
	 * @param organs
	 *            机构id
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要查询条数
	 * @return 报警记录
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-19 上午10:42:45
	 */
	public List<DeviceAlarmStatusVO> listDeviceAlarmStatus(String[] organs,
			Long beginTime, Long endTime, Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计单个设备报警列表
	 * 
	 * @param deviceId
	 *            设备id
	 * @param alarmType
	 *            报警类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 设备报警列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-19 下午4:48:36
	 */
	public List<DeviceOnlineHistroyVO> listAlarmByDevice(String deviceId,
			String alarmType, Long beginTime, Long endTime, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 批量创建数据设备
	 * 
	 * @param type
	 *            数据设备类型
	 * @param device
	 *            设备集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-29 上午11:10:36
	 */
	public void batchSaveDataDevice(String type, List<Element> device);

	/**
	 * 查询在线设备列表
	 * 
	 * @param sns
	 *            设备sn
	 * @return 在线设备列表
	 */
	public List<Element> listDeviceStatus1(List<String> sns);

	/**
	 * 
	 * 根据机构id查询本机以及下级机构设备
	 * 
	 * @param organId
	 *            机构id
	 * @return Map<String, List<Camera>>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-11-27 上午10:25:29
	 */
	public Map<String, List<Camera>> listOrganCamera(String organId);

	/**
	 * 
	 * 创建机构设备树
	 * 
	 * @param organId
	 *            机构id
	 * @param map
	 *            机构设备map
	 * @return ListOrganDeviceTreeVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-11-27 上午11:11:36
	 */
	public ListOrganDeviceTreeVO organDeviceTree(String organId,
			Map<String, List<Camera>> map);

	/**
	 * 获取所有的摄像头列表
	 * 
	 * @return 所有的摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-12-2 下午12:00:01
	 */
	public List<Camera> listCameras();
}
