package com.znsx.cms.web.controller;

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
import com.znsx.cms.service.iface.StatManager;
import com.znsx.cms.service.model.RoadDeviceStatusVO;
import com.znsx.cms.service.model.StatCameraStatusVO;
import com.znsx.cms.service.model.StatCoviVO;
import com.znsx.cms.service.model.StatLoliVO;
import com.znsx.cms.service.model.StatNoVO;
import com.znsx.cms.service.model.StatRsdVO;
import com.znsx.cms.service.model.StatVdVO;
import com.znsx.cms.service.model.StatWstVO;
import com.znsx.cms.service.model.TunnelDeviceStatusVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.ListCameraStatusDTO;
import com.znsx.cms.web.dto.omc.ListRoadDeviceStatusDTO;
import com.znsx.cms.web.dto.omc.ListTunnelDeviceStatusDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;

/**
 * 统计类接口控制器
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 上午10:56:25
 */
@Controller
public class StatController extends BaseController {
	@Autowired
	private StatManager statManager;

	@InterfaceDescription(logon = true, method = "List_Road_Device_Status", cmd = "2474")
	@RequestMapping("/list_road_device_status.json")
	public void listRoadDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organSn = reader.getString("organSn", true);
		String name = reader.getString("name", true);
		if (StringUtils.isNotBlank(name)) {
			name = StringUtils.replace(name, " ", "+");
		}
		Integer type = reader.getInteger("type", true);
		Integer status = reader.getInteger("status", true);
		Integer commStatus = reader.getInteger("commStatus", true);

		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = Integer.valueOf(0);
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}
		if (limit.intValue() <= 0) {
			limit = Integer.valueOf(50);
		}

		// 获取总的设备状态记录
		List<RoadDeviceStatusVO> totalList = statManager.listRoadDeviceStatus(
				organSn, name, type, status, commStatus);
		int count = totalList.size();

		// 返回结果
		ListRoadDeviceStatusDTO dto = new ListRoadDeviceStatusDTO();
		dto.setCmd("2474");
		dto.setMethod("List_Road_Device_Status");
		dto.setTotalCount(count + "");

		// 内存处理分页
		int from = startIndex.intValue();
		if (from < 0) {
			from = 0;
		}
		int to = from + limit.intValue();
		if (to > count) {
			to = count;
		}
		List<RoadDeviceStatusVO> devideList = totalList.subList(from, to);
		dto.setItems(devideList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Tunnel_Device_Status", cmd = "2475")
	@RequestMapping("/list_tunnel_device_status.json")
	public void listTunnelDeviceStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organSn = reader.getString("organSn", true);
		String name = reader.getString("name", true);
		if (StringUtils.isNotBlank(name)) {
			name = StringUtils.replace(name, " ", "+");
		}
		Integer type = reader.getInteger("type", true);
		Integer status = reader.getInteger("status", true);
		Integer commStatus = reader.getInteger("commStatus", true);

		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = Integer.valueOf(0);
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}
		if (limit.intValue() <= 0) {
			limit = Integer.valueOf(50);
		}

		// 获取总的设备状态记录
		List<TunnelDeviceStatusVO> totalList = statManager
				.listTunnelDeviceStatus(organSn, name, type, status, commStatus);
		int count = totalList.size();

		// 返回结果
		ListTunnelDeviceStatusDTO dto = new ListTunnelDeviceStatusDTO();
		dto.setCmd("2475");
		dto.setMethod("List_Tunnel_Device_Status");
		dto.setTotalCount(count + "");

		// 内存处理分页
		int from = startIndex.intValue();
		if (from < 0) {
			from = 0;
		}
		int to = from + limit.intValue();
		if (to > count) {
			to = count;
		}
		List<TunnelDeviceStatusVO> devideList = totalList.subList(from, to);
		dto.setItems(devideList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "List_Camera_Status", cmd = "2476")
	@RequestMapping("/list_camera_status.json")
	public void listCameraStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String organSn = reader.getString("organSn", true);
		String name = reader.getString("name", true);
		if (StringUtils.isNotBlank(name)) {
			name = StringUtils.replace(name, " ", "+");
		}
		Integer commStatus = reader.getInteger("commStatus", true);

		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = Integer.valueOf(0);
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}
		if (limit.intValue() <= 0) {
			limit = Integer.valueOf(50);
		}

		// 获取总的设备状态记录
		List<StatCameraStatusVO> totalList = statManager.listCameraStatus(
				organSn, name, commStatus);
		int count = totalList.size();

		// 返回结果
		ListCameraStatusDTO dto = new ListCameraStatusDTO();
		dto.setCmd("2476");
		dto.setMethod("List_Camera_Status");
		dto.setTotalCount(count + "");

		// 内存处理分页
		int from = startIndex.intValue();
		if (from < 0) {
			from = 0;
		}
		int to = from + limit.intValue();
		if (to > count) {
			to = count;
		}
		List<StatCameraStatusVO> devideList = totalList.subList(from, to);
		dto.setItems(devideList);

		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vd_By_Hour", cmd = "1281")
	@RequestMapping("/statVdByHour.xml")
	public void statVdByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countVdByHour(sn, beginTime, endTime);

		List<StatVdVO> list = statManager.statVdByHour(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1281");
		dto.setMethod("Stat_Vd_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatVdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vd_By_Day", cmd = "1282")
	@RequestMapping("/statVdByDay.xml")
	public void statVdByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countVdByDay(sn, beginTime, endTime);

		List<StatVdVO> list = statManager.statVdByDay(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1282");
		dto.setMethod("Stat_Vd_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatVdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Vd_By_Month", cmd = "1283")
	@RequestMapping("/statVdByMonth.xml")
	public void statVdByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countVdByMonth(sn, beginTime, endTime);

		List<StatVdVO> list = statManager.statVdByMonth(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1283");
		dto.setMethod("Stat_Vd_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatVdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Wst_By_Hour", cmd = "1284")
	@RequestMapping("/statWstByHour.xml")
	public void statWstByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countWstByHour(sn, beginTime, endTime);

		List<StatWstVO> list = statManager.statWstByHour(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1284");
		dto.setMethod("Stat_Wst_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatWstVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Wst_By_Day", cmd = "1285")
	@RequestMapping("/statWstByDay.xml")
	public void statWstByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countWstByDay(sn, beginTime, endTime);

		List<StatWstVO> list = statManager.statWstByDay(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1285");
		dto.setMethod("Stat_Wst_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatWstVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Wst_By_Month", cmd = "1286")
	@RequestMapping("/statWstByMonth.xml")
	public void statWstByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countWstByMonth(sn, beginTime, endTime);

		List<StatWstVO> list = statManager.statWstByMonth(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1286");
		dto.setMethod("Stat_Wst_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatWstVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Rsd_By_Hour", cmd = "1287")
	@RequestMapping("/statRsdByHour.xml")
	public void statRsdByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countRsdByHour(sn, beginTime, endTime);

		List<StatRsdVO> list = statManager.statRsdByHour(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1287");
		dto.setMethod("Stat_Rsd_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatRsdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Rsd_By_Day", cmd = "1288")
	@RequestMapping("/statRsdByDay.xml")
	public void statRsdByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countRsdByDay(sn, beginTime, endTime);

		List<StatRsdVO> list = statManager.statRsdByDay(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1288");
		dto.setMethod("Stat_Rsd_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatRsdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Rsd_By_Month", cmd = "1289")
	@RequestMapping("/statRsdByMonth.xml")
	public void statRsdByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countRsdByMonth(sn, beginTime, endTime);

		List<StatRsdVO> list = statManager.statRsdByMonth(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1289");
		dto.setMethod("Stat_Rsd_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatRsdVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Covi_By_Hour", cmd = "1290")
	@RequestMapping("/statCoviByHour.xml")
	public void statCoviByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countCoviByHour(sn, beginTime, endTime);

		List<StatCoviVO> list = statManager.statCoviByHour(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1290");
		dto.setMethod("Stat_Covi_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatCoviVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Covi_By_Day", cmd = "1291")
	@RequestMapping("/statCoviByDay.xml")
	public void statCoviByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countCoviByDay(sn, beginTime, endTime);

		List<StatCoviVO> list = statManager.statCoviByDay(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1291");
		dto.setMethod("Stat_Covi_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatCoviVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Covi_By_Month", cmd = "1292")
	@RequestMapping("/statCoviByMonth.xml")
	public void statCoviByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countCoviByMonth(sn, beginTime, endTime);

		List<StatCoviVO> list = statManager.statCoviByMonth(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1292");
		dto.setMethod("Stat_Covi_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatCoviVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Loli_By_Hour", cmd = "1293")
	@RequestMapping("/statLoliByHour.xml")
	public void statLoliByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countLoliByHour(sn, beginTime, endTime);

		List<StatLoliVO> list = statManager.statLoliByHour(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1293");
		dto.setMethod("Stat_Loli_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatLoliVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Loli_By_Day", cmd = "1294")
	@RequestMapping("/statLoliByDay.xml")
	public void statLoliByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countLoliByDay(sn, beginTime, endTime);

		List<StatLoliVO> list = statManager.statLoliByDay(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1294");
		dto.setMethod("Stat_Loli_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatLoliVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_Loli_By_Month", cmd = "1295")
	@RequestMapping("/statLoliByMonth.xml")
	public void statLoliByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countLoliByMonth(sn, beginTime, endTime);

		List<StatLoliVO> list = statManager.statLoliByMonth(sn, beginTime,
				endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1295");
		dto.setMethod("Stat_Loli_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatLoliVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_No_By_Hour", cmd = "1296")
	@RequestMapping("/statNoByHour.xml")
	public void statNoByHour(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countNoByHour(sn, beginTime, endTime);

		List<StatNoVO> list = statManager.statNoByHour(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1296");
		dto.setMethod("Stat_No_By_Hour");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatNoVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_No_By_Day", cmd = "1297")
	@RequestMapping("/statNoByDay.xml")
	public void statNoByDay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countNoByDay(sn, beginTime, endTime);

		List<StatNoVO> list = statManager.statNoByDay(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1297");
		dto.setMethod("Stat_No_By_Day");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatNoVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Stat_No_By_Month", cmd = "1298")
	@RequestMapping("/statNoByMonth.xml")
	public void statNoByMonth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String sn = reader.getString("standardNumber", true);
		Long beginTime = reader.getLong("beginTime", true);
		if (null == beginTime) {
			beginTime = 1l;
		}
		Long endTime = reader.getLong("endTime", true);
		if (null == endTime) {
			endTime = System.currentTimeMillis();
		}
		Integer start = reader.getInteger("start", true);
		if (null == start) {
			start = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = statManager.countNoByMonth(sn, beginTime, endTime);

		List<StatNoVO> list = statManager.statNoByMonth(sn, beginTime, endTime,
				start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1298");
		dto.setMethod("Stat_No_By_Month");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.setAttribute("TotalCount", count + "");
		doc.setRootElement(root);

		for (StatNoVO vo : list) {
			Element item = ElementUtil.createElement("Item", vo, null, null);
			root.addContent(item);
		}

		writePageWithContentLength(response, doc);
	}
}
