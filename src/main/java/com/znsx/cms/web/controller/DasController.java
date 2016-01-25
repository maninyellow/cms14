package com.znsx.cms.web.controller;

import java.sql.Timestamp;
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
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.RoadFluxComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DasDataManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.model.CoviVO;
import com.znsx.cms.service.model.DeviceStatusVO;
import com.znsx.cms.service.model.LoLiVO;
import com.znsx.cms.service.model.OrganVDVO;
import com.znsx.cms.service.model.OrganVehicleDetectorTopVO;
import com.znsx.cms.service.model.RoadFluxStatVO;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.VehicleDetectorTotalVO;
import com.znsx.cms.service.model.VehicleDetectorVO;
import com.znsx.cms.service.model.WsVO;
import com.znsx.cms.service.model.WstVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;
import com.znsx.util.xml.RequestUtil;

/**
 * 数采服务器接口控制器
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-9 上午10:40:51
 */
@Controller
public class DasController extends BaseController {

	@Autowired
	private DasDataManager dasDataManager;
	@Autowired
	private OrganManager organManager;

	@InterfaceDescription(logon = false, method = "DAS_Report", cmd = "3102")
	@RequestMapping("/das_report.xml")
	public void dasReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = RequestUtil.parseRequest(request);
		Element reqRoot = reqDoc.getRootElement();
		List<Element> data = reqRoot.getChildren("Device");
		dasDataManager.saveData1(data);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("DAS_Report");
		dto.setCmd("3102");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Device_Status", cmd = "1213")
	@RequestMapping("/stat_device_status.xml")
	public void statDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organSN = request.getParameter("organSN");

