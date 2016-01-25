package com.znsx.cms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.drools.lang.dsl.DSLMapParser.mapping_file_return;
import org.hibernate.id.UUIDHexGenerator;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.dao.CmsPublishLogDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.CrsDAO;
import com.znsx.cms.persistent.dao.DasCmsDAO;
import com.znsx.cms.persistent.dao.DasCmsRealDAO;
import com.znsx.cms.persistent.dao.DasControlDeviceDAO;
import com.znsx.cms.persistent.dao.DasControlDeviceRealDAO;
import com.znsx.cms.persistent.dao.DasCoviDAO;
import com.znsx.cms.persistent.dao.DasCoviRealDAO;
import com.znsx.cms.persistent.dao.DasDAO;
import com.znsx.cms.persistent.dao.DasDeviceStatusDAO;
import com.znsx.cms.persistent.dao.DasDeviceStatusRealDAO;
import com.znsx.cms.persistent.dao.DasGpsDAO;
import com.znsx.cms.persistent.dao.DasGpsRealDAO;
import com.znsx.cms.persistent.dao.DasLilDAO;
import com.znsx.cms.persistent.dao.DasLilRealDAO;
import com.znsx.cms.persistent.dao.DasLoliDAO;
import com.znsx.cms.persistent.dao.DasLoliRealDAO;
import com.znsx.cms.persistent.dao.DasNodDAO;
import com.znsx.cms.persistent.dao.DasNodRealDAO;
import com.znsx.cms.persistent.dao.DasRoadDetectorDAO;
import com.znsx.cms.persistent.dao.DasRoadDetectorRealDAO;
import com.znsx.cms.persistent.dao.DasTslDAO;
import com.znsx.cms.persistent.dao.DasTslRealDAO;
import com.znsx.cms.persistent.dao.DasVdDAO;
import com.znsx.cms.persistent.dao.DasVdRealDAO;
import com.znsx.cms.persistent.dao.DasViDetectorDAO;
import com.znsx.cms.persistent.dao.DasViDetectorRealDAO;
import com.znsx.cms.persistent.dao.DasWsDAO;
import com.znsx.cms.persistent.dao.DasWsRealDAO;
import com.znsx.cms.persistent.dao.DasWstDAO;
import com.znsx.cms.persistent.dao.DasWstRealDAO;
import com.znsx.cms.persistent.dao.DvrDAO;
import com.znsx.cms.persistent.dao.DwsDAO;
import com.znsx.cms.persistent.dao.EnsDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.MonitorDAO;
import com.znsx.cms.persistent.dao.MssDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PtsDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.RoadDetectorDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.SubPlatformDAO;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.SyncVehicleDetectorDAO;
import com.znsx.cms.persistent.dao.TmDeviceDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserSessionDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.CmsPublishLog;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.Crs;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.persistent.model.DasCms;
import com.znsx.cms.persistent.model.DasCmsReal;
import com.znsx.cms.persistent.model.DasControlDevice;
import com.znsx.cms.persistent.model.DasControlDeviceReal;
import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasCoviReal;
import com.znsx.cms.persistent.model.DasDeviceStatus;
import com.znsx.cms.persistent.model.DasDeviceStatusReal;
import com.znsx.cms.persistent.model.DasGps;
import com.znsx.cms.persistent.model.DasGpsReal;
import com.znsx.cms.persistent.model.DasLil;
import com.znsx.cms.persistent.model.DasLilReal;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasLoliReal;
import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.persistent.model.DasNodReal;
import com.znsx.cms.persistent.model.DasRoadDetector;
import com.znsx.cms.persistent.model.DasRoadDetectorReal;
import com.znsx.cms.persistent.model.DasTsl;
import com.znsx.cms.persistent.model.DasTslReal;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.persistent.model.DasVdReal;
import com.znsx.cms.persistent.model.DasViDetector;
import com.znsx.cms.persistent.model.DasViDetectorReal;
import com.znsx.cms.persistent.model.DasWs;
import com.znsx.cms.persistent.model.DasWsReal;
import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.persistent.model.DasWstReal;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.Monitor;
import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.RoadDetector;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.SubVehicleDetector;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.persistent.model.TmDevice;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DasDataManager;
import com.znsx.cms.service.model.CoviVO;
import com.znsx.cms.service.model.DeviceStatusVO;
import com.znsx.cms.service.model.LoLiVO;
import com.znsx.cms.service.model.OrganVDVO;
import com.znsx.cms.service.model.OrganVehicleDetectorTopVO;
import com.znsx.cms.service.model.RoadFluxStatVO;
import com.znsx.cms.service.model.VdFluxByMonthVO;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.VehicleDetectorTotalVO;
import com.znsx.cms.service.model.VehicleDetectorVO;
import com.znsx.cms.service.model.WsVO;
import com.znsx.cms.service.model.WstVO;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * DasDataManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午10:18:43
 */
