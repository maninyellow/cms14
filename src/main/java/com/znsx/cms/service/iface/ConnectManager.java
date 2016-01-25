package com.znsx.cms.service.iface;

import java.util.Collection;
import java.util.List;

import org.jdom.Element;

import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 平台互联业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-16 下午1:52:21
 */
public interface ConnectManager extends BaseManager {

	public static final String REGION = "platformCache";

	/**
	 * 获取下级平台机构设备树
	 * 
	 * @param id
	 *            下级平台ID
	 * @param items
	 *            平台下的所有资源集合
	 * @param isRec
	 *            是否递归，true-递归，false-不递归
	 * @return 指定平台的机构设备树
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-16 下午3:34:07
	 */
	public Element treeSubPlatform(String id, List<SubPlatformResource> items,
			boolean isRec) throws BusinessException;

	/**
	 * 查询指定下级平台的全部资源列表
	 * 
	 * @param platformId
	 *            平台ID
	 * @return 平台的全部资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-16 下午5:17:10
	 */
	public Collection<SubPlatformResource> listPlatformResource(
			String platformId, String userId);

	/**
	 * 查询下级平台列表
	 * 
	 * @return 下级平台列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-17 上午9:06:45
	 */
	public List<SubPlatform> listSubPlatform();

	/**
	 * 本级平台向上级平台注册
	 * 
	 * @param address
	 *            上级平台地址信息
	 * @return 成功失败编码。200-成功，其他-失败
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-17 下午2:58:55
	 */
	public String sendRegister(String address) throws BusinessException;

	/**
	 * 上级平台接收来自下级平台的注册
	 * 
	 * @param standardNumber
	 *            下级平台标准号
	 * @param name
	 *            下级平台名称
	 * @param ip
	 *            下级平台通信IP
	 * @param port
	 *            下级平台通信端口
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-17 下午5:16:12
	 */
	public void acceptRegister(String standardNumber, String name, String ip,
			Integer port) throws BusinessException;

	/**
	 * 下级平台资源推送
	 * 
	 * @param organ
	 *            机构
	 * @param address
	 *            上级平台接收地址
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 上午10:58:08
	 */
	public String pushResource(String address) throws BusinessException;

	/**
	 * 上级平台接收下级推送上来的资源树
	 * 
	 * @param node
	 *            XML型式的资源树
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 下午4:16:38
	 */
	public void acceptResource(Element node) throws BusinessException;

	/**
	 * 接收下级上来的资源数据前首先删除旧的资源数据
	 * 
	 * @param standardNumber
	 *            下级平台的标准号
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-23 下午3:44:36
	 */
	public void deleteByPlatform(String standardNumber)
			throws BusinessException;

	/**
	 * 查询下级平台资源路由信息
	 * 
	 * @param resource
	 *            资源对象
	 * @return {@code <Route LanIp="192.168.1.1" Port="5060" />}
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-24 上午11:20:57
	 */
	public Element getResourceRoute(SubPlatformResource resource);

	/**
	 * 根据标准号查询下级平台资源
	 * 
	 * @param standardNumber
	 *            资源标准号
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-24 下午1:44:06
	 */
	public SubPlatformResource getSubPlatformResourceBySN(String standardNumber);

	/**
	 * 根据ID获取下级平台对象
	 * 
	 * @param id
	 *            平台ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-29 下午3:25:02
	 */
	public SubPlatform getSubPlatform(String id);

	/**
	 * 保存下级平台上报数据
	 * 
	 * @param rows
	 *            数据列表
	 * @param organSN
	 *            上报平台的标准号
	 * @param tableName
	 *            存入数据表的名称,参见
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_VD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_CD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_CMS}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_COVI}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_LOLI}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_NOD}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_WS}
	 *            ,
	 *            {@link com.znsx.cms.service.common.TypeDefinition#TABLE_NAME_WST}
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-26 下午2:44:15
	 */
	public void saveReportData(List<Element> rows, String organSN,
			String tableName) throws BusinessException;

	/**
	 * 28059资源目录上报处理接口
	 * 
	 * @param parent
	 *            父节点
	 * @param items
	 *            子节点列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-23 上午10:59:28
	 */
	public void pushResource28059(SubPlatformResource parent,
			List<SubPlatformResource> items) throws BusinessException;

	/**
	 * 28181标准目录查询
	 * 
	 * @param deviceId
	 *            根节点编码
	 * @param 级联查询下级
	 *            ，0-不级联，1-级联
	 * @return 资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-26 下午7:19:57
	 */
	public Element catalog28181(String deviceId, boolean cascade);

	/**
	 * 更新GB28181标准推送上来的资源信息
	 * 
	 * @param items
	 *            资源列表
	 * @param rootSn
	 *            根节点SN
	 * @param gatewayId
	 *            网关ID
	 * @param name
	 *            根节点名称 ，不存在且新增时，需要手动修改sv_sub_platform_resource表中的name属性
	 * @param model
	 *            设备型号
	 * @param owner
	 *            设备归属
	 * @param parental
	 *            是否有子设备：1-有，0-没有
	 * @param civilCode
	 *            行政区域
	 * @param address
	 *            安装地址
	 * @param registerWay
	 *            注册方式
	 * @param secrecy
	 *            保密属性
	 * @param manufacturer
	 *            设备厂商
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-31 上午10:06:38
	 */
	public void updateCatalog28181(List<Element> items, String rootSn,
			String gatewayId, String name, String model, String owner,
			Integer parental, String civilCode, String address,
			Integer registerWay, Integer secrecy, String manufacturer);

	/**
	 * 28181标准,根据用户查询目录
	 * 
	 * @param useSn
	 *            用户编号
	 * @return 资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-24 下午4:54:12
	 */
	public Element catalog28181ByUser(String userSn);

	/**
	 * 获取所有的下级设备的路由信息
	 * 
	 * @return 所有的下级设备的路由信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-27 下午2:06:27
	 */
	public List<Element> listSubDevice();

	/**
	 * 查询所有本地网关管理的设备
	 * 
	 * @return 所有本地网关管理的设备
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-28 上午11:42:29
	 */
	public List<Element> listGatewayDevice();

	/**
	 * 推送设备状态
	 * 
	 * @return 状态码
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-8-11 下午16:47:29
	 */
	public String pushDeviceStatus();

	/**
	 * 更新设备状态心跳时间
	 * 
	 * @param sns
	 *            机构sn
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-8-11 下午18:00:29
	 * 
	 */
	public void updateSubPlatform(List<String> sns);

	/**
	 * 更行下级平台设备状态
	 * 
	 * @param organSN
	 *            机构sn
	 * @param deviceOnlineReals
	 *            在线设备
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-8-12 上午10:07:29
	 */
	public void updateLowerDeviceStatus(String organSN,
			List<DeviceOnlineReal> deviceOnlineReals);

	/**
	 * 检查平台心跳
	 */
	public void checkPlatform();
}
