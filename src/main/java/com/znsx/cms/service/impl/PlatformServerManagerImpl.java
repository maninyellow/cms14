package com.znsx.cms.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.CrsDAO;
import com.znsx.cms.persistent.dao.DasDAO;
import com.znsx.cms.persistent.dao.DeviceUpdateListenerDAO;
import com.znsx.cms.persistent.dao.DvrDAO;
import com.znsx.cms.persistent.dao.DwsDAO;
import com.znsx.cms.persistent.dao.EnsDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.GisDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.MonitorDAO;
import com.znsx.cms.persistent.dao.MssDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PtsDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.RmsDAO;
import com.znsx.cms.persistent.dao.RoadDetectorDAO;
import com.znsx.cms.persistent.dao.RssDAO;
import com.znsx.cms.persistent.dao.ServerInfoDAO;
import com.znsx.cms.persistent.dao.SrsDAO;
import com.znsx.cms.persistent.dao.SysLogDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserSessionDAO;
import com.znsx.cms.persistent.dao.UserSessionHistoryDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.ViDetectorDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.Crs;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.persistent.model.DeviceUpdateListener;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.Gis;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.Monitor;
import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.PlatformServer;
import com.znsx.cms.persistent.model.Pts;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.Rms;
import com.znsx.cms.persistent.model.RoadDetector;
import com.znsx.cms.persistent.model.Rss;
import com.znsx.cms.persistent.model.ServerInfo;
import com.znsx.cms.persistent.model.Srs;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionCcs;
import com.znsx.cms.persistent.model.UserSessionCrs;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.UserSessionDws;
import com.znsx.cms.persistent.model.UserSessionEns;
import com.znsx.cms.persistent.model.UserSessionHistory;
import com.znsx.cms.persistent.model.UserSessionMss;
import com.znsx.cms.persistent.model.UserSessionPts;
import com.znsx.cms.persistent.model.UserSessionRms;
import com.znsx.cms.persistent.model.UserSessionRss;
import com.znsx.cms.persistent.model.UserSessionSrs;
import com.znsx.cms.persistent.model.UserSessionUser;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.ViDetector;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.StandardObjectCode;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.PlatformServerManager;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.Channel;
import com.znsx.cms.service.model.CrsUpdateConfigVO;
import com.znsx.cms.service.model.ListCcsVO;
import com.znsx.cms.service.model.ListCrsVO;
import com.znsx.cms.service.model.ListDasVO;
import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.service.model.ListEnsVO;
import com.znsx.cms.service.model.ListGisVO;
import com.znsx.cms.service.model.ListMssVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.service.model.ListPtsVO;
import com.znsx.cms.service.model.ListRmsVO;
import com.znsx.cms.service.model.ListRssVO;
import com.znsx.cms.service.model.ListSrsVO;
import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.ListOnlineUsersDTO;
import com.znsx.cms.web.dto.omc.ListPlatformServerDTO;
import com.znsx.util.base64.Base64Utils;
import com.znsx.util.hardware.HardwareUtil;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 平台服务器业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:11:15
 */