		Integer type = null;
		String deviceType = request.getParameter("deviceType");
		if (StringUtils.isNotBlank(deviceType)) {
			try {
				type = Integer.valueOf(deviceType);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"deviceType[" + deviceType + "] invalid !");
			}
		}

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");

		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, type);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, type);
		}
		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 统计查询
		int count = dasDataManager.countDeviceStatus(beginTime, endTime, type,
				sns, organSN);

		List<DeviceStatusVO> list = dasDataManager.statDeviceStatus(beginTime,
				endTime, type, sns, organSN, start, limit);
		dasDataManager.completeStatDeviceStatus(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_Device_Status");
		dto.setCmd("1213");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (DeviceStatusVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_VD", cmd = "1214")
	@RequestMapping("/stat_vd.xml")
	public void statVD(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String organSN = request.getParameter("organSN");

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");

		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		String scope = request.getParameter("scope");
		if (!TypeDefinition.STAT_SCOPE_DAY.equals(scope)
				&& !TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "scope["
					+ scope + "] invalid !");
		}

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, TypeDefinition.DEVICE_TYPE_VD);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, TypeDefinition.DEVICE_TYPE_VD);
		}
		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 统计查询
		int count = dasDataManager.countVD(beginTime, endTime, sns, organSN,
				scope);

		List<VdVO> list = dasDataManager.statVD(beginTime, endTime, sns,
				organSN, scope, start, limit);
		dasDataManager.completeStatVD(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_VD");
		dto.setCmd("1214");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (VdVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_WST", cmd = "1219")
	@RequestMapping("/stat_wst.xml")
	public void statWST(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String organSN = request.getParameter("organSN");

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");
		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		// String scope = request.getParameter("scope");
		// if (!TypeDefinition.STAT_SCOPE_DAY.equals(scope)
		// && !TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
		// throw new BusinessException(ErrorCode.PARAMETER_INVALID, "scope["
		// + scope + "] invalid !");
		// }

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, TypeDefinition.DEVICE_TYPE_WST);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, TypeDefinition.DEVICE_TYPE_WST);
		}

		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		int count = dasDataManager.countWSTInfo(beginTime, endTime, organSN,
				sns);

		// List<WstVO> list = dasDataManager.statWST(beginTime, endTime,
		// standardNumber, organSN, scope, start, limit);
		List<WstVO> list = dasDataManager.listWSTInfo(beginTime, endTime,
				organSN, sns, start, limit);
		dasDataManager.completeStatWST(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_WST");
		dto.setCmd("1219");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (WstVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_WS", cmd = "1216")
	@RequestMapping("/stat_ws.xml")
	public void statWS(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String organSN = request.getParameter("organSN");

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");

		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		String scope = request.getParameter("scope");
		if (!TypeDefinition.STAT_SCOPE_DAY.equals(scope)
				&& !TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "scope["
					+ scope + "] invalid !");
		}

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, TypeDefinition.DEVICE_TYPE_WS);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, TypeDefinition.DEVICE_TYPE_WS);
		}

		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 统计查询
		int count = dasDataManager.countWS(beginTime, endTime, sns, organSN,
				scope);

		List<WsVO> list = dasDataManager.statWS(beginTime, endTime, sns,
				organSN, scope, start, limit);
		dasDataManager.completeStatWS(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_WS");
		dto.setCmd("1216");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (WsVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_LOLI", cmd = "1217")
	@RequestMapping("/stat_loli.xml")
	public void statLoLi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organSN = request.getParameter("organSN");

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");

		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		String scope = request.getParameter("scope");
		if (!TypeDefinition.STAT_SCOPE_DAY.equals(scope)
				&& !TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "scope["
					+ scope + "] invalid !");
		}

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, TypeDefinition.DEVICE_TYPE_LOLI);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, TypeDefinition.DEVICE_TYPE_LOLI);
		}
		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 统计查询
		int count = dasDataManager.countLoLi(beginTime, endTime, sns, organSN,
				scope);

		List<LoLiVO> list = dasDataManager.statLoLi(beginTime, endTime, sns,
				organSN, scope, start, limit);
		dasDataManager.completeStatLoLi(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_LOLI");
		dto.setCmd("1217");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (LoLiVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_COVI", cmd = "1218")
	@RequestMapping("/stat_covi.xml")
	public void statCovi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String organSN = request.getParameter("organSN");

		String deviceName = request.getParameter("deviceName");
		deviceName = StringUtils.replace(deviceName, " ", "+");

		Timestamp beginTime = null;
		String beginTimeString = request.getParameter("beginTime");
		if (StringUtils.isBlank(beginTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [beginTime]");
		} else {
			try {
				beginTime = new Timestamp(Long.parseLong(beginTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"beginTime[" + beginTimeString + "] invalid !");
			}
		}

		Timestamp endTime = null;
		String endTimeString = request.getParameter("endTime");
		if (StringUtils.isBlank(endTimeString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [endTime]");
		} else {
			try {
				endTime = new Timestamp(Long.parseLong(endTimeString));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"endTime[" + endTimeString + "] invalid !");
			}
		}

		String scope = request.getParameter("scope");
		if (!TypeDefinition.STAT_SCOPE_DAY.equals(scope)
				&& !TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "scope["
					+ scope + "] invalid !");
		}

		int start = 0;
		String startString = request.getParameter("start");
		if (StringUtils.isBlank(startString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [start]");
		} else {
			try {
				start = Integer.parseInt(startString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"start[" + startString + "] invalid !");
			}
		}

		int limit = 50;
		String limitString = request.getParameter("limit");
		if (StringUtils.isBlank(limitString)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [limit]");
		} else {
			try {
				limit = Integer.parseInt(limitString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"limit[" + limitString + "] invalid !");
			}
		}

		// 查询deviceNumber
		List<String> standardNumber = new ArrayList<String>();

		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						deviceName, TypeDefinition.DEVICE_TYPE_COVI);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					deviceName, TypeDefinition.DEVICE_TYPE_COVI);
		}
		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 统计查询
		int count = dasDataManager.countCovi(beginTime, endTime, sns, organSN,
				scope);

		List<CoviVO> list = dasDataManager.statCovi(beginTime, endTime, sns,
				organSN, scope, start, limit);
		dasDataManager.completeStatCovi(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_COVI");
		dto.setCmd("1218");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (CoviVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vehicle_Detector", cmd = "1235")
	@RequestMapping("/stat_vehicleDetector.xml")
	public void statVehicleDetector(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		Timestamp beginTime = new Timestamp(
				System.currentTimeMillis() - 1000 * 60 * 5);

		Timestamp endTime = new Timestamp(System.currentTimeMillis());

		VehicleDetectorVO vo = dasDataManager.statVehicleDetector(id,
				beginTime, endTime);
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_Vehicle_Detector");
		dto.setCmd("1235");
		dto.setMessage("");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element device = ElementUtil.createElement("Device", vo, null, null);
		root.addContent(device);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Traffic_Flow", cmd = "1236")
	@RequestMapping("/stat_traffic_flow.xml")
	public void statTrafficFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", true);

		VehicleDetectorTotalVO vo = dasDataManager.statOrganTotalVD(organId);
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_Traffic_Flow");
		dto.setCmd("1236");
		dto.setMessage("");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element device = ElementUtil.createElement("Organ", vo, null, null);
		root.addContent(device);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Traffic_Flow_Top", cmd = "1237")
	@RequestMapping("/traffic_flow_top.xml")
	public void trafficFlowTop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long beginTimeLong = reader.getLong("beginTime", false);
		Long endTimeLong = reader.getLong("endTime", true);
		Timestamp beginTime = new Timestamp(beginTimeLong);
		Timestamp endTime = null;
		if (null == endTimeLong) {
			endTime = new Timestamp(System.currentTimeMillis());
		} else {
			endTime = new Timestamp(endTimeLong);
		}
		List<OrganVehicleDetectorTopVO> list = dasDataManager.trafficFlowTop(
				beginTime, endTime);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Traffic_Flow_Top");
		dto.setCmd("1237");
		dto.setMessage("");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		for (OrganVehicleDetectorTopVO vo : list) {
			Element device = ElementUtil.createElement("Organ", vo, null, null);
			root.addContent(device);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Road_Traffic_Flow", cmd = "1235")
	@RequestMapping("/road_traffic_flow.xml")
	public void roadTrafficFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = reader.getString("organId", false);
		Timestamp beginTime = new Timestamp(
				System.currentTimeMillis() - 1000 * 60 * 5);

		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		OrganVDVO vo = dasDataManager.roadTrafficFlow(organId, beginTime,
				endTime);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Road_Traffic_Flow");
		dto.setCmd("1235");
		dto.setMessage("");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element device = ElementUtil.createElement("Organ", vo, null, null);
		root.addContent(device);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Device_Status", cmd = "1258")
	@RequestMapping("/device_status_stat.xml")
	public void deviceStatusStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organSN = reader.getString("organSN", false);
		Long time = reader.getLong("time", true);
		String name = reader.getString("name", true);
		Integer type = reader.getInteger("type", true);
		Integer startIndex = reader.getInteger("startIndex", false);
		Integer limit = reader.getInteger("limit", false);
		List<String> standardNumber = new ArrayList<String>();
		// 如果organSN不为本平台SN,deviceName参数无意义,略过此参数
		if (StringUtils.isNotBlank(organSN)) {
			Organ root = organManager.getRootOrgan();
			if (organSN.equals(root.getStandardNumber())) {
				standardNumber = dasDataManager.getStandardNumberByNameAndType(
						name, type);
			}
		} else {
			standardNumber = dasDataManager.getStandardNumberByNameAndType(
					name, type);
		}
		String sns[] = new String[standardNumber.size()];
		for (int i = 0; i < standardNumber.size(); i++) {
			sns[i] = standardNumber.get(i);
		}
		// 如果没有出入时间，则默认为当前时间
		if (time == null) {
			time = System.currentTimeMillis();
		}
		int count = dasDataManager.countTotalDeviceStatus(sns, time, type);

		List<DeviceStatusVO> list = dasDataManager.deviceStatusStat(time, type,
				sns, organSN, startIndex, limit);
		dasDataManager.completeStatDeviceStatus(list);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Device_Status_Stat");
		dto.setCmd("1258");
		dto.setMessage(count + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (DeviceStatusVO vo : list) {
			Element device = ElementUtil
					.createElement("Device", vo, null, null);
			root.addContent(device);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vd_Count", cmd = "1268")
	@RequestMapping("/stat_vd_count.xml")
	public void vdStatByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organId = resource.get().getOrganId();
		Timestamp beginTime = new Timestamp(reader.getLong("beginTime", false));
		Timestamp endTime = new Timestamp(reader.getLong("endTime", false));
		Integer startIndex = reader.getInteger("startIndex", false);
		Integer limit = reader.getInteger("limit", false);
		String name = reader.getString("deviceName", true);
		if (StringUtils.isNotBlank(name)) {
			name = StringUtils.replace(name, " ", "+");
		}
		String flag = reader.getString("flag", false);
		int totalCount = dasDataManager.vdStatByDayTotal(organId, beginTime,
				endTime, name, flag);
		Element listDevice = dasDataManager.vdStat(organId, beginTime, endTime,
				startIndex, limit, name, flag);
		Document doc = new Document();
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_Vd_Count");
		dto.setCmd("1268");
		dto.setMessage(totalCount + "");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(listDevice);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vd_By_Time", cmd = "1271")
	@RequestMapping("/stat_vd_by_time.xml")
	public void statVdByTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Timestamp beginTime = new Timestamp(reader.getLong("beginTime", false));
		Timestamp endTime = new Timestamp(reader.getLong("endTime", false));
		String idString = reader.getString("ids", false);
		String[] ids = idString.split(",");
		String scope = reader.getString("scope", true);
		if (StringUtils.isBlank(scope)) {
			scope = TypeDefinition.STAT_SCOPE_HOUR;
		}

		List<VdStatByDayVO> list = dasDataManager.statVdByTime(ids, beginTime,
				endTime, scope);
		List<VdStatByDayVO> vds = dasDataManager.sortVD(list);

		Document doc = new Document();
		BaseDTO dto = new BaseDTO();
		dto.setMethod("Stat_Vd_By_Time");
		dto.setCmd("1271");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.addContent(root);

		for (int n = 0; n < vds.size(); n++) {
			Element de = new Element("Device");
			de.setAttribute("Id",
					MyStringUtil.object2StringNotNull(vds.get(n).getId()));
			de.setAttribute("Name",
					MyStringUtil.object2StringNotNull(vds.get(n).getName()));
			de.setAttribute("UpOccs",
					MyStringUtil.cutObject(vds.get(n).getUpOccs(), 2));
			de.setAttribute("DwOccs",
					MyStringUtil.cutObject(vds.get(n).getDwOccs(), 2));
			de.setAttribute("UpOccm",
					MyStringUtil.cutObject(vds.get(n).getUpOccm(), 2));
			de.setAttribute("DwOccm",
					MyStringUtil.cutObject(vds.get(n).getDwOccm(), 2));
			de.setAttribute("UpOccb",
					MyStringUtil.cutObject(vds.get(n).getUpOccb(), 2));
			de.setAttribute("DwOccb",
					MyStringUtil.cutObject(vds.get(n).getDwOccb(), 2));
			de.setAttribute("UpOcc",
					MyStringUtil.cutObject(vds.get(n).getUpOcc(), 2));
			de.setAttribute("DwOcc",
					MyStringUtil.cutObject(vds.get(n).getDwOcc(), 2));
			de.setAttribute("UpSpeeds",
					MyStringUtil.cutObject(vds.get(n).getUpSpeeds(), 2));
			de.setAttribute("DwSpeeds",
					MyStringUtil.cutObject(vds.get(n).getDwSpeeds(), 2));
			de.setAttribute("UpSpeedm",
					MyStringUtil.cutObject(vds.get(n).getUpSpeedm(), 2));
			de.setAttribute("DwSpeedm",
					MyStringUtil.cutObject(vds.get(n).getDwSpeedm(), 2));
			de.setAttribute("UpSpeedb",
					MyStringUtil.cutObject(vds.get(n).getUpSpeedb(), 2));
			de.setAttribute("DwSpeedb",
					MyStringUtil.cutObject(vds.get(n).getDwSpeedb(), 2));
			de.setAttribute("UpSpeed",
					MyStringUtil.cutObject(vds.get(n).getUpSpeed(), 2));
			de.setAttribute("DwSpeed",
					MyStringUtil.cutObject(vds.get(n).getDwSpeed(), 2));
			de.setAttribute("UpFlows",
					MyStringUtil.object2StringNotNull(vds.get(n).getUpFlows()));
			de.setAttribute("DwFlows",
					MyStringUtil.object2StringNotNull(vds.get(n).getDwFlows()));
			de.setAttribute("UpFlowm",
					MyStringUtil.object2StringNotNull(vds.get(n).getUpFlowm()));
			de.setAttribute("DwFlowm",
					MyStringUtil.object2StringNotNull(vds.get(n).getDwFlowm()));
			de.setAttribute("UpFlowb",
					MyStringUtil.object2StringNotNull(vds.get(n).getUpFlowb()));
			de.setAttribute("DwFlowb",
					MyStringUtil.cutObject(vds.get(n).getDwFlowb(), 2));
			de.setAttribute("UpFlow",
					MyStringUtil.cutObject(vds.get(n).getUpFlow(), 2));
			de.setAttribute("DwFlow",
					MyStringUtil.cutObject(vds.get(n).getDwFlow(), 2));
			de.setAttribute("UpFlowTotal", MyStringUtil
					.object2StringNotNull(vds.get(n).getUpFlowTotal()));
			de.setAttribute("DwFlowTotal", MyStringUtil
					.object2StringNotNull(vds.get(n).getDwFlowTotal()));
			de.setAttribute("UpHeadway",
					MyStringUtil.cutObject(vds.get(n).getUpHeadway(), 2));
			de.setAttribute("DwHeadway",
					MyStringUtil.cutObject(vds.get(n).getDwHeadway(), 2));
			de.setAttribute("RecTime",
					MyStringUtil.object2StringNotNull(vds.get(n).getRecTime()));
			de.setAttribute("OrganSN",
					MyStringUtil.object2StringNotNull(vds.get(n).getOrganSN()));
			de.setAttribute("OrganName", MyStringUtil.object2StringNotNull(vds
					.get(n).getOrganName()));
			de.setAttribute("OrganId",
					MyStringUtil.object2StringNotNull(vds.get(n).getOrganId()));
			root.addContent(de);
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Road_Flux", cmd = "1275")
	@RequestMapping("/list_road_flux.xml")
	public void listRoadFlux(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Collection<RoadFluxStatVO> vos = dasDataManager.listRoadFlux();
		List<RoadFluxStatVO> list = new LinkedList<RoadFluxStatVO>(vos);
		Collections.sort(list, new RoadFluxComparator());

		Document doc = new Document();
		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Road_Flux");
		dto.setCmd("1275");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (RoadFluxStatVO vo : list) {
			root.addContent(ElementUtil.createElement("Road", vo));
		}
		writePageWithContentLength(response, doc);
	}
}
