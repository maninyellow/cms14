package com.znsx.cms.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.WallScheme;
import com.znsx.cms.persistent.model.WallSchemeItem;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.MonitorManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.DisplayWallVO;
import com.znsx.cms.service.model.ListDwsMonitorVO;
import com.znsx.cms.service.model.MonitorVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.WallVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.GetMonitorDTO;
import com.znsx.cms.web.dto.omc.GetWallDTO;
import com.znsx.cms.web.dto.omc.ListMonitorDTO;
import com.znsx.cms.web.dto.omc.ListWallDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;

/**
 * 视频输出接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午9:45:41
 */

@Controller
public class MonitorController extends BaseController {

	@Autowired
	private MonitorManager monitorManager;
	@Autowired
	private UserManager userManager;

	@InterfaceDescription(logon = true, method = "Create_Wall", cmd = "2170")
	@RequestMapping("/create_wall.json")
	public void createWall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String wallName = request.getParameter("wallName");
		if (StringUtils.isBlank(wallName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [wallName]");
		}

		String note = request.getParameter("note");

		String id = monitorManager.createWall(organId, wallName, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2170");
		dto.setMethod("Create_Wall");
		dto.setMessage(id + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Wall", cmd = "2171")
	@RequestMapping("/update_wall.json")
	public void updateWall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		String organId = request.getParameter("organId");
		String wallName = request.getParameter("wallName");
		if (null != wallName && StringUtils.isBlank(wallName)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		String note = request.getParameter("note");

		monitorManager.updateWall(id, wallName, note, organId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2171");
		dto.setMethod("Update_Wall");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Wall", cmd = "2172")
	@RequestMapping("/delete_wall.json")
	public void deleteWall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		monitorManager.deleteWall(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2172");
		dto.setMethod("Delete_Wall");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Wall", cmd = "2174")
	@RequestMapping("/get_wall.json")
	public void getWall(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		DisplayWallVO dw = monitorManager.getWall(id);

		GetWallDTO dto = new GetWallDTO();
		dto.setCmd("2174");
		dto.setMethod("Get_Wall");
		dto.setWall(dw);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Wall", cmd = "2173")
	@RequestMapping("/list_wall.json")
	public void listWall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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

		Integer totalCount = monitorManager.wallTotalCount(organId);

		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<DisplayWallVO> vo = monitorManager.listWall(organId, startIndex,
				limit);

		ListWallDTO dto = new ListWallDTO();
		dto.setCmd("2173");
		dto.setMethod("List_Wall");
		dto.setDisplayWalls(vo);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Monitor", cmd = "2161")
	@RequestMapping("/create_monitor.json")
	public void createMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String wallId = request.getParameter("wallId");
		if (StringUtils.isBlank(wallId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [wallId]");
		}

		Integer channelNumber = null;
		String channelNumberString = request.getParameter("channelNumber");
		if (StringUtils.isBlank(channelNumberString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [channelNumber]");
		} else {
			try {
				channelNumber = Integer.parseInt(channelNumberString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelNumber[" + channelNumberString
								+ "] invalid !");
			}
		}

		String standardNumber = request.getParameter("standardNumber");
		// 自动生成标准号
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager
					.generateStandardNum("Monitor", organId);
		}

		String dwsId = request.getParameter("dwsId");
		if (StringUtils.isBlank(dwsId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [dwsId]");
		}

		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [name]");
		}

		String id = monitorManager.createMonitor(organId, wallId,
				channelNumber, standardNumber, dwsId, name);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2161");
		dto.setMethod("Create_Monitor");
		dto.setMessage(id);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Monitor", cmd = "2162")
	@RequestMapping("/update_monitor.json")
	public void updateMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		String organId = request.getParameter("organId");

		String wallId = request.getParameter("wallId");

		Integer channelNumber = null;
		String channelNumberString = request.getParameter("channelNumber");
		if (StringUtils.isNotBlank(channelNumberString)) {
			try {
				channelNumber = Integer.parseInt(channelNumberString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter channelNumber[" + channelNumberString
								+ "] invalid !");
			}
		}

		String standardNumber = request.getParameter("standardNumber");

		String dwsId = request.getParameter("dwsId");

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}

		monitorManager.updateMonitor(id, organId, wallId, channelNumber,
				standardNumber, dwsId, name);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2162");
		dto.setMethod("Update_Monitor");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Monitor", cmd = "2163")
	@RequestMapping("/delete_monitor.json")
	public void deleteMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		monitorManager.deleteMonitor(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2163");
		dto.setMethod("Delete_Monitor");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Monitor", cmd = "2164")
	@RequestMapping("/get_monitor.json")
	public void getMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		MonitorVO vo = monitorManager.getMonitor(id);

		GetMonitorDTO dto = new GetMonitorDTO();
		dto.setCmd("2164");
		dto.setMethod("Get_Monitor");
		dto.setMonitor(vo);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Monitor", cmd = "2160")
	@RequestMapping("/list_monitor.json")
	public void listMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String wallId = request.getParameter("wallId");
		if (StringUtils.isBlank(wallId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [wallId]");
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

		Integer totalCount = monitorManager.monitorTotalCount(wallId);

		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<MonitorVO> vo = monitorManager.listMonitor(wallId, startIndex,
				limit);

		ListMonitorDTO dto = new ListMonitorDTO();
		dto.setCmd("2160");
		dto.setMethod("List_Monitor");
		dto.setTotalCount(totalCount + "");
		dto.setMonitors(vo);
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Edit_Wall_Layout", cmd = "1023")
	@RequestMapping("/edit_wall_layout.xml")
	public void editWallLayout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();
		Element wall = reqRoot.getChild("Wall");
		if (null == wall) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Wall]");
		}
		String wallId = wall.getAttributeValue("Id");
		if (StringUtils.isBlank(wallId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Wall Id]");
		}

		Element monitorListElement = reqRoot.getChild("MonitorList");
		if (null == monitorListElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [MonitorList]");
		}
		List<Element> monitors = monitorListElement.getChildren("Monitor");

		monitorManager.editWallLayout(wallId, monitors);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1023");
		dto.setMethod("Edit_Wall_Layout");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Display_Wall", cmd = "1024")
	@RequestMapping("/list_display_wall.xml")
	public void listDisplayWall(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		String userId = resource.get().getId();

		List<WallVO> list = monitorManager.listUserWall(userId);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1024");
		dto.setMethod("List_Display_Wall");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (WallVO wall : list) {
			Element wallElement = ElementUtil.createElement("Wall", wall, null,
					null);
			List<WallVO.Monitor> monitors = wall.getMonitors();
			for (WallVO.Monitor monitor : monitors) {
				Element monitorElement = ElementUtil.createElement("Monitor",
						monitor, null, null);
				wallElement.addContent(monitorElement);
			}
			root.addContent(wallElement);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_DWS_Monitor", cmd = "1266")
	@RequestMapping("/get_dws_monitor.xml")
	public void getDwsMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = resource.get().getOrganId();
		List<ListDwsMonitorVO> list = monitorManager.getDwsMonitor(organId);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1266");
		dto.setMethod("Get_DWS_Monitor");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);

		doc.setRootElement(root);
		for (ListDwsMonitorVO vo : list) {
			Element wallElement = ElementUtil.createElement("Dws", vo.getDws(),
					null, null);
			for (MonitorVO monitor : vo.getListMonitor()) {
				Element monitorElement = ElementUtil.createElement("Monitor",
						monitor, null, null);
				wallElement.addContent(monitorElement);
			}
			root.addContent(wallElement);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Wall_Scheme", cmd = "1029")
	@RequestMapping("/create_wall_scheme.xml")
	public void createWallScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();
		ResourceVO user = resource.get();
		String userId = user.getId();
		String organId = user.getOrganId();
		
		Element scheme = reqRoot.getChild("WallScheme");

		String wallId = scheme.getAttributeValue("WallId");
		if (StringUtils.isBlank(wallId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [WallId] !");
		}

		String name = scheme.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name] !");
		}

		List<Element> monitors = scheme.getChildren();
		if (monitors.size() <= 0) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Monitor] element !");
		}

		String wallSchemeId = monitorManager.createWallScheme(name, userId,
				organId, wallId, monitors);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1029");
		dto.setMethod("Create_Wall_Scheme");
		dto.setMessage(wallSchemeId);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}
	
	@InterfaceDescription(logon = true, method = "List_Wall_Scheme", cmd = "1030")
	@RequestMapping("/list_wall_scheme.xml")
	public void listWallScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		
		String userId = reader.getString("userId", true);
		String organId = null;
		// 用户ID为空，获取当前登录用户机构ID作为查询条件
		if (StringUtils.isBlank(userId)) {
			ResourceVO user = resource.get(
					);
			organId = user.getOrganId();
		}
		
		List<WallScheme> list = monitorManager.listWallScheme(userId, organId);
		
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1030");
		dto.setMethod("List_Wall_Scheme");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		
		for (WallScheme wallScheme : list) {
			Element we = ElementUtil.createElement("Scheme", wallScheme);
			root.addContent(we);
			we.setAttribute("WallId", wallScheme.getWall().getId());
			
			Set<WallSchemeItem> items = wallScheme.getItems();
			for (WallSchemeItem wsi : items) {
				Document d = ElementUtil.string2Doc(wsi.getContent());
				we.addContent(d.getRootElement().detach());
			}
		}
		
		writePageWithContentLength(response, doc);
	}
	
	@InterfaceDescription(logon = true, method = "Delete_Wall_Scheme", cmd = "1031")
	@RequestMapping("/delete_wall_scheme.xml")
	public void deleteWallScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		
		String id = reader.getString("id", false);
		
		monitorManager.deleteWallScheme(id);
		
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1031");
		dto.setMethod("Delete_Wall_Scheme");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}
	
	@InterfaceDescription(logon = true, method = "Update_Wall_Scheme", cmd = "1032")
	@RequestMapping("/update_wall_scheme.xml")
	public void updateWallScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();
		Element scheme = reqRoot.getChild("WallScheme");
		
		String schemeId = scheme.getAttributeValue("Id");
		if (StringUtils.isBlank(schemeId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Id] !");
		}

		String name = scheme.getAttributeValue("Name");
		
		List<Element> monitors = scheme.getChildren();
		monitorManager.updateWallScheme(schemeId, name, monitors);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1032");
		dto.setMethod("Update_Wall_Scheme");
		dto.setMessage("");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}
}
