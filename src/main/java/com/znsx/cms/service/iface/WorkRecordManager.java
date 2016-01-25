package com.znsx.cms.service.iface;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.SoundRecordLog;
import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.ClassesVO;
import com.znsx.cms.service.model.ListCenterWorkRecordVO;
import com.znsx.cms.service.model.ListClassesVO;
import com.znsx.cms.service.model.ListVisitRecordVO;
import com.znsx.cms.service.model.SoundRecordLogVO;

/**
 * 操作人员值班记录业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-26 上午9:57:41
 */
public interface WorkRecordManager extends BaseManager {
	/**
	 * 保存来电来访记录
	 * 
	 * @param id
	 *            来电来访记录ID,为空表示新增
	 * @param userId
	 *            记录人员ID
	 * @param userName
	 *            记录人员名称
	 * @param arriveTime
	 *            来访时间
	 * @param leaveTime
	 *            离开时间
	 * @param visitorName
	 *            来访人姓名
	 * @param phone
	 *            来访人电话
	 * @param reason
	 *            原由
	 * @param result
	 *            处理结果
	 * @param note
	 *            重大实现备注
	 * @return 来电来访记录ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-27 下午3:11:42
	 */
	@LogMethod(name = "保存来电来访记录", targetType = "VisitRecord", operationType = "saveVisitRecord", code = "1259")
	public String saveVisitRecord(@LogParam("id") String id, String userId,
			String userName, Long arriveTime, Long leaveTime,
			@LogParam("name") String visitorName, String phone, String reason,
			String result, String note) throws BusinessException;

	/**
	 * 保存中心当班情况记录
	 * 
	 * @param id
	 *            中心当班情况记录ID,为空表示新增
	 * @param userId
	 *            记录人员ID
	 * @param userName
	 *            记录人员名称
	 * @param handoverRecordId
	 *            交接班记录对象ID
	 * @param recordTime
	 *            记录时间
	 * @param monitorUsers
	 *            监控员姓名
	 * @param maintainUsers
	 *            维护员姓名
	 * @param note
	 *            备注
	 * @return 中心当班情况记录ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-27 下午3:13:27
	 */
	@LogMethod(name = "保存中心当班情况记录", targetType = "CenterWorkRecord", operationType = "saveCenterWorkRecord", code = "1260")
	public String saveCenterWorkRecord(@LogParam("id") String id,
			String userId, String userName, Long recordTime,
			@LogParam("name") String monitorUsers, String maintainUsers,
			String note) throws BusinessException;

	/**
	 * 查询来电来访记录
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 来电来访记录列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 上午11:25:57
	 */
	public List<ListVisitRecordVO> listVisitRecord(String userName, Long begin,
			Long end, int start, int limit);

	/**
	 * 统计满足条件的来电来访记录条数
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 满足条件的来电来访记录条数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 上午11:28:25
	 */
	public int countVisitRecord(String userName, Long begin, Long end);

	/**
	 * 查询中心当班情况记录
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 中心当班情况记录列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午2:50:29
	 */
	public List<ListCenterWorkRecordVO> listCenterWorkRecord(String userName,
			Long begin, Long end, int start, int limit);

	/**
	 * 统计满足条件的中心当班情况记录
	 * 
	 * @param userName
	 *            监控员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 满足条件的中心当班情况记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午2:51:46
	 */
	public int countCenterWorkRecord(String userName, Long begin, Long end);

	/**
	 * 保存排班计划
	 * 
	 * @param wb
	 *            排班计划表格
	 * @param type
	 *            计划类型: 0-交警,1-路政
	 * @author huangbuji
	 *         <p />
	 *         2014-12-2 下午5:51:42
	 */
	public void saveWorkPlan(Workbook wb, String type);

	/**
	 * 获取当前的值班计划
	 * 
	 * @param type
	 *            计划类型: 0-交警,1-路政
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         2014-12-9 上午9:25:02
	 */
	public WorkPlan getCurrentWorkPlan(String type);