public class DasDataManagerImpl extends BaseManagerImpl implements
		DasDataManager {
	@Autowired
	private DasCmsDAO dasCmsDAO;
	@Autowired
	private DasControlDeviceDAO dasCdDAO;
	@Autowired
	private DasCoviDAO dasCoviDAO;
	@Autowired
	private DasDeviceStatusDAO dasDeviceStatusDAO;
	@Autowired
	private DasLoliDAO dasLoliDAO;
	@Autowired
	private DasNodDAO dasNodDAO;
	@Autowired
	private DasVdDAO dasVdDAO;
	@Autowired
	private DasWsDAO dasWsDAO;
	@Autowired
	private DasWstDAO dasWstDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private WindSpeedDAO wsDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private FireDetectorDAO fireDetectorDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private NoDetectorDAO noDetectorDAO;
	@Autowired
	private PushButtonDAO pushButtonDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private SubPlatformDAO subPlatformDAO;
	@Autowired
	private StandardNumberDAO snDAO;
	@Autowired
	private MssDAO mssDAO;
	@Autowired
	private CcsDAO ccsDAO;
	@Autowired
	private CrsDAO crsDAO;
	@Autowired
	private PtsDAO ptsDAO;
	@Autowired
	private DasDAO dasDAO;
	@Autowired
	private DwsDAO dwsDAO;
	@Autowired
	private EnsDAO ensDAO;
	@Autowired
	private DvrDAO dvrDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MonitorDAO monitorDAO;
	@Autowired
	private DasTslDAO dasTslDAO;
	@Autowired
	private DasLilDAO dasLilDAO;
	@Autowired
	private SyncVehicleDetectorDAO syncVehicleDetectorDAO;
	@Autowired
	private CmsPublishLogDAO cmsPublishLogDAO;
	@Autowired
	private DasDeviceStatusRealDAO dasDeviceStatusRealDAO;
	@Autowired
	private DasControlDeviceRealDAO dasControlDeviceRealDAO;
	@Autowired
	private DasCmsRealDAO dasCmsRealDAO;
	@Autowired
	private DasCoviRealDAO dasCoviRealDAO;
	@Autowired
	private DasLoliRealDAO dasLoliRealDAO;
	@Autowired
	private DasNodRealDAO dasNodRealDAO;
	@Autowired
	private DasVdRealDAO dasVdRealDAO;
	@Autowired
	private DasWstRealDAO dasWstRealDAO;
	@Autowired
	private DasWsRealDAO dasWsRealDAO;
	@Autowired
	private DasTslRealDAO dasTslRealDAO;
	@Autowired
	private DasLilRealDAO dasLilRealDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;
	@Autowired
	private DasViDetectorDAO dasViDetectorDAO;
	@Autowired
	private DasViDetectorRealDAO dasViDetectorRealDAO;
	@Autowired
	private DasRoadDetectorRealDAO dasRoadDetectorRealDAO;
	@Autowired
	private DasRoadDetectorDAO dasRoadDetectorDAO;
	@Autowired
	private DasGpsRealDAO dasGpsRealDAO;
	@Autowired
	private DasGpsDAO dasGpsDAO;
	@Autowired
	private RoadDetectorDAO roadDetectorDAO;
	@Autowired
	private UserSessionDAO userSessionDAO;
	@Autowired
	private TmDeviceDAO tmDeviceDAO;

	private Map<String, String> vdChannelMap = new HashMap<String, String>();

	@Override
	public void saveData1(List<Element> data) throws BusinessException {
		// 变化量设备实时表集合
		List<DasDeviceStatusReal> list = new LinkedList<DasDeviceStatusReal>();
		List<DasCmsReal> cmsList = new LinkedList<DasCmsReal>();
		List<DasControlDeviceReal> cdList = new LinkedList<DasControlDeviceReal>();
		List<DasCoviReal> coviList = new LinkedList<DasCoviReal>();
		List<DasLoliReal> loliList = new LinkedList<DasLoliReal>();
		List<DasNodReal> nodList = new LinkedList<DasNodReal>();
		List<DasVdReal> vdList = new LinkedList<DasVdReal>();
		List<DasWsReal> wsList = new LinkedList<DasWsReal>();
		List<DasWstReal> wstList = new LinkedList<DasWstReal>();
		List<DasTslReal> tslList = new LinkedList<DasTslReal>();
		List<DasLilReal> lilList = new LinkedList<DasLilReal>();
		List<DasViDetectorReal> vidList = new LinkedList<DasViDetectorReal>();
		List<DasRoadDetectorReal> rdList = new LinkedList<DasRoadDetectorReal>();
		List<DasGpsReal> gpsList = new LinkedList<DasGpsReal>();

		// 存入变化量SN集合
		Set<String> deviceStatusSN = new HashSet<String>();
		Set<String> cmsSN = new HashSet<String>();
		Set<String> cdSN = new HashSet<String>();
		Set<String> coviSN = new HashSet<String>();
		Set<String> loliSN = new HashSet<String>();
		Set<String> nodSN = new HashSet<String>();
		Set<String> vdSN = new HashSet<String>();
		Set<String> wsSN = new HashSet<String>();
		Set<String> wstSN = new HashSet<String>();
		Set<String> tslSN = new HashSet<String>();
		Set<String> lilSN = new HashSet<String>();
		Set<String> vidSN = new HashSet<String>();
		Set<String> rdSN = new HashSet<String>();
		Set<String> gpsSN = new HashSet<String>();

		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		// 2014-02-01, 分区表起始分区
		long beginDate = 1391184000000l;
		// 当前时间的一个月后
		long lastDate = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30;

		// 获取平台根机构SN
		String organSN = organDAO.getRootOrgan().getStandardNumber();
		for (Element e : data) {
			Timestamp date = ElementUtil.getTimestamp(e, "RecTime");
			if (date.getTime() <= beginDate) {
				System.out.println("RecTime[" + e.getAttributeValue("RecTime")
						+ "] invalid, set RecTime to current time !");
				date = new Timestamp(System.currentTimeMillis());
			} else if (date.getTime() >= lastDate) {
				System.out.println("RecTime[" + e.getAttributeValue("RecTime")
						+ "] invalid, set RecTime to current time !");
				date = new Timestamp(System.currentTimeMillis());
			}
			DasDeviceStatusReal deviceStatusReal = new DasDeviceStatusReal();
			deviceStatusReal.setStandardNumber(e
					.getAttributeValue("StandardNumber"));
			deviceStatusReal.setRecTime(date);
			deviceStatusReal.setType(ElementUtil.getInteger(e, "Type"));
			deviceStatusReal.setStatus(ElementUtil.getShort(e, "Status"));
			deviceStatusReal.setCommStatus(ElementUtil
					.getShort(e, "CommStatus"));
			deviceStatusReal.setFaultMessage(e
					.getAttributeValue("FaultMessage"));
			deviceStatusReal.setOrgan(organSN);
			// 取缓存
			DasDeviceStatusReal cacheDeviceStatus = (DasDeviceStatusReal) CacheUtil
					.getCache(
							(e.getAttributeValue("StandardNumber") + "deviceStatus")
									.hashCode(), "dasCache");
			// 如果没有缓存
			if (cacheDeviceStatus == null) {
				list.add(deviceStatusReal); // 存入实时表数据集合，用于最后存入实时数据
				deviceStatusSN.add(deviceStatusReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
			} else {// 如果有缓存
				boolean flag = true; // 变化标志
				if (deviceStatusReal.getCommStatus().equals( // 判断是否变化
						cacheDeviceStatus.getCommStatus())
						&& (deviceStatusReal.getFaultMessage() != null ? deviceStatusReal
								.getFaultMessage() : "")
								.equals(cacheDeviceStatus.getFaultMessage() != null ? cacheDeviceStatus
										.getFaultMessage() : "")
						&& deviceStatusReal.getStatus().equals(
								cacheDeviceStatus.getStatus())) {
					flag = false;
				}
				if (flag) { // 如果变化则存入集合
					list.add(deviceStatusReal);
					deviceStatusSN.add(deviceStatusReal.getStandardNumber());
				}
			}

			if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_VD + "")) {
				DasVdReal vdReal = new DasVdReal();
				vdReal.setType(TypeDefinition.VD_TYPE_VD);
				vdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				vdReal.setRecTime(date);
				vdReal.setUpFluxb(ElementUtil.getInteger(e, "UpFluxB"));
				vdReal.setUpFluxm(ElementUtil.getInteger(e, "UpFluxM"));
				vdReal.setUpFluxms(ElementUtil.getInteger(e, "UpFluxMS"));
				vdReal.setUpFluxs(ElementUtil.getInteger(e, "UpFluxS"));
				vdReal.setUpFlux(ElementUtil.getInteger(e, "UpFlux"));
				vdReal.setDwFluxb(ElementUtil.getInteger(e, "DwFluxB"));
				vdReal.setDwFluxm(ElementUtil.getInteger(e, "DwFluxM"));
				vdReal.setDwFluxms(ElementUtil.getInteger(e, "DwFluxMS"));
				vdReal.setDwFluxs(ElementUtil.getInteger(e, "DwFluxS"));
				vdReal.setDwFlux(ElementUtil.getInteger(e, "DwFlux"));
				vdReal.setUpSpeed(ElementUtil.getInteger(e, "UpSpeed"));
				vdReal.setUpSpeedb(ElementUtil.getInteger(e, "UpSpeedB"));
				vdReal.setUpSpeedm(ElementUtil.getInteger(e, "UpSpeedM"));
				vdReal.setUpSpeeds(ElementUtil.getInteger(e, "UpSpeedS"));
				vdReal.setUpSpeedms(ElementUtil.getInteger(e, "UpSpeedMS"));
				vdReal.setDwSpeed(ElementUtil.getInteger(e, "DwSpeed"));
				vdReal.setDwSpeedb(ElementUtil.getInteger(e, "DwSpeedB"));
				vdReal.setDwSpeedm(ElementUtil.getInteger(e, "DwSpeedM"));
				vdReal.setDwSpeedms(ElementUtil.getInteger(e, "DwSpeedMS"));
				vdReal.setDwSpeeds(ElementUtil.getInteger(e, "DwSpeedS"));
				vdReal.setUpOcc(NumberUtil.getInteger(e
						.getAttributeValue("UpOcc")));
				vdReal.setUpOccb(NumberUtil.getInteger(e
						.getAttributeValue("UpOccB")));
				vdReal.setUpOccm(NumberUtil.getInteger(e
						.getAttributeValue("UpOccM")));
				vdReal.setUpOccms(NumberUtil.getInteger(e
						.getAttributeValue("UpOccMS")));
				vdReal.setUpOccs(NumberUtil.getInteger(e
						.getAttributeValue("UpOccS")));
				vdReal.setDwOccb(NumberUtil.getInteger(e
						.getAttributeValue("DwOccB")));
				vdReal.setDwOccm(NumberUtil.getInteger(e
						.getAttributeValue("DwOccM")));
				vdReal.setDwOccms(NumberUtil.getInteger(e
						.getAttributeValue("DwOccMS")));
				vdReal.setDwOccs(NumberUtil.getInteger(e
						.getAttributeValue("DwOccS")));
				vdReal.setDwOcc(NumberUtil.getInteger(e
						.getAttributeValue("DwOcc")));
				vdReal.setLaneNumber(ElementUtil.getShort(e, "LaneNum"));
				vdReal.setStatus(ElementUtil.getShort(e, "Status"));
				vdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				vdReal.setUpHeadway(NumberUtil.getInteger(e
						.getAttributeValue("UpHeadway")));
				vdReal.setDwHeadway(NumberUtil.getInteger(e
						.getAttributeValue("DwHeadway")));
				vdReal.setOrgan(organSN);
				// 取缓存
				DasVdReal cacheVd = (DasVdReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheVd == null) {
					vdList.add(vdReal); // 存入实时表数据集合，用于最后存入实时数据
					vdSN.add(vdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (vdReal.getCommStatus().equals( // 判断是否变化
							cacheVd.getCommStatus())
							&& vdReal.getStatus().equals(cacheVd.getStatus())
							&& vdReal.getRecTime().equals(cacheVd.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						vdList.add(vdReal); // 存入实时表数据集合，用于最后存入实时数据
						vdSN.add(vdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WST + "")) {
				DasWstReal wstReal = new DasWstReal();
				wstReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				wstReal.setRecTime(date);
				wstReal.setVisMax(ElementUtil.getInteger(e, "VisMax"));
				wstReal.setVisMin(ElementUtil.getInteger(e, "VisMin"));
				wstReal.setVisAvg(ElementUtil.getInteger(e, "VisAvg"));
				wstReal.setWsMax(e.getAttributeValue("WindSpeedMax"));
				wstReal.setWsMin(e.getAttributeValue("WindSpeedMin"));
				wstReal.setWsAvg(e.getAttributeValue("WindSpeedAvg"));
				wstReal.setWindDir(ElementUtil.getInteger(e, "WindDir"));
				wstReal.setAirTempMax(e.getAttributeValue("AirTempMax"));
				wstReal.setAirTempMin(e.getAttributeValue("AirTempMin"));
				wstReal.setAirTempAvg(e.getAttributeValue("AirTempAvg"));
				wstReal.setRoadTempMax(e.getAttributeValue("RoadTempMax"));
				wstReal.setRoadTempMin(e.getAttributeValue("RoadTempMin"));
				wstReal.setRoadTempAvg(e.getAttributeValue("RoadTempAvg"));
				wstReal.setHumiMax(ElementUtil.getShort(e, "HumiMax"));
				wstReal.setHumiMin(ElementUtil.getShort(e, "HumiMin"));
				wstReal.setHumiAvg(e.getAttributeValue("HumiAvg"));
				wstReal.setRainVol(e.getAttributeValue("RainVol"));
				wstReal.setSnowVol(e.getAttributeValue("SnowVol"));
				wstReal.setRoadSurface(ElementUtil.getShort(e, "RoadState"));
				wstReal.setStatus(ElementUtil.getShort(e, "Status"));
				wstReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				wstReal.setOrgan(organSN);
				// 取缓存
				DasWstReal cacheWst = (DasWstReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheWst == null) {
					wstList.add(wstReal); // 存入实时表数据集合，用于最后存入实时数据
					wstSN.add(wstReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (wstReal.getCommStatus().equals( // 判断是否变化
							cacheWst.getCommStatus())
							&& wstReal.getStatus().equals(cacheWst.getStatus())
							&& wstReal.getRecTime().equals(
									cacheWst.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						wstList.add(wstReal); // 存入实时表数据集合，用于最后存入实时数据
						wstSN.add(wstReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_COVI + "")) {
				DasCoviReal coviReal = new DasCoviReal();
				coviReal.setStandardNumber(e
						.getAttributeValue("StandardNumber"));
				coviReal.setRecTime(date);
				coviReal.setCo(e.getAttributeValue("COConct"));
				coviReal.setVi(e.getAttributeValue("Visibility"));
				coviReal.setStatus(ElementUtil.getShort(e, "Status"));
				coviReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				coviReal.setOrgan(organSN);
				// 取缓存
				DasCoviReal cacheCovi = (DasCoviReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheCovi == null) {
					coviList.add(coviReal); // 存入实时表数据集合，用于最后存入实时数据
					coviSN.add(coviReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (coviReal.getCommStatus().equals( // 判断是否变化
							cacheCovi.getCommStatus())
							&& coviReal.getStatus().equals(
									cacheCovi.getStatus())
							&& coviReal.getRecTime().equals(
									cacheCovi.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						coviList.add(coviReal); // 存入实时表数据集合，用于最后存入实时数据
						coviSN.add(coviReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_NOD + "")) {
				DasNodReal nodReal = new DasNodReal();
				nodReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				nodReal.setRecTime(date);
				nodReal.setNo(e.getAttributeValue("NOConct"));
				nodReal.setNo2(e.getAttributeValue("NO2Conct"));
				nodReal.setStatus(ElementUtil.getShort(e, "Status"));
				nodReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				nodReal.setOrgan(organSN);
				// 取缓存
				DasNodReal cacheNod = (DasNodReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheNod == null) {
					nodList.add(nodReal); // 存入实时表数据集合，用于最后存入实时数据
					nodSN.add(nodReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (nodReal.getCommStatus().equals( // 判断是否变化
							cacheNod.getCommStatus())
							&& nodReal.getStatus().equals(cacheNod.getStatus())
							&& nodReal.getRecTime().equals(
									cacheNod.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						nodList.add(nodReal); // 存入实时表数据集合，用于最后存入实时数据
						nodSN.add(nodReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LOLI + "")) {
				DasLoliReal loliReal = new DasLoliReal();
				loliReal.setStandardNumber(e
						.getAttributeValue("StandardNumber"));
				loliReal.setRecTime(date);
				loliReal.setLo(e.getAttributeValue("LOLumi"));
				loliReal.setLi(e.getAttributeValue("LILumi"));
				loliReal.setStatus(ElementUtil.getShort(e, "Status"));
				loliReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				loliReal.setOrgan(organSN);
				// 取缓存
				DasLoliReal cacheLoli = (DasLoliReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheLoli == null) {
					loliList.add(loliReal); // 存入实时表数据集合，用于最后存入实时数据
					loliSN.add(loliReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (loliReal.getCommStatus().equals( // 判断是否变化
							cacheLoli.getCommStatus())
							&& loliReal.getStatus().equals(
									cacheLoli.getStatus())
							&& loliReal.getRecTime().equals(
									cacheLoli.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						loliList.add(loliReal); // 存入实时表数据集合，用于最后存入实时数据
						loliSN.add(loliReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WS + "")) {
				DasWsReal wsReal = new DasWsReal();
				wsReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				wsReal.setRecTime(date);
				wsReal.setDirection(ElementUtil.getShort(e, "Direction"));
				wsReal.setSpeed(ElementUtil.getInteger(e, "Speed"));
				wsReal.setStatus(ElementUtil.getShort(e, "Status"));
				wsReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				wsReal.setOrgan(organSN);
				// 取缓存
				DasWsReal cacheWs = (DasWsReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheWs == null) {
					wsList.add(wsReal); // 存入实时表数据集合，用于最后存入实时数据
					wsSN.add(wsReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (wsReal.getCommStatus().equals( // 判断是否变化
							cacheWs.getCommStatus())
							&& wsReal.getStatus().equals(cacheWs.getStatus())
							&& wsReal.getRecTime().equals(cacheWs.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						wsList.add(wsReal); // 存入实时表数据集合，用于最后存入实时数据
						wsSN.add(wsReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_FAN + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_FAN);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_RD + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_RD);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WP + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_WP);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_RAIL + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_RAIL);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_IS + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_IS);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_LIGHT);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_PB + "")) {
				DasControlDeviceReal cdReal = new DasControlDeviceReal();
				cdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cdReal.setRecTime(date);
				cdReal.setWorkState(e.getAttributeValue("WorkState"));
				cdReal.setStatus(ElementUtil.getShort(e, "Status"));
				cdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cdReal.setType(TypeDefinition.DEVICE_TYPE_PB);
				cdReal.setOrgan(organSN);
				cache(cdList, cdSN, cdReal);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_CMS + "")) {
				DasCmsReal cmsReal = new DasCmsReal();
				cmsReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cmsReal.setRecTime(date);
				cmsReal.setDispCont(e.getAttributeValue("DispText"));
				cmsReal.setDispTime(ElementUtil.getInteger(e, "DispTime"));
				cmsReal.setColor(e.getAttributeValue("Color"));
				cmsReal.setSize(ElementUtil.getShort(e, "Size"));
				cmsReal.setFont(e.getAttributeValue("Font"));
				cmsReal.setStatus(ElementUtil.getShort(e, "Status"));
				cmsReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cmsReal.setOrgan(organSN);
				Map<String, List<DasCmsReal>> mapCms = (Map<String, List<DasCmsReal>>) CacheUtil
						.getCache(
								(e.getAttributeValue("StandardNumber") + "dasCache")
										.hashCode(), "dasCache");
				// 如果没有缓存
				if (mapCms == null) {
					cmsList.add(cmsReal); // 存入实时表数据集合，用于最后存入实时数据
					cmsSN.add(cmsReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					List<DasCmsReal> realCache = mapCms.get(e
							.getAttributeValue("StandardNumber"));
					boolean flag = true;
					for (DasCmsReal entity : realCache) {
						if ((cmsReal.getColor() != null ? cmsReal.getColor()
								: "").equals(entity.getColor() != null ? entity
								.getColor() : "")
								&& cmsReal.getCommStatus().equals(
										entity.getCommStatus())
								&& (cmsReal.getDispTime() != null ? cmsReal
										.getDispTime() : "").equals(entity
										.getDispTime() != null ? entity
										.getDispTime() : "")
								&& (cmsReal.getDispCont() != null ? cmsReal
										.getDispCont() : "").equals(entity
										.getDispCont() != null ? entity
										.getDispCont() : "")
								&& (cmsReal.getFont() != null ? cmsReal
										.getFont() : "").equals(entity
										.getFont() != null ? entity.getFont()
										: "")
								&& (cmsReal.getSize() != null ? cmsReal
										.getSize() : "").equals(entity
										.getSize() != null ? entity.getSize()
										: "")
								&& cmsReal.getStatus().equals(
										entity.getStatus())) {
							flag = false;
						}
					}
					if (flag) {
						cmsList.add(cmsReal);
						cmsSN.add(cmsReal.getStandardNumber());
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_TSL + "")) {
				DasTslReal tslReal = new DasTslReal();
				tslReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				tslReal.setRecTime(date);
				// tslReal.setGreenStatus(e.getAttributeValue("GreenStatus")
				// .equals("true") ? Short.valueOf("1") : Short
				// .valueOf("0"));
				// tslReal.setRedStatus(e.getAttributeValue("RedStatus").equals(
				// "true") ? Short.valueOf("1") : Short.valueOf("0"));
				// tslReal.setYellowStatus(e.getAttributeValue("YellowStatus")
				// .equals("true") ? Short.valueOf("1") : Short
				// .valueOf("0"));
				// tslReal.setTurnStatus(e.getAttributeValue("TurnStatus").equals(
				// "true") ? Short.valueOf("1") : Short.valueOf("0"));
				tslReal.setGreenStatus(Short.valueOf(e
						.getAttributeValue("GreenStatus")));
				tslReal.setRedStatus(Short.valueOf(e
						.getAttributeValue("RedStatus")));
				tslReal.setYellowStatus(Short.valueOf(e
						.getAttributeValue("YellowStatus")));
				tslReal.setTurnStatus(Short.valueOf(e
						.getAttributeValue("TurnStatus")));
				tslReal.setStatus(ElementUtil.getShort(e, "Status"));
				tslReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				tslReal.setOrgan(organSN);
				// 取缓存
				DasTslReal cacheTsl = (DasTslReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheTsl == null) {
					tslList.add(tslReal); // 存入实时表数据集合，用于最后存入实时数据
					tslSN.add(tslReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (tslReal.getCommStatus().equals( // 判断是否变化
							cacheTsl.getCommStatus())
							&& tslReal.getStatus().equals(cacheTsl.getStatus())
							&& tslReal.getGreenStatus().equals(
									cacheTsl.getGreenStatus())
							&& tslReal.getRedStatus().equals(
									cacheTsl.getRedStatus())
							&& tslReal.getTurnStatus().equals(
									cacheTsl.getTurnStatus())
							&& tslReal.getYellowStatus().equals(
									cacheTsl.getYellowStatus())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						tslList.add(tslReal); // 存入实时表数据集合，用于最后存入实时数据
						tslSN.add(tslReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LIL + "")) {
				DasLilReal lilReal = new DasLilReal();
				lilReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				lilReal.setRecTime(date);
				// lilReal.setBackArrowStatus(e.getAttributeValue(
				// "BackArrowStatus").equals("true") ? Short.valueOf("1")
				// : Short.valueOf("0"));
				// lilReal.setBackXStatus(e.getAttributeValue("BackXStatus")
				// .equals("true") ? Short.valueOf("1") : Short
				// .valueOf("0"));
				// lilReal.setFrontArrowStatus(e.getAttributeValue(
				// "FrontArrowStatus").equals("true") ? Short.valueOf("1")
				// : Short.valueOf("0"));
				// lilReal.setFrontXStatus(e.getAttributeValue("FrontXStatus")
				// .equals("true") ? Short.valueOf("1") : Short
				// .valueOf("0"));
				lilReal.setBackArrowStatus(Short.valueOf(e
						.getAttributeValue("BackArrowStatus")));
				lilReal.setBackXStatus(Short.valueOf(e
						.getAttributeValue("BackXStatus")));
				lilReal.setFrontArrowStatus(Short.valueOf(e
						.getAttributeValue("FrontArrowStatus")));
				lilReal.setFrontXStatus(Short.valueOf(e
						.getAttributeValue("FrontXStatus")));
				lilReal.setStatus(ElementUtil.getShort(e, "Status"));
				lilReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				lilReal.setOrgan(organSN);
				// 取缓存
				DasLilReal cacheLil = (DasLilReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheLil == null) {
					lilList.add(lilReal); // 存入实时表数据集合，用于最后存入实时数据
					lilSN.add(lilReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (lilReal.getCommStatus().equals( // 判断是否变化
							cacheLil.getCommStatus())
							&& lilReal.getStatus().equals(cacheLil.getStatus())
							&& lilReal.getBackArrowStatus().equals(
									cacheLil.getBackArrowStatus())
							&& lilReal.getBackXStatus().equals(
									cacheLil.getBackXStatus())
							&& lilReal.getFrontArrowStatus().equals(
									cacheLil.getFrontArrowStatus())
							&& lilReal.getFrontXStatus().equals(
									cacheLil.getFrontXStatus())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						lilList.add(lilReal); // 存入实时表数据集合，用于最后存入实时数据
						lilSN.add(lilReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")) {
				DasViDetectorReal viDetectorReal = new DasViDetectorReal();
				viDetectorReal.setStandardNumber(e
						.getAttributeValue("StandardNumber"));
				viDetectorReal.setRecTime(date);
				viDetectorReal.setVisAvg(ElementUtil.getInteger(e, "VisAvg"));
				viDetectorReal.setStatus(ElementUtil.getShort(e, "Status"));
				viDetectorReal.setCommStatus(ElementUtil.getShort(e,
						"CommStatus"));
				viDetectorReal.setOrgan(organSN);
				// 取缓存
				DasViDetectorReal cacheVid = (DasViDetectorReal) CacheUtil
						.getCache(
								(e.getAttributeValue("StandardNumber") + "dasCache")
										.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheVid == null) {
					vidList.add(viDetectorReal); // 存入实时表数据集合，用于最后存入实时数据
					vidSN.add(viDetectorReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (viDetectorReal.getCommStatus().equals( // 判断是否变化
							cacheVid.getCommStatus())
							&& viDetectorReal.getStatus().equals(
									cacheVid.getStatus())
							&& viDetectorReal.getRecTime().equals(
									cacheVid.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						vidList.add(viDetectorReal); // 存入实时表数据集合，用于最后存入实时数据
						vidSN.add(viDetectorReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")) {
				DasRoadDetectorReal rdReal = new DasRoadDetectorReal();
				rdReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				rdReal.setRecTime(date);
				rdReal.setRoadTempAvg(e.getAttributeValue("RoadTempAvg"));
				rdReal.setRainVol(e.getAttributeValue("RainVol"));
				rdReal.setSnowVol(e.getAttributeValue("SnowVol"));
				rdReal.setRoadSurface(ElementUtil.getShort(e, "RoadState"));
				rdReal.setStatus(ElementUtil.getShort(e, "Status"));
				rdReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				rdReal.setOrgan(organSN);
				// 取缓存
				DasRoadDetectorReal cacheRd = (DasRoadDetectorReal) CacheUtil
						.getCache(
								(e.getAttributeValue("StandardNumber") + "dasCache")
										.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheRd == null) {
					rdList.add(rdReal); // 存入实时表数据集合，用于最后存入实时数据
					rdSN.add(rdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (rdReal.getCommStatus().equals( // 判断是否变化
							cacheRd.getCommStatus())
							&& rdReal.getStatus().equals(cacheRd.getStatus())
							&& rdReal.getRecTime().equals(cacheRd.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						rdList.add(rdReal); // 存入实时表数据集合，用于最后存入实时数据
						rdSN.add(rdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_GPS + "")) {
				DasGpsReal gpsReal = new DasGpsReal();
				gpsReal.setStandardNumber(e.getAttributeValue("StandardNumber"));
				gpsReal.setRecTime(date);
				gpsReal.setAltitude(e.getAttributeValue("Altitude"));
				gpsReal.setBearing(e.getAttributeValue("Bearing"));
				gpsReal.setLatitude(e.getAttributeValue("Latitude"));
				gpsReal.setLongitude(e.getAttributeValue("Longitude"));
				Integer max = ElementUtil.getInteger(e, "MaxSpeed");
				if (null != max) {
					gpsReal.setMaxSpeed(max.intValue() * 1000);
				}
				Integer min = ElementUtil.getInteger(e, "MinSpeed");
				if (null != min) {
					gpsReal.setMinSpeed(min.intValue() * 1000);
				}
				Float floatSpeed = ElementUtil.getFloat(e, "Speed");
				if (null != floatSpeed) {
					int speed = Math.round(floatSpeed * 1000);
					gpsReal.setSpeed(Integer.valueOf(speed));
				}
				gpsReal.setStatus(ElementUtil.getShort(e, "Status"));
				gpsReal.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				gpsReal.setOrgan(organSN);
				// 取缓存
				DasGpsReal cacheGps = (DasGpsReal) CacheUtil.getCache((e
						.getAttributeValue("StandardNumber") + "dasCache")
						.hashCode(), "dasCache");
				// 如果没有缓存
				if (cacheGps == null) {
					gpsList.add(gpsReal); // 存入实时表数据集合，用于最后存入实时数据
					gpsSN.add(gpsReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
				} else {// 如果有缓存
					boolean flag = true; // 变化标志
					if (gpsReal.getCommStatus().equals( // 判断是否变化
							cacheGps.getCommStatus())
							&& gpsReal.getStatus().equals(cacheGps.getStatus())
							&& gpsReal.getRecTime().equals(
									cacheGps.getRecTime())) {
						flag = false;
					}
					if (flag) { // 如果变化则存入集合
						gpsList.add(gpsReal); // 存入实时表数据集合，用于最后存入实时数据
						gpsSN.add(gpsReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
					}
				}
			}
		}

		// 如果有变化量数据则查询表所有数据，根据sn匹配id放入数据，最后批量删除
		List<DasDeviceStatusReal> deviceStatusAll = new ArrayList<DasDeviceStatusReal>();
		List<DasVdReal> vdAll = new ArrayList<DasVdReal>();
		List<DasWstReal> wstAll = new ArrayList<DasWstReal>();
		List<DasCoviReal> coviAll = new ArrayList<DasCoviReal>();
		List<DasNodReal> nodAll = new ArrayList<DasNodReal>();
		List<DasLoliReal> loliAll = new ArrayList<DasLoliReal>();
		List<DasControlDeviceReal> cdAll = new ArrayList<DasControlDeviceReal>();
		List<DasWsReal> wsAll = new ArrayList<DasWsReal>();
		List<DasTslReal> tslAll = new ArrayList<DasTslReal>();
		List<DasLilReal> lilAll = new ArrayList<DasLilReal>();
		List<DasViDetectorReal> vidAll = new ArrayList<DasViDetectorReal>();
		List<DasRoadDetectorReal> rdAll = new ArrayList<DasRoadDetectorReal>();
		List<DasGpsReal> gpsAll = new ArrayList<DasGpsReal>();

		if (list.size() > 0) {
			deviceStatusAll = dasDeviceStatusRealDAO.findDasAll();
		}
		if (vdList.size() > 0) {
			vdAll = dasVdRealDAO.findDasAll();
		}
		if (wstList.size() > 0) {
			wstAll = dasWstRealDAO.findDasAll();
		}
		if (coviList.size() > 0) {
			coviAll = dasCoviRealDAO.findDasAll();
		}
		if (nodList.size() > 0) {
			nodAll = dasNodRealDAO.findDasAll();
		}
		if (loliList.size() > 0) {
			loliAll = dasLoliRealDAO.findDasAll();
		}
		if (cdList.size() > 0) {
			cdAll = dasControlDeviceRealDAO.findDasAll();
		}
		if (wsList.size() > 0) {
			wsAll = dasWsRealDAO.findDasAll();
		}
		if (tslList.size() > 0) {
			tslAll = dasTslRealDAO.findDasAll();
		}
		if (lilList.size() > 0) {
			lilAll = dasLilRealDAO.findDasAll();
		}
		if (vidList.size() > 0) {
			vidAll = dasViDetectorRealDAO.findDasAll();
		}
		if (rdList.size() > 0) {
			rdAll = dasRoadDetectorRealDAO.findDasAll();
		}
		if (gpsList.size() > 0) {
			gpsAll = dasGpsRealDAO.findDasAll();
		}

		// 插入历史表集合
		List<DasDeviceStatus> listHistory = new ArrayList<DasDeviceStatus>();
		List<DasCms> listCmsHistory = new ArrayList<DasCms>();
		List<DasVd> listVdHistory = new ArrayList<DasVd>();
		List<DasWst> listWstHistory = new ArrayList<DasWst>();
		List<DasCovi> listCoviHistory = new ArrayList<DasCovi>();
		List<DasNod> listNodHistory = new ArrayList<DasNod>();
		List<DasLoli> listLoliHistory = new ArrayList<DasLoli>();
		List<DasControlDevice> listCdHistory = new ArrayList<DasControlDevice>();
		List<DasWs> listWsHistory = new ArrayList<DasWs>();
		List<DasTsl> listTslHistory = new ArrayList<DasTsl>();
		List<DasLil> listLilHistory = new ArrayList<DasLil>();
		List<DasViDetector> listVidHistory = new ArrayList<DasViDetector>();
		List<DasRoadDetector> listRdHistory = new ArrayList<DasRoadDetector>();
		List<DasGps> listGpsHistory = new ArrayList<DasGps>();

		// 变化量sn集合迭代
		Iterator<String> deviceStatusIt = deviceStatusSN.iterator();
		Iterator<String> cmsIt = cmsSN.iterator();
		Iterator<String> vdIt = vdSN.iterator();
		Iterator<String> wstIt = wstSN.iterator();
		Iterator<String> coviIt = coviSN.iterator();
		Iterator<String> nodIt = nodSN.iterator();
		Iterator<String> loliIt = loliSN.iterator();
		Iterator<String> cdIt = cdSN.iterator();
		Iterator<String> wsIt = wsSN.iterator();
		Iterator<String> tslIt = tslSN.iterator();
		Iterator<String> lilIt = lilSN.iterator();
		Iterator<String> vidIt = vidSN.iterator();
		Iterator<String> rdIt = rdSN.iterator();
		Iterator<String> gpsIt = gpsSN.iterator();

		// 设备状态
		while (deviceStatusIt.hasNext()) {
			String sn = deviceStatusIt.next();
			List<DasDeviceStatusReal> dds = new ArrayList<DasDeviceStatusReal>();
			for (DasDeviceStatusReal entity : list) {
				if (sn.equals(entity.getStandardNumber())) {
					dds.add(entity);
				}
			}
			// 放入缓存 如果多个就放最后一个
			CacheUtil.putCache((sn + "deviceStatus").hashCode(),
					dds.get(dds.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有其他车检器则直接放入历史车检器列表
			if (dds.size() > 1) {
				System.out
						.println("WARN----------------device_status same standard_number das report more than one");
				System.out
						.println("WARN----------------device_status same standard_number das report size:"
								+ dds.size()
								+ ", device_status standard_number:"
								+ dds.get(0).getStandardNumber());
				dds.remove(dds.size() - 1);
				for (DasDeviceStatusReal entity : dds) {
					list.remove(entity);
					DasDeviceStatus deviceStatus = setDeviceStatusEntity(entity);
					listHistory.add(deviceStatus);
				}
			}
		}
		List<DasDeviceStatusReal> insertDSs = new ArrayList<DasDeviceStatusReal>();
		// 放入历史表集合
		for (DasDeviceStatusReal po : list) {
			boolean flag = true;
			for (DasDeviceStatusReal entity : deviceStatusAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasDeviceStatus ds = setDeviceStatusEntity(entity);
					listHistory.add(ds);
					// 更新实时表数据操作
					entity.setCommStatus(po.getCommStatus());
					entity.setFaultMessage(po.getFaultMessage());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setType(po.getType());
					break;
				}
			}
			if (flag) {
				insertDSs.add(po);
			}
		}

		if (insertDSs.size() > 0) {
			dasDeviceStatusRealDAO.batchInsert(insertDSs);
		}

		// 情报板
		while (cmsIt.hasNext()) {
			String sn = cmsIt.next();
			List<DasCmsReal> dcr = new ArrayList<DasCmsReal>();
			Map<String, List<DasCmsReal>> map = new HashMap<String, List<DasCmsReal>>();
			for (DasCmsReal entity : cmsList) {
				if (sn.equals(entity.getStandardNumber())) {
					dcr.add(entity);
				}
			}
			// 用于更新缓存
			map.put(sn, dcr);
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(), map, "dasCache");// 放入缓存
			params.put("standardNumber", sn);
			// 根据sn查询实时表数据
			List<DasCmsReal> cmsReals = dasCmsRealDAO
					.findByPropertysDas(params);
			params.clear();
			for (DasCmsReal entity : cmsReals) {
				DasCms cms = new DasCms();
				cms.setColor(entity.getColor());
				cms.setCommStatus(entity.getCommStatus());
				cms.setDispCont(entity.getDispCont());
				cms.setDispTime(entity.getDispTime());
				cms.setFont(entity.getFont());
				cms.setOrgan(entity.getOrgan());
				cms.setRecTime(entity.getRecTime());
				cms.setReserve(entity.getReserve());
				cms.setSize(entity.getSize());
				cms.setStandardNumber(entity.getStandardNumber());
				cms.setStatus(entity.getStatus());
				listCmsHistory.add(cms);
			}
			// 删除实时表数据
			dasCmsRealDAO.deleteBySN(sn);
		}

		// 车检器
		while (vdIt.hasNext()) {
			String sn = vdIt.next();
			List<DasVdReal> dvr = new ArrayList<DasVdReal>();
			for (DasVdReal entity : vdList) {
				if (sn.equals(entity.getStandardNumber())) {
					dvr.add(entity);
				}
			}
			// 放入缓存 如果存在多个车检器取最后一个车检器放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dvr.get(dvr.size() - 1), "dasCache");

			// 如果DAS上报的同一个SN还有其他车检器则直接放入历史车检器列表
			if (dvr.size() > 1) {
				System.out
						.println("WARN----------------vechicle_detector same standard_number das report more than one");
				System.out
						.println("WARN----------------vechicle_detector same standard_number das report size:"
								+ dvr.size()
								+ ", vehicle_detector standard_number:"
								+ dvr.get(0).getStandardNumber());
				dvr.remove(dvr.size() - 1);
				for (DasVdReal entity : dvr) {
					vdList.remove(entity);
					DasVd vd = setVdEntity(entity);
					listVdHistory.add(vd);
				}
			}
		}

		List<DasVdReal> insertVds = new ArrayList<DasVdReal>();
		for (DasVdReal po : vdList) {
			boolean flag = true;
			for (DasVdReal entity : vdAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					// 历史表插入数据
					flag = false;
					DasVd vd = setVdEntity(entity);
					listVdHistory.add(vd);
					// 更新实时表操作
					entity.setCommStatus(po.getCommStatus());
					entity.setDwFlux(po.getDwFlux());
					entity.setDwFluxb(po.getDwFluxb());
					entity.setDwFluxm(po.getDwFluxm());
					entity.setDwFluxms(po.getDwFluxms());
					entity.setDwFluxs(po.getDwFluxs());
					entity.setDwHeadway(po.getDwHeadway());
					entity.setDwOcc(po.getDwOcc());
					entity.setDwOccb(po.getDwOccb());
					entity.setDwOccm(po.getDwOccm());
					entity.setDwOccms(po.getDwOccms());
					entity.setDwOccs(po.getDwOccs());
					entity.setDwSpeed(po.getDwSpeed());
					entity.setDwSpeedb(po.getDwSpeedb());
					entity.setDwSpeedm(po.getDwSpeedm());
					entity.setDwSpeedms(po.getDwSpeedms());
					entity.setDwSpeeds(po.getDwSpeeds());
					entity.setLaneNumber(po.getLaneNumber());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setType(po.getType());
					entity.setUpFlux(po.getUpFlux());
					entity.setUpFluxb(po.getUpFluxb());
					entity.setUpFluxm(po.getUpFluxm());
					entity.setUpFluxms(po.getUpFluxms());
					entity.setUpFluxs(po.getUpFluxs());
					entity.setUpHeadway(po.getUpHeadway());
					entity.setUpOcc(po.getUpOcc());
					entity.setUpOccb(po.getUpOccb());
					entity.setUpOccm(po.getUpOccm());
					entity.setUpOccms(po.getUpOccms());
					entity.setUpOccs(po.getUpOccs());
					entity.setUpSpeed(po.getUpSpeed());
					entity.setUpSpeedb(po.getUpSpeedb());
					entity.setUpSpeedm(po.getUpSpeedm());
					entity.setUpSpeedms(po.getUpSpeedms());
					entity.setUpSpeeds(po.getUpSpeeds());
					break;
				}
			}
			if (flag) {
				insertVds.add(po);
			}
		}
		if (insertVds.size() > 0) {
			dasVdRealDAO.batchInsert(insertVds);
		}

		// 气象检测器
		while (wstIt.hasNext()) {
			String sn = wstIt.next();
			List<DasWstReal> dwr = new ArrayList<DasWstReal>();
			for (DasWstReal entity : wstList) {
				if (sn.equals(entity.getStandardNumber())) {
					dwr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dwr.get(dwr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dwr.size() > 1) {
				System.out
						.println("WARN----------------weather_stat same standard_number das report more than one");
				System.out
						.println("WARN----------------weather_stat same standard_number das report size:"
								+ dwr.size()
								+ ", weather_stat standard_number:"
								+ dwr.get(0).getStandardNumber());
				dwr.remove(dwr.size() - 1);
				for (DasWstReal entity : dwr) {
					wstList.remove(entity);
					DasWst wst = setWstEntity(entity);
					listWstHistory.add(wst);
				}
			}
		}
		List<DasWstReal> insertWst = new ArrayList<DasWstReal>();
		for (DasWstReal po : wstList) {
			boolean flag = true;
			for (DasWstReal entity : wstAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasWst wst = setWstEntity(entity);
					listWstHistory.add(wst);
					// 更新实时表操作
					entity.setAirTempAvg(po.getAirTempAvg());
					entity.setAirTempMax(po.getAirTempMax());
					entity.setAirTempMin(po.getAirTempMin());
					entity.setCommStatus(po.getCommStatus());
					entity.setHumiAvg(po.getHumiAvg());
					entity.setHumiMax(po.getHumiMax());
					entity.setHumiMin(po.getHumiMin());
					entity.setOrgan(po.getOrgan());
					entity.setRainVol(po.getRainVol());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setRoadSurface(po.getRoadSurface());
					entity.setRoadTempAvg(po.getRoadTempAvg());
					entity.setRoadTempMax(po.getRoadTempMax());
					entity.setRoadTempMin(po.getRoadTempMin());
					entity.setSnowVol(po.getSnowVol());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setVisAvg(po.getVisAvg());
					entity.setVisMax(po.getVisMax());
					entity.setVisMin(po.getVisMin());
					entity.setWindDir(po.getWindDir());
					entity.setWsAvg(po.getWsAvg());
					entity.setWsMax(po.getWsMax());
					entity.setWsMin(po.getWsMin());
					break;
				}
			}
			if (flag) {
				insertWst.add(po);
			}
		}
		if (insertWst.size() > 0) {
			dasWstRealDAO.batchInsert(insertWst);
		}

		// 一氧化碳能见度检测器
		while (coviIt.hasNext()) {
			String sn = coviIt.next();
			List<DasCoviReal> dcr = new ArrayList<DasCoviReal>();
			for (DasCoviReal entity : coviList) {
				if (sn.equals(entity.getStandardNumber())) {
					dcr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dcr.get(dcr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dcr.size() > 1) {
				System.out
						.println("WARN----------------covi same standard_number das report more than one");
				System.out
						.println("WARN----------------covi same standard_number das report size:"
								+ dcr.size()
								+ ", covi standard_number:"
								+ dcr.get(0).getStandardNumber());
				dcr.remove(dcr.size() - 1);
				for (DasCoviReal entity : dcr) {
					coviList.remove(entity);
					DasCovi wst = setCoviEntity(entity);
					listCoviHistory.add(wst);
				}
			}
		}
		List<DasCoviReal> insertCovi = new ArrayList<DasCoviReal>();
		for (DasCoviReal po : coviList) {
			boolean flag = true;
			for (DasCoviReal entity : coviAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasCovi covi = setCoviEntity(entity);
					listCoviHistory.add(covi);
					// 更新实时表操作
					entity.setCo(po.getCo());
					entity.setCommStatus(po.getCommStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setVi(po.getVi());
					break;
				}
			}
			if (flag) {
				insertCovi.add(po);
			}
		}
		if (insertCovi.size() > 0) {
			dasCoviRealDAO.batchInsert(coviList);
		}

		// 氮氧化物检测器
		while (nodIt.hasNext()) {
			String sn = nodIt.next();
			List<DasNodReal> dnr = new ArrayList<DasNodReal>();
			for (DasNodReal entity : nodList) {
				if (sn.equals(entity.getStandardNumber())) {
					dnr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dnr.get(dnr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dnr.size() > 1) {
				System.out
						.println("WARN----------------no_detector same standard_number das report more than one");
				System.out
						.println("WARN----------------no_detector same standard_number das report size:"
								+ dnr.size()
								+ ", no_detector standard_number:"
								+ dnr.get(0).getStandardNumber());
				dnr.remove(dnr.size() - 1);
				for (DasNodReal entity : dnr) {
					nodList.remove(entity);
					DasNod nod = setNodEntity(entity);
					listNodHistory.add(nod);
				}
			}
		}
		List<DasNodReal> insertNod = new ArrayList<DasNodReal>();
		for (DasNodReal po : nodList) {
			boolean flag = true;
			for (DasNodReal entity : nodAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasNod nod = setNodEntity(entity);
					listNodHistory.add(nod);
					// 更新实时表数据
					entity.setCommStatus(po.getCommStatus());
					entity.setNo(po.getNo());
					entity.setNo2(po.getNo2());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertNod.add(po);
			}
		}
		if (insertNod.size() > 0) {
			dasNodRealDAO.batchInsert(insertNod);
		}

		// 光强检测器
		while (loliIt.hasNext()) {
			String sn = loliIt.next();
			List<DasLoliReal> dlr = new ArrayList<DasLoliReal>();
			for (DasLoliReal entity : loliList) {
				if (sn.equals(entity.getStandardNumber())) {
					dlr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dlr.get(dlr.size() - 1), "dasCache");
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dlr.get(dlr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dlr.size() > 1) {
				System.out
						.println("WARN----------------loli same standard_number das report more than one");
				System.out
						.println("WARN----------------loli same standard_number das report size:"
								+ dlr.size()
								+ ", loli standard_number:"
								+ dlr.get(0).getStandardNumber());
				dlr.remove(dlr.size() - 1);
				for (DasLoliReal entity : dlr) {
					loliList.remove(entity);
					DasLoli loli = setLoliEntity(entity);
					listLoliHistory.add(loli);
				}
			}

		}
		List<DasLoliReal> insertLoli = new ArrayList<DasLoliReal>();
		for (DasLoliReal po : loliList) {
			boolean flag = true;
			for (DasLoliReal entity : loliAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasLoli loli = setLoliEntity(entity);
					listLoliHistory.add(loli);
					// 更新实时表数据
					entity.setCommStatus(po.getCommStatus());
					entity.setLi(po.getLi());
					entity.setLo(po.getLo());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertLoli.add(po);
			}
		}
		if (insertLoli.size() > 0) {
			dasLoliRealDAO.batchInsert(insertLoli);
		}

		// 控制设备
		while (cdIt.hasNext()) {
			String sn = cdIt.next();
			List<DasControlDeviceReal> dcr = new ArrayList<DasControlDeviceReal>();
			for (DasControlDeviceReal entity : cdList) {
				if (sn.equals(entity.getStandardNumber())) {
					dcr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dcr.get(dcr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dcr.size() > 1) {
				System.out
						.println("WARN----------------contorl_device same standard_number das report more than one");
				System.out
						.println("WARN----------------contorl_device same standard_number das report size:"
								+ dcr.size()
								+ ", contorl_device standard_number:"
								+ dcr.get(0).getStandardNumber());
				dcr.remove(dcr.size() - 1);
				for (DasControlDeviceReal entity : dcr) {
					cdList.remove(entity);
					DasControlDevice cd = setCdEntity(entity);
					listCdHistory.add(cd);
				}
			}
		}
		List<DasControlDeviceReal> insertCd = new ArrayList<DasControlDeviceReal>();
		for (DasControlDeviceReal po : cdList) {
			boolean flag = true;
			for (DasControlDeviceReal entity : cdAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasControlDevice cd = setCdEntity(entity);
					listCdHistory.add(cd);
					// 更新实时表数据
					entity.setCommStatus(po.getCommStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setType(po.getType());
					entity.setWorkState(po.getWorkState());
					break;
				}
			}
			if (flag) {
				insertCd.add(po);
			}
		}
		if (insertCd.size() > 0) {
			dasControlDeviceRealDAO.batchInsert(insertCd);
		}

		// 风速风向检测器
		while (wsIt.hasNext()) {
			String sn = wsIt.next();
			List<DasWsReal> dwr = new ArrayList<DasWsReal>();
			for (DasWsReal entity : wsList) {
				if (sn.equals(entity.getStandardNumber())) {
					dwr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dwr.get(dwr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dwr.size() > 1) {
				System.out
						.println("WARN----------------ws same standard_number das report more than one");
				System.out
						.println("WARN----------------ws same standard_number das report size:"
								+ dwr.size()
								+ ", ws standard_number:"
								+ dwr.get(0).getStandardNumber());
				dwr.remove(dwr.size() - 1);
				for (DasWsReal entity : dwr) {
					wsList.remove(entity);
					DasWs ws = setWsEntity(entity);
					listWsHistory.add(ws);
				}
			}
		}
		List<DasWsReal> insertWs = new ArrayList<DasWsReal>();
		for (DasWsReal po : wsList) {
			boolean flag = true;
			for (DasWsReal entity : wsAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasWs ws = setWsEntity(entity);
					listWsHistory.add(ws);
					// 更新实时表数据
					entity.setCommStatus(po.getCommStatus());
					entity.setDirection(po.getDirection());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setSpeed(po.getSpeed());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertWs.add(po);
			}
		}
		if (insertWs.size() > 0) {
			dasWsRealDAO.batchInsert(insertWs);
		}

		// 交通信号灯
		while (tslIt.hasNext()) {
			String sn = tslIt.next();
			List<DasTslReal> dtr = new ArrayList<DasTslReal>();
			for (DasTslReal entity : tslList) {
				if (sn.equals(entity.getStandardNumber())) {
					dtr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dtr.get(dtr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dtr.size() > 1) {
				System.out
						.println("WARN----------------tsl same standard_number das report more than one");
				System.out
						.println("WARN----------------tsl same standard_number das report size:"
								+ dtr.size()
								+ ", tsl standard_number:"
								+ dtr.get(0).getStandardNumber());
				dtr.remove(dtr.size() - 1);
				for (DasTslReal entity : dtr) {
					tslList.remove(entity);
					DasTsl tsl = setTslEntity(entity);
					listTslHistory.add(tsl);
				}
			}
		}
		List<DasTslReal> insertTsl = new ArrayList<DasTslReal>();
		for (DasTslReal po : tslList) {
			boolean flag = true;
			for (DasTslReal entity : tslAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasTsl tsl = setTslEntity(entity);
					listTslHistory.add(tsl);
					// 更新实时表数据
					entity.setCommStatus(po.getCommStatus());
					entity.setGreenStatus(po.getGreenStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setRedStatus(po.getRedStatus());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					entity.setTurnStatus(po.getTurnStatus());
					entity.setYellowStatus(po.getYellowStatus());
					break;
				}
			}
			if (flag) {
				insertTsl.add(po);
			}
		}
		if (insertTsl.size() > 0) {
			dasTslRealDAO.batchInsert(insertTsl);
		}

		// 车道指示器
		while (lilIt.hasNext()) {
			String sn = lilIt.next();
			List<DasLilReal> dlr = new ArrayList<DasLilReal>();
			for (DasLilReal entity : lilList) {
				if (sn.equals(entity.getStandardNumber())) {
					dlr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dlr.get(dlr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dlr.size() > 1) {
				System.out
						.println("WARN----------------lil same standard_number das report more than one");
				System.out
						.println("WARN----------------lil same standard_number das report size:"
								+ dlr.size()
								+ ", lil standard_number:"
								+ dlr.get(0).getStandardNumber());
				dlr.remove(dlr.size() - 1);
				for (DasLilReal entity : dlr) {
					lilList.remove(entity);
					DasLil lil = setLilEntity(entity);
					listLilHistory.add(lil);
				}
			}
		}
		List<DasLilReal> insertLil = new ArrayList<DasLilReal>();
		for (DasLilReal po : lilList) {
			boolean flag = true;
			for (DasLilReal entity : lilAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasLil lil = setLilEntity(entity);
					listLilHistory.add(lil);
					// 更新实时表数据
					entity.setBackArrowStatus(po.getBackArrowStatus());
					entity.setBackXStatus(po.getBackXStatus());
					entity.setCommStatus(po.getCommStatus());
					entity.setFrontArrowStatus(po.getFrontArrowStatus());
					entity.setFrontXStatus(po.getFrontXStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertLil.add(po);
			}
		}
		if (insertLil.size() > 0) {
			dasLilRealDAO.batchInsert(insertLil);
		}

		// 能见度仪
		while (vidIt.hasNext()) {
			String sn = vidIt.next();
			List<DasViDetectorReal> dvdr = new ArrayList<DasViDetectorReal>();
			for (DasViDetectorReal entity : vidList) {
				if (sn.equals(entity.getStandardNumber())) {
					dvdr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dvdr.get(dvdr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dvdr.size() > 1) {
				System.out
						.println("WARN----------------vid same standard_number das report more than one");
				System.out
						.println("WARN----------------vid same standard_number das report size:"
								+ dvdr.size()
								+ ", vid standard_number:"
								+ dvdr.get(0).getStandardNumber());
				dvdr.remove(dvdr.size() - 1);
				for (DasViDetectorReal entity : dvdr) {
					vidList.remove(entity);
					DasViDetector vid = setVidEntity(entity);
					listVidHistory.add(vid);
				}
			}
		}
		List<DasViDetectorReal> insertVid = new ArrayList<DasViDetectorReal>();
		for (DasViDetectorReal po : vidList) {
			boolean flag = true;
			for (DasViDetectorReal entity : vidAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasViDetector vid = setVidEntity(entity);
					listVidHistory.add(vid);
					// 更新实时表数据
					entity.setVisAvg(po.getVisAvg());
					entity.setCommStatus(po.getCommStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertVid.add(po);
			}
		}
		if (insertVid.size() > 0) {
			dasViDetectorRealDAO.batchInsert(insertVid);
		}

		// 路面检测器
		while (rdIt.hasNext()) {
			String sn = rdIt.next();
			List<DasRoadDetectorReal> drdr = new ArrayList<DasRoadDetectorReal>();
			for (DasRoadDetectorReal entity : rdList) {
				if (sn.equals(entity.getStandardNumber())) {
					drdr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					drdr.get(drdr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (drdr.size() > 1) {
				System.out
						.println("WARN----------------rd same standard_number das report more than one");
				System.out
						.println("WARN----------------rd same standard_number das report size:"
								+ drdr.size()
								+ ", rd standard_number:"
								+ drdr.get(0).getStandardNumber());
				drdr.remove(drdr.size() - 1);
				for (DasRoadDetectorReal entity : drdr) {
					rdList.remove(entity);
					DasRoadDetector rd = setRdEntity(entity);
					listRdHistory.add(rd);
				}
			}
		}
		List<DasRoadDetectorReal> insertRd = new ArrayList<DasRoadDetectorReal>();
		for (DasRoadDetectorReal po : rdList) {
			boolean flag = true;
			for (DasRoadDetectorReal entity : rdAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasRoadDetector rd = setRdEntity(entity);
					listRdHistory.add(rd);
					// 更新实时表数据
					entity.setRainVol(po.getRainVol());
					entity.setRoadSurface(po.getRoadSurface());
					entity.setRoadTempAvg(po.getRoadTempAvg());
					entity.setSnowVol(po.getSnowVol());
					entity.setCommStatus(po.getCommStatus());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setReserve(po.getReserve());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertRd.add(po);
			}
		}
		if (insertRd.size() > 0) {
			dasRoadDetectorRealDAO.batchInsert(insertRd);
		}

		// GPS设备
		while (gpsIt.hasNext()) {
			String sn = gpsIt.next();
			List<DasGpsReal> dgr = new ArrayList<DasGpsReal>();
			for (DasGpsReal entity : gpsList) {
				if (sn.equals(entity.getStandardNumber())) {
					dgr.add(entity);
				}
			}
			// 放入缓存
			CacheUtil.putCache((sn + "dasCache").hashCode(),
					dgr.get(dgr.size() - 1), "dasCache");
			// 如果DAS上报的同一个SN还有多个则直接放入历史列表
			if (dgr.size() > 1) {
				System.out
						.println("WARN----------------gps same standard_number das report more than one");
				System.out
						.println("WARN----------------gps same standard_number das report size:"
								+ dgr.size()
								+ ", gps standard_number:"
								+ dgr.get(0).getStandardNumber());
				dgr.remove(dgr.size() - 1);
				for (DasGpsReal entity : dgr) {
					gpsList.remove(entity);
					DasGps gps = setGpsEntity(entity);
					listGpsHistory.add(gps);
				}
			}
		}
		List<DasGpsReal> insertGps = new ArrayList<DasGpsReal>();
		for (DasGpsReal po : gpsList) {
			boolean flag = true;
			for (DasGpsReal entity : gpsAll) {
				if (entity.getStandardNumber().equals(po.getStandardNumber())) {
					flag = false;
					DasGps gps = setGpsEntity(entity);
					listGpsHistory.add(gps);
					// 更新实时表操作
					entity.setAltitude(po.getAltitude());
					entity.setBearing(po.getBearing());
					entity.setCommStatus(po.getCommStatus());
					entity.setLatitude(po.getLatitude());
					entity.setLongitude(po.getLongitude());
					entity.setMaxSpeed(po.getMaxSpeed());
					entity.setMinSpeed(po.getMinSpeed());
					entity.setOrgan(po.getOrgan());
					entity.setRecTime(po.getRecTime());
					entity.setSpeed(po.getSpeed());
					entity.setStandardNumber(po.getStandardNumber());
					entity.setStatus(po.getStatus());
					break;
				}
			}
			if (flag) {
				insertGps.add(po);
			}
		}
		if (insertGps.size() > 0) {
			dasGpsRealDAO.batchInsert(insertGps);
		}

		// 变化量数据批量插入实时表
		dasCmsRealDAO.batchInsert(cmsList);
		// 实时表数据插入历史表
		dasDeviceStatusDAO.batchInsert(listHistory);
		dasCmsDAO.batchInsert(listCmsHistory);
		dasVdDAO.batchInsert(listVdHistory);
		dasWstDAO.batchInsert(listWstHistory);
		dasCoviDAO.batchInsert(listCoviHistory);
		dasNodDAO.batchInsert(listNodHistory);
		dasLoliDAO.batchInsert(listLoliHistory);
		dasCdDAO.batchInsert(listCdHistory);
		dasWsDAO.batchInsert(listWsHistory);
		dasTslDAO.batchInsert(listTslHistory);
		dasLilDAO.batchInsert(listLilHistory);
		dasViDetectorDAO.batchInsert(listVidHistory);
		dasRoadDetectorDAO.batchInsert(listRdHistory);
		dasGpsDAO.batchInsert(listGpsHistory);
	}

	private DasVd setVdEntity(DasVdReal entity) {
		DasVd vd = new DasVd();
		vd.setCommStatus(entity.getCommStatus());
		vd.setDwFlux(entity.getDwFlux());
		vd.setDwFluxb(entity.getDwFluxb());
		vd.setDwFluxm(entity.getDwFluxm());
		vd.setDwFluxms(entity.getDwFluxms());
		vd.setDwFluxs(entity.getDwFluxs());
		vd.setDwHeadway(entity.getDwHeadway());
		vd.setDwOcc(entity.getDwOcc());
		vd.setDwOccb(entity.getDwOccb());
		vd.setDwOccm(entity.getDwOccm());
		vd.setDwOccms(entity.getDwOccms());
		vd.setDwOccs(entity.getDwOccs());
		vd.setDwSpeed(entity.getDwSpeed());
		vd.setDwSpeedb(entity.getDwSpeedb());
		vd.setDwSpeedm(entity.getDwSpeedm());
		vd.setDwSpeedms(entity.getDwSpeedms());
		vd.setDwSpeeds(entity.getDwSpeeds());
		vd.setLaneNumber(entity.getLaneNumber());
		vd.setOrgan(entity.getOrgan());
		vd.setRecTime(entity.getRecTime());
		vd.setReserve(entity.getReserve());
		vd.setStandardNumber(entity.getStandardNumber());
		vd.setStatus(entity.getStatus());
		vd.setType(entity.getType());
		vd.setUpFlux(entity.getUpFlux());
		vd.setUpFluxb(entity.getUpFluxb());
		vd.setUpFluxm(entity.getUpFluxm());
		vd.setUpFluxms(entity.getUpFluxms());
		vd.setUpFluxs(entity.getUpFluxs());
		vd.setUpHeadway(entity.getUpHeadway());
		vd.setUpOcc(entity.getUpOcc());
		vd.setUpOccb(entity.getUpOccb());
		vd.setUpOccm(entity.getUpOccm());
		vd.setUpOccms(entity.getUpOccms());
		vd.setUpOccs(entity.getUpOccs());
		vd.setUpSpeed(entity.getUpSpeed());
		vd.setUpSpeedb(entity.getUpSpeedb());
		vd.setUpSpeedm(entity.getUpSpeedm());
		vd.setUpSpeedms(entity.getUpSpeedms());
		vd.setUpSpeeds(entity.getUpSpeeds());
		return vd;
	}

	private DasDeviceStatus setDeviceStatusEntity(DasDeviceStatusReal entity) {
		DasDeviceStatus ds = new DasDeviceStatus();
		ds.setCommStatus(entity.getCommStatus());
		ds.setFaultMessage(entity.getFaultMessage());
		ds.setOrgan(entity.getOrgan());
		ds.setRecTime(entity.getRecTime());
		ds.setStandardNumber(entity.getStandardNumber());
		ds.setStatus(entity.getStatus());
		ds.setType(entity.getType());
		return ds;
	}

	private DasWst setWstEntity(DasWstReal entity) {
		DasWst wst = new DasWst();
		wst.setAirTempAvg(entity.getAirTempAvg());
		wst.setAirTempMax(entity.getAirTempMax());
		wst.setAirTempMin(entity.getAirTempMin());
		wst.setCommStatus(entity.getCommStatus());
		wst.setHumiAvg(entity.getHumiAvg());
		wst.setHumiMax(entity.getHumiMax());
		wst.setHumiMin(entity.getHumiMin());
		wst.setOrgan(entity.getOrgan());
		wst.setRainVol(entity.getRainVol());
		wst.setRecTime(entity.getRecTime());
		wst.setReserve(entity.getReserve());
		wst.setRoadSurface(entity.getRoadSurface());
		wst.setRoadTempAvg(entity.getRoadTempAvg());
		wst.setRoadTempMax(entity.getRoadTempMax());
		wst.setRoadTempMin(entity.getRoadTempMin());
		wst.setSnowVol(entity.getSnowVol());
		wst.setStandardNumber(entity.getStandardNumber());
		wst.setStatus(entity.getStatus());
		wst.setVisAvg(entity.getVisAvg());
		wst.setVisMax(entity.getVisMax());
		wst.setVisMin(entity.getVisMin());
		wst.setWindDir(entity.getWindDir());
		wst.setWsAvg(entity.getWsAvg());
		wst.setWsMax(entity.getWsMax());
		wst.setWsMin(entity.getWsMin());
		return wst;
	}

	private DasCovi setCoviEntity(DasCoviReal entity) {
		DasCovi covi = new DasCovi();
		covi.setCo(entity.getCo());
		covi.setCommStatus(entity.getCommStatus());
		covi.setOrgan(entity.getOrgan());
		covi.setRecTime(entity.getRecTime());
		covi.setReserve(entity.getReserve());
		covi.setStandardNumber(entity.getStandardNumber());
		covi.setStatus(entity.getStatus());
		covi.setVi(entity.getVi());
		return covi;
	}

	private DasNod setNodEntity(DasNodReal entity) {
		DasNod nod = new DasNod();
		nod.setCommStatus(entity.getCommStatus());
		nod.setNo(entity.getNo());
		nod.setNo2(entity.getNo2());
		nod.setOrgan(entity.getOrgan());
		nod.setRecTime(entity.getRecTime());
		nod.setReserve(nod.getReserve());
		nod.setStandardNumber(entity.getStandardNumber());
		nod.setStatus(entity.getStatus());
		return nod;
	}

	private DasLoli setLoliEntity(DasLoliReal entity) {
		DasLoli loli = new DasLoli();
		loli.setCommStatus(entity.getCommStatus());
		loli.setLi(entity.getLi());
		loli.setLo(entity.getLo());
		loli.setOrgan(entity.getOrgan());
		loli.setRecTime(entity.getRecTime());
		loli.setReserve(entity.getReserve());
		loli.setStandardNumber(entity.getStandardNumber());
		loli.setStatus(entity.getStatus());
		return loli;
	}

	private DasWs setWsEntity(DasWsReal entity) {
		DasWs ws = new DasWs();
		ws.setCommStatus(entity.getCommStatus());
		ws.setDirection(entity.getDirection());
		ws.setOrgan(entity.getOrgan());
		ws.setRecTime(entity.getRecTime());
		ws.setReserve(entity.getReserve());
		ws.setSpeed(entity.getSpeed());
		ws.setStandardNumber(entity.getStandardNumber());
		ws.setStatus(entity.getStatus());
		return ws;
	}

	private DasTsl setTslEntity(DasTslReal entity) {
		DasTsl tsl = new DasTsl();
		tsl.setCommStatus(entity.getCommStatus());
		tsl.setGreenStatus(entity.getGreenStatus());
		tsl.setOrgan(entity.getOrgan());
		tsl.setRecTime(entity.getRecTime());
		tsl.setRedStatus(entity.getRedStatus());
		tsl.setReserve(entity.getReserve());
		tsl.setStandardNumber(entity.getStandardNumber());
		tsl.setStatus(entity.getStatus());
		tsl.setTurnStatus(entity.getTurnStatus());
		tsl.setYellowStatus(entity.getYellowStatus());
		return tsl;
	}

	private DasControlDevice setCdEntity(DasControlDeviceReal entity) {
		DasControlDevice cd = new DasControlDevice();
		cd.setCommStatus(entity.getCommStatus());
		cd.setOrgan(entity.getOrgan());
		cd.setRecTime(entity.getRecTime());
		cd.setReserve(entity.getReserve());
		cd.setStandardNumber(entity.getStandardNumber());
		cd.setStatus(entity.getStatus());
		cd.setType(entity.getType());
		cd.setWorkState(entity.getWorkState());
		return cd;
	}

	private DasLil setLilEntity(DasLilReal entity) {
		DasLil lil = new DasLil();
		lil.setBackArrowStatus(entity.getBackArrowStatus());
		lil.setBackXStatus(entity.getBackXStatus());
		lil.setCommStatus(entity.getCommStatus());
		lil.setFrontArrowStatus(entity.getFrontArrowStatus());
		lil.setFrontXStatus(entity.getFrontXStatus());
		lil.setOrgan(entity.getOrgan());
		lil.setRecTime(entity.getRecTime());
		lil.setReserve(entity.getReserve());
		lil.setStandardNumber(entity.getStandardNumber());
		lil.setStatus(entity.getStatus());
		return lil;
	}

	private DasViDetector setVidEntity(DasViDetectorReal entity) {
		DasViDetector vid = new DasViDetector();
		vid.setVisAvg(entity.getVisAvg());
		vid.setCommStatus(entity.getCommStatus());
		vid.setOrgan(entity.getOrgan());
		vid.setRecTime(entity.getRecTime());
		vid.setReserve(entity.getReserve());
		vid.setStandardNumber(entity.getStandardNumber());
		vid.setStatus(entity.getStatus());
		return vid;
	}

	private DasRoadDetector setRdEntity(DasRoadDetectorReal entity) {
		DasRoadDetector rd = new DasRoadDetector();
		rd.setRainVol(entity.getRainVol());
		rd.setRoadSurface(entity.getRoadSurface());
		rd.setRoadTempAvg(entity.getRoadTempAvg());
		rd.setSnowVol(entity.getSnowVol());
		rd.setCommStatus(entity.getCommStatus());
		rd.setOrgan(entity.getOrgan());
		rd.setRecTime(entity.getRecTime());
		rd.setReserve(entity.getReserve());
		rd.setStandardNumber(entity.getStandardNumber());
		rd.setStatus(entity.getStatus());
		return rd;
	}

	private DasGps setGpsEntity(DasGpsReal entity) {
		DasGps gps = new DasGps();
		gps.setAltitude(entity.getAltitude());
		gps.setBearing(entity.getBearing());
		gps.setCommStatus(entity.getCommStatus());
		gps.setLatitude(entity.getLatitude());
		gps.setLongitude(entity.getLongitude());
		gps.setMaxSpeed(entity.getMaxSpeed());
		gps.setMinSpeed(entity.getMinSpeed());
		gps.setOrgan(entity.getOrgan());
		gps.setRecTime(entity.getRecTime());
		gps.setSpeed(entity.getSpeed());
		gps.setStandardNumber(entity.getStandardNumber());
		gps.setStatus(entity.getStatus());
		return gps;
	}

	@Override
	public void saveData(List<Element> data) throws BusinessException {
		// 所有设备的状态
		List<DasDeviceStatus> list = new LinkedList<DasDeviceStatus>();

		List<DasCms> cmsList = new LinkedList<DasCms>();
		List<DasControlDevice> cdList = new LinkedList<DasControlDevice>();
		List<DasCovi> coviList = new LinkedList<DasCovi>();
		List<DasLoli> loliList = new LinkedList<DasLoli>();
		List<DasNod> nodList = new LinkedList<DasNod>();
		List<DasVd> vdList = new LinkedList<DasVd>();
		List<DasWs> wsList = new LinkedList<DasWs>();
		List<DasWst> wstList = new LinkedList<DasWst>();
		List<DasTsl> tslList = new LinkedList<DasTsl>();
		List<DasLil> lilList = new LinkedList<DasLil>();

		// 获取平台根机构SN
		String organSN = organDAO.getRootOrgan().getStandardNumber();
		for (Element e : data) {
			Timestamp date = ElementUtil.getTimestamp(e, "RecTime");
			DasDeviceStatus deviceStatus = new DasDeviceStatus();
			deviceStatus.setStandardNumber(e
					.getAttributeValue("StandardNumber"));
			deviceStatus.setRecTime(date);
			deviceStatus.setType(ElementUtil.getInteger(e, "Type"));
			deviceStatus.setStatus(ElementUtil.getShort(e, "Status"));
			deviceStatus.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
			deviceStatus.setFaultMessage(e.getAttributeValue("FaultMessage"));
			deviceStatus.setOrgan(organSN);
			list.add(deviceStatus);

			if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_VD + "")) {
				DasVd vd = new DasVd();
				vd.setType((short) 1);
				vd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				vd.setRecTime(date);
				vd.setUpFluxb(ElementUtil.getInteger(e, "UpFluxB"));
				vd.setUpFluxm(ElementUtil.getInteger(e, "UpFluxM"));
				vd.setUpFluxms(ElementUtil.getInteger(e, "UpFluxMS"));
				vd.setUpFluxs(ElementUtil.getInteger(e, "UpFluxS"));
				vd.setUpFlux(ElementUtil.getInteger(e, "UpFlux"));
				vd.setDwFluxb(ElementUtil.getInteger(e, "DwFluxB"));
				vd.setDwFluxm(ElementUtil.getInteger(e, "DwFluxM"));
				vd.setDwFluxms(ElementUtil.getInteger(e, "DwFluxMS"));
				vd.setDwFluxs(ElementUtil.getInteger(e, "DwFluxS"));
				vd.setDwFlux(ElementUtil.getInteger(e, "DwFlux"));
				vd.setUpSpeed(ElementUtil.getInteger(e, "UpSpeed"));
				vd.setUpSpeedb(ElementUtil.getInteger(e, "UpSpeedB"));
				vd.setUpSpeedm(ElementUtil.getInteger(e, "UpSpeedM"));
				vd.setUpSpeeds(ElementUtil.getInteger(e, "UpSpeedS"));
				vd.setUpSpeedms(ElementUtil.getInteger(e, "UpSpeedMS"));
				vd.setDwSpeed(ElementUtil.getInteger(e, "DwSpeed"));
				vd.setDwSpeedb(ElementUtil.getInteger(e, "DwSpeedB"));
				vd.setDwSpeedm(ElementUtil.getInteger(e, "DwSpeedM"));
				vd.setDwSpeedms(ElementUtil.getInteger(e, "DwSpeedMS"));
				vd.setDwSpeeds(ElementUtil.getInteger(e, "DwSpeedS"));
				vd.setUpOcc(NumberUtil.getInteger(e.getAttributeValue("UpOcc")));
				vd.setUpOccb(NumberUtil.getInteger(e
						.getAttributeValue("UpOccB")));
				vd.setUpOccm(NumberUtil.getInteger(e
						.getAttributeValue("UpOccM")));
				vd.setUpOccms(NumberUtil.getInteger(e
						.getAttributeValue("UpOccMS")));
				vd.setUpOccs(NumberUtil.getInteger(e
						.getAttributeValue("UpOccS")));
				vd.setDwOccb(NumberUtil.getInteger(e
						.getAttributeValue("DwOccB")));
				vd.setDwOccm(NumberUtil.getInteger(e
						.getAttributeValue("DwOccM")));
				vd.setDwOccms(NumberUtil.getInteger(e
						.getAttributeValue("DwOccMS")));
				vd.setDwOccs(NumberUtil.getInteger(e
						.getAttributeValue("DwOccS")));
				vd.setDwOcc(NumberUtil.getInteger(e.getAttributeValue("DwOcc")));
				vd.setLaneNumber(ElementUtil.getShort(e, "LaneNum"));
				vd.setStatus(ElementUtil.getShort(e, "Status"));
				vd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				vd.setUpHeadway(NumberUtil.getInteger(e
						.getAttributeValue("UpHeadway")));
				vd.setDwHeadway(NumberUtil.getInteger(e
						.getAttributeValue("DwHeadway")));
				vd.setOrgan(organSN);
				vdList.add(vd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WST + "")) {
				DasWst wst = new DasWst();
				wst.setStandardNumber(e.getAttributeValue("StandardNumber"));
				wst.setRecTime(date);
				wst.setVisMax(ElementUtil.getInteger(e, "VisMax"));
				wst.setVisMin(ElementUtil.getInteger(e, "VisMin"));
				wst.setVisAvg(ElementUtil.getInteger(e, "VisAvg"));
				wst.setWsMax(e.getAttributeValue("WindSpeedMax"));
				wst.setWsMin(e.getAttributeValue("WindSpeedMin"));
				wst.setWsAvg(e.getAttributeValue("WindSpeedAvg"));
				wst.setWindDir(ElementUtil.getInteger(e, "WindDir"));
				wst.setAirTempMax(e.getAttributeValue("AirTempMax"));
				wst.setAirTempMin(e.getAttributeValue("AirTempMin"));
				wst.setAirTempAvg(e.getAttributeValue("AirTempAvg"));
				wst.setRoadTempMax(e.getAttributeValue("RoadTempMax"));
				wst.setRoadTempMin(e.getAttributeValue("RoadTempMin"));
				wst.setRoadTempAvg(e.getAttributeValue("RoadTempAvg"));
				wst.setHumiMax(ElementUtil.getShort(e, "HumiMax"));
				wst.setHumiMin(ElementUtil.getShort(e, "HumiMin"));
				wst.setHumiAvg(e.getAttributeValue("HumiAvg"));
				wst.setRainVol(e.getAttributeValue("RainVol"));
				wst.setSnowVol(e.getAttributeValue("SnowVol"));
				wst.setRoadSurface(ElementUtil.getShort(e, "RoadState"));
				wst.setStatus(ElementUtil.getShort(e, "Status"));
				wst.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				wst.setOrgan(organSN);
				wstList.add(wst);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_COVI + "")) {
				DasCovi covi = new DasCovi();
				covi.setStandardNumber(e.getAttributeValue("StandardNumber"));
				covi.setRecTime(date);
				covi.setCo(e.getAttributeValue("COConct"));
				covi.setVi(e.getAttributeValue("Visibility"));
				covi.setStatus(ElementUtil.getShort(e, "Status"));
				covi.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				covi.setOrgan(organSN);
				coviList.add(covi);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_NOD + "")) {
				DasNod nod = new DasNod();
				nod.setStandardNumber(e.getAttributeValue("StandardNumber"));
				nod.setRecTime(date);
				nod.setNo(e.getAttributeValue("NOConct"));
				nod.setNo2(e.getAttributeValue("NO2Conct"));
				nod.setStatus(ElementUtil.getShort(e, "Status"));
				nod.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				nod.setOrgan(organSN);
				nodList.add(nod);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LOLI + "")) {
				DasLoli loli = new DasLoli();
				loli.setStandardNumber(e.getAttributeValue("StandardNumber"));
				loli.setRecTime(date);
				loli.setLo(e.getAttributeValue("LOLumi"));
				loli.setLi(e.getAttributeValue("LILumi"));
				loli.setStatus(ElementUtil.getShort(e, "Status"));
				loli.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				loli.setOrgan(organSN);
				loliList.add(loli);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WS + "")) {
				DasWs ws = new DasWs();
				ws.setStandardNumber(e.getAttributeValue("StandardNumber"));
				ws.setRecTime(date);
				ws.setDirection(ElementUtil.getShort(e, "Direction"));
				ws.setSpeed(ElementUtil.getInteger(e, "Speed"));
				ws.setStatus(ElementUtil.getShort(e, "Status"));
				ws.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				ws.setOrgan(organSN);
				wsList.add(ws);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_FAN + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_FAN);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_RD + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_RD);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_WP + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_WP);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_RAIL + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_RAIL);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_IS + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_IS);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_LIGHT);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_PB + "")) {
				DasControlDevice cd = new DasControlDevice();
				cd.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cd.setRecTime(date);
				cd.setWorkState(e.getAttributeValue("WorkState"));
				cd.setStatus(ElementUtil.getShort(e, "Status"));
				cd.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cd.setType(TypeDefinition.DEVICE_TYPE_PB);
				cd.setOrgan(organSN);
				cdList.add(cd);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_CMS + "")) {
				DasCms cms = new DasCms();
				cms.setStandardNumber(e.getAttributeValue("StandardNumber"));
				cms.setRecTime(date);
				cms.setDispCont(e.getAttributeValue("DispText"));
				cms.setDispTime(ElementUtil.getInteger(e, "DispTime"));
				cms.setColor(e.getAttributeValue("Color"));
				cms.setSize(ElementUtil.getShort(e, "Size"));
				cms.setFont(e.getAttributeValue("Font"));
				cms.setStatus(ElementUtil.getShort(e, "Status"));
				cms.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				cms.setOrgan(organSN);
				cmsList.add(cms);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_TSL + "")) {
				DasTsl tsl = new DasTsl();
				tsl.setStandardNumber(e.getAttributeValue("StandardNumber"));
				tsl.setRecTime(date);
				// tsl.setGreenStatus(Short.valueOf(e
				// .getAttributeValue("GreenStatus")));
				// tsl.setRedStatus(Short.valueOf(e.getAttributeValue("RedStatus")));
				// tsl.setYellowStatus(Short.valueOf(e
				// .getAttributeValue("YellowStatus")));
				// tsl.setTurnStatus(Short.valueOf(e
				// .getAttributeValue("TurnStatus")));
				tsl.setGreenStatus(e.getAttributeValue("GreenStatus").equals(
						"true") ? Short.valueOf("1") : Short.valueOf("0"));
				tsl.setRedStatus(e.getAttributeValue("RedStatus")
						.equals("true") ? Short.valueOf("1") : Short
						.valueOf("0"));
				tsl.setYellowStatus(e.getAttributeValue("YellowStatus").equals(
						"true") ? Short.valueOf("1") : Short.valueOf("0"));
				tsl.setTurnStatus(e.getAttributeValue("TurnStatus").equals(
						"true") ? Short.valueOf("1") : Short.valueOf("0"));
				tsl.setStatus(ElementUtil.getShort(e, "Status"));
				tsl.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				tsl.setOrgan(organSN);
				tslList.add(tsl);
			} else if (e.getAttributeValue("Type").equals(
					TypeDefinition.DEVICE_TYPE_LIL + "")) {
				DasLil lil = new DasLil();
				lil.setStandardNumber(e.getAttributeValue("StandardNumber"));
				lil.setRecTime(date);
				// lil.setBackArrowStatus(Short.valueOf(e
				// .getAttributeValue("BackArrowStatus")));
				// lil.setBackXStatus(Short.valueOf(e
				// .getAttributeValue("BackXStatus")));
				// lil.setFrontArrowStatus(Short.valueOf(e
				// .getAttributeValue("FrontArrowStatus")));
				// lil.setFrontXStatus(Short.valueOf(e
				// .getAttributeValue("FrontXStatus")));
				lil.setBackArrowStatus(e.getAttributeValue("BackArrowStatus")
						.equals("true") ? Short.valueOf("1") : Short
						.valueOf("0"));
				lil.setBackXStatus(e.getAttributeValue("BackXStatus").equals(
						"true") ? Short.valueOf("1") : Short.valueOf("0"));
				lil.setFrontArrowStatus(e.getAttributeValue("FrontArrowStatus")
						.equals("true") ? Short.valueOf("1") : Short
						.valueOf("0"));
				lil.setFrontXStatus(e.getAttributeValue("FrontXStatus").equals(
						"true") ? Short.valueOf("1") : Short.valueOf("0"));
				lil.setStatus(ElementUtil.getShort(e, "Status"));
				lil.setCommStatus(ElementUtil.getShort(e, "CommStatus"));
				lil.setOrgan(organSN);
				lilList.add(lil);
			}
		}

		dasDeviceStatusDAO.batchInsert(list);

		dasCdDAO.batchInsert(cdList);
		dasCmsDAO.batchInsert(cmsList);
		dasCoviDAO.batchInsert(coviList);
		dasLoliDAO.batchInsert(loliList);
		dasNodDAO.batchInsert(nodList);
		dasWsDAO.batchInsert(wsList);
		dasWstDAO.batchInsert(wstList);
		dasTslDAO.batchInsert(tslList);
		dasLilDAO.batchInsert(lilList);
		// 车检器通道合并处理
		if (vdChannelMap.isEmpty()) {
			List<SubVehicleDetector> detectors = vdDAO.listSubVehicleDetector();
			for (SubVehicleDetector channel : detectors) {
				vdChannelMap.put(channel.getStandardNumber(), channel
						.getParent().getStandardNumber());
			}
		}
		// 合并
		if (!vdChannelMap.isEmpty()) {
			// 新增的父车检器列表
			List<DasVd> parents = new LinkedList<DasVd>();
			// 父车检器创建标志
			boolean createFlag = false;
			for (DasVd vd : vdList) {
				String parentSn = vdChannelMap.get(vd.getStandardNumber());
				createFlag = false;
				// 存在通道
				if (StringUtils.isNotBlank(parentSn)) {
					for (DasVd parent : parents) {
						// 已经创建车检器
						if (parent.getStandardNumber().equals(parentSn)) {
							createFlag = true;
							parent.setDwFlux(NumberUtil.plusInteger(
									parent.getDwFlux(), vd.getDwFlux()));
							parent.setDwFluxb(NumberUtil.plusInteger(
									parent.getDwFluxb(), vd.getDwFluxb()));
							parent.setDwFluxm(NumberUtil.plusInteger(
									parent.getDwFluxm(), vd.getDwFluxm()));
							parent.setDwFluxms(NumberUtil.plusInteger(
									parent.getDwFluxms(), vd.getDwFluxms()));
							parent.setDwFluxs(NumberUtil.plusInteger(
									parent.getDwFluxs(), vd.getDwFluxs()));
							parent.setDwHeadway(NumberUtil.avgInteger(
									parent.getDwHeadway(), vd.getDwHeadway()));
							parent.setDwOcc(NumberUtil.avgInteger(
									parent.getDwOcc(), vd.getDwOcc()));
							parent.setDwOccb(NumberUtil.avgInteger(
									parent.getDwOccb(), vd.getDwOccb()));
							parent.setDwOccm(NumberUtil.avgInteger(
									parent.getDwOccm(), vd.getDwOccm()));
							parent.setDwOccms(NumberUtil.avgInteger(
									parent.getDwOccms(), vd.getDwOccms()));
							parent.setDwOccs(NumberUtil.avgInteger(
									parent.getDwOccs(), vd.getDwOccs()));
							parent.setDwSpeed(NumberUtil.avgInteger(
									parent.getDwSpeed(), vd.getDwSpeed()));
							parent.setDwSpeedb(NumberUtil.avgInteger(
									parent.getDwSpeedb(), vd.getDwSpeedb()));
							parent.setDwSpeedm(NumberUtil.avgInteger(
									parent.getDwSpeedm(), vd.getDwSpeedm()));
							parent.setDwSpeedms(NumberUtil.avgInteger(
									parent.getDwSpeedms(), vd.getDwSpeedms()));
							parent.setDwSpeeds(NumberUtil.avgInteger(
									parent.getDwSpeeds(), vd.getDwSpeeds()));

							parent.setUpFlux(NumberUtil.plusInteger(
									parent.getUpFlux(), vd.getUpFlux()));
							parent.setUpFluxb(NumberUtil.plusInteger(
									parent.getUpFluxb(), vd.getUpFluxb()));
							parent.setUpFluxm(NumberUtil.plusInteger(
									parent.getUpFluxm(), vd.getUpFluxm()));
							parent.setUpFluxms(NumberUtil.plusInteger(
									parent.getUpFluxms(), vd.getUpFluxms()));
							parent.setUpFluxs(NumberUtil.plusInteger(
									parent.getUpFluxs(), vd.getUpFluxs()));
							parent.setUpHeadway(NumberUtil.avgInteger(
									parent.getUpHeadway(), vd.getUpHeadway()));
							parent.setUpOcc(NumberUtil.avgInteger(
									parent.getUpOcc(), vd.getUpOcc()));
							parent.setUpOccb(NumberUtil.avgInteger(
									parent.getUpOccb(), vd.getUpOccb()));
							parent.setUpOccm(NumberUtil.avgInteger(
									parent.getUpOccm(), vd.getUpOccm()));
							parent.setUpOccms(NumberUtil.avgInteger(
									parent.getUpOccms(), vd.getUpOccms()));
							parent.setUpOccs(NumberUtil.avgInteger(
									parent.getUpOccs(), vd.getUpOccs()));
							parent.setUpSpeed(NumberUtil.avgInteger(
									parent.getUpSpeed(), vd.getUpSpeed()));
							parent.setUpSpeedb(NumberUtil.avgInteger(
									parent.getUpSpeedb(), vd.getUpSpeedb()));
							parent.setUpSpeedm(NumberUtil.avgInteger(
									parent.getUpSpeedm(), vd.getUpSpeedm()));
							parent.setUpSpeedms(NumberUtil.avgInteger(
									parent.getUpSpeedms(), vd.getUpSpeedms()));
							parent.setUpSpeeds(NumberUtil.avgInteger(
									parent.getUpSpeeds(), vd.getUpSpeeds()));
						}
					}
					// 创建一个新的车检器，合并下面通道的采集值
					if (!createFlag) {
						DasVd parent = vd.clone();
						parent.setStandardNumber(parentSn);
						parent.setType(TypeDefinition.VD_TYPE_VD);
						parents.add(parent);
					}
					// 设置为通道
					vd.setType(TypeDefinition.VD_TYPE_CHANNEL);
				}
				// 不存在通道
				else {
					// do nothing
				}
			}
			vdList.addAll(parents);
		}
		dasVdDAO.batchInsert(vdList);
	}

	@Override
	public List<Element> listDeviceInfo(List<Element> devices)
			throws BusinessException {
		List<String> vdSNs = new LinkedList<String>();
		List<String> wsSNs = new LinkedList<String>();
		List<String> wstSNs = new LinkedList<String>();
		List<String> loliSNs = new LinkedList<String>();
		List<String> noSNs = new LinkedList<String>();
		List<String> cmsSNs = new LinkedList<String>();
		List<String> coviSNs = new LinkedList<String>();
		List<String> cdSNs = new LinkedList<String>();
		List<String> lilSNs = new LinkedList<String>();
		List<String> tslSNs = new LinkedList<String>();

		// 解析接口数据，将要查询的设备分类
		for (Element device : devices) {
			if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(device
					.getAttributeValue("Type"))) {
				vdSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(device
					.getAttributeValue("Type"))) {
				wsSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(device
					.getAttributeValue("Type"))) {
				wstSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(device
					.getAttributeValue("Type"))) {
				loliSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(device
					.getAttributeValue("Type"))) {
				noSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_CMS + "").equals(device
					.getAttributeValue("Type"))) {
				cmsSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(device
					.getAttributeValue("Type"))) {
				coviSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_FAN + "").equals(device
					.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_LIGHT + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_RD + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_WP + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_RAIL + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_IS + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_PB + "").equals(device
							.getAttributeValue("Type"))) {
				cdSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_LIL + "").equals(device
					.getAttributeValue("Type"))) {
				lilSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_TSL + "").equals(device
					.getAttributeValue("Type"))) {
				tslSNs.add(device.getAttributeValue("StandardNumber"));
			}
		}
		// 返回对象列表
		List<Element> rtnList = new LinkedList<Element>();
		if (vdSNs.size() > 0) {
			List<DasVd> rows = dasVdDAO.listVdInfo(vdSNs);
			buildVD(rows, rtnList);
		}
		if (wsSNs.size() > 0) {
			List<DasWs> rows = dasWsDAO.listWsInfo(wsSNs);
			buildWS(rows, rtnList);
		}
		if (wstSNs.size() > 0) {
			List<DasWst> rows = dasWstDAO.listWstInfo(wstSNs);
			buildWST(rows, rtnList);
		}
		if (loliSNs.size() > 0) {
			List<DasLoli> rows = dasLoliDAO.listLoliInfo(loliSNs);
			buildLoli(rows, rtnList);
		}
		if (noSNs.size() > 0) {
			List<DasNod> rows = dasNodDAO.listNodInfo(noSNs);
			buildNOD(rows, rtnList);
		}
		if (cmsSNs.size() > 0) {
			List<DasCms> rows = dasCmsDAO.listCmsInfo(cmsSNs);
			Map<String, List<DasCms>> map = new HashMap<String, List<DasCms>>();
			for (DasCms cms : rows) {
				if (!map.containsKey(cms.getStandardNumber())) {
					List<DasCms> list = new ArrayList<DasCms>();
					map.put(cms.getStandardNumber(), list);
					list.add(cms);
				} else {
					map.get(cms.getStandardNumber()).add(cms);
				}
			}
			buildCMS(map, rtnList);
		}
		if (coviSNs.size() > 0) {
			List<DasCovi> rows = dasCoviDAO.listCoviInfo(coviSNs);
			buildCovi(rows, rtnList);
		}
		if (cdSNs.size() > 0) {
			List<DasControlDevice> rows = dasCdDAO.listCdInfo(cdSNs);
			buildCD(rows, rtnList);
		}
		if (lilSNs.size() > 0) {
			List<DasLil> rows = dasLilDAO.listLilInfo(lilSNs);
			buildLil(rows, rtnList);
		}
		if (tslSNs.size() > 0) {
			List<DasTsl> rows = dasTslDAO.listTslInfo(tslSNs);
			buildTsl(rows, rtnList);
		}

		return rtnList;
	}

	@Override
	public List<Element> listDeviceInfo1(List<Element> devices)
			throws BusinessException {
		List<String> vdSNs = new LinkedList<String>();
		List<String> wsSNs = new LinkedList<String>();
		List<String> wstSNs = new LinkedList<String>();
		List<String> loliSNs = new LinkedList<String>();
		List<String> noSNs = new LinkedList<String>();
		List<String> cmsSNs = new LinkedList<String>();
		List<String> coviSNs = new LinkedList<String>();
		List<String> cdSNs = new LinkedList<String>();
		List<String> lilSNs = new LinkedList<String>();
		List<String> tslSNs = new LinkedList<String>();
		List<String> vidSNs = new LinkedList<String>();
		List<String> rdSNs = new LinkedList<String>();
		List<String> gpsSNs = new LinkedList<String>();

		// 不在线das管辖下map
		List<Das> dases = dasDAO.findAll();
		List<UserSessionDas> dasSession = userSessionDAO.listOnlineDas();
		Map<String, UserSessionDas> dasSessionMap = new HashMap<String, UserSessionDas>();
		for (UserSessionDas das : dasSession) {
			dasSessionMap.put(das.getDas().getId(), das);
		}
		// 最终不需要的设备map
		Map<String, TmDevice> map = new HashMap<String, TmDevice>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		for (Das das : dases) {
			if (null == dasSessionMap.get(das.getId())) {
				params.put("das.id", das.getId());
				List<TmDevice> tds = tmDeviceDAO.findByPropertys(params);
				params.clear();
				for (TmDevice td : tds) {
					map.put(td.getStandardNumber(), td);
				}
			}
		}

		// 解析接口数据，将要查询的设备分类
		for (Element device : devices) {
			if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(device
					.getAttributeValue("Type"))) {
				vdSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(device
					.getAttributeValue("Type"))) {
				wsSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(device
					.getAttributeValue("Type"))) {
				wstSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(device
					.getAttributeValue("Type"))) {
				loliSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(device
					.getAttributeValue("Type"))) {
				noSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_CMS + "").equals(device
					.getAttributeValue("Type"))) {
				cmsSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(device
					.getAttributeValue("Type"))) {
				coviSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_FAN + "").equals(device
					.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_LIGHT + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_RD + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_WP + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_RAIL + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_IS + "").equals(device
							.getAttributeValue("Type"))
					|| (TypeDefinition.DEVICE_TYPE_PB + "").equals(device
							.getAttributeValue("Type"))) {
				cdSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_LIL + "").equals(device
					.getAttributeValue("Type"))) {
				lilSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_TSL + "").equals(device
					.getAttributeValue("Type"))) {
				tslSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
					.equals(device.getAttributeValue("Type"))) {
				vidSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
					.equals(device.getAttributeValue("Type"))) {
				rdSNs.add(device.getAttributeValue("StandardNumber"));
			} else if ((TypeDefinition.DEVICE_TYPE_GPS + "").equals(device
					.getAttributeValue("Type"))) {
				gpsSNs.add(device.getAttributeValue("StandardNumber"));
			}
		}
		// 返回对象列表
		List<Element> rtnList = new LinkedList<Element>();
		// 返回对象集合
		List<DasVdReal> vdRows = new ArrayList<DasVdReal>();
		List<DasWsReal> wsRows = new ArrayList<DasWsReal>();
		List<DasWstReal> wstRows = new ArrayList<DasWstReal>();
		List<DasLoliReal> loliRows = new ArrayList<DasLoliReal>();
		List<DasNodReal> noRows = new ArrayList<DasNodReal>();
		Map<String, List<DasCmsReal>> cmsRows = new HashMap<String, List<DasCmsReal>>();
		List<DasCoviReal> coviRows = new ArrayList<DasCoviReal>();
		List<DasControlDeviceReal> cdRows = new ArrayList<DasControlDeviceReal>();
		List<DasLilReal> lilRows = new ArrayList<DasLilReal>();
		List<DasTslReal> tslRows = new ArrayList<DasTslReal>();
		List<DasViDetectorReal> vidRows = new ArrayList<DasViDetectorReal>();
		List<DasRoadDetectorReal> rdRows = new ArrayList<DasRoadDetectorReal>();
		List<DasGpsReal> gpsRows = new ArrayList<DasGpsReal>();

		// 在DAS掉线所管辖的设备中，缓存和数据都没有查出数据则不返回
		boolean flag = true;

		if (vdSNs.size() > 0) {
			for (String standardNumber : vdSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasVdReal mapVd = (DasVdReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 有缓存则去缓存
					if (mapVd != null) {
						vdRows.add(mapVd);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						vdRows.addAll(dasVdRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasVdReal vdReal = new DasVdReal();
					vdReal.setCommStatus((short) 1);
					vdReal.setStatus((short) 1);
					vdReal.setStandardNumber(standardNumber);
					if (mapVd != null) {
						vdReal.setRecTime(mapVd.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasVdReal> vdList = dasVdRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (vdList.size() > 0) {
							vdReal.setRecTime(vdList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						vdRows.add(vdReal);
					}
				}
			}
			buildVD1(vdRows, rtnList);
		}
		if (wsSNs.size() > 0) {
			for (String standardNumber : wsSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasWsReal mapWs = (DasWsReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 有缓存则去缓存
					if (mapWs != null) {
						wsRows.add(mapWs);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						wsRows.addAll(dasWsRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasWsReal wsReal = new DasWsReal();
					wsReal.setCommStatus((short) 1);
					wsReal.setStatus((short) 1);
					wsReal.setStandardNumber(standardNumber);
					if (mapWs != null) {
						wsReal.setRecTime(mapWs.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasWsReal> wsList = dasWsRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (wsList.size() > 0) {
							wsReal.setRecTime(wsList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						wsRows.add(wsReal);
					}
				}
			}
			buildWS1(wsRows, rtnList);
		}
		if (wstSNs.size() > 0) {
			for (String standardNumber : wstSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasWstReal mapWst = (DasWstReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 有缓存则去缓存
					if (mapWst != null) {
						wstRows.add(mapWst);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						wstRows.addAll(dasWstRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasWstReal wstReal = new DasWstReal();
					wstReal.setCommStatus((short) 1);
					wstReal.setStatus((short) 1);
					wstReal.setStandardNumber(standardNumber);
					if (mapWst != null) {
						wstReal.setRecTime(mapWst.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasWstReal> wstList = dasWstRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (wstList.size() > 0) {
							wstReal.setRecTime(wstList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						wstRows.add(wstReal);
					}
				}
			}
			buildWST1(wstRows, rtnList);
		}
		if (loliSNs.size() > 0) {
			for (String standardNumber : loliSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasLoliReal mapLoli = (DasLoliReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 有缓存则去缓存
					if (mapLoli != null) {
						loliRows.add(mapLoli);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						loliRows.addAll(dasLoliRealDAO
								.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasLoliReal loliReal = new DasLoliReal();
					loliReal.setCommStatus((short) 1);
					loliReal.setStatus((short) 1);
					loliReal.setStandardNumber(standardNumber);
					if (mapLoli != null) {
						loliReal.setRecTime(mapLoli.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasLoliReal> loliList = dasLoliRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (loliList.size() > 0) {
							loliReal.setRecTime(loliList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						loliRows.add(loliReal);
					}
				}
			}
			buildLoli1(loliRows, rtnList);
		}
		if (noSNs.size() > 0) {
			for (String standardNumber : noSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasNodReal mapNod = (DasNodReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 有缓存则去缓存
					if (mapNod != null) {
						noRows.add(mapNod);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						noRows.addAll(dasNodRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasNodReal nodReal = new DasNodReal();
					nodReal.setCommStatus((short) 1);
					nodReal.setStatus((short) 1);
					nodReal.setStandardNumber(standardNumber);
					if (mapNod != null) {
						nodReal.setRecTime(mapNod.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasNodReal> nodList = dasNodRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (nodList.size() > 0) {
							nodReal.setRecTime(nodList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						noRows.add(nodReal);
					}
				}
			}
			buildNOD1(noRows, rtnList);
		}
		if (cmsSNs.size() > 0) {
			for (String standardNumber : cmsSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				Map<String, List<DasCmsReal>> mapCms = (Map<String, List<DasCmsReal>>) CacheUtil
						.getCache((standardNumber + "dasCache").hashCode(),
								"dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (null != mapCms) {
						List<DasCmsReal> list = mapCms.get(standardNumber);
						if (null != list && list.size() > 0) {
							cmsRows.put(standardNumber, list);
						}
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						List<DasCmsReal> list = dasCmsRealDAO
								.findByPropertysDas(params);
						if (null != list && list.size() > 0) {
							cmsRows.put(standardNumber, list);
						}
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					List<DasCmsReal> listCms = new ArrayList<DasCmsReal>();
					DasCmsReal cmsReal = new DasCmsReal();
					cmsReal.setCommStatus((short) 1);
					cmsReal.setStatus((short) 1);
					cmsReal.setStandardNumber(standardNumber);
					if (mapCms != null) {
						if (mapCms.get(standardNumber).size() > 0) {
							cmsReal.setRecTime(mapCms.get(standardNumber)
									.get(0).getRecTime());
						} else {
							flag = false;
						}
					} else {
						params.put("standardNumber", standardNumber);
						List<DasCmsReal> cmsList = dasCmsRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (cmsList.size() > 0) {
							cmsReal.setRecTime(cmsList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					listCms.add(cmsReal);
					cmsRows.put(standardNumber, listCms);
				}
			}
			buildCMS2(cmsRows, rtnList);
		}
		if (coviSNs.size() > 0) {
			for (String standardNumber : coviSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasCoviReal mapCovi = (DasCoviReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (null != mapCovi) {
						coviRows.add(mapCovi);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						coviRows.addAll(dasCoviRealDAO
								.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasCoviReal coviReal = new DasCoviReal();
					coviReal.setCommStatus((short) 1);
					coviReal.setStatus((short) 1);
					coviReal.setStandardNumber(standardNumber);
					if (mapCovi != null) {
						coviReal.setRecTime(mapCovi.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasCoviReal> coviList = dasCoviRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (coviList.size() > 0) {
							coviReal.setRecTime(coviList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						coviRows.add(coviReal);
					}
				}
			}
			buildCovi1(coviRows, rtnList);
		}
		if (cdSNs.size() > 0) {
			for (String standardNumber : cdSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasControlDeviceReal mapCd = (DasControlDeviceReal) CacheUtil
						.getCache((standardNumber + "dasCache").hashCode(),
								"dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapCd != null) {
						cdRows.add(mapCd);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						cdRows.addAll(dasControlDeviceRealDAO
								.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasControlDeviceReal cd = new DasControlDeviceReal();
					cd.setCommStatus((short) 1);
					cd.setStatus((short) 1);
					cd.setStandardNumber(standardNumber);
					cd.setType(Integer.parseInt(map.get(standardNumber)
							.getType()));
					if (mapCd != null) {
						cd.setRecTime(mapCd.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasControlDeviceReal> cdList = dasControlDeviceRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (cdList.size() > 0) {
							cd.setRecTime(cdList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						cdRows.add(cd);
					}
				}
			}
			buildCD1(cdRows, rtnList);
		}
		if (lilSNs.size() > 0) {
			for (String standardNumber : lilSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasLilReal mapLil = (DasLilReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 匹配das没有掉线的设备
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapLil != null) {
						lilRows.add(mapLil);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						lilRows.addAll(dasLilRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasLilReal lilReal = new DasLilReal();
					lilReal.setCommStatus((short) 1);
					lilReal.setStatus((short) 1);
					lilReal.setStandardNumber(standardNumber);
					if (mapLil != null) {
						lilReal.setRecTime(mapLil.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasLilReal> lilList = dasLilRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (lilList.size() > 0) {
							lilReal.setRecTime(lilList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						lilRows.add(lilReal);
					}
				}
			}

			buildLil1(lilRows, rtnList);
		}
		if (tslSNs.size() > 0) {
			for (String standardNumber : tslSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasTslReal mapTsl = (DasTslReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 没有缓存取查询数据直接加入集合
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapTsl != null) {
						tslRows.add(mapTsl);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						tslRows.addAll(dasTslRealDAO.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasTslReal tslReal = new DasTslReal();
					tslReal.setCommStatus((short) 1);
					tslReal.setStatus((short) 1);
					tslReal.setStandardNumber(standardNumber);
					if (mapTsl != null) {
						tslReal.setRecTime(mapTsl.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasTslReal> tslList = dasTslRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (tslList.size() > 0) {
							tslReal.setRecTime(tslList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						tslRows.add(tslReal);
					}
				}
			}
			buildTsl1(tslRows, rtnList);
		}
		if (vidSNs.size() > 0) {
			for (String standardNumber : vidSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasViDetectorReal mapVid = (DasViDetectorReal) CacheUtil
						.getCache((standardNumber + "dasCache").hashCode(),
								"dasCache");
				// 没有缓存取查询数据直接加入集合
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapVid != null) {
						vidRows.add(mapVid);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						vidRows.addAll(dasViDetectorRealDAO
								.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasViDetectorReal vidReal = new DasViDetectorReal();
					vidReal.setCommStatus((short) 1);
					vidReal.setStatus((short) 1);
					vidReal.setStandardNumber(standardNumber);
					if (mapVid != null) {
						vidReal.setRecTime(mapVid.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasViDetectorReal> vidList = dasViDetectorRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (vidList.size() > 0) {
							vidReal.setRecTime(vidList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					vidRows.add(vidReal);
				}
			}
			if (flag) {
				buildVid(vidRows, rtnList);
			}
		}
		if (rdSNs.size() > 0) {
			for (String standardNumber : rdSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasRoadDetectorReal mapRd = (DasRoadDetectorReal) CacheUtil
						.getCache((standardNumber + "dasCache").hashCode(),
								"dasCache");
				// 没有缓存取查询数据直接加入集合
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapRd != null) {
						rdRows.add(mapRd);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						rdRows.addAll(dasRoadDetectorRealDAO
								.findByPropertysDas(params));
						params.clear();
					}
				}
				// 匹配das掉线的设备状态直接置为异常采集时间先从缓存匹配，缓存没有再查数据库，都没有则不加入返回集合
				else {
					DasRoadDetectorReal rdReal = new DasRoadDetectorReal();
					rdReal.setCommStatus((short) 1);
					rdReal.setStatus((short) 1);
					rdReal.setStandardNumber(standardNumber);
					if (mapRd != null) {
						rdReal.setRecTime(mapRd.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasRoadDetectorReal> rdList = dasRoadDetectorRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (rdList.size() > 0) {
							rdReal.setRecTime(rdList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						rdRows.add(rdReal);
					}
				}
			}
			buildRd(rdRows, rtnList);
		}

		if (gpsSNs.size() > 0) {
			for (String standardNumber : gpsSNs) {
				flag = true;
				// 先查询缓存,如果存在就从缓存取出，不存在则查实时表
				DasGpsReal mapGps = (DasGpsReal) CacheUtil.getCache(
						(standardNumber + "dasCache").hashCode(), "dasCache");
				// 没有缓存取查询数据直接加入集合
				if (null == map.get(standardNumber)) {
					// 没有缓存取查询数据直接加入集合
					if (mapGps != null) {
						gpsRows.add(mapGps);
					}
					// 没有缓存取查询数据直接加入集合
					else {
						params.put("standardNumber", standardNumber);
						gpsRows.addAll(dasGpsRealDAO.findByPropertysDas(params));
						params.clear();
					}
				} else {
					DasGpsReal gpsReal = new DasGpsReal();
					gpsReal.setCommStatus((short) 1);
					gpsReal.setStatus((short) 1);
					gpsReal.setStandardNumber(standardNumber);
					if (mapGps != null) {
						gpsReal.setRecTime(mapGps.getRecTime());
					} else {
						params.put("standardNumber", standardNumber);
						List<DasGpsReal> gpsList = dasGpsRealDAO
								.findByPropertysDas(params);
						params.clear();
						if (gpsList.size() > 0) {
							gpsReal.setRecTime(gpsList.get(0).getRecTime());
						} else {
							flag = false;
						}
					}
					if (flag) {
						gpsRows.add(gpsReal);
					}
				}
			}
			buildGps(gpsRows, rtnList);
		}
		return rtnList;
	}

	private void buildGps(List<DasGpsReal> rows, List<Element> rtnList) {
		for (DasGpsReal entity : rows) {
			Element de = new Element("Device");
			de.setAttribute(
					"StandardNumber",
					entity.getStandardNumber() != null ? entity
							.getStandardNumber() : "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WST + "");
			de.setAttribute("WorkStatus", entity.getStatus() != null ? entity
					.getStatus().toString() : "");
			de.setAttribute("CommStatus",
					entity.getCommStatus() != null ? entity.getCommStatus()
							.toString() : "");
			de.setAttribute("GatherTime", entity.getRecTime() != null ? entity
					.getRecTime().getTime() + "" : "");

			// 以后根据具体情况调整
			Element altitudeElement = new Element("Altitude");
			altitudeElement.setText(cutString(entity.getAltitude()));
			de.addContent(altitudeElement);

			Element bearingElement = new Element("Bearing");
			bearingElement.setText(cutString(entity.getBearing()));
			de.addContent(bearingElement);

			Element latitudeElement = new Element("Latitude");
			latitudeElement.setText(cutString(entity.getLatitude()));
			de.addContent(latitudeElement);

			Element longitudeElement = new Element("Longitude");
			longitudeElement.setText(cutString(entity.getLongitude()));
			de.addContent(longitudeElement);

			Element maxSpeedElement = new Element("MaxSpeed");
			maxSpeedElement.setText(cutString(entity.getMaxSpeed()));
			de.addContent(maxSpeedElement);

			Element minSpeedElement = new Element("MinSpeed");
			minSpeedElement.setText(cutString(entity.getMinSpeed()));
			de.addContent(minSpeedElement);

			rtnList.add(de);
		}
	}

	private void buildRd(List<DasRoadDetectorReal> rows, List<Element> rtnList) {
		for (DasRoadDetectorReal entity : rows) {
			Element de = new Element("Device");
			de.setAttribute(
					"StandardNumber",
					entity.getStandardNumber() != null ? entity
							.getStandardNumber() : "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WST + "");
			de.setAttribute("WorkStatus", entity.getStatus() != null ? entity
					.getStatus().toString() : "");
			de.setAttribute("CommStatus",
					entity.getCommStatus() != null ? entity.getCommStatus()
							.toString() : "");
			de.setAttribute("GatherTime", entity.getRecTime() != null ? entity
					.getRecTime().getTime() + "" : "");

			// 路面温度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element rTempElement = new Element("RTemp");
			rTempElement.setText(cutString(entity.getRoadTempAvg()));
			de.addContent(rTempElement);

			Element rainVolElement = new Element("RainVol");
			rainVolElement.setText(cutString(entity.getRainVol()));
			de.addContent(rainVolElement);

			Element snowVolElement = new Element("SnowVol");
			snowVolElement.setText(cutString(entity.getSnowVol()));
			de.addContent(snowVolElement);

			Element rsurfaceElement = new Element("Rsurface");
			rsurfaceElement.setText(cutString(entity.getRoadSurface()));
			de.addContent(rsurfaceElement);

			rtnList.add(de);
		}
	}

	private void buildVid(List<DasViDetectorReal> rows, List<Element> rtnList) {
		for (DasViDetectorReal entity : rows) {
			Element de = new Element("Device");
			de.setAttribute(
					"StandardNumber",
					entity.getStandardNumber() != null ? entity
							.getStandardNumber() : "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "");
			de.setAttribute("WorkStatus", entity.getStatus() != null ? entity
					.getStatus().toString() : "");
			de.setAttribute("CommStatus",
					entity.getCommStatus() != null ? entity.getCommStatus()
							.toString() : "");
			de.setAttribute("GatherTime", entity.getRecTime() != null ? entity
					.getRecTime().getTime() + "" : "");
			Element visElement = new Element("Vis");
			visElement.setText(cutString(entity.getVisAvg()));
			de.addContent(visElement);

			rtnList.add(de);
		}
	}

	private void buildCMS2(Map<String, List<DasCmsReal>> map, List<Element> list) {
		for (Map.Entry<String, List<DasCmsReal>> cms : map.entrySet()) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber", cms.getValue().get(0)
					.getStandardNumber() != null ? cms.getValue().get(0)
					.getStandardNumber() : "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_CMS + "");
			de.setAttribute("WorkStatus",
					cms.getValue().get(0).getStatus() != null ? cms.getValue()
							.get(0).getStatus().toString() : "");
			de.setAttribute("CommStatus",
					cms.getValue().get(0).getCommStatus() != null ? cms
							.getValue().get(0).getCommStatus().toString() : "");
			de.setAttribute("GatherTime",
					cms.getValue().get(0).getRecTime() != null ? cms.getValue()
							.get(0).getRecTime().getTime()
							+ "" : "");

			Element playlist = new Element("Playlist");
			de.addContent(playlist);
			// 采集数据有值优先选择采集值
			for (DasCmsReal dasCms : cms.getValue()) {
				if (StringUtils.isNotBlank(dasCms.getDispCont())) {
					Element item = new Element("Item");
					item.setAttribute("Content", MyStringUtil
							.object2StringNotNull(dasCms.getDispCont()));
					item.setAttribute("Color", MyStringUtil
							.object2StringNotNull(dasCms.getColor()));
					item.setAttribute("Font",
							MyStringUtil.object2StringNotNull(dasCms.getFont()));
					item.setAttribute("Size",
							MyStringUtil.object2StringNotNull(dasCms.getSize()));
					item.setAttribute("Space", "0");
					item.setAttribute("Duration", MyStringUtil
							.object2StringNotNull(dasCms.getDispTime()));
					item.setAttribute("X", "");
					item.setAttribute("Y", "");
					item.setAttribute("Type", "1");
					playlist.addContent(item);
				}
				// 如果数采没有成功采集到情报板当前数据，从情报板发布记录里面查询最近的发布记录
				else {
					List<CmsPublishLog> records = cmsPublishLogDAO
							.listLatestRecord(dasCms.getStandardNumber());
					for (CmsPublishLog record : records) {
						Element item = new Element("Item");
						item.setAttribute("Content", MyStringUtil
								.object2StringNotNull(record.getContent()));
						item.setAttribute("Color", MyStringUtil
								.object2StringNotNull(record.getColor()));
						item.setAttribute("Font", MyStringUtil
								.object2StringNotNull(record.getFont()));
						item.setAttribute("Size", MyStringUtil
								.object2StringNotNull(record.getSize()));
						item.setAttribute("Space", MyStringUtil
								.object2StringNotNull(record.getSpace()));
						item.setAttribute("Duration", MyStringUtil
								.object2StringNotNull(record.getDuration()));
						item.setAttribute("X", MyStringUtil
								.object2StringNotNull(record.getX()));
						item.setAttribute("Y", MyStringUtil
								.object2StringNotNull(record.getY()));
						item.setAttribute("Type", MyStringUtil
								.object2StringNotNull(record.getInfoType()));
						item.setAttribute("RowSpace", MyStringUtil
								.object2StringNotNull(record.getRowSpace()));
						playlist.addContent(item);
					}
				}
			}
			// Map<String, String> sn = new HashMap<String, String>();
			// boolean flag = true;
			// for (DasCmsReal dasCms : cms.getValue()) {
			// Element item = new Element("Item");
			// item.setAttribute("Content",
			// MyStringUtil.object2StringNotNull(dasCms.getDispCont()));
			// item.setAttribute("Color",
			// MyStringUtil.object2StringNotNull(dasCms.getColor()));
			// item.setAttribute("Font",
			// MyStringUtil.object2StringNotNull(dasCms.getFont()));
			// item.setAttribute("Size",
			// MyStringUtil.object2StringNotNull(dasCms.getSize()));
			// item.setAttribute("Space", "0");
			// item.setAttribute("Duration",
			// MyStringUtil.object2StringNotNull(dasCms.getDispTime()));
			// item.setAttribute("X", "");
			// item.setAttribute("Y", "");
			// item.setAttribute("Type", "1");
			// item.setAttribute("Flag", "0");
			// playlist.addContent(item);
			// // 如果dasCms对象的sn都一样，则从情报板发布记录里面查询最近的发布记录只查询一次
			// if (sn.size() > 0) {
			// if (StringUtils.isNotBlank(sn.get(dasCms
			// .getStandardNumber()))) {
			// flag = false;
			// break;
			// }
			// }
			// sn.put(dasCms.getStandardNumber(), "sn");
			//
			// if (flag) {
			// // 如果数采没有成功采集到情报板当前数据，从情报板发布记录里面查询最近的发布记录
			// List<CmsPublishLog> records = cmsPublishLogDAO
			// .listLatestRecord(dasCms.getStandardNumber());
			// for (CmsPublishLog record : records) {
			// Element item1 = new Element("Item");
			// item1.setAttribute("Content", MyStringUtil
			// .object2StringNotNull(record.getContent()));
			// item1.setAttribute("Color", MyStringUtil
			// .object2StringNotNull(record.getColor()));
			// item1.setAttribute("Font", MyStringUtil
			// .object2StringNotNull(record.getFont()));
			// item1.setAttribute("Size", MyStringUtil
			// .object2StringNotNull(record.getSize()));
			// item1.setAttribute("Space", MyStringUtil
			// .object2StringNotNull(record.getSpace()));
			// item1.setAttribute("Duration", MyStringUtil
			// .object2StringNotNull(record.getDuration()));
			// item1.setAttribute("X", MyStringUtil
			// .object2StringNotNull(record.getX()));
			// item1.setAttribute("Y", MyStringUtil
			// .object2StringNotNull(record.getY()));
			// item1.setAttribute("Type", MyStringUtil
			// .object2StringNotNull(record.getInfoType()));
			// item1.setAttribute("RowSpace", MyStringUtil
			// .object2StringNotNull(record.getRowSpace()));
			// item1.setAttribute("Flag", "1");
			// playlist.addContent(item1);
			// }
			// }
			// }
			list.add(de);
		}

	}

	private void buildVD(List<DasVd> rows, List<Element> list) {
		for (DasVd vd : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					vd.getStandardNumber() != null ? vd.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_VD + "");
			de.setAttribute("WorkStatus", vd.getStatus() != null ? vd
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", vd.getCommStatus() != null ? vd
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", vd.getRecTime() != null ? vd
					.getRecTime().getTime() + "" : "");
			Element uupFluxBElement = new Element("UpFluxB");
			uupFluxBElement.setText(vd.getUpFluxb() != null ? vd.getUpFluxb()
					.toString() : "");
			de.addContent(uupFluxBElement);

			Element uupFluxSElement = new Element("UpFluxS");
			uupFluxSElement.setText(vd.getUpFluxs() != null ? vd.getUpFluxs()
					.toString() : "");
			de.addContent(uupFluxSElement);

			Element uupFluxMElement = new Element("UpFluxM");
			uupFluxMElement.setText(vd.getUpFluxm() != null ? vd.getUpFluxm()
					.toString() : "");
			de.addContent(uupFluxMElement);

			Element uupFluxElement = new Element("UpFlux");
			uupFluxElement.setText(vd.getUpFlux() != null ? vd.getUpFlux()
					.toString() : "");
			de.addContent(uupFluxElement);

			Element dwFluxBElement = new Element("DwFluxB");
			dwFluxBElement.setText(vd.getDwFluxb() != null ? vd.getDwFluxb()
					.toString() : "");
			de.addContent(dwFluxBElement);

			Element dwFluxSElement = new Element("DwFluxS");
			dwFluxSElement.setText(vd.getDwFluxs() != null ? vd.getDwFluxs()
					.toString() : "");
			de.addContent(dwFluxSElement);

			Element dwFluxMElement = new Element("DwFluxM");
			dwFluxMElement.setText(vd.getDwFluxm() != null ? vd.getDwFluxm()
					.toString() : "");
			de.addContent(dwFluxMElement);

			Element dwFluxElement = new Element("DwFlux");
			dwFluxElement.setText(vd.getDwFlux() != null ? vd.getDwFlux()
					.toString() : "");
			de.addContent(dwFluxElement);

			Element upSpeedElement = new Element("UpSpeed");
			upSpeedElement.setText(vd.getUpSpeed() != null ? vd.getUpSpeed()
					.toString() : "");
			de.addContent(upSpeedElement);

			Element upSpeedBElement = new Element("UpSpeedB");
			upSpeedBElement.setText(vd.getUpSpeedb() != null ? vd.getUpSpeedb()
					.toString() : "");
			de.addContent(upSpeedBElement);

			Element upSpeedSElement = new Element("UpSpeedS");
			upSpeedSElement.setText(vd.getUpSpeeds() != null ? vd.getUpSpeeds()
					.toString() : "");
			de.addContent(upSpeedSElement);

			Element upSpeedMElement = new Element("UpSpeedM");
			upSpeedMElement.setText(vd.getUpSpeedm() != null ? vd.getUpSpeedm()
					.toString() : "");
			de.addContent(upSpeedMElement);

			Element dwSpeedElement = new Element("DwSpeed");
			dwSpeedElement.setText(vd.getDwSpeed() != null ? vd.getDwSpeed()
					.toString() : "");
			de.addContent(dwSpeedElement);

			Element dwSpeedBElement = new Element("DwSpeedB");
			dwSpeedBElement.setText(vd.getDwSpeedb() != null ? vd.getDwSpeedb()
					.toString() : "");
			de.addContent(dwSpeedBElement);

			Element dwSpeedSElement = new Element("DwSpeedS");
			dwSpeedSElement.setText(vd.getDwSpeeds() != null ? vd.getDwSpeeds()
					.toString() : "");
			de.addContent(dwSpeedSElement);

			Element dwSpeedMElement = new Element("DwSpeedM");
			dwSpeedMElement.setText(vd.getDwSpeedm() != null ? vd.getDwSpeedm()
					.toString() : "");
			de.addContent(dwSpeedMElement);

			Element upOccupElement = new Element("UpOccup");
			upOccupElement.setText(vd.getUpOcc() != null ? vd.getUpOcc()
					.toString() : "");
			de.addContent(upOccupElement);

			Element dwOccdownElement = new Element("DwOccdown");
			dwOccdownElement.setText(vd.getDwOcc() != null ? vd.getDwOcc()
					.toString() : "");
			de.addContent(dwOccdownElement);

			Element upHeadwayElement = new Element("UpHeadway");
			upHeadwayElement.setText(vd.getUpHeadway() != null ? vd
					.getUpHeadway().toString() : "");
			de.addContent(upHeadwayElement);

			Element dwHeadwayElement = new Element("DwHeadway");
			dwHeadwayElement.setText(vd.getDwHeadway() != null ? vd
					.getDwHeadway().toString() : "");
			de.addContent(dwHeadwayElement);

			// Element laneNumElement = new Element("LaneNum");
			// laneNumElement.setText(vd.getLaneNumber() != null ? vd
			// .getLaneNumber().toString() : "");
			// de.addContent(laneNumElement);

			list.add(de);
		}
	}

	private void buildVD1(List<DasVdReal> rows, List<Element> list) {
		for (DasVdReal vd : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					vd.getStandardNumber() != null ? vd.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_VD + "");
			de.setAttribute("WorkStatus", vd.getStatus() != null ? vd
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", vd.getCommStatus() != null ? vd
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", vd.getRecTime() != null ? vd
					.getRecTime().getTime() + "" : "");
			Element uupFluxBElement = new Element("UpFluxB");
			uupFluxBElement.setText(vd.getUpFluxb() != null ? vd.getUpFluxb()
					.toString() : "");
			de.addContent(uupFluxBElement);

			Element uupFluxSElement = new Element("UpFluxS");
			uupFluxSElement.setText(vd.getUpFluxs() != null ? vd.getUpFluxs()
					.toString() : "");
			de.addContent(uupFluxSElement);

			Element uupFluxMElement = new Element("UpFluxM");
			uupFluxMElement.setText(vd.getUpFluxm() != null ? vd.getUpFluxm()
					.toString() : "");
			de.addContent(uupFluxMElement);

			Element uupFluxElement = new Element("UpFlux");
			uupFluxElement.setText(vd.getUpFlux() != null ? vd.getUpFlux()
					.toString() : "");
			de.addContent(uupFluxElement);

			Element dwFluxBElement = new Element("DwFluxB");
			dwFluxBElement.setText(vd.getDwFluxb() != null ? vd.getDwFluxb()
					.toString() : "");
			de.addContent(dwFluxBElement);

			Element dwFluxSElement = new Element("DwFluxS");
			dwFluxSElement.setText(vd.getDwFluxs() != null ? vd.getDwFluxs()
					.toString() : "");
			de.addContent(dwFluxSElement);

			Element dwFluxMElement = new Element("DwFluxM");
			dwFluxMElement.setText(vd.getDwFluxm() != null ? vd.getDwFluxm()
					.toString() : "");
			de.addContent(dwFluxMElement);

			Element dwFluxElement = new Element("DwFlux");
			dwFluxElement.setText(vd.getDwFlux() != null ? vd.getDwFlux()
					.toString() : "");
			de.addContent(dwFluxElement);

			Element upSpeedElement = new Element("UpSpeed");
			upSpeedElement.setText(vd.getUpSpeed() != null ? vd.getUpSpeed()
					.toString() : "");
			de.addContent(upSpeedElement);

			Element upSpeedBElement = new Element("UpSpeedB");
			upSpeedBElement.setText(vd.getUpSpeedb() != null ? vd.getUpSpeedb()
					.toString() : "");
			de.addContent(upSpeedBElement);

			Element upSpeedSElement = new Element("UpSpeedS");
			upSpeedSElement.setText(vd.getUpSpeeds() != null ? vd.getUpSpeeds()
					.toString() : "");
			de.addContent(upSpeedSElement);

			Element upSpeedMElement = new Element("UpSpeedM");
			upSpeedMElement.setText(vd.getUpSpeedm() != null ? vd.getUpSpeedm()
					.toString() : "");
			de.addContent(upSpeedMElement);

			Element dwSpeedElement = new Element("DwSpeed");
			dwSpeedElement.setText(vd.getDwSpeed() != null ? vd.getDwSpeed()
					.toString() : "");
			de.addContent(dwSpeedElement);

			Element dwSpeedBElement = new Element("DwSpeedB");
			dwSpeedBElement.setText(vd.getDwSpeedb() != null ? vd.getDwSpeedb()
					.toString() : "");
			de.addContent(dwSpeedBElement);

			Element dwSpeedSElement = new Element("DwSpeedS");
			dwSpeedSElement.setText(vd.getDwSpeeds() != null ? vd.getDwSpeeds()
					.toString() : "");
			de.addContent(dwSpeedSElement);

			Element dwSpeedMElement = new Element("DwSpeedM");
			dwSpeedMElement.setText(vd.getDwSpeedm() != null ? vd.getDwSpeedm()
					.toString() : "");
			de.addContent(dwSpeedMElement);

			Element upOccupElement = new Element("UpOccup");
			upOccupElement.setText(vd.getUpOcc() != null ? vd.getUpOcc()
					.toString() : "");
			de.addContent(upOccupElement);

			Element dwOccdownElement = new Element("DwOccdown");
			dwOccdownElement.setText(vd.getDwOcc() != null ? vd.getDwOcc()
					.toString() : "");
			de.addContent(dwOccdownElement);

			Element upHeadwayElement = new Element("UpHeadway");
			upHeadwayElement.setText(vd.getUpHeadway() != null ? vd
					.getUpHeadway().toString() : "");
			de.addContent(upHeadwayElement);

			Element dwHeadwayElement = new Element("DwHeadway");
			dwHeadwayElement.setText(vd.getDwHeadway() != null ? vd
					.getDwHeadway().toString() : "");
			de.addContent(dwHeadwayElement);

			// Element laneNumElement = new Element("LaneNum");
			// laneNumElement.setText(vd.getLaneNumber() != null ? vd
			// .getLaneNumber().toString() : "");
			// de.addContent(laneNumElement);

			list.add(de);
		}
	}

	private void buildLoli(List<DasLoli> rows, List<Element> list) {
		for (DasLoli loli : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					loli.getStandardNumber() != null ? loli.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_LOLI + "");
			de.setAttribute("WorkStatus", loli.getStatus() != null ? loli
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", loli.getCommStatus() != null ? loli
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", loli.getRecTime() != null ? loli
					.getRecTime().getTime() + "" : "");
			Element lOLumiElement = new Element("LOLumi");
			lOLumiElement.setText(loli.getLo() != null ? loli.getLo()
					.toString() : "");
			de.addContent(lOLumiElement);

			Element lILumiElement = new Element("LILumi");
			lILumiElement.setText(loli.getLi() != null ? loli.getLi()
					.toString() : "");
			de.addContent(lILumiElement);

			list.add(de);
		}
	}

	private void buildLoli1(List<DasLoliReal> rows, List<Element> list) {
		for (DasLoliReal loli : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					loli.getStandardNumber() != null ? loli.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_LOLI + "");
			de.setAttribute("WorkStatus", loli.getStatus() != null ? loli
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", loli.getCommStatus() != null ? loli
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", loli.getRecTime() != null ? loli
					.getRecTime().getTime() + "" : "");
			Element lOLumiElement = new Element("LOLumi");
			lOLumiElement.setText(loli.getLo() != null ? loli.getLo()
					.toString() : "");
			de.addContent(lOLumiElement);

			Element lILumiElement = new Element("LILumi");
			lILumiElement.setText(loli.getLi() != null ? loli.getLi()
					.toString() : "");
			de.addContent(lILumiElement);

			list.add(de);
		}
	}

	private void buildWST(List<DasWst> rows, List<Element> list) {
		for (DasWst wst : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					wst.getStandardNumber() != null ? wst.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WST + "");
			de.setAttribute("WorkStatus", wst.getStatus() != null ? wst
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", wst.getCommStatus() != null ? wst
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", wst.getRecTime() != null ? wst
					.getRecTime().getTime() + "" : "");
			// 能见度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element visElement = new Element("Vis");
			visElement.setText(cutString(wst.getVisAvg()));
			de.addContent(visElement);

			// 风速标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element wSpeedElement = new Element("WSpeed");
			wSpeedElement.setText(cutString(wst.getWsAvg()));
			de.addContent(wSpeedElement);

			Element windDirElement = new Element("WindDir");
			windDirElement.setText(wst.getWindDir() != null ? wst.getWindDir()
					.toString() : "");
			de.addContent(windDirElement);

			// 气温标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element aTempElement = new Element("ATemp");
			aTempElement.setText(cutString(wst.getAirTempAvg()));
			de.addContent(aTempElement);

			// 路面温度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element rTempElement = new Element("RTemp");
			rTempElement.setText(cutString(wst.getRoadTempAvg()));
			de.addContent(rTempElement);

			// 湿度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element humiElement = new Element("Humi");
			humiElement.setText(cutString(wst.getHumiAvg()));
			de.addContent(humiElement);

			Element rainVolElement = new Element("RainVol");
			rainVolElement.setText(cutString(wst.getRainVol()));
			de.addContent(rainVolElement);

			Element snowVolElement = new Element("SnowVol");
			snowVolElement.setText(cutString(wst.getSnowVol()));
			de.addContent(snowVolElement);

			Element rsurfaceElement = new Element("Rsurface");
			rsurfaceElement.setText(cutString(wst.getRoadSurface()));
			de.addContent(rsurfaceElement);

			list.add(de);
		}
	}

	private void buildWST1(List<DasWstReal> rows, List<Element> list) {
		for (DasWstReal wst : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					wst.getStandardNumber() != null ? wst.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WST + "");
			de.setAttribute("WorkStatus", wst.getStatus() != null ? wst
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", wst.getCommStatus() != null ? wst
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", wst.getRecTime() != null ? wst
					.getRecTime().getTime() + "" : "");
			// 能见度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element visElement = new Element("Vis");
			visElement.setText(cutString(wst.getVisAvg()));
			de.addContent(visElement);

			// 风速标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element wSpeedElement = new Element("WSpeed");
			wSpeedElement.setText(cutString(wst.getWsAvg()));
			de.addContent(wSpeedElement);

			Element windDirElement = new Element("WindDir");
			windDirElement.setText(wst.getWindDir() != null ? wst.getWindDir()
					.toString() : "");
			de.addContent(windDirElement);

			// 气温标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element aTempElement = new Element("ATemp");
			aTempElement.setText(cutString(wst.getAirTempAvg()));
			de.addContent(aTempElement);

			// 路面温度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element rTempElement = new Element("RTemp");
			rTempElement.setText(cutString(wst.getRoadTempAvg()));
			de.addContent(rTempElement);

			// 湿度标准定义有问题，暂时取平均值展示，以后根据具体情况调整
			Element humiElement = new Element("Humi");
			humiElement.setText(cutString(wst.getHumiAvg()));
			de.addContent(humiElement);

			Element rainVolElement = new Element("RainVol");
			rainVolElement.setText(cutString(wst.getRainVol()));
			de.addContent(rainVolElement);

			Element snowVolElement = new Element("SnowVol");
			snowVolElement.setText(cutString(wst.getSnowVol()));
			de.addContent(snowVolElement);

			Element rsurfaceElement = new Element("Rsurface");
			rsurfaceElement.setText(cutString(wst.getRoadSurface()));
			de.addContent(rsurfaceElement);

			list.add(de);
		}
	}

	private void buildWS(List<DasWs> rows, List<Element> list) {
		for (DasWs ws : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					ws.getStandardNumber() != null ? ws.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WS + "");
			de.setAttribute("WorkStatus", ws.getStatus() != null ? ws
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", ws.getCommStatus() != null ? ws
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", ws.getRecTime() != null ? ws
					.getRecTime().getTime() + "" : "");
			Element directionElement = new Element("Direction");
			directionElement.setText(ws.getDirection() != null ? ws
					.getDirection().toString() : "");
			de.addContent(directionElement);

			Element speedElement = new Element("Speed");
			speedElement.setText(ws.getSpeed() != null ? ws.getSpeed()
					.toString() : "");
			de.addContent(speedElement);

			list.add(de);
		}
	}

	private void buildWS1(List<DasWsReal> rows, List<Element> list) {
		for (DasWsReal ws : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					ws.getStandardNumber() != null ? ws.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_WS + "");
			de.setAttribute("WorkStatus", ws.getStatus() != null ? ws
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", ws.getCommStatus() != null ? ws
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", ws.getRecTime() != null ? ws
					.getRecTime().getTime() + "" : "");
			Element directionElement = new Element("Direction");
			directionElement.setText(ws.getDirection() != null ? ws
					.getDirection().toString() : "");
			de.addContent(directionElement);

			Element speedElement = new Element("Speed");
			speedElement.setText(ws.getSpeed() != null ? ws.getSpeed()
					.toString() : "");
			de.addContent(speedElement);

			list.add(de);
		}
	}

	private void buildNOD(List<DasNod> rows, List<Element> list) {
		for (DasNod nod : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber", nod.getStandardNumber());
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_NOD + "");
			de.setAttribute("WorkStatus", nod.getStatus() != null ? nod
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", nod.getCommStatus() != null ? nod
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", nod.getRecTime() != null ? nod
					.getRecTime().getTime() + "" : "");
			Element nOConctElement = new Element("NOConct");
			nOConctElement.setText(nod.getNo() != null ? nod.getNo().toString()
					: "");
			de.addContent(nOConctElement);

			Element nOOConctElement = new Element("NOOConct");
			nOOConctElement.setText(nod.getNo2() != null ? nod.getNo2()
					.toString() : "");
			de.addContent(nOOConctElement);

			list.add(de);
		}
	}

	private void buildNOD1(List<DasNodReal> rows, List<Element> list) {
		for (DasNodReal nod : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber", nod.getStandardNumber());
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_NOD + "");
			de.setAttribute("WorkStatus", nod.getStatus() != null ? nod
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", nod.getCommStatus() != null ? nod
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", nod.getRecTime() != null ? nod
					.getRecTime().getTime() + "" : "");
			Element nOConctElement = new Element("NOConct");
			nOConctElement.setText(nod.getNo() != null ? nod.getNo().toString()
					: "");
			de.addContent(nOConctElement);

			Element nOOConctElement = new Element("NOOConct");
			nOOConctElement.setText(nod.getNo2() != null ? nod.getNo2()
					.toString() : "");
			de.addContent(nOOConctElement);

			list.add(de);
		}
	}

	private void buildCMS(Map<String, List<DasCms>> map, List<Element> list) {
		for (Map.Entry<String, List<DasCms>> cms : map.entrySet()) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber", cms.getValue().get(0)
					.getStandardNumber() != null ? cms.getValue().get(0)
					.getStandardNumber() : "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_CMS + "");
			de.setAttribute("WorkStatus",
					cms.getValue().get(0).getStatus() != null ? cms.getValue()
							.get(0).getStatus().toString() : "");
			de.setAttribute("CommStatus",
					cms.getValue().get(0).getCommStatus() != null ? cms
							.getValue().get(0).getCommStatus().toString() : "");
			de.setAttribute("GatherTime",
					cms.getValue().get(0).getRecTime() != null ? cms.getValue()
							.get(0).getRecTime().getTime()
							+ "" : "");

			// Element dispContElement = new Element("DispCont");
			// dispContElement.setText(cms.getDispCont() != null ? cms
			// .getDispCont() : "");
			// de.addContent(dispContElement);
			//
			// Element dispTimeElement = new Element("DispTime");
			// dispTimeElement.setText(cms.getDispTime() != null ? cms
			// .getDispTime().toString() : "");
			// de.addContent(dispTimeElement);
			//
			// // 标准外的私有数据扩展放在下面，根据实际情况调整以下代码
			// Element colorElement = new Element("Color");
			// colorElement.setText(cms.getColor() != null ? cms.getColor() :
			// "");
			// de.addContent(colorElement);
			//
			// Element sizeElement = new Element("Size");
			// sizeElement.setText(cms.getSize() != null ? cms.getSize()
			// .toString() : "");
			// de.addContent(sizeElement);
			//
			// Element fontElement = new Element("Font");
			// fontElement.setText(cms.getFont() != null ? cms.getFont() : "");
			// de.addContent(fontElement);

			Element playlist = new Element("Playlist");
			de.addContent(playlist);
			// 采集数据有值优先选择采集值
			for (DasCms dasCms : cms.getValue()) {
				if (StringUtils.isNotBlank(dasCms.getDispCont())) {
					Element item = new Element("Item");
					item.setAttribute("Content", MyStringUtil
							.object2StringNotNull(dasCms.getDispCont()));
					item.setAttribute("Color", MyStringUtil
							.object2StringNotNull(dasCms.getColor()));
					item.setAttribute("Font",
							MyStringUtil.object2StringNotNull(dasCms.getFont()));
					item.setAttribute("Size",
							MyStringUtil.object2StringNotNull(dasCms.getSize()));
					item.setAttribute("Space", "0");
					item.setAttribute("Duration", MyStringUtil
							.object2StringNotNull(dasCms.getDispTime()));
					item.setAttribute("X", "");
					item.setAttribute("Y", "");
					item.setAttribute("Type", "1");
					playlist.addContent(item);
				}
				// 如果数采没有成功采集到情报板当前数据，从情报板发布记录里面查询最近的发布记录
				else {
					List<CmsPublishLog> records = cmsPublishLogDAO
							.listLatestRecord(dasCms.getStandardNumber());
					for (CmsPublishLog record : records) {
						Element item = new Element("Item");
						item.setAttribute("Content", MyStringUtil
								.object2StringNotNull(record.getContent()));
						item.setAttribute("Color", MyStringUtil
								.object2StringNotNull(record.getColor()));
						item.setAttribute("Font", MyStringUtil
								.object2StringNotNull(record.getFont()));
						item.setAttribute("Size", MyStringUtil
								.object2StringNotNull(record.getSize()));
						item.setAttribute("Space", MyStringUtil
								.object2StringNotNull(record.getSpace()));
						item.setAttribute("Duration", MyStringUtil
								.object2StringNotNull(record.getDuration()));
						item.setAttribute("X", MyStringUtil
								.object2StringNotNull(record.getX()));
						item.setAttribute("Y", MyStringUtil
								.object2StringNotNull(record.getY()));
						item.setAttribute("Type", MyStringUtil
								.object2StringNotNull(record.getInfoType()));
						item.setAttribute("RowSpace", MyStringUtil
								.object2StringNotNull(record.getRowSpace()));
						playlist.addContent(item);
					}
				}
			}
			list.add(de);
		}
	}

	private void buildCMS1(List<DasCmsReal> rows, List<Element> list) {
		for (DasCmsReal cms : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					cms.getStandardNumber() != null ? cms.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_CMS + "");
			de.setAttribute("WorkStatus", cms.getStatus() != null ? cms
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", cms.getCommStatus() != null ? cms
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", cms.getRecTime() != null ? cms
					.getRecTime().getTime() + "" : "");

			// Element dispContElement = new Element("DispCont");
			// dispContElement.setText(cms.getDispCont() != null ? cms
			// .getDispCont() : "");
			// de.addContent(dispContElement);
			//
			// Element dispTimeElement = new Element("DispTime");
			// dispTimeElement.setText(cms.getDispTime() != null ? cms
			// .getDispTime().toString() : "");
			// de.addContent(dispTimeElement);
			//
			// // 标准外的私有数据扩展放在下面，根据实际情况调整以下代码
			// Element colorElement = new Element("Color");
			// colorElement.setText(cms.getColor() != null ? cms.getColor() :
			// "");
			// de.addContent(colorElement);
			//
			// Element sizeElement = new Element("Size");
			// sizeElement.setText(cms.getSize() != null ? cms.getSize()
			// .toString() : "");
			// de.addContent(sizeElement);
			//
			// Element fontElement = new Element("Font");
			// fontElement.setText(cms.getFont() != null ? cms.getFont() : "");
			// de.addContent(fontElement);

			Element playlist = new Element("Playlist");
			de.addContent(playlist);
			// 采集数据有值优先选择采集值
			if (StringUtils.isNotBlank(cms.getDispCont())) {
				Element item = new Element("Item");
				item.setAttribute("Content",
						MyStringUtil.object2StringNotNull(cms.getDispCont()));
				item.setAttribute("Color",
						MyStringUtil.object2StringNotNull(cms.getColor()));
				item.setAttribute("Font",
						MyStringUtil.object2StringNotNull(cms.getFont()));
				item.setAttribute("Size",
						MyStringUtil.object2StringNotNull(cms.getSize()));
				item.setAttribute("Space", "0");
				item.setAttribute("Duration",
						MyStringUtil.object2StringNotNull(cms.getDispTime()));
				item.setAttribute("X", "");
				item.setAttribute("Y", "");
				item.setAttribute("Type", "1");
				playlist.addContent(item);
			}
			// 如果数采没有成功采集到情报板当前数据，从情报板发布记录里面查询最近的发布记录
			else {
				List<CmsPublishLog> records = cmsPublishLogDAO
						.listLatestRecord(cms.getStandardNumber());
				for (CmsPublishLog record : records) {
					Element item = new Element("Item");
					item.setAttribute("Content", MyStringUtil
							.object2StringNotNull(record.getContent()));
					item.setAttribute("Color", MyStringUtil
							.object2StringNotNull(record.getColor()));
					item.setAttribute("Font",
							MyStringUtil.object2StringNotNull(record.getFont()));
					item.setAttribute("Size",
							MyStringUtil.object2StringNotNull(record.getSize()));
					item.setAttribute("Space", MyStringUtil
							.object2StringNotNull(record.getSpace()));
					item.setAttribute("Duration", MyStringUtil
							.object2StringNotNull(record.getDuration()));
					item.setAttribute("X",
							MyStringUtil.object2StringNotNull(record.getX()));
					item.setAttribute("Y",
							MyStringUtil.object2StringNotNull(record.getY()));
					item.setAttribute("Type", MyStringUtil
							.object2StringNotNull(record.getInfoType()));
					item.setAttribute("RowSpace", MyStringUtil
							.object2StringNotNull(record.getRowSpace()));
					playlist.addContent(item);
				}
			}

			list.add(de);
		}
	}

	private void buildCovi(List<DasCovi> rows, List<Element> list) {
		for (DasCovi covi : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					covi.getStandardNumber() != null ? covi.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_COVI + "");
			de.setAttribute("WorkStatus", covi.getStatus() != null ? covi
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", covi.getCommStatus() != null ? covi
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", covi.getRecTime() != null ? covi
					.getRecTime().getTime() + "" : "");

			Element cOConctElement = new Element("COConct");
			cOConctElement.setText(covi.getCo() != null ? covi.getCo()
					.toString() : "");
			de.addContent(cOConctElement);

			Element visibilityElement = new Element("Visibility");
			visibilityElement.setText(covi.getVi() != null ? covi.getVi()
					.toString() : "");
			de.addContent(visibilityElement);

			list.add(de);
		}
	}

	private void buildCovi1(List<DasCoviReal> rows, List<Element> list) {
		for (DasCoviReal covi : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					covi.getStandardNumber() != null ? covi.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_COVI + "");
			de.setAttribute("WorkStatus", covi.getStatus() != null ? covi
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", covi.getCommStatus() != null ? covi
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", covi.getRecTime() != null ? covi
					.getRecTime().getTime() + "" : "");

			Element cOConctElement = new Element("COConct");
			cOConctElement.setText(covi.getCo() != null ? covi.getCo()
					.toString() : "");
			de.addContent(cOConctElement);

			Element visibilityElement = new Element("Visibility");
			visibilityElement.setText(covi.getVi() != null ? covi.getVi()
					.toString() : "");
			de.addContent(visibilityElement);

			list.add(de);
		}
	}

	private void buildCD(List<DasControlDevice> rows, List<Element> list) {
		for (DasControlDevice cd : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					cd.getStandardNumber() != null ? cd.getStandardNumber()
							: "");
			de.setAttribute("Type", cd.getType() != null ? cd.getType()
					.toString() : "");
			de.setAttribute("WorkStatus", cd.getStatus() != null ? cd
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", cd.getCommStatus() != null ? cd
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", cd.getRecTime() != null ? cd
					.getRecTime().getTime() + "" : "");

			// 风机有档位特殊处理
			if (TypeDefinition.DEVICE_TYPE_FAN == cd.getType().intValue()) {
				Element statusElement = new Element("Status");
				statusElement.setText(cd.getWorkState());
				de.addContent(statusElement);

				// 暂时采用预留字段来取值
				Element shiftElement = new Element("Shift");
				shiftElement.setText(cd.getReserve());
				de.addContent(shiftElement);
			} else if (TypeDefinition.DEVICE_TYPE_PB == cd.getType().intValue()) {
				// 暂时采用预留字段来取值
				de.setAttribute("Status",
						cd.getWorkState() != null ? cd.getWorkState() + "" : "");
				Element pBTimeElement = new Element("PBTime");
				pBTimeElement.setText(cd.getReserve());
				de.addContent(pBTimeElement);
			} else {
				Element statusElement = new Element("Status");
				statusElement.setText(cd.getWorkState());
				de.addContent(statusElement);
			}

			list.add(de);
		}
	}

	private void buildCD1(List<DasControlDeviceReal> rows, List<Element> list) {
		for (DasControlDeviceReal cd : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					cd.getStandardNumber() != null ? cd.getStandardNumber()
							: "");
			de.setAttribute("Type", cd.getType() != null ? cd.getType()
					.toString() : "");
			de.setAttribute("WorkStatus", cd.getStatus() != null ? cd
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", cd.getCommStatus() != null ? cd
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", cd.getRecTime() != null ? cd
					.getRecTime().getTime() + "" : "");

			// 风机有档位特殊处理
			if (TypeDefinition.DEVICE_TYPE_FAN == cd.getType().intValue()) {
				Element statusElement = new Element("Status");
				statusElement.setText(cd.getWorkState());
				de.addContent(statusElement);

				// 暂时采用预留字段来取值
				Element shiftElement = new Element("Shift");
				shiftElement.setText(cd.getReserve());
				de.addContent(shiftElement);
			} else if (TypeDefinition.DEVICE_TYPE_PB == cd.getType().intValue()) {
				// 暂时采用预留字段来取值
				de.setAttribute("Status",
						cd.getWorkState() != null ? cd.getWorkState() + "" : "");
				Element pBTimeElement = new Element("PBTime");
				pBTimeElement.setText(cd.getReserve());
				de.addContent(pBTimeElement);
			} else {
				Element statusElement = new Element("Status");
				statusElement.setText(cd.getWorkState());
				de.addContent(statusElement);
			}

			list.add(de);
		}
	}

	private void buildLil(List<DasLil> rows, List<Element> list) {
		for (DasLil lil : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					lil.getStandardNumber() != null ? lil.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_LIL + "");
			de.setAttribute("WorkStatus", lil.getStatus() != null ? lil
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", lil.getCommStatus() != null ? lil
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", lil.getRecTime() != null ? lil
					.getRecTime().getTime() + "" : "");

			Element fae = new Element("FrontArrowStatus");
			fae.setText(lil.getFrontArrowStatus() != null ? lil
					.getFrontArrowStatus().toString() : "");
			de.addContent(fae);

			Element fxe = new Element("FrontXStatus");
			fxe.setText(lil.getFrontXStatus() != null ? lil.getFrontXStatus()
					.toString() : "");
			de.addContent(fxe);

			Element bae = new Element("BackArrowStatus");
			bae.setText(lil.getBackArrowStatus() != null ? lil
					.getBackArrowStatus().toString() : "");
			de.addContent(bae);

			Element bxe = new Element("BackXStatus");
			bxe.setText(lil.getBackXStatus() != null ? lil.getBackXStatus()
					.toString() : "");
			de.addContent(bxe);

			list.add(de);
		}
	}

	private void buildLil1(List<DasLilReal> rows, List<Element> list) {
		for (DasLilReal lil : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					lil.getStandardNumber() != null ? lil.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_LIL + "");
			de.setAttribute("WorkStatus", lil.getStatus() != null ? lil
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", lil.getCommStatus() != null ? lil
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", lil.getRecTime() != null ? lil
					.getRecTime().getTime() + "" : "");

			Element fae = new Element("FrontArrowStatus");
			fae.setText(lil.getFrontArrowStatus() != null ? lil
					.getFrontArrowStatus().toString() : "");
			de.addContent(fae);

			Element fxe = new Element("FrontXStatus");
			fxe.setText(lil.getFrontXStatus() != null ? lil.getFrontXStatus()
					.toString() : "");
			de.addContent(fxe);

			Element bae = new Element("BackArrowStatus");
			bae.setText(lil.getBackArrowStatus() != null ? lil
					.getBackArrowStatus().toString() : "");
			de.addContent(bae);

			Element bxe = new Element("BackXStatus");
			bxe.setText(lil.getBackXStatus() != null ? lil.getBackXStatus()
					.toString() : "");
			de.addContent(bxe);

			list.add(de);
		}
	}

	private void buildTsl(List<DasTsl> rows, List<Element> list) {
		for (DasTsl tsl : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					tsl.getStandardNumber() != null ? tsl.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_TSL + "");
			de.setAttribute("WorkStatus", tsl.getStatus() != null ? tsl
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", tsl.getCommStatus() != null ? tsl
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", tsl.getRecTime() != null ? tsl
					.getRecTime().getTime() + "" : "");

			Element red = new Element("RedStatus");
			red.setText(tsl.getRedStatus() != null ? tsl.getRedStatus()
					.toString() : "");
			de.addContent(red);

			Element green = new Element("GreenStatus");
			green.setText(tsl.getGreenStatus() != null ? tsl.getGreenStatus()
					.toString() : "");
			de.addContent(green);

			Element yellow = new Element("YellowStatus");
			yellow.setText(tsl.getYellowStatus() != null ? tsl
					.getYellowStatus().toString() : "");
			de.addContent(yellow);

			Element turn = new Element("TurnStatus");
			turn.setText(tsl.getTurnStatus() != null ? tsl.getTurnStatus()
					.toString() : "");
			de.addContent(turn);

			list.add(de);
		}
	}

	private void buildTsl1(List<DasTslReal> rows, List<Element> list) {
		for (DasTslReal tsl : rows) {
			Element de = new Element("Device");
			de.setAttribute("StandardNumber",
					tsl.getStandardNumber() != null ? tsl.getStandardNumber()
							: "");
			de.setAttribute("Type", TypeDefinition.DEVICE_TYPE_TSL + "");
			de.setAttribute("WorkStatus", tsl.getStatus() != null ? tsl
					.getStatus().toString() : "");
			de.setAttribute("CommStatus", tsl.getCommStatus() != null ? tsl
					.getCommStatus().toString() : "");
			de.setAttribute("GatherTime", tsl.getRecTime() != null ? tsl
					.getRecTime().getTime() + "" : "");

			Element red = new Element("RedStatus");
			red.setText(tsl.getRedStatus() != null ? tsl.getRedStatus()
					.toString() : "");
			de.addContent(red);

			Element green = new Element("GreenStatus");
			green.setText(tsl.getGreenStatus() != null ? tsl.getGreenStatus()
					.toString() : "");
			de.addContent(green);

			Element yellow = new Element("YellowStatus");
			yellow.setText(tsl.getYellowStatus() != null ? tsl
					.getYellowStatus().toString() : "");
			de.addContent(yellow);

			Element turn = new Element("TurnStatus");
			turn.setText(tsl.getTurnStatus() != null ? tsl.getTurnStatus()
					.toString() : "");
			de.addContent(turn);

			list.add(de);
		}
	}

	@Override
	public List<DeviceStatusVO> statDeviceStatus(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[], String organSN,
			int start, int limit) {
		return dasDeviceStatusDAO.statDeviceStatus(beginTime, endTime, type,
				sns, organSN, start, limit);
	}

	@Override
	public List<String> getStandardNumberByNameAndType(String deviceName,
			Integer type) throws BusinessException {
		List<String> sns = new ArrayList<String>();
		// 查询设备名称对应的设备编号
		if (StringUtils.isNotBlank(deviceName)) {
			// 如果type不为空
			if (null != type) {
				if (type.intValue() == TypeDefinition.DEVICE_TYPE_VD) {
					List<VehicleDetector> vds = vdDAO
							.getEntityByName(deviceName);
					for (VehicleDetector vd : vds) {
						sns.add(vd.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_WS) {
					List<WindSpeed> wss = wsDAO.getEntityByName(deviceName);
					for (WindSpeed ws : wss) {
						sns.add(ws.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_WST) {
					List<WeatherStat> wsts = wstDAO.getEntityByName(deviceName);
					for (WeatherStat wst : wsts) {
						sns.add(wst.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_NOD) {
					List<NoDetector> nods = noDetectorDAO
							.getEntityByName(deviceName);
					for (NoDetector nod : nods) {
						sns.add(nod.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_LOLI) {
					List<LoLi> lolis = loliDAO.getEntityByName(deviceName);
					for (LoLi loli : lolis) {
						sns.add(loli.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_COVI) {
					List<Covi> covis = coviDAO.getEntityByName(deviceName);
					for (Covi covi : covis) {
						sns.add(covi.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_CMS) {
					Organ organ = organDAO.getRootOrgan();
					String organs[] = organDAO.findOrgansByOrganId(organ
							.getId());
					String cmses[] = controlDeviceDAO.cmsSNByOrgan(organs,
							deviceName);
					for (int i = 0; i < cmses.length; i++) {
						sns.add(cmses[i]);
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_FD) {
					List<FireDetector> fds = fireDetectorDAO
							.getEntityByName(deviceName);
					for (FireDetector fd : fds) {
						sns.add(fd.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_PB) {
					List<PushButton> pbs = pushButtonDAO
							.getEntityByName(deviceName);
					for (PushButton pb : pbs) {
						sns.add(pb.getStandardNumber());
					}
				} else if (type.intValue() == TypeDefinition.DEVICE_TYPE_FAN
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_LIGHT
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_RD
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_RAIL
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_WP
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_TSL
						|| type.intValue() == TypeDefinition.DEVICE_TYPE_LIL) {
					List<ControlDevice> cds = controlDeviceDAO
							.getEntityByName(deviceName);
					for (ControlDevice cd : cds) {
						sns.add(cd.getStandardNumber());
					}
				}
			}
			// type为空
			else {
				List<VehicleDetector> vds = vdDAO.getEntityByName(deviceName);
				for (VehicleDetector vd : vds) {
					sns.add(vd.getStandardNumber());
				}
				List<WindSpeed> wss = wsDAO.getEntityByName(deviceName);
				for (WindSpeed ws : wss) {
					sns.add(ws.getStandardNumber());
				}
				List<WeatherStat> wsts = wstDAO.getEntityByName(deviceName);
				for (WeatherStat wst : wsts) {
					sns.add(wst.getStandardNumber());
				}
				List<NoDetector> nods = noDetectorDAO
						.getEntityByName(deviceName);
				for (NoDetector nod : nods) {
					sns.add(nod.getStandardNumber());
				}
				List<LoLi> lolis = loliDAO.getEntityByName(deviceName);
				for (LoLi loli : lolis) {
					sns.add(loli.getStandardNumber());
				}
				List<Covi> covis = coviDAO.getEntityByName(deviceName);
				for (Covi covi : covis) {
					sns.add(covi.getStandardNumber());
				}
				Organ organ = organDAO.getRootOrgan();
				String organs[] = organDAO.findOrgansByOrganId(organ.getId());
				String cmses[] = controlDeviceDAO.cmsSNByOrgan(organs,
						deviceName);
				for (int i = 0; i < cmses.length; i++) {
					sns.add(cmses[i]);
				}
				List<FireDetector> fds = fireDetectorDAO
						.getEntityByName(deviceName);
				for (FireDetector fd : fds) {
					sns.add(fd.getStandardNumber());
				}
				List<PushButton> pbs = pushButtonDAO
						.getEntityByName(deviceName);
				for (PushButton pb : pbs) {
					sns.add(pb.getStandardNumber());
				}
				List<ControlDevice> cds = controlDeviceDAO
						.getEntityByName(deviceName);
				for (ControlDevice cd : cds) {
					sns.add(cd.getStandardNumber());
				}
			}
			if (sns.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Device type[" + type + "]" + " name[" + deviceName
								+ "] not found !");
			}
		}
		return sns;
	}

	@Override
	public void completeStatCovi(List<CoviVO> list) {
		if (list.size() < 1) {
			return;
		}
		Set<String> coviSNSet = new HashSet<String>();
		// 设备SN归类
		for (CoviVO vo : list) {
			coviSNSet.add(vo.getStandardNumber());
		}

		String[] sns = coviSNSet.toArray(new String[0]);
		Map<String, Covi> coviMap = coviDAO.mapCoviBySNs(sns);
		// 再次迭代补充完整数据
		for (CoviVO vo : list) {
			Covi covi = coviMap.get(vo.getStandardNumber());
			if (null != covi) {
				vo.setNavigation(covi.getNavigation());
				vo.setOrganName(covi.getOrgan().getName());
				vo.setStakeNumber(covi.getStakeNumber());
				vo.setName(covi.getName());
			}
		}
	}

	@Override
	public void completeStatDeviceStatus(List<DeviceStatusVO> list) {
		// 本平台信息
		Organ root = organDAO.getRootOrgan();
		// 是否存在其他平台设备标志
		boolean existSubPlatform = false;
		// 车检器设备SN
		Set<String> vdSet = null;
		// 气象监测器设备SN
		Set<String> wstSet = null;
		// 风速风向监测器设备SN
		Set<String> wsSet = null;
		// 光强监测器设备SN
		Set<String> loliSet = null;
		// 氮氧监测器设备SN
		Set<String> nodSet = null;
		// COVI监测器设备SN
		Set<String> coviSet = null;
		// 情报板设备SN
		Set<String> cmsSet = null;
		// 火灾监测器设备SN
		Set<String> fdSet = null;
		// 手动报警按钮设备SN
		Set<String> pbSet = null;
		// 控制类设备SN
		Set<String> cdSet = null;

		for (DeviceStatusVO vo : list) {
			// 外平台的SN记录
			if (!root.getStandardNumber().equals(vo.getOrganName())) {
				existSubPlatform = true;
			}
			// 各类设备记录
			if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_VD + "")) {
				if (null == vdSet) {
					vdSet = new HashSet<String>();
				}
				vdSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_WS + "")) {
				if (null == wsSet) {
					wsSet = new HashSet<String>();
				}
				wsSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_WST + "")) {
				if (null == wstSet) {
					wstSet = new HashSet<String>();
				}
				wstSet.add(vo.getStandardNumber());
			} else if (vo.getType()
					.equals(TypeDefinition.DEVICE_TYPE_LOLI + "")) {
				if (null == loliSet) {
					loliSet = new HashSet<String>();
				}
				loliSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_NOD + "")) {
				if (null == nodSet) {
					nodSet = new HashSet<String>();
				}
				nodSet.add(vo.getStandardNumber());
			} else if (vo.getType()
					.equals(TypeDefinition.DEVICE_TYPE_COVI + "")) {
				if (null == coviSet) {
					coviSet = new HashSet<String>();
				}
				coviSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
				if (null == cmsSet) {
					cmsSet = new HashSet<String>();
				}
				cmsSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
				if (null == fdSet) {
					fdSet = new HashSet<String>();
				}
				fdSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
				if (null == pbSet) {
					pbSet = new HashSet<String>();
				}
				pbSet.add(vo.getStandardNumber());
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_FAN + "")
					|| vo.getType().equals(
							TypeDefinition.DEVICE_TYPE_LIGHT + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_RD + "")
					|| vo.getType()
							.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_WP + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_IS + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_TSL + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_LIL + "")) {
				if (null == cdSet) {
					cdSet = new HashSet<String>();
				}
				cdSet.add(vo.getStandardNumber());
			}
		}

		// 其他平台SN映射Map
		Map<String, SubPlatform> subPlatformMap = null;
		if (existSubPlatform) {
			subPlatformMap = subPlatformDAO.mapSubPlatform();
		}

		// key是设备SN,value是设备实体对象
		Map<String, VehicleDetector> vdMap = null;
		Map<String, WeatherStat> wstMap = null;
		Map<String, WindSpeed> wsMap = null;
		Map<String, LoLi> loliMap = null;
		Map<String, NoDetector> nodMap = null;
		Map<String, Covi> coviMap = null;
		Map<String, ControlDevice> cmsMap = null;
		Map<String, FireDetector> fdMap = null;
		Map<String, PushButton> pbMap = null;
		Map<String, ControlDevice> cdMap = null;

		// 根据各类设备SN分别去所在的表查询设备信息
		if (null != vdSet) {
			String[] sns = vdSet.toArray(new String[0]);
			vdMap = vdDAO.mapVDBySNs(sns);
		}
		if (null != wstSet) {
			String[] sns = wstSet.toArray(new String[0]);
			wstMap = wstDAO.mapWSTBySNs(sns);
		}
		if (null != wsSet) {
			String[] sns = wsSet.toArray(new String[0]);
			wsMap = wsDAO.mapWSBySNs(sns);
		}
		if (null != loliSet) {
			String[] sns = loliSet.toArray(new String[0]);
			loliMap = loliDAO.mapLoliBySNs(sns);
		}
		if (null != nodSet) {
			String[] sns = nodSet.toArray(new String[0]);
			nodMap = noDetectorDAO.mapNODBySNs(sns);
		}
		if (null != coviSet) {
			String[] sns = coviSet.toArray(new String[0]);
			coviMap = coviDAO.mapCoviBySNs(sns);
		}
		if (null != cmsSet) {
			String[] sns = cmsSet.toArray(new String[0]);
			cmsMap = controlDeviceDAO.mapCDBySNs(sns);
		}
		if (null != fdSet) {
			String[] sns = fdSet.toArray(new String[0]);
			fdMap = fireDetectorDAO.mapFDBySNs(sns);
		}
		if (null != pbSet) {
			String[] sns = pbSet.toArray(new String[0]);
			pbMap = pushButtonDAO.mapPBBySNs(sns);
		}
		if (null != cdSet) {
			String[] sns = cdSet.toArray(new String[0]);
			cdMap = controlDeviceDAO.mapCDBySNs(sns);
		}

		// 再次迭代结果集，添加查询出来的信息
		for (DeviceStatusVO vo : list) {
			String organSN = vo.getOrganName();
			if (organSN.equals(root.getStandardNumber())) {
				vo.setOrganName(root.getName());
			} else {
				SubPlatform platform = subPlatformMap.get(organSN);
				// 能够找到外平台的名称则赋值名称，否则还是采用SN
				if (null != platform
						&& StringUtils.isNotBlank(platform.getName())) {
					vo.setOrganName(platform.getName());
				}
			}
			if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_VD + "")) {
				VehicleDetector vd = vdMap.get(vo.getStandardNumber());
				if (vd != null) {
					vo.setNavigation(vd.getNavigation());
					vo.setOrganName(vd.getOrgan().getName());
					vo.setStakeNumber(vd.getStakeNumber());
					vo.setName(vd.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_WS + "")) {
				WindSpeed ws = wsMap.get(vo.getStandardNumber());
				if (ws != null) {
					vo.setNavigation(ws.getNavigation());
					vo.setOrganName(ws.getOrgan().getName());
					vo.setStakeNumber(ws.getStakeNumber());
					vo.setName(ws.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_WST + "")) {
				WeatherStat wst = wstMap.get(vo.getStandardNumber());
				if (wst != null) {
					vo.setNavigation(wst.getNavigation());
					vo.setOrganName(wst.getOrgan().getName());
					vo.setStakeNumber(wst.getStakeNumber());
					vo.setName(wst.getName());
				}
			} else if (vo.getType()
					.equals(TypeDefinition.DEVICE_TYPE_LOLI + "")) {
				LoLi loli = loliMap.get(vo.getStandardNumber());
				if (loli != null) {
					vo.setNavigation(loli.getNavigation());
					vo.setOrganName(loli.getOrgan().getName());
					vo.setStakeNumber(loli.getStakeNumber());
					vo.setName(loli.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_NOD + "")) {
				NoDetector nod = nodMap.get(vo.getStandardNumber());
				if (nod != null) {
					vo.setNavigation(nod.getNavigation());
					vo.setOrganName(nod.getOrgan().getName());
					vo.setStakeNumber(nod.getStakeNumber());
					vo.setName(nod.getName());
				}
			} else if (vo.getType()
					.equals(TypeDefinition.DEVICE_TYPE_COVI + "")) {
				Covi covi = coviMap.get(vo.getStandardNumber());
				if (covi != null) {
					vo.setNavigation(covi.getNavigation());
					vo.setOrganName(covi.getOrgan().getName());
					vo.setStakeNumber(covi.getStakeNumber());
					vo.setName(covi.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
				ControlDevice cms = cmsMap.get(vo.getStandardNumber());
				if (cms != null) {
					vo.setNavigation(cms.getNavigation());
					vo.setOrganName(cms.getOrgan().getName());
					vo.setStakeNumber(cms.getStakeNumber());
					vo.setName(cms.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
				FireDetector fd = fdMap.get(vo.getStandardNumber());
				if (fd != null) {
					vo.setNavigation(fd.getNavigation());
					vo.setOrganName(fd.getOrgan().getName());
					vo.setStakeNumber(fd.getStakeNumber());
					vo.setName(fd.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
				PushButton pb = pbMap.get(vo.getStandardNumber());
				if (pb != null) {
					vo.setNavigation(pb.getNavigation());
					vo.setOrganName(pb.getOrgan().getName());
					vo.setStakeNumber(pb.getStakeNumber());
					vo.setName(pb.getName());
				}
			} else if (vo.getType().equals(TypeDefinition.DEVICE_TYPE_FAN + "")
					|| vo.getType().equals(
							TypeDefinition.DEVICE_TYPE_LIGHT + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_RD + "")
					|| vo.getType()
							.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_WP + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_IS + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_TSL + "")
					|| vo.getType().equals(TypeDefinition.DEVICE_TYPE_LIL + "")) {
				ControlDevice cd = cdMap.get(vo.getStandardNumber());
				if (cd != null) {
					vo.setNavigation(cd.getNavigation());
					vo.setOrganName(cd.getOrgan().getName());
					vo.setStakeNumber(cd.getStakeNumber());
					vo.setName(cd.getName());
				}
			}
		}
	}

	@Override
	public void completeStatLoLi(List<LoLiVO> list) {
		if (list.size() < 1) {
			return;
		}
		Set<String> loLiSNSet = new HashSet<String>();
		// 设备SN归类
		for (LoLiVO vo : list) {
			loLiSNSet.add(vo.getStandardNumber());
		}

		String[] sns = loLiSNSet.toArray(new String[0]);
		Map<String, LoLi> loLiMap = loliDAO.mapLoliBySNs(sns);
		// 再次迭代补充完整数据
		for (LoLiVO vo : list) {
			LoLi loLi = loLiMap.get(vo.getStandardNumber());
			if (null != loLi) {
				vo.setNavigation(loLi.getNavigation());
				vo.setOrganName(loLi.getOrgan().getName());
				vo.setStakeNumber(loLi.getStakeNumber());
				vo.setName(loLi.getName());
			}
		}
	}

	@Override
	public void completeStatVD(List<VdVO> list) {
		if (list.size() < 1) {
			return;
		}
		Set<String> vdSNSet = new HashSet<String>();
		// 设备SN归类
		for (VdVO vo : list) {
			vdSNSet.add(vo.getStandardNumber());
		}

		String[] sns = vdSNSet.toArray(new String[0]);
		Map<String, VehicleDetector> vdMap = vdDAO.mapVDBySNs(sns);
		// 再次迭代补充完整数据
		for (VdVO vo : list) {
			VehicleDetector vd = vdMap.get(vo.getStandardNumber());
			if (null != vd) {
				vo.setNavigation(vd.getNavigation());
				vo.setOrganName(vd.getOrgan().getName());
				vo.setStakeNumber(vd.getStakeNumber());
				vo.setName(vd.getName());
			}
		}
	}

	@Override
	public void completeStatWS(List<WsVO> list) {
		if (list.size() < 1) {
			return;
		}
		Set<String> wsSNSet = new HashSet<String>();
		// 设备SN归类
		for (WsVO vo : list) {
			wsSNSet.add(vo.getStandardNumber());
		}

		String[] sns = wsSNSet.toArray(new String[0]);
		Map<String, WindSpeed> wsMap = wsDAO.mapWSBySNs(sns);
		// 再次迭代补充完整数据
		for (WsVO vo : list) {
			WindSpeed ws = wsMap.get(vo.getStandardNumber());
			if (null != ws) {
				vo.setNavigation(ws.getNavigation());
				vo.setOrganName(ws.getOrgan().getName());
				vo.setStakeNumber(ws.getStakeNumber());
				vo.setName(ws.getName());
			}
		}
	}

	@Override
	public void completeStatWST(List<WstVO> list) {
		if (list.size() < 1) {
			return;
		}
		Set<String> wstSNSet = new HashSet<String>();
		// 设备SN归类
		for (WstVO vo : list) {
			wstSNSet.add(vo.getStandardNumber());
		}

		String[] sns = wstSNSet.toArray(new String[0]);
		Map<String, WeatherStat> wstMap = wstDAO.mapWSTBySNs(sns);
		// 再次迭代补充完整数据
		for (WstVO vo : list) {
			WeatherStat wst = wstMap.get(vo.getStandardNumber());
			if (null != wst) {
				vo.setNavigation(wst.getNavigation());
				vo.setOrganName(wst.getOrgan().getName());
				vo.setStakeNumber(wst.getStakeNumber());
				vo.setName(wst.getName());
			}
		}
	}

	@Override
	public int countCovi(Timestamp beginTime, Timestamp endTime, String sns[],
			String organSN, String scope) {
		return dasCoviDAO.countCovi(beginTime, endTime, scope, sns, organSN);
	}

	@Override
	public int countDeviceStatus(Timestamp beginTime, Timestamp endTime,
			Integer type, String[] sns, String organSN) {
		return dasDeviceStatusDAO.countDeviceStatus(beginTime, endTime, type,
				sns, organSN);
	}

	@Override
	public int countLoLi(Timestamp beginTime, Timestamp endTime, String sns[],
			String organSN, String scope) {
		return dasLoliDAO.countLoLi(beginTime, endTime, scope, sns, organSN);
	}

	@Override
	public int countVD(Timestamp beginTime, Timestamp endTime, String sns[],
			String organSN, String scope) {
		return dasVdDAO.countVD(beginTime, endTime, scope, sns, organSN);
	}

	@Override
	public int countWS(Timestamp beginTime, Timestamp endTime, String sns[],
			String organSN, String scope) {
		return dasWsDAO.countWS(beginTime, endTime, scope, sns, organSN);
	}

	@Override
	public int countWST(Timestamp beginTime, Timestamp endTime,
			String standardNumber, String organSN, String scope) {
		return dasWstDAO.countWST(beginTime, endTime, scope, standardNumber,
				organSN);
	}

	@Override
	public Element getResourceRouteInfo(String standardNumber)
			throws BusinessException {
		StandardNumber sn = snDAO.getBySN(standardNumber);
		if (null == sn) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"StandardNumber[" + standardNumber + "] not found");
		}
		String classType = sn.getClassType();
		// Camera
		if (TypeDefinition.RESOURCE_TYPE_CAMERA.equals(classType)) {
			Camera camera = cameraDAO.findBySN(standardNumber);
			Ccs ccs = camera.getParent().getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Dvr
		if (TypeDefinition.RESOURCE_TYPE_DVR.equals(classType)) {
			Dvr dvr = dvrDAO.findBySN(standardNumber);
			Ccs ccs = dvr.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Ccs
		if (TypeDefinition.RESOURCE_TYPE_CCS.equals(classType)) {
			Ccs ccs = ccsDAO.findBySN(standardNumber);
			return buildRouteByCcs(ccs);
		}
		// Monitor
		if (TypeDefinition.RESOURCE_TYPE_MONITOR.equals(classType)) {
			Monitor monitor = monitorDAO.findBySN(standardNumber);
			Dws dws = monitor.getDws();
			if (null == dws) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDws(dws);
		}
		// User
		if (TypeDefinition.RESOURCE_TYPE_USER.equals(classType)) {
			User user = userDAO.findBySN(standardNumber);
			Ccs ccs = user.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Mss
		if (TypeDefinition.RESOURCE_TYPE_MSS.equals(classType)) {
			Mss mss = mssDAO.findBySN(standardNumber);
			Ccs ccs = mss.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Crs
		if (TypeDefinition.RESOURCE_TYPE_CRS.equals(classType)) {
			Crs crs = crsDAO.findBySN(standardNumber);
			Ccs ccs = crs.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Das
		if (TypeDefinition.RESOURCE_TYPE_DAS.equals(classType)) {
			Das das = dasDAO.findBySN(standardNumber);
			return buildRouteByDas(das);
		}
		// VD
		if (TypeDefinition.RESOURCE_TYPE_VD.equals(classType)) {
			VehicleDetector vd = vdDAO.findBySN(standardNumber);
			Das das = vd.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// WST
		if (TypeDefinition.RESOURCE_TYPE_WST.equals(classType)) {
			WeatherStat wst = wstDAO.findBySN(standardNumber);
			Das das = wst.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// WS
		if (TypeDefinition.RESOURCE_TYPE_WS.equals(classType)) {
			WindSpeed ws = wsDAO.findBySN(standardNumber);
			Das das = ws.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// LOLI
		if (TypeDefinition.RESOURCE_TYPE_LOLI.equals(classType)) {
			LoLi loli = loliDAO.findBySN(standardNumber);
			Das das = loli.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// NOD
		if (TypeDefinition.RESOURCE_TYPE_NOD.equals(classType)) {
			NoDetector nod = noDetectorDAO.findBySN(standardNumber);
			Das das = nod.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// COVI
		if (TypeDefinition.RESOURCE_TYPE_COVI.equals(classType)) {
			Covi covi = coviDAO.findBySN(standardNumber);
			Das das = covi.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// FD
		if (TypeDefinition.RESOURCE_TYPE_FD.equals(classType)) {
			FireDetector fd = fireDetectorDAO.findBySN(standardNumber);
			Das das = fd.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// PB
		if (TypeDefinition.RESOURCE_TYPE_PB.equals(classType)) {
			PushButton pb = pushButtonDAO.findBySN(standardNumber);
			Das das = pb.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// CD
		if (TypeDefinition.RESOURCE_TYPE_CD.equals(classType)) {
			ControlDevice cd = controlDeviceDAO.findBySN(standardNumber);
			Das das = cd.getDas();
			if (null == das) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByDas(das);
		}
		// Dws
		if (TypeDefinition.RESOURCE_TYPE_DWS.equals(classType)) {
			Dws dws = dwsDAO.findBySN(standardNumber);
			Ccs ccs = dws.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}
		// Ens
		if (TypeDefinition.RESOURCE_TYPE_ENS.equals(classType)) {
			Ens ens = ensDAO.findBySN(standardNumber);
			Ccs ccs = ens.getCcs();
			if (null == ccs) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			}
			return buildRouteByCcs(ccs);
		}

		throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Class["
				+ classType + "] not support !");
	}

	/**
	 * 构建注册在CCS上的资源路由信息
	 * 
	 * @param ccs
	 *            通信服务器
	 * @return {@code <Route LanIp="" Port="" />} 格式的资源路由信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-7 上午10:00:17
	 */
	private Element buildRouteByCcs(Ccs ccs) {
		Element element = new Element("Route");
		element.setAttribute("LanIp", ccs.getLanIp() != null ? ccs.getLanIp()
				: "");
		element.setAttribute("Port", ccs.getPort() != null ? ccs.getPort() + ""
				: "");
		return element;
	}

	/**
	 * 构建注册在DWS上的资源路由信息
	 * 
	 * @param dws
	 *            电视墙服务器
	 * @return {@code <Route LanIp="" Port="" />} 格式的资源路由信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-7 上午10:10:14
	 */
	private Element buildRouteByDws(Dws dws) {
		Element element = new Element("Route");
		element.setAttribute("LanIp", dws.getLanIp() != null ? dws.getLanIp()
				: "");
		element.setAttribute("Port", dws.getPort() != null ? dws.getPort() + ""
				: "");
		return element;
	}

	/**
	 * 构建注册在DAS上的资源路由信息
	 * 
	 * @param das
	 *            数据采集服务器
	 * @return {@code <Route LanIp="" Port="" />} 格式的资源路由信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-7 上午10:35:45
	 */
	private Element buildRouteByDas(Das das) {
		Element element = new Element("Route");
		element.setAttribute("LanIp", das.getLanIp() != null ? das.getLanIp()
				: "");
		element.setAttribute("Port", das.getPort() != null ? das.getPort() + ""
				: "");
		return element;
	}

	@Override
	public void removeSwitchData() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CoviVO> statCovi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start, int limit) {
		return dasCoviDAO.statCovi(beginTime, endTime, scope, sns, organSN,
				start, limit);
	}

	@Override
	public List<LoLiVO> statLoLi(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start, int limit) {
		return dasLoliDAO.statLoLi(beginTime, endTime, scope, sns, organSN,
				start, limit);
	}

	@Override
	public List<VdVO> statVD(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start, int limit) {
		return dasVdDAO.statVD(beginTime, endTime, scope, sns, organSN, start,
				limit);
	}

	@Override
	public List<WsVO> statWS(Timestamp beginTime, Timestamp endTime,
			String sns[], String organSN, String scope, int start, int limit) {
		return dasWsDAO.statWS(beginTime, endTime, scope, sns, organSN, start,
				limit);
	}

	@Override
	public List<WstVO> statWST(Timestamp beginTime, Timestamp endTime,
			String standardNumber, String organSN, String scope, int start,
			int limit) {
		return dasWstDAO.statWST(beginTime, endTime, scope, standardNumber,
				organSN, start, limit);
	}

	@Override
	public VehicleDetectorVO statVehicleDetector(String id,
			Timestamp beginTime, Timestamp endTime) {
		VehicleDetectorVO vo = new VehicleDetectorVO();
		Object obj = dasVdDAO.statVehicleDetectorTotal(id);

		vo = dasVdDAO.statVehicleDetector(id, beginTime, endTime);
		VehicleDetector vd = vdDAO.findById(id);
		vo.setName(vd.getName());
		vo.setTotalFlux(obj == null ? "" : obj + "");
		return vo;
	}

	@Override
	public VehicleDetectorTotalVO statOrganTotalVD(String organId) {
		Timestamp yestodayBegin = getYesTodayBegin();
		Timestamp yestodayEnd = getYesTodayEnd();
		VehicleDetectorTotalVO vo = new VehicleDetectorTotalVO();
		vo = dasVdDAO.statOrganYesTodayTotalVD(organId, yestodayBegin,
				yestodayEnd);
		Organ organ = organDAO.findById(organId);
		vo.setName(organ.getName());
		vo.setId(organ.getId());
		VehicleDetectorTotalVO totalvo = dasVdDAO.statOrganTotalVD(organId);
		vo.setEtcTotal(totalvo.getEtcTotal());
		vo.setFreightTotal(totalvo.getFreightTotal());
		vo.setTotalFlow(totalvo.getTotalFlow());
		return vo;
	}

	// 获取当前时间的前一天开始时间
	public Timestamp getYesTodayBegin() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.MILLISECOND, 0);
		Timestamp yesTodayBegin = new Timestamp(c.getTimeInMillis());
		return yesTodayBegin;
	}

	// 获取当前时间前一天结束时间
	public Timestamp getYesTodayEnd() {
		Calendar c = Calendar.getInstance();
		// c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.MILLISECOND, 0);
		Timestamp yesTodayBegin = new Timestamp(c.getTimeInMillis());
		return yesTodayBegin;
	}

	@Override
	public List<OrganVehicleDetectorTopVO> trafficFlowTop(Timestamp beginTime,
			Timestamp endTime) {
		List<OrganVehicleDetectorTopVO> list = dasVdDAO.trafficFlowTop(
				beginTime, endTime);
		for (OrganVehicleDetectorTopVO vo : list) {
			vo.setName(organDAO.findById(vo.getId()).getName());
		}
		return list;
	}

	@Override
	public OrganVDVO roadTrafficFlow(String organId, Timestamp beginTime,
			Timestamp endTime) {
		OrganVDVO vo = new OrganVDVO();
		Object obj = dasVdDAO.roadTrafficFlowTotal(organId);

		vo = dasVdDAO.roadTrafficFlow(organId, beginTime, endTime);
		Organ organ = organDAO.findById(organId);
		vo.setName(organ.getName());
		vo.setTotalFlow(obj == null ? "" : obj + "");
		return vo;
	}

	@Override
	public int countTotalDeviceStatus(String sns[], Long time, Integer type) {
		Timestamp beginTime = getBeginTime(time);
		Timestamp endTime = getEndTime(time);
		int count = dasDeviceStatusDAO.countTotalDeviceStatus(sns, beginTime,
				endTime, type);
		return count;
	}

	private Timestamp getEndTime(Long time) {
		Date date = new Date(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
		return endTime;
	}

	private Timestamp getBeginTime(Long time) {
		Date date = new Date(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
		return beginTime;
	}

	@Override
	public List<DeviceStatusVO> deviceStatusStat(Long time, Integer type,
			String sns[], String organSN, Integer startIndex, Integer limit) {
		Timestamp beginTime = getBeginTime(time);
		Timestamp endTime = getEndTime(time);
		List<DeviceStatusVO> list = dasDeviceStatusDAO.deviceStatusStat(
				beginTime, endTime, type, sns, organSN, startIndex, limit);
		return list;
	}

	@Override
	public Element vdStat(String organId, Timestamp beginTime,
			Timestamp endTime, Integer startIndex, Integer limit, String name,
			String flag) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		List<VehicleDetector> vds = vdDAO.listVd(organs, name);
		List<VehicleDetector> subVds = vdDAO.listSubVd(organs, name);
		vds.addAll(subVds);
		String sn[] = new String[vds.size()];
		for (int i = 0; i < vds.size(); i++) {
			sn[i] = vds.get(i).getStandardNumber();
		}
		List<VdStatByDayVO> list = new ArrayList<VdStatByDayVO>();
		if (vds.size() > 0) {
			// 按日统计
			if (flag.equals("3")) {
				list = dasVdDAO.vdStatByHour(beginTime, endTime, sn,
						startIndex, limit);
			} else if (flag.equals("2")) {
				// 按月统计
				String beginTimeS = getStringTime(beginTime);
				String endTimeS = getStringTime(endTime);
				List<SyncVehicleDetector> svds = vdDAO.vdStatByMonth(
						beginTimeS, endTimeS, sn, startIndex, limit);
				for (SyncVehicleDetector svd : svds) {
					VdStatByDayVO vo = new VdStatByDayVO();
					vo.setStandardNumber(svd.getStandardNumber());
					vo.setRecTime(svd.getDateTime());
					vo.setUpOccs(svd.getUpOccs() + "");
					vo.setDwOccs(svd.getDwOccs() + "");
					vo.setUpOccm(svd.getUpOccm() + "");
					vo.setDwOccm(svd.getDwOccm() + "");
					vo.setUpOccb(svd.getUpOccb() + "");
					vo.setDwOccb(svd.getDwOccb() + "");
					vo.setUpOcc(svd.getUpOcc() + "");
					vo.setDwOcc(svd.getDwOcc() + "");
					vo.setUpSpeeds(svd.getUpSpeeds() + "");
					vo.setDwSpeeds(svd.getDwSpeeds() + "");
					vo.setUpSpeedm(svd.getUpSpeedm() + "");
					vo.setDwSpeedm(svd.getDwSpeedm() + "");
					vo.setUpSpeedb(svd.getUpSpeedb() + "");
					vo.setDwSpeedb(svd.getDwSpeedb() + "");
					vo.setUpSpeed(svd.getUpSpeed() + "");
					vo.setDwSpeed(svd.getDwSpeed() + "");
					vo.setUpFlows(svd.getUpFlows() + "");
					vo.setDwFlows(svd.getDwFlows() + "");
					vo.setUpFlowm(svd.getUpFlowm() + "");
					vo.setDwFlowm(svd.getUpFlowm() + "");
					vo.setUpFlowb(svd.getUpFlowb() + "");
					vo.setDwFlowb(svd.getDwFlowb() + "");
					vo.setUpFlow(svd.getUpFlow() + "");
					vo.setDwFlow(svd.getDwFlow() + "");
					vo.setUpFlowTotal(svd.getUpFlowTotal() + "");
					vo.setDwFlowTotal(svd.getDwFlowTotal() + "");
					list.add(vo);
				}
			} else if (flag.equals("1")) {
				// 按年统计
				String beginTimeS = getStringTime(beginTime);
				String endTimeS = getStringTime(endTime);
				list = vdDAO.vdStatByYear(beginTimeS, endTimeS, sn, startIndex,
						limit);
			}
		}

		Element devices = new Element("Devices");
		List<Element> listElement = new ArrayList<Element>();
		for (int n = 0; n < list.size(); n++) {
			Element de = new Element("Device");
			de.setAttribute("UpOccs", list.get(n).getUpOccs() != null ? list
					.get(n).getUpOccs().length() > 6 ? list.get(n).getUpOccs()
					.substring(0, 6) : list.get(n).getUpOccs() : "");
			de.setAttribute("DwOccs", list.get(n).getDwOccs() != null ? list
					.get(n).getDwOccs().length() > 6 ? list.get(n).getDwOccs()
					.substring(0, 6) : list.get(n).getDwOccs() : "");
			de.setAttribute("UpOccm", list.get(n).getUpOccm() != null ? list
					.get(n).getUpOccm().length() > 6 ? list.get(n).getUpOccm()
					.substring(0, 6) : list.get(n).getUpOccm() : "");
			de.setAttribute("DwOccm", list.get(n).getDwOccm() != null ? list
					.get(n).getDwOccm().length() > 6 ? list.get(n).getDwOccm()
					.substring(0, 6) : list.get(n).getDwOccm() : "");
			de.setAttribute("UpOccb", list.get(n).getUpOccb() != null ? list
					.get(n).getUpOccb().length() > 6 ? list.get(n).getUpOccb()
					.substring(0, 6) : list.get(n).getUpOccb() : "");
			de.setAttribute("DwOccb", list.get(n).getDwOccb() != null ? list
					.get(n).getDwOccb().length() > 6 ? list.get(n).getDwOccb()
					.substring(0, 6) : list.get(n).getDwOccb() : "");
			de.setAttribute(
					"UpOcc",
					list.get(n).getUpOcc() != null ? list.get(n).getUpOcc()
							.length() > 6 ? list.get(n).getUpOcc()
							.substring(0, 6) : list.get(n).getUpOcc() : "");
			de.setAttribute(
					"DwOcc",
					list.get(n).getDwOcc() != null ? list.get(n).getDwOcc()
							.length() > 6 ? list.get(n).getDwOcc()
							.substring(0, 6) : list.get(n).getDwOcc() : "");
			de.setAttribute("UpSpeeds",
					list.get(n).getUpSpeeds() != null ? list.get(n)
							.getUpSpeeds().length() > 6 ? list.get(n)
							.getUpSpeeds().substring(0, 6) : list.get(n)
							.getUpSpeeds() : "");
			de.setAttribute("DwSpeeds",
					list.get(n).getDwSpeeds() != null ? list.get(n)
							.getDwSpeeds().length() > 6 ? list.get(n)
							.getDwSpeeds().substring(0, 6) : list.get(n)
							.getDwSpeeds() : "");
			de.setAttribute("UpSpeedm",
					list.get(n).getUpSpeedm() != null ? list.get(n)
							.getUpSpeedm().length() > 6 ? list.get(n)
							.getUpSpeedm().substring(0, 6) : list.get(n)
							.getUpSpeedm() : "");
			de.setAttribute("DwSpeedm",
					list.get(n).getDwSpeedm() != null ? list.get(n)
							.getDwSpeedm().length() > 6 ? list.get(n)
							.getDwSpeedm().substring(0, 6) : list.get(n)
							.getDwSpeedm() : "");
			de.setAttribute("UpSpeedb",
					list.get(n).getUpSpeedb() != null ? list.get(n)
							.getUpSpeedb().length() > 6 ? list.get(n)
							.getUpSpeedb().substring(0, 6) : list.get(n)
							.getUpSpeedb() : "");
			de.setAttribute("DwSpeedb",
					list.get(n).getDwSpeedb() != null ? list.get(n)
							.getDwSpeedb().length() > 6 ? list.get(n)
							.getDwSpeedb().substring(0, 6) : list.get(n)
							.getDwSpeedb() : "");
			de.setAttribute("UpSpeed", list.get(n).getUpSpeed() != null ? list
					.get(n).getUpSpeed().length() > 6 ? list.get(n)
					.getUpSpeed().substring(0, 6) : list.get(n).getUpSpeed()
					: "");
			de.setAttribute("DwSpeed", list.get(n).getDwSpeed() != null ? list
					.get(n).getDwSpeed().length() > 6 ? list.get(n)
					.getDwSpeed().substring(0, 6) : list.get(n).getDwSpeed()
					: "");
			de.setAttribute("UpFlows", list.get(n).getUpFlows() != null ? list
					.get(n).getUpFlows() : "");
			de.setAttribute("DwFlows", list.get(n).getDwFlows() != null ? list
					.get(n).getDwFlows() : "");
			de.setAttribute("UpFlowm", list.get(n).getUpFlowm() != null ? list
					.get(n).getUpFlowm() : "");
			de.setAttribute("DwFlowm", list.get(n).getDwFlowm() != null ? list
					.get(n).getDwFlowm() : "");
			de.setAttribute("UpFlowb", list.get(n).getUpFlowb() != null ? list
					.get(n).getUpFlowb() : "");
			de.setAttribute("DwFlowb", list.get(n).getDwFlowb() != null ? list
					.get(n).getDwFlowb().length() > 6 ? list.get(n)
					.getDwFlowb().substring(0, 6) : list.get(n).getDwFlowb()
					: "");
			de.setAttribute("UpFlow", list.get(n).getUpFlow() != null ? list
					.get(n).getUpFlow().length() > 6 ? list.get(n).getUpFlow()
					.substring(0, 6) : list.get(n).getUpFlow() : "");
			de.setAttribute("DwFlow", list.get(n).getDwFlow() != null ? list
					.get(n).getDwFlow().length() > 6 ? list.get(n).getDwFlow()
					.substring(0, 6) : list.get(n).getDwFlow() : "");
			de.setAttribute("UpFlowTotal",
					list.get(n).getUpFlowTotal() != null ? list.get(n)
							.getUpFlowTotal() : "");
			de.setAttribute("DwFlowTotal",
					list.get(n).getDwFlowTotal() != null ? list.get(n)
							.getDwFlowTotal() : "");
			de.setAttribute("RecTime", list.get(n).getRecTime() != null ? list
					.get(n).getRecTime() : "");
			for (int m = 0; m < vds.size(); m++) {
				if (list.get(n).getStandardNumber()
						.equals(vds.get(m).getStandardNumber())) {
					de.setAttribute("Name", vds.get(m).getName() != null ? vds
							.get(m).getName() : "");
					continue;
				}
			}
			listElement.add(de);
		}
		devices.addContent(listElement);
		return devices;
	}

	@Override
	public int vdStatByDayTotal(String organId, Timestamp beginTime,
			Timestamp endTime, String name, String flag) {
		String[] organs = organDAO.findOrgansByOrganId(organId);
		List<VehicleDetector> vds = vdDAO.listVd(organs, name);
		String sn[] = new String[vds.size()];
		for (int i = 0; i < vds.size(); i++) {
			sn[i] = vds.get(i).getStandardNumber();
		}
		int totalCount = 0;
		if (vds.size() > 0) {
			// 按日统计
			if (flag.equals("3")) {
				totalCount = dasVdDAO.vdStatByHourTotal(sn, beginTime, endTime,
						name);
			} else if (flag.equals("2")) {// 按月统计
				String beginTimeS = getStringTime(beginTime);
				String endTimeS = getStringTime(endTime);
				totalCount = vdDAO.vdStatByMonthTotal(beginTimeS, endTimeS, sn);
			} else if (flag.equals("1")) {// 按年统计
				String beginTimeS = getStringTime(beginTime);
				String endTimeS = getStringTime(endTime);
				totalCount = vdDAO.vdStatByYearTotal(beginTimeS, endTimeS, sn);
			}
		}
		return totalCount;
	}

	// 模拟车检器采集数据 30分钟一条数据
	@Override
	public void testInsertBatchVd() {
		List<DasVd> list = new ArrayList<DasVd>();
		List<VehicleDetector> vehicleDetectors = vdDAO.findAll();
		for (VehicleDetector entity : vehicleDetectors) {
			for (int i = 0; i < 10; i++) {
				DasVd vd = new DasVd();
				vd.setCommStatus((short) 1);
				vd.setDwFluxb((int) (50 * Math.random()));
				vd.setDwFluxm((int) (50 * Math.random()));
				vd.setDwFluxs((int) (50 * Math.random()));
				vd.setDwFlux(vd.getDwFluxb() + vd.getDwFluxm()
						+ vd.getDwFluxs());
				vd.setUpFluxb((int) (50 * Math.random()));
				vd.setUpFluxm((int) (50 * Math.random()));
				vd.setUpFluxs((int) (50 * Math.random()));
				vd.setUpFlux(vd.getUpFluxb() + vd.getUpFluxm()
						+ vd.getUpFluxs());
				vd.setDwHeadway((int) (20 * Math.random() + 150 * Math.random()));
				vd.setDwOcc((int) (10 * Math.random()));
				// vd.setDwOccb((int) (70 * Math.random() + 30));
				// vd.setDwOccm((int) (70 * Math.random() + 30));
				// vd.setDwOccms((int) (70 * Math.random() + 30));
				// vd.setDwOccs((int) (70 * Math.random() + 30));
				vd.setUpOcc((int) (10 * Math.random()));
				// vd.setUpOccb((int) (70 * Math.random() + 30));
				// vd.setUpOccm((int) (70 * Math.random() + 30));
				// vd.setUpOccms((int) (70 * Math.random() + 30));
				// vd.setUpOccs((int) (70 * Math.random() + 30));
				vd.setDwSpeed((int) (80 * Math.random() + 70));
				// vd.setDwSpeedb((int) (80 * Math.random() + 70));
				// vd.setDwSpeedm((int) (80 * Math.random() + 70));
				// vd.setDwSpeedms((int) (80 * Math.random() + 70));
				// vd.setDwSpeeds((int) (80 * Math.random() + 70));
				vd.setUpSpeed((int) (80 * Math.random() + 70));
				// vd.setUpSpeedb((int) (80 * Math.random() + 70));
				// vd.setUpSpeedm((int) (80 * Math.random() + 70));
				// vd.setUpSpeedms((int) (80 * Math.random() + 70));
				// vd.setUpSpeeds((int) (80 * Math.random() + 70));
				vd.setUpHeadway((int) (20 * Math.random() + 150 * Math.random()));
				vd.setId((String) new UUIDHexGenerator().generate(null, null));
				vd.setLaneNumber((short) 4);
				vd.setOrgan("10010000000000000000");
				vd.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));

				vd.setReserve("");
				vd.setStandardNumber(entity.getStandardNumber());
				vd.setStatus((short) 0);
				vd.setType((short) 1);
				list.add(vd);

			}
		}
		dasVdDAO.batchInsert(list);
		System.out.println("end");
	}

	@Override
	public void testInsertBatchWst() {
		List<DasWst> list = new ArrayList<DasWst>();
		List<WeatherStat> weatherStats = wstDAO.findAll();
		for (WeatherStat entity : weatherStats) {
			for (int i = 0; i < 17520; i++) {
				DasWst vo = new DasWst();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setVisAvg((int) (1500 * Math.random() + 500));
				vo.setWsAvg((int) (5 * Math.random()) + "");
				vo.setWindDir((int) (360 * Math.random()));
				vo.setAirTempAvg((int) (50 * Math.random()) + "");
				vo.setRoadTempAvg((int) (10 * Math.random()) + "");
				vo.setHumiAvg((int) (50 * Math.random() + 40) + "");
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setRoadSurface((short) 0);
				vo.setRainVol(0 + "");
				vo.setStatus((short) 0);
				vo.setCommStatus((short) 0);
				list.add(vo);
			}
		}
		dasWstDAO.batchInsert(list);
	}

	// 模拟创建风速风向30分钟一条数据
	@Override
	public void testInsertBatchWs() {
		List<DasWs> list = new ArrayList<DasWs>();
		List<WindSpeed> windspeeds = wsDAO.findAll();
		for (WindSpeed entity : windspeeds) {
			for (int i = 0; i < 17520; i++) {
				DasWs vo = new DasWs();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setCommStatus((short) 0);
				vo.setDirection((short) (360 * Math.random()));
				vo.setOrgan("10010000000000000000");
				vo.setSpeed((int) (5 * Math.random()));
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setStatus((short) 0);
				list.add(vo);
			}
		}
		dasWsDAO.batchInsert(list);
	}

	// 模拟创建Covi30分钟一条数据
	@Override
	public void testInsertBatchCovi() {
		List<DasCovi> list = new ArrayList<DasCovi>();
		List<Covi> covis = coviDAO.findAll();
		for (Covi entity : covis) {
			for (int i = 0; i < 17520; i++) {
				DasCovi vo = new DasCovi();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setCommStatus((short) 0);
				vo.setCo((int) (5 * Math.random()) + "");
				vo.setOrgan("10010000000000000000");
				vo.setVi((int) (500 * Math.random()) + "");
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setStatus((short) 0);
				list.add(vo);
			}
		}
		dasCoviDAO.batchInsert(list);
	}

	// 模拟创建No30分钟一条数据
	@Override
	public void testInsertBatchNo() {
		List<DasNod> list = new ArrayList<DasNod>();
		List<NoDetector> nods = noDetectorDAO.findAll();
		for (NoDetector entity : nods) {
			for (int i = 0; i < 17520; i++) {
				DasNod vo = new DasNod();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setCommStatus((short) 0);
				vo.setNo((int) (5 * Math.random()) + "");
				vo.setOrgan("10010000000000000000");
				vo.setNo2((int) (5 * Math.random()) + "");
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setStatus((short) 0);
				list.add(vo);
			}
		}
		dasNodDAO.batchInsert(list);
	}

	// 模拟创建Loli30分钟一条数据
	@Override
	public void testInsertBatchLoli() {
		List<DasLoli> list = new ArrayList<DasLoli>();
		List<LoLi> lolis = loliDAO.findAll();
		for (LoLi entity : lolis) {
			for (int i = 0; i < 17520; i++) {
				DasLoli vo = new DasLoli();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setCommStatus((short) 0);
				vo.setLi((int) (500 * Math.random()) + "");
				vo.setOrgan("10010000000000000000");
				vo.setLo((int) (1000 * Math.random()) + "");
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setStatus((short) 0);
				list.add(vo);
			}
		}
		dasLoliDAO.batchInsert(list);
	}

	// 模拟创建roadDetector30分钟一条数据
	@Override
	public void testInsertBatchRoadDetector() {
		List<DasRoadDetector> list = new ArrayList<DasRoadDetector>();
		List<RoadDetector> roads = roadDetectorDAO.findAll();
		for (RoadDetector entity : roads) {
			for (int i = 0; i < 17520; i++) {
				DasRoadDetector vo = new DasRoadDetector();
				vo.setRecTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 30));
				vo.setCommStatus((short) 0);
				vo.setRainVol((int) (50 * Math.random()) + "");
				vo.setOrgan("10010000000000000000");
				vo.setRoadSurface((short) (20 * Math.random()));
				vo.setRoadTempAvg((short) (20 * Math.random()) + "");
				vo.setSnowVol((int) (50 * Math.random()) + "");
				vo.setStandardNumber(entity.getStandardNumber());
				vo.setStatus((short) 0);
				list.add(vo);
			}
		}
		dasRoadDetectorDAO.batchInsert(list);
	}

	// 模拟创建情报板发布记录30分钟一条数据
	@Override
	public void testInsertBatchCmsPushLog() {
		String organs[] = organDAO.findOrgansByOrganId("10010000000000000000");
		String[] cmses = controlDeviceDAO.cmsSNByOrgan(organs, null);
		List<CmsPublishLog> logs = new ArrayList<CmsPublishLog>();
		for (int n = 0; n < cmses.length; n++) {
			for (int i = 0; i < 365; i++) {
				CmsPublishLog vo = new CmsPublishLog();
				vo.setColor("63020");
				vo.setContent("禁止疲↓劳驾驶");
				vo.setDuration(60);
				vo.setFont("0");
				vo.setInfoType((short) 1);
				vo.setNavigation((short) 1);
				vo.setRowSpace(0);
				vo.setSendOrganId("10010000000000000000");
				vo.setSendOrganName("湖南高速公路湘西管理处");
				vo.setSendTime(new Timestamp(System.currentTimeMillis()
						- (long) i * (long) 1000 * (long) 60 * (long) 1440));
				vo.setSendUserId("10020000000000000000");
				vo.setSendUserName("admin");
				vo.setSize("60");
				vo.setSpace(0);
				vo.setStakeNumber("K100+20");
				vo.setStandardNumber(cmses[n]);
				vo.setX(1);
				vo.setY(54);
				logs.add(vo);
			}
		}
		cmsPublishLogDAO.batchInsert(logs);
	}

	@Override
	public void vdStatByMonth() {
		Timestamp beginTime = getTodayBeginTime();
		Timestamp endTime = getTodayEndTime();
		List<VdStatByDayVO> listVo = dasVdDAO.vdStatByMonth(beginTime, endTime);
		List<SyncVehicleDetector> list = new ArrayList<SyncVehicleDetector>();
		for (VdStatByDayVO vo : listVo) {
			SyncVehicleDetector svd = new SyncVehicleDetector();
			svd.setDateTime(vo.getRecTime());
			svd.setUpOccs(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpOccs()) ? vo.getUpOccs() : "0"));
			svd.setDwOccs(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwOccs()) ? vo.getDwOccs() : "0"));
			svd.setUpOccm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpOccm()) ? vo.getUpOccm() : "0"));
			svd.setDwOccm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwOccm()) ? vo.getDwOccm() : "0"));
			svd.setUpOccb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpOccb()) ? vo.getUpOccb() : "0"));
			svd.setDwOccb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwOccb()) ? vo.getDwOccb() : "0"));
			svd.setUpOcc(Float.parseFloat(StringUtils.isNotBlank(vo.getUpOcc()) ? vo
					.getUpOcc() : "0"));
			svd.setDwOcc(Float.parseFloat(StringUtils.isNotBlank(vo.getDwOcc()) ? vo
					.getDwOcc() : "0"));
			svd.setUpSpeeds(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpSpeeds()) ? vo.getUpSpeeds() : "0"));
			svd.setDwSpeeds(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwSpeeds()) ? vo.getDwSpeeds() : "0"));
			svd.setUpSpeedm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpSpeedm()) ? vo.getUpSpeedm() : "0"));
			svd.setDwSpeedm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwSpeedm()) ? vo.getDwSpeedm() : "0"));
			svd.setUpSpeedb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpSpeedb()) ? vo.getUpSpeedb() : "0"));
			svd.setDwSpeedb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwSpeedb()) ? vo.getDwSpeedb() : "0"));
			svd.setUpSpeed(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpSpeed()) ? vo.getUpSpeed() : "0"));
			svd.setDwSpeed(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwSpeed()) ? vo.getDwSpeed() : "0"));
			svd.setUpFlows(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpFlows()) ? vo.getUpFlows() : "0"));
			svd.setDwFlows(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwFlows()) ? vo.getDwFlows() : "0"));
			svd.setUpFlowm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpFlowm()) ? vo.getUpFlowm() : "0"));
			svd.setDwFlowm(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwFlowm()) ? vo.getDwFlowm() : "0"));
			svd.setUpFlowb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpFlowb()) ? vo.getUpFlowb() : "0"));
			svd.setDwFlowb(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwFlowb()) ? vo.getDwFlowb() : "0"));
			svd.setUpFlow(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpFlow()) ? vo.getUpFlow() : "0"));
			svd.setDwFlow(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwFlow()) ? vo.getDwFlow() : "0"));
			svd.setUpFlowTotal(Float.parseFloat(StringUtils.isNotBlank(vo
					.getUpFlowTotal()) ? vo.getUpFlowTotal() : "0"));
			svd.setDwFlowTotal(Float.parseFloat(StringUtils.isNotBlank(vo
					.getDwFlowTotal()) ? vo.getDwFlowTotal() : "0"));
			svd.setStandardNumber(vo.getStandardNumber());
			list.add(svd);
		}
		syncVehicleDetectorDAO.batchInsertSyncVd(list);
	}

	/**
	 * 根据当前时间获取上个月开始时间
	 * 
	 * @return Timestamp
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:08:02
	 */
	private Timestamp getLastMonthBegin() {
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
		return beginTime;
	}

	/**
	 * 
	 * 返回前一天的0点
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-27 下午4:48:20
	 */
	private Timestamp getTodayBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, -24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
		return beginTime;
	}

	/**
	 * 返回前一天的23点59
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:48:50
	 */
	private Timestamp getTodayEndTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
		return beginTime;
	}

	/**
	 * 根据当前时间获取上个月结束时间
	 * 
	 * @return Timestamp
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:08:02
	 */
	private Timestamp getLastMonthEnd() {
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
		return endTime;
	}

	/**
	 * 截取时间字符串
	 * 
	 * @param time
	 *            日期时间
	 * @return 时间字符串
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:42:53
	 */
	private String getStringTime(Timestamp time) {
		StringBuffer sb = new StringBuffer();
		String timeS = time.toString().split(" ")[0];
		String[] timeArray = timeS.split("-");
		sb.append(timeArray[0]);
		sb.append(timeArray[1]);
		sb.append(timeArray[2]);
		return sb.toString();
	}

	@Override
	public List<DasVd> listVdInfo(List<String> sns) {
		return dasVdDAO.listVdInfo(sns);
	}

	public List<VdStatByDayVO> statVdByTime(String[] ids, Timestamp beginTime,
			Timestamp endTime, String scope) {
		String[] sns = new String[ids.length];
		Map<String, String> nameMap = new HashMap<String, String>();
		Map<String, String> idMap = new HashMap<String, String>();
		for (int i = 0; i < ids.length; i++) {
			try {
				VehicleDetector vd = vdDAO.findById(ids[i]);
				sns[i] = vd.getStandardNumber();
				nameMap.put(vd.getStandardNumber(), vd.getName());
				idMap.put(vd.getStandardNumber(), vd.getId());
			} catch (BusinessException e) {
				SubPlatformResource vd = subPlatformResourceDAO
						.findById(ids[i]);
				sns[i] = vd.getStandardNumber();
				nameMap.put(vd.getStandardNumber(), vd.getName());
				idMap.put(vd.getStandardNumber(), vd.getId());
			}

		}
		List<VdStatByDayVO> list = null;
		// 精度到小时
		if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
			list = dasVdDAO.vdStatByHour(beginTime, endTime, sns, 0, 9999);
		}
		// 精度到天
		else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
			list = dasVdDAO.vdStatByDay(beginTime, endTime, sns, 0, 9999);
		}
		// 总量统计
		else {
			list = dasVdDAO.vdStat(beginTime, endTime, sns, 0, 9999);
		}
		// 补充名称
		for (VdStatByDayVO vo : list) {
			vo.setName(nameMap.get(vo.getStandardNumber()));
			vo.setId(idMap.get(vo.getStandardNumber()));
		}
		return list;
	}

	@Override
	public int countWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[]) {
		return dasWstDAO.countWSTInfo(beginTime, endTime, organSN, sns);
	}

	@Override
	public List<WstVO> listWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[], int start, int limit) {
		return dasWstDAO.listWSTInfo(beginTime, endTime, organSN, sns, start,
				limit);
	}

	private String cutString(Object s) {
		if (null == s) {
			return "";
		}
		String temp = s.toString();
		if (temp.length() > 5) {
			return temp.substring(0, 5);
		}
		return temp;
	}

	private void cache(List<DasControlDeviceReal> cdList, Set<String> cdSN,
			DasControlDeviceReal cdReal) {

		// 取缓存
		DasControlDeviceReal cacheCd = (DasControlDeviceReal) CacheUtil
				.getCache((cdReal.getStandardNumber() + "dasCache").hashCode(),
						"dasCache");
		// 如果没有缓存
		if (cacheCd == null) {
			cdList.add(cdReal); // 存入实时表数据集合，用于最后存入实时数据
			cdSN.add(cdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
		} else {// 如果有缓存
			boolean flag = true; // 变化标志
			if (cdReal.getCommStatus().equals( // 判断是否变化
					cacheCd.getCommStatus())
					&& cdReal.getStatus().equals(cacheCd.getStatus())
					&& (cdReal.getWorkState() != null ? cdReal.getWorkState()
							: "")
							.equals(cacheCd.getWorkState() != null ? cacheCd
									.getWorkState() : "")) {
				flag = false;
			}
			if (flag) { // 如果变化则存入集合
				cdList.add(cdReal); // 存入实时表数据集合，用于最后存入实时数据
				cdSN.add(cdReal.getStandardNumber()); // 获取上报sn存入set集合，用于最后根据sn查询实时表存入历史表和删除实时数据
			}
		}
	}

	@Override
	public void batchInsertVdSyncData(List<SyncVehicleDetector> list) {
		syncVehicleDetectorDAO.batchInsertSyncVd(list);
	}

	@Override
	public Collection<RoadFluxStatVO> listRoadFlux() {
		// 车检器按月统计结果
		List<VdFluxByMonthVO> list = syncVehicleDetectorDAO.listVdFluxByMonth();
		// 所有的车检器
		List<VehicleDetector> vds = vdDAO.findAll();
		// 所有的车检器机构-路段
		Set<Organ> organs = new HashSet<Organ>();
		for (VehicleDetector vd : vds) {
			organs.add(vd.getOrgan());
		}

		// key为机构SN+month
		Map<String, RoadFluxStatVO> map = new HashMap<String, RoadFluxStatVO>();

		for (Organ road : organs) {
			for (VehicleDetector vd : vds) {
				if (road.equals(vd.getOrgan())) {
					for (VdFluxByMonthVO vo : list) {
						// 该机构的设备
						if (vo.getStandardNumber().equals(
								vd.getStandardNumber())) {
							String key = road.getStandardNumber()
									+ vo.getMonth();
							RoadFluxStatVO stat = map.get(key);
							// 新增加一条统计数据
							if (stat == null) {
								stat = new RoadFluxStatVO();
								stat.setId(road.getId());
								stat.setMonth(vo.getMonth());
								stat.setName(road.getName());
								stat.setStandardNumber(road.getStandardNumber());
								stat.setUpFlux(vo.getUpFlux().longValue());
								stat.setDwFlux(vo.getDwFlux().longValue());
								map.put(key, stat);
							}
							// 合并相同机构相同月份的统计数据
							else {
								stat.setUpFlux(stat.getUpFlux()
										+ vo.getUpFlux().longValue());
								stat.setDwFlux(stat.getDwFlux()
										+ vo.getDwFlux().longValue());
							}
						}
					}
				}
			}
		}
		return map.values();
	}

	@Override
	public List<DasGpsReal> listGpsInfo() {
		return dasGpsRealDAO.findDasAll();
	}

	@Override
	public List<VdStatByDayVO> sortVD(List<VdStatByDayVO> list) {
		List<VdStatByDayVO> vds = new ArrayList<VdStatByDayVO>();
		if (list.size() < 1) {
			return list;
		}
		Set<String> vdSNSet = new HashSet<String>();
		// 设备SN归类
		for (VdStatByDayVO vo : list) {
			vdSNSet.add(vo.getStandardNumber());
		}

		Set<String> organs = new HashSet<String>();

		String[] sns = vdSNSet.toArray(new String[0]);
		Map<String, VehicleDetector> vdMap = vdDAO.mapVDBySNs(sns);
		// 再次迭代补充完整数据
		for (VdStatByDayVO vo : list) {
			VehicleDetector vd = vdMap.get(vo.getStandardNumber());
			if (null != vd) {
				vo.setOrganName(vd.getOrgan().getName());
				vo.setOrganId(vd.getOrgan().getId());
				vo.setOrganSN(vd.getOrgan().getStandardNumber());
				organs.add(vd.getOrgan().getStandardNumber());
			}
		}
		Map<String, List<VdStatByDayVO>> maps = new HashMap<String, List<VdStatByDayVO>>();

		Iterator<String> iter = organs.iterator();
		while (iter.hasNext()) {
			String organSN = iter.next();
			List<VdStatByDayVO> vos = new ArrayList<VdStatByDayVO>();
			for (VdStatByDayVO vo : list) {
				if (vo.getOrganSN().equals(organSN)) {
					vos.add(vo);
				}
			}
			maps.put(organSN, vos);
		}

		for (Map.Entry<String, List<VdStatByDayVO>> entry : maps.entrySet()) {
			vds.addAll(entry.getValue());
		}
		return vds;
	}

	@Override
	public void batchInsertWstData(List<DasWst> list) {
		dasWstDAO.batchInsert(list);
	}

	@Override
	public void batchInsertRsdData(List<DasRoadDetector> list) {
		dasRoadDetectorDAO.batchInsert(list);
	}

	@Override
	public void batchInsertCoviData(List<DasCovi> list) {
		dasCoviDAO.batchInsert(list);
	}

	@Override
	public void batchInsertLoliData(List<DasLoli> list) {
		dasLoliDAO.batchInsert(list);
	}

	@Override
	public void batchInsertNoData(List<DasNod> list) {
		dasNodDAO.batchInsert(list);
	}
}
