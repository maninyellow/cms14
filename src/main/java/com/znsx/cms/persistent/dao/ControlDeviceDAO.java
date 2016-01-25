package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.service.model.LogCmsVO;

/**
 * 控制设备器数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:28:51
 */
public interface ControlDeviceDAO extends BaseDAO<ControlDevice, String> {
	/**
	 * 返回以控制类设备ID为键，控制类设备对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有控制类设备键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-21 下午5:00:55
	 */
	public Map<String, ControlDevice> mapCDByOrganIds(String[] organIds);

	/**
	 * 
	 * 统计控制设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:57:25
	 */
	public Integer countControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Short type, Short subType);

	/**
	 * 
	 * 根据条件查询控制设备列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param type
	 *            设备类型
	 * @param subType
	 *            子类型
	 * @return 控制设备列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:07:12
	 */
	public List<ControlDevice> listControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit, Short type, Short subType);

	/**
	 * 查询指定数采服务器下方的控制类列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的控制类列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午4:16:07
	 */
	public List<ControlDevice> listByDAS(String dasId);

	/**
	 * 根据SN数组查找控制类设备对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以控制类设备SN为键，控制类设备对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, ControlDevice> mapCDBySNs(String[] sns);

	/**
	 * 
	 * 删除控制设备和角色的关系
	 * 
	 * @param id
	 *            设备id
	 * @param type
	 *            设备类型
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:39:11
	 */
	public void deleteRoleCDPermission(String id, String type);

	/**
	 * 根据CMS标准号数组查找CMS对象列表
	 * 
	 * @param sns
	 *            CMS标准号数组
	 * @return CMS对象列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-14 下午2:38:07
	 */
	public List<ControlDevice> listCMSBySNs(String[] sns);

	/**
	 * 可变信息标志信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param subType
	 *            子类型：门架式可变信息标志、立柱式可变信息标志、悬臂式可变信息标志、交通信号灯、隧道内可变信息标志，车道指示灯，分别为：1、
	 *            2、3、4、5、6
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的可变信息标志列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午2:57:45
	 */
	public List<ControlDevice> cmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType,
			Integer start, Integer limit);

	/**
	 * 统计满足条件的可变信息标志数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param subType
	 *            子类型：门架式可变信息标志、立柱式可变信息标志、悬臂式可变信息标志、交通信号灯、隧道内可变信息标志，车道指示灯，分别为：1、
	 *            2、3、4、5、6
	 * @return 满足条件的可变信息标志数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午2:58:23
	 */
	public Integer countCmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType);

	/**
	 * 
	 * 根据机构id数组查询控制设备
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 控制设备集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:35:14
	 */
	public String[] countCD(String[] organIds);

	/**
	 * 
	 * 根据机构id统计控制设备数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 控制设备数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午1:47:50
	 */
	public int countByOrganId(String organId);

	/**
	 * 查询给定机构ID集合下的控制类设备列表
	 * 
	 * @param organIds
	 *            机构ID集合
	 * @param type
	 *            设备类型，参见{@link com.znsx.cms.service.common.TypeDefinition}
	 * @return 机构下的控制类设备列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-16 上午10:40:32
	 */
	public List<ControlDevice> listControlDevices(List<String> organIds,
			int type);

	/**
	 * 根据机构数组统计情报板sn
	 * 
	 * @param organs
	 *            机构数组
	 * @param cmsName
	 *            情报板名称
	 * @return 情报板sn
	 */
	public String[] cmsSNByOrgan(String[] organs, String cmsName);

	/**
	 * 查询情报板列表
	 * 
	 * @param cmsSNs
	 *            情报板sn
	 * @return 情报板列表
	 */
	public List<LogCmsVO> listCmsVO(String[] cmsSNs);

	/**
	 * 查询情报板以SN为key, Name为value的映射表
	 * 
	 * @param cmsSns
	 *            情报板SN集合
	 * @return SN为key, Name为value的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-10-14 下午2:51:35
	 */
	public Map<String, String> mapCms(String[] cmsSns);

	/**
	 * 
	 * 批量插入照明回路
	 * 
	 * @param controlDeviceLight
	 *            照明回路对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:13:18
	 */
	public void batchInsertLight(ControlDeviceLight controlDeviceLight);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:16:26
	 */
	public void excuteBatchLight();

	/**
	 * 
	 * 批量插入照明回路
	 * 
	 * @param controlDeviceLight
	 *            照明回路对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:38:56
	 */
	public void batchInsertWp(ControlDeviceWp controlDeviceWp);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:39:19
	 */
	public void excuteBatchWp();

	/**
	 * 
	 * 批量插入风机
	 * 
	 * @param controlDeviceFan
	 *            风机对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:50:39
	 */
	public void batchInsertFan(ControlDeviceFan controlDeviceFan);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:51:03
	 */
	public void excuteBatchFan();

	/**
	 * 
	 * 批量插入车道指示器
	 * 
	 * @param controlDeviceLil
	 *            车道指示器对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午6:05:17
	 */
	public void batchInsertLil(ControlDeviceLil controlDeviceLil);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午6:05:48
	 */
	public void excuteBatchLil();
}
