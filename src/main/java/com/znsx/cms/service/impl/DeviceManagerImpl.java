package com.znsx.cms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.id.UUIDHexGenerator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.BoxTransformerDAO;
import com.znsx.cms.persistent.dao.BridgeDetectorDAO;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.CrsDAO;
import com.znsx.cms.persistent.dao.DasDAO;
import com.znsx.cms.persistent.dao.DeviceAlarmDAO;
import com.znsx.cms.persistent.dao.DeviceAlarmRealDAO;
import com.znsx.cms.persistent.dao.DeviceModelDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineRealDAO;
import com.znsx.cms.persistent.dao.DeviceUpdateListenerDAO;
import com.znsx.cms.persistent.dao.DisplayWallDAO;
import com.znsx.cms.persistent.dao.DvrDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.GPSDeviceDAO;
import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.ManufacturerDAO;
import com.znsx.cms.persistent.dao.MssDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PresetDAO;
import com.znsx.cms.persistent.dao.PtsDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.RmsDAO;
import com.znsx.cms.persistent.dao.RoadDetectorDAO;
import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.dao.RoleResourcePermissionDAO;
import com.znsx.cms.persistent.dao.SolarBatteryDAO;
import com.znsx.cms.persistent.dao.SolarDeviceDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.SubVehicleDetectorDAO;
import com.znsx.cms.persistent.dao.SysLogDAO;
import com.znsx.cms.persistent.dao.TmDeviceDAO;
import com.znsx.cms.persistent.dao.UrgentPhoneDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserFavoriteDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.ViDetectorDAO;
import com.znsx.cms.persistent.dao.VideoDevicePropertyDAO;
import com.znsx.cms.persistent.dao.WareHouseDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.BoxTransformer;
import com.znsx.cms.persistent.model.BridgeDetector;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceIs;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceRail;
import com.znsx.cms.persistent.model.ControlDeviceRd;
import com.znsx.cms.persistent.model.ControlDeviceTsl;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.DeviceAlarm;
import com.znsx.cms.persistent.model.DeviceAlarmReal;
import com.znsx.cms.persistent.model.DeviceModel;
import com.znsx.cms.persistent.model.DeviceOnline;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.DeviceUpdateListener;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.GPSDeviceCamera;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.Manufacturer;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganBridge;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.OrganTunnel;
import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.RoadDetector;
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
import com.znsx.cms.persistent.model.RoleResourcePermissionWp;
import com.znsx.cms.persistent.model.RoleResourcePermissionWs;
import com.znsx.cms.persistent.model.RoleResourcePermissionWst;
import com.znsx.cms.persistent.model.SolarBattery;
import com.znsx.cms.persistent.model.SolarDevice;
import com.znsx.cms.persistent.model.SolarDeviceCamera;
import com.znsx.cms.persistent.model.SolarDeviceVD;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.SubVehicleDetector;
import com.znsx.cms.persistent.model.TmDevice;
import com.znsx.cms.persistent.model.Tollgate;
import com.znsx.cms.persistent.model.UrgentPhone;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.ViDetector;
import com.znsx.cms.persistent.model.VideoDeviceProperty;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.StandardObjectCode;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.AuthDeviceVO;
import com.znsx.cms.service.model.CameraStatusVO;
import com.znsx.cms.service.model.CountDataTypeVO;
import com.znsx.cms.service.model.DeviceAlarmNumberVO;
import com.znsx.cms.service.model.DeviceAlarmStatusVO;
import com.znsx.cms.service.model.DeviceAlarmVO;
import com.znsx.cms.service.model.DeviceGPSVO;
import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.service.model.DeviceOnlineHistroyVO;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.DeviceSolarVO;
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
import com.znsx.cms.service.model.PtsCameraVO;
import com.znsx.cms.service.model.PtsDvrVO;
import com.znsx.cms.service.model.RoleResourcePermissionVO;
import com.znsx.cms.service.model.TemplateDvrIsChannelNumberVO;
import com.znsx.cms.service.model.TopRealPlayLog;
import com.znsx.cms.service.model.UserResourceVO;
import com.znsx.cms.web.dto.omc.CountDeviceDTO;
import com.znsx.cms.web.dto.omc.ListDvrDTO;
import com.znsx.util.base64.Base64Utils;
import com.znsx.util.file.Configuration;
import com.znsx.util.network.NetworkUtil;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * 设备业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:39:29
 */
public class DeviceManagerImpl extends BaseManagerImpl implements DeviceManager {
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private VideoDevicePropertyDAO videoDevicePropertyDAO;
	@Autowired
	private CcsDAO ccsDAO;
	@Autowired
	private CrsDAO crsDAO;
	@Autowired
	private MssDAO mssDAO;
	@Autowired
	private PtsDAO ptsDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private DvrDAO dvrDAO;
	@Autowired
	private PresetDAO presetDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleResourcePermissionDAO roleResourcePermissionDAO;
	@Autowired
	private ManufacturerDAO manufacturerDAO;
	@Autowired
	private DeviceModelDAO deviceModelDAO;
	@Autowired
	private DeviceAlarmDAO deviceAlarmDAO;
	@Autowired
	private UserFavoriteDAO userFavoriteDAO;
	@Autowired
	private DeviceUpdateListenerDAO deviceUpdateListenerDAO;
	@Autowired
	private FireDetectorDAO fireDetectorDAO;
	@Autowired
	private DasDAO dasDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private WindSpeedDAO wsDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private NoDetectorDAO noDetectorDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private PushButtonDAO pushButtonDAO;
	@Autowired
	private WareHouseDAO wareHouseDAO;
	@Autowired
	private SolarBatteryDAO solarBatteryDAO;
	@Autowired
	private SolarDeviceDAO solarDeviceDAO;
	@Autowired
	private BoxTransformerDAO boxTransformerDAO;
	@Autowired
	private StandardNumberDAO snDAO;
	@Autowired
	private ViDetectorDAO viDetectorDAO;
	@Autowired
	private RoadDetectorDAO roadDetectorDAO;
	@Autowired
	private BridgeDetectorDAO bridgeDetectorDAO;
	@Autowired
	private SubVehicleDetectorDAO subVehicleDetectorDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;
	@Autowired
	private UrgentPhoneDAO urgentPhoneDAO;
	@Autowired
	private DisplayWallDAO displayWallDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private DeviceOnlineDAO deviceOnlineDAO;
	@Autowired
	private DeviceOnlineRealDAO deviceOnlineRealDAO;
	@Autowired
	private SysLogDAO sysLogDAO;
	@Autowired
	private DeviceAlarmRealDAO deviceAlarmRealDAO;
	@Autowired
	private RmsDAO rmsDAO;
	@Autowired
	private TmDeviceDAO tmDeviceDAO;
	@Autowired
	private GPSDeviceDAO gpsDeviceDAO;

	/**
	 * 设备更新时间记录
	 */
	public static long deviceUpdateTime = 0L;

	/**
	 * 设备更新存储计划时间纪录
	 */
	public static long deviceCrsUpdeateTime = 0L;

	public String createCamera(String standardNumber, String subType,
			@LogParam("name") String name, String organId,
			String manufacturerId, String location, String note,
			Short storeType, String localStorePlan, String centerStorePlan,
			String streamType, String parentId, String mssId, String crsId,
			Short channelNumber, String deviceModelId, String expand,
			String navigation, String stakeNumber, String owner,
			String civilCode, Double block, String certNum,
			Integer certifiable, Integer errCode, Long endTime, String rmsId,
			String storeStream) throws BusinessException {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Camera> list = cameraDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = cameraDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// 检查通道号是否重复
		params.clear();
		params.put("channelNumber", channelNumber);
		params.put("parent.id", parentId);
		list = cameraDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
					"channelId[" + channelNumber + "] is already exist !");
		}

		// 检查DVR通道数量是否达到最大数量
		Dvr parent = dvrDAO.findById(parentId);
		int channelAmount = parent.getChannelAmount().intValue();
		int count = cameraDAO.cameraTotalCount(parentId).intValue();
		if (count >= channelAmount) {
			throw new BusinessException(ErrorCode.CHANNEL_AMOUNT_OVER_LIMIT,
					"Channel amount[" + channelAmount + "] over limit !");
		}

		// String id = cameraDAO.getNextId("Camera", 1);
		Camera camera = new Camera();
		// camera.setId(id);
		camera.setChannelNumber(channelNumber);
		camera.setCreateTime(System.currentTimeMillis());
		camera.setLocation(location);
		camera.setName(name);
		camera.setNote(note);
		camera.setStandardNumber(standardNumber);
		camera.setSubType(subType);
		camera.setType(TypeDefinition.DEVICE_TYPE_CAMERA);

		camera.setCrs(StringUtils.isNotBlank(crsId) ? crsDAO.findById(crsId)
				: null);
		camera.setRms(StringUtils.isNotBlank(rmsId) ? rmsDAO.findById(rmsId)
				: null);
		if (StringUtils.isNotBlank(manufacturerId)) {
			camera.setManufacturer(manufacturerDAO.findById(manufacturerId));
		}
		if (StringUtils.isNotBlank(deviceModelId)) {
			camera.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		}
		camera.setMss(StringUtils.isNotBlank(mssId) ? mssDAO.findById(mssId)
				: null);
		camera.setOrgan(organDAO.findById(organId));
		camera.setParent(parent);
		camera.setStatus(0);
		camera.setNavigation(navigation);
		camera.setStakeNumber(stakeNumber);
		cameraDAO.save(camera);
		// 添加property
		VideoDeviceProperty property = new VideoDeviceProperty();

		if (storeType == 0) {
			property.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			property.setCenterStorePlan(null);
		} else if (storeType == 1) {
			if (StringUtils.isNotBlank(crsId)) {
				property.setCenterStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
				property.setLocalStorePlan(null);
			} else {
				property.setCenterStorePlan(null);
				property.setLocalStorePlan(null);
			}
		} else if (storeType == 2) {
			if (StringUtils.isNotBlank(crsId)) {
				property.setCenterStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
				property.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			} else {
				property.setCenterStorePlan(null);
				property.setLocalStorePlan(TypeDefinition.LOCAL_STORE_PLAN_DEFAULT);
			}
		} else {
			property.setCenterStorePlan(centerStorePlan);
			property.setLocalStorePlan(localStorePlan);
		}
		property.setDevice(camera);
		property.setStreamType(streamType);
		property.setStoreType(storeType);
		property.setExpand(expand);
		property.setOwner(owner);
		property.setCivilCode(civilCode);
		property.setBlock(block);
		property.setCertNum(certNum);
		property.setCertifiable(certifiable);
		property.setErrCode(errCode);
		property.setEndTime(endTime);
		property.setStoreStream(storeStream);

		videoDevicePropertyDAO.save(property);
		camera.setProperty(property);
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();

		// 设备存储计划更新时间修改
		deviceCrsUpdeateTime = System.currentTimeMillis();

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_CAMERA);
		return camera.getId();
	}

	@Override
	public GetCameraVO getCamera(String id) throws BusinessException {
		try {
			Camera camera = cameraDAO.findById(id);
			GetCameraVO vo = new GetCameraVO();
			VideoDeviceProperty property = camera.getProperty();
			vo.setCcsId(camera.getParent().getCcs() != null ? camera
					.getParent().getCcs().getId() : "");
			vo.setCcsName(camera.getParent().getCcs() != null ? camera
					.getParent().getCcs().getName() : "");
			vo.setCenterStorePlan(property.getCenterStorePlan());
			vo.setChannelId(camera.getChannelNumber() + "");
			vo.setCreateTime(camera.getCreateTime() + "");
			vo.setCrsId(camera.getCrs() != null ? camera.getCrs().getId() : "");
			vo.setCrsName(camera.getCrs() != null ? camera.getCrs().getName()
					: "");
			vo.setExpand(property.getExpand());
			vo.setId(camera.getId());
			vo.setLocalStorePlan(property.getLocalStorePlan());
			vo.setLocation(camera.getLocation());
			vo.setStreamType(property.getStreamType());
			vo.setMssId(camera.getMss() != null ? camera.getMss().getId() : "");
			vo.setMssName(camera.getMss() != null ? camera.getMss().getName()
					: "");
			vo.setName(camera.getName());
			vo.setNote(camera.getNote());
			vo.setOrganId(camera.getOrgan().getId());
			vo.setOrganName(camera.getOrgan().getName());
			vo.setParentId(camera.getParent().getId());
			vo.setParentName(camera.getParent().getName());
			vo.setPtsId(camera.getParent().getPts() != null ? camera
					.getParent().getPts().getId() : "");
			vo.setPtsName(camera.getParent().getPts() != null ? camera
					.getParent().getPts().getName() : "");
			vo.setStandardNumber(camera.getStandardNumber());
			vo.setStoreType(property.getStoreType() + "");
			vo.setSubType(camera.getSubType());
			vo.setType(TypeDefinition.DEVICE_TYPE_CAMERA + "");
			vo.setManufacturerId(camera.getManufacturer() != null ? camera
					.getManufacturer().getId().toString() : "");
			vo.setManufacturerName(camera.getManufacturer() != null ? camera
					.getManufacturer().getName() : "");
			vo.setDeviceModelId(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getId().toString() : "");
			vo.setDeviceModelName(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getName() : "");
			vo.setNavigation(camera.getNavigation());
			vo.setStakeNumber(camera.getStakeNumber());
			vo.setDvrName(camera.getParent().getName());
			vo.setOwner(property.getOwner());
			vo.setCivilCode(property.getCivilCode());
			vo.setBlock(property.getBlock() + "");
			vo.setCertNum(property.getCertNum());
			vo.setCertifiable(property.getCertifiable() + "");
			vo.setErrCode(property.getErrCode() + "");
			vo.setEndTime(property.getEndTime() + "");
			vo.setRmsId(camera.getRms() != null ? camera.getRms().getId() : "");
			vo.setRmsName(camera.getRms() != null ? camera.getRms().getName()
					: "");
			vo.setStoreStream(property.getStoreStream());
			return vo;
		} catch (BusinessException b) {
			System.out.println("create lower platform preset log");
			SubPlatformResource resource = subPlatformResourceDAO.findById(id);
			GetCameraVO vo = new GetCameraVO();
			vo.setName(resource.getName());
			return vo;
		}

	}

	public void updateCamera(String id, String standardNumber, String subType,
			String name, String organId, String manufacturerId,
			String location, String note, Short storeType,
			String localStorePlan, String centerStorePlan, String streamType,
			String parentId, String mssId, String crsId, Short channelNumber,
			String deviceModelId, String expand, String navigation,
			String stakeNumber, String owner, String civilCode, Double block,
			String certNum, Integer certifiable, Integer errCode, Long endTime,
			String rmsId, String storeStream) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Camera> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = cameraDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = cameraDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		// 检查通道号是否重复
		params.clear();
		params.put("channelNumber", channelNumber);
		params.put("parent.id", parentId);
		list = cameraDAO.findByPropertys(params);
		if (list.size() >= 1) {
			if (!id.equals(list.get(0).getId())) {
				throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
						"channelId[" + channelNumber + "] is already exist !");
			}
		}

		// 是否更新存储计划，如果更新则修改时间
		boolean isUpdateCrs = false;

		Camera camera = cameraDAO.findById(id);
		if (null != channelNumber) {
			camera.setChannelNumber(channelNumber);
		}
		if (null != location) {
			camera.setLocation(location);
		}
		if (null != name) {
			camera.setName(name);
		}
		if (null != note) {
			camera.setNote(note);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(camera.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_CAMERA);
			camera.setStandardNumber(standardNumber);
		}
		if (null != subType) {
			camera.setSubType(subType);
		}
		if (null != crsId) {
			if (!crsId.equals(camera.getCrs() != null ? camera.getCrs().getId()
					: "")) {
				isUpdateCrs = true;
			}
			camera.setCrs(StringUtils.isNotBlank(crsId) ? crsDAO
					.findById(crsId) : null);
		}
		if (null != rmsId) {
			camera.setRms(StringUtils.isNotBlank(rmsId) ? rmsDAO
					.findById(rmsId) : null);
		}
		if (StringUtils.isNotBlank(manufacturerId)) {
			camera.setManufacturer(manufacturerDAO.findById(manufacturerId));
		}
		if (StringUtils.isNotBlank(deviceModelId)) {
			camera.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		} else {
			camera.setDeviceModel(null);
		}
		if (null != mssId) {
			camera.setMss(StringUtils.isNotBlank(mssId) ? mssDAO
					.findById(mssId) : null);
		}
		if (null != organId) {
			camera.setOrgan(organDAO.findById(organId));
		}
		if (null != parentId) {
			camera.setParent(dvrDAO.findById(parentId));
		}
		if (null != navigation) {
			camera.setNavigation(navigation);
		}
		if (null != stakeNumber) {
			camera.setStakeNumber(stakeNumber);
		}
		cameraDAO.update(camera);

		// 修改property
		VideoDeviceProperty property = camera.getProperty();

		if (null != storeType) {
			// 如果存储计划不为空，并且修改了话，更新isUpdateCrs
			if (!storeType.equals(camera.getProperty().getStoreType())) {
				isUpdateCrs = true;
			}
			//
			if (storeType == 0) {
				if (null != localStorePlan) {
					property.setLocalStorePlan(localStorePlan);
				}
				property.setCenterStorePlan(null);
			} else if (storeType == 1) {
				if (StringUtils.isNotBlank(crsId)) {
					if (null != centerStorePlan) {
						property.setCenterStorePlan(centerStorePlan);
					}
				} else {
					property.setCenterStorePlan(null);
				}
				property.setLocalStorePlan(null);
			} else if (storeType == 2) {
				if (StringUtils.isNotBlank(crsId)) {
					if (null != centerStorePlan) {
						property.setCenterStorePlan(centerStorePlan);
					}
				} else {
					property.setCenterStorePlan(null);
				}
				if (null != localStorePlan) {
					property.setLocalStorePlan(localStorePlan);
				}
			}
		} else {
			// 只修改了存储时间话，更新isUpdateCrs
			if (null != centerStorePlan) {
				if (!centerStorePlan.equals(camera.getProperty()
						.getCenterStorePlan() != null ? camera.getProperty()
						.getCenterStorePlan() : "")) {
					isUpdateCrs = true;
				}
				property.setCenterStorePlan(centerStorePlan);
			}
			if (null != localStorePlan) {
				if (!localStorePlan.equals(camera.getProperty()
						.getLocalStorePlan() != null ? camera.getProperty()
						.getLocalStorePlan() : "")) {
					isUpdateCrs = true;
				}
				property.setLocalStorePlan(localStorePlan);
			}
		}

		if (null != streamType) {
			property.setStreamType(streamType);
		}
		if (null != storeType) {
			property.setStoreType(storeType);
		}
		if (null != expand) {
			property.setExpand(expand);
		}
		if (null != owner) {
			property.setOwner(owner);
		}
		if (null != civilCode) {
			property.setCivilCode(civilCode);
		}
		if (null != block) {
			property.setBlock(block);
		}
		if (null != certNum) {
			property.setCertNum(certNum);
		}
		if (null != certifiable) {
			property.setCertifiable(certifiable);
		}
		if (null != errCode) {
			property.setErrCode(errCode);
		}
		if (null != endTime) {
			property.setEndTime(endTime);
		}
		if (null != storeStream) {
			if (!storeStream
					.equals(camera.getProperty().getStoreStream() != null ? camera
							.getProperty().getStoreStream() : "")) {
				isUpdateCrs = true;
			}
			property.setStoreStream(storeStream);
		}
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();

		// 设备存储计划更新时间修改
		if (isUpdateCrs) {
			deviceCrsUpdeateTime = System.currentTimeMillis();
		}
	}

	public void deleteCamera(String id) throws BusinessException {
		// 删除摄像头和搜藏夹关联
		cameraDAO.deleteDeviceFavorite(id);
		// 删除摄像头和角色的关联
		cameraDAO.deleteRoleDevicePermission(id);
		// 删除预置点关联的图片
		imageDAO.deleteCameraPresetImage(id);
		// 删除预置点
		presetDAO.deleteByCamera(id);
		// 删除摄像头关联的图片
		Camera camera = cameraDAO.findById(id);
		if (camera.getProperty().getImageId() != null) {
			imageDAO.deleteById(camera.getProperty().getImageId());
		}
		// 同步SN
		syncSN(camera.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_CAMERA);
		// 删除摄像头
		cameraDAO.delete(camera);
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();

		// 设备存储计划更新时间修改
		deviceCrsUpdeateTime = System.currentTimeMillis();
	}

	public String createPreset(String vicId, Integer presetNumber,
			String presetName) {
		Preset preset = new Preset();
		preset.setDeviceId(vicId);
		preset.setName(presetName);
		preset.setPresetValue(presetNumber);
		preset.setIsDefault(new Short("0"));
		presetDAO.save(preset);
		return preset.getId();
	}

	public void updatePreset(String presetId, Integer presetNumber,
			String presetName) {
		Preset preset = presetDAO.findById(presetId);
		if (null != presetName) {
			preset.setName(presetName);
		}
		if (null != presetNumber) {
			preset.setPresetValue(presetNumber);
		}
		presetDAO.update(preset);
	}

	public void deletePreset(String presetId) {
		Preset preset = presetDAO.findById(presetId);
		if (preset.getImageId() != null) {
			imageDAO.deleteById(preset.getImageId());
		}
		presetDAO.delete(preset);
	}

	@Override
	public Preset getPreset(String id) {
		return presetDAO.findById(id);
	}

	public List<PresetVO> listVicPreset(String vicId) {
		List<PresetVO> listVO = new ArrayList<PresetVO>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("deviceId", vicId);
		List<Preset> presets = presetDAO.findByPropertys(params);
		for (Preset p : presets) {
			PresetVO vo = new PresetVO();
			vo.setId(p.getId().toString());
			vo.setName(p.getName());
			vo.setNumber(p.getPresetValue().toString());
			vo.setImageId(p.getImageId() != null ? p.getImageId().toString()
					: "");
			vo.setIsDefault(p.getIsDefault().toString());
			listVO.add(vo);
		}
		return listVO;
	}

	public List<RoleResourcePermissionVO> listDeviceByOperation(String id) {
		// User user = userDAO.findById(id);
		// LinkedHashMap<String, Object> map = new LinkedHashMap<String,
		// Object>();
		// // 根据登录用户ID得到非管理员角色集合
		// Set<Role> roles = user.getRoles();
		// // 返回列表
		// List<RoleResourcePermissionVO> cameras = new
		// LinkedList<RoleResourcePermissionVO>();
		// // 根据角色去获取角色和设备关联表数据集合
		// for (Role role : roles) {
		// map.clear();
		// map.put("roleId", role.getId());
		// map.put("resourceType", TypeDefinition.DEVICE_TYPE_CAMERA + "");
		// List<RoleResourcePermission> permissions = roleResourcePermissionDAO
		// .findByPropertys(map);
		// // 根据查询的关联去匹配返回的集合
		// for (RoleResourcePermission permission : permissions) {
		// RoleResourcePermissionVO vo = null;
		// for (RoleResourcePermissionVO camera : cameras) {
		// if (camera.getDeviceId().equals(permission.getResourceId())) {
		// vo = camera;
		// // 去掉重复的拆分字符串
		// Set<String> set = new HashSet<String>();
		// String[] a1 = camera.getPrivilege().split(",");
		// for (int i = 0; i < a1.length; i++) {
		// set.add(a1[i]);
		// }
		// String[] a2 = permission.getPrivilege().split(",");
		// for (int i = 0; i < a2.length; i++) {
		// set.add(a2[i]);
		// }
		// // 字符串得到的数字相加得到设备权限并集
		// StringBuffer sb = new StringBuffer();
		// for (String s : set) {
		// sb.append(s);
		// sb.append(",");
		// }
		// // 修改设备权限
		// camera.setPrivilege(sb.toString());
		// }
		// }
		// // 如果设备集合中没有找到权限集合中的匹配值,则把权限集合中的设备加入到设备集合中
		// if (null == vo) {
		// vo = new RoleResourcePermissionVO();
		// vo.setDeviceId(permission.getResourceId());
		// vo.setPrivilege(permission.getPrivilege());
		// cameras.add(vo);
		// }
		// }
		// }
		//
		// return cameras;
		return null;
	}

	public boolean isAdmin(String id) {
		User user = userDAO.findById(id);
		Set<Role> roles = user.getRoles();

		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int countCamera() {
		return cameraDAO.getTotalCount();
	}

	@Override
	public String createDvr(String standardNumber, String subType, String name,
			String ptsId, String transport, String mode, Integer maxConnect,
			Integer channelAmount, String organId, String linkType,
			String lanIp, Integer port, String manufacturerId,
			String deviceModelId, String location, String note,
			String userName, String password, Integer heartCycle,
			String expand, Integer aicAmount, Integer aocAmount, String decode)
			throws BusinessException {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Dvr> list = dvrDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = dvrDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		Dvr dvr = new Dvr();
		// String id = dvrDAO.getNextId("Dvr", 1);
		// dvr.setId(id);
		dvr.setCreateTime(System.currentTimeMillis());
		dvr.setLanIp(lanIp);
		dvr.setPort(port.toString());
		dvr.setLinkType(linkType);
		dvr.setLocation(location);
		Manufacturer manufacturer = null;
		if (null != manufacturerId) {
			manufacturer = manufacturerDAO.findById(manufacturerId);
		}
		if (null != manufacturer) {
			dvr.setManufacturer(manufacturer);
		}
		dvr.setMaxConnect(maxConnect);
		dvr.setMode(mode);
		dvr.setName(name);
		dvr.setNote(note);
		dvr.setOrgan(organDAO.findById(organId));
		if (StringUtils.isNotBlank(ptsId)) {
			// dvr.setPts(ptsDAO.findById(ptsId));
			dvr.setCcs(ccsDAO.findById(ptsId));
		}
		dvr.setStandardNumber(standardNumber);
		dvr.setSubType(subType);
		dvr.setTransport(transport);
		dvr.setType(TypeDefinition.DEVICE_TYPE_DVR);
		dvr.setChannelAmount(channelAmount);
		if (StringUtils.isNotBlank(deviceModelId)) {
			dvr.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		}
		dvr.setAicAmount(aicAmount);
		dvr.setAocAmount(aocAmount);
		dvrDAO.save(dvr);

		// 添加property
		VideoDeviceProperty property = new VideoDeviceProperty();
		property.setUserName(userName);
		property.setPassword(password);
		property.setHeartCycle(heartCycle);
		property.setExpand(expand);
		property.setDevice(dvr);
		if (null != manufacturer) {
			property.setProtocol(manufacturer.getProtocol());
		}
		property.setDecode(decode);
		videoDevicePropertyDAO.save(property);
		dvr.setProperty(property);

		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();

		// 设备存储计划更新时间修改
		deviceCrsUpdeateTime = System.currentTimeMillis();

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_DVR);
		return dvr.getId();
	}

	public List<ListDeviceAlarmVO> listDeviceAlarm(String organId,
			String deviceName, String deviceType, String alarmType,
			Long startTime, Long endTime, Integer startIndex, Integer limit,
			String userId) {
		String[] organIds = null;
		if (StringUtils.isBlank(organId)) {
			User user = userDAO.findById(userId);
			organIds = organDAO.findOrgansByOrganId(user.getOrgan().getId());
		}

		List<ListDeviceAlarmVO> list = new LinkedList<ListDeviceAlarmVO>();
		List<DeviceAlarm> alarms = deviceAlarmDAO.listDeviceAlarm(organId,
				deviceName, deviceType, alarmType, startTime, endTime,
				startIndex, limit, organIds);
		for (DeviceAlarm alarm : alarms) {
			ListDeviceAlarmVO vo = new ListDeviceAlarmVO();
			vo.setConfirmUser(alarm.getConfirmUser());
			vo.setDetectTime(MyStringUtil.object2StringNotNull(alarm
					.getAlarmTime()));
			vo.setDeviceName(alarm.getDeviceName());
			vo.setId(alarm.getId());
			vo.setMaintainUser(alarm.getMaintainUser());
			vo.setNavigation(alarm.getNavigation());
			vo.setReason(alarm.getAlarmContent());
			vo.setRecoverTime(MyStringUtil.object2StringNotNull(alarm
					.getRecoverTime()));
			vo.setStake(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(MyStringUtil.object2StringNotNull(alarm
					.getConfirmFlag()));
			list.add(vo);
		}
		return list;
	}

	public int selectTotalCount(String organId, String deviceName,
			String deviceType, String alarmType, Long startTime, Long endTime,
			String userId) {
		String[] organIds = null;
		if (StringUtils.isBlank(organId)) {
			User user = userDAO.findById(userId);
			organIds = organDAO.findOrgansByOrganId(user.getOrgan().getId());
		}
		return deviceAlarmDAO.countDeviceAlarm(organId, deviceName, deviceType,
				alarmType, startTime, endTime, organIds);

	}

	public List<Manufacturer> listManufacturer(Integer startIndex, Integer limit) {
		return manufacturerDAO.listManufacturer(startIndex, limit);
	}

	public List<DeviceModelVO> listDeviceModel(String manufacturerId,
			Integer startIndex, Integer limit) {
		List<DeviceModelVO> listVO = new ArrayList<DeviceModelVO>();

		List<DeviceModel> list = deviceModelDAO.listDeviceModel(manufacturerId,
				startIndex, limit);

		for (DeviceModel dm : list) {
			DeviceModelVO vo = new DeviceModelVO();
			vo.setId(dm.getId().toString());
			vo.setManufacturerId(dm.getManufacturer() != null ? dm
					.getManufacturer().getId().toString() : "");
			vo.setManufacturerName(dm.getManufacturer() != null ? dm
					.getManufacturer().getName() : "");
			vo.setName(dm.getName());
			vo.setNote(dm.getNote());
			vo.setType(dm.getType());
			listVO.add(vo);
		}
		return listVO;
	}

	public List<ListCameraVO> listCamera(String dvrId, Integer startIndex,
			Integer limit) {
		List<ListCameraVO> listVO = new ArrayList<ListCameraVO>();
		List<Camera> cameras = cameraDAO.findCameraByDvrId(dvrId, startIndex,
				limit);
		for (Camera camera : cameras) {
			ListCameraVO vo = new ListCameraVO();
			VideoDeviceProperty property = camera.getProperty();
			vo.setCcsId(camera.getParent().getCcs() != null ? camera
					.getParent().getCcs().getId() : "");
			vo.setCenterStorePlan(property.getCenterStorePlan());
			vo.setChannelId(camera.getChannelNumber() + "");
			vo.setCreateTime(camera.getCreateTime() + "");
			vo.setCrsId(camera.getCrs() != null ? camera.getCrs().getId() : "");
			vo.setCrsName(camera.getCrs() != null ? camera.getCrs().getName()
					: "");
			vo.setExpand(camera.getProperty().getExpand());
			vo.setId(camera.getId());
			vo.setLocalStorePlan(property.getLocalStorePlan());
			vo.setLocation(camera.getLocation());
			vo.setStreamType(property.getStreamType());
			vo.setMssId(camera.getMss() != null ? camera.getMss().getId() : "");
			vo.setMssName(camera.getMss() != null ? camera.getMss().getName()
					: "");
			vo.setName(camera.getName());
			vo.setNote(camera.getNote());
			vo.setOrganId(camera.getOrgan().getId());
			vo.setParentId(camera.getParent().getId());
			vo.setPtsId(camera.getParent().getPts() != null ? camera
					.getParent().getPts().getId() : "");
			vo.setStandardNumber(camera.getStandardNumber());
			vo.setStoreType(property.getStoreType() + "");
			vo.setSubType(camera.getSubType());
			vo.setType(TypeDefinition.DEVICE_TYPE_CAMERA + "");
			vo.setManufacturerId(camera.getManufacturer() != null ? camera
					.getManufacturer().getId().toString() : "");
			vo.setManufacturerName(camera.getManufacturer() != null ? camera
					.getManufacturer().getName() : "");
			vo.setDeviceModelId(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getId().toString() : "");
			vo.setDeviceModelName(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getName() : "");
			vo.setNavigation(camera.getNavigation());
			vo.setRmsId(camera.getRms() != null ? camera.getRms().getId() : "");
			vo.setRmsName(camera.getRms() != null ? camera.getRms().getName()
					: "");
			vo.setStoreStream(property.getStoreStream());
			listVO.add(vo);
		}
		return listVO;
	}

	public void updateDvr(String id, String standardNumber, String subType,
			String name, String ptsId, String transport, String mode,
			Integer maxConnect, String organId, String linkType, String lanIp,
			Integer port, String manufacturerId, String deviceModelId,
			String location, String note, String userName, String password,
			Integer heartCycle, String expand, Integer aicAmount,
			Integer aocAmount, Integer channelAmount, String decode) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			List<Dvr> list = dvrDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// List<Dvr> list = dvrDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		Dvr dvr = dvrDAO.findById(id);
		if (null != lanIp) {
			dvr.setLanIp(lanIp);
		}
		if (null != port) {
			dvr.setPort(port.toString());
		}
		if (null != linkType) {
			dvr.setLinkType(linkType);
		}
		if (null != location) {
			dvr.setLocation(location);
		}
		Manufacturer manufacturer = null;
		if (StringUtils.isNotBlank(manufacturerId)) {
			manufacturer = manufacturerDAO.findById(manufacturerId);
		}
		if (null != manufacturer) {
			dvr.setManufacturer(manufacturer);
		}
		if (null != maxConnect) {
			dvr.setMaxConnect(maxConnect);
		}
		if (null != mode) {
			dvr.setMode(mode);
		}
		if (null != name) {
			dvr.setName(name);
		}
		if (null != note) {
			dvr.setNote(note);
		}
		if (null != organId) {
			dvr.setOrgan(organDAO.findById(organId));
		}
		if (StringUtils.isNotBlank(ptsId)) {
			// dvr.setPts(ptsDAO.findById(ptsId));
			dvr.setCcs(ccsDAO.findById(ptsId));
		}
		if (null != standardNumber) {
			syncSN(dvr.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_DVR);
			dvr.setStandardNumber(standardNumber);
		}
		if (null != subType) {
			dvr.setSubType(subType);
		}
		if (null != transport) {
			dvr.setTransport(transport);
		}
		if (StringUtils.isNotBlank(deviceModelId)) {
			dvr.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		}
		if (null != aicAmount) {
			dvr.setAicAmount(aicAmount);
		}
		if (null != aocAmount) {
			dvr.setAocAmount(aocAmount);
		}
		// 判断channelAmount是否合法，不小于当前下方的摄像头数量才允许修改
		if (null != channelAmount) {
			int count = cameraDAO.cameraTotalCount(id).intValue();
			if (count > channelAmount.intValue()) {
				throw new BusinessException(
						ErrorCode.CHANNEL_AMOUNT_OVER_LIMIT,
						"Can not modify channel amount to "
								+ channelAmount.toString()
								+ ", because child camera count is " + count
								+ " !");
			}
			dvr.setChannelAmount(channelAmount);
		}
		// 修改property
		VideoDeviceProperty property = dvr.getProperty();
		if (null != userName) {
			property.setUserName(userName);
		}
		if (null != password) {
			property.setPassword(password);
		}
		if (null != heartCycle) {
			property.setHeartCycle(heartCycle);
		}
		if (null != expand) {
			property.setExpand(expand);
		}
		if (null != manufacturer) {
			property.setProtocol(manufacturer.getProtocol());
		}
		if (null != decode) {
			property.setDecode(decode);
		}
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();
	}

	public void deleteDvr(String id, Boolean force) {
		List<Camera> cameras = findCameraByDvrId(id);
		if (!force) {
			if (cameras.size() > 0) {
				throw new BusinessException(ErrorCode.CHILDREN_EXIST,
						"Child camera found");
			}
		} else {
			// 删除摄像头和搜藏夹关联
			cameraDAO.deleteRUserDeviceFavorite(id);
			// 删除角色和摄像头关联
			cameraDAO.deleteRRP(id);
			// 删除预置点关联的图片
			cameraDAO.deleteCameraPresetImage(id);
			// 删除预置点
			cameraDAO.deletePreset(id);
			// 删除摄像头
			for (Camera camera : cameras) {
				syncSN(camera.getStandardNumber(), null,
						TypeDefinition.RESOURCE_TYPE_CAMERA);
				cameraDAO.delete(camera);
			}
		}

		// 同步SN
		Dvr dvr = dvrDAO.findById(id);
		syncSN(dvr.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_DVR);
		dvrDAO.delete(dvr);
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();

		// 设备存储计划更新时间修改
		deviceCrsUpdeateTime = System.currentTimeMillis();
	}

	@Override
	public List<DvrVO> listDvr(String organId, Integer startIndex, Integer limit) {
		List<Dvr> dvrs = dvrDAO.listDvr(organId, startIndex, limit);
		List<DvrVO> listVO = new ArrayList<DvrVO>();
		for (Dvr dvr : dvrs) {
			DvrVO vo = new DvrVO();
			vo.setCcsId(dvr.getCcs() != null ? dvr.getCcs().getId() : "");
			vo.setCcsName(dvr.getCcs() != null ? dvr.getCcs().getName() : "");
			vo.setCreateTime(dvr.getCreateTime() + "");
			vo.setExpand(dvr.getProperty().getExpand());
			vo.setHeartCycle(dvr.getProperty().getHeartCycle() + "");
			vo.setId(dvr.getId());
			vo.setLanIp(dvr.getLanIp());
			vo.setLinkType(dvr.getLinkType());
			vo.setLocation(dvr.getLocation());
			vo.setManufacturerId(dvr.getManufacturer() != null ? dvr
					.getManufacturer().getId() + "" : "");
			vo.setMaxConnect(dvr.getMaxConnect() + "");
			vo.setMode(dvr.getMode());
			vo.setName(dvr.getName());
			vo.setNote(dvr.getNote());
			vo.setOrganId(dvr.getOrgan().getId());
			vo.setPassword(dvr.getProperty().getPassword());
			vo.setProtocol(dvr.getProperty().getProtocol());
			vo.setPtsId(dvr.getPts() != null ? dvr.getPts().getId() : "");
			vo.setPtsName(dvr.getPts() != null ? dvr.getPts().getName() : "");
			vo.setStandardNumber(dvr.getStandardNumber());
			vo.setTransport(dvr.getTransport());
			vo.setType(TypeDefinition.DEVICE_TYPE_DVR + "");
			vo.setUserName(dvr.getProperty().getUserName());
			vo.setOrganName(dvr.getOrgan().getName());
			vo.setChannelAmount(dvr.getChannelAmount() != null ? dvr
					.getChannelAmount().toString() : "0");
			vo.setDecode(dvr.getProperty().getDecode());
			listVO.add(vo);
		}
		return listVO;
	}

	public GetDvrVO getDvr(String id) {
		Dvr dvr = dvrDAO.findById(id);
		GetDvrVO vo = new GetDvrVO();
		vo.setCcsId(dvr.getCcs() != null ? dvr.getCcs().getId() : "");
		vo.setCcsName(dvr.getCcs() != null ? dvr.getCcs().getName() : "");
		vo.setCreateTime(dvr.getCreateTime() + "");
		vo.setExpand(dvr.getProperty().getExpand());
		vo.setHeartCycle(dvr.getProperty().getHeartCycle() + "");
		vo.setId(dvr.getId());
		vo.setLanIp(dvr.getLanIp());
		vo.setPort(dvr.getPort());
		vo.setLinkType(dvr.getLinkType());
		vo.setLocation(dvr.getLocation());
		vo.setManufacturerId(dvr.getManufacturer() != null ? dvr
				.getManufacturer().getId() + "" : "");
		vo.setManufacturerName(dvr.getManufacturer() != null ? dvr
				.getManufacturer().getName() : "");
		vo.setMaxConnect(dvr.getMaxConnect() + "");
		vo.setMode(dvr.getMode());
		vo.setName(dvr.getName());
		vo.setNote(dvr.getNote());
		vo.setOrganId(dvr.getOrgan().getId());
		vo.setOrganName(dvr.getOrgan().getName());
		vo.setPassword(dvr.getProperty().getPassword());
		vo.setProtocol(dvr.getProperty().getProtocol());
		vo.setPtsId(dvr.getPts() != null ? dvr.getPts().getId() : "");
		vo.setPtsName(dvr.getPts() != null ? dvr.getPts().getName() : "");
		vo.setStandardNumber(dvr.getStandardNumber());
		vo.setTransport(dvr.getTransport());
		vo.setType(TypeDefinition.DEVICE_TYPE_DVR + "");
		vo.setSubType(dvr.getSubType());
		vo.setUserName(dvr.getProperty().getUserName());
		vo.setAicAmount(dvr.getAicAmount() != null ? dvr.getAicAmount()
				.toString() : "");
		vo.setAocAmount(dvr.getAocAmount() != null ? dvr.getAocAmount()
				.toString() : "");
		vo.setDeviceModelId(dvr.getDeviceModel() != null ? dvr.getDeviceModel()
				.getId().toString() : "");
		vo.setDeviceModelName(dvr.getDeviceModel() != null ? dvr
				.getDeviceModel().getName() : "");
		vo.setChannelAmount(dvr.getChannelAmount() != null ? dvr
				.getChannelAmount().toString() : "0");
		vo.setDecode(dvr.getProperty().getDecode());
		return vo;
	}

	@Override
	public List<Camera> findCameraByDvrId(String id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("parent.id", id);
		List<Camera> cameras = cameraDAO.findByPropertys(map);
		return cameras;
	}

	public List<Preset> findPresetByCameraId(String id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("deviceId", id);
		List<Preset> presets = presetDAO.findByPropertys(map);
		return presets;
	}

	@Override
	public List<RoleResourcePermission> findRRPByCameraId(String id) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("resourceId", id);
		List<RoleResourcePermission> rrp = roleResourcePermissionDAO
				.findByPropertys(map);
		return rrp;
	}

	@Override
	public void bindImage(String presetId, String imageId) {
		Preset preset = presetDAO.findById(presetId);
		preset.setImageId(imageId);
	}

	@Override
	public void deleteRRPById(RoleResourcePermission rrp) {
		roleResourcePermissionDAO.delete(rrp);
	}

	public String createDeviceModel(String name, String type,
			String manufacturerId, String note) {
		// LinkedHashMap<String, Object> map = new LinkedHashMap<String,
		// Object>();
		// map.put("name", name);
		// List<DeviceModel> dms = deviceModelDAO.findByPropertys(map);
		// if (dms.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		DeviceModel dm = new DeviceModel();
		dm.setName(name);
		dm.setType(type);
		dm.setManufacturer(manufacturerDAO.findById(manufacturerId));
		dm.setNote(note);
		deviceModelDAO.save(dm);
		return dm.getId();
	}

	public void updateDeviceModel(String id, String name, String type,
			String manufacturerId, String note) {
		// if (name != null) {
		// LinkedHashMap<String, Object> map = new LinkedHashMap<String,
		// Object>();
		// map.put("name", name);
		// List<DeviceModel> dms = deviceModelDAO.findByPropertys(map);
		// if (dms.size() > 0) {
		// if (!dms.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST,
		// "DeviceModel name[" + name + "] is already exist !");
		// }
		// }
		// }
		DeviceModel dm = deviceModelDAO.findById(id);
		if (null != name) {
			dm.setName(name);
		}
		if (null != type) {
			dm.setType(type);
		}
		if (StringUtils.isNotBlank(manufacturerId)) {
			dm.setManufacturer(manufacturerDAO.findById(manufacturerId));
		}
		if (null != note) {
			dm.setNote(note);
		}
		deviceModelDAO.update(dm);
	}

	public void deleteDeviceModel(String id) {
		// 查询是否有改类型的摄像头，有不允许删除
		int cameraCount = cameraDAO.countByDeviceModel(id);
		if (cameraCount > 0) {
			throw new BusinessException(ErrorCode.CHILDREN_EXIST,
					"Existing camera use this DeviceModel !");
		}
		// 查询是否有改类型的dvr，有不允许删除
		int dvrCount = dvrDAO.countByDeviceModel(id);
		if (dvrCount > 0) {
			throw new BusinessException(ErrorCode.CHILDREN_EXIST,
					"Existing dvr use this DeviceModel !");
		}

		deviceModelDAO.deleteById(id);
	}

	public DeviceModelVO getDeviceModel(String id) {
		DeviceModel dm = deviceModelDAO.findById(id);
		DeviceModelVO vo = new DeviceModelVO();
		vo.setId(dm.getId() + "");
		vo.setName(dm.getName());
		vo.setType(dm.getType());
		vo.setManufacturerId(dm.getManufacturer() != null ? dm
				.getManufacturer().getId() + "" : "");
		vo.setManufacturerName(dm.getManufacturer() != null ? dm
				.getManufacturer().getName() : "");
		vo.setNote(dm.getNote());
		return vo;
	}

	@Override
	public List<PtsDvrVO> listDvrByCcs(String ccsId, int start, int limit) {
		List<Dvr> dvrs = dvrDAO.listDvrByCcs(ccsId, start, limit);
		// 摄像头列表查询
		List<String> dvrIds = new ArrayList<String>();
		for (Dvr dvr : dvrs) {
			dvrIds.add(dvr.getId());
		}
		List<Camera> cameras = cameraDAO.listDvrsCamera(dvrIds);
		// 找到一个摄像头就从集合中移除，减少循环次数
		List<Camera> removeList = new LinkedList<Camera>();
		// 返回列表
		List<PtsDvrVO> rtnList = new LinkedList<PtsDvrVO>();
		try {
			for (Dvr dvr : dvrs) {
				// 视频服务器返回构建
				PtsDvrVO dvrVO = new PtsDvrVO();
				VideoDeviceProperty property = dvr.getProperty();
				if (null == property) {
					throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
							"DVR[" + dvr.getId() + "] property not found !");
				}
				if (null != property.getExpand()) {
					dvrVO.setExpand(new String(Base64.encodeBase64(property
							.getExpand().getBytes("utf8")), "utf8"));
				}
				dvrVO.setHeartCycle(property.getHeartCycle() != null ? property
						.getHeartCycle().toString() : "");
				dvrVO.setLanIp(dvr.getLanIp());
				dvrVO.setMaxConnect(dvr.getMaxConnect() != null ? dvr
						.getMaxConnect().toString() : "");
				dvrVO.setPassword(property.getPassword());
				dvrVO.setPort(dvr.getPort());
				dvrVO.setProtocol(property.getProtocol());
				dvrVO.setStandardNumber(dvr.getStandardNumber());
				dvrVO.setTransport(dvr.getTransport());
				dvrVO.setUserName(property.getUserName());

				// 下属摄像头返回构建
				List<PtsCameraVO> cameraVOs = new LinkedList<PtsCameraVO>();
				for (Camera camera : cameras) {
					if (camera.getParent().getId().equals(dvr.getId())) {
						PtsCameraVO cameraVO = new PtsCameraVO();
						VideoDeviceProperty cameraProperty = camera
								.getProperty();
						if (null == cameraProperty) {
							throw new BusinessException(
									ErrorCode.RESOURCE_NOT_FOUND, "Camera["
											+ camera.getId()
											+ "] property not found !");
						}
						cameraVO.setChannelNumber(camera.getChannelNumber() != null ? camera
								.getChannelNumber().toString() : "");
						cameraVO.setCRSStandardNumber(camera.getCrs() != null ? camera
								.getCrs().getStandardNumber() : "");
						cameraVO.setStreamType(cameraProperty.getStreamType());
						cameraVO.setMSSStandardNumber(camera.getMss() != null ? camera
								.getMss().getStandardNumber() : "");
						cameraVO.setRMSStandardNumber(camera.getRms() != null ? camera
								.getRms().getStandardNumber() : "");
						cameraVO.setStandardNumber(camera.getStandardNumber());
						cameraVO.setStoreType(cameraProperty.getStoreType() != null ? cameraProperty
								.getStoreType().toString() : "");
						if (cameraProperty.getExpand() != null) {
							String expend = cameraProperty.getExpand();
							String encode = new String(
									Base64.encodeBase64(expend.getBytes("utf8")),
									"utf8");
							cameraVO.setExpand(encode);

						} else {
							cameraVO.setExpand("");
						}

						cameraVOs.add(cameraVO);
						// 找到一个摄像头就从集合中移除，减少循环次数
						removeList.add(camera);
					}
				}
				cameras.removeAll(removeList);
				removeList.clear();

				dvrVO.setCameras(cameraVOs);
				rtnList.add(dvrVO);
			}
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(ErrorCode.ENCODING_ERROR,
					e.getMessage());
		}
		return rtnList;
	}

	@Override
	public Integer cameraTotalCount(String dvrId) {
		return cameraDAO.cameraTotalCount(dvrId);
	}

	@Override
	public int countDvrByCcs(String ccsId) {
		return dvrDAO.countDvrByCcs(ccsId);
	}

	@Override
	public Integer deviceModeTotalCount(String manufacturerId) {
		return deviceModelDAO.deviceModeTotalCount(manufacturerId);
	}

	@Override
	public Integer dvrTotalCount(String organId) {
		return dvrDAO.dvrTotalCount(organId);
	}

	@Override
	public void batchCreateDvrCameras(Camera[] cameras)
			throws BusinessException {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < cameras.length; i++) {
			cameraDAO.batchInsert(cameras[i]);
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(cameras[i].getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CAMERA);
			list.add(sn);
		}
		cameraDAO.excuteBatch();
		batchInsertSN(list);
	}

	@Override
	public void updateDeviceUpdateListener() throws BusinessException {
		if (deviceUpdateTime > 0) {
			List<DeviceUpdateListener> list = deviceUpdateListenerDAO.findAll();
			if (list.size() == 1) {
				DeviceUpdateListener record = list.get(0);
				// 内存中的更新时间大于数据库中的时间才写入到数据库
				if (record.getUpdateTime() < deviceUpdateTime) {
					record.setUpdateTime(deviceUpdateTime);
				}
			} else {
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						"table sv_device_update_listener must only has 1 record !");
			}
		}

		if (deviceCrsUpdeateTime > 0) {
			List<DeviceUpdateListener> list = deviceUpdateListenerDAO.findAll();
			if (list.size() == 1) {
				DeviceUpdateListener record = list.get(0);
				// 内存中的更新时间大于数据库中的时间才写入到数据库
				if (record.getCrsUpdateTime() < deviceCrsUpdeateTime) {
					record.setCrsUpdateTime(deviceCrsUpdeateTime);
				}
			} else {
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						"table sv_device_update_listener must only has 1 record !");
			}
		}

	}

	@Override
	public ListDvrDTO listDvrByDevice(String name, String standardNumber,
			String ip, Integer startIndex, Integer limit, String logonUserId,
			String organId) {
		List<DvrVO> listVO = new ArrayList<DvrVO>();
		Integer totalCount = null;
		if (StringUtils.isNotBlank(name)
				|| StringUtils.isNotBlank(standardNumber)
				|| StringUtils.isNotBlank(ip)) {
			String parentOrganId = organId;
			if (StringUtils.isBlank(organId)) {
				User user = userDAO.findById(logonUserId);
				parentOrganId = user.getOrgan().getId();
			}
			String[] organIds = organDAO.findOrgansByOrganId(parentOrganId);

			totalCount = dvrDAO.dvrTotalCount(name, standardNumber, ip,
					organIds);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount.intValue() != 0) {
				if (startIndex.intValue() >= totalCount.intValue()) {
					startIndex -= ((startIndex.intValue() - totalCount
							.intValue()) / limit + 1)
							* limit;
				}
			}

			List<Dvr> dvrs = dvrDAO.listDvrByDevice(name, standardNumber, ip,
					startIndex, limit, organIds);
			listVO = setDvrVO(dvrs);
		} else {
			totalCount = dvrDAO.dvrTotalCount(organId);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount.intValue() != 0) {
				if (startIndex.intValue() >= totalCount.intValue()) {
					startIndex -= ((startIndex.intValue() - totalCount
							.intValue()) / limit + 1)
							* limit;
				}
			}
			List<Dvr> dvrs = dvrDAO.listDvr(organId, startIndex, limit);
			listVO = setDvrVO(dvrs);
		}
		ListDvrDTO dto = new ListDvrDTO();
		dto.setDvrList(listVO);
		dto.setTotalCount(totalCount + "");
		return dto;
	}

	public List<DvrVO> setDvrVO(List<Dvr> dvrs) {
		List<DvrVO> listVO = new ArrayList<DvrVO>();

		for (Dvr dvr : dvrs) {
			DvrVO vo = new DvrVO();
			vo.setCcsId(dvr.getCcs() != null ? dvr.getCcs().getId() : "");
			vo.setCcsName(dvr.getCcs() != null ? dvr.getCcs().getName() : "");
			vo.setCreateTime(dvr.getCreateTime() + "");
			vo.setExpand(dvr.getProperty() != null ? dvr.getProperty()
					.getExpand() : "");
			vo.setHeartCycle(dvr.getProperty() != null ? dvr.getProperty()
					.getHeartCycle() + "" : "");
			vo.setId(dvr.getId());
			vo.setLanIp(dvr.getLanIp());
			vo.setLinkType(dvr.getLinkType());
			vo.setLocation(dvr.getLocation());
			vo.setManufacturerId(dvr.getManufacturer() != null ? dvr
					.getManufacturer().getId() + "" : "");
			vo.setManufacturerName(dvr.getManufacturer() != null ? dvr
					.getManufacturer().getName() : "");
			vo.setMaxConnect(dvr.getMaxConnect() + "");
			vo.setMode(dvr.getMode());
			vo.setName(dvr.getName());
			vo.setNote(dvr.getNote());
			vo.setOrganId(dvr.getOrgan() != null ? dvr.getOrgan().getId() : "");
			vo.setOrganName(dvr.getOrgan().getName());
			vo.setPassword(dvr.getProperty() != null ? dvr.getProperty()
					.getPassword() : "");
			vo.setProtocol(dvr.getProperty() != null ? dvr.getProperty()
					.getProtocol() : "");
			vo.setPtsId(dvr.getPts() != null ? dvr.getPts().getId() : "");
			vo.setPtsName(dvr.getPts() != null ? dvr.getPts().getName() : "");
			vo.setStandardNumber(dvr.getStandardNumber());
			vo.setTransport(dvr.getTransport());
			vo.setType(dvr.getType() + "");
			vo.setUserName(dvr.getProperty() != null ? dvr.getProperty()
					.getUserName() : "");
			vo.setDeviceModelId(dvr.getDeviceModel() != null ? dvr
					.getDeviceModel().getId().toString() : "");
			vo.setDeviceModelName(dvr.getDeviceModel() != null ? dvr
					.getDeviceModel().getName() : "");
			vo.setChannelAmount(dvr.getChannelAmount() != null ? dvr
					.getChannelAmount().toString() : "0");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public void setCameraDefaultPreset(String presetId, boolean set)
			throws BusinessException {
		Preset preset = presetDAO.findById(presetId);
		// 移除以前的默认预置点
		presetDAO.removeDefault(preset.getDeviceId());
		if (set) {
			preset.setIsDefault(new Short("1"));
		}
	}

	@Override
	public Integer manufacturerTotalCount() {
		return manufacturerDAO.manufacturerTotalCount();
	}

	@Override
	public List<AuthCameraVO> listUserAuthCamera(String userId)
			throws BusinessException {
		User user = userDAO.findById(userId);
		// 获取用户角色
		Set<Role> roles = user.getRoles();
		// 用户机构下的所有子机构ID列表
		String[] organIds = organDAO.findOrgansByOrganId(user.getOrgan()
				.getId());

		// 用户机构下的所有摄像头
		List<Camera> cameras = cameraDAO.listCameraByOrganIds(organIds);
		// 自定义角色权限的所有摄像头
		List<RoleResourcePermission> authCameras = null;
		// 返回列表
		List<AuthCameraVO> rtnList = new LinkedList<AuthCameraVO>();
		// 判断是否拥有系统角色，系统具有机构下的所有的设备的权限，自定义角色只拥有部分设备权限
		String auth = "";
		// 是否管理员角色
		boolean isAdmin = false;
		// 该用户的自定义角色ID列表
		List<String> roleIds = new ArrayList<String>();
		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)) {
				auth = "1,2,4";
				isAdmin = true;
				break;
			}
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)) {
				if (StringUtils.isBlank(auth)) {
					auth = "1";
				}
			} else if (role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				auth = "1,2";
			}
			// 加入到自定义角色列表中
			else {
				roleIds.add(role.getId());
			}
		}

		// 如果存在管理员角色
		if (isAdmin) {
			// 用户机构下的所有摄像头都是最高权限
			for (Camera camera : cameras) {
				AuthCameraVO vo = new AuthCameraVO();
				vo.setId(camera.getId());
				vo.setOrganId(camera.getOrgan().getId());
				vo.setName(camera.getName());
				vo.setChannelNumber(camera.getChannelNumber() != null ? camera
						.getChannelNumber().toString() : "");
				vo.setImageId(camera.getProperty().getImageId() != null ? camera
						.getProperty().getImageId().toString()
						: "");
				vo.setLocation(camera.getLocation());
				vo.setStandardNumber(camera.getStandardNumber());
				vo.setStatus(camera.getStatus() != null ? camera.getStatus()
						.toString() : "");
				vo.setSubType(camera.getSubType());
				vo.setAuth(auth);
				rtnList.add(vo);
			}
		}
		// 如果不存在任何系统角色
		else if (StringUtils.isBlank(auth)) {
			// 查询自定义角色权限设备
			if (roleIds.size() > 0) {
				authCameras = roleResourcePermissionDAO.listByRoleIds(roleIds,
						TypeDefinition.DEVICE_TYPE_CAMERA + "");

				for (RoleResourcePermission authCamera : authCameras) {
					RoleResourcePermissionCamera pc = (RoleResourcePermissionCamera) authCamera;
					// 返回集合中已经添加过该设备,合并权限
					AuthCameraVO vo = isAdded(rtnList, pc.getCamera().getId());
					if (null != vo) {
						vo.setAuth(combineAuth(vo.getAuth(),
								authCamera.getPrivilege()));
					}
					// 添加新的
					else {
						vo = new AuthCameraVO();
						Camera camera = getCameraFromList(cameras, pc
								.getCamera().getId());
						if (null != camera) {
							vo.setId(camera.getId());
							vo.setOrganId(camera.getOrgan().getId());
							vo.setName(camera.getName());
							vo.setLocation(camera.getLocation());
							vo.setChannelNumber(camera.getChannelNumber() != null ? camera
									.getChannelNumber().toString() : "");
							vo.setImageId(camera.getProperty().getImageId() != null ? camera
									.getProperty().getImageId().toString()
									: "");
							vo.setStandardNumber(camera.getStandardNumber());
							vo.setStatus(camera.getStatus() != null ? camera
									.getStatus().toString() : "");
							vo.setSubType(camera.getSubType());
							vo.setAuth(authCamera.getPrivilege());
							rtnList.add(vo);
						}
					}
				}
			}
		}
		// 存在系统角色,同时存在自定义角色
		else if (StringUtils.isNotBlank(auth) && roleIds.size() > 0) {
			// 查询自定义角色权限设备
			authCameras = roleResourcePermissionDAO.listByRoleIds(roleIds,
					TypeDefinition.DEVICE_TYPE_CAMERA + "");
			// 用户机构下的所有摄像头至少具有auth权限，与自定角色摄像头取并集
			for (Camera camera : cameras) {
				AuthCameraVO vo = new AuthCameraVO();
				vo.setId(camera.getId());
				vo.setOrganId(camera.getOrgan().getId());
				vo.setName(camera.getName());
				vo.setLocation(camera.getLocation());
				vo.setChannelNumber(camera.getChannelNumber() != null ? camera
						.getChannelNumber().toString() : "");
				vo.setImageId(camera.getProperty().getImageId() != null ? camera
						.getProperty().getImageId().toString()
						: "");
				vo.setStandardNumber(camera.getStandardNumber());
				vo.setStatus(camera.getStatus() != null ? camera.getStatus()
						.toString() : "");
				vo.setSubType(camera.getSubType());
				vo.setAuth(auth);
				// 迭代authCameras，合并权限
				for (int i = 0; i < authCameras.size(); i++) {
					RoleResourcePermissionCamera authCamera = (RoleResourcePermissionCamera) authCameras
							.get(i);
					if (camera.getId().equals(authCamera.getCamera().getId())) {
						vo.setAuth(combineAuth(vo.getAuth(),
								authCamera.getPrivilege()));
					}
				}
				rtnList.add(vo);
			}
		}
		// 只存在系统角色
		else {
			// 用户机构下的所有摄像头都设置为auth权限
			for (Camera camera : cameras) {
				AuthCameraVO vo = new AuthCameraVO();
				vo.setId(camera.getId());
				vo.setOrganId(camera.getOrgan().getId());
				vo.setName(camera.getName());
				vo.setLocation(camera.getLocation());
				vo.setChannelNumber(camera.getChannelNumber() != null ? camera
						.getChannelNumber().toString() : "");
				vo.setImageId(camera.getProperty().getImageId() != null ? camera
						.getProperty().getImageId().toString()
						: "");
				vo.setStandardNumber(camera.getStandardNumber());
				vo.setStatus(camera.getStatus() != null ? camera.getStatus()
						.toString() : "");
				vo.setSubType(camera.getSubType());
				vo.setAuth(auth);
				rtnList.add(vo);
			}
		}

		return rtnList;
	}

	/**
	 * 判断设备集合中是否已经拥有该设备
	 * 
	 * @param list
	 *            设备集合
	 * @param id
	 *            设备ID
	 * @return 已存在-返回存在对象, 不存在-返回空
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午3:01:50
	 */
	private AuthCameraVO isAdded(List<AuthCameraVO> list, String id) {
		for (AuthCameraVO vo : list) {
			if (vo.getId().equals(id)) {
				return vo;
			}
		}
		return null;
	}

	/**
	 * 合并权限
	 * 
	 * @param auth1
	 *            权限1
	 * @param auth2
	 *            权限2
	 * @return 合并后的权限字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午3:12:13
	 */
	private String combineAuth(String auth1, String auth2) {
		Set<String> set = new HashSet<String>();
		String[] s1 = auth1.split(",");
		String[] s2 = auth2.split(",");
		for (int i = 0; i < s1.length; i++) {
			set.add(s1[i]);
		}
		for (int i = 0; i < s2.length; i++) {
			set.add(s2[i]);
		}
		// 字符串得到的数字相加得到设备权限并集
		StringBuffer sb = new StringBuffer();
		for (String s : set) {
			sb.append(s);
			sb.append(",");
		}
		return sb.toString();
	}

	/**
	 * 从摄像头列表中，获取某个ID的摄像头
	 * 
	 * @param cameras
	 *            摄像头列表
	 * @param id
	 *            摄像头ID
	 * @return 摄像头对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午3:19:12
	 */
	private Camera getCameraFromList(List<Camera> cameras, String id) {
		for (Camera camera : cameras) {
			if (camera.getId().equals(id)) {
				return camera;
			}
		}
		return null;
	}

	@Override
	public CountDeviceDTO countDevice(String organId, String deviceType) {
		Set<String> organs = new TreeSet<String>();
		String[] organIds = organDAO.findOrgansByOrganId(organId);
		String dvrs[] = dvrDAO.countDvr(organIds);
		// 加入机构集合
		for (String dvr : dvrs) {
			organs.add(dvr);
		}
		String[] windSpeeds = wsDAO.countWindSpeed(organIds);
		// 风速风向检测器加入集合
		for (String windSpeed : windSpeeds) {
			organs.add(windSpeed);
		}
		String[] vds = vdDAO.countVD(organIds);
		// 车辆检测器加入集合
		for (String vd : vds) {
			organs.add(vd);
		}
		String[] wsts = wstDAO.countWST(organIds);
		// 气象检测器加入集合
		for (String wst : wsts) {
			organs.add(wst);
		}
		String[] lolis = loliDAO.countLoli(organIds);
		// 光强检测器加入集合
		for (String loli : lolis) {
			organs.add(loli);
		}
		String[] fds = fireDetectorDAO.countFD(organIds);
		// 火灾检测器加入集合
		for (String fd : fds) {
			organs.add(fd);
		}
		String[] covis = coviDAO.countCovi(organIds);
		// covi检测器加入集合
		for (String covi : covis) {
			organs.add(covi);
		}
		String[] nds = noDetectorDAO.countND(organIds);
		// 氮氧化合物检测器加入集合
		for (String nd : nds) {
			organs.add(nd);
		}
		String[] pbs = pushButtonDAO.countPB(organIds);
		// 手动报警按钮加入集合
		for (String pb : pbs) {
			organs.add(pb);
		}
		String[] cds = controlDeviceDAO.countCD(organIds);
		// 控制设备加入集合
		for (String cd : cds) {
			organs.add(cd);
		}
		String[] bts = boxTransformerDAO.countBT(organIds);
		// 箱变电力监控加入集合
		for (String bt : bts) {
			organs.add(bt);
		}
		String[] vids = viDetectorDAO.countVID(organIds);
		// 能见度仪加入集合
		for (String vid : vids) {
			organs.add(vid);
		}
		String[] rds = roadDetectorDAO.countRD(organIds);
		// 路面检测器加入集合
		for (String rd : rds) {
			organs.add(rd);
		}
		String[] bds = bridgeDetectorDAO.countBD(organIds);
		// 桥面检测器加入集合
		for (String bd : bds) {
			organs.add(bd);
		}
		String[] ups = urgentPhoneDAO.countUP(organIds);
		// 紧急电话加入集合
		for (String up : ups) {
			organs.add(up);
		}
		int j = 0;
		CountDeviceDTO dto = new CountDeviceDTO();
		String organName[] = new String[organs.size()];
		int cData[] = new int[organs.size()];
		int dData[] = new int[organs.size()];
		int sData[] = new int[organs.size()];
		// Iterator<String> it = organs.iterator();
		for (String organ : organs) {
			int cTotal = cameraDAO.countByOrganId(organ);
			int dTotal = dvrDAO.countByOrganId(organ);
			int sTotal = wsDAO.countByOrganId(organ);
			sTotal += vdDAO.countByOrganId(organ);
			sTotal += wstDAO.countByOrganId(organ);
			sTotal += loliDAO.countByOrganId(organ);
			sTotal += fireDetectorDAO.countByOrganId(organ);
			sTotal += coviDAO.countByOrganId(organ);
			sTotal += noDetectorDAO.countByOrganId(organ);
			sTotal += pushButtonDAO.countByOrganId(organ);
			sTotal += controlDeviceDAO.countByOrganId(organ);
			sTotal += boxTransformerDAO.countByOrganId(organ);
			sTotal += viDetectorDAO.countByOrganId(organ);
			sTotal += roadDetectorDAO.countByOrganId(organ);
			sTotal += bridgeDetectorDAO.countByOrganId(organ);
			sTotal += urgentPhoneDAO.countByOrganId(organ);
			organName[j] = organDAO.findById(organ).getName();
			dData[j] = dTotal;
			cData[j] = cTotal;
			sData[j] = sTotal;
			j++;
		}
		List<CountDataTypeVO> listVO = new ArrayList<CountDataTypeVO>();
		CountDataTypeVO cvo = new CountDataTypeVO();
		CountDataTypeVO dvo = new CountDataTypeVO();
		CountDataTypeVO svo = new CountDataTypeVO();

		int cameraCount = 0;
		int dvrCount = 0;
		int sCount = 0;

		if (deviceType.equals("1")) {
			cvo.setData(cData);
			for (int z = 0; z < cData.length; z++) {
				cameraCount += cData[z];
			}
			cvo.setName("摄像头:(" + cameraCount + ")个");
			listVO.add(cvo);
			dvo.setData(dData);
			for (int x = 0; x < dData.length; x++) {
				dvrCount += dData[x];
			}
			dvo.setName("视频服务器:(" + dvrCount + ")个");
			listVO.add(dvo);
			svo.setData(sData);
			for (int y = 0; y < sData.length; y++) {
				sCount += sData[y];
			}
			svo.setName("数据设备:(" + sCount + ")个");
			listVO.add(svo);
			dto.setDeviceCount(listVO);
		} else if (deviceType.equals("2")) {
			cvo.setData(cData);
			for (int z = 0; z < cData.length; z++) {
				cameraCount += cData[z];
			}
			cvo.setName("摄像头:(" + cameraCount + ")个");
			listVO.add(cvo);
			dto.setDeviceCount(listVO);
		} else if (deviceType.equals("3")) {
			dvo.setData(dData);
			for (int x = 0; x < dData.length; x++) {
				dvrCount += dData[x];
			}
			dvo.setName("视频服务器:(" + dvrCount + ")个");
			listVO.add(dvo);
			dto.setDeviceCount(listVO);
		} else if (deviceType.equals("4")) {
			svo.setData(sData);
			for (int x = 0; x < sData.length; x++) {
				sCount += sData[x];
			}
			svo.setName("数据设备:(" + sCount + ")个");
			listVO.add(svo);
			dto.setDeviceCount(listVO);
		} else {
			dto.setDeviceCount(null);
		}

		dto.setOrganName(organName);

		return dto;
	}

	@Override
	public Workbook checkoutIo(InputStream in) {
		Workbook wb = null;
		try {// excel2007以上版本解析
			wb = new XSSFWorkbook(in);
		} catch (IOException i) {
			i.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"excel stream read error !");
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		} catch (OfficeXmlFileException e) {// excel2007版本以前版本解析
			try {
				wb = new HSSFWorkbook(in);
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
						"excel stream read error !");
			} catch (POIXMLException p) {
				p.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			} catch (Exception e3) {
				e.printStackTrace();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return wb;
	}

	public List<Dvr> readDvrWb(Workbook wb, Organ organ) {
		List<Dvr> list = new ArrayList<Dvr>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet dvrSheet = wb.getSheetAt(0);
			list = readDvrRows(dvrSheet, organ);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	public List<Camera> readCameraWB(Workbook wb, Organ organ, License license,
			List<Dvr> dvrs) {
		List<Camera> cList = new ArrayList<Camera>();
		try {
			Sheet cameraSheet = wb.getSheetAt(1);
			cList = readCameraRows(cameraSheet, organ, dvrs, license);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return cList;
	}

	private List<Dvr> readDvrRows(Sheet sheet, Organ organ) {
		List<Dvr> list = new ArrayList<Dvr>();
		List<Manufacturer> manufs = manufacturerDAO.findAll();
		List<String> dvrNames = dvrDAO.listDvrName(); // dvr列表
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		List<String> snList = dvrDAO.listDvrSN(); // 查询所有dvr的sn标准号
		List<String> dvrNumbers = new ArrayList<String>();
		String[] standardNumber = batchGenerateSN("Dvr", organ.getId(),
				(rows - 1)); // 获取Dvr的标准号
		isExcelModel(sheet.getRow(0));
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {// 如果不是第一行，就去解析列
					// dvrNames.add(isNameExist(dvrNames, rowIndex, row)); //
					// 判断dvr名称是否重复
					dvrNumbers.add(isDvrNumber(row, rowIndex, dvrNumbers)); // 判断给定的设备标号，不能重复，以便摄像头对应相应的Dvr
					// snList.add(isDvrStandardNumber(row, rowIndex, snList));//
					// 判断sn是否和数据库重复，如果不重复则加入列表继续判断，如果重复则抛异常
					list.add(readDvrCells(row, rowIndex, organ,
							standardNumber[notnullRowIndex - 1], manufs, snList));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}
		// 如果dvr没有数据则抛异常
		if (list.size() <= 0) {
			throw new BusinessException(ErrorCode.EXCEL_DVR_IS_NULL,
					"excel dvr sheet is null");
		}
		return list;
	}

	private String isNameExist(List<String> dvrs, int rowIndex, Row row) {
		Cell cell = row.getCell(0);
		String name = "";
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dvrName is not null");

		} else {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			name = cell.getStringCellValue();
			if (dvrs.contains(name)) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 1 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.DVR_NAME_EXIST + ",name["
								+ name + "] is already exist !");
			}
		}
		return name;
	}

	private String isDvrNumber(Row row, int rowIndex, List<String> dvrNumbers) {
		Cell cell = row.getCell(11);
		Long dvrNumber = null;
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 12 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dvrNumber is not null");
		} else {
			try {
				row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
				String dn = cell.getStringCellValue();
				if (dvrNumbers.size() > 0) {
					if (dvrNumbers.contains(dn)) {
						throw new BusinessException(
								ErrorCode.EXCEL_CONTENT_ERROR, "excel row:"
										+ (rowIndex + 1) + ",cellIndex:" + 12
										+ "," + TypeDefinition.DVR_TEMPLATE
										+ "," + TypeDefinition.PARAMETER_ERROR
										+ ",parameter dvrNumber[" + dn
										+ "] invalid !");
					}
					dvrNumber = Long.parseLong(dn);
				}
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 12
								+ "," + TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter dvrNumber["
								+ cell.getStringCellValue() + "] invalid !");
			}

		}
		return dvrNumber + "";
	}

	public Dvr readDvrCells(Row row, int rowIndex, Organ organ,
			String standardNumber, List<Manufacturer> manufs,
			List<String> snList) {
		Dvr dvr = new Dvr();
		VideoDeviceProperty property = new VideoDeviceProperty();
		Cell cell = row.getCell(0);
		row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
		dvr.setName(cell.getStringCellValue());

		cell = row.getCell(1);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",subType is not null");
		} else {
			Integer subType = 1;
			try {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				subType = Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 2 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter subType["
								+ cell.getStringCellValue() + "] invalid !");
			}
			dvr.setSubType("0" + subType);
		}

		cell = row.getCell(2);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",maxConnect is not null");
		} else {
			Integer maxConnect = 10;
			try {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				maxConnect = Integer.parseInt(cell.getStringCellValue());
				if (maxConnect < 1 || maxConnect > 32) {
					throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
							"excel row:" + (rowIndex + 1) + ",cellIndex:" + 3
									+ "," + TypeDefinition.DVR_TEMPLATE + ","
									+ TypeDefinition.PARAMETER_ERROR
									+ ",parameter maxConnect["
									+ cell.getStringCellValue() + "] invalid !");
				}
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 3 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter maxConnect["
								+ cell.getStringCellValue() + "] invalid !");
			}
			dvr.setMaxConnect(maxConnect);
		}

		property.setHeartCycle(120);

		cell = row.getCell(3);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",channelAmount is not null");
		} else {
			Integer channelAmount = 1;
			try {
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				channelAmount = Integer.parseInt(cell.getStringCellValue());
				if (channelAmount < 1 || channelAmount > 1000) {
					throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
							"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4
									+ "," + TypeDefinition.DVR_TEMPLATE + ","
									+ TypeDefinition.PARAMETER_ERROR
									+ ",parameter channelAmount["
									+ cell.getStringCellValue() + "] invalid !");
				}
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter channelAmount["
								+ cell.getStringCellValue() + "] invalid !");
			}
			dvr.setChannelAmount(channelAmount);
		}

		cell = row.getCell(4);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 5 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",userName is not null");
		} else {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String userName = cell.getStringCellValue();
			property.setUserName(userName);
		}

		cell = row.getCell(5);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",password is not null");
		} else {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String password = cell.getStringCellValue();
			property.setPassword(Base64Utils.getBASE64(password.getBytes()));
		}

		cell = row.getCell(6);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 7 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",manufacturer is not null");
		} else {
			String manufacturerId = "1";
			try {
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				manufacturerId = cell.getStringCellValue();
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 7 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter manufacturer["
								+ cell.getStringCellValue() + "] invalid !");
			}
			Manufacturer manuf = findManuf(manufs, manufacturerId, rowIndex, 7,
					1);
			dvr.setManufacturer(manuf);
		}

		cell = row.getCell(7);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 8 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",ip is not null");
		} else {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			if (!isIp(cell.getStringCellValue())) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 8 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR + ",ip error");
			} else {
				String lanIp = cell.getStringCellValue();
				dvr.setLanIp(lanIp);
			}
		}

		cell = row.getCell(8);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 9 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",port is not null");
		} else {
			Integer port = 8000;
			try {
				row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				port = Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 9 + ","
								+ TypeDefinition.DVR_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter port["
								+ cell.getStringCellValue() + "] invalid !");
			}
			dvr.setPort(port + "");
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
		}
		dvr.setLocation(cell == null ? " " : cell.getStringCellValue());

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
		}
		dvr.setNote(cell == null ? "" : cell.getStringCellValue());

		cell = row.getCell(11);
		row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
		dvr.setLinkType(cell.getStringCellValue());

		cell = row.getCell(12);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 12 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",ccs is not null");
		}
		row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", cell.getStringCellValue());
		dvr.setCcs(ccsDAO.findByPropertys(params).get(0));

		cell = row.getCell(13);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 14 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",transport is not null");
		}
		row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
		dvr.setTransport(cell.getStringCellValue());

		cell = row.getCell(14);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 15 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",mode is not null");
		}
		row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
		dvr.setMode(cell.getStringCellValue());

		cell = row.getCell(15);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 16 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",mode is not null");
		}
		row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
		property.setDecode(cell.getStringCellValue());
		property.setProtocol(cell.getStringCellValue());
		cell = row.getCell(16);
		if (null != cell) {
			row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
			String cellSn = cell.getStringCellValue();
			if (StringUtils.isNotBlank(cellSn)) {
				for (String sn : snList) {
					if (sn.equals(cell.getStringCellValue())) {
						throw new BusinessException(
								ErrorCode.EXCEL_CONTENT_ERROR, "excel row:"
										+ (rowIndex + 1) + ",cellIndex:" + 17
										+ "," + TypeDefinition.DVR_TEMPLATE
										+ "," + TypeDefinition.PARAMETER_ERROR
										+ ",sn error");
					}
				}
				dvr.setStandardNumber(cellSn);
			} else {
				dvr.setStandardNumber(standardNumber);
			}
		} else {
			dvr.setStandardNumber(standardNumber);
		}

		dvr.setOrgan(organ);
		dvr.setProperty(property);
		String id = (String) new UUIDHexGenerator().generate(null, null);
		dvr.setId(id);
		dvr.setType(TypeDefinition.DEVICE_TYPE_DVR);
		return dvr;
	}

	private Manufacturer findManuf(List<Manufacturer> manufs,
			String manufacturerId, int rowIndex, int cellIndex, int templateType) {
		Manufacturer manufReturn = null;
		for (Manufacturer manuf : manufs) {
			if (manuf.getId().equals(manufacturerId)) {
				manufReturn = manuf;
				break;
			}
		}
		if (manufReturn == null) {
			if (templateType == 1) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:"
								+ cellIndex + "," + templateType + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter manufacturerId[" + manufacturerId
								+ "] invalid !");
			} else if (templateType == 2) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:"
								+ cellIndex + "," + templateType + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter manufacturerId[" + manufacturerId
								+ "] invalid !");
			}
		}

		return manufReturn;
	}

	// 判断IP正确性
	public boolean isIp(String ipAddress) {
		String test = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(test);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	public List<Camera> readCameraRows(Sheet sheet, Organ organ,
			List<Dvr> dvrs, License license) {
		List<Camera> list = new ArrayList<Camera>();
		List<Manufacturer> manufs = manufacturerDAO.findAll();
		// 校验license
		int rows = sheet.getPhysicalNumberOfRows();
		int cameraAmount = Integer.parseInt(license.getCameraAmount());
		int cameraCount = countCamera();
		if (((rows - 1) + cameraCount) > cameraAmount) {
			throw new BusinessException(ErrorCode.CAMERA_AMOUNT_LIMIT,
					"camera more than limit");
		}
		// 创建摄像头standardNumber
		String[] standardNumber = batchGenerateSN("Camera", organ.getId(),
				(rows - 1));
		List<TemplateDvrIsChannelNumberVO> tdcs = new ArrayList<TemplateDvrIsChannelNumberVO>();
		List<String> cameraNames = cameraDAO.findNameByCamera(); // 查询所有摄像头名称
		List<String> snList = cameraDAO.listCameraSN();
		List<String> dvrNumbers = new ArrayList<String>(); // 用于判断从excel中获取的dvrNumber是否出现在列表中
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		int[] arrayChannelAmount = null; // dvr通道号数组
		Dvr dvr = null;
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {// 如果不是第一行，就去解析列
					// 如果excel表中摄像头standardNumber相等就从内存里边取dvr，反之则重新查询一次dvr
					String dvrNumber = findDvrNumber(row, rowIndex);
					// cameraNames.add(isCameraNameExist(cameraNames, rowIndex,
					// row)); // 判断摄像头名称是否重复
					if (dvr == null) {
						TemplateDvrIsChannelNumberVO tdc = new TemplateDvrIsChannelNumberVO();
						dvr = findDvr(dvrs, dvrNumber, rowIndex);
						arrayChannelAmount = new int[dvr.getChannelAmount()];
						for (int i = 0; i < arrayChannelAmount.length; i++) {// 初始化数组
							arrayChannelAmount[i] = i + 1;
						}
						dvrNumbers.add(dvrNumber);
						tdc.setDvrNumber(dvrNumber);
						tdc.setArrayChannelAmount(arrayChannelAmount);
						tdcs.add(tdc);
					} else {
						if (!dvrNumbers.contains(dvrNumber)) {
							TemplateDvrIsChannelNumberVO tdc = new TemplateDvrIsChannelNumberVO();
							dvr = findDvr(dvrs, dvrNumber, rowIndex);
							arrayChannelAmount = new int[dvr.getChannelAmount()];
							for (int n = 0; n < arrayChannelAmount.length; n++) {// 初始化数组
								arrayChannelAmount[n] = n + 1;
							}
							dvrNumbers.add(dvrNumber);
							tdc.setDvrNumber(dvrNumber);
							tdc.setArrayChannelAmount(arrayChannelAmount);
							tdcs.add(tdc);
						}
					}
					isExistChannelNumber(row, dvr, rowIndex, tdcs);// 判断摄像头通道号重复性
					list.add(readCameraCells(row, rowIndex, organ, dvr,
							standardNumber[notnullRowIndex - 1], manufs, snList));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}
		return list;
	}

	private String isCameraNameExist(List<String> cameraNames, int rowIndex,
			Row row) {
		Cell cell = row.getCell(0);
		String name = "";
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 1 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",cameraName is not null");

		} else {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			name = cell.getStringCellValue();
			if (cameraNames.contains(name)) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 1 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.CAMERA_NAME_EXIST + ",name["
								+ name + "] is already exist !");
			}
		}
		return name;
	}

	/**
	 * 
	 * 判断dvr下摄像头通道号是否重复以及摄像头通道号数量是否大于dvr通道号
	 * 
	 * @param row
	 *            行
	 * @param cameraNumber
	 *            摄像头数量
	 * @param dvr
	 *            dvr实体
	 * @param rowIndex
	 *            摄像头行数
	 * @param arrayChannelAmount
	 *            dvr通道数数组
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:39:57
	 */
	private void isExistChannelNumber(Row row, Dvr dvr, int rowIndex,
			List<TemplateDvrIsChannelNumberVO> tdcs) {
		int arrayChannelAmount[] = null;
		for (int i = 0; i < tdcs.size(); i++) {
			if (row.getCell(7).getStringCellValue()
					.equals(tdcs.get(i).getDvrNumber())) {
				arrayChannelAmount = tdcs.get(i).getArrayChannelAmount();
				break;
			}
		}
		int channelAmount = dvr.getChannelAmount();
		Cell cell = row.getCell(3);
		int channelNumber = 0;
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",channelNumber is not null");
		} else {
			try {
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				channelNumber = Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter channelNumber["
								+ cell.getStringCellValue() + "] invalid !");
			}
		}
		if (channelNumber > channelAmount) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.CAMERA_OVER_LIMIT
							+ ",Channel amount[" + channelAmount
							+ "] over limit !");
		}

		if (channelNumber == arrayChannelAmount[channelNumber - 1]) {
			arrayChannelAmount[channelNumber - 1] = -1;
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 4 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.CAMERA_CHANNEL_NUMBER_EXIST
							+ ",channelId[" + channelNumber
							+ "] is already exist !");
		}
	}

	private String[] batchGenerateSN(String className, String organId, int size) {
		Organ organ = null;
		if (StringUtils.isBlank(organId)) {
			organ = organDAO.getRootOrgan();
		} else {
			organ = organDAO.findById(organId);
		}
		String prefix = organ.getStandardNumber().substring(0, 10);
		String objectCode = StandardObjectCode.getObjectCode(className);
		int seq = userDAO.getStandardNumber(className, size);
		String[] rtn = new String[size];
		for (int i = 0; i < size; i++) {
			rtn[i] = prefix + objectCode + intToSixLengthString(seq + i);
		}
		return rtn;
	}

	/**
	 * 数字转为6位长度的字符串,不够位数时,左边补"0". 如: 15->"000015"
	 * 
	 * @param seq
	 *            要转变的数字
	 * @return 6位长度的字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:59:59
	 */
	private String intToSixLengthString(int seq) {
		String s = seq + "";
		int i = 6 - s.length();
		if (i <= 0) {
			return s;
		}
		while (i > 0) {
			s = "0" + s;
			i--;
		}
		return s;
	}

	/**
	 * 
	 * 解析摄像头中的dvr sn
	 * 
	 * @param row
	 *            行
	 * @param rowIndex
	 *            行索引
	 * @return standardNumber
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:52:45
	 */
	private String findDvrNumber(Row row, int rowIndex) {
		Cell cell = row.getCell(7);
		row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
		String dvrNumberString = cell.getStringCellValue();
		Long dvrNumber = null;
		if (StringUtils.isBlank(dvrNumberString)) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 8 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dvrNumber is not null");
		} else {
			try {
				dvrNumber = Long.parseLong(dvrNumberString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 8 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter dvrNumber[" + dvrNumberString
								+ "] invalid !");
			}
		}
		return dvrNumber + "";
	}

	/**
	 * 
	 * 根据sn查询dvr
	 * 
	 * @param row
	 *            行
	 * @param map
	 *            map集合放sn参数
	 * @param rowIndex
	 *            行索引
	 * @param standardNumber
	 *            sn标准号
	 * @return Dvr实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:03:14
	 */
	public Dvr findDvr(List<Dvr> dvrs, String dvrNumber, int rowIndex) {
		Dvr dvrReturn = null;
		for (Dvr dvr : dvrs) {
			if (dvr.getLinkType().equals(dvrNumber)) {
				dvrReturn = dvr;
				break;
			}
		}
		if (dvrReturn == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 8 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_ERROR
							+ ",Camera dvrNumber[" + dvrNumber
							+ "] not found !");
		}
		return dvrReturn;
	}

	public Camera readCameraCells(Row row, int rowIndex, Organ organ, Dvr dvr,
			String standardNumber, List<Manufacturer> manufs,
			List<String> snList) {
		Camera camera = new Camera();
		VideoDeviceProperty property = new VideoDeviceProperty();
		Cell cell = null;
		cell = row.getCell(0);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex:" + 1 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		} else {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			camera.setName(cell.getStringCellValue());
		}

		cell = row.getCell(1);
		if (cell == null) {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.CAMERA_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",subType is not null");
		} else {
			Integer subType = null;
			try {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				subType = Integer.parseInt(cell.getStringCellValue());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 2 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter subType["
								+ cell.getStringCellValue() + "] invalid !");
			}
			if (subType == 1) {
				camera.setSubType(TypeDefinition.SUBTYPE_CAMERA_DEFAULT);
			} else if (subType == 2) {
				camera.setSubType(TypeDefinition.SUBTYPE_CAMERA_BALL);
			}
		}

		cell = row.getCell(2);
		Short storeType = 0;
		if (cell != null) {
			try {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				storeType = Short.parseShort(cell.getStringCellValue());
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 3 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter storeType["
								+ cell.getStringCellValue() + "] invalid !");
			}
			property.setStoreType(storeType);
		} else {
			property.setStoreType(storeType);
		}

		cell = row.getCell(3);

		Short channelNumber = null;
		row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
		channelNumber = Short.parseShort(cell.getStringCellValue());
		camera.setChannelNumber(channelNumber);

		cell = row.getCell(4);
		Manufacturer manuf = null;
		if (cell != null) {
			String manufacturerId = "";
			try {
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				manufacturerId = cell.getStringCellValue();
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex:" + 5 + ","
								+ TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_ERROR
								+ ",parameter channelNumber["
								+ cell.getStringCellValue() + "] invalid !");
			}
			if (StringUtils.isNotBlank(manufacturerId)) {
				manuf = findManuf(manufs, manufacturerId, rowIndex, 5, 2);
			}
		}
		camera.setManufacturer(manuf);

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
		}
		camera.setLocation(cell == null ? " " : cell.getStringCellValue());

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
		}
		camera.setNote(cell == null ? " " : cell.getStringCellValue());

		cell = row.getCell(8);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if (property.getStoreType() == 1 || property.getStoreType() == 2) {
			if (cell == null) {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex: " + 9
								+ "," + TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_NULL
								+ ",crs is not null");
			}
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			if (StringUtils.isNotBlank(cell.getStringCellValue())) {
				params.put("standardNumber", cell.getStringCellValue());
				camera.setCrs(crsDAO.findByPropertys(params).get(0));
				params.clear();
				property.setCenterStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
			} else {
				throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
						"excel row:" + (rowIndex + 1) + ",cellIndex: " + 9
								+ "," + TypeDefinition.CAMERA_TEMPLATE + ","
								+ TypeDefinition.PARAMETER_NULL
								+ ",crs is not null");
			}
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			if (StringUtils.isNotBlank(cell.getStringCellValue())) {
				params.put("standardNumber", cell.getStringCellValue());
				camera.setMss(mssDAO.findByPropertys(params).get(0));
				params.clear();
			} else {
				camera.setMss(null);
			}
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			camera.setNavigation(cell.getStringCellValue());
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			camera.setStakeNumber(cell.getStringCellValue());
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String cellSn = cell.getStringCellValue();
			if (StringUtils.isNotBlank(cellSn)) {
				for (String sn : snList) {
					if (sn.equals(cell.getStringCellValue())) {
						throw new BusinessException(
								ErrorCode.EXCEL_CONTENT_ERROR, "excel row:"
										+ (rowIndex + 1) + ",cellIndex:" + 13
										+ "," + TypeDefinition.CAMERA_TEMPLATE
										+ "," + TypeDefinition.PARAMETER_ERROR
										+ ",sn error");
					}
				}
				camera.setStandardNumber(cellSn);
			} else {
				camera.setStandardNumber(standardNumber);
			}
		} else {
			camera.setStandardNumber(standardNumber);
		}

		camera.setCreateTime(System.currentTimeMillis());
		camera.setParent(dvr);
		camera.setOrgan(organ);
		camera.setProperty(property);
		camera.setType(TypeDefinition.DEVICE_TYPE_CAMERA);
		return camera;
	}

	@Override
	public void batchInsertDvrAndCamera(List<Dvr> dvrs, List<Camera> cameras) {
		String dvrId = dvrDAO.getNextId("Dvr", dvrs.size());
		for (int i = 0; i < dvrs.size(); i++) {
			dvrs.get(i).setId(
					(new BigDecimal(dvrId).add(new BigDecimal(i))).toString());
			dvrDAO.batchInsertDvr(dvrs.get(i));
		}
		dvrDAO.excuteBatchDvr();
		if (cameras.size() > 0) {
			String cameraId = cameraDAO.getNextId("Camera", cameras.size());
			for (int i = 0; i < cameras.size(); i++) {
				cameras.get(i).setId(
						(new BigDecimal(cameraId).add(new BigDecimal(i)))
								.toString());
				cameraDAO.batchInsert(cameras.get(i));
			}
			cameraDAO.excuteBatch();
		}
	}

	@Override
	public String[] batchGenerateId(String className, int length) {
		String id = cameraDAO.getNextId(className, length);
		String[] ids = new String[length];
		for (int i = 0; i < length; i++) {
			ids[i] = (new BigDecimal(id).add(new BigDecimal(i))).toString();
		}
		return ids;
	}

	@Override
	public String[] batchDvrId(String className, int length) {
		String id = dvrDAO.getNextId(className, length);
		String[] ids = new String[length];
		for (int i = 0; i < length; i++) {
			ids[i] = (new BigDecimal(id).add(new BigDecimal(i))).toString();
		}
		return ids;
	}

	@Override
	public void batchInsertDvr(List<Dvr> dvrs) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < dvrs.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			dvrDAO.batchInsertDvr(dvrs.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(dvrs.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_DVR);
			list.add(sn);
		}
		dvrDAO.excuteBatchDvr();
		batchInsertSN(list);
	}

	@Override
	public void batchInsertCamera(List<Camera> cameras) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < cameras.size(); i++) {
			// cameras.get(i).setId(cameraIds[i]);
			cameraDAO.batchInsert(cameras.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(cameras.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CAMERA);
			list.add(sn);
		}
		cameraDAO.excuteBatch();
		batchInsertSN(list);
	}

	private void isExcelModel(Row row) {
		if (row == null) {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format invalid");
		}

		if (row.getCell(0) != null) {
			if (!replaceBlank(row.getCell(0).getStringCellValue()).equals(
					"设备名称")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 1cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(1) != null) {
			if (!replaceBlank(row.getCell(1).getStringCellValue()).equals(
					"设备类型(0:DVS,1:DVR,2:IPC)")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 2cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(2) != null) {
			if (!replaceBlank(row.getCell(2).getStringCellValue()).equals(
					"最大连接数(最小值:1,最大值：32)")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 3cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(3) != null) {
			if (!replaceBlank(row.getCell(3).getStringCellValue()).equals(
					"视频通道数(最小值:1,最大值:1000)")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 4cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(4) != null) {
			if (!replaceBlank(row.getCell(4).getStringCellValue()).equals(
					"用户名称")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 5cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(5) != null) {
			if (!replaceBlank(row.getCell(5).getStringCellValue()).equals("密码")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 6cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(6) != null) {
			if (!replaceBlank(row.getCell(6).getStringCellValue()).equals(
					"设备厂商(默认为海康对应号1,如需要选择其他则对应手册)")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 7cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(7) != null) {
			if (!replaceBlank(row.getCell(7).getStringCellValue()).equals(
					"ip地址")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 8cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(8) != null) {
			if (!replaceBlank(row.getCell(8).getStringCellValue()).equals(
					"消息端口")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 9cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(9) != null) {
			if (!replaceBlank(row.getCell(9).getStringCellValue()).equals(
					"安装位置")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 10cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(10) != null) {
			if (!replaceBlank(row.getCell(10).getStringCellValue())
					.equals("备注")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 11cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(11) != null) {
			if (!replaceBlank(row.getCell(11).getStringCellValue()).equals(
					"视频服务器标号")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 12cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(12) != null) {
			if (!replaceBlank(row.getCell(12).getStringCellValue()).equals(
					"通信服务器标准号")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 13cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(13) != null) {
			if (!replaceBlank(row.getCell(13).getStringCellValue()).equals(
					"流传输协议")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 14cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(14) != null) {
			if (!replaceBlank(row.getCell(14).getStringCellValue()).equals(
					"接入类型")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 15cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}
	}

	/**
	 * 
	 * 去掉字符串空格换行
	 * 
	 * @param str
	 * @return string
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:47:56
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	@Override
	public Element listMcuDevice(Integer startIndex, Integer limit,
			List<AuthCameraVO> devices) {
		Element channelList = new Element("ChannelList");
		List<AuthCameraVO> list = new ArrayList<AuthCameraVO>();
		if (devices.size() > 0) {
			if (startIndex >= devices.size()) {
				// startIndex -= ((startIndex - devices.size()) / limit + 1)
				// * limit;
				// list = devices.subList(startIndex, devices.size());
				list = null;
			} else {
				if (startIndex + limit < devices.size()) {
					list = devices.subList(startIndex, startIndex + limit);
				} else {
					list = devices.subList(startIndex, devices.size());
				}
			}
			if (list != null) {
				for (AuthCameraVO camera : list) {
					Element channel = new Element("Channel");
					channel.setAttribute("Id", camera.getId());
					channel.setAttribute(
							"StandardNumber",
							camera.getStandardNumber() != null ? camera
									.getStandardNumber() : "");
					channel.setAttribute("Name",
							camera.getName() != null ? camera.getName() : "");
					channel.setAttribute("SubType",
							camera.getSubType() != null ? camera.getSubType()
									: "");
					channel.setAttribute("Status",
							camera.getStatus() != null ? camera.getStatus()
									: "");
					channel.setAttribute("Auth",
							camera.getAuth() != null ? camera.getAuth() : "");
					channel.setAttribute(
							"ChannelNumber",
							camera.getChannelNumber() != null ? camera
									.getChannelNumber() : "");
					channel.setAttribute(
							"Location",
							camera.getLocation() == null ? "" : camera
									.getLocation());
					channelList.addContent(channel);
				}
			}
		}
		return channelList;
	}

	@Override
	public String createFireDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<FireDetector> list = fireDetectorDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = fireDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		FireDetector fireDetector = new FireDetector();
		fireDetector.setCreateTime(System.currentTimeMillis());
		fireDetector.setDas(dasDAO.findById(dasId));
		fireDetector.setLatitude(latitude);
		fireDetector.setLongitude(longitude);
		fireDetector.setName(name);
		fireDetector.setNote(note);
		fireDetector.setOrgan(organDAO.findById(organId));
		fireDetector.setPeriod(period);
		fireDetector.setStakeNumber(stakeNumber);
		fireDetector.setStandardNumber(standardNumber);
		fireDetector.setNavigation(navigation);
		fireDetector.setReserve(reserve);
		fireDetector.setIp(ip);
		fireDetector.setPort(port);
		fireDetectorDAO.save(fireDetector);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_FD);
		return fireDetector.getId();
	}

	@Override
	public void updateFireDetector(@LogParam("id") String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<FireDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = fireDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = fireDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		FireDetector fireDetector = fireDetectorDAO.findById(id);
		if (null != name) {
			fireDetector.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(fireDetector.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_FD);
			fireDetector.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			fireDetector.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			fireDetector.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			fireDetector.setPeriod(period);
		}
		if (null != stakeNumber) {
			fireDetector.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			fireDetector.setLongitude(longitude);
		}
		if (null != latitude) {
			fireDetector.setLatitude(latitude);
		}
		if (null != note) {
			fireDetector.setNote(note);
		}
		if (null != navigation) {
			fireDetector.setNavigation(navigation);
		}
		if (null != reserve) {
			fireDetector.setReserve(reserve);
		}
		if (null != ip) {
			fireDetector.setIp(ip);
		}
		fireDetector.setPort(port);
		fireDetectorDAO.update(fireDetector);
	}

	@Override
	public void deleteFireDetector(String id) {
		// 删除角色和设备的关联关系
		fireDetectorDAO.deleteRoleFDPermission(id);

		// 同步SN
		FireDetector fd = fireDetectorDAO.findById(id);
		syncSN(fd.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_FD);
		fireDetectorDAO.delete(fd);

	}

	@Override
	public GetFireDetectorVO getFireDetector(String id) {
		FireDetector fd = fireDetectorDAO.findById(id);

		GetFireDetectorVO fireDetectorVO = new GetFireDetectorVO();
		fireDetectorVO.setId(id + "");
		fireDetectorVO.setCreateTime(fd.getCreateTime() + "");
		fireDetectorVO.setDasId(fd.getDas() != null ? fd.getDas().getId() : "");
		fireDetectorVO.setDasName(fd.getDas() != null ? fd.getDas().getName()
				: "");
		fireDetectorVO.setLatitude(fd.getLatitude());
		fireDetectorVO.setLongitude(fd.getLongitude());
		fireDetectorVO.setName(fd.getName());
		fireDetectorVO.setNote(fd.getNote());
		fireDetectorVO.setOrganId(fd.getOrgan().getId());
		fireDetectorVO.setPeriod(fd.getPeriod() + "");
		fireDetectorVO.setReserve(fd.getReserve());
		fireDetectorVO.setStakeNumber(fd.getStakeNumber());
		fireDetectorVO.setStandardNumber(fd.getStandardNumber());
		fireDetectorVO.setNavigation(fd.getNavigation());
		fireDetectorVO.setIp(fd.getIp());
		fireDetectorVO.setPort(fd.getPort() + "");
		return fireDetectorVO;
	}

	@Override
	public Integer countFireDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return fireDetectorDAO.countFireDetector(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetFireDetectorVO> listFireDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		List<FireDetector> list = fireDetectorDAO.listFireDetector(organId,
				name, standardNumber, stakeNumber, startIndex, limit);
		List<GetFireDetectorVO> listVO = new ArrayList<GetFireDetectorVO>();
		for (FireDetector fd : list) {
			GetFireDetectorVO vo = new GetFireDetectorVO();
			vo.setCreateTime(fd.getCreateTime() + "");
			vo.setDasId(fd.getDas() != null ? fd.getDas().getId() : "");
			vo.setDasName(fd.getDas() != null ? fd.getDas().getName() : "");
			vo.setId(fd.getId() + "");
			vo.setLatitude(fd.getLatitude());
			vo.setLongitude(fd.getLongitude());
			vo.setName(fd.getName());
			vo.setNote(fd.getNote());
			vo.setOrganId(fd.getOrgan() != null ? fd.getOrgan().getId() : "");
			vo.setPeriod(fd.getPeriod() + "");
			vo.setReserve(fd.getReserve());
			vo.setStakeNumber(fd.getStakeNumber());
			vo.setStandardNumber(fd.getStandardNumber());
			vo.setNavigation(fd.getNavigation());
			vo.setIp(fd.getIp());
			vo.setPort(fd.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createCovi(String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short coConctLimit,
			Short visibilityLimit, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Covi> list = coviDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = coviDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Covi covi = new Covi();
		covi.setCreateTime(System.currentTimeMillis());
		covi.setDas(dasDAO.findById(dasId));
		covi.setLatitude(latitude);
		covi.setLongitude(longitude);
		covi.setName(name);
		covi.setNote(note);
		covi.setOrgan(organDAO.findById(organId));
		covi.setPeriod(period);
		covi.setStakeNumber(stakeNumber);
		covi.setStandardNumber(standardNumber);
		covi.setCoConctLimit(coConctLimit);
		covi.setVisibilityLimit(visibilityLimit);
		covi.setNavigation(navigation);
		covi.setReserve(reserve);
		covi.setIp(ip);
		covi.setPort(port);
		coviDAO.save(covi);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_COVI);
		return covi.getId();
	}

	@Override
	public void updateCovi(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short coConctLimit,
			Short visibilityLimit, String note, String navigation,
			String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Covi> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = coviDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = coviDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Covi covi = coviDAO.findById(id);
		if (null != name) {
			covi.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(covi.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_COVI);
			covi.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			covi.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			covi.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			covi.setPeriod(period);
		}
		if (null != stakeNumber) {
			covi.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			covi.setLongitude(longitude);
		}
		if (null != latitude) {
			covi.setLatitude(latitude);
		}
		covi.setCoConctLimit(coConctLimit);
		covi.setVisibilityLimit(visibilityLimit);
		if (null != note) {
			covi.setNote(note);
		}
		if (null != navigation) {
			covi.setNavigation(navigation);
		}
		if (null != reserve) {
			covi.setReserve(reserve);
		}
		if (null != ip) {
			covi.setIp(ip);
		}
		covi.setPort(port);
		coviDAO.update(covi);

	}

	@Override
	public void deleteCovi(String id) {
		// 删除一氧化碳检测器时先删除检测器和角色的关系
		coviDAO.deleteRoleCoviPermission(id);

		// 同步SN
		Covi covi = coviDAO.findById(id);
		syncSN(covi.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_COVI);
		coviDAO.delete(covi);
	}

	@Override
	public GetCoviVO getCovi(String id) {
		Covi covi = coviDAO.findById(id);
		GetCoviVO vo = new GetCoviVO();
		vo.setCoConctLimit(covi.getCoConctLimit() + "");
		vo.setCreateTime(covi.getCreateTime() + "");
		vo.setDasId(covi.getDas() != null ? covi.getDas().getId() : "");
		vo.setDasName(covi.getDas() != null ? covi.getDas().getName() : "");
		vo.setId(covi.getId() + "");
		vo.setLatitude(covi.getLatitude());
		vo.setLongitude(covi.getLongitude());
		vo.setName(covi.getName());
		vo.setNote(covi.getNote());
		vo.setOrganId(covi.getOrgan() != null ? covi.getOrgan().getId() : "");
		vo.setPeriod(covi.getPeriod() + "");
		vo.setReserve(covi.getReserve());
		vo.setStakeNumber(covi.getStakeNumber());
		vo.setStandardNumber(covi.getStandardNumber());
		vo.setVisibilityLimit(covi.getVisibilityLimit() + "");
		vo.setNavigation(covi.getNavigation());
		vo.setIp(covi.getIp());
		vo.setPort(covi.getPort() + "");
		return vo;
	}

	@Override
	public Integer countCovi(String organId, String name,
			String standardNumber, String stakeNumber) {
		return coviDAO.countCovi(organId, name, standardNumber, stakeNumber);
	}

	@Override
	public List<GetCoviVO> listCovi(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<Covi> list = coviDAO.listCovi(organId, name, standardNumber,
				stakeNumber, startIndex, limit);
		List<GetCoviVO> listVO = new ArrayList<GetCoviVO>();
		for (Covi covi : list) {
			GetCoviVO vo = new GetCoviVO();
			vo.setCreateTime(covi.getCreateTime() + "");
			vo.setDasId(covi.getDas() != null ? covi.getDas().getId() : "");
			vo.setDasName(covi.getDas() != null ? covi.getDas().getName() : "");
			vo.setId(covi.getId() + "");
			vo.setLatitude(covi.getLatitude());
			vo.setLongitude(covi.getLongitude());
			vo.setName(covi.getName());
			vo.setNote(covi.getNote());
			vo.setOrganId(covi.getOrgan() != null ? covi.getOrgan().getId()
					: "");
			vo.setPeriod(covi.getPeriod() + "");
			vo.setReserve(covi.getReserve());
			vo.setStakeNumber(covi.getStakeNumber());
			vo.setStandardNumber(covi.getStandardNumber());
			vo.setCoConctLimit(covi.getCoConctLimit() + "");
			vo.setVisibilityLimit(covi.getVisibilityLimit() + "");
			vo.setNavigation(covi.getNavigation());
			vo.setIp(covi.getIp());
			vo.setPort(covi.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createPushButton(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<PushButton> list = pushButtonDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = pushButtonDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		PushButton pushButton = new PushButton();
		pushButton.setCreateTime(System.currentTimeMillis());
		pushButton.setDas(dasDAO.findById(dasId));
		pushButton.setLatitude(latitude);
		pushButton.setLongitude(longitude);
		pushButton.setName(name);
		pushButton.setNote(note);
		pushButton.setOrgan(organDAO.findById(organId));
		pushButton.setPeriod(period);
		pushButton.setStakeNumber(stakeNumber);
		pushButton.setStandardNumber(standardNumber);
		pushButton.setNavigation(navigation);
		pushButton.setReserve(reserve);
		pushButton.setIp(ip);
		pushButton.setPort(port);
		pushButtonDAO.save(pushButton);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_PB);
		return pushButton.getId();
	}

	@Override
	public void updatePushButton(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<PushButton> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = pushButtonDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = pushButtonDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		PushButton pushButton = pushButtonDAO.findById(id);
		if (null != name) {
			pushButton.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(pushButton.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_PB);
			pushButton.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			pushButton.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			pushButton.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			pushButton.setPeriod(period);
		}
		if (null != stakeNumber) {
			pushButton.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			pushButton.setLongitude(longitude);
		}
		if (null != latitude) {
			pushButton.setLatitude(latitude);
		}
		if (null != note) {
			pushButton.setNote(note);
		}
		if (null != navigation) {
			pushButton.setNavigation(navigation);
		}
		if (null != reserve) {
			pushButton.setReserve(reserve);
		}
		if (null != ip) {
			pushButton.setIp(ip);
		}
		pushButton.setPort(port);
		pushButtonDAO.update(pushButton);

	}

	@Override
	public void deletePushButton(String id) {
		// 删除手动报警按钮时删除设备和角色的关系
		pushButtonDAO.deleteRolePBPermission(id);

		// 同步SN
		PushButton pb = pushButtonDAO.findById(id);
		syncSN(pb.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_PB);
		pushButtonDAO.delete(pb);
	}

	@Override
	public GetPushButtonVO getPushButton(String id) {
		PushButton pushButton = pushButtonDAO.findById(id);
		GetPushButtonVO vo = new GetPushButtonVO();
		vo.setCreateTime(pushButton.getCreateTime() + "");
		vo.setDasId(pushButton.getDas() != null ? pushButton.getDas().getId()
				: "");
		vo.setDasName(pushButton.getDas() != null ? pushButton.getDas()
				.getName() : "");
		vo.setId(pushButton.getId() + "");
		vo.setLatitude(pushButton.getLatitude());
		vo.setLongitude(pushButton.getLongitude());
		vo.setName(pushButton.getName());
		vo.setNote(pushButton.getNote());
		vo.setOrganId(pushButton.getOrgan() != null ? pushButton.getOrgan()
				.getId() : "");
		vo.setPeriod(pushButton.getPeriod() + "");
		vo.setReserve(pushButton.getReserve());
		vo.setStakeNumber(pushButton.getStakeNumber());
		vo.setStandardNumber(pushButton.getStandardNumber());
		vo.setNavigation(pushButton.getNavigation());
		vo.setIp(pushButton.getIp());
		vo.setPort(pushButton.getPort() + "");
		return vo;
	}

	@Override
	public List<GetPushButtonVO> listPushButton(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<PushButton> list = pushButtonDAO.listPushButton(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetPushButtonVO> listVO = new ArrayList<GetPushButtonVO>();
		for (PushButton pb : list) {
			GetPushButtonVO vo = new GetPushButtonVO();
			vo.setCreateTime(pb.getCreateTime() + "");
			vo.setDasId(pb.getDas() != null ? pb.getDas().getId() : "");
			vo.setDasName(pb.getDas() != null ? pb.getDas().getName() : "");
			vo.setId(pb.getId() + "");
			vo.setLatitude(pb.getLatitude());
			vo.setLongitude(pb.getLongitude());
			vo.setName(pb.getName());
			vo.setNote(pb.getNote());
			vo.setOrganId(pb.getOrgan() != null ? pb.getOrgan().getId() : "");
			vo.setPeriod(pb.getPeriod() + "");
			vo.setReserve(pb.getReserve());
			vo.setStakeNumber(pb.getStakeNumber());
			vo.setStandardNumber(pb.getStandardNumber());
			vo.setNavigation(pb.getNavigation());
			vo.setIp(pb.getIp());
			vo.setPort(pb.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public Integer countPushButton(String organId, String name,
			String standardNumber, String stakeNumber) {
		return pushButtonDAO.countPushButton(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public Collection<UserResourceVO> listUserResource(String userId) {
		// 所有的具有权限的设备存放地方
		Map<String, UserResourceVO> map = new LinkedHashMap<String, UserResourceVO>();
		// 级联查询不能正确处理SubClass的问题,手动查询一次全部机构
		List<Organ> all = organDAO.findAll();
		User user = userDAO.findById(userId);
		// 获取用户角色
		Set<Role> roles = user.getRoles();
		// 所有的本级机构
		List<Organ> organs = organDAO.listOrganById(user.getOrgan().getId());
		// 平台跟机构
		Organ root = organDAO.getRootOrgan();

		// 本级机构加入到返回集合中
		for (Organ organ : organs) {
			UserResourceVO vo = buildOrgan(organ);
			map.put(organ.getStandardNumber(), vo);
		}

		// 判断是否拥有系统角色，系统角色具有机构下的所有的设备的权限，自定义角色只拥有部分设备权限
		String auth = "";
		// 是否管理员角色
		boolean isAdmin = false;
		// 该用户的自定义角色ID列表
		List<String> roleIds = new ArrayList<String>();
		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)) {
				auth = "1,2,4";
				isAdmin = true;
				break;
			}
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)) {
				if (StringUtils.isBlank(auth)) {
					auth = "1";
				}
			} else if (role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				auth = "1,2";
			}
			// 加入到自定义角色列表中
			else {
				roleIds.add(role.getId());
			}
		}

		// 关联的设备的太阳能电池属性
		List<DeviceSolarVO> deviceSolars = solarBatteryDAO.listDeviceSolar();
		// 获取所有的下级平台资源
		List<SubPlatformResource> subResources = subPlatformResourceDAO
				.findAll();
		// 下级所有资源
		Map<String, UserResourceVO> subMap = new HashMap<String, UserResourceVO>();
		// 标识下级平台资源在本级的组织关系
		for (SubPlatformResource subResource : subResources) {
			UserResourceVO vo = map.get(subResource.getStandardNumber());
			// 本级不存在的，新增下级资源
			if (null == vo) {
				vo = buildSubResource(subResource, null);
				// 下级根节点在本级不存在，自动添加本级根机构，为下级平台的父节点
				if (StringUtils.isBlank(vo.getParent())) {
					vo.setParent(root.getStandardNumber());
				}
				subMap.put(
						buildKey(subResource.getId(),
								TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + ""),
						vo);
			}
		}

		// GPS设备不处理权限
		List<TmDevice> tmDevices = tmDeviceDAO.listInOrgan(user.getOrgan()
				.getId());
		// GPS加入到返回集合中
		for (TmDevice tmDevice : tmDevices) {
			if (tmDevice.getType().equals(TypeDefinition.DEVICE_TYPE_GPS + "")) {
				UserResourceVO vo = buildGps(tmDevice, "1,2,4");
				map.put(buildKey(tmDevice.getId(),
						TypeDefinition.DEVICE_TYPE_GPS + ""), vo);
			}
		}

		// 如果存在管理员角色
		if (isAdmin) {
			// 用户机构下的所有设备都是最高权限
			List<Camera> cameras = cameraDAO.listInOrgan(user.getOrgan()
					.getId());
			List<VehicleDetector> vds = vdDAO.listInOrgan(user.getOrgan()
					.getId());
			List<WeatherStat> wsts = wstDAO
					.listInOrgan(user.getOrgan().getId());
			List<WindSpeed> wss = wsDAO.listInOrgan(user.getOrgan().getId());
			List<LoLi> lolis = loliDAO.listInOrgan(user.getOrgan().getId());
			List<FireDetector> fds = fireDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<Covi> covis = coviDAO.listInOrgan(user.getOrgan().getId());
			List<NoDetector> nods = noDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<ControlDevice> cds = controlDeviceDAO.listInOrgan(user
					.getOrgan().getId());
			List<PushButton> pbs = pushButtonDAO.listInOrgan(user.getOrgan()
					.getId());
			List<BoxTransformer> bts = boxTransformerDAO.listInOrgan(user
					.getOrgan().getId());
			List<ViDetector> vis = viDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<RoadDetector> rds = roadDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<BridgeDetector> bds = bridgeDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<UrgentPhone> ups = urgentPhoneDAO.listInOrgan(user.getOrgan()
					.getId());

			// 摄像头加入到返回集合中
			for (Camera camera : cameras) {
				UserResourceVO vo = buildCamera(camera, deviceSolars, auth);
				map.put(buildKey(camera.getId(),
						TypeDefinition.DEVICE_TYPE_CAMERA + ""), vo);
			}
			// 车检器加入到返回集合中
			for (VehicleDetector vd : vds) {
				UserResourceVO vo = buildVd(vd, deviceSolars, auth);
				map.put(buildKey(vd.getId(), TypeDefinition.DEVICE_TYPE_VD + ""),
						vo);
			}
			// 气象检测器加入到返回集合中
			for (WeatherStat wst : wsts) {
				UserResourceVO vo = buildWst(wst, auth);
				map.put(buildKey(wst.getId(), TypeDefinition.DEVICE_TYPE_WST
						+ ""), vo);
			}
			// 风速风向加入到返回集合中
			for (WindSpeed ws : wss) {
				UserResourceVO vo = buildWs(ws, auth);
				map.put(buildKey(ws.getId(), TypeDefinition.DEVICE_TYPE_WS + ""),
						vo);
			}
			// 光强加入到返回集合中
			for (LoLi loli : lolis) {
				UserResourceVO vo = buildLoLi(loli, auth);
				map.put(buildKey(loli.getId(), TypeDefinition.DEVICE_TYPE_LOLI
						+ ""), vo);
			}
			// 火灾加入到返回集合中
			for (FireDetector fd : fds) {
				UserResourceVO vo = buildFd(fd, auth);
				map.put(buildKey(fd.getId(), TypeDefinition.DEVICE_TYPE_FD + ""),
						vo);
			}
			// 一氧化碳/能见度加入到返回集合中
			for (Covi covi : covis) {
				UserResourceVO vo = buildCoVi(covi, auth);
				map.put(buildKey(covi.getId(), TypeDefinition.DEVICE_TYPE_COVI
						+ ""), vo);
			}
			// 氮氧化物加入到返回集合中
			for (NoDetector nod : nods) {
				UserResourceVO vo = buildNod(nod, auth);
				map.put(buildKey(nod.getId(), TypeDefinition.DEVICE_TYPE_NOD
						+ ""), vo);
			}
			// 控制类设备加入到返回集合中
			for (ControlDevice cd : cds) {
				UserResourceVO vo = buildCd(cd, auth);
				map.put(buildKey(cd.getId(), cd.getType()), vo);
			}
			// 手报加入到返回集合中
			for (PushButton pb : pbs) {
				UserResourceVO vo = buildPb(pb, auth);
				map.put(buildKey(pb.getId(), TypeDefinition.DEVICE_TYPE_PB + ""),
						vo);
			}
			// 变电箱加入到返回集合中
			for (BoxTransformer bt : bts) {
				UserResourceVO vo = buildBt(bt, auth);
				map.put(buildKey(bt.getId(), TypeDefinition.DEVICE_TYPE_BT + ""),
						vo);
			}
			// 能见度加入到返回集合中
			for (ViDetector vi : vis) {
				UserResourceVO vo = buildVid(vi, auth);
				map.put(buildKey(vi.getId(),
						TypeDefinition.DEVICE_TYPE_VI_DETECTOR + ""), vo);
			}
			// 路面检测加入到返回集合中
			for (RoadDetector rd : rds) {
				UserResourceVO vo = buildRd(rd, auth);
				map.put(buildKey(rd.getId(),
						TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + ""), vo);
			}
			// 桥面检测加入到返回集合中
			for (BridgeDetector bd : bds) {
				UserResourceVO vo = buildBd(bd, auth);
				map.put(buildKey(bd.getId(),
						TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + ""), vo);
			}
			// 紧急电话加入到返回集合中
			for (UrgentPhone up : ups) {
				UserResourceVO vo = buildUp(up, auth);
				map.put(buildKey(up.getId(),
						TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + ""), vo);
			}
			// 下级平台资源加入到返回集合中
			for (UserResourceVO vo : subMap.values()) {
				vo.setAuth(auth);
			}
			map.putAll(subMap);
		}
		// 如果不存在任何系统角色
		else if (StringUtils.isBlank(auth)) {
			// 查询自定义角色权限设备
			if (roleIds.size() > 0) {
				List<RoleResourcePermission> authDevices = roleResourcePermissionDAO
						.listByRoleIds(roleIds, null);

				for (RoleResourcePermission authDevice : authDevices) {
					// 返回集合中已经添加过该设备,合并权限
					UserResourceVO vo = map.get(buildKey(authDevice));
					if (null != vo) {
						vo.setAuth(combineAuth(vo.getAuth(),
								authDevice.getPrivilege()));
					}
					// 添加新的
					else {
						// 摄像头
						if (authDevice instanceof RoleResourcePermissionCamera) {
							Camera camera = ((RoleResourcePermissionCamera) authDevice)
									.getCamera();

							if (null != camera) {
								vo = buildCamera(camera, deviceSolars,
										authDevice.getPrivilege());
								map.put(buildKey(camera.getId(),
										TypeDefinition.DEVICE_TYPE_CAMERA + ""),
										vo);
							}
						}
						// 车检器
						else if (authDevice instanceof RoleResourcePermissionVd) {
							VehicleDetector vd = ((RoleResourcePermissionVd) authDevice)
									.getVd();
							if (null != vd) {
								vo = buildVd(vd, deviceSolars,
										authDevice.getPrivilege());
								map.put(buildKey(vd.getId(),
										TypeDefinition.DEVICE_TYPE_VD + ""), vo);
							}
						}
						// 气象检测器
						else if (authDevice instanceof RoleResourcePermissionWst) {
							WeatherStat wst = ((RoleResourcePermissionWst) authDevice)
									.getWst();
							if (null != wst) {
								vo = buildWst(wst, authDevice.getPrivilege());
								map.put(buildKey(wst.getId(),
										TypeDefinition.DEVICE_TYPE_WST + ""),
										vo);
							}
						}
						// 风速风向检测器
						else if (authDevice instanceof RoleResourcePermissionWs) {
							WindSpeed ws = ((RoleResourcePermissionWs) authDevice)
									.getWs();
							if (null != ws) {
								vo = buildWs(ws, authDevice.getPrivilege());
								map.put(buildKey(ws.getId(),
										TypeDefinition.DEVICE_TYPE_WS + ""), vo);
							}
						}
						// 光强检测器
						else if (authDevice instanceof RoleResourcePermissionLoli) {
							LoLi loLi = ((RoleResourcePermissionLoli) authDevice)
									.getLoli();
							if (null != loLi) {
								vo = buildLoLi(loLi, authDevice.getPrivilege());
								map.put(buildKey(loLi.getId(),
										TypeDefinition.DEVICE_TYPE_LOLI + ""),
										vo);
							}
						}
						// 火灾检测器
						else if (authDevice instanceof RoleResourcePermissionFd) {
							FireDetector fd = ((RoleResourcePermissionFd) authDevice)
									.getFd();
							if (null != fd) {
								vo = buildFd(fd, authDevice.getPrivilege());
								map.put(buildKey(fd.getId(),
										TypeDefinition.DEVICE_TYPE_FD + ""), vo);
							}
						}
						// 一氧化碳/能见度检测器
						else if (authDevice instanceof RoleResourcePermissionCovi) {
							Covi covi = ((RoleResourcePermissionCovi) authDevice)
									.getCovi();
							if (null != covi) {
								vo = buildCoVi(covi, authDevice.getPrivilege());
								map.put(buildKey(covi.getId(),
										TypeDefinition.DEVICE_TYPE_COVI + ""),
										vo);
							}
						}
						// 氮氧化物检测器
						else if (authDevice instanceof RoleResourcePermissionNod) {
							NoDetector nod = ((RoleResourcePermissionNod) authDevice)
									.getNod();
							if (null != nod) {
								vo = buildNod(nod, authDevice.getPrivilege());
								map.put(buildKey(nod.getId(),
										TypeDefinition.DEVICE_TYPE_NOD + ""),
										vo);
							}
						}
						// 控制类设备，包括可变信息标志、风机、照明灯、卷帘门等
						else if (authDevice instanceof RoleResourcePermissionCms) {
							vo = buildCd(
									((RoleResourcePermissionCms) authDevice)
											.getCms(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionFan) {
							vo = buildCd(
									((RoleResourcePermissionFan) authDevice)
											.getFan(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionLight) {
							vo = buildCd(
									((RoleResourcePermissionLight) authDevice)
											.getLight(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionRd) {
							vo = buildCd(
									((RoleResourcePermissionRd) authDevice)
											.getRd(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionWp) {
							vo = buildCd(
									((RoleResourcePermissionWp) authDevice)
											.getWp(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionRail) {
							vo = buildCd(
									((RoleResourcePermissionRail) authDevice)
											.getRail(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionIs) {
							vo = buildCd(
									((RoleResourcePermissionIs) authDevice)
											.getIs(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						}
						// 手动报警按钮
						else if (authDevice instanceof RoleResourcePermissionPb) {
							PushButton pb = ((RoleResourcePermissionPb) authDevice)
									.getPb();
							if (null != pb) {
								vo = buildPb(pb, authDevice.getPrivilege());
								map.put(buildKey(pb.getId(),
										TypeDefinition.DEVICE_TYPE_PB + ""), vo);
							}
						}
						// 箱变电力监控
						else if (authDevice instanceof RoleResourcePermissionBT) {
							BoxTransformer bt = ((RoleResourcePermissionBT) authDevice)
									.getBoxTransformer();
							if (null != bt) {
								vo = buildBt(bt, authDevice.getPrivilege());
								map.put(buildKey(bt.getId(),
										TypeDefinition.DEVICE_TYPE_BT + ""), vo);
							}
						}
						// 能见度仪
						else if (authDevice instanceof RoleResourcePermissionViD) {
							ViDetector vid = ((RoleResourcePermissionViD) authDevice)
									.getViDetector();
							if (null != vid) {
								vo = buildVid(vid, authDevice.getPrivilege());
								map.put(buildKey(vid.getId(),
										TypeDefinition.DEVICE_TYPE_VI_DETECTOR
												+ ""), vo);
							}
						}
						// 路面检测器
						else if (authDevice instanceof RoleResourcePermissionRoadD) {
							RoadDetector rd = ((RoleResourcePermissionRoadD) authDevice)
									.getRoadDetector();
							if (null != rd) {
								vo = buildRd(rd, authDevice.getPrivilege());
								map.put(buildKey(
										rd.getId(),
										TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR
												+ ""), vo);
							}
						}
						// 桥面检测器
						else if (authDevice instanceof RoleResourcePermissionBridgeD) {
							BridgeDetector bd = ((RoleResourcePermissionBridgeD) authDevice)
									.getBridgeDetector();
							if (null != bd) {
								vo = buildBd(bd, authDevice.getPrivilege());
								map.put(buildKey(
										bd.getId(),
										TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR
												+ ""), vo);
							}
						}
						// 紧急电话
						else if (authDevice instanceof RoleResourcePermissionUP) {
							UrgentPhone up = ((RoleResourcePermissionUP) authDevice)
									.getUrgentPhone();
							if (null != up) {
								vo = buildUp(up, authDevice.getPrivilege());
								map.put(buildKey(
										up.getId(),
										TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE
												+ ""), vo);
							}
						}
						// 下级资源
						else if (authDevice instanceof RoleResourcePermissionSubResource) {
							SubPlatformResource subResource = ((RoleResourcePermissionSubResource) authDevice)
									.getSubResource();
							if (null != subResource) {
								String key = buildKey(subResource.getId(),
										TypeDefinition.DEVICE_TYPE_SUB_RESOURCE
												+ "");
								vo = subMap.get(key);
								if (null != vo) {
									vo.setAuth(authDevice.getPrivilege());
									map.put(key, vo);
								}
							}
						}
					}
				}
			}
		}
		// 存在系统角色,同时存在自定义角色
		else if (StringUtils.isNotBlank(auth) && roleIds.size() > 0) {
			// 查询自定义角色权限设备
			if (roleIds.size() > 0) {
				List<RoleResourcePermission> authDevices = roleResourcePermissionDAO
						.listByRoleIds(roleIds, null);

				for (RoleResourcePermission authDevice : authDevices) {
					// 返回集合中已经添加过该设备,合并权限
					UserResourceVO vo = map.get(buildKey(authDevice));
					if (null != vo) {
						vo.setAuth(combineAuth(vo.getAuth(),
								authDevice.getPrivilege()));
					}
					// 添加新的
					else {
						// 摄像头
						if (authDevice instanceof RoleResourcePermissionCamera) {
							Camera camera = ((RoleResourcePermissionCamera) authDevice)
									.getCamera();

							if (null != camera) {
								vo = buildCamera(camera, deviceSolars,
										authDevice.getPrivilege());
								map.put(buildKey(camera.getId(),
										TypeDefinition.DEVICE_TYPE_CAMERA + ""),
										vo);
							}
						}
						// 车检器
						else if (authDevice instanceof RoleResourcePermissionVd) {
							VehicleDetector vd = ((RoleResourcePermissionVd) authDevice)
									.getVd();
							if (null != vd) {
								vo = buildVd(vd, deviceSolars,
										authDevice.getPrivilege());
								map.put(buildKey(vd.getId(),
										TypeDefinition.DEVICE_TYPE_VD + ""), vo);
							}
						}
						// 气象检测器
						else if (authDevice instanceof RoleResourcePermissionWst) {
							WeatherStat wst = ((RoleResourcePermissionWst) authDevice)
									.getWst();
							if (null != wst) {
								vo = buildWst(wst, authDevice.getPrivilege());
								map.put(buildKey(wst.getId(),
										TypeDefinition.DEVICE_TYPE_WST + ""),
										vo);
							}
						}
						// 风速风向检测器
						else if (authDevice instanceof RoleResourcePermissionWs) {
							WindSpeed ws = ((RoleResourcePermissionWs) authDevice)
									.getWs();
							if (null != ws) {
								vo = buildWs(ws, authDevice.getPrivilege());
								map.put(buildKey(ws.getId(),
										TypeDefinition.DEVICE_TYPE_WS + ""), vo);
							}
						}
						// 光强检测器
						else if (authDevice instanceof RoleResourcePermissionLoli) {
							LoLi loLi = ((RoleResourcePermissionLoli) authDevice)
									.getLoli();
							if (null != loLi) {
								vo = buildLoLi(loLi, authDevice.getPrivilege());
								map.put(buildKey(loLi.getId(),
										TypeDefinition.DEVICE_TYPE_LOLI + ""),
										vo);
							}
						}
						// 火灾检测器
						else if (authDevice instanceof RoleResourcePermissionFd) {
							FireDetector fd = ((RoleResourcePermissionFd) authDevice)
									.getFd();
							if (null != fd) {
								vo = buildFd(fd, authDevice.getPrivilege());
								map.put(buildKey(fd.getId(),
										TypeDefinition.DEVICE_TYPE_FD + ""), vo);
							}
						}
						// 一氧化碳/能见度检测器
						else if (authDevice instanceof RoleResourcePermissionCovi) {
							Covi covi = ((RoleResourcePermissionCovi) authDevice)
									.getCovi();
							if (null != covi) {
								vo = buildCoVi(covi, authDevice.getPrivilege());
								map.put(buildKey(covi.getId(),
										TypeDefinition.DEVICE_TYPE_COVI + ""),
										vo);
							}
						}
						// 氮氧化物检测器
						else if (authDevice instanceof RoleResourcePermissionNod) {
							NoDetector nod = ((RoleResourcePermissionNod) authDevice)
									.getNod();
							if (null != nod) {
								vo = buildNod(nod, authDevice.getPrivilege());
								map.put(buildKey(nod.getId(),
										TypeDefinition.DEVICE_TYPE_NOD + ""),
										vo);
							}
						}
						// 控制类设备，包括可变信息标志、风机、照明灯、卷帘门等
						else if (authDevice instanceof RoleResourcePermissionCms) {
							vo = buildCd(
									((RoleResourcePermissionCms) authDevice)
											.getCms(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionFan) {
							vo = buildCd(
									((RoleResourcePermissionFan) authDevice)
											.getFan(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionLight) {
							vo = buildCd(
									((RoleResourcePermissionLight) authDevice)
											.getLight(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionRd) {
							vo = buildCd(
									((RoleResourcePermissionRd) authDevice)
											.getRd(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionWp) {
							vo = buildCd(
									((RoleResourcePermissionWp) authDevice)
											.getWp(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionRail) {
							vo = buildCd(
									((RoleResourcePermissionRail) authDevice)
											.getRail(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						} else if (authDevice instanceof RoleResourcePermissionIs) {
							vo = buildCd(
									((RoleResourcePermissionIs) authDevice)
											.getIs(),
									authDevice.getPrivilege());
							map.put(buildKey(authDevice), vo);
						}
						// 手动报警按钮
						else if (authDevice instanceof RoleResourcePermissionPb) {
							PushButton pb = ((RoleResourcePermissionPb) authDevice)
									.getPb();
							if (null != pb) {
								vo = buildPb(pb, authDevice.getPrivilege());
								map.put(buildKey(pb.getId(),
										TypeDefinition.DEVICE_TYPE_PB + ""), vo);
							}
						}
						// 箱变电力监控
						else if (authDevice instanceof RoleResourcePermissionBT) {
							BoxTransformer bt = ((RoleResourcePermissionBT) authDevice)
									.getBoxTransformer();
							if (null != bt) {
								vo = buildBt(bt, authDevice.getPrivilege());
								map.put(buildKey(bt.getId(),
										TypeDefinition.DEVICE_TYPE_BT + ""), vo);
							}
						}
						// 能见度仪
						else if (authDevice instanceof RoleResourcePermissionViD) {
							ViDetector vid = ((RoleResourcePermissionViD) authDevice)
									.getViDetector();
							if (null != vid) {
								vo = buildVid(vid, authDevice.getPrivilege());
								map.put(buildKey(vid.getId(),
										TypeDefinition.DEVICE_TYPE_VI_DETECTOR
												+ ""), vo);
							}
						}
						// 路面检测器
						else if (authDevice instanceof RoleResourcePermissionRoadD) {
							RoadDetector rd = ((RoleResourcePermissionRoadD) authDevice)
									.getRoadDetector();
							if (null != rd) {
								vo = buildRd(rd, authDevice.getPrivilege());
								map.put(buildKey(
										rd.getId(),
										TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR
												+ ""), vo);
							}
						}
						// 桥面检测器
						else if (authDevice instanceof RoleResourcePermissionBridgeD) {
							BridgeDetector bd = ((RoleResourcePermissionBridgeD) authDevice)
									.getBridgeDetector();
							if (null != bd) {
								vo = buildBd(bd, authDevice.getPrivilege());
								map.put(buildKey(
										bd.getId(),
										TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR
												+ ""), vo);
							}
						}
						// 紧急电话
						else if (authDevice instanceof RoleResourcePermissionUP) {
							UrgentPhone up = ((RoleResourcePermissionUP) authDevice)
									.getUrgentPhone();
							if (null != up) {
								vo = buildUp(up, authDevice.getPrivilege());
								map.put(buildKey(
										up.getId(),
										TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE
												+ ""), vo);
							}
						}
						// 下级资源
						else if (authDevice instanceof RoleResourcePermissionSubResource) {
							SubPlatformResource subResource = ((RoleResourcePermissionSubResource) authDevice)
									.getSubResource();
							if (null != subResource) {
								String key = buildKey(subResource.getId(),
										TypeDefinition.DEVICE_TYPE_SUB_RESOURCE
												+ "");
								vo = subMap.get(key);
								if (vo != null) {
									vo.setAuth(authDevice.getPrivilege());
									map.put(key, vo);
								}

							}
						}
					}
				}
			}
			// 用户机构下的所有设备至少具有auth权限，与自定角色取并集
			List<Camera> cameras = cameraDAO.listInOrgan(user.getOrgan()
					.getId());
			List<VehicleDetector> vds = vdDAO.listInOrgan(user.getOrgan()
					.getId());
			List<WeatherStat> wsts = wstDAO
					.listInOrgan(user.getOrgan().getId());
			List<WindSpeed> wss = wsDAO.listInOrgan(user.getOrgan().getId());
			List<LoLi> lolis = loliDAO.listInOrgan(user.getOrgan().getId());
			List<FireDetector> fds = fireDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<Covi> covis = coviDAO.listInOrgan(user.getOrgan().getId());
			List<NoDetector> nods = noDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<ControlDevice> cds = controlDeviceDAO.listInOrgan(user
					.getOrgan().getId());
			List<PushButton> pbs = pushButtonDAO.listInOrgan(user.getOrgan()
					.getId());
			List<BoxTransformer> bts = boxTransformerDAO.listInOrgan(user
					.getOrgan().getId());
			List<ViDetector> vis = viDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<RoadDetector> rds = roadDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<BridgeDetector> bds = bridgeDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<UrgentPhone> ups = urgentPhoneDAO.listInOrgan(user.getOrgan()
					.getId());
			// 摄像头
			for (Camera camera : cameras) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(camera.getId(),
						TypeDefinition.DEVICE_TYPE_CAMERA + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildCamera(camera, deviceSolars, auth);
					map.put(buildKey(camera.getId(),
							TypeDefinition.DEVICE_TYPE_CAMERA + ""), vo);
				}
			}
			// 车辆检测器
			for (VehicleDetector vd : vds) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(vd.getId(),
						TypeDefinition.DEVICE_TYPE_VD + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildVd(vd, deviceSolars, auth);
					map.put(buildKey(vd.getId(), TypeDefinition.DEVICE_TYPE_VD
							+ ""), vo);
				}
			}
			// 气象检测器
			for (WeatherStat wst : wsts) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(wst.getId(),
						TypeDefinition.DEVICE_TYPE_WST + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildWst(wst, auth);
					map.put(buildKey(wst.getId(),
							TypeDefinition.DEVICE_TYPE_WST + ""), vo);
				}
			}
			// 风速风向检测器
			for (WindSpeed ws : wss) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(ws.getId(),
						TypeDefinition.DEVICE_TYPE_WS + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildWs(ws, auth);
					map.put(buildKey(ws.getId(), TypeDefinition.DEVICE_TYPE_WS
							+ ""), vo);
				}
			}
			// 光强检测器
			for (LoLi loli : lolis) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(loli.getId(),
						TypeDefinition.DEVICE_TYPE_LOLI + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildLoLi(loli, auth);
					map.put(buildKey(loli.getId(),
							TypeDefinition.DEVICE_TYPE_LOLI + ""), vo);
				}
			}
			// 火灾检测器
			for (FireDetector fd : fds) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(fd.getId(),
						TypeDefinition.DEVICE_TYPE_FD + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildFd(fd, auth);
					map.put(buildKey(fd.getId(), TypeDefinition.DEVICE_TYPE_FD
							+ ""), vo);
				}
			}
			// 一氧化碳/能见度检测器
			for (Covi covi : covis) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(covi.getId(),
						TypeDefinition.DEVICE_TYPE_COVI + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildCoVi(covi, auth);
					map.put(buildKey(covi.getId(),
							TypeDefinition.DEVICE_TYPE_COVI + ""), vo);
				}
			}
			// 氮氧化物检测器
			for (NoDetector nod : nods) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(nod.getId(),
						TypeDefinition.DEVICE_TYPE_NOD + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildNod(nod, auth);
					map.put(buildKey(nod.getId(),
							TypeDefinition.DEVICE_TYPE_NOD + ""), vo);
				}
			}
			// 控制类设备，包括可变信息标志、风机、照明灯、卷帘门等
			for (ControlDevice cd : cds) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(cd.getId(), cd.getType()
						.toString()));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildCd(cd, auth);
					map.put(buildKey(cd.getId(), cd.getType().toString()), vo);
				}
			}
			// 手动报警按钮
			for (PushButton pb : pbs) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(pb.getId(),
						TypeDefinition.DEVICE_TYPE_PB + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildPb(pb, auth);
					map.put(buildKey(pb.getId(), TypeDefinition.DEVICE_TYPE_PB
							+ ""), vo);
				}
			}
			// 箱变电力监控
			for (BoxTransformer bt : bts) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(bt.getId(),
						TypeDefinition.DEVICE_TYPE_BT + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildBt(bt, auth);
					map.put(buildKey(bt.getId(), TypeDefinition.DEVICE_TYPE_BT
							+ ""), vo);
				}
			}
			// 能见度仪
			for (ViDetector vid : vis) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(vid.getId(),
						TypeDefinition.DEVICE_TYPE_VI_DETECTOR + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildVid(vid, auth);
					map.put(buildKey(vid.getId(),
							TypeDefinition.DEVICE_TYPE_VI_DETECTOR + ""), vo);
				}
			}
			// 路面检测器
			for (RoadDetector rd : rds) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(rd.getId(),
						TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildRd(rd, auth);
					map.put(buildKey(rd.getId(),
							TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + ""), vo);
				}
			}
			// 桥面检测器
			for (BridgeDetector bd : bds) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(bd.getId(),
						TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildBd(bd, auth);
					map.put(buildKey(bd.getId(),
							TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + ""),
							vo);
				}
			}
			// 紧急电话
			for (UrgentPhone up : ups) {
				// 返回集合中已经添加过该设备,合并权限
				UserResourceVO vo = map.get(buildKey(up.getId(),
						TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + ""));
				if (null != vo) {
					vo.setAuth(combineAuth(vo.getAuth(), auth));
				}
				// 添加新的
				else {
					vo = buildUp(up, auth);
					map.put(buildKey(up.getId(),
							TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + ""),
							vo);
				}
			}
			// 下级平台资源加入到返回集合中
			for (UserResourceVO vo : subMap.values()) {
				UserResourceVO authDevice = map.get(buildKey(vo.getId(),
						TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + ""));
				// 合并权限
				if (null != authDevice) {
					authDevice.setAuth(combineAuth(authDevice.getAuth(), auth));
				}
				// 添加新的
				else {
					vo.setAuth(auth);
					map.put(buildKey(vo.getId(),
							TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + ""), vo);
				}
			}
			map.putAll(subMap);
		}
		// 只存在系统角色
		else {
			// 用户机构下的所有设备都设置为auth权限
			List<Camera> cameras = cameraDAO.listInOrgan(user.getOrgan()
					.getId());
			List<VehicleDetector> vds = vdDAO.listInOrgan(user.getOrgan()
					.getId());
			List<WeatherStat> wsts = wstDAO
					.listInOrgan(user.getOrgan().getId());
			List<WindSpeed> wss = wsDAO.listInOrgan(user.getOrgan().getId());
			List<LoLi> lolis = loliDAO.listInOrgan(user.getOrgan().getId());
			List<FireDetector> fds = fireDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<Covi> covis = coviDAO.listInOrgan(user.getOrgan().getId());
			List<NoDetector> nods = noDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<ControlDevice> cds = controlDeviceDAO.listInOrgan(user
					.getOrgan().getId());
			List<PushButton> pbs = pushButtonDAO.listInOrgan(user.getOrgan()
					.getId());
			List<BoxTransformer> bts = boxTransformerDAO.listInOrgan(user
					.getOrgan().getId());
			List<ViDetector> vis = viDetectorDAO.listInOrgan(user.getOrgan()
					.getId());
			List<RoadDetector> rds = roadDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<BridgeDetector> bds = bridgeDetectorDAO.listInOrgan(user
					.getOrgan().getId());
			List<UrgentPhone> ups = urgentPhoneDAO.listInOrgan(user.getOrgan()
					.getId());

			// 摄像头加入到返回集合中
			for (Camera camera : cameras) {
				UserResourceVO vo = buildCamera(camera, deviceSolars, auth);
				map.put(buildKey(camera.getId(),
						TypeDefinition.DEVICE_TYPE_CAMERA + ""), vo);
			}
			// 车检器加入到返回集合中
			for (VehicleDetector vd : vds) {
				UserResourceVO vo = buildVd(vd, deviceSolars, auth);
				map.put(buildKey(vd.getId(), TypeDefinition.DEVICE_TYPE_VD + ""),
						vo);
			}
			// 气象检测器加入到返回集合中
			for (WeatherStat wst : wsts) {
				UserResourceVO vo = buildWst(wst, auth);
				map.put(buildKey(wst.getId(), TypeDefinition.DEVICE_TYPE_WST
						+ ""), vo);
			}
			// 风速风向加入到返回集合中
			for (WindSpeed ws : wss) {
				UserResourceVO vo = buildWs(ws, auth);
				map.put(buildKey(ws.getId(), TypeDefinition.DEVICE_TYPE_WS + ""),
						vo);
			}
			// 光强加入到返回集合中
			for (LoLi loli : lolis) {
				UserResourceVO vo = buildLoLi(loli, auth);
				map.put(buildKey(loli.getId(), TypeDefinition.DEVICE_TYPE_LOLI
						+ ""), vo);
			}
			// 火灾加入到返回集合中
			for (FireDetector fd : fds) {
				UserResourceVO vo = buildFd(fd, auth);
				map.put(buildKey(fd.getId(), TypeDefinition.DEVICE_TYPE_FD + ""),
						vo);
			}
			// 一氧化碳/能见度加入到返回集合中
			for (Covi covi : covis) {
				UserResourceVO vo = buildCoVi(covi, auth);
				map.put(buildKey(covi.getId(), TypeDefinition.DEVICE_TYPE_COVI
						+ ""), vo);
			}
			// 氮氧化物加入到返回集合中
			for (NoDetector nod : nods) {
				UserResourceVO vo = buildNod(nod, auth);
				map.put(buildKey(nod.getId(), TypeDefinition.DEVICE_TYPE_NOD
						+ ""), vo);
			}
			// 控制类设备加入到返回集合中
			for (ControlDevice cd : cds) {
				UserResourceVO vo = buildCd(cd, auth);
				map.put(buildKey(cd.getId(), cd.getType()), vo);
			}
			// 手报加入到返回集合中
			for (PushButton pb : pbs) {
				UserResourceVO vo = buildPb(pb, auth);
				map.put(buildKey(pb.getId(), TypeDefinition.DEVICE_TYPE_PB + ""),
						vo);
			}
			// 变电箱加入到返回集合中
			for (BoxTransformer bt : bts) {
				UserResourceVO vo = buildBt(bt, auth);
				map.put(buildKey(bt.getId(), TypeDefinition.DEVICE_TYPE_BT + ""),
						vo);
			}
			// 能见度加入到返回集合中
			for (ViDetector vi : vis) {
				UserResourceVO vo = buildVid(vi, auth);
				map.put(buildKey(vi.getId(),
						TypeDefinition.DEVICE_TYPE_VI_DETECTOR + ""), vo);
			}
			// 路面检测加入到返回集合中
			for (RoadDetector rd : rds) {
				UserResourceVO vo = buildRd(rd, auth);
				map.put(buildKey(rd.getId(),
						TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + ""), vo);
			}
			// 桥面检测加入到返回集合中
			for (BridgeDetector bd : bds) {
				UserResourceVO vo = buildBd(bd, auth);
				map.put(buildKey(bd.getId(),
						TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + ""), vo);
			}
			// 紧急电话加入到返回集合中
			for (UrgentPhone up : ups) {
				UserResourceVO vo = buildUp(up, auth);
				map.put(buildKey(up.getId(),
						TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + ""), vo);
			}
			// 下级平台资源加入到返回集合中
			for (UserResourceVO vo : subMap.values()) {
				vo.setAuth(auth);
			}
			map.putAll(subMap);
		}

		return map.values();
	}

	private UserResourceVO buildOrgan(Organ organ) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(organ.getId());
		vo.setName(organ.getName());
		vo.setStandardNumber(organ.getStandardNumber());
		vo.setParent(organ.getParent() != null ? organ.getParent()
				.getStandardNumber() : "");
		vo.setType(organ.getType());
		vo.setImageId(organ.getImageId());
		vo.setLongitude(organ.getLongitude());
		vo.setLatitude(organ.getLatitude());
		vo.setStakeNumber(organ.getStakeNumber());
		if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
			OrganTunnel organTunnel = (OrganTunnel) organ;
			vo.setHeight(organTunnel.getHeight());
			vo.setLength(organTunnel.getLength());
			vo.setLaneNumber(MyStringUtil.object2String(organTunnel
					.getLaneNumber()));
			vo.setLimitSpeed(MyStringUtil.object2String(organTunnel
					.getLimitSpeed()));
			vo.setCapacity(MyStringUtil.object2String(organTunnel.getCapacity()));
			vo.setLeftDirection(organTunnel.getLeftDirection());
			vo.setRightDirection(organTunnel.getRightDirection());
		} else if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			OrganRoad organRoad = (OrganRoad) organ;
			vo.setLimitSpeed(MyStringUtil.object2String(organRoad
					.getLimitSpeed()));
			vo.setCapacity(MyStringUtil.object2String(organRoad.getCapacity()));
			vo.setLaneNumber(MyStringUtil.object2String(organRoad
					.getLaneNumber()));
			vo.setLaneWidth(MyStringUtil.object2String(organRoad.getLaneWidth()));
			vo.setLeftEdge(MyStringUtil.object2String(organRoad.getLeftEdge()));
			vo.setRightEdge(MyStringUtil.object2String(organRoad.getRightEdge()));
			vo.setCentralReserveWidth(MyStringUtil.object2String(organRoad
					.getCentralReserveWidth()));
			vo.setBeginStakeNumber(organRoad.getBeginStakeNumber());
			vo.setEndStakeNumber(organRoad.getEndStakeNumber());
			vo.setRoadNumber(organRoad.getRoadNumber());
		} else if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			Tollgate tollgate = (Tollgate) organ;
			vo.setEntranceNumber(MyStringUtil.object2String(tollgate
					.getEntranceNumber()));
			vo.setExitNumber(MyStringUtil.object2String(tollgate
					.getExitNumber()));
			vo.setNavigation(tollgate.getNavigation());
		} else if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			OrganBridge organBridge = (OrganBridge) organ;
			vo.setLimitSpeed(MyStringUtil.object2String(organBridge
					.getLimitSpeed()));
			vo.setCapacity(MyStringUtil.object2String(organBridge.getCapacity()));
			vo.setWidth(organBridge.getWidth());
			vo.setLength(organBridge.getLength());
		}
		return vo;
	}

	private UserResourceVO buildSubResource(SubPlatformResource resource,
			String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(resource.getId());
		vo.setName(resource.getName());
		vo.setStandardNumber(resource.getStandardNumber());
		vo.setParent(resource.getParent() != null ? resource.getParent()
				.getStandardNumber() : "");
		vo.setType(resource.getType());
		vo.setLongitude(resource.getLongitude());
		vo.setLatitude(resource.getLatitude());
		vo.setStakeNumber(resource.getStakeNumber());
		vo.setNavigation(resource.getNavigation());
		vo.setWidth(MyStringUtil.object2String(resource.getWidth()));
		vo.setHeight(MyStringUtil.object2String(resource.getHeight()));
		vo.setStoreType(TypeDefinition.STORE_TYPE_BOTH + "");
		vo.setAuth(auth);
		// 设备属性
		if (!TypeDefinition.ORGAN_TYPE_MANAGEMENT.equals(resource.getType())) {
			vo.setManufacture(resource.getManufacturer());
			vo.setLocation(resource.getAddress());
			vo.setSubType(translatePtzType(resource.getPtzType()));
			vo.setStatus(resource.getStatus());
			// CCS推送数据
			if (StringUtils.isNotBlank(resource.getGatewayId())) {
				Ccs ccs = ccsDAO.findById(resource.getGatewayId());
				vo.setCcsSN(ccs.getStandardNumber());
			}
			// 内部互联中心推送数据
			else {
				vo.setCcsSN(Configuration.getInstance().getProperties(
						"gateway_ccs_sn"));
			}
		}
		return vo;
	}

	private UserResourceVO buildCamera(Camera camera,
			List<DeviceSolarVO> deviceSolars, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(camera.getId());
		vo.setParent(camera.getOrgan().getStandardNumber());
		vo.setStandardNumber(camera.getStandardNumber());
		vo.setName(camera.getName());
		vo.setSubType(camera.getSubType());
		vo.setStatus(MyStringUtil.object2String(camera.getStatus()));
		vo.setAuth(auth);
		vo.setImageId(camera.getProperty().getImageId());
		vo.setChannelNumber(MyStringUtil.object2String(camera
				.getChannelNumber()));
		vo.setLocation(camera.getLocation());
		vo.setLongitude(camera.getLongitude());
		vo.setLatitude(camera.getLatitude());
		vo.setStakeNumber(camera.getStakeNumber());
		vo.setType(MyStringUtil.object2String(camera.getType()));
		vo.setNavigation(camera.getNavigation());
		vo.setManufacture(camera.getManufacturer() != null ? camera
				.getManufacturer().getName() : "");
		vo.setCcsSN(camera.getParent().getCcs() != null ? camera.getParent()
				.getCcs().getStandardNumber() : "");
		vo.setStoreType(camera.getProperty().getStoreType() + "");
		// 设置太阳能电池相关属性
		setCameraSolar(deviceSolars, camera, vo);
		return vo;
	}

	private UserResourceVO buildVd(VehicleDetector vd,
			List<DeviceSolarVO> deviceSolars, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(vd.getId());
		vo.setParent(vd.getOrgan().getStandardNumber());
		vo.setName(vd.getName());
		vo.setStandardNumber(vd.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(vd.getLatitude());
		vo.setLongitude(vd.getLongitude());
		vo.setStakeNumber(vd.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_VD + "");
		vo.setDasSN(vd.getDas().getStandardNumber());
		vo.setNavigation(vd.getNavigation());
		vo.setManufacture(vd.getReserve());
		vo.setSpeedLowLimit(vd.getsLowLimit() != null ? vd.getsLowLimit() + ""
				: "");
		vo.setSpeedUpLimit(vd.getsUpLimit() != null ? vd.getsUpLimit() + ""
				: "");
		vo.setOccLowLimit(vd.getoLowLimit() != null ? vd.getoLowLimit() + ""
				: "");
		vo.setOccUpLimit(vd.getoUpLimit() != null ? vd.getoUpLimit() + "" : "");
		vo.setVolumeLowLimit(vd.getvLowLimit() != null ? vd.getvLowLimit() + ""
				: "");
		vo.setVolumeUpLimit(vd.getvUpLimit() != null ? vd.getvUpLimit() + ""
				: "");
		// 设置太阳能电池相关属性
		setVehicleSolar(deviceSolars, vd, vo);
		return vo;
	}

	private UserResourceVO buildWst(WeatherStat wst, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(wst.getId());
		vo.setParent(wst.getOrgan().getStandardNumber());
		vo.setName(wst.getName());
		vo.setStandardNumber(wst.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(wst.getLatitude());
		vo.setLongitude(wst.getLongitude());
		vo.setStakeNumber(wst.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_WST + "");
		vo.setDasSN(wst.getDas().getStandardNumber());
		vo.setNavigation(wst.getNavigation());
		vo.setManufacture(wst.getReserve());
		vo.setViLowLimit(wst.getvLowLimit() != null ? wst.getvLowLimit() + ""
				: "");
		vo.setWindUpLimit(wst.getwUpLimit() != null ? wst.getwUpLimit() + ""
				: "");
		vo.setRainUpLimit(wst.getrUpLimit() != null ? wst.getrUpLimit() + ""
				: "");
		vo.setSnowUpLimit(wst.getsUpLimit() != null ? wst.getsUpLimit() + ""
				: "");
		return vo;
	}

	private UserResourceVO buildWs(WindSpeed ws, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(ws.getId());
		vo.setParent(ws.getOrgan().getStandardNumber());
		vo.setName(ws.getName());
		vo.setStandardNumber(ws.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(ws.getLatitude());
		vo.setLongitude(ws.getLongitude());
		vo.setStakeNumber(ws.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_WS + "");
		vo.setDasSN(ws.getDas().getStandardNumber());
		vo.setNavigation(ws.getNavigation());
		vo.setManufacture(ws.getReserve());
		vo.setWindUpLimit(ws.getwUpLimit() != null ? ws.getwUpLimit() + "" : "");
		return vo;
	}

	private UserResourceVO buildLoLi(LoLi loLi, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(loLi.getId());
		vo.setParent(loLi.getOrgan().getStandardNumber());
		vo.setName(loLi.getName());
		vo.setStandardNumber(loLi.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(loLi.getLatitude());
		vo.setLongitude(loLi.getLongitude());
		vo.setStakeNumber(loLi.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_LOLI + "");
		vo.setDasSN(loLi.getDas().getStandardNumber());
		vo.setNavigation(loLi.getNavigation());
		vo.setManufacture(loLi.getReserve());
		vo.setLiLumiMax(loLi.getLiLumiMax() != null ? loLi.getLiLumiMax() + ""
				: "");
		vo.setLiLumiMin(loLi.getLiLumiMin() != null ? loLi.getLiLumiMin() + ""
				: "");
		vo.setLoLumiMax(loLi.getLoLumiMax() != null ? loLi.getLoLumiMax() + ""
				: "");
		vo.setLoLumiMin(loLi.getLoLumiMin() != null ? loLi.getLoLumiMin() + ""
				: "");
		return vo;
	}

	private UserResourceVO buildFd(FireDetector fd, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(fd.getId());
		vo.setParent(fd.getOrgan().getStandardNumber());
		vo.setName(fd.getName());
		vo.setStandardNumber(fd.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(fd.getLatitude());
		vo.setLongitude(fd.getLongitude());
		vo.setStakeNumber(fd.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_FD + "");
		vo.setDasSN(fd.getDas().getStandardNumber());
		vo.setNavigation(fd.getNavigation());
		vo.setManufacture(fd.getReserve());
		return vo;
	}

	private UserResourceVO buildCoVi(Covi covi, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(covi.getId());
		vo.setParent(covi.getOrgan().getStandardNumber());
		vo.setName(covi.getName());
		vo.setStandardNumber(covi.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(covi.getLatitude());
		vo.setLongitude(covi.getLongitude());
		vo.setStakeNumber(covi.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_COVI + "");
		vo.setDasSN(covi.getDas().getStandardNumber());
		vo.setNavigation(covi.getNavigation());
		vo.setManufacture(covi.getReserve());
		vo.setCoConctLimit(covi.getCoConctLimit() != null ? covi
				.getCoConctLimit() + "" : "");
		vo.setViLowLimit(covi.getVisibilityLimit() != null ? covi
				.getVisibilityLimit() + "" : "");
		return vo;
	}

	private UserResourceVO buildNod(NoDetector nod, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(nod.getId());
		vo.setParent(nod.getOrgan().getStandardNumber());
		vo.setName(nod.getName());
		vo.setStandardNumber(nod.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(nod.getLatitude());
		vo.setLongitude(nod.getLongitude());
		vo.setStakeNumber(nod.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_NOD + "");
		vo.setDasSN(nod.getDas().getStandardNumber());
		vo.setNavigation(nod.getNavigation());
		vo.setManufacture(nod.getReserve());
		vo.setNoConctLimit(nod.getNoConctLimit() != null ? nod
				.getNoConctLimit() + "" : "");
		vo.setNooConctLimit(nod.getNooConctLimit() != null ? nod
				.getNooConctLimit() + "" : "");
		return vo;
	}

	private UserResourceVO buildCd(ControlDevice cd, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(cd.getId());
		vo.setParent(cd.getOrgan().getStandardNumber());
		vo.setName(cd.getName());
		vo.setStandardNumber(cd.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(cd.getLatitude());
		vo.setLongitude(cd.getLongitude());
		vo.setStakeNumber(cd.getStakeNumber());
		vo.setType(cd.getType());
		// 可变信息标志具有子类型
		if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
			vo.setSubType(cd.getSubType().toString());
		} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_RD + "")) {
			vo.setSubType(cd.getSubType().toString());
		}
		vo.setWidth(cd.getWidth() != null ? cd.getWidth().toString() : "");
		vo.setHeight(cd.getHeight() != null ? cd.getHeight().toString() : "");
		vo.setDasSN(cd.getDas().getStandardNumber());
		vo.setNavigation(cd.getNavigation());
		vo.setManufacture(cd.getReserve());
		return vo;
	}

	private UserResourceVO buildPb(PushButton pb, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(pb.getId());
		vo.setParent(pb.getOrgan().getStandardNumber());
		vo.setName(pb.getName());
		vo.setStandardNumber(pb.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(pb.getLatitude());
		vo.setLongitude(pb.getLongitude());
		vo.setStakeNumber(pb.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_PB + "");
		vo.setDasSN(pb.getDas().getStandardNumber());
		vo.setNavigation(pb.getNavigation());
		vo.setManufacture(pb.getReserve());
		return vo;
	}

	private UserResourceVO buildBt(BoxTransformer bt, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(bt.getId());
		vo.setParent(bt.getOrgan().getStandardNumber());
		vo.setName(bt.getName());
		vo.setStandardNumber(bt.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(bt.getLatitude());
		vo.setLongitude(bt.getLongitude());
		vo.setStakeNumber(bt.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_BT + "");
		vo.setDasSN(bt.getDas().getStandardNumber());
		vo.setNavigation(bt.getNavigation());
		vo.setManufacture(bt.getReserve());
		return vo;
	}

	private UserResourceVO buildVid(ViDetector vid, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(vid.getId());
		vo.setParent(vid.getOrgan().getStandardNumber());
		vo.setName(vid.getName());
		vo.setStandardNumber(vid.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(vid.getLatitude());
		vo.setLongitude(vid.getLongitude());
		vo.setStakeNumber(vid.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "");
		vo.setDasSN(vid.getDas().getStandardNumber());
		vo.setNavigation(vid.getNavigation());
		vo.setManufacture(vid.getReserve());
		return vo;
	}

	private UserResourceVO buildRd(RoadDetector rd, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(rd.getId());
		vo.setParent(rd.getOrgan().getStandardNumber());
		vo.setName(rd.getName());
		vo.setStandardNumber(rd.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(rd.getLatitude());
		vo.setLongitude(rd.getLongitude());
		vo.setStakeNumber(rd.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "");
		vo.setDasSN(rd.getDas().getStandardNumber());
		vo.setNavigation(rd.getNavigation());
		vo.setManufacture(rd.getReserve());
		return vo;
	}

	private UserResourceVO buildBd(BridgeDetector bd, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(bd.getId());
		vo.setParent(bd.getOrgan().getStandardNumber());
		vo.setName(bd.getName());
		vo.setStandardNumber(bd.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(bd.getLatitude());
		vo.setLongitude(bd.getLongitude());
		vo.setStakeNumber(bd.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "");
		vo.setDasSN(bd.getDas().getStandardNumber());
		vo.setNavigation(bd.getNavigation());
		vo.setManufacture(bd.getReserve());
		return vo;
	}

	private UserResourceVO buildUp(UrgentPhone up, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(up.getId());
		vo.setParent(up.getOrgan().getStandardNumber());
		vo.setName(up.getName());
		vo.setStandardNumber(up.getStandardNumber());
		vo.setAuth(auth);
		vo.setLatitude(up.getLatitude());
		vo.setLongitude(up.getLongitude());
		vo.setStakeNumber(up.getStakeNumber());
		vo.setType(TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "");
		vo.setDasSN(up.getDas().getStandardNumber());
		vo.setNavigation(up.getNavigation());
		vo.setManufacture(up.getReserve());
		return vo;
	}

	private UserResourceVO buildGps(TmDevice tmDevice, String auth) {
		UserResourceVO vo = new UserResourceVO();
		vo.setId(tmDevice.getId());
		vo.setParent(tmDevice.getOrgan().getStandardNumber());
		vo.setName(tmDevice.getName());
		vo.setStandardNumber(tmDevice.getStandardNumber());
		vo.setAuth(auth);
		vo.setType(TypeDefinition.DEVICE_TYPE_GPS + "");
		vo.setDasSN(tmDevice.getDas().getStandardNumber());
		vo.setManufacture(tmDevice.getManufacturer() != null ? tmDevice
				.getManufacturer().getName() : "");
		return vo;
	}

	/**
	 * 设备主键hashcode值
	 * 
	 * @param id
	 *            设备主键
	 * @param type
	 *            设备类型
	 * @return hashcode值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-20 上午11:25:20
	 */
	private String buildKey(Object id, String type) {
		return (id.toString() + "-" + type).hashCode() + "";
	}

	/**
	 * 生成设备主键hashcode值
	 * 
	 * @param authDevice
	 *            权限设备
	 * @return 主键hashcode值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-6 上午9:28:12
	 */
	private String buildKey(RoleResourcePermission authDevice) {
		if (authDevice instanceof RoleResourcePermissionCamera) {
			return (((RoleResourcePermissionCamera) authDevice).getCamera()
					.getId() + "-" + TypeDefinition.DEVICE_TYPE_CAMERA)
					.hashCode() + "";
		} else if (authDevice instanceof RoleResourcePermissionWp) {
			return (((RoleResourcePermissionWp) authDevice).getWp().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_WP).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionWst) {
			return (((RoleResourcePermissionWst) authDevice).getWst().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_WST).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionRd) {
			return (((RoleResourcePermissionRd) authDevice).getRd().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_RD).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionVd) {
			return (((RoleResourcePermissionVd) authDevice).getVd().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_VD).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionRail) {
			return (((RoleResourcePermissionRail) authDevice).getRail().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_RAIL).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionNod) {
			return (((RoleResourcePermissionNod) authDevice).getNod().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_NOD).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionPb) {
			return (((RoleResourcePermissionPb) authDevice).getPb().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_PB).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionLight) {
			return (((RoleResourcePermissionLight) authDevice).getLight()
					.getId() + "-" + TypeDefinition.DEVICE_TYPE_LIGHT)
					.hashCode() + "";
		} else if (authDevice instanceof RoleResourcePermissionLoli) {
			return (((RoleResourcePermissionLoli) authDevice).getLoli().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_LOLI).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionCms) {
			return (((RoleResourcePermissionCms) authDevice).getCms().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_CMS).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionCovi) {
			return (((RoleResourcePermissionCovi) authDevice).getCovi().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_COVI).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionFan) {
			return (((RoleResourcePermissionFan) authDevice).getFan().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_FAN).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionFd) {
			return (((RoleResourcePermissionFd) authDevice).getFd().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_FD).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionIs) {
			return (((RoleResourcePermissionIs) authDevice).getIs().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_IS).hashCode()
					+ "";
		} else if (authDevice instanceof RoleResourcePermissionWs) {
			return (((RoleResourcePermissionWs) authDevice).getWs().getId()
					+ "-" + TypeDefinition.DEVICE_TYPE_WS).hashCode()
					+ "";
		} else {
			return "";
		}
	}

	@Override
	public String createNoDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short noConctLimit,
			Short nooConctLimit, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<NoDetector> list = noDetectorDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = noDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		NoDetector noDetector = new NoDetector();
		noDetector.setCreateTime(System.currentTimeMillis());
		noDetector.setDas(dasDAO.findById(dasId));
		noDetector.setLatitude(latitude);
		noDetector.setLongitude(longitude);
		noDetector.setName(name);
		noDetector.setNote(note);
		noDetector.setOrgan(organDAO.findById(organId));
		noDetector.setPeriod(period);
		noDetector.setStakeNumber(stakeNumber);
		noDetector.setStandardNumber(standardNumber);
		noDetector.setNoConctLimit(noConctLimit);
		noDetector.setNooConctLimit(nooConctLimit);
		noDetector.setNavigation(navigation);
		noDetector.setReserve(reserve);
		noDetector.setIp(ip);
		noDetector.setPort(port);
		noDetectorDAO.save(noDetector);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_NOD);
		return noDetector.getId();
	}

	@Override
	public void updateNoDetector(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short noConctLimit,
			Short nooConctLimit, String note, String navigation,
			String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<NoDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = noDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = noDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		NoDetector noDetector = noDetectorDAO.findById(id);
		if (null != name) {
			noDetector.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(noDetector.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_NOD);
			noDetector.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			noDetector.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			noDetector.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			noDetector.setPeriod(period);
		}
		if (null != stakeNumber) {
			noDetector.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			noDetector.setLongitude(longitude);
		}
		if (null != latitude) {
			noDetector.setLatitude(latitude);
		}
		noDetector.setNoConctLimit(noConctLimit);
		noDetector.setNooConctLimit(nooConctLimit);
		if (null != note) {
			noDetector.setNote(note);
		}
		if (null != navigation) {
			noDetector.setNavigation(navigation);
		}
		if (null != reserve) {
			noDetector.setReserve(reserve);
		}
		if (null != ip) {
			noDetector.setIp(ip);
		}
		noDetector.setPort(port);
		noDetectorDAO.update(noDetector);

	}

	@Override
	public void deleteNoDetector(String id) {
		// 删除氮氧化合物和角色关系
		noDetectorDAO.deleteRoleNODPermission(id);
		// 同步SN
		NoDetector nod = noDetectorDAO.findById(id);
		syncSN(nod.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_NOD);
		noDetectorDAO.delete(nod);
	}

	@Override
	public GetNoDetectorVO getNoDetector(String id) {
		NoDetector noDetector = noDetectorDAO.findById(id);
		GetNoDetectorVO vo = new GetNoDetectorVO();
		vo.setNoConctLimit(noDetector.getNoConctLimit() + "");
		vo.setCreateTime(noDetector.getCreateTime() + "");
		vo.setDasId(noDetector.getDas() != null ? noDetector.getDas().getId()
				: "");
		vo.setDasName(noDetector.getDas() != null ? noDetector.getDas()
				.getName() : "");
		vo.setId(noDetector.getId() + "");
		vo.setLatitude(noDetector.getLatitude());
		vo.setLongitude(noDetector.getLongitude());
		vo.setName(noDetector.getName());
		vo.setNote(noDetector.getNote());
		vo.setOrganId(noDetector.getOrgan() != null ? noDetector.getOrgan()
				.getId() : "");
		vo.setPeriod(noDetector.getPeriod() + "");
		vo.setReserve(noDetector.getReserve());
		vo.setStakeNumber(noDetector.getStakeNumber());
		vo.setStandardNumber(noDetector.getStandardNumber());
		vo.setNooConctLimit(noDetector.getNooConctLimit() + "");
		vo.setNavigation(noDetector.getNavigation());
		vo.setIp(noDetector.getIp());
		vo.setPort(noDetector.getPort() + "");
		return vo;
	}

	@Override
	public Integer countNoDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return noDetectorDAO.countNoDetector(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetNoDetectorVO> listNoDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<NoDetector> list = noDetectorDAO.listNoDetector(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetNoDetectorVO> listVO = new ArrayList<GetNoDetectorVO>();
		for (NoDetector noDetector : list) {
			GetNoDetectorVO vo = new GetNoDetectorVO();
			vo.setCreateTime(noDetector.getCreateTime() + "");
			vo.setDasId(noDetector.getDas() != null ? noDetector.getDas()
					.getId() : "");
			vo.setDasName(noDetector.getDas() != null ? noDetector.getDas()
					.getName() : "");
			vo.setId(noDetector.getId() + "");
			vo.setLatitude(noDetector.getLatitude());
			vo.setLongitude(noDetector.getLongitude());
			vo.setName(noDetector.getName());
			vo.setNote(noDetector.getNote());
			vo.setOrganId(noDetector.getOrgan() != null ? noDetector.getOrgan()
					.getId() : "");
			vo.setPeriod(noDetector.getPeriod() + "");
			vo.setReserve(noDetector.getReserve());
			vo.setStakeNumber(noDetector.getStakeNumber());
			vo.setStandardNumber(noDetector.getStandardNumber());
			vo.setNoConctLimit(noDetector.getNoConctLimit() + "");
			vo.setNooConctLimit(noDetector.getNooConctLimit() + "");
			vo.setNavigation(noDetector.getNavigation());
			vo.setIp(noDetector.getIp());
			vo.setPort(noDetector.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createLoli(String name, String standardNumber, String dasId,
			String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short loLumiMax,
			Short loLumiMin, Short liLumiMax, Short liLumiMin, String note,
			String navigation, String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<LoLi> list = loliDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = loliDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		LoLi loli = new LoLi();
		loli.setCreateTime(System.currentTimeMillis());
		loli.setDas(dasDAO.findById(dasId));
		loli.setLatitude(latitude);
		loli.setLongitude(longitude);
		loli.setName(name);
		loli.setNote(note);
		loli.setOrgan(organDAO.findById(organId));
		loli.setPeriod(period);
		loli.setStakeNumber(stakeNumber);
		loli.setStandardNumber(standardNumber);
		loli.setLiLumiMax(liLumiMax);
		loli.setLiLumiMin(liLumiMin);
		loli.setLoLumiMax(loLumiMax);
		loli.setLoLumiMin(loLumiMin);
		loli.setNavigation(navigation);
		loli.setReserve(reserve);
		loli.setIp(ip);
		loli.setPort(port);
		loliDAO.save(loli);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_LOLI);
		return loli.getId();
	}

	@Override
	public void updateLoli(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short loLumiMax,
			Short loLumiMin, Short liLumiMax, Short liLumiMin, String note,
			String navigation, String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<LoLi> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = loliDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = loliDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		LoLi loli = loliDAO.findById(id);
		if (null != name) {
			loli.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(loli.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_LOLI);
			loli.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			loli.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			loli.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			loli.setPeriod(period);
		}
		if (null != stakeNumber) {
			loli.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			loli.setLongitude(longitude);
		}
		if (null != latitude) {
			loli.setLatitude(latitude);
		}
		loli.setLoLumiMax(loLumiMax);
		loli.setLoLumiMin(loLumiMin);
		loli.setLiLumiMax(liLumiMax);
		loli.setLiLumiMin(liLumiMin);
		if (null != note) {
			loli.setNote(note);
		}
		if (null != navigation) {
			loli.setNavigation(navigation);
		}
		if (null != reserve) {
			loli.setReserve(reserve);
		}
		if (null != ip) {
			loli.setIp(ip);
		}
		loli.setPort(port);
		loliDAO.update(loli);

	}

	@Override
	public void deleteLoli(String id) {
		// 删除检测器和角色关系
		loliDAO.deleteRoleLoliPermission(id);
		// 同步SN
		LoLi loli = loliDAO.findById(id);
		syncSN(loli.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_LOLI);
		loliDAO.deleteById(id);
	}

	@Override
	public GetLoliVO getLoli(String id) {
		LoLi loli = loliDAO.findById(id);
		GetLoliVO vo = new GetLoliVO();
		vo.setCreateTime(loli.getCreateTime() + "");
		vo.setDasId(loli.getDas() != null ? loli.getDas().getId() : "");
		vo.setDasName(loli.getDas() != null ? loli.getDas().getName() : "");
		vo.setId(loli.getId() + "");
		vo.setLatitude(loli.getLatitude());
		vo.setLongitude(loli.getLongitude());
		vo.setName(loli.getName());
		vo.setNote(loli.getNote());
		vo.setOrganId(loli.getOrgan() != null ? loli.getOrgan().getId() : "");
		vo.setPeriod(loli.getPeriod() + "");
		vo.setReserve(loli.getReserve());
		vo.setStakeNumber(loli.getStakeNumber());
		vo.setStandardNumber(loli.getStandardNumber());
		vo.setLiLumiMax(loli.getLiLumiMax() + "");
		vo.setLiLumiMin(loli.getLiLumiMin() + "");
		vo.setLoLumiMax(loli.getLoLumiMax() + "");
		vo.setLoLumiMin(loli.getLoLumiMin() + "");
		vo.setNavigation(loli.getNavigation());
		vo.setIp(loli.getIp());
		vo.setPort(loli.getPort() + "");
		return vo;
	}

	@Override
	public Integer countLoli(String organId, String name,
			String standardNumber, String stakeNumber) {
		return loliDAO.countLoli(organId, name, standardNumber, stakeNumber);
	}

	@Override
	public List<GetLoliVO> listLoli(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<LoLi> list = loliDAO.listLoli(organId, name, standardNumber,
				stakeNumber, startIndex, limit);
		List<GetLoliVO> listVO = new ArrayList<GetLoliVO>();
		for (LoLi loli : list) {
			GetLoliVO vo = new GetLoliVO();
			vo.setCreateTime(loli.getCreateTime() + "");
			vo.setDasId(loli.getDas() != null ? loli.getDas().getId() : "");
			vo.setDasName(loli.getDas() != null ? loli.getDas().getName() : "");
			vo.setId(loli.getId() + "");
			vo.setLatitude(loli.getLatitude());
			vo.setLongitude(loli.getLongitude());
			vo.setName(loli.getName());
			vo.setNote(loli.getNote());
			vo.setOrganId(loli.getOrgan() != null ? loli.getOrgan().getId()
					: "");
			vo.setPeriod(loli.getPeriod() + "");
			vo.setReserve(loli.getReserve());
			vo.setStakeNumber(loli.getStakeNumber());
			vo.setStandardNumber(loli.getStandardNumber());
			vo.setLiLumiMax(loli.getLiLumiMax() + "");
			vo.setLiLumiMin(loli.getLiLumiMin() + "");
			vo.setLoLumiMax(loli.getLoLumiMax() + "");
			vo.setLoLumiMin(loli.getLoLumiMin() + "");
			vo.setNavigation(loli.getNavigation());
			vo.setIp(loli.getIp());
			vo.setPort(loli.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createWeatherStat(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short vLowLimit, Short wUpLimit,
			Short rUpLimit, Short sUpLimit, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<WeatherStat> list = wstDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wstDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		WeatherStat wst = new WeatherStat();
		wst.setCreateTime(System.currentTimeMillis());
		wst.setDas(dasDAO.findById(dasId));
		wst.setLatitude(latitude);
		wst.setLongitude(longitude);
		wst.setName(name);
		wst.setNote(note);
		wst.setOrgan(organDAO.findById(organId));
		wst.setPeriod(period);
		wst.setStakeNumber(stakeNumber);
		wst.setStandardNumber(standardNumber);
		wst.setvLowLimit(vLowLimit);
		wst.setwUpLimit(wUpLimit);
		wst.setrUpLimit(rUpLimit);
		wst.setsUpLimit(sUpLimit);
		wst.setNavigation(navigation);
		wst.setReserve(reserve);
		wst.setIp(ip);
		wst.setPort(port);
		wstDAO.save(wst);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_WST);
		return wst.getId();
	}

	@Override
	public void updateWeatherStat(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short vLowLimit, Short wUpLimit, Short rUpLimit,
			Short sUpLimit, String note, String navigation, String reserve,
			String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<WeatherStat> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = wstDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wstDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		WeatherStat wst = wstDAO.findById(id);
		if (null != name) {
			wst.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(wst.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_WST);
			wst.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			wst.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			wst.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			wst.setPeriod(period);
		}
		if (null != stakeNumber) {
			wst.setStakeNumber(stakeNumber);
		}
		wst.setLongitude(longitude);
		wst.setLatitude(latitude);
		wst.setvLowLimit(vLowLimit);
		wst.setwUpLimit(wUpLimit);
		wst.setrUpLimit(rUpLimit);
		wst.setsUpLimit(sUpLimit);
		if (null != note) {
			wst.setNote(note);
		}
		if (null != navigation) {
			wst.setNavigation(navigation);
		}
		if (null != reserve) {
			wst.setReserve(reserve);
		}
		if (null != ip) {
			wst.setIp(ip);
		}
		wst.setPort(port);
		wstDAO.update(wst);

	}

	@Override
	public void deleteWeatherStat(String id) {
		// 删除气象检测器和角色关系
		wstDAO.deleteRoleWSTPermission(id);
		// 同步SN
		WeatherStat wst = wstDAO.findById(id);
		syncSN(wst.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_WST);
		wstDAO.deleteById(id);
	}

	@Override
	public GetWeatherStatVO getWeatherStat(String id) {
		WeatherStat wst = wstDAO.findById(id);
		GetWeatherStatVO vo = new GetWeatherStatVO();
		vo.setCreateTime(wst.getCreateTime() + "");
		vo.setDasId(wst.getDas() != null ? wst.getDas().getId() : "");
		vo.setDasName(wst.getDas() != null ? wst.getDas().getName() : "");
		vo.setId(wst.getId() + "");
		vo.setLatitude(wst.getLatitude());
		vo.setLongitude(wst.getLongitude());
		vo.setName(wst.getName());
		vo.setNote(wst.getNote());
		vo.setOrganId(wst.getOrgan() != null ? wst.getOrgan().getId() : "");
		vo.setPeriod(wst.getPeriod() + "");
		vo.setReserve(wst.getReserve());
		vo.setStakeNumber(wst.getStakeNumber());
		vo.setStandardNumber(wst.getStandardNumber());
		vo.setvLowLimit(wst.getvLowLimit() + "");
		vo.setsUpLimit(wst.getsUpLimit() + "");
		vo.setrUpLimit(wst.getrUpLimit() + "");
		vo.setwUpLimit(wst.getwUpLimit() + "");
		vo.setNavigation(wst.getNavigation());
		vo.setIp(wst.getIp());
		vo.setPort(wst.getPort() + "");
		return vo;
	}

	@Override
	public Integer countWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber) {
		return wstDAO.countWeatherStat(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetWeatherStatVO> listWeatherStat(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<WeatherStat> list = wstDAO.listWeatherStat(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetWeatherStatVO> listVO = new ArrayList<GetWeatherStatVO>();
		for (WeatherStat wst : list) {
			GetWeatherStatVO vo = new GetWeatherStatVO();
			vo.setCreateTime(wst.getCreateTime() + "");
			vo.setDasId(wst.getDas() != null ? wst.getDas().getId() : "");
			vo.setDasName(wst.getDas() != null ? wst.getDas().getName() : "");
			vo.setId(wst.getId() + "");
			vo.setLatitude(wst.getLatitude());
			vo.setLongitude(wst.getLongitude());
			vo.setName(wst.getName());
			vo.setNote(wst.getNote());
			vo.setOrganId(wst.getOrgan() != null ? wst.getOrgan().getId() : "");
			vo.setPeriod(wst.getPeriod() + "");
			vo.setReserve(wst.getReserve());
			vo.setStakeNumber(wst.getStakeNumber());
			vo.setStandardNumber(wst.getStandardNumber());
			vo.setvLowLimit(wst.getvLowLimit() + "");
			vo.setsUpLimit(wst.getsUpLimit() + "");
			vo.setwUpLimit(wst.getwUpLimit() + "");
			vo.setrUpLimit(wst.getrUpLimit() + "");
			vo.setNavigation(wst.getNavigation());
			vo.setIp(wst.getIp());
			vo.setPort(wst.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createVehicleDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Integer sUpLimit,
			Integer sLowLimit, Integer oUpLimit, Integer oLowLimit,
			Integer vUpLimit, Integer vLowLimit, String note,
			String navigation, String ip, String port, String laneNumber,
			String reserve) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<VehicleDetector> list = vdDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = vdDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		VehicleDetector vd = new VehicleDetector();
		vd.setCreateTime(System.currentTimeMillis());
		vd.setDas(dasDAO.findById(dasId));
		vd.setLatitude(latitude);
		vd.setLongitude(longitude);
		vd.setName(name);
		vd.setNote(note);
		vd.setOrgan(organDAO.findById(organId));
		vd.setPeriod(period);
		vd.setStakeNumber(stakeNumber);
		vd.setStandardNumber(standardNumber);
		vd.setsUpLimit(sUpLimit);
		vd.setsLowLimit(sLowLimit);
		vd.setoUpLimit(oUpLimit);
		vd.setoLowLimit(oLowLimit);
		vd.setvUpLimit(vUpLimit);
		vd.setvLowLimit(vLowLimit);
		vd.setNavigation(navigation);
		vd.setIp(ip);
		// 数据库trigger需要强制转换port为integer类型
		if (StringUtils.isBlank(port)) {
			vd.setPort(null);
		} else if (StringUtils.isNumeric(port)) {
			vd.setPort(port);
		} else {
			vd.setPort(null);
		}
		vd.setLaneNumber(laneNumber);
		vd.setReserve(reserve);
		vdDAO.save(vd);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_VD);
		return vd.getId();
	}

	@Override
	public void updateVehicleDetector(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Integer sUpLimit, Integer sLowLimit,
			Integer oUpLimit, Integer oLowLimit, Integer vUpLimit,
			Integer vLowLimit, String note, String navigation, String ip,
			String port, String laneNumber, String reserve) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<VehicleDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = vdDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = vdDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		VehicleDetector vd = vdDAO.findById(id);
		if (null != name) {
			vd.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(vd.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_VD);
			vd.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			vd.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			vd.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			vd.setPeriod(period);
		}
		if (null != stakeNumber) {
			vd.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			vd.setLongitude(longitude);
		}
		if (null != latitude) {
			vd.setLatitude(latitude);
		}
		vd.setsUpLimit(sUpLimit);
		vd.setsLowLimit(sLowLimit);
		vd.setoUpLimit(oUpLimit);
		vd.setoLowLimit(oLowLimit);
		vd.setvUpLimit(vUpLimit);
		vd.setvLowLimit(vLowLimit);
		if (null != note) {
			vd.setNote(note);
		}
		if (null != navigation) {
			vd.setNavigation(navigation);
		}
		if (null != ip) {
			vd.setIp(ip);
		}
		// 数据库trigger需要强制转换port为integer类型
		if (StringUtils.isBlank(port)) {
			vd.setPort(null);
		} else if (StringUtils.isNumeric(port)) {
			vd.setPort(port);
		} else {
			vd.setPort(null);
		}

		if (null != laneNumber) {
			vd.setLaneNumber(laneNumber);
		}
		if (null != reserve) {
			vd.setReserve(reserve);
		}
		vdDAO.update(vd);
	}

	@Override
	public void deleteVehicleDetector(String id) {
		// 删除子车检器
		vdDAO.deleteSubVehicleDetector(id);
		// 删除检测器和角色关系
		vdDAO.deleteRoleVDPermission(id);
		// 同步SN
		VehicleDetector vd = vdDAO.findById(id);
		syncSN(vd.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_VD);
		vdDAO.deleteById(id);
	}

	@Override
	public GetVehicleDetectorVO getVehicleDetector(String id) {
		VehicleDetector vd = vdDAO.findById(id);
		GetVehicleDetectorVO vo = new GetVehicleDetectorVO();
		vo.setCreateTime(vd.getCreateTime() + "");
		vo.setDasId(vd.getDas() != null ? vd.getDas().getId() : "");
		vo.setDasName(vd.getDas() != null ? vd.getDas().getName() : "");
		vo.setId(vd.getId() + "");
		vo.setLatitude(vd.getLatitude());
		vo.setLongitude(vd.getLongitude());
		vo.setName(vd.getName());
		vo.setNote(vd.getNote());
		vo.setOrganId(vd.getOrgan() != null ? vd.getOrgan().getId() : "");
		vo.setPeriod(vd.getPeriod() + "");
		vo.setReserve(vd.getReserve());
		vo.setStakeNumber(vd.getStakeNumber());
		vo.setStandardNumber(vd.getStandardNumber());
		vo.setsUpLimit(vd.getsUpLimit() + "");
		vo.setsLowLimit(vd.getsLowLimit() + "");
		vo.setoUpLimit(vd.getoUpLimit() + "");
		vo.setoLowLimit(vd.getoLowLimit() + "");
		vo.setvUpLimit(vd.getvUpLimit() + "");
		vo.setvLowLimit(vd.getvLowLimit() + "");
		vo.setNavigation(vd.getNavigation());
		vo.setIp(vd.getIp());
		vo.setPort(vd.getPort());
		vo.setLaneNumber(vd.getLaneNumber());
		return vo;
	}

	@Override
	public Integer countVehicleDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return vdDAO.countVehicleDetector(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetVehicleDetectorVO> listVehicleDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		List<VehicleDetector> list = vdDAO.listVehicleDetector(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetVehicleDetectorVO> listVO = new ArrayList<GetVehicleDetectorVO>();
		for (VehicleDetector vd : list) {
			GetVehicleDetectorVO vo = new GetVehicleDetectorVO();
			vo.setCreateTime(vd.getCreateTime() + "");
			vo.setDasId(vd.getDas() != null ? vd.getDas().getId() : "");
			vo.setDasName(vd.getDas() != null ? vd.getDas().getName() : "");
			vo.setId(vd.getId() + "");
			vo.setLatitude(vd.getLatitude());
			vo.setLongitude(vd.getLongitude());
			vo.setName(vd.getName());
			vo.setNote(vd.getNote());
			vo.setOrganId(vd.getOrgan() != null ? vd.getOrgan().getId() : "");
			vo.setPeriod(vd.getPeriod() + "");
			vo.setReserve(vd.getReserve());
			vo.setStakeNumber(vd.getStakeNumber());
			vo.setStandardNumber(vd.getStandardNumber());
			vo.setsUpLimit(vd.getsUpLimit() + "");
			vo.setsLowLimit(vd.getsLowLimit() + "");
			vo.setoUpLimit(vd.getoUpLimit() + "");
			vo.setoLowLimit(vd.getoLowLimit() + "");
			vo.setvUpLimit(vd.getvUpLimit() + "");
			vo.setvLowLimit(vd.getvLowLimit() + "");
			vo.setNavigation(vd.getNavigation());
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createWindSpeed(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short direction,
			Integer wUpLimit, String note, String navigation, String reserve,
			String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<WindSpeed> list = wsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		WindSpeed ws = new WindSpeed();
		ws.setCreateTime(System.currentTimeMillis());
		ws.setDas(dasDAO.findById(dasId));
		ws.setLatitude(latitude);
		ws.setLongitude(longitude);
		ws.setName(name);
		ws.setNote(note);
		ws.setOrgan(organDAO.findById(organId));
		ws.setPeriod(period);
		ws.setStakeNumber(stakeNumber);
		ws.setStandardNumber(standardNumber);
		ws.setDirection(direction);
		ws.setwUpLimit(wUpLimit);
		ws.setNavigation(navigation);
		ws.setReserve(reserve);
		ws.setIp(ip);
		ws.setPort(port);
		wsDAO.save(ws);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_WS);
		return ws.getId();
	}

	@Override
	public void updateWindSpeed(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short direction,
			Integer wUpLimit, String note, String navigation, String reserve,
			String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<WindSpeed> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = wsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		WindSpeed ws = wsDAO.findById(id);
		if (null != name) {
			ws.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(ws.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_WS);
			ws.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			ws.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			ws.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			ws.setPeriod(period);
		}
		if (null != stakeNumber) {
			ws.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			ws.setLongitude(longitude);
		}
		if (null != latitude) {
			ws.setLatitude(latitude);
		}
		ws.setDirection(direction);
		ws.setwUpLimit(wUpLimit);
		if (null != note) {
			ws.setNote(note);
		}
		if (null != navigation) {
			ws.setNavigation(navigation);
		}
		if (null != reserve) {
			ws.setReserve(reserve);
		}
		if (null != ip) {
			ws.setIp(ip);
		}
		ws.setPort(port);
		wsDAO.update(ws);

	}

	@Override
	public void deleteWindSpeed(String id) {
		// 删除检测器和角色的关系
		wsDAO.deleteRoleWSPermission(id);
		// 同步SN
		WindSpeed ws = wsDAO.findById(id);
		syncSN(ws.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_WS);

		wsDAO.deleteById(id);
	}

	@Override
	public GetWindSpeedVO getWindSpeed(String id) {
		WindSpeed ws = wsDAO.findById(id);
		GetWindSpeedVO vo = new GetWindSpeedVO();
		vo.setCreateTime(ws.getCreateTime() + "");
		vo.setDasId(ws.getDas() != null ? ws.getDas().getId() : "");
		vo.setDasName(ws.getDas() != null ? ws.getDas().getName() : "");
		vo.setId(ws.getId() + "");
		vo.setLatitude(ws.getLatitude());
		vo.setLongitude(ws.getLongitude());
		vo.setName(ws.getName());
		vo.setNote(ws.getNote());
		vo.setOrganId(ws.getOrgan() != null ? ws.getOrgan().getId() : "");
		vo.setDasName(ws.getDas() != null ? ws.getDas().getName() : "");
		vo.setPeriod(ws.getPeriod() + "");
		vo.setReserve(ws.getReserve());
		vo.setStakeNumber(ws.getStakeNumber());
		vo.setStandardNumber(ws.getStandardNumber());
		vo.setDirection(ws.getDirection() + "");
		vo.setwUpLimit(ws.getwUpLimit() + "");
		vo.setNavigation(ws.getNavigation());
		vo.setIp(ws.getIp());
		vo.setPort(ws.getPort() + "");
		return vo;
	}

	@Override
	public Integer countWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber) {
		return wsDAO.countWindSpeed(organId, name, standardNumber, stakeNumber);
	}

	@Override
	public List<GetWindSpeedVO> listWindSpeed(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<WindSpeed> list = wsDAO.listWindSpeed(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetWindSpeedVO> listVO = new ArrayList<GetWindSpeedVO>();
		for (WindSpeed ws : list) {
			GetWindSpeedVO vo = new GetWindSpeedVO();
			vo.setCreateTime(ws.getCreateTime() + "");
			vo.setDasId(ws.getDas() != null ? ws.getDas().getId() : "");
			vo.setDasName(ws.getDas() != null ? ws.getDas().getName() : "");
			vo.setId(ws.getId() + "");
			vo.setLatitude(ws.getLatitude());
			vo.setLongitude(ws.getLongitude());
			vo.setName(ws.getName());
			vo.setNote(ws.getNote());
			vo.setOrganId(ws.getOrgan() != null ? ws.getOrgan().getId() : "");
			vo.setPeriod(ws.getPeriod() + "");
			vo.setReserve(ws.getReserve());
			vo.setStakeNumber(ws.getStakeNumber());
			vo.setStandardNumber(ws.getStandardNumber());
			vo.setDirection(ws.getDirection() + "");
			vo.setwUpLimit(ws.getwUpLimit() + "");
			vo.setNavigation(ws.getNavigation());
			vo.setIp(ws.getIp());
			vo.setPort(ws.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createControlDevice(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Short type, Short subType,
			String note, String navigation, Integer height, Integer width,
			Short sectionType, String reserve, String ip, Integer port) {
		ControlDevice cd = null;
		if (type.intValue() == TypeDefinition.DEVICE_TYPE_CMS) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
					&& !organ.getType()
							.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
					&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)
					&& !organ.getType().equals(
							TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceCms();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_FAN) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceFan();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_LIGHT) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceLight();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_RD) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceRd();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_WP) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceWp();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_RAIL) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
					&& !organ.getType().equals(
							TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceRail();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_IS) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
					&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)
					&& !organ.getType()
							.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceIs();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_TSL) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
					&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)
					&& !organ.getType()
							.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceTsl();
		} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_LIL) {
			// 验证机构
			Organ organ = organDAO.findById(organId);
			if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
					&& !organ.getType().equals(
							TypeDefinition.ORGAN_TYPE_TOLLGATE)
					&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)
					&& !organ.getType()
							.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
				throw new BusinessException(
						ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
						"detector mapping organ ," + type.toString()
								+ ", is error");
			}
			cd = new ControlDeviceLil();
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<ControlDevice> list = controlDeviceDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = controlDeviceDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		cd.setCreateTime(System.currentTimeMillis());
		cd.setDas(dasDAO.findById(dasId));
		cd.setLatitude(latitude);
		cd.setLongitude(longitude);
		cd.setName(name);
		cd.setNote(note);
		cd.setOrgan(organDAO.findById(organId));
		cd.setPeriod(period);
		cd.setStakeNumber(stakeNumber);
		cd.setStandardNumber(standardNumber);
		cd.setSubType(subType);
		cd.setNavigation(navigation);
		cd.setHeight(height);
		cd.setWidth(width);
		cd.setSectionType(sectionType);
		cd.setReserve(reserve);
		cd.setIp(ip);
		cd.setPort(port);
		controlDeviceDAO.save(cd);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_CD);
		return cd.getId();
	}

	@Override
	public void updateControlDevice(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Short type, Short subType, String note,
			String navigation, Integer height, Integer width,
			Short sectionType, String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<ControlDevice> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = controlDeviceDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = controlDeviceDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		ControlDevice cd = controlDeviceDAO.findById(id);
		if (null != name) {
			cd.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(cd.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_CD);
			cd.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			cd.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			cd.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			cd.setPeriod(period);
		}
		if (null != stakeNumber) {
			cd.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			cd.setLongitude(longitude);
		}
		if (null != latitude) {
			cd.setLatitude(latitude);
		}
		if (null != note) {
			cd.setNote(note);
		}
		if (null != navigation) {
			cd.setNavigation(navigation);
		}
		if (null != reserve) {
			cd.setReserve(reserve);
		}
		if (null != ip) {
			cd.setIp(ip);
		}
		cd.setPort(port);
		cd.setHeight(height);
		cd.setWidth(width);
		cd.setSectionType(sectionType);
		if (null != subType) {
			cd.setSubType(subType);
		}
		controlDeviceDAO.update(cd);

	}

	@Override
	public void deleteControlDevice(String id, String type) {
		// 删除控制设备和角色的关系
		controlDeviceDAO.deleteRoleCDPermission(id, type);
		// 同步SN
		ControlDevice cd = controlDeviceDAO.findById(id);
		syncSN(cd.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_CD);

		controlDeviceDAO.deleteById(id);
	}

	@Override
	public GetControlDeviceVO getControlDevice(String id) {
		ControlDevice cd = controlDeviceDAO.findById(id);
		GetControlDeviceVO vo = new GetControlDeviceVO();
		vo.setCreateTime(cd.getCreateTime() + "");
		vo.setDasId(cd.getDas() != null ? cd.getDas().getId() : "");
		vo.setDasName(cd.getDas() != null ? cd.getDas().getName() : "");
		vo.setId(cd.getId() + "");
		vo.setLatitude(cd.getLatitude());
		vo.setLongitude(cd.getLongitude());
		vo.setName(cd.getName());
		vo.setNote(cd.getNote());
		vo.setOrganId(cd.getOrgan() != null ? cd.getOrgan().getId() : "");
		vo.setPeriod(cd.getPeriod() + "");
		vo.setReserve(cd.getReserve());
		vo.setStakeNumber(cd.getStakeNumber());
		vo.setStandardNumber(cd.getStandardNumber());
		// if (cd.getType() == TypeDefinition.DEVICE_TYPE_TSL + "") {
		// vo.setType(TypeDefinition.DEVICE_TYPE_CMS + "");
		// vo.setSubType("4");
		// } else if (cd.getType() == TypeDefinition.DEVICE_TYPE_LIL + "") {
		// vo.setType(TypeDefinition.DEVICE_TYPE_CMS + "");
		// vo.setSubType("6");
		// } else {
		vo.setType(cd.getType() + "");
		vo.setSubType(cd.getSubType() + "");
		// }
		vo.setNavigation(cd.getNavigation());
		vo.setHeight(cd.getHeight() + "");
		vo.setWidth(cd.getWidth() + "");
		vo.setSectionType(cd.getSectionType() + "");
		vo.setIp(cd.getIp());
		vo.setPort(cd.getPort() + "");
		return vo;
	}

	@Override
	public List<GetControlDeviceVO> listControlDevice(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit, Short type, Short subType) {
		List<ControlDevice> list = controlDeviceDAO.listControlDevice(organId,
				name, standardNumber, stakeNumber, startIndex, limit, type,
				subType);
		List<GetControlDeviceVO> listVO = new ArrayList<GetControlDeviceVO>();
		for (ControlDevice cd : list) {
			GetControlDeviceVO vo = new GetControlDeviceVO();
			vo.setCreateTime(cd.getCreateTime() + "");
			vo.setDasId(cd.getDas() != null ? cd.getDas().getId() : "");
			vo.setDasName(cd.getDas() != null ? cd.getDas().getName() : "");
			vo.setId(cd.getId() + "");
			vo.setLatitude(cd.getLatitude());
			vo.setLongitude(cd.getLongitude());
			vo.setName(cd.getName());
			vo.setNote(cd.getNote());
			vo.setOrganId(cd.getOrgan() != null ? cd.getOrgan().getId() : "");
			vo.setPeriod(cd.getPeriod() + "");
			vo.setReserve(cd.getReserve());
			vo.setStakeNumber(cd.getStakeNumber());
			vo.setStandardNumber(cd.getStandardNumber());
			// if (cd.getType() == TypeDefinition.DEVICE_TYPE_TSL + "") {
			// vo.setType(TypeDefinition.DEVICE_TYPE_CMS + "");
			// vo.setSubType("4");
			// } else if (cd.getType() == TypeDefinition.DEVICE_TYPE_LIL + "") {
			// vo.setType(TypeDefinition.DEVICE_TYPE_CMS + "");
			// vo.setSubType("6");
			// } else {
			vo.setType(cd.getType() + "");
			vo.setSubType(cd.getSubType() + "");
			// }
			vo.setNavigation(cd.getNavigation());
			vo.setHeight(cd.getHeight() + "");
			vo.setWidth(cd.getWidth() + "");
			vo.setSectionType(cd.getSectionType() + "");
			vo.setIp(cd.getIp());
			vo.setPort(cd.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public Integer countControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Short type, Short subType) {

		return controlDeviceDAO.countControlDevice(organId, name,
				standardNumber, stakeNumber, type, subType);
	}

	@Override
	public List<ListCameraVO> listCameraByDevice(String[] organs, String name,
			String stakeNumber, String manufacturerId, Integer startIndex,
			Integer limit, String standardNumber) {
		List<Camera> cameras = cameraDAO.listCameraByDevice(organs, name,
				stakeNumber, manufacturerId, startIndex, limit, standardNumber);
		List<ListCameraVO> listVO = new ArrayList<ListCameraVO>();
		for (Camera camera : cameras) {
			ListCameraVO vo = new ListCameraVO();
			VideoDeviceProperty property = camera.getProperty();
			vo.setCcsId(camera.getParent().getCcs() != null ? camera
					.getParent().getCcs().getId() : "");
			vo.setCenterStorePlan(property.getCenterStorePlan());
			vo.setChannelId(camera.getChannelNumber() + "");
			vo.setCreateTime(camera.getCreateTime() + "");
			vo.setCrsId(camera.getCrs() != null ? camera.getCrs().getId() : "");
			vo.setCrsName(camera.getCrs() != null ? camera.getCrs().getName()
					: "");
			vo.setExpand(camera.getProperty().getExpand());
			vo.setId(camera.getId());
			vo.setLocalStorePlan(property.getLocalStorePlan());
			vo.setLocation(camera.getLocation());
			vo.setStreamType(property.getStreamType());
			vo.setMssId(camera.getMss() != null ? camera.getMss().getId() : "");
			vo.setMssName(camera.getMss() != null ? camera.getMss().getName()
					: "");
			vo.setName(camera.getName());
			vo.setNote(camera.getNote());
			vo.setOrganId(camera.getOrgan().getId());
			vo.setParentId(camera.getParent().getId());
			vo.setPtsId(camera.getParent().getPts() != null ? camera
					.getParent().getPts().getId() : "");
			vo.setStandardNumber(camera.getStandardNumber());
			vo.setStoreType(property.getStoreType() + "");
			vo.setSubType(camera.getSubType());
			vo.setType(TypeDefinition.DEVICE_TYPE_CAMERA + "");
			vo.setManufacturerId(camera.getManufacturer() != null ? camera
					.getManufacturer().getId().toString() : "");
			vo.setManufacturerName(camera.getManufacturer() != null ? camera
					.getManufacturer().getName() : "");
			vo.setDeviceModelId(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getId().toString() : "");
			vo.setDeviceModelName(camera.getDeviceModel() != null ? camera
					.getDeviceModel().getName() : "");
			vo.setNavigation(camera.getNavigation());
			vo.setStakeNumber(camera.getStakeNumber());
			vo.setDvrName(camera.getParent().getName());
			vo.setStoreStream(property.getStoreStream());
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public Integer cameraByDeviceTotalCount(String organs[], String name,
			String stakeNumber, String manufacturerId, String standardNumber) {
		return cameraDAO.cameraByDeviceTotalCount(organs, name, stakeNumber,
				manufacturerId, standardNumber);
	}

	@Override
	public String createSolarBattery(String name, String standardNumber,
			String organId, String maxVoltage, String minVoltage,
			String batteryCapacity, String storePlan, String note,
			String dasId, String navigation, String stakeNumber, String period) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<SolarBattery> list = solarBatteryDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		SolarBattery sb = new SolarBattery();
		sb.setBatteryCapacity(batteryCapacity);
		sb.setCreateTime(System.currentTimeMillis());
		sb.setMaxVoltage(maxVoltage);
		sb.setMinVoltage(minVoltage);
		sb.setName(name);
		sb.setNote(note);
		sb.setOrgan(organDAO.findById(organId));
		sb.setStandardNumber(standardNumber);
		sb.setStorePlan(storePlan);
		sb.setDas(dasDAO.findById(dasId));
		sb.setNavigation(navigation);
		sb.setStakeNumber(stakeNumber);
		sb.setPeriod(period);
		solarBatteryDAO.save(sb);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_SOLAR_BATTERY);
		return sb.getId();
	}

	@Override
	public void updateSolarBattery(String id, String name,
			String standardNumber, String organId, String maxVoltage,
			String minVoltage, String batteryCapacity, String storePlan,
			String note, String dasId, String navigation, String stakeNumber,
			String period) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<SolarBattery> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = solarBatteryDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}

		SolarBattery sb = solarBatteryDAO.findById(id);
		if (null != name) {
			sb.setName(name);
		}
		if (null != standardNumber) {
			sb.setStandardNumber(standardNumber);
		}
		if (null != organId) {
			sb.setOrgan(organDAO.findById(organId));
		}
		if (null != maxVoltage) {
			sb.setMaxVoltage(maxVoltage);
		}
		if (null != minVoltage) {
			sb.setMinVoltage(minVoltage);
		}
		if (null != batteryCapacity) {
			sb.setBatteryCapacity(batteryCapacity);
		}
		if (null != storePlan) {
			sb.setStorePlan(storePlan);
		}
		if (null != note) {
			sb.setNote(note);
		}
		if (null != dasId) {
			sb.setDas(dasDAO.findById(dasId));
		}
		if (null != navigation) {
			sb.setNavigation(navigation);
		}
		if (null != stakeNumber) {
			sb.setStakeNumber(stakeNumber);
		}
		if (null != period) {
			sb.setPeriod(period);
		}
	}

	@Override
	@LogMethod(targetType = "SolarBattery", operationType = "delete", name = "删除太阳能电池", code = "2060")
	public void deleteSolarBattery(String id) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("solarBattery.id", id);
		List<SolarDevice> sds = solarDeviceDAO.findByPropertys(params);
		for (SolarDevice sd : sds) {
			solarDeviceDAO.delete(sd);
		}

		solarBatteryDAO.deleteById(id);
	}

	@Override
	public GetSolarBatteryVO getSolarBattery(String id) {
		SolarBattery sb = solarBatteryDAO.findById(id);
		GetSolarBatteryVO vo = new GetSolarBatteryVO();
		vo.setBatteryCapacity(sb.getBatteryCapacity());
		vo.setCreateTime(sb.getCreateTime() + "");
		vo.setId(sb.getId());
		vo.setMaxVoltage(sb.getMaxVoltage());
		vo.setMinVoltage(sb.getMinVoltage());
		vo.setName(sb.getName());
		vo.setNote(sb.getNote());
		vo.setOrganId(sb.getOrgan().getId());
		vo.setStandardNumber(sb.getStandardNumber());
		vo.setStorePlan(sb.getStorePlan());
		vo.setDasId(sb.getDas().getId());
		vo.setNavigation(sb.getNavigation());
		vo.setStakeNumber(sb.getStakeNumber());
		vo.setPeriod(sb.getPeriod());
		vo.setDasName(sb.getDas().getName());
		return vo;
	}

	@Override
	public Integer solarBatteryTotalCount(String name, String organId) {
		return solarBatteryDAO.solarBatteryTotalCount(name, organId);
	}

	@Override
	public List<GetSolarBatteryVO> listSolarBattery(String name,
			String organId, Integer startIndex, Integer limit) {
		List<SolarBattery> sbs = solarBatteryDAO.listSolarBattery(name,
				organId, startIndex, limit);
		List<GetSolarBatteryVO> list = new ArrayList<GetSolarBatteryVO>();
		for (SolarBattery sb : sbs) {
			GetSolarBatteryVO vo = new GetSolarBatteryVO();
			vo.setBatteryCapacity(sb.getBatteryCapacity());
			vo.setCreateTime(sb.getCreateTime() + "");
			vo.setId(sb.getId());
			vo.setMaxVoltage(sb.getMaxVoltage());
			vo.setMinVoltage(sb.getMinVoltage());
			vo.setName(sb.getName());
			vo.setNote(sb.getNote());
			vo.setOrganId(sb.getOrgan().getId());
			vo.setStandardNumber(sb.getStandardNumber());
			vo.setStorePlan(sb.getStorePlan());
			vo.setDasId(sb.getDas().getId());
			vo.setNavigation(sb.getNavigation());
			vo.setStakeNumber(sb.getStakeNumber());
			vo.setPeriod(sb.getPeriod());
			vo.setDasName(sb.getDas().getName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void bindDeviceSolar(String type, String solarId, String json) {
		// 解析
		try {
			JSONArray array = JSONArray.fromObject(json);
			// 要删除的角色设备权限的设备ID集合
			String[] deviceIds = new String[array.size()];
			if (deviceIds.length <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"deviceId can not be null !");
			}
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				deviceIds[i] = obj.getString("deviceId");
			}
			// 删除以前的关系
			solarDeviceDAO.deleteBySolarDevice(solarId, type, deviceIds);
			SolarBattery sb = solarBatteryDAO.findById(solarId);
			if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (obj.getString("privilege").equals("1")) {
						SolarDeviceCamera sdc = new SolarDeviceCamera();
						sdc.setCamera(cameraDAO.findById(obj
								.getString("deviceId")));
						sdc.setSolarBattery(sb);
						solarDeviceDAO.save(sdc);
					}
				}
			} else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (obj.getString("privilege").equals("1")) {
						SolarDeviceVD sdv = new SolarDeviceVD();
						sdv.setVd(vdDAO.findById(obj.getString("deviceId")));
						sdv.setSolarBattery(sb);
						solarDeviceDAO.save(sdv);
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
	public int countOrganCamera(String organId, String solarId, String name) {
		return solarDeviceDAO.countOrganCamera(organId, solarId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganCamera(String organId,
			String solarId, String name, int startIndex, int limit) {
		return solarDeviceDAO.listOrganCamera(organId, solarId, name,
				startIndex, limit);
	}

	@Override
	public void removeSolarDevice(String solarId) {
		solarDeviceDAO.removeSolarDevice(solarId);
	}

	/**
	 * 判断摄像头是否关联太阳能电池
	 * 
	 * @param deviceSolars
	 *            太阳能电池关联的设备列表
	 * @param camera
	 *            摄像头
	 * @param vo
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:26:26
	 */
	public void setCameraSolar(List<DeviceSolarVO> deviceSolars, Camera camera,
			AuthDeviceVO vo) {
		for (DeviceSolarVO dsVO : deviceSolars) {
			if (camera.getId().equals(dsVO.getDeviceId())) {
				vo.setSolarName(dsVO.getSolarName());
				vo.setSolarSN(dsVO.getSolarSN());
				vo.setSolarNavigation(dsVO.getSolarNavigation());
				vo.setSolarStakeNumber(dsVO.getSolarStakeNumber());
				vo.setBatteryCapacity(dsVO.getBatteryCapacity());
				deviceSolars.remove(dsVO);
				break;
			}
		}
	}

	private void setCameraSolar(List<DeviceSolarVO> deviceSolars,
			Camera camera, UserResourceVO vo) {
		for (DeviceSolarVO dsVO : deviceSolars) {
			if (camera.getId().equals(dsVO.getDeviceId())) {
				vo.setSolarName(dsVO.getSolarName());
				vo.setSolarSN(dsVO.getSolarSN());
				vo.setSolarNavigation(dsVO.getSolarNavigation());
				vo.setSolarStakeNumber(dsVO.getSolarStakeNumber());
				vo.setBatteryCapacity(dsVO.getBatteryCapacity());
				deviceSolars.remove(dsVO);
				break;
			}
		}
	}

	/**
	 * 判断车检器是否关联太阳能电池
	 * 
	 * @param deviceSolars
	 *            太阳能电池关联的设备列表
	 * @param vd
	 *            摄像头
	 * @param vo
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:28:49
	 */
	public void setVehicleSolar(List<DeviceSolarVO> deviceSolars,
			VehicleDetector vd, AuthDeviceVO vo) {
		for (DeviceSolarVO dsVO : deviceSolars) {
			if (vd.getId().equals(dsVO.getDeviceId())) {
				vo.setSolarName(dsVO.getSolarName());
				vo.setSolarSN(dsVO.getSolarSN());
				vo.setSolarNavigation(dsVO.getSolarNavigation());
				vo.setSolarStakeNumber(dsVO.getSolarStakeNumber());
				vo.setBatteryCapacity(dsVO.getBatteryCapacity());
				deviceSolars.remove(dsVO);
				break;
			}
		}
	}

	private void setVehicleSolar(List<DeviceSolarVO> deviceSolars,
			VehicleDetector vd, UserResourceVO vo) {
		for (DeviceSolarVO dsVO : deviceSolars) {
			if (vd.getId().equals(dsVO.getDeviceId())) {
				vo.setSolarName(dsVO.getSolarName());
				vo.setSolarSN(dsVO.getSolarSN());
				vo.setSolarNavigation(dsVO.getSolarNavigation());
				vo.setSolarStakeNumber(dsVO.getSolarStakeNumber());
				vo.setBatteryCapacity(dsVO.getBatteryCapacity());
				deviceSolars.remove(dsVO);
				break;
			}
		}
	}

	@Override
	public int countOrganVD(String organId, String solarId, String name) {
		return solarDeviceDAO.countOrganVD(organId, solarId, name);
	}

	@Override
	public List<DevicePermissionVO> listOrganVD(String organId, String solarId,
			String name, int startIndex, int limit) {
		return solarDeviceDAO.listOrganVD(organId, solarId, name, startIndex,
				limit);
	}

	@Override
	public List<ListCameraVO> getNearCamera(String stakeNumber,
			String navigation, String organId) {
		List<ListCameraVO> vo = new ArrayList<ListCameraVO>();
		String organIds[] = organDAO.findOrgansByOrganId(organId);
		List<Camera> list = cameraDAO.listNearCamera(organIds, navigation,
				stakeNumber);
		float nowLocation = NumberUtil.floatStake(stakeNumber);
		Camera frontCamera = null;
		Camera afterCamera = null;
		for (Camera camera : list) {
			if (frontCamera != null ? NumberUtil.floatStake(frontCamera
					.getStakeNumber()) > NumberUtil.floatStake(camera
					.getStakeNumber())
					&& NumberUtil.floatStake(camera.getStakeNumber()) >= nowLocation
					: NumberUtil.floatStake(camera.getStakeNumber()) > nowLocation) {
				frontCamera = camera;
			}
			if (afterCamera != null ? NumberUtil.floatStake(afterCamera
					.getStakeNumber()) < NumberUtil.floatStake(camera
					.getStakeNumber())
					&& NumberUtil.floatStake(camera.getStakeNumber()) < nowLocation
					: NumberUtil.floatStake(camera.getStakeNumber()) < nowLocation) {
				afterCamera = camera;
			}
		}
		if (frontCamera != null) {
			ListCameraVO frontCameraVO = new ListCameraVO();
			frontCameraVO.setStandardNumber(frontCamera.getStandardNumber());
			frontCameraVO.setStakeNumber(frontCamera.getStakeNumber());
			frontCameraVO.setNavigation(frontCamera.getNavigation());
			frontCameraVO.setId(frontCamera.getId());
			frontCameraVO.setName(frontCamera.getName());
			frontCameraVO.setChannelId(frontCamera.getChannelNumber()
					.toString());
			frontCameraVO.setCreateTime(frontCamera.getCreateTime().toString());
			frontCameraVO.setCenterStorePlan(frontCamera.getProperty()
					.getCenterStorePlan());
			frontCameraVO.setLocalStorePlan(frontCamera.getProperty()
					.getLocalStorePlan());
			frontCameraVO.setMssId(frontCamera.getMss().getId());
			frontCameraVO.setMssName(frontCamera.getMss().getName());
			vo.add(frontCameraVO);
		}
		if (afterCamera != null) {
			ListCameraVO afterCameraVO = new ListCameraVO();
			afterCameraVO.setStandardNumber(afterCamera.getStandardNumber());
			afterCameraVO.setStakeNumber(afterCamera.getStakeNumber());
			afterCameraVO.setNavigation(afterCamera.getNavigation());
			afterCameraVO.setId(afterCamera.getId());
			afterCameraVO.setName(afterCamera.getName());
			afterCameraVO.setChannelId(afterCamera.getChannelNumber()
					.toString());
			afterCameraVO.setCreateTime(afterCamera.getCreateTime().toString());
			afterCameraVO.setCenterStorePlan(afterCamera.getProperty()
					.getCenterStorePlan());
			afterCameraVO.setLocalStorePlan(afterCamera.getProperty()
					.getLocalStorePlan());
			afterCameraVO.setMssId(afterCamera.getMss().getId());
			afterCameraVO.setMssName(afterCamera.getMss().getName());

			vo.add(afterCameraVO);
		}
		return vo;
	}

	@Override
	public String createBoxTransformer(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String maxVoltage, String maxCurrents, String maxCapacity,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<BoxTransformer> list = boxTransformerDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = boxTransformerDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		BoxTransformer boxTransformer = new BoxTransformer();
		boxTransformer.setCreateTime(System.currentTimeMillis());
		boxTransformer.setDas(dasDAO.findById(dasId));
		boxTransformer.setLatitude(latitude);
		boxTransformer.setLongitude(longitude);
		boxTransformer.setName(name);
		boxTransformer.setNote(note);
		boxTransformer.setOrgan(organDAO.findById(organId));
		boxTransformer.setPeriod(period);
		boxTransformer.setStakeNumber(stakeNumber);
		boxTransformer.setStandardNumber(standardNumber);
		boxTransformer.setNavigation(navigation);
		boxTransformer.setMaxVoltage(maxVoltage);
		boxTransformer.setMaxCurrents(maxCurrents);
		boxTransformer.setMaxCapacity(maxCapacity);
		boxTransformer.setReserve(reserve);
		boxTransformer.setIp(ip);
		boxTransformer.setPort(port);
		boxTransformerDAO.save(boxTransformer);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_BT);
		return boxTransformer.getId();
	}

	@Override
	public void updateBoxTransformer(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String maxVoltage,
			String maxCurrents, String maxCapacity, String reserve, String ip,
			Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<BoxTransformer> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = boxTransformerDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = boxTransformerDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		BoxTransformer boxTransformer = boxTransformerDAO.findById(id);
		if (null != name) {
			boxTransformer.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(boxTransformer.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_BT);
			boxTransformer.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			boxTransformer.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			boxTransformer.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			boxTransformer.setPeriod(period);
		}
		if (null != stakeNumber) {
			boxTransformer.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			boxTransformer.setLongitude(longitude);
		}
		if (null != latitude) {
			boxTransformer.setLatitude(latitude);
		}
		if (null != note) {
			boxTransformer.setNote(note);
		}
		if (null != navigation) {
			boxTransformer.setNavigation(navigation);
		}
		if (null != maxVoltage) {
			boxTransformer.setMaxVoltage(maxVoltage);
		}
		if (null != maxCurrents) {
			boxTransformer.setMaxCurrents(maxCurrents);
		}
		if (null != maxCapacity) {
			boxTransformer.setMaxCapacity(maxCapacity);
		}
		if (null != reserve) {
			boxTransformer.setReserve(reserve);
		}
		if (null != ip) {
			boxTransformer.setIp(ip);
		}
		boxTransformer.setPort(port);
		boxTransformerDAO.update(boxTransformer);

	}

	@Override
	public void deleteBoxTransformer(String id) {
		// 删除角色和设备的关联关系
		boxTransformerDAO.deleteRoleBTPermission(id);

		// 同步SN
		BoxTransformer bf = boxTransformerDAO.findById(id);
		syncSN(bf.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_BT);
		boxTransformerDAO.delete(bf);

	}

	@Override
	public GetBoxTransformerVO getBoxTransformer(String id) {
		BoxTransformer bt = boxTransformerDAO.findById(id);
		GetBoxTransformerVO vo = new GetBoxTransformerVO();
		vo.setCreateTime(bt.getCreateTime().toString());
		vo.setDasId(bt.getDas() != null ? bt.getDas().getId() : "");
		vo.setDasName(bt.getDas() != null ? bt.getDas().getName() : "");
		vo.setId(bt.getId());
		vo.setLatitude(bt.getLatitude());
		vo.setLongitude(bt.getLongitude());
		vo.setMaxCapacity(bt.getMaxCapacity());
		vo.setMaxCurrents(bt.getMaxCurrents());
		vo.setMaxVoltage(bt.getMaxVoltage());
		vo.setName(bt.getName());
		vo.setNavigation(bt.getNavigation());
		vo.setNote(bt.getNote());
		vo.setPeriod(bt.getPeriod() + "");
		vo.setReserve(bt.getReserve());
		vo.setStakeNumber(bt.getStakeNumber());
		vo.setStandardNumber(bt.getStandardNumber());
		vo.setIp(bt.getIp());
		vo.setPort(bt.getPort() + "");
		return vo;
	}

	@Override
	public Integer countBoxTransformer(String organId, String name,
			String standardNumber, String stakeNumber) {
		return boxTransformerDAO.countBoxTransformer(organId, name,
				standardNumber, stakeNumber);
	}

	@Override
	public List<GetBoxTransformerVO> listBoxTransformer(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		List<BoxTransformer> list = boxTransformerDAO.listBoxTransformer(
				organId, name, standardNumber, stakeNumber, startIndex, limit);
		List<GetBoxTransformerVO> listVO = new ArrayList<GetBoxTransformerVO>();
		for (BoxTransformer bt : list) {
			GetBoxTransformerVO vo = new GetBoxTransformerVO();
			vo.setCreateTime(bt.getCreateTime().toString());
			vo.setDasId(bt.getDas() != null ? bt.getDas().getId() : "");
			vo.setDasName(bt.getDas() != null ? bt.getDas().getName() : "");
			vo.setId(bt.getId());
			vo.setLatitude(bt.getLatitude());
			vo.setLongitude(bt.getLongitude());
			vo.setMaxCapacity(bt.getMaxCapacity());
			vo.setMaxCurrents(bt.getMaxCurrents());
			vo.setMaxVoltage(bt.getMaxVoltage());
			vo.setName(bt.getName());
			vo.setNavigation(bt.getNavigation());
			vo.setNote(bt.getNote());
			vo.setPeriod(bt.getPeriod() + "");
			vo.setReserve(bt.getReserve());
			vo.setStakeNumber(bt.getStakeNumber());
			vo.setStandardNumber(bt.getStandardNumber());
			vo.setIp(bt.getIp());
			vo.setPort(bt.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String saveDeviceFault(String id, String deviceName,
			String standardNumber, String deviceType, Long detectTime,
			Integer status, String confirmUser, String maintainUser,
			String reason, Long recoverTime, String organId,
			String stakeNumber, String navigation) throws BusinessException {
		DeviceAlarm da = null;
		if (StringUtils.isBlank(id)) {
			da = new DeviceAlarm();
			// 新增默认为未确认状态
			da.setConfirmFlag(Integer.valueOf(0));
			da.setOrganId(organId);
			// 如果SN不为空，查询设备对象
			if (StringUtils.isNotBlank(standardNumber)) {
				if (StringUtils.isBlank(deviceType)) {
					throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
							"missing [DeviceType]");
				}
				// Camera
				if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(deviceType)) {
					Camera device = cameraDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Vd
				if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(deviceType)) {
					VehicleDetector device = vdDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Ws
				if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(deviceType)) {
					WindSpeed device = wsDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Wst
				if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(deviceType)) {
					WeatherStat device = wstDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Loli
				if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(deviceType)) {
					LoLi device = loliDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Fd
				if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(deviceType)) {
					FireDetector device = fireDetectorDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Covi
				if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(deviceType)) {
					Covi device = coviDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Nod
				if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(deviceType)) {
					NoDetector device = noDetectorDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// ControlDevice
				if ((TypeDefinition.DEVICE_TYPE_CMS + "").equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_FAN + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_LIGHT + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_RD + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_WP + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_RAIL + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_IS + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_TSL + "")
								.equals(deviceType)
						|| (TypeDefinition.DEVICE_TYPE_LIL + "")
								.equals(deviceType)) {
					ControlDevice device = controlDeviceDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// Pb
				if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(deviceType)) {
					PushButton device = pushButtonDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// road detector
				if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
						.equals(deviceType)) {
					RoadDetector device = roadDetectorDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// bridge detector
				if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
						.equals(deviceType)) {
					BridgeDetector device = bridgeDetectorDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// vi detector
				if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
						.equals(deviceType)) {
					ViDetector device = viDetectorDAO.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// box transformer
				if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(deviceType)) {
					BoxTransformer device = boxTransformerDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// emergence phone
				if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
						.equals(deviceType)) {
					UrgentPhone device = urgentPhoneDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
				// solar battery
				if ((TypeDefinition.DEVICE_TYPE_SOLAR_BATTERY + "")
						.equals(deviceType)) {
					SolarBattery device = solarBatteryDAO
							.findBySN(standardNumber);
					da.setDeviceId(device.getId());
					da.setStandardNumber(standardNumber);
					da.setDeviceName(device.getName());
					da.setNavigation(device.getNavigation());
					da.setStakeNumber(device.getStakeNumber());
				}
			}
			// 非平台内部的设备
			else {
				da.setDeviceName(deviceName);
				da.setStakeNumber(stakeNumber);
				da.setNavigation(navigation);
			}
			da.setDeviceType(deviceType);
			da.setAlarmTime(detectTime);
			// 保存入库
			deviceAlarmDAO.save(da);
		} else {
			da = deviceAlarmDAO.findById(id);
		}
		if (StringUtils.isNotBlank(reason)) {
			da.setAlarmContent(reason);
		}
		if (null != status) {
			da.setConfirmFlag(status);
		}
		// 状态为1，且原本的confirmTime为空，判定为确认操作
		if (status != null && da.getConfirmTime() == null) {
			da.setConfirmTime(System.currentTimeMillis());
			da.setConfirmUser(confirmUser);
		}
		da.setMaintainUser(maintainUser);
		da.setRecoverTime(recoverTime);
		// 修改时允许修改非平台内部设备的桩号和方向
		if (StringUtils.isBlank(standardNumber)) {
			da.setDeviceName(deviceName);
			da.setStakeNumber(stakeNumber);
			da.setNavigation(navigation);
			da.setStandardNumber("");
		}

		return da.getId();
	}

	@Override
	public String createViDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			Integer visibilityLimit, String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<ViDetector> list = viDetectorDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = viDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		ViDetector viDetector = new ViDetector();
		viDetector.setCreateTime(System.currentTimeMillis());
		viDetector.setDas(dasDAO.findById(dasId));
		viDetector.setLatitude(latitude);
		viDetector.setLongitude(longitude);
		viDetector.setName(name);
		viDetector.setNote(note);
		viDetector.setOrgan(organDAO.findById(organId));
		viDetector.setPeriod(period);
		viDetector.setStakeNumber(stakeNumber);
		viDetector.setStandardNumber(standardNumber);
		viDetector.setNavigation(navigation);
		viDetector.setVisibilityLimit(visibilityLimit);
		viDetector.setReserve(reserve);
		viDetector.setIp(ip);
		viDetector.setPort(port);
		viDetectorDAO.save(viDetector);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_VI_DETECTOR);
		return viDetector.getId();
	}

	@Override
	public void updateViDetector(String id, String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			Integer visibilityLimit, String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<ViDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = viDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = viDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		ViDetector viDetector = viDetectorDAO.findById(id);
		if (null != name) {
			viDetector.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(viDetector.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_VI_DETECTOR);
			viDetector.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			viDetector.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			viDetector.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			viDetector.setPeriod(period);
		}
		if (null != stakeNumber) {
			viDetector.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			viDetector.setLongitude(longitude);
		}
		if (null != latitude) {
			viDetector.setLatitude(latitude);
		}
		if (null != note) {
			viDetector.setNote(note);
		}
		if (null != navigation) {
			viDetector.setNavigation(navigation);
		}
		if (null != visibilityLimit) {
			viDetector.setVisibilityLimit(visibilityLimit);
		}
		if (null != reserve) {
			viDetector.setReserve(reserve);
		}
		if (null != ip) {
			viDetector.setIp(ip);
		}
		viDetector.setPort(port);
		viDetectorDAO.update(viDetector);
	}

	@Override
	public void deleteViDetector(String id) {
		// 删除角色和设备的关联关系
		viDetectorDAO.deleteRoleViDetectorPermission(id);

		// 同步SN
		ViDetector viDetector = viDetectorDAO.findById(id);
		syncSN(viDetector.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_VI_DETECTOR);
		viDetectorDAO.delete(viDetector);
	}

	@Override
	public GetViDetectorVO getViDetector(String id) {
		ViDetector videtector = viDetectorDAO.findById(id);
		GetViDetectorVO vo = new GetViDetectorVO();
		vo.setCreateTime(videtector.getCreateTime().toString());
		vo.setDasId(videtector.getDas() != null ? videtector.getDas().getId()
				: "");
		vo.setDasName(videtector.getDas() != null ? videtector.getDas()
				.getName() : "");
		vo.setId(videtector.getId());
		vo.setLatitude(videtector.getLatitude());
		vo.setLongitude(videtector.getLongitude());
		vo.setName(videtector.getName());
		vo.setNavigation(videtector.getNavigation());
		vo.setNote(videtector.getNote());
		vo.setPeriod(videtector.getPeriod() != null ? videtector.getPeriod()
				.toString() : "");
		vo.setReserve(videtector.getReserve());
		vo.setStakeNumber(videtector.getStakeNumber());
		vo.setStandardNumber(videtector.getStandardNumber());
		vo.setVisibilityLimit(videtector.getVisibilityLimit() != null ? videtector
				.getVisibilityLimit().toString() : "");
		vo.setIp(videtector.getIp());
		vo.setPort(videtector.getPort() + "");
		return vo;
	}

	@Override
	public Integer countViDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return viDetectorDAO.countViDetector(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetViDetectorVO> listViDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<ViDetector> list = viDetectorDAO.listViDetector(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetViDetectorVO> listVO = new ArrayList<GetViDetectorVO>();
		for (ViDetector videtector : list) {
			GetViDetectorVO vo = new GetViDetectorVO();
			vo.setCreateTime(videtector.getCreateTime().toString());
			vo.setDasId(videtector.getDas() != null ? videtector.getDas()
					.getId() : "");
			vo.setDasName(videtector.getDas() != null ? videtector.getDas()
					.getName() : "");
			vo.setId(videtector.getId());
			vo.setLatitude(videtector.getLatitude());
			vo.setLongitude(videtector.getLongitude());
			vo.setName(videtector.getName());
			vo.setNavigation(videtector.getNavigation());
			vo.setNote(videtector.getNote());
			vo.setPeriod(videtector.getPeriod() != null ? videtector
					.getPeriod().toString() : "");
			vo.setReserve(videtector.getReserve());
			vo.setStakeNumber(videtector.getStakeNumber());
			vo.setStandardNumber(videtector.getStandardNumber());
			vo.setVisibilityLimit(videtector.getVisibilityLimit() != null ? videtector
					.getVisibilityLimit().toString() : "");
			vo.setIp(videtector.getIp());
			vo.setPort(videtector.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createRoadDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			Integer roadTemperature, Integer waterThickness, String reserve,
			String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<RoadDetector> list = roadDetectorDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = roadDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		RoadDetector roadDetector = new RoadDetector();
		roadDetector.setCreateTime(System.currentTimeMillis());
		roadDetector.setDas(dasDAO.findById(dasId));
		roadDetector.setLatitude(latitude);
		roadDetector.setLongitude(longitude);
		roadDetector.setName(name);
		roadDetector.setNote(note);
		roadDetector.setOrgan(organDAO.findById(organId));
		roadDetector.setPeriod(period);
		roadDetector.setStakeNumber(stakeNumber);
		roadDetector.setStandardNumber(standardNumber);
		roadDetector.setNavigation(navigation);
		roadDetector.setWaterThickness(waterThickness);
		roadDetector.setRoadTemperature(roadTemperature);
		roadDetector.setReserve(reserve);
		roadDetector.setIp(ip);
		roadDetector.setPort(port);
		roadDetectorDAO.save(roadDetector);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ROAD_DETECTOR);
		return roadDetector.getId();
	}

	@Override
	public void updateRoadDetector(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer roadTemperature, Integer waterThickness, String reserve,
			String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<RoadDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = roadDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = roadDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		RoadDetector roadDetector = roadDetectorDAO.findById(id);
		if (null != name) {
			roadDetector.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(roadDetector.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ROAD_DETECTOR);
			roadDetector.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			roadDetector.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			roadDetector.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			roadDetector.setPeriod(period);
		}
		if (null != stakeNumber) {
			roadDetector.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			roadDetector.setLongitude(longitude);
		}
		if (null != latitude) {
			roadDetector.setLatitude(latitude);
		}
		if (null != note) {
			roadDetector.setNote(note);
		}
		if (null != navigation) {
			roadDetector.setNavigation(navigation);
		}
		if (null != roadTemperature) {
			roadDetector.setRoadTemperature(roadTemperature);
		}
		if (null != waterThickness) {
			roadDetector.setWaterThickness(waterThickness);
		}
		if (null != reserve) {
			roadDetector.setReserve(reserve);
		}
		if (null != ip) {
			roadDetector.setIp(ip);
		}
		roadDetector.setPort(port);
		roadDetectorDAO.update(roadDetector);

	}

	@Override
	public void deleteRoadDetector(String id) {
		// 删除角色和设备的关联关系
		roadDetectorDAO.deleteRoleRoadDetectorPermission(id);

		// 同步SN
		RoadDetector roadDetector = roadDetectorDAO.findById(id);
		syncSN(roadDetector.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_ROAD_DETECTOR);
		roadDetectorDAO.delete(roadDetector);

	}

	@Override
	public GetRoadDetectorVO getRoadDetector(String id) {
		RoadDetector roadDetector = roadDetectorDAO.findById(id);
		GetRoadDetectorVO vo = new GetRoadDetectorVO();
		vo.setDasId(roadDetector.getDas().getId());
		vo.setDasName(roadDetector.getDas().getName());
		vo.setId(roadDetector.getId());
		vo.setLatitude(roadDetector.getLatitude());
		vo.setLongitude(roadDetector.getLongitude());
		vo.setName(roadDetector.getName());
		vo.setNavigation(roadDetector.getNavigation());
		vo.setNote(roadDetector.getNote());
		vo.setPeriod(roadDetector.getPeriod() != null ? roadDetector
				.getPeriod().toString() : "");
		vo.setRoadTemperature(roadDetector.getRoadTemperature() != null ? roadDetector
				.getRoadTemperature().toString() : "");
		vo.setStakeNumber(roadDetector.getStakeNumber());
		vo.setStandardNumber(roadDetector.getStandardNumber());
		vo.setWaterThickness(roadDetector.getWaterThickness() != null ? roadDetector
				.getWaterThickness().toString() : "");
		vo.setReserve(roadDetector.getReserve());
		vo.setIp(roadDetector.getIp());
		vo.setPort(roadDetector.getPort() + "");
		return vo;
	}

	@Override
	public Integer countRoadDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return roadDetectorDAO.countRoadDetector(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetRoadDetectorVO> listRoadDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		List<RoadDetector> rds = roadDetectorDAO.listRoadDetector(organId,
				name, standardNumber, stakeNumber, startIndex, limit);
		List<GetRoadDetectorVO> list = new ArrayList<GetRoadDetectorVO>();
		for (RoadDetector roadDetector : rds) {
			GetRoadDetectorVO vo = new GetRoadDetectorVO();
			vo.setDasId(roadDetector.getDas().getId());
			vo.setDasName(roadDetector.getDas().getName());
			vo.setId(roadDetector.getId());
			vo.setLatitude(roadDetector.getLatitude());
			vo.setLongitude(roadDetector.getLongitude());
			vo.setName(roadDetector.getName());
			vo.setNavigation(roadDetector.getNavigation());
			vo.setNote(roadDetector.getNote());
			vo.setPeriod(roadDetector.getPeriod() != null ? roadDetector
					.getPeriod().toString() : "");
			vo.setRoadTemperature(roadDetector.getRoadTemperature() != null ? roadDetector
					.getRoadTemperature().toString() : "");
			vo.setStakeNumber(roadDetector.getStakeNumber());
			vo.setStandardNumber(roadDetector.getStandardNumber());
			vo.setWaterThickness(roadDetector.getWaterThickness() != null ? roadDetector
					.getWaterThickness().toString() : "");
			vo.setIp(roadDetector.getIp());
			vo.setPort(roadDetector.getPort() + "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createBridgeDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			Integer bridgeTemperature, Integer saltConcentration, Integer mist,
			Integer freezeTemperature, String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<BridgeDetector> list = bridgeDetectorDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = bridgeDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		BridgeDetector bd = new BridgeDetector();
		bd.setCreateTime(System.currentTimeMillis());
		bd.setDas(dasDAO.findById(dasId));
		bd.setLatitude(latitude);
		bd.setLongitude(longitude);
		bd.setName(name);
		bd.setNote(note);
		bd.setOrgan(organDAO.findById(organId));
		bd.setPeriod(period);
		bd.setStakeNumber(stakeNumber);
		bd.setStandardNumber(standardNumber);
		bd.setNavigation(navigation);
		bd.setBridgeTemperature(bridgeTemperature);
		bd.setFreezeTemperature(freezeTemperature);
		bd.setMist(mist);
		bd.setSaltConcentration(saltConcentration);
		bd.setReserve(reserve);
		bd.setIp(ip);
		bd.setPort(port);
		bridgeDetectorDAO.save(bd);

		// 同步SN
		syncSN(null, standardNumber,
				TypeDefinition.RESOURCE_TYPE_BRIDGE_DETECTOR);
		return bd.getId();
	}

	@Override
	public void updateBridgeDetector(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation,
			Integer bridgeTemperature, Integer saltConcentration, Integer mist,
			Integer freezeTemperature, String reserve, String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<BridgeDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = bridgeDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = bridgeDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		BridgeDetector bd = bridgeDetectorDAO.findById(id);
		if (null != name) {
			bd.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			syncSN(bd.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_BRIDGE_DETECTOR);
			bd.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			bd.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			bd.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			bd.setPeriod(period);
		}
		if (null != stakeNumber) {
			bd.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			bd.setLongitude(longitude);
		}
		if (null != latitude) {
			bd.setLatitude(latitude);
		}
		if (null != note) {
			bd.setNote(note);
		}
		if (null != navigation) {
			bd.setNavigation(navigation);
		}
		if (null != bridgeTemperature) {
			bd.setBridgeTemperature(bridgeTemperature);
		}
		if (null != saltConcentration) {
			bd.setSaltConcentration(saltConcentration);
		}
		if (null != mist) {
			bd.setMist(mist);
		}
		if (null != freezeTemperature) {
			bd.setFreezeTemperature(freezeTemperature);
		}
		if (null != reserve) {
			bd.setReserve(reserve);
		}
		if (null != ip) {
			bd.setIp(ip);
		}
		bd.setPort(port);
		bridgeDetectorDAO.update(bd);

	}

	@Override
	public void deleteBridgeDetector(String id) {
		// 删除角色和设备的关联关系
		bridgeDetectorDAO.deleteRoleBridgeDetectorPermission(id);

		// 同步SN
		BridgeDetector bd = bridgeDetectorDAO.findById(id);
		syncSN(bd.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_BRIDGE_DETECTOR);
		bridgeDetectorDAO.delete(bd);
	}

	@Override
	public GetBridgeDetectorVO getBridgeDetector(String id) {
		BridgeDetector bd = bridgeDetectorDAO.findById(id);
		GetBridgeDetectorVO vo = new GetBridgeDetectorVO();
		vo.setDasId(bd.getDas().getId());
		vo.setDasName(bd.getDas().getName());
		vo.setId(bd.getId());
		vo.setLatitude(bd.getLatitude());
		vo.setLongitude(bd.getLongitude());
		vo.setName(bd.getName());
		vo.setNavigation(bd.getNavigation());
		vo.setNote(bd.getNote());
		vo.setPeriod(bd.getPeriod() != null ? bd.getPeriod().toString() : "");
		vo.setBridgeTemperature(bd.getBridgeTemperature() != null ? bd
				.getBridgeTemperature().toString() : "");
		vo.setFreezeTemperature(bd.getFreezeTemperature() != null ? bd
				.getFreezeTemperature().toString() : "");
		vo.setMist(bd.getMist() != null ? bd.getMist().toString() : "");
		vo.setStakeNumber(bd.getStakeNumber());
		vo.setStandardNumber(bd.getStandardNumber());
		vo.setSaltConcentration(bd.getSaltConcentration() != null ? bd
				.getSaltConcentration().toString() : "");
		vo.setReserve(bd.getReserve());
		vo.setIp(bd.getIp());
		vo.setPort(bd.getPort() + "");
		return vo;
	}

	@Override
	public Integer countBridgeDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		return bridgeDetectorDAO.countBridgeDetector(organId, name,
				standardNumber, stakeNumber);
	}

	@Override
	public List<GetBridgeDetectorVO> listBridgeDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		List<BridgeDetector> bds = bridgeDetectorDAO.listBridgeDetector(
				organId, name, standardNumber, stakeNumber, startIndex, limit);
		List<GetBridgeDetectorVO> list = new ArrayList<GetBridgeDetectorVO>();
		for (BridgeDetector bd : bds) {
			GetBridgeDetectorVO vo = new GetBridgeDetectorVO();
			vo.setDasId(bd.getDas().getId());
			vo.setDasName(bd.getDas().getName());
			vo.setId(bd.getId());
			vo.setLatitude(bd.getLatitude());
			vo.setLongitude(bd.getLongitude());
			vo.setName(bd.getName());
			vo.setNavigation(bd.getNavigation());
			vo.setNote(bd.getNote());
			vo.setPeriod(bd.getPeriod() != null ? bd.getPeriod().toString()
					: "");
			vo.setBridgeTemperature(bd.getBridgeTemperature() != null ? bd
					.getBridgeTemperature().toString() : "");
			vo.setFreezeTemperature(bd.getFreezeTemperature() != null ? bd
					.getFreezeTemperature().toString() : "");
			vo.setMist(bd.getMist() != null ? bd.getMist().toString() : "");
			vo.setStakeNumber(bd.getStakeNumber());
			vo.setStandardNumber(bd.getStandardNumber());
			vo.setSaltConcentration(bd.getSaltConcentration() != null ? bd
					.getSaltConcentration().toString() : "");
			vo.setIp(bd.getIp());
			vo.setPort(bd.getPort() + "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createSubVehicleDetector(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, Integer sUpLimit,
			Integer sLowLimit, Integer oUpLimit, Integer oLowLimit,
			Integer vUpLimit, Integer vLowLimit, String note,
			String navigation, String ip, String port, String laneNumber,
			String parentId, String reserve) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<SubVehicleDetector> list = subVehicleDetectorDAO
				.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = subVehicleDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		SubVehicleDetector svd = new SubVehicleDetector();
		svd.setCreateTime(System.currentTimeMillis());
		svd.setDas(dasDAO.findById(dasId));
		svd.setLatitude(latitude);
		svd.setLongitude(longitude);
		svd.setName(name);
		svd.setNote(note);
		svd.setOrgan(organDAO.findById(organId));
		svd.setPeriod(period);
		svd.setStakeNumber(stakeNumber);
		svd.setStandardNumber(standardNumber);
		svd.setsUpLimit(sUpLimit);
		svd.setsLowLimit(sLowLimit);
		svd.setoUpLimit(oUpLimit);
		svd.setoLowLimit(oLowLimit);
		svd.setvUpLimit(vUpLimit);
		svd.setvLowLimit(vLowLimit);
		svd.setNavigation(navigation);
		svd.setIp(ip);
		svd.setPort(port);
		svd.setLaneNumber(laneNumber);
		svd.setParent(vdDAO.findById(parentId));
		svd.setReserve(reserve);

		subVehicleDetectorDAO.save(svd);
		// 同步SN
		// syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_VD);
		return svd.getId();
	}

	@Override
	public void updateSubVehicleDetector(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, Integer sUpLimit, Integer sLowLimit,
			Integer oUpLimit, Integer oLowLimit, Integer vUpLimit,
			Integer vLowLimit, String note, String navigation, String ip,
			String port, String laneNumber, String parentId, String reserve) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<SubVehicleDetector> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = subVehicleDetectorDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = subVehicleDetectorDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		SubVehicleDetector svd = subVehicleDetectorDAO.findById(id);
		if (null != name) {
			svd.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(svd.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_VD);
			svd.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			svd.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			svd.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			svd.setPeriod(period);
		}
		if (null != stakeNumber) {
			svd.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			svd.setLongitude(longitude);
		}
		if (null != latitude) {
			svd.setLatitude(latitude);
		}
		svd.setsUpLimit(sUpLimit);
		svd.setsLowLimit(sLowLimit);
		svd.setoUpLimit(oUpLimit);
		svd.setoLowLimit(oLowLimit);
		svd.setvUpLimit(vUpLimit);
		svd.setvLowLimit(vLowLimit);
		if (null != note) {
			svd.setNote(note);
		}
		if (null != navigation) {
			svd.setNavigation(navigation);
		}
		if (null != ip) {
			svd.setIp(ip);
		}
		if (null != port) {
			svd.setPort(port);
		}
		if (null != laneNumber) {
			svd.setLaneNumber(laneNumber);
		}
		if (null != parentId) {
			svd.setParent(vdDAO.findById(parentId));
		}
		if (null != reserve) {
			svd.setReserve(reserve);
		}
		subVehicleDetectorDAO.update(svd);

	}

	@Override
	public void deleteSubVehicleDetector(String id) {
		// 同步SN
		// SubVehicleDetector svd = subVehicleDetectorDAO.findById(id);
		// syncSN(svd.getStandardNumber(), null,
		// TypeDefinition.RESOURCE_TYPE_VD);
		subVehicleDetectorDAO.deleteById(id);
	}

	@Override
	public GetVehicleDetectorVO getSubVehicleDetector(String id) {
		SubVehicleDetector svd = subVehicleDetectorDAO.findById(id);
		GetVehicleDetectorVO vo = new GetVehicleDetectorVO();
		vo.setCreateTime(svd.getCreateTime() + "");
		vo.setDasId(svd.getDas() != null ? svd.getDas().getId() : "");
		vo.setDasName(svd.getDas() != null ? svd.getDas().getName() : "");
		vo.setId(svd.getId() + "");
		vo.setLatitude(svd.getLatitude());
		vo.setLongitude(svd.getLongitude());
		vo.setName(svd.getName());
		vo.setNote(svd.getNote());
		vo.setOrganId(svd.getOrgan() != null ? svd.getOrgan().getId() : "");
		vo.setPeriod(svd.getPeriod() + "");
		vo.setReserve(svd.getReserve());
		vo.setStakeNumber(svd.getStakeNumber());
		vo.setStandardNumber(svd.getStandardNumber());
		vo.setsUpLimit(svd.getsUpLimit() + "");
		vo.setsLowLimit(svd.getsLowLimit() + "");
		vo.setoUpLimit(svd.getoUpLimit() + "");
		vo.setoLowLimit(svd.getoLowLimit() + "");
		vo.setvUpLimit(svd.getvUpLimit() + "");
		vo.setvLowLimit(svd.getvLowLimit() + "");
		vo.setNavigation(svd.getNavigation());
		vo.setIp(svd.getIp());
		vo.setPort(svd.getPort());
		vo.setLaneNumber(svd.getLaneNumber());
		return vo;
	}

	@Override
	public Integer countSubVehicleDetector(String parentId) {
		return subVehicleDetectorDAO.countSubVehicleDetector(parentId);
	}

	@Override
	public List<GetVehicleDetectorVO> listSubVehicleDetector(String parentId,
			Integer startIndex, Integer limit) {
		List<SubVehicleDetector> list = subVehicleDetectorDAO
				.listSubVehicleDetector(parentId, startIndex, limit);
		List<GetVehicleDetectorVO> listVO = new ArrayList<GetVehicleDetectorVO>();
		for (SubVehicleDetector svd : list) {
			GetVehicleDetectorVO vo = new GetVehicleDetectorVO();
			vo.setCreateTime(svd.getCreateTime() + "");
			vo.setDasId(svd.getDas() != null ? svd.getDas().getId() : "");
			vo.setDasName(svd.getDas() != null ? svd.getDas().getName() : "");
			vo.setId(svd.getId() + "");
			vo.setLatitude(svd.getLatitude());
			vo.setLongitude(svd.getLongitude());
			vo.setName(svd.getName());
			vo.setNote(svd.getNote());
			vo.setOrganId(svd.getOrgan() != null ? svd.getOrgan().getId() : "");
			vo.setPeriod(svd.getPeriod() + "");
			vo.setReserve(svd.getReserve());
			vo.setStakeNumber(svd.getStakeNumber());
			vo.setStandardNumber(svd.getStandardNumber());
			vo.setsUpLimit(svd.getsUpLimit() + "");
			vo.setsLowLimit(svd.getsLowLimit() + "");
			vo.setoUpLimit(svd.getoUpLimit() + "");
			vo.setoLowLimit(svd.getoLowLimit() + "");
			vo.setvUpLimit(svd.getvUpLimit() + "");
			vo.setvLowLimit(svd.getvLowLimit() + "");
			vo.setNavigation(svd.getNavigation());
			vo.setIp(svd.getIp());
			vo.setPort(svd.getPort());
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createUrgentPhone(String name, String standardNumber,
			String dasId, String organId, Integer period, String stakeNumber,
			String longitude, String latitude, String note, String navigation,
			String reserve, String ip, Integer port) {
		// 验证机构
		Organ organ = organDAO.findById(organId);
		if (!organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
				&& !organ.getType().equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			throw new BusinessException(ErrorCode.DETECTOR_MAPPING_ORGAN_ERROR,
					"detector mapping organ [" + organ.getType() + "] is error");
		}

		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<UrgentPhone> list = urgentPhoneDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = urgentPhoneDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		UrgentPhone urgentPhone = new UrgentPhone();
		urgentPhone.setCreateTime(System.currentTimeMillis());
		urgentPhone.setDas(dasDAO.findById(dasId));
		urgentPhone.setLatitude(latitude);
		urgentPhone.setLongitude(longitude);
		urgentPhone.setName(name);
		urgentPhone.setNote(note);
		urgentPhone.setOrgan(organDAO.findById(organId));
		urgentPhone.setPeriod(period);
		urgentPhone.setStakeNumber(stakeNumber);
		urgentPhone.setStandardNumber(standardNumber);
		urgentPhone.setNavigation(navigation);
		urgentPhone.setReserve(reserve);
		urgentPhone.setIp(ip);
		urgentPhone.setPort(port);
		urgentPhoneDAO.save(urgentPhone);

		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UP);
		return urgentPhone.getId();
	}

	@Override
	public void updateUrgentPhone(String id, String name,
			String standardNumber, String dasId, String organId,
			Integer period, String stakeNumber, String longitude,
			String latitude, String note, String navigation, String reserve,
			String ip, Integer port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<UrgentPhone> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = urgentPhoneDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = urgentPhoneDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		UrgentPhone urgentPhone = urgentPhoneDAO.findById(id);
		if (null != name) {
			urgentPhone.setName(name);
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			// 同步SN
			syncSN(urgentPhone.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_UP);
			urgentPhone.setStandardNumber(standardNumber);
		}
		if (null != dasId) {
			urgentPhone.setDas(dasDAO.findById(dasId));
		}
		if (null != organId) {
			urgentPhone.setOrgan(organDAO.findById(organId));
		}
		if (null != period) {
			urgentPhone.setPeriod(period);
		}
		if (null != stakeNumber) {
			urgentPhone.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			urgentPhone.setLongitude(longitude);
		}
		if (null != latitude) {
			urgentPhone.setLatitude(latitude);
		}
		if (null != note) {
			urgentPhone.setNote(note);
		}
		if (null != navigation) {
			urgentPhone.setNavigation(navigation);
		}
		if (null != reserve) {
			urgentPhone.setReserve(reserve);
		}
		if (null != ip) {
			urgentPhone.setIp(ip);
		}
		urgentPhone.setPort(port);
		urgentPhoneDAO.update(urgentPhone);

	}

	@Override
	public void deleteUrgentPhone(String id) {
		// 删除手动报警按钮时删除设备和角色的关系
		urgentPhoneDAO.deleteRoleUPPermission(id);

		// 同步SN
		UrgentPhone urgentPhone = urgentPhoneDAO.findById(id);
		syncSN(urgentPhone.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_UP);
		urgentPhoneDAO.delete(urgentPhone);
	}

	@Override
	public GetUrgentPhoneVO getUrgentPhone(String id) {
		UrgentPhone urgentPhone = urgentPhoneDAO.findById(id);
		GetUrgentPhoneVO vo = new GetUrgentPhoneVO();
		vo.setCreateTime(urgentPhone.getCreateTime() + "");
		vo.setDasId(urgentPhone.getDas() != null ? urgentPhone.getDas().getId()
				: "");
		vo.setDasName(urgentPhone.getDas() != null ? urgentPhone.getDas()
				.getName() : "");
		vo.setId(urgentPhone.getId() + "");
		vo.setLatitude(urgentPhone.getLatitude());
		vo.setLongitude(urgentPhone.getLongitude());
		vo.setName(urgentPhone.getName());
		vo.setNote(urgentPhone.getNote());
		vo.setOrganId(urgentPhone.getOrgan() != null ? urgentPhone.getOrgan()
				.getId() : "");
		vo.setPeriod(urgentPhone.getPeriod() + "");
		vo.setReserve(urgentPhone.getReserve());
		vo.setStakeNumber(urgentPhone.getStakeNumber());
		vo.setStandardNumber(urgentPhone.getStandardNumber());
		vo.setNavigation(urgentPhone.getNavigation());
		vo.setIp(urgentPhone.getIp());
		vo.setPort(urgentPhone.getPort() + "");
		return vo;
	}

	@Override
	public Integer countUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber) {
		return urgentPhoneDAO.countUrgentPhone(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<GetUrgentPhoneVO> listUrgentPhone(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<UrgentPhone> list = urgentPhoneDAO.listUrgentPhone(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<GetUrgentPhoneVO> listVO = new ArrayList<GetUrgentPhoneVO>();
		for (UrgentPhone urgentPhone : list) {
			GetUrgentPhoneVO vo = new GetUrgentPhoneVO();
			vo.setCreateTime(urgentPhone.getCreateTime() + "");
			vo.setDasId(urgentPhone.getDas() != null ? urgentPhone.getDas()
					.getId() : "");
			vo.setDasName(urgentPhone.getDas() != null ? urgentPhone.getDas()
					.getName() : "");
			vo.setId(urgentPhone.getId() + "");
			vo.setLatitude(urgentPhone.getLatitude());
			vo.setLongitude(urgentPhone.getLongitude());
			vo.setName(urgentPhone.getName());
			vo.setNote(urgentPhone.getNote());
			vo.setOrganId(urgentPhone.getOrgan() != null ? urgentPhone
					.getOrgan().getId() : "");
			vo.setPeriod(urgentPhone.getPeriod() + "");
			vo.setReserve(urgentPhone.getReserve());
			vo.setStakeNumber(urgentPhone.getStakeNumber());
			vo.setStandardNumber(urgentPhone.getStandardNumber());
			vo.setNavigation(urgentPhone.getNavigation());
			vo.setIp(urgentPhone.getIp());
			vo.setPort(urgentPhone.getPort() + "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String saveDeviceAlarm(String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber) {
		DeviceAlarm da = new DeviceAlarm();
		da.setDeviceName(deviceName);
		da.setStatus(status);
		da.setAlarmTime(alarmTime);
		da.setConfirmTime(confirmTime);
		da.setAlarmContent(alarmContent);
		da.setAlarmType(alarmType);
		da.setDeviceType(deviceType);
		da.setDeviceId(deviceId);
		da.setOrganId(organId);
		da.setStandardNumber(standardNumber);
		da.setCurrentValue(currentValue);
		da.setThreshold(threshold);
		da.setStakeNumber(stakeNumber);
		deviceAlarmDAO.save(da);
		return da.getId();
	}

	@Override
	public void updateDeviceAlarm(String id, String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber) {
		DeviceAlarm da = deviceAlarmDAO.findById(id);
		if (null != deviceName) {
			da.setDeviceName(deviceName);
		}
		if (null != status) {
			da.setStatus(status);
		}
		if (null != alarmTime) {
			da.setAlarmTime(alarmTime);
		}
		if (null != confirmTime) {
			da.setConfirmTime(confirmTime);
		}
		if (null != alarmContent) {
			da.setAlarmContent(alarmContent);
		}
		if (null != alarmType) {
			da.setAlarmType(alarmType);
		}
		if (null != deviceType) {
			da.setDeviceType(deviceType);
		}
		if (null != deviceId) {
			da.setDeviceId(deviceId);
		}
		if (null != organId) {
			da.setOrganId(organId);
		}
		if (null != standardNumber) {
			da.setStandardNumber(standardNumber);
		}
		if (null != currentValue) {
			da.setCurrentValue(currentValue);
		}
		if (null != threshold) {
			da.setThreshold(threshold);
		}
		if (null != stakeNumber) {
			da.setStakeNumber(stakeNumber);
		}
	}

	@Override
	public Integer deviceAlarmTotalCount(String deviceName, String deviceType,
			String organId, Long beginTime, Long endTime, String alarmType) {
		return deviceAlarmDAO.deviceAlarmTotalCount(deviceName, deviceType,
				organId, beginTime, endTime, alarmType);
	}

	@Override
	public List<DeviceAlarmVO> listDeviceAlarmByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType) {
		List<DeviceAlarm> alarms = deviceAlarmDAO.listDeviceAlarmByOrganId(
				deviceName, deviceType, organId, beginTime, endTime,
				startIndex, limit, alarmType);
		List<DeviceAlarmVO> list = new ArrayList<DeviceAlarmVO>();
		// 统计设备告警次数
		List<DeviceAlarmNumberVO> faultNumbers = deviceAlarmDAO
				.listDeviceAlarmNumber(beginTime, endTime);

		for (DeviceAlarm alarm : alarms) {
			DeviceAlarmVO vo = new DeviceAlarmVO();
			vo.setAlarmContent(alarm.getAlarmContent());
			vo.setAlarmTime(alarm.getAlarmTime() != null ? alarm.getAlarmTime()
					.toString() : "");
			vo.setAlarmType(alarm.getAlarmType());
			vo.setConfirmTime(alarm.getConfirmTime() != null ? alarm
					.getConfirmTime().toString() : "");
			vo.setCurrentValue(alarm.getCurrentValue());
			vo.setDeviceId(alarm.getDeviceId());
			vo.setDeviceName(alarm.getDeviceName());
			vo.setDeviceType(alarm.getDeviceType());
			vo.setId(alarm.getId());
			vo.setOrganId(alarm.getOrganId());
			vo.setStakeNumber(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(alarm.getStatus());
			vo.setThreshold(alarm.getThreshold());
			vo.setConfirmFlag(alarm.getConfirmFlag() != null ? alarm
					.getConfirmFlag().toString() : "");
			vo.setOrganName(organDAO.findById(alarm.getOrganId()).getName());
			vo.setRecoverTime(alarm.getRecoverTime() != null ? alarm
					.getRecoverTime().toString() : "");
			for (DeviceAlarmNumberVO faultNumber : faultNumbers) {
				if (faultNumber.getStandardNumber().equals(
						alarm.getStandardNumber())) {
					vo.setFaultNumber(faultNumber.getFaultNumber() + "");
					break;
				}
			}
			list.add(vo);
		}
		return list;
	}

	@Override
	public void batchSaveDeviceAlarm(List<DeviceAlarmReal> list)
			throws BusinessException {
		// 当前存在的所有未处理报警信息
		List<DeviceAlarmReal> exists = deviceAlarmRealDAO.listUseLock();
		// 需要批量插入的未处理报警信息列表
		List<DeviceAlarmReal> filterList = new LinkedList<DeviceAlarmReal>();
		// DVR编号集合
		Set<String> dvrSns = new HashSet<String>();
		// 摄像头编号集合
		Set<String> cameraSns = new HashSet<String>();

		long currentTime = System.currentTimeMillis();

		for (DeviceAlarmReal da : list) {
			// 上线报警=报警恢复
			if (da.getAlarmType().equals(TypeDefinition.ALARM_TYPE_ONLINE)) {
				for (DeviceAlarmReal entity : exists) {
					if (da.getStandardNumber().equals(
							entity.getStandardNumber())) {
						// 移动到历史表中
						DeviceAlarm history = new DeviceAlarm();
						history.setAlarmContent(entity.getAlarmContent());
						history.setAlarmTime(entity.getAlarmTime());
						history.setAlarmType(entity.getAlarmType());
						history.setAlarmTypeName(entity.getAlarmTypeName());
						history.setConfirmFlag(entity.getConfirmFlag());
						history.setConfirmTime(entity.getConfirmTime());
						history.setConfirmUser(entity.getConfirmUser());
						history.setCurrentValue(entity.getCurrentValue());
						history.setDeviceId(entity.getDeviceId());
						history.setDeviceName(entity.getDeviceName());
						history.setDeviceType(entity.getDeviceType());
						history.setEventReference(entity.getEventReference());
						history.setMaintainUser(entity.getMaintainUser());
						history.setNavigation(entity.getNavigation());
						history.setNote("系统自检测恢复");
						history.setOrganId(entity.getOrganId());
						history.setRecordFlag(entity.getRecordFlag());
						history.setRecoverTime(currentTime);
						history.setStakeNumber(entity.getStakeNumber());
						history.setStandardNumber(entity.getStandardNumber());
						history.setStatus(entity.getStatus());
						history.setThreshold(entity.getThreshold());
						deviceAlarmDAO.save(history);

						// 删除该条实时报警记录
						deviceAlarmRealDAO.delete(entity);
					}
				}
			}
			// 其他报警
			else {
				// 该报警在未处理报警列表中是否存在标志
				boolean existFlag = false;

				for (DeviceAlarmReal entity : exists) {
					// 设备编号和报警类型都相同视为同一个报警
					if (da.getStandardNumber().equals(
							entity.getStandardNumber())
							&& da.getAlarmType().equals(entity.getAlarmType())) {
						existFlag = true;
						// 修改更新时间
						entity.setUpdateTime(currentTime);
						break;
					}
				}
				// 不存在，插入新的记录
				if (!existFlag) {
					da.setUpdateTime(currentTime);
					// 摄像头和dvr采取批量插入方式，其他设备直接插入
					// DVR
					if ((TypeDefinition.DEVICE_TYPE_DVR + "").equals(da
							.getDeviceType())) {
						dvrSns.add(da.getStandardNumber());
						filterList.add(da);
					}
					// Camera
					else if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(da
							.getDeviceType())) {
						cameraSns.add(da.getStandardNumber());
						filterList.add(da);
					}
					// 其他的设备报警数据目前不多不需要采用批量插入的方式
					else {
						// Vd
						if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(da
								.getDeviceType())) {
							VehicleDetector device = vdDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Ws
						else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(da
								.getDeviceType())) {
							WindSpeed device = wsDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Wst
						else if ((TypeDefinition.DEVICE_TYPE_WST + "")
								.equals(da.getDeviceType())) {
							WeatherStat device = wstDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Loli
						else if ((TypeDefinition.DEVICE_TYPE_LOLI + "")
								.equals(da.getDeviceType())) {
							LoLi device = loliDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Fd
						else if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(da
								.getDeviceType())) {
							FireDetector device = fireDetectorDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Covi
						else if ((TypeDefinition.DEVICE_TYPE_COVI + "")
								.equals(da.getDeviceType())) {
							Covi device = coviDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Nod
						else if ((TypeDefinition.DEVICE_TYPE_NOD + "")
								.equals(da.getDeviceType())) {
							NoDetector device = noDetectorDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// ControlDevice
						else if ((TypeDefinition.DEVICE_TYPE_CMS + "")
								.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_FAN + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_LIGHT + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_RD + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_WP + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_RAIL + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_IS + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_TSL + "")
										.equals(da.getDeviceType())
								|| (TypeDefinition.DEVICE_TYPE_LIL + "")
										.equals(da.getDeviceType())) {
							ControlDevice device = controlDeviceDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// Pb
						else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(da
								.getDeviceType())) {
							PushButton device = pushButtonDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// road detector
						else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
								.equals(da.getDeviceType())) {
							RoadDetector device = roadDetectorDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// bridge detector
						else if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
								.equals(da.getDeviceType())) {
							BridgeDetector device = bridgeDetectorDAO
									.findBySN(da.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// vi detector
						else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
								.equals(da.getDeviceType())) {
							ViDetector device = viDetectorDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// box transformer
						else if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(da
								.getDeviceType())) {
							BoxTransformer device = boxTransformerDAO
									.findBySN(da.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// emergence phone
						else if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
								.equals(da.getDeviceType())) {
							UrgentPhone device = urgentPhoneDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						// solar battery
						else if ((TypeDefinition.DEVICE_TYPE_SOLAR_BATTERY + "")
								.equals(da.getDeviceType())) {
							SolarBattery device = solarBatteryDAO.findBySN(da
									.getStandardNumber());
							da.setDeviceId(device.getId());
							da.setDeviceName(device.getName());
							da.setNavigation(device.getNavigation());
							da.setStakeNumber(device.getStakeNumber());
							da.setOrganId(device.getOrgan().getId());
						}
						deviceAlarmRealDAO.save(da);
					}
				}
			}
		}

		Map<String, Dvr> dvrMap = null;
		Map<String, Camera> cameraMap = null;
		// 查询设备信息
		if (dvrSns.size() > 0) {
			dvrMap = dvrDAO.mapBySns(dvrSns);
		}
		if (cameraSns.size() > 0) {
			cameraMap = cameraDAO.mapBySns(cameraSns);
		}
		// 最终需要批量入库的报警信息
		List<DeviceAlarmReal> insertList = new LinkedList<DeviceAlarmReal>();
		for (DeviceAlarmReal da : filterList) {
			if ((TypeDefinition.DEVICE_TYPE_DVR + "")
					.equals(da.getDeviceType())) {
				Dvr device = dvrMap.get(da.getStandardNumber());
				if (null == device) {
					continue;
				}
				da.setDeviceId(device.getId());
				da.setDeviceName(device.getName());
				da.setNavigation(device.getNavigation());
				da.setStakeNumber(device.getStakeNumber());
				da.setOrganId(device.getOrgan().getId());
				insertList.add(da);
			} else if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(da
					.getDeviceType())) {
				Camera device = cameraMap.get(da.getStandardNumber());
				if (null == device) {
					continue;
				}
				da.setDeviceId(device.getId());
				da.setDeviceName(device.getName());
				da.setNavigation(device.getNavigation());
				da.setStakeNumber(device.getStakeNumber());
				da.setOrganId(device.getOrgan().getId());
				insertList.add(da);
			}
		}
		// 批量插入
		if (insertList.size() > 0) {
			deviceAlarmRealDAO.batchInsert(insertList);
		}
	}

	@Override
	public void updateDeviceOnline(List<DeviceAlarmReal> list)
			throws BusinessException {
		// 要更新的sn列表
		Set<String> sns = new HashSet<String>();
		for (DeviceAlarmReal da : list) {
			sns.add(da.getStandardNumber());
		}
		// 在线设备记录
		Map<String, DeviceOnlineReal> map = deviceOnlineRealDAO.mapBySns(sns);
		// 需要批量写入的上线设备记录
		List<DeviceOnlineReal> onlineList = new LinkedList<DeviceOnlineReal>();
		// 需要批量写入的下线设备记录
		List<DeviceOnline> offlineList = new LinkedList<DeviceOnline>();

		long currentTime = System.currentTimeMillis();

		for (DeviceAlarmReal da : list) {
			DeviceOnlineReal entity = map.get(da.getStandardNumber());
			// 上线
			if (TypeDefinition.ALARM_TYPE_ONLINE.equals(da.getAlarmType())) {
				// 新增一条记录
				if (null == entity) {
					entity = new DeviceOnlineReal();
					entity.setOnlineTime(currentTime);
					entity.setStandardNumber(da.getStandardNumber());
					entity.setUpdateTime(currentTime);
					onlineList.add(entity);
				}
				// 更新updateTime
				else {
					entity.setUpdateTime(currentTime);
				}
			}
			// 下线
			else if (TypeDefinition.ALARM_TYPE_OFFLINE
					.equals(da.getAlarmType())) {
				// 从上线变为下线
				if (null != entity) {
					DeviceOnline deviceOnline = new DeviceOnline();
					deviceOnline.setOfflineTime(currentTime);
					deviceOnline.setOnlineTime(entity.getOnlineTime());
					deviceOnline.setStandardNumber(da.getStandardNumber());
					offlineList.add(deviceOnline);

					// 移除在线记录
					deviceOnlineRealDAO.delete(entity);
				}
			}
		}
		// 批量插入
		deviceOnlineRealDAO.batchInsert(onlineList);
		deviceOnlineDAO.batchInsert(offlineList);
	}

	@Override
	public long statDeviceOnlineTime(String standardNumber, Long begin, Long end) {
		long total = 0;
		// 查询实时表，查看当前在线时间是否包含在统计时间范围内
		DeviceOnlineReal real = deviceOnlineRealDAO.loadBySn(standardNumber);
		if (null != real) {
			long onlineTime = real.getOnlineTime().longValue();
			if (onlineTime < end.longValue()) {
				// 实时和历史都需要统计
				if (onlineTime >= begin.longValue()) {
					total += end.longValue() - onlineTime;
				}
				// 只需要统计实时
				else {
					total += end.longValue() - begin.longValue();
					return total;
				}
			}
		}
		String sn[] = new String[] { standardNumber };
		// 查询历史表
		List<DeviceOnline> list = deviceOnlineDAO.listDeviceOnline(sn, begin,
				end);
		for (DeviceOnline record : list) {
			long start = record.getOnlineTime().longValue();
			long over = record.getOfflineTime().longValue();
			if (start < begin.longValue()) {
				start = begin.longValue();
			}
			if (over > end.longValue()) {
				over = end.longValue();
			}
			total += over - start;
		}

		return total;
	}

	@Override
	public List<TopRealPlayLog> topRealPlay(String userId, int limit) {
		// 总的top排行
		List<TopRealPlayLog> list = sysLogDAO.topRealPlay();
		// 查找用户权限设备
		List<String> authCamera = null;
		// 是否拥有系统角色
		boolean systemRoleFlag = false;
		User user = userDAO.findById(userId);
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			// 如果存在系统角色，拥有用户机构下的所有设备查看权限,以及下级平台所有设备查看权限
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				systemRoleFlag = true;
				break;
			}
		}
		if (systemRoleFlag) {
			authCamera = cameraDAO.listCameraIdInOrgan(user.getOrgan().getId());
			List<SubPlatformResource> subCameras = subPlatformResourceDAO
					.listCamera(null);
			for (SubPlatformResource subCamera : subCameras) {
				authCamera.add(subCamera.getId());
			}
		} else {
			authCamera = new LinkedList<String>();
			List<String> resourceType = Arrays.asList("2", "300");
			for (Role role : roles) {
				authCamera.addAll(roleResourcePermissionDAO.listRoleResourceId(
						role.getId(), resourceType));
			}
		}

		// 返回结果
		List<TopRealPlayLog> rtn = new LinkedList<TopRealPlayLog>();
		// 过滤权限设备
		int count = 0;
		for (TopRealPlayLog top : list) {
			if (authCamera.contains(top.getId())) {
				rtn.add(top);
				count++;
				if (count >= limit) {
					break;
				}
			}
		}

		return rtn;
	}

	@Override
	public Integer organTotalCount(String organId) {
		String[] organs = organDAO.findOrgansByOrganId(organId);
		return organs.length;
	}

	@Override
	public List<OrganDeviceOnline> listOrganDeviceOnline(String organId,
			Integer startIndex, Integer limit) {
		List<OrganDeviceOnline> list = new ArrayList<OrganDeviceOnline>();
		List<Organ> organs = organDAO.listOrganById(organId);
		// 分页后的实际organ,查询dvr时需要
		String[] organReals;
		// 分页后实际的organ对象,最后机构名称set时需要
		List<Organ> listOrgan = new ArrayList<Organ>();
		int n = 0;
		// 如果最后一页，需特殊处理
		if ((startIndex + limit - organs.size()) < 10
				&& (startIndex + limit - organs.size()) > 0) {
			organReals = new String[organs.size() - startIndex];
			for (int i = startIndex; i < organs.size(); i++) {
				organReals[n] = organs.get(i).getId();
				listOrgan.add(organs.get(i));
				n++;
			}
			// 正常处理
		} else {
			organReals = new String[limit];
			for (int i = startIndex; i < startIndex + limit; i++) {
				organReals[n] = organs.get(i).getId();
				listOrgan.add(organs.get(i));
				n++;
			}
		}
		// 机构以及子机构下所有设备
		List<Camera> cameras = cameraDAO.listCameraByOrganIds(organReals);

		// 所有在线设备
		List<DeviceOnlineReal> onlineReals = deviceOnlineRealDAO.findAll();

		// 拼凑返回结果,外层循环：实际需要查询的机构实体
		for (Organ organ : listOrgan) {
			// 当前机构下设备数量
			long deviceNumber = 0l;
			// 当前机构下得在线设备数量
			long onlineDeviceNumber = 0l;
			OrganDeviceOnline vo = new OrganDeviceOnline();
			// 2层循环：根据实际机构查询出的所有dvr
			for (Camera camera : cameras) {
				// 如果dvr的机构id和实际机构id相等当前机构就增加一个设备
				if (camera.getOrgan().getId().equals(organ.getId())) {
					deviceNumber += 1;
					// 3层循环：所有在线设备表设备
					for (DeviceOnlineReal onlineDevice : onlineReals) {
						// 在线表设备匹配dvr成功后再匹配在线设备状态统计在线数量
						if (onlineDevice.getStandardNumber().equals(
								camera.getStandardNumber())) {
							onlineDeviceNumber += 1;
							break;
						}
					}
				}
			}
			vo.setDeviceNumber(deviceNumber + "");
			vo.setOnlineDeviceNumber(onlineDeviceNumber + "");
			vo.setOnlineRate(onlineRate(deviceNumber, onlineDeviceNumber));
			vo.setOrganName(organ.getName());
			list.add(vo);
		}
		return list;
	}

	public String onlineRate(long deviceNumber, long onlineDeviceNumber) {
		if (deviceNumber == 0 || onlineDeviceNumber == 0) {
			return "0%";
		} else {
			String rate = ((double) onlineDeviceNumber / deviceNumber) * 100
					+ "";
			return MyStringUtil.cutObject(rate, 2) + "%";
		}
	}

	@Override
	public List<OrganDeviceCheck> listOrganDeviceCheck(String organId,
			Long beginTime, Long endTime, String deviceName,
			Integer startIndex, Integer limit) {
		String organs[] = new String[] { organId };
		List<OrganDeviceCheck> list = new ArrayList<OrganDeviceCheck>();
		List<Camera> cameras = cameraDAO.listCameraByDevice(organs, deviceName,
				null, null, startIndex, limit, null);
		// 查询设备sn
		String deviceSN[] = new String[cameras.size()];
		// 总时间
		long totalTime = endTime - beginTime;
		// 设备SN数组
		for (int i = 0; i < cameras.size(); i++) {
			deviceSN[i] = cameras.get(i).getStandardNumber();
		}
		// 故障设备
		List<DeviceAlarm> deviceAlarms = deviceAlarmDAO.listDeviceAlarm(null,
				deviceName, TypeDefinition.DEVICE_TYPE_CAMERA + "",
				TypeDefinition.ALARM_TYPE_OFFLINE, beginTime, endTime, 0,
				99999, organs);
		// 在线设备
		List<DeviceOnlineReal> deviceOnlineReals = deviceOnlineRealDAO
				.listDeviceOnline(deviceSN);

		for (Camera camera : cameras) {
			// 设备在线时间累加
			long onlineTime = 0;
			// 故障次数累加
			int faultNumber = 0;
			// 设备是否在线
			String isOnline = "1";
			// 设备登录时间
			String loginTime = "";
			// 设备心跳更新时间
			String updateTime = "";
			// 统计在线时间和设备故障次数
			for (DeviceAlarm deviceOnline : deviceAlarms) {
				if (deviceOnline.getStandardNumber().equals(
						camera.getStandardNumber())) {
					// 故障+1
					faultNumber += 1;
				}
			}
			// 如果统计开始时间小于设备创建时间则以设备创建时间为准
			if (beginTime < camera.getCreateTime()) {
				beginTime = camera.getCreateTime();
				totalTime = endTime - beginTime;
			}
			onlineTime = (long) statDeviceOnlineTime(
					camera.getStandardNumber(), beginTime, endTime);
			// 判断设备是否在线
			for (DeviceOnlineReal deviceOnlineReal : deviceOnlineReals) {
				if (deviceOnlineReal.getStandardNumber().equals(
						camera.getStandardNumber())) {
					loginTime = deviceOnlineReal.getOnlineTime() + "";
					updateTime = deviceOnlineReal.getUpdateTime() + "";
					isOnline = "0";
					break;
				}
			}
			OrganDeviceCheck vo = new OrganDeviceCheck();
			vo.setDeviceIp(camera.getParent().getLanIp());
			vo.setDeviceName(camera.getName());
			vo.setFaultNumber(faultNumber + "");
			vo.setIsOnline(isOnline);
			vo.setManufacturerName(camera.getParent().getManufacturer() != null ? camera
					.getParent().getManufacturer().getName()
					: "");
			vo.setOnlineRate(onlineRate(totalTime, onlineTime));
			vo.setStandardNumber(camera.getStandardNumber());
			vo.setPort(camera.getParent().getPort());
			vo.setLoginTime(loginTime);
			vo.setUpdateTime(updateTime);
			vo.setOrganName(camera.getOrgan().getName());
			list.add(vo);
		}

		return list;
	}

	@Override
	public Integer deviceHistoryTotalCount(String standardNumber,
			Long beginTime, Long endTime) {
		return deviceAlarmDAO.deviceHistoryTotalCount(standardNumber,
				beginTime, endTime);
	}

	/**
	 * 转换摄像头类型为内部通用类型
	 * 
	 * @param subType
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-30 下午6:58:42
	 */
	private String translatePtzType(String subType) {
		if (StringUtils.isBlank(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		if (TypeDefinition.GB28181_CAMERA_BALL.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_BALL;
		}
		if (TypeDefinition.GB28181_CAMERA_BOLT.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		if (TypeDefinition.GB28181_CAMERA_BALL_BOLT.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT;
		}
		if (TypeDefinition.GB28181_CAMERA_HALF_BALL.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		return subType;
	}

	@Override
	public List<DeviceOnlineHistroyVO> listDeviceOnlineHistory(
			String standardNumber, Long beginTime, Long endTime,
			Integer startIndex, Integer limit) {
		List<DeviceOnlineHistroyVO> list = new ArrayList<DeviceOnlineHistroyVO>();
		List<DeviceAlarm> deviceOnlines = deviceAlarmDAO.listDeviceAlarmBySN(
				standardNumber, beginTime, endTime, startIndex, limit);
		for (DeviceAlarm deviceOnline : deviceOnlines) {
			DeviceOnlineHistroyVO vo = new DeviceOnlineHistroyVO();
			vo.setOfflineTime(deviceOnline.getAlarmTime() != null ? deviceOnline
					.getAlarmTime().toString() : "");
			vo.setOnlineTime(deviceOnline.getRecoverTime() != null ? deviceOnline
					.getRecoverTime().toString() : "");
			vo.setConfirmUser(deviceOnline.getConfirmUser());
			vo.setConfirmTime(deviceOnline.getConfirmTime() != null ? deviceOnline
					.getConfirmTime().toString() : "");
			vo.setRecoverUser(deviceOnline.getRecoverUser());
			vo.setDeviceName(deviceOnline.getDeviceName());
			vo.setOrganName(organDAO.findById(deviceOnline.getOrganId())
					.getName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void updateAlarmBySN(String deviceName, String status,
			Long alarmTime, Long confirmTime, String alarmContent,
			String alarmType, String deviceType, String deviceId,
			String organId, String standardNumber, String currentValue,
			String threshold, String stakeNumber, String confirmUser,
			String maintainUser) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		params.put("alarmType", alarmType);
		params.put("alarmTime", alarmTime);
		List<DeviceAlarmReal> deviceAlarms = deviceAlarmRealDAO
				.findByPropertys(params);
		// 实时表没有查到数据则去历史表查询
		if (deviceAlarms.size() <= 0 || null == deviceAlarms) {
			List<DeviceAlarm> deviceAlarmHistorys = deviceAlarmDAO
					.findByPropertys(params);
			// 历史表也没有数据则抛异常
			if (deviceAlarmHistorys.size() <= 0 || null == deviceAlarmHistorys) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"standardNumber:" + standardNumber + " and alarmTime:"
								+ alarmTime + " is error");
			} else {
				DeviceAlarm da = deviceAlarmHistorys.get(0);
				if (null != deviceName) {
					da.setDeviceName(deviceName);
				}
				if (null != status) {
					da.setStatus(status);
				}
				if (null != alarmTime) {
					da.setAlarmTime(alarmTime);
				}
				if (null != confirmTime) {
					da.setConfirmTime(confirmTime);
				}
				if (null != alarmContent) {
					da.setAlarmContent(alarmContent);
				}
				if (null != alarmType) {
					da.setAlarmType(alarmType);
				}
				if (null != deviceType) {
					da.setDeviceType(deviceType);
				}
				if (null != deviceId) {
					da.setDeviceId(deviceId);
				}
				if (null != organId) {
					da.setOrganId(organId);
				}
				if (null != standardNumber) {
					da.setStandardNumber(standardNumber);
				}
				if (null != currentValue) {
					da.setCurrentValue(currentValue);
				}
				if (null != threshold) {
					da.setThreshold(threshold);
				}
				if (null != stakeNumber) {
					da.setStakeNumber(stakeNumber);
				}
				if (null != confirmUser) {
					da.setConfirmUser(confirmUser);
				}
				if (null != maintainUser) {
					da.setMaintainUser(maintainUser);
				}
			}
		} else {
			DeviceAlarmReal da = deviceAlarms.get(0);
			if (null != deviceName) {
				da.setDeviceName(deviceName);
			}
			if (null != status) {
				da.setStatus(status);
			}
			if (null != alarmTime) {
				da.setAlarmTime(alarmTime);
			}
			if (null != confirmTime) {
				da.setConfirmTime(confirmTime);
			}
			if (null != alarmContent) {
				da.setAlarmContent(alarmContent);
			}
			if (null != alarmType) {
				da.setAlarmType(alarmType);
			}
			if (null != deviceType) {
				da.setDeviceType(deviceType);
			}
			if (null != deviceId) {
				da.setDeviceId(deviceId);
			}
			if (null != organId) {
				da.setOrganId(organId);
			}
			if (null != standardNumber) {
				da.setStandardNumber(standardNumber);
			}
			if (null != currentValue) {
				da.setCurrentValue(currentValue);
			}
			if (null != threshold) {
				da.setThreshold(threshold);
			}
			if (null != stakeNumber) {
				da.setStakeNumber(stakeNumber);
			}
			if (null != confirmUser) {
				da.setConfirmUser(confirmUser);
			}
			if (null != maintainUser) {
				da.setMaintainUser(maintainUser);
			}
		}
	}

	@Override
	public Integer deviceAlarmRealTotalCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType) {
		return deviceAlarmRealDAO.deviceAlarmRealTotalCount(deviceName,
				deviceType, organId, beginTime, endTime, alarmType);
	}

	@Override
	public List<DeviceAlarmVO> listDeviceAlarmRealByOrganId(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			Integer startIndex, Integer limit, String alarmType) {
		long begin = System.currentTimeMillis();
		List<DeviceAlarmReal> alarms = deviceAlarmRealDAO.listDeviceAlarmReal(
				deviceName, deviceType, organId, beginTime, endTime,
				startIndex, limit, alarmType);
		System.out.println("listDeviceAlarmReal结束:"
				+ (System.currentTimeMillis() - begin));
		List<DeviceAlarmVO> list = new ArrayList<DeviceAlarmVO>();
		for (DeviceAlarmReal alarm : alarms) {
			DeviceAlarmVO vo = new DeviceAlarmVO();
			vo.setAlarmContent(alarm.getAlarmContent());
			vo.setAlarmTime(alarm.getAlarmTime() != null ? alarm.getAlarmTime()
					.toString() : "");
			vo.setAlarmType(alarm.getAlarmType());
			vo.setConfirmTime(alarm.getConfirmTime() != null ? alarm
					.getConfirmTime().toString() : "");
			vo.setCurrentValue(alarm.getCurrentValue());
			vo.setDeviceId(alarm.getDeviceId());
			vo.setDeviceName(alarm.getDeviceName());
			vo.setDeviceType(alarm.getDeviceType());
			vo.setId(alarm.getId());
			vo.setOrganId(alarm.getOrganId());
			vo.setStakeNumber(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(alarm.getStatus());
			vo.setThreshold(alarm.getThreshold());
			vo.setConfirmFlag(alarm.getConfirmFlag() != null ? alarm
					.getConfirmFlag().toString() : "");
			vo.setConfirmUser(alarm.getConfirmUser());
			list.add(vo);
		}

		begin = System.currentTimeMillis();
		// 查询告警历史表，如果为确认状态为null则查询出来
		List<DeviceAlarm> alarmHistory = deviceAlarmDAO.listFlagNull(
				deviceName, deviceType, organId, beginTime, endTime,
				startIndex, limit, alarmType);
		System.out.println("listFlagNull结束:"
				+ (System.currentTimeMillis() - begin));
		for (DeviceAlarm alarm : alarmHistory) {
			DeviceAlarmVO vo = new DeviceAlarmVO();
			vo.setAlarmContent(alarm.getAlarmContent());
			vo.setAlarmTime(alarm.getAlarmTime() != null ? alarm.getAlarmTime()
					.toString() : "");
			vo.setAlarmType(alarm.getAlarmType());
			vo.setConfirmTime(alarm.getConfirmTime() != null ? alarm
					.getConfirmTime().toString() : "");
			vo.setCurrentValue(alarm.getCurrentValue());
			vo.setDeviceId(alarm.getDeviceId());
			vo.setDeviceName(alarm.getDeviceName());
			vo.setDeviceType(alarm.getDeviceType());
			vo.setId(alarm.getId());
			vo.setOrganId(alarm.getOrganId());
			vo.setStakeNumber(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(alarm.getStatus());
			vo.setThreshold(alarm.getThreshold());
			vo.setConfirmFlag(alarm.getConfirmFlag() != null ? alarm
					.getConfirmFlag().toString() : "");
			vo.setConfirmUser(alarm.getConfirmUser());
			list.add(vo);
		}
		return list;
	}

	@Override
	public CountDeviceDTO countDeviceOnline(Long beginTime, Long endTime,
			String organId, String deviceType, String alarmType) {
		// 返回对象
		CountDeviceDTO dto = new CountDeviceDTO();
		List<CountDataTypeVO> deviceCounts = new ArrayList<CountDataTypeVO>();
		CountDataTypeVO deviceAll = new CountDataTypeVO();
		CountDataTypeVO deviceOnline = new CountDataTypeVO();
		CountDataTypeVO faultNumbers = new CountDataTypeVO();

		// 当前机构以及子机构对象
		List<Organ> organs = organDAO.listOrganById(organId);

		// 当前机构以及子机构数组（用于查询Dvr）
		String[] organReals = organDAO.findOrgansByOrganId(organId);

		// 机构以及子机构下所有设备
		List<Camera> cameras = cameraDAO.listCameraByDevice(organReals, null,
				null, null, 0, 99999, null);

		// 设备SN数组
		String deviceSN[] = new String[cameras.size()];
		for (int i = 0; i < cameras.size(); i++) {
			deviceSN[i] = cameras.get(i).getStandardNumber();
		}

		// 所有在线设备
		List<DeviceOnlineReal> onlineReals = deviceOnlineRealDAO.findAll();

		// 故障设备
		List<DeviceAlarm> deviceAlarms = deviceAlarmDAO.listDeviceAlarm(null,
				null, TypeDefinition.DEVICE_TYPE_CAMERA + "",
				TypeDefinition.ALARM_TYPE_OFFLINE, beginTime, endTime, 0,
				99999, organReals);

		int[] allNumber = new int[organs.size()];
		int[] onlineNumber = new int[organs.size()];
		int[] faultNumber = new int[organs.size()];
		String[] organNames = new String[organs.size()];

		// 拼凑返回结果,外层循环：实际需要查询的机构实体
		for (int i = 0; i < organs.size(); i++) {
			// 当前机构下设备数量
			int deviceNumber = 0;
			// 当前机构下得在线设备数量
			int onlineDeviceNumber = 0;
			// 设备故障次数
			int faultN = 0;
			// 2层循环：根据实际机构查询出的所有dvr
			for (Camera camera : cameras) {
				// 如果dvr的机构id和实际机构id相等当前机构就增加一个设备
				if (camera.getOrgan().getId().equals(organs.get(i).getId())) {
					deviceNumber += 1;
					// 3层循环：所有在线设备表设备
					for (DeviceOnlineReal onlineDevice : onlineReals) {
						// 在线表设备匹配dvr成功后再匹配在线设备状态统计在线数量
						if (onlineDevice.getStandardNumber().equals(
								camera.getStandardNumber())) {
							onlineDeviceNumber += 1;
							break;
						}
					}
					// 统计在线时间和设备故障次数
					for (DeviceAlarm deviceAlarm : deviceAlarms) {
						if (deviceAlarm.getStandardNumber().equals(
								camera.getStandardNumber())) {
							// 故障+1
							faultN += 1;
						}
					}
				}
			}
			allNumber[i] = deviceNumber;
			onlineNumber[i] = onlineDeviceNumber;
			faultNumber[i] = faultN;
			organNames[i] = organs.get(i).getName();
		}

		deviceAll.setData(allNumber);
		deviceAll.setName("所有设备数量");

		deviceOnline.setData(onlineNumber);
		deviceOnline.setName("在线设备数量");

		faultNumbers.setData(faultNumber);
		faultNumbers.setName("设备故障次数");

		deviceCounts.add(deviceAll);
		deviceCounts.add(deviceOnline);
		deviceCounts.add(faultNumbers);

		dto.setOrganName(organNames);
		dto.setDeviceCount(deviceCounts);
		return dto;
	}

	@Override
	public Integer deviceCheckTotalCount(String alarmType, String deviceName,
			String[] organs) {
		Integer total = 0;
		List<Camera> cameras = cameraDAO.listCameraByDevice(organs, deviceName,
				null, null, 0, 99999, null);
		// 查询设备sn
		String deviceSN[] = new String[cameras.size()];
		// 设备SN数组
		for (int i = 0; i < cameras.size(); i++) {
			deviceSN[i] = cameras.get(i).getStandardNumber();
		}
		if (StringUtils.isNotBlank(alarmType)) {
			// 在线设备
			List<DeviceOnlineReal> deviceOnlineReals = deviceOnlineRealDAO
					.listDeviceOnline(deviceSN);
			if (alarmType.equals(TypeDefinition.ALARM_TYPE_OFFLINE)) {
				total = cameras.size() - deviceOnlineReals.size();
			} else if (alarmType.equals(TypeDefinition.ALARM_TYPE_ONLINE)) {
				total = deviceOnlineReals.size();
			} else {
				total = cameras.size();
			}
		} else {
			total = cameras.size();
		}
		return total;
	}

	@Override
	public List<Element> listDeviceStatus(List<String> sns) {
		// 返回结果
		List<Element> rtn = new LinkedList<Element>();
		// 本级查询
		List<String> cameras = cameraDAO.listCameraSN();
		List<DeviceOnlineReal> deviceOnlineReals = deviceOnlineRealDAO
				.findAll();

		// 在线的本级列表
		List<String> onlines = new LinkedList<String>();
		for (DeviceOnlineReal online : deviceOnlineReals) {
			onlines.add(online.getStandardNumber());
		}

		// 下级平台SN列表
		List<String> subCameras = new LinkedList<String>();

		for (String sn : sns) {
			// 本级设备
			if (cameras.contains(sn)) {
				Element device = new Element("Device");
				device.setAttribute("StandardNumber", sn);
				device.setAttribute("Status", onlines.contains(sn) ? "1" : "0");
				rtn.add(device);
			}
			// 下级设备
			else {
				subCameras.add(sn);
			}
		}
		// 存在下级设备
		if (subCameras.size() > 0) {
			// 待查询的下级平台列表, key-下级平台SN, value-设备SN列表
			Map<String, List<String>> requestMap = new HashMap<String, List<String>>();
			// 所有下级资源, key-资源SN, value-资源对象
			Map<String, SubPlatformResource> map = subPlatformResourceDAO
					.mapSubPlatformResource(null);
			// 存放所有的根机构, key-根机构ID, value-根机构对象
			Map<String, SubPlatformResource> rootMap = new HashMap<String, SubPlatformResource>();

			for (String sn : subCameras) {
				SubPlatformResource resource = map.get(sn);
				if (null == resource) {
					System.out.println("Device[" + sn + "] not find !");
					continue;
				}
				// 获取资源跟机构
				String path = resource.getPath();
				String rootId = path.substring(1);
				rootId = rootId.substring(0, rootId.indexOf("/"));
				SubPlatformResource root = map.get(rootId);
				if (null == root) {
					root = subPlatformResourceDAO.loadEntity(rootId);
					if (null == root) {
						System.out
								.println("Device[" + sn + "] root not find !");
						continue;
					}
					rootMap.put(rootId, root);
				}

				// 添加资源到对应的根机构下
				List<String> list = requestMap.get(root.getStandardNumber());
				if (null == list) {
					list = new LinkedList<String>();
					requestMap.put(root.getStandardNumber(), list);
				}
				list.add(sn);
			}

			if (requestMap.keySet().size() > 0) {
				ExecutorService executor = Executors
						.newFixedThreadPool(requestMap.keySet().size());
				Future<List<Element>>[] futures = new Future[requestMap
						.keySet().size()];
				int index = 0;
				// 向所有需要查询的下级机构分别发起请求
				for (final String subSn : requestMap.keySet()) {
					final List<String> devices = requestMap.get(subSn);
					Callable<List<Element>> callable = new Callable<List<Element>>() {
						@Override
						public List<Element> call() throws Exception {
							return requestSubDeviceStatus(subSn, devices);
						}
					};
					Future<List<Element>> future = executor.submit(callable);
					futures[index++] = future;
				}
				// 各下级中心的返回加入到返回列表中
				for (Future<List<Element>> future : futures) {
					try {
						List<Element> devices = future.get();
						int length = devices.size();
						// 每次detach(), devices的元素就会减少一个
						for (int i = 0; i < length; i++) {
							Element e = (Element) devices.get(0).detach();
							rtn.add(e);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
				// 释放线程资源
				executor.shutdown();
			}
		}

		return rtn;
	}

	private List<Element> requestSubDeviceStatus(String subSn,
			List<String> devices) {
		String url = Configuration.getInstance().getProperties(subSn);
		// url不能为空
		if (StringUtils.isBlank(url)) {
			System.out.println("Sub platform[" + subSn
					+ "] not in config.properties !");
			return new LinkedList<Element>();
		}
		// url必须是IP:Port格式
		String[] address = url.split(":");
		if (address.length != 2) {
			System.out.println("Sub platform[" + subSn + "] config url[" + url
					+ "] is invalid !");
			return new LinkedList<Element>();
		}
		// 判断是否自身循环请求
		if (isSelfAddress(address[0])) {
			System.out.println("Sub platform[" + subSn
					+ "] address can not be my address !");
			return new LinkedList<Element>();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<Request Method=\"List_Device_Status\" Cmd=\"1017\">");
		sb.append(System.getProperty("line.separator"));
		for (String sn : devices) {
			sb.append("  <Device StandardNumber=\"");
			sb.append(sn);
			sb.append("\" />");
			sb.append(System.getProperty("line.separator"));
		}
		sb.append("</Request>");
		String body = sb.toString();
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(20000);
		PostMethod method = new PostMethod("http://" + url
				+ "/cms/list_device_status.xml");
		System.out.println("Send request to " + url + " body is :");
		System.out.println(body);
		try {
			RequestEntity entity = new StringRequestEntity(body,
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);
			// 返回
			SAXBuilder builder = new SAXBuilder();
			Document respDoc = builder.build(method.getResponseBodyAsStream());
			String code = respDoc.getRootElement().getAttributeValue("Code");
			if (ErrorCode.SUCCESS.equals(code)) {
				List<Element> list = respDoc.getRootElement().getChildren(
						"Device");
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return new LinkedList<Element>();
	}

	private boolean isSelfAddress(String ip) {
		String[] ips = NetworkUtil.getAllCidrIP();
		for (String myIp : ips) {
			if (myIp.startsWith(ip)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<CameraStatusVO> listLocalCameraStatus() {
		return cameraDAO.listLocalCameraStatus();
	}

	@Override
	public String createGPSDevice(String name, Integer port,
			String standardNumber, String location, String dasId,
			String deviceModelId, String manufacturerId, String ccsId,
			String lanIp, String wanIp, String organId, String stakeNumber,
			String longitude, String latitude, String note, String navigation) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<TmDevice> list = tmDeviceDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		TmDevice device = new TmDevice();
		if (StringUtils.isNotBlank(ccsId)) {
			device.setCcs(ccsDAO.findById(ccsId));
		}
		if (StringUtils.isNotBlank(dasId)) {
			device.setDas(dasDAO.findById(dasId));
		}
		device.setCreateTime(System.currentTimeMillis());
		device.setLanIp(lanIp);
		device.setPort(port);
		device.setLocation(location);
		Manufacturer manufacturer = null;
		if (StringUtils.isNotBlank(manufacturerId)) {
			manufacturer = manufacturerDAO.findById(manufacturerId);
		}
		if (null != manufacturer) {
			device.setManufacturer(manufacturer);
		}
		device.setName(name);
		device.setNote(note);
		device.setStakeNumber(stakeNumber);
		device.setNavigation(navigation);
		device.setOrgan(organDAO.findById(organId));
		device.setStandardNumber(standardNumber);
		device.setType(TypeDefinition.DEVICE_TYPE_GPS + "");
		if (StringUtils.isNotBlank(deviceModelId)) {
			device.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		}
		tmDeviceDAO.save(device);

		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_GPS);
		return device.getId();
	}

	@Override
	public void updateGPSDevice(String id, String name, Integer port,
			String standardNumber, String location, String dasId,
			String deviceModelId, String manufacturerId, String ccsId,
			String lanIp, String wanIp, String organId, String stakeNumber,
			String longitude, String latitude, String note, String navigation) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			List<TmDevice> list = tmDeviceDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		TmDevice device = tmDeviceDAO.findById(id);
		if (null != ccsId) {
			device.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != dasId) {
			device.setDas(dasDAO.findById(dasId));
		}
		if (null != lanIp) {
			device.setLanIp(lanIp);
		}
		if (null != port) {
			device.setPort(port);
		}
		if (null != location) {
			device.setLocation(location);
		}
		if (null != navigation) {
			device.setNavigation(navigation);
		}
		if (null != stakeNumber) {
			device.setStakeNumber(stakeNumber);
		}
		Manufacturer manufacturer = null;
		if (StringUtils.isNotBlank(manufacturerId)) {
			manufacturer = manufacturerDAO.findById(manufacturerId);
		}
		if (null != manufacturer) {
			device.setManufacturer(manufacturer);
		}
		if (null != name) {
			device.setName(name);
		}
		if (null != note) {
			device.setNote(note);
		}
		if (null != organId) {
			device.setOrgan(organDAO.findById(organId));
		}
		if (null != standardNumber) {
			syncSN(device.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_GPS);
			device.setStandardNumber(standardNumber);
		}
		if (StringUtils.isNotBlank(deviceModelId)) {
			device.setDeviceModel(deviceModelDAO.findById(deviceModelId));
		}
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();
	}

	@Override
	public void deleteGPSDevice(String id) {
		TmDevice gpsDevice = tmDeviceDAO.findById(id);
		syncSN(gpsDevice.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_GPS);
		tmDeviceDAO.delete(gpsDevice);
		// 设备更新时间修改
		deviceUpdateTime = System.currentTimeMillis();
	}

	@Override
	public DeviceGPSVO getGPSDevice(String id) {
		TmDevice device = tmDeviceDAO.findById(id);
		DeviceGPSVO vo = new DeviceGPSVO();
		vo.setCcsId(device.getCcs() != null ? device.getCcs().getId() : "");
		vo.setCcsName(device.getCcs() != null ? device.getCcs().getName() : "");
		vo.setDasId(device.getDas() != null ? device.getDas().getId() : "");
		vo.setDasName(device.getDas() != null ? device.getDas().getName() : "");
		vo.setCreateTime(device.getCreateTime() + "");
		vo.setDeviceModelId(device.getDeviceModel() != null ? device
				.getDeviceModel().getId() : "");
		vo.setDeviceModelName(device.getDeviceModel() != null ? device
				.getDeviceModel().getName() : "");
		vo.setId(device.getId());
		vo.setLanIp(device.getLanIp());
		vo.setLatitude(device.getLatitude());
		vo.setLocation(device.getLocation());
		vo.setLongitude(device.getLongitude());
		vo.setManufacturerId(device.getManufacturer() != null ? device
				.getManufacturer().getId() : "");
		vo.setManufacturerName(device.getManufacturer() != null ? device
				.getManufacturer().getName() : "");
		vo.setName(device.getName());
		vo.setNavigation(device.getNavigation());
		vo.setNote(device.getNote());
		vo.setOrganId(device.getOrgan().getId());
		vo.setOrganName(device.getOrgan().getName());
		vo.setPort(device.getPort() != null ? device.getPort().toString() : "");
		vo.setStakeNumber(device.getStakeNumber());
		vo.setStandardNumber(device.getStandardNumber());
		vo.setWanIp(device.getWanIp());
		return vo;
	}

	@Override
	public Integer countGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber) {
		return tmDeviceDAO.countGPSDevice(organId, name, standardNumber,
				stakeNumber);
	}

	@Override
	public List<DeviceGPSVO> listGPSDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		List<TmDevice> devices = tmDeviceDAO.listGPSDevice(organId, name,
				standardNumber, stakeNumber, startIndex, limit);
		List<DeviceGPSVO> list = new ArrayList<DeviceGPSVO>();
		for (TmDevice device : devices) {
			DeviceGPSVO vo = new DeviceGPSVO();
			vo.setCcsId(device.getCcs() != null ? device.getCcs().getId() : "");
			vo.setCcsName(device.getCcs() != null ? device.getCcs().getName()
					: "");
			vo.setDasId(device.getDas() != null ? device.getDas().getId() : "");
			vo.setDasName(device.getDas() != null ? device.getDas().getName()
					: "");
			vo.setCreateTime(device.getCreateTime() + "");
			vo.setDeviceModelId(device.getDeviceModel() != null ? device
					.getDeviceModel().getId() : "");
			vo.setDeviceModelName(device.getDeviceModel() != null ? device
					.getDeviceModel().getName() : "");
			vo.setId(device.getId());
			vo.setLanIp(device.getLanIp());
			vo.setLatitude(device.getLatitude());
			vo.setLocation(device.getLocation());
			vo.setLongitude(device.getLongitude());
			vo.setManufacturerId(device.getManufacturer() != null ? device
					.getManufacturer().getId() : "");
			vo.setManufacturerName(device.getManufacturer() != null ? device
					.getManufacturer().getName() : "");
			vo.setName(device.getName());
			vo.setNavigation(device.getNavigation());
			vo.setNote(device.getNote());
			vo.setOrganId(device.getOrgan().getId());
			vo.setOrganName(device.getOrgan().getName());
			vo.setPort(device.getPort() != null ? device.getPort().toString()
					: "");
			vo.setStakeNumber(device.getStakeNumber());
			vo.setStandardNumber(device.getStandardNumber());
			vo.setWanIp(device.getWanIp());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void bindDeviceGPS(String type, String gpsId, String json) {
		// 解析
		try {
			JSONArray array = JSONArray.fromObject(json);
			// 要删除的角色设备权限的设备ID集合
			String[] deviceIds = new String[array.size()];
			if (deviceIds.length <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"deviceId can not be null !");
			}
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				deviceIds[i] = obj.getString("deviceId");
			}
			// 删除以前的关系
			gpsDeviceDAO.deleteByGPSDevice(gpsId, type, deviceIds);
			TmDevice sb = tmDeviceDAO.findById(gpsId);
			if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (obj.getString("privilege").equals("1")) {
						GPSDeviceCamera sdc = new GPSDeviceCamera();
						sdc.setCamera(cameraDAO.findById(obj
								.getString("deviceId")));
						sdc.setGps(sb);
						gpsDeviceDAO.save(sdc);
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
	public List<DevicePermissionVO> listOrganCameraByGPS(String organId,
			String gpsId, String name, int startIndex, int limit) {
		return gpsDeviceDAO.listOrganCameraByGPS(organId, gpsId, name,
				startIndex, limit);
	}

	@Override
	public void removeGPSDevice(String gpsId) {
		gpsDeviceDAO.removeGPSDevice(gpsId);
	}

	@Override
	public Integer deviceAlarmTotalFlagNullCount(String deviceName,
			String deviceType, String organId, Long beginTime, Long endTime,
			String alarmType) {
		return deviceAlarmDAO.deviceAlarmTotalFlagNullCount(deviceName,
				deviceType, organId, beginTime, endTime, alarmType);
	}

	@Override
	public Integer deviceAlarmRealTotalCount(String deviceId,
			String deviceType, String[] organs, String alarmType) {
		return deviceAlarmRealDAO.deviceAlarmTotalCount(deviceId, deviceType,
				organs, alarmType);
	}

	@Override
	public Integer deviceAlarmTotalFlagNullCount(String deviceId,
			String deviceType, String[] organs, String alarmType) {
		return deviceAlarmDAO.deviceAlarmTotalFlagNullCount(deviceId,
				deviceType, organs, alarmType);
	}

	@Override
	public List<DeviceAlarmVO> listDeviceAlarmRealByOrganId(String deviceId,
			String deviceType, String[] organs, String alarmType) {
		List<DeviceAlarmReal> alarms = deviceAlarmRealDAO.listDeviceAlarmReal(
				deviceId, deviceType, organs, alarmType);
		List<DeviceAlarmVO> list = new ArrayList<DeviceAlarmVO>();
		for (DeviceAlarmReal alarm : alarms) {
			DeviceAlarmVO vo = new DeviceAlarmVO();
			vo.setAlarmContent(alarm.getAlarmContent());
			vo.setAlarmTime(alarm.getAlarmTime() != null ? alarm.getAlarmTime()
					.toString() : "");
			vo.setAlarmType(alarm.getAlarmType());
			vo.setConfirmTime(alarm.getConfirmTime() != null ? alarm
					.getConfirmTime().toString() : "");
			vo.setCurrentValue(alarm.getCurrentValue());
			vo.setDeviceId(alarm.getDeviceId());
			vo.setDeviceName(alarm.getDeviceName());
			vo.setDeviceType(alarm.getDeviceType());
			vo.setId(alarm.getId());
			vo.setOrganId(alarm.getOrganId());
			vo.setStakeNumber(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(alarm.getStatus());
			vo.setThreshold(alarm.getThreshold());
			vo.setConfirmFlag(alarm.getConfirmFlag() != null ? alarm
					.getConfirmFlag().toString() : "");
			vo.setConfirmUser(alarm.getConfirmUser());
			vo.setMaintainUser(alarm.getMaintainUser());
			list.add(vo);
		}
		// 查询告警历史表，如果为确认状态为null则查询出来
		List<DeviceAlarm> alarmHistory = deviceAlarmDAO.listFlagNull(deviceId,
				deviceType, organs, alarmType);
		for (DeviceAlarm alarm : alarmHistory) {
			DeviceAlarmVO vo = new DeviceAlarmVO();
			vo.setAlarmContent(alarm.getAlarmContent());
			vo.setAlarmTime(alarm.getAlarmTime() != null ? alarm.getAlarmTime()
					.toString() : "");
			vo.setAlarmType(alarm.getAlarmType());
			vo.setConfirmTime(alarm.getConfirmTime() != null ? alarm
					.getConfirmTime().toString() : "");
			vo.setCurrentValue(alarm.getCurrentValue());
			vo.setDeviceId(alarm.getDeviceId());
			vo.setDeviceName(alarm.getDeviceName());
			vo.setDeviceType(alarm.getDeviceType());
			vo.setId(alarm.getId());
			vo.setOrganId(alarm.getOrganId());
			vo.setStakeNumber(alarm.getStakeNumber());
			vo.setStandardNumber(alarm.getStandardNumber());
			vo.setStatus(alarm.getStatus());
			vo.setThreshold(alarm.getThreshold());
			vo.setConfirmFlag(alarm.getConfirmFlag() != null ? alarm
					.getConfirmFlag().toString() : "");
			vo.setConfirmUser(alarm.getConfirmUser());
			vo.setMaintainUser(alarm.getMaintainUser());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int[] deviceHistoryTotalCount(String[] organs, String deviceId,
			String deviceType, String alarmType, String type, Long beginTime,
			Long endTime) {
		// 返回全部报警、已确认报警、未确认报警
		int[] totalCount = new int[3];
		// type为null返回全部报警
		totalCount[0] = deviceAlarmDAO.deviceHistoryTotalCount(organs,
				deviceId, deviceType, alarmType, null, beginTime, endTime);
		// type为0返回已确认报警
		totalCount[1] = deviceAlarmDAO.deviceHistoryTotalCount(organs,
				deviceId, deviceType, alarmType,
				TypeDefinition.HISTORY_ALARM_TRUE, beginTime, endTime);
		// type为1返回未确认报警
		totalCount[2] = totalCount[0] - totalCount[1];
		return totalCount;
	}

	@Override
	public List<DeviceOnlineHistroyVO> listDeviceAlarmHistory(String[] organs,
			String deviceId, String deviceType, String alarmType, String type,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		List<DeviceOnlineHistroyVO> list = new ArrayList<DeviceOnlineHistroyVO>();
		List<DeviceAlarm> deviceAlarms = deviceAlarmDAO.listDeviceAlarmHistory(
				organs, deviceId, deviceType, alarmType, type, beginTime,
				endTime, startIndex, limit);
		for (DeviceAlarm deviceAlarm : deviceAlarms) {
			DeviceOnlineHistroyVO vo = new DeviceOnlineHistroyVO();
			vo.setOfflineTime(deviceAlarm.getAlarmTime() != null ? deviceAlarm
					.getAlarmTime().toString() : "");
			vo.setOnlineTime(deviceAlarm.getRecoverTime() != null ? deviceAlarm
					.getRecoverTime().toString() : "");
			vo.setConfirmUser(deviceAlarm.getConfirmUser());
			vo.setConfirmTime(deviceAlarm.getConfirmTime() != null ? deviceAlarm
					.getConfirmTime().toString() : "");
			vo.setRecoverUser(deviceAlarm.getRecoverUser());
			vo.setDeviceName(deviceAlarm.getDeviceName());
			vo.setOrganName(organDAO.findById(deviceAlarm.getOrganId())
					.getName());
			vo.setStatus(StringUtils.isNotBlank(deviceAlarm.getStatus()) ? deviceAlarm
					.getStatus() : TypeDefinition.HISTORY_ALARM_FALSE);// 如果为null则返回1未确认，0已确认
			vo.setMaintainUser(deviceAlarm.getMaintainUser());
			vo.setAlarmContent(deviceAlarm.getAlarmContent());
			vo.setStandardNumber(deviceAlarm.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<DeviceAlarmStatusVO> listDeviceAlarmStatus(String[] organs,
			Long beginTime, Long endTime, Integer startIndex, Integer limit) {
		List<DeviceAlarmStatusVO> list = new ArrayList<DeviceAlarmStatusVO>();
		List<Camera> cameras = cameraDAO.listCameraByDevice(organs, null, null,
				null, startIndex, limit, null);
		// 查询设备sn
		String deviceSN[] = new String[cameras.size()];
		// 总时间
		long totalTime = endTime - beginTime;
		// 设备SN数组
		for (int i = 0; i < cameras.size(); i++) {
			deviceSN[i] = cameras.get(i).getStandardNumber();
		}
		// 故障设备
		// System.out.println("listDeviceAlarm开始时间:"
		// + new Timestamp(System.currentTimeMillis()));
		List<DeviceAlarm> deviceAlarms = deviceAlarmDAO.listDeviceAlarm(null,
				null, TypeDefinition.DEVICE_TYPE_CAMERA + "",
				TypeDefinition.ALARM_TYPE_OFFLINE, beginTime, endTime, 0,
				9999999, organs);
		// 在线设备
		List<DeviceOnlineReal> deviceOnlineReals = deviceOnlineRealDAO
				.listDeviceOnline(deviceSN);
		List<DeviceOnline> deviceOnlines = deviceOnlineDAO.listDeviceOnline(
				deviceSN, beginTime, endTime);
		for (Camera camera : cameras) {
			// 设备在线时间累加
			long onlineTime = 0;
			// 故障次数累加
			int faultNumber = 0;
			// 已确认
			int alarmTrue = 0;
			// 未确认
			int alarmFalse = 0;
			// 设备是否在线
			String isOnline = "1";
			// 统计在线时间和设备故障次数
			// System.out.println("deviceAlarms开始时间:"
			// + new Timestamp(System.currentTimeMillis()));
			for (DeviceAlarm deviceOnline : deviceAlarms) {
				if (deviceOnline.getStandardNumber().equals(
						camera.getStandardNumber())) {
					// 故障+1
					faultNumber += 1;
					if (deviceOnline.getStatus() != null) {
						if (deviceOnline.getStatus().equals(
								TypeDefinition.HISTORY_ALARM_TRUE)) {
							// 已确认次数
							alarmTrue += 1;
						} else {
							// 未确认次数
							alarmFalse += 1;
						}
					} else {
						// 未确认次数
						alarmFalse += 1;
					}
				}
			}
			// 如果统计开始时间小于设备创建时间则以设备创建时间为准
			if (beginTime < camera.getCreateTime()) {
				beginTime = camera.getCreateTime();
				totalTime = endTime - beginTime;
			}
			// 统计每个设备的在线时长
			onlineTime = (long) statDeviceOnlineTime(deviceOnlineReals,
					deviceOnlines, camera.getStandardNumber(), beginTime,
					endTime);
			if (totalTime - onlineTime < 0) {
				System.out.println(millisTohour(totalTime));
				System.out.println(millisTohour(onlineTime));
				onlineTime = (long) statDeviceOnlineTime(deviceOnlineReals,
						deviceOnlines, camera.getStandardNumber(), beginTime,
						endTime);
			}
			// 判断设备是否在线
			for (DeviceOnlineReal deviceOnlineReal : deviceOnlineReals) {
				if (deviceOnlineReal.getStandardNumber().equals(
						camera.getStandardNumber())) {
					isOnline = "0";
					break;
				}
			}
			DeviceAlarmStatusVO vo = new DeviceAlarmStatusVO();
			vo.setDeviceName(camera.getName());
			vo.setAlarmAll(faultNumber + "");
			vo.setIsOnline(isOnline);
			vo.setAlarmFalse(alarmFalse + "");
			vo.setAlarmTrue(alarmTrue + "");
			vo.setOfflineDuration(millisTohour(totalTime - onlineTime > 0 ? totalTime
					- onlineTime
					: totalTime));
			vo.setOrganName(camera.getOrgan().getName());
			vo.setStandardNumber(camera.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	public static String millisTohour(long time) {
		// 取小时数
		long hour = time / (1000l * 60 * 60);
		// 取分钟数
		long minute = (time % (1000l * 60 * 60)) / (1000l * 60);
		// 取秒数
		long second = (time % (1000l * 60 * 60)) % (1000l * 60) / 1000;
		return hour + "小时" + minute + "分钟" + second + "秒";
	}

	@Override
	public List<DeviceOnlineHistroyVO> listAlarmByDevice(String deviceId,
			String alarmType, Long beginTime, Long endTime, Integer startIndex,
			Integer limit) {
		List<DeviceOnlineHistroyVO> list = new ArrayList<DeviceOnlineHistroyVO>();
		List<DeviceAlarm> deviceAlarms = deviceAlarmDAO.listDeviceAlarmHistory(
				null, deviceId, null, alarmType, null, beginTime, endTime,
				startIndex, limit);
		for (DeviceAlarm da : deviceAlarms) {
			DeviceOnlineHistroyVO vo = new DeviceOnlineHistroyVO();
			vo.setAlarmContent(da.getAlarmContent());
			vo.setConfirmTime(da.getConfirmTime() != null ? da.getConfirmTime()
					.toString() : "");
			vo.setConfirmUser(da.getConfirmUser());
			vo.setDeviceName(da.getDeviceName());
			vo.setMaintainUser(da.getMaintainUser());
			vo.setOfflineDuration(millisTohour(da.getRecoverTime()
					- da.getAlarmTime()));
			vo.setOfflineTime(da.getAlarmTime() != null ? da.getAlarmTime()
					+ "" : "");
			vo.setOnlineTime(da.getRecoverTime() != null ? da.getRecoverTime()
					+ "" : "");
			vo.setStatus(StringUtils.isNotBlank(da.getStatus()) ? da
					.getStatus() : TypeDefinition.HISTORY_ALARM_FALSE);
			vo.setStandardNumber(da.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void batchSaveDataDevice(String type, List<Element> device) {
		int i = 0;
		// 光纤光栅按钮
		if (type.equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
			List<FireDetector> fds = new ArrayList<FireDetector>();
			for (Element e : device) {
				FireDetector fd = new FireDetector();
				fd.setCreateTime(System.currentTimeMillis() + i);
				fd.setDas(dasDAO.findById(e.getAttributeValue("DasId")));
				fd.setLatitude(e.getAttributeValue("Latitude"));
				fd.setLongitude(e.getAttributeValue("Longitude"));
				fd.setName(e.getAttributeValue("Name"));
				fd.setNavigation(e.getAttributeValue("Navigation"));
				fd.setPeriod(Integer.parseInt(e.getAttributeValue("Period")));
				fd.setOrgan(organDAO.findById(e.getAttributeValue("OrganId")));
				fd.setStakeNumber(e.getAttributeValue("StakeNumber"));
				fd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				fds.add(fd);
				i++;
			}
			fireDetectorDAO.batchInsert(fds);
		}
		// 手动报警按钮
		else if (type.equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
			List<PushButton> pbs = new ArrayList<PushButton>();
			for (Element e : device) {
				PushButton pb = new PushButton();
				pb.setCreateTime(System.currentTimeMillis() + i);
				pb.setDas(dasDAO.findById(e.getAttributeValue("DasId")));
				pb.setLatitude(e.getAttributeValue("Latitude"));
				pb.setLongitude(e.getAttributeValue("Longitude"));
				pb.setName(e.getAttributeValue("Name"));
				pb.setNavigation(e.getAttributeValue("Navigation"));
				pb.setPeriod(Integer.parseInt(e.getAttributeValue("Period")));
				pb.setOrgan(organDAO.findById(e.getAttributeValue("OrganId")));
				pb.setStakeNumber(e.getAttributeValue("StakeNumber"));
				pb.setStandardNumber(e.getAttributeValue("StandardNumber"));
				pbs.add(pb);
			}
			pushButtonDAO.batchInsert(pbs);
			i++;
		}
		// .......
		else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

	}

	public long statDeviceOnlineTime(List<DeviceOnlineReal> reals,
			List<DeviceOnline> historys, String sn, Long begin, Long end) {
		long total = 0;
		for (DeviceOnlineReal real : reals) {
			if (real.getStandardNumber().equals(sn)) {
				long onlineTime = real.getOnlineTime().longValue();
				if (onlineTime < end.longValue()) {
					// 实时和历史都需要统计
					if (onlineTime >= begin.longValue()) {
						total += end.longValue() - onlineTime;
					}
					// 只需要统计实时
					else {
						total += end.longValue() - begin.longValue();
						return total;
					}
				}
			}
		}
		for (DeviceOnline history : historys) {
			if (history.getStandardNumber().equals(sn)) {
				long start = history.getOnlineTime().longValue();
				long over = history.getOfflineTime().longValue();
				if (start < begin.longValue()) {
					start = begin.longValue();
				}
				if (over > end.longValue()) {
					over = end.longValue();
				}
				total += over - start;
			}
		}
		return total;
	}

	@Override
	public List<Element> listDeviceStatus1(List<String> sns) {
		// 返回结果
		List<Element> rtn = new LinkedList<Element>();
		String[] standardNumbers = new String[sns.size()];
		int i = 0;
		for (String sn : sns) {
			standardNumbers[i] = sn;
			i++;
		}
		List<DeviceOnlineReal> deviceOnlineReals = new ArrayList<DeviceOnlineReal>();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.MYSQL.equals(dbName)) {
			deviceOnlineReals = deviceOnlineRealDAO
					.listDeviceOnline(standardNumbers);
		} else {
			deviceOnlineReals = deviceOnlineRealDAO.findAll();
		}
		for (String sn : sns) {
			boolean flag = true;
			for (DeviceOnlineReal deviceOnlineReal : deviceOnlineReals) {
				if (sn.equals(deviceOnlineReal.getStandardNumber())) {
					Element device = new Element("Device");
					device.setAttribute("StandardNumber",
							deviceOnlineReal.getStandardNumber());
					device.setAttribute("Status", "1");
					rtn.add(device);
					flag = false;
					break;
				}
			}
			if (flag) {
				Element device = new Element("Device");
				device.setAttribute("StandardNumber", sn);
				device.setAttribute("Status", "0");
				rtn.add(device);
			}
		}
		return rtn;
	}

	@Override
	public Map<String, List<Camera>> listOrganCamera(String organId) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		Map<String, List<Camera>> map = new HashMap<String, List<Camera>>();
		List<Camera> cameras = cameraDAO.listCameraByOrganIds(organs);
		for (int i = 0; i < organs.length; i++) {
			List<Camera> list = new ArrayList<Camera>();
			for (Camera camera : cameras) {
				if (organs[i].equals(camera.getOrgan().getId())) {
					list.add(camera);
				}
			}
			map.put(organs[i], list);
		}
		return map;
	}

	@Override
	public ListOrganDeviceTreeVO organDeviceTree(String organId,
			Map<String, List<Camera>> map) {
		List<Organ> organs = organDAO.listOrganById(organId);
		Organ rootOrgan = organDAO.findById(organId);
		ListOrganDeviceTreeVO result = new ListOrganDeviceTreeVO();
		result.setCode(rootOrgan.getStandardNumber());
		result.setText(rootOrgan.getName());
		result.setLeaf(false);
		List<ListOrganDeviceTreeVO> vos = new ArrayList<ListOrganDeviceTreeVO>();
		for (Camera camera : map.get(rootOrgan.getId())) {
			ListOrganDeviceTreeVO vo = new ListOrganDeviceTreeVO();
			vo.setCode(camera.getStandardNumber());
			vo.setText(camera.getName());
			vo.setLeaf(true);
			vos.add(vo);
		}
		// result.setChildren(vos);
		organs.remove(rootOrgan);
		CreateDeviceTree(result, map, organs, rootOrgan.getId(), vos);
		map.clear();
		return result;
	}

	private void CreateDeviceTree(ListOrganDeviceTreeVO result,
			Map<String, List<Camera>> map, List<Organ> organs, String organId,
			List<ListOrganDeviceTreeVO> vos) {
		result.setChildren(vos);
		for (Organ organ : organs) {
			if (organ.getParent().getId().equals(organId)) {
				ListOrganDeviceTreeVO organVO = new ListOrganDeviceTreeVO();
				organVO.setCode(organ.getStandardNumber());
				organVO.setText(organ.getName());
				organVO.setLeaf(false);
				List<ListOrganDeviceTreeVO> voes = new ArrayList<ListOrganDeviceTreeVO>();
				for (Camera camera : map.get(organ.getId())) {
					ListOrganDeviceTreeVO vo = new ListOrganDeviceTreeVO();
					vo.setCode(camera.getStandardNumber());
					vo.setText(camera.getName());
					vo.setLeaf(true);
					voes.add(vo);
				}
				// organVO.setChildren(vos);
				vos.add(organVO);
				CreateDeviceTree(organVO, map, organs, organ.getId(), voes);
			}
		}
	}

	@Override
	public List<Camera> listCameras() {
		return cameraDAO.findAll();
	}
}
