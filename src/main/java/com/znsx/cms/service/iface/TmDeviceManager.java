package com.znsx.cms.service.iface;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.CmsCommand;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.persistent.model.PlaylistFolder;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.TypeDef;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.CmsInfoVO;
import com.znsx.cms.service.model.CoviInfoVO;
import com.znsx.cms.service.model.GetTypeDefVO;
import com.znsx.cms.service.model.LoliInfoVO;
import com.znsx.cms.service.model.NodInfoVO;
import com.znsx.cms.service.model.VdInfoVO;
import com.znsx.cms.service.model.WsInfoVO;
import com.znsx.cms.service.model.WstInfoVO;

/**
 * 交通监控设备业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-15 下午3:03:02
 */
public interface TmDeviceManager extends BaseManager {
	/**
	 * 车检器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的车检器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-15 下午2:44:57
	 */
	public List<VdInfoVO> vdInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的车检器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的车检器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-15 下午3:43:54
	 */
	public int countVdInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 气象检测器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的气象检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:19:46
	 */
	public List<WstInfoVO> wstInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的气象检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的气象检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:20:46
	 */
	public int countWstInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * COVI检测器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的COVI检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:19:46
	 */
	public List<CoviInfoVO> coviInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的COVI检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的COVI检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:20:46
	 */
	public int countCoviInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 风速风向检测器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的风速风向检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:19:46
	 */
	public List<WsInfoVO> wsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的风速风向检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的风速风向检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:20:46
	 */
	public int countWsInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 光强检测器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的光强检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:19:46
	 */
	public List<LoliInfoVO> loliInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的光强检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的光强检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:20:46
	 */
	public int countLoliInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

