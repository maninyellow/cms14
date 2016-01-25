package com.znsx.cms.web.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.DeviceAlarmReal;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.Manufacturer;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.VideoDeviceProperty;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.LicenseManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.TmDeviceManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.DeviceAlarmStatusVO;
import com.znsx.cms.service.model.DeviceAlarmVO;
import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.service.model.DeviceOnlineHistroyVO;
import com.znsx.cms.service.model.DevicePermissionVO;
import com.znsx.cms.service.model.DvrVO;
import com.znsx.cms.service.model.GetCameraVO;
import com.znsx.cms.service.model.GetDvrVO;
import com.znsx.cms.service.model.GetSolarBatteryVO;
import com.znsx.cms.service.model.ListCameraVO;
import com.znsx.cms.service.model.ListDeviceAlarmVO;
import com.znsx.cms.service.model.ListOrganDeviceTreeVO;
import com.znsx.cms.service.model.OrganDeviceCheck;
import com.znsx.cms.service.model.OrganDeviceOnline;
import com.znsx.cms.service.model.PresetVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.TopRealPlayLog;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.cs.ListDeviceModelDTO;
import com.znsx.cms.web.dto.cs.ListManufacturerDTO;
import com.znsx.cms.web.dto.omc.CountDeviceDTO;
import com.znsx.cms.web.dto.omc.DeviceAlarmDTO;
import com.znsx.cms.web.dto.omc.DeviceOnlineHistroyDTO;
import com.znsx.cms.web.dto.omc.GetCameraDTO;
import com.znsx.cms.web.dto.omc.GetDeviceModelDTO;
import com.znsx.cms.web.dto.omc.GetDvrDTO;
import com.znsx.cms.web.dto.omc.GetSolarBatteryDTO;
import com.znsx.cms.web.dto.omc.ListCameraDTO;
import com.znsx.cms.web.dto.omc.ListDvrDTO;
import com.znsx.cms.web.dto.omc.ListSolarBatteryDTO;
import com.znsx.cms.web.dto.omc.ListSolarDeviceDTO;
import com.znsx.cms.web.dto.omc.OrganDeviceCheckDTO;
import com.znsx.cms.web.dto.omc.OrganDeviceOnlineDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 设备对外接口控制类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:37:19
 */

@Controller
public class DeviceController extends BaseController {

	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private OrganManager organManger;
	@Autowired
	private TmDeviceManager tmDeviceManager;
	@Autowired
	private SysLogManager sysLogManager;

