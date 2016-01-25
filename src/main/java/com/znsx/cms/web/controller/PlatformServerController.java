package com.znsx.cms.web.controller;

import java.net.URLDecoder;
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
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.persistent.model.DeviceUpdateListener;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.persistent.model.Pts;
import com.znsx.cms.persistent.model.Rms;
import com.znsx.cms.persistent.model.Rss;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionCcs;
import com.znsx.cms.persistent.model.UserSessionCrs;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.UserSessionDws;
import com.znsx.cms.persistent.model.UserSessionEns;
import com.znsx.cms.persistent.model.UserSessionMss;
import com.znsx.cms.persistent.model.UserSessionPts;
import com.znsx.cms.persistent.model.UserSessionRms;
import com.znsx.cms.persistent.model.UserSessionRss;
import com.znsx.cms.persistent.model.UserSessionUser;
import com.znsx.cms.persistent.model.UserSessionSrs;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.cms.service.iface.DasDataManager;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.PlatformServerManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.CameraStatusVO;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.CrsUpdateConfigVO;
import com.znsx.cms.service.model.ListCcsVO;
import com.znsx.cms.service.model.ListCrsVO;
import com.znsx.cms.service.model.ListDasVO;
import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.service.model.ListEnsVO;
import com.znsx.cms.service.model.ListGisVO;
import com.znsx.cms.service.model.ListMssVO;
import com.znsx.cms.service.model.ListPtsVO;
import com.znsx.cms.service.model.ListRmsVO;
import com.znsx.cms.service.model.ListRssVO;
import com.znsx.cms.service.model.ListSrsVO;
import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.service.model.PtsCameraVO;
import com.znsx.cms.service.model.PtsDvrVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.UserSessionVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.ccs.CheckUserSessionDTO;
import com.znsx.cms.web.dto.ccs.GetDeviceUpdateTimeDTO;
import com.znsx.cms.web.dto.omc.GetPlatformHardwareDTO;
import com.znsx.cms.web.dto.omc.GetPlatformServerDTO;
import com.znsx.cms.web.dto.omc.ListCcsDTO;
import com.znsx.cms.web.dto.omc.ListCrsDTO;
import com.znsx.cms.web.dto.omc.ListDasDTO;
import com.znsx.cms.web.dto.omc.ListDwsDTO;
import com.znsx.cms.web.dto.omc.ListEnsDTO;
import com.znsx.cms.web.dto.omc.ListGisDTO;
import com.znsx.cms.web.dto.omc.ListMssDTO;
import com.znsx.cms.web.dto.omc.ListOnlineUsersDTO;
import com.znsx.cms.web.dto.omc.ListPlatformServerDTO;
import com.znsx.cms.web.dto.omc.ListPtsDTO;
import com.znsx.cms.web.dto.omc.ListRmsDTO;
import com.znsx.cms.web.dto.omc.ListRssDTO;
import com.znsx.cms.web.dto.omc.ListSrsDTO;
import com.znsx.util.base64.Base64Utils;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.file.Configuration;
import com.znsx.util.network.NetworkUtil;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 平台服务器对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:39:55
 */
@Controller
public class PlatformServerController extends BaseController {
	@Autowired
	private PlatformServerManager platformServerManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SysLogManager sysLogManager;
	@Autowired
	private OrganManager organManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private ConnectManager connectManager;
	@Autowired
	private DasDataManager dasDataManager;

	public static final String REGION = "standardNumberCache";

	@InterfaceDescription(logon = true, method = "List_Platform_Server", cmd = "2130")
	@RequestMapping("/list_platform_server.json")
	public void listPlatformServer(HttpServletRequest request,
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

		String name = request.getParameter("name");
		String type = request.getParameter("type");

		ListPlatformServerDTO dto = platformServerManager.listPlatformServer(
				startIndex, limit, name, type);

		dto.setCmd("2130");
		dto.setMethod("List_Platform_Server");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Ccs", cmd = "2080")
	@RequestMapping("/create_ccs.json")
	public void createCcs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String standardNumber = reader.getString("standardNumber", true);

		String name = reader.getString("name", false);

		String location = reader.getString("location", true);

		String lanIp = reader.getString("lanIp", true);
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = reader.getString("wanIp", true);

		String configValue = reader.getString("configValue", true);

		String port = reader.getString("port", true);

		Short forward = reader.getShort("forward", false);

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Ccs", null);
		}

