package com.znsx.cms.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.StakeNumberLib;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.UserResourceComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.EmManager;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.GetOrganBridgeVO;
import com.znsx.cms.service.model.GetOrganRoadVO;
import com.znsx.cms.service.model.GetOrganTollgateVO;
import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.service.model.GetRoadMouthVO;
import com.znsx.cms.service.model.GetServiceZoneVO;
import com.znsx.cms.service.model.ListOrganDeviceTreeVO;
import com.znsx.cms.service.model.ListOrganVO;
import com.znsx.cms.service.model.OrganVO;
import com.znsx.cms.service.model.UserResourceVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.GetOrganBridgeDTO;
import com.znsx.cms.web.dto.omc.GetOrganDTO;
import com.znsx.cms.web.dto.omc.GetOrganRoadDTO;
import com.znsx.cms.web.dto.omc.GetOrganTollgateDTO;
import com.znsx.cms.web.dto.omc.GetRoadMouthDTO;
import com.znsx.cms.web.dto.omc.GetServiceZoneDTO;
import com.znsx.cms.web.dto.omc.ListOrganByIdDTO;
import com.znsx.cms.web.dto.omc.ListOrganByNameDTO;
import com.znsx.cms.web.dto.omc.ListOrganByParentIdDTO;
import com.znsx.cms.web.dto.omc.ListOrganDTO;
import com.znsx.cms.web.dto.omc.ListOrganTreeDTO;
import com.znsx.cms.web.dto.omc.ListRoadMouthDTO;
import com.znsx.cms.web.dto.omc.ListServiceZoneDTO;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 机构对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 下午02:03:32
 */
@Controller
public class OrganController extends BaseController {

	@Autowired
	private OrganManager organManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private EmManager emManager;

	@InterfaceDescription(logon = true, method = "Create_Organ", cmd = "2010")
	@RequestMapping("/create_organ.json")
	public void createOrgan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String parentId = reader.getString("parentId", false);

		String type = reader.getString("type", false);

		String fax = reader.getString("fax", true);

		String contact = reader.getString("contact", true);

		String phone = reader.getString("phone", true);

		String address = reader.getString("address", true);

		String areaCode = reader.getString("areaCode", true);