public class PlatformServerManagerImpl extends BaseManagerImpl implements
		PlatformServerManager {
	@Autowired
	private MssDAO mssDAO;
	@Autowired
	private CcsDAO ccsDAO;
	@Autowired
	private CrsDAO crsDAO;
	@Autowired
	private PtsDAO ptsDAO;
	@Autowired
	private DwsDAO dwsDAO;
	@Autowired
	private DvrDAO dvrDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private SysLogDAO sysLogDAO;
	@Autowired
	private DeviceUpdateListenerDAO deviceUpdateListenerDAO;
	@Autowired
	private UserSessionDAO userSessionDAO;
	@Autowired
	private MonitorDAO monitorDAO;
	@Autowired
	private DasDAO dasDAO;
	@Autowired
	private EnsDAO ensDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private WindSpeedDAO wsDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private NoDetectorDAO noDetectorDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private FireDetectorDAO fdDAO;
	@Autowired
	private PushButtonDAO pbDAO;
	@Autowired
	private ServerInfoDAO serverInfoDAO;
	@Autowired
	private GisDAO gisDAO;
	@Autowired
	private RmsDAO rmsDAO;
	@Autowired
	private RssDAO rssDAO;
	@Autowired
	private RoadDetectorDAO roadDetectorDAO;
	@Autowired
	private ViDetectorDAO viDetectorDAO;
	@Autowired
	private PushButtonDAO pushButtonDAO;
	@Autowired
	private UserSessionHistoryDAO userSessionHistoryDAO;
	@Autowired
	private SrsDAO srsDAO;

	@Override
	public String createMss(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Mss> list = mssDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = mssDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Mss> list = mssDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		Mss mss = new Mss();
		// String id = mssDAO.getNextId("Mss", 1);
		// mss.setId(id);
		mss.setConfigValue(configValue);
		mss.setCreateTime(System.currentTimeMillis());
		mss.setLanIp(lanIp);
		mss.setLocation(location);
		mss.setName(name);
		mss.setPort(port);
		mss.setStandardNumber(standardNumber);
		mss.setWanIp(wanIp);
		mssDAO.save(mss);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_MSS);
		return mss.getId();
	}

	@Override
	public void updateMss(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String ccsId, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Mss> list = null;
		Mss mss = mssDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = mssDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(mss.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_MSS);
			mss.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = mssDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			mss.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = mssDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			mss.setLanIp(lanIp);
		}

		if (null != location) {
			mss.setLocation(location);
		}
		if (null != wanIp) {
			mss.setWanIp(wanIp);
		}
		if (null != port) {
			mss.setPort(port);
		}
		if (null != configValue) {
			mss.setConfigValue(configValue);
		}
		if (null != ccsId) {
			mss.setCcs(ccsDAO.findById(ccsId));
		}
	}

	@Override
	public void deleteMss(@LogParam("id") String id) throws BusinessException {
		// 检查是否有子Camera
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("mss.id", id);
		List<Camera> cameras = cameraDAO.findByPropertys(params);
		if (cameras.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child Camera found !");
		}

		// 同步SN
		Mss mss = mssDAO.findById(id);
		syncSN(mss.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_MSS);
		mssDAO.delete(mss);
	}

	@Override
	public List<ListMssVO> listMss() throws BusinessException {
		List<Mss> msss = mssDAO.findAll();
		List<ListMssVO> list = new ArrayList<ListMssVO>();

		for (Mss mss : msss) {
			ListMssVO vo = new ListMssVO();
			Ccs ccs = mss.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(mss.getConfigValue());
			vo.setCreateTime(mss.getCreateTime().toString());
			vo.setId(mss.getId());
			vo.setLanIp(mss.getLanIp());
			vo.setLocation(mss.getLocation());
			vo.setName(mss.getName());
			vo.setPort(mss.getPort() != null ? mss.getPort() : "");
			vo.setStandardNumber(mss.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_MSS + "");
			vo.setWanIp(mss.getWanIp());
			vo.setTelnetPort(mss.getTelnetPort() != null ? mss.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public ListPlatformServerDTO listPlatformServer(Integer startIndex,
			Integer limit, String name, String type) throws BusinessException {
		ListPlatformServerDTO dto = new ListPlatformServerDTO();

		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		List<Ccs> ccsList = ccsDAO.findCcsByName(name);
		List<Crs> crsList = crsDAO.findCrsByName(name);
		List<Mss> mssList = mssDAO.findMssByName(name);
		List<Pts> ptsList = ptsDAO.findPtsByName(name);
		List<Dws> dwsList = dwsDAO.findDwsByName(name);
		List<Das> dasList = dasDAO.findDasByName(name);
		List<Ens> ensList = ensDAO.findEnsByName(name);
		List<Gis> gisList = gisDAO.findGisByName(name);
		List<Rms> rmsList = rmsDAO.findRmsByName(name);
		List<Rss> rssList = rssDAO.findRssByName(name);
		List<Srs> srsList = srsDAO.findSrsByName(name);

		if (StringUtils.isBlank(type)) {
			list.addAll(setCcsServerVO(ccsList));
			list.addAll(setCrsServerVO(crsList));
			list.addAll(setMssServerVO(mssList));
			list.addAll(setPtsServerVO(ptsList));
			list.addAll(setDwsServerVO(dwsList));
			list.addAll(setDasServerVO(dasList));
			list.addAll(setEnsServerVO(ensList));
			list.addAll(setGisServerVO(gisList));
			list.addAll(setRmsServerVO(rmsList));
			list.addAll(setRssServerVO(rssList));
			list.addAll(setSrsServerVO(srsList));
		}
		// 获取ccs列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_CCS + "")) {
			list.addAll(setCcsServerVO(ccsList));
		}
		// 获取crs列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_CRS + "")) {
			list.addAll(setCrsServerVO(crsList));
		}
		// 获取mss列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_MSS + "")) {
			list.addAll(setMssServerVO(mssList));
		}
		// 获取pts列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_PTS + "")) {
			list.addAll(setPtsServerVO(ptsList));
		}
		// 获取dws列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_DWS + "")) {
			list.addAll(setDwsServerVO(dwsList));
		}
		// 获取das列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_DAS + "")) {
			list.addAll(setDasServerVO(dasList));
		}
		// 获取ens列表,加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_ENS + "")) {
			list.addAll(setEnsServerVO(ensList));
		}
		// 获取gis列表，加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_GIS + "")) {
			list.addAll(setGisServerVO(gisList));
		}
		// 获取rms列表，加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_RMS + "")) {
			list.addAll(setRmsServerVO(rmsList));
		}
		// 获取rss列表，加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_RSS + "")) {
			list.addAll(setRssServerVO(rssList));
		}
		// 获取srs列表，加入到返回中
		else if (type.equals(TypeDefinition.SERVER_TYPE_SRS + "")) {
			list.addAll(setSrsServerVO(srsList));
		}

		Integer totalCount = list.size();
		dto.setTotalCount(totalCount + "");

		if (totalCount != 0) {
			if (startIndex >= totalCount) {
				startIndex -= ((startIndex - totalCount) / limit + 1) * limit;
				list = list.subList(startIndex, totalCount);
			} else {
				if (startIndex + limit < totalCount) {
					list = list.subList(startIndex, startIndex + limit);
				} else {
					list = list.subList(startIndex, totalCount);
				}
			}
		}
		dto.setPlatformServerList(list);

		return dto;
	}

	private Collection<? extends PlatformServerVO> setGisServerVO(
			List<Gis> gisList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Gis item : gisList) {
			PlatformServerVO vo = new PlatformServerVO();
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_GIS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createCcs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port, Short forward)
			throws BusinessException {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Ccs> list = ccsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = ccsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Ccs> list = ccsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = ccsDAO.getNextId("Ccs", 1);
		Ccs ccs = new Ccs();
		// ccs.setId(id);
		ccs.setConfigValue(configValue);
		ccs.setCreateTime(System.currentTimeMillis());
		ccs.setLanIp(lanIp);
		ccs.setLocation(location);
		ccs.setName(name);
		ccs.setPort(port);
		ccs.setStandardNumber(standardNumber);
		ccs.setWanIp(wanIp);
		ccs.setForward(forward);
		ccsDAO.save(ccs);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_CCS);
		return ccs.getId();
	}

	@Override
	public String createCrs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue)
			throws BusinessException {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Crs> list = crsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = crsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Crs> list = crsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = crsDAO.getNextId("Crs", 1);
		Crs crs = new Crs();
		// crs.setId(id);
		crs.setConfigValue(configValue);
		crs.setCreateTime(System.currentTimeMillis());
		crs.setLanIp(lanIp);
		crs.setLocation(location);
		crs.setName(name);
		crs.setPort(port);
		crs.setStandardNumber(standardNumber);
		crs.setWanIp(wanIp);
		crsDAO.save(crs);
		// 同步
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_CRS);
		return crs.getId();
	}

	@Override
	public String createDws(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Dws> list = dwsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = dwsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Dws> list = dwsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = dwsDAO.getNextId("Dws", 1);
		Dws dws = new Dws();
		// dws.setId(id);
		dws.setConfigValue(configValue);
		dws.setCreateTime(System.currentTimeMillis());
		dws.setLanIp(lanIp);
		dws.setLocation(location);
		dws.setName(name);
		dws.setPort(port);
		dws.setStandardNumber(standardNumber);
		dws.setWanIp(wanIp);
		dwsDAO.save(dws);
		// 同步
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_DWS);
		return dws.getId();
	}

	@Override
	public String createPts(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Pts> list = ptsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = ptsDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Pts> list = ptsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = ptsDAO.getNextId("Pts", 1);
		Pts pts = new Pts();
		// pts.setId(id);
		pts.setConfigValue(configValue);
		pts.setCreateTime(System.currentTimeMillis());
		pts.setLanIp(lanIp);
		pts.setLocation(location);
		pts.setName(name);
		pts.setPort(port);
		pts.setStandardNumber(standardNumber);
		pts.setWanIp(wanIp);
		ptsDAO.save(pts);
		// 同步
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_PTS);
		return pts.getId();
	}

	@Override
	public PlatformServerVO getPlatformServer(String id, Integer type)
			throws BusinessException {
		PlatformServer server = null;
		PlatformServerVO vo = new PlatformServerVO();
		Ccs ccs = null;

		// 根据不同的type分别查询各种平台服务器
		switch (type.intValue()) {
		case TypeDefinition.SERVER_TYPE_CCS:
			server = ccsDAO.findById(id);
			vo.setForward(server.getForward().toString());
			vo.setType(TypeDefinition.SERVER_TYPE_CCS + "");
			break;
		case TypeDefinition.SERVER_TYPE_CRS:
			server = crsDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_CRS + "");
			ccs = ((Crs) server).getCcs();
			break;
		case TypeDefinition.SERVER_TYPE_MSS:
			server = mssDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_MSS + "");
			ccs = ((Mss) server).getCcs();
			break;
		case TypeDefinition.SERVER_TYPE_DWS:
			server = dwsDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_DWS + "");
			ccs = ((Dws) server).getCcs();
			break;
		case TypeDefinition.SERVER_TYPE_PTS:
			server = ptsDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_PTS + "");
			ccs = ((Pts) server).getCcs();
			break;
		case TypeDefinition.SERVER_TYPE_DAS:
			server = dasDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_DAS + "");
			break;
		case TypeDefinition.SERVER_TYPE_ENS:
			server = ensDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_ENS + "");
			ccs = ((Ens) server).getCcs();
			break;
		case TypeDefinition.SERVER_TYPE_GIS:
			server = gisDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_GIS + "");
			break;
		case TypeDefinition.SERVER_TYPE_RMS:
			server = rmsDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_RMS + "");
			break;
		case TypeDefinition.SERVER_TYPE_RSS:
			server = rssDAO.findById(id);
			vo.setType(TypeDefinition.SERVER_TYPE_RSS + "");
			break;
		default:
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type.toString() + "] invalid !");
		}

		vo.setConfigValue(server.getConfigValue());
		vo.setCreateTime(server.getCreateTime().toString());
		vo.setId(server.getId());
		vo.setLanIp(server.getLanIp());
		vo.setLocation(server.getLocation());
		vo.setName(server.getName());
		vo.setPort(server.getPort() != null ? server.getPort() : "");
		vo.setStandardNumber(server.getStandardNumber());
		vo.setWanIp(server.getWanIp());
		vo.setTelnetPort(server.getTelnetPort());
		if (null != ccs) {
			vo.setCcsId(ccs.getId());
			vo.setCcsName(ccs.getName());
		}
		return vo;
	}

	@Override
	public void updateCcs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port, Short forward)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Ccs> list = null;

		Ccs ccs = ccsDAO.findById(id);
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = ccsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(ccs.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_CCS);
			ccs.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = ccsDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			ccs.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = ccsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			ccs.setLanIp(lanIp);
		}
		if (null != location) {
			ccs.setLocation(location);
		}
		if (null != wanIp) {
			ccs.setWanIp(wanIp);
		}
		if (null != configValue) {
			ccs.setConfigValue(configValue);
		}
		if (null != port) {
			ccs.setPort(port);
		}
		if (null != forward) {
			ccs.setForward(forward);
		}
	}

	@Override
	public void deleteCcs(@LogParam("id") String id) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("ccs.id", id);

		// 检查是否有子MSS
		List<Mss> mssList = mssDAO.findByPropertys(params);
		if (mssList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child MSS found !");
		}
		// 检查是否有子CRS
		List<Crs> crsList = crsDAO.findByPropertys(params);
		if (crsList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child CRS found !");
		}
		// 检查是否有子DWS
		List<Dws> dwsList = dwsDAO.findByPropertys(params);
		if (dwsList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child DWS found !");
		}
		// 检查是否有子PTS
		List<Pts> ptsList = ptsDAO.findByPropertys(params);
		if (ptsList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child PTS found !");
		}
		// 检查是否有子DVR
		List<Dvr> dvrList = dvrDAO.findByPropertys(params);
		if (dvrList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child DVR found !");
		}
		// 检查是否有用户存在
		List<User> userList = userDAO.findByPropertys(params);
		if (userList.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child USER found !");
		}

		// 同步SN
		Ccs ccs = ccsDAO.findById(id);
		syncSN(ccs.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_CCS);
		ccsDAO.deleteById(id);
	}

	@Override
	public List<ListCcsVO> listCcs() throws BusinessException {
		List<Ccs> ccss = ccsDAO.findAll();
		List<ListCcsVO> list = new ArrayList<ListCcsVO>();
		for (Ccs ccs : ccss) {
			ListCcsVO vo = new ListCcsVO();
			vo.setConfigValue(ccs.getConfigValue());
			vo.setCreateTime(ccs.getCreateTime().toString());
			vo.setId(ccs.getId());
			vo.setLanIp(ccs.getLanIp());
			vo.setLocation(ccs.getLocation());
			vo.setName(ccs.getName());
			vo.setPort(ccs.getPort() != null ? ccs.getPort() : "");
			vo.setStandardNumber(ccs.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_CCS + "");
			vo.setWanIp(ccs.getWanIp());
			vo.setTelnetPort(ccs.getTelnetPort() != null ? ccs.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public void updateCrs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Crs> list = null;
		Crs crs = crsDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = crsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步
			syncSN(crs.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_CRS);
			crs.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = crsDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			crs.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = crsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			crs.setLanIp(lanIp);
		}

		if (null != ccsId) {
			crs.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != location) {
			crs.setLocation(location);
		}
		if (null != wanIp) {
			crs.setWanIp(wanIp);
		}
		if (null != port) {
			crs.setPort(port);
		}
		if (null != configValue) {
			crs.setConfigValue(configValue);
		}
	}

	@Override
	public void deleteCrs(@LogParam("id") String id) throws BusinessException {
		// 检查是否有子Camera
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("crs.id", id);
		List<Camera> cameras = cameraDAO.findByPropertys(params);
		if (cameras.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child Camera found !");
		}

		// 同步
		Crs crs = crsDAO.findById(id);
		syncSN(crs.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_CRS);
		crsDAO.deleteById(id);
	}

	@Override
	public List<ListCrsVO> listCrs() throws BusinessException {
		List<Crs> crss = crsDAO.findAll();
		List<ListCrsVO> list = new ArrayList<ListCrsVO>();
		for (Crs crs : crss) {
			ListCrsVO vo = new ListCrsVO();
			Ccs ccs = crs.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(crs.getConfigValue());
			vo.setCreateTime(crs.getCreateTime().toString());
			vo.setId(crs.getId());
			vo.setLanIp(crs.getLanIp());
			vo.setLocation(crs.getLocation());
			vo.setName(crs.getName());
			vo.setPort(crs.getPort() != null ? crs.getPort().toString() : "");
			vo.setStandardNumber(crs.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_CRS + "");
			vo.setWanIp(crs.getWanIp());
			vo.setTelnetPort(crs.getTelnetPort() != null ? crs.getTelnetPort()
					: "");
			list.add(vo);
		}

		return list;
	}

	@Override
	public void updateDws(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Dws> list = null;
		Dws dws = dwsDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = dwsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步
			syncSN(dws.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_DWS);
			dws.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = dwsDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			dws.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = dwsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			dws.setLanIp(lanIp);
		}

		if (null != ccsId) {
			dws.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != location) {
			dws.setLocation(location);
		}
		if (null != wanIp) {
			dws.setWanIp(wanIp);
		}
		if (null != port) {
			dws.setPort(port);
		}
		if (null != configValue) {
			dws.setConfigValue(configValue);
		}
	}

	@Override
	public void deleteDws(@LogParam("id") String id) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("dws.id", id);
		List<Monitor> monitors = monitorDAO.findByPropertys(params);
		if (monitors.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child Monitor found !");
		}
		// 同步
		Dws dws = dwsDAO.findById(id);
		syncSN(dws.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_DWS);
		dwsDAO.delete(dws);
	}

	@Override
	public List<ListDwsVO> listDws() throws BusinessException {
		List<Dws> dwss = dwsDAO.findAll();
		List<ListDwsVO> list = new ArrayList<ListDwsVO>();
		for (Dws dws : dwss) {
			ListDwsVO vo = new ListDwsVO();
			Ccs ccs = dws.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(dws.getConfigValue());
			vo.setCreateTime(dws.getCreateTime().toString());
			vo.setId(dws.getId());
			vo.setLanIp(dws.getLanIp());
			vo.setLocation(dws.getLocation());
			vo.setName(dws.getName());
			vo.setPort(dws.getPort() != null ? dws.getPort().toString() : "");
			vo.setStandardNumber(dws.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_DWS + "");
			vo.setWanIp(dws.getWanIp());
			vo.setTelnetPort(dws.getTelnetPort() != null ? dws.getTelnetPort()
					: "");
			list.add(vo);
		}

		return list;
	}

	@Override
	public void updatePts(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Pts> list = null;
		Pts pts = ptsDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = ptsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步
			syncSN(pts.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_PTS);
			pts.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = ptsDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			pts.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = ptsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			pts.setLanIp(lanIp);
		}

		if (null != ccsId) {
			pts.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != location) {
			pts.setLocation(location);
		}
		if (null != wanIp) {
			pts.setWanIp(wanIp);
		}
		if (null != port) {
			pts.setPort(port);
		}
		if (null != configValue) {
			pts.setConfigValue(configValue);
		}
	}

	@Override
	public void deletePts(@LogParam("id") String id) throws BusinessException {
		// 检查是否有子DVR
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("pts.id", id);
		List<Dvr> dvrs = dvrDAO.findByPropertys(params);
		if (dvrs.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child DVR found !");
		}
		// 同步SN
		Pts pts = ptsDAO.findById(id);
		syncSN(pts.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_PTS);

		ptsDAO.delete(pts);
	}

	@Override
	public List<ListPtsVO> listPts() throws BusinessException {
		List<Pts> ptss = ptsDAO.findAll();
		List<ListPtsVO> list = new ArrayList<ListPtsVO>();
		for (Pts pts : ptss) {
			ListPtsVO vo = new ListPtsVO();
			Ccs ccs = pts.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(pts.getConfigValue());
			vo.setCreateTime(pts.getCreateTime().toString());
			vo.setId(pts.getId());
			vo.setLanIp(pts.getLanIp());
			vo.setLocation(pts.getLocation());
			vo.setName(pts.getName());
			vo.setPort(pts.getPort() != null ? pts.getPort().toString() : "");
			vo.setStandardNumber(pts.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_PTS + "");
			vo.setWanIp(pts.getWanIp());
			vo.setTelnetPort(pts.getTelnetPort() != null ? pts.getTelnetPort()
					: "");
			list.add(vo);
		}

		return list;
	}

	@Override
	public Element getResourceRouteInfo(String standardNumber) {
		// 根据截取出来的值拿到className
		String objectCode = standardNumber.substring(10, 12);
		String className = StandardObjectCode.getClassName(objectCode);
		Element element = null;
		// 如果没有className就错误
		if (className.equals(StandardObjectCode.ERROR)) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"class standardNumber[" + standardNumber + "] not found");
		}
		// 如果className是Dvr或者Camera、Ccs、Pts、Monitor时则作特殊处理，其他统一处理
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("standardNumber", standardNumber);
		if (className.equals("Dvr")) {
			List<Dvr> dvrs = dvrDAO.findByPropertys(map);
			if (dvrs.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"dvr standardNumber[" + standardNumber + "] not found");
			}
			element = getElementByDvr(dvrs.get(0), standardNumber);
		} else if (className.equals("Camera")) {
			List<Camera> cameras = cameraDAO.findByPropertys(map);
			if (cameras.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"camera standardNumber [" + standardNumber
								+ "] not found");
			}
			Dvr dvr = cameras.get(0).getParent();
			element = getElementByDvr(dvr, standardNumber);
		} else if (className.equals("Ccs")) {
			List<Ccs> ccs = ccsDAO.findByPropertys(map);
			if (ccs.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"ccs standardNumber[" + standardNumber + "] not found");
			}
			element = new Element("Route");
			element.setAttribute("LanIp", ccs.get(0).getLanIp() != null ? ccs
					.get(0).getLanIp() : "");
			element.setAttribute("Port", ccs.get(0).getPort() != null ? ccs
					.get(0).getPort() + "" : "");
		} else if (className.equals("Pts")) {
			List<Pts> pts = ptsDAO.findByPropertys(map);
			if (pts.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"pts standardNumber[" + standardNumber + "] not found");
			}
			element = new Element("Route");
			element.setAttribute("LanIp", pts.get(0).getLanIp() != null ? pts
					.get(0).getLanIp() : "");
			element.setAttribute("Port", pts.get(0).getPort() != null ? pts
					.get(0).getPort() + "" : "");
		} else if (className.equals("Monitor")) {
			List<Monitor> monitors = monitorDAO.findByPropertys(map);
			if (monitors.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Monitor standardNumber[" + standardNumber
								+ "] not found");
			}
			element = getElementByMonitor(monitors.get(0), standardNumber);
		} else {
			String ccsId = userDAO.getCcsId(className, standardNumber);
			if (StringUtils.isBlank(ccsId)) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						className + " standardNumber [" + standardNumber
								+ "] not found");
			}
			Ccs ccs = ccsDAO.findById(ccsId);
			element = new Element("Route");
			element.setAttribute("LanIp",
					ccs.getLanIp() != null ? ccs.getLanIp() : "");
			element.setAttribute("Port", ccs.getPort() != null ? ccs.getPort()
					+ "" : "");
		}
		return element;
	}

	public Element getElementByDvr(Dvr dvr, String standardNumber) {
		Element element = null;
		// 根据standardNumber取到的Dvr判断是否注册
		if (dvr.getCcs() == null) {
			if (dvr.getPts() == null) {
				throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
						"Resource not Register [" + standardNumber + "]");
			} else {
				element = new Element("Route");
				element.setAttribute("LanIp",
						dvr.getPts().getLanIp() != null ? dvr.getPts()
								.getLanIp() : "");
				element.setAttribute("Port",
						dvr.getPts().getPort() != null ? dvr.getPts().getPort()
								+ "" : "");
			}
		} else {
			element = new Element("Route");
			element.setAttribute("LanIp", dvr.getCcs().getLanIp() != null ? dvr
					.getCcs().getLanIp() : "");
			element.setAttribute("Port", dvr.getCcs().getPort() != null ? dvr
					.getCcs().getPort() + "" : "");
		}

		return element;
	}

	public Element getElementByMonitor(Monitor monitor, String standardNumber) {
		Element element = null;
		if (monitor.getDws() == null) {
			throw new BusinessException(ErrorCode.AUTHORISE_FAILED,
					"Resource not Register [" + standardNumber + "]");
		} else {
			element = new Element("Route");
			element.setAttribute("LanIp",
					monitor.getDws().getLanIp() != null ? monitor.getDws()
							.getLanIp() : "");
			element.setAttribute("Port",
					monitor.getDws().getPort() != null ? monitor.getDws()
							.getPort() + "" : "");
		}
		return element;
	}

	@Override
	public Ccs getCcsByStandardNumber(String standardNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Ccs> list = ccsDAO.findByPropertys(params);
		if (list.size() < 1) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "PTS["
					+ standardNumber + "] not found !");
		}
		return list.get(0);
	}

	@Override
	public Integer platformServerTotalCount() {
		return ccsDAO.platformServerTotalCount();
	}

	@Override
	public DeviceUpdateListener getDeviceUpdateTime() {
		List<DeviceUpdateListener> list = deviceUpdateListenerDAO.findAll();
		if (list.size() != 1) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"table sv_device_update_listener must only has 1 record !");
		}
		return list.get(0);
	}

	@Override
	public List<CcsUserSessionVO> listCcsUserSession(String standardNumber) {
		Ccs ccs = ccsDAO.getByStandardNumber(standardNumber);
		return userSessionDAO.listCcsUserSession(ccs.getId());
	}

	@Override
	public UserSession serverRegister(String standardNumber, String type,
			String ccsId, String lanIp) throws BusinessException {
		if (TypeDefinition.CLIENT_TYPE_CCS.equalsIgnoreCase(type)) {
			Ccs ccs = ccsDAO.getByStandardNumber(standardNumber);
			List<UserSessionCcs> sessions = userSessionDAO
					.listUserSessionCcs(ccs.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "ccs["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("CCS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			ccs.setLanIp(lanIp);
			return createSession(ccs.getId(), ccs.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_CCS);
		} else if (TypeDefinition.CLIENT_TYPE_CRS.equalsIgnoreCase(type)) {
			Crs crs = crsDAO.getByStandardNumber(standardNumber);
			List<UserSessionCrs> sessions = userSessionDAO
					.listUserSessionCrs(crs.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "crs["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("CRS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			crs.setCcs(ccsDAO.findById(ccsId));
			crs.setLanIp(lanIp);
			return createSession(crs.getId(), crs.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_CRS);
		} else if (TypeDefinition.CLIENT_TYPE_MSS.equalsIgnoreCase(type)) {
			Mss mss = mssDAO.getByStandardNumber(standardNumber);
			List<UserSessionMss> sessions = userSessionDAO
					.listUserSessionMss(mss.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "mss["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("MSS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			mss.setCcs(ccsDAO.findById(ccsId));
			mss.setLanIp(lanIp);
			return createSession(mss.getId(), mss.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_MSS);
		} else if (TypeDefinition.CLIENT_TYPE_DWS.equalsIgnoreCase(type)) {
			Dws dws = dwsDAO.getByStandardNumber(standardNumber);
			List<UserSessionDws> sessions = userSessionDAO
					.listUserSessionDws(dws.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "dws["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("DWS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			dws.setCcs(ccsDAO.findById(ccsId));
			dws.setLanIp(lanIp);
			return createSession(dws.getId(), dws.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_DWS);
		} else if (TypeDefinition.CLIENT_TYPE_PTS.equalsIgnoreCase(type)) {
			Pts pts = ptsDAO.getByStandardNumber(standardNumber);
			List<UserSessionPts> sessions = userSessionDAO
					.listUserSessionPts(pts.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "pts["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("PTS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			pts.setLanIp(lanIp);
			return createSession(pts.getId(), pts.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_PTS);
		} else if (TypeDefinition.CLIENT_TYPE_DAS.equalsIgnoreCase(type)) {
			Das das = dasDAO.getByStandardNumber(standardNumber);
			List<UserSessionDas> sessions = userSessionDAO
					.listUserSessionDas(das.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "das["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("DAS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			das.setLanIp(lanIp);
			return createSession(das.getId(), das.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_DAS);
		} else if (TypeDefinition.CLIENT_TYPE_ENS.equalsIgnoreCase(type)) {
			Ens ens = ensDAO.getByStandardNumber(standardNumber);
			List<UserSessionEns> sessions = userSessionDAO
					.listUserSessionEns(ens.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "ens["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("ENS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			ens.setCcs(ccsDAO.findById(ccsId));
			ens.setLanIp(lanIp);
			return createSession(ens.getId(), ens.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_ENS);
		} else if (TypeDefinition.CLIENT_TYPE_RMS.equalsIgnoreCase(type)) {
			Rms rms = rmsDAO.getByStandardNumber(standardNumber);
			List<UserSessionRms> sessions = userSessionDAO
					.listUserSessionRms(rms.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "rms["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("RMS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			rms.setCcs(ccsDAO.findById(ccsId));
			rms.setLanIp(lanIp);
			return createSession(rms.getId(), rms.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_RMS);
		} else if (TypeDefinition.CLIENT_TYPE_RSS.equalsIgnoreCase(type)) {
			Rss rss = rssDAO.findBySN(standardNumber);
			List<UserSessionRss> sessions = userSessionDAO
					.listUserSessionRss(rss.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "rss["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("RSS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			rss.setCcs(ccsDAO.findById(ccsId));
			rss.setLanIp(lanIp);
			return createSession(rss.getId(), rss.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_RSS);
		} else if (TypeDefinition.CLIENT_TYPE_SRS.equalsIgnoreCase(type)) {
			Srs srs = srsDAO.getByStandardNumber(standardNumber);
			List<UserSessionSrs> sessions = userSessionDAO
					.listUserSessionSrs(srs.getId());
			if (sessions.size() > 0) {
				if (!sessions.get(0).getIp().equals(lanIp)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "srs["
									+ standardNumber + "] lanIp["
									+ sessions.get(0).getIp() + "] is in use !");
				}
				// 清除原先的session
				else {
					System.out.println("SRS[" + standardNumber
							+ "] register again, remove old Session["
							+ sessions.get(0).getId() + "] !");
					removeSession(sessions.get(0));
				}
			}
			// srs.setCcs(ccsDAO.findById(ccsId));
			srs.setLanIp(lanIp);
			return createSession(srs.getId(), srs.getName(), standardNumber,
					lanIp, TypeDefinition.CLIENT_TYPE_SRS);
		}
		throw new BusinessException(ErrorCode.PARAMETER_INVALID,
				"Parameter type[" + type + "] invalid !");
	}

	private UserSession createSession(String id, String name,
			String standardNumber, String lanIp, String type) {
		Organ organ = organDAO.getRootOrgan();
		UserSession us = new UserSession();
		if (type.equals(TypeDefinition.CLIENT_TYPE_CCS)) {
			UserSessionCcs userSession = new UserSessionCcs();
			userSession.setCcs(ccsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_CRS)) {
			UserSessionCrs userSession = new UserSessionCrs();
			userSession.setCrs(crsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_MSS)) {
			UserSessionMss userSession = new UserSessionMss();
			userSession.setMss(mssDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_DWS)) {
			UserSessionDws userSession = new UserSessionDws();
			userSession.setDws(dwsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_PTS)) {
			UserSessionPts userSession = new UserSessionPts();
			userSession.setPts(ptsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_DAS)) {
			UserSessionDas userSession = new UserSessionDas();
			userSession.setDas(dasDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_ENS)) {
			UserSessionEns userSession = new UserSessionEns();
			userSession.setEns(ensDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_RMS)) {
			UserSessionRms userSession = new UserSessionRms();
			userSession.setRms(rmsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_RSS)) {
			UserSessionRss userSession = new UserSessionRss();
			userSession.setRss(rssDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else if (type.equals(TypeDefinition.CLIENT_TYPE_SRS)) {
			UserSessionSrs userSession = new UserSessionSrs();
			userSession.setSrs(srsDAO.findById(id));
			userSession.setIp(lanIp);
			userSession.setTicket("");
			userSession.setLogonName(name);
			userSession.setOrganId(organ.getId());
			userSession.setOrganName(organ.getName());
			userSession.setPath(organ.getPath());
			userSession.setStandardNumber(standardNumber);
			userSession.setClientType(type);
			userSession.setLogonTime(System.currentTimeMillis());
			userSession.setUpdateTime(System.currentTimeMillis());
			userSessionDAO.save(userSession);

			us = userSessionDAO.findById(userSession.getId());
			us.setTicket(userSession.getId());
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

		return us;
	}

	private void removeSession(UserSession us) {
		// 保存到历史会话中
		UserSessionHistory record = new UserSessionHistory();
		record.setClientType(us.getClientType());
		record.setId(us.getId());
		record.setIp(us.getIp());
		record.setLogoffTime(System.currentTimeMillis());
		record.setLogonName(us.getLogonName());
		record.setLogonTime(us.getLogonTime());
		record.setOrganId(us.getOrganId());
		record.setOrganName(us.getOrganName());
		record.setPath(us.getPath());
		record.setStandardNumber(us.getStandardNumber());
		record.setTicket(us.getTicket());
		if (us instanceof UserSessionUser) {
			record.setUserId(((UserSessionUser) us).getUser().getId());
		} else if (us instanceof UserSessionCcs) {
			record.setUserId(((UserSessionCcs) us).getCcs().getId());
		} else if (us instanceof UserSessionCrs) {
			record.setUserId(((UserSessionCrs) us).getCrs().getId());
		} else if (us instanceof UserSessionDws) {
			record.setUserId(((UserSessionDws) us).getDws().getId());
		} else if (us instanceof UserSessionMss) {
			record.setUserId(((UserSessionMss) us).getMss().getId());
		} else if (us instanceof UserSessionPts) {
			record.setUserId(((UserSessionPts) us).getPts().getId());
		} else if (us instanceof UserSessionDas) {
			record.setUserId(((UserSessionDas) us).getDas().getId());
		} else if (us instanceof UserSessionEns) {
			record.setUserId(((UserSessionEns) us).getEns().getId());
		} else if (us instanceof UserSessionRms) {
			record.setUserId(((UserSessionRms) us).getRms().getId());
		} else if (us instanceof UserSessionRss) {
			record.setUserId(((UserSessionRss) us).getRss().getId());
		}
		record.setKickFlag(new Short((short) 0));
		userSessionHistoryDAO.save(record);
		// 移除session
		userSessionDAO.delete(us);
	}

	@Override
	public Ccs ccsUpdateConfig(String ccsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Ccs ccs = ccsDAO.findById(ccsId);
		if (null != lanIp) {
			ccs.setLanIp(lanIp);
		}
		ccs.setPort(sipPort);
		ccs.setTelnetPort(telnetPort);
		return ccs;
	}

	@Override
	public Pts ptsUpdateConfig(String ptsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Pts pts = ptsDAO.findById(ptsId);
		if (null != lanIp) {
			pts.setLanIp(lanIp);
		}
		pts.setPort(sipPort);
		pts.setTelnetPort(telnetPort);
		return pts;
	}

	@Override
	public Dws dwsUpdateConfig(String dwsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Dws dws = dwsDAO.findById(dwsId);
		if (null != lanIp) {
			dws.setLanIp(lanIp);
		}
		dws.setPort(sipPort);
		dws.setTelnetPort(telnetPort);
		return dws;
	}

	@Override
	public Mss mssUpdateConfig(String mssId, String lanIp, String sipPort,
			String telnetPort, String videoPort) throws BusinessException {
		Mss mss = mssDAO.findById(mssId);
		if (null != lanIp) {
			mss.setLanIp(lanIp);
		}
		mss.setPort(sipPort);
		mss.setTelnetPort(telnetPort);
		// 修改扩展configValue值
		String configValue = mss.getConfigValue();
		String[] array = null;
		if (StringUtils.isNotBlank(configValue)) {
			array = configValue.split(",");
		}
		array = MyStringUtil.setAttributeValue(array, "videoPort", videoPort);
		mss.setConfigValue(MyStringUtil.array2String(array, ","));
		return mss;
	}

	@Override
	public Das dasUpdateConfig(String dasId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Das das = dasDAO.findById(dasId);
		if (null != lanIp) {
			das.setLanIp(lanIp);
		}
		das.setPort(sipPort);
		das.setTelnetPort(telnetPort);
		return das;
	}

	@Override
	public CrsUpdateConfigVO crsUpdateConfig(String crsId, String lanIp,
			String sipPort, String telnetPort, String videoPort)
			throws BusinessException {
		Crs crs = crsDAO.findById(crsId);

		if (null != lanIp) {
			crs.setLanIp(lanIp);
		}
		crs.setPort(sipPort);
		crs.setTelnetPort(telnetPort);
		// 修改扩展configValue值
		String configValue = crs.getConfigValue();
		String[] array = null;
		if (StringUtils.isNotBlank(configValue)) {
			array = configValue.split(",");
		}
		array = MyStringUtil.setAttributeValue(array, "videoPort", videoPort);
		crs.setConfigValue(MyStringUtil.array2String(array, ","));
		// 返回数据
		CrsUpdateConfigVO vo = new CrsUpdateConfigVO();
		vo.setMaxInput(MyStringUtil.getAttributeValue(array, "maxInput"));
		vo.setMaxOutput(MyStringUtil.getAttributeValue(array, "maxOutput"));
		vo.setWanIp(crs.getWanIp());
		Integer totalCount = cameraDAO.cameraTotalCountByCrsId(crsId);
		vo.setTotalCount(totalCount + "");
		// 查询存储下的摄像头列表
		// List<Camera> cameras = cameraDAO.listCrsCamera(crsId);
		// for (Camera camera : cameras) {
		// CrsUpdateConfigVO.Channel channel = vo.new Channel();
		// channel.setSn(camera.getStandardNumber());
		// channel.setPlan(camera.getProperty().getCenterStorePlan());
		// vo.getChannels().add(channel);
		// }
		return vo;
	}

	public List<PlatformServerVO> setCcsServerVO(List<Ccs> ccsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Ccs item : ccsList) {
			PlatformServerVO vo = new PlatformServerVO();
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_CCS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			vo.setForward(item.getForward().toString());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setCrsServerVO(List<Crs> crsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Crs item : crsList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_CRS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setMssServerVO(List<Mss> mssList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Mss item : mssList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_MSS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setPtsServerVO(List<Pts> ptsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Pts item : ptsList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_PTS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setDwsServerVO(List<Dws> dwsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Dws item : dwsList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_DWS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setDasServerVO(List<Das> dasList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Das item : dasList) {
			PlatformServerVO vo = new PlatformServerVO();
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_DAS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setEnsServerVO(List<Ens> ensList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Ens item : ensList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_ENS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setRmsServerVO(List<Rms> rmsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Rms item : rmsList) {
			PlatformServerVO vo = new PlatformServerVO();
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_RMS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	public List<PlatformServerVO> setRssServerVO(List<Rss> rssList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Rss item : rssList) {
			PlatformServerVO vo = new PlatformServerVO();
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_RSS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createDas(String standardNumber, String name,
			String location, String lanIp, String wanIp, String configValue,
			String port) {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Das> list = dasDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = mssDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Das> list = dasDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		Das das = new Das();
		// String id = dasDAO.getNextId("Das", 1);
		// das.setId(id);
		das.setConfigValue(configValue);
		das.setCreateTime(System.currentTimeMillis());
		das.setLanIp(lanIp);
		das.setLocation(location);
		das.setName(name);
		das.setPort(port);
		das.setStandardNumber(standardNumber);
		das.setWanIp(wanIp);
		dasDAO.save(das);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_DAS);
		return das.getId();
	}

	@Override
	public void updateDas(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Das> list = null;
		Das das = dasDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = dasDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(das.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_DAS);
			das.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = dasDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			das.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = dasDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			das.setLanIp(lanIp);
		}

		if (null != location) {
			das.setLocation(location);
		}
		if (null != wanIp) {
			das.setWanIp(wanIp);
		}
		if (null != port) {
			das.setPort(port);
		}
		if (null != configValue) {
			das.setConfigValue(configValue);
		}
	}

	@Override
	public void deleteDas(String id) {
		// 检查是否有子设备
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("das.id", id);
		List<WindSpeed> ws = wsDAO.findByPropertys(params);
		if (ws.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child WindSpeed found !");
		}

		List<VehicleDetector> vd = vdDAO.findByPropertys(params);
		if (vd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child VehicleDetector found !");
		}

		List<WeatherStat> wst = wstDAO.findByPropertys(params);
		if (wst.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child WeatherStat found !");
		}

		List<LoLi> loli = loliDAO.findByPropertys(params);
		if (loli.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child LoLi found !");
		}

		List<FireDetector> fd = fdDAO.findByPropertys(params);
		if (fd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child FireDetector found !");
		}

		List<Covi> covi = coviDAO.findByPropertys(params);
		if (covi.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child Covi found !");
		}

		List<NoDetector> noDetector = noDetectorDAO.findByPropertys(params);
		if (noDetector.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child NoDetector found !");
		}

		List<PushButton> pb = pbDAO.findByPropertys(params);
		if (pb.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child PushButton found !");
		}

		List<ControlDevice> cd = controlDeviceDAO.findByPropertys(params);
		if (cd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_SERVER_EXIST,
					"Child ControlDevice found !");
		}

		// 同步SN
		Das das = dasDAO.findById(id);
		syncSN(das.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_DAS);
		dasDAO.delete(das);

	}

	@Override
	public List<ListDasVO> listDas() {
		List<Das> dass = dasDAO.findAll();
		List<ListDasVO> list = new ArrayList<ListDasVO>();

		for (Das das : dass) {
			ListDasVO vo = new ListDasVO();
			vo.setConfigValue(das.getConfigValue());
			vo.setCreateTime(das.getCreateTime().toString());
			vo.setId(das.getId());
			vo.setLanIp(das.getLanIp());
			vo.setLocation(das.getLocation());
			vo.setName(das.getName());
			vo.setPort(das.getPort() != null ? das.getPort() : "");
			vo.setStandardNumber(das.getStandardNumber());
			vo.setType(TypeDefinition.CLIENT_TYPE_DAS + "");
			vo.setWanIp(das.getWanIp());
			vo.setTelnetPort(das.getTelnetPort() != null ? das.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createEns(String standardNumber, String name,
			String location, String lanIp, String wanIp, String configValue,
			String port) {
		// name重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Ens> list = ensDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		// lanIp重复检查
		// params.clear();
		// params.put("lanIp", lanIp);
		// list = mssDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp[" + lanIp
		// + "] is already exist !");
		// }

		// standardNumber重复检查
		// params.clear();
		params.put("standardNumber", standardNumber);
		List<Ens> list = ensDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		Ens ens = new Ens();
		// String id = ensDAO.getNextId("Ens", 1);
		// ens.setId(id);
		ens.setConfigValue(configValue);
		ens.setCreateTime(System.currentTimeMillis());
		ens.setLanIp(lanIp);
		ens.setLocation(location);
		ens.setName(name);
		ens.setPort(port);
		ens.setStandardNumber(standardNumber);
		ens.setWanIp(wanIp);
		ensDAO.save(ens);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ENS);
		return ens.getId();
	}

	@Override
	public void updateEns(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String ccsId, String configValue) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Ens> list = null;
		Ens ens = ensDAO.findById(id);

		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = ensDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(ens.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ENS);
			ens.setStandardNumber(standardNumber);
		}
		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = ensDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			ens.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = ensDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			ens.setLanIp(lanIp);
		}

		if (null != location) {
			ens.setLocation(location);
		}
		if (null != wanIp) {
			ens.setWanIp(wanIp);
		}
		if (null != port) {
			ens.setPort(port);
		}
		if (null != configValue) {
			ens.setConfigValue(configValue);
		}
		if (null != ccsId) {
			ens.setCcs(ccsDAO.findById(ccsId));
		}

	}

	@Override
	public void deleteEns(String id) {
		// 检查是否有子设备

		// 同步SN
		Ens ens = ensDAO.findById(id);
		syncSN(ens.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_ENS);
		ensDAO.deleteById(id);

	}

	@Override
	public List<ListEnsVO> listEns() {
		List<Ens> enss = ensDAO.findAll();
		List<ListEnsVO> list = new ArrayList<ListEnsVO>();

		for (Ens ens : enss) {
			ListEnsVO vo = new ListEnsVO();
			Ccs ccs = ens.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(ens.getConfigValue());
			vo.setCreateTime(ens.getCreateTime().toString());
			vo.setId(ens.getId());
			vo.setLanIp(ens.getLanIp());
			vo.setLocation(ens.getLocation());
			vo.setName(ens.getName());
			vo.setPort(ens.getPort() != null ? ens.getPort() : "");
			vo.setStandardNumber(ens.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_ENS + "");
			vo.setWanIp(ens.getWanIp());
			vo.setTelnetPort(ens.getTelnetPort() != null ? ens.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<Element> mapDevices(String dasId, List<Element> devices)
			throws BusinessException {
		// DAS上的设备配置List转换为Map,提高查询执行效率
		Map<String, String> vdMap = new HashMap<String, String>();
		Map<String, String> wsMap = new HashMap<String, String>();
		Map<String, String> wstMap = new HashMap<String, String>();
		Map<String, String> nodMap = new HashMap<String, String>();
		Map<String, String> loliMap = new HashMap<String, String>();
		Map<String, String> coviMap = new HashMap<String, String>();
		Map<String, String> cdMap = new HashMap<String, String>();
		Map<String, String> rdMap = new HashMap<String, String>();
		Map<String, String> vidMap = new HashMap<String, String>();
		Map<String, String> pbMap = new HashMap<String, String>();
		for (Element device : devices) {
			String sn = device.getAttributeValue("StandardNumber");
			String type = device.getAttributeValue("Type");
			if (StringUtils.isBlank(sn)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"[StandardNumber] can not be null !");
			}
			if (StringUtils.isBlank(type)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"[Type] can not be null !");
			}
			if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
				vdMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(type)) {
				wsMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_WST + "").equals(type)) {
				wstMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_NOD + "").equals(type)) {
				nodMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_LOLI + "").equals(type)) {
				loliMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_COVI + "").equals(type)) {
				coviMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_CMS + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_FAN + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_LIGHT + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_RD + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_WP + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_RAIL + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_IS + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_PB + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_TSL + "").equals(type)
					|| (TypeDefinition.DEVICE_TYPE_LIL + "").equals(type)) {
				cdMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
					.equals(type)) {
				rdMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
					.equals(type)) {
				vidMap.put(sn, type);
			} else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(type)) {
				pbMap.put(sn, type);
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Type[" + type + "] invalid !");
			}
		}

		// 返回列表
		List<Element> list = new LinkedList<Element>();
		// 分类查询和匹配各种设备，匹配上的加入到返回列表中
		if (!vdMap.isEmpty()) {
			List<VehicleDetector> vds = vdDAO.listByDAS(dasId);
			for (VehicleDetector vd : vds) {
				String value = vdMap.get(vd.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(vd.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							vd.getStandardNumber() != null ? vd
									.getStandardNumber() : "");
					e.setAttribute("Period", vd.getPeriod() != null ? vd
							.getPeriod().toString() : "");
					e.setAttribute("Note", vd.getNote() != null ? vd.getNote()
							: "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("SUpLimit",
							vd.getsUpLimit() != null ? vd.getsUpLimit()
									.toString() : "");
					threshold.setAttribute("SLowLimit",
							vd.getsLowLimit() != null ? vd.getsLowLimit()
									.toString() : "");
					threshold.setAttribute("OUpLimit",
							vd.getoUpLimit() != null ? vd.getoUpLimit()
									.toString() : "");
					threshold.setAttribute("OLowLimit",
							vd.getoLowLimit() != null ? vd.getoLowLimit()
									.toString() : "");
					threshold.setAttribute("VUpLimit",
							vd.getvUpLimit() != null ? vd.getvUpLimit()
									.toString() : "");
					threshold.setAttribute("VLowLimit",
							vd.getvLowLimit() != null ? vd.getvLowLimit()
									.toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!wsMap.isEmpty()) {
			List<WindSpeed> wss = wsDAO.listByDAS(dasId);
			for (WindSpeed ws : wss) {
				String value = wsMap.get(ws.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(ws.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							ws.getStandardNumber() != null ? ws
									.getStandardNumber() : "");
					e.setAttribute("Period", ws.getPeriod() != null ? ws
							.getPeriod().toString() : "");
					e.setAttribute("Note", ws.getNote() != null ? ws.getNote()
							: "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("WUpLimit",
							ws.getwUpLimit() != null ? ws.getwUpLimit()
									.toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!wstMap.isEmpty()) {
			List<WeatherStat> wsts = wstDAO.listByDAS(dasId);
			for (WeatherStat wst : wsts) {
				String value = wstMap.get(wst.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(wst.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							wst.getStandardNumber() != null ? wst
									.getStandardNumber() : "");
					e.setAttribute("Period", wst.getPeriod() != null ? wst
							.getPeriod().toString() : "");
					e.setAttribute("Note",
							wst.getNote() != null ? wst.getNote() : "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("WUpLimit",
							wst.getwUpLimit() != null ? wst.getwUpLimit()
									.toString() : "");
					threshold.setAttribute("VLowLimit",
							wst.getvLowLimit() != null ? wst.getvLowLimit()
									.toString() : "");
					threshold.setAttribute("RUpLimit",
							wst.getrUpLimit() != null ? wst.getrUpLimit()
									.toString() : "");
					threshold.setAttribute("SUpLimit",
							wst.getsUpLimit() != null ? wst.getsUpLimit()
									.toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!nodMap.isEmpty()) {
			List<NoDetector> nods = noDetectorDAO.listByDAS(dasId);
			for (NoDetector nod : nods) {
				String value = nodMap.get(nod.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(nod.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							nod.getStandardNumber() != null ? nod
									.getStandardNumber() : "");
					e.setAttribute("Period", nod.getPeriod() != null ? nod
							.getPeriod().toString() : "");
					e.setAttribute("Note",
							nod.getNote() != null ? nod.getNote() : "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("NoConctLimit", nod
							.getNoConctLimit() != null ? nod.getNoConctLimit()
							.toString() : "");
					threshold.setAttribute("NooConctLimit", nod
							.getNooConctLimit() != null ? nod
							.getNooConctLimit().toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!loliMap.isEmpty()) {
			List<LoLi> lolis = loliDAO.listByDAS(dasId);
			for (LoLi loli : lolis) {
				String value = loliMap.get(loli.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(loli.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							loli.getStandardNumber() != null ? loli
									.getStandardNumber() : "");
					e.setAttribute("Period", loli.getPeriod() != null ? loli
							.getPeriod().toString() : "");
					e.setAttribute("Note",
							loli.getNote() != null ? loli.getNote() : "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("LoLumiMax",
							loli.getLoLumiMax() != null ? loli.getLoLumiMax()
									.toString() : "");
					threshold.setAttribute("LoLumiMin",
							loli.getLoLumiMin() != null ? loli.getLoLumiMin()
									.toString() : "");
					threshold.setAttribute("LiLumiMax",
							loli.getLiLumiMax() != null ? loli.getLiLumiMax()
									.toString() : "");
					threshold.setAttribute("LiLumiMin",
							loli.getLiLumiMin() != null ? loli.getLiLumiMin()
									.toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!coviMap.isEmpty()) {
			List<Covi> covis = coviDAO.listByDAS(dasId);
			for (Covi covi : covis) {
				String value = coviMap.get(covi.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(covi.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							covi.getStandardNumber() != null ? covi
									.getStandardNumber() : "");
					e.setAttribute("Period", covi.getPeriod() != null ? covi
							.getPeriod().toString() : "");
					e.setAttribute("Note",
							covi.getNote() != null ? covi.getNote() : "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("CoConctLimit", covi
							.getCoConctLimit() != null ? covi.getCoConctLimit()
							.toString() : "");
					threshold.setAttribute("VisibilityLimit", covi
							.getVisibilityLimit() != null ? covi
							.getVisibilityLimit().toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!cdMap.isEmpty()) {
			List<ControlDevice> cds = controlDeviceDAO.listByDAS(dasId);
			for (ControlDevice cd : cds) {
				String value = cdMap.get(cd.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(cd.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							cd.getStandardNumber() != null ? cd
									.getStandardNumber() : "");
					e.setAttribute("Period", cd.getPeriod() != null ? cd
							.getPeriod().toString() : "");
					e.setAttribute("Note", cd.getNote() != null ? cd.getNote()
							: "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_FALSE);
					list.add(e);
				}
			}
		}
		if (!rdMap.isEmpty()) {
			List<RoadDetector> rds = roadDetectorDAO.listByDAS(dasId);
			for (RoadDetector rd : rds) {
				String value = rdMap.get(rd.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(rd.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							rd.getStandardNumber() != null ? rd
									.getStandardNumber() : "");
					e.setAttribute("Period", rd.getPeriod() != null ? rd
							.getPeriod().toString() : "");
					e.setAttribute("Note", rd.getNote() != null ? rd.getNote()
							: "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("RoadTemperature", rd
							.getRoadTemperature() != null ? rd
							.getRoadTemperature().toString() : "");
					threshold.setAttribute("WaterThickness", rd
							.getWaterThickness() != null ? rd
							.getWaterThickness().toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!vidMap.isEmpty()) {
			List<ViDetector> vids = viDetectorDAO.listByDAS(dasId);
			for (ViDetector vid : vids) {
				String value = vidMap.get(vid.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(vid.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							vid.getStandardNumber() != null ? vid
									.getStandardNumber() : "");
					e.setAttribute("Period", vid.getPeriod() != null ? vid
							.getPeriod().toString() : "");
					e.setAttribute("Note",
							vid.getNote() != null ? vid.getNote() : "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_TRUE);
					Element threshold = new Element("Threshold");
					threshold.setAttribute("VisibilityLimit", vid
							.getVisibilityLimit() != null ? vid
							.getVisibilityLimit().toString() : "");
					e.addContent(threshold);
					list.add(e);
				}
			}
		}
		if (!pbMap.isEmpty()) {
			List<PushButton> pbs = pushButtonDAO.listByDAS(dasId);
			for (PushButton pb : pbs) {
				String value = pbMap.get(pb.getStandardNumber());
				if (null != value) {
					Element e = new Element("Deivice");
					e.setAttribute("Name",
							Base64Utils.getBASE64(pb.getName().getBytes()));
					e.setAttribute("Type", value != null ? value : "");
					e.setAttribute(
							"StandardNumber",
							pb.getStandardNumber() != null ? pb
									.getStandardNumber() : "");
					e.setAttribute("Period", pb.getPeriod() != null ? pb
							.getPeriod().toString() : "");
					e.setAttribute("Note", pb.getNote() != null ? pb.getNote()
							: "");
					e.setAttribute("AlarmEnable",
							TypeDefinition.ALARM_ENABLE_FALSE);
					list.add(e);
				}
			}
		}
		return list;
	}

	@Override
	public void writeHardwareInfoToDB() throws BusinessException {
		String motherBoardSN = HardwareUtil.getMotherboardSN();
		String cpuId = HardwareUtil.getCPUID();
		String mac = HardwareUtil.getMACAddress();

		// 查看该服务器硬件信息是否已经写入到数据库
		int motherBoardSNFlag = 0;
		int cpuIdFlag = 0;
		int macFlag = 0;
		List<ServerInfo> list = serverInfoDAO.findAll();
		for (ServerInfo record : list) {
			if (StringUtils.isNotBlank(motherBoardSN)) {
				if (motherBoardSN.equals(record.getMotherBoardSN())) {
					motherBoardSNFlag = 1;
				} else if (motherBoardSNFlag != 1) {
					motherBoardSNFlag = 2;
				}
			}
			if (StringUtils.isNotBlank(cpuId)) {
				if (cpuId.equals(record.getCpuId())) {
					cpuIdFlag = 1;
				} else if (cpuIdFlag != 1) {
					cpuIdFlag = 2;
				}
			}
			if (StringUtils.isNotBlank(mac)) {
				if (mac.equals(record.getMac())) {
					macFlag = 1;
				} else if (macFlag != 1) {
					macFlag = 2;
				}
			}
		}

		if (motherBoardSNFlag == 2 || cpuIdFlag == 2 || macFlag == 2
				|| (motherBoardSNFlag == 0 && cpuIdFlag == 0 && macFlag == 0)) {
			ServerInfo entity = new ServerInfo();
			entity.setMotherBoardSN(motherBoardSN);
			entity.setCpuId(cpuId);
			entity.setMac(mac);
			serverInfoDAO.save(entity);
		}
	}

	@Override
	public String getPlatformHardware() throws BusinessException {
		StringBuffer motherBoardList = new StringBuffer();
		StringBuffer cpuIdList = new StringBuffer();
		StringBuffer macList = new StringBuffer();

		List<ServerInfo> list = serverInfoDAO.findAll();
		for (ServerInfo info : list) {
			if (StringUtils.isNotBlank(info.getMotherBoardSN())) {
				motherBoardList.append(info.getMotherBoardSN());
				motherBoardList.append(",");
			}
			if (StringUtils.isNotBlank(info.getCpuId())) {
				cpuIdList.append(info.getCpuId());
				cpuIdList.append(",");
			}
			if (StringUtils.isNotBlank(info.getMac())) {
				macList.append(info.getMac());
				macList.append(",");
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("mother_board_list = ");
		// 去掉最后的逗号
		if (motherBoardList.length() > 0) {
			sb.append(motherBoardList.substring(0, motherBoardList.length() - 1));
		}
		sb.append("\r\n");
		sb.append("cpuid_list = ");
		// 去掉最后的逗号
		if (cpuIdList.length() > 0) {
			sb.append(cpuIdList.substring(0, cpuIdList.length() - 1));
		}
		sb.append("\r\n");
		sb.append("mac_list = ");
		// 去掉最后的逗号
		if (macList.length() > 0) {
			sb.append(macList.substring(0, macList.length() - 1));
		}

		return sb.toString();
	}

	@Override
	public Element listCRSPlan(String crsId, Integer start, Integer limit) {
		Element store = new Element("Store");
		List<Channel> cameras = cameraDAO.listCrsCamera(crsId, start, limit);
		for (Channel camera : cameras) {
			Element channel = new Element("Channel");
			channel.setAttribute("SN", camera.getSn() != null ? camera.getSn()
					: "");
			channel.setAttribute("Plan",
					camera.getPlan() != null ? camera.getPlan() : "");
			channel.setAttribute("Stream",
					camera.getStoreStream() != null ? camera.getStoreStream()
							: "");
			store.addContent(channel);
		}
		return store;
	}

	@Override
	public String createGis(String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue) {
		// name重复检查
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// params.put("name", name);
		// List<Gis> list = gisDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Gis gis = new Gis();
		gis.setConfigValue(configValue);
		gis.setCreateTime(System.currentTimeMillis());
		gis.setLanIp(lanIp);
		gis.setLocation(location);
		gis.setName(name);
		gis.setPort(port);
		gis.setStandardNumber(standardNumber);
		gis.setWanIp(wanIp);
		gisDAO.save(gis);
		return gis.getId();
	}

	@Override
	public void updateGis(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Gis> list = null;
		Gis gis = gisDAO.findById(id);

		if (null != name) {
			// name重复检查
			// params.clear();
			// params.put("name", name);
			// list = gisDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!(list.get(0).getId().equals(id))) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + name + "] is already exist !");
			// }
			// }
			gis.setName(name);
		}
		if (null != lanIp) {
			gis.setLanIp(lanIp);
		}

		if (null != location) {
			gis.setLocation(location);
		}
		if (null != wanIp) {
			gis.setWanIp(wanIp);
		}
		if (null != port) {
			gis.setPort(port);
		}
		if (null != configValue) {
			gis.setConfigValue(configValue);
		}

	}

	@Override
	public List<ListGisVO> listGis() {
		List<Gis> giss = gisDAO.findAll();
		List<ListGisVO> list = new ArrayList<ListGisVO>();

		for (Gis gis : giss) {
			ListGisVO vo = new ListGisVO();
			vo.setConfigValue(gis.getConfigValue());
			vo.setCreateTime(gis.getCreateTime().toString());
			vo.setId(gis.getId());
			vo.setLanIp(gis.getLanIp());
			vo.setLocation(gis.getLocation());
			vo.setName(gis.getName());
			vo.setPort(gis.getPort() != null ? gis.getPort() : "");
			vo.setStandardNumber(gis.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_GIS + "");
			vo.setWanIp(gis.getWanIp());
			vo.setTelnetPort(gis.getTelnetPort() != null ? gis.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteGis(String id) {
		gisDAO.deleteById(id);
	}

	@Override
	public String createRms(String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Rms> list = rmsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = ccsDAO.getNextId("Ccs", 1);
		Rms rms = new Rms();
		// ccs.setId(id);
		rms.setConfigValue(configValue);
		rms.setCreateTime(System.currentTimeMillis());
		rms.setLanIp(lanIp);
		rms.setLocation(location);
		rms.setName(name);
		rms.setPort(port);
		rms.setStandardNumber(standardNumber);
		rms.setWanIp(wanIp);
		rmsDAO.save(rms);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_RMS);
		return rms.getId();
	}

	@Override
	public void updateRms(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String configValue,
			String port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Rms> list = null;

		Rms rms = rmsDAO.findById(id);
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = rmsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(rms.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_RMS);
			rms.setStandardNumber(standardNumber);
		}
		if (null != name) {
			rms.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = rmsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			rms.setLanIp(lanIp);
		}
		if (null != location) {
			rms.setLocation(location);
		}
		if (null != wanIp) {
			rms.setWanIp(wanIp);
		}
		if (null != configValue) {
			rms.setConfigValue(configValue);
		}
		if (null != port) {
			rms.setPort(port);
		}
	}

	@Override
	public void deleteRms(String id) {
		// 同步SN
		Rms rms = rmsDAO.findById(id);
		syncSN(rms.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_RMS);
		rmsDAO.delete(rms);

	}

	@Override
	public List<ListRmsVO> listRms() {
		List<Rms> rmses = rmsDAO.findAll();
		List<ListRmsVO> list = new ArrayList<ListRmsVO>();
		for (Rms rms : rmses) {
			ListRmsVO vo = new ListRmsVO();
			vo.setConfigValue(rms.getConfigValue());
			vo.setCreateTime(rms.getCreateTime().toString());
			vo.setId(rms.getId());
			vo.setLanIp(rms.getLanIp());
			vo.setLocation(rms.getLocation());
			vo.setName(rms.getName());
			vo.setPort(rms.getPort() != null ? rms.getPort() : "");
			vo.setStandardNumber(rms.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_RMS + "");
			vo.setWanIp(rms.getWanIp());
			vo.setTelnetPort(rms.getTelnetPort() != null ? rms.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public Rms rmsUpdateConfig(String id, String lanIp, String sipPort,
			String telnetPort, String videoPort) throws BusinessException {
		Rms rms = rmsDAO.findById(id);
		if (null != lanIp) {
			rms.setLanIp(lanIp);
		}
		rms.setPort(sipPort);
		rms.setTelnetPort(telnetPort);
		// 修改扩展configValue值
		String configValue = rms.getConfigValue();
		String[] array = null;
		if (StringUtils.isNotBlank(configValue)) {
			array = configValue.split(",");
		}
		array = MyStringUtil.setAttributeValue(array, "videoPort", videoPort);
		rms.setConfigValue(MyStringUtil.array2String(array, ","));
		return rms;
	}

	@Override
	public ListOnlineUsersDTO listOnlinePlatformServer(Integer startIndex,
			Integer limit, String type) {
		ListOnlineUsersDTO dto = new ListOnlineUsersDTO();
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		List<UserSessionCcs> ccsList = userSessionDAO.listOnlineCcs();
		List<UserSessionCrs> crsList = userSessionDAO.listOnlineCrs();
		List<UserSessionMss> mssList = userSessionDAO.listOnlineMss();
		List<UserSessionPts> ptsList = userSessionDAO.listOnlinePts();
		List<UserSessionDws> dwsList = userSessionDAO.listOnlineDws();
		List<UserSessionDas> dasList = userSessionDAO.listOnlineDas();
		List<UserSessionEns> ensList = userSessionDAO.listOnlineEns();
		List<UserSessionRms> rmsList = userSessionDAO.listOnlineRms();

		if (StringUtils.isBlank(type)) {
			list.addAll(setOnlineCcsVO(ccsList));
			list.addAll(setOnlineCrsVO(crsList));
			list.addAll(setOnlineMssVO(mssList));
			list.addAll(setOnlinePtsVO(ptsList));
			list.addAll(setOnlineDwsVO(dwsList));
			list.addAll(setOnlineDasVO(dasList));
			list.addAll(setOnlineEnsVO(ensList));
			list.addAll(setOnlineRmsVO(rmsList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_CCS + "")) {
			list.addAll(setOnlineCcsVO(ccsList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_CRS + "")) {
			list.addAll(setOnlineCrsVO(crsList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_MSS + "")) {
			list.addAll(setOnlineMssVO(mssList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_PTS + "")) {
			list.addAll(setOnlinePtsVO(ptsList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_DWS + "")) {
			list.addAll(setOnlineDwsVO(dwsList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_DAS + "")) {
			list.addAll(setOnlineDasVO(dasList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_ENS + "")) {
			list.addAll(setOnlineEnsVO(ensList));
		} else if (type.equals(TypeDefinition.SERVER_TYPE_RMS + "")) {
			list.addAll(setOnlineRmsVO(rmsList));
		}
		Integer totalCount = list.size();
		dto.setTotalCount(totalCount + "");

		if (totalCount != 0) {
			if (startIndex >= totalCount) {
				startIndex -= ((startIndex - totalCount) / limit + 1) * limit;
				list = list.subList(startIndex, totalCount);
			} else {
				if (startIndex + limit < totalCount) {
					list = list.subList(startIndex, startIndex + limit);
				} else {
					list = list.subList(startIndex, totalCount);
				}
			}
		}
		dto.setUserList(list);

		return dto;
	}

	private List<ListOnlineUsersVO> setOnlineRmsVO(List<UserSessionRms> rmsList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionRms entity : rmsList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getRms().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getRms().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineEnsVO(List<UserSessionEns> ensList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionEns entity : ensList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getEns().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getEns().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineDasVO(List<UserSessionDas> dasList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionDas entity : dasList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getDas().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getDas().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineDwsVO(List<UserSessionDws> dwsList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionDws entity : dwsList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getDws().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getDws().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlinePtsVO(List<UserSessionPts> ptsList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionPts entity : ptsList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getPts().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getPts().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineMssVO(List<UserSessionMss> mssList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionMss entity : mssList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getMss().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getMss().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineCrsVO(List<UserSessionCrs> crsList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionCrs entity : crsList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getCrs().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getCrs().getId());
			list.add(vo);
		}
		return list;
	}

	private List<ListOnlineUsersVO> setOnlineCcsVO(List<UserSessionCcs> ccsList) {
		List<ListOnlineUsersVO> list = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionCcs entity : ccsList) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(entity.getId());
			vo.setClientType(entity.getClientType());
			vo.setIp(entity.getIp());
			vo.setLogonName(entity.getLogonName());
			vo.setLogonTime(entity.getLogonTime() + "");
			vo.setName(entity.getCcs().getName());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setTicket(entity.getTicket());
			vo.setUpdateTime(entity.getUpdateTime() + "");
			vo.setUserId(entity.getCcs().getId());
			list.add(vo);
		}
		return list;
	}

	@Override
	public Ens ensUpdateConfig(String ensId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Ens ens = ensDAO.findById(ensId);
		if (null != lanIp) {
			ens.setLanIp(lanIp);
		}
		ens.setPort(sipPort);
		ens.setTelnetPort(telnetPort);
		return ens;
	}

	@Override
	public Rss rssUpdateConfig(String rssId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException {
		Rss rss = rssDAO.findById(rssId);
		if (null != lanIp) {
			rss.setLanIp(lanIp);
		}
		rss.setPort(sipPort);
		rss.setTelnetPort(telnetPort);
		return rss;
	}

	@Override
	public String createRss(String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Rss> list = rssDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// String id = ccsDAO.getNextId("Ccs", 1);
		Rss rss = new Rss();
		// ccs.setId(id);
		rss.setConfigValue(configValue);
		rss.setCreateTime(System.currentTimeMillis());
		rss.setLanIp(lanIp);
		rss.setLocation(location);
		rss.setName(name);
		rss.setPort(port);
		rss.setStandardNumber(standardNumber);
		rss.setWanIp(wanIp);
		rssDAO.save(rss);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_RSS);
		return rss.getId();
	}

	@Override
	public void updateRss(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String configValue,
			String port) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Rss> list = null;

		Rss rss = rssDAO.findById(id);
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = rssDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步SN
			syncSN(rss.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_RSS);
			rss.setStandardNumber(standardNumber);
		}
		if (null != name) {
			rss.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = rssDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			rss.setLanIp(lanIp);
		}
		if (null != location) {
			rss.setLocation(location);
		}
		if (null != wanIp) {
			rss.setWanIp(wanIp);
		}
		if (null != configValue) {
			rss.setConfigValue(configValue);
		}
		if (null != port) {
			rss.setPort(port);
		}

	}

	@Override
	public void deleteRss(String id) {
		// 同步SN
		Rss rss = rssDAO.findById(id);
		syncSN(rss.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_RSS);
		rssDAO.delete(rss);
	}

	@Override
	public List<ListRssVO> listRss() {
		List<Rss> rsses = rssDAO.findAll();
		List<ListRssVO> list = new ArrayList<ListRssVO>();
		for (Rss rss : rsses) {
			ListRssVO vo = new ListRssVO();
			vo.setConfigValue(rss.getConfigValue());
			vo.setCreateTime(rss.getCreateTime().toString());
			vo.setId(rss.getId());
			vo.setLanIp(rss.getLanIp());
			vo.setLocation(rss.getLocation());
			vo.setName(rss.getName());
			vo.setPort(rss.getPort() != null ? rss.getPort() : "");
			vo.setStandardNumber(rss.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_RSS + "");
			vo.setWanIp(rss.getWanIp());
			vo.setTelnetPort(rss.getTelnetPort() != null ? rss.getTelnetPort()
					: "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public Long getCrsDeviceUpdateTime() {
		List<DeviceUpdateListener> list = deviceUpdateListenerDAO.findAll();
		if (list.size() != 1) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"table sv_device_update_listener must only has 1 record !");
		}
		return list.get(0).getCrsUpdateTime();
	}

	@Override
	public String createSrs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Srs> list = srsDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		Srs srs = new Srs();
		srs.setConfigValue(configValue);
		srs.setCreateTime(System.currentTimeMillis());
		srs.setLanIp(lanIp);
		srs.setLocation(location);
		srs.setName(name);
		srs.setPort(port);
		srs.setStandardNumber(standardNumber);
		srs.setWanIp(wanIp);
		srsDAO.save(srs);
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_SRS);
		return srs.getId();
	}

	@Override
	public List<ListSrsVO> listSrs() throws BusinessException {
		List<Srs> srss = srsDAO.findAll();
		List<ListSrsVO> list = new ArrayList<ListSrsVO>();
		for (Srs srs : srss) {
			ListSrsVO vo = new ListSrsVO();
			Ccs ccs = srs.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(srs.getConfigValue());
			vo.setCreateTime(srs.getCreateTime().toString());
			vo.setId(srs.getId());
			vo.setLanIp(srs.getLanIp());
			vo.setLocation(srs.getLocation());
			vo.setName(srs.getName());
			vo.setPort(srs.getPort() != null ? srs.getPort().toString() : "");
			vo.setStandardNumber(srs.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_SRS + "");
			vo.setWanIp(srs.getWanIp());
			vo.setTelnetPort(srs.getTelnetPort() != null ? srs.getTelnetPort()
					: "");
			list.add(vo);
		}

		return list;
	}

	public List<PlatformServerVO> setSrsServerVO(List<Srs> srsList) {
		List<PlatformServerVO> list = new ArrayList<PlatformServerVO>();
		for (Srs item : srsList) {
			PlatformServerVO vo = new PlatformServerVO();
			Ccs ccs = item.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setConfigValue(item.getConfigValue());
			vo.setCreateTime(item.getCreateTime().toString());
			vo.setId(item.getId());
			vo.setLanIp(item.getLanIp());
			vo.setLocation(item.getLocation());
			vo.setName(item.getName());
			vo.setPort(item.getPort() != null ? item.getPort() : "");
			vo.setStandardNumber(item.getStandardNumber());
			vo.setType(TypeDefinition.SERVER_TYPE_SRS + "");
			vo.setWanIp(item.getWanIp());
			vo.setTelnetPort(item.getTelnetPort());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void updateSrs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Srs> list = null;
		Srs srs = srsDAO.findById(id);
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = srsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
			// 同步
			syncSN(srs.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_CRS);
			srs.setStandardNumber(standardNumber);
		}
		if (null != name) {
			srs.setName(name);
		}
		if (null != lanIp) {
			// lanIp重复检查
			params.clear();
			params.put("lanIp", lanIp);
			list = srsDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!(list.get(0).getId().equals(id))) {
					throw new BusinessException(ErrorCode.LANIP_EXIST, "lanIp["
							+ lanIp + "] is already exist !");
				}
			}

			srs.setLanIp(lanIp);
		}

		if (null != ccsId) {
			srs.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != location) {
			srs.setLocation(location);
		}
		if (null != wanIp) {
			srs.setWanIp(wanIp);
		}
		if (null != port) {
			srs.setPort(port);
		}
		if (null != configValue) {
			srs.setConfigValue(configValue);
		}
	}

	@Override
	public void deleteSrs(@LogParam("id") String id) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("srs.id", id);
		// 同步
		Srs srs = srsDAO.findById(id);
		syncSN(srs.getStandardNumber(), null, TypeDefinition.RESOURCE_TYPE_SRS);
		srsDAO.deleteById(id);
	}
}
