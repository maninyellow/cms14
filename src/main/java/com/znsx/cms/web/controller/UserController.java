package com.znsx.cms.web.controller;

import java.util.ArrayList;
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
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.LogOperation;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.LicenseManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.AndroidUpdate;
import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.service.model.GisLogonVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.UserLogonVO;
import com.znsx.cms.service.model.UserOperationLogVO;
import com.znsx.cms.service.model.UserViewVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.cs.ListUserSessionHistoryDTO;
import com.znsx.cms.web.dto.omc.GetUserDTO;
import com.znsx.cms.web.dto.omc.ListLogOperationDTO;
import com.znsx.cms.web.dto.omc.ListOnlineUsersDTO;
import com.znsx.cms.web.dto.omc.ListSysLogDTO;
import com.znsx.cms.web.dto.omc.ListUserDTO;
import com.znsx.cms.web.dto.omc.UserLoginDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;

/**
 * UserController
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:32:33
 */

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserManager userManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private SysLogManager sysLogManager;

	@InterfaceDescription(logon = false, method = "Login", cmd = "1000")
	@RequestMapping("/user_login.xml")
	public void csLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userName = request.getParameter("userName");
		String passwd = request.getParameter("passwd");
		if (StringUtils.isBlank(userName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userName]");
		}
		if (StringUtils.isBlank(passwd)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [passwd]");
		}

		// 校验License
		License license = licenseManager.getLicense();
		licenseManager.checkLicense(license);

		// 中心服务器接收请求的IP地址，在集群或者端口映射环境下，要求客户端传递这个ip参数，否则获取到的始终是内网IP
		String centerIp = request.getParameter("centerIp");

		// 客户端的IP
		String remoteIp = request.getParameter("remoteIp");
		if (StringUtils.isBlank(remoteIp)) {
			remoteIp = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isBlank(remoteIp)) {
			remoteIp = request.getRemoteAddr();
		}
		System.out.println("client remote ip is : " + remoteIp);

		// 生成Session
		String sessionId = userManager.csLogin(userName, passwd, remoteIp,
				TypeDefinition.CLIENT_TYPE_CS);

		// 用户登录基本信息
		UserLogonVO user = null;
		// 采取计算客户端IP地址和中心以及CCS掩码方式返回
		if (StringUtils.isBlank(centerIp)) {
			user = userManager.getUserByName(userName, remoteIp);
		}
		// 根据中心config.properties配置的路由信息返回CCS的IP
		else {
			System.out.println("request centerIp is : " + centerIp);
			user = userManager.getUserUseConfig(userName, centerIp);
		}

		// 登录后用户关联的视图权限
		UserViewVO uv = userManager.getPermissionsByUserId(user.getId());

		AndroidUpdate android = userManager.getAndroidConfig();

		UserViewVO auth = new UserViewVO();
		auth.setVideo(uv.getVideo());
		auth.setGis(uv.getGis());
		auth.setAlarm(uv.getAlarm());
		auth.setDisplay(uv.getDisplay());

		Document doc = new Document();
		List<String> list = new ArrayList<String>();
		Element root = new Element("Response");
		root.setAttribute("Cmd", "1000");
		root.setAttribute("Method", "Login");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");
		doc.setRootElement(root);

		Element sessionIdXml = new Element("SessionId");
		root.addContent(sessionIdXml);
		sessionIdXml.setText(sessionId);

		Element ccs = new Element("CCS");
		root.addContent(ccs);
		Element ipXml = new Element("IP");
		ipXml.setText(user.getIp());
		ccs.addContent(ipXml);
		Element port = new Element("Port");
		port.setText(user.getPort());
		ccs.addContent(port);
		Element telnetPort = new Element("TelnetPort");
		telnetPort.setText(user.getTelnetPort() != null ? user.getTelnetPort()
				: "");
		ccs.addContent(telnetPort);

		list.clear();
		list.add("ccsId".toLowerCase());
		list.add("ip");
		list.add("port");
		Element u = ElementUtil.createElement("User", user, null, list);
		root.addContent(u);

		Element authElement = new Element("Auth");
		root.addContent(authElement);
		Element video = new Element("Video");
		video.setText(auth.getVideo());
		authElement.addContent(video);
		Element gis = new Element("GIS");
		gis.setText(auth.getGis());
		authElement.addContent(gis);
		Element alarm = new Element("Alarm");
		alarm.setText(auth.getAlarm());
		authElement.addContent(alarm);
		Element display = new Element("Display");
		display.setText(auth.getDisplay());
		authElement.addContent(display);

		Element androidXml = new Element("Android");
		root.addContent(androidXml);

		Element packageXml = new Element("Package");
		androidXml.addContent(packageXml);
		Element nameXml = new Element("Name");
		nameXml.setText("android.app.mcu");
		packageXml.addContent(nameXml);
		Element versionXml = new Element("Version");
		versionXml.setText(android.getApkVersion());
		packageXml.addContent(versionXml);
		Element addressXml = new Element("Address");
		addressXml.setText(android.getDownloadAddress());
		packageXml.addContent(addressXml);

		Element packageXml1 = new Element("Package");
		androidXml.addContent(packageXml1);
		Element nameXml1 = new Element("Name");
		nameXml1.setText("android.app.qh");
		packageXml1.addContent(nameXml1);
		Element versionXml1 = new Element("Version");
		versionXml1.setText(android.getApkVersion());
		packageXml1.addContent(versionXml1);
		Element addressXml1 = new Element("Address");
		addressXml1.setText(android.getDownloadAddress());
		packageXml1.addContent(addressXml1);

		Element svcXml = new Element("SVC");
		root.addContent(svcXml);
		Element svcVersionXml = new Element("Version");
		svcVersionXml.setText("1.2.11.7850");
		svcXml.addContent(svcVersionXml);
		Element downloadAddressXml = new Element("DownloadAddress");
		downloadAddressXml
				.setText("http://192.168.1.7:8181/download/file/mcu.apk");
		svcXml.addContent(downloadAddressXml);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "User_Login", cmd = "2000")
	@RequestMapping("/user_login.json")
	public void omcLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("userName");
		if (StringUtils.isBlank(loginName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userName]");
		}
		String passwd = request.getParameter("passwd");
		if (StringUtils.isBlank(passwd)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [passwd]");
		}
		String ip = request.getParameter("ip");
		if (StringUtils.isBlank(ip)) {
			if (StringUtils.isNotBlank(request.getHeader("X-Forwarded-For"))) {
				ip = request.getHeader("X-Forwarded-For");
			} else {
				ip = request.getRemoteAddr();
			}
		}

		// // 校验License
		// licenseManager.checkLicense(license);

		UserLoginDTO dto = userManager.omcLogin(loginName, passwd, ip);
		dto.setCmd("2000");
		dto.setMethod("User_Login");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Favorite", cmd = "1004")
	@RequestMapping("/update_favorite.xml")
	public void updateFavorite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		Document doc = (Document) request.getAttribute("RequestDocument");
		Element root = doc.getRootElement();
		// String sessionId = root.getChildText("SessionId");
		Element favorite = root.getChild("Favorite");
		if (null == favorite) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [favorite]");
		}

		String favoriteName = favorite.getAttributeValue("Name");
		if (null != favoriteName && StringUtils.isBlank(favoriteName)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		String favoriteId = favorite.getAttributeValue("Id");
		if (StringUtils.isBlank(favoriteId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [favoriteId]");
		}
		List<Element> channel = favorite.getChildren("Channel");
		List<String> channelIds = new ArrayList<String>();
		if (channel.size() > 0) {
			for (int i = 0; i < channel.size(); i++) {
				String s = channel.get(i).getAttributeValue("Id");
				channelIds.add(s);
			}
		}
		String userId = resource.get().getId();
		userManager
				.updateFavorite(favoriteId, favoriteName, channelIds, userId);

		dto.setMethod("Update_Favorite");
		dto.setCmd("1004");
		Document docXml = new Document();
		Element rootXml = ElementUtil
				.createElement("Response", dto, null, null);
		docXml.setRootElement(rootXml);

		writePageWithContentLength(response, docXml);
	}

	@InterfaceDescription(logon = true, method = "Delete_Favorite", cmd = "1005")
	@RequestMapping("/delete_favorite.xml")
	public void deleteFavorite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String favoriteId = request.getParameter("favoriteId");
		if (StringUtils.isBlank(favoriteId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [favoriteId]");
		}
		userManager.deleteFavorite(favoriteId);
		dto.setMethod("Delete_Favorite");
		dto.setCmd("1005");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Favorite", cmd = "1002")
	@RequestMapping("/list_favorite.xml")
	public void listFavorite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String userId = resource.get().getId();

		Element element = userManager.listFavorite(userId);

		Document doc = new Document();
		dto.setMethod("List_Favorite");
		dto.setCmd("1002");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.addContent(element);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_User", cmd = "2002")
	@RequestMapping("/create_user.json")
	public void createUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String ccsId = request.getParameter("ccsId");
		if (StringUtils.isBlank(ccsId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [ccsId]");
		}

		String logonName = request.getParameter("logonName");
		if (StringUtils.isBlank(logonName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [logonName]");
		}

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String password = request.getParameter("password");
		if (StringUtils.isBlank(password)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [password]");
		}

		Short sex = new Short("0");
		String sexString = request.getParameter("sex");
		if (StringUtils.isNotBlank(sexString)) {
			try {
				sex = Short.parseShort(sexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter sex[" + sexString + "] invalid !");
			}
		}

		String email = request.getParameter("email");

		String phone = request.getParameter("phone");

		String address = request.getParameter("address");

		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		Short priority = new Short("0");
		String priorityString = request.getParameter("priority");
		if (StringUtils.isNotBlank(priorityString)) {
			try {
				priority = Short.parseShort(priorityString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + priorityString + "] invalid !");
			}
		}

		String note = request.getParameter("note");

		Integer maxConnect = 1;
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

		// 获取License的用户数量限制,校验能否创建
		License license = licenseManager.getLicense();
		int userCount = userManager.countUser();
		if (userCount >= Integer.parseInt(license.getUserAmount())) {
			throw new BusinessException(ErrorCode.USER_AMOUNT_LIMIT,
					"User amount over license limit !");
		}

		// 自动生成标准编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("User", organId);
		}

		// 创建用户
		String userId = userManager.createUser(name, standardNumber, ccsId,
				logonName, password, sex, email, phone, address, organId,
				priority, note, maxConnect);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2002");
		dto.setMethod("Create_User");
		dto.setMessage(userId);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_User", cmd = "2003")
	@RequestMapping("/update_user.json")
	public void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String standardNumber = request.getParameter("standardNumber");

		String ccsId = request.getParameter("ccsId");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		String password = request.getParameter("password");

		Short sex = null;
		String sexString = request.getParameter("sex");
		if (StringUtils.isNotBlank(sexString)) {
			try {
				sex = Short.parseShort(sexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter sex[" + sexString + "] invalid !");
			}
		}

		String email = request.getParameter("email");

		String phone = request.getParameter("phone");

		String address = request.getParameter("address");

		String organId = request.getParameter("organId");

		Short priority = null;
		String priorityString = request.getParameter("priority");
		if (StringUtils.isNotBlank(priorityString)) {
			try {
				priority = Short.parseShort(priorityString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + priorityString + "] invalid !");
			}
		}

		String note = request.getParameter("note");

		Short status = null;
		String statusString = request.getParameter("status");
		if (StringUtils.isNotBlank(statusString)) {
			try {
				status = Short.parseShort(statusString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter status[" + statusString + "] invalid !");
			}
		}

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

		userManager.updateUser(id, name, standardNumber, ccsId, password, sex,
				email, phone, address, organId, priority, note, status,
				maxConnect);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2003");
		dto.setMethod("Update_User");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_User", cmd = "2004")
	@RequestMapping("/delete_user.json")
	public void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		userManager.deleteUser(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2004");
		dto.setMethod("Delete_User");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Play_Scheme", cmd = "1007")
	@RequestMapping("/create_play_scheme.xml")
	public void createPlayScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Document doc = (Document) request.getAttribute("RequestDocument");

		Element root = doc.getRootElement();
		Element playScheme = root.getChild("PlayScheme");
		if (null == playScheme) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [playScheme]");
		}

		String playSchemeName = playScheme.getAttributeValue("Name");
		if (StringUtils.isBlank(playSchemeName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [playSchemeName]");
		}
		String userId = resource.get().getId();
		String id = userManager.createPlayScheme(playSchemeName, playScheme,
				userId);

		Document docXML = new Document();
		Element rootXML = new Element("Response");
		rootXML.setAttribute("Method", "Create_Play_Scheme");
		rootXML.setAttribute("Cmd", "1007");
		rootXML.setAttribute("Code", ErrorCode.SUCCESS);
		rootXML.setAttribute("Message", "");
		docXML.addContent(rootXML);

		Element element = new Element("PlayScheme");
		element.setAttribute("Id", id);
		rootXML.addContent(element);

		writePageWithContentLength(response, docXML);
	}

	@InterfaceDescription(logon = true, method = "Create_Favorite", cmd = "1003")
	@RequestMapping("/create_favorite.xml")
	public void createFavorite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Document doc = (Document) request.getAttribute("RequestDocument");
		Element root = doc.getRootElement();
		// String sessionId = root.getChildText("SessionId");
		Element favorite = root.getChild("Favorite");
		String favoriteName = favorite.getAttributeValue("Name");
		if (StringUtils.isBlank(favoriteName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [favoriteName]");
		}
		List<Element> channel = favorite.getChildren("Channel");
		List<String> channelIds = new ArrayList<String>();
		if (channel.size() > 0) {
			for (int i = 0; i < channel.size(); i++) {
				String s = channel.get(i).getAttributeValue("Id");
				channelIds.add(s);
			}
		}

		String userId = resource.get().getId();

		String favoriteId = userManager.createFavorite(favoriteName,
				channelIds, userId);

		Document docXml = new Document();
		Element rootXml = new Element("Response");
		rootXml.setAttribute("Method", "Create_Favorite");
		rootXml.setAttribute("Cmd", "1003");
		rootXml.setAttribute("Code", ErrorCode.SUCCESS);
		rootXml.setAttribute("Message", "");
		docXml.setRootElement(rootXml);

		Element favoriteXml = new Element("Favorite");
		favoriteXml.setAttribute("Id", favoriteId);
		rootXml.addContent(favoriteXml);

		writePageWithContentLength(response, docXml);
	}

	@InterfaceDescription(logon = true, method = "Update_Play_Scheme", cmd = "1008")
	@RequestMapping("/update_play_scheme.xml")
	public void updatePlayScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();

		Document doc = (Document) request.getAttribute("RequestDocument");

		Element root = doc.getRootElement();
		Element playScheme = root.getChild("PlayScheme");
		if (null == playScheme) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [playScheme]");
		}

		String playSchemeId = playScheme.getAttributeValue("Id");
		if (StringUtils.isBlank(playSchemeId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [playSchemeId]");
		}

		String playSchemeName = playScheme.getAttributeValue("Name");
		if (null != playSchemeName && StringUtils.isBlank(playSchemeName)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		String userId = resource.get().getId();
		userManager.updatePlayScheme(playSchemeId, playSchemeName, playScheme,
				userId);
		dto.setMethod("Update_Play_Scheme");
		dto.setCmd("1008");
		Document docXML = new Document();
		Element element = ElementUtil
				.createElement("Response", dto, null, null);
		docXML.addContent(element);
		writePageWithContentLength(response, docXML);
	}

	@InterfaceDescription(logon = true, method = "Delete_Play_Scheme", cmd = "1009")
	@RequestMapping("/delete_play_scheme.xml")
	public void deletePlayScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("playSchemeId");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [playSchemeId]");
		}
		userManager.deletePlayScheme(id);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Delete_Play_Scheme");
		dto.setCmd("1009");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Play_Scheme", cmd = "1006")
	@RequestMapping("/list_play_scheme.xml")
	public void listPlayScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String userId = resource.get().getId();

		Element element = userManager.listPlayScheme(userId);

		dto.setCmd("1006");
		dto.setMethod("List_Play_Scheme");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.addContent(root);
		root.addContent(element);
		writePageWithContentLength(response, doc);

	}

	@InterfaceDescription(logon = true, method = "Get_User", cmd = "2006")
	@RequestMapping("/get_user.json")
	public void getUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetUserVO user = userManager.getUser(id);

		GetUserDTO dto = new GetUserDTO();
		dto.setCmd("2006");
		dto.setMethod("Get_User");
		dto.setUser(user);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_User", cmd = "2005")
	@RequestMapping("/list_user.json")
	public void listUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");

		String name = request.getParameter("name");
		String logonName = request.getParameter("logonName");
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

		Integer totalCount = userManager.userTotalCount(organId, name,
				logonName);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<GetUserVO> userList = userManager.listUser(organId, name,
				logonName, startIndex, limit);

		ListUserDTO dto = new ListUserDTO();
		dto.setCmd("2005");
		dto.setMethod("List_User");
		dto.setUserList(userList);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Online_User", cmd = "2020")
	@RequestMapping("/list_online_user.json")
	public void listOnlineUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		Integer startIndex = 0;
		String startIndexSatring = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexSatring)) {
			try {
				startIndex = Integer.parseInt(startIndexSatring);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter startIndex[" + startIndexSatring
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

		String logonName = request.getParameter("logonName");

		String name = request.getParameter("name");

		int totalCount = userManager.countOnlineUser(organId, logonName, name);

		// omc客户端需要删除一页的最后一条时查询前一页的数据
		if (startIndex != 0 && totalCount != 0) {
			if (startIndex.intValue() >= totalCount) {
				startIndex -= ((startIndex.intValue() - totalCount) / limit + 1)
						* limit;
			}
		}

		List<ListOnlineUsersVO> list = userManager.listOnlineUser(organId,
				logonName, name, startIndex, limit);

		ListOnlineUsersDTO dto = new ListOnlineUsersDTO();
		dto.setUserList(list);
		dto.setTotalCount(totalCount + "");

		dto.setCmd("2020");
		dto.setMethod("List_Online_User");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_User_Session", cmd = "2024")
	@RequestMapping("/list_user_session.json")
	public void listUserSession(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String organId = request.getParameter("organId");

		Long startTime = null;
		String startTimeString = request.getParameter("startTime");
		if (StringUtils.isNotBlank(startTimeString)) {
			try {
				startTime = Long.parseLong(startTimeString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + startTimeString + "] invalid !");
			}
		}
		Long endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isNotBlank(endTimeString)) {
			try {
				endTime = Long.parseLong(endTimeString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + endTimeString + "] invalid !");
			}
		}
		Integer startIndex = 0;
		String startIndexSatring = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexSatring)) {
			try {
				startIndex = Integer.parseInt(startIndexSatring);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + startIndexSatring
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
		String logonUserId = resource.get().getId();

		Integer totalCount = userManager.selectTotalCount(userId, userName,
				organId, startTime, endTime, logonUserId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<ListUserSessionHistoryVO> listVO = userManager
				.listUserSessionHistory(userId, userName, organId, startTime,
						endTime, startIndex, limit, logonUserId);

		ListUserSessionHistoryDTO dto = new ListUserSessionHistoryDTO();
		dto.setUserSessionList(listVO);
		dto.setTotalCount(totalCount.toString());
		dto.setCmd("2024");
		dto.setMethod("List_User_Session");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "User_Logoff", cmd = "2007")
	@RequestMapping("/user_logoff.json")
	public void omcUserLogoff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionId = request.getParameter("sessionId");
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [sessionId]");
		}
		userManager.userLogoff(sessionId);
		BaseDTO dto = new BaseDTO();
		request.getSession().removeAttribute("sessionId");
		dto.setCmd("2007");
		dto.setMethod("User_Logoff");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "User_Logoff", cmd = "1016")
	@RequestMapping("/user_logoff.xml")
	public void csUserLogoff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionId = request.getParameter("sessionId");
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [sessionId]");
		}
		userManager.userLogoff(sessionId);
		BaseDTO dto = new BaseDTO();
		request.getSession().removeAttribute("sessionId");
		dto.setCmd("1016");
		dto.setMethod("User_Logoff");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);

	}

	@InterfaceDescription(logon = false, method = "List_Log_Operation", cmd = "2120")
	@RequestMapping("/list_log_operation.json")
	public void listLogOperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<LogOperation> logOperations = userManager.listLogOperation();
		ListLogOperationDTO dto = new ListLogOperationDTO();
		dto.setCmd("2120");
		dto.setMethod("List_Log_Operation");
		dto.setListLogOperation(logOperations);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Sys_Log", cmd = "2121")
	@RequestMapping("/list_sys_log.json")
	public void listSysLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resourceName = request.getParameter("resourceName");
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}
		Long startTime = null;
		String startTimeString = request.getParameter("startTime");
		if (StringUtils.isNotBlank(startTimeString)) {
			try {
				startTime = Long.parseLong(startTimeString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + startTimeString + "] invalid !");
			}
		}
		Long endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isNotBlank(endTimeString)) {
			try {
				endTime = Long.parseLong(endTimeString);
			} catch (NumberFormatException be) {
				be.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter priority[" + endTimeString + "] invalid !");
			}
		}
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
		String targetName = request.getParameter("targetName");

		String operationCode = request.getParameter("operationCode");

		String operationType = request.getParameter("operationType");

		String resourceType = request.getParameter("resourceType");

		String logonUserId = resource.get().getId();

		String type = request.getParameter("type");

		List<SysLog> listLog = new ArrayList<SysLog>();

		Integer totalCount = userManager.findTotalCount(organId, resourceName,
				startTime, endTime, targetName, operationCode, operationType,
				resourceType, type);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		// 判断是否是根机构用户
		// Boolean isAdmin = false;
		// isAdmin = userManager.isAdmin(resource.get().getId());
		// if (isAdmin) {
		// listLog = userManager.listSysLogByAdmin(resourceName, startTime,
		// endTime, startIndex, limit, targetName, operationCode,
		// operationType, resourceType);
		// } else {
		listLog = userManager.listSysLog(organId, resourceName, startTime,
				endTime, startIndex, limit, targetName, operationCode,
				logonUserId, operationType, resourceType, type);
		// }

		ListSysLogDTO dto = new ListSysLogDTO();
		dto.setCmd("2121");
		dto.setMethod("List_Sys_Log");
		dto.setTotalCount(totalCount.toString());
		dto.setListSysLog(listLog);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Online_User_By_User_Id", cmd = "2021")
	@RequestMapping("/list_online_user_by_user_id.json")
	public void listOnlineUserByUserId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userId]");
		}
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

		int totalCount = userManager.countOnlineUserByUserId(userId);

		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount != 0) {
			if (startIndex.intValue() >= totalCount) {
				startIndex -= ((startIndex.intValue() - totalCount) / limit + 1)
						* limit;
			}
		}

		List<ListOnlineUsersVO> list = userManager.listOnlineUserByUserId(
				userId, startIndex, limit);

		ListOnlineUsersDTO dto = new ListOnlineUsersDTO();
		dto.setUserList(list);
		dto.setTotalCount(totalCount + "");

		dto.setCmd("2021");
		dto.setMethod("List_Online_User_By_User_Id");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Heartbeat", cmd = "2022")
	@RequestMapping("/heartbeat.json")
	public void heartbeat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 切面已经完成更新操作，因此只需要返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2022");
		dto.setMethod("Heartbeat");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_User_Info", cmd = "1022")
	@RequestMapping("/update_user_info.xml")
	public void updateUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userId]");
		}

		String password = request.getParameter("password");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		Short sex = null;
		String sexString = request.getParameter("sex");
		if (StringUtils.isNotBlank(sexString)) {
			try {
				sex = Short.parseShort(sexString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter sex[" + sexString + "] invalid !");
			}
		}

		String email = request.getParameter("email");

		String phone = request.getParameter("phone");

		String address = request.getParameter("address");

		userManager.updateUserInfo(userId, password, name, sex, email, phone,
				address);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1022");
		dto.setMethod("Update_User_Info");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Sys_Log", cmd = "1021")
	@RequestMapping("/create_sys_log.xml")
	public void createSysLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targetId = request.getParameter("targetId");
		if (StringUtils.isBlank(targetId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [targetId]");
		}

		String operationTypeModel = request.getParameter("operationTypeModel");

		Integer targetType = null;
		String targetTypeString = request.getParameter("targetType");
		if (StringUtils.isBlank(targetTypeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [targetType]");
		} else {
			try {
				targetType = Integer.parseInt(targetTypeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter targetType[" + targetType + "] invalid !");
			}
		}

		String operationCode = request.getParameter("operationCode");
		if (StringUtils.isBlank(operationCode)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [operationCode]");
		}

		String operationType = request.getParameter("operationType");
		if (StringUtils.isBlank(operationType)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [operationType]");
		}

		String operationName = request.getParameter("operationName");
		if (StringUtils.isBlank(operationName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [operationName]");
		}

		String successFlag = request.getParameter("successFlag");
		if (StringUtils.isBlank(successFlag)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [successFlag]");
		}

		// 获取目标对象的名称,目前只有Camera和Dws两种
		String type = "";
		if (TypeDefinition.DEVICE_TYPE_CAMERA == targetType.intValue()) {
			type = "Camera";
		} else if (TypeDefinition.SERVER_TYPE_DWS == targetType.intValue()) {
			type = "Dws";
		} else if (TypeDefinition.DEVICE_TYPE_DVR == targetType.intValue()) {
			type = "Dvr";
		} else if (TypeDefinition.DEVICE_TYPE_VD == targetType.intValue()) {
			type = "FVehicleDetector";
		} else if (TypeDefinition.DEVICE_TYPE_WS == targetType.intValue()) {
			type = "WindSpeed";
		} else if (TypeDefinition.DEVICE_TYPE_WST == targetType.intValue()) {
			type = "WeatherStat";
		} else if (TypeDefinition.DEVICE_TYPE_LOLI == targetType.intValue()) {
			type = "LoLi";
		} else if (TypeDefinition.DEVICE_TYPE_FD == targetType.intValue()) {
			type = "FireDetector";
		} else if (TypeDefinition.DEVICE_TYPE_COVI == targetType.intValue()) {
			type = "Covi";
		} else if (TypeDefinition.DEVICE_TYPE_NOD == targetType.intValue()) {
			type = "NoDetector";
		} else if (TypeDefinition.DEVICE_TYPE_CMS == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_FAN == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_LIGHT == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_RD == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_WP == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_RAIL == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_IS == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_PB == targetType.intValue()) {
			type = "PushButton";
		} else if (TypeDefinition.DEVICE_TYPE_TSL == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_LIL == targetType.intValue()) {
			type = "ControlDevice";
		} else if (TypeDefinition.DEVICE_TYPE_BT == targetType.intValue()) {
			type = "BoxTransformer";
		} else if (TypeDefinition.DEVICE_TYPE_VI_DETECTOR == targetType
				.intValue()) {
			type = "ViDetector";
		} else if (TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR == targetType
				.intValue()) {
			type = "RoadDetector";
		} else if (TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR == targetType
				.intValue()) {
			type = "BridgeDetector";
		} else if (TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE == targetType
				.intValue()) {
			type = "UrgentPhone";
		} else if (TypeDefinition.DEVICE_TYPE_SOLAR_BATTERY == targetType
				.intValue()) {
			type = "SolarBattery";
		} else if (TypeDefinition.DEVICE_TYPE_DISPLAY_WALL == targetType
				.intValue()) {
			type = "DisplayWall";
		} else if (TypeDefinition.DEVICE_TYPE_WH == targetType.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_TUNNEL == targetType.intValue()) {
			type = "Organ";
		} else if (TypeDefinition.DEVICE_TYPE_BRIDGE == targetType.intValue()) {
			type = "Organ";
		} else if (TypeDefinition.DEVICE_TYPE_ROAD == targetType.intValue()) {
			type = "Organ";
		} else if (TypeDefinition.DEVICE_TYPE_TOLLGATE == targetType.intValue()) {
			type = "Organ";
		} else if (TypeDefinition.DEVICE_TYPE_ROAD_MOUTH == targetType
				.intValue()) {
			type = "RoadMouth";
		} else if (TypeDefinition.DEVICE_TYPE_EM_RESOURCE == targetType
				.intValue()) {
			type = "Resource";
		} else if (TypeDefinition.DEVICE_TYPE_WAREHOUSE == targetType
				.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_FIRE == targetType.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_HOSPITAL == targetType.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_POLICE == targetType.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_ROAD_ADMIN == targetType
				.intValue()) {
			type = "EmUnit";
		} else if (TypeDefinition.DEVICE_TYPE_CMS_INFO == targetType.intValue()) {
			type = "Playlist";
		} else if (TypeDefinition.DEVICE_TYPE_CMS_INFO_FOLDER == targetType
				.intValue()) {
			type = "PlaylistFolder";
		} else if (TypeDefinition.DEVICE_TYPE_VIEW_SELECT == targetType
				.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_STAT == targetType.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_WORK_MANAGE == targetType
				.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_FLOW_VIEW == targetType
				.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_EVENT == targetType.intValue()) {
			type = "Event";
		} else if (TypeDefinition.DEVICE_TYPE_USER == targetType.intValue()) {
			type = "User";
		} else if (TypeDefinition.DEVICE_TYPE_TUNNEL_VIEW == targetType
				.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_TOLLGATE_VIEW == targetType
				.intValue()) {
			type = "null";
		} else if (TypeDefinition.DEVICE_TYPE_BRIDGE_VIEW == targetType
				.intValue()) {
			type = "null";
		} else {
			type = "null";
		}
		String targetName = "null";
		if (!"null".equals(type)) {
			targetName = sysLogManager.getNameByIdAndType(targetId, type);
			// Camera中没有找到，可能是下级平台资源
			if (StringUtils.isBlank(targetName) && type.equals("Camera")) {
				targetName = sysLogManager.getNameByIdAndType(targetId,
						"SubPlatformResource");
			}
		} else {
			targetName = operationName;
		}
		String resourceType = resource.get().getType();
		String resourceId = resource.get().getId();
		String resourceName = resource.get().getName();
		// 客户端用户操作日志保存
		if (resourceType.equals(TypeDefinition.CLIENT_TYPE_SGC)
				|| resourceType.equals(TypeDefinition.CLIENT_TYPE_CS)) {
			userManager.saveUserOperationLog(resourceId, operationName,
					operationTypeModel);
		}

		SysLog sysLog = new SysLog();
		sysLog.setCreateTime(System.currentTimeMillis());
		sysLog.setLogTime(System.currentTimeMillis());
		sysLog.setOperationCode(operationCode);
		sysLog.setOperationName(operationName);
		sysLog.setOperationType(operationType);
		sysLog.setOrganId(resource.get().getOrganId());
		sysLog.setResourceId(resourceId);
		sysLog.setResourceName(resourceName);
		sysLog.setResourceType(resourceType);
		sysLog.setSuccessFlag(successFlag);
		sysLog.setTargetId(targetId);
		sysLog.setTargetName(targetName);
		sysLog.setTargetType(type);
		sysLogManager.batchLog(sysLog);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1021");
		dto.setMethod("Create_Sys_Log");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Force_Logoff", cmd = "2023")
	@RequestMapping("/force_logoff.json")
	public void forceLogoff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userSessionId = request.getParameter("userSessionId");
		if (StringUtils.isBlank(userSessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userSessionId]");
		}

		userManager.forceLogoff(userSessionId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Force_Logoff");
		dto.setCmd("2023");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "Mcu_Login", cmd = "3020")
	@RequestMapping("/mcu_user_login.xml")
	public void mcuUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		String passwd = request.getParameter("passwd");
		if (StringUtils.isBlank(userName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userName]");
		}
		if (StringUtils.isBlank(passwd)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [passwd]");
		}
		// 校验License
		License license = licenseManager.getLicense();
		licenseManager.checkLicense(license);

		// 中心服务器接收请求的IP地址，在集群或者端口映射环境下，要求客户端传递这个ip参数，否则获取到的始终是内网IP
		String centerIp = request.getParameter("centerIp");

		// 客户端的IP
		String remoteIp = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(remoteIp)) {
			remoteIp = request.getRemoteAddr();
			System.out.println("client remote ip is : " + remoteIp);
		}

		// 生成Session
		String sessionId = userManager.csLogin(userName, passwd, remoteIp,
				TypeDefinition.CLIENT_TYPE_CS);

		// 用户登录基本信息
		UserLogonVO user = null;
		// 采取计算客户端IP地址和中心以及CCS掩码方式返回
		if (StringUtils.isBlank(centerIp)) {
			user = userManager.getUserByName(userName, remoteIp);
		}
		// 根据中心config.properties配置的路由信息返回CCS的IP
		else {
			System.out.println("request centerIp is : " + centerIp);
			user = userManager.getUserUseConfig(userName, centerIp);
		}

		// 安卓版本号和下载地址
		AndroidUpdate android = userManager.getAndroidConfig();

		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("Cmd", "3020");
		root.setAttribute("Method", "Mcu_Login");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");
		doc.setRootElement(root);

		Element sessionIdXml = new Element("SessionId");
		root.addContent(sessionIdXml);
		sessionIdXml.setText(sessionId);

		Element userXml = new Element("User");
		userXml.setAttribute("Id", user.getId());
		userXml.setAttribute("Name", user.getName());
		userXml.setAttribute("OrganId", user.getOrganId());
		userXml.setAttribute("StandardNumber", user.getStandardNumber());
		root.addContent(userXml);

		Element ccsXml = new Element("CCS");
		root.addContent(ccsXml);
		Element ipXml = new Element("IP");
		ipXml.setText(user.getIp());
		ccsXml.addContent(ipXml);
		Element port = new Element("Port");
		port.setText(user.getPort());
		ccsXml.addContent(port);

		Element androidXml = new Element("Android");
		root.addContent(androidXml);
		Element versionXml = new Element("Version");
		versionXml.setText(android.getApkVersion());
		androidXml.addContent(versionXml);
		Element addressXml = new Element("Address");
		addressXml.setText(android.getDownloadAddress());
		androidXml.addContent(addressXml);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "SGC_Login", cmd = "1200")
	@RequestMapping("/sgc_user_login.xml")
	public void sgcUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		if (StringUtils.isBlank(userName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [userName]");
		}
		String passwd = request.getParameter("passwd");
		if (StringUtils.isBlank(passwd)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [passwd]");
		}

		// 校验License
		License license = licenseManager.getLicense();
		licenseManager.checkLicense(license);

		// 中心服务器接收请求的IP地址，在集群或者端口映射环境下，要求客户端传递这个ip参数，否则获取到的始终是内网IP
		String centerIp = request.getParameter("centerIp");

		// 客户端的IP
		String remoteIp = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(remoteIp)) {
			remoteIp = request.getRemoteAddr();
			System.out.println("client remote ip is : " + remoteIp);
		}

		// 生成Session
		String sessionId = userManager.csLogin(userName, passwd, remoteIp,
				TypeDefinition.CLIENT_TYPE_SGC);

		// 用户登录基本信息
		UserLogonVO user = null;
		// 采取计算客户端IP地址和中心以及CCS掩码方式返回
		if (StringUtils.isBlank(centerIp)) {
			user = userManager.getUserByName(userName, remoteIp);
		}
		// 根据中心config.properties配置的路由信息返回CCS的IP
		else {
			System.out.println("request centerIp is : " + centerIp);
			user = userManager.getUserUseConfig(userName, centerIp);
		}

		// 地图信息
		GisLogonVO gisVo = userManager.getGisServer();

		// 登录后用户关联的设备权限
		UserViewVO auth = userManager.getPermissionsByUserId(user.getId());

		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("Cmd", "1200");
		root.setAttribute("Method", "SGC_Login");
		root.setAttribute("Code", ErrorCode.SUCCESS);
		root.setAttribute("Message", "");
		doc.setRootElement(root);

		Element sessionIdXml = new Element("SessionId");
		root.addContent(sessionIdXml);
		sessionIdXml.setText(sessionId);

		Element ccs = new Element("CCS");
		root.addContent(ccs);
		Element ipXml = new Element("IP");
		ipXml.setText(user.getIp());
		ccs.addContent(ipXml);
		Element port = new Element("Port");
		port.setText(user.getPort());
		ccs.addContent(port);
		Element telnetPort = new Element("TelnetPort");
		telnetPort.setText(user.getTelnetPort());
		ccs.addContent(telnetPort);

		Element gisElement = new Element("GIS");
		root.addContent(gisElement);
		Element gisIpXml = new Element("IP");
		gisIpXml.setText(gisVo.getIp());
		gisElement.addContent(gisIpXml);
		Element gisPort = new Element("Port");
		gisPort.setText(gisVo.getPort());
		gisElement.addContent(gisPort);

		List<String> list = new ArrayList<String>();
		list.add("ccsId".toLowerCase());
		list.add("ip");
		list.add("port");
		Element u = ElementUtil.createElement("User", user, null, list);
		root.addContent(u);

		Element authElement = new Element("Auth");
		root.addContent(authElement);
		Element alarm = new Element("Alarm");
		alarm.setText(auth.getAlarm());
		authElement.addContent(alarm);
		Element gis = new Element("GIS");
		gis.setText(auth.getGis());
		authElement.addContent(gis);
		Element query = new Element("Query");
		query.setText(auth.getQuery());
		authElement.addContent(query);

		Element wms = new Element("WMS");
		// wms.setText(Configuration.getInstance().loadProperties("wms_url"));
		wms.setText(gisVo.getWmsUrl());
		root.addContent(wms);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "SGC_User_Logoff", cmd = "1201")
	@RequestMapping("/sgc_user_logoff.xml")
	public void sgcUserLogoff(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionId = request.getParameter("sessionId");
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [sessionId]");
		}
		userManager.userLogoff(sessionId);
		BaseDTO dto = new BaseDTO();
		request.getSession().removeAttribute("sessionId");
		dto.setCmd("1201");
		dto.setMethod("SGC_User_Logoff");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	// @InterfaceDescription(logon = false, method = "Export_Excel_Log", cmd =
	// "1201")
	// @RequestMapping("/export_excel_log.xml")
	// public void exportExcelLog(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// String resourceName = request.getParameter("resourceName");
	// String organId = request.getParameter("organId");
	// if (StringUtils.isBlank(organId)) {
	// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
	// "missing [organId]");
	// }
	// Long startTime = null;
	// String startTimeString = request.getParameter("startTime");
	// if (StringUtils.isNotBlank(startTimeString)) {
	// try {
	// startTime = Long.parseLong(startTimeString);
	// } catch (NumberFormatException be) {
	// be.printStackTrace();
	// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
	// "Parameter priority[" + startTimeString + "] invalid !");
	// }
	// }
	// Long endTime = null;
	// String endTimeString = request.getParameter("endTime");
	// if (StringUtils.isNotBlank(endTimeString)) {
	// try {
	// endTime = Long.parseLong(endTimeString);
	// } catch (NumberFormatException be) {
	// be.printStackTrace();
	// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
	// "Parameter priority[" + endTimeString + "] invalid !");
	// }
	// }
	// Integer startIndex = 0;
	// String startIndexString = request.getParameter("startIndex");
	// if (StringUtils.isNotBlank(startIndexString)) {
	// try {
	// startIndex = Integer.parseInt(startIndexString);
	// } catch (NumberFormatException be) {
	// be.printStackTrace();
	// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
	// "Parameter priority[" + startIndexString
	// + "] invalid !");
	// }
	// }
	// Integer limit = 1000;
	// String limitString = request.getParameter("limit");
	// if (StringUtils.isNotBlank(limitString)) {
	// try {
	// limit = Integer.parseInt(limitString);
	// } catch (NumberFormatException be) {
	// be.printStackTrace();
	// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
	// "Parameter priority[" + limitString + "] invalid !");
	// }
	// }
	// String targetName = request.getParameter("targetName");
	//
	// String operationCode = request.getParameter("operationCode");
	//
	// String operationType = request.getParameter("operationType");
	//
	// String resourceType = request.getParameter("resourceType");
	//
	// String logonUserId = "";
	//
	// List<SysLog> listLog = userManager.listSysLog(organId, resourceName,
	// startTime, endTime, startIndex, limit, targetName,
	// operationCode, logonUserId, operationType, resourceType);
	//
	// // OutputStream out = response.getOutputStream();
	// Workbook wb = userManager.exportExcelLog(listLog);
	// OutputStream os = response.getOutputStream();// 取得输出流
	// response.reset();// 清空输出流
	// String fname = "工作情况";
	// fname = java.net.URLEncoder.encode(fname, "UTF-8");
	// response.setHeader("Content-Disposition", "attachment;filename="
	// + new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
	// response.setContentType("application/msexcel");
	// wb.write(os);
	// os.flush();
	// os.close();
	// // ByteArrayOutputStream os = new ByteArrayOutputStream();
	// // wb.write(out);
	// // byte[] bytes = os.toByteArray();
	// // response.setContentType("application/msexcel;charset=utf-8");
	// // out.write(bytes);
	// // out.flush();
	// // out.close();
	// }

	@InterfaceDescription(logon = true, method = "Add_Favorite_Device", cmd = "1018")
	@RequestMapping("/add_favorite_device.xml")
	public void addFavoriteDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		Element favorite = requestRoot.getChild("Favorite");
		if (null == favorite) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Favorite]");
		}

		String id = favorite.getAttributeValue("Id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Id]");
		}

		String cameraId = favorite.getAttributeValue("CameraId");
		if (StringUtils.isBlank(cameraId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [CameraId]");
		}

		userManager.addFavoriteDevice(id, cameraId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1018");
		dto.setMethod("Add_Favorite_Device");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Favorite_Device", cmd = "1032")
	@RequestMapping("/delete_favorite_device.xml")
	public void deleteFavoriteDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		Element favorite = requestRoot.getChild("Favorite");
		if (null == favorite) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Favorite]");
		}

		String id = favorite.getAttributeValue("Id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Id]");
		}

		String cameraId = favorite.getAttributeValue("CameraId");
		if (StringUtils.isBlank(cameraId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [CameraId]");
		}

		userManager.deleteFavoriteDevice(id, cameraId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1032");
		dto.setMethod("Delete_Favorite_Device");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_User_Operation_Log", cmd = "1318")
	@RequestMapping("/list_user_operation_log.xml")
	public void listUserOperationLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String operationType = reader.getString("operationType", true);

		Long beginTime = reader.getLong("beginTime", true);

		Long endTime = reader.getLong("endTime", true);

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

		Integer totalCount = userManager.findTotalCount(operationType,
				beginTime, endTime);
		// 判断是否是根机构用户
		// Boolean isAdmin = false;
		// isAdmin = userManager.isAdmin(resource.get().getId());
		// if (isAdmin) {
		// listLog = userManager.listSysLogByAdmin(resourceName, startTime,
		// endTime, startIndex, limit, targetName, operationCode,
		// operationType, resourceType);
		// } else {
		Element list = userManager.listUserOperationLog(operationType,
				beginTime, endTime, startIndex, limit);

		BaseDTO dto = new BaseDTO();
		Document doc = new Document();
		dto.setMethod("List_User_Operation_Log");
		dto.setCmd("1318");
		dto.setMessage(totalCount + "");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.addContent(list);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}
}
