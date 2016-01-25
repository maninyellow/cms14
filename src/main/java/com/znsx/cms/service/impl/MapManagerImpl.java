package com.znsx.cms.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CmsPublishLogDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PlayItemDAO;
import com.znsx.cms.persistent.dao.PlaylistDAO;
import com.znsx.cms.persistent.dao.PolicyDAO;
import com.znsx.cms.persistent.dao.PolicyDeviceDAO;
import com.znsx.cms.persistent.dao.TimePolicyDAO;
import com.znsx.cms.persistent.dao.WareHouseDAO;
import com.znsx.cms.persistent.model.CmsPublishLog;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.PlayItem;
import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.persistent.model.Policy;
import com.znsx.cms.persistent.model.PolicyDevice;
import com.znsx.cms.persistent.model.TimePolicy;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.MapManager;
import com.znsx.cms.service.model.LightPolicyVO;
import com.znsx.cms.service.model.LightVO;
import com.znsx.cms.service.model.LogCmsVO;
import com.znsx.cms.service.model.PlayItemVO;
import com.znsx.cms.service.model.PlaylistVO;
import com.znsx.cms.service.model.TimePolicyItemVO;
import com.znsx.cms.service.model.TimePolicyVO;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 地图业务接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-22 下午2:22:06
 */
