package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.CameraStatusVO;
import com.znsx.cms.service.model.Channel;

/**
 * 摄像头数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:30:21
 */
public interface CameraDAO extends BaseDAO<Camera, String> {
	/**
	 * 查询摄像头列表
	 * 
	 * @param organId
	 *            机构条件
	 * @param name
	 *            名称条件,模糊查询
	 * @return 摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:26:41
	 */
	public List<Camera> listCamera(String organId, String name);

	/**
	 * 
	 * 根据dvrId查询摄像头
	 * 
	 * @param dvrId
	 *            视频服务器ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询总条数
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:59:23
	 */
	public List<Camera> findCameraByDvrId(String dvrId, Integer startIndex,
			Integer limit);

	/**
	 * 
	 * 根据DvrId查询摄像头总数
	 * 
	 * @param dvrId
	 *            视频服务器ID
	 * @return 总数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:09:55
	 */
	public Integer cameraTotalCount(String dvrId);

	/**
	 * 查询指定DVR集合所包涵的摄像头列表
	 * 
	 * @param dvrIds
	 *            DVRID的集合
	 * @return 摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:16:01
	 */
	public List<Camera> listDvrsCamera(List<String> dvrIds);

	/**
	 * 批量插入摄像头
	 * 
	 * @param record
	 *            摄像头
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:19:28
	 */
	public void batchInsert(Camera record) throws BusinessException;

	/**
	 * 执行批量写入数据库的操作
	 * 
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:18:44
	 */
	public void excuteBatch() throws BusinessException;

	/**
	 * 
	 * 删除摄像头和搜藏夹的关联关系
	 * 
	 * @param id
	 *            DvrId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:58:34
	 */
	public void deleteRUserDeviceFavorite(String id);

	/**
	 * 
	 * 删除摄像头和角色的关联
	 * 
	 * @param id
	 *            DvrId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:38:44
	 */
	public void deleteRRP(String id);

	/**
	 * 
	 * 删除预置点
	 * 
	 * @param id
	 *            dvrId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:43:22
	 */
	public void deletePreset(String id);

	/**
	 * 
	 * 删除摄像头
	 * 
	 * @param id
	 *            dvrId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:48:37
	 */
	public void deleteCamera(String id);

	/**
	 * 
	 * 删除预置点和图片关联
	 * 
	 * @param id
	 *            dvrId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:57:16
	 */
	public void deleteCameraPresetImage(String id);

	/**
	 * 
	 * 删除摄像头和搜藏夹关联
	 * 
	 * @param id
	 *            摄像头ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:27:29
	 */
	public void deleteDeviceFavorite(String id);

	/**
	 * 
	 * 删除摄像头和角色的关联
	 * 
	 * @param id
	 *            摄像头ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:31:18
	 */
	public void deleteRoleDevicePermission(String id);

	/**
	 * 统计指定摄像头型号设备的数量
	 * 
	 * @param deviceModelId
	 *            设备型号
	 * @return 设备的数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:05:19
	 */
	public Integer countByDeviceModel(String deviceModelId);

	/**
	 * 获取指定机构ID列表下的所有摄像头
	 * 
	 * @param organIds
	 *            机构ID列表
	 * @return 机构ID列表下的所有摄像头
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午2:23:25
	 */
	public List<Camera> listCameraByOrganIds(String[] organIds);

	/**
	 * 
	 * 根据机构ID查询摄像头总计数
	 * 
	 * @param organId
	 *            机构ID
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:52:47
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 查询摄像头名称
	 * 
	 * @return 摄像头名称
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:07:45
	 */
	public List<String> findNameByCamera();

	/**
	 * 返回以摄像头ID为键，摄像头对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID列表
	 * @return 机构ID列表下的所有摄像头键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-20 下午1:49:46
	 */
	public Map<String, Camera> mapCameraByOrganIds(String[] organIds);

