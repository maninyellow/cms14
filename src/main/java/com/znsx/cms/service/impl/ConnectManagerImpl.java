package com.znsx.cms.service.impl;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineRealDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.dao.RoleResourcePermissionDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.SubPlatformDAO;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.DeviceAlarmReal;
import com.znsx.cms.persistent.model.DeviceOnline;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.persistent.model.RoleResourcePermissionSubResource;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.persistent.model.SubPlatform;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.CameraComparator;
import com.znsx.cms.service.comparator.SubPlatformResourceComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 平台互联业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-16 下午1:53:38
 */
public class ConnectManagerImpl extends BaseManagerImpl implements
		ConnectManager {
	@Autowired
	private SubPlatformDAO subPlatformDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;
	@Autowired
	private CcsDAO ccsDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private RoleResourcePermissionDAO roleResourcePermissionDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private StandardNumberDAO snDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DeviceOnlineRealDAO deviceOnlineRealDAO;
	@Autowired
	private DeviceOnlineDAO deviceOnlineDAO;

	/**
	 * 用户会话过期时间毫秒数，默认4分钟
	 */
	public static final long SESSION_EXPIRE_TIME = 240000L;

	// 平台间数据上报待转换的日期格式
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public Element treeSubPlatform(String id, List<SubPlatformResource> items,
			boolean isRec) throws BusinessException {
		// 自身节点
		Element node = new Element("Node");

		// 设备节点
		Element channelList = new Element("ChannelList");
		node.addContent(channelList);

		// 子机构节点
		Element subNodes = new Element("SubNodes");
		node.addContent(subNodes);

		for (SubPlatformResource item : items) {
			// 自身信息
			if (item.getId().equals(id)) {
				node.setAttribute("Id", id.toString());
				node.setAttribute("StandardNumber", StringUtils.isBlank(item
						.getStandardNumber()) ? "" : item.getStandardNumber());
				node.setAttribute("Name", item.getName());
			}
			// 下级资源加入
			else if ((item.getParent() != null)
					&& (item.getParent().getId().equals(id))) {
				// 加入设备信息
				if (item.getType().equals(TypeDefinition.SUBTYPE_CAMERA_BALL)
						|| item.getType().equals(
								TypeDefinition.SUBTYPE_CAMERA_DEFAULT)
						|| item.getType().equals(
								TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT)
						|| item.getType().equals(
								TypeDefinition.GB28181_CAMERA_BALL)
						|| item.getType().equals(
								TypeDefinition.GB28181_CAMERA_BALL_BOLT)
						|| item.getType().equals(
								TypeDefinition.GB28181_CAMERA_BOLT)
						|| item.getType().equals(
								TypeDefinition.GB28181_CAMERA_HALF_BALL)) {
					Element channel = new Element("Channel");
					channel.setAttribute("Id", item.getId().toString());
					channel.setAttribute("StandardNumber",
							StringUtils.isBlank(item.getStandardNumber()) ? ""
									: item.getStandardNumber());
					channel.setAttribute("Name",
							item.getName() != null ? item.getName() : "");
					channel.setAttribute("SubType",
							translatePtzType(item.getType()));
					channel.setAttribute(
							"Auth",
							item.getAuth() != null ? translateAuth(item
									.getAuth()) : "");
					channel.setAttribute("CcsSN", MyStringUtil
							.object2StringNotNull(Configuration.getInstance()
									.getProperties("gateway_ccs_sn")));
					channelList.addContent(channel);
				}
				// 加入子机构节点
				else {
					// 子机构递归构建
					if (isRec) {
						Element subNode = treeSubPlatform(item.getId(), items,
								isRec);
						subNodes.addContent(subNode);
					}
					// 子机构列表构建
					else {
						Element subNode = new Element("Node");
						subNode.setAttribute("Id", item.getId().toString());
						subNode.setAttribute(
								"StandardNumber",
								StringUtils.isBlank(item.getStandardNumber()) ? ""
										: item.getStandardNumber());
						subNode.setAttribute("Name",
								item.getName() != null ? item.getName() : "");
						subNodes.addContent(subNode);
					}
				}
			}
		}

		return node;
	}

	@Override
	public Collection<SubPlatformResource> listPlatformResource(
			String platformId, String userId) {
		Map<String, SubPlatformResource> resourceMap = subPlatformResourceDAO
				.mapPlatformResourceById(platformId);
		// 存放所有权限资源
		Map<String, SubPlatformResource> map = new HashMap<String, SubPlatformResource>();

		// 处理用户权限
		User user = userDAO.findById(userId);
		Set<Role> roles = user.getRoles();
		// 判断是否拥有系统角色，系统角色具有机构下的所有的设备的权限，自定义角色只拥有部分设备权限
		String auth = "";
		// 是否管理员角色
		boolean isAdmin = false;
		// 该用户的自定义角色ID列表
		List<String> roleIds = new ArrayList<String>();
		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)) {
				auth = "1,2,4";
				isAdmin = true;
				break;
			}
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)) {
				if (StringUtils.isBlank(auth)) {
					auth = "1";
				}
			} else if (role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				auth = "1,2";
			}
			// 加入到自定义角色列表中
			else {
				roleIds.add(role.getId());
			}
		}
		// 如果存在管理员角色
		if (isAdmin) {
			for (SubPlatformResource resource : resourceMap.values()) {
				updateCcsSn(resource);
				resource.setAuth(auth);
				map.put(resource.getId(), resource);
			}
		}
		// 如果不存在任何系统角色
		else if (StringUtils.isBlank(auth)) {
			// 查询自定义角色权限设备
			if (roleIds.size() > 0) {
				List<RoleResourcePermission> authDevices = roleResourcePermissionDAO
						.listByRoleIds(roleIds,
								TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "");
				for (RoleResourcePermission authDevice : authDevices) {
					if (authDevice instanceof RoleResourcePermissionSubResource) {
						String resourceId = ((RoleResourcePermissionSubResource) authDevice)
								.getSubResource().getId();
						// 返回集合中已经添加过该设备,合并权限
						SubPlatformResource resource = map.get(resourceId);
						if (null != resource) {
							resource.setAuth(combineAuth(resource.getAuth(),
									authDevice.getPrivilege()));
						}
						// 添加新的
						else {
							resource = resourceMap.get(resourceId);
							if (null != resource) {
								resource.setAuth(authDevice.getPrivilege());
								updateCcsSn(resource);
								map.put(resourceId, resource);
							}
						}
					}
				}
			}
		}
		// 存在系统角色,同时存在自定义角色
		else if (StringUtils.isNotBlank(auth) && roleIds.size() > 0) {
			// 查询自定义角色权限设备
			List<RoleResourcePermission> authDevices = roleResourcePermissionDAO
					.listByRoleIds(roleIds,
							TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "");
			// 将自定角色的设备加入到map中
			for (RoleResourcePermission authDevice : authDevices) {
				String resourceId = ((RoleResourcePermissionSubResource) authDevice)
						.getSubResource().getId();
				// 返回集合中已经添加过该设备,合并权限
				SubPlatformResource resource = map.get(resourceId);
				if (null != resource) {
					resource.setAuth(combineAuth(resource.getAuth(),
							authDevice.getPrivilege()));
				}
				// 添加新的
				else {
					resource = resourceMap.get(resourceId);
					if (null != resource) {
						resource.setAuth(authDevice.getPrivilege());
						updateCcsSn(resource);
						map.put(resourceId, resource);
					}
				}
			}
			// 所有下级平台资源至少具有系统角色给予的权限
			for (SubPlatformResource resource : resourceMap.values()) {
				SubPlatformResource temp = map.get(resource.getId());
				// 合并自定义角色和系统角色权限
				if (null != temp) {
					temp.setAuth(combineAuth(auth, temp.getAuth()));
				}
				// 添加新的
				else {
					resource.setAuth(auth);
					updateCcsSn(resource);
					map.put(resource.getId(), resource);
				}
			}
		}
		// 只存在系统角色
		else {
			// 下级平台所有设备都设置为auth权限
			for (SubPlatformResource resource : resourceMap.values()) {
				// 添加新的
				resource.setAuth(auth);
				updateCcsSn(resource);
				map.put(resource.getId(), resource);
			}
		}

		return map.values();
	}

	@Override
	public List<SubPlatform> listSubPlatform() {
		return subPlatformDAO.findAll();
	}

	@Override
	public String sendRegister(String address) throws BusinessException {
		String gatewaySN = Configuration.getInstance().loadProperties(
				"gateway_ccs_sn");
		// 获取信令网关IP和端口
		Ccs gateway = ccsDAO.getByStandardNumber(gatewaySN);
		String ip = gateway.getWanIp();
		String port = gateway.getPort();
		// 获取根机构SN和名称
		Organ organ = organDAO.getRootOrgan();
		String standardNumber = organ.getStandardNumber();
		String name = organ.getName();

		// 发起请求
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(address + "register.xml");
		String body = registerBody(standardNumber, name, ip, port);
		try {
			RequestEntity entity = new StringRequestEntity(body,
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			System.out.println(ElementUtil.doc2String(doc));
			return doc.getRootElement().getAttributeValue("Code");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Register to " + address + " failed !");
		} finally {
			method.releaseConnection();
		}
	}

	private String registerBody(String standardNumber, String name, String ip,
			String port) {
		Document doc = new Document();
		Element root = new Element("Request");
		root.setAttribute("Method", "Register");
		root.setAttribute("Cmd", "1027");
		doc.setRootElement(root);

		Element sne = new Element("StandardNumber");
		sne.setText(standardNumber);
		root.addContent(sne);

		Element ne = new Element("Name");
		ne.setText(name);
		root.addContent(ne);

		Element ipe = new Element("IP");
		ipe.setText(ip);
		root.addContent(ipe);

		Element pe = new Element("Port");
		pe.setText(port);
		root.addContent(pe);

		String body = ElementUtil.doc2String(doc);
		return body;
	}

	@Override
	public void acceptRegister(String standardNumber, String name, String ip,
			Integer port) throws BusinessException {
		SubPlatform platform = subPlatformDAO
				.getByStandardNumber(standardNumber);
		// 不存在新增一条记录
		if (null == platform) {
			platform = new SubPlatform();
			platform.setName(name);
			platform.setSipIp(ip);
			platform.setSipPort(port);
			platform.setStandardNumber(standardNumber);
			platform.setUpdateTime(System.currentTimeMillis());
			subPlatformDAO.save(platform);
		}
		// 存在修改更新时间
		else {
			platform.setName(name);
			platform.setSipIp(ip);
			platform.setSipPort(port);
			platform.setUpdateTime(System.currentTimeMillis());
		}
	}

	@Override
	public String pushResource(String address) throws BusinessException {
		Document doc = new Document();
		Element root = new Element("Request");
		root.setAttribute("Method", "Push_Resource");
		root.setAttribute("Cmd", "1028");
		doc.setRootElement(root);

		Element node = getOrganElement();
		root.addContent(node);
		// 发送资源推送请求
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(address + "push_resource.xml");
		String body = ElementUtil.doc2String(doc);
		System.out.println(body);
		try {
			RequestEntity entity = new StringRequestEntity(body,
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);
			// 返回
			SAXBuilder builder = new SAXBuilder();
			Document respDoc = builder.build(method.getResponseBodyAsStream());
			String code = respDoc.getRootElement().getAttributeValue("Code");
			if (!ErrorCode.SUCCESS.equals(code)) {
				System.out.println(ElementUtil.doc2String(respDoc));
			}
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Push resource to " + address + " failed !\r\n" + body);
		} finally {
			method.releaseConnection();
		}
	}

	/**
	 * 获取平台的资源树
	 * 
	 * @return 该机构下的资源
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 上午11:13:17
	 */
	private Element getOrganElement() {
		Organ organ = organDAO.getRootOrgan();
		// 获取所有子机构
		List<Organ> organs = organDAO.listOrganById(organ.getId());

		// 自身信息
		Element parent = new Element("Node");
		parent.setAttribute("Id", organ.getId());
		parent.setAttribute("StandardNumber",
				organ.getStandardNumber() != null ? organ.getStandardNumber()
						: "");
		parent.setAttribute("Name", organ.getName() != null ? organ.getName()
				: "");

		// 设备信息
		Element channelList = new Element("ChannelList");
		parent.addContent(channelList);
		// 获取上级平台权限设备
		List<AuthCameraVO> devices = listPlatformAuthCamera();
		for (AuthCameraVO camera : devices) {
			if (camera.getOrganId().equals(organ.getId())) {
				Element channel = new Element("Channel");
				channel.setAttribute(
						"StandardNumber",
						camera.getStandardNumber() != null ? camera
								.getStandardNumber() : "");
				channel.setAttribute("Name",
						camera.getName() != null ? camera.getName() : "");
				channel.setAttribute("SubType",
						camera.getSubType() != null ? camera.getSubType() : "");
				channel.setAttribute(
						"Auth",
						translateAuth(camera.getAuth()) != null ? translateAuth(camera
								.getAuth()) : "");
				channelList.addContent(channel);
			}
		}

		// 子节点
		Element subNodes = new Element("SubNodes");
		parent.addContent(subNodes);

		// 构建机构树状关系
		parent = treeOrgan(organs, devices, parent);
		// 移除不需要的ID属性
		parent.removeAttribute("Id");

		// 多级平台互联情况下，加入下级平台数据推送，追加在lower_platform_root配置的机构下方
		String lowerRootSn = Configuration.getInstance().loadProperties(
				"lower_platform_root");
		// 如果没有lower_platform_root的配置，加在根机构下方
		if (StringUtils.isBlank(lowerRootSn)) {
			getSubPlatformNode(null, subNodes);
		} else {
			if (lowerRootSn.equals(organ.getStandardNumber())) {
				SubPlatformResource lowerRoot = subPlatformResourceDAO
						.findBySN(lowerRootSn);
				getSubPlatformNode(lowerRoot.getId(), subNodes);
			}
		}

		return parent;
	}

	/**
	 * 多级平台互联，推送资源时，追加下级平台资源树
	 * 
	 * @return 下级平台资源树
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-9 下午4:23:29
	 */
	private void getSubPlatformNode(String parentId, Element parent) {
		List<SubPlatformResource> roots = null;
		if (StringUtils.isBlank(parentId)) {
			roots = subPlatformResourceDAO.listRoleRoots("4");
		} else {
			roots = subPlatformResourceDAO.listRoleSubOrgan("4", parentId);
		}
		for (SubPlatformResource root : roots) {
			// 如果parent中已经存在的机构SN，则追加下方的设备数据
			Element self = findElement(root.getStandardNumber(), parent);
			if (null != self) {
				// 追加设备信息
				Element channelList = self.getChild("ChannelList");
				// 根据平台互联角色权限来获取下级资源
				List<SubPlatformResource> list = subPlatformResourceDAO
						.listRoleSubResource("4", root.getId());
				for (SubPlatformResource resource : list) {
					// 推送摄像头
					if (TypeDefinition.SUBTYPE_CAMERA_BALL.equals(resource
							.getType())
							|| TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT
									.equals(resource.getType())
							|| TypeDefinition.SUBTYPE_CAMERA_DEFAULT
									.equals(resource.getType())
							|| (TypeDefinition.DEVICE_TYPE_CAMERA + "")
									.equals(resource.getType())) {
						Element channel = new Element("Channel");
						channel.setAttribute(
								"StandardNumber",
								resource.getStandardNumber() != null ? resource
										.getStandardNumber() : "");
						channel.setAttribute("Name",
								resource.getName() != null ? resource.getName()
										: "");
						channel.setAttribute(
								"SubType",
								resource.getPtzType() != null ? resource
										.getPtzType() : resource.getType());
						channel.setAttribute("Auth", resource.getAuth());
						channelList.addContent(channel);
					}
				}
				// 构建子机构
				getSubPlatformNode(root.getId(), self.getChild("SubNodes"));
			} else {
				// 自身信息
				self = new Element("Node");

				self.setAttribute(
						"StandardNumber",
						root.getStandardNumber() != null ? root
								.getStandardNumber() : "");
				self.setAttribute("Name",
						root.getName() != null ? root.getName() : "");
				// 设备信息
				Element channelList = new Element("ChannelList");
				self.addContent(channelList);
				// 根据平台互联角色权限来获取下级资源
				List<SubPlatformResource> list = subPlatformResourceDAO
						.listRoleSubResource("4", root.getId());
				for (SubPlatformResource resource : list) {
					// 推送摄像头
					if (TypeDefinition.SUBTYPE_CAMERA_BALL.equals(resource
							.getType())
							|| TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT
									.equals(resource.getType())
							|| TypeDefinition.SUBTYPE_CAMERA_DEFAULT
									.equals(resource.getType())
							|| (TypeDefinition.DEVICE_TYPE_CAMERA + "")
									.equals(resource.getType())) {
						Element channel = new Element("Channel");
						channel.setAttribute(
								"StandardNumber",
								resource.getStandardNumber() != null ? resource
										.getStandardNumber() : "");
						channel.setAttribute("Name",
								resource.getName() != null ? resource.getName()
										: "");
						channel.setAttribute(
								"SubType",
								resource.getPtzType() != null ? resource
										.getPtzType() : resource.getType());
						channel.setAttribute("Auth", resource.getAuth());
						channelList.addContent(channel);
					}
				}

				// 子节点
				Element subNodes = new Element("SubNodes");
				self.addContent(subNodes);

				// 构建子机构
				getSubPlatformNode(root.getId(), subNodes);
				// 将自身加入到parent的SubNodes中
				parent.addContent(self);
			}
		}
	}

	public Element findElement(String sn, Element parent) {
		if (null == parent) {
			return null;
		}
		List<Element> nodes = parent.getChildren();
		for (Element node : nodes) {
			if (sn.equals(node.getAttributeValue("StandardNumber"))) {
				return node;
			}
			// 递归查询子节点
			else {
				Element subNodes = node.getChild("SubNodes");
				Element rtn = findElement(sn, subNodes);
				if (null != rtn) {
					return rtn;
				}
			}
		}
		return null;
	}

	private List<AuthCameraVO> listPlatformAuthCamera() {
		// 平台下的所有摄像头
		List<Camera> cameras = cameraDAO.findAll();
		// 返回列表
		List<AuthCameraVO> rtnList = new LinkedList<AuthCameraVO>();

		// 获取平台互联角色
		Role role = roleDAO.getPlatformRole();
		// 如果不存在平台互联角色，默认赋予所有设备的查看权限
		if (null == role) {
			String auth = "1";
			for (Camera camera : cameras) {
				AuthCameraVO vo = new AuthCameraVO();
				vo.setOrganId(camera.getOrgan().getId());
				vo.setName(camera.getName());
				vo.setStandardNumber(camera.getStandardNumber());
				vo.setSubType(camera.getSubType());
				vo.setAuth(auth);
				rtnList.add(vo);
			}
			return rtnList;
		}
		List<String> roleIds = new ArrayList<String>();
		roleIds.add(role.getId());

		// 平台互联角色权限的所有摄像头
		List<RoleResourcePermission> authCameras = roleResourcePermissionDAO
				.listByRoleIds(roleIds, TypeDefinition.DEVICE_TYPE_CAMERA + "");

		for (RoleResourcePermission authCamera : authCameras) {
			AuthCameraVO vo = new AuthCameraVO();
			Camera camera = ((RoleResourcePermissionCamera) authCamera)
					.getCamera();
			if (null != camera) {
				vo.setOrganId(camera.getOrgan().getId());
				vo.setName(camera.getName());
				vo.setStandardNumber(camera.getStandardNumber());
				vo.setSubType(camera.getSubType());
				vo.setAuth(authCamera.getPrivilege());
				rtnList.add(vo);
			}
		}
		return rtnList;
	}

	private String translateAuth(String auth) {
		String[] array = auth.split(",");
		int rtn = 0;
		for (int i = 0; i < array.length; i++) {
			rtn += Integer.parseInt(array[i]);
		}
		return "%0" + rtn;
	}

	/**
	 * 从摄像头列表中，获取某个ID的摄像头
	 * 
	 * @param cameras
	 *            摄像头列表
	 * @param id
	 *            摄像头ID
	 * @return 摄像头对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午3:19:12
	 */
	private Camera getCameraFromList(List<Camera> cameras, String id) {
		for (Camera camera : cameras) {
			if (camera.getId().equals(id)) {
				return camera;
			}
		}
		return null;
	}

	/**
	 * 将机构列表转换为机构树
	 * 
	 * @param organs
	 *            机构列表
	 * @param devices
	 *            权限设备
	 * @param parent
	 *            父机构
	 * @return 机构树
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 下午3:22:25
	 */
	private Element treeOrgan(List<Organ> organs, List<AuthCameraVO> devices,
			Element parent) {
		for (Organ organ : organs) {
			Organ parentOrgan = organ.getParent();
			if (null == parentOrgan) {
				// donothing
			} else {
				// 如果父机构满足，加入到父机构中
				if (parent.getAttributeValue("Id").equals(parentOrgan.getId())) {
					// 自身信息
					Element organNode = new Element("Node");
					organNode.setAttribute("Id", organ.getId());
					organNode.setAttribute(
							"StandardNumber",
							organ.getStandardNumber() != null ? organ
									.getStandardNumber() : "");
					organNode.setAttribute("Name",
							organ.getName() != null ? organ.getName() : "");
					// 设备信息
					Element channelList = new Element("ChannelList");
					organNode.addContent(channelList);
					for (AuthCameraVO camera : devices) {
						if (camera.getOrganId().equals(organ.getId())) {
							Element channel = new Element("Channel");
							channel.setAttribute(
									"StandardNumber",
									camera.getStandardNumber() != null ? camera
											.getStandardNumber() : "");
							channel.setAttribute("Name",
									camera.getName() != null ? camera.getName()
											: "");
							channel.setAttribute(
									"SubType",
									camera.getSubType() != null ? camera
											.getSubType() : "");
							channel.setAttribute(
									"Auth",
									translateAuth(camera.getAuth()) != null ? translateAuth(camera
											.getAuth()) : "");
							channelList.addContent(channel);
						}
					}

					// 子节点
					Element subNodes = new Element("SubNodes");
					organNode.addContent(subNodes);

					// 构建子机构
					organNode = treeOrgan(organs, devices, organNode);
					// 移除不需要的Id属性
					organNode.removeAttribute("Id");
					// 将自身加入到parent的SubNodes中
					parent.getChild("SubNodes").addContent(organNode);

					// 多级平台互联情况下，加入下级平台数据推送，追加在lower_platform_root配置的机构下方
					String lowerRootSn = Configuration.getInstance()
							.loadProperties("lower_platform_root");
					if (StringUtils.isNotBlank(lowerRootSn)
							&& lowerRootSn.equals(organ.getStandardNumber())) {
						SubPlatformResource lowerRoot = subPlatformResourceDAO
								.findBySN(lowerRootSn);
						getSubPlatformNode(lowerRoot.getId(), subNodes);
					}
				}
			}
		}
		return parent;
	}

	@Override
	public void deleteByPlatform(String standardNumber)
			throws BusinessException {
		subPlatformResourceDAO.deleteByPlatform(standardNumber);
	}

	@Override
	public void acceptResource(Element node) throws BusinessException {
		// 创建新的数据
		updatePlatformResource(node, null, null);
	}

	/**
	 * 递归解析和创建下级推送资源树
	 * 
	 * @param node
	 *            资源树
	 * @param parent
	 *            父机构
	 * @param map
	 *            平台下的全部资源与SN的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-23 下午4:46:18
	 */
	private void updatePlatformResource(Element node,
			SubPlatformResource parent, Map<String, SubPlatformResource> map) {
		Long time = System.currentTimeMillis();
		// 根机构初始化
		if (null == parent) {
			SubPlatformResource root = subPlatformResourceDAO.loadBySn(node
					.getAttributeValue("StandardNumber"));
			// 第一次推送，全部新建
			if (null == root) {
				map = new HashMap<String, SubPlatformResource>();
			}
			// 更新
			else {
				map = subPlatformResourceDAO.mapSubPlatformResource(root
						.getId());
			}
		}
		// 机构节点
		SubPlatformResource organ = map.get(node
				.getAttributeValue("StandardNumber"));
		if (null == organ) {
			organ = new SubPlatformResource();
			organ.setUpdateTime(time);
		}
		organ.setName(node.getAttributeValue("Name"));
		organ.setStandardNumber(node.getAttributeValue("StandardNumber"));
		organ.setParent(parent);
		// 默认高速公路
		organ.setType(TypeDefinition.ORGAN_TYPE_MANAGEMENT);
		subPlatformResourceDAO.saveorupdate(organ);
		if (null != parent) {
			organ.setPath(parent.getPath() + "/" + organ.getId());
		} else {
			organ.setPath("/" + organ.getId());
		}

		// 摄像头节点
		List<Element> cameras = node.getChild("ChannelList").getChildren();
		for (Element camera : cameras) {
			SubPlatformResource entity = map.get(camera
					.getAttributeValue("StandardNumber"));
			if (null == entity) {
				entity = new SubPlatformResource();
				entity.setUpdateTime(time);
			}
			entity.setName(camera.getAttributeValue("Name"));
			entity.setStandardNumber(camera.getAttributeValue("StandardNumber"));
			entity.setType(TypeDefinition.DEVICE_TYPE_CAMERA + "");
			entity.setPtzType(camera.getAttributeValue("SubType"));
			entity.setAuth(camera.getAttributeValue("Auth"));
			entity.setProtocol("haikang");
			entity.setParent(organ);
			subPlatformResourceDAO.saveorupdate(entity);
			entity.setPath(organ.getPath() + "/" + entity.getId());
		}

		// 子节点
		List<Element> children = node.getChild("SubNodes").getChildren();
		for (Element child : children) {
			updatePlatformResource(child, organ, map);
		}
	}

	@Override
	public Element getResourceRoute(SubPlatformResource resource) {
		// 如果是GB28181推送数据，路由到推送上来的网关
		if (StringUtils.isNotBlank(resource.getGatewayId())) {
			Ccs ccs = ccsDAO.findById(resource.getGatewayId());
			Element e = new Element("Route");
			e.setAttribute("LanIp",
					MyStringUtil.object2StringNotNull(ccs.getLanIp()));
			e.setAttribute("Port",
					MyStringUtil.object2StringNotNull(ccs.getPort()));
			return e;
		} else {
			// 根据path得到根机构ID
			String rootId = resource.getPath().substring(1);
			int endIndex = rootId.indexOf("/");
			if (-1 == endIndex) {
				endIndex = rootId.length();
			}
			rootId = rootId.substring(0, endIndex);
			// 获取根机构
			SubPlatformResource root = subPlatformResourceDAO.findById(rootId);
			if (null == root) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"SubPlatformResource [" + rootId + "] not found !");
			}
			// 获取下级平台信息
			SubPlatform platform = subPlatformDAO.getByStandardNumber(root
					.getStandardNumber());
			if (null == platform) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"SubPlatform [" + root.getStandardNumber()
								+ "] not found !");
			}

			Element e = new Element("Route");
			e.setAttribute("LanIp",
					platform.getSipIp() != null ? platform.getSipIp() : "");
			e.setAttribute("Port", platform.getSipPort() != null ? platform
					.getSipPort().toString() : "");
			return e;
		}
	}

	@Override
	public SubPlatformResource getSubPlatformResourceBySN(String standardNumber) {
		return subPlatformResourceDAO.getByStandardNumber(standardNumber);
	}

	@Override
	public SubPlatform getSubPlatform(String id) {
		return subPlatformDAO.findById(id);
	}

	@Override
	public void saveReportData(List<Element> rows, String organSN,
			String tableName) throws BusinessException {
	}

	@Override
	public void pushResource28059(SubPlatformResource parent,
			List<SubPlatformResource> items) throws BusinessException {
		SubPlatformResource parentEntity = subPlatformResourceDAO
				.getByStandardNumber(parent.getStandardNumber());
		// 第一次上报，新增
		if (null == parentEntity) {
			parent.setType(TypeDefinition.ORGAN_TYPE_MANAGEMENT);
			subPlatformResourceDAO.save(parent);
			parent.setPath("/" + parent.getId());

			for (SubPlatformResource item : items) {
				item.setParent(parent);
				subPlatformResourceDAO.save(item);
				item.setPath(parent.getPath() + "/" + item.getId());
			}
		}
		// 修改,不支持属性变化，只支持对象的增加和减少
		else {
			parentEntity.setName(parent.getName());
			List<SubPlatformResource> list = subPlatformResourceDAO
					.listSubPlatformResource(parentEntity.getId());
			// 判断资源数量是否有变化，有变化才做处理
			if ((list.size()) != items.size()) {
				// 数据库中不存在的新增
				for (SubPlatformResource item : items) {
					if (!list.contains(item)) {
						item.setParent(parentEntity);
						subPlatformResourceDAO.save(item);
						item.setPath(parentEntity.getPath() + "/"
								+ item.getId());
					}
				}
				// 上报数据中不存在的删除
				for (SubPlatformResource entity : list) {
					if (!items.contains(entity)) {
						subPlatformResourceDAO.delete(entity);
					}
				}
			}
		}
	}

	@Override
	public Element catalog28181(String deviceId, boolean cascade) {
		// 返回元素
		Element deviceList = new Element("DeviceList");

		StandardNumber standardNumber = snDAO.getBySN(deviceId);
		// 本级平台资源
		if (null != standardNumber) {
			if (standardNumber.getClassType().equals(
					TypeDefinition.RESOURCE_TYPE_ORGAN)) {
				Organ p = organDAO.findBySN(deviceId);
				// 所有子机构-Item
				List<Organ> organs = organDAO.listOrganById(p.getId());
				// 所有的本级机构映射，处理下级在本级的层次结构
				Map<String, Organ> map = organDAO.mapOrganBySn(p.getId());

				// 平台互联角色权限的所有摄像头
				Role role = roleDAO.getPlatformRole();
				List<String> roleIds = new LinkedList<String>();
				roleIds.add(role.getId());
				List<RoleResourcePermission> authCameras = roleResourcePermissionDAO
						.listByRoleIds(roleIds,
								TypeDefinition.DEVICE_TYPE_CAMERA + "");
				// 机构下，平台互联角色关联的所有摄像头-Item
				List<Camera> cameras = new LinkedList<Camera>();
				for (RoleResourcePermission authCamera : authCameras) {
					Camera camera = ((RoleResourcePermissionCamera) authCamera)
							.getCamera();
					// 非机构下的摄像头不加入返回集合中
					if (null != camera && organs.contains(camera.getOrgan())) {
						cameras.add(camera);
					}
				}

				// 所有下级平台机构-Item
				List<SubPlatformResource> subOrgans = new LinkedList<SubPlatformResource>();
				// 所有下级平台摄像头-Item
				List<SubPlatformResource> subCameras = new LinkedList<SubPlatformResource>();

				if (cascade) {
					// 所有下级平台机构-Item
					subOrgans = subPlatformResourceDAO.listOrgan(null);
					// 所有下级平台摄像头-Item
					subCameras = subPlatformResourceDAO.listCamera(null);

					// 设备排序
					Collections.sort(cameras, new CameraComparator());
					Collections.sort(subCameras,
							new SubPlatformResourceComparator());
				}

				// 构建本机和下级资源Item返回
				for (Organ organ : organs) {
					// 构建本级机构元素
					// 根机构不在Item展示
					if (!organ.getStandardNumber().equals(deviceId)) {
						Element item = initOrganElement(organ);
						deviceList.addContent(item);
					}
				}
				int subOrganSize = 0;
				// 如果下级根机构属于本级某个机构加入到本级该机构下；如果不属于任何机构加入到本级平台根机构下
				Map<String, Boolean> isChildMap = new HashMap<String, Boolean>();
				for (SubPlatformResource subOrgan : subOrgans) {
					if (subOrgan.getParent() == null) {
						if (map.get(subOrgan.getStandardNumber()) != null) {
							isChildMap.put(subOrgan.getId(),
									Boolean.valueOf(true));
						} else {
							isChildMap.put(subOrgan.getId(),
									Boolean.valueOf(false));
						}
					}
				}
				// 构建下级平台机构元素
				for (SubPlatformResource subOrgan : subOrgans) {
					// 获取跟机构ID
					String rootId = null;
					int index = subOrgan.getPath().indexOf("/", 1);
					if (index >= 0) {
						rootId = subOrgan.getPath().substring(1, index);
					} else {
						rootId = subOrgan.getPath().substring(1);
					}
					// 是本级的某个机构的子机构
					if (isChildMap.get(rootId)) {
						// 是本级的子机构是根机构采用本级的属性,略过根机构
						if (map.get(subOrgan.getStandardNumber()) == null) {
							Element item = initSubOrganElement(subOrgan);
							deviceList.addContent(item);
							subOrganSize++;
						}
					}
					// 不是本级的子机构
					else {
						// 请求查询的是本级平台根机构，加入到返回中
						if (p.getParent() == null) {
							Element item = initSubOrganElement(subOrgan);
							deviceList.addContent(item);
							subOrganSize++;
						}
					}
				}
				// 当前在线的摄像头SN列表
				List<String> onlineCameraSns = deviceOnlineRealDAO
						.listOnlineDeviceSn();

				// 构建本级摄像头元素
				for (Camera camera : cameras) {
					Element item = initCameraElement(camera, onlineCameraSns);
					deviceList.addContent(item);
				}
				// 构建下级平台摄像头元素
				int subCameraSize = 0;
				for (SubPlatformResource subCamera : subCameras) {
					// 获取跟机构ID
					String rootId = null;
					int index = subCamera.getPath().indexOf("/", 1);
					if (index >= 0) {
						rootId = subCamera.getPath().substring(1, index);
					} else {
						rootId = subCamera.getPath().substring(1);
					}
					// 是本级的某个机构的子机构
					if (isChildMap.get(rootId)) {
						Element item = initSubCameraElement(subCamera);
						deviceList.addContent(item);
						subCameraSize++;
					}
					// 不是本级的子机构
					else {
						// 请求查询的是本级平台根机构，加入到返回中
						if (p.getParent() == null) {
							Element item = initSubCameraElement(subCamera);
							deviceList.addContent(item);
							subCameraSize++;
						}
					}
				}
				// 减一是去掉去根机构
				int total = organs.size() - 1 + cameras.size() + subOrganSize
						+ subCameraSize;
				deviceList.setAttribute("Num", total + "");
			}
			// 如果是设备
			else if (standardNumber.getClassType().equals(
					TypeDefinition.RESOURCE_TYPE_CAMERA)) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID, "["
						+ deviceId + "] is a camera !");
			}
		} else {
			SubPlatformResource subPlatformResource = subPlatformResourceDAO
					.getByStandardNumber(deviceId);
			// 没有此编码的资源，返回空数据
			if (null == subPlatformResource) {
				deviceList.setAttribute("Num", "0");
			}
			// 从下级资源表中查询
			else {
				// 下级平台机构
				List<SubPlatformResource> subOrgans = subPlatformResourceDAO
						.listOrgan(subPlatformResource.getId());
				// 下级平台摄像头
				List<SubPlatformResource> subCameras = subPlatformResourceDAO
						.listCamera(subPlatformResource.getId());
				int total = subOrgans.size() - 1 + subCameras.size();

				// 设备排序
				Collections.sort(subCameras,
						new SubPlatformResourceComparator());

				// 构建本机和下级资源Item返回
				for (SubPlatformResource subOrgan : subOrgans) {
					if (!subOrgan.getStandardNumber().equals(deviceId)) {
						// 构建下级平台机构元素
						Element item = initSubOrganElement(subOrgan);
						deviceList.addContent(item);
					}
				}
				for (SubPlatformResource subCamera : subCameras) {
					// 构建下级平台摄像头元素
					Element item = initSubCameraElement(subCamera);
					deviceList.addContent(item);
				}
				deviceList.setAttribute("Num", total + "");
			}
		}
		return deviceList;
	}

	private Element initOrganElement(Organ organ) {
		Element item = new Element("Item");
		item.setAttribute("DeviceID", organ.getStandardNumber());
		item.setAttribute("Name", organ.getName());
		item.setAttribute("ParentID", organ.getParent() != null ? organ
				.getParent().getStandardNumber() : "");
		return item;
	}

	private Element initSubOrganElement(SubPlatformResource organ) {
		Element item = new Element("Item");
		item.setAttribute("DeviceID", organ.getStandardNumber());
		item.setAttribute("Name",
				MyStringUtil.object2StringNotNull(organ.getName()));
		if (organ.getParent() != null) {
			item.setAttribute("ParentID", organ.getParent().getStandardNumber());
		}
		// 如果配置了lower_platform_root属性，下级平台的根节点放在该配置机构下
		else {
			String parentSn = Configuration.getInstance().loadProperties(
					"lower_platform_root");
			item.setAttribute("ParentID",
					MyStringUtil.object2StringNotNull(parentSn));
		}

		return item;
	}

	private Element initCameraElement(Camera camera,
			List<String> onlineCameraSns) {
		Element item = new Element("Item");
		item.setAttribute("DeviceID", camera.getStandardNumber());
		item.setAttribute("Name", camera.getName());
		item.setAttribute("Manufacturer",
				camera.getManufacturer() != null ? camera.getManufacturer()
						.getName() : "");
		item.setAttribute("Model", camera.getDeviceModel() != null ? camera
				.getDeviceModel().getName() : "");
		item.setAttribute("Owner", MyStringUtil.object2StringNotNull(camera
				.getProperty().getOwner()));
		item.setAttribute("CivilCode", MyStringUtil.object2StringNotNull(camera
				.getProperty().getCivilCode()));
		item.setAttribute("Block", MyStringUtil.object2StringNotNull(camera
				.getProperty().getBlock()));
		item.setAttribute("Address",
				MyStringUtil.object2StringNotNull(camera.getLocation()));
		item.setAttribute("Parental", "0");
		item.setAttribute("ParentID", camera.getOrgan().getStandardNumber());
		item.setAttribute("SafetyWay", "0");
		item.setAttribute("RegisterWay", "0");
		item.setAttribute("CertNum", MyStringUtil.object2StringNotNull(camera
				.getProperty().getCertNum()));
		item.setAttribute("Certifiable", MyStringUtil
				.object2StringNotNull(camera.getProperty().getCertifiable()));
		item.setAttribute("ErrCode", MyStringUtil.object2StringNotNull(camera
				.getProperty().getErrCode()));
		item.setAttribute("EndTime", MyStringUtil.object2StringNotNull(camera
				.getProperty().getEndTime()));
		item.setAttribute("Secrecy", MyStringUtil.object2StringNotNull(camera
				.getProperty().getSecrecy()));
		item.setAttribute("IPAddress", MyStringUtil.object2StringNotNull(camera
				.getParent().getLanIp()));
		item.setAttribute("Port",
				MyStringUtil.object2StringNotNull(camera.getParent().getPort()));
		item.setAttribute(
				"Password",
				MyStringUtil.object2StringNotNull(camera.getParent()
						.getProperty().getPassword()));
		item.setAttribute("Status", onlineCameraSns.contains(camera
				.getStandardNumber()) ? "1" : "0");
		item.setAttribute("Longitude",
				MyStringUtil.object2StringNotNull(camera.getLongitude()));
		item.setAttribute("Latitude",
				MyStringUtil.object2StringNotNull(camera.getLatitude()));
		item.setAttribute("PTZType",
				translatePtzTypeGB28181(camera.getSubType()));
		item.setAttribute(
				"LoginName",
				MyStringUtil.object2StringNotNull(camera.getParent()
						.getProperty().getUserName()));
		item.setAttribute("PileNo",
				MyStringUtil.object2StringNotNull(camera.getStakeNumber()));

		return item;
	}

	private Element initSubCameraElement(SubPlatformResource camera) {
		Element item = new Element("Item");
		item.setAttribute("DeviceID", camera.getStandardNumber());
		item.setAttribute("Name", camera.getName());
		item.setAttribute("Manufacturer",
				MyStringUtil.object2StringNotNull(camera.getManufacturer()));
		item.setAttribute("Model",
				MyStringUtil.object2StringNotNull(camera.getModel()));
		item.setAttribute("Owner",
				MyStringUtil.object2StringNotNull(camera.getOwner()));
		item.setAttribute("CivilCode",
				MyStringUtil.object2StringNotNull(camera.getCivilCode()));
		item.setAttribute("Block",
				MyStringUtil.object2StringNotNull(camera.getBlock()));
		item.setAttribute("Address",
				MyStringUtil.object2StringNotNull(camera.getAddress()));
		item.setAttribute("Parental",
				MyStringUtil.object2StringNotNull(camera.getParental()));
		item.setAttribute("ParentID", MyStringUtil.object2StringNotNull(camera
				.getParent().getStandardNumber()));
		item.setAttribute("SafetyWay",
				MyStringUtil.object2StringNotNull(camera.getSafetyWay()));
		item.setAttribute("RegisterWay",
				MyStringUtil.object2StringNotNull(camera.getRegisterWay()));
		item.setAttribute("CertNum",
				MyStringUtil.object2StringNotNull(camera.getCertNum()));
		item.setAttribute("Certifiable",
				MyStringUtil.object2StringNotNull(camera.getCertifiable()));
		item.setAttribute("ErrCode",
				MyStringUtil.object2StringNotNull(camera.getErrCode()));
		item.setAttribute("EndTime", MyStringUtil
				.object2StringNotNull(MyStringUtil.timeToString(camera
						.getEndTime())));
		item.setAttribute("Secrecy",
				MyStringUtil.object2StringNotNull(camera.getSecrecy()));
		item.setAttribute("IPAddress",
				MyStringUtil.object2StringNotNull(camera.getIp()));
		item.setAttribute("Port",
				MyStringUtil.object2StringNotNull(camera.getPort()));
		item.setAttribute("Password",
				MyStringUtil.object2StringNotNull(camera.getPassword()));
		item.setAttribute("Status",
				MyStringUtil.object2StringNotNull(camera.getStatus()));
		item.setAttribute("Longitude",
				MyStringUtil.object2StringNotNull(camera.getLongitude()));
		item.setAttribute("Latitude",
				MyStringUtil.object2StringNotNull(camera.getLatitude()));
		item.setAttribute("PTZType",
				MyStringUtil.object2StringNotNull(camera.getPtzType()));
		item.setAttribute("PileNo",
				MyStringUtil.object2StringNotNull(camera.getStakeNumber()));

		return item;
	}

	/**
	 * 转换摄像头类型为内部通用类型
	 * 
	 * @param subType
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-30 下午6:58:42
	 */
	private String translatePtzType(String subType) {
		if (StringUtils.isBlank(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		if (TypeDefinition.GB28181_CAMERA_BALL.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_BALL;
		}
		if (TypeDefinition.GB28181_CAMERA_BOLT.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		if (TypeDefinition.GB28181_CAMERA_BALL_BOLT.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT;
		}
		if (TypeDefinition.GB28181_CAMERA_HALF_BALL.equals(subType)) {
			return TypeDefinition.SUBTYPE_CAMERA_DEFAULT;
		}
		return subType;
	}

	/**
	 * 转换摄像头类型为GB28181的标准类型
	 * 
	 * @param subType
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-2 上午9:53:39
	 */
	private String translatePtzTypeGB28181(String subType) {
		if (StringUtils.isBlank(subType)) {
			return TypeDefinition.GB28181_CAMERA_BOLT;
		}
		if (TypeDefinition.SUBTYPE_CAMERA_BALL.equals(subType)) {
			return TypeDefinition.GB28181_CAMERA_BALL;
		}
		if (TypeDefinition.SUBTYPE_CAMERA_DEFAULT.equals(subType)) {
			return TypeDefinition.GB28181_CAMERA_BOLT;
		}
		if (TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT.equals(subType)) {
			return TypeDefinition.GB28181_CAMERA_BALL_BOLT;
		}
		return TypeDefinition.GB28181_CAMERA_BOLT;
	}

	@Override
	public void updateCatalog28181(List<Element> items, String rootSn,
			String gatewayId, String name, String model, String owner,
			Integer parental, String civilCode, String address,
			Integer registerWay, Integer secrecy, String manufacturer) {
		// rootSn下所有的平台资源映射
		Map<String, SubPlatformResource> map = null;
		// 根节点
		SubPlatformResource root = subPlatformResourceDAO.loadBySn(rootSn);
		if (null == root) {
			root = new SubPlatformResource();
			root.setStandardNumber(rootSn);
			root.setName(StringUtils.isBlank(name) ? rootSn : name);
			root.setModel(model);
			root.setOwner(owner);
			root.setParental(parental);
			root.setCivilCode(civilCode);
			root.setAddress(address);
			root.setRegisterWay(registerWay);
			root.setSecrecy(secrecy);
			root.setManufacturer(manufacturer);
			root.setGatewayId(gatewayId);
			root.setType(TypeDefinition.ORGAN_TYPE_MANAGEMENT);
			root.setUpdateTime(System.currentTimeMillis());
			subPlatformResourceDAO.save(root);
			root.setPath("/" + root.getId());
			map = new HashMap<String, SubPlatformResource>();
			map.put(rootSn, root);
		} else {
			map = subPlatformResourceDAO.mapSubPlatformResource(root.getId());
			// 修改根机构属性
			root.setUpdateTime(System.currentTimeMillis());
			root.setName(StringUtils.isBlank(name) ? rootSn : name);
			root.setGatewayId(gatewayId);
		}

		// 每一级子节点总数
		int count = 0;
		// 父节点数组
		Element[] parents = new Element[items.size()];
		// 临时存放的下级父节点数组
		Element[] tempElements = new Element[items.size()];
		// 初始化parents
		int index = 0;
		for (Element item : items) {
			if (item.getChildText("ParentID").equals(rootSn)) {
				parents[index++] = item;
				updateSubResource(item, rootSn, gatewayId, map);
			}
		}

		while (true) {
			int k = 0;
			// 循环上级机构
			for (int i = 0; i < parents.length; i++) {
				if (parents[i] == null) {
					break;
				}

				// ParentID匹配的本级写入数据库
				for (Element item : items) {
					if (parents[i].getChildText("DeviceID").equals(
							item.getChildText("ParentID"))) {
						updateSubResource(item, item.getChildText("ParentID"),
								gatewayId, map);
						// 记录本级节点，用于下级循环
						tempElements[k++] = item;
						count++;
					}
				}
			}
			// 清空父节点数组
			for (int i = 0; i < parents.length; i++) {
				if (parents[i] == null) {
					break;
				}
				parents[i] = null;
			}
			// 指定本级为下级循环列表
			for (int i = 0; i < tempElements.length; i++) {
				if (tempElements[i] == null) {
					break;
				}
				parents[i] = tempElements[i];
			}
			// 清空本级
			for (int i = 0; i < tempElements.length; i++) {
				if (tempElements[i] == null) {
					break;
				}
				tempElements[i] = null;
			}
			// 没有子节点退出循环
			if (count <= 0) {
				break;
			} else {
				count = 0;
			}
		}
	}

	private void updateSubResource(Element resource, String parentSn,
			String gatewayId, Map<String, SubPlatformResource> map)
			throws BusinessException {
		String sn = resource.getChildText("DeviceID");
		// 从已有列表中查找
		SubPlatformResource entity = map.get(sn);
		// 新增
		if (null == entity) {
			entity = new SubPlatformResource();
			entity.setStandardNumber(sn);
			entity.setParent(map.get(parentSn));
			entity.setAuth("07%");
			entity.setProtocol("GB28181");
			subPlatformResourceDAO.save(entity);
			map.put(sn, entity);
		}
		// 修改
		entity.setAddress(resource.getChildText("Address"));
		entity.setBlock(resource.getChildText("Block"));
		entity.setCertifiable(NumberUtil.getInteger(resource
				.getChildText("Certifiable")));
		entity.setCertNum(resource.getChildText("CertNum"));
		entity.setCivilCode(resource.getChildText("CivilCode"));
		entity.setEndTime(MyStringUtil.stringToTime(resource
				.getChildText("EndTime")));
		entity.setErrCode(NumberUtil.getInteger(resource
				.getChildText("ErrCode")));
		entity.setGatewayId(gatewayId);
		entity.setIp(resource.getChildText("IPAddress"));
		entity.setLatitude(resource.getChildText("Latitude"));
		entity.setLongitude(resource.getChildText("Longitude"));
		entity.setManufacturer(resource.getChildText("Manufacturer"));
		entity.setModel(resource.getChildText("Model"));
		entity.setName(resource.getChildText("Name"));
		entity.setOwner(resource.getChildText("Owner"));
		entity.setParental(NumberUtil.getInteger(resource
				.getChildText("Parental")));
		entity.setPassword(resource.getChildText("Password"));
		entity.setPath(map.get(parentSn).getPath() + "/" + entity.getId());
		entity.setPort(NumberUtil.getInteger(resource.getChildText("Port")));
		Element info = resource.getChild("Info");
		if (null != info) {
			entity.setPtzType(info.getChildText("PTZType"));
		}
		entity.setRegisterWay(NumberUtil.getInteger(resource
				.getChildText("RegisterWay")));
		entity.setSafetyWay(NumberUtil.getInteger(resource
				.getChildText("SafetyWay")));
		entity.setSecrecy(NumberUtil.getInteger(resource
				.getChildText("Secrecy")));
		entity.setStatus(resource.getChildText("Status"));
		entity.setType(resource.getChildText("Type"));
		entity.setUpdateTime(System.currentTimeMillis());
		entity.setStakeNumber(resource.getChildText("PileNo"));
	}

	/**
	 * 合并权限
	 * 
	 * @param auth1
	 *            权限1
	 * @param auth2
	 *            权限2
	 * @return 合并后的权限字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 下午3:12:13
	 */
	private String combineAuth(String auth1, String auth2) {
		Set<String> set = new HashSet<String>();
		String[] s1 = auth1.split(",");
		String[] s2 = auth2.split(",");
		for (int i = 0; i < s1.length; i++) {
			set.add(s1[i]);
		}
		for (int i = 0; i < s2.length; i++) {
			set.add(s2[i]);
		}
		// 字符串得到的数字相加得到设备权限并集
		StringBuffer sb = new StringBuffer();
		for (String s : set) {
			sb.append(s);
			sb.append(",");
		}
		return sb.toString();
	}

	/**
	 * 修改gatewayId为SN
	 * 
	 * @param resource
	 *            下级平台资源
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-2 下午2:43:02
	 */
	private void updateCcsSn(SubPlatformResource resource) {
		// 修改gatewayId为SN
		if (StringUtils.isBlank(resource.getGatewayId())) {
			resource.setGatewayId(Configuration.getInstance().getProperties(
					"gateway_ccs_sn"));
		} else {
			Ccs ccs = ccsDAO.findById(resource.getGatewayId());
			resource.setGatewayId(ccs.getStandardNumber());
		}
	}

	private boolean containBySn(List<Organ> organs, String sn) {
		for (Organ organ : organs) {
			if (organ.getStandardNumber().equals(sn)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Element catalog28181ByUser(String userSn) {
		// 返回元素
		Element deviceList = new Element("DeviceList");

		User user = userDAO.findBySN(userSn);
		Organ root = user.getOrgan();
		Set<Role> roles = user.getRoles();
		List<String> roleIds = new LinkedList();
		// 所有子机构-Item
		List<Organ> organs = organDAO.listOrganById(root.getId());
		// 所有的本级机构映射，处理下级在本级的层次结构
		Map<String, Organ> map = organDAO.mapOrganBySn(root.getId());
		// 机构下，用户权限关联的所有摄像头-Item
		List<Camera> cameras = null;
		// 用户权限关联的下级平台机构-Item
		List<SubPlatformResource> subOrgans = null;
		// 用户权限关联的下级平台摄像头-Item
		List<SubPlatformResource> subCameras = null;

		boolean systemRoleFlag = false;
		// 判断是系统角色，系统角色拥有本级机构下的所有查看权限和下级平台属于本机机构下时的所有查看权限
		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				systemRoleFlag = true;
				break;
			} else {
				roleIds.add(role.getId());
			}
		}
		// 系统角色，系统角色拥有本级机构下的所有查看权限和下级平台属于本机机构下时的所有查看权限
		if (systemRoleFlag) {
			// 查询机构下的所有摄像头
			cameras = cameraDAO.listInOrgan(root.getId());
			// 查询所有的下级平台机构
			subOrgans = subPlatformResourceDAO.listOrgan(null);
			// 查询所有的下级平台摄像头
			subCameras = subPlatformResourceDAO.listCamera(null);
		}
		// 查询用户角色关联的设备
		else {
			if (roleIds.size() == 0) {
				throw new BusinessException(ErrorCode.USER_ROLE_INVALID,
						"User[" + userSn
								+ "] has no role, please assign role first !");
			}
			cameras = new LinkedList<Camera>();
			List<RoleResourcePermission> authCameras = roleResourcePermissionDAO
					.listByRoleIds(roleIds, TypeDefinition.DEVICE_TYPE_CAMERA
							+ "");
			for (RoleResourcePermission authCamera : authCameras) {
				Camera camera = ((RoleResourcePermissionCamera) authCamera)
						.getCamera();
				cameras.add(camera);
			}

			subOrgans = new LinkedList<SubPlatformResource>();
			subCameras = new LinkedList<SubPlatformResource>();
			List<RoleResourcePermission> authSubResources = roleResourcePermissionDAO
					.listByRoleIds(roleIds,
							TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "");
			// 下级机构类型
			List<String> organType = Arrays.asList("1", "0", "100", "110",
					"120", "121", "122");
			// 下级摄像头类型
			List<String> cameraType = Arrays.asList("2", "04", "05", "06");
			for (RoleResourcePermission authSubResource : authSubResources) {
				SubPlatformResource subResource = ((RoleResourcePermissionSubResource) authSubResource)
						.getSubResource();
				if (organType.contains(subResource.getType())) {
					subOrgans.add(subResource);
				} else if (cameraType.contains(subResource.getType())) {
					subCameras.add(subResource);
				}
			}
		}

		// 设备排序
		Collections.sort(cameras, new CameraComparator());
		Collections.sort(subCameras, new SubPlatformResourceComparator());

		// 构建本机和下级资源Item返回
		for (Organ organ : organs) {
			// 构建本级机构元素
			Element item = initOrganElement(organ);
			deviceList.addContent(item);
		}

		int subOrganSize = 0;
		// 构建下级平台机构元素
		for (SubPlatformResource subOrgan : subOrgans) {
			// 是本级的子机构采用本级的属性,略过该机构
			if (map.get(subOrgan.getStandardNumber()) == null) {
				Element item = initSubOrganElement(subOrgan);
				deviceList.addContent(item);
				subOrganSize++;
			}
		}

		// 当前在线的摄像头SN列表
		List<String> onlineCameraSns = deviceOnlineRealDAO.listOnlineDeviceSn();
		// 构建本级摄像头元素
		for (Camera camera : cameras) {
			Element item = initCameraElement(camera, onlineCameraSns);
			deviceList.addContent(item);
		}
		// 构建下级平台摄像头元素
		for (SubPlatformResource subCamera : subCameras) {
			Element item = initSubCameraElement(subCamera);
			deviceList.addContent(item);
		}
		int total = organs.size() + cameras.size() + subOrganSize
				+ subCameras.size();
		deviceList.setAttribute("Num", total + "");

		return deviceList;
	}

	@Override
	public List<Element> listSubDevice() {
		List<Element> list = new LinkedList<Element>();

		List<SubPlatform> platforms = subPlatformDAO.findAll();
		List<SubPlatformResource> cameras = subPlatformResourceDAO
				.listCamera(null);
		List<SubPlatformResource> roots = subPlatformResourceDAO.listRoots();

		// 每个平台就是一个Gateway Elment节点
		for (SubPlatform platform : platforms) {
			Element gateway = new Element("Gateway");
			gateway.setAttribute("Ip",
					MyStringUtil.object2StringNotNull(platform.getSipIp()));
			gateway.setAttribute("Port",
					MyStringUtil.object2StringNotNull(platform.getSipPort()));

			String rootId = null;
			// 查找该平台对应的根机构ID
			for (SubPlatformResource root : roots) {
				if (root.getStandardNumber().equals(
						platform.getStandardNumber())) {
					rootId = root.getId();
					break;
				}
			}
			// 未找到某平台的根机构,忽略该平台
			if (null == rootId) {
				continue;
			}
			// 填加属于该平台的摄像头
			for (SubPlatformResource camera : cameras) {
				if (camera.getPath().indexOf(rootId) >= 0) {
					Element device = new Element("Device");
					device.setAttribute("StandardNumber",
							camera.getStandardNumber());
					gateway.addContent(device);
				}
			}

			list.add(gateway);
		}

		return list;
	}

	@Override
	public List<Element> listGatewayDevice() {
		List<Element> list = new LinkedList<Element>();
		// 所有下级摄像头
		List<SubPlatformResource> cameras = subPlatformResourceDAO
				.listCamera(null);
		// 28181互联摄像头
		List<SubPlatformResource> cameras28181 = new LinkedList<SubPlatformResource>();
		// 28181 CCS网关
		Set<String> gatewaySet = new HashSet<String>();

		String sn = Configuration.getInstance()
				.loadProperties("gateway_ccs_sn");
		// 内部互联
		if (StringUtils.isNotBlank(sn)) {
			Ccs gateway = ccsDAO.findBySN(sn);

			Element gate = new Element("Gateway");
			gate.setAttribute("StandardNumber", sn);
			gate.setAttribute("LanIp",
					MyStringUtil.object2StringNotNull(gateway.getLanIp()));
			gate.setAttribute("WanIp",
					MyStringUtil.object2StringNotNull(gateway.getWanIp()));
			gate.setAttribute("Port",
					MyStringUtil.object2StringNotNull(gateway.getPort()));
			list.add(gate);

			for (SubPlatformResource camera : cameras) {
				if (StringUtils.isBlank(camera.getGatewayId())) {
					Element device = new Element("Device");
					device.setAttribute("StandardNumber",
							camera.getStandardNumber());
					gate.addContent(device);
				} else {
					gatewaySet.add(camera.getGatewayId());
					cameras28181.add(camera);
				}
			}
		}
		// 28181网关
		for (String gatewayId : gatewaySet) {
			Ccs gateway = ccsDAO.findById(gatewayId);
			Element gate = new Element("Gateway");
			gate.setAttribute("StandardNumber", gateway.getStandardNumber());
			gate.setAttribute("LanIp",
					MyStringUtil.object2StringNotNull(gateway.getLanIp()));
			gate.setAttribute("WanIp",
					MyStringUtil.object2StringNotNull(gateway.getWanIp()));
			gate.setAttribute("Port",
					MyStringUtil.object2StringNotNull(gateway.getPort()));
			list.add(gate);

			for (SubPlatformResource camera : cameras28181) {
				if (camera.getGatewayId().equals(gatewayId)) {
					Element device = new Element("Device");
					device.setAttribute("StandardNumber",
							camera.getStandardNumber());
					gate.addContent(device);
				}
			}
		}

		return list;
	}

	public static void main(String[] args) {
		ConnectManagerImpl connectManager = new ConnectManagerImpl();
		Element subNodes = new Element("subNodes");
		for (int i = 1; i < 100; i++) {
			Element organNode = new Element("Node");
			organNode.setAttribute("StandardNumber", "111" + i);
			Element sub = new Element("SubNodes");
			organNode.addContent(sub);
			subNodes.addContent(organNode);

			Element ssub = new Element("Node");
			ssub.setAttribute("StandardNumber", "222" + i);
			sub.addContent(ssub);
			// Element sssub = new Element("SubNodes");
			// ssub.addContent(sssub);
		}

		Element rtn = connectManager.findElement("22211", subNodes);
		System.out.println(ElementUtil.element2String(rtn));

	}

	@Override
	public String pushDeviceStatus() {
		String higherPlatformAddress = Configuration.getInstance()
				.loadProperties("higher_platform_address");

		// 如果没有配置上级平台IP抛错
		if (StringUtils.isBlank(higherPlatformAddress)) {
			// throw new BusinessException(ErrorCode.URL_ERROR,
			// "higher platform address not find");
			return ErrorCode.SUCCESS;
		}
		// 如果是最高级平台返回200OK
		if (higherPlatformAddress.equals("http://127.0.0.1:8080/cms/")) {
			return ErrorCode.SUCCESS;
		}

		// 发送请求到上级平台，更新下级平台设备状态心跳时间
		Organ organSN = organDAO.getRootOrgan();
		StringBuffer sb = new StringBuffer();
		sb.append("<Request Method=\"List_Device_Status\" Cmd=\"1017\">");
		sb.append(System.getProperty("line.separator"));
		sb.append("<Organ StandardNumber=\"");
		sb.append(organSN.getStandardNumber());
		sb.append("\"/>");
		sb.append(System.getProperty("line.separator"));
		sb.append("</Request>");
		String body = sb.toString();
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(20000);
		PostMethod method = new PostMethod(higherPlatformAddress
				+ "update_sub_platform.xml");
		System.out
				.println("Send update sub platform request to higher platform address:"
						+ higherPlatformAddress);
		try {
			RequestEntity entity = new StringRequestEntity(body,
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);
			// 返回
			SAXBuilder builder = new SAXBuilder();
			Document respDoc = builder.build(method.getResponseBodyAsStream());
			String updatePlatformCode = respDoc.getRootElement()
					.getAttributeValue("Code");
			if (!ErrorCode.SUCCESS.equals(updatePlatformCode)) {
				throw new BusinessException(updatePlatformCode, respDoc
						.getRootElement().getAttributeValue("Message"));
			}
		} catch (ConnectException con) {
			con.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"network io error to higher platform address:"
							+ higherPlatformAddress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

		// 发送请求到上级平台，推送下级平台设备状态
		List<DeviceOnlineReal> deviceOnlineReals = deviceOnlineRealDAO
				.findAll();
		StringBuffer sb1 = new StringBuffer();
		sb1.append("<Request Method=\"List_Device_Status\" Cmd=\"1017\">");
		sb1.append(System.getProperty("line.separator"));
		sb1.append("<Organ>");
		sb1.append(organSN.getStandardNumber());
		sb1.append("</Organ>");
		sb1.append(System.getProperty("line.separator"));
		if (deviceOnlineReals.size() > 0) {
			sb1.append("<Devices>");
		} else {
			sb1.append("<Devices/>");
		}
		sb1.append(System.getProperty("line.separator"));
		for (DeviceOnlineReal deviceOnlineReal : deviceOnlineReals) {
			sb1.append("  <Device StandardNumber=\"");
			sb1.append(deviceOnlineReal.getStandardNumber());
			sb1.append("\" ");
			sb1.append("OnlineTime=\"");
			sb1.append(deviceOnlineReal.getOnlineTime());
			sb1.append("\" ");
			sb1.append("UpdateTime=\"");
			sb1.append(deviceOnlineReal.getUpdateTime());
			sb1.append("\"/>");
			sb1.append(System.getProperty("line.separator"));
		}
		if (deviceOnlineReals.size() > 0) {
			sb1.append("</Devices>");
		}
		sb1.append(System.getProperty("line.separator"));
		sb1.append("</Request>");
		String body1 = sb1.toString();
		HttpClient client1 = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		client1.getHttpConnectionManager().getParams()
				.setConnectionTimeout(20000);
		PostMethod method1 = new PostMethod(higherPlatformAddress
				+ "update_lower_device_status.xml");
		System.out
				.println("Send update lower device status request to higher platform address:"
						+ higherPlatformAddress);
		System.out.println(body1);
		try {
			RequestEntity entity = new StringRequestEntity(body1,
					"application/xml", "utf8");
			method1.setRequestEntity(entity);
			client1.executeMethod(method1);
			// 返回
			SAXBuilder builder = new SAXBuilder();
			Document respDoc = builder.build(method1.getResponseBodyAsStream());
			String updateDeviceStatusCode = respDoc.getRootElement()
					.getAttributeValue("Code");
			if (!ErrorCode.SUCCESS.equals(updateDeviceStatusCode)) {
				throw new BusinessException(updateDeviceStatusCode, respDoc
						.getRootElement().getAttributeValue("Message"));
			}
		} catch (ConnectException con) {
			con.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"network io error to higher platform address:"
							+ higherPlatformAddress);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return ErrorCode.SUCCESS;
	}

	@Override
	public void updateSubPlatform(List<String> sns) {
		for (String sn : sns) {
			SubPlatform sp = subPlatformDAO.findBySN(sn);
			sp.setUpdateTime(System.currentTimeMillis());
		}
	}

	@Override
	public void updateLowerDeviceStatus(String organSN,
			List<DeviceOnlineReal> deviceOnlineReals) {
		long currentTime = System.currentTimeMillis();

		// 根据下级平台机构的SN查询下级平台摄像头
		SubPlatformResource organ = subPlatformResourceDAO.findBySN(organSN);
		List<SubPlatformResource> cameras = subPlatformResourceDAO
				.listCamera(organ.getId());

		// 过滤上报的设备状态以下级平台资源为准
		List<DeviceOnlineReal> filterOnlines = new ArrayList<DeviceOnlineReal>();
		for (DeviceOnlineReal entity : deviceOnlineReals) {
			boolean flag = false;
			for (SubPlatformResource spfr : cameras) {
				if (spfr.getStandardNumber().equals(entity.getStandardNumber())) {
					flag = true;
					break;
				}
			}
			if (flag) {
				filterOnlines.add(entity);
			}
		}

		// 要更新的sn列表
		String[] sns = new String[cameras.size()];
		int i = 0;
		for (SubPlatformResource camera : cameras) {
			sns[i] = camera.getStandardNumber();
			i++;
		}

		// 在线设备记录
		List<DeviceOnlineReal> onlineReal = deviceOnlineRealDAO
				.listDeviceOnline(sns);

		// 批量插入实时表集合
		List<DeviceOnlineReal> insertOnlineReal = new ArrayList<DeviceOnlineReal>();

		// 批量插入历史表集合
		List<DeviceOnline> offlineList = new ArrayList<DeviceOnline>();

		// 需要剔除掉的在线设备 ，以便下线设备插入历史表
		List<DeviceOnlineReal> otherList = new ArrayList<DeviceOnlineReal>();

		//
		for (DeviceOnlineReal deviceOnlineReal : filterOnlines) {
			boolean flag = true;
			for (DeviceOnlineReal entity : onlineReal) {
				if (deviceOnlineReal.getStandardNumber().equals(
						entity.getStandardNumber())) {
					// 更新设备在线实时表
					entity.setOnlineTime(deviceOnlineReal.getOnlineTime());
					entity.setUpdateTime(deviceOnlineReal.getUpdateTime());
					// 准备剔除掉的在线设备
					otherList.add(entity);
					flag = false;
					break;
				}
			}
			if (flag) {
				insertOnlineReal.add(deviceOnlineReal);
			}
		}

		// 当前在线设备剔除传入的设备后，剩下插入历史表设备
		onlineReal.removeAll(otherList);
		for (DeviceOnlineReal entity : onlineReal) {
			// 实时表删除当前状态
			deviceOnlineRealDAO.delete(entity);
			// 添加到历史表
			DeviceOnline deviceOnline = new DeviceOnline();
			deviceOnline.setOfflineTime(currentTime);
			deviceOnline.setOnlineTime(entity.getOnlineTime());
			deviceOnline.setStandardNumber(entity.getStandardNumber());
			offlineList.add(deviceOnline);
		}

		// 批量插入
		deviceOnlineRealDAO.batchInsert(insertOnlineReal);
		deviceOnlineDAO.batchInsert(offlineList);
	}

	@Override
	public void checkPlatform() {
		List<SubPlatform> list = subPlatformDAO.findAll();
		for (SubPlatform sp : list) {
			Long updateTime = sp.getUpdateTime();
			if ((updateTime + SESSION_EXPIRE_TIME) < System.currentTimeMillis()) {
				System.out.println("Lower Platform OrganSN["
						+ sp.getStandardNumber()
						+ "] is offline, offline all managed cameras !");
				// 根据下级平台机构的SN查询下级平台摄像头
				SubPlatformResource organ = subPlatformResourceDAO.findBySN(sp
						.getStandardNumber());
				List<SubPlatformResource> cameras = subPlatformResourceDAO
						.listCamera(organ.getId());

				// 要更新的sn列表
				String[] sns = new String[cameras.size()];
				int i = 0;
				for (SubPlatformResource camera : cameras) {
					sns[i] = camera.getStandardNumber();
					i++;
				}

				// 在线设备记录
				List<DeviceOnlineReal> onlineReal = deviceOnlineRealDAO
						.listDeviceOnline(sns);

				// 需要批量写入的下线设备记录
				List<DeviceOnline> offlineList = new LinkedList<DeviceOnline>();

				for (DeviceOnlineReal entity : onlineReal) {
					DeviceOnline deviceOnline = new DeviceOnline();
					deviceOnline.setOfflineTime(System.currentTimeMillis());
					deviceOnline.setOnlineTime(entity.getOnlineTime());
					deviceOnline.setStandardNumber(entity.getStandardNumber());
					offlineList.add(deviceOnline);

					// 移除在线记录
					deviceOnlineRealDAO.delete(entity);
				}
				// 批量插入历史表
				if (offlineList.size() > 0) {
					deviceOnlineDAO.batchInsert(offlineList);
				}
			}
		}
	}
}
