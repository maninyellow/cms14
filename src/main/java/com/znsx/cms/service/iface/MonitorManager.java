package com.znsx.cms.service.iface;

import java.util.List;

import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.WallScheme;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DisplayWallVO;
import com.znsx.cms.service.model.ListDwsMonitorVO;
import com.znsx.cms.service.model.MonitorVO;
import com.znsx.cms.service.model.WallVO;

/**
 * 视频输出业务接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:12:41
 */
public interface MonitorManager extends BaseManager {

	/**
	 * 
	 * 创建电视墙
	 * 
	 * @param organId
	 *            机构ID
	 * @param wallName
	 *            电视墙名称
	 * @param note
	 *            备注
	 * @return 电视墙ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:18:37
	 */
	@LogMethod(name = "创建电视墙", operationType = "create", code = "2170", targetType = "DisplayWall")
	public String createWall(String organId, @LogParam("name") String wallName,
			String note);

	/**
	 * 
	 * 更新电视墙
	 * 
	 * @param id
	 *            电视墙ID
	 * @param wallName
	 *            电视墙名称
	 * @param note
	 *            备注
	 * @param organId
	 *            机构id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:11:19
	 */
	@LogMethod(name = "更新电视墙", operationType = "update", code = "2171", targetType = "DisplayWall")
	public void updateWall(@LogParam("id") String id,
			@LogParam("name") String wallName, String note, String organId);

	/**
	 * 
	 * 删除电视墙
	 * 
	 * @param id
	 *            电视墙ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:29:05
	 */
	@LogMethod(name = "删除电视墙", operationType = "delete", code = "2172", targetType = "DisplayWall")
	public void deleteWall(@LogParam("id") String id);

	/**
	 * 
	 * 根据ID查询电视墙
	 * 
	 * @param id
	 *            电视墙ID
	 * @return 电视墙实体
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:43:18
	 */
	public DisplayWallVO getWall(String id);

	/**
	 * 
	 * 查询电视墙总计数
	 * 
	 * @param organId
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:11:42
	 */
	public Integer wallTotalCount(String organId);

