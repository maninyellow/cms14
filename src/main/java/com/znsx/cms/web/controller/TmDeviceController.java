package com.znsx.cms.web.controller;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.Base64;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.CmsCommand;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.PlayItem;
import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.persistent.model.PlaylistFolder;
import com.znsx.cms.persistent.model.TypeDef;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.LicenseManager;
import com.znsx.cms.service.iface.TmDeviceManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.CmsInfoVO;
import com.znsx.cms.service.model.CoviInfoVO;
import com.znsx.cms.service.model.DeviceGPSVO;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.GetBoxTransformerVO;
import com.znsx.cms.service.model.GetBridgeDetectorVO;
import com.znsx.cms.service.model.GetControlDeviceVO;
import com.znsx.cms.service.model.GetCoviVO;
import com.znsx.cms.service.model.GetFireDetectorVO;
import com.znsx.cms.service.model.GetLoliVO;
import com.znsx.cms.service.model.GetNoDetectorVO;
import com.znsx.cms.service.model.GetPushButtonVO;
import com.znsx.cms.service.model.GetRoadDetectorVO;
import com.znsx.cms.service.model.GetTypeDefVO;
import com.znsx.cms.service.model.GetUrgentPhoneVO;
import com.znsx.cms.service.model.GetVehicleDetectorVO;
import com.znsx.cms.service.model.GetViDetectorVO;
import com.znsx.cms.service.model.GetWeatherStatVO;
import com.znsx.cms.service.model.GetWindSpeedVO;
import com.znsx.cms.service.model.LoliInfoVO;
import com.znsx.cms.service.model.NodInfoVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.VdInfoVO;
import com.znsx.cms.service.model.WsInfoVO;
import com.znsx.cms.service.model.WstInfoVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.GetBoxTransformerDTO;
import com.znsx.cms.web.dto.omc.GetBridgeDetectirDTO;
import com.znsx.cms.web.dto.omc.GetControlDeviceDTO;
import com.znsx.cms.web.dto.omc.GetCoviDTO;
import com.znsx.cms.web.dto.omc.GetFireDetectorDTO;
import com.znsx.cms.web.dto.omc.GetGPSDeviceDTO;
import com.znsx.cms.web.dto.omc.GetLoliDTO;
import com.znsx.cms.web.dto.omc.GetNoDetectorDTO;
import com.znsx.cms.web.dto.omc.GetPushButtonDTO;
import com.znsx.cms.web.dto.omc.GetRoadDetectorDTO;
import com.znsx.cms.web.dto.omc.GetUrgentPhoneDTO;
import com.znsx.cms.web.dto.omc.GetVehicleDetectorDTO;
import com.znsx.cms.web.dto.omc.GetViDetectorDTO;
import com.znsx.cms.web.dto.omc.GetWeatherStatDTO;
import com.znsx.cms.web.dto.omc.GetWindSpeedDTO;
import com.znsx.cms.web.dto.omc.ListBoxTransformerDTO;
import com.znsx.cms.web.dto.omc.ListBridgeDetectorDTO;
import com.znsx.cms.web.dto.omc.ListControlDeviceDTO;
import com.znsx.cms.web.dto.omc.ListCoviDTO;
import com.znsx.cms.web.dto.omc.ListDeviceTypeDTO;
import com.znsx.cms.web.dto.omc.ListFireDetectorDTO;
import com.znsx.cms.web.dto.omc.ListGPSDeviceDTO;
import com.znsx.cms.web.dto.omc.ListGPSPrivilegeDTO;
import com.znsx.cms.web.dto.omc.ListLoliDTO;
import com.znsx.cms.web.dto.omc.ListNoDetectorDTO;
import com.znsx.cms.web.dto.omc.ListPushButtonDTO;
import com.znsx.cms.web.dto.omc.ListRoadDetectorDTO;
import com.znsx.cms.web.dto.omc.ListSolarDeviceDTO;
import com.znsx.cms.web.dto.omc.ListUrgentPhoneDTO;
import com.znsx.cms.web.dto.omc.ListVehicleDetectorDTO;
import com.znsx.cms.web.dto.omc.ListViDetectorDTO;
import com.znsx.cms.web.dto.omc.ListWeatherStatDTO;
import com.znsx.cms.web.dto.omc.ListWindSpeedDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 交通监控设备对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-15 下午3:36:22
 */
@Controller
public class TmDeviceController extends BaseController {
	@Autowired
	private TmDeviceManager tmDeviceManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private UserManager userManager;