		String note = reader.getString("note", true);

		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Organ", parentId);
			// 特别的机构标准号生成还需要加入机构类型信息
			standardNumber = standardNumber.substring(0, 9) + type
					+ standardNumber.substring(10);
		}

		String frontOrganId = reader.getString("frontOrganId", true);

		String backOrganId = reader.getString("backOrganId", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String height = reader.getString("height", true);

		String length = reader.getString("length", true);

		Integer limitSpeed = reader.getInteger("limitSpeed", true);

		Integer capacity = reader.getInteger("capacity", true);

		String beginStakeNumber = reader.getString("beginStakeNumber", true);
		beginStakeNumber = StringUtils.replace(beginStakeNumber, " ", "+");

		String endStakeNumber = reader.getString("endStakeNumber", true);
		endStakeNumber = StringUtils.replace(endStakeNumber, " ", "+");

		Integer laneWidth = reader.getInteger("laneWidth", true);

		Integer leftEdge = reader.getInteger("leftEdge", true);

		Integer rightEdge = reader.getInteger("rightEdge", true);

		Integer centralReserveWidth = reader.getInteger("centralReserveWidth",
				true);

		Integer entranceNumber = reader.getInteger("entranceNumber", true);

		Integer exitNumber = reader.getInteger("exitNumber", true);

		String navigation = reader.getString("navigation", true);

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String width = reader.getString("width", true);

		String roadNumber = reader.getString("roadNumber", true);

		String leftDirection = reader.getString("leftDirection", true);

		String rightDirection = reader.getString("rightDirection", true);

		Integer laneNumber = reader.getInteger("laneNumber", true);

		String id = "";

		// StakeNumberLib stakeNumberLib = new StakeNumberLib();

		// 根据桩号计算坐标
		// if (StringUtils.isNotBlank(stakeNumber)) {
		// stakeNumberLib = emManager.getStakeNumberLib(stakeNumber, null);
		// }
		// if (StringUtils.isBlank(longitude)) {
		// longitude = stakeNumberLib.getLongitude();
		// }
		// if (StringUtils.isBlank(latitude)) {
		// latitude = stakeNumberLib.getLatitude();
		// }

		// 创建机构
		if (type.equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
			id = organManager.createOrganTunnel(name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, type,
					stakeNumber, height, length, laneNumber, limitSpeed,
					capacity, leftDirection, rightDirection);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			id = organManager.createOrganRoad(name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, type,
					stakeNumber, limitSpeed, capacity, laneNumber,
					beginStakeNumber, endStakeNumber, laneWidth, leftEdge,
					rightEdge, centralReserveWidth, roadNumber);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			id = organManager.createOrganTollgate(name, standardNumber,
					parentId, fax, contact, phone, address, areaCode, note,
					type, stakeNumber, entranceNumber, exitNumber, navigation,
					longitude, latitude);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			id = organManager.createOrganBridge(name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, type,
					stakeNumber, longitude, latitude, limitSpeed, capacity,
					width, length);
		} else {
			id = organManager.createOrgan(name, standardNumber, parentId, fax,
					contact, phone, address, areaCode, note, type,
					frontOrganId, backOrganId, stakeNumber);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2010");
		dto.setMethod("Create_Organ");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Organ", cmd = "2011")
	@RequestMapping("/update_organ.json")
	public void updateOrgan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String standardNumber = reader.getString("standardNumber", true);

		String parentId = reader.getString("parentId", true);

		String type = reader.getString("type", true);

		String fax = reader.getString("fax", true);

		String contact = reader.getString("contact", true);

		String phone = reader.getString("phone", true);

		String address = reader.getString("address", true);

		String areaCode = reader.getString("areaCode", true);

		String note = reader.getString("note", true);

		String frontOrganId = reader.getString("frontOrganId", true);

		String backOrganId = reader.getString("backOrganId", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String height = reader.getString("height", true);

		String length = reader.getString("length", true);

		Integer limitSpeed = reader.getInteger("limitSpeed", true);

		Integer capacity = reader.getInteger("capacity", true);

		String beginStakeNumber = reader.getString("beginStakeNumber", true);
		beginStakeNumber = StringUtils.replace(beginStakeNumber, " ", "+");

		String endStakeNumber = reader.getString("endStakeNumber", true);
		endStakeNumber = StringUtils.replace(endStakeNumber, " ", "+");

		Integer laneWidth = reader.getInteger("laneWidth", true);

		Integer leftEdge = reader.getInteger("leftEdge", true);

		Integer rightEdge = reader.getInteger("rightEdge", true);

		Integer centralReserveWidth = reader.getInteger("centralReserveWidth",
				true);

		Integer entranceNumber = reader.getInteger("entranceNumber", true);

		Integer exitNumber = reader.getInteger("exitNumber", true);

		String navigation = reader.getString("navigation", true);

		if (StringUtils.isNotBlank(stakeNumber)) {
		}

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String width = reader.getString("width", true);

		String roadNumber = reader.getString("roadNumber", true);

		String leftDirection = reader.getString("leftDirection", true);

		String rightDirection = reader.getString("rightDirection", true);

		Integer laneNumber = reader.getInteger("laneNumber", true);

		StakeNumberLib stakeNumberLib = new StakeNumberLib();

		// 根据桩号计算坐标
		if (StringUtils.isNotBlank(stakeNumber)) {
			stakeNumberLib = emManager.getStakeNumberLib(stakeNumber, null);
		}
		if (StringUtils.isBlank(longitude)) {
			longitude = stakeNumberLib.getLongitude();
		}
		if (StringUtils.isBlank(latitude)) {
			latitude = stakeNumberLib.getLatitude();
		}

		if (type.equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
			organManager.updateOrganTunnel(id, name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, stakeNumber,
					height, length, laneNumber, limitSpeed, capacity,
					leftDirection, rightDirection);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			organManager.updateOrganRoad(id, name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, type,
					stakeNumber, limitSpeed, capacity, laneNumber,
					beginStakeNumber, endStakeNumber, laneWidth, leftEdge,
					rightEdge, centralReserveWidth, roadNumber);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			organManager.updateOrganTollgate(id, name, standardNumber,
					parentId, fax, contact, phone, address, areaCode, note,
					stakeNumber, entranceNumber, exitNumber, navigation,
					longitude, latitude);
		} else if (type.equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			organManager.updateOrganBridge(id, name, standardNumber, parentId,
					fax, contact, phone, address, areaCode, note, type,
					stakeNumber, longitude, latitude, limitSpeed, capacity,
					width, length);
		} else {
			organManager.updateOrgan(id, name, standardNumber, parentId, fax,
					contact, phone, address, areaCode, note, frontOrganId,
					backOrganId, stakeNumber);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2011");
		dto.setMethod("Update_Organ");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Organ", cmd = "2012")
	@RequestMapping("/delete_organ.json")
	public void deleteOrgan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		organManager.deleteOrgan(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2012");
		dto.setMethod("Delete_Organ");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_Tree", cmd = "2014")
	@RequestMapping("/list_organ_tree.json")
	public void getOrganTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		OrganVO vo = organManager.getOrganTree(organId);

		ListOrganTreeDTO dto = new ListOrganTreeDTO();
		dto.setCmd("2014");
		dto.setMethod("List_Organ_Tree");
		dto.setOrgan(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_Device", cmd = "1001")
	@RequestMapping("/list_organ_device.xml")
	public void listOrganDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}

		boolean isZip = false;
		String gzip = request.getParameter("gzip");
		if (StringUtils.isNotBlank(gzip)) {
			if ("1".equals(gzip)) {
				isZip = true;
			}
		}

		Collection<UserResourceVO> devices = deviceManager
				.listUserResource(resource.get().getId());
		// 排序
		List<UserResourceVO> list = new ArrayList<UserResourceVO>(devices);
		Collections.sort(list, new UserResourceComparator());
		Element element = organManager.generateOrganTree(organId, list);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1001");
		dto.setMethod("List_Organ_Device");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(element);
		if (isZip) {
			writePageUseGzip(response, doc);
		} else {
			writePageWithContentLength(response, doc);
		}
	}

	@InterfaceDescription(logon = true, method = "Get_Organ", cmd = "2015")
	@RequestMapping("/get_organ.json")
	public void getOrgan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetOrganVO organ = organManager.getOrgan(id);

		GetOrganDTO dto = new GetOrganDTO();
		dto.setCmd("2015");
		dto.setMethod("Get_Organ");
		dto.setOrgan(organ);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ", cmd = "2013")
	@RequestMapping("/list_organ.json")
	public void listOrgan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");

		String organName = request.getParameter("organName");

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

		Integer totalCount = organManager.organTotalCount(organId, organName);
		if (StringUtils.isNotBlank(organId)) {
			totalCount = totalCount + 1;
		}
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<GetOrganVO> organList = organManager.listOrgan(organId, organName,
				startIndex, limit);

		ListOrganDTO dto = new ListOrganDTO();
		dto.setCmd("2013");
		dto.setMethod("List_Organ");
		dto.setOrganList(organList);
		dto.setTotalCount(totalCount + "");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "Test_Interface", cmd = "1999")
	@RequestMapping("/test_interface.json")
	public void testInterface(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1999");
		dto.setMethod("Test_Interface");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_By_Id", cmd = "2016")
	@RequestMapping("/list_organ_by_id.json")
	public void listOrganById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		// if (StringUtils.isBlank(organId)) {
		// throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
		// "missing [organId]");
		// }
		if (StringUtils.isBlank(organId)) {
			organId = resource.get().getOrganId();
		}
		List<ListOrganVO> listVO = organManager.listOrganById(organId);
		ListOrganByIdDTO dto = new ListOrganByIdDTO();
		dto.setListOrgan(listVO);
		dto.setCmd("2016");
		dto.setMethod("List_Organ_By_Id");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_By_Name", cmd = "2017")
	@RequestMapping("/list_organ_by_name.json")
	public void listOrganByName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [organId]");
		}
		String standardNumber = request.getParameter("standardNumber");
		String logonUserId = resource.get().getId();

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

		ListOrganByNameDTO dto = organManager.listOrganByName(name,
				logonUserId, startIndex, limit, organId, standardNumber, type);

		dto.setCmd("2017");
		dto.setMethod("List_Organ_By_Name");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_By_ParentId", cmd = "2018")
	@RequestMapping("/list_organ_by_parentId.json")
	public void listOrganByParentId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		if (StringUtils.isBlank(parentId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [parentId]");
		}
		ListOrganByParentIdDTO dto = organManager.listOrganByParentId(parentId);

		dto.setCmd("2018");
		dto.setMethod("List_Organ_By_ParentId");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Is_Road_Exist", cmd = "2315")
	@RequestMapping("/is_road_exist.json")
	public void isRoadExist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String frontOrganId = request.getParameter("frontOrganId");
		String backOrganId = request.getParameter("backOrganId");
		String createOrUpdate = request.getParameter("createOrUpdate");
		organManager.isRoadExist(id, frontOrganId, backOrganId, createOrUpdate);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2315");
		dto.setMethod("Is_Road_Exist");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Organ_Tunnel", cmd = "2019")
	@RequestMapping("/get_organ_tunnel.json")
	public void getOrganTunnel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetOrganVO organ = organManager.getOrganTunnel(id);

		GetOrganDTO dto = new GetOrganDTO();
		dto.setCmd("2019");
		dto.setMethod("Get_Organ_Tunnel");
		dto.setOrgan(organ);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Organ_Road", cmd = "2025")
	@RequestMapping("/get_organ_road.json")
	public void getOrganRoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetOrganRoadVO organ = organManager.getOrganRoad(id);

		GetOrganRoadDTO dto = new GetOrganRoadDTO();
		dto.setCmd("2025");
		dto.setMethod("Get_Organ_Road");
		dto.setOrgan(organ);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Organ_Tollgate", cmd = "2026")
	@RequestMapping("/get_organ_tollgate.json")
	public void getOrganTollgate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetOrganTollgateVO organ = organManager.getOrganTollgate(id);

		GetOrganTollgateDTO dto = new GetOrganTollgateDTO();
		dto.setCmd("2026");
		dto.setMethod("Get_Organ_Tollgate");
		dto.setOrgan(organ);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Road_Mouth", cmd = "2360")
	@RequestMapping("/create_road_mouth.json")
	public void createRoadMouth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		Integer limitSpeed = reader.getInteger("limitSpeed", true);

		String navigation = reader.getString("navigation", true);

		String organId = reader.getString("organId", false);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String id = organManager.createRoadMouth(name, limitSpeed, navigation,
				organId, stakeNumber, longitude, latitude);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2360");
		dto.setMethod("Create_Road_Mouth");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Road_Mouth", cmd = "2361")
	@RequestMapping("/update_road_mouth.json")
	public void updateRoadMouth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		Integer limitSpeed = reader.getInteger("limitSpeed", true);

		String navigation = reader.getString("navigation", true);

		String organId = reader.getString("organId", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		organManager.updateRoadMouth(id, name, limitSpeed, navigation, organId,
				stakeNumber, longitude, latitude);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2361");
		dto.setMethod("Update_Road_Mouth");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Road_Mouth", cmd = "2364")
	@RequestMapping("/delete_road_mouth.json")
	public void deleteRoadMouth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		organManager.deleteRoadMouth(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2364");
		dto.setMethod("Delete_Road_Mouth");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Road_Mouth", cmd = "2363")
	@RequestMapping("/get_road_mouth.json")
	public void getRoadMouth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetRoadMouthVO vo = organManager.getRoadMouth(id);
		GetRoadMouthDTO dto = new GetRoadMouthDTO();
		dto.setCmd("2363");
		dto.setMethod("Get_Road_Mouth");
		dto.setRoadMouth(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Road_Mouth", cmd = "2362")
	@RequestMapping("/list_road_mouth.json")
	public void listRoadMouth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		String organId = reader.getString("organId", false);
		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = 0;
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 1000;
		}

		Integer totalCount = organManager.roadMouthTotalCount(name, organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<GetRoadMouthVO> list = organManager.listRoadMouth(name,
				startIndex, limit, organId);
		ListRoadMouthDTO dto = new ListRoadMouthDTO();
		dto.setCmd("2362");
		dto.setMethod("List_Road_Mouth");
		dto.setRoadMouthList(list);
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	// @InterfaceDescription(logon = true, method = "Create_Tollgate", cmd =
	// "2360")
	// @RequestMapping("/create_tollgate.json")
	// public void createTollgate(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// SimpleRequestReader reader = new SimpleRequestReader(request);
	// String name = reader.getString("name", false);
	//
	// Integer entranceNumber = reader.getInteger("entranceNumber", true);
	//
	// Integer exitNumber = reader.getInteger("exitNumber", true);
	//
	// String organId = reader.getString("organId", false);
	//
	// String stakeNumber = reader.getString("stakeNumber", true);
	//
	// String linkMan = reader.getString("linkMan", true);
	//
	// String phone = reader.getString("phone", true);
	//
	// String longitude = reader.getString("longitude", true);
	//
	// String latitude = reader.getString("latitude", true);
	//
	// String navigation = reader.getString("navigation", true);
	//
	// String id = organManager.createTollgate(name, entranceNumber,
	// exitNumber, organId, stakeNumber, linkMan, phone, longitude,
	// latitude, navigation);
	//
	// BaseDTO dto = new BaseDTO();
	// dto.setCmd("2360");
	// dto.setMessage(id);
	// dto.setMethod("Create_Tollgate");
	//
	// writePage(response, dto);
	// }
	//
	// @InterfaceDescription(logon = true, method = "Update_Tollgate", cmd =
	// "2361")
	// @RequestMapping("/update_tollgate.json")
	// public void updateTollgate(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// SimpleRequestReader reader = new SimpleRequestReader(request);
	// String id = reader.getString("id", false);
	//
	// String name = request.getParameter("name");
	// if (null != name && StringUtils.isBlank(name)) {
	// throw new BusinessException(ErrorCode.PARAMETER_INVALID,
	// "Can not modify name to empty !");
	// }
	//
	// Integer entranceNumber = reader.getInteger("entranceNumber", true);
	//
	// Integer exitNumber = reader.getInteger("exitNumber", true);
	//
	// String organId = reader.getString("organId", true);
	//
	// String stakeNumber = reader.getString("stakeNumber", true);
	//
	// String linkMan = reader.getString("linkMan", true);
	//
	// String phone = reader.getString("phone", true);
	//
	// String longitude = reader.getString("longitude", true);
	//
	// String latitude = reader.getString("latitude", true);
	//
	// String navigation = reader.getString("navigation", true);
	//
	// organManager.updateTollgate(id, name, entranceNumber, exitNumber,
	// organId, stakeNumber, linkMan, phone, longitude, latitude,
	// navigation);
	//
	// BaseDTO dto = new BaseDTO();
	// dto.setCmd("2361");
	// dto.setMethod("Update_Tollgate");
	//
	// writePage(response, dto);
	// }
	//
	// @InterfaceDescription(logon = true, method = "Delete_Tollgate", cmd =
	// "2364")
	// @RequestMapping("/delete_tollgate.json")
	// public void deleteTollgate(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// SimpleRequestReader reader = new SimpleRequestReader(request);
	// String id = reader.getString("id", false);
	//
	// organManager.deleteTollgate(id);
	//
	// BaseDTO dto = new BaseDTO();
	// dto.setCmd("2364");
	// dto.setMethod("Delete_Tollgate");
	//
	// writePage(response, dto);
	// }
	//
	// @InterfaceDescription(logon = true, method = "Get_Tollgate", cmd =
	// "2363")
	// @RequestMapping("/get_tollgate.json")
	// public void getTollgate(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// SimpleRequestReader reader = new SimpleRequestReader(request);
	// String id = reader.getString("id", false);
	//
	// GetTollgateVO tollgate = organManager.getTollgate(id);
	//
	// GetTollgateDTO dto = new GetTollgateDTO();
	// dto.setCmd("2363");
	// dto.setMethod("Get_Tollgate");
	// dto.setTollgate(tollgate);
	//
	// writePage(response, dto);
	// }
	//
	// @InterfaceDescription(logon = true, method = "List_Tollgate", cmd =
	// "2362")
	// @RequestMapping("/list_tollgate.json")
	// public void listTollgate(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// SimpleRequestReader reader = new SimpleRequestReader(request);
	// String name = reader.getString("name", true);
	// String organId = reader.getString("organId", false);
	// Integer startIndex = reader.getInteger("startIndex", true);
	// if (null == startIndex) {
	// startIndex = 0;
	// }
	//
	// Integer limit = reader.getInteger("limit", true);
	// if (null == limit) {
	// limit = 1000;
	// }
	//
	// Integer totalCount = organManager.tollgateTotalCount(name, organId);
	//
	// List<GetTollgateVO> list = organManager.listTollgate(name, startIndex,
	// limit, organId);
	// ListTollgateDTO dto = new ListTollgateDTO();
	// dto.setCmd("2362");
	// dto.setMethod("List_Tollgate");
	// dto.setTollgateList(list);
	// dto.setTotalCount(totalCount + "");
	//
	// writePage(response, dto);
	// }

	@InterfaceDescription(logon = true, method = "Create_Service_Zone", cmd = "2370")
	@RequestMapping("/create_service_zone.json")
	public void createServiceZone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", false);
		name = StringUtils.replace(name, " ", "+");
		String organId = reader.getString("organId", false);

		String level = reader.getString("level", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String linkMan = reader.getString("linkMan", true);

		String phone = reader.getString("phone", true);

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String navigation = reader.getString("navigation", true);

		String id = organManager.createServiceZone(name, level, organId,
				stakeNumber, linkMan, phone, longitude, latitude, navigation);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2370");
		dto.setMessage(id);
		dto.setMethod("Create_Service_Zone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Service_Zone", cmd = "2371")
	@RequestMapping("/update_service_zone.json")
	public void updateServiceZone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		name = StringUtils.replace(name, " ", "+");
		String level = reader.getString("level", true);

		String organId = reader.getString("organId", true);

		String stakeNumber = reader.getString("stakeNumber", true);
		stakeNumber = StringUtils.replace(stakeNumber, " ", "+");

		String linkMan = reader.getString("linkMan", true);

		String phone = reader.getString("phone", true);

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String navigation = reader.getString("navigation", true);

		organManager.updateServiceZone(id, name, level, organId, stakeNumber,
				linkMan, phone, longitude, latitude, navigation);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2371");
		dto.setMethod("Update_Service_Zone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Service_Zone", cmd = "2374")
	@RequestMapping("/delete_service_zone.json")
	public void deleteServiceZone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		organManager.deleteServiceZone(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2374");
		dto.setMethod("Delete_Service_Zone");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Service_Zone", cmd = "2373")
	@RequestMapping("/get_service_zone.json")
	public void getServiceZone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetServiceZoneVO serviceZone = organManager.getServiceZone(id);

		GetServiceZoneDTO dto = new GetServiceZoneDTO();
		dto.setCmd("2373");
		dto.setMethod("Get_Service_Zone");
		dto.setServiceZone(serviceZone);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Service_Zone", cmd = "2372")
	@RequestMapping("/list_service_zone.json")
	public void listServiceZone(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);
		String organId = reader.getString("organId", false);
		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = 0;
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 1000;
		}

		Integer totalCount = organManager.serviceZoneTotalCount(name, organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<GetServiceZoneVO> list = organManager.listServiceZone(name,
				startIndex, limit, organId);
		ListServiceZoneDTO dto = new ListServiceZoneDTO();
		dto.setCmd("2372");
		dto.setMethod("List_Service_Zone");
		dto.setServiceZoneList(list);
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Organ_Bridge", cmd = "2027")
	@RequestMapping("/get_organ_bridge.json")
	public void getOrganBridge(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}

		GetOrganBridgeVO organ = organManager.getOrganBridge(id);

		GetOrganBridgeDTO dto = new GetOrganBridgeDTO();
		dto.setCmd("2027");
		dto.setMethod("Get_Organ_Bridge");
		dto.setOrgan(organ);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Organ_Tree_All", cmd = "2028")
	@RequestMapping("/list_organ_tree_all.json")
	public void listOrganTreeAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organId = request.getParameter("organId");
		if (StringUtils.isBlank(organId)) {
			organId = resource.get().getOrganId();
		}
		List<ListOrganVO> listVO = organManager.listOrganTreeAll(organId);
		ListOrganByIdDTO dto = new ListOrganByIdDTO();
		dto.setListOrgan(listVO);
		dto.setCmd("2028");
		dto.setMethod("List_Organ_Tree_All");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "List_Organ_Camera", cmd = "1810")
	@RequestMapping("/list_organ_camera.xml")
	public void listOrganCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = RequestUtil.parseRequest(request);
		Element reqRoot = document.getRootElement();
		Element organElement = reqRoot.getChild("OrganId");
		String organSN = null;
		if (null != organElement) {
			if (StringUtils.isNotBlank(organElement.getText())) {
				organSN = organElement.getText();
			}
		}
		Document doc = (Document) CacheUtil.getCache("listOrganCamera"
				+ organSN, null);
		if (null == doc) {
			// 构成机构设备树
			ListOrganDeviceTreeVO result = organManager
					.treeOrganCamera(organSN);
			String json = JSONObject.fromObject(result).toString();
			BaseDTO dto = new BaseDTO();
			dto.setCmd("1810");
			dto.setMethod("List_Organ_Camera");
			dto.setMessage(organSN);
			doc = new Document();
			Element root = ElementUtil.createElement("Response", dto, null,
					null);
			doc.setRootElement(root);
			Element tree = new Element("Tree");
			tree.setText(json);
			root.addContent(tree);

			CacheUtil.putCache("listOrganCamera" + organSN, doc, null);
		}
		writePageWithContentLengthGBK(response, doc);
	}
}