	/**
	 * 
	 * 按条件查询摄像头列表
	 * 
	 * @param organs
	 *            机构以及子机构id
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param manufacturerId
	 *            厂商id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:39:52
	 * @return
	 */
	public List<Camera> listCameraByDevice(String[] organs, String name,
			String stakeNumber, String manufacturerId, Integer startIndex,
			Integer limit, String standardNumber);

	/**
	 * 
	 * 统计摄像头数量
	 * 
	 * @param organs
	 *            机构以及子机构id
	 * @param name
	 *            摄像头名称
	 * @param stakeNumber
	 *            桩号
	 * @param manufacturerId
	 *            厂商id
	 * @param standardNumber
	 *            标准号
	 * @return 摄像头数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:30:24
	 */
	public Integer cameraByDeviceTotalCount(String[] organs, String name,
			String stakeNumber, String manufacturerId, String standardNumber);

	/**
	 * 查询存储下面的摄像头列表
	 * 
	 * @param crsId
	 *            存储ID
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-3-10 下午4:19:33
	 */
	public List<Channel> listCrsCamera(String crsId, Integer start,
			Integer limit);

	/**
	 * 
	 * 根据crsId查询摄像头总数
	 * 
	 * @param crsId
	 *            存储id
	 * @return 摄像头总数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:19:27
	 */
	public Integer cameraTotalCountByCrsId(String crsId);

	/**
	 * 根据机构id和行车方向查询摄像头
	 * 
	 * @param organIds
	 *            机构id
	 * @param navigation
	 *            行车方向
	 * @param stakeNumber
	 *            桩号
	 * @return 摄像头列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:15:09
	 */
	public List<Camera> listNearCamera(String[] organIds, String navigation,
			String stakeNumber);

	/**
	 * 根据摄像头安装位置类型查询摄像头列表
	 * 
	 * @param organIds
	 *            摄像头所属机构列表
	 * @param cameraTypes
	 *            摄像头安装位置类型
	 * @return 满足条件的摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         2014-12-1 下午3:31:46
	 */
	public List<Camera> listCameraByType(Set<String> organIds,
			List<String> cameraTypes);

	/**
	 * 查询摄像头SN
	 * 
	 * @return
	 */
	public List<String> listCameraSN();

	/**
	 * 无状态查询所有的摄像头
	 * 
	 * @return 所有摄像头键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-16 下午2:13:50
	 */
	public Map<String, Camera> statelessMapCamera();

	/**
	 * 获取给定的SN集合的Camera的对象与sn的映射表
	 * 
	 * @param sns
	 *            Camera的sn结合
	 * @return sn为键,Camera为值的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-16 下午2:41:34
	 */
	public Map<String, Camera> mapBySns(Set<String> sns);

	/**
	 * 查询给定机构及子机构下的所有摄像头ID列表
	 * 
	 * @param organId
	 *            机构ID
	 * @return 机构及子机构下的所有摄像头ID列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-22 下午3:09:46
	 */
	public List<String> listCameraIdInOrgan(String organId);

	/**
	 * 查询摄像头状态列表
	 * 
	 * @return 摄像头状态列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-27 上午11:50:47
	 */
	public List<CameraStatusVO> listLocalCameraStatus();

	/**
	 * 查询摄像头列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param name
	 *            摄像头名称，模糊查询
	 * @return 摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-9 下午3:47:46
	 */
	public Map<String, Camera> mapCameraNoTransaction(String organId,
			String name);

	/**
	 * 查询CCS管辖的摄像头列表
	 * 
	 * @param ccsId
	 *            CCS ID
	 * @return CCS管辖的摄像头列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-28 上午10:10:44
	 */
	public List<Camera> listCameraByCcs(String ccsId);
	/**
	 * 查询所有的摄像头
	 * listCamerasNoJoin方法说明
	 * @return
	 * @author huangbuji <p />
	 * Create at 2015-12-2 下午3:28:06
	 */
	public List<Camera> listCamerasNoJoin();
}