	/**
	 * 氮氧化物检测器信息查询
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @param start
	 *            开始行号，分页需要
	 * @param limit
	 *            要查询的记录数，分页需要
	 * @return 满足条件的氮氧化物检测器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:19:46
	 */
	public List<NodInfoVO> nodInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit);

	/**
	 * 统计满足条件的氮氧化物检测器数量
	 * 
	 * @param organId
	 *            查询条件：机构ID，精确查询
	 * @param deviceName
	 *            查询条件：设备名称，模糊查询
	 * @param navigation
	 *            查询条件：车行方向:1-上行方向，2-下行方向，精确查询
	 * @param stakeNumber
	 *            查询条件：桩号，精确查询
	 * @return 满足条件的氮氧化物检测器数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 上午9:20:46
	 */
	public int countNodInfo(String organId, String deviceName,
			String navigation, String stakeNumber);

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
	 *         Create at 2014-1-16 下午2:37:27
	 */
	public List<CmsInfoVO> cmsInfo(String organId, String deviceName,
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
	 *         Create at 2014-1-16 下午2:39:04
	 */
	public int countCmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType);

	/**
	 * 统计平台中的数据设备数量
	 * 
	 * @return 平台中的数据设备数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-8 上午10:55:22
	 */
	public int countDeviceAmount();

	/**
	 * 查询类型字典表
	 * 
	 * @param type
	 *            主类型，为空将返回全部类型
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-1 上午11:58:21
	 */
	public List<TypeDef> listTypeDef(Integer type);

	/**
	 * 保存情报板常用条目 savePlaylist方法说明
	 * 
	 * @param folderId
	 *            文件夹ID
	 * @param playlistId
	 *            常用条目ID
	 * @param playlistName
	 *            常用条目名称
	 * @param type
	 *            信息类型，1-标语，2-交通提醒，3-施工提醒.
	 * @param items
	 *            播放内容列表
	 * @param cmsSize
	 *            情报板大小规格，格式宽*高
	 * @return 保存成功的常用条目对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-3 下午2:46:59
	 */
	// @LogMethod(targetType = "Playlist", operationType = "save", name =
	// "保存情报板常用条目", code = "1251")
	public Playlist savePlaylist(String folderId,
			@LogParam("id") String playlistId,
			@LogParam("name") String playlistName, Short type,
			List<Element> items, String cmsSize) throws BusinessException;

	/**
	 * 删除情报板常用条目
	 * 
	 * @param id
	 *            常用条目ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午9:22:52
	 */
	@LogMethod(targetType = "Playlist", operationType = "delete", name = "删除情报板常用条目", code = "1252")
	public void deletePlaylist(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 查询文件夹下的情报板常用列表
	 * 
	 * @param folderId
	 *            文件夹ID
	 * @param type
	 *            情报板子类型，为空查询所有类型
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午9:49:37
	 */
	public List<Playlist> listPlaylist(String folderId, Short type);

	/**
	 * 查询指定类型情报板的常用条目文件夹
	 * 
	 * @param subType
	 *            情报板子类型
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午10:12:18
	 */
	public List<PlaylistFolder> listFolder(Integer subType);

	/**
	 * 创建情报板的常用条目文件夹
	 * 
	 * @param name
	 *            文件夹名称
	 * @param subType
	 *            情报板子类型
	 * @return 创建成功的文件夹ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午10:29:43
	 */
	@LogMethod(targetType = "PlaylistFolder", operationType = "create", name = "创建情报板的常用条目文件夹", code = "1255")
	public String createFolder(@LogParam("name") String name, Integer subType)
			throws BusinessException;

	/**
	 * 删除情报板的常用条目文件夹
	 * 
	 * @param id
	 *            文件夹ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午10:40:45
	 */
	@LogMethod(targetType = "PlaylistFolder", operationType = "delete", name = "删除情报板的常用条目文件夹", code = "1256")
	public void deleteFolder(@LogParam("id") String id)
			throws BusinessException;

	/**
	 * 查询设备类型列表
	 * 
	 * @param type
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午10:37:54
	 */
	public List<GetTypeDefVO> listDeviceType(Integer type);

	/**
	 * 保存情报板发布信令
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-20 下午4:00:49
	 */
	public List<String> saveVmsCommand(List<InputStream> ins);

	/**
	 * 获取情报板发布指令
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-20 下午4:30:10
	 */
	public CmsCommand getCmsCommand(String id);

	/**
	 * 获取平台已建的所有情报板的宽*高类型集合
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-22 上午11:59:00
	 */
	public Set<String> listCmsSize();

	/**
	 * 查询所有的车辆检测器
	 * 
	 * @return 平台中所有的车辆检测器
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-21 下午2:36:15
	 */
	public List<VehicleDetector> listAllVd();

	/**
	 * 
	 * 读入excel火灾检测器数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 火灾检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午2:55:34
	 */
	public List<FireDetector> readFireDetectorWb(Workbook wb, License license);

	/**
	 * 
	 * 读入excel照明回路数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 照明回路列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午2:56:41
	 */
	public List<ControlDeviceLight> readControlDeviceLightWb(Workbook wb,
			License license);

	/**
	 * 
	 * 读入excel水泵数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 水泵列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午2:57:32
	 */
	public List<ControlDeviceWp> readControlDeviceWpWb(Workbook wb,
			License license);

	/**
	 * 
	 * 读入excel手报数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 手报列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午2:58:55
	 */
	public List<PushButton> readPushButtonWb(Workbook wb, License license);

	/**
	 * 
	 * 读入excel车道指示器数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 车道指示器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午2:59:35
	 */
	public List<ControlDeviceLil> readControlDeviceLilWb(Workbook wb,
			License license);

	/**
	 * 
	 * 读入excel风机数据
	 * 
	 * @param wb
	 *            excel内容
	 * @param license
	 *            许可证
	 * @return 风机列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午3:00:06
	 */
	public List<ControlDeviceFan> readControlDeviceFanWb(Workbook wb,
			License license);

	/**
	 * 
	 * 批量插入火灾检测器
	 * 
	 * @param fds
	 *            火灾检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午4:45:15
	 */
	public void batchInsertFd(List<FireDetector> fds);

	/**
	 * 
	 * 批量插入照明回路
	 * 
	 * @param light
	 *            照明回路列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:11:47
	 */
	public void batchInsertLight(List<ControlDeviceLight> cdls);

	/**
	 * 
	 * 批量插入水泵
	 * 
	 * @param cdws
	 *            水泵列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:26:57
	 */
	public void batchInsertWp(List<ControlDeviceWp> cdws);

	/**
	 * 
	 * 批量插入手报
	 * 
	 * @param pbs
	 *            手报列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:41:35
	 */
	public void batchInsertPb(List<PushButton> pbs);

	/**
	 * 
	 * 批量插入风机
	 * 
	 * @param cdlis
	 *            风机列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:48:13
	 */
	public void batchInsertFan(List<ControlDeviceFan> cdlis);

	/**
	 * 
	 * 批量插入车道指示器
	 * 
	 * @param cdlis
	 *            车道指示器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-7-14 下午5:54:04
	 */
	public void batchInsertLil(List<ControlDeviceLil> cdlis);
}
