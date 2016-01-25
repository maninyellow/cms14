package com.znsx.cms.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CrsDAO;
import com.znsx.cms.persistent.dao.DasControlDeviceRealDAO;
import com.znsx.cms.persistent.dao.DasDeviceStatusRealDAO;
import com.znsx.cms.persistent.dao.DayCoviDetectorDAO;
import com.znsx.cms.persistent.dao.DayLoliDetectorDAO;
import com.znsx.cms.persistent.dao.DayNoDetectorDAO;
import com.znsx.cms.persistent.dao.DayRoadDetectorDAO;
import com.znsx.cms.persistent.dao.DayVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.DayWeatherStatDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineRealDAO;
import com.znsx.cms.persistent.dao.HourCoviDetectorDAO;
import com.znsx.cms.persistent.dao.HourLoliDetectorDAO;
import com.znsx.cms.persistent.dao.HourNoDetectorDAO;
import com.znsx.cms.persistent.dao.HourRoadDetectorDAO;
import com.znsx.cms.persistent.dao.HourVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.HourWeatherStatDAO;
import com.znsx.cms.persistent.dao.MonthCoviDetectorDAO;
import com.znsx.cms.persistent.dao.MonthLoliDetectorDAO;
import com.znsx.cms.persistent.dao.MonthNoDetectorDAO;
import com.znsx.cms.persistent.dao.MonthRoadDetectorDAO;
import com.znsx.cms.persistent.dao.MonthVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.MonthWeatherStatDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.TmDeviceDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Crs;
import com.znsx.cms.persistent.model.DasDeviceStatusReal;
import com.znsx.cms.persistent.model.DayCoviDetector;
import com.znsx.cms.persistent.model.DayLoliDetector;
import com.znsx.cms.persistent.model.DayNoDetector;
import com.znsx.cms.persistent.model.DayRoadDetector;
import com.znsx.cms.persistent.model.DayVehichleDetector;
import com.znsx.cms.persistent.model.DayWeatherStat;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.HourCoviDetector;
import com.znsx.cms.persistent.model.HourLoliDetector;
import com.znsx.cms.persistent.model.HourNoDetector;
import com.znsx.cms.persistent.model.HourRoadDetector;
import com.znsx.cms.persistent.model.HourVehichleDetector;
import com.znsx.cms.persistent.model.HourWeatherStat;
import com.znsx.cms.persistent.model.MonthCoviDetector;
import com.znsx.cms.persistent.model.MonthLoliDetector;
import com.znsx.cms.persistent.model.MonthNoDetector;
import com.znsx.cms.persistent.model.MonthRoadDetector;
import com.znsx.cms.persistent.model.MonthVehichleDetector;
import com.znsx.cms.persistent.model.MonthWeatherStat;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.TmDevice;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.CameraStatusComparator;
import com.znsx.cms.service.comparator.RoadDeviceStatusComparator;
import com.znsx.cms.service.comparator.TunnelDeviceStatusComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.StatManager;
import com.znsx.cms.service.model.RoadDeviceStatusVO;
import com.znsx.cms.service.model.StatCameraStatusVO;
import com.znsx.cms.service.model.StatCoviVO;
import com.znsx.cms.service.model.StatLoliVO;
import com.znsx.cms.service.model.StatNoVO;
import com.znsx.cms.service.model.StatRsdVO;
import com.znsx.cms.service.model.StatVdVO;
import com.znsx.cms.service.model.StatWstVO;
import com.znsx.cms.service.model.TunnelDeviceStatusVO;

/**
 * StatManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 上午11:29:15
 */
