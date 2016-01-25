package com.znsx.cms.web.controller;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.SoundRecordLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.WorkRecordManager;
import com.znsx.cms.service.model.ClassesVO;
import com.znsx.cms.service.model.ListCenterWorkRecordVO;
import com.znsx.cms.service.model.ListClassesVO;
import com.znsx.cms.service.model.ListVisitRecordVO;
import com.znsx.cms.service.model.SoundRecordLogVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;

/**
 * 监控人员工作记录接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-28 上午10:23:35
 */
@Controller
public class WorkRecordController extends BaseController {
	@Autowired
	private WorkRecordManager workRecordManager;

	@InterfaceDescription(logon = true, method = "Save_Visit_Record", cmd = "1259")
	@RequestMapping("/save_visit_record.xml")
	public void saveVisitRecord(HttpServletRequest request,
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

		Long arriveTime = ElementUtil.getLong(record, "ArriveTime");
		if (null == arriveTime) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [ArriveTime]");
		}

		Long leaveTime = ElementUtil.getLong(record, "LeaveTime");

		String visitorName = record.getAttributeValue("VisitorName");
		if (StringUtils.isBlank(visitorName)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [VisitorName]");
		}

		String phone = record.getAttributeValue("Phone");

		String reason = record.getAttributeValue("Reason");

		String result = record.getAttributeValue("Result");

		String note = record.getAttributeValue("Note");

		com.znsx.cms.service.model.ResourceVO user = resource.get();
		// 如果真实名称未找到，使用登录用户名做为监控员名称
		String name = StringUtils.isBlank(user.getRealName()) ? user.getName()
				: user.getRealName();

