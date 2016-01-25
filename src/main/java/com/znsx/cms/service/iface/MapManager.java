package com.znsx.cms.service.iface;

import java.util.List;

import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.CmsPublishLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.LightPolicyVO;
import com.znsx.cms.service.model.PlaylistVO;
import com.znsx.cms.service.model.TimePolicyVO;

/**
 * 地图业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-22 下午2:20:56
 */
public interface MapManager extends BaseManager {
	/**
	 * 保存地图上的标注点位信息
	 * 
	 * @param marks
	 *            标注
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-22 下午2:43:51
	 */
	@LogMethod(name = "修改地图标注坐标", operationType = "savaMarkers", code = "1204", targetType = "Map")
	public void saveMarkers(List<Element> markers) throws BusinessException;

	/**
	 * 创建情报板播放方案
	 * 
	 * @param organId
	 *            机构ID
	 * @param name
	 *            播放方案名称
	 * @param items
	 *            播放方案内容列表
	 * @return 创建成功的播放方案ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-26 上午9:59:46
	 */
	@LogMethod(name = "创建情报板播放方案", operationType = "createPlaylist", code = "1205", targetType = "Playlist")
	public String createPlaylist(String organId, @LogParam("name") String name,
			List<Element> items) throws BusinessException;

	/**
	 * 删除情报板播放方案
	 * 
	 * @param id
	 *            播放方案ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-26 下午1:38:56
	 */
	@LogMethod(name = "删除情报板播放方案", operationType = "deletePlaylist", code = "1207", targetType = "Playlist")
	public void deletePlaylist(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 查询机构下的情报板播放方案列表
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构下的情报板播放方案列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-26 下午1:49:41
	 */
	public List<PlaylistVO> listPlaylist(String organId);

	/**
	 * 保存照明策略
	 * 
	 * @param organId
	 *            机构ID
	 * @param name
	 *            策略名称
	 * @param id
	 *            照明策略ID，如果存在则修改，如果为空则新增
	 * @param lights
	 *            照明灯控制状态列表
	 * @return 照明策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午9:38:01
	 */
	@LogMethod(name = "保存照明策略", operationType = "saveLightPolicy", code = "1208", targetType = "Policy")
	public String saveLightPolicy(String organId,
			@LogParam("name") String name, String id, List<Element> lights)
			throws BusinessException;

	/**
	 * 删除照明策略
	 * 
	 * @param id
	 *            策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午11:05:19
	 */
	@LogMethod(name = "删除照明策略", operationType = "deleteLightPolicy", code = "1209", targetType = "Policy")
	public void deleteLightPolicy(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 查询照明策略列表
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构下的照明策略列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午11:25:53
	 */
	public List<LightPolicyVO> listLightPolicy(String organId);

	/**
	 * 保存定时策略
	 * 
	 * @param organId
	 *            机构ID
	 * @param name
	 *            定时策略名称
	 * @param id
	 *            策略ID
	 * @param items
	 *            普通策略执行计划列表
	 * @return 定时策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午3:51:02
	 */
	@LogMethod(name = "保存定时策略", operationType = "saveTimePolicy", code = "1211", targetType = "Policy")
	public String saveTimePolicy(String organId, @LogParam("name") String name,
			String id, List<Element> items) throws BusinessException;

	/**
	 * 查询机构下的定时策略列表
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构下的定时策略列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-28 上午10:17:48
	 */
	public List<TimePolicyVO> listTimePolicy(String organId);

	/**
	 * 删除定时策略
	 * 
	 * @param id
	 *            定时策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-3-4 上午9:34:01
	 */
	@LogMethod(name = "删除定时策略", operationType = "deleteTimePolicy", code = "1229", targetType = "Policy")
	public void deleteTimePolicy(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 保存CMS发布信息日志记录
	 * 
	 * @param cmsSNs
	 *            CMS标准号列表
	 * @param organId
	 *            发布机构ID,如果是下级机构则为机构标准号
	 * @param organName
	 *            发布机构名称
	 * @param userId
	 *            发布用户ID
	 * @param userName
	 *            发布用户名称
	 * @param items
	 *            信息内容列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-14 上午11:12:04
	 */
	public void cmsPublishLog(List<String> cmsSNs, String organId,
			String organName, String userId, String userName,
			List<Element> items) throws BusinessException;

	/**
	 * 查询指定情报的最近发布信息记录
	 * 
	 * @param standardNumber
	 *            情报板标准号
	 * @return 最近发布信息记录
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-31 上午11:26:30
	 */
	public List<CmsPublishLog> listCmsLatestRecord(String standardNumber);

	/**
	 * 统计情报板发布日志数量
	 * 
	 * @param cmsName
	 *            情报板名称
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organId
	 *            当前用户机构id
	 * @return 数量
	 */
	public Integer countTotalCMSLog(String cmsName, String userName,
			Long beginTime, Long endTime, String organId);

	/**
	 * 查询情报板发布日志信息
	 * 
	 * @param organId
	 *            机构id
	 * @param cmsName
	 *            情报板名称
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 情报板发布日志信息
	 */
	public List<Element> listCmsLog(String organId, String cmsName,
			String userName, Long beginTime, Long endTime, Integer startIndex,
			Integer limit);

	/**
	 * 统计情报板发布日志数量(多条合并)
	 * 
	 * @param cmsName
	 *            情报板名称
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organId
	 *            当前用户机构id
	 * @return 数量
	 */
	public Integer countCmsPublishLog(String[] organs, String[] cmsSns,
			String userName, Long beginTime, Long endTime);

	/**
	 * 查询情报板发布日志信息
	 * 
	 * @param organId
	 *            机构id
	 * @param cmsName
	 *            情报板名称
	 * @param userName
	 *            用户名称
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 情报板发布日志信息
	 */
	public List<Element> listCmsPublishLog(String[] organs, String[] cmsSns,
			String userName, Long beginTime, Long endTime, Integer startIndex,
			Integer limit);

	/**
	 * 获取机构下，按名称模糊查询的情报板SN集合
	 * 
	 * @param organs
	 *            机构ID集合
	 * @param cmsName
	 *            情报板名称
	 * @return 情报板SN集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-10-14 下午2:30:43
	 */
	public String[] listCmsSnsByName(String[] organs, String cmsName);
}