public class StatManagerImpl extends BaseManagerImpl implements StatManager {
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private TmDeviceDAO tmDeviceDAO;
	@Autowired
	private DasDeviceStatusRealDAO dasDeviceStatusRealDAO;
	@Autowired
	private DasControlDeviceRealDAO dasControlDeviceRealDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private DeviceOnlineRealDAO deviceOnlineRealDAO;
	@Autowired
	private CrsDAO crsDAO;
	@Autowired
	private HourVehichleDetectorDAO hourVdDAO;
	@Autowired
	private DayVehichleDetectorDAO dayVdDAO;
	@Autowired
	private MonthVehichleDetectorDAO monthVdDAO;
	@Autowired
	private HourWeatherStatDAO hourWstDAO;
	@Autowired
	private DayWeatherStatDAO dayWstDAO;
	@Autowired
	private MonthWeatherStatDAO monthWstDAO;
	@Autowired
	private HourRoadDetectorDAO hourRsdDAO;
	@Autowired
	private DayRoadDetectorDAO dayRsdDAO;
	@Autowired
	private MonthRoadDetectorDAO monthRsdDAO;
	@Autowired
	private HourCoviDetectorDAO hourCoviDAO;
	@Autowired
	private DayCoviDetectorDAO dayCoviDAO;
	@Autowired
	private MonthCoviDetectorDAO monthCoviDAO;
	@Autowired
	private HourLoliDetectorDAO hourLoliDAO;
	@Autowired
	private DayLoliDetectorDAO dayLoliDAO;
	@Autowired
	private MonthLoliDetectorDAO monthLoliDAO;
	@Autowired
	private HourNoDetectorDAO hourNoDAO;
	@Autowired
	private DayNoDetectorDAO dayNoDAO;
	@Autowired
	private MonthNoDetectorDAO monthNoDAO;