	@InterfaceDescription(logon = true, method = "Vd_Info", cmd = "1222")
	@RequestMapping("/vd_info.xml")
	public void vdInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<VdInfoVO> list = tmDeviceManager.vdInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countVdInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Vd_Info");
		dto.setCmd("1222");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (VdInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Wst_Info", cmd = "1223")
	@RequestMapping("/wst_info.xml")
	public void wstInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<WstInfoVO> list = tmDeviceManager.wstInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countWstInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Wst_Info");
		dto.setCmd("1223");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (WstInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Covi_Info", cmd = "1224")
	@RequestMapping("/covi_info.xml")
	public void coviInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<CoviInfoVO> list = tmDeviceManager.coviInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countCoviInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Covi_Info");
		dto.setCmd("1224");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (CoviInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Ws_Info", cmd = "1225")
	@RequestMapping("/ws_info.xml")
	public void wsInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<WsInfoVO> list = tmDeviceManager.wsInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countWsInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Ws_Info");
		dto.setCmd("1225");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (WsInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Loli_Info", cmd = "1226")
	@RequestMapping("/loli_info.xml")
	public void loliInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<LoliInfoVO> list = tmDeviceManager.loliInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countLoliInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Loli_Info");
		dto.setCmd("1226");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (LoliInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Nod_Info", cmd = "1227")
	@RequestMapping("/nod_info.xml")
	public void nodInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		List<NodInfoVO> list = tmDeviceManager.nodInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		int count = tmDeviceManager.countNodInfo(organId, deviceName,
				navigation, stakeNumber);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Nod_Info");
		dto.setCmd("1227");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (NodInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Cms_Info", cmd = "1228")
	@RequestMapping("/cms_info.xml")
	public void cmsInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		String deviceName = reader.getString("deviceName", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		Short subType = reader.getShort("subType", true);

		List<CmsInfoVO> list = tmDeviceManager.cmsInfo(organId, deviceName,
				navigation, stakeNumber, subType, start, limit);
		int count = tmDeviceManager.countCmsInfo(organId, deviceName,
				navigation, stakeNumber, subType);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Cms_Info");
		dto.setCmd("1228");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (CmsInfoVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Fire_Detector", cmd = "2230")
	@RequestMapping("/create_fire_detector.json")
	public void createFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createFireDetector(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2230");
		dto.setMethod("Create_Fire_Detector");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Fire_Detector", cmd = "2231")
	@RequestMapping("/update_fire_detector.json")
	public void updateFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateFireDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2231");
		dto.setMethod("Update_Fire_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Fire_Detector", cmd = "2232")
	@RequestMapping("/delete_fire_detector.json")
	public void deleteFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteFireDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2232");
		dto.setMethod("Delete_Fire_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Fire_Detector", cmd = "2233")
	@RequestMapping("/get_fire_detector.json")
	public void getFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetFireDetectorVO fireDetector = deviceManager.getFireDetector(id);
		GetFireDetectorDTO dto = new GetFireDetectorDTO();
		dto.setFireDetector(fireDetector);
		dto.setCmd("2233");
		dto.setMethod("Get_Fire_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Fire_Detector", cmd = "2234")
	@RequestMapping("/list_fire_detector.json")
	public void listFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countFireDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetFireDetectorVO> fireDetectorList = deviceManager
				.listFireDetector(organId, name, standardNumber, stakeNumber,
						startIndex, limit);

		ListFireDetectorDTO dto = new ListFireDetectorDTO();
		dto.setCmd("2234");
		dto.setFireDetectorList(fireDetectorList);
		dto.setMethod("List_Fire_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Covi", cmd = "2240")
	@RequestMapping("/create_covi.json")
	public void createCovi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short coConctLimit = reader.getShort("coConctLimit", true);

		Short visibilityLimit = reader.getShort("visibilityLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createCovi(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude,
				coConctLimit, visibilityLimit, note, navigation, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2240");
		dto.setMethod("Create_Covi");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Covi", cmd = "2241")
	@RequestMapping("/update_covi.json")
	public void updateCovi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short coConctLimit = reader.getShort("coConctLimit", true);

		Short visibilityLimit = reader.getShort("visibilityLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateCovi(id, name, standardNumber, dasId, organId,
				period, stakeNumber, longitude, latitude, coConctLimit,
				visibilityLimit, note, navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2241");
		dto.setMethod("Update_Covi");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Covi", cmd = "2242")
	@RequestMapping("/delete_covi.json")
	public void deleteCovi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteCovi(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2242");
		dto.setMethod("Delete_Covi");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Covi", cmd = "2243")
	@RequestMapping("/get_covi.json")
	public void getCovi(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetCoviVO covi = deviceManager.getCovi(id);
		GetCoviDTO dto = new GetCoviDTO();
		dto.setCovi(covi);
		dto.setCmd("2243");
		dto.setMethod("Get_Covi");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Covi", cmd = "2244")
	@RequestMapping("/list_covi.json")
	public void listCovi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countCovi(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetCoviVO> coviList = deviceManager.listCovi(organId, name,
				standardNumber, stakeNumber, startIndex, limit);

		ListCoviDTO dto = new ListCoviDTO();
		dto.setCmd("2244");
		dto.setCoviList(coviList);
		dto.setMethod("List_Covi");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Push_Button", cmd = "2260")
	@RequestMapping("/create_push_button.json")
	public void createPushButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createPushButton(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2260");
		dto.setMethod("Create_Push_Button");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Push_Button", cmd = "2261")
	@RequestMapping("/update_push_button.json")
	public void updatePushButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("id", false);

		String name = reader.getString("name", true);
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updatePushButton(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2261");
		dto.setMethod("Update_Push_Button");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Push_Button", cmd = "2262")
	@RequestMapping("/delete_push_button.json")
	public void deletePushButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deletePushButton(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2262");
		dto.setMethod("Delete_Push_Button");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Push_Button", cmd = "2263")
	@RequestMapping("/get_push_button.json")
	public void getPushButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetPushButtonVO pushButton = deviceManager.getPushButton(id);

		GetPushButtonDTO dto = new GetPushButtonDTO();
		dto.setPushButton(pushButton);
		dto.setCmd("2263");
		dto.setMethod("Get_Push_Button");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Push_Button", cmd = "2264")
	@RequestMapping("/list_push_button.json")
	public void listPushButton(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countPushButton(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetPushButtonVO> pushButtonList = deviceManager.listPushButton(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListPushButtonDTO dto = new ListPushButtonDTO();
		dto.setCmd("2264");
		dto.setPushButtonList(pushButtonList);
		dto.setMethod("List_Push_Button");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_No_Detector", cmd = "2250")
	@RequestMapping("/create_no_detector.json")
	public void createNoDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short noConctLimit = reader.getShort("noConctLimit", true);

		Short nooConctLimit = reader.getShort("nooConctLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createNoDetector(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude,
				noConctLimit, nooConctLimit, note, navigation, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2250");
		dto.setMethod("Create_No_Detector");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_No_Detector", cmd = "2251")
	@RequestMapping("/update_no_detector.json")
	public void updateNoDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short noConctLimit = reader.getShort("noConctLimit", true);

		Short nooConctLimit = reader.getShort("nooConctLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateNoDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude,
				noConctLimit, nooConctLimit, note, navigation, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2251");
		dto.setMethod("Update_No_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_No_Detector", cmd = "2252")
	@RequestMapping("/delete_no_detector.json")
	public void deleteNoDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteNoDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2252");
		dto.setMethod("Delete_No_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_No_Detector", cmd = "2253")
	@RequestMapping("/get_no_detector.json")
	public void getNoDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetNoDetectorVO noDetector = deviceManager.getNoDetector(id);
		GetNoDetectorDTO dto = new GetNoDetectorDTO();
		dto.setNoDetector(noDetector);
		dto.setCmd("2253");
		dto.setMethod("Get_No_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_No_Detector", cmd = "2254")
	@RequestMapping("/list_no_detector.json")
	public void listNoDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countNoDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetNoDetectorVO> noDetectorList = deviceManager.listNoDetector(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListNoDetectorDTO dto = new ListNoDetectorDTO();
		dto.setCmd("2254");
		dto.setNoDetectorList(noDetectorList);
		dto.setMethod("List_No_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Loli", cmd = "2220")
	@RequestMapping("/create_loli.json")
	public void createLoli(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short loLumiMax = reader.getShort("loLumiMax", true);

		Short loLumiMin = reader.getShort("loLumiMin", true);

		Short liLumiMax = reader.getShort("liLumiMax", true);

		Short liLumiMin = reader.getShort("liLumiMin", true);
		;

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createLoli(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, loLumiMax,
				loLumiMin, liLumiMax, liLumiMin, note, navigation, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2220");
		dto.setMethod("Create_Loli");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Loli", cmd = "2221")
	@RequestMapping("/update_loli.json")
	public void updateLoli(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String standardNumber = reader.getString("id", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short loLumiMax = reader.getShort("loLumiMax", true);

		Short loLumiMin = reader.getShort("loLumiMin", true);

		Short liLumiMax = reader.getShort("liLumiMax", true);

		Short liLumiMin = reader.getShort("liLumiMin", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateLoli(id, name, standardNumber, dasId, organId,
				period, stakeNumber, longitude, latitude, loLumiMax, loLumiMin,
				liLumiMax, liLumiMin, note, navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2221");
		dto.setMethod("Update_Loli");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Loli", cmd = "2222")
	@RequestMapping("/delete_loli.json")
	public void deleteLoli(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteLoli(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2222");
		dto.setMethod("Delete_Loli");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Loli", cmd = "2223")
	@RequestMapping("/get_loli.json")
	public void getLoli(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetLoliVO loli = deviceManager.getLoli(id);
		GetLoliDTO dto = new GetLoliDTO();
		dto.setLoli(loli);
		dto.setCmd("2223");
		dto.setMethod("Get_Loli");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Loli", cmd = "2224")
	@RequestMapping("/list_loli.json")
	public void listLoli(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countLoli(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetLoliVO> loliList = deviceManager.listLoli(organId, name,
				standardNumber, stakeNumber, startIndex, limit);

		ListLoliDTO dto = new ListLoliDTO();
		dto.setCmd("2224");
		dto.setLoliList(loliList);
		dto.setMethod("List_Loli");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Weather_Stat", cmd = "2210")
	@RequestMapping("/create_weather_stat.json")
	public void createWeatherStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short vLowLimit = reader.getShort("vLowLimit", true);

		Short wUpLimit = reader.getShort("wUpLimit", true);

		Short rUpLimit = reader.getShort("rUpLimit", true);

		Short sUpLimit = reader.getShort("sUpLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createWeatherStat(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude,
				vLowLimit, wUpLimit, rUpLimit, sUpLimit, note, navigation,
				reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2210");
		dto.setMethod("Create_Weather_Stat");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Weather_Stat", cmd = "2211")
	@RequestMapping("/update_weather_stat.json")
	public void updateWeatherStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short vLowLimit = reader.getShort("vLowLimit", true);

		Short wUpLimit = reader.getShort("wUpLimit", true);

		Short rUpLimit = reader.getShort("rUpLimit", true);

		Short sUpLimit = reader.getShort("sUpLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateWeatherStat(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, vLowLimit,
				wUpLimit, rUpLimit, sUpLimit, note, navigation, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2211");
		dto.setMethod("Update_Weather_Stat");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Weather_Stat", cmd = "2212")
	@RequestMapping("/delete_weather_stat.json")
	public void deleteWertherStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteWeatherStat(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2212");
		dto.setMethod("Delete_Weather_Stat");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Vehicle_Detector", cmd = "2213")
	@RequestMapping("/get_weather_stat.json")
	public void getWeatherStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetWeatherStatVO wst = deviceManager.getWeatherStat(id);
		GetWeatherStatDTO dto = new GetWeatherStatDTO();
		dto.setWeatherStat(wst);
		dto.setCmd("2213");
		dto.setMethod("Get_Weather_Stat");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Weather_Stat", cmd = "2214")
	@RequestMapping("/list_weather_stat.json")
	public void listWeatherStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countWeatherStat(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetWeatherStatVO> weatherStatList = deviceManager.listWeatherStat(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListWeatherStatDTO dto = new ListWeatherStatDTO();
		dto.setCmd("2214");
		dto.setWeatherStatList(weatherStatList);
		dto.setMethod("List_Weather_Stat");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Vehicle_Detector", cmd = "2200")
	@RequestMapping("/create_vehicle_detector.json")
	public void createVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [standardNumber]");
		}

		String dasId = request.getParameter("dasId");
		if (StringUtils.isBlank(dasId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [dasId]");
		}

		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		Integer period = null;
		String periodString = request.getParameter("period");
		if (StringUtils.isBlank(periodString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [period]");
		} else {
			try {
				period = Integer.parseInt(periodString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter period[" + periodString + "] invalid !");
			}

		}

		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		Integer sUpLimit = null;
		String sUpLimitString = request.getParameter("sUpLimit");
		if (StringUtils.isNotBlank(sUpLimitString)) {
			try {
				sUpLimit = Integer.parseInt(sUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter coConctValue[" + sUpLimitString
								+ "] invalid !");
			}
		}

		Integer sLowLimit = null;
		String sLowLimitString = request.getParameter("sLowLimit");
		if (StringUtils.isNotBlank(sLowLimitString)) {
			try {
				sLowLimit = Integer.parseInt(sLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + sLowLimitString
								+ "] invalid !");
			}
		}

		Integer oUpLimit = null;
		String oUpLimitString = request.getParameter("oUpLimit");
		if (StringUtils.isNotBlank(oUpLimitString)) {
			try {
				oUpLimit = Integer.parseInt(oUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + oUpLimitString
								+ "] invalid !");
			}
		}

		Integer oLowLimit = null;
		String oLowLimitString = request.getParameter("oLowLimit");
		if (StringUtils.isNotBlank(oLowLimitString)) {
			try {
				oLowLimit = Integer.parseInt(oLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + oLowLimitString
								+ "] invalid !");
			}
		}

		Integer vUpLimit = null;
		String vUpLimitString = request.getParameter("vUpLimit");
		if (StringUtils.isNotBlank(vUpLimitString)) {
			try {
				vUpLimit = Integer.parseInt(vUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + vUpLimitString
								+ "] invalid !");
			}
		}

		Integer vLowLimit = null;
		String vLowLimitString = request.getParameter("vLowLimit");
		if (StringUtils.isNotBlank(vLowLimitString)) {
			try {
				vLowLimit = Integer.parseInt(vLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + vLowLimitString
								+ "] invalid !");
			}
		}

		String note = request.getParameter("note");
		String navigation = request.getParameter("navigation");
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		String laneNumber = request.getParameter("laneNumber");
		String reserve = request.getParameter("reserve");
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createVehicleDetector(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude,
				sUpLimit, sLowLimit, oUpLimit, oLowLimit, vUpLimit, vLowLimit,
				note, navigation, ip, port, laneNumber, reserve);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2210");
		dto.setMethod("Create_Weather_Stat");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Vehicle_Detector", cmd = "2201")
	@RequestMapping("/update_vehicle_detector.json")
	public void updateVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String dasId = request.getParameter("dasId");
		String organId = request.getParameter("organId");
		Integer period = null;
		String periodString = request.getParameter("period");
		if (StringUtils.isNotBlank(periodString)) {
			try {
				period = Integer.parseInt(periodString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter period[" + periodString + "] invalid !");
			}
		}
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		Integer sUpLimit = null;
		String sUpLimitString = request.getParameter("sUpLimit");
		if (StringUtils.isNotBlank(sUpLimitString)) {
			try {
				sUpLimit = Integer.parseInt(sUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter coConctValue[" + sUpLimitString
								+ "] invalid !");
			}
		}

		Integer sLowLimit = null;
		String sLowLimitString = request.getParameter("sLowLimit");
		if (StringUtils.isNotBlank(sLowLimitString)) {
			try {
				sLowLimit = Integer.parseInt(sLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + sLowLimitString
								+ "] invalid !");
			}
		}

		Integer oUpLimit = null;
		String oUpLimitString = request.getParameter("oUpLimit");
		if (StringUtils.isNotBlank(oUpLimitString)) {
			try {
				oUpLimit = Integer.parseInt(oUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + oUpLimitString
								+ "] invalid !");
			}
		}

		Integer oLowLimit = null;
		String oLowLimitString = request.getParameter("oLowLimit");
		if (StringUtils.isNotBlank(oLowLimitString)) {
			try {
				oLowLimit = Integer.parseInt(oLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + oLowLimitString
								+ "] invalid !");
			}
		}

		Integer vUpLimit = null;
		String vUpLimitString = request.getParameter("vUpLimit");
		if (StringUtils.isNotBlank(vUpLimitString)) {
			try {
				vUpLimit = Integer.parseInt(vUpLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + vUpLimitString
								+ "] invalid !");
			}
		}

		Integer vLowLimit = null;
		String vLowLimitString = request.getParameter("vLowLimit");
		if (StringUtils.isNotBlank(vLowLimitString)) {
			try {
				vLowLimit = Integer.parseInt(vLowLimitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter visibilityValue[" + vLowLimitString
								+ "] invalid !");
			}
		}

		String note = request.getParameter("note");
		String navigation = request.getParameter("navigation");
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		String laneNumber = request.getParameter("laneNumber");
		String reserve = request.getParameter("reserve");

		deviceManager.updateVehicleDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, sUpLimit,
				sLowLimit, oUpLimit, oLowLimit, vUpLimit, vLowLimit, note,
				navigation, ip, port, laneNumber, reserve);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2201");
		dto.setMethod("Update_Vehicle_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Vehicle_Detector", cmd = "2202")
	@RequestMapping("/delete_vehicle_detector.json")
	public void deleteVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteVehicleDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2202");
		dto.setMethod("Delete_Vehicle_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Vehicle_Detector", cmd = "2203")
	@RequestMapping("/get_vehicle_detector.json")
	public void getVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetVehicleDetectorVO vd = deviceManager.getVehicleDetector(id);
		GetVehicleDetectorDTO dto = new GetVehicleDetectorDTO();
		dto.setVehicleDetector(vd);
		dto.setCmd("2203");
		dto.setMethod("Get_Vehicle_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Vehicle_Detector", cmd = "2204")
	@RequestMapping("/list_vehicle_detector.json")
	public void listVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countVehicleDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetVehicleDetectorVO> vehicleDetectorList = deviceManager
				.listVehicleDetector(organId, name, standardNumber,
						stakeNumber, startIndex, limit);

		ListVehicleDetectorDTO dto = new ListVehicleDetectorDTO();
		dto.setCmd("2204");
		dto.setVehicleDetectorList(vehicleDetectorList);
		dto.setMethod("List_Vehicle_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Wind_Speed", cmd = "2190")
	@RequestMapping("/create_wind_speed.json")
	public void createWindSpeed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short direction = reader.getShort("direction", true);

		Integer wUpLimit = reader.getInteger("wUpLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createWindSpeed(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, direction,
				wUpLimit, note, navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2190");
		dto.setMethod("Create_Wind_Speed");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Wind_Speed", cmd = "2191")
	@RequestMapping("/update_wind_speed.json")
	public void updateWindSpeed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short direction = reader.getShort("direction", true);

		Integer wUpLimit = reader.getInteger("wUpLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateWindSpeed(id, name, standardNumber, dasId, organId,
				period, stakeNumber, longitude, latitude, direction, wUpLimit,
				note, navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2191");
		dto.setMethod("Update_Wind_Speed");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Wind_Speed", cmd = "2192")
	@RequestMapping("/delete_wind_speed.json")
	public void deleteWindSpeed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteWindSpeed(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2192");
		dto.setMethod("Delete_Wind_Speed");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Wind_Speed", cmd = "2193")
	@RequestMapping("/get_wind_speed.json")
	public void getWindSpeed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetWindSpeedVO ws = deviceManager.getWindSpeed(id);
		GetWindSpeedDTO dto = new GetWindSpeedDTO();
		dto.setWindSpeed(ws);
		dto.setCmd("2193");
		dto.setMethod("Get_Wind_Speed");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Wind_Speed", cmd = "2194")
	@RequestMapping("/list_wind_speed.json")
	public void listWindSpeed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countWindSpeed(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetWindSpeedVO> windSpeedList = deviceManager.listWindSpeed(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListWindSpeedDTO dto = new ListWindSpeedDTO();
		dto.setCmd("2194");
		dto.setWindSpeedList(windSpeedList);
		dto.setMethod("List_Wind_Speed");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Control_Device", cmd = "2270")
	@RequestMapping("/create_control_device.json")
	public void createControlDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("stakeNumber", true);

		String latitude = reader.getString("stakeNumber", true);

		Short type = reader.getShort("type", true);

		Short subType = reader.getShort("subType", true);

		// if (type == TypeDefinition.DEVICE_TYPE_CMS && subType == 4) {
		// type = TypeDefinition.DEVICE_TYPE_TSL;
		// subType = null;
		// } else if (type == TypeDefinition.DEVICE_TYPE_CMS && subType == 6) {
		// type = TypeDefinition.DEVICE_TYPE_LIL;
		// subType = null;
		// }

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer height = reader.getInteger("height", true);

		Integer width = reader.getInteger("width", true);

		Short sectionType = reader.getShort("sectionType", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createControlDevice(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, type,
				subType, note, navigation, height, width, sectionType, reserve,
				ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2270");
		dto.setMethod("Create_Control_Device");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Control_Device", cmd = "2271")
	@RequestMapping("/update_control_device.json")
	public void updateControlDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Short type = reader.getShort("type", true);

		Short subType = reader.getShort("subType", true);
		// if (type != null && subType != null) {
		// if (type == TypeDefinition.DEVICE_TYPE_CMS && subType == 4) {
		// type = TypeDefinition.DEVICE_TYPE_TSL;
		// subType = null;
		// } else if (type == TypeDefinition.DEVICE_TYPE_CMS && subType == 6) {
		// type = TypeDefinition.DEVICE_TYPE_LIL;
		// subType = null;
		// }
		// }
		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer height = reader.getInteger("height", true);

		Integer width = reader.getInteger("width", true);

		Short sectionType = reader.getShort("sectionType", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateControlDevice(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, type,
				subType, note, navigation, height, width, sectionType, reserve,
				ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2271");
		dto.setMethod("Update_Control_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Control_Device", cmd = "2272")
	@RequestMapping("/delete_control_device.json")
	public void deleteControlDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing[type]");
		}

		deviceManager.deleteControlDevice(id, type);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2272");
		dto.setMethod("Delete_Control_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Control_Device", cmd = "2273")
	@RequestMapping("/get_control_device.json")
	public void getControlDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetControlDeviceVO cd = deviceManager.getControlDevice(id);
		GetControlDeviceDTO dto = new GetControlDeviceDTO();
		dto.setControlDevice(cd);
		dto.setCmd("2273");
		dto.setMethod("Get_Control_Device");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Control_Device", cmd = "2274")
	@RequestMapping("/list_control_device.json")
	public void listControlDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Short type = null;
		String typeString = request.getParameter("type");
		if (StringUtils.isNotBlank(typeString)) {
			try {
				type = Short.parseShort(typeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter coConctValue[" + typeString + "] invalid !");
			}
		}

		Short subType = null;
		String subTypeString = request.getParameter("subType");
		if (StringUtils.isNotBlank(subTypeString)) {
			try {
				subType = Short.parseShort(subTypeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter coConctValue[" + subTypeString
								+ "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countControlDevice(organId, name,
				standardNumber, stakeNumber, type, subType);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetControlDeviceVO> controlDeviceList = deviceManager
				.listControlDevice(organId, name, standardNumber, stakeNumber,
						startIndex, limit, type, subType);

		ListControlDeviceDTO dto = new ListControlDeviceDTO();
		dto.setCmd("2274");
		dto.setControlDeviceList(controlDeviceList);
		dto.setMethod("List_Control_Device");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Type_Definition", cmd = "1250")
	@RequestMapping("/get_type_definition.xml")
	public void getTypeDefinition(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer type = reader.getInteger("type", true);

		List<TypeDef> list = tmDeviceManager.listTypeDef(type);

		// 获取所有的情报板类型，按照宽度和高度规格划分，SGC客户端特殊需求
		Set<String> cmsSizes = tmDeviceManager.listCmsSize();

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1250");
		dto.setMethod("Get_Type_Definition");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (TypeDef typeDef : list) {
			// 情报板类型单独处理
			if (typeDef.getType().intValue() == TypeDefinition.DEVICE_TYPE_CMS) {
				continue;
			}
			if (typeDef.getSubType() == null) {
				Element element = new Element("TypeDefinition");
				element.setAttribute("Name", MyStringUtil
						.object2StringNotNull(typeDef.getTypeName()));
				element.setAttribute("Type",
						MyStringUtil.object2StringNotNull(typeDef.getType()));
				root.addContent(element);
				// 子类型节点
				for (TypeDef subType : list) {
					if (subType.getType().intValue() == typeDef.getType()
							.intValue()) {
						if (subType.getSubType() != null) {
							Element item = new Element("Item");
							item.setAttribute("Name", MyStringUtil
									.object2StringNotNull(subType
											.getSubTypeName()));
							item.setAttribute("SubType", MyStringUtil
									.object2StringNotNull(subType.getSubType()));
							element.addContent(item);
						}
					} else {
						continue;
					}
				}
			}
			continue;
		}
		// 情报板单独处理，根据宽度*高度来返回类型
		Element element = new Element("TypeDefinition");
		element.setAttribute("Name", "情报板");
		element.setAttribute("Type", TypeDefinition.DEVICE_TYPE_CMS + "");
		root.addContent(element);
		for (String cmsSize : cmsSizes) {
			Element item = new Element("Item");
			item.setAttribute("Name", "情报板(" + cmsSize + ")");
			item.setAttribute("SubType", cmsSize);
			element.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Cms_Playlist", cmd = "1251")
	@RequestMapping("/save_cms_playlist.xml")
	public void saveCmsPlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String folderId = requestRoot.getChildText("Folder");
		if (StringUtils.isBlank(folderId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Folder]");
		}

		Element playlist = requestRoot.getChild("Playlist");
		String playlistId = playlist.getAttributeValue("Id");
		String playlistName = playlist.getAttributeValue("Name");
		if (StringUtils.isBlank(playlistName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing Playlist[Name]");
		}

		// Short type = ElementUtil.getShort(playlist, "Type");
		// if (null == type) {
		// type = Short.valueOf((short) 1);
		// }
		String type = playlist.getAttributeValue("Type");

		Playlist record = tmDeviceManager.savePlaylist(folderId, playlistId,
				playlistName, null, playlist.getChildren(), type);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1251");
		dto.setMethod("Save_Cms_Playlist");
		dto.setMessage(record.getId());
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element playlistElement = ElementUtil.createElement("Playlist", record,
				null, null);
		root.addContent(playlistElement);

		for (PlayItem item : record.getItems()) {
			Element playItem = new Element("Item");
			playItem.setAttribute("Id", item.getId());
			playItem.setAttribute("Content",
					MyStringUtil.object2StringNotNull(item.getContent()));
			playItem.setAttribute("Color",
					MyStringUtil.object2StringNotNull(item.getColor()));
			playItem.setAttribute("Font",
					MyStringUtil.object2StringNotNull(item.getFont()));
			playItem.setAttribute("Size",
					MyStringUtil.object2StringNotNull(item.getSize()));
			playItem.setAttribute("Space",
					MyStringUtil.object2StringNotNull(item.getWordSpace()));
			playItem.setAttribute("Duration",
					MyStringUtil.object2StringNotNull(item.getDuration()));
			playItem.setAttribute("X",
					MyStringUtil.object2StringNotNull(item.getX()));
			playItem.setAttribute("Y",
					MyStringUtil.object2StringNotNull(item.getY()));
			playlistElement.addContent(playItem);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Cms_Playlist", cmd = "1252")
	@RequestMapping("/delete_cms_playlist.xml")
	public void deleteCmsPlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String playlistId = reader.getString("playlistId", false);

		tmDeviceManager.deletePlaylist(playlistId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1252");
		dto.setMethod("Delete_Cms_Playlist");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Cms_Playlist", cmd = "1253")
	@RequestMapping("/list_cms_playlist.xml")
	public void listCmsPlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String folderId = reader.getString("folderId", false);

		Short type = reader.getShort("type", true);

		List<Playlist> list = tmDeviceManager.listPlaylist(folderId, type);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1253");
		dto.setMethod("List_Cms_Playlist");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (Playlist playlist : list) {
			Element playlistElement = ElementUtil.createElement("Playlist",
					playlist, null, null);
			// 覆盖Type属性采用新的cmsSize属性
			playlistElement.setAttribute("Type",
					MyStringUtil.object2StringNotNull(playlist.getCmsSize()));
			root.addContent(playlistElement);

			for (PlayItem item : playlist.getItems()) {
				Element playItem = new Element("Item");
				playItem.setAttribute("Id", item.getId());
				playItem.setAttribute("Content",
						MyStringUtil.object2StringNotNull(item.getContent()));
				playItem.setAttribute("Color",
						MyStringUtil.object2StringNotNull(item.getColor()));
				playItem.setAttribute("Font",
						MyStringUtil.object2StringNotNull(item.getFont()));
				playItem.setAttribute("Size",
						MyStringUtil.object2StringNotNull(item.getSize()));
				playItem.setAttribute("Space",
						MyStringUtil.object2StringNotNull(item.getWordSpace()));
				playItem.setAttribute("Duration",
						MyStringUtil.object2StringNotNull(item.getDuration()));
				playItem.setAttribute("X",
						MyStringUtil.object2StringNotNull(item.getX()));
				playItem.setAttribute("Y",
						MyStringUtil.object2StringNotNull(item.getY()));
				playItem.setAttribute("Type",
						MyStringUtil.object2StringNotNull(item.getType()));
				playlistElement.addContent(playItem);
			}
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Playlist_Folder", cmd = "1254")
	@RequestMapping("/list_playlist_folder.xml")
	public void listPlaylistFolder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer subType = reader.getInteger("subType", true);

		List<PlaylistFolder> list = tmDeviceManager.listFolder(subType);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1254");
		dto.setMethod("List_Playlist_Folder");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		List<String> excludeProperties = new LinkedList<String>();
		excludeProperties.add("subtype");

		for (PlaylistFolder folder : list) {
			Element e = ElementUtil.createElement("Folder", folder, null,
					excludeProperties);
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Playlist_Folder", cmd = "1255")
	@RequestMapping("/create_playlist_folder.xml")
	public void createPlaylistFolder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String folderName = requestRoot.getChildText("FolderName");
		if (StringUtils.isBlank(folderName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [FolderName]");
		}

		// Integer subType = null;
		// String subTypeString = requestRoot.getChildText("SubType");
		// if (StringUtils.isBlank(subTypeString)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [SubType]");
		// } else {
		// try {
		// subType = Integer.valueOf(subTypeString);
		// } catch (NumberFormatException e) {
		// e.printStackTrace();
		// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
		// "Parameter SubType[" + subTypeString + "] invalid !");
		// }
		// }

		String id = tmDeviceManager.createFolder(folderName, null);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1255");
		dto.setMethod("Create_Playlist_Folder");
		dto.setMessage(id);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Playlist_Folder", cmd = "1256")
	@RequestMapping("/delete_playlist_folder.xml")
	public void deletePlaylistFolder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String folderId = reader.getString("folderId", false);

		tmDeviceManager.deleteFolder(folderId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1256");
		dto.setMethod("Delete_Playlist_Folder");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Type", cmd = "1250")
	@RequestMapping("/list_device_type.json")
	public void listDeviceType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer type = reader.getInteger("type", true);

		List<GetTypeDefVO> list = tmDeviceManager.listDeviceType(type);

		ListDeviceTypeDTO dto = new ListDeviceTypeDTO();
		dto.setCmd("1250");
		dto.setMethod("List_Device_Type");
		dto.setTypeDefs(list);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Box_Transformer", cmd = "2400")
	@RequestMapping("/create_box_transformer.json")
	public void createBoxTransformer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String maxVoltage = reader.getString("maxVoltage", true);

		String maxCurrents = reader.getString("maxCurrents", true);

		String maxCapacity = reader.getString("maxCapacity", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createBoxTransformer(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, note,
				navigation, maxVoltage, maxCurrents, maxCapacity, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2400");
		dto.setMethod("Create_Box_Transformer");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Box_Transformer", cmd = "2401")
	@RequestMapping("/update_box_transformer.json")
	public void updateBoxTransformer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String maxVoltage = reader.getString("maxVoltage", true);

		String maxCurrents = reader.getString("maxCurrents", true);

		String maxCapacity = reader.getString("maxCapacity", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		deviceManager.updateBoxTransformer(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, maxVoltage, maxCurrents, maxCapacity, reserve, ip,
				port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2401");
		dto.setMethod("Update_Box_Transformer");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Box_Transformer", cmd = "2402")
	@RequestMapping("/delete_box_transformer.json")
	public void deleteBoxTransformer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		deviceManager.deleteBoxTransformer(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2402");
		dto.setMethod("Delete_Box_Transformer");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Box_Transformer", cmd = "2403")
	@RequestMapping("/get_box_transformer.json")
	public void getBoxTransformer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetBoxTransformerVO boxTransformer = deviceManager
				.getBoxTransformer(id);
		GetBoxTransformerDTO dto = new GetBoxTransformerDTO();
		dto.setBoxTransformer(boxTransformer);
		dto.setCmd("2403");
		dto.setMethod("Get_Box_Transformer");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Box_Transformer", cmd = "2404")
	@RequestMapping("/list_box_transformer.json")
	public void listBoxTransformer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.countBoxTransformer(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetBoxTransformerVO> btList = deviceManager.listBoxTransformer(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListBoxTransformerDTO dto = new ListBoxTransformerDTO();
		dto.setCmd("2404");
		dto.setBoxTransformerList(btList);
		dto.setMethod("List_Box_Transformer");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Vi_Detector", cmd = "2410")
	@RequestMapping("/create_vi_detector.json")
	public void createViDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer visibilityLimit = reader.getInteger("visibilityLimit", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createViDetector(name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, visibilityLimit, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2410");
		dto.setMethod("Create_Vi_Detector");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Vi_Detector", cmd = "2411")
	@RequestMapping("/update_vi_detector.json")
	public void updateViDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer visibilityLimit = reader.getInteger("visibilityLimit", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		deviceManager.updateViDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, visibilityLimit, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2411");
		dto.setMethod("Update_Vi_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Vi_Detector", cmd = "2412")
	@RequestMapping("/delete_vi_detector.json")
	public void deleteViDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		deviceManager.deleteViDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2412");
		dto.setMethod("Delete_Vi_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Vi_Detector", cmd = "2413")
	@RequestMapping("/get_vi_detector.json")
	public void getViDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetViDetectorVO viDetector = deviceManager.getViDetector(id);
		GetViDetectorDTO dto = new GetViDetectorDTO();
		dto.setViDetector(viDetector);
		dto.setCmd("2413");
		dto.setMethod("Get_Vi_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Vi_Detector", cmd = "2414")
	@RequestMapping("/list_vi_detector.json")
	public void listViDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.countViDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetViDetectorVO> vdList = deviceManager.listViDetector(organId,
				name, standardNumber, stakeNumber, startIndex, limit);

		ListViDetectorDTO dto = new ListViDetectorDTO();
		dto.setCmd("2414");
		dto.setViDetectorList(vdList);
		dto.setMethod("List_Vi_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Road_Detector", cmd = "2420")
	@RequestMapping("/create_road_detector.json")
	public void createRoadDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer roadTemperature = reader.getInteger("roadTemperature", true);

		Integer waterThickness = reader.getInteger("waterThickness", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createRoadDetector(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, note,
				navigation, roadTemperature, waterThickness, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2420");
		dto.setMethod("Create_Road_Detector");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Road_Detector", cmd = "2421")
	@RequestMapping("/update_road_detector.json")
	public void updateRoadDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer roadTemperature = reader.getInteger("roadTemperature", true);

		Integer waterThickness = reader.getInteger("waterThickness", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateRoadDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, roadTemperature, waterThickness, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2421");
		dto.setMethod("Update_Road_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Road_Detector", cmd = "2422")
	@RequestMapping("/delete_road_detector.json")
	public void deleteRoadDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		deviceManager.deleteRoadDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2422");
		dto.setMethod("Delete_Road_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Road_Detector", cmd = "2423")
	@RequestMapping("/get_road_detector.json")
	public void getRoadDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetRoadDetectorVO roadDetector = deviceManager.getRoadDetector(id);
		GetRoadDetectorDTO dto = new GetRoadDetectorDTO();
		dto.setRoadDetector(roadDetector);
		dto.setCmd("2423");
		dto.setMethod("Get_Road_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Road_Detector", cmd = "2424")
	@RequestMapping("/list_road_detector.json")
	public void listRoadDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.countRoadDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetRoadDetectorVO> roadDetectorList = deviceManager
				.listRoadDetector(organId, name, standardNumber, stakeNumber,
						startIndex, limit);

		ListRoadDetectorDTO dto = new ListRoadDetectorDTO();
		dto.setCmd("2424");
		dto.setRoadDetectorList(roadDetectorList);
		dto.setMethod("List_Road_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Bridge_Detector", cmd = "2430")
	@RequestMapping("/create_bridge_detector.json")
	public void createBridgeDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer bridgeTemperature = reader
				.getInteger("bridgeTemperature", true);

		Integer saltConcentration = reader
				.getInteger("saltConcentration", true);

		Integer mist = reader.getInteger("mist", true);

		Integer freezeTemperature = reader
				.getInteger("freezeTemperature", true);
		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createBridgeDetector(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, note,
				navigation, bridgeTemperature, saltConcentration, mist,
				freezeTemperature, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2430");
		dto.setMethod("Create_Bridge_Detector");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Bridge_Detector", cmd = "2431")
	@RequestMapping("/update_bridge_detector.json")
	public void updateBridgeDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		Integer bridgeTemperature = reader
				.getInteger("bridgeTemperature", true);

		Integer saltConcentration = reader
				.getInteger("saltConcentration", true);

		Integer mist = reader.getInteger("mist", true);

		Integer freezeTemperature = reader
				.getInteger("freezeTemperature", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		deviceManager.updateBridgeDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, bridgeTemperature, saltConcentration, mist,
				freezeTemperature, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2431");
		dto.setMethod("Update_Bridge_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Bridge_Detector", cmd = "2432")
	@RequestMapping("/delete_bridge_detector.json")
	public void deleteBridgeDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		deviceManager.deleteBridgeDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2432");
		dto.setMethod("Delete_Bridge_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Bridge_Detector", cmd = "2433")
	@RequestMapping("/get_bridge_detector.json")
	public void getBridgeDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetBridgeDetectorVO bd = deviceManager.getBridgeDetector(id);
		GetBridgeDetectirDTO dto = new GetBridgeDetectirDTO();
		dto.setBridgeDetector(bd);
		dto.setCmd("2433");
		dto.setMethod("Get_Bridge_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Bridge_Detector", cmd = "2434")
	@RequestMapping("/list_bridge_detector.json")
	public void listBridgeDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.countBridgeDetector(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetBridgeDetectorVO> bridgeDetectorList = deviceManager
				.listBridgeDetector(organId, name, standardNumber, stakeNumber,
						startIndex, limit);

		ListBridgeDetectorDTO dto = new ListBridgeDetectorDTO();
		dto.setCmd("2434");
		dto.setBridgeDetectorList(bridgeDetectorList);
		dto.setMethod("List_Bridge_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Sub_Vehicle_Detector", cmd = "2200")
	@RequestMapping("/create_sub_vehicle_detector.json")
	public void createSubVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", false);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Integer sUpLimit = reader.getInteger("sUpLimit", true);

		Integer sLowLimit = reader.getInteger("sLowLimit", true);

		Integer oUpLimit = reader.getInteger("oUpLimit", true);

		Integer oLowLimit = reader.getInteger("oLowLimit", true);

		Integer vUpLimit = reader.getInteger("vUpLimit", true);

		Integer vLowLimit = reader.getInteger("vLowLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String ip = reader.getString("ip", true);

		String port = reader.getString("port", true);

		String laneNumber = reader.getString("laneNumber", true);

		String parentId = reader.getString("parentId", false);
		// 校验License
		// License license = licenseManager.getLicense();
		// int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		// int deviceCount = tmDeviceManager.countDeviceAmount();
		// if (deviceCount >= deviceAmount) {
		// throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
		// "Device amount over license limit !");
		// }
		String reserve = reader.getString("reserve", true);
		String id = deviceManager.createSubVehicleDetector(name,
				standardNumber, dasId, organId, period, stakeNumber, longitude,
				latitude, sUpLimit, sLowLimit, oUpLimit, oLowLimit, vUpLimit,
				vLowLimit, note, navigation, ip, port, laneNumber, parentId,
				reserve);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2200");
		dto.setMethod("Create_Sub_Vehicle_Detector");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Sub_Vehicle_Detector", cmd = "2201")
	@RequestMapping("/update_sub_vehicle_detector.json")
	public void updateSubVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		Integer sUpLimit = reader.getInteger("sUpLimit", true);

		Integer sLowLimit = reader.getInteger("sLowLimit", true);

		Integer oUpLimit = reader.getInteger("oUpLimit", true);

		Integer oLowLimit = reader.getInteger("oLowLimit", true);

		Integer vUpLimit = reader.getInteger("vUpLimit", true);

		Integer vLowLimit = reader.getInteger("vLowLimit", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String ip = reader.getString("ip", true);

		String port = reader.getString("port", true);

		String laneNumber = reader.getString("laneNumber", true);

		String parentId = reader.getString("parentId", true);

		String reserve = reader.getString("reserve", true);

		deviceManager.updateSubVehicleDetector(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, sUpLimit,
				sLowLimit, oUpLimit, oLowLimit, vUpLimit, vLowLimit, note,
				navigation, ip, port, laneNumber, parentId, reserve);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2201");
		dto.setMethod("Update_Sub_Vehicle_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Sub_Vehicle_Detector", cmd = "2202")
	@RequestMapping("/delete_sub_vehicle_detector.json")
	public void deleteSubVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteSubVehicleDetector(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2202");
		dto.setMethod("Delete_Sub_Vehicle_Detector");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Sub_Vehicle_Detector", cmd = "2203")
	@RequestMapping("/get_sub_vehicle_detector.json")
	public void getSubVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetVehicleDetectorVO vd = deviceManager.getSubVehicleDetector(id);
		GetVehicleDetectorDTO dto = new GetVehicleDetectorDTO();
		dto.setVehicleDetector(vd);
		dto.setCmd("2203");
		dto.setMethod("Get_Vehicle_Detector");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Sub_Vehicle_Detector", cmd = "2204")
	@RequestMapping("/list_sub_vehicle_detector.json")
	public void listSubVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String parentId = reader.getString("parentId", false);

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.countSubVehicleDetector(parentId);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetVehicleDetectorVO> vehicleDetectorList = deviceManager
				.listSubVehicleDetector(parentId, startIndex, limit);

		ListVehicleDetectorDTO dto = new ListVehicleDetectorDTO();
		dto.setCmd("2204");
		dto.setVehicleDetectorList(vehicleDetectorList);
		dto.setMethod("List_Sub_Vehicle_Detector");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Urgent_Phone", cmd = "2440")
	@RequestMapping("/create_urgent_phone.json")
	public void createUrgentPhone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = reader.getString("standardNumber", false);

		String dasId = reader.getString("dasId", false);

		String organId = reader.getString("organId", false);

		Integer period = reader.getInteger("period", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);
		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		String id = deviceManager.createUrgentPhone(name, standardNumber,
				dasId, organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2440");
		dto.setMethod("Create_Urgent_Phone");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Urgent_Phone", cmd = "2441")
	@RequestMapping("/update_urgent_phone.json")
	public void updateUrgentPhone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String dasId = reader.getString("dasId", true);

		String organId = reader.getString("organId", true);

		Integer period = reader.getInteger("period", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String note = reader.getString("note", true);

		String navigation = reader.getString("navigation", true);

		String reserve = reader.getString("reserve", true);

		String ip = reader.getString("ip", true);

		Integer port = reader.getInteger("port", true);

		deviceManager.updateUrgentPhone(id, name, standardNumber, dasId,
				organId, period, stakeNumber, longitude, latitude, note,
				navigation, reserve, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2441");
		dto.setMethod("Update_Urgent_Phone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Urgent_Phone", cmd = "2442")
	@RequestMapping("/delete_urgent_phone.json")
	public void deleteUrgentPhone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteUrgentPhone(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2442");
		dto.setMethod("Delete_Urgent_Phone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Urgent_Phone", cmd = "2443")
	@RequestMapping("/get_urgent_phone.json")
	public void getUrgentPhone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetUrgentPhoneVO urgentPhone = deviceManager.getUrgentPhone(id);

		GetUrgentPhoneDTO dto = new GetUrgentPhoneDTO();
		dto.setUrgentPhone(urgentPhone);
		dto.setCmd("2443");
		dto.setMethod("Get_Urgent_Phone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Urgent_Phone", cmd = "2444")
	@RequestMapping("/list_urgent_phone.json")
	public void listUrgentPhone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = request.getParameter("standardNumber");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = 0;
		String start = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(start)) {
			try {
				startIndex = Integer.parseInt(start);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + start + "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.countUrgentPhone(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetUrgentPhoneVO> urgentPhoneList = deviceManager.listUrgentPhone(
				organId, name, standardNumber, stakeNumber, startIndex, limit);

		ListUrgentPhoneDTO dto = new ListUrgentPhoneDTO();
		dto.setCmd("2444");
		dto.setUrgentPhoneList(urgentPhoneList);
		dto.setMethod("List_Urgent_Phone");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "Save_Vms_Command", cmd = "1015")
	@RequestMapping("/save_vms_command.xml")
	public void saveVmsCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Image image = null;
		// 检查是否文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 登陆的用户
			ResourceVO resource = null;
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// 图片数据流
			List<InputStream> ins = new LinkedList<InputStream>();
			// 解析请求
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String fieldName = item.getFieldName();

				// 简单文本参数部分，得到sessionId
				if ("sessionId".equals(fieldName)) {
					String sessionId = item.getString();
					if (StringUtils.isBlank(sessionId)) {
						throw new BusinessException(
								ErrorCode.PARAMETER_NOT_FOUND,
								"missing [sessionId]");
					}
					// 验证sessionId
					resource = userManager.checkSession(sessionId);
				}
				// 文件流部分
				if (fieldName.startsWith("Filedata")) {
					uploadFlag = true;
					InputStream in = item.getInputStream();
					ins.add(in);
				}
			}
			if (!uploadFlag) {
				throw new BusinessException(
						ErrorCode.MISSING_PARAMETER_FILEDATA,
						"Parameter [Filedata] not found !");
			}
			// 保存情报板发布信令到数据库
			List<String> ids = tmDeviceManager.saveVmsCommand(ins);

			BaseDTO dto = new BaseDTO();
			dto.setCmd("1015");
			dto.setMethod("Save_Vms_Command");
			Document doc = new Document();
			Element root = ElementUtil.createElement("Response", dto);
			doc.setRootElement(root);

			int seq = 0;
			for (String id : ids) {
				Element command = new Element("Command");
				command.setAttribute("Id", id);
				command.setAttribute("Seq", seq++ + "");
				root.addContent(command);
			}

			writePageWithContentLength(response, doc);
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}
	}

	@InterfaceDescription(logon = false, method = "Get_Vms_Command", cmd = "3017")
	@RequestMapping("/get_vms_command.xml")
	public void getVmsCommand(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element reqRoot = requestDoc.getRootElement();
		Element e = reqRoot.getChild("CommandId");
		if (null == e) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [CommandId]");
		}

		String commandId = e.getText();
		if (StringUtils.isBlank(commandId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [CommandId]");
		}

		CmsCommand command = tmDeviceManager.getCmsCommand(commandId);
		Blob blob = command.getContent();
		long length = blob.length();
		if (length >= Integer.MAX_VALUE) {
			throw new BusinessException(ErrorCode.ERROR,
					"Image length too long, can not encode by BASE64 encoding !");
		}
		int intLength = (int) length;

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3017");
		dto.setMethod("Get_Vms_Command");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		Element commandElement = new Element("Command");
		commandElement.setText(new String(Base64.encodeBase64(command
				.getContent().getBytes(1, intLength)), "utf8"));
		root.addContent(commandElement);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_GPS_Device", cmd = "2450")
	@RequestMapping("/create_gps_device.json")
	public void createGPSDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		Integer port = reader.getInteger("port", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		String navigation = reader.getString("navigation", true);
		String organId = reader.getString("organId", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String location = reader.getString("location", true);
		String note = reader.getString("note", true);
		String manufacturerId = reader.getString("manufacturerId", true);
		String deviceModelId = reader.getString("deviceModelId", true);
		String ccsId = reader.getString("ccsId", true);
		String dasId = reader.getString("dasId", true);
		String lanIp = reader.getString("lanIp", true);
		String wanIp = reader.getString("wanIp", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		// 自动生成标准号
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("GPSDevice",
					organId);
		}

		String id = deviceManager.createGPSDevice(name, port, standardNumber,
				location, dasId, deviceModelId, manufacturerId, ccsId, lanIp,
				wanIp, organId, stakeNumber, longitude, latitude, note,
				navigation);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2450");
		dto.setMethod("Create_GPS_Device");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_GPS_Device", cmd = "2451")
	@RequestMapping("/update_gps_device.json")
	public void updateGPSDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);
		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		Integer port = reader.getInteger("port", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		String navigation = reader.getString("navigation", true);
		String organId = reader.getString("organId", true);
		String longitude = reader.getString("longitude", true);
		String latitude = reader.getString("latitude", true);
		String location = reader.getString("location", true);
		String note = reader.getString("note", true);
		String manufacturerId = reader.getString("manufacturerId", true);
		String deviceModelId = reader.getString("deviceModelId", true);
		String ccsId = reader.getString("ccsId", true);
		String dasId = reader.getString("dasId", true);
		String lanIp = reader.getString("lanIp", true);
		String wanIp = reader.getString("wanIp", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		deviceManager.updateGPSDevice(id, name, port, standardNumber, location,
				dasId, deviceModelId, manufacturerId, ccsId, lanIp, wanIp,
				organId, stakeNumber, longitude, latitude, note, navigation);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2451");
		dto.setMethod("Update_GPS_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_GPS_Device", cmd = "2452")
	@RequestMapping("/delete_gps_device.json")
	public void deleteGPSDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		deviceManager.deleteGPSDevice(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2452");
		dto.setMethod("Delete_GPS_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_GPS_Device", cmd = "2453")
	@RequestMapping("/get_gps_device.json")
	public void getGPSDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		DeviceGPSVO gpsDevice = deviceManager.getGPSDevice(id);

		GetGPSDeviceDTO dto = new GetGPSDeviceDTO();
		dto.setGpsDevice(gpsDevice);
		dto.setCmd("2453");
		dto.setMethod("Get_GPS_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_GPS_Device", cmd = "2454")
	@RequestMapping("/list_gps_device.json")
	public void listGPSDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		Integer startIndex = reader.getInteger("startIndex", false);
		Integer limit = reader.getInteger("limit", false);

		Integer totalCount = deviceManager.countGPSDevice(organId, name,
				standardNumber, stakeNumber);

		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<DeviceGPSVO> gpsDeviceList = deviceManager.listGPSDevice(organId,
				name, standardNumber, stakeNumber, startIndex, limit);

		ListGPSDeviceDTO dto = new ListGPSDeviceDTO();
		dto.setCmd("2454");
		dto.setGpsDeviceList(gpsDeviceList);
		dto.setMethod("List_GPS_Device");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_Device_GPS", cmd = "2455")
	@RequestMapping("/bind_device_gps.json")
	public void bindDeviceSolar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String type = reader.getString("type", false);
		String gpsId = reader.getString("gpsId", false);
		String json = reader.getString("json", false);
		deviceManager.bindDeviceGPS(type, gpsId, json);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2455");
		dto.setMethod("Bind_Device_GPS");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_GPS_Bind_Device", cmd = "2456")
	@RequestMapping("/list_gps_bind_device.json")
	public void listSolarBindDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String gpsId = request.getParameter("gpsId");
		if (StringUtils.isBlank(gpsId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [gpsId]");
		}

		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [type]");
		}

		String name = request.getParameter("name");

		int startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}

		int limit = 10;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		List<DevicePermissionVO> deviceList = null;
		int totalCount = 0;
		if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(type)) {
			totalCount = deviceManager.countOrganCamera(organId, gpsId, name);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount != 0) {
				if (startIndex >= totalCount) {
					startIndex -= ((startIndex - totalCount) / limit + 1)
							* limit;
				}
			}
			deviceList = deviceManager.listOrganCameraByGPS(organId, gpsId,
					name, startIndex, limit);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

		ListGPSPrivilegeDTO dto = new ListGPSPrivilegeDTO();
		dto.setCmd("2456");
		dto.setMethod("List_GPS_Bind_Device");
		dto.setTotalCount(totalCount + "");
		dto.setGpsDevices(deviceList);
		writePage(response, dto);

	}

	@InterfaceDescription(logon = true, method = "Remove_GPS_Device", cmd = "2457")
	@RequestMapping("/remove_gps_device.json")
	public void removeSolarDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String gpsId = request.getParameter("gpsId");
		if (StringUtils.isBlank(gpsId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [gpsId]");
		}

		deviceManager.removeGPSDevice(gpsId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2457");
		dto.setMethod("Remove_GPS_Device");
		writePage(response, dto);
	}
}
