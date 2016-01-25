package com.znsx.cms.service.iface;

import java.util.List;

import org.jdom.Element;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.persistent.model.DeviceUpdateListener;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.persistent.model.Ens;
import com.znsx.cms.persistent.model.Mss;
import com.znsx.cms.persistent.model.Pts;
import com.znsx.cms.persistent.model.Rms;
import com.znsx.cms.persistent.model.Rss;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.CrsUpdateConfigVO;
import com.znsx.cms.service.model.ListCcsVO;
import com.znsx.cms.service.model.ListCrsVO;
import com.znsx.cms.service.model.ListDasVO;
import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.service.model.ListEnsVO;
import com.znsx.cms.service.model.ListGisVO;
import com.znsx.cms.service.model.ListMssVO;
import com.znsx.cms.service.model.ListPtsVO;
import com.znsx.cms.service.model.ListRmsVO;
import com.znsx.cms.service.model.ListRssVO;
import com.znsx.cms.service.model.ListSrsVO;
import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.web.dto.omc.ListOnlineUsersDTO;
import com.znsx.cms.web.dto.omc.ListPlatformServerDTO;

/**
 * 平台服务器业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:09:59
 */
public interface PlatformServerManager extends BaseManager {

	/**
	 * 创建流媒体服务器
	 * 
	 * @param standardNumber
	 *            流媒体服务器标准号
	 * @param name
	 *            流媒体服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @return 创建成功后的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:39:24
	 */
	@LogMethod(targetType = "Mss", operationType = "create", name = "创建流媒体服务器", code = "2090")
	public String createMss(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException;

	/**
	 * 修改流媒体服务器
	 * 
	 * @param id
	 *            流媒体服务器ID
	 * @param standardNumber
	 *            流媒体服务器标准号
	 * @param name
	 *            流媒体服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param ccsId
	 *            注册的通信服务器ID
	 * @param configValue
	 *            配置信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:40:51
	 */
	@LogMethod(targetType = "Mss", operationType = "update", name = "修改流媒体服务器", code = "2091")
	public void updateMss(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String ccsId, String configValue)
			throws BusinessException;

	/**
	 * 删除流媒体服务器
	 * 
	 * @param id
	 *            流媒体服务器ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:42:34
	 */
	@LogMethod(targetType = "Mss", operationType = "delete", name = "删除流媒体服务器", code = "2092")
	public void deleteMss(@LogParam("id") String id) throws BusinessException;

	/**
	 * 列表查询流媒体服务器
	 * 
	 * @return 流媒体服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:59:47
	 */
	public List<ListMssVO> listMss() throws BusinessException;

	/**
	 * 查询所有的平台服务器列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param name
	 *            服务器名称
	 * @param type
	 *            服务器类型
	 * @return 平台服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:52:41
	 */
	public ListPlatformServerDTO listPlatformServer(Integer startIndex,
			Integer limit, String name, String type) throws BusinessException;

	/**
	 * 创建通信服务器
	 * 
	 * @param standardNumber
	 *            通信服务器标准号
	 * @param name
	 *            通信服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @param forward
	 *            1 路由给设备ccs处理, 0直接走mss
	 * @return 创建成功后生成的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:05:55
	 */
	@LogMethod(targetType = "Ccs", operationType = "create", name = "创建通信服务器", code = "2080")
	public String createCcs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port, Short forward)
			throws BusinessException;