		String id = platformServerManager.createCcs(standardNumber, name,
				location, lanIp, wanIp, configValue, port, forward);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2080");
		dto.setMethod("Create_Ccs");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Mss", cmd = "2090")
	@RequestMapping("/create_mss.json")
	public void createMss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");
		if (StringUtils.isBlank(configValue)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [configValue]");
		}

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Mss", null);
		}

		String id = platformServerManager.createMss(standardNumber, name,
				location, lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2090");
		dto.setMethod("Create_Mss");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Dws", cmd = "2100")
	@RequestMapping("/create_dws.json")
	public void createDws(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Dws", null);
		}

		String id = platformServerManager.createDws(standardNumber, name,
				location, lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2100");
		dto.setMethod("Create_Dws");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Pts", cmd = "2110")
	@RequestMapping("/create_pts.json")
	public void createPts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Pts", null);
		}

		String id = platformServerManager.createPts(standardNumber, name,
				location, lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2110");
		dto.setMethod("Create_Pts");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Crs", cmd = "2140")
	@RequestMapping("/create_crs.json")
	public void createCrs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Crs", null);
		}

		String id = platformServerManager.createCrs(standardNumber, name,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2140");
		dto.setMethod("Create_Crs");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Rms", cmd = "2153")
	@RequestMapping("/create_rms.json")
	public void createRms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Rms", null);
		}

		String id = platformServerManager.createRms(standardNumber, name,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2153");
		dto.setMethod("Create_Rms");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Platform_Server", cmd = "2131")
	@RequestMapping("/get_platform_server.json")
	public void getPlatformServer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		Integer type = null;
		String typeString = request.getParameter("type");
		if (StringUtils.isBlank(typeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [type]");
		} else {
			try {
				type = Integer.parseInt(typeString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter type[" + typeString + "] invalid !");
			}
		}

		PlatformServerVO platformServer = platformServerManager
				.getPlatformServer(id, type);

		GetPlatformServerDTO dto = new GetPlatformServerDTO();
		dto.setCmd("2131");
		dto.setMethod("Get_Platform_Server");
		dto.setPlatformServer(platformServer);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Ccs", cmd = "2081")
	@RequestMapping("/update_ccs.json")
	public void updateCcs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		Short forward = reader.getShort("forward", true);

		platformServerManager.updateCcs(id, standardNumber, name, location,
				lanIp, wanIp, configValue, port, forward);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2081");
		dto.setMethod("Update_Ccs");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Rms", cmd = "2154")
	@RequestMapping("/update_rms.json")
	public void updateRms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updateRms(id, standardNumber, name, location,
				lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2154");
		dto.setMethod("Update_Rms");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Ccs", cmd = "2082")
	@RequestMapping("/delete_ccs.json")
	public void deleteCcs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteCcs(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2082");
		dto.setMethod("Delete_Ccs");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Rms", cmd = "2155")
	@RequestMapping("/delete_rms.json")
	public void deleteRms(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteRms(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2155");
		dto.setMethod("Delete_Rms");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Ccs", cmd = "2083")
	@RequestMapping("/list_ccs.json")
	public void listCcs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListCcsVO> ccsList = platformServerManager.listCcs();

		ListCcsDTO dto = new ListCcsDTO();
		dto.setCmd("2083");
		dto.setMethod("List_Ccs");
		dto.setCcsList(ccsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Rms", cmd = "2156")
	@RequestMapping("/list_rms.json")
	public void listRms(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListRmsVO> rmsList = platformServerManager.listRms();

		ListRmsDTO dto = new ListRmsDTO();
		dto.setCmd("2156");
		dto.setMethod("List_Rms");
		dto.setRmsList(rmsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Crs", cmd = "2141")
	@RequestMapping("/update_crs.json")
	public void updateCrs(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updateCrs(id, standardNumber, name, null,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2141");
		dto.setMethod("Update_Crs");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Crs", cmd = "2142")
	@RequestMapping("/delete_crs.json")
	public void deleteCrs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteCrs(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2142");
		dto.setMethod("Delete_Crs");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Crs", cmd = "2143")
	@RequestMapping("/list_crs.json")
	public void listCrs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListCrsVO> crsList = platformServerManager.listCrs();

		ListCrsDTO dto = new ListCrsDTO();
		dto.setCmd("2143");
		dto.setMethod("List_Crs");
		dto.setCrsList(crsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Mss", cmd = "2091")
	@RequestMapping("/update_mss.json")
	public void updateMss(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");
		if (StringUtils.isNotBlank(configValue)) {
			configValue = URLDecoder.decode(configValue, "utf8");
		}

		String port = request.getParameter("port");

		platformServerManager.updateMss(id, standardNumber, name, location,
				lanIp, wanIp, port, null, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2091");
		dto.setMethod("Update_Mss");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Mss", cmd = "2092")
	@RequestMapping("/delete_mss.json")
	public void deleteMss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteMss(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2092");
		dto.setMethod("Delete_Mss");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Mss", cmd = "2093")
	@RequestMapping("/list_mss.json")
	public void listMss(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListMssVO> mssList = platformServerManager.listMss();

		ListMssDTO dto = new ListMssDTO();
		dto.setCmd("2093");
		dto.setMethod("List_Mss");
		dto.setMssList(mssList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Dws", cmd = "2101")
	@RequestMapping("/update_dws.json")
	public void updateDws(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updateDws(id, standardNumber, name, null,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2101");
		dto.setMethod("Update_Dws");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Dws", cmd = "2102")
	@RequestMapping("/delete_dws.json")
	public void deleteDws(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteDws(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2102");
		dto.setMethod("Delete_Dws");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Dws", cmd = "2103")
	@RequestMapping("/list_dws.json")
	public void listDws(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListDwsVO> dwsList = platformServerManager.listDws();

		ListDwsDTO dto = new ListDwsDTO();
		dto.setCmd("2103");
		dto.setMethod("List_Dws");
		dto.setDwsList(dwsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Pts", cmd = "2111")
	@RequestMapping("/update_pts.json")
	public void updatePts(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updatePts(id, standardNumber, name, null,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2111");
		dto.setMethod("Update_Pts");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Pts", cmd = "2112")
	@RequestMapping("/delete_pts.json")
	public void deletePts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deletePts(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2112");
		dto.setMethod("Delete_Pts");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Pts", cmd = "2113")
	@RequestMapping("/list_pts.json")
	public void listPts(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListPtsVO> ptsList = platformServerManager.listPts();

		ListPtsDTO dto = new ListPtsDTO();
		dto.setCmd("2113");
		dto.setMethod("List_Pts");
		dto.setPtsList(ptsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Resource_Route_Info", cmd = "3007")
	@RequestMapping("/get_resource_route_info.xml")
	public void getResourceRouteInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document docXml = (Document) request.getAttribute("RequestDocument");
		Element rootXml = docXml.getRootElement();

		Element standardNumberElement = rootXml.getChild("StandardNumber");
		String standardNumber = standardNumberElement.getText();
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		// 从缓存中返回路由节点
		Element element = (Element) CacheUtil.getCache(standardNumber, REGION);

		if (null == element) {
			// 查询下级平台资源表
			SubPlatformResource resource = connectManager
					.getSubPlatformResourceBySN(standardNumber);
			// 下级资源表有数据
			if (null != resource) {
				// // 根据sessionId，获取CCS信息，判断是否互联网关
				// ResourceVO vo = BaseController.resource.get();
				// PlatformServerVO ccs =
				// platformServerManager.getPlatformServer(
				// vo.getId(), TypeDefinition.SERVER_TYPE_CCS);
				// // 如果是网关返回下级平台的路由信息
				// if (ccs.getStandardNumber().equals(
				// Configuration.getInstance().getProperties(
				// "gateway_ccs_sn"))) {
				element = connectManager.getResourceRoute(resource);
				// }
				// // 如果不是网关返回本级网关的路由信息
				// else {
				// String gateSN = Configuration.getInstance().getProperties(
				// "gateway_ccs_sn");
				// if (StringUtils.isBlank(gateSN)) {
				// throw new BusinessException(
				// ErrorCode.RESOURCE_NOT_FOUND,
				// "No CCS gate config or resource cannot be control by ccs !");
				// }
				// Ccs gate = platformServerManager
				// .getCcsByStandardNumber(gateSN);
				// element = new Element("Route");
				// element.setAttribute("LanIp", gate.getLanIp() == null ? ""
				// : gate.getLanIp());
				// element.setAttribute("Port", gate.getPort());
				// }
			}
			// 下级资源表无数据，查询平台内部资源
			else {
				element = dasDataManager.getResourceRouteInfo(standardNumber);
			}
			CacheUtil.putCache(standardNumber, element, REGION);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3007");
		dto.setMethod("Get_Resource_Route_Info");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		if (null != element) {
			root.addContent(element);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Check_Session", cmd = "3006")
	@RequestMapping("/check_session.xml")
	public void checkUserSession(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document doc = RequestUtil.parseRequest(request);
		Element root = doc.getRootElement();
		Element sessionElement = root.getChild("SessionId");
		if (null == sessionElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}
		String sessionId = sessionElement.getText();
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}

		ResourceVO user = userManager.checkSession(sessionId);
		// 返回
		CheckUserSessionDTO dto = new CheckUserSessionDTO();
		dto.setCmd("3006");
		dto.setMethod("Check_Session");
		dto.setPriority(user.getPriority());
		// 用作字节点的属性
		List<String> childProperties = new ArrayList<String>();
		childProperties.add("priority");
		Document docRtn = new Document();
		Element rootRtn = ElementUtil.createElement("Response", dto,
				childProperties, null);
		docRtn.setRootElement(rootRtn);
		writePageWithContentLength(response, docRtn);
	}

	@InterfaceDescription(logon = false, method = "List_PTS_Device", cmd = "3005")
	@RequestMapping("/list_pts_device.xml")
	public void listPtsDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element ptsElement = requestRoot.getChild("StandardNumber");
		if (null == ptsElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}
		String standardNumber = ptsElement.getText();
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		Integer start = 0;
		Element startElement = requestRoot.getChild("Start");
		if (null != startElement) {
			String startString = startElement.getText();
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Start[" + startString + "] invalid !");
			}
		}

		Integer limit = 50;
		Element limitElement = requestRoot.getChild("Limit");
		if (null != limitElement) {
			String limitString = limitElement.getText();
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Limit[" + limitString + "] invalid !");
			}
		}

		// 根据StandardNumber查询CCS
		Ccs ccs = platformServerManager.getCcsByStandardNumber(standardNumber);
		// 根据PTS_ID查询设备
		List<PtsDvrVO> list = deviceManager.listDvrByCcs(ccs.getId(), start,
				limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3005");
		dto.setMethod("List_PTS_Device");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element deviceList = new Element("DeviceList");
		root.addContent(deviceList);
		// DVR
		List<String> childProperties = new ArrayList<String>();
		childProperties.add("expand");
		for (PtsDvrVO dvr : list) {
			Element device = ElementUtil.createElement("Device", dvr,
					childProperties, null);
			deviceList.addContent(device);
			// Camera
			Element channelList = new Element("ChannelList");
			device.addContent(channelList);
			List<PtsCameraVO> cameras = dvr.getCameras();
			for (PtsCameraVO camera : cameras) {
				Element channel = ElementUtil.createElement("Channel", camera,
						childProperties, null);
				channelList.addContent(channel);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Count_PTS_DVR", cmd = "3008")
	@RequestMapping("/count_pts_dvr.xml")
	public void countPtsDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element ptsElement = requestRoot.getChild("StandardNumber");
		if (null == ptsElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}
		String standardNumber = ptsElement.getText();
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		// 根据StandardNumber查询CCS
		Ccs ccs = platformServerManager.getCcsByStandardNumber(standardNumber);
		// 统计管辖的设备数量
		int count = deviceManager.countDvrByCcs(ccs.getId());
		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3008");
		dto.setMethod("Count_PTS_DVR");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element total = new Element("Total");
		total.setText(count + "");
		root.addContent(total);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Get_Device_Update_Time", cmd = "3009")
	@RequestMapping("/get_device_update_time.xml")
	public void getDeviceUpdateTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DeviceUpdateListener deviceUpdate = platformServerManager
				.getDeviceUpdateTime();

		// 返回
		GetDeviceUpdateTimeDTO dto = new GetDeviceUpdateTimeDTO();
		dto.setCmd("3009");
		dto.setMethod("Get_Device_Update_Time");
		dto.setUpdateTime(deviceUpdate.getUpdateTime() != null ? deviceUpdate
				.getUpdateTime().toString() : "");
		dto.setCrsUpdateTime(deviceUpdate.getCrsUpdateTime() != null ? deviceUpdate
				.getCrsUpdateTime().toString() : "");

		List<String> childProperties = new ArrayList<String>();
		childProperties.add("updatetime");
		childProperties.add("crsupdatetime");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto,
				childProperties, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Heartbeat", cmd = "3010")
	@RequestMapping("/heartbeat.xml")
	public void heartbeat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element sessionElement = requestRoot.getChild("SessionId");
		if (null == sessionElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}
		String sessionId = sessionElement.getText();
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}

		userManager.heartbeat(sessionId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3010");
		dto.setMethod("Heartbeat");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Session_Expire", cmd = "3011")
	@RequestMapping("/session_expire.xml")
	public void sessionExpire(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element sessionElement = requestRoot.getChild("SessionId");
		if (null == sessionElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}
		String sessionId = sessionElement.getText();
		if (StringUtils.isBlank(sessionId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SessionId]");
		}

		userManager.userLogoff(sessionId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3011");
		dto.setMethod("Session_Expire");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_CCS_User_Session", cmd = "3012")
	@RequestMapping("/list_ccs_user_session.xml")
	public void listCcsUserSession(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element standardNumberElement = requestRoot.getChild("StandardNumber");
		if (null == standardNumberElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}
		String standardNumber = standardNumberElement.getText();
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		List<CcsUserSessionVO> sessions = platformServerManager
				.listCcsUserSession(standardNumber);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3012");
		dto.setMethod("List_CCS_User_Session");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (CcsUserSessionVO vo : sessions) {
			Element sessionElement = ElementUtil.createElement("Session", vo,
					null, null);
			root.addContent(sessionElement);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Server_Register", cmd = "3001")
	@RequestMapping("/server_register.xml")
	public void serverRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = RequestUtil.parseRequest(request);
		Element requestRoot = requestDoc.getRootElement();
		Element standardNumberElement = requestRoot.getChild("StandardNumber");
		if (null == standardNumberElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}
		String standardNumber = standardNumberElement.getText();
		if (StringUtils.isBlank(standardNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [StandardNumber]");
		}

		Element typeElement = requestRoot.getChild("Type");
		if (null == typeElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Type]");
		}
		String type = typeElement.getText();
		if (StringUtils.isBlank(type)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Type]");
		}

		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null == lanIpElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [LanIP]");
		}
		String lanIp = lanIpElement.getText();
		if (StringUtils.isBlank(lanIp)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [LanIP]");
		}

		// 注册成功生成的服务器会话ID
		UserSession session = null;
		// 平台互联时判定CCS是否为网关的标志,"0"-不是网关,"1"-是网关
		String isGateway = null;
		// CCS或PTS或DAS或RMS注册
		if (TypeDefinition.CLIENT_TYPE_CCS.equalsIgnoreCase(type)
				|| TypeDefinition.CLIENT_TYPE_PTS.equalsIgnoreCase(type)
				|| TypeDefinition.CLIENT_TYPE_DAS.equalsIgnoreCase(type)
				|| TypeDefinition.CLIENT_TYPE_SRS.equalsIgnoreCase(type)) {
			session = platformServerManager.serverRegister(standardNumber,
					type, null, lanIp);
			// if (TypeDefinition.CLIENT_TYPE_CCS.equalsIgnoreCase(type)) {
			// isGateway = "0";
			// // 注册成功，判断CCS是否是config.properties中配置的信令网关，决定是否向上级平台发起注册
			// if (standardNumber.equals(Configuration.getInstance()
			// .loadProperties("gateway_ccs_sn"))) {
			// isGateway = "1";
			// Thread t = new Thread(new PlatformRegisterThread(
			// connectManager), "RegisterThread-1");
			// t.setDaemon(false);
			// t.start();
			// }
			// }
		}
		// 其他服务器注册
		else {
			// 根据SessionId获取CCS信息
			Element sessionElement = requestRoot.getChild("SessionId");
			if (null == sessionElement) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [SessionId]");
			}
			String sessionId = sessionElement.getText();
			if (StringUtils.isBlank(sessionId)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [SessionId]");
			}
			ResourceVO ccs = userManager.checkSession(sessionId);
			session = platformServerManager.serverRegister(standardNumber,
					type, ccs.getId(), lanIp);
		}

		// 插入日志列表
		String organId = organManager.getRootOrgan().getId();
		SysLog log = new SysLog();
		log.setCreateTime(System.currentTimeMillis());
		log.setLogTime(System.currentTimeMillis());
		log.setOperationCode("3001");
		log.setOperationName(type.toUpperCase() + "注册");
		log.setOperationType("register");
		log.setOrganId(organId);
		if (session instanceof UserSessionUser) {
			log.setResourceId(((UserSessionUser) session).getUser().getId());
			log.setTargetId(((UserSessionUser) session).getUser().getId());
		} else if (session instanceof UserSessionCcs) {
			log.setResourceId(((UserSessionCcs) session).getCcs().getId());
			log.setTargetId(((UserSessionCcs) session).getCcs().getId());
		} else if (session instanceof UserSessionCrs) {
			log.setResourceId(((UserSessionCrs) session).getCrs().getId());
			log.setTargetId(((UserSessionCrs) session).getCrs().getId());
		} else if (session instanceof UserSessionMss) {
			log.setResourceId(((UserSessionMss) session).getMss().getId());
			log.setTargetId(((UserSessionMss) session).getMss().getId());
		} else if (session instanceof UserSessionDws) {
			log.setResourceId(((UserSessionDws) session).getDws().getId());
			log.setTargetId(((UserSessionDws) session).getDws().getId());
		} else if (session instanceof UserSessionPts) {
			log.setResourceId(((UserSessionPts) session).getPts().getId());
			log.setTargetId(((UserSessionPts) session).getPts().getId());
		} else if (session instanceof UserSessionDas) {
			log.setResourceId(((UserSessionDas) session).getDas().getId());
			log.setTargetId(((UserSessionDas) session).getDas().getId());
		} else if (session instanceof UserSessionEns) {
			log.setResourceId(((UserSessionEns) session).getEns().getId());
			log.setTargetId(((UserSessionEns) session).getEns().getId());
		} else if (session instanceof UserSessionRms) {
			log.setResourceId(((UserSessionRms) session).getRms().getId());
			log.setTargetId(((UserSessionRms) session).getRms().getId());
		} else if (session instanceof UserSessionRss) {
			log.setResourceId(((UserSessionRss) session).getRss().getId());
			log.setTargetId(((UserSessionRss) session).getRss().getId());
		} else if (session instanceof UserSessionSrs) {
			log.setResourceId(((UserSessionSrs) session).getSrs().getId());
			log.setTargetId(((UserSessionSrs) session).getSrs().getId());
		}
		log.setResourceName(session.getLogonName());
		log.setResourceType(type.substring(0, 1).toUpperCase()
				+ type.substring(1).toLowerCase());
		log.setSuccessFlag(ErrorCode.SUCCESS);
		log.setTargetName(session.getLogonName());
		log.setTargetType(type.substring(0, 1).toUpperCase()
				+ type.substring(1).toLowerCase());
		sysLogManager.batchLog(log);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3001");
		dto.setMethod("Server_Register");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element se = new Element("SessionId");
		se.setText(session.getId());
		root.addContent(se);
		// // 如果是CCS注册加入是否网关的节点返回
		// if (null != isGateway) {
		// Element gateway = new Element("Gateway");
		// gateway.setText(isGateway);
		// root.addContent(gateway);
		// }

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "CCS_Update_Config", cmd = "3002")
	@RequestMapping("/ccs_update_config.xml")
	public void ccsUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Ccs ccs = platformServerManager.ccsUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3002");
		dto.setMethod("CCS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(ccs.getWanIp() != null ? ccs.getWanIp() : "");
		root.addContent(wanIp);

		Element forward = new Element("Forward");
		forward.setText(ccs.getForward().toString());
		root.addContent(forward);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "MSS_Update_Config", cmd = "3003")
	@RequestMapping("/mss_update_config.xml")
	public void mssUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Element videoPortElement = requestRoot.getChild("VideoPort");
		if (null == videoPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}
		String videoPort = videoPortElement.getText();
		if (StringUtils.isBlank(videoPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}

		Mss mss = platformServerManager.mssUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort, videoPort);
		String[] array = null;
		if (StringUtils.isNotBlank(mss.getConfigValue())) {
			array = mss.getConfigValue().split(",");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3003");
		dto.setMethod("MSS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(mss.getWanIp() != null ? mss.getWanIp() : "");
		root.addContent(wanIp);

		Element maxInput = new Element("MaxInput");
		maxInput.setText(MyStringUtil.getAttributeValue(array, "maxInput"));
		root.addContent(maxInput);

		Element maxOutput = new Element("MaxOutput");
		maxOutput.setText(MyStringUtil.getAttributeValue(array, "maxOutput"));
		root.addContent(maxOutput);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "DWS_Update_Config", cmd = "3014")
	@RequestMapping("/dws_update_config.xml")
	public void dwsUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Dws dws = platformServerManager.dwsUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3014");
		dto.setMethod("DWS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(dws.getWanIp() != null ? dws.getWanIp() : "");
		root.addContent(wanIp);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "PTS_Update_Config", cmd = "3004")
	@RequestMapping("/pts_update_config.xml")
	public void ptsUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Pts pts = platformServerManager.ptsUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3004");
		dto.setMethod("PTS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(pts.getWanIp() != null ? pts.getWanIp() : "");
		root.addContent(wanIp);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "DAS_Update_Config", cmd = "3015")
	@RequestMapping("/das_update_config.xml")
	public void dasUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Das das = platformServerManager.dasUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3015");
		dto.setMethod("DAS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(das.getWanIp() != null ? das.getWanIp() : "");
		root.addContent(wanIp);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "CRS_Update_Config", cmd = "3016")
	@RequestMapping("/crs_update_config.xml")
	public void crsUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Element videoPortElement = requestRoot.getChild("VideoPort");
		if (null == videoPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}
		String videoPort = videoPortElement.getText();
		if (StringUtils.isBlank(videoPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}

		CrsUpdateConfigVO vo = platformServerManager.crsUpdateConfig(resource
				.get().getId(), lanIp, sipPort, telnetPort, videoPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3016");
		dto.setMethod("CRS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(vo.getWanIp());
		root.addContent(wanIp);

		Element maxInput = new Element("MaxInput");
		maxInput.setText(vo.getMaxInput());
		root.addContent(maxInput);

		Element maxOutput = new Element("MaxOutput");
		maxOutput.setText(vo.getMaxOutput());
		root.addContent(maxOutput);

		Element storePlan = new Element("StorePlan");
		root.addContent(storePlan);
		storePlan.setText(vo.getTotalCount());
		// List<CrsUpdateConfigVO.Channel> list = vo.getChannels();
		// for (CrsUpdateConfigVO.Channel c : list) {
		// Element channel = new Element("Channel");
		// channel.setAttribute("SN", c.getSn());
		// channel.setAttribute("Plan", c.getPlan());
		// storePlan.addContent(channel);
		// }

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Das", cmd = "2290")
	@RequestMapping("/create_das.json")
	public void createDas(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Das", null);
		}
		String id = platformServerManager.createDas(standardNumber, name,
				location, lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2290");
		dto.setMethod("Create_Das");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Das", cmd = "2291")
	@RequestMapping("/update_das.json")
	public void updateDas(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");
		if (StringUtils.isNotBlank(configValue)) {
			configValue = URLDecoder.decode(configValue, "utf8");
		}

		String port = request.getParameter("port");

		platformServerManager.updateDas(id, standardNumber, name, location,
				lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2291");
		dto.setMethod("Update_Das");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Das", cmd = "2292")
	@RequestMapping("/delete_das.json")
	public void deleteDas(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteDas(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2292");
		dto.setMethod("Delete_Das");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Das", cmd = "2293")
	@RequestMapping("/list_das.json")
	public void listDas(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListDasVO> dasList = platformServerManager.listDas();

		ListDasDTO dto = new ListDasDTO();
		dto.setCmd("2293");
		dto.setMethod("List_Das");
		dto.setDasList(dasList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Ens", cmd = "2300")
	@RequestMapping("/create_ens.json")
	public void createEns(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Ens", null);
		}
		String id = platformServerManager.createEns(standardNumber, name,
				location, lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2300");
		dto.setMethod("Create_Ens");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Ens", cmd = "2301")
	@RequestMapping("/update_ens.json")
	public void updateEns(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");
		if (StringUtils.isNotBlank(configValue)) {
			configValue = URLDecoder.decode(configValue, "utf8");
		}

		String port = request.getParameter("port");

		platformServerManager.updateEns(id, standardNumber, name, location,
				lanIp, wanIp, port, null, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2301");
		dto.setMethod("Update_Ens");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Ens", cmd = "2302")
	@RequestMapping("/delete_ens.json")
	public void deleteEns(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteEns(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2302");
		dto.setMethod("Delete_Ens");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Ens", cmd = "2303")
	@RequestMapping("/list_ens.json")
	public void listEns(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListEnsVO> ensList = platformServerManager.listEns();

		ListEnsDTO dto = new ListEnsDTO();
		dto.setCmd("2303");
		dto.setMethod("List_Ens");
		dto.setEnsList(ensList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "DAS_Update_Devices", cmd = "3101")
	@RequestMapping("/das_update_devices.xml")
	public void dasUpdateDevices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		List<Element> devices = requestRoot.getChildren("Device");

		String dasId = resource.get().getId();

		List<Element> list = platformServerManager.mapDevices(dasId, devices);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3101");
		dto.setMethod("DAS_Update_Devices");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (Element e : list) {
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_Platform_Hardware", cmd = "2152")
	@RequestMapping("/get_platform_hardware.json")
	public void getPlatformHardware(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String info = platformServerManager.getPlatformHardware();

		GetPlatformHardwareDTO dto = new GetPlatformHardwareDTO();
		dto.setCmd("2152");
		dto.setMethod("Get_Platform_Hardware");
		dto.setInfo(info);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_CRS_Plan", cmd = "3013")
	@RequestMapping("/list_crs_plan.xml")
	public void listCRSPlan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		Integer start = 0;
		Element startElement = requestRoot.getChild("Start");
		if (null != startElement) {
			String startString = startElement.getText();
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Start[" + startString + "] invalid !");
			}
		}

		Integer limit = 50;
		Element limitElement = requestRoot.getChild("Limit");
		if (null != limitElement) {
			String limitString = limitElement.getText();
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter Limit[" + limitString + "] invalid !");
			}
		}

		String crsId = resource.get().getId();

		Element element = platformServerManager
				.listCRSPlan(crsId, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3013");
		dto.setMethod("List_CRS_Plan");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(element);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Gis", cmd = "2380")
	@RequestMapping("/create_gis.json")
	public void createGis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		// if (StringUtils.isBlank(standardNumber)) {
		// standardNumber = userManager.generateStandardNum("Gis", null);
		// }

		String id = platformServerManager.createGis(standardNumber, name,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2380");
		dto.setMethod("Create_Crs");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Gis", cmd = "2381")
	@RequestMapping("/update_gis.json")
	public void updateGis(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("gisPort");

		platformServerManager.updateGis(id, standardNumber, name, location,
				lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2381");
		dto.setMethod("Update_Crs");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Gis", cmd = "2382")
	@RequestMapping("/list_gis.json")
	public void listGis(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListGisVO> gisList = platformServerManager.listGis();

		ListGisDTO dto = new ListGisDTO();
		dto.setCmd("2382");
		dto.setMethod("List_Gis");
		dto.setGisList(gisList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Gis", cmd = "2383")
	@RequestMapping("/delete_gis.json")
	public void deleteGis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteGis(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2383");
		dto.setMethod("Delete_Gis");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "RMS_Update_Config", cmd = "3018")
	@RequestMapping("/rms_update_config.xml")
	public void rmsUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}
		// if (StringUtils.isBlank(lanIp)) {
		// if (request.getHeader("X-Forwarded-For") != null) {
		// lanIp = request.getHeader("X-Forwarded-For");
		// } else {
		// lanIp = request.getRemoteAddr();
		// }
		// }

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Element videoPortElement = requestRoot.getChild("VideoPort");
		if (null == videoPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}
		String videoPort = videoPortElement.getText();
		if (StringUtils.isBlank(videoPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VideoPort]");
		}

		Rms rms = platformServerManager.rmsUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort, videoPort);
		String[] array = null;
		if (StringUtils.isNotBlank(rms.getConfigValue())) {
			array = rms.getConfigValue().split(",");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3018");
		dto.setMethod("RMS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(rms.getWanIp() != null ? rms.getWanIp() : "");
		root.addContent(wanIp);

		Element maxInput = new Element("MaxInput");
		maxInput.setText(MyStringUtil.getAttributeValue(array, "maxInput"));
		root.addContent(maxInput);

		Element maxOutput = new Element("MaxOutput");
		maxOutput.setText(MyStringUtil.getAttributeValue(array, "maxOutput"));
		root.addContent(maxOutput);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Manual_Push_Resource", cmd = "1028")
	@RequestMapping("/push.xml")
	public void PushResourceManual(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取素有ipv4地址，如果传入参数ip和获取到的地址相同则抛出异常
		String localIp[] = NetworkUtil.getAllLocalHostIP();
		String port = request.getParameter("port");
		String ip = request.getParameter("ip");
		for (int i = 0; i < localIp.length; i++) {
			if (localIp[i].equals(ip)) {
				throw new BusinessException(ErrorCode.URL_ERROR,
						"ip can not local platform");
			}
		}

		String address = null;
		if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port)) {
			StringBuffer sb = new StringBuffer();
			sb.append("http://");
			sb.append(ip);
			sb.append(":");
			sb.append(port);
			sb.append("/cms/");
			address = sb.toString();
		} else {
			address = Configuration.getInstance().loadProperties(
					"higher_platform_address");
		}
		if (StringUtils.isBlank(address)) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"higher_platform_address not configurate !");
		}

		// 首先向上级发起注册
		String code = connectManager.sendRegister(address);
		// 注册成功，发起资源推送请求
		if (ErrorCode.SUCCESS.equals(code)) {
			code = connectManager.pushResource(address);
		}

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Manual_Push_Resource");
		dto.setCmd("1028");
		dto.setCode(code);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Online_Platform_Server", cmd = "2132")
	@RequestMapping("/list_online_platform_server.json")
	public void listOnlinePlatformServer(HttpServletRequest request,
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

		String type = request.getParameter("type");

		ListOnlineUsersDTO dto = platformServerManager
				.listOnlinePlatformServer(startIndex, limit, type);

		dto.setCmd("2132");
		dto.setMethod("List_Online_Platform_Server");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "ENS_Update_Config", cmd = "3024")
	@RequestMapping("/ens_update_config.xml")
	public void ensUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Ens ens = platformServerManager.ensUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3024");
		dto.setMethod("ENS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(ens.getWanIp() != null ? ens.getWanIp() : "");
		root.addContent(wanIp);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "RSS_Update_Config", cmd = "3025")
	@RequestMapping("/rss_update_config.xml")
	public void rssUpdateConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		String lanIp = null;
		Element lanIpElement = requestRoot.getChild("LanIP");
		if (null != lanIpElement) {
			lanIp = lanIpElement.getText();
			if (StringUtils.isBlank(lanIp)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter LanIP can not set to empty !");
			}
		}

		Element sipPortElement = requestRoot.getChild("SipPort");
		if (null == sipPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}
		String sipPort = sipPortElement.getText();
		if (StringUtils.isBlank(sipPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SipPort]");
		}

		Element telnetPortElement = requestRoot.getChild("TelnetPort");
		if (null == telnetPortElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}
		String telnetPort = telnetPortElement.getText();
		if (StringUtils.isBlank(telnetPort)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TelnetPort]");
		}

		Rss rss = platformServerManager.rssUpdateConfig(resource.get().getId(),
				lanIp, sipPort, telnetPort);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3025");
		dto.setMethod("RSS_Update_Config");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element wanIp = new Element("WanIP");
		wanIp.setText(rss.getWanIp() != null ? rss.getWanIp() : "");
		root.addContent(wanIp);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_Local_Device_Status", cmd = "3026")
	@RequestMapping("/list_local_device_status.xml")
	public void listLocalDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CameraStatusVO> list = deviceManager.listLocalCameraStatus();

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3026");
		dto.setMethod("List_Local_Device_Status");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (CameraStatusVO vo : list) {
			Element camera = ElementUtil.createElement("Device", vo);
			root.addContent(camera);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_Sub_Device", cmd = "3027")
	@RequestMapping("/list_sub_device.xml")
	public void listSubDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Element> list = connectManager.listSubDevice();

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3027");
		dto.setMethod("List_Sub_Device");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		root.addContent(list);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_Gateway_Device", cmd = "3028")
	@RequestMapping("/list_gateway_device.xml")
	public void listGatewayDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Element> list = connectManager.listGatewayDevice();

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3028");
		dto.setMethod("List_Gateway_Device");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		root.addContent(list);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_Online_User", cmd = "3029")
	@RequestMapping("/list_online_user.xml")
	public void listOnlineUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<UserSessionVO> list = userManager.listOnlineUser();

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("3029");
		dto.setMethod("List_Online_User");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (UserSessionVO us : list) {
			root.addContent(ElementUtil.createElement("Session", us));
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Rss", cmd = "2470")
	@RequestMapping("/create_rss.json")
	public void createRss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");
		// if (StringUtils.isBlank(lanIp)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [lanIp]");
		// }

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Rss", null);
		}

		String id = platformServerManager.createRss(standardNumber, name,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2470");
		dto.setMethod("Create_Rss");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Rms", cmd = "2471")
	@RequestMapping("/update_rss.json")
	public void updateRss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updateRss(id, standardNumber, name, location,
				lanIp, wanIp, configValue, port);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2471");
		dto.setMethod("Update_Rms");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Rss", cmd = "2472")
	@RequestMapping("/delete_rss.json")
	public void deleteRss(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteRss(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2472");
		dto.setMethod("Delete_Rss");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Rss", cmd = "2473")
	@RequestMapping("/list_rss.json")
	public void listRss(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListRssVO> rssList = platformServerManager.listRss();

		ListRssDTO dto = new ListRssDTO();
		dto.setCmd("2473");
		dto.setMethod("List_Rss");
		dto.setRssList(rssList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Srs", cmd = "2480")
	@RequestMapping("/create_srs.json")
	public void createSrs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String standardNumber = request.getParameter("standardNumber");

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		// 自动生成编码
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Srs", null);
		}

		String id = platformServerManager.createSrs(standardNumber, name,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2480");
		dto.setMethod("Create_Srs");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Srs", cmd = "2481")
	@RequestMapping("/list_srs.json")
	public void listSrs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ListSrsVO> srsList = platformServerManager.listSrs();

		ListSrsDTO dto = new ListSrsDTO();
		dto.setCmd("2481");
		dto.setMethod("List_Srs");
		dto.setSrsList(srsList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Srs", cmd = "2482")
	@RequestMapping("/update_srs.json")
	public void updateSrs(HttpServletRequest request,
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

		String location = request.getParameter("location");

		String lanIp = request.getParameter("lanIp");

		String wanIp = request.getParameter("wanIp");

		String configValue = request.getParameter("configValue");

		String port = request.getParameter("port");

		platformServerManager.updateSrs(id, standardNumber, name, null,
				location, lanIp, wanIp, port, configValue);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2482");
		dto.setMethod("Update_Srs");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Srs", cmd = "2483")
	@RequestMapping("/delete_srs.json")
	public void deleteSrs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		platformServerManager.deleteSrs(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2483");
		dto.setMethod("Delete_Srs");

		writePage(response, dto);
	}
}
