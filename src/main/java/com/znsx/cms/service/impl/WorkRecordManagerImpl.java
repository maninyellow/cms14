package com.znsx.cms.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CenterWorkRecordDAO;
import com.znsx.cms.persistent.dao.ClassesDAO;
import com.znsx.cms.persistent.dao.SoundRecordLogDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserOperationLogDAO;
import com.znsx.cms.persistent.dao.VisitRecordDAO;
import com.znsx.cms.persistent.dao.WorkPlanDAO;
import com.znsx.cms.persistent.model.CenterWorkRecord;
import com.znsx.cms.persistent.model.Classes;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.SoundRecordLog;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserOperationLog;
import com.znsx.cms.persistent.model.VisitRecord;
import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.WorkRecordManager;
import com.znsx.cms.service.model.ClassesVO;
import com.znsx.cms.service.model.ListCenterWorkRecordVO;
import com.znsx.cms.service.model.ListClassesVO;
import com.znsx.cms.service.model.ListVisitRecordVO;
import com.znsx.cms.service.model.SoundRecordLogVO;
import com.znsx.util.cache.CacheData;

/**
 * 操作人员值班记录业务接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午10:12:27
 */
public class WorkRecordManagerImpl extends BaseManagerImpl implements
		WorkRecordManager {
	@Autowired
	private VisitRecordDAO visitRecordDAO;
	@Autowired
	private CenterWorkRecordDAO centerWorkRecordDAO;
	@Autowired
	private WorkPlanDAO workPlanDAO;
	@Autowired
	private SoundRecordLogDAO soundRecordLogDAO;
	@Autowired
	private ClassesDAO classesDAO;
	@Autowired
	private UserOperationLogDAO userOperationLogDAO;
	@Autowired
	private UserDAO userDAO;

	@Override
	public String saveVisitRecord(@LogParam("id") String id, String userId,
			String userName, Long arriveTime, Long leaveTime,
			@LogParam("name") String visitorName, String phone, String reason,
			String result, String note) throws BusinessException {
		VisitRecord entity = null;
		// 记录时间、记录人员不允许修改
		if (StringUtils.isBlank(id)) {
			entity = new VisitRecord();
			entity.setRecordTime(new Timestamp(System.currentTimeMillis()));
			entity.setUserId(userId);
			entity.setUserName(userName);
		} else {
			entity = visitRecordDAO.findById(id);
		}
		if (null != arriveTime) {
			entity.setArriveTime(new Timestamp(arriveTime.longValue()));
		}
		if (StringUtils.isNotBlank(note)) {
			entity.setImportantFlag(Short.valueOf((short) 1));
		} else {
			entity.setImportantFlag(Short.valueOf((short) 0));
		}
		if (null != leaveTime) {
			entity.setLeaveTime(new Timestamp(leaveTime.longValue()));
		}
		entity.setNote(note);
		entity.setPhone(phone);
		entity.setReason(reason);
		entity.setVisitorName(visitorName);
		entity.setWorkResult(result);
		entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		visitRecordDAO.save(entity);
		return entity.getId();
	}

	@Override
	public String saveCenterWorkRecord(@LogParam("id") String id,
			String userId, String userName, Long recordTime,
			@LogParam("name") String monitorUsers, String maintainUsers,
			String note) throws BusinessException {
		CenterWorkRecord entity = null;
		// 记录时间、记录人员不允许修改
		if (StringUtils.isBlank(id)) {
			entity = new CenterWorkRecord();
			if (null != recordTime) {
				entity.setRecordTime(new Timestamp(recordTime.longValue()));
			} else {
				entity.setRecordTime(new Timestamp(System.currentTimeMillis()));
			}
			entity.setUserId(userId);
			entity.setUserName(userName);
		} else {
			entity = centerWorkRecordDAO.findById(id);
		}
		entity.setMaintainUsers(maintainUsers);
		entity.setMonitorUsers(monitorUsers);
		entity.setNote(note);
		entity.setUpdateTime(new Timestamp(recordTime));
		centerWorkRecordDAO.save(entity);
		return entity.getId();
	}

	@Override
	public List<ListVisitRecordVO> listVisitRecord(String userName, Long begin,
			Long end, int start, int limit) {
		Timestamp beginTime = new Timestamp(begin.longValue());
		Timestamp endTime = end == null ? new Timestamp(
				System.currentTimeMillis()) : new Timestamp(end.longValue());
		List<VisitRecord> records = visitRecordDAO.listVisitRecord(userName,
				beginTime, endTime, start, limit);
		List<ListVisitRecordVO> list = new LinkedList<ListVisitRecordVO>();
		for (VisitRecord record : records) {
			ListVisitRecordVO vo = new ListVisitRecordVO();
			vo.setArriveTime(record.getArriveTime().getTime() + "");
			vo.setId(record.getId());
			vo.setLeaveTime(record.getLeaveTime() == null ? "" : record
					.getLeaveTime().getTime() + "");
			vo.setNote(record.getNote());
			vo.setPhone(record.getPhone());
			vo.setReason(record.getReason());
			vo.setRecordTime(record.getRecordTime().getTime() + "");
			vo.setResult(record.getWorkResult());
			vo.setUserId(record.getUserId());
			vo.setUserName(record.getUserName());
			vo.setVisitorName(record.getVisitorName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countVisitRecord(String userName, Long begin, Long end) {
		Timestamp beginTime = new Timestamp(begin.longValue());
		Timestamp endTime = end == null ? new Timestamp(
				System.currentTimeMillis()) : new Timestamp(end.longValue());
		return visitRecordDAO.countVisitRecord(userName, beginTime, endTime);
	}

	@Override
	public List<ListCenterWorkRecordVO> listCenterWorkRecord(String userName,
			Long begin, Long end, int start, int limit) {
		Timestamp beginTime = new Timestamp(begin.longValue());
		Timestamp endTime = end == null ? new Timestamp(
				System.currentTimeMillis()) : new Timestamp(end.longValue());
		List<CenterWorkRecord> records = centerWorkRecordDAO.listVisitRecord(
				userName, beginTime, endTime, start, limit);
		List<ListCenterWorkRecordVO> list = new LinkedList<ListCenterWorkRecordVO>();
		for (CenterWorkRecord record : records) {
			ListCenterWorkRecordVO vo = new ListCenterWorkRecordVO();
			vo.setId(record.getId());
			vo.setMaintainUsers(record.getMaintainUsers());
			vo.setMonitorUsers(record.getMonitorUsers());
			vo.setNote(record.getNote());
			vo.setRecordTime(record.getRecordTime().getTime() + "");
			vo.setUserId(record.getUserId());
			vo.setUserName(record.getUserName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countCenterWorkRecord(String userName, Long begin, Long end) {
		Timestamp beginTime = new Timestamp(begin.longValue());
		Timestamp endTime = end == null ? new Timestamp(
				System.currentTimeMillis()) : new Timestamp(end.longValue());
		return centerWorkRecordDAO.countVisitRecord(userName, beginTime,
				endTime);
	}

	@Override
	public void saveWorkPlan(Workbook wb, String type) {
		Sheet sheet = wb.getSheetAt(0);
		int count = sheet.getPhysicalNumberOfRows();
		List<WorkPlan> list = new LinkedList<WorkPlan>();
		for (int i = 1; i < count; i++) {
			WorkPlan workPlan = new WorkPlan();
			Row row = sheet.getRow(i);
			Cell cell0 = row.getCell(0);
			Cell cell1 = row.getCell(1);
			Cell cell2 = row.getCell(2);
			// 排班日期
			if (cell0.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				Date date = cell0.getDateCellValue();
				workPlan.setWorkDate(new Timestamp(date.getTime()));
			}
			// 值班人员
			if (cell1.getCellType() == Cell.CELL_TYPE_STRING) {
				String name = cell1.getStringCellValue();
				workPlan.setName(name);
			}
			// 联系电话
			String phone = null;
			if (cell2.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				phone = new BigDecimal(cell2.getNumericCellValue())
						.toPlainString();
			} else if (cell2.getCellType() == Cell.CELL_TYPE_STRING) {
				phone = cell2.getStringCellValue();
			}
			workPlan.setPhone(phone);

			// 保存数据库
			workPlan.setType(type);
			list.add(workPlan);
		}
		// 删除老的数据
		workPlanDAO.deleteByType(type);
		workPlanDAO.batchInsert(list);
	}

	@Override
	public WorkPlan getCurrentWorkPlan(String type) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Timestamp today = new Timestamp(c.getTimeInMillis());
		WorkPlan workPlan = workPlanDAO.getCurrentWorkPlan(type, today);
		if (null == workPlan) {
			// throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
			// "Could not find today's work plan !");
			workPlan = new WorkPlan();
			workPlan.setName("");
			workPlan.setPhone("");
		}
		return workPlan;
	}

	@Override
	public SoundRecordLogVO getSoundRecord(String id) {
		SoundRecordLog record = soundRecordLogDAO.findById(id);
		// 更新最近一次播放时间
		record.setUpdateTime(System.currentTimeMillis());
		// 返回
		SoundRecordLogVO vo = new SoundRecordLogVO();
		vo.setCreateTime(record.getCreateTime().toString());
		vo.setHttpAddress(record.getHttpAddress());
		vo.setDuration(record.getDuration().toString());
		vo.setId(record.getId());
		vo.setName(record.getName());
		vo.setNote(record.getNote());
		vo.setCallFlag(record.getCallFlag());
		vo.setStatus(record.getStatus());
		vo.setUpdateTime(record.getUpdateTime().toString());
		vo.setCallNumber(record.getCallNumber());
		vo.setHostNumber(record.getHostNumber());
		return vo;
	}

	@Override
	public String saveClasses(@LogParam("id") String id, String userName,
			String maintainer, String captain, Long beginTime, Long endTime,
			String remark) throws BusinessException {
		Classes entity = null;
		if (StringUtils.isBlank(id)) {
			entity = new Classes();
			entity.setUserName(userName);
			if (null == beginTime) {
				entity.setBeginTime(System.currentTimeMillis());
			} else {
				entity.setBeginTime(beginTime);
			}
			entity.setEndTime(endTime);
			entity.setMaintainer(maintainer);
			entity.setCaptain(captain);
			entity.setRemark(remark);
			// 存入缓存以便记录日志信息
			CacheData.getInstance().classes.clear();
			CacheData.getInstance().classes.put("classes", entity);
			classesDAO.save(entity);
		} else {
			entity = classesDAO.findById(id);
			if (null != userName) {
				entity.setUserName(userName);
			}
			if (null != beginTime) {
				entity.setBeginTime(beginTime);
			}
			// 交班时结束时间不为空
			if (null != endTime) {
				entity.setEndTime(endTime);
				// 交班时清除缓存记录
				CacheData.getInstance().classes.clear();
			}
			if (null != maintainer) {
				entity.setMaintainer(maintainer);
			}
			if (null != captain) {
				entity.setCaptain(captain);
			}
			if (null != remark) {
				entity.setRemark(remark);
			}
		}

		return entity.getId();
	}

	@Override
	public int countClasses(String userName, Long beginTime, Long endTime) {
		return classesDAO.countClasses(userName, beginTime, endTime);
	}

	@Override
	public List<ListClassesVO> listClasses(String userName, Long begin,
			Long end, int start, int limit) {
		List<Classes> classes = classesDAO.listClasses(userName, begin, end,
				start, limit);
		List<ListClassesVO> list = new LinkedList<ListClassesVO>();
		for (Classes clas : classes) {
			ListClassesVO vo = new ListClassesVO();
			vo.setId(clas.getId());
			vo.setBeginTime(clas.getBeginTime());
			vo.setCaptain(clas.getCaptain());
			vo.setEndTime(clas.getEndTime());
			vo.setMaintainer(clas.getMaintainer());
			vo.setUserName(clas.getUserName());
			vo.setRemark(clas.getRemark());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteClasses(@LogParam("id") String id) {
		classesDAO.deleteById(id);
	}

	@Override
	public ClassesVO getClasses(String id) throws BusinessException {
		ClassesVO vo = null;
		Classes clas = null;
		if (StringUtils.isNotBlank(id)) {
			clas = classesDAO.findById(id);
			vo = new ClassesVO();
			vo.setBeginTime(clas.getBeginTime());
			vo.setCaptain(clas.getCaptain());
			vo.setEndTime(clas.getEndTime());
			vo.setId(clas.getId());
			vo.setMaintainer(clas.getMaintainer());
			vo.setUserName(clas.getUserName());
			vo.setRemark(clas.getRemark());
		} else {
			clas = classesDAO.getClasses();
			if (clas != null) {
				if (clas.getEndTime() == null) {
					vo = new ClassesVO();
					vo.setBeginTime(clas.getBeginTime());
					vo.setCaptain(clas.getCaptain());
					vo.setEndTime(clas.getEndTime());
					vo.setId(clas.getId());
					vo.setMaintainer(clas.getMaintainer());
					vo.setUserName(clas.getUserName());
					vo.setRemark(clas.getRemark());
				}
			}
		}
		return vo;
	}

	@Override
	public Element listSoundRecord(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag, String type,
			Integer startIndex, Integer limit) {
		List<SoundRecordLog> logs = soundRecordLogDAO.listSoundRecord(
				beginTime, endTime, minDuration, maxDuration, name, callNumber,
				hostNumber, callFlag, type, startIndex, limit);
		Element list = new Element("Sounds");
		for (SoundRecordLog log : logs) {
			Element entity = new Element("Sound");
			entity.setAttribute("HttpAddress", log.getHttpAddress());
			entity.setAttribute("CallNumber",
					log.getCallNumber() != null ? log.getCallNumber() : "");
			entity.setAttribute("Id", log.getId());
			entity.setAttribute("Name", log.getName());
			entity.setAttribute("Note", log.getNote() != null ? log.getNote()
					: "");
			entity.setAttribute("CallFlag", log.getCallFlag());
			entity.setAttribute("Status",
					log.getStatus() != null ? log.getStatus() : "");
			entity.setAttribute("HostNumber",
					log.getHostNumber() != null ? log.getHostNumber() : "");
			entity.setAttribute("CreateTime", log.getCreateTime() != null ? log
					.getCreateTime().toString() : "");
			entity.setAttribute("Duration", log.getDuration() != null ? log
					.getDuration().toString() : "");
			entity.setAttribute("UpdateTime", log.getUpdateTime() != null ? log
					.getUpdateTime().toString() : "");
			list.addContent(entity);
		}
		return list;
	}

	@Override
	public int countSoundRecordLog(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag) {
		return soundRecordLogDAO.countSoundRecordLog(beginTime, endTime,
				minDuration, maxDuration, name, callNumber, hostNumber,
				callFlag);
	}

	@Override
	public void saveSoundRecord(Integer duration, String callFlag,
			Long createTime, String status, String httpAddress,
			String callNumber, String hostNumber, String note, String userId) {
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// params.put("httpAddress", httpAddress);
		// List<SoundRecordLog> httpAddresses = soundRecordLogDAO
		// .findByPropertys(params);
		// if (httpAddresses.size() > 0) {
		// throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
		// "httpAddress[" + httpAddress + "]is already exist");
		// }
		String name = "";
		if (StringUtils.isNotBlank(httpAddress)) {
			String s[] = httpAddress.split("/");
			name = s[s.length - 1];
		}

		SoundRecordLog sr = new SoundRecordLog();
		sr.setCreateTime(createTime);
		sr.setDuration(duration);
		sr.setCallFlag(callFlag);
		sr.setCallNumber(callNumber);
		sr.setHostNumber(hostNumber);
		sr.setHttpAddress(httpAddress);
		sr.setName(name);
		sr.setNote(note);
		sr.setStatus(status);
		soundRecordLogDAO.save(sr);

		// 保存录音是单独记录用户操作日志
		Classes classes = CacheData.getInstance().classes.get("classes");
		User user = userDAO.findById(userId);
		UserOperationLog log = new UserOperationLog();
		log.setClassesId(classes != null ? classes.getId() : "");
		log.setClassesName(classes != null ? classes.getUserName() : "");
		log.setCreateTime(createTime);
		log.setUserId(user.getId());
		log.setUserName(user.getLogonName());
		log.setOperationType("soundRecord");
		log.setSoundRecordLog(sr);
		userOperationLogDAO.save(log);
	}
}