	/**
	 * 
	 * 根据机构ID查询电视墙列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 电视墙列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:19:12
	 */
	public List<DisplayWallVO> listWall(String organId, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 创建视频输出
	 * 
	 * @param organId
	 *            机构ID
	 * @param wallId
	 *            电视墙ID
	 * @param channelNumber
	 *            通道号
	 * @param standardNumber
	 *            标准号
	 * @param dwsId
	 *            电视墙ID
	 * @param name
	 *            视频输出名称
	 * @return 视频输出ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:58:34
	 */
	@LogMethod(name = "创建视频输出", operationType = "create", code = "2161", targetType = "Monitor")
	public String createMonitor(String organId, String wallId,
			Integer channelNumber, String standardNumber, String dwsId,
			@LogParam("name") String name);

	/**
	 * 
	 * 更新视频输出
	 * 
	 * @param id
	 *            视频输出ID
	 * @param organId
	 *            机构ID
	 * @param wallId
	 *            电视墙ID
	 * @param channelNumber
	 *            通道号
	 * @param standardNumber
	 *            标准号
	 * @param dwsId
	 *            电视墙服务器ID
	 * @param name
	 *            视频输出名称
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:56:14
	 */
	@LogMethod(name = "修改视频输出", operationType = "update", code = "2162", targetType = "Monitor")
	public void updateMonitor(@LogParam("id") String id, String organId,
			String wallId, Integer channelNumber, String standardNumber,
			String dwsId, @LogParam("name") String name);

	/**
	 * 
	 * 删除视频输出
	 * 
	 * @param id
	 *            视频输出ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:49:56
	 */
	@LogMethod(name = "删除视频输出", operationType = "delete", code = "2163", targetType = "Monitor")
	public void deleteMonitor(@LogParam("id") String id);

	/**
	 * 
	 * 根据ID查询视频输出
	 * 
	 * @param id
	 *            视频输出ID
	 * @return 视频输出对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:08:16
	 */
	public MonitorVO getMonitor(String id);

	/**
	 * 
	 * 根据电视墙ID查询视频输出总计数
	 * 
	 * @param wallId
	 *            电视墙ID
	 * @return 视频输出总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:19:40
	 */
	public Integer monitorTotalCount(String wallId);

	/**
	 * 
	 * 根据电视墙ID查询视频输出列表
	 * 
	 * @param wallId
	 *            电视墙ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 视频输出列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:31:16
	 */
	public List<MonitorVO> listMonitor(String wallId, Integer startIndex,
			Integer limit);

	/**
	 * 编辑电视墙布局
	 * 
	 * @param wallId
	 *            电视墙ID
	 * @param monitorList
	 *            视频输出列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-10 上午10:56:58
	 */
	@LogMethod(name = "编辑电视墙布局", operationType = "editWallLayout", targetType = "DisplayWall", code = "1023")
	public void editWallLayout(@LogParam("id") String wallId,
			List<Element> monitorList) throws BusinessException;

	/**
	 * 查询机构下的电视墙列表
	 * 
	 * @param organId
	 *            机构ID
	 * @return 电视墙列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-10 上午11:25:01
	 */
	public List<WallVO> listOrganWall(String organId) throws BusinessException;

	/**
	 * 查询用户具有权限的电视墙
	 * 
	 * @param userId
	 *            登录的用户ID
	 * @return 电视墙列表
	 * @author huangbuji
	 *         <p />
	 *         2014-11-15 上午11:46:25
	 */
	public List<WallVO> listUserWall(String userId);

	/**
	 * 获取电视墙服务器和视频输出
	 * 
	 * @param organId
	 *            机构id
	 * @return 电视墙服务器和视频输出
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:30:46
	 */
	public List<ListDwsMonitorVO> getDwsMonitor(String organId);

	/**
	 * 创建电视墙控制方案
	 * 
	 * @param name
	 *            方案名称
	 * @param userId
	 *            创建用户ID
	 * @param organId
	 *            所属机构ID
	 * @param wallId
	 *            关联的电视墙ID
	 * @param items
	 *            各输出通道中的播放内容
	 * @return 创建成功的电视墙控制方案ID
	 * @author huangbuji
	 *         <p />
	 *         2014-11-18 下午3:33:22
	 */
	@LogMethod(name = "创建电视墙控制方案", operationType = "createWallScheme", targetType = "WallScheme", code = "1029")
	public String createWallScheme(@LogParam("name") String name,
			String userId, String organId, String wallId, List<Element> items)
			throws BusinessException;

	/**
	 * @param id
	 *            电视墙控制方案ID
	 * @author huangbuji
	 *         <p />
	 *         2014-11-18 下午3:39:01
	 */
	@LogMethod(name = "删除电视墙控制方案", operationType = "deleteWallScheme", targetType = "WallScheme", code = "1031")
	public void deleteWallScheme(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 查询电视墙控制方案列表
	 * 
	 * @param userId
	 *            根据用户条件查询
	 * @param organId
	 *            根据机构条件查询
	 * @return 电视墙控制方案列表
	 * @author huangbuji
	 *         <p />
	 *         2014-11-18 下午3:40:55
	 */
	public List<WallScheme> listWallScheme(String userId, String organId);
	
	/**
	 *   修改电视墙控制方案
	 * 
	 * @param name
	 *            方案名称
	 * @param schemeId
	 *            关联的电视墙ID
	 * @author sjt
	 *         <p />
	 *         2015-9-10 下午3:33:22
	 */
	@LogMethod(name = "修改电视墙控制方案", operationType = "updateWallScheme", targetType = "WallScheme", code = "1032")
	public void updateWallScheme(@LogParam("id") String schemeId,@LogParam("name") String name,
			List<Element> monitors)
			throws BusinessException;
}