	@InterfaceDescription(logon = true, method = "Create_Preset", cmd = "1011")
	@RequestMapping("/create_preset.xml")
	public void createPreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String vicId = request.getParameter("vicId");
		if (StringUtils.isBlank(vicId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [vicId]");
		}
		Integer presetNumber = null;
		String presetNumberString = request.getParameter("presetNumber");
		if (StringUtils.isBlank(presetNumberString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [presetNumber]");
		} else {
			try {
				presetNumber = Integer.parseInt(presetNumberString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter presetNumber[" + presetNumberString
								+ "] invalid !");
			}
		}
		String presetName = request.getParameter("presetName");
		if (StringUtils.isBlank(presetName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [presetName]");
		}
		presetName = StringUtils.replace(presetName, " ", "+");
		// 创建预置点
		String presetId = deviceManager.createPreset(vicId, presetNumber,
				presetName);

		// 查询摄像头
		GetCameraVO camera = deviceManager.getCamera(vicId);
		ResourceVO user = resource.get();

		// 单独记录成功的日志
		SysLog log = new SysLog();
		log.setResourceId(user.getId());
		log.setResourceName(user.getName());
		log.setResourceType(user.getType());
		log.setTargetId(presetId);
		log.setTargetName(camera.getName() + "预置点：" + presetName);
		log.setTargetType("Preset");
		log.setLogTime(System.currentTimeMillis());
		log.setOperationType("create");
		log.setOperationName("创建预置点");
		log.setOperationCode("1011");
		log.setSuccessFlag(ErrorCode.SUCCESS);
		log.setCreateTime(System.currentTimeMillis());
		log.setOrganId(user.getOrganId());
		sysLogManager.batchLog(log);

		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("Method", "Create_Preset");
		root.setAttribute("Cmd", "1011");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");
		doc.setRootElement(root);

		Element preset = new Element("Preset");
		preset.setAttribute("Id", presetId + "");
		root.addContent(preset);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Update_Preset", cmd = "1012")
	@RequestMapping("/update_preset.xml")
	public void updatePreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String presetId = request.getParameter("presetId");
		if (StringUtils.isBlank(presetId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [presetId]");
		}

		Integer presetNumber = null;
		String presetNumberString = request.getParameter("presetNumber");
		if (StringUtils.isNotBlank(presetNumberString)) {
			try {
				presetNumber = Integer.parseInt(presetNumberString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter presetNumber[" + presetNumberString
								+ "] invalid !");
			}
		}
		String presetName = request.getParameter("presetName");
		presetName = StringUtils.replace(presetName, " ", "+");

		// 查询摄像头
		Preset preset = deviceManager.getPreset(presetId);
		GetCameraVO camera = deviceManager.getCamera(preset.getDeviceId());

		deviceManager.updatePreset(presetId, presetNumber, presetName);

		ResourceVO user = resource.get();

		// 单独记录成功的日志
		SysLog log = new SysLog();
		log.setResourceId(user.getId());
		log.setResourceName(user.getName());
		log.setResourceType(user.getType());
		log.setTargetId(presetId);
		log.setTargetName(camera.getName() + "预置点：" + preset.getName());
		log.setTargetType("Preset");
		log.setLogTime(System.currentTimeMillis());
		log.setOperationType("update");
		log.setOperationName("修改预置点");
		log.setOperationCode("1012");
		log.setSuccessFlag(ErrorCode.SUCCESS);
		log.setCreateTime(System.currentTimeMillis());
		log.setOrganId(user.getOrganId());
		sysLogManager.batchLog(log);

		dto.setMethod("Update_Preset");
		dto.setCmd("1012");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Preset", cmd = "1013")
	@RequestMapping("/delete_preset.xml")
	public void deletePreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String presetId = request.getParameter("presetId");
		if (StringUtils.isBlank(presetId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [presetId]");
		}

		// 查询摄像头
		Preset preset = deviceManager.getPreset(presetId);
		GetCameraVO camera = deviceManager.getCamera(preset.getDeviceId());

		deviceManager.deletePreset(presetId);

		ResourceVO user = resource.get();

		// 单独记录成功的日志
		SysLog log = new SysLog();
		log.setResourceId(user.getId());
		log.setResourceName(user.getName());
		log.setResourceType(user.getType());
		log.setTargetId(presetId);
		log.setTargetName(camera.getName() + "预置点：" + preset.getName());
		log.setTargetType("Preset");
		log.setLogTime(System.currentTimeMillis());
		log.setOperationType("delete");
		log.setOperationName("删除预置点");
		log.setOperationCode("1013");
		log.setSuccessFlag(ErrorCode.SUCCESS);
		log.setCreateTime(System.currentTimeMillis());
		log.setOrganId(user.getOrganId());
		sysLogManager.batchLog(log);

		dto.setMethod("Delete_Preset");
		dto.setCmd("1013");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Vic_Preset", cmd = "1010")
	@RequestMapping("/list_vic_preset.xml")
	public void listVicPreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String vicId = request.getParameter("vicId");
		if (StringUtils.isBlank("vicId")) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [vicId]");
		}

		List<PresetVO> presets = deviceManager.listVicPreset(vicId);

		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("Method", "List_Vic_Preset");
		root.setAttribute("Cmd", "1010");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");

		doc.setRootElement(root);
		Element presetList = new Element("PresetList");
		root.addContent(presetList);

		for (int i = 0; i < presets.size(); i++) {
			Element preset = ElementUtil.createElement("Preset",
					presets.get(i), null, null);
			presetList.addContent(preset);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Camera", cmd = "2060")
	@RequestMapping("/create_camera.json")
	public void createCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String standardNumber = request.getParameter("standardNumber");

		String subType = request.getParameter("subType");
		if (StringUtils.isBlank(subType)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [subType]");
		}

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}
		name = StringUtils.replace(name, " ", "+");
		String mssId = request.getParameter("mssId");

		String crsId = request.getParameter("crsId");

		Short storeType = null;
		String storeTypeString = request.getParameter("storeType");
		if (StringUtils.isBlank(storeTypeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [storeType]");
		} else {
			try {
				storeType = Short.parseShort(storeTypeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter storeType[" + storeTypeString
								+ "] invalid !");
			}
		}

		String localStorePlan = request.getParameter("localStorePlan");

		String centerStorePlan = request.getParameter("centerStorePlan");

		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String manufacturerId = request.getParameter("manufacturerId");

		String location = request.getParameter("location");

		String parentId = request.getParameter("parentId");
		if (StringUtils.isBlank(parentId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [parentId]");
		}

		Short channelNumber = null;
		String channelId = request.getParameter("channelId");
		if (StringUtils.isBlank(channelId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [channelId]");
		} else {
			try {
				channelNumber = Short.parseShort(channelId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelId[" + channelId + "] invalid !");
			}
		}

		String streamType = request.getParameter("streamType");

		String note = request.getParameter("note");

		String deviceModelId = request.getParameter("deviceModelId");

		String expand = request.getParameter("expand");
		String navigation = request.getParameter("navigation");
		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String owner = reader.getString("owner", true);

		String civilCode = reader.getString("civilCode", true);

		Double block = reader.getDouble("block", true);

		String certNum = reader.getString("certNum", true);

		Integer certifiable = reader.getInteger("certifiable", true);

		Integer errCode = reader.getInteger("errCode", true);

		Long endTime = reader.getLong("endTime", true);

		String rmsId = reader.getString("rmsId", true);

		String storeStream = reader.getString("storeStream", true);

		// 校验License
		License license = licenseManager.getLicense();
		int cameraAmount = Integer.parseInt(license.getCameraAmount());
		int cameraCount = deviceManager.countCamera();
		if (cameraCount >= cameraAmount) {
			throw new BusinessException(ErrorCode.CAMERA_AMOUNT_LIMIT,
					"Camera amount over license limit !");
		}

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Camera", organId);
		}

		String id = deviceManager.createCamera(standardNumber, subType, name,
				organId, manufacturerId, location, note, storeType,
				localStorePlan, centerStorePlan, streamType, parentId, mssId,
				crsId, channelNumber, deviceModelId, expand, navigation,
				stakeNumber, owner, civilCode, block, certNum, certifiable,
				errCode, endTime, rmsId, storeStream);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2060");
		dto.setMethod("Create_Camera");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Camera", cmd = "2061")
	@RequestMapping("/update_camera.json")
	public void updateCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		String standardNumber = request.getParameter("standardNumber");

		String subType = request.getParameter("subType");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String mssId = request.getParameter("mssId");

		String crsId = request.getParameter("crsId");

		Short storeType = null;
		String storeTypeString = request.getParameter("storeType");
		if (StringUtils.isNotBlank(storeTypeString)) {
			try {
				storeType = Short.parseShort(storeTypeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter storeType[" + storeTypeString
								+ "] invalid !");
			}
		}

		String localStorePlan = request.getParameter("localStorePlan");

		String centerStorePlan = request.getParameter("centerStorePlan");

		String organId = request.getParameter("organId");

		String manufacturerId = request.getParameter("manufacturerId");

		String location = request.getParameter("location");

		String parentId = request.getParameter("parentId");

		Short channelNumber = null;
		String channelId = request.getParameter("channelId");
		if (StringUtils.isNotBlank(channelId)) {
			try {
				channelNumber = Short.parseShort(channelId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelId[" + channelId + "] invalid !");
			}
		}

		String streamType = request.getParameter("streamType");

		String note = request.getParameter("note");

		String expand = request.getParameter("expand");

		String navigation = request.getParameter("navigation");

		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String deviceModelId = request.getParameter("deviceModelId");

		String owner = reader.getString("owner", true);

		String civilCode = reader.getString("civilCode", true);

		Double block = reader.getDouble("block", true);

		String certNum = reader.getString("certNum", true);

		Integer certifiable = reader.getInteger("certifiable", true);

		Integer errCode = reader.getInteger("errCode", true);

		Long endTime = reader.getLong("endTime", true);

		String rmsId = reader.getString("rmsId", true);

		String storeStream = reader.getString("storeStream", true);

		deviceManager.updateCamera(id, standardNumber, subType, name, organId,
				manufacturerId, location, note, storeType, localStorePlan,
				centerStorePlan, streamType, parentId, mssId, crsId,
				channelNumber, deviceModelId, expand, navigation, stakeNumber,
				owner, civilCode, block, certNum, certifiable, errCode,
				endTime, rmsId, storeStream);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2061");
		dto.setMethod("Update_Camera");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Camera", cmd = "2062")
	@RequestMapping("/delete_camera.json")
	public void deleteCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteCamera(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2062");
		dto.setMethod("Delete_Camera");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Camera", cmd = "2063")
	@RequestMapping("/list_camera.json")
	public void listCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dvrId = request.getParameter("dvrId");
		if (StringUtils.isBlank(dvrId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [dvrId]");
		}

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
		Integer totalCount = deviceManager.cameraTotalCount(dvrId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<ListCameraVO> vo = deviceManager.listCamera(dvrId, startIndex,
				limit);

		ListCameraDTO dto = new ListCameraDTO();
		dto.setCmd("2063");
		dto.setMethod("List_Camera");
		dto.setCameraList(vo);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Camera", cmd = "2064")
	@RequestMapping("/get_camera.json")
	public void getCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		GetCameraVO camera = deviceManager.getCamera(id);
		GetCameraDTO dto = new GetCameraDTO();
		dto.setCamera(camera);
		dto.setCmd("2064");
		dto.setMethod("Get_Camera");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Dvr", cmd = "2050")
	@RequestMapping("/create_dvr.json")
	public void createDvr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String subType = request.getParameter("subType");
		if (StringUtils.isBlank(subType)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [subType]");
		}

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}
		name = StringUtils.replace(name, " ", "+");
		String ptsId = request.getParameter("ptsId");

		String transport = request.getParameter("transport");
		if (StringUtils.isBlank(transport)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [transport]");
		}

		String mode = request.getParameter("mode");
		if (StringUtils.isBlank(mode)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [mode]");
		}

		Integer maxConnect = null;
		String maxConnectString = request.getParameter("maxConnect");
		if (StringUtils.isBlank(maxConnectString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [maxConnect]");
		} else {
			try {
				maxConnect = Integer.parseInt(maxConnectString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter maxConnect[" + maxConnectString
								+ "] invalid !");
			}
		}

		Integer channelAmount = null;
		String channelAmountString = request.getParameter("channelAmount");
		if (StringUtils.isBlank(channelAmountString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [channelAmount]");
		} else {
			try {
				channelAmount = Integer.parseInt(channelAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelAmount[" + channelAmountString
								+ "] invalid !");
			}
		}

		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String linkType = request.getParameter("linkType");

		String lanIp = request.getParameter("lanIp");
		if (StringUtils.isBlank(lanIp)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [lanIp]");
		}

		Integer port = null;
		String portString = request.getParameter("port");
		if (StringUtils.isBlank(portString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [port]");
		} else {
			try {
				port = Integer.parseInt(portString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter port[" + portString + "] invalid !");
			}
		}

		String manufacturerId = request.getParameter("manufacturerId");

		String deviceModelId = request.getParameter("deviceModelId");

		String location = request.getParameter("location");

		String note = request.getParameter("note");

		String userName = request.getParameter("userName");
		if (StringUtils.isBlank(userName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userName]");
		}

		String password = request.getParameter("password");
		// if (StringUtils.isBlank(password)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [password]");
		// }

		Integer heartCycle = null;
		String heartCycleString = request.getParameter("heartCycle");
		if (StringUtils.isBlank(heartCycleString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [heartCycle]");
		} else {
			try {
				heartCycle = Integer.parseInt(heartCycleString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter heartCycle[" + heartCycleString
								+ "] invalid !");
			}
		}

		String expand = request.getParameter("expand");

		Integer aicAmount = 0;
		String aicAmountString = request.getParameter("aicAmount");
		if (StringUtils.isNotBlank(aicAmountString)) {
			try {
				aicAmount = Integer.parseInt(aicAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter aicAmount[" + aicAmountString
								+ "] invalid !");
			}
		}

		Integer aocAmount = 0;
		String aocAmountString = request.getParameter("aocAmount");
		if (StringUtils.isNotBlank(aocAmountString)) {
			try {
				aocAmount = Integer.parseInt(aocAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter aocAmount[" + aocAmountString
								+ "] invalid !");
			}
		}

		String decode = request.getParameter("decode");
		if (StringUtils.isBlank(decode)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [decode]");
		}

		// 根据License限制和channelAmount自动生成下面的摄像头
		License license = licenseManager.getLicense();
		int cameraAmount = Integer.parseInt(license.getCameraAmount());
		int cameraCount = deviceManager.countCamera();
		int generateNum = 0;
		if ((cameraCount + channelAmount.intValue()) > cameraAmount) {
			generateNum = cameraAmount - cameraCount;
		} else {
			generateNum = channelAmount.intValue();
		}
		// 如果受到了通道license的限制给出提示信息
		if (channelAmount.intValue() > generateNum) {
			throw new BusinessException(ErrorCode.CAMERA_AMOUNT_LIMIT,
					"camera more than limit");
		}

		// 自动生成标准号
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Dvr", organId);
		}

		String id = deviceManager.createDvr(standardNumber, subType, name,
				ptsId, transport, mode, maxConnect, channelAmount, organId,
				linkType, lanIp, port, manufacturerId, deviceModelId, location,
				note, userName, password, heartCycle, expand, aicAmount,
				aocAmount, decode);

		Organ organ = new Organ();
		organ.setId(organId);
		Dvr parent = new Dvr();
		parent.setId(id);
		VideoDeviceProperty property = new VideoDeviceProperty();
		property.setStoreType(TypeDefinition.STORE_TYPE_LOCAL);
		property.setLocalStorePlan(TypeDefinition.STORE_PLAN_DEFAULT);
		property.setStoreStream(TypeDefinition.STORE_MAIN_STREAM);

		// 待分配的标准号集合
		String[] sns = userManager.batchGenerateSN("Camera", organId,
				generateNum);
		// 待分配的ID集合
		// String[] ids = deviceManager.batchGenerateId("Camera", generateNum);
		// 待创建摄像头集合
		Camera[] cameras = new Camera[generateNum];
		for (int i = 0; i < generateNum; i++) {
			cameras[i] = new Camera();
			// cameras[i].setId(ids[i]);
			cameras[i].setStandardNumber(sns[i]);
			cameras[i].setType(TypeDefinition.DEVICE_TYPE_CAMERA);
			cameras[i].setSubType(TypeDefinition.SUBTYPE_CAMERA_DEFAULT);
			cameras[i].setName(name + "_" + (i + 1));
			cameras[i].setOrgan(organ);
			cameras[i].setLocation(location);
			cameras[i].setNote(note);
			cameras[i].setProperty(property);
			cameras[i].setChannelNumber((short) (i + 1));
			cameras[i].setParent(parent);
			cameras[i].setCreateTime(System.currentTimeMillis());
		}
		deviceManager.batchCreateDvrCameras(cameras);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2050");
		dto.setMethod("Create_Dvr");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Dvr", cmd = "2051")
	@RequestMapping("/update_dvr.json")
	public void updateDvr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		String standardNumber = request.getParameter("standardNumber");

		String subType = request.getParameter("subType");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String ptsId = request.getParameter("ptsId");

		String transport = request.getParameter("transport");

		String mode = request.getParameter("mode");

		Integer maxConnect = null;
		String maxConnectString = request.getParameter("maxConnect");
		if (StringUtils.isNotBlank(maxConnectString)) {
			try {
				maxConnect = Integer.parseInt(maxConnectString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter maxConnect[" + maxConnectString
								+ "] invalid !");
			}
		}

		String organId = request.getParameter("organId");

		String linkType = request.getParameter("linkType");

		String lanIp = request.getParameter("lanIp");

		Integer port = null;
		String portString = request.getParameter("port");
		if (StringUtils.isNotBlank(portString)) {
			try {
				port = Integer.parseInt(portString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter port[" + portString + "] invalid !");
			}
		}

		String manufacturerId = request.getParameter("manufacturerId");

		String deviceModelId = request.getParameter("deviceModelId");

		String location = request.getParameter("location");

		String note = request.getParameter("note");

		String userName = request.getParameter("userName");

		String password = request.getParameter("password");

		Integer heartCycle = null;
		String heartCycleString = request.getParameter("heartCycle");
		if (StringUtils.isNotBlank(heartCycleString)) {
			try {
				heartCycle = Integer.parseInt(heartCycleString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter heartCycle[" + heartCycleString
								+ "] invalid !");
			}
		}

		String expand = request.getParameter("expand");

		Integer aicAmount = null;
		String aicAmountString = request.getParameter("aicAmount");
		if (StringUtils.isNotBlank(aicAmountString)) {
			try {
				aicAmount = Integer.parseInt(aicAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter aicAmount[" + aicAmountString
								+ "] invalid !");
			}
		}

		Integer aocAmount = null;
		String aocAmountString = request.getParameter("aocAmount");
		if (StringUtils.isNotBlank(aocAmountString)) {
			try {
				aocAmount = Integer.parseInt(aocAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter aocAmount[" + aocAmountString
								+ "] invalid !");
			}
		}

		Integer channelAmount = null;
		String channelAmountString = request.getParameter("channelAmount");
		if (StringUtils.isNotBlank(channelAmountString)) {
			try {
				channelAmount = Integer.parseInt(channelAmountString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelAmount[" + channelAmountString
								+ "] invalid !");
			}
		}

		String decode = request.getParameter("decode");

		deviceManager.updateDvr(id, standardNumber, subType, name, ptsId,
				transport, mode, maxConnect, organId, linkType, lanIp, port,
				manufacturerId, deviceModelId, location, note, userName,
				password, heartCycle, expand, aicAmount, aocAmount,
				channelAmount, decode);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2051");
		dto.setMethod("Update_Dvr");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Dvr", cmd = "2052")
	@RequestMapping("/delete_dvr.json")
	public void deleteDvr(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		Boolean force = false;
		String forceString = request.getParameter("force");
		if (StringUtils.isNotBlank(forceString)) {
			if (forceString.equals("1")) {
				force = true;
			}
		}

		deviceManager.deleteDvr(id, force);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2052");
		dto.setMethod("Delete_Dvr");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Dvr", cmd = "2053")
	@RequestMapping("/list_dvr.json")
	public void listDvr(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

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
		Integer totalCount = deviceManager.dvrTotalCount(organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<DvrVO> listVO = deviceManager.listDvr(organId, startIndex, limit);

		ListDvrDTO dto = new ListDvrDTO();
		dto.setCmd("2053");
		dto.setMethod("List_Dvr");
		dto.setDvrList(listVO);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Dvr", cmd = "2054")
	@RequestMapping("/get_dvr.json")
	public void getDvr(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetDvrVO dvrVO = deviceManager.getDvr(id);
		GetDvrDTO dto = new GetDvrDTO();
		dto.setCmd("2054");
		dto.setMethod("Get_Dvr");
		dto.setDvr(dvrVO);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Fault", cmd = "1263")
	@RequestMapping("/list_device_fault.xml")
	public void listDeviceFault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String deviceName = reader.getString("deviceName", true);

		String deviceType = reader.getString("deviceType", true);

		String alarmType = reader.getString("alarmType", true);

		Long begin = reader.getLong("begin", false);

		Long end = reader.getLong("end", false);

		Integer start = Integer.valueOf(0);
		Integer paramStart = reader.getInteger("start", true);
		if (null != paramStart) {
			start = paramStart;
		}

		Integer limit = Integer.valueOf(50);
		Integer paramLimit = reader.getInteger("limit", true);
		if (null != paramLimit) {
			limit = paramLimit;
		}

		ResourceVO user = resource.get();

		List<ListDeviceAlarmVO> list = deviceManager.listDeviceAlarm(null,
				deviceName, deviceType, alarmType, begin, end, start, limit,
				user.getId());
		int count = deviceManager.selectTotalCount(null, deviceName,
				deviceType, alarmType, begin, end, user.getId());

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1263");
		dto.setMethod("List_Device_Fault");
		dto.setMessage(count + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (ListDeviceAlarmVO vo : list) {
			Element record = ElementUtil.createElement("Record", vo);
			root.addContent(record);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Device_Fault", cmd = "1265")
	@RequestMapping("/save_device_fault.xml")
	public void saveDeviceFault(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		Element record = requestRoot.getChild("Record");
		if (null == record) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Record]");
		}

		String id = record.getAttributeValue("Id");
		String deviceName = record.getAttributeValue("DeviceName");
		String standardNumber = record.getAttributeValue("StandardNumber");
		String deviceType = record.getAttributeValue("DeviceType");
		Long detectTime = ElementUtil.getLong(record, "DetectTime");
		Integer status = ElementUtil.getInteger(record, "Status");
		String confirmUser = record.getAttributeValue("ConfirmUser");
		String maintainUser = record.getAttributeValue("MaintainUser");
		String reason = record.getAttributeValue("Reason");
		Long recoverTime = ElementUtil.getLong(record, "RecoverTime");
		String stakeNumber = record.getAttributeValue("Stake");
		String navigation = record.getAttributeValue("Navigation");

		ResourceVO user = resource.get();

		id = deviceManager
				.saveDeviceFault(id, deviceName, standardNumber, deviceType,
						detectTime, status, confirmUser, maintainUser, reason,
						recoverTime, user.getOrganId(), stakeNumber, navigation);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1265");
		dto.setMethod("Save_Device_Fault");
		dto.setMessage(id);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Manufacturer", cmd = "2040")
	@RequestMapping("/list_manufacturer.json")
	public void listManufacturer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexString
								+ "] invalid !");
			}
		}
		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		List<Manufacturer> manufacturers = deviceManager.listManufacturer(
				startIndex, limit);
		Integer totalCount = deviceManager.manufacturerTotalCount();

		ListManufacturerDTO dto = new ListManufacturerDTO();
		dto.setCmd("2040");
		dto.setMethod("List_Manufacturer");
		dto.setListManufacturer(manufacturers);
		dto.setTotalCount(totalCount);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Model", cmd = "2041")
	@RequestMapping("/list_device_model.json")
	public void listDeviceModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String manufacturerId = request.getParameter("manufacturerId");

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

		Integer totalCount = deviceManager.deviceModeTotalCount(manufacturerId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<DeviceModelVO> deviceModels = deviceManager.listDeviceModel(
				manufacturerId, startIndex, limit);

		ListDeviceModelDTO dto = new ListDeviceModelDTO();
		dto.setCmd("2041");
		dto.setMethod("List_Device_Model");
		dto.setListDeviceModel(deviceModels);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Device_Model", cmd = "2042")
	@RequestMapping("/create_device_model.json")
	public void createDeviceModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}
		name = StringUtils.replace(name, " ", "+");
		String type = request.getParameter("type");
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [type]");
		}

		String manufacturerId = request.getParameter("manufacturerId");
		if (StringUtils.isBlank(manufacturerId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [manufacturerId]");
		}

		String note = request.getParameter("note");

		String id = deviceManager.createDeviceModel(name, type, manufacturerId,
				note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2042");
		dto.setMethod("Create_Device_Model");
		dto.setMessage(id + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Device_Model", cmd = "2043")
	@RequestMapping("/update_device_model.json")
	public void updateDeviceModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String type = request.getParameter("type");

		String manufacturerId = request.getParameter("manufacturerId");

		String note = request.getParameter("note");

		deviceManager.updateDeviceModel(id, name, type, manufacturerId, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2043");
		dto.setMethod("Update_Device_Model");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Device_Model", cmd = "2044")
	@RequestMapping("/delete_device_model.json")
	public void deleteDeviceModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteDeviceModel(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2044");
		dto.setMethod("Delete_Device_Model");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Device_Model", cmd = "2045")
	@RequestMapping("/get_device_model.json")
	public void getDeviceModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		DeviceModelVO vo = deviceManager.getDeviceModel(id);
		GetDeviceModelDTO dto = new GetDeviceModelDTO();
		dto.setCmd("2045");
		dto.setMethod("Get_Device_Model");
		dto.setDeviceModel(vo);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Dvr_By_Device", cmd = "2055")
	@RequestMapping("/list_dvr_by_device.json")
	public void listDvrByDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");

		String standardNumber = request.getParameter("standardNumber");
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}
		String ip = request.getParameter("ip");
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
		String logonUserId = resource.get().getId();
		ListDvrDTO dto = deviceManager.listDvrByDevice(name, standardNumber,
				ip, startIndex, limit, logonUserId, organId);
		dto.setCmd("2055");
		dto.setMethod("List_Dvr_By_Device");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Set_Default_Preset", cmd = "1019")
	@RequestMapping("/set_default_preset.xml")
	public void setDefaultPreset(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String presetId = request.getParameter("presetId");
		if (StringUtils.isBlank(presetId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [presetId]");
		}

		boolean set = false;
		String setString = request.getParameter("set");
		if ("1".equals(setString)) {
			set = true;
		}

		deviceManager.setCameraDefaultPreset(presetId, set);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1019");
		dto.setMethod("Set_Default_Preset");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Count_Device", cmd = "2180")
	@RequestMapping("/count_device.json")
	public void countDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}
		String deviceType = request.getParameter("deviceType");
		if (StringUtils.isBlank(deviceType)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [deviceType]");
		}

		CountDeviceDTO dto = deviceManager.countDevice(organId, deviceType);
		dto.setCmd("2180");
		dto.setMethod("Count_Device");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "Batch_Create_Camera", cmd = "2065")
	@RequestMapping("/batch_create_camera.json")
	public void batchCreateCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// Excel数据流
			InputStream in = null;
			// 机构ID
			String organId = "";
			// 解析请求
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			// 获取License
			License license = licenseManager.getLicense();
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
					userManager.checkSession(sessionId);
				}

				if ("organId".equals(fieldName)) {
					organId = item.getString();
					if (StringUtils.isBlank(organId)) {
						throw new BusinessException(
								ErrorCode.PARAMETER_NOT_FOUND,
								"missing [organId]");
					}
				}

				if ("Filedata".equals(fieldName)) {
					uploadFlag = true;
					in = item.getInputStream();
				}
			}
			if (!uploadFlag) {
				throw new BusinessException(
						ErrorCode.MISSING_PARAMETER_FILEDATA,
						"Parameter [Filedata] not found !");
			}
			Organ organ = organManger.getOrganById(organId);
			Workbook wb = deviceManager.checkoutIo(in);
			List<Dvr> dvrs = deviceManager.readDvrWb(wb, organ);
			List<Camera> cameras = deviceManager.readCameraWB(wb, organ,
					license, dvrs);
			// 待分配的DvrID集合
			// String[] dvrIds = deviceManager.batchDvrId("Dvr", dvrs.size());
			// String[] cameraIds = null;
			// if (cameras.size() > 0) {
			// // 待分配的CameraID集合
			// cameraIds = deviceManager.batchGenerateId("Camera",
			// cameras.size());
			// }
			deviceManager.batchInsertDvr(dvrs);
			// if (cameraIds != null) {
			deviceManager.batchInsertCamera(cameras);
			// }
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2065");
		dto.setMethod("Batch_Create_Camera");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Mcu_List_Organ_Device", cmd = "3021")
	@RequestMapping("/mcu_list_organ_device.xml")
	public void mcuListOrganDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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

		List<AuthCameraVO> devices = deviceManager.listUserAuthCamera(resource
				.get().getId());

		Element element = deviceManager.listMcuDevice(startIndex, limit,
				devices);

		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("Cmd", "1000");
		root.setAttribute("Method", "Login");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");
		doc.setRootElement(root);
		Element totalCount = new Element("TotalCount");
		totalCount.setText(devices.size() + "");
		root.addContent(totalCount);
		root.addContent(element);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Camera_By_Device", cmd = "2066")
	@RequestMapping("/list_camera_by_device.json")
	public void listCameraByDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String name = request.getParameter("name");
		name = StringUtils.replace(name, " ", "+");

		String stakeNumber = request.getParameter("stakeNumber");
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String manufacturerId = request.getParameter("manufacturerId");

		String standardNumber = request.getParameter("standardNumber");

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

		String organs[] = organManger.listOrganAllChildren(organId);

		Integer totalCount = deviceManager.cameraByDeviceTotalCount(organs,
				name, stakeNumber, manufacturerId, standardNumber);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<ListCameraVO> vo = deviceManager.listCameraByDevice(organs, name,
				stakeNumber, manufacturerId, startIndex, limit, standardNumber);

		ListCameraDTO dto = new ListCameraDTO();
		dto.setCmd("2066");
		dto.setMethod("List_Camera_By_Device");
		dto.setCameraList(vo);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Solar_Battery", cmd = "2390")
	@RequestMapping("/create_solar_battery.json")
	public void createSolarBattery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String organId = reader.getString("organId", false);

		String maxVoltage = reader.getString("maxVoltage", true);

		String minVoltage = reader.getString("minVoltage", true);

		String batteryCapacity = reader.getString("batteryCapacity", true);

		String storePlan = reader.getString("storePlan", true);

		String note = reader.getString("note", true);

		String dasId = reader.getString("dasId", false);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String period = reader.getString("period", true);

		// 校验License
		License license = licenseManager.getLicense();
		int deviceAmount = Integer.parseInt(license.getDeviceAmount());
		int deviceCount = tmDeviceManager.countDeviceAmount();
		if (deviceCount >= deviceAmount) {
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
					"Device amount over license limit !");
		}

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("SolarBattery",
					organId);
		}

		String id = deviceManager.createSolarBattery(name, standardNumber,
				organId, maxVoltage, minVoltage, batteryCapacity, storePlan,
				note, dasId, navigation, stakeNumber, period);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2390");
		dto.setMethod("Create_Solar_Battery");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Solar_Battery", cmd = "2391")
	@RequestMapping("/update_solar_battery.json")
	public void updateFireDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String organId = reader.getString("organId", true);

		String maxVoltage = reader.getString("maxVoltage", true);

		String minVoltage = reader.getString("minVoltage", true);

		String batteryCapacity = reader.getString("batteryCapacity", true);

		String storePlan = reader.getString("storePlan", true);

		String note = reader.getString("note", true);

		String dasId = reader.getString("dasId", true);

		String navigation = reader.getString("navigation", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String period = reader.getString("period", true);

		deviceManager.updateSolarBattery(id, name, standardNumber, organId,
				maxVoltage, minVoltage, batteryCapacity, storePlan, note,
				dasId, navigation, stakeNumber, period);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2391");
		dto.setMethod("Update_Solar_Battery");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Solar_Battery", cmd = "2392")
	@RequestMapping("/delete_solar_battery.json")
	public void deleteSolarBattery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		deviceManager.deleteSolarBattery(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2392");
		dto.setMethod("Delete_Solar_Battery");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Solar_Battery", cmd = "2393")
	@RequestMapping("/get_solar_battery.json")
	public void getSolarBattery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		GetSolarBatteryVO solarBattery = deviceManager.getSolarBattery(id);
		GetSolarBatteryDTO dto = new GetSolarBatteryDTO();
		dto.setSolarBattery(solarBattery);
		dto.setCmd("2393");
		dto.setMethod("Get_Solar_Battery");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Solar_Battery", cmd = "2394")
	@RequestMapping("/list_solar_battery.json")
	public void listSolarBattery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		name = StringUtils.replace(name, " ", "+");

		String organId = reader.getString("organId", false);

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager
				.solarBatteryTotalCount(name, organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<GetSolarBatteryVO> list = deviceManager.listSolarBattery(name,
				organId, startIndex, limit);

		ListSolarBatteryDTO dto = new ListSolarBatteryDTO();
		dto.setCmd("2394");
		dto.setMethod("List_Solar_Battery");
		dto.setSolarBatteryList(list);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Bind_Device_Solar", cmd = "2395")
	@RequestMapping("/bind_device_solar.json")
	public void bindDeviceSolar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String type = reader.getString("type", false);
		String solarId = reader.getString("solarId", false);
		String json = reader.getString("json", false);
		deviceManager.bindDeviceSolar(type, solarId, json);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2395");
		dto.setMethod("Bind_Device_Solar");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Solar_Bind_Device", cmd = "2396")
	@RequestMapping("/list_solar_bind_device.json")
	public void listSolarBindDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String solarId = request.getParameter("solarId");
		if (StringUtils.isBlank(solarId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [solarId]");
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
			totalCount = deviceManager.countOrganCamera(organId, solarId, name);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount != 0) {
				if (startIndex >= totalCount) {
					startIndex -= ((startIndex - totalCount) / limit + 1)
							* limit;
				}
			}
			deviceList = deviceManager.listOrganCamera(organId, solarId, name,
					startIndex, limit);
		} else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(type)) {
			totalCount = deviceManager.countOrganVD(organId, solarId, name);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount != 0) {
				if (startIndex >= totalCount) {
					startIndex -= ((startIndex - totalCount) / limit + 1)
							* limit;
				}
			}
			deviceList = deviceManager.listOrganVD(organId, solarId, name,
					startIndex, limit);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

		ListSolarDeviceDTO dto = new ListSolarDeviceDTO();
		dto.setCmd("2396");
		dto.setMethod("List_Solar_Bind_Device");
		dto.setTotalCount(totalCount + "");
		dto.setSolarDevices(deviceList);
		writePage(response, dto);

	}

	@InterfaceDescription(logon = true, method = "Remove_Solar_Device", cmd = "2397")
	@RequestMapping("/remove_solar_device.json")
	public void removeSolarDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String solarId = request.getParameter("solarId");
		if (StringUtils.isBlank(solarId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [roleId]");
		}

		deviceManager.removeSolarDevice(solarId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2397");
		dto.setMethod("Remove_Solar_Device");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Near_Camera", cmd = "1267")
	@RequestMapping("/get_near_camera.xml")
	public void getNearCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String stakeNumber = reader.getString("stakeNumber", false);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String navigation = reader.getString("navigation", false);
		String organId = reader.getString("organId", false);
		List<ListCameraVO> list = deviceManager.getNearCamera(stakeNumber,
				navigation, organId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1267");
		dto.setMethod("Get_Near_Camera");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);

		doc.setRootElement(root);
		for (ListCameraVO vo : list) {
			Element camera = new Element("Camera");
			camera.setAttribute("Id", vo.getId() != null ? vo.getId() : "");
			camera.setAttribute("Name", vo.getName() != null ? vo.getName()
					: "");
			camera.setAttribute("StandardNumber",
					vo.getStandardNumber() != null ? vo.getStandardNumber()
							: "");
			camera.setAttribute("StakeNumber",
					vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
			camera.setAttribute("Navigation",
					vo.getNavigation() != null ? vo.getNavigation() : "");
			camera.setAttribute("ChannelId",
					vo.getChannelId() != null ? vo.getChannelId() : "");
			camera.setAttribute("ChannelId",
					vo.getChannelId() != null ? vo.getChannelId() : "");
			camera.setAttribute("MssId", vo.getMssId() != null ? vo.getMssId()
					: "");
			camera.setAttribute("MssName",
					vo.getMssName() != null ? vo.getMssName() : "");
			camera.setAttribute("CreateTime",
					vo.getCreateTime() != null ? vo.getCreateTime() : "");
			root.addContent(camera);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Device_Alarm", cmd = "1271")
	@RequestMapping("/save_device_alarm.xml")
	public void saveDeviceAlarm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", true);
		String deviceName = reader.getString("deviceName", false);
		deviceName = StringUtils.replace(deviceName, " ", "+");
		String status = reader.getString("status", false);
		Long alarmTime = reader.getLong("alarmTime", true);
		Long confirmTime = reader.getLong("confirmTime", true);
		String alarmContent = reader.getString("alarmContent", true);
		String alarmType = reader.getString("alarmType", true);
		String deviceType = reader.getString("deviceType", true);
		String deviceId = reader.getString("deviceId", true);
		String organId = reader.getString("organId", true);
		String standardNumber = reader.getString("standardNumber", true);
		String currentValue = reader.getString("currentValue", true);
		String threshold = reader.getString("threshold", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		if (StringUtils.isBlank(id)) {
			id = deviceManager.saveDeviceAlarm(deviceName, status, alarmTime,
					confirmTime, alarmContent, alarmType, deviceType, deviceId,
					organId, standardNumber, currentValue, threshold,
					stakeNumber);
		} else {
			deviceManager.updateDeviceAlarm(id, deviceName, status, alarmTime,
					confirmTime, alarmContent, alarmType, deviceType, deviceId,
					organId, standardNumber, currentValue, threshold,
					stakeNumber);
		}

		BaseDTO dto = new BaseDTO();
		Document doc = new Document();
		dto.setCmd("1271");
		dto.setMethod("Save_Device_Alarm");
		dto.setMessage(id + "");
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Alarm", cmd = "2463")
	@RequestMapping("/list_device_alarm.json")
	public void listDeviceAlarm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String deviceName = reader.getString("deviceName", true);
		deviceName = StringUtils.replace(deviceName, " ", "+");
		String alarmType = reader.getString("alarmType", true);
		String deviceType = reader.getString("deviceType", true);
		String organId = reader.getString("organId", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);

		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}

		// 返回json数据
		String flag = reader.getString("flag", true);

		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + startIndexString
								+ "] invalid !");
			}
		}

		Integer limit = 1000;
		String limitString = request.getParameter("limit");
		if (StringUtils.isNotBlank(limitString)) {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + limitString + "] invalid !");
			}
		}

		Integer totalCount = deviceManager.deviceAlarmTotalCount(deviceName,
				deviceType, organId, beginTime, endTime, alarmType);

		List<DeviceAlarmVO> list = deviceManager.listDeviceAlarmByOrganId(
				deviceName, deviceType, organId, beginTime, endTime,
				startIndex, limit, alarmType);

		if (StringUtils.isNotBlank(flag)) {
			DeviceAlarmDTO dto = new DeviceAlarmDTO();
			dto.setCmd("2463");
			dto.setMethod("List_Device_Alarm");
			dto.setDeviceOnlineHistory(list);
			dto.setTotalCount(totalCount + "");
			writePage(response, dto);
		} else {
			BaseDTO dto = new BaseDTO();
			dto.setCmd("2463");
			dto.setMethod("List_Device_Alarm");
			dto.setMessage(totalCount + "");
			Document doc = new Document();
			Element root = ElementUtil.createElement("Response", dto);
			doc.setRootElement(root);

			for (DeviceAlarmVO vo : list) {
				Element deviceAlarm = ElementUtil.createElement("DeviceAlarm",
						vo);
				root.addContent(deviceAlarm);
			}

			writePageWithContentLength(response, doc);
		}
	}

	@InterfaceDescription(logon = false, method = "Batch_Save_Device_Alarm", cmd = "3019")
	@RequestMapping("/batch_save_device_alarm.xml")
	public void batchSaveDeviceAlarm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();

		List<Element> records = requestRoot.getChildren("Record");
		List<DeviceAlarmReal> list = new LinkedList<DeviceAlarmReal>();
		for (Element record : records) {
			// String deviceName = record.getAttributeValue("DeviceName");
			String standardNumber = record.getAttributeValue("StandardNumber");
			String deviceType = record.getAttributeValue("DeviceType");
			Long detectTime = ElementUtil.getLong(record, "DetectTime");
			// 默认为系统当前时间
			if (null == detectTime) {
				detectTime = System.currentTimeMillis();
			}
			Integer status = ElementUtil.getInteger(record, "Status");
			// String confirmUser = record.getAttributeValue("ConfirmUser");
			// String maintainUser = record.getAttributeValue("MaintainUser");
			String reason = record.getAttributeValue("Reason");
			// Long recoverTime = ElementUtil.getLong(record, "RecoverTime");
			// String stakeNumber = record.getAttributeValue("Stake");
			// String navigation = record.getAttributeValue("Navigation");
			String alarmType = record.getAttributeValue("AlarmType");

			DeviceAlarmReal da = new DeviceAlarmReal();
			da.setStandardNumber(standardNumber);
			da.setDeviceType(deviceType);
			da.setAlarmTime(detectTime);
			da.setStatus(MyStringUtil.object2StringNotNull(status));
			da.setAlarmContent(reason);
			da.setAlarmType(alarmType);
			list.add(da);
		}

		// 保存报警信息
		deviceManager.batchSaveDeviceAlarm(list);
		// 更新设备在线离线记录
		deviceManager.updateDeviceOnline(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3019");
		dto.setMethod("Batch_Save_Device_Alarm");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Top_Real_Play", cmd = "3022")
	@RequestMapping("/top_real_play.xml")
	public void topRealPlay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Integer limit = reader.getInteger("limit", true);
		// 默认查询50条记录
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		ResourceVO user = resource.get();
		List<TopRealPlayLog> list = deviceManager.topRealPlay(user.getId(),
				limit.intValue());

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3022");
		dto.setMethod("Top_Real_Play");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (TopRealPlayLog top : list) {
			Element e = ElementUtil.createElement("Camera", top);
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_Device_Online", cmd = "2460")
	@RequestMapping("/list_organ_device_online.json")
	public void listOrganDeviceOnline(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		Integer startIndex = reader.getInteger("startIndex", true);
		Integer limit = reader.getInteger("limit", true);

		Integer totalCount = deviceManager.organTotalCount(organId);

		List<OrganDeviceOnline> list = deviceManager.listOrganDeviceOnline(
				organId, startIndex, limit);

		OrganDeviceOnlineDTO dto = new OrganDeviceOnlineDTO();
		dto.setCmd("2460");
		dto.setMethod("List_Organ_Device_Online");
		dto.setDeviceOnlineList(list);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_Device_Check", cmd = "2461")
	@RequestMapping("/list_organ_device_check.json")
	public void listOrganDeviceCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		String[] organs = { organId };
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		String alarmType = reader.getString("alarmType", true);
		String deviceName = reader.getString("deviceName", true);

		Integer startIndex = 0;
		Integer startIndexRequest = reader.getInteger("startIndex", true);
		if (null != startIndexRequest) {
			startIndex = startIndexRequest;
		}

		Integer limit = 1000;
		Integer limitRequest = reader.getInteger("limit", true);
		if (null != limitRequest) {
			limit = limitRequest;
		}

		Integer totalCount = deviceManager.deviceCheckTotalCount(alarmType,
				deviceName, organs);

		List<OrganDeviceCheck> list = deviceManager.listOrganDeviceCheck(
				organId, beginTime, endTime, deviceName, startIndex, limit);

		OrganDeviceCheckDTO dto = new OrganDeviceCheckDTO();
		dto.setCmd("2461");
		dto.setMethod("List_Organ_Device_Check");
		dto.setDeviceOnlineList(list);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Online_History", cmd = "2462")
	@RequestMapping("/list_device_online_history.json")
	public void listDeviceOnlineHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String standardNumber = reader.getString("standardNumber", false);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer startIndex = 0;
		Integer startIndexRequest = reader.getInteger("startIndex", true);
		if (null != startIndexRequest) {
			startIndex = startIndexRequest;
		}
		Integer limit = 1000;
		Integer limitRequest = reader.getInteger("limit", true);
		if (null != limitRequest) {
			limit = limitRequest;
		}

		Integer totalCount = deviceManager.deviceHistoryTotalCount(
				standardNumber, beginTime, endTime);

		List<DeviceOnlineHistroyVO> list = deviceManager
				.listDeviceOnlineHistory(standardNumber, beginTime, endTime,
						startIndex, limit);
		DeviceOnlineHistroyDTO dto = new DeviceOnlineHistroyDTO();
		dto.setCmd("2462");
		dto.setMethod("List_Device_Online_History");
		dto.setDeviceOnlineHistory(list);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Confirm_Device_Alarm", cmd = "1273")
	@RequestMapping("/confirm_device_alarm.xml")
	public void confirmDeviceAlarm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String deviceName = reader.getString("deviceName", true);
		if (StringUtils.isNotBlank(deviceName)) {
			deviceName = StringUtils.replace(deviceName, " ", "+");
		}
		String status = reader.getString("status", true);
		if (StringUtils.isBlank(status)) {
			status = "0";
		}
		Long alarmTime = reader.getLong("alarmTime", true);
		Long confirmTime = reader.getLong("confirmTime", true);
		if (null == confirmTime) {
			confirmTime = System.currentTimeMillis();
		}
		String confirmUser = reader.getString("confirmUser", true);
		if (StringUtils.isBlank(confirmUser)) {
			confirmUser = resource.get().getName();
		}
		String alarmContent = reader.getString("alarmContent", true);
		String alarmType = reader.getString("alarmType", false);
		String deviceType = reader.getString("deviceType", true);
		String deviceId = reader.getString("deviceId", true);
		String organId = reader.getString("organId", true);
		String standardNumber = reader.getString("standardNumber", false);
		String currentValue = reader.getString("currentValue", true);
		String threshold = reader.getString("threshold", true);
		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");
		String maintainUser = reader.getString("maintainUser", true);

		deviceManager.updateAlarmBySN(deviceName, status, alarmTime,
				confirmTime, alarmContent, alarmType, deviceType, deviceId,
				organId, standardNumber, currentValue, threshold, stakeNumber,
				confirmUser, maintainUser);

		BaseDTO dto = new BaseDTO();
		Document doc = new Document();
		dto.setCmd("1273");
		dto.setMethod("Confirm_Device_Alarm");
		dto.setMessage("");
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Alarm_Real", cmd = "1274")
	@RequestMapping("/list_device_alarm_real.xml")
	public void listDeviceAlarmReal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String deviceName = reader.getString("deviceName", true);
		deviceName = StringUtils.replace(deviceName, " ", "+");
		String deviceType = reader.getString("deviceType", true);
		String organId = reader.getString("organId", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		String alarmType = reader.getString("alarmType", true);
		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		long begin = System.currentTimeMillis();
		Integer totalCount = deviceManager.deviceAlarmRealTotalCount(
				deviceName, deviceType, organId, beginTime, endTime, alarmType);

		totalCount += deviceManager.deviceAlarmTotalFlagNullCount(deviceName,
				deviceType, organId, beginTime, endTime, alarmType);
		System.out.println("totalCount结束:"
				+ (System.currentTimeMillis() - begin) + "ms");

		List<DeviceAlarmVO> list = deviceManager.listDeviceAlarmRealByOrganId(
				deviceName, deviceType, organId, beginTime, endTime,
				startIndex, limit, alarmType);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1274");
		dto.setMethod("List_Device_Alarm_Real");
		dto.setMessage(totalCount + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (DeviceAlarmVO vo : list) {
			Element deviceAlarm = ElementUtil.createElement("DeviceAlarm", vo);
			root.addContent(deviceAlarm);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Count_Device_Online", cmd = "2181")
	@RequestMapping("/count_device_online.xml")
	public void countDeviceOnline(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		String organId = reader.getString("organId", true);
		String deviceType = reader.getString("deviceType", true);
		String alarmType = reader.getString("alarmType", true);

		CountDeviceDTO dto = deviceManager.countDeviceOnline(beginTime,
				endTime, organId, deviceType, alarmType);
		dto.setCmd("2181");
		dto.setMethod("Count_Device_Online");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "List_Device_Status", cmd = "1017")
	@RequestMapping("/list_device_status.xml")
	public void listDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();

		List<Element> devices = requestRoot.getChildren("Device");
		List<String> sns = new LinkedList<String>();
		for (Element e : devices) {
			sns.add(e.getAttributeValue("StandardNumber"));
		}

		List<Element> status = deviceManager.listDeviceStatus1(sns);

		BaseDTO dto = new BaseDTO();
		Document doc = new Document();
		dto.setCmd("1017");
		dto.setMethod("List_Device_Status");
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		root.addContent(status);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Alarm", cmd = "1272")
	@RequestMapping("/list_device_alarm.xml")
	public void listDeviceAlarmXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String organId = reader.getString("organId", true);
		String deviceId = reader.getString("deviceId", true);
		String deviceType = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		String deviceTypeString = reader.getString("deviceType", true);
		if (StringUtils.isNotBlank(deviceTypeString)) {
			deviceType = deviceTypeString;
		}
		String alarmType = TypeDefinition.ALARM_TYPE_OFFLINE;
		String alarmTypeString = reader.getString("alarmType", true);
		if (StringUtils.isNotBlank(alarmTypeString)) {
			alarmType = alarmTypeString;
		}
		if ((StringUtils.isNotBlank(organId) && StringUtils
				.isNotBlank(deviceId))
				|| (StringUtils.isBlank(organId) && StringUtils
						.isBlank(deviceId))) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter organId and deviceId invalid !");
		}

		String[] organs = null;
		if (StringUtils.isNotBlank(organId)) {
			organs = organManger.listOrganAllChildren(organId);
		}
		Integer totalCount = 0;
		List<DeviceAlarmVO> list = new ArrayList<DeviceAlarmVO>();
		// 有可能是下级平台机构id查不出数据
		if (StringUtils.isNotBlank(deviceId)
				|| (organs != null && organs.length > 0)) {
			totalCount = deviceManager.deviceAlarmRealTotalCount(deviceId,
					deviceType, organs, alarmType);

			totalCount += deviceManager.deviceAlarmTotalFlagNullCount(deviceId,
					deviceType, organs, alarmType);

			list = deviceManager.listDeviceAlarmRealByOrganId(deviceId,
					deviceType, organs, alarmType);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1272");
		dto.setMethod("List_Device_Alarm");
		dto.setMessage(totalCount + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (DeviceAlarmVO vo : list) {
			Element deviceAlarm = new Element("DeviceAlarm");
			deviceAlarm.setAttribute("AlarmTime",
					vo.getAlarmTime() != null ? vo.getAlarmTime() : "");
			deviceAlarm.setAttribute("ConfirmTime",
					vo.getConfirmTime() != null ? vo.getConfirmTime() : "");
			deviceAlarm
					.setAttribute("Id", vo.getId() != null ? vo.getId() : "");
			deviceAlarm.setAttribute("DeviceType",
					vo.getDeviceType() != null ? vo.getDeviceType() : "");
			deviceAlarm.setAttribute("StandardNumber",
					vo.getStandardNumber() != null ? vo.getStandardNumber()
							: "");
			deviceAlarm.setAttribute("DeviceId",
					vo.getDeviceId() != null ? vo.getDeviceId() : "");
			deviceAlarm.setAttribute("OrganId",
					vo.getOrganId() != null ? vo.getOrganId() : "");
			deviceAlarm.setAttribute("AlarmType",
					vo.getAlarmType() != null ? vo.getAlarmType() : "");
			deviceAlarm.setAttribute("DeviceName",
					vo.getDeviceName() != null ? vo.getDeviceName() : "");
			deviceAlarm.setAttribute("AlarmContent",
					vo.getAlarmContent() != null ? vo.getAlarmContent() : "");
			deviceAlarm.setAttribute("StakeNumber",
					vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
			deviceAlarm.setAttribute("CurrentValue",
					vo.getCurrentValue() != null ? vo.getCurrentValue() : "");
			deviceAlarm.setAttribute("Threshold",
					vo.getThreshold() != null ? vo.getThreshold() : "");
			deviceAlarm.setAttribute("Status",
					vo.getStatus() != null ? vo.getStatus() : "");
			deviceAlarm.setAttribute("ConfirmUser",
					vo.getConfirmUser() != null ? vo.getConfirmUser() : "");
			deviceAlarm.setAttribute("ConfirmFlag",
					vo.getConfirmFlag() != null ? vo.getConfirmFlag() : "");
			deviceAlarm.setAttribute("OrganName",
					vo.getOrganName() != null ? vo.getOrganName() : "");
			deviceAlarm.setAttribute("RecoverTime",
					vo.getRecoverTime() != null ? vo.getRecoverTime() : "");
			deviceAlarm.setAttribute("RecoverTime",
					vo.getRecoverTime() != null ? vo.getRecoverTime() : "");
			deviceAlarm.setAttribute("FaultNumber",
					vo.getFaultNumber() != null ? vo.getFaultNumber() : "");
			deviceAlarm.setAttribute("MaintainUser",
					vo.getMaintainUser() != null ? vo.getMaintainUser() : "");
			root.addContent(deviceAlarm);
		}
		writePageUseGzip(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Alarm_History", cmd = "1277")
	@RequestMapping("/list_device_alarm_history.xml")
	public void listDeviceAlarmHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String organId = reader.getString("organId", true);
		String deviceId = reader.getString("deviceId", true);
		String deviceType = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		String deviceTypeString = reader.getString("deviceType", true);
		if (StringUtils.isNotBlank(deviceTypeString)) {
			deviceType = deviceTypeString;
		}
		String alarmType = TypeDefinition.ALARM_TYPE_OFFLINE;
		String alarmTypeString = reader.getString("alarmType", true);
		if (StringUtils.isNotBlank(alarmTypeString)) {
			alarmType = alarmTypeString;
		}
		if ((StringUtils.isNotBlank(organId) && StringUtils
				.isNotBlank(deviceId))
				|| (StringUtils.isBlank(organId) && StringUtils
						.isBlank(deviceId))) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter organId and deviceId invalid !");
		}
		String type = reader.getString("type", true);
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer startIndex = reader.getInteger("startIndex", true);
		Integer limit = reader.getInteger("limit", true);

		String[] organs = null;
		if (StringUtils.isNotBlank(organId)) {
			organs = organManger.listOrganAllChildren(organId);
		}
		int totalCount[] = { 0, 0, 0 };
		List<DeviceOnlineHistroyVO> list = new ArrayList<DeviceOnlineHistroyVO>();
		// 有可能是下级平台机构id查不出数据
		if (StringUtils.isNotBlank(deviceId)
				|| (null != organs && organs.length > 0)) {
			totalCount = deviceManager.deviceHistoryTotalCount(organs,
					deviceId, deviceType, alarmType, type, beginTime, endTime);

			list = deviceManager.listDeviceAlarmHistory(organs, deviceId,
					deviceType, alarmType, type, beginTime, endTime,
					startIndex, limit);
		}
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1277");
		dto.setMethod("List_Device_Alarm_History");
		dto.setMessage(totalCount[0] + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);
		Element allAlarm = new Element("AlarmAll");
		allAlarm.setText(totalCount[0] + "");
		root.addContent(allAlarm);
		Element trueAlarm = new Element("AlarmTrue");
		trueAlarm.setText(totalCount[1] + "");
		root.addContent(trueAlarm);
		Element falseAlarm = new Element("AlarmFalse");
		falseAlarm.setText(totalCount[2] + "");
		root.addContent(falseAlarm);
		for (DeviceOnlineHistroyVO vo : list) {
			Element deviceAlarm = ElementUtil.createElement("DeviceAlarm", vo);
			root.addContent(deviceAlarm);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Alarm_Status", cmd = "1278")
	@RequestMapping("/list_device_alarm_status.xml")
	public void listDeviceAlarmStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		String[] organs = null;
		if (StringUtils.isNotBlank(organId)) {
			organs = organManger.listOrganAllChildren(organId);
		}
		String alarmType = TypeDefinition.ALARM_TYPE_OFFLINE;
		String alarmTypeString = reader.getString("alarmType", true);
		if (StringUtils.isNotBlank(alarmTypeString)) {
			alarmType = alarmTypeString;
		}
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer startIndex = reader.getInteger("startIndex", true);
		Integer limit = reader.getInteger("limit", true);
		int[] totalCount = { 0, 0, 0 };
		int deviceCount = 0;
		List<DeviceAlarmStatusVO> list = new ArrayList<DeviceAlarmStatusVO>();
		if (null != organs && organs.length > 0) {
			totalCount = deviceManager.deviceHistoryTotalCount(organs, null,
					null, alarmType, null, beginTime, endTime);
			deviceCount = deviceManager.cameraByDeviceTotalCount(organs, null,
					null, null, null);
			list = deviceManager.listDeviceAlarmStatus(organs, beginTime,
					endTime, startIndex, limit);
		}
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1277");
		dto.setMethod("List_Device_Alarm_History");
		dto.setMessage(deviceCount + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);
		Element allAlarm = new Element("AlarmAll");
		allAlarm.setText(totalCount[0] + "");
		root.addContent(allAlarm);
		Element trueAlarm = new Element("AlarmTrue");
		trueAlarm.setText(totalCount[1] + "");
		root.addContent(trueAlarm);
		Element falseAlarm = new Element("AlarmFalse");
		falseAlarm.setText(totalCount[2] + "");
		root.addContent(falseAlarm);
		for (DeviceAlarmStatusVO vo : list) {
			Element deviceAlarm = ElementUtil.createElement("DeviceAlarm", vo);
			root.addContent(deviceAlarm);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Alarm_By_Device", cmd = "1279")
	@RequestMapping("/list_alarm_by_device.xml")
	public void listAlarmByDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String deviceId = reader.getString("deviceId", false);
		String alarmType = TypeDefinition.ALARM_TYPE_OFFLINE;
		String alarmTypeString = reader.getString("alarmType", true);
		if (StringUtils.isNotBlank(alarmTypeString)) {
			alarmType = alarmTypeString;
		}
		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		// 如果没有传入时间 ，则默认为1天
		if (null == beginTime) {
			beginTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		}
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer startIndex = reader.getInteger("startIndex", true);
		Integer limit = reader.getInteger("limit", true);

		int[] totalCount = deviceManager.deviceHistoryTotalCount(null,
				deviceId, null, alarmType, null, beginTime, endTime);

		List<DeviceOnlineHistroyVO> list = deviceManager.listAlarmByDevice(
				deviceId, alarmType, beginTime, endTime, startIndex, limit);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1277");
		dto.setMethod("List_Device_Alarm_History");
		dto.setMessage(totalCount[0] + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);
		Element allAlarm = new Element("AlarmAll");
		allAlarm.setText(totalCount[0] + "");
		root.addContent(allAlarm);
		Element trueAlarm = new Element("AlarmTrue");
		trueAlarm.setText(totalCount[1] + "");
		root.addContent(trueAlarm);
		Element falseAlarm = new Element("AlarmFalse");
		falseAlarm.setText(totalCount[2] + "");
		root.addContent(falseAlarm);
		for (DeviceOnlineHistroyVO vo : list) {
			Element deviceAlarm = ElementUtil.createElement("DeviceAlarm", vo);
			root.addContent(deviceAlarm);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Batch_Data_Device", cmd = "1280")
	@RequestMapping("/batch_data_device.xml")
	public void batchDataDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String type = requestRoot.getChildText("Type");
		Element devices = requestRoot.getChild("Devices");
		List<Element> device = devices.getChildren();
		deviceManager.batchSaveDataDevice(type, device);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1280");
		dto.setMethod("Batch_Data_Device");
		dto.setMessage("");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Batch_Create_Data_Device", cmd = "2067")
	@RequestMapping("/batch_create_data_device.json")
	public void batchCreateDataDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// Excel数据流
			InputStream in = null;
			// 机构ID
			String organId = "";
			// 设备类型
			String type = "";
			// 解析请求
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			// 获取License
			License license = licenseManager.getLicense();
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
					userManager.checkSession(sessionId);
				}

				if ("type".equals(fieldName)) {
					type = item.getString();
					if (StringUtils.isBlank(type)) {
						throw new BusinessException(
								ErrorCode.PARAMETER_NOT_FOUND, "missing [type]");
					}
				}

				if ("Filedata".equals(fieldName)) {
					uploadFlag = true;
					in = item.getInputStream();
				}
			}
			if (!uploadFlag) {
				throw new BusinessException(
						ErrorCode.MISSING_PARAMETER_FILEDATA,
						"Parameter [Filedata] not found !");
			}
			// 检测数据流
			Workbook wb = deviceManager.checkoutIo(in);
			// 火灾检测器
			if (type.equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
				List<FireDetector> fds = tmDeviceManager.readFireDetectorWb(wb,
						license);
				tmDeviceManager.batchInsertFd(fds);
			}
			// 照明回路
			else if (type.equals(TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
				List<ControlDeviceLight> cdls = tmDeviceManager
						.readControlDeviceLightWb(wb, license);
				tmDeviceManager.batchInsertLight(cdls);
			}
			// 水泵
			else if (type.equals(TypeDefinition.DEVICE_TYPE_WP + "")) {
				List<ControlDeviceWp> cdws = tmDeviceManager
						.readControlDeviceWpWb(wb, license);
				tmDeviceManager.batchInsertWp(cdws);
			}
			// 手动报警按钮
			else if (type.equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
				List<PushButton> pbs = tmDeviceManager.readPushButtonWb(wb,
						license);
				tmDeviceManager.batchInsertPb(pbs);
			}
			// 车道指示器
			else if (type.equals(TypeDefinition.DEVICE_TYPE_LIL + "")) {
				List<ControlDeviceLil> cdlis = tmDeviceManager
						.readControlDeviceLilWb(wb, license);
				tmDeviceManager.batchInsertLil(cdlis);
			}
			// 风机
			else if (type.equals(TypeDefinition.DEVICE_TYPE_FAN + "")) {
				List<ControlDeviceFan> cdfs = tmDeviceManager
						.readControlDeviceFanWb(wb, license);
				tmDeviceManager.batchInsertFan(cdfs);
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter type[" + type + "] invalid !");
			}
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2065");
		dto.setMethod("Batch_Create_Camera");

		writePage(response, dto);
	}

	// @InterfaceDescription(logon = false, method = "List_Organ_Camera", cmd =
	// "1810")
	// @RequestMapping("/list_organ_camera.xml")
	// public void listOrganCamera(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// Document document = RequestUtil.parseRequest(request);
	// Element reqRoot = document.getRootElement();
	// Element organElement = reqRoot.getChild("OrganId");
	// String organSN = organManger.getRootOrgan().getStandardNumber();
	// if (null != organElement) {
	// if (StringUtils.isNotBlank(organElement.getText())) {
	// organSN = organElement.getText();
	// }
	// }
	// Organ organ = organManger.findBySn(organSN);
	// // 机构设备map
	// Map<String, List<Camera>> map = deviceManager.listOrganCamera(organ
	// .getId());
	// // 构成机构设备树
	// ListOrganDeviceTreeVO result = deviceManager.organDeviceTree(
	// organ.getId(), map);
	// String json = JSONObject.fromObject(result).toString();
	// BaseDTO dto = new BaseDTO();
	// dto.setCmd("1810");
	// dto.setMethod("List_Organ_Camera");
	// dto.setMessage(organSN);
	// Document doc = new Document();
	// Element root = ElementUtil.createElement("Response", dto, null, null);
	// doc.setRootElement(root);
	// Element tree = new Element("Tree");
	// tree.setText(json);
	// root.addContent(tree);
	// writePageWithContentLengthGBK(response, doc);
	// }
}
