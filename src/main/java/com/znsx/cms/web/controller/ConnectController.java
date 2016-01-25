package com.znsx.cms.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.SubPlatformResourceComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.md5.MD5Util;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 平台互联外部接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-17 上午9:10:26
 */
@Controller
public class ConnectController extends BaseController {
	@Autowired
	private ConnectManager connectManager;
	@Autowired
	private OrganManager organManager;
	@Autowired
	private UserManager userManager;

	@InterfaceDescription(logon = true, method = "List_Sub_Platform", cmd = "1025")
	@RequestMapping("/list_sub_platform.xml")
	public void listSubPlatform(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<SubPlatform> list = connectManager.listSubPlatform();

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1025");
		dto.setMethod("List_Sub_Platform");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		List<String> excludeProperties = new ArrayList<String>();
		excludeProperties.add("sipip");
		excludeProperties.add("sipport");
		excludeProperties.add("updatetime");
		for (SubPlatform sp : list) {
			Element platform = ElementUtil.createElement("Platform", sp, null,
					excludeProperties);
			root.addContent(platform);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Tree_Sub_Platform", cmd = "1026")
	@RequestMapping("/tree_sub_platform.xml")
	public void treeSubPlatform(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		boolean isRec = false;
		String recursion = request.getParameter("recursion");
		if ("1".equals(recursion)) {
			isRec = true;
		}

		ResourceVO user = resource.get();

		SubPlatform platform = connectManager.getSubPlatform(id);
		SubPlatformResource rootResource = connectManager
				.getSubPlatformResourceBySN(platform.getStandardNumber());

		Collection<SubPlatformResource> subResources = connectManager
				.listPlatformResource(rootResource.getId(), user.getId());
		// 排序
		List<SubPlatformResource> list = new ArrayList<SubPlatformResource>(
				subResources);
		Collections.sort(list, new SubPlatformResourceComparator());

		Element node = connectManager.treeSubPlatform(rootResource.getId(),
				list, isRec);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1026");
		dto.setMethod("Tree_Sub_Platform");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		root.addContent(node);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Register", cmd = "1027")
	@RequestMapping("/register.xml")
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		String method = reqRoot.getAttributeValue("Method");
		if (!"Register".equals(method)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Request Method[" + method + "] not support !");
		}

		String standardNumber = reqRoot.getChildText("StandardNumber");
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		String name = reqRoot.getChildText("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name]");
		}

		String ip = reqRoot.getChildText("IP");
		if (StringUtils.isBlank(ip)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [IP]");
		}

		Integer port = null;
		String portString = reqRoot.getChildText("Port");
		if (StringUtils.isBlank(portString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Port]");
		} else {
			try {
				port = Integer.parseInt(portString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Port[" + portString + "] invalid !");
			}
		}

		connectManager.acceptRegister(standardNumber, name, ip, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1027");
		dto.setMethod("Register");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Push_Resource", cmd = "1028")
	@RequestMapping("/push_resource.xml")
	public void pushResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		String method = reqRoot.getAttributeValue("Method");
		if (!"Push_Resource".equals(method)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Request Method[" + method + "] not support !");
		}

		Element node = reqRoot.getChild("Node");

		// // 首先移除该平台下方旧的资源数据
		// String standardNumber = node.getAttributeValue("StandardNumber");
		// connectManager.deleteByPlatform(standardNumber);
		// 添加新的资源数据
		connectManager.acceptResource(node);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1028");
		dto.setMethod("Push_Resource");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Vd_Data_Report", cmd = "1300")
	@RequestMapping("/vd_data_report.xml")
	public void vdDataReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();

		// 获取平台SN
		Element header = reqRoot.getChild("Header");
		if (null == header) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Header]");
		}
		Element sendOrgan = header.getChild("SendOrgan");
		if (null == sendOrgan) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}
		String organSN = sendOrgan.getText();
		if (StringUtils.isBlank(organSN)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}

		// 解析上报数据
		Element body = reqRoot.getChild("Body");
		if (null == body) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Body]");
		}
		List<Element> rows = body.getChildren("Row");

		// 保存数据
		connectManager.saveReportData(rows, organSN,
				TypeDefinition.TABLE_NAME_VD);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1300");
		dto.setMethod("Vd_Data_Report");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Wst_Data_Report", cmd = "1301")
	@RequestMapping("/wst_data_report.xml")
	public void wstDataReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();

		// 获取平台SN
		Element header = reqRoot.getChild("Header");
		if (null == header) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Header]");
		}
		Element sendOrgan = header.getChild("SendOrgan");
		if (null == sendOrgan) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}
		String organSN = sendOrgan.getText();
		if (StringUtils.isBlank(organSN)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}

		// 解析上报数据
		Element body = reqRoot.getChild("Body");
		if (null == body) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Body]");
		}
		List<Element> rows = body.getChildren("Row");

		// 保存数据
		connectManager.saveReportData(rows, organSN,
				TypeDefinition.TABLE_NAME_WST);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1301");
		dto.setMethod("Wst_Data_Report");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Covi_Data_Report", cmd = "1302")
	@RequestMapping("/covi_data_report.xml")
	public void coviDataReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();

		// 获取平台SN
		Element header = reqRoot.getChild("Header");
		if (null == header) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Header]");
		}
		Element sendOrgan = header.getChild("SendOrgan");
		if (null == sendOrgan) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}
		String organSN = sendOrgan.getText();
		if (StringUtils.isBlank(organSN)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SendOrgan]");
		}

		// 解析上报数据
		Element body = reqRoot.getChild("Body");
		if (null == body) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Body]");
		}
		List<Element> rows = body.getChildren("Row");

		// 保存数据
		connectManager.saveReportData(rows, organSN,
				TypeDefinition.TABLE_NAME_COVI);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1302");
		dto.setMethod("Covi_Data_Report");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Push_Resource_28059", cmd = "1306")
	@RequestMapping("/push_resource_28059.xml")
	public void pushResource28059(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();

		String variable = reqRoot.getChildText("Variable");
		if (!"Catalog".equals(variable)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Request Variable[" + variable + "] not support !");
		}

		SubPlatformResource parent = new SubPlatformResource();
		parent.setName(reqRoot.getChildText("Name"));
		parent.setStandardNumber(reqRoot.getChildText("Coding"));

		List<SubPlatformResource> items = new LinkedList<SubPlatformResource>();
		Element sublist = reqRoot.getChild("Sublist");
		List<Element> children = sublist.getChildren();
		for (Element child : children) {
			SubPlatformResource item = new SubPlatformResource();
			item.setAuth(child.getChildText("Privilege"));
			// item.setLatitude(child.getChildText("Latitude"));
			// item.setLongitude(child.getChildText("Longitude"));
			item.setName(child.getChildText("Name"));
			item.setRoadName(child.getChildText("Roadway"));
			item.setStakeNumber(child.getChildText("PileNo"));
			item.setStandardNumber(child.getChildText("Coding"));
			item.setType(child.getChildText("Type"));
			item.setUpdateTime(System.currentTimeMillis());
			item.setNavigation(child.getChildText("Navigation"));
			try {
				item.setWidth(NumberUtil.getInteger(child.getChildText("Width")));
			} catch (NumberFormatException e) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Width[" + child.getChildText("Width")
								+ "] invaliad!");
			}
			try {
				item.setHeight(NumberUtil.getInteger(child
						.getChildText("Height")));
			} catch (NumberFormatException e) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Height[" + child.getChildText("Height")
								+ "] invaliad!");
			}
			items.add(item);
		}

		connectManager.pushResource28059(parent, items);
		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1306");
		dto.setMethod("Push_Resource_28059");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Catalog_28181", cmd = "1307")
	@RequestMapping("/catalog_28181.xml")
	public void catalog28181(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();
		String deviceId = requestRoot.getChildText("DeviceID");
		Organ rootOrgan = null;
		if (StringUtils.isBlank(deviceId)) {
			rootOrgan = organManager.getRootOrgan();
		} else {
			rootOrgan = organManager.findOrganResource(deviceId);
		}

		// 默认级联查询下级资源
		String cascadeString = requestRoot.getChildText("Cascade");
		boolean cascade = true;
		if (StringUtils.isNotBlank(cascadeString) && "0".equals(cascadeString)) {
			cascade = false;
		}
		// 暂不处理时间条件
		// String startTime = requestRoot.getChildText("StartTime");
		// String endTime = requestRoot.getChildText("EndTime");

		Element element = connectManager.catalog28181(
				rootOrgan.getStandardNumber(), cascade);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1307");
		dto.setMethod("Catalog_28181");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element deviceID = new Element("DeviceID");
		deviceID.setText(rootOrgan.getStandardNumber());
		root.addContent(deviceID);

		Element name = new Element("Name");
		name.setText(rootOrgan.getName());
		root.addContent(name);

		Element sumNum = new Element("SumNum");
		sumNum.setText(element.getAttributeValue("Num"));
		root.addContent(sumNum);

		root.addContent(element);

		writePageWithContentLengthGBK(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Update_Catalog_28181", cmd = "1308")
	@RequestMapping("/update_catalog_28181.xml")
	public void updateCatalog28181(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();
		ResourceVO ccs = resource.get();

		String deviceID = requestRoot.getChildText("DeviceID");
		if (StringUtils.isBlank(deviceID)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [DeviceID]");
		}

		String name = requestRoot.getChildText("Name");

		String model = requestRoot.getChildText("Model");
		String owner = requestRoot.getChildText("Owner");
		Integer parental = NumberUtil.getInteger(requestRoot
				.getChildText("Parental"));
		String civilCode = requestRoot.getChildText("CivilCode");
		String address = requestRoot.getChildText("Address");
		Integer registerWay = NumberUtil.getInteger(requestRoot
				.getChildText("RegisterWay"));
		Integer secrecy = NumberUtil.getInteger(requestRoot
				.getChildText("Secrecy"));
		String manufacturer = requestRoot.getChildText("Manufacturer");

		Element deviceList = requestRoot.getChild("DeviceList");
		if (null == deviceList) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [DeviceList]");
		}

		Integer num = ElementUtil.getInteger(deviceList, "Num");

		List<Element> items = deviceList.getChildren();
		if (items.size() != num.intValue()) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"parameter [Num=" + num.intValue()
							+ "], but Item number is : " + items.size());
		}

		// 保存数据库
		connectManager.updateCatalog28181(items, deviceID, ccs.getId(), name,
				model, owner, parental, civilCode, address, registerWay,
				secrecy, manufacturer);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1308");
		dto.setMethod("Update_Catalog_28181");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Authenticate", cmd = "3023")
	@RequestMapping("/authenticate.xml")
	public void authenticate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();

		Element authorization = reqRoot.getChild("Authorization");
		String userName = authorization.getAttributeValue("Username");
		String realm = authorization.getAttributeValue("Realm");
		String nonce = authorization.getAttributeValue("Nonce");
		String uri = authorization.getAttributeValue("Uri");
		String passwd = authorization.getAttributeValue("Response");

		User user = userManager.getUserBySn(userName);
		// 计算密码
		String ha1 = MD5Util.MD5(userName + ":" + realm + ":"
				+ user.getPassword());
		String ha2 = MD5Util.MD5("REGISTER:" + uri);
		String myPasswd = MD5Util.MD5(ha1 + ":" + nonce + ":" + ha2);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3023");
		dto.setMethod("Authenticate");
		// 鉴权失败
		if (!myPasswd.equals(passwd)) {
			dto.setCode(ErrorCode.PASSWORD_ERROR);
			dto.setMessage("User[" + userName + "] authenticate failed ! ha1["
					+ ha1 + "] ha2[" + ha2 + "] Response[" + passwd + "] mine["
					+ myPasswd + "] !");
		}
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Catalog_28181_By_User", cmd = "1309")
	@RequestMapping("/catalog_28181_by_user.xml")
	public void catalog28181ByUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		String userSn = reqRoot.getChildText("User");
		if (StringUtils.isBlank(userSn)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [User]");
		}

		Element element = connectManager.catalog28181ByUser(userSn);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1309");
		dto.setMethod("Catalog_28181_By_User");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		root.addContent(element);

		writePageWithContentLengthGBK(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Update_Sub_Platform", cmd = "1310")
	@RequestMapping("/update_sub_platform.xml")
	public void updateSubPlatform(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		List<Element> organs = reqRoot.getChildren("Organ");
		List<String> sns = new LinkedList<String>();
		for (Element e : organs) {
			sns.add(e.getAttributeValue("StandardNumber"));
		}
		connectManager.updateSubPlatform(sns);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Update_Sub_Platform");
		dto.setCmd("1310");
		dto.setCode(ErrorCode.SUCCESS);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Update_Lower_Device_Status", cmd = "1311")
	@RequestMapping("/update_lower_device_status.xml")
	public void updateLowerDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		Element organ = reqRoot.getChild("Organ");
		String organSN = organ.getText();
		if (StringUtils.isBlank(organSN)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organSN]");
		}
		Element list = reqRoot.getChild("Devices");
		List<Element> devices = list.getChildren("Device");
		List<DeviceOnlineReal> deviceOnlineReals = new LinkedList<DeviceOnlineReal>();
		for (Element e : devices) {
			DeviceOnlineReal deviceOnlineReal = new DeviceOnlineReal();
			deviceOnlineReal.setStandardNumber(e
					.getAttributeValue("StandardNumber"));
			deviceOnlineReal.setOnlineTime(Long.parseLong(e
					.getAttributeValue("OnlineTime")));
			deviceOnlineReal.setUpdateTime(Long.parseLong(e
					.getAttributeValue("UpdateTime")));
			deviceOnlineReals.add(deviceOnlineReal);
		}
		connectManager.updateLowerDeviceStatus(organSN, deviceOnlineReals);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Update_Lower_Device_Status");
		dto.setCmd("1311");
		dto.setCode(ErrorCode.SUCCESS);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}
}