public class MapManagerImpl extends BaseManagerImpl implements MapManager {
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private PlayItemDAO playItemDAO;
	@Autowired
	private PlaylistDAO playlistDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private PolicyDAO policyDAO;
	@Autowired
	private PolicyDeviceDAO policyDeviceDAO;
	@Autowired
	private TimePolicyDAO timePolicyDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private CmsPublishLogDAO cmsPublishLogDAO;
	@Autowired
	private WareHouseDAO wareHouseDAO;

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void saveMarkers(List<Element> markers) throws BusinessException {
		for (Element marker : markers) {
			String sn = marker.getAttributeValue("StandardNumber");
			String type = marker.getAttributeValue("Type");
			if (StringUtils.isBlank(sn) || StringUtils.isBlank(type)) {
				continue;
			}
			String longitude = marker.getAttributeValue("Longitude");
			String latitude = marker.getAttributeValue("Latitude");
			if (null == longitude) {
				longitude = "";
			}
			if (null == latitude) {
				latitude = "";
			}
			try {
				// 摄像头
				if (type.equals(TypeDefinition.DEVICE_TYPE_CAMERA + "")) {
					cameraDAO.saveLonlat("Camera", sn, longitude, latitude);
				}
				// 车检器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_VD + "")) {
					cameraDAO.saveLonlat("VehicleDetector", sn, longitude,
							latitude);
				}
				// 气象检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_WST + "")) {
					cameraDAO
							.saveLonlat("WeatherStat", sn, longitude, latitude);
				}
				// 风速风向检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_WS + "")) {
					cameraDAO.saveLonlat("WindSpeed", sn, longitude, latitude);
				}
				// 光强检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_LOLI + "")) {
					cameraDAO.saveLonlat("LoLi", sn, longitude, latitude);
				}
				// 火灾检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
					cameraDAO.saveLonlat("FireDetector", sn, longitude,
							latitude);
				}
				// CO/VI检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_COVI + "")) {
					cameraDAO.saveLonlat("Covi", sn, longitude, latitude);
				}
				// 氮氧化物检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_NOD + "")) {
					cameraDAO.saveLonlat("NoDetector", sn, longitude, latitude);
				}
				// 控制类设备
				else if (type.equals(TypeDefinition.DEVICE_TYPE_CMS + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_FAN + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_LIGHT + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_RD + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_WP + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_IS + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_TSL + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_LIL + "")) {
					cameraDAO.saveLonlat("ControlDevice", sn, longitude,
							latitude);
				}
				// 氮氧化物检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
					cameraDAO.saveLonlat("PushButton", sn, longitude, latitude);
				}
				// 隧道或桥梁、收费站
				else if (type.equals(TypeDefinition.DEVICE_TYPE_TUNNEL + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_BRIDGE + "")
						|| type.equals(TypeDefinition.DEVICE_TYPE_TOLLGATE + "")) {
					cameraDAO.saveLonlat("Organ", sn, longitude, latitude);
				}
				// 仓库
				else if (type.equals(TypeDefinition.DEVICE_TYPE_WAREHOUSE + "")) {
					wareHouseDAO.saveLonlat("WareHouse", sn, longitude,
							latitude);
				}
				// 能见度仪
				else if (type.equals(TypeDefinition.DEVICE_TYPE_VI_DETECTOR
						+ "")) {
					cameraDAO.saveLonlat("ViDetector", sn, longitude, latitude);
				}
				// 路面检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR
						+ "")) {
					cameraDAO.saveLonlat("RoadDetector", sn, longitude,
							latitude);
				}
				// 桥面检测器
				else if (type.equals(TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR
						+ "")) {
					cameraDAO.saveLonlat("BridgeDetector", sn, longitude,
							latitude);
				}
				// 本平台表设置完毕后，增加对下级平台表的坐标设置
				cameraDAO.saveLonlat("SubPlatformResource", sn, longitude,
						latitude);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						e.getMessage());
			}
		}
	}

	public String createPlaylist(String organId, String name,
			List<Element> items) throws BusinessException {
		Playlist playlist = new Playlist();
		playlist.setName(name);
		Organ organ = organDAO.findById(organId);
		playlist.setOrgan(organ);
		playlistDAO.save(playlist);
		// 添加播放节目内容
		for (Element item : items) {
			PlayItem entity = new PlayItem();
			entity.setColor(item.getAttributeValue("Color"));
			entity.setContent(item.getAttributeValue("Content"));
			entity.setFont(item.getAttributeValue("Font"));
			entity.setPlaylist(playlist);
			entity.setSize(item.getAttributeValue("Size"));
			String duration = item.getAttributeValue("Duration");
			if (null != duration) {
				try {
					Integer period = Integer.parseInt(duration);
					entity.setTwinklePeriod(period);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.PARAMETER_INVALID,
							"Duration[" + duration + "] invalid !");
				}
			}
			String wordSpace = item.getAttributeValue("Space");
			if (null != wordSpace) {
				try {
					Short space = Short.parseShort(wordSpace);
					entity.setWordSpace(space);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.PARAMETER_INVALID,
							"Space[" + wordSpace + "] invalid !");
				}
			}
			playItemDAO.save(entity);
		}
		return playlist.getId();
	}

	public void deletePlaylist(@LogParam("id") String id)
			throws BusinessException {
		Playlist playlist = playlistDAO.findById(id);
		Set<PlayItem> items = playlist.getItems();
		for (PlayItem item : items) {
			playItemDAO.delete(item);
		}
		playlistDAO.delete(playlist);
	}

	public List<PlaylistVO> listPlaylist(String organId) {
		List<Playlist> list = playlistDAO.listByOrgan(organId);
		List<PlaylistVO> rtnList = new LinkedList<PlaylistVO>();
		for (Playlist playlist : list) {
			PlaylistVO vo = new PlaylistVO();
			vo.setId(playlist.getId().toString());
			vo.setName(playlist.getName());
			rtnList.add(vo);

			Set<PlayItem> playItems = playlist.getItems();
			for (PlayItem playItem : playItems) {
				PlayItemVO itemVO = new PlayItemVO();
				itemVO.setColor(playItem.getColor());
				itemVO.setContent(playItem.getContent());
				itemVO.setDuration(playItem.getTwinklePeriod() == null ? ""
						: playItem.getTwinklePeriod().toString());
				itemVO.setFont(playItem.getFont());
				itemVO.setSize(playItem.getSize());
				itemVO.setSpace(playItem.getWordSpace() == null ? "" : playItem
						.getWordSpace().toString());
				vo.getItems().add(itemVO);
			}
		}
		return rtnList;
	}

	public String saveLightPolicy(String organId, String name, String id,
			List<Element> lights) throws BusinessException {
		Policy policy = null;
		// 新增
		if (null == id) {
			policy = new Policy();
			policy.setName(name);
			policy.setType(TypeDefinition.POLICY_TYPE_LIGHT);
			Organ organ = organDAO.findById(organId);
			policy.setOrgan(organ);
			policyDAO.save(policy);
		}
		// 修改
		else {
			policy = policyDAO.findById(id);
			policy.setName(name);
			// 删除以前的数据
			policyDeviceDAO.deleteByPolicy(id);
		}
		// 增加policyDevice
		try {
			for (Element e : lights) {
				PolicyDevice light = new PolicyDevice();
				light.setDeviceId(e.getAttributeValue("Id"));
				light.setStatus(Short.parseShort(e.getAttributeValue("Status")));
				light.setType((short) TypeDefinition.DEVICE_TYPE_LIGHT);
				light.setPolicyId(policy.getId());
				policyDeviceDAO.batchInsert(light);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			PolicyDeviceDAO.vector.clear();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"[Id] and [Status] must be numberic character !");
		}
		policyDeviceDAO.excuteBatch();
		return policy.getId();
	}

	public void deleteLightPolicy(String id) throws BusinessException {
		policyDeviceDAO.deleteByPolicy(id);
		policyDAO.deleteById(id);
	}

	public List<LightPolicyVO> listLightPolicy(String organId) {
		List<LightPolicyVO> list = new LinkedList<LightPolicyVO>();

		List<Policy> policies = policyDAO.listByOrgan(organId,
				TypeDefinition.POLICY_TYPE_LIGHT);
		for (Policy policy : policies) {
			LightPolicyVO vo = new LightPolicyVO();
			vo.setId(policy.getId().toString());
			vo.setName(policy.getName());
			// 获取关联的设备列表
			List<LightVO> lights = policyDeviceDAO.listByPolicy(policy.getId());
			vo.setLights(lights);
			list.add(vo);
		}
		return list;
	}

	public String saveTimePolicy(String organId, @LogParam("name") String name,
			String id, List<Element> items) throws BusinessException {
		Policy policy = null;
		// 新增
		if (null == id) {
			policy = new Policy();
			policy.setName(name);
			policy.setType(TypeDefinition.POLICY_TYPE_TIME);
			Organ organ = organDAO.findById(organId);
			policy.setOrgan(organ);
			policyDAO.save(policy);
		}
		// 修改
		else {
			policy = policyDAO.findById(id);
			policy.setName(name);
			// 删除老的定时执行计划
			timePolicyDAO.deleteByTimePolicy(id);
		}
		// 添加定时执行计划
		for (Element item : items) {
			TimePolicy tp = new TimePolicy();
			try {
				tp.setPolicyId(item.getAttributeValue("LightPolicyId"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"LightPolicyId["
								+ item.getAttributeValue("LightPolicyId")
								+ "] invalid !");
			}
			tp.setTimePolicyId(policy.getId());
			tp.setBeginDate(buildDate(item.getAttributeValue("BeginDate")));
			tp.setEndDate(buildDate(item.getAttributeValue("EndDate")));
			tp.setBeginTime(buildTime(item.getAttributeValue("BeginTime")));
			tp.setEndTime(buildTime(item.getAttributeValue("EndTime")));
			timePolicyDAO.save(tp);
		}

		return policy.getId();
	}

	public List<TimePolicyVO> listTimePolicy(String organId) {
		List<TimePolicyVO> list = new LinkedList<TimePolicyVO>();

		List<Policy> policies = policyDAO.listByOrgan(organId,
				TypeDefinition.POLICY_TYPE_TIME);
		for (Policy policy : policies) {
			TimePolicyVO vo = new TimePolicyVO();
			vo.setId(policy.getId().toString());
			vo.setName(policy.getName());
			// 查询定时策略的策略执行计划列表
			List<TimePolicyItemVO> items = timePolicyDAO
					.listByTimePolicy(policy.getId());
			vo.setItems(items);
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteTimePolicy(@LogParam("id") String id)
			throws BusinessException {
		// 删除关联
		timePolicyDAO.deleteByTimePolicy(id);
		// 删除自身
		policyDAO.deleteById(id);
	}

	@Override
	public void cmsPublishLog(List<String> cmsSNs, String organId,
			String organName, String userId, String userName,
			List<Element> items) throws BusinessException {
		String[] sns = cmsSNs.toArray(new String[0]);
		List<ControlDevice> list = controlDeviceDAO.listCMSBySNs(sns);
		Timestamp sendTime = new Timestamp(System.currentTimeMillis());
		// 每个情报板针对每项发布信息都需要保存一条记录
		for (ControlDevice cms : list) {
			for (Element item : items) {
				CmsPublishLog record = new CmsPublishLog();
				record.setStandardNumber(cms.getStandardNumber());
				record.setStakeNumber(cms.getStakeNumber());
				record.setNavigation(Short.valueOf(cms.getNavigation()));
				record.setSendOrganId(organId);
				record.setSendOrganName(organName);
				record.setSendUserId(userId);
				record.setSendUserName(userName);
				record.setDuration(ElementUtil.getInteger(item, "Duration"));
				try {
					record.setContent(item.getAttributeValue("Content")
							.getBytes("utf8").length > 255 ? item
							.getAttributeValue("Content").substring(0, 80)
							: item.getAttributeValue("Content"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.ENCODING_ERROR,
							"utf-8 error");
				}
				record.setColor(item.getAttributeValue("Color"));
				record.setFont(item.getAttributeValue("Font"));
				record.setSize(item.getAttributeValue("Size"));
				record.setSpace(ElementUtil.getInteger(item, "Space"));
				record.setSendTime(sendTime);
				record.setX(ElementUtil.getInteger(item, "X"));
				record.setY(ElementUtil.getInteger(item, "Y"));
				record.setInfoType(ElementUtil.getShort(item, "Type"));
				record.setRowSpace(ElementUtil.getInteger(item, "RowSpace"));
				cmsPublishLogDAO.save(record);
			}
		}
	}

	@Override
	public List<CmsPublishLog> listCmsLatestRecord(String standardNumber) {
		return cmsPublishLogDAO.listLatestRecord(standardNumber);
	}

	/**
	 * 将MM-dd格式的日期转换为数字
	 * 
	 * @param date
	 *            MM-dd格式的日期，如04-25表示4月25日
	 * @return 数字形式的日期，如04-25转换为425
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午4:20:06
	 */
	private long buildDate(String date) throws BusinessException {
		String[] array = date.split("-");
		if (array.length != 2) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "Date["
					+ date + "] invalid !");
		}
		try {
			int month = Integer.parseInt(array[0]);
			int day = Integer.parseInt(array[1]);
			return month * 100 + day;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "Date["
					+ date + "] invalid !");
		}
	}

	/**
	 * 将hh:MM格式的时间转换为秒单位的数字
	 * 
	 * @param time
	 *            hh:MM格式的时间，如08:00
	 * @return 秒单位的数字
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午4:31:57
	 */
	private int buildTime(String time) throws BusinessException {
		String[] array = time.split(":");
		if (array.length != 2) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "Time["
					+ time + "] invalid !");
		}
		try {
			int hour = Integer.parseInt(array[0]);
			int minute = Integer.parseInt(array[1]);
			return hour * 3600 + minute * 60;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "Time["
					+ time + "] invalid !");
		}
	}

	@Override
	public Integer countTotalCMSLog(String cmsName, String userName,
			Long beginTime, Long endTime, String organId) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		String cmsSNs[] = controlDeviceDAO.cmsSNByOrgan(organs, cmsName);
		Integer countTotal = 0;
		// 如果cmsSNs情报板的sn没有查出来则返回0
		if (cmsSNs.length > 0) {
			countTotal = cmsPublishLogDAO.countTotalCMSLog(cmsSNs, userName,
					beginTime, endTime);
		}
		return countTotal;
	}

	@Override
	public List<Element> listCmsLog(String organId, String cmsName,
			String userName, Long beginTime, Long endTime, Integer startIndex,
			Integer limit) {
		String organs[] = organDAO.findOrgansByOrganId(organId); // 查询当前用户所在机构以及子机构集合
		String cmsSNs[] = controlDeviceDAO.cmsSNByOrgan(organs, cmsName); // 根据设备名称和机构查询设备SN
		List<LogCmsVO> cmses = new ArrayList<LogCmsVO>();
		List<CmsPublishLog> list = new ArrayList<CmsPublishLog>();
		// 如果cmsSNs情报板的sn没有查询出来则直接返回
		if (cmsSNs.length > 0) {
			cmses = controlDeviceDAO.listCmsVO(cmsSNs);
			list = cmsPublishLogDAO.listCmsLog(cmsSNs, userName, beginTime,
					endTime, startIndex, limit);
		}
		List<Element> elements = new ArrayList<Element>();
		String cmsName1 = "";
		for (CmsPublishLog cpll : list) {
			Element element = new Element("Device");
			element.setAttribute("Id", cpll.getId());
			for (LogCmsVO vo : cmses) {
				if (vo.getStandardNumber().equals(cpll.getStandardNumber())) {
					cmsName1 = vo.getName();
				}
			}
			element.setAttribute("CmsName", cmsName1);
			element.setAttribute("UserName",
					cpll.getSendUserName() != null ? cpll.getSendUserName()
							: "");
			element.setAttribute("StakeNumber",
					cpll.getStakeNumber() != null ? cpll.getStakeNumber() : "");
			element.setAttribute("Navigation",
					cpll.getNavigation() != null ? cpll.getNavigation()
							.toString() : "");
			element.setAttribute("OrganName",
					cpll.getSendOrganName() != null ? cpll.getSendOrganName()
							: "");
			element.setAttribute("Content",
					cpll.getContent() != null ? cpll.getContent() : "");
			element.setAttribute(
					"SendTime",
					cpll.getSendTime() != null ? format.format(cpll
							.getSendTime()) : "");
			element.setAttribute("StandardNumber",
					cpll.getStandardNumber() != null ? cpll.getStandardNumber()
							: "");
			elements.add(element);
		}
		return elements;
	}

	@Override
	public Integer countCmsPublishLog(String[] organs, String[] cmsSns,
			String userName, Long beginTime, Long endTime) {
		Integer countTotal = 0;
		// 如果cmsSNs情报板的sn没有查出来则返回0
		if (cmsSns.length > 0) {
			countTotal = cmsPublishLogDAO.countCmsPublishLog(cmsSns, userName,
					beginTime, endTime);
		}
		return countTotal;
	}

	@Override
	public String[] listCmsSnsByName(String[] organs, String cmsName) {
		return controlDeviceDAO.cmsSNByOrgan(organs, cmsName);
	}

	@Override
	public List<Element> listCmsPublishLog(String[] organs, String[] cmsSns,
			String userName, Long beginTime, Long endTime, Integer startIndex,
			Integer limit) {
		List<Element> elements = new LinkedList<Element>();
		// 如果cmsSNs情报板的sn没有查询出来则直接返回
		if (cmsSns.length <= 0) {
			return elements;
		}

		// 因为采用内存分页，限制超过5W行的查询
		int countTotal = cmsPublishLogDAO.countTotalCMSLog(cmsSns, userName,
				beginTime, endTime);
		if (countTotal > 50000) {
			throw new BusinessException(ErrorCode.TOO_MANY_ROWS_FIND,
					"too many record found !");
		}
		// 原始记录
		List<CmsPublishLog> logs = cmsPublishLogDAO.listCmsPublishLog(cmsSns,
				userName, beginTime, endTime);
		// 情报板名称map
		Map<String, String> map = controlDeviceDAO.mapCms(cmsSns);
		// 按照sendTime与sn合并的集合
		Map<Integer, Element> rowMap = new HashMap<Integer, Element>();

		for (CmsPublishLog log : logs) {
			// 根据sendTime与sn查找是否已经创建了Element
			Integer key = Integer.valueOf((log.getSendTime().getTime() + log
					.getStandardNumber()).hashCode());
			Element element = rowMap.get(key);
			// 创建新的合并记录
			if (null == element) {
				element = new Element("Device");
				element.setAttribute("Id", log.getId());
				element.setAttribute("CmsName",
						map.get(log.getStandardNumber()));
				element.setAttribute("UserName",
						log.getSendUserName() != null ? log.getSendUserName()
								: "");
				element.setAttribute("StakeNumber",
						log.getStakeNumber() != null ? log.getStakeNumber()
								: "");
				element.setAttribute("Navigation",
						log.getNavigation() != null ? log.getNavigation()
								.toString() : "");
				element.setAttribute("OrganName",
						log.getSendOrganName() != null ? log.getSendOrganName()
								: "");
				element.setAttribute("SendTime",
						log.getSendTime() != null ? log.getSendTime()
								.toString() : "");
				element.setAttribute(
						"StandardNumber",
						log.getStandardNumber() != null ? log
								.getStandardNumber() : "");
				element.setAttribute("Type",
						MyStringUtil.object2StringNotNull(log.getInfoType()));
				// 发布内容集合
				Element contents = new Element("Contents");
				element.addContent(contents);

				rowMap.put(key, element);

				elements.add(element);
			}

			// 每条log对应的内容
			Element item = new Element("Item");
			item.setAttribute("Content",
					MyStringUtil.object2StringNotNull(log.getContent()));
			item.setAttribute("Color",
					MyStringUtil.object2StringNotNull(log.getColor()));
			item.setAttribute("Font",
					MyStringUtil.object2StringNotNull(log.getFont()));
			item.setAttribute("Size",
					MyStringUtil.object2StringNotNull(log.getSize()));
			item.setAttribute("Duration",
					MyStringUtil.object2StringNotNull(log.getDuration()));
			item.setAttribute("Space",
					MyStringUtil.object2StringNotNull(log.getSpace()));
			item.setAttribute("X",
					MyStringUtil.object2StringNotNull(log.getX()));
			item.setAttribute("Y",
					MyStringUtil.object2StringNotNull(log.getY()));
			item.setAttribute("RowSpace",
					MyStringUtil.object2StringNotNull(log.getRowSpace()));
			element.getChild("Contents").addContent(item);
		}

		int from = startIndex.intValue();
		if (from > elements.size()) {
			return null;
		}

		int to = from + limit.intValue();
		if (to > elements.size()) {
			to = elements.size();
		}
		return elements.subList(from, to);
	}
}