	@Override
	public List<RoadDeviceStatusVO> listRoadDeviceStatus(String organSn,
			String name, Integer type, Integer status, Integer commStatus) {
		// 获取过滤道路设备的organIds查询条件
		Map<String, Organ> organMap = new HashMap<String, Organ>();
		List<String> organIds = new LinkedList<String>();
		if (StringUtils.isNotBlank(organSn)) {
			Organ organ = organDAO.loadBySNNoTransaction(organSn);
			if (null == organ) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Organ standardNumber[" + organSn + "] not found !");
			}
			// 不允许是隧道和桥梁
			if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Organ[" + organSn + "] is a bridge !");
			}
			if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Organ[" + organSn + "] is a tunnel !");
			}
			organIds.add(organ.getId());
			organMap.put(organ.getId(), organ);
		} else {
			String[] types = new String[] {
					TypeDefinition.ORGAN_TYPE_MANAGEMENT,
					TypeDefinition.ORGAN_TYPE_ROAD,
					TypeDefinition.ORGAN_TYPE_HIGHWAY,
					TypeDefinition.ORGAN_TYPE_NORMAL,
					TypeDefinition.ORGAN_TYPE_TRAFFIC,
					TypeDefinition.ORGAN_TYPE_CITYWAY };
			List<Organ> organs = organDAO.listOrganByTypesNoTransaction(types);
			for (Organ organ : organs) {
				organIds.add(organ.getId());
				organMap.put(organ.getId(), organ);
			}
		}

		// 转换字符串设备类型
		String deviceType = null;
		if (type != null) {
			deviceType = type.toString();
		}
		// 获取所有的道路设备
		Map<String, TmDevice> map = tmDeviceDAO.mapDeviceByOrgansNoTransaction(
				organIds, name, deviceType);
		Set<String> sns = map.keySet();
		// 获取道路设备的状态信息
		List<DasDeviceStatusReal> statusList = dasDeviceStatusRealDAO
				.listDeviceStatusRealNoTransaction(sns);

		// 找出从未上报过状态的设备,并添加到statusList中
		if (sns.size() > statusList.size()) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			// 初始化所有设备状态
			for (String sn : sns) {
				statusMap.put(sn, null);
			}
			// 数据存在标志
			Object flag = new Object();
			// 更新数据库查询到的设备状态
			for (DasDeviceStatusReal ds : statusList) {
				statusMap.put(ds.getStandardNumber(), flag);
			}
			// 数据库查询不到的设备状态视为通信异常和工作异常
			Set<Entry<String, Object>> entrySet = statusMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getValue() == null) {
					DasDeviceStatusReal ds = new DasDeviceStatusReal();
					String sn = entry.getKey();
					ds.setStandardNumber(sn);
					ds.setCommStatus(Short.valueOf("1"));
					ds.setStatus(Short.valueOf("1"));
					statusList.add(ds);
				}
			}
		}
		// 构建分页记录的总数据
		List<RoadDeviceStatusVO> totalList = new LinkedList<RoadDeviceStatusVO>();
		for (DasDeviceStatusReal ds : statusList) {
			// 过滤状态
			if (null != status
					&& ds.getStatus().intValue() != status.intValue()) {
				continue;
			}
			if (null != commStatus
					&& ds.getCommStatus().intValue() != commStatus.intValue()) {
				continue;
			}

			RoadDeviceStatusVO vo = new RoadDeviceStatusVO();
			TmDevice device = map.get(ds.getStandardNumber());
			vo.setCommStatus(ds.getCommStatus().toString());
			vo.setStatus(ds.getStatus().toString());
			vo.setIp(device.getLanIp());
			vo.setName(device.getName());
			String organId = device.getOrgan().getId();
			vo.setOrganId(organId);
			vo.setOrganName(organMap.get(organId).getName());
			vo.setOrganSn(organMap.get(organId).getStandardNumber());
			vo.setStakeNumber(device.getStakeNumber());
			vo.setStandardNumber(device.getStandardNumber());
			vo.setType(device.getType());
			totalList.add(vo);
		}
		// 排序-先按机构ID排序，再按名称排序
		Collections.sort(totalList, new RoadDeviceStatusComparator());

		return totalList;
	}

	@Override
	public List<TunnelDeviceStatusVO> listTunnelDeviceStatus(String organSn,
			String name, Integer type, Integer status, Integer commStatus) {
		// 获取过滤隧道和桥梁设备的organIds查询条件
		Map<String, Organ> organMap = new HashMap<String, Organ>();
		List<String> organIds = new LinkedList<String>();
		if (StringUtils.isNotBlank(organSn)) {
			Organ organ = organDAO.loadBySNNoTransaction(organSn);
			if (null == organ) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Organ standardNumber[" + organSn + "] not found !");
			}
			// 只允许是隧道和桥梁
			if (organ.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)
					|| organ.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
				organIds.add(organ.getId());
				organMap.put(organ.getId(), organ);
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Organ[" + organSn + "] is not a tunnel or bridge !");
			}
		} else {
			String[] types = new String[] { TypeDefinition.ORGAN_TYPE_BRIDGE,
					TypeDefinition.ORGAN_TYPE_TUNNEL };
			List<Organ> organs = organDAO.listOrganByTypesNoTransaction(types);
			for (Organ organ : organs) {
				organIds.add(organ.getId());
				organMap.put(organ.getId(), organ);
			}
		}

		// 转换字符串设备类型
		String deviceType = null;
		if (type != null) {
			deviceType = type.toString();
		}
		// 获取所有的隧道或桥梁设备
		Map<String, TmDevice> map = tmDeviceDAO.mapDeviceByOrgansNoTransaction(
				organIds, name, deviceType);
		// 对设备进行分类: 控制开关量类和采集类
		Set<String> cdSns = new HashSet<String>();
		Set<String> gatherSns = new HashSet<String>();

		for (TmDevice device : map.values()) {
			if ((TypeDefinition.DEVICE_TYPE_FAN + "").equals(device.getType())
					|| (TypeDefinition.DEVICE_TYPE_LIGHT + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_RD + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_WP + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_RAIL + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_IS + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_PB + "").equals(device
							.getType())
					|| (TypeDefinition.DEVICE_TYPE_FD + "").equals(device
							.getType())) {
				cdSns.add(device.getStandardNumber());
			} else {
				gatherSns.add(device.getStandardNumber());
			}
		}
		// 总的满足条件的设备实时数据
		List<TunnelDeviceStatusVO> totalList = new LinkedList<TunnelDeviceStatusVO>();

		// 采集类去device_status_real表中查询
		List<DasDeviceStatusReal> gatherList = dasDeviceStatusRealDAO
				.listDeviceStatusRealNoTransaction(gatherSns);
		// 采集类数据加入到totalList
		for (DasDeviceStatusReal entity : gatherList) {
			TunnelDeviceStatusVO vo = new TunnelDeviceStatusVO();
			vo.setCommStatus(entity.getCommStatus().toString());
			vo.setStatus(entity.getStatus().toString());
			vo.setStandardNumber(entity.getStandardNumber());
			totalList.add(vo);
		}
		// 开关控制类去control_device_real表中查询
		List<TunnelDeviceStatusVO> cdList = dasControlDeviceRealDAO
				.listTunnelDeviceStatusNoTransaction(cdSns);
		// 开关控制类数据加入到totalList
		totalList.addAll(cdList);

		// 找出从未上报过状态的设备,并添加到totalList中
		Set<String> sns = new HashSet<String>();
		sns.addAll(cdSns);
		sns.addAll(gatherSns);
		if (sns.size() > totalList.size()) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			// 初始化所有设备状态
			for (String sn : sns) {
				statusMap.put(sn, null);
			}
			// 数据存在标志
			Object flag = new Object();
			// 更新数据库查询到的设备状态
			for (TunnelDeviceStatusVO ds : totalList) {
				statusMap.put(ds.getStandardNumber(), flag);
			}
			// 数据库查询不到的设备状态视为通信异常和工作异常
			Set<Entry<String, Object>> entrySet = statusMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getValue() == null) {
					TunnelDeviceStatusVO ds = new TunnelDeviceStatusVO();
					String sn = entry.getKey();
					ds.setStandardNumber(sn);
					ds.setCommStatus("1");
					ds.setStatus("1");
					totalList.add(ds);
				}
			}
		}

		// 构建分页记录的总数据
		List<TunnelDeviceStatusVO> filterList = new LinkedList<TunnelDeviceStatusVO>();
		for (TunnelDeviceStatusVO ds : totalList) {
			// 过滤状态
			if (null != status && !ds.getStatus().equals(status.toString())) {
				continue;
			}
			if (null != commStatus
					&& !ds.getCommStatus().equals(commStatus.toString())) {
				continue;
			}

			TmDevice device = map.get(ds.getStandardNumber());
			ds.setIp(device.getLanIp());
			ds.setName(device.getName());
			String organId = device.getOrgan().getId();
			ds.setOrganId(organId);
			ds.setOrganName(organMap.get(organId).getName());
			ds.setOrganSn(organMap.get(organId).getStandardNumber());
			ds.setStakeNumber(device.getStakeNumber());
			ds.setStandardNumber(device.getStandardNumber());
			ds.setType(device.getType());
			filterList.add(ds);
		}
		// 排序-先按机构ID排序，再按名称排序
		Collections.sort(filterList, new TunnelDeviceStatusComparator());

		return filterList;
	}

	@Override
	public List<StatCameraStatusVO> listCameraStatus(String organSn,
			String name, Integer commStatus) {
		// 所有的机构ID和对象映射表
		Map<String, Organ> organMap = new HashMap<String, Organ>();
		// 满足机构和名称条件的摄像头列表
		Map<String, Camera> map = null;

		if (StringUtils.isNotBlank(organSn)) {
			Organ organ = organDAO.loadBySNNoTransaction(organSn);
			if (null == organ) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Organ standardNumber[" + organSn + "] not found !");
			}
			organMap.put(organ.getId(), organ);
			// 查询单个机构下的摄像头
			map = cameraDAO.mapCameraNoTransaction(organ.getId(), name);
		} else {
			List<Organ> organs = organDAO.listOrganByTypesNoTransaction(null);
			for (Organ organ : organs) {
				organMap.put(organ.getId(), organ);
			}
			// 查询所有的摄像头
			map = cameraDAO.mapCameraNoTransaction(null, name);
		}
		// 查询所有的摄像头状态
		Set<String> sns = map.keySet();
		// 数据库中记录的摄像头在线信息
		List<DeviceOnlineReal> statusList = deviceOnlineRealDAO
				.listDeviceStatusNoTransaction(sns);
		// 不在线的设备数据补充
		if (sns.size() > statusList.size()) {
			Map<String, Object> statusMap = new HashMap<String, Object>();
			// 初始化所有设备状态
			for (String sn : sns) {
				statusMap.put(sn, null);
			}
			// 数据存在标志
			Object flag = new Object();
			// 更新数据库查询到的设备状态
			for (DeviceOnlineReal o : statusList) {
				statusMap.put(o.getStandardNumber(), flag);
			}
			// 数据库查询不到的设备状态视为通信异常和工作异常
			Set<Entry<String, Object>> entrySet = statusMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getValue() == null) {
					DeviceOnlineReal o = new DeviceOnlineReal();
					String sn = entry.getKey();
					o.setStandardNumber(sn);
					statusList.add(o);
				}
			}
		}
		// 构建分页记录的总数据
		List<StatCameraStatusVO> totalList = new LinkedList<StatCameraStatusVO>();
		// 所有的中心存储服务器
		Map<String, Crs> crsMap = crsDAO.mapByIdNoTransaction();
		for (DeviceOnlineReal o : statusList) {
			// 过滤状态：o.getId()==null表示不在线，o.getId()!=null表示在线
			if (commStatus != null && commStatus.intValue() == 0
					&& o.getId() == null) {
				continue;
			}
			if (commStatus != null && commStatus.intValue() == 1
					&& o.getId() != null) {
				continue;
			}
			StatCameraStatusVO vo = new StatCameraStatusVO();
			Camera camera = map.get(o.getStandardNumber());
			vo.setCommStatus(o.getId() == null ? "1" : "0");
			Crs crs = camera.getCrs();
			if (null == crs) {
				vo.setCrsName("前端硬盘存储");
			} else {
				vo.setCrsName(crsMap.get(crs.getId()).getName());
			}
			vo.setIp(camera.getParent().getLanIp());
			vo.setName(camera.getName());
			vo.setOrganId(camera.getOrgan().getId());
			vo.setOrganName(organMap.get(camera.getOrgan().getId()).getName());
			vo.setOrganSn(organMap.get(camera.getOrgan().getId())
					.getStandardNumber());
			vo.setStakeNumber(camera.getStakeNumber());
			vo.setStandardNumber(camera.getStandardNumber());
			totalList.add(vo);
		}
		// 排序-先按机构ID排序，再按名称排序
		Collections.sort(totalList, new CameraStatusComparator());

		return totalList;
	}

	@Override
	public List<StatVdVO> statVdByHour(String sn, Long beginTime, Long endTime,
			int start, int limit) {
		List<HourVehichleDetector> list = hourVdDAO.list(sn, beginTime,
				endTime, start, limit);
		List<StatVdVO> rtnList = new LinkedList<StatVdVO>();
		for (HourVehichleDetector hvd : list) {
			StatVdVO vo = new StatVdVO();
			vo.setDateTime(hvd.getDateTime());
			vo.setDwFlow(new BigDecimal(hvd.getDwFlow()).toPlainString());
			vo.setDwHeadway(hvd.getDwHeadway());
			vo.setDwOcc(hvd.getDwOcc());
			vo.setDwSpeed(hvd.getDwSpeed());
			vo.setStandardNumber(hvd.getStandardNumber());
			vo.setUpFlow(new BigDecimal(hvd.getUpFlow()).toPlainString());
			vo.setUpHeadway(hvd.getUpHeadway());
			vo.setUpOcc(hvd.getUpOcc());
			vo.setUpSpeed(hvd.getUpSpeed());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countVdByHour(String sn, Long beginTime, Long endTime) {
		return hourVdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatVdVO> statVdByDay(String sn, Long beginTime, Long endTime,
			int start, int limit) {
		List<DayVehichleDetector> list = dayVdDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatVdVO> rtnList = new LinkedList<StatVdVO>();
		for (DayVehichleDetector dvd : list) {
			StatVdVO vo = new StatVdVO();
			vo.setDateTime(dvd.getDateTime());
			vo.setDwFlow(new BigDecimal(dvd.getDwFlow()).toPlainString());
			vo.setDwHeadway(dvd.getDwHeadway());
			vo.setDwOcc(dvd.getDwOcc());
			vo.setDwSpeed(dvd.getDwSpeed());
			vo.setStandardNumber(dvd.getStandardNumber());
			vo.setUpFlow(new BigDecimal(dvd.getUpFlow()).toPlainString());
			vo.setUpHeadway(dvd.getUpHeadway());
			vo.setUpOcc(dvd.getUpOcc());
			vo.setUpSpeed(dvd.getUpSpeed());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countVdByDay(String sn, Long beginTime, Long endTime) {
		return dayVdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatVdVO> statVdByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<MonthVehichleDetector> list = monthVdDAO.list(sn, beginTime,
				endTime, start, limit);
		List<StatVdVO> rtnList = new LinkedList<StatVdVO>();
		for (MonthVehichleDetector mvd : list) {
			StatVdVO vo = new StatVdVO();
			vo.setDateTime(mvd.getDateTime());
			vo.setDwFlow(new BigDecimal(mvd.getDwFlow()).toPlainString());
			vo.setDwHeadway(mvd.getDwHeadway());
			vo.setDwOcc(mvd.getDwOcc());
			vo.setDwSpeed(mvd.getDwSpeed());
			vo.setStandardNumber(mvd.getStandardNumber());
			vo.setUpFlow(new BigDecimal(mvd.getUpFlow()).toPlainString());
			vo.setUpHeadway(mvd.getUpHeadway());
			vo.setUpOcc(mvd.getUpOcc());
			vo.setUpSpeed(mvd.getUpSpeed());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countVdByMonth(String sn, Long beginTime, Long endTime) {
		return monthVdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatWstVO> statWstByHour(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<HourWeatherStat> list = hourWstDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatWstVO> rtnList = new LinkedList<StatWstVO>();
		for (HourWeatherStat hwst : list) {
			StatWstVO vo = new StatWstVO();
			vo.setAirTempAvg(hwst.getAirTempAvg());
			vo.setAirTempMax(hwst.getAirTempMax());
			vo.setAirTempMin(hwst.getAirTempMin());
			vo.setDateTime(hwst.getDateTime());
			vo.setRoadTempAvg(hwst.getRoadTempAvg());
			vo.setRoadTempMax(hwst.getRoadTempMax());
			vo.setRoadTempMin(hwst.getRoadTempMin());
			vo.setStandardNumber(hwst.getStandardNumber());
			vo.setVisAvg(hwst.getVisAvg());
			vo.setWsAvg(hwst.getWsAvg());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countWstByHour(String sn, Long beginTime, Long endTime) {
		return hourWstDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatWstVO> statWstByDay(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<DayWeatherStat> list = dayWstDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatWstVO> rtnList = new LinkedList<StatWstVO>();
		for (DayWeatherStat dwst : list) {
			StatWstVO vo = new StatWstVO();
			vo.setAirTempAvg(dwst.getAirTempAvg());
			vo.setAirTempMax(dwst.getAirTempMax());
			vo.setAirTempMin(dwst.getAirTempMin());
			vo.setDateTime(dwst.getDateTime());
			vo.setRoadTempAvg(dwst.getRoadTempAvg());
			vo.setRoadTempMax(dwst.getRoadTempMax());
			vo.setRoadTempMin(dwst.getRoadTempMin());
			vo.setStandardNumber(dwst.getStandardNumber());
			vo.setVisAvg(dwst.getVisAvg());
			vo.setWsAvg(dwst.getWsAvg());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countWstByDay(String sn, Long beginTime, Long endTime) {
		return dayWstDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatWstVO> statWstByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<MonthWeatherStat> list = monthWstDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatWstVO> rtnList = new LinkedList<StatWstVO>();
		for (MonthWeatherStat mwst : list) {
			StatWstVO vo = new StatWstVO();
			vo.setAirTempAvg(mwst.getAirTempAvg());
			vo.setAirTempMax(mwst.getAirTempMax());
			vo.setAirTempMin(mwst.getAirTempMin());
			vo.setDateTime(mwst.getDateTime());
			vo.setRoadTempAvg(mwst.getRoadTempAvg());
			vo.setRoadTempMax(mwst.getRoadTempMax());
			vo.setRoadTempMin(mwst.getRoadTempMin());
			vo.setStandardNumber(mwst.getStandardNumber());
			vo.setVisAvg(mwst.getVisAvg());
			vo.setWsAvg(mwst.getWsAvg());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countWstByMonth(String sn, Long beginTime, Long endTime) {
		return monthWstDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatRsdVO> statRsdByHour(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<HourRoadDetector> list = hourRsdDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatRsdVO> rtnList = new LinkedList<StatRsdVO>();
		for (HourRoadDetector hrsd : list) {
			StatRsdVO vo = new StatRsdVO();
			vo.setDateTime(hrsd.getDateTime());
			vo.setRoadTempAvg(hrsd.getRoadTempAvg());
			vo.setRoadTempMax(hrsd.getRoadTempMax());
			vo.setRoadTempMin(hrsd.getRoadTempMin());
			vo.setStandardNumber(hrsd.getStandardNumber());
			vo.setRainVol(hrsd.getRainVol());
			vo.setRoadSurface(hrsd.getRoadSurface());
			vo.setSnowVol(hrsd.getSnowVol());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countRsdByHour(String sn, Long beginTime, Long endTime) {
		return hourRsdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatRsdVO> statRsdByDay(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<DayRoadDetector> list = dayRsdDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatRsdVO> rtnList = new LinkedList<StatRsdVO>();
		for (DayRoadDetector drsd : list) {
			StatRsdVO vo = new StatRsdVO();
			vo.setDateTime(drsd.getDateTime());
			vo.setRoadTempAvg(drsd.getRoadTempAvg());
			vo.setRoadTempMax(drsd.getRoadTempMax());
			vo.setRoadTempMin(drsd.getRoadTempMin());
			vo.setStandardNumber(drsd.getStandardNumber());
			vo.setRainVol(drsd.getRainVol());
			vo.setRoadSurface(drsd.getRoadSurface());
			vo.setSnowVol(drsd.getSnowVol());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countRsdByDay(String sn, Long beginTime, Long endTime) {
		return dayRsdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatRsdVO> statRsdByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<MonthRoadDetector> list = monthRsdDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatRsdVO> rtnList = new LinkedList<StatRsdVO>();
		for (MonthRoadDetector mrsd : list) {
			StatRsdVO vo = new StatRsdVO();
			vo.setDateTime(mrsd.getDateTime());
			vo.setRoadTempAvg(mrsd.getRoadTempAvg());
			vo.setRoadTempMax(mrsd.getRoadTempMax());
			vo.setRoadTempMin(mrsd.getRoadTempMin());
			vo.setStandardNumber(mrsd.getStandardNumber());
			vo.setRainVol(mrsd.getRainVol());
			vo.setRoadSurface(mrsd.getRoadSurface());
			vo.setSnowVol(mrsd.getSnowVol());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countRsdByMonth(String sn, Long beginTime, Long endTime) {
		return monthRsdDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatCoviVO> statCoviByHour(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<HourCoviDetector> list = hourCoviDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatCoviVO> rtnList = new LinkedList<StatCoviVO>();
		for (HourCoviDetector hcovi : list) {
			StatCoviVO vo = new StatCoviVO();
			vo.setDateTime(hcovi.getDateTime());
			vo.setCoAvg(hcovi.getCoAvg());
			vo.setCoMax(hcovi.getCoMax());
			vo.setCoMin(hcovi.getCoMin());
			vo.setStandardNumber(hcovi.getStandardNumber());
			vo.setViAvg(hcovi.getViAvg());
			vo.setViMax(hcovi.getViMax());
			vo.setViMin(hcovi.getViMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countCoviByHour(String sn, Long beginTime, Long endTime) {
		return hourCoviDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatCoviVO> statCoviByDay(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<DayCoviDetector> list = dayCoviDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatCoviVO> rtnList = new LinkedList<StatCoviVO>();
		for (DayCoviDetector dcovi : list) {
			StatCoviVO vo = new StatCoviVO();
			vo.setDateTime(dcovi.getDateTime());
			vo.setCoAvg(dcovi.getCoAvg());
			vo.setCoMax(dcovi.getCoMax());
			vo.setCoMin(dcovi.getCoMin());
			vo.setStandardNumber(dcovi.getStandardNumber());
			vo.setViAvg(dcovi.getViAvg());
			vo.setViMax(dcovi.getViMax());
			vo.setViMin(dcovi.getViMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countCoviByDay(String sn, Long beginTime, Long endTime) {
		return dayCoviDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatCoviVO> statCoviByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<MonthCoviDetector> list = monthCoviDAO.list(sn, beginTime,
				endTime, start, limit);
		List<StatCoviVO> rtnList = new LinkedList<StatCoviVO>();
		for (MonthCoviDetector mcovi : list) {
			StatCoviVO vo = new StatCoviVO();
			vo.setDateTime(mcovi.getDateTime());
			vo.setCoAvg(mcovi.getCoAvg());
			vo.setCoMax(mcovi.getCoMax());
			vo.setCoMin(mcovi.getCoMin());
			vo.setStandardNumber(mcovi.getStandardNumber());
			vo.setViAvg(mcovi.getViAvg());
			vo.setViMax(mcovi.getViMax());
			vo.setViMin(mcovi.getViMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countCoviByMonth(String sn, Long beginTime, Long endTime) {
		return monthCoviDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatLoliVO> statLoliByHour(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<HourLoliDetector> list = hourLoliDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatLoliVO> rtnList = new LinkedList<StatLoliVO>();
		for (HourLoliDetector hloli : list) {
			StatLoliVO vo = new StatLoliVO();
			vo.setDateTime(hloli.getDateTime());
			vo.setLoAvg(hloli.getLoAvg());
			vo.setLoMax(hloli.getLoMax());
			vo.setLoMin(hloli.getLoMin());
			vo.setStandardNumber(hloli.getStandardNumber());
			vo.setLiAvg(hloli.getLiAvg());
			vo.setLiMax(hloli.getLiMax());
			vo.setLiMin(hloli.getLiMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countLoliByHour(String sn, Long beginTime, Long endTime) {
		return hourLoliDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatLoliVO> statLoliByDay(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<DayLoliDetector> list = dayLoliDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatLoliVO> rtnList = new LinkedList<StatLoliVO>();
		for (DayLoliDetector dloli : list) {
			StatLoliVO vo = new StatLoliVO();
			vo.setDateTime(dloli.getDateTime());
			vo.setLoAvg(dloli.getLoAvg());
			vo.setLoMax(dloli.getLoMax());
			vo.setLoMin(dloli.getLoMin());
			vo.setStandardNumber(dloli.getStandardNumber());
			vo.setLiAvg(dloli.getLiAvg());
			vo.setLiMax(dloli.getLiMax());
			vo.setLiMin(dloli.getLiMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countLoliByDay(String sn, Long beginTime, Long endTime) {
		return dayLoliDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatLoliVO> statLoliByMonth(String sn, Long beginTime,
			Long endTime, int start, int limit) {
		List<MonthLoliDetector> list = monthLoliDAO.list(sn, beginTime,
				endTime, start, limit);
		List<StatLoliVO> rtnList = new LinkedList<StatLoliVO>();
		for (MonthLoliDetector mloli : list) {
			StatLoliVO vo = new StatLoliVO();
			vo.setDateTime(mloli.getDateTime());
			vo.setLoAvg(mloli.getLoAvg());
			vo.setLoMax(mloli.getLoMax());
			vo.setLoMin(mloli.getLoMin());
			vo.setStandardNumber(mloli.getStandardNumber());
			vo.setLiAvg(mloli.getLiAvg());
			vo.setLiMax(mloli.getLiMax());
			vo.setLiMin(mloli.getLiMin());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countLoliByMonth(String sn, Long beginTime, Long endTime) {
		return monthLoliDAO.count(sn, beginTime, endTime);
	}

	@Override
	public int countNoByHour(String sn, Long beginTime, Long endTime) {
		return hourNoDAO.countNoByHour(sn, beginTime, endTime);
	}

	@Override
	public List<StatNoVO> statNoByHour(String sn, Long beginTime, Long endTime,
			Integer start, Integer limit) {
		List<HourNoDetector> list = hourNoDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatNoVO> rtnList = new LinkedList<StatNoVO>();
		for (HourNoDetector hnod : list) {
			StatNoVO vo = new StatNoVO();
			vo.setDateTime(hnod.getDateTime());
			vo.setNoAvg(hnod.getNoAvg());
			vo.setNoMax(hnod.getNoMax());
			vo.setNoMin(hnod.getNoMin());
			vo.setStandardNumber(hnod.getStandardNumber());
			vo.setNo2Avg(hnod.getNo2Avg());
			vo.setNo2Max(hnod.getNo2Max());
			vo.setNo2Min(hnod.getNo2Min());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countNoByDay(String sn, Long beginTime, Long endTime) {
		return dayNoDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatNoVO> statNoByDay(String sn, Long beginTime, Long endTime,
			Integer start, Integer limit) {
		List<DayNoDetector> list = dayNoDAO.list(sn, beginTime, endTime, start,
				limit);
		List<StatNoVO> rtnList = new LinkedList<StatNoVO>();
		for (DayNoDetector dnod : list) {
			StatNoVO vo = new StatNoVO();
			vo.setDateTime(dnod.getDateTime());
			vo.setNoAvg(dnod.getNoAvg());
			vo.setNoMax(dnod.getNoMax());
			vo.setNoMin(dnod.getNoMin());
			vo.setStandardNumber(dnod.getStandardNumber());
			vo.setNo2Avg(dnod.getNo2Avg());
			vo.setNo2Max(dnod.getNo2Max());
			vo.setNo2Min(dnod.getNo2Min());
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countNoByMonth(String sn, Long beginTime, Long endTime) {
		return monthNoDAO.count(sn, beginTime, endTime);
	}

	@Override
	public List<StatNoVO> statNoByMonth(String sn, Long beginTime,
			Long endTime, Integer start, Integer limit) {
		List<MonthNoDetector> list = monthNoDAO.list(sn, beginTime, endTime,
				start, limit);
		List<StatNoVO> rtnList = new LinkedList<StatNoVO>();
		for (MonthNoDetector mnod : list) {
			StatNoVO vo = new StatNoVO();
			vo.setDateTime(mnod.getDateTime());
			vo.setNoAvg(mnod.getNoAvg());
			vo.setNoMax(mnod.getNoMax());
			vo.setNoMin(mnod.getNoMin());
			vo.setStandardNumber(mnod.getStandardNumber());
			vo.setNo2Avg(mnod.getNo2Avg());
			vo.setNo2Max(mnod.getNo2Max());
			vo.setNo2Min(mnod.getNo2Min());
			rtnList.add(vo);
		}
		return rtnList;
	}
}