	/**
	 * 创建中心存储服务器
	 * 
	 * @param standardNumber
	 *            中心存储服务器标准号
	 * @param name
	 *            中心存储服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @return 创建成功后的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:17:13
	 */
	@LogMethod(targetType = "Crs", operationType = "create", name = "创建中心存储服务器", code = "2140")
	public String createCrs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue)
			throws BusinessException;

	/**
	 * 创建协转服务器
	 * 
	 * @param standardNumber
	 *            协转服务器标准号
	 * @param name
	 *            协转服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @return 创建成功后的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:18:53
	 */
	@LogMethod(targetType = "Pts", operationType = "create", name = "创建协转服务器", code = "2110")
	public String createPts(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException;

	/**
	 * 创建电视墙服务器
	 * 
	 * @param standardNumber
	 *            电视墙服务器标准号
	 * @param name
	 *            电视墙服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @return 创建成功后的ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:21:23
	 */
	@LogMethod(targetType = "Dws", operationType = "create", name = "创建电视墙服务器", code = "2100")
	public String createDws(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port)
			throws BusinessException;

	/**
	 * 根据ID和类型查询平台服务器
	 * 
	 * @param id
	 *            平台服务器ID
	 * @param type
	 *            服务器类型.3-通信服务器,4-中心存储服务器,5-流媒体服务器,6-协转服务器,7-显示墙服务器
	 * @return 平台服务器详细信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:35:29
	 */
	public PlatformServerVO getPlatformServer(String id, Integer type)
			throws BusinessException;

	/**
	 * 修改通信服务器
	 * 
	 * @param id
	 *            通信服务器ID
	 * @param standardNumber
	 *            通信服务器标准号
	 * @param name
	 *            通信服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            通信端口
	 * @param forward
	 *            1 路由给设备ccs处理, 0直接走mss
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:06:54
	 */
	@LogMethod(targetType = "Ccs", operationType = "update", name = "修改通信服务器", code = "2081")
	public void updateCcs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port, Short forward)
			throws BusinessException;

	/**
	 * 删除通信服务器
	 * 
	 * @param id
	 *            通信服务器ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:11:48
	 */
	@LogMethod(targetType = "Ccs", operationType = "delete", name = "删除通信服务器", code = "2082")
	public void deleteCcs(@LogParam("id") String id) throws BusinessException;

	/**
	 * 查询通信服务器列表
	 * 
	 * @return 通信服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午6:20:13
	 */
	public List<ListCcsVO> listCcs() throws BusinessException;

	/**
	 * 修改中心存储服务器
	 * 
	 * @param id
	 *            中心存储服务器ID
	 * @param standardNumber
	 *            中心存储服务器标准号
	 * @param name
	 *            中心存储服务器名称
	 * @param ccsId
	 *            通信服务器ID
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:01:14
	 */
	@LogMethod(targetType = "Crs", operationType = "update", name = "修改中心存储服务器", code = "2141")
	public void updateCrs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException;

	/**
	 * 删除中心存储服务器
	 * 
	 * @param id
	 *            中心存储服务器ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:03:09
	 */
	@LogMethod(targetType = "Crs", operationType = "delete", name = "删除中心存储服务器", code = "2142")
	public void deleteCrs(@LogParam("id") String id) throws BusinessException;

	/**
	 * 查询中心存储服务器列表
	 * 
	 * @return 中心存储服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:09:31
	 */
	public List<ListCrsVO> listCrs() throws BusinessException;

	/**
	 * 修改电视墙服务器
	 * 
	 * @param id
	 *            电视墙服务器ID
	 * @param standardNumber
	 *            电视墙服务器标准号
	 * @param name
	 *            电视墙服务器名称
	 * @param ccsId
	 *            通信服务器ID
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:58:51
	 */
	@LogMethod(targetType = "Dws", operationType = "update", name = "修改电视墙服务器", code = "2101")
	public void updateDws(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException;

	/**
	 * 删除电视墙服务器
	 * 
	 * @param id
	 *            电视墙服务器ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:01:08
	 */
	@LogMethod(targetType = "Dws", operationType = "delete", name = "删除电视墙服务器", code = "2102")
	public void deleteDws(@LogParam("id") String id) throws BusinessException;

	/**
	 * 查询电视墙服务器列表
	 * 
	 * @return 电视墙服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:08:28
	 */
	public List<ListDwsVO> listDws() throws BusinessException;

	/**
	 * 修改协转服务器
	 * 
	 * @param id
	 *            协转服务器ID
	 * @param standardNumber
	 *            协转服务器标准号
	 * @param name
	 *            协转服务器名称
	 * @param ccsId
	 *            通信服务器ID
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:18:54
	 */
	@LogMethod(targetType = "Pts", operationType = "update", name = "修改协转服务器", code = "2111")
	public void updatePts(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException;

	/**
	 * 删除协转服务器
	 * 
	 * @param id
	 *            删除协转服务器ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:21:29
	 */
	@LogMethod(targetType = "Pts", operationType = "delete", name = "删除协转服务器", code = "2112")
	public void deletePts(@LogParam("id") String id) throws BusinessException;

	/**
	 * 查询协转服务器列表
	 * 
	 * @return 协转服务器列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:26:29
	 */
	public List<ListPtsVO> listPts() throws BusinessException;

	/**
	 * 
	 * 根据standardNumber查询路由资源信息
	 * 
	 * @param standardNumber
	 *            标准号
	 * @return Element
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:17:15
	 */
	public Element getResourceRouteInfo(String standardNumber);

	/**
	 * 根据ccs标准号获取ccs对象
	 * 
	 * @param standardNumber
	 *            ccs标准号
	 * @return ccs对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:32:06
	 */
	public Ccs getCcsByStandardNumber(String standardNumber);

	/**
	 * 
	 * 查询服务器列表计数
	 * 
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:43:09
	 */
	public Integer platformServerTotalCount();

	/**
	 * 获取设备的最后更新时间
	 * 
	 * @return 设备的最后更新时间
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:30:02
	 */
	public DeviceUpdateListener getDeviceUpdateTime();

	/**
	 * 获取CCS管辖的用户会话列表
	 * 
	 * @param standardNumber
	 *            CCS的标准号
	 * @return 用户会话列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午8:54:00
	 */
	public List<CcsUserSessionVO> listCcsUserSession(String standardNumber);

	/**
	 * 服务器注册
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param type
	 *            服务器
	 * @param ccsId
	 *            通信服务器ID
	 * @param lanIp
	 *            注册的服务器的内网IP
	 * @return 注册成功的会话
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:57:52
	 */
	public UserSession serverRegister(String standardNumber, String type,
			String ccsId, String lanIp) throws BusinessException;

	/**
	 * CCS更新配置信息
	 * 
	 * @param ccsId
	 *            CCS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的CCS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午2:26:50
	 */
	public Ccs ccsUpdateConfig(String ccsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * PTS更新配置信息
	 * 
	 * @param ptsId
	 *            PTS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的PTS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午2:33:12
	 */
	public Pts ptsUpdateConfig(String ptsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * MSS更新配置信息
	 * 
	 * @param mssId
	 *            MSS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @param videoPort
	 *            视频连接端口
	 * @return 更新后的MSS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午2:36:12
	 */
	public Mss mssUpdateConfig(String mssId, String lanIp, String sipPort,
			String telnetPort, String videoPort) throws BusinessException;

	/**
	 * DWS更新配置信息
	 * 
	 * @param dwsId
	 *            DWS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的DWS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午2:42:44
	 */
	public Dws dwsUpdateConfig(String dwsId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * DAS更新配置信息
	 * 
	 * @param dasId
	 *            DAS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的DAS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 上午11:14:04
	 */
	public Das dasUpdateConfig(String dasId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * CRS更新配置信息
	 * 
	 * @param crsId
	 *            CRS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @param videoPort
	 *            视频监听端口
	 * @return CRS配置信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-3-10 下午3:52:54
	 */
	public CrsUpdateConfigVO crsUpdateConfig(String crsId, String lanIp,
			String sipPort, String telnetPort, String videoPort)
			throws BusinessException;

	/**
	 * 
	 * 创建数据采集服务器
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            数采名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @return string
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:21:15
	 */
	@LogMethod(targetType = "Das", operationType = "create", name = "创建数采服务器", code = "2290")
	public String createDas(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port);

	/**
	 * 
	 * 更新数据采集服务器
	 * 
	 * @param id
	 *            数采id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            数采名称
	 * @param location
	 *            安装位置
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param port
	 *            端口
	 * @param configValue
	 *            配置信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:54:34
	 */
	@LogMethod(targetType = "Das", operationType = "update", name = "修改数采服务器", code = "2291")
	public void updateDas(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue);

	/**
	 * 
	 * 删除数据采集服务器
	 * 
	 * @param id
	 *            数采id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:05:27
	 */
	@LogMethod(targetType = "Das", operationType = "delete", name = "删除数采服务器", code = "2292")
	public void deleteDas(@LogParam("id") String id);

	/**
	 * 
	 * 列表查询数据采集服务器
	 * 
	 * @return 数据采集服务器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:14:33
	 */
	public List<ListDasVO> listDas();

	/**
	 * 
	 * 创建事件服务器
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            事件服务器名称
	 * @param location
	 *            安装位置
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param configValue
	 *            配置信息
	 * @param port
	 *            端口
	 * @return 事件服务器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:03:28
	 */
	@LogMethod(targetType = "Ens", operationType = "create", name = "创建事件服务器", code = "2300")
	public String createEns(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String configValue, String port);

	/**
	 * 
	 * 更新事件服务器
	 * 
	 * @param id
	 *            事件服务器id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            事件服务器名称
	 * @param location
	 *            安装位置
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param port
	 *            端口
	 * @param ccsId
	 *            通信服务器id
	 * @param configValue
	 *            配置信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:14:59
	 */
	@LogMethod(targetType = "Ens", operationType = "update", name = "修改事件服务器", code = "2301")
	public void updateEns(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String ccsId, String configValue);

	/**
	 * 
	 * 删除事件服务器
	 * 
	 * @param id
	 *            事件服务器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:20:12
	 */
	@LogMethod(targetType = "Ens", operationType = "delete", name = "删除事件服务器", code = "2302")
	public void deleteEns(@LogParam("id") String id);

	/**
	 * 
	 * 查询事件服务器列表
	 * 
	 * @return 事件服务器集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:27:35
	 */
	public List<ListEnsVO> listEns();

	/**
	 * 匹配DAS配置的设备信息，决定采集设备的注册情况
	 * 
	 * @param dasId
	 *            采集服务器ID
	 * @param devices
	 *            采集服务器上配置的设备列表
	 * @return 能够匹配上的设备列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-11 下午1:43:35
	 */
	public List<Element> mapDevices(String dasId, List<Element> devices)
			throws BusinessException;

	/**
	 * 将本机硬件信息写入数据库
	 * 
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-10 上午11:20:25
	 */
	public void writeHardwareInfoToDB() throws BusinessException;

	/**
	 * 获取平台硬件信息，合并多个中心服务器的硬件信息返回
	 * 
	 * @return 合并多个中心服务器的硬件信息返回
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-13 下午4:02:41
	 */
	public String getPlatformHardware() throws BusinessException;

	/**
	 * 
	 * 查询CRS存储策略列表
	 * 
	 * @param crsId
	 *            存储服务器id
	 * @param start
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return Element
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:24:39
	 */
	public Element listCRSPlan(String crsId, Integer start, Integer limit);

	/**
	 * 创建GIS服务器
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            服务器名称
	 * @param location
	 *            位置
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            端口
	 * @param configValue
	 *            WFS值
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:31:07
	 */
	@LogMethod(targetType = "Gis", operationType = "create", name = "创建地图服务器", code = "2380")
	public String createGis(String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue);

	/**
	 * 修改Gis服务器
	 * 
	 * @param id
	 *            gis服务器id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            位置
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            端口
	 * @param configValue
	 *            wfs值
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:44:45
	 */
	@LogMethod(targetType = "Gis", operationType = "update", name = "修改地图服务器", code = "2381")
	public void updateGis(String id, String standardNumber, String name,
			String location, String lanIp, String wanIp, String port,
			String configValue);

	/**
	 * 查询gis列表
	 * 
	 * @return gis列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:52:38
	 */
	public List<ListGisVO> listGis();

	/**
	 * 删除gis服务器
	 * 
	 * @param id
	 *            gis id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:57:58
	 */
	@LogMethod(targetType = "Gis", operationType = "delete", name = "删除地图服务器", code = "2383")
	public void deleteGis(String id);

	/**
	 * 
	 * 创建录像转发服务器
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            地址
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param port
	 *            端口
	 * @param configValue
	 *            配置值
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:47:41
	 */
	@LogMethod(targetType = "Rms", operationType = "create", name = "创建录像转发服务器", code = "2153")
	public String createRms(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue);

	/**
	 * 
	 * 更新录像转发服务器
	 * 
	 * @param id
	 *            服务器id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            地址
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param configValue
	 *            配置值
	 * @param port
	 *            端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午9:20:33
	 */
	@LogMethod(targetType = "Rms", operationType = "update", name = "修改录像转发服务器", code = "2154")
	public void updateRms(@LogParam("id") String id, String standardNumber,
			String name, String location, String lanIp, String wanIp,
			String configValue, String port);

	/**
	 * 
	 * 删除录像转发服务器
	 * 
	 * @param id
	 *            服务器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午9:26:18
	 */
	@LogMethod(targetType = "Rms", operationType = "delete", name = "删除录像转发服务器", code = "2155")
	public void deleteRms(@LogParam("id") String id);

	/**
	 * 
	 * 查询录像转发服务器列表
	 * 
	 * @return list
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午9:40:28
	 */
	public List<ListRmsVO> listRms();

	/**
	 * DWS更新配置信息
	 * 
	 * @param dwsId
	 *            DWS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的DWS对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午9:56:11
	 */
	public Rms rmsUpdateConfig(String id, String lanIp, String sipPort,
			String telnetPort, String videoPort) throws BusinessException;

	/**
	 * 
	 * 查询在线服务器列表
	 * 
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param type
	 *            服务器类型
	 * @return 在线服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:55:10
	 */
	public ListOnlineUsersDTO listOnlinePlatformServer(Integer startIndex,
			Integer limit, String type);

	/**
	 * ENS更新配置信息
	 * 
	 * @param ensId
	 *            ENS的ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的ENS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-15 下午2:42:44
	 */
	public Ens ensUpdateConfig(String ensId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * RSS更新配置信息
	 * 
	 * @param rssId
	 *            状态服务器ID
	 * @param lanIp
	 *            内网IP
	 * @param sipPort
	 *            SIP通信端口
	 * @param telnetPort
	 *            telnet连接配置端口
	 * @return 更新后的RSS对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-27 上午11:05:12
	 */
	public Rss rssUpdateConfig(String rssId, String lanIp, String sipPort,
			String telnetPort) throws BusinessException;

	/**
	 * 
	 * 创建状态服务器
	 * 
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            端口
	 * @param configValue
	 *            配置值
	 * @return id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-11 上午9:58:18
	 */
	@LogMethod(targetType = "Rss", operationType = "create", name = "创建状态服务器", code = "2470")
	public String createRss(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue);

	/**
	 * 
	 * 更新录像转发服务器
	 * 
	 * @param id
	 *            服务器id
	 * @param standardNumber
	 *            标准号
	 * @param name
	 *            名称
	 * @param location
	 *            地址
	 * @param lanIp
	 *            内网ip
	 * @param wanIp
	 *            外网ip
	 * @param configValue
	 *            配置值
	 * @param port
	 *            端口
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-11 上午10:30:57
	 */
	@LogMethod(targetType = "Rss", operationType = "update", name = "修改状态服务器", code = "2471")
	public void updateRss(@LogParam("id") String id, String standardNumber,
			String name, String location, String lanIp, String wanIp,
			String configValue, String port);

	/**
	 * 
	 * 删除状态服务器
	 * 
	 * @param id
	 *            服务器id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-11 上午10:34:23
	 */
	@LogMethod(targetType = "Rss", operationType = "delete", name = "删除状态服务器", code = "2472")
	public void deleteRss(@LogParam("id") String id);

	/**
	 * 
	 * 查询状态服务器列表
	 * 
	 * @return 状态服务器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:39:01
	 */
	public List<ListRssVO> listRss();

	/**
	 * 获取更新Crs存储计划时间
	 * 
	 * @return long
	 */
	public Long getCrsDeviceUpdateTime();
	
	/**
	 * 创建录音服务器
	 * 
	 * @param standardNumber
	 *            录音存储服务器标准号
	 * @param name
	 *            录音存储服务器名称
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @return 创建成功后的ID
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午3:17:13
	 */
	@LogMethod(targetType = "Srs", operationType = "create", name = "创建录音服务器", code = "2480")
	public String createSrs(String standardNumber,
			@LogParam("name") String name, String location, String lanIp,
			String wanIp, String port, String configValue)
			throws BusinessException;
	
	/**
	 * 查询录音服务器列表
	 * 
	 * @return 录音服务器列表
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午10:09:31
	 */
	public List<ListSrsVO> listSrs() throws BusinessException;
	
	/**
	 * 修改录音服务器
	 * 
	 * @param id
	 *            录音服务器ID
	 * @param standardNumber
	 *            录音服务器标准号
	 * @param name
	 *            录音服务器名称
	 * @param ccsId
	 *            通信服务器ID
	 * @param location
	 *            安装地址
	 * @param lanIp
	 *            内网IP
	 * @param wanIp
	 *            外网IP
	 * @param port
	 *            通信端口
	 * @param configValue
	 *            配置信息
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午10:01:14
	 */
	@LogMethod(targetType = "Srs", operationType = "update", name = "修改录音服务器", code = "2482")
	public void updateSrs(@LogParam("id") String id, String standardNumber,
			@LogParam("name") String name, String ccsId, String location,
			String lanIp, String wanIp, String port, String configValue)
			throws BusinessException;
	
	/**
	 * 删除录音服务器
	 * 
	 * @param id
	 *            录音服务器ID
	 * @throws BusinessException
	 * @author sjt
	 *         <p />
	 *         Create at 2015 下午5:11:48
	 */
	@LogMethod(targetType = "Srs", operationType = "delete", name = "删除录音服务器", code = "2483")
	public void deleteSrs(@LogParam("id") String id) throws BusinessException;
}