		String entityId = workRecordManager.saveVisitRecord(id, user.getId(),
				name, arriveTime, leaveTime, visitorName, phone, reason,
				result, note);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1259");
		dto.setMethod("Save_Visit_Record");
		dto.setMessage(entityId);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Center_Work_Record", cmd = "1260")
	@RequestMapping("/save_center_work_record.xml")
	public void saveCenterWorkRecord(HttpServletRequest request,
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

		Long recordTime = ElementUtil.getLong(record, "RecordTime");

		String monitorUsers = record.getAttributeValue("MonitorUsers");

		String maintainUsers = record.getAttributeValue("MaintainUsers");

		String note = record.getAttributeValue("Note");

		com.znsx.cms.service.model.ResourceVO user = resource.get();
		// 如果真实名称未找到，使用登录用户名做为监控员名称
		String name = StringUtils.isBlank(user.getRealName()) ? user.getName()
				: user.getRealName();

		String entityId = workRecordManager.saveCenterWorkRecord(id,
				user.getId(), name, recordTime, monitorUsers, maintainUsers,
				note);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1260");
		dto.setMethod("Save_Center_Work_Record");
		dto.setMessage(entityId);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Visit_Record", cmd = "1261")
	@RequestMapping("/list_visit_record.xml")
	public void listVisitRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String userName = reader.getString("userName", true);

		Long begin = reader.getLong("begin", false);

		Long end = reader.getLong("end", false);

		int start = 0;
		Integer startInteger = reader.getInteger("start", true);
		if (null != startInteger) {
			start = startInteger.intValue();
		}

		int limit = 50;
		Integer limitInteger = reader.getInteger("limit", true);
		if (null != limitInteger) {
			limit = limitInteger.intValue();
		}

		int count = workRecordManager.countVisitRecord(userName, begin, end);

		List<ListVisitRecordVO> list = workRecordManager.listVisitRecord(
				userName, begin, end, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1261");
		dto.setMethod("List_Visit_Record");
		dto.setMessage(count + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (ListVisitRecordVO vo : list) {
			root.addContent(ElementUtil.createElement("Record", vo));
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Center_Work_Record", cmd = "1262")
	@RequestMapping("/list_center_work_record.xml")
	public void listCenterWorkRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String userName = reader.getString("userName", true);

		Long begin = reader.getLong("begin", false);

		Long end = reader.getLong("end", false);

		int start = 0;
		Integer startInteger = reader.getInteger("start", true);
		if (null != startInteger) {
			start = startInteger.intValue();
		}

		int limit = 50;
		Integer limitInteger = reader.getInteger("limit", true);
		if (null != limitInteger) {
			limit = limitInteger.intValue();
		}

		int count = workRecordManager.countCenterWorkRecord(userName, begin,
				end);

		List<ListCenterWorkRecordVO> list = workRecordManager
				.listCenterWorkRecord(userName, begin, end, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1262");
		dto.setMethod("List_Center_Work_Record");
		dto.setMessage(count + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (ListCenterWorkRecordVO vo : list) {
			root.addContent(ElementUtil.createElement("Record", vo));
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Upload_Work_Plan", cmd = "1269")
	@RequestMapping("/upload_work_plan.xml")
	public void uploadWorkPlan(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 检查是否文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 工作计划类型：0-交警, 1-路政
			String type = null;
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// Excel数据流
			InputStream in = null;
			// 解析请求
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String fieldName = item.getFieldName();

				// 简单文本参数部分，得到type
				if ("type".equals(fieldName)) {
					type = item.getString();
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
			if (StringUtils.isBlank(type)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [type]");
			}

			try {
				Workbook wb = WorkbookFactory.create(in);
				workRecordManager.saveWorkPlan(wb, type);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Could not parse upload file, ensure it is a Excel format file !");
			}
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1269");
		dto.setMethod("Upload_Work_Plan");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Sound_Record", cmd = "1312")
	@RequestMapping("/save_sound_record.xml")
	public void saveSoundRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document requestDoc = (Document) request
				.getAttribute("RequestDocument");
		Element requestRoot = requestDoc.getRootElement();

		Element record = requestRoot.getChild("Record");

		Integer duration = ElementUtil.getInteger(record, "Duration");
		if (null == duration) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Duration]");
		}
		String callFlag = record.getAttributeValue("CallFlag");
		Long createTime = ElementUtil.getLong(record, "CreateTime");
		// 默认为系统当前时间
		if (null == createTime) {
			createTime = System.currentTimeMillis();
		}
		String status = record.getAttributeValue("Status");
		String httpAddress = record.getAttributeValue("HttpAddress");
		String callNumber = record.getAttributeValue("CallNumber");
		String hostNumber = record.getAttributeValue("HostNumber");
		String note = record.getAttributeValue("Note");

		String userId = resource.get().getId();
		workRecordManager.saveSoundRecord(duration, callFlag, createTime,
				status, httpAddress, callNumber, hostNumber, note, userId);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1312");
		dto.setMethod("Save_Sound_Record");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_Sound_Record", cmd = "1313")
	@RequestMapping("/get_sound_record.xml")
	public void getSoundRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		String id = reader.getString("id", false);

		SoundRecordLogVO vo = workRecordManager.getSoundRecord(id);
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1313");
		dto.setMethod("Get_Sound_Record");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		Element record = ElementUtil.createElement("Record", vo, null, null);
		root.addContent(record);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Sound_Record", cmd = "1319")
	@RequestMapping("/list_sound_record.xml")
	public void listSoundRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);
		Long beginTime = reader.getLong("beginTime", false);
		Long endTime = reader.getLong("endTime", false);
		Integer minDuration = reader.getInteger("minDuration", true);
		Integer maxDuration = reader.getInteger("maxDuration", true);
		String name = reader.getString("name", true);
		String callNumber = reader.getString("callNumber", true);
		String hostNumber = reader.getString("hostNumber", true);
		String callFlag = reader.getString("callFlag", true);
		String type = reader.getString("type", false);
		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = 0;
		}
		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = 50;
		}

		int count = workRecordManager.countSoundRecordLog(beginTime, endTime,
				minDuration, maxDuration, name, callNumber, hostNumber,
				callFlag);

		Element list = workRecordManager.listSoundRecord(beginTime, endTime,
				minDuration, maxDuration, name, callNumber, hostNumber,
				callFlag, type, startIndex, limit);
		BaseDTO dto = new BaseDTO();
		Document doc = new Document();
		dto.setMethod("List_Sound_Record");
		dto.setCmd("1319");
		dto.setMessage(count + "");
		Element root = ElementUtil.createElement("Response", dto, null, null);
		root.addContent(list);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Classes", cmd = "1314")
	@RequestMapping("/save_classes.xml")
	public void saveClassesRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", true);

		String userName = reader.getString("userName", true);
		userName = StringUtils.replace(userName, " ", "+");

		String maintainer = reader.getString("maintainer", true);

		String captain = reader.getString("captain", true);

		Long beginTime = reader.getLong("beginTime", true);

		Long endTime = reader.getLong("endTime", true);

		String remark = reader.getString("remark", true);

		String entityId = workRecordManager.saveClasses(id, userName,
				maintainer, captain, beginTime, endTime, remark);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1314");
		dto.setMethod("Save_Classes");
		dto.setMessage(entityId);
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Classes", cmd = "1315")
	@RequestMapping("/list_classes.xml")
	public void listClasses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String userName = reader.getString("userName", true);

		Long beginTime = reader.getLong("beginTime", true);

		Long endTime = reader.getLong("endTime", true);

		int start = 0;
		Integer startInteger = reader.getInteger("start", true);
		if (null != startInteger) {
			start = startInteger.intValue();
		}

		int limit = 50;
		Integer limitInteger = reader.getInteger("limit", true);
		if (null != limitInteger) {
			limit = limitInteger.intValue();
		}

		int count = workRecordManager
				.countClasses(userName, beginTime, endTime);

		List<ListClassesVO> list = workRecordManager.listClasses(userName,
				beginTime, endTime, start, limit);

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1315");
		dto.setMethod("List_Classes");
		dto.setMessage(count + "");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto);
		doc.setRootElement(root);

		for (ListClassesVO vo : list) {
			root.addContent(ElementUtil.createElement("Record", vo));
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_classes", cmd = "1316")
	@RequestMapping("/delete_classes.xml")
	public void deleteClasses(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseDTO dto = new BaseDTO();
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [id]");
		}
		workRecordManager.deleteClasses(id);
		dto.setMethod("Delete_Classes");
		dto.setCmd("1316");
		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Get_Classes", cmd = "1317")
	@RequestMapping("/get_classes.xml")
	public void getUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		ClassesVO user = workRecordManager.getClasses(id);

		BaseDTO dto = new BaseDTO();
		dto.setCmd("1317");
		dto.setMethod("Get_Classes");
		Document doc = new Document();
		Element root = ElementUtil.createElement("response", dto, null, null);
		doc.setRootElement(root);
		if (null != user) {
			root.addContent(ElementUtil.createElement("record", user));
		}

		writePageWithContentLength(response, doc);
	}
}
