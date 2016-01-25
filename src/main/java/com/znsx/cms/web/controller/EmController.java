package com.znsx.cms.web.controller;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.AddressBook;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.Event;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.RoadMouth;
import com.znsx.cms.persistent.model.SchemeStepInstance;
import com.znsx.cms.persistent.model.StakeNumberLib;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.Tollgate;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.SchemeInstanceVOItemComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.EmManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.RoadStatusManager;
import com.znsx.cms.service.iface.RuleManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.iface.WebGisManager;
import com.znsx.cms.service.iface.WorkRecordManager;
import com.znsx.cms.service.model.SchemeInstanceVO;
import com.znsx.cms.service.model.SchemeTempletVO;
import com.znsx.cms.service.model.UnitFireVO;
import com.znsx.cms.service.model.UnitHospitalVO;
import com.znsx.cms.service.model.UnitRoadAdminVO;
import com.znsx.cms.service.model.UnitVO;
import com.znsx.cms.service.model.UnitWareHouseVO;
import com.znsx.cms.service.model.WfsRoadAdminVO;
import com.znsx.cms.service.model.WfsTollgateVO;
import com.znsx.cms.service.rule.model.RoadEvent;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.GetAddressBookDTO;
import com.znsx.cms.web.dto.omc.GetAddressBookVO;
import com.znsx.cms.web.dto.omc.GetResourceDTO;
import com.znsx.cms.web.dto.omc.GetResourceUserDTO;
import com.znsx.cms.web.dto.omc.GetTeamDTO;
import com.znsx.cms.web.dto.omc.GetTollgateLocationDTO;
import com.znsx.cms.web.dto.omc.GetUnitDTO;
import com.znsx.cms.web.dto.omc.GetUnitFireDTO;
import com.znsx.cms.web.dto.omc.GetUnitHospitalDTO;
import com.znsx.cms.web.dto.omc.GetUnitRoadAdminDTO;
import com.znsx.cms.web.dto.omc.GetVehicleDTO;
import com.znsx.cms.web.dto.omc.GetWareHouseDTO;
import com.znsx.cms.web.dto.omc.ListAddressBookDTO;
import com.znsx.cms.web.dto.omc.ListGisUnitDTO;
import com.znsx.cms.web.dto.omc.ListResourceDTO;
import com.znsx.cms.web.dto.omc.ListResourceUserDTO;
import com.znsx.cms.web.dto.omc.ListTeamDTO;
import com.znsx.cms.web.dto.omc.ListUnitDTO;
import com.znsx.cms.web.dto.omc.ListVehicleDTO;
import com.znsx.cms.web.dto.omc.ResourceUserVO;
import com.znsx.cms.web.dto.omc.ResourceVO;
import com.znsx.cms.web.dto.omc.TeamVO;
import com.znsx.cms.web.dto.omc.VehicleVO;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 应急救援对外接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 上午9:55:16
 */

@Controller
public class EmController extends BaseController {

	@Autowired
	private EmManager emManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private WebGisManager webGisManager;
	@Autowired
	private SysLogManager sysLogManager;
	@Autowired
	private RuleManager ruleManager;
	@Autowired
	private OrganManager organManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private RoadStatusManager roadStatusManager;
	@Autowired
	private WorkRecordManager workRecordManager;