	/**
	 * 
	 * 根据id获取录音信息
	 * 
	 * @param id
	 *            录音id
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-16 上午10:09:56
	 */
	public SoundRecordLogVO getSoundRecord(String id);

	/**
	 * 保存值班班次记录
	 * 
	 * @param id
	 *            值班班次记录ID,为空表示新增
	 * @param userName
	 *            班次人员名称
	 * @param maintainer
	 *            维护人员
	 * @param captain
	 *            班长
	 * @param beginTime
	 *            值班开始时间
	 * @param endTime
	 *            值班结束时间
	 * @param remark
	 *            备注
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午3:11:42
	 */
	@LogMethod(name = "保存值班班次记录", targetType = "ClassesRecord", operationType = "saveClasses", code = "1314")
	public String saveClasses(@LogParam("id") String id, String userName,
			String maintainer, String captain, Long beginTime, Long endTime,
			String remark) throws BusinessException;

	/**
	 * 统计满足条件的所以班次条数
	 * 
	 * @param userName
	 *            班次人员名称条件
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 满足条件的所以班次条数
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午11:28:25
	 */
	public int countClasses(String userName, Long beginTime, Long endTime);

	/**
	 * 查询值班班次列表
	 * 
	 * @param userName
	 *            班次人员名称条件
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 值班班次列表
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午11:25:57
	 */
	public List<ListClassesVO> listClasses(String userName, Long begin,
			Long end, int start, int limit);

	/**
	 * 
	 * 删除值班班次
	 * 
	 * @param id
	 *            班次ID
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午2:41:20
	 */
	@LogMethod(targetType = "Classes", operationType = "delete", name = "删除值班班次", code = "1316")
	public void deleteClasses(@LogParam("id") String id);

	/**
	 * 根据ID查询班次。如果ID不等于null，就是SGC登录时判断是否有班次
	 * 
	 * @param id
	 *            班次ID
	 * @return 班次详细信息
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午1:22:34
	 */
	public ClassesVO getClasses(String id) throws BusinessException;

	/**
	 * 
	 * 条件查询录音记录
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param minDuration
	 *            最小时长
	 * @param maxDuration
	 *            最大时长
	 * @param name
	 *            录音文件名称
	 * @param callNumber
	 *            来电去电号码
	 * @param hostNumber
	 *            主机号码
	 * @param callFlag
	 *            打进打出标志 0-未知 1-打进 2-打出
	 * @param type
	 *            是否按照最近播放录音排序 1-按播放时间排序 0-按录音创建时间排序
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 录音记录集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-18 上午11:40:47
	 */
	public Element listSoundRecord(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag, String type,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计录音记录数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param minDuration
	 *            最小时长
	 * @param maxDuration
	 *            最大时长
	 * @param name
	 *            录音文件名称
	 * @param callNumber
	 *            来电去电号码
	 * @param hostNumber
	 *            主机号码
	 * @param callFlag
	 *            打进打出标志 0-未知 1-打进 2-打出
	 * 
	 * @return 录音数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-18 上午11:51:05
	 */
	public int countSoundRecordLog(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag);

	/**
	 * 
	 * saveSoundRecord方法说明
	 * 
	 * @param duration
	 *            录音时长
	 * @param callFlag
	 *            打进打出标志 1：打进 2：打出 0：未知
	 * @param createTime
	 *            创建时间
	 * @param status
	 *            保留状态
	 * @param httpAddress
	 *            http下载地址
	 * @param callNumber
	 *            呼叫号码
	 * @param hostNumber
	 *            主机号码
	 * @param note
	 *            备注
	 * @param userId
	 *            用户id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-10-22 上午10:57:29
	 */
	public void saveSoundRecord(Integer duration, String callFlag,
			Long createTime, String status, String httpAddress,
			String callNumber, String hostNumber, String note, String userId);
}