	@InterfaceDescription(logon = true, method = "Create_Unit", cmd = "2310")
	@RequestMapping("/create_unit.json")
	public void createUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);

		String linkMan = reader.getString("linkMan", true);

		String mobile = reader.getString("mobile", true);

		String location = reader.getString("location", true);

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String fax = reader.getString("fax", true);

		String email = reader.getString("email", true);

		String telephone = reader.getString("telephone", true);

		String note = reader.getString("note", true);

		String organId = reader.getString("organId", false);

		String type = reader.getString("type", false);

		Short rescueCapability = reader.getShort("rescueCapability", true);

		Short fireEngineNumber = reader.getShort("fireEngineNumber", true);

		Short ambulanceNumber = reader.getShort("ambulanceNumber", true);

		Short unitLevel = reader.getShort("unitLevel", true);

		Short carNumber = reader.getShort("carNumber", true);

		Short teamNumber = reader.getShort("teamNumber", true);

		String gisId = reader.getString("gisId", true);

		String managerUnit = reader.getString("managerUnit", true);

		String standardNumber = reader.getString("standardNumber", true);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Unit", organId);
		}

		String id = "";

		if (type.equals(TypeDefinition.COOPERATIVE_FIRE)) {
			id = emManager.createUnitFire(name, linkMan, mobile, location,
					longitude, latitude, fax, email, telephone, note, organId,
					standardNumber, rescueCapability, fireEngineNumber, gisId);
		} else if (type.equals(TypeDefinition.COOPERATIVE_HOSPITAL)) {
			id = emManager.createUnitHospital(name, linkMan, mobile, location,
					longitude, latitude, fax, email, telephone, note, organId,
					standardNumber, ambulanceNumber, unitLevel,
					rescueCapability, gisId);
		} else if (type.equals(TypeDefinition.COOPERATIVE_POLICE)) {
			id = emManager.createUnitPolice(name, linkMan, mobile, location,
					longitude, latitude, fax, email, telephone, note, organId,
					standardNumber, gisId);
		} else if (type.equals(TypeDefinition.COOPERATIVE_ROAD_ADMIN)) {
			id = emManager.createUnitRoadAdmin(name, linkMan, mobile, location,
					longitude, latitude, fax, email, telephone, note, organId,
					standardNumber, carNumber, teamNumber, gisId);
		} else if (type.equals(TypeDefinition.COOPERATIVE_WAREHOUSE)) {
			id = emManager.createUnitWareHouse(name, linkMan, mobile, location,
					longitude, latitude, fax, email, telephone, note, organId,
					standardNumber, gisId, managerUnit);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "type["
					+ type + "] invalid !");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2310");
		dto.setMethod("Create_Unit");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Unit", cmd = "2311")
	@RequestMapping("/update_unit.json")
	public void updateUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String name = request.getParameter("name");
		if (null != name && StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Can not modify name to empty !");
		}
		String linkMan = reader.getString("linkMan", true);

		String mobile = reader.getString("mobile", true);

		String location = reader.getString("location", true);

		String longitude = reader.getString("longitude", true);

		String latitude = reader.getString("latitude", true);

		String fax = reader.getString("fax", true);

		String email = reader.getString("email", true);

		String telephone = reader.getString("telephone", true);

		String note = reader.getString("note", true);

		String organId = reader.getString("organId", true);

		String type = reader.getString("type", true);

		Short rescueCapability = reader.getShort("rescueCapability", true);

		Short fireEngineNumber = reader.getShort("fireEngineNumber", true);

		Short ambulanceNumber = reader.getShort("ambulanceNumber", true);

		Short unitLevel = reader.getShort("unitLevel", true);

		Short carNumber = reader.getShort("carNumber", true);

		Short teamNumber = reader.getShort("teamNumber", true);

		String managerUnit = reader.getString("managerUnit", true);

		if (type.equals(TypeDefinition.COOPERATIVE_FIRE)) {
			emManager.updateUnitFire(id, standardNumber, name, linkMan, mobile,
					location, longitude, latitude, fax, email, telephone, note,
					organId, rescueCapability, fireEngineNumber);
		} else if (type.equals(TypeDefinition.COOPERATIVE_HOSPITAL)) {
			emManager.updateUnitHospital(id, standardNumber, name, linkMan,
					mobile, location, longitude, latitude, fax, email,
					telephone, note, organId, ambulanceNumber, unitLevel,
					rescueCapability);
		} else if (type.equals(TypeDefinition.COOPERATIVE_POLICE)) {
			emManager.updateUnitPolice(id, standardNumber, name, linkMan,
					mobile, location, longitude, latitude, fax, email,
					telephone, note, organId);
		} else if (type.equals(TypeDefinition.COOPERATIVE_ROAD_ADMIN)) {
			emManager.updateUnitRoadAdmin(id, standardNumber, name, linkMan,
					mobile, location, longitude, latitude, fax, email,
					telephone, note, organId, carNumber, teamNumber);
		} else if (type.equals(TypeDefinition.COOPERATIVE_WAREHOUSE)) {
			emManager.updateUnitWareHouse(id, standardNumber, name, linkMan,
					mobile, location, longitude, latitude, fax, email,
					telephone, note, organId, managerUnit);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "type["
					+ type + "] invalid !");
		}

		// emManager.updateUnit(id, standardNumber, name, linkMan, mobile,
		// location, longitude, latitude, fax, email, telephone, note,
		// organId);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2311");
		dto.setMethod("Update_Unit");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Unit", cmd = "2312")
	@RequestMapping("/delete_unit.json")
	public void deleteUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		emManager.deleteUnit(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2312");
		dto.setMethod("Delete_Unit");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Unit", cmd = "2313")
	@RequestMapping("/get_unit.json")
	public void getUnit(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		String type = reader.getString("type", false);
		if (type.equals(TypeDefinition.COOPERATIVE_FIRE)) {
			UnitFireVO vo = emManager.getUnitFire(id);
			GetUnitFireDTO dto = new GetUnitFireDTO();
			dto.setCmd("2313");
			dto.setMethod("Get_Unit");
			dto.setUnit(vo);
			writePage(response, dto);
		} else if (type.equals(TypeDefinition.COOPERATIVE_HOSPITAL)) {
			UnitHospitalVO vo = emManager.getHospital(id);
			GetUnitHospitalDTO dto = new GetUnitHospitalDTO();
			dto.setCmd("2313");
			dto.setMethod("Get_Unit");
			dto.setUnit(vo);
			writePage(response, dto);
		} else if (type.equals(TypeDefinition.COOPERATIVE_POLICE)) {
			UnitVO vo = emManager.getUnit(id);
			GetUnitDTO dto = new GetUnitDTO();
			dto.setCmd("2313");
			dto.setMethod("Get_Unit");
			dto.setUnit(vo);
			writePage(response, dto);
		} else if (type.equals(TypeDefinition.COOPERATIVE_ROAD_ADMIN)) {
			UnitRoadAdminVO vo = emManager.getRoadAdmin(id);
			GetUnitRoadAdminDTO dto = new GetUnitRoadAdminDTO();
			dto.setCmd("2313");
			dto.setMethod("Get_Unit");
			dto.setUnit(vo);
			writePage(response, dto);
		} else if (type.equals(TypeDefinition.COOPERATIVE_WAREHOUSE)) {
			UnitWareHouseVO vo = emManager.getWareHouse(id);
			GetWareHouseDTO dto = new GetWareHouseDTO();
			dto.setCmd("2313");
			dto.setMethod("Get_Unit");
			dto.setUnit(vo);
			writePage(response, dto);
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "type["
					+ type + "] invalid !");
		}

	}

	@InterfaceDescription(logon = true, method = "List_Unit", cmd = "2314")
	@RequestMapping("/list_unit.json")
	public void listUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String name = reader.getString("name", true);

		String organId = reader.getString("organId", false);

		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
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
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer countTotal = emManager.countTotalUnit(name, organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && countTotal.intValue() != 0) {
			if (startIndex.intValue() >= countTotal.intValue()) {
				startIndex -= ((startIndex.intValue() - countTotal.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<UnitVO> list = emManager
				.listUnit(name, organId, startIndex, limit);
		ListUnitDTO dto = new ListUnitDTO();
		dto.setCmd("2314");
		dto.setTotalCount(countTotal + "");
		dto.setUnitlist(list);
		dto.setMethod("List_Unit");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Resource", cmd = "2320")
	@RequestMapping("/create_resource.json")
	public void createResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);

		String unitId = reader.getString("unitId", false);

		String standardNumber = reader.getString("standardNumber", true);

		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Resource", null);
		}

		String abilityType = reader.getString("abilityType", false);

		String note = reader.getString("note", true);

		String unitType = reader.getString("unitType", true);

		String minNumber = reader.getString("minNumber", false);

		String resourceNumber = reader.getString("resourceNumber", false);

		String id = emManager.createResource(name, unitId, standardNumber,
				abilityType, note, unitType, minNumber, resourceNumber);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2320");
		dto.setMethod("Create_Resource");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Resource", cmd = "2321")
	@RequestMapping("/update_resource.json")
	public void updateResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		String name = reader.getString("name", true);
		String unitId = reader.getString("unitId", true);
		String standardNumber = reader.getString("standardNumber", true);
		String abilityType = reader.getString("abilityType", true);

		String note = reader.getString("note", true);

		String unitType = reader.getString("unitType", true);

		String minNumber = reader.getString("minNumber", true);

		String resourceNumber = reader.getString("resourceNumber", true);

		emManager.updateResource(id, name, unitId, standardNumber, abilityType,
				note, unitType, minNumber, resourceNumber);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2321");
		dto.setMethod("Update_Resource");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Resource", cmd = "2322")
	@RequestMapping("/delete_resource.json")
	public void deleteResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		emManager.deleteResource(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2322");
		dto.setMethod("Delete_Resource");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Resource", cmd = "2323")
	@RequestMapping("/get_resource.json")
	public void getResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		ResourceVO vo = emManager.getResource(id);
		GetResourceDTO dto = new GetResourceDTO();
		dto.setCmd("2323");
		dto.setMethod("Get_Resource");
		dto.setResource(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Resource", cmd = "2324")
	@RequestMapping("/list_resource.json")
	public void listResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String unitId = reader.getString("unitId", true);
		String abilityType = reader.getString("abilityType", true);
		String unitType = reader.getString("unitType", true);
		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
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
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}

		Integer countTotal = emManager.countTotalResource(unitId, abilityType,
				unitType);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && countTotal.intValue() != 0) {
			if (startIndex.intValue() >= countTotal.intValue()) {
				startIndex -= ((startIndex.intValue() - countTotal.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<ResourceVO> list = emManager.listResource(unitId, abilityType,
				unitType, startIndex, limit);

		ListResourceDTO dto = new ListResourceDTO();
		dto.setCmd("2324");
		dto.setMethod("List_Resource");
		dto.setResourceList(list);
		dto.setTotalCount(countTotal + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Vehicle", cmd = "2340")
	@RequestMapping("/create_vehicle.json")
	public void createVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String unitId = reader.getString("unitId", false);

		String name = reader.getString("name", false);

		String standardNumber = reader.getString("standardNumber", true);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Vehicle", null);
		}

		String abilityType = reader.getString("abilityType", false);

		String seatNumber = reader.getString("seatNumber", true);

		String vehicleNumber = reader.getString("vehicleNumebr", true);

		String status = reader.getString("status", true);

		String note = reader.getString("note", true);

		String id = emManager.createVehicle(unitId, name, standardNumber,
				abilityType, seatNumber, vehicleNumber, status, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2340");
		dto.setMethod("Create_Vehicle");
		dto.setMessage(id);

		writePage(response, dto);

	}

	@InterfaceDescription(logon = true, method = "Update_Vehicle", cmd = "2341")
	@RequestMapping("/update_vehicle.json")
	public void updateVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String unitId = reader.getString("unitId", true);

		String name = reader.getString("name", true);

		String standardNumber = reader.getString("standardNumber", true);

		String abilityType = reader.getString("abilityType", true);

		String seatNumber = reader.getString("seatNumber", true);

		String vehicleNumber = reader.getString("vehicleNumebr", true);

		String status = reader.getString("status", true);

		String note = reader.getString("note", true);

		emManager.updateVehicle(id, unitId, name, standardNumber, abilityType,
				seatNumber, vehicleNumber, status, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2341");
		dto.setMethod("Update_Vehicle");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Vehicle", cmd = "2342")
	@RequestMapping("/delete_vehicle.json")
	public void deleteVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		emManager.deleteVehicle(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2342");
		dto.setMethod("Delete_Vehicle");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Vehicle", cmd = "2343")
	@RequestMapping("/get_vehicle.json")
	public void getVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		VehicleVO vo = emManager.getVehicle(id);
		GetVehicleDTO dto = new GetVehicleDTO();
		dto.setCmd("2343");
		dto.setMethod("Get_Vehicle");
		dto.setVehicle(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Vehicle", cmd = "2344")
	@RequestMapping("/list_vehicle.json")
	public void listVehicle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String unitId = reader.getString("unitId", false);

		String abilityType = reader.getString("abilityType", true);

		String name = reader.getString("name", true);

		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
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
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		Integer countTotal = emManager.countTotalVehilce(unitId, abilityType,
				name);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && countTotal.intValue() != 0) {
			if (startIndex.intValue() >= countTotal.intValue()) {
				startIndex -= ((startIndex.intValue() - countTotal.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<VehicleVO> list = emManager.listVehicle(unitId, abilityType, name,
				startIndex, limit);

		ListVehicleDTO dto = new ListVehicleDTO();
		dto.setCmd("2344");
		dto.setMethod("List_Vehicle");
		dto.setVehicleList(list);
		dto.setTotalCount(countTotal + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Team", cmd = "2330")
	@RequestMapping("/create_team.json")
	public void createTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", false);

		String standardNumber = reader.getString("standardNumber", true);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("Team", null);
		}

		String unitId = reader.getString("unitId", false);

		String type = reader.getString("type", false);

		String note = reader.getString("note", true);

		String id = emManager.createTeam(name, standardNumber, unitId, type,
				note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2330");
		dto.setMethod("Create_Team");
		dto.setMessage(id);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Update_Team", cmd = "2331")
	@RequestMapping("/update_team.json")
	public void updateTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String name = reader.getString("name", true);

		String standardNumber = reader.getString("standardNumber", true);

		String unitId = reader.getString("unitId", true);

		String type = reader.getString("type", true);

		String note = reader.getString("note", false);

		emManager.updateTeam(id, name, standardNumber, unitId, type, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2331");
		dto.setMethod("Update_Team");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Team", cmd = "2332")
	@RequestMapping("/delete_team.json")
	public void deleteTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		emManager.deleteTeam(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2332");
		dto.setMethod("Delete_Team");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Team", cmd = "2333")
	@RequestMapping("/get_team.json")
	public void getTeam(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		TeamVO vo = emManager.getTeam(id);
		GetTeamDTO dto = new GetTeamDTO();
		dto.setCmd("2333");
		dto.setMethod("Get_Team");
		dto.setTeam(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Team", cmd = "2334")
	@RequestMapping("/list_team.json")
	public void listTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String unitId = reader.getString("unitId", false);

		String type = reader.getString("type", true);

		String name = reader.getString("name", true);

		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
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
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		Integer countTotal = emManager.countTotalTeam(unitId, type, name);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && countTotal.intValue() != 0) {
			if (startIndex.intValue() >= countTotal.intValue()) {
				startIndex -= ((startIndex.intValue() - countTotal.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<TeamVO> list = emManager.listTeam(unitId, type, name, startIndex,
				limit);

		ListTeamDTO dto = new ListTeamDTO();
		dto.setCmd("2334");
		dto.setMethod("List_Team");
		dto.setTeamList(list);
		dto.setTotalCount(countTotal + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Create_Resource_User", cmd = "2350")
	@RequestMapping("/create_resource_user.json")
	public void createResourceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String standardNumber = reader.getString("standardNumber", true);
		if (StringUtils.isBlank(standardNumber)) {
			standardNumber = userManager.generateStandardNum("ResourceUser",
					null);
		}

		String teamId = reader.getString("teamId", false);

		String telephone = reader.getString("telephone", true);

		String name = reader.getString("name", false);

		String type = reader.getString("type", false);

		String isAdmin = reader.getString("isAdmin", true);

		String speciality = reader.getString("speciality", true);

		String note = reader.getString("note", true);

		String id = emManager.createResourceUser(standardNumber, teamId,
				telephone, name, type, isAdmin, speciality, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2350");
		dto.setMethod("Create_Resource_User");
		dto.setMessage(id);

		writePage(response, dto);

	}

	@InterfaceDescription(logon = true, method = "Update_Resource_User", cmd = "2351")
	@RequestMapping("/update_resource_user.json")
	public void updateResourceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		String standardNumber = reader.getString("standardNumber", true);

		String teamId = reader.getString("teamId", true);

		String telephone = reader.getString("telephone", true);

		String name = reader.getString("name", true);

		String type = reader.getString("type", true);

		String isAdmin = reader.getString("isAdmin", true);

		String speciality = reader.getString("speciality", true);

		String note = reader.getString("note", true);

		emManager.updateResourceUser(id, standardNumber, teamId, telephone,
				name, type, isAdmin, speciality, note);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2351");
		dto.setMethod("Update_Resource_User");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Resource_User", cmd = "2353")
	@RequestMapping("/get_resource_user.json")
	public void getResourceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		ResourceUserVO vo = emManager.getResourceUser(id);
		GetResourceUserDTO dto = new GetResourceUserDTO();
		dto.setCmd("2353");
		dto.setMethod("Get_Resource_User");
		dto.setResourceUser(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Resource_User", cmd = "2354")
	@RequestMapping("/list_resource_user.json")
	public void listResourceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String name = reader.getString("name", true);

		String type = reader.getString("type", true);

		String teamId = reader.getString("teamId", false);

		Integer startIndex = 0;
		String startIndexString = request.getParameter("startIndex");
		if (StringUtils.isNotBlank(startIndexString)) {
			try {
				startIndex = Integer.parseInt(startIndexString);
			} catch (NumberFormatException n) {
				n.printStackTrace();
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
			} catch (NumberFormatException n) {
				n.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Parameter limit[" + limitString + "] invalid !");
			}
		}
		Integer countTotal = emManager.countTotalResourceUser(name, type,
				teamId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && countTotal.intValue() != 0) {
			if (startIndex.intValue() >= countTotal.intValue()) {
				startIndex -= ((startIndex.intValue() - countTotal.intValue())
						/ limit + 1)
						* limit;
			}
		}

		List<ResourceUserVO> list = emManager.listResourceUser(name, type,
				teamId, startIndex, limit);

		ListResourceUserDTO dto = new ListResourceUserDTO();
		dto.setCmd("2354");
		dto.setMethod("List_Resource_User");
		dto.setResourceUserList(list);
		dto.setTotalCount(countTotal + "");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Resource_User", cmd = "2352")
	@RequestMapping("/delete_resource_user.json")
	public void deleteResourceUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		emManager.deleteResourceUser(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2352");
		dto.setMethod("Delete_Resource_User");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Save_Scheme_Templet", cmd = "1230")
	@RequestMapping("/save_scheme_templet.xml")
	public void saveSchemeTemplet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		com.znsx.cms.service.model.ResourceVO user = resource.get();
		String organId = user.getOrganId();
		Element organ = requestRoot.getChild("OrganId");
		if (null != organ) {
			organId = organ.getText();
		}
		if (StringUtils.isBlank(organId)) {
			organId = user.getOrganId();
		}

		Element schemeTemplet = requestRoot.getChild("SchemeTemplet");
		String name = schemeTemplet.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [" + name + "]");
		}

		String id = emManager.saveSchemeTemplet(name, organId, schemeTemplet);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1230");
		dto.setMethod("Save_Scheme_Templet");
		dto.setMessage(id);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Scheme_Templet", cmd = "1232")
	@RequestMapping("/list_scheme_templet.xml")
	public void listSchemeTemplet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		Short type = reader.getShort("type", true);

		List<SchemeTempletVO> list = emManager.listSchemeTemplet(organId, type);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1232");
		dto.setMethod("List_Scheme_Templet");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (SchemeTempletVO vo : list) {
			Element schemeTemplet = ElementUtil.createElement("SchemeTemplet",
					vo, null, null);
			root.addContent(schemeTemplet);
			List<SchemeTempletVO.Step> steps = vo.getSteps();
			for (SchemeTempletVO.Step step : steps) {
				Element stepElement = ElementUtil.createElement("Step", step,
						null, null);
				schemeTemplet.addContent(stepElement);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Scheme", cmd = "1233")
	@RequestMapping("/save_scheme.xml")
	public void saveScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		com.znsx.cms.service.model.ResourceVO user = resource.get();
		String organId = user.getOrganId();
		Element organ = requestRoot.getChild("OrganId");
		if (null != organ) {
			organId = organ.getText();
		}
		if (StringUtils.isBlank(organId)) {
			organId = user.getOrganId();
		}

		Element scheme = requestRoot.getChild("Scheme");
		String id = scheme.getAttributeValue("Id");

		String templetId = scheme.getAttributeValue("SchemeTempletId");
		if (StringUtils.isBlank(templetId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [SchemeTempletId]");
		}

		String name = scheme.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name]");
		}

		String eventId = scheme.getAttributeValue("EventId");
		if (StringUtils.isBlank(eventId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [EventId]");
		}

		String schemeId = emManager.saveScheme(id, name, organId, templetId,
				eventId, user.getId(), user.getName(), scheme);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1233");
		dto.setMethod("Save_Scheme");
		dto.setMessage(schemeId);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_Scheme", cmd = "1234")
	@RequestMapping("/get_scheme.xml")
	public void getScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String eventId = reader.getString("eventId", false);

		SchemeInstanceVO scheme = emManager.getSchemeInstance(eventId);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1234");
		dto.setMethod("Get_Scheme");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element schemeElement = ElementUtil.createElement("Scheme", scheme,
				null, null);
		root.addContent(schemeElement);

		List<SchemeInstanceVO.Step> steps = scheme.getSteps();
		for (SchemeInstanceVO.Step step : steps) {
			Element stepElement = ElementUtil.createElement("Step", step, null,
					null);
			schemeElement.addContent(stepElement);

			List<SchemeInstanceVO.Item> items = step.getItems();
			for (SchemeInstanceVO.Item item : items) {
				Element itemElement = ElementUtil.createElement("Item", item,
						null, null);
				stepElement.addContent(itemElement);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Scheme_Templet", cmd = "1231")
	@RequestMapping("/delete_scheme_templet.xml")
	public void deleteSchemeTemplet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String schemeTempletId = reader.getString("schemeTempletId", false);

		emManager.deleteTemplet(schemeTempletId);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1231");
		dto.setMethod("Delete_Scheme_Templet");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Wfs_List_Unit", cmd = "2450")
	@RequestMapping("/wfs_list_unit.json")
	public void wfsListUnit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String type = reader.getString("type", false);
		String wfsUrl = webGisManager.getWfsUrl();
		List<WfsRoadAdminVO> list = new ArrayList<WfsRoadAdminVO>();
		if (type.equals(TypeDefinition.COOPERATIVE_FIRE)) {
			list = webGisManager.wfsListFire(wfsUrl);
		} else if (type.equals(TypeDefinition.COOPERATIVE_HOSPITAL)) {
			list = webGisManager.wfsListHospital(wfsUrl);
		} else if (type.equals(TypeDefinition.COOPERATIVE_POLICE)) {
			list = webGisManager.wfsListPolice(wfsUrl);
		} else if (type.equals(TypeDefinition.COOPERATIVE_ROAD_ADMIN)) {
			list = webGisManager.wfsListRoadAdmin(wfsUrl);
		} else if (type.equals(TypeDefinition.COOPERATIVE_WAREHOUSE)) {
			list = null;
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "type["
					+ type + "] invalid !");
		}
		ListGisUnitDTO dto = new ListGisUnitDTO();
		dto.setCmd("2450");
		dto.setGisUnit(list);
		dto.setMethod("Wfs_List_Unit");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Event_Entry", cmd = "1240")
	@RequestMapping("/event_entry.xml")
	public void eventEntry(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", true);
		String name = reader.getString("name", true);
		String standardNumber = reader.getString("standardNumber", false);
		Short type = reader.getShort("type", false);
		String subType = reader.getString("subType", false);
		Short eventLevel = reader.getShort("eventLevel", true);
		Short roadType = reader.getShort("roadType", true);
		String location = reader.getString("location", true);
		String beginStake = reader.getString("beginStake", false);
		beginStake = StringUtils.replace(beginStake, " ", "+");
		// 验证桩号
		NumberUtil.floatStake(beginStake);
		String endStake = reader.getString("endStake", false);
		endStake = StringUtils.replace(endStake, " ", "+");
		// 验证桩号
		NumberUtil.floatStake(endStake);
		Short navigation = reader.getShort("navigation", true);
		String sendUser = reader.getString("sendUser", true);
		String phone = reader.getString("phone", true);
		String impactProvince = reader.getString("impactProvince", true);
		Long createTime = reader.getLong("createTime", true);
		Long estimatesRecoverTime = reader
				.getLong("estimatesRecoverTime", true);
		Short hurtNumber = reader.getShort("hurtNumber", true);
		Short deathNumber = reader.getShort("deathNumber", true);
		Integer delayHumanNumber = reader.getInteger("delayHumanNumber", true);
		Integer crowdMeter = reader.getInteger("crowdMeter", true);
		Short damageCarNumber = reader.getShort("damageCarNumber", true);
		Integer delayCarNumber = reader.getInteger("delayCarNumber", true);
		Integer lossAmount = reader.getInteger("lossAmount", true);
		Long recoverTime = reader.getLong("recoverTime", true);
		String description = reader.getString("description", true);
		Short isFire = reader.getShort("isFire", true);
		Short laneNumber = reader.getShort("laneNumber", true);
		String note = reader.getString("note", true);
		String administration = reader.getString("administration", true);
		String managerUnit = reader.getString("managerUnit", true);
		Short flag = reader.getShort("flag", true);
		String roadName = reader.getString("roadName", true);
		String recoverStatus = reader.getString("recoverStatus", true);
		Short eventStatus = reader.getShort("eventStatus", true);

		Organ organ = organManager.findBySn(standardNumber);
		StakeNumberLib stakeNumberLib = emManager.getStakeNumberLib(beginStake,
				organ.getId());

		if (StringUtils.isBlank(id)) {
			id = emManager.eventEntry(name, organ.getId(), type, subType,
					eventLevel, roadType, location, beginStake, endStake,
					navigation, sendUser, phone, impactProvince, createTime,
					estimatesRecoverTime, hurtNumber, deathNumber,
					delayHumanNumber, crowdMeter, damageCarNumber,
					delayCarNumber, lossAmount, recoverTime, description,
					isFire, laneNumber, note, administration, managerUnit,
					roadName, recoverStatus, stakeNumberLib.getLongitude(),
					stakeNumberLib.getLatitude());
		} else {
			emManager.updateEvent(id, name, standardNumber, type, subType,
					eventLevel, roadType, location, beginStake, endStake,
					navigation, sendUser, phone, impactProvince, createTime,
					estimatesRecoverTime, hurtNumber, deathNumber,
					delayHumanNumber, crowdMeter, damageCarNumber,
					delayCarNumber, lossAmount, recoverTime, description,
					isFire, laneNumber, note, administration, managerUnit,
					flag, roadName, recoverStatus, eventStatus);
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1240");
		dto.setMethod("Event_Entry");
		dto.setMessage(id);

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);

	}

	@InterfaceDescription(logon = false, method = "Event_Upload_Image", cmd = "1241")
	@RequestMapping("/event_upload_image.xml")
	public void eventUploadImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Image image = null;
		// 检查是否文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 登陆的用户
			com.znsx.cms.service.model.ResourceVO resource = null;
			// 资源ID
			String resourceId = null;
			// 资源类型
			String resourceType = null;
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// 图片数据流
			InputStream in = null;
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
				// 简单文本参数部分，得到resourceId
				if ("resourceId".equals(fieldName)) {
					resourceId = item.getString();
				}
				// 文件流部分
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
			if (StringUtils.isBlank(resourceId)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [resourceId]");
			}
			// 事件关联图片
			image = emManager.getResourceImageId(resourceId);
			// 图片流写入到数据库
			emManager.upload(image.getId(), in);

			// 单独记录成功的日志
			SysLog log = new SysLog();
			log.setResourceId(resource.getId());
			log.setResourceName(resource.getName());
			log.setResourceType(resource.getType());
			log.setTargetId(resourceId);
			log.setTargetName(image.getName());
			log.setTargetType(resourceType);
			log.setLogTime(System.currentTimeMillis());
			log.setOperationType("eventUploadImage");
			log.setOperationName("上传图片");
			log.setOperationCode("1241");
			log.setSuccessFlag(ErrorCode.SUCCESS);
			log.setCreateTime(System.currentTimeMillis());
			log.setOrganId(resource.getOrganId());
			sysLogManager.batchLog(log);
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1241");
		dto.setMethod("Event_Upload_Image");
		if (null != image) {
			dto.setMessage(image.getId().toString());
		}

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Get_Even", cmd = "1242")
	@RequestMapping("/get_event.xml")
	public void getEvent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		Element event = emManager.getEvent(id);
		Element imageIds = emManager.getImageIds(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1242");
		dto.setMethod("Get_Even");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(event);
		root.addContent(imageIds);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Generate_Scheme", cmd = "1243")
	@RequestMapping("/generate_scheme.xml")
	public void generateScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String eventId = reader.getString("eventId", false);

		Event event = emManager.getEmEvent(eventId);
		// if (TypeDefinition.EVENT_STATUS_0 != event.getStatus().shortValue())
		// {
		// throw new BusinessException(ErrorCode.EVENT_SCHEME_GENERATED,
		// "Event[" + eventId + "] has been dealed with other user !");
		// }
		// 构建规则输入条件
		com.znsx.cms.service.rule.model.Event conditions = new com.znsx.cms.service.rule.model.Event();
		List<String> reasons = new LinkedList<String>();
		conditions.setBeginStake(event.getBeginStake());
		conditions.setCrowdMeter(event.getCrowdMeter() == null ? 0 : event
				.getCrowdMeter().intValue());
		conditions.setDamageCarNumber(event.getDamageCarNumber() == null ? 0
				: event.getDamageCarNumber().intValue());
		conditions.setDeathNumber(event.getDeathNumber() == null ? 0 : event
				.getDeathNumber().intValue());
		conditions.setDelayHumanNumber(event.getDelayHumanNumber() == null ? 0
				: event.getDelayHumanNumber().intValue());
		conditions.setEndStake(event.getEndStake());
		conditions.setEventLevel(event.getEventLevel() == null ? 1 : event
				.getEventLevel().intValue());
		Short isFire = event.getIsFire();
		conditions.setFire(isFire == null ? false
				: isFire.shortValue() != (short) 0);
		conditions.setHurtNumber(event.getHurtNumber() == null ? 0 : event
				.getHurtNumber().intValue());
		conditions.setLaneNumber(event.getLaneNumber() == null ? 0 : event
				.getLaneNumber().intValue());
		conditions.setNavigation(event.getNavigation() == null ? 0 : event
				.getNavigation().intValue());
		if (event.getDelayHumanNumber() != null
				&& event.getDelayHumanNumber().intValue() > 0) {
			reasons.add("9-7");
		}
		String subType = event.getSubType();
		if (StringUtils.isNotBlank(subType)) {
			String[] subTypes = subType.split("\\|");
			for (String st : subTypes) {
				reasons.add(st);
			}
		}
		conditions.setReasons(reasons);

		// 查询事故发生路段
		OrganRoad road = organManager.getEventRoad(event.getRoadName(), event
				.getOrgan().getId());
		List<VehicleDetector> vds = null;
		if (null != road) {
			vds = emManager.listVd(road.getId());
		} else {
			vds = emManager.listVd(event.getOrgan().getId());
		}
		// 能否借用对侧车道，通过对侧车道车检器检测值进行通行能力运算得到
		conditions.setOppositeSupport(roadStatusManager.isOppositeUsable(road,
				vds, event.getNavigation().toString(), event.getBeginStake(),
				event.getEndStake()));
		// 通行能力(-1:交通量>通行能力，0:交通量=通行能力,1:交通量<通行能力)
		conditions.setQc(roadStatusManager.compareQc(road, vds, event
				.getNavigation().toString(), event.getBeginStake(), event
				.getEndStake(), event.getLaneNumber().intValue()));
		conditions.setRoadType(event.getRoadType() == null ? 0 : event
				.getRoadType().intValue());
		conditions.setType(event.getType().intValue());
		// TODO 通过气象/能见度检测器检测值得到
		conditions.setVisibility(200);
		// 生成规则输出
		ruleManager.generateScheme(conditions);
		// 修改Event状态
		emManager.updateEvent(eventId, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null,
				TypeDefinition.EVENT_STATUS_1);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1243");
		dto.setMethod("Generate_Scheme");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		// 长潭不需要注释掉

		// Element policeElement = new Element("Police");
		// List<String> policeActions = conditions.getPoliceActions();
		// for (String action : policeActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// policeElement.addContent(act);
		// }
		// root.addContent(policeElement);
		//
		//
		// Element fireElement = new Element("Fire");
		// List<String> fireActions = conditions.getFireActions();
		// for (String action : fireActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// fireElement.addContent(act);
		// }
		// root.addContent(fireElement);
		//
		// Element hospitalElement = new Element("Hospital");
		// List<String> hospitalActions = conditions.getHospitalActions();
		// for (String action : hospitalActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// hospitalElement.addContent(act);
		// }
		// root.addContent(hospitalElement);
		//
		// Element roadAdminElement = new Element("RoadAdmin");
		// List<String> roadAdminActions = conditions.getRoadAdminActions();
		// for (String action : roadAdminActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// roadAdminElement.addContent(act);
		// }
		// root.addContent(roadAdminElement);
		// Element policeElement = new Element("Police");
		// List<String> policeActions = conditions.getPoliceActions();
		// for (String action : policeActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// policeElement.addContent(act);
		// }
		// root.addContent(policeElement);
		//
		//
		// Element fireElement = new Element("Fire");
		// List<String> fireActions = conditions.getFireActions();
		// for (String action : fireActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// fireElement.addContent(act);
		// }
		// root.addContent(fireElement);
		//
		// Element hospitalElement = new Element("Hospital");
		// List<String> hospitalActions = conditions.getHospitalActions();
		// for (String action : hospitalActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// hospitalElement.addContent(act);
		// }
		// root.addContent(hospitalElement);
		//
		// Element roadAdminElement = new Element("RoadAdmin");
		// List<String> roadAdminActions = conditions.getRoadAdminActions();
		// for (String action : roadAdminActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// roadAdminElement.addContent(act);
		// }
		// root.addContent(roadAdminElement);

		// Element policeElement = new Element("Police");
		// List<String> policeActions = conditions.getPoliceActions();
		// for (String action : policeActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// policeElement.addContent(act);
		// }
		// root.addContent(policeElement);
		//
		//
		// Element fireElement = new Element("Fire");
		// List<String> fireActions = conditions.getFireActions();
		// for (String action : fireActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// fireElement.addContent(act);
		// }
		// root.addContent(fireElement);
		//
		// Element hospitalElement = new Element("Hospital");
		// List<String> hospitalActions = conditions.getHospitalActions();
		// for (String action : hospitalActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// hospitalElement.addContent(act);
		// }
		// root.addContent(hospitalElement);
		//
		// Element roadAdminElement = new Element("RoadAdmin");
		// List<String> roadAdminActions = conditions.getRoadAdminActions();
		// for (String action : roadAdminActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// roadAdminElement.addContent(act);
		// }
		// root.addContent(roadAdminElement);

		// 步骤0：投墙操作
		if (event.getType().intValue() == TypeDefinition.EVENT_TYPE_VD) {
			// 視頻檢測事件不做投墻操作
			Element realPlayElement = new Element("RealPlay");
			Element realPlayAct = new Element("Action");
			realPlayAct.setText("查看视频画面");
			realPlayAct.setAttribute("Id", event.getSendUser());
			realPlayElement.addContent(realPlayAct);
			root.addContent(realPlayElement);
		} else {
			Element displayElement = new Element("Display");
			Element displayAct = new Element("Action");
			displayAct.setText("摄像头画面投墙");
			displayElement.addContent(displayAct);
			root.addContent(displayElement);
		}

		// 步骤1：通过导入的交警值班计划，取得当天的值班人员与联系电话
		WorkPlan policeWorkPlan = workRecordManager
				.getCurrentWorkPlan(TypeDefinition.WORK_PLAN_TYPE_POLICE);
		Element policeElement = new Element("Police");
		Element policeAct = new Element("Action");
		policeAct.setText("通知交警，当班人员：" + policeWorkPlan.getName() + "，联系电话："
				+ policeWorkPlan.getPhone());
		policeElement.addContent(policeAct);
		root.addContent(policeElement);
		// 步骤2：通过导入的路政值班计划，取得当天的值班人员与联系电话
		WorkPlan roadAdminWorkPlan = workRecordManager
				.getCurrentWorkPlan(TypeDefinition.WORK_PLAN_TYPE_ROAD_ADMIN);
		Element roadAdminElement = new Element("RoadAdmin");
		Element roadAdminAct = new Element("Action");
		roadAdminAct.setText("通知路政，当班人员：" + roadAdminWorkPlan.getName()
				+ "，联系电话：" + roadAdminWorkPlan.getPhone());
		roadAdminElement.addContent(roadAdminAct);
		root.addContent(roadAdminElement);
		// 步骤3：根据事件类型提示是否上报
		if (reasons.contains("0-2") || reasons.contains("2-1")
				|| reasons.contains("3-1") || reasons.contains("4-1")) {
			Element reportElement = new Element("Report");
			Element reportAct = new Element("Action");
			reportAct.setText("事件上报省中心");
			reportElement.addContent(reportAct);
			root.addContent(reportElement);
		}

		// 步骤4: 情报板发布
		if (event.getType().intValue() == TypeDefinition.EVENT_TYPE_VD) {
			// 視頻檢測事件只查找前方10000米的情報板
			List<ControlDeviceCms> cmsList = emManager.listCmsByRange(
					event.getBeginStake(), event.getEndStake(), 10000,
					event.getNavigation(), event.getOrgan().getId());
			Element vmsElement = new Element("Cms");
			for (ControlDeviceCms cms : cmsList) {
				Element act = new Element("Action");
				String content = cms.getName() + "编辑";
				act.setAttribute("Id", cms.getId());
				act.setText(content);
				vmsElement.addContent(act);
			}
			root.addContent(vmsElement);
		} else {
			Element vmsElement = new Element("Cms");
			List<String> vmsActions = conditions.getVmsActions();
			for (String action : vmsActions) {
				Element act = new Element("Action");
				String content = StringUtils.replace(action, "{stake}",
						event.getBeginStake());
				content = StringUtils.replace(content, "{name}",
						event.getLocation());

				content = StringUtils.replace(content, "{organName}", event
						.getOrgan().getName());
				// 长潭项目写死
				if (event.getNavigation().intValue() == TypeDefinition.NAVIGATION_UPLOAD) {
					// if (road.getName().indexOf("长潭") >= 0) {
					// content = StringUtils.replace(content, "{pos}", "北往南");
					// } else {
					// content = StringUtils.replace(content, "{pos}", "西往东");
					// }
					content = StringUtils.replace(content, "{pos}", "上行");
				} else {
					// if (road.getName().indexOf("长潭") >= 0) {
					// content = StringUtils.replace(content, "{pos}", "南往北");
					// } else {
					// content = StringUtils.replace(content, "{pos}", "东往西");
					// }
					content = StringUtils.replace(content, "{pos}", "下行");
				}
				// 查找确切情报板
				ControlDevice cms = null;
				if (content.startsWith("上游收费站入口情报板")) {
					cms = emManager.getEventVms(road.getId(), event
							.getBeginStake(), event.getEndStake(), event
							.getNavigation().intValue(), "entrance");
				} else if (content.startsWith("主线情报板")) {
					cms = emManager.getEventVms(road.getId(), event
							.getBeginStake(), event.getEndStake(), event
							.getNavigation().intValue(), "road");
				} else if (content.startsWith("上游出口匝道情报板")) {
					cms = emManager.getEventVms(road.getId(), event
							.getBeginStake(), event.getEndStake(), event
							.getNavigation().intValue(), "exit");
				} else if (content.startsWith("下游对侧收费站入口情报板")) {
					cms = emManager.getEventVms(road.getId(), event
							.getBeginStake(), event.getEndStake(), event
							.getNavigation().intValue(), "oppEntrance");
				} else if (content.startsWith("下游对侧主线情报板")) {
					cms = emManager.getEventVms(road.getId(), event
							.getBeginStake(), event.getEndStake(), event
							.getNavigation().intValue(), "oppRoad");
				} else {
					continue;
				}
				if (cms == null) {
					continue;
				}
				int index = content.indexOf("：");
				if (index < 0) {
					index = content.indexOf(":");
				}
				content = content.substring(index);
				content = cms.getName() + content;
				act.setAttribute("Id", cms.getId());
				act.setText(content);
				vmsElement.addContent(act);
			}
			root.addContent(vmsElement);
		}

		// 长潭不需要注释掉
		// // 收费站控制属于交通控制，但是接口中要区分开，所以特殊处理
		// Element roadControlElement = new Element("RoadControl");
		// Element tollgateElement = new Element("Tollgate");
		// List<String> roadActions = conditions.getRoadActions();
		// // 过渡区位置判定
		// String buffer = NumberUtil.bufferPosition(conditions.getBeginStake(),
		// conditions.getEndStake(), conditions.getNavigation(), false);
		// String bufferOpposite = NumberUtil.bufferPosition(
		// conditions.getBeginStake(), conditions.getEndStake(),
		// conditions.getNavigation(), true);
		// for (String action : roadActions) {
		// Element act = new Element("Action");
		// String content = StringUtils.replace(action, "{buffer}", buffer);
		// content = StringUtils.replace(content, "{bufferOpposite}",
		// bufferOpposite);
		// act.setText(content);
		// if (action.indexOf("收费站") >= 0) {
		// tollgateElement.addContent(act);
		// } else {
		// roadControlElement.addContent(act);
		// }
		// }
		// root.addContent(roadControlElement);
		// root.addContent(tollgateElement);
		//
		// Element resourceElement = new Element("Resource");
		// Set<String> resourceActions = conditions.getResourceActions();
		// for (String action : resourceActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// resourceElement.addContent(act);
		// }
		// root.addContent(resourceElement);
		//
		// Element smsElement = new Element("Sms");
		// List<String> smsActions = conditions.getSmsActions();
		// for (String action : smsActions) {
		// Element act = new Element("Action");
		// act.setText(action);
		// smsElement.addContent(act);
		// }
		// root.addContent(smsElement);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Search_Resource", cmd = "1244")
	@RequestMapping("/search_resource.xml")
	public void searchResource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);

		String beginStake = reader.getString("beginStake", false);
		beginStake = StringUtils.replace(beginStake, " ", "+");

		String endStake = reader.getString("endStake", false);
		endStake = StringUtils.replace(endStake, " ", "+");

		Integer range = reader.getInteger("range", true);

		Integer navigation = reader.getInteger("navigation", false);

		Integer type = reader.getInteger("type", false);

		Element body = null;

		switch (type.intValue()) {
		case TypeDefinition.DEVICE_TYPE_CMS:
			List<ControlDeviceCms> cmss = emManager.listCmsByRange(beginStake,
					endStake, range, navigation, organId);
			body = new Element("CmsList");
			List<String> excludeProperties = new LinkedList<String>();
			excludeProperties.add("createTime");
			excludeProperties.add("period");
			excludeProperties.add("reserve");
			excludeProperties.add("note");
			excludeProperties.add("sectionType");
			for (ControlDeviceCms cms : cmss) {
				Element cmsElement = ElementUtil.createElement("Cms", cms,
						null, excludeProperties);
				cmsElement.setAttribute("DasSN", cms.getDas()
						.getStandardNumber());
				body.addContent(cmsElement);
			}
			break;
		case TypeDefinition.DEVICE_TYPE_ROAD_MOUTH:
			List<RoadMouth> roadMouths = emManager.listRoadMouthByRange(
					beginStake, endStake, range, navigation, organId);
			body = new Element("RoadMouthList");
			for (RoadMouth roadMouth : roadMouths) {
				Element roadMouthElement = ElementUtil.createElement(
						"RoadMouth", roadMouth, null, null);
				roadMouthElement.setAttribute("Type",
						TypeDefinition.DEVICE_TYPE_ROAD_MOUTH + "");
				body.addContent(roadMouthElement);
			}
			break;
		case TypeDefinition.DEVICE_TYPE_TOLLGATE:
			List<Tollgate> tollgates = emManager.listTollgateByRange(
					beginStake, endStake, range, navigation, organId);
			body = new Element("TollgateList");
			for (Tollgate tollgate : tollgates) {
				Element tollgateElement = ElementUtil.createElement("Tollgate",
						tollgate, null, null);
				tollgateElement.setAttribute("Type",
						TypeDefinition.DEVICE_TYPE_TOLLGATE + "");
				body.addContent(tollgateElement);
			}
			break;
		default:
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1244");
		dto.setMethod("Search_Resource");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		root.addContent(body);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Event_Action", cmd = "1245")
	@RequestMapping("/save_event_action.xml")
	public void saveEventAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		com.znsx.cms.service.model.ResourceVO user = resource.get();

		String eventId = requestRoot.getChildText("EventId");
		if (StringUtils.isBlank(eventId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [EventId]");
		}

		String targetType = requestRoot.getChildText("TargetType");
		if (StringUtils.isBlank(targetType)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TargetType]");
		}

		Element action = requestRoot.getChild("Action");
		if (null == action) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Action]");
		}

		Element targetList = requestRoot.getChild("TargetList");
		List<Element> list = targetList.getChildren();
		// 存入数据库的预案步骤
		List<SchemeStepInstance> steps = new LinkedList<SchemeStepInstance>();
		for (Element e : list) {
			SchemeStepInstance step = new SchemeStepInstance();
			step.setTargetId(e.getAttributeValue("Id"));
			step.setTargetName(e.getAttributeValue("Name"));
			step.setTargetType(emManager.class2Type(targetType));
			step.setTelephone(e.getAttributeValue("Telephone"));
			step.setLinkMan(e.getAttributeValue("LinkMan"));
			step.setNavigation(e.getAttributeValue("Navigation"));
			step.setBeginStake(e.getAttributeValue("BeginStake"));
			step.setEndStake(e.getAttributeValue("EndStake"));
			step.setManagerUnit(e.getAttributeValue("ManagerUnit"));
			step.setLocation(e.getAttributeValue("Location"));

			step.setRequestContent(action.getAttributeValue("Content"));
			if (targetType.equals(TypeDefinition.RESOURCE_TYPE_CMS)) {
				step.setContent(action.getAttributeValue("Content"));
			}
			step.setColor(action.getAttributeValue("Color"));
			step.setFont(action.getAttributeValue("Font"));
			step.setPlaySize(action.getAttributeValue("Size"));
			step.setSpace(ElementUtil.getShort(action, "Space"));
			step.setDuration(ElementUtil.getInteger(action, "Duration"));
			step.setX(ElementUtil.getShort(action, "X"));
			step.setY(ElementUtil.getShort(action, "Y"));
			steps.add(step);
		}

		emManager.saveEventAction(
				steps,
				eventId,
				user.getId(),
				StringUtils.isBlank(user.getRealName()) ? user.getName() : user
						.getRealName());

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1245");
		dto.setMethod("Save_Event_Action");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_Event_Scheme", cmd = "1246")
	@RequestMapping("/get_event_scheme.xml")
	public void getEventScheme(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String eventId = reader.getString("eventId", false);

		SchemeInstanceVO scheme = emManager.getSchemeInstance(eventId);
		if (scheme == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"Scheme for Event[" + eventId + "] not found !");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1246");
		dto.setMethod("Get_Event_Scheme");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element schemeElement = ElementUtil.createElement("Scheme", scheme,
				null, null);
		root.addContent(schemeElement);

		List<SchemeInstanceVO.Step> steps = scheme.getSteps();
		for (SchemeInstanceVO.Step step : steps) {
			Element stepElement = ElementUtil.createElement("Step", step, null,
					null);
			schemeElement.addContent(stepElement);

			List<SchemeInstanceVO.Item> items = step.getItems();
			Collections.sort(items, new SchemeInstanceVOItemComparator());
			for (SchemeInstanceVO.Item item : items) {
				Element itemElement = ElementUtil.createElement("Item", item,
						null, null);
				stepElement.addContent(itemElement);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Wfs_Get_Tollgate", cmd = "2451")
	@RequestMapping("/wfs_get_tollgate.json")
	public void wfsGetTollgate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// SimpleRequestReader reader = new SimpleRequestReader(request);
		// String name = reader.getString("name", false);

		List<WfsTollgateVO> vo = webGisManager.wfsGetTollgate();

		GetTollgateLocationDTO dto = new GetTollgateLocationDTO();
		dto.setCmd("2451");
		dto.setMethod("Wfs_Get_Tollgate");
		dto.setGisTollgate(vo);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Event", cmd = "1248")
	@RequestMapping("/list_event.xml")
	public void listEvent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = resource.get().getOrganId();

		Long beginTime = reader.getLong("beginTime", true);
		Long endTime = reader.getLong("endTime", true);
		Short type = reader.getShort("type", true);
		Short status = reader.getShort("status", true);

		Element eventsElement = null;
		// 尝试从缓存读取
		int key = (organId + beginTime + endTime + type + status).hashCode();
		int keyRes = (organId + beginTime + endTime + type + status + "list_event")
				.hashCode();
		// 上次请求时间
		Long requestTime = (Long) CacheUtil.getCache(key, "event_request_time");
		// 事件更新时间
		Long updateTime = (Long) CacheUtil.getCache(
				"event_update_time".hashCode(), "event_update_time");
		// 更新时间为空，设置为当前时间
		if (null == updateTime) {
			CacheUtil.putCache("event_update_time".hashCode(),
					Long.valueOf(System.currentTimeMillis()),
					"event_update_time");
			updateTime = Long.valueOf(System.currentTimeMillis());
		}
		// 比较上次请求时间和更新时间
		if (null != requestTime) {
			// 请求时间大于更新时间从缓存读取
			if (requestTime.longValue() > updateTime.longValue()) {
				eventsElement = (Element) CacheUtil.getCache(keyRes,
						"list_event");
			} else {
				eventsElement = emManager.listEvent(organId, beginTime,
						endTime, type, status);
			}
		} else {
			eventsElement = emManager.listEvent(organId, beginTime, endTime,
					type, status);
		}
		// 更新请求时间
		CacheUtil.putCache(key, Long.valueOf(System.currentTimeMillis()),
				"event_request_time");
		// 结果写入缓存
		CacheUtil.putCache(keyRes, eventsElement, "list_event");

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1248");
		dto.setMethod("List_Event");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		if (null != eventsElement) {
			root.addContent(eventsElement);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Resource", cmd = "1247")
	@RequestMapping("/list_resource.xml")
	public void listResourceById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		Element resourcesElement = emManager.listResourceById(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1247");
		dto.setMethod("List_Resource");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(resourcesElement);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Finish_Event_Action", cmd = "1249")
	@RequestMapping("/finish_event_action.xml")
	public void finishEventAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String eventId = reader.getString("eventId", false);

		emManager.updateEvent(eventId, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null,
				TypeDefinition.EVENT_STATUS_2);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1249");
		dto.setMethod("Finish_Event_Action");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Update_Address_Book", cmd = "2370")
	@RequestMapping("/update_address_book.json")
	public void updateAddressBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);
		String linkMan = reader.getString("linkMan", true);
		String phone = reader.getString("phone", true);
		String sex = reader.getString("sex", true);
		String address = reader.getString("address", true);

		String email = reader.getString("email", true);

		String fax = reader.getString("fax", true);
		String organId = reader.getString("organId", true);
		String position = reader.getString("position", true);

		String note = reader.getString("note", true);

		emManager.updateAddressBook(id, linkMan, phone, sex, address, note,
				email, fax, organId, position);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2370");
		dto.setMethod("Update_Address_Book");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Delete_Address_Book", cmd = "2371")
	@RequestMapping("/delete_address_book.json")
	public void deleteAddressBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		emManager.deleteAddressBook(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2371");
		dto.setMethod("Delete_Address_Book");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_Address_Book", cmd = "2372")
	@RequestMapping("/get_address_book.json")
	public void getAddressBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		GetAddressBookVO addressBook = emManager.getAddressBook(id);
		GetAddressBookDTO dto = new GetAddressBookDTO();
		dto.setAddressBook(addressBook);
		dto.setCmd("2372");
		dto.setMethod("Get_Address_Book");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Address_Book", cmd = "2373")
	@RequestMapping("/list_address_book.json")
	public void listAddressBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		String linkMan = reader.getString("linkMan", true);

		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = 0;
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 1000;
		}

		Integer totalCount = emManager.addressBookTotalCount(linkMan, organId);
		// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
		if (startIndex != 0 && totalCount.intValue() != 0) {
			if (startIndex.intValue() >= totalCount.intValue()) {
				startIndex -= ((startIndex.intValue() - totalCount.intValue())
						/ limit + 1)
						* limit;
			}
		}
		List<GetAddressBookVO> list = emManager.listAddressBook(linkMan,
				organId, startIndex, limit);
		ListAddressBookDTO dto = new ListAddressBookDTO();
		dto.setAddressBookList(list);
		dto.setCmd("2373");
		dto.setMethod("List_Address_Book");
		dto.setTotalCount(totalCount + "");

		writePage(response, dto);

	}

	@InterfaceDescription(logon = false, method = "Batch_Import_Address_Book", cmd = "2374")
	@RequestMapping("/batch_import_address_book.json")
	public void batchImportAddressBook(HttpServletRequest request,
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
			// 检测数据流正确后返回工作薄
			Workbook wb = deviceManager.checkoutIo(in);
			List<AddressBook> abs = emManager.readAddressBookWb(organId, wb);
			emManager.batchInsertAddressBook(abs, organId);
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("2374");
		dto.setMethod("Batch_Import_Address_Book");

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Unit_By_Gis_Id", cmd = "1257")
	@RequestMapping("/list_unit_by_gis_id.xml")
	public void listUnitByGisId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();
		Element units = requestRoot.getChild("Units");
		List<Element> listElement = units.getChildren();
		if (listElement.size() <= 0) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [" + units + "]");
		}
		Element unitElement = emManager.listUnitByGisId(listElement);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1257");
		dto.setMethod("Get_Unit_By_Gis_Id");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(unitElement);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Event_Camera", cmd = "1270")
	@RequestMapping("/list_event_camera.xml")
	public void listEventCamera(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String eventId = reader.getString("eventId", false);

		Event event = emManager.getEmEvent(eventId);

		// 根据规则生成搜索条件
		RoadEvent roadEvent = new RoadEvent();
		roadEvent.setName(event.getOrgan().getName());
		ruleManager.generateSearchCondition(roadEvent);

		List<Camera> cameras = emManager.listEventCamera(roadEvent
				.getCameraTypes(), roadEvent.getTollgates(), NumberUtil
				.floatStake(event.getBeginStake()), NumberUtil.floatStake(event
				.getEndStake()), roadEvent.getFrontSearchRange(), roadEvent
				.getBackSearchRange(), event.getNavigation(), event.getOrgan()
				.getId());

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1270");
		dto.setMethod("List_Event_Camera");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (Camera c : cameras) {
			Element e = new Element("Camera");
			e.setAttribute("Id", c.getId());
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Event", cmd = "3103")
	@RequestMapping("/create_event.xml")
	public void createEvent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		List<Element> events = requestRoot.getChildren("Event");
		// 返回事件ID列表
		List<String> eventIds = new LinkedList<String>();
		for (Element event : events) {
			String standardNumber = event.getAttributeValue("StandardNumber");
			if (StringUtils.isBlank(standardNumber)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"Parameter[StandardNumber] can not be empty !");
			}
			Short type = ElementUtil.getShort(event, "MainType");
			if (null == type) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"Parameter[MainType] can not be empty !");
			}
			Long subType = ElementUtil.getLong(event, "SubType");
			if (null == subType) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"Parameter[SubType] can not be empty !");
			}
			Timestamp createTime = ElementUtil.getTimestamp(event, "RecTime");
			// 構件subType
			StringBuffer sb = new StringBuffer();
			List<String> list = MyStringUtil.int32TypeList(subType.toString());
			for (String intType : list) {
				sb.append(type.toString());
				sb.append("-");
				sb.append(intType);
				sb.append("|");
			}
			String id = emManager.createEvent(standardNumber, type,
					sb.substring(0, sb.length() - 1), createTime.getTime());
			eventIds.add(id);
		}
		// 修改事件更新时间
		if (eventIds.size() > 0) {
			CacheUtil.putCache("event_update_time".hashCode(),
					Long.valueOf(System.currentTimeMillis()),
					"event_update_time");
		}

		BaseDTO dto = new BaseDTO();
		dto.setCmd("3103");
		dto.setMethod("Create_Event");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (String eventId : eventIds) {
			Element e = new Element("Event");
			e.setText(eventId);
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}
}
