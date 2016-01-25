package com.znsx.cms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.DisplayWallDAO;
import com.znsx.cms.persistent.dao.DvrDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.MonitorDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganBridgeDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.OrganRoadDAO;
import com.znsx.cms.persistent.dao.OrganTunnelDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.RoadMouthDAO;
import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.dao.ServiceZoneDAO;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.TollgateDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.DisplayWall;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganBridge;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.OrganTunnel;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.RoadMouth;
import com.znsx.cms.persistent.model.ServiceZone;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.Tollgate;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.AuthDeviceVO;
import com.znsx.cms.service.model.CsOrganVO;
import com.znsx.cms.service.model.GetOrganBridgeVO;
import com.znsx.cms.service.model.GetOrganRoadVO;
import com.znsx.cms.service.model.GetOrganTollgateVO;
import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.service.model.GetRoadMouthVO;
import com.znsx.cms.service.model.GetServiceZoneVO;
import com.znsx.cms.service.model.GetTollgateVO;
import com.znsx.cms.service.model.ListOrganDeviceTreeVO;
import com.znsx.cms.service.model.ListOrganVO;
import com.znsx.cms.service.model.OrganVO;
import com.znsx.cms.service.model.RoleResourcePermissionVO;
import com.znsx.cms.service.model.UserResourceVO;
import com.znsx.cms.web.dto.omc.ListOrganByNameDTO;
import com.znsx.cms.web.dto.omc.ListOrganByParentIdDTO;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.file.Configuration;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 机构业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 上午10:37:28
 */
public class OrganManagerImpl extends BaseManagerImpl implements OrganManager {
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DvrDAO dvrDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private DisplayWallDAO displayWallDAO;
	@Autowired
	private MonitorDAO monitorDAO;
	@Autowired
	private WindSpeedDAO windSpeedDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private FireDetectorDAO fdDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private NoDetectorDAO nodDAO;
	@Autowired
	private PushButtonDAO pbDAO;
	@Autowired
	private ControlDeviceDAO cdDAO;
	@Autowired
	private OrganTunnelDAO organTunnelDAO;
	@Autowired
	private OrganRoadDAO organRoadDAO;
	@Autowired
	private RoadMouthDAO roadMouthDAO;
	@Autowired
	private TollgateDAO tollgateDAO;
	@Autowired
	private ServiceZoneDAO serviceZoneDAO;
	@Autowired
	private OrganBridgeDAO organBridgeDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;

	public String createOrgan(@LogParam("name") String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String frontOrganId, String backOrganId,
			String stakeNumber) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		params.put("standardNumber", standardNumber);
		// list.clear();
		List<Organ> list = organDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "]is already exist");
		}
		params.clear();
		list.clear();
		Organ frontOrgan = null;
		if (StringUtils.isNotBlank(frontOrganId)) {
			frontOrgan = organDAO.findById(frontOrganId);
			// 根据前一路段id查询机构并且把机构的前一路段设置为空
			params.put("frontOrganId", frontOrganId);
			list = organDAO.findByPropertys(params);
			if (list.size() > 0) {
				list.get(0).setFrontOrganId("");
			}
			params.clear();
			list.clear();
		}

		Organ backOrgan = null;
		if (StringUtils.isNotBlank(backOrganId)) {
			backOrgan = organDAO.findById(backOrganId);
			// 根据后一路段id查询机构并且把机构的后一路段设置为空
			params.put("backOrganId", backOrganId);
			list = organDAO.findByPropertys(params);
			if (list.size() > 0) {
				list.get(0).setBackOrganId("");
			}
		}

		// String id = organDAO.getNextId("Organ", 1);
		Organ organ = new Organ();
		// organ.setId(id);
		organ.setAddress(address);
		organ.setAreaCode(areaCode);
		organ.setContact(contact);
		organ.setCreateTime(System.currentTimeMillis());
		organ.setFax(fax);
		organ.setName(name);
		organ.setNote(note);
		organ.setPhone(phone);
		organ.setStandardNumber(standardNumber);
		organ.setDeep(null);
		organ.setPath("");
		organ.setType(type);
		organ.setFrontOrganId(frontOrganId);
		organ.setBackOrganId(backOrganId);
		organ.setStakeNumber(stakeNumber);
		organDAO.save(organ);

		Organ o = organDAO.findById(organ.getId());
		String path = "/" + o.getId();
		if (StringUtils.isNotBlank(parentId)) {
			Organ parent = organDAO.findById(parentId);
			o.setParent(parent);
			path = parent.getPath() + path;
			// 目前对机构层级的创建不做限制,需要限制时根据上级机构的deep来判断
			o.setDeep(parent.getDeep() + 1);
		} else {
			o.setDeep(1);
		}
		o.setPath(path);

		if (null != frontOrgan) {
			frontOrgan.setBackOrganId(o.getId());
		}

		if (null != backOrgan) {
			backOrgan.setFrontOrganId(o.getId());
		}

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ORGAN);

		return o.getId();
	}

	public void updateOrgan(@LogParam("id") String id, String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String frontOrganId, String backOrganId, String stakeNumber)
			throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		if (null != standardNumber) {
			params.clear();
			params.put("standardNumber", standardNumber);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "]is already exist");
				}
			}
		}

		Organ organ = organDAO.findById(id);

		Organ frontOrgan = null;
		Organ backOrgan = null;

		String organFrontId = "";
		if (StringUtils.isBlank(frontOrganId)) {
			organFrontId = organ.getFrontOrganId();
		} else {
			frontOrgan = organDAO.findById(frontOrganId);
			// 根据前一路段id查询机构并且把机构的前一路段设置为空
			params.clear();
			params.put("frontOrganId", frontOrganId);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() > 0) {
				list.get(0).setFrontOrganId("");
			}

		}

		String organBackId = "";
		if (StringUtils.isBlank(backOrganId)) {
			organBackId = organ.getBackOrganId();
		} else {
			backOrgan = organDAO.findById(backOrganId);
			// 根据后一路段id查询机构并且把机构的后一路段设置为空
			params.clear();
			params.put("backOrganId", backOrganId);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() > 0) {
				list.get(0).setBackOrganId("");
			}
		}

		if (null != name) {

			organ.setName(name);
		}
		if (null != standardNumber) {
			syncSN(organ.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ORGAN);
			organ.setStandardNumber(standardNumber);
		}
		if (null != parentId) {
			Organ parent = organDAO.findById(parentId);
			organ.setParent(parent);
			String path = parent.getPath() + "/" + id;
			organ.setPath(path);
		}
		if (null != fax) {
			organ.setFax(fax);
		}
		if (null != contact) {
			organ.setContact(contact);
		}
		if (null != phone) {
			organ.setPhone(phone);
		}
		if (null != address) {
			organ.setAddress(address);
		}
		if (null != areaCode) {
			organ.setAreaCode(areaCode);
		}
		if (null != note) {
			organ.setNote(note);
		}
		if (null != frontOrganId) {
			organ.setFrontOrganId(frontOrganId);
		}
		if (null != backOrganId) {
			organ.setBackOrganId(backOrganId);
		}
		if (null != stakeNumber) {
			organ.setStakeNumber(stakeNumber);
		}

		// 强制更新前后机构id
		if (StringUtils.isNotBlank(organFrontId)) {
			Organ organFront = organDAO.findById(organFrontId);
			organFront.setBackOrganId("");
		}

		if (StringUtils.isNotBlank(organBackId)) {
			Organ organBack = organDAO.findById(organBackId);
			organBack.setFrontOrganId("");
		}

		if (null != frontOrgan) {
			frontOrgan.setBackOrganId(id);
		}

		if (null != backOrgan) {
			backOrgan.setFrontOrganId(id);
		}
	}

	public void deleteOrgan(@LogParam("id") String id) throws BusinessException {
		// 检查是否有子机构
		List<Organ> children = organDAO.listOrgan(id, null, 0, 1);
		if (children.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child organ found !");
		}
		// 检查是否有子用户
		String organs[] = organDAO.findOrgansByOrganId(id);
		List<User> users = userDAO.listUser(organs, null, null, 0, 1);
		if (users.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child user found !");
		}
		// 检查是否有子DVR
		List<Dvr> dvrs = dvrDAO.listDvr(id, null);
		if (dvrs.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child dvr found !");
		}
		// 检查是否有子Camera
		List<Camera> cameras = cameraDAO.listCamera(id, null);
		if (cameras.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child camera found !");
		}
		// 检查是否有子用户角色
		int roleCount = roleDAO.countOrganRole(id);
		if (roleCount > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child role found !");
		}

		// 检查是否有电视墙在机构下
		List<DisplayWall> displayWalls = displayWallDAO.listWall(id, 0, 10);
		if (displayWalls.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child displaywall found !");
		}

		Organ organ = organDAO.findById(id);
		// 删除关联的图片
		if (organ.getImageId() != null) {
			imageDAO.deleteById(organ.getImageId());
		}

		// 删除机构时把该机构的前后路段id设置成""
		if (StringUtils.isNotBlank(organ.getFrontOrganId())) {
			Organ frontOrgan = organDAO.findById(organ.getFrontOrganId());
			frontOrgan.setBackOrganId("");
		}
		if (StringUtils.isNotBlank(organ.getBackOrganId())) {
			Organ backOrgan = organDAO.findById(organ.getBackOrganId());
			backOrgan.setFrontOrganId("");
		}

		// 检查风速风向检测器是否存在
		List<WindSpeed> ws = windSpeedDAO.listWindSpeed(id, null, null, null,
				0, 10);
		if (ws.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child windspeed found !");
		}

		// 检查车辆检测器是否存在
		List<VehicleDetector> vd = vdDAO.listVehicleDetector(id, null, null,
				null, 0, 10);
		if (vd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child vehicleDetector found !");
		}

		// 检查气象检测器是否存在
		List<WeatherStat> wst = wstDAO.listWeatherStat(id, null, null, null, 0,
				10);
		if (wst.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child weatherstat found !");
		}

		// 检查光强检测器是否存在
		List<LoLi> loli = loliDAO.listLoli(id, null, null, null, 0, 10);
		if (loli.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child loli found !");
		}

		// 检查火灾检测器是否存在
		List<FireDetector> fd = fdDAO.listFireDetector(id, null, null, null, 0,
				10);
		if (fd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child fireDetector found !");
		}

		// 检查covi检测器是否存在
		List<Covi> covi = coviDAO.listCovi(id, null, null, null, 0, 10);
		if (covi.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child covi found !");
		}

		// 检查氮氧化合物检测器是否存在
		List<NoDetector> nod = nodDAO.listNoDetector(id, null, null, null, 0,
				10);
		if (nod.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child noDetector found !");
		}

		// 检查手动报警按钮是否存在
		List<PushButton> pb = pbDAO.listPushButton(id, null, null, null, 0, 10);
		if (pb.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child pushButton found !");
		}

		// 检查检测器是否存在
		List<ControlDevice> cd = cdDAO.listControlDevice(id, null, null, null,
				0, 10, null, null);
		if (cd.size() > 0) {
			throw new BusinessException(ErrorCode.RESOURCE_ORGAN_EXIST,
					"Child controlDevice found !");
		}

		// 同步标准号
		syncSN(organ.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_ORGAN);
		// 最后删除机构
		organDAO.delete(organ);

	}

	@Override
	public void bindImage(String organId, String imageId)
			throws BusinessException {
		Organ organ = organDAO.findById(organId);
		organ.setImageId(imageId);
	}

	public List<Organ> listOrganUtil(String parentId, String name,
			Integer startIndex, Integer limit) {
		Integer finalIndex = new Integer(startIndex);
		Integer finalLimit = new Integer(limit);
		// 如果parentId不为空,将自身也加入到返回列表中
		if (StringUtils.isNotBlank(parentId) && startIndex == 0 && limit != 0) {
			finalLimit = limit - 1;
		}

		if (startIndex != 0) {
			finalIndex = startIndex - 1;
		}
		List<Organ> organs = organDAO.listOrgan(parentId, name, finalIndex,
				finalLimit);
		if (StringUtils.isNotBlank(parentId) && startIndex == 0 && limit != 0) {
			organs.add(organDAO.findById(parentId));
		}
		return organs;
	}

	public List<GetOrganVO> setOrganVO(List<Organ> organs) {
		List<GetOrganVO> list = new ArrayList<GetOrganVO>();

		for (Organ organ : organs) {
			GetOrganVO vo = new GetOrganVO();
			vo.setAddress(organ.getAddress());
			vo.setAreaCode(organ.getAreaCode());
			vo.setContact(organ.getContact());
			vo.setCreateTime(organ.getCreateTime().toString());
			vo.setFax(organ.getFax());
			vo.setId(organ.getId());
			vo.setImageId(organ.getImageId() != null ? organ.getImageId()
					.toString() : "");
			vo.setName(organ.getName());
			vo.setNote(organ.getNote());
			Organ parent = organ.getParent();
			if (null != parent) {
				vo.setParentId(parent.getId());
				vo.setParentName(parent.getName());
			}
			vo.setPath(organ.getPath());
			vo.setPhone(organ.getPhone());
			vo.setStandardNumber(organ.getStandardNumber());
			vo.setType(organ.getType());
			vo.setDeep(organ.getDeep() + "");
			vo.setFrontOrganId(organ.getFrontOrganId());
			vo.setBackOrganId(organ.getBackOrganId());
			vo.setStakeNumber(organ.getStakeNumber());

			list.add(vo);
		}
		return list;
	}

	@Override
	public List<GetOrganVO> listOrgan(String parentId, String name,
			Integer startIndex, Integer limit) throws BusinessException {

		List<Organ> organs = listOrganUtil(parentId, name, startIndex, limit);

		return setOrganVO(organs);
	}

	@Override
	public OrganVO getOrganTree(String organId) throws BusinessException {
		// 机构树
		OrganVO vo = new OrganVO();

		Organ organ = organDAO.findById(organId);
		vo.setId(organ.getId());
		vo.setAddress(organ.getAddress());
		vo.setAreaCode(organ.getAreaCode());
		vo.setContact(organ.getContact());
		vo.setCreateTime(organ.getCreateTime().toString());
		vo.setFax(organ.getFax());
		vo.setImageId(organ.getImageId() != null ? organ.getImageId()
				.toString() : "");
		vo.setName(organ.getName());
		vo.setNote(organ.getNote());
		Organ parent = organ.getParent();
		vo.setParentId(null == parent ? "" : parent.getId());
		vo.setPath(organ.getPath());
		vo.setPhone(organ.getPhone());
		vo.setStandardNumber(organ.getStandardNumber());

		List<OrganVO> children = new ArrayList<OrganVO>();

		// 查询子机构
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("parent.id", organId);
		List<Organ> organs = organDAO.findByPropertys(params);
		for (Organ o : organs) {
			OrganVO child = getOrganTree(o.getId());
			children.add(child);
		}
		vo.setChildren(children);
		return vo;
	}

	public Element listOrganDevice(String organId, boolean isRec,
			List<RoleResourcePermissionVO> devices, boolean isAdmin)
			throws Exception {
		// 自身属性
		Organ organ = organDAO.findById(organId);
		CsOrganVO vo = new CsOrganVO();
		vo.setId(organ.getId());
		vo.setStandardNumber(organ.getStandardNumber());
		vo.setImageId(organ.getImageId() != null ? organ.getImageId()
				.toString() : "");
		vo.setName(organ.getName());
		// 机构节点
		Element nodeElement = ElementUtil.createElement("Node", vo, null, null);

		// 摄像头列表
		Element channelList = new Element("ChannelList");
		nodeElement.addContent(channelList);

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("organ.id", organId);
		List<Camera> cameras = cameraDAO.findByPropertys(map);
		// 权限列表找到一个匹配设备的索引号,用于快速移除
		int removeIndex = -1;

		for (Camera c : cameras) {
			// 如果是管理员则全部设备都添加进去
			if (isAdmin) {
				Element channel = new Element("Channel");
				channelList.addContent(setChannel(channel, c,
						TypeDefinition.ADMIN_AUTH));
			} else {
				// 如果不是管理员则根据前边查的设备ID进行判断
				if (devices.size() > 0) {
					for (int i = 0; i < devices.size(); i++) {
						RoleResourcePermissionVO rrp = devices.get(i);
						if (rrp.getDeviceId().equals(c.getId())) {
							Element channel = new Element("channel");
							channelList.addContent(setChannel(channel, c,
									translateAuth(rrp.getPrivilege())));
							removeIndex = i;
							break;
						}
					}
					// 移除匹配上的摄像头
					if (removeIndex >= 0) {
						devices.remove(removeIndex);
						removeIndex = -1;
					}
				}
			}
		}

		Element subNodesElement = new Element("SubNodes");
		nodeElement.addContent(subNodesElement);
		// 如果需要递归查询子节点
		if (isRec) {
			map.clear();
			map.put("parent.id", organId);
			List<Organ> organs = organDAO.findByPropertys(map);
			List<Element> children = new ArrayList<Element>();
			for (Organ o : organs) {
				Element child = listOrganDevice(o.getId(), isRec, devices,
						isAdmin);
				children.add(child);
			}
			subNodesElement.addContent(children);
		}
		return nodeElement;
	}

	private Element setChannel(Element channel, Camera c, String auth) {
		channel.setAttribute("Id", c.getId());
		channel.setAttribute("StandardNumber",
				c.getStandardNumber() != null ? c.getStandardNumber() : "");
		channel.setAttribute("Name", c.getName() != null ? c.getName() : "");
		channel.setAttribute("SubType", c.getSubType() != null ? c.getSubType()
				: "");
		channel.setAttribute("Status", c.getStatus() == null ? "" : c
				.getStatus().toString());
		channel.setAttribute("Auth", auth != null ? auth : "");
		channel.setAttribute("ImageId",
				c.getProperty().getImageId() == null ? "" : c.getProperty()
						.getImageId().toString());
		channel.setAttribute("ChannelNumber", c.getChannelNumber() == null ? ""
				: c.getChannelNumber().toString());
		return channel;
	}

	@Override
	public GetOrganVO getOrgan(String id) throws BusinessException {
		Organ organ = organDAO.findById(id);
		GetOrganVO vo = new GetOrganVO();
		vo.setAddress(organ.getAddress());
		vo.setAreaCode(organ.getAreaCode());
		vo.setContact(organ.getContact());
		vo.setCreateTime(organ.getCreateTime().toString());
		vo.setFax(organ.getFax());
		vo.setId(organ.getId());
		vo.setImageId(organ.getImageId() != null ? organ.getImageId()
				.toString() : "");
		vo.setName(organ.getName());
		vo.setNote(organ.getNote());
		Organ parent = organ.getParent();
		if (null != parent) {
			vo.setParentId(parent.getId());
			vo.setParentName(parent.getName());
		}
		vo.setPath(organ.getPath());
		vo.setPhone(organ.getPhone());
		vo.setStandardNumber(organ.getStandardNumber());
		vo.setType(organ.getType());
		vo.setFrontOrganId(organ.getFrontOrganId());
		vo.setBackOrganId(organ.getBackOrganId());
		vo.setStakeNumber(organ.getStakeNumber());
		return vo;
	}

	private String translateAuth(String auth) {
		String[] array = auth.split(",");
		int rtn = 0;
		for (int i = 0; i < array.length; i++) {
			rtn += Integer.parseInt(array[i]);
		}
		return "%0" + rtn;
	}

	@Override
	public List<ListOrganVO> listOrganById(String organId) {
		List<Organ> organs = organDAO.listOrganById(organId);
		List<ListOrganVO> listVO = new ArrayList<ListOrganVO>();
		for (Organ organ : organs) {
			ListOrganVO vo = new ListOrganVO();
			vo.setAddress(organ.getAddress());
			vo.setAreaCode(organ.getAreaCode());
			vo.setContact(organ.getContact());
			vo.setCreateTime(organ.getCreateTime().toString());
			vo.setDeep(organ.getDeep() + "");
			vo.setFax(organ.getFax());
			vo.setId(organ.getId());
			vo.setImageId(organ.getImageId() != null ? organ.getImageId()
					.toString() : "");
			vo.setName(organ.getName());
			vo.setNote(organ.getNote());
			vo.setParentId(organ.getParent() != null ? organ.getParent()
					.getId() : "");
			vo.setParentName(organ.getParent() != null ? organ.getParent()
					.getName() : "");
			vo.setPath(organ.getPath());
			vo.setPhone(organ.getPhone());
			vo.setStandardNumber(organ.getStandardNumber());
			vo.setType(organ.getType());
			listVO.add(vo);
		}
		return listVO;
	}

	public Organ getRootOrgan() {
		return organDAO.getRootOrgan();
	}

	@Override
	public Integer organTotalCount(String parentId, String organName) {
		return organDAO.organTotalCount(parentId, organName);
	}

	@Override
	public ListOrganByNameDTO listOrganByName(String name, String logonUserId,
			Integer startIndex, Integer limit, String organId,
			String standardNumber, String type) {
		ListOrganByNameDTO dto = new ListOrganByNameDTO();
		List<Organ> listOrgan = new ArrayList<Organ>();
		List<GetOrganVO> getOrgan = new ArrayList<GetOrganVO>();
		Integer totalCount = null;
		User user = userDAO.findById(logonUserId);
		String userOrganId = user.getOrgan().getId();
		String organIds[] = organDAO.findOrgansByOrganId(userOrganId);
		if (StringUtils.isBlank(name) && StringUtils.isBlank(standardNumber)
				&& StringUtils.isBlank(type)) {
			totalCount = organTotalCount(organId, name);
			totalCount += 1;
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount.intValue() != 0) {
				if (startIndex.intValue() >= totalCount.intValue()) {
					startIndex -= ((startIndex.intValue() - totalCount
							.intValue()) / limit + 1)
							* limit;
				}
			}
			listOrgan = listOrganUtil(organId, name, startIndex, limit);
			getOrgan = setOrganVO(listOrgan);
		} else {
			totalCount = organDAO.listOrganTotalCount(organIds, name,
					standardNumber, type);
			// omc客户端查询列表时，如果是删除一页的最后一条信息，那么查询它前一页数据
			if (startIndex != 0 && totalCount.intValue() != 0) {
				if (startIndex.intValue() >= totalCount.intValue()) {
					startIndex -= ((startIndex.intValue() - totalCount
							.intValue()) / limit + 1)
							* limit;
				}
			}
			listOrgan = organDAO.listOrganByName(organIds, name, startIndex,
					limit, standardNumber, type);
			getOrgan = setOrganVO(listOrgan);
		}
		dto.setOrganList(getOrgan);

		dto.setTotalCount(totalCount + "");
		return dto;
	}

	@Override
	public String[] listOrganAllChildren(String organId) {
		return organDAO.findOrgansByOrganId(organId);
	}

	@Override
	public Element recursiveOrganDevice(String organId,
			List<AuthCameraVO> devices, boolean isRec) throws BusinessException {
		// 获取所有子机构
		List<Organ> organs = organDAO.listOrganById(organId);
		// 自身信息
		Organ o = organDAO.findById(organId);
		Element parent = new Element("Node");
		parent.setAttribute("Id", o.getId());
		parent.setAttribute("StandardNumber",
				o.getStandardNumber() != null ? o.getStandardNumber() : "");
		parent.setAttribute("Name", o.getName() != null ? o.getName() : "");
		parent.setAttribute("Type", o.getType() != null ? o.getType() : "");
		parent.setAttribute("ImageId", o.getImageId() != null ? o.getImageId()
				.toString() : "");

		// 设备信息
		Element channelList = new Element("ChannelList");
		parent.addContent(channelList);
		for (AuthCameraVO camera : devices) {
			if (camera.getOrganId().equals(organId)) {
				Element channel = new Element("Channel");
				channel.setAttribute("Id", camera.getId());
				channel.setAttribute(
						"StandardNumber",
						camera.getStandardNumber() != null ? camera
								.getStandardNumber() : "");
				channel.setAttribute("Name",
						camera.getName() != null ? camera.getName() : "");
				channel.setAttribute("SubType",
						camera.getSubType() != null ? camera.getSubType() : "");
				channel.setAttribute("Status",
						camera.getStatus() != null ? camera.getStatus() : "");
				channel.setAttribute(
						"Auth",
						translateAuth(camera.getAuth()) != null ? translateAuth(camera
								.getAuth()) : "");
				channel.setAttribute("ImageId",
						camera.getImageId() != null ? camera.getImageId() : "");
				channel.setAttribute(
						"ChannelNumber",
						camera.getChannelNumber() != null ? camera
								.getChannelNumber() : "");
				channel.setAttribute(
						"Location",
						camera.getLocation() == null ? "" : camera
								.getLocation());
				channelList.addContent(channel);
			}
		}

		// 子节点
		Element subNodes = new Element("SubNodes");
		parent.addContent(subNodes);

		// 构建机构树状关系
		if (isRec) {
			parent = treeOrgan(organs, devices, parent);
		}
		// 子机构列表构建
		else {
			for (Organ child : organs) {
				if (!child.getId().equals(organId)) {
					if (child.getParent().getId().equals(organId)) {
						Element subNode = new Element("Node");
						subNode.setAttribute("Id", child.getId());
						subNode.setAttribute(
								"StandardNumber",
								child.getStandardNumber() != null ? child
										.getStandardNumber() : "");
						subNode.setAttribute("Name",
								child.getName() != null ? child.getName() : "");
						subNode.setAttribute("Type",
								child.getType() != null ? child.getType() : "");
						subNode.setAttribute("ImageId",
								child.getImageId() != null ? child.getImageId()
										.toString() : "");
						subNodes.addContent(subNode);
					}
				}
			}
		}

		return parent;
	}

	/**
	 * 将机构列表转换为机构树
	 * 
	 * @param organs
	 *            机构列表
	 * @param parent
	 *            父机构
	 * @return 机构树
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-29 上午10:02:23
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
					organNode.setAttribute("Type",
							organ.getType() != null ? organ.getType() : "");
					organNode.setAttribute("ImageId",
							organ.getImageId() != null ? organ.getImageId()
									.toString() : "");
					// 设备信息
					Element channelList = new Element("ChannelList");
					organNode.addContent(channelList);
					for (AuthCameraVO camera : devices) {
						if (camera.getOrganId().equals(organ.getId())) {
							Element channel = new Element("Channel");
							channel.setAttribute("Id", camera.getId());
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
									"Status",
									camera.getStatus() != null ? camera
											.getStatus() : "");
							channel.setAttribute(
									"Auth",
									translateAuth(camera.getAuth()) != null ? translateAuth(camera
											.getAuth()) : "");
							channel.setAttribute(
									"ImageId",
									camera.getImageId() != null ? camera
											.getImageId() : "");
							channel.setAttribute(
									"ChannelNumber",
									camera.getChannelNumber() != null ? camera
											.getChannelNumber() : "");
							channel.setAttribute(
									"Location",
									camera.getLocation() == null ? "" : camera
											.getLocation());
							channelList.addContent(channel);
						}
					}

					// 子节点
					Element subNodes = new Element("SubNodes");
					organNode.addContent(subNodes);

					// 构建子机构
					organNode = treeOrgan(organs, devices, organNode);
					parent.getChild("SubNodes").addContent(organNode);
				}
			}
		}
		return parent;
	}

	@Override
	public Organ getOrganById(String organId) {
		Organ organ = organDAO.findById(organId);
		if (organ == null) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"parameter organId[" + organId + "] invalid !");
		}
		return organ;
	}

	/**
	 * 递归生成子机构树
	 * 
	 * @param organs
	 *            机构列表
	 * @param devices
	 *            所有的权限设备对象列表
	 * @param parent
	 *            父节点
	 * @return 机构树
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-21 下午3:47:19
	 */
	private Element recursiveOrgan(List<Organ> organs,
			Collection<AuthDeviceVO> devices, Element parent) {
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
					organNode.setAttribute("Type",
							organ.getType() != null ? organ.getType() : "");
					organNode.setAttribute("ImageId",
							organ.getImageId() != null ? organ.getImageId()
									.toString() : "");
					organNode.setAttribute("Longitude",
							organ.getLongitude() != null ? organ.getLongitude()
									: "");
					organNode.setAttribute("Latitude",
							organ.getLatitude() != null ? organ.getLatitude()
									: "");
					organNode.setAttribute("StakeNumber", organ
							.getStakeNumber() != null ? organ.getStakeNumber()
							: "");
					organNode.setAttribute("Note",
							organ.getNote() != null ? organ.getNote() : "");
					// 根据机构类型设置信息
					setParentByType(organNode, organ);
					// 设备信息
					Element channelList = new Element("ChannelList");
					organNode.addContent(channelList);
					Element vehicleDetectorList = new Element(
							"VehicleDetectorList");
					organNode.addContent(vehicleDetectorList);
					Element windSpeedList = new Element("WindSpeedList");
					organNode.addContent(windSpeedList);
					Element weatherStatList = new Element("WeatherStatList");
					organNode.addContent(weatherStatList);
					Element loLiList = new Element("LoLiList");
					organNode.addContent(loLiList);
					Element fireDetectorList = new Element("FireDetectorList");
					organNode.addContent(fireDetectorList);
					Element coViList = new Element("CoViList");
					organNode.addContent(coViList);
					Element noDetectorList = new Element("NoDetectorList");
					organNode.addContent(noDetectorList);
					Element cMSList = new Element("CMSList");
					organNode.addContent(cMSList);
					Element fanList = new Element("FanList");
					organNode.addContent(fanList);
					Element lightList = new Element("LightList");
					organNode.addContent(lightList);
					Element rollingDoorList = new Element("RollingDoorList");
					organNode.addContent(rollingDoorList);
					Element waterPumpList = new Element("WaterPumpList");
					organNode.addContent(waterPumpList);
					Element railList = new Element("RailList");
					organNode.addContent(railList);
					Element inductionSignList = new Element("InductionSignList");
					organNode.addContent(inductionSignList);
					Element pushButtonList = new Element("PushButtonList");
					organNode.addContent(pushButtonList);
					Element wareHouseList = new Element("WareHouseList");
					organNode.addContent(wareHouseList);
					Element trafficSignList = new Element("TrafficSignList");
					organNode.addContent(trafficSignList);
					Element laneIndicationList = new Element(
							"LaneIndicationList");
					organNode.addContent(laneIndicationList);
					Element boxTransformerList = new Element(
							"BoxTransformerList");
					organNode.addContent(boxTransformerList);
					Element viDetectorList = new Element("ViDetectorList");
					organNode.addContent(viDetectorList);
					Element roadDetectorList = new Element("RoadDetectorList");
					organNode.addContent(roadDetectorList);
					Element bridgeDetectorList = new Element(
							"BridgeDetectorList");
					organNode.addContent(bridgeDetectorList);
					Element urgentPhoneList = new Element("UrgentPhoneList");
					organNode.addContent(urgentPhoneList);

					for (AuthDeviceVO vo : devices) {
						if (vo.getOrganId().equals(organ.getId())) {
							// 摄像头
							if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_CAMERA + "")) {
								Element channel = new Element("Channel");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"SubType",
										vo.getSubType() != null ? vo
												.getSubType() : "");
								channel.setAttribute("Status",
										vo.getStatus() != null ? vo.getStatus()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"ImageId",
										vo.getImageId() != null ? vo
												.getImageId() : "");
								channel.setAttribute(
										"ChannelNumber",
										vo.getChannelNumber() != null ? vo
												.getChannelNumber() : "");
								channel.setAttribute(
										"Location",
										vo.getLocation() != null ? vo
												.getLocation() : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"SolarSN",
										vo.getSolarSN() != null ? vo
												.getSolarSN() : "");
								channel.setAttribute(
										"SolarName",
										vo.getSolarName() != null ? vo
												.getSolarName() : "");
								channel.setAttribute(
										"SolarNavigation",
										vo.getSolarNavigation() != null ? vo
												.getSolarNavigation() : "");
								channel.setAttribute(
										"SolarStakeNumber",
										vo.getSolarStakeNumber() != null ? vo
												.getSolarStakeNumber() : "");
								channel.setAttribute(
										"BatteryCapacity",
										vo.getBatteryCapacity() != null ? vo
												.getBatteryCapacity() : "");
								channel.setAttribute("CcsSN",
										vo.getCcsSN() != null ? vo.getCcsSN()
												: "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channelList.addContent(channel);
							}
							// 车检器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_VD + "")) {
								Element channel = new Element("VehicleDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"SolarSN",
										vo.getSolarSN() != null ? vo
												.getSolarSN() : "");
								channel.setAttribute(
										"SolarName",
										vo.getSolarName() != null ? vo
												.getSolarName() : "");
								channel.setAttribute(
										"SolarNavigation",
										vo.getSolarNavigation() != null ? vo
												.getSolarNavigation() : "");
								channel.setAttribute(
										"SolarStakeNumber",
										vo.getSolarStakeNumber() != null ? vo
												.getSolarStakeNumber() : "");
								channel.setAttribute(
										"BatteryCapacity",
										vo.getBatteryCapacity() != null ? vo
												.getBatteryCapacity() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"SpeedUpLimit",
										vo.getSpeedUpLimit() != null ? vo
												.getSpeedUpLimit() + "" : "");
								channel.setAttribute(
										"SpeedLowLimit",
										vo.getSpeedLowLimit() != null ? vo
												.getSpeedLowLimit() + "" : "");
								channel.setAttribute(
										"OccUpLimit",
										vo.getOccUpLimit() != null ? vo
												.getOccUpLimit() + "" : "");
								channel.setAttribute(
										"OccLowLimit",
										vo.getOccLowLimit() != null ? vo
												.getOccLowLimit() + "" : "");
								channel.setAttribute(
										"VolumeLowLimit",
										vo.getVolumeLowLimit() != null ? vo
												.getVolumeLowLimit() + "" : "");
								channel.setAttribute(
										"VolumeUpLimit",
										vo.getVolumeUpLimit() != null ? vo
												.getVolumeUpLimit() + "" : "");
								vehicleDetectorList.addContent(channel);
							}
							// 气象检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_WST + "")) {
								Element channel = new Element("WeatherStat");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"ViLowLimit",
										vo.getViLowLimit() != null ? vo
												.getViLowLimit() + "" : "");
								channel.setAttribute(
										"WindUpLimit",
										vo.getWindUpLimit() != null ? vo
												.getWindUpLimit() + "" : "");
								channel.setAttribute(
										"RainUpLimit",
										vo.getRainUpLimit() != null ? vo
												.getRainUpLimit() + "" : "");
								channel.setAttribute(
										"SnowUpLimit",
										vo.getSnowUpLimit() != null ? vo
												.getSnowUpLimit() + "" : "");
								weatherStatList.addContent(channel);
							}
							// 风速风向检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_WS + "")) {
								Element channel = new Element("WindSpeed");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"WindUpLimit",
										vo.getWindUpLimit() != null ? vo
												.getWindUpLimit() + "" : "");
								windSpeedList.addContent(channel);
							}
							// 光强检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_LOLI + "")) {
								Element channel = new Element("LoLi");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"LiLumiMax",
										vo.getLiLumiMax() != null ? vo
												.getLiLumiMax() + "" : "");
								channel.setAttribute(
										"LiLumiMin",
										vo.getLiLumiMin() != null ? vo
												.getLiLumiMin() + "" : "");
								channel.setAttribute(
										"LoLumiMax",
										vo.getLoLumiMax() != null ? vo
												.getLoLumiMax() + "" : "");
								channel.setAttribute(
										"LoLumiMin",
										vo.getLoLumiMin() != null ? vo
												.getLoLumiMin() + "" : "");
								loLiList.addContent(channel);
							}
							// 火灾检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_FD + "")) {
								Element channel = new Element("FireDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								fireDetectorList.addContent(channel);
							}
							// 一氧化碳/能见度检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_COVI + "")) {
								Element channel = new Element("CoVi");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"CoConctLimit",
										vo.getCoConctLimit() != null ? vo
												.getCoConctLimit() + "" : "");
								channel.setAttribute(
										"ViLowLimit",
										vo.getViLowLimit() != null ? vo
												.getViLowLimit() + "" : "");
								coViList.addContent(channel);
							}
							// 氮氧化物检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_NOD + "")) {
								Element channel = new Element("NoDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"NoConctLimit",
										vo.getNoConctLimit() != null ? vo
												.getNoConctLimit() + "" : "");
								channel.setAttribute(
										"NooConctLimit",
										vo.getNooConctLimit() != null ? vo
												.getNooConctLimit() + "" : "");
								noDetectorList.addContent(channel);
							}
							// 可变信息标志
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_CMS + "")) {
								Element channel = new Element("Cms");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute(
										"SubType",
										vo.getSubType() != null ? vo
												.getSubType() : "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");

								channel.setAttribute("Width",
										vo.getWidth() != null ? vo.getWidth()
												: "");
								channel.setAttribute("Height",
										vo.getHeight() != null ? vo.getHeight()
												: "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								cMSList.addContent(channel);
							}
							// 风机
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_FAN + "")) {
								Element channel = new Element("Fan");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								fanList.addContent(channel);
							}
							// 灯光
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
								Element channel = new Element("Light");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								lightList.addContent(channel);
							}
							// 卷帘门
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_RD + "")) {
								Element channel = new Element("RollingDoor");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								channel.setAttribute(
										"SubType",
										vo.getSubType() != null ? vo
												.getSubType() : "");
								rollingDoorList.addContent(channel);
							}
							// 水泵
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_WP + "")) {
								Element channel = new Element("WaterPump");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								waterPumpList.addContent(channel);
							}
							// 栏杆机
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_RAIL + "")) {
								Element channel = new Element("Rail");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								railList.addContent(channel);
							}
							// 电光诱导标志
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_IS + "")) {
								Element channel = new Element("InductionSign");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								inductionSignList.addContent(channel);
							}
							// 手动报警按钮
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_PB + "")) {
								Element channel = new Element("PushButton");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								pushButtonList.addContent(channel);
							} else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_WH + "")) {
								Element channel = new Element("WareHouse");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute(
										"Location",
										vo.getLocation() != null ? vo
												.getLocation() : "");
								channel.setAttribute(
										"Telephone",
										vo.getTelephone() != null ? vo
												.getTelephone() : "");
								channel.setAttribute(
										"LinkMan",
										vo.getLinkMan() != null ? vo
												.getLinkMan() : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute(
										"ManagerUnit",
										vo.getManagerUnit() != null ? vo
												.getManagerUnit() : "");
								wareHouseList.addContent(channel);
							}
							// 交通信号灯
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_TSL + "")) {
								Element channel = new Element("TrafficSign");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute("Width",
										vo.getWidth() != null ? vo.getWidth()
												: "");
								channel.setAttribute("Height",
										vo.getHeight() != null ? vo.getHeight()
												: "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								trafficSignList.addContent(channel);
							}
							// 车道指示灯
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_LIL + "")) {
								Element channel = new Element("LaneIndication");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute("Width",
										vo.getWidth() != null ? vo.getWidth()
												: "");
								channel.setAttribute("Height",
										vo.getHeight() != null ? vo.getHeight()
												: "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								laneIndicationList.addContent(channel);
							}
							// 箱变电力监控
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_BT + "")) {
								Element channel = new Element("BoxTransformer");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								boxTransformerList.addContent(channel);
							}
							// 能见度仪
							else if (vo
									.getType()
									.equals(TypeDefinition.DEVICE_TYPE_VI_DETECTOR
											+ "")) {
								Element channel = new Element("ViDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								viDetectorList.addContent(channel);
							}
							// 路面检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR
											+ "")) {
								Element channel = new Element("RoadDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								roadDetectorList.addContent(channel);
							}
							// 桥面检测器
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR
											+ "")) {
								Element channel = new Element("BridgeDetector");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								bridgeDetectorList.addContent(channel);
							}
							// 紧急电话
							else if (vo.getType().equals(
									TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE
											+ "")) {
								Element channel = new Element("UrgentPhone");
								channel.setAttribute("Id", vo.getId());
								channel.setAttribute(
										"StandardNumber",
										vo.getStandardNumber() != null ? vo
												.getStandardNumber() : "");
								channel.setAttribute("Name",
										vo.getName() != null ? vo.getName()
												: "");
								channel.setAttribute(
										"Auth",
										translateAuth(vo.getAuth()) != null ? translateAuth(vo
												.getAuth()) : "");
								channel.setAttribute(
										"Longitude",
										vo.getLongitude() != null ? vo
												.getLongitude() : "");
								channel.setAttribute(
										"Latitude",
										vo.getLatitude() != null ? vo
												.getLatitude() : "");
								channel.setAttribute(
										"StakeNumber",
										vo.getStakeNumber() != null ? vo
												.getStakeNumber() : "");
								channel.setAttribute("Type",
										vo.getType() != null ? vo.getType()
												: "");
								channel.setAttribute("DasSN",
										vo.getDasSN() != null ? vo.getDasSN()
												: "");
								channel.setAttribute(
										"Navigation",
										vo.getNavigation() != null ? vo
												.getNavigation() : "");
								channel.setAttribute(
										"Manufacture",
										vo.getManufacture() != null ? vo
												.getManufacture() : "");
								urgentPhoneList.addContent(channel);
							}
						}
					}

					// 子节点
					Element subNodes = new Element("SubNodes");
					organNode.addContent(subNodes);

					// 构建子机构
					organNode = recursiveOrgan(organs, devices, organNode);
					parent.getChild("SubNodes").addContent(organNode);
				}
			}
		}
		return parent;
	}

	@Override
	public ListOrganByParentIdDTO listOrganByParentId(String parentId) {
		List<Organ> organs = organDAO.listOrganByParentId(parentId);
		List<GetOrganVO> organList = setOrganVO(organs);
		ListOrganByParentIdDTO dto = new ListOrganByParentIdDTO();
		dto.setOrganList(organList);
		return dto;
	}

	@Override
	public void isRoadExist(String id, String frontOrganId, String backOrganId,
			String createOrUpdate) {
		Organ organ = null;
		if (StringUtils.isNotBlank(id)) {
			organ = organDAO.findById(id);
		}
		if (createOrUpdate.equals("0")) {
			isCreateRoadExist(organ, frontOrganId, backOrganId);
		} else if (createOrUpdate.equals("1")) {
			isUpdateRoadExist(organ, frontOrganId, backOrganId);
		} else {
			throw new BusinessException(ErrorCode.ERROR,
					"createOrUpdate is error:" + createOrUpdate);
		}

	}

	private void isUpdateRoadExist(Organ organ, String frontOrganId,
			String backOrganId) {
		if (organ == null) {
			throw new BusinessException(ErrorCode.ERROR, "organ is null");
		}
		if (StringUtils.isNotBlank(frontOrganId)
				&& StringUtils.isNotBlank(backOrganId)) {
			Organ frontOrgan = organDAO.findById(frontOrganId);
			Organ backOrgan = organDAO.findById(backOrganId);
			if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())
					&& StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				if (!frontOrgan.getBackOrganId().equals(organ.getId())
						&& !backOrgan.getFrontOrganId().equals(organ.getId())) {
					throw new BusinessException(
							ErrorCode.FRONT_AND_BACK_ORGAN_EXIST,
							"front and back organ exist ,"
									+ organDAO.findById(
											frontOrgan.getBackOrganId())
											.getName()
									+ ","
									+ organDAO.findById(
											backOrgan.getFrontOrganId())
											.getName());
				} else if (!frontOrgan.getBackOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.FRONT_ORGAN_ID_EXIST,
							"front organ exist ,"
									+ organDAO.findById(
											frontOrgan.getBackOrganId())
											.getName());
				} else if (!backOrgan.getFrontOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.BACK_ORGAN_ID_EXIST,
							"back organ exist ,"
									+ organDAO.findById(
											backOrgan.getFrontOrganId())
											.getName());
				}
			} else if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())) {
				if (!frontOrgan.getBackOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.FRONT_ORGAN_ID_EXIST,
							"front organ exist ,"
									+ organDAO.findById(
											frontOrgan.getBackOrganId())
											.getName());
				}
			} else if (StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				if (!backOrgan.getFrontOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.BACK_ORGAN_ID_EXIST,
							"back organ exist ,"
									+ organDAO.findById(
											backOrgan.getFrontOrganId())
											.getName());
				}
			}
		} else if (StringUtils.isNotBlank(frontOrganId)) {
			Organ frontOrgan = organDAO.findById(frontOrganId);
			if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())) {
				if (!frontOrgan.getBackOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.FRONT_ORGAN_ID_EXIST,
							"front organ exist ,"
									+ organDAO.findById(
											frontOrgan.getBackOrganId())
											.getName());
				}
			}
		} else if (StringUtils.isNotBlank(backOrganId)) {
			Organ backOrgan = organDAO.findById(backOrganId);
			if (StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				if (!backOrgan.getFrontOrganId().equals(organ.getId())) {
					throw new BusinessException(ErrorCode.BACK_ORGAN_ID_EXIST,
							"back organ exist ,"
									+ organDAO.findById(
											backOrgan.getFrontOrganId())
											.getName());
				}
			}
		}
	}

	private void isCreateRoadExist(Organ organ, String frontOrganId,
			String backOrganId) {
		if (StringUtils.isNotBlank(frontOrganId)
				&& StringUtils.isNotBlank(backOrganId)) {
			Organ frontOrgan = organDAO.findById(frontOrganId);
			Organ backOrgan = organDAO.findById(backOrganId);
			if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())
					&& StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				throw new BusinessException(
						ErrorCode.FRONT_AND_BACK_ORGAN_EXIST,
						"front and back organ exist ,"
								+ organDAO
										.findById(frontOrgan.getBackOrganId())
										.getName()
								+ ","
								+ organDAO
										.findById(backOrgan.getFrontOrganId())
										.getName());
			} else if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())) {
				throw new BusinessException(ErrorCode.FRONT_ORGAN_ID_EXIST,
						"front organ exist ,"
								+ organDAO
										.findById(frontOrgan.getBackOrganId())
										.getName());
			} else if (StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				throw new BusinessException(ErrorCode.BACK_ORGAN_ID_EXIST,
						"back organ exist ,"
								+ organDAO.findById(backOrgan.getBackOrganId())
										.getName());
			}
		} else if (StringUtils.isNotBlank(frontOrganId)) {
			Organ frontOrgan = organDAO.findById(frontOrganId);
			if (StringUtils.isNotBlank(frontOrgan.getBackOrganId())) {
				throw new BusinessException(ErrorCode.FRONT_ORGAN_ID_EXIST,
						"front organ exist ,"
								+ organDAO
										.findById(frontOrgan.getBackOrganId())
										.getName());
			}
		} else if (StringUtils.isNotBlank(backOrganId)) {
			Organ backOrgan = organDAO.findById(backOrganId);
			if (StringUtils.isNotBlank(backOrgan.getFrontOrganId())) {
				throw new BusinessException(ErrorCode.BACK_ORGAN_ID_EXIST,
						"back organ exist ,"
								+ organDAO
										.findById(backOrgan.getFrontOrganId())
										.getName());
			}
		}
	}

	@Override
	public String createOrganTunnel(String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, String height, String length,
			Integer laneNumber, Integer limitSpeed, Integer capacity,
			String leftDirection, String rightDirection) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		params.put("standardNumber", standardNumber);
		// list.clear();
		List<Organ> list = organDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "]is already exist");
		}

		// String id = organTunnelDAO.getNextId("Organ", 1);
		OrganTunnel organTunnel = new OrganTunnel();
		// organTunnel.setId(id);
		organTunnel.setAddress(address);
		organTunnel.setAreaCode(areaCode);
		organTunnel.setContact(contact);
		organTunnel.setCreateTime(System.currentTimeMillis());
		organTunnel.setFax(fax);
		organTunnel.setName(name);
		organTunnel.setNote(note);
		organTunnel.setPhone(phone);
		organTunnel.setStandardNumber(standardNumber);
		organTunnel.setDeep(null);
		organTunnel.setPath("");
		organTunnel.setType(type);
		organTunnel.setStakeNumber(stakeNumber);
		organTunnel.setHeight(height);
		organTunnel.setLength(length);
		organTunnel.setLaneNumber(laneNumber);
		organTunnel.setLimitSpeed(limitSpeed);
		organTunnel.setCapacity(capacity);
		organTunnel.setLeftDirection(leftDirection);
		organTunnel.setRightDirection(rightDirection);
		organTunnelDAO.save(organTunnel);

		OrganTunnel o = organTunnelDAO.findById(organTunnel.getId());
		String path = "/" + o.getId();
		if (StringUtils.isNotBlank(parentId)) {
			Organ parent = organDAO.findById(parentId);
			o.setParent(parent);
			path = parent.getPath() + path;
			// 目前对机构层级的创建不做限制,需要限制时根据上级机构的deep来判断
			o.setDeep(parent.getDeep() + 1);
		} else {
			o.setDeep(1);
		}
		o.setPath(path);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ORGAN);

		return o.getId();
	}

	@Override
	public void updateOrganTunnel(String id, String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String stakeNumber, String height, String length,
			Integer laneNumber, Integer limitSpeed, Integer capacity,
			String leftDirection, String rightDirection) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		if (null != standardNumber) {
			params.clear();
			params.put("standardNumber", standardNumber);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "]is already exist");
				}
			}
		}

		OrganTunnel organTunnel = organTunnelDAO.findById(id);

		if (null != name) {

			organTunnel.setName(name);
		}
		if (null != standardNumber) {
			syncSN(organTunnel.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ORGAN);
			organTunnel.setStandardNumber(standardNumber);
		}
		if (null != parentId) {
			Organ parent = organDAO.findById(parentId);
			organTunnel.setParent(parent);
			String path = parent.getPath() + "/" + id;
			organTunnel.setPath(path);
		}
		if (null != fax) {
			organTunnel.setFax(fax);
		}
		if (null != contact) {
			organTunnel.setContact(contact);
		}
		if (null != phone) {
			organTunnel.setPhone(phone);
		}
		if (null != address) {
			organTunnel.setAddress(address);
		}
		if (null != areaCode) {
			organTunnel.setAreaCode(areaCode);
		}
		if (null != note) {
			organTunnel.setNote(note);
		}
		if (null != stakeNumber) {
			organTunnel.setStakeNumber(stakeNumber);
		}
		if (null != height) {
			organTunnel.setHeight(height);
		}
		if (null != length) {
			organTunnel.setLength(length);
		}
		if (null != laneNumber) {
			organTunnel.setLaneNumber(laneNumber);
		}
		if (null != limitSpeed) {
			organTunnel.setLimitSpeed(limitSpeed);
		}
		if (null != capacity) {
			organTunnel.setCapacity(capacity);
		}
		if (null != leftDirection) {
			organTunnel.setLeftDirection(leftDirection);
		}
		if (null != rightDirection) {
			organTunnel.setRightDirection(rightDirection);
		}
	}

	@Override
	public GetOrganVO getOrganTunnel(String id) {
		OrganTunnel organTunnel = organTunnelDAO.findById(id);

		GetOrganVO vo = new GetOrganVO();
		vo.setAddress(organTunnel.getAddress());
		vo.setAreaCode(organTunnel.getAreaCode());
		vo.setContact(organTunnel.getContact());
		vo.setCreateTime(organTunnel.getCreateTime().toString());
		vo.setFax(organTunnel.getFax());
		vo.setId(organTunnel.getId());
		vo.setImageId(organTunnel.getImageId() != null ? organTunnel
				.getImageId().toString() : "");
		vo.setName(organTunnel.getName());
		vo.setNote(organTunnel.getNote());
		Organ parent = organTunnel.getParent();
		if (null != parent) {
			vo.setParentId(parent.getId());
			vo.setParentName(parent.getName());
		}
		vo.setPath(organTunnel.getPath());
		vo.setPhone(organTunnel.getPhone());
		vo.setStandardNumber(organTunnel.getStandardNumber());
		vo.setType(organTunnel.getType());
		vo.setStakeNumber(organTunnel.getStakeNumber());
		vo.setHeight(organTunnel.getHeight());
		vo.setLength(organTunnel.getLength());
		vo.setLaneNumber(organTunnel.getLaneNumber() != null ? organTunnel
				.getLaneNumber().toString() : "");
		vo.setLimitSpeed(organTunnel.getLimitSpeed() != null ? organTunnel
				.getLimitSpeed().toString() : "");
		vo.setCapacity(organTunnel.getCapacity() != null ? organTunnel
				.getCapacity().toString() : "");
		vo.setLeftDirection(organTunnel.getLeftDirection() != null ? organTunnel
				.getLeftDirection().toString() : "");
		vo.setRightDirection(organTunnel.getRightDirection() != null ? organTunnel
				.getRightDirection().toString() : "");
		return vo;
	}

	@Override
	public String createOrganRoad(String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, Integer limitSpeed, Integer capacity,
			Integer laneNumber, String beginStakeNumber, String endStakeNumber,
			Integer laneWidth, Integer leftEdge, Integer rightEdge,
			Integer centralReserveWidth, String roadNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		params.put("standardNumber", standardNumber);
		// list.clear();
		List<Organ> list = organDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "]is already exist");
		}

		// String id = organTunnelDAO.getNextId("Organ", 1);
		OrganRoad organRoad = new OrganRoad();
		// organTunnel.setId(id);
		organRoad.setAddress(address);
		organRoad.setAreaCode(areaCode);
		organRoad.setContact(contact);
		organRoad.setCreateTime(System.currentTimeMillis());
		organRoad.setFax(fax);
		organRoad.setName(name);
		organRoad.setNote(note);
		organRoad.setPhone(phone);
		organRoad.setStandardNumber(standardNumber);
		organRoad.setDeep(null);
		organRoad.setPath("");
		organRoad.setType(type);
		organRoad.setStakeNumber(stakeNumber);
		organRoad.setLaneNumber(laneNumber);
		organRoad.setRightEdge(rightEdge);
		organRoad.setLimitSpeed(limitSpeed);
		organRoad.setLeftEdge(leftEdge);
		organRoad.setLaneWidth(laneWidth);
		organRoad.setEndStakeNumber(endStakeNumber);
		organRoad.setCentralReserveWidth(centralReserveWidth);
		organRoad.setCapacity(capacity);
		organRoad.setBeginStakeNumber(beginStakeNumber);
		organRoad.setRoadNumber(roadNumber);
		organRoadDAO.save(organRoad);

		OrganRoad o = organRoadDAO.findById(organRoad.getId());
		String path = "/" + o.getId();
		if (StringUtils.isNotBlank(parentId)) {
			Organ parent = organDAO.findById(parentId);
			o.setParent(parent);
			path = parent.getPath() + path;
			// 目前对机构层级的创建不做限制,需要限制时根据上级机构的deep来判断
			o.setDeep(parent.getDeep() + 1);
		} else {
			o.setDeep(1);
		}
		o.setPath(path);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ORGAN);

		return o.getId();
	}

	@Override
	public void updateOrganRoad(String id, String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, Integer limitSpeed, Integer capacity,
			Integer laneNumber, String beginStakeNumber, String endStakeNumber,
			Integer laneWidth, Integer leftEdge, Integer rightEdge,
			Integer centralReserveWidth, String roadNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		if (null != standardNumber) {
			params.clear();
			params.put("standardNumber", standardNumber);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "]is already exist");
				}
			}
		}

		OrganRoad organRoad = organRoadDAO.findById(id);

		if (null != name) {

			organRoad.setName(name);
		}
		if (null != standardNumber) {
			syncSN(organRoad.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ORGAN);
			organRoad.setStandardNumber(standardNumber);
		}
		if (null != parentId) {
			Organ parent = organDAO.findById(parentId);
			organRoad.setParent(parent);
			String path = parent.getPath() + "/" + id;
			organRoad.setPath(path);
		}
		if (null != fax) {
			organRoad.setFax(fax);
		}
		if (null != contact) {
			organRoad.setContact(contact);
		}
		if (null != phone) {
			organRoad.setPhone(phone);
		}
		if (null != address) {
			organRoad.setAddress(address);
		}
		if (null != areaCode) {
			organRoad.setAreaCode(areaCode);
		}
		if (null != note) {
			organRoad.setNote(note);
		}
		if (null != stakeNumber) {
			organRoad.setStakeNumber(stakeNumber);
		}
		if (null != limitSpeed) {
			organRoad.setLimitSpeed(limitSpeed);
		}
		if (null != capacity) {
			organRoad.setCapacity(capacity);
		}
		if (null != laneNumber) {
			organRoad.setLaneNumber(laneNumber);
		}
		if (null != beginStakeNumber) {
			organRoad.setBeginStakeNumber(beginStakeNumber);
		}
		if (null != endStakeNumber) {
			organRoad.setEndStakeNumber(endStakeNumber);
		}
		if (null != laneWidth) {
			organRoad.setLaneWidth(laneWidth);
		}
		if (null != leftEdge) {
			organRoad.setLeftEdge(leftEdge);
		}
		if (null != rightEdge) {
			organRoad.setRightEdge(rightEdge);
		}
		if (null != centralReserveWidth) {
			organRoad.setCentralReserveWidth(centralReserveWidth);
		}
		if (null != roadNumber) {
			organRoad.setRoadNumber(roadNumber);
		}
	}

	@Override
	public GetOrganRoadVO getOrganRoad(String id) {
		OrganRoad organ = organRoadDAO.findById(id);
		GetOrganRoadVO vo = new GetOrganRoadVO();
		vo.setAddress(organ.getAddress());
		vo.setAreaCode(organ.getAreaCode());
		vo.setBackOrganId(organ.getBackOrganId());
		vo.setBeginStakeNumber(organ.getBeginStakeNumber());
		vo.setCapacity(organ.getCapacity() != null ? organ.getCapacity()
				.toString() : "");
		vo.setCentralReserveWidth(organ.getCentralReserveWidth() != null ? organ
				.getCentralReserveWidth().toString() : "");
		vo.setContact(organ.getContact());
		vo.setCreateTime(organ.getCreateTime() != null ? organ.getCreateTime()
				.toString() : "");
		vo.setEndStakeNumber(organ.getEndStakeNumber());
		vo.setFax(organ.getFax());
		vo.setFrontOrganId(organ.getFrontOrganId());
		vo.setId(organ.getId());
		Organ parent = organ.getParent();
		if (null != parent) {
			vo.setParentId(parent.getId());
			vo.setParentName(parent.getName());
		}
		vo.setLaneNumber(organ.getLaneNumber() != null ? organ.getLaneNumber()
				.toString() : "");
		vo.setLaneWidth(organ.getLaneWidth() != null ? organ.getLaneWidth()
				.toString() : "");
		vo.setLeftEdge(organ.getLeftEdge() != null ? organ.getLeftEdge()
				.toString() : "");
		vo.setLimitSpeed(organ.getLimitSpeed() != null ? organ.getLimitSpeed()
				.toString() : "");
		vo.setName(organ.getName());
		vo.setNote(organ.getNote());
		vo.setPhone(organ.getPhone());
		vo.setRightEdge(organ.getRightEdge() != null ? organ.getRightEdge()
				.toString() : "");
		vo.setStakeNumber(organ.getStakeNumber());
		vo.setStandardNumber(organ.getStandardNumber());
		vo.setType(organ.getType());
		vo.setRoadNumber(organ.getRoadNumber());
		return vo;
	}

	@Override
	public String createRoadMouth(String name, Integer limitSpeed,
			String navigation, String organId, String stakeNumber,
			String longitude, String latitude) {
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<RoadMouth> list = roadMouthDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }

		RoadMouth rm = new RoadMouth();
		rm.setLimitSpeed(limitSpeed);
		rm.setName(name);
		rm.setNavigation(navigation);
		rm.setOrgan(organDAO.findById(organId));
		rm.setStakeNumber(stakeNumber);
		rm.setLongitude(longitude);
		rm.setLatitude(latitude);
		roadMouthDAO.save(rm);
		return rm.getId();
	}

	@Override
	public void updateRoadMouth(String id, String name, Integer limitSpeed,
			String navigation, String organId, String stakeNumber,
			String longitude, String latitude) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<RoadMouth> list = roadMouthDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		RoadMouth rm = roadMouthDAO.findById(id);
		if (null != name) {
			rm.setName(name);
		}
		if (null != limitSpeed) {
			rm.setLimitSpeed(limitSpeed);
		}
		if (null != navigation) {
			rm.setNavigation(navigation);
		}
		if (null != organId) {
			rm.setOrgan(organDAO.findById(organId));
		}
		if (null != stakeNumber) {
			rm.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			rm.setLongitude(longitude);
		}
		if (null != latitude) {
			rm.setLatitude(latitude);
		}

	}

	@Override
	public GetRoadMouthVO getRoadMouth(String id) {
		RoadMouth rm = roadMouthDAO.findById(id);
		GetRoadMouthVO vo = new GetRoadMouthVO();
		vo.setId(rm.getId());
		vo.setLimitSpeed(rm.getLimitSpeed() != null ? rm.getLimitSpeed()
				.toString() : "");
		vo.setName(rm.getName());
		vo.setNavigation(rm.getNavigation());
		vo.setOrganId(rm.getOrgan() != null ? rm.getOrgan().getId() : "");
		vo.setStakeNumber(rm.getStakeNumber());
		vo.setLongitude(rm.getLongitude());
		vo.setLatitude(rm.getLatitude());
		return vo;
	}

	@Override
	public List<GetRoadMouthVO> listRoadMouth(String name, Integer startIndex,
			Integer limit, String organId) {
		List<RoadMouth> roadMouths = roadMouthDAO.listRoadMouth(name,
				startIndex, limit, organId);
		List<GetRoadMouthVO> list = new ArrayList<GetRoadMouthVO>();
		for (RoadMouth rm : roadMouths) {
			GetRoadMouthVO vo = new GetRoadMouthVO();
			vo.setId(rm.getId());
			vo.setLimitSpeed(rm.getLimitSpeed() != null ? rm.getLimitSpeed()
					.toString() : "");
			vo.setName(rm.getName());
			vo.setNavigation(rm.getNavigation());
			vo.setOrganId(rm.getOrgan() != null ? rm.getOrgan().getId() : "");
			vo.setStakeNumber(rm.getStakeNumber());
			vo.setLongitude(rm.getLongitude());
			vo.setLatitude(rm.getLatitude());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteRoadMouth(String id) {
		roadMouthDAO.deleteById(id);
	}

	@Override
	public GetTollgateVO getTollgate(String id) {
		// Tollgate tollgate = tollgateDAO.findById(id);
		GetTollgateVO vo = new GetTollgateVO();
		// vo.setEntranceNumber(tollgate.getEntranceNumber() != null ? tollgate
		// .getEntranceNumber().toString() : "");
		// vo.setExitNumber(tollgate.getExitNumber() != null ? tollgate
		// .getExitNumber().toString() : "");
		// vo.setId(tollgate.getId());
		// vo.setName(tollgate.getName());
		// vo.setOrganId(tollgate.getOrgan() != null ?
		// tollgate.getOrgan().getId()
		// : "");
		// vo.setStakeNumber(tollgate.getStakeNumber());
		// vo.setLinkMan(tollgate.getLinkMan());
		// vo.setPhone(tollgate.getPhone());
		// vo.setLongitude(tollgate.getLongitude());
		// vo.setLatitude(tollgate.getLatitude());
		// vo.setNavigation(tollgate.getNavigation());
		return vo;
	}

	@Override
	public List<GetTollgateVO> listTollgate(String name, Integer startIndex,
			Integer limit, String organId) {
		// List<Tollgate> tollgates = tollgateDAO.listTollgate(name, startIndex,
		// limit, organId);
		List<GetTollgateVO> list = new ArrayList<GetTollgateVO>();
		// for (Tollgate tollgate : tollgates) {
		// GetTollgateVO vo = new GetTollgateVO();
		// vo.setEntranceNumber(tollgate.getEntranceNumber() != null ? tollgate
		// .getEntranceNumber().toString() : "");
		// vo.setExitNumber(tollgate.getExitNumber() != null ? tollgate
		// .getExitNumber().toString() : "");
		// vo.setId(tollgate.getId());
		// vo.setName(tollgate.getName());
		// vo.setOrganId(tollgate.getOrgan() != null ? tollgate.getOrgan()
		// .getId() : "");
		// vo.setStakeNumber(tollgate.getStakeNumber());
		// vo.setLinkMan(tollgate.getLinkMan());
		// vo.setPhone(tollgate.getPhone());
		// vo.setLongitude(tollgate.getLongitude());
		// vo.setLatitude(tollgate.getLatitude());
		// vo.setNavigation(tollgate.getNavigation());
		// list.add(vo);
		// }
		return list;
	}

	@Override
	public Integer tollgateTotalCount(String name, String organId) {
		return tollgateDAO.tollgateTotalCount(name, organId);
	}

	@Override
	public Integer roadMouthTotalCount(String name, String organId) {
		return roadMouthDAO.roadMouthTotalCount(name, organId);
	}

	@Override
	public String createServiceZone(String name, String level, String organId,
			String stakeNumber, String linkMan, String phone, String longitude,
			String latitude, String navigation) {
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<ServiceZone> list = serviceZoneDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }

		ServiceZone serviceZone = new ServiceZone();
		serviceZone.setName(name);
		serviceZone.setOrgan(organDAO.findById(organId));
		serviceZone.setStakeNumber(stakeNumber);
		serviceZone.setLevel(level);
		serviceZone.setLinkMan(linkMan);
		serviceZone.setPhone(phone);
		serviceZone.setLongitude(longitude);
		serviceZone.setLatitude(latitude);
		serviceZone.setNavigation(navigation);
		serviceZoneDAO.save(serviceZone);
		return serviceZone.getId();
	}

	@Override
	public void updateServiceZone(String id, String name, String level,
			String organId, String stakeNumber, String linkMan, String phone,
			String longitude, String latitude, String navigation) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<ServiceZone> list = serviceZoneDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		ServiceZone serviceZone = serviceZoneDAO.findById(id);
		if (null != name) {
			serviceZone.setName(name);
		}
		if (null != level) {
			serviceZone.setLevel(level);
		}
		if (null != organId) {
			serviceZone.setOrgan(organDAO.findById(organId));
		}
		if (null != stakeNumber) {
			serviceZone.setStakeNumber(stakeNumber);
		}
		if (null != linkMan) {
			serviceZone.setLinkMan(linkMan);
		}
		if (null != phone) {
			serviceZone.setPhone(phone);
		}
		if (null != longitude) {
			serviceZone.setLongitude(longitude);
		}
		if (null != latitude) {
			serviceZone.setLatitude(latitude);
		}
		if (null != navigation) {
			serviceZone.setNavigation(navigation);
		}

	}

	@Override
	public void deleteServiceZone(String id) {
		serviceZoneDAO.deleteById(id);
	}

	@Override
	public GetServiceZoneVO getServiceZone(String id) {
		ServiceZone sz = serviceZoneDAO.findById(id);
		GetServiceZoneVO vo = new GetServiceZoneVO();
		vo.setId(sz.getId());
		vo.setLatitude(sz.getLatitude());
		vo.setLevel(sz.getLevel());
		vo.setLinkMan(sz.getLinkMan());
		vo.setLongitude(sz.getLongitude());
		vo.setName(sz.getName());
		vo.setNavigation(sz.getNavigation());
		vo.setOrganId(sz.getOrgan().getId());
		vo.setPhone(sz.getPhone());
		vo.setStakeNumber(sz.getStakeNumber());
		return vo;
	}

	@Override
	public Integer serviceZoneTotalCount(String name, String organId) {
		return serviceZoneDAO.serviceZoneTotalCount(name, organId);
	}

	@Override
	public List<GetServiceZoneVO> listServiceZone(String name,
			Integer startIndex, Integer limit, String organId) {
		List<ServiceZone> serviceZones = serviceZoneDAO.listServiceZone(name,
				startIndex, limit, organId);
		List<GetServiceZoneVO> list = new ArrayList<GetServiceZoneVO>();
		for (ServiceZone sz : serviceZones) {
			GetServiceZoneVO vo = new GetServiceZoneVO();
			vo.setId(sz.getId());
			vo.setLatitude(sz.getLatitude());
			vo.setLevel(sz.getLevel());
			vo.setLinkMan(sz.getLinkMan());
			vo.setLongitude(sz.getLongitude());
			vo.setName(sz.getName());
			vo.setNavigation(sz.getNavigation());
			vo.setOrganId(sz.getOrgan().getId());
			vo.setPhone(sz.getPhone());
			vo.setStakeNumber(sz.getStakeNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public Organ findBySn(String standardNumber) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Organ> organs = organDAO.findByPropertys(params);
		if (organs.size() <= 0) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"standardNumber[" + standardNumber + "] invalid !");
		}
		return organs.get(0);
	}

	@Override
	public String createOrganTollgate(String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, Integer entranceNumber, Integer exitNumber,
			String navigation, String longitude, String latitude) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		params.put("standardNumber", standardNumber);
		// list.clear();
		List<Organ> list = organDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "]is already exist");
		}

		// String id = organTunnelDAO.getNextId("Organ", 1);
		Tollgate tollgate = new Tollgate();
		// organTunnel.setId(id);
		tollgate.setAddress(address);
		tollgate.setAreaCode(areaCode);
		tollgate.setContact(contact);
		tollgate.setCreateTime(System.currentTimeMillis());
		tollgate.setFax(fax);
		tollgate.setName(name);
		tollgate.setNote(note);
		tollgate.setPhone(phone);
		tollgate.setStandardNumber(standardNumber);
		tollgate.setDeep(null);
		tollgate.setPath("");
		tollgate.setType(type);
		tollgate.setStakeNumber(stakeNumber);
		tollgate.setEntranceNumber(entranceNumber);
		tollgate.setExitNumber(exitNumber);
		tollgate.setNavigation(navigation);
		tollgate.setLongitude(longitude);
		tollgate.setLatitude(latitude);
		tollgateDAO.save(tollgate);

		Tollgate o = tollgateDAO.findById(tollgate.getId());
		String path = "/" + o.getId();
		if (StringUtils.isNotBlank(parentId)) {
			Organ parent = organDAO.findById(parentId);
			o.setParent(parent);
			path = parent.getPath() + path;
			// 目前对机构层级的创建不做限制,需要限制时根据上级机构的deep来判断
			o.setDeep(parent.getDeep() + 1);
		} else {
			o.setDeep(1);
		}
		o.setPath(path);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ORGAN);

		return o.getId();
	}

	@Override
	public void updateOrganTollgate(String id, String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String stakeNumber, Integer entranceNumber, Integer exitNumber,
			String navigation, String longitude, String latitude) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		if (null != standardNumber) {
			params.clear();
			params.put("standardNumber", standardNumber);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "]is already exist");
				}
			}
		}

		Tollgate tollgate = tollgateDAO.findById(id);

		if (null != name) {

			tollgate.setName(name);
		}
		if (null != standardNumber) {
			syncSN(tollgate.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ORGAN);
			tollgate.setStandardNumber(standardNumber);
		}
		if (null != parentId) {
			Organ parent = organDAO.findById(parentId);
			tollgate.setParent(parent);
			String path = parent.getPath() + "/" + id;
			tollgate.setPath(path);
		}
		if (null != fax) {
			tollgate.setFax(fax);
		}
		if (null != contact) {
			tollgate.setContact(contact);
		}
		if (null != phone) {
			tollgate.setPhone(phone);
		}
		if (null != address) {
			tollgate.setAddress(address);
		}
		if (null != areaCode) {
			tollgate.setAreaCode(areaCode);
		}
		if (null != note) {
			tollgate.setNote(note);
		}
		if (null != stakeNumber) {
			tollgate.setStakeNumber(stakeNumber);
		}
		if (null != entranceNumber) {
			tollgate.setEntranceNumber(entranceNumber);
		}
		if (null != exitNumber) {
			tollgate.setExitNumber(exitNumber);
		}
		if (null != navigation) {
			tollgate.setNavigation(navigation);
		}
		if (null != longitude) {
			tollgate.setLongitude(longitude);
		}
		if (null != latitude) {
			tollgate.setLatitude(latitude);
		}
	}

	@Override
	public GetOrganTollgateVO getOrganTollgate(String id) {
		Tollgate tollgate = tollgateDAO.findById(id);
		GetOrganTollgateVO vo = new GetOrganTollgateVO();
		vo.setAddress(tollgate.getAddress());
		vo.setAreaCode(tollgate.getAreaCode());
		vo.setContact(tollgate.getContact());
		vo.setCreateTime(tollgate.getCreateTime() != null ? tollgate
				.getCreateTime().toString() : "");
		vo.setDeep(tollgate.getDeep() != null ? tollgate.getDeep().toString()
				: "");
		vo.setEntranceNumber(tollgate.getEntranceNumber() != null ? tollgate
				.getEntranceNumber().toString() : "");
		vo.setExitNumber(tollgate.getExitNumber() != null ? tollgate
				.getExitNumber().toString() : "");
		vo.setFax(tollgate.getFax());
		vo.setId(tollgate.getId());
		vo.setImageId(tollgate.getImageId());
		vo.setName(tollgate.getName());
		vo.setNavigation(tollgate.getNavigation());
		vo.setNote(tollgate.getNote());
		vo.setParentId(tollgate.getParent().getId());
		vo.setParentName(tollgate.getParent().getName());
		vo.setPath(tollgate.getPath());
		vo.setPhone(tollgate.getPhone());
		vo.setStakeNumber(tollgate.getStakeNumber());
		vo.setStandardNumber(tollgate.getStandardNumber());
		vo.setType(tollgate.getType());
		vo.setLongitude(tollgate.getLongitude());
		vo.setLatitude(tollgate.getLatitude());
		return vo;
	}

	public void setParentByType(Element parent, Organ o) {
		String height = "";
		String length = "";
		String laneNumber = "";
		String limitSpeed = "";
		String capacity = "";
		String laneWidth = "";
		String leftEdge = "";
		String rightEdge = "";
		String centralReserveWidth = "";
		String beginStakeNumber = "";
		String endStakeNumber = "";
		String entranceNumber = "";
		String exitNumber = "";
		String navigation = "";
		String width = "";
		String roadNumber = "";
		String leftDirection = "";
		String rightDirection = "";
		if (o.getType().equals(TypeDefinition.ORGAN_TYPE_TUNNEL)) {
			OrganTunnel organTunnel = (OrganTunnel) o;
			height = organTunnel.getHeight() != null ? organTunnel.getHeight()
					: "";
			length = organTunnel.getLength() != null ? organTunnel.getLength()
					: "";
			laneNumber = organTunnel.getLaneNumber() != null ? organTunnel
					.getLaneNumber().toString() : "";
			limitSpeed = organTunnel.getLimitSpeed() != null ? organTunnel
					.getLimitSpeed().toString() : "";
			capacity = organTunnel.getCapacity() != null ? organTunnel
					.getCapacity().toString() : "";
			leftDirection = organTunnel.getLeftDirection() != null ? organTunnel
					.getLeftDirection().toString() : "";
			rightDirection = organTunnel.getRightDirection() != null ? organTunnel
					.getRightDirection().toString() : "";
		} else if (o.getType().equals(TypeDefinition.ORGAN_TYPE_ROAD)) {
			OrganRoad organRoad = (OrganRoad) o;
			limitSpeed = organRoad.getLimitSpeed() != null ? organRoad
					.getLimitSpeed().toString() : "";
			capacity = organRoad.getCapacity() != null ? organRoad
					.getCapacity().toString() : "";
			laneNumber = organRoad.getLaneNumber() != null ? organRoad
					.getLaneNumber().toString() : "";
			laneWidth = organRoad.getLaneWidth() != null ? organRoad
					.getLaneWidth().toString() : "";
			leftEdge = organRoad.getLeftEdge() != null ? organRoad
					.getLeftEdge().toString() : "";
			rightEdge = organRoad.getRightEdge() != null ? organRoad
					.getRightEdge().toString() : "";
			centralReserveWidth = organRoad.getCentralReserveWidth() != null ? organRoad
					.getCentralReserveWidth().toString() : "";
			beginStakeNumber = organRoad.getBeginStakeNumber() != null ? organRoad
					.getBeginStakeNumber().toString() : "";
			endStakeNumber = organRoad.getEndStakeNumber() != null ? organRoad
					.getEndStakeNumber().toString() : "";
			roadNumber = organRoad.getRoadNumber() != null ? organRoad
					.getRoadNumber() : "";
		} else if (o.getType().equals(TypeDefinition.ORGAN_TYPE_TOLLGATE)) {
			Tollgate tollgate = (Tollgate) o;
			entranceNumber = tollgate.getEntranceNumber() != null ? tollgate
					.getEntranceNumber().toString() : "";
			exitNumber = tollgate.getExitNumber() != null ? tollgate
					.getExitNumber().toString() : "";
			navigation = tollgate.getNavigation() != null ? tollgate
					.getNavigation() : "";
		} else if (o.getType().equals(TypeDefinition.ORGAN_TYPE_BRIDGE)) {
			OrganBridge organBridge = (OrganBridge) o;
			limitSpeed = organBridge.getLimitSpeed() != null ? organBridge
					.getLimitSpeed().toString() : "";
			capacity = organBridge.getCapacity() != null ? organBridge
					.getCapacity().toString() : "";
			width = organBridge.getWidth() != null ? organBridge.getWidth()
					.toString() : "";
			length = organBridge.getLength() != null ? organBridge.getLength()
					.toString() : "";
		}
		parent.setAttribute("Height", height);
		parent.setAttribute("Length", length);
		parent.setAttribute("LaneNumber", laneNumber);
		parent.setAttribute("LimitSpeed", limitSpeed);
		parent.setAttribute("Capacity", capacity);
		parent.setAttribute("LaneWidth", laneWidth);
		parent.setAttribute("LeftEdge", leftEdge);
		parent.setAttribute("RightEdge", rightEdge);
		parent.setAttribute("CentralReserveWidth", centralReserveWidth);
		parent.setAttribute("BeginStakeNumber", beginStakeNumber);
		parent.setAttribute("EndStakeNumber", endStakeNumber);
		parent.setAttribute("EntranceNumber", entranceNumber);
		parent.setAttribute("ExitNumber", exitNumber);
		parent.setAttribute("Navigation", navigation);
		parent.setAttribute("Width", width);
		parent.setAttribute("RoadNumber", roadNumber);
		parent.setAttribute("LeftDirection", leftDirection);
		parent.setAttribute("RightDirection", rightDirection);

	}

	@Override
	public OrganRoad getEventRoad(String roadName, String parentId) {
		return organDAO.getEventRoad(roadName, parentId);
	}

	@Override
	public String createOrganBridge(String name, String standardNumber,
			String parentId, String fax, String contact, String phone,
			String address, String areaCode, String note, String type,
			String stakeNumber, String longitude, String latitude,
			Integer limitSpeed, Integer capacity, String width, String length) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		params.put("standardNumber", standardNumber);
		// list.clear();
		List<Organ> list = organDAO.findByPropertys(params);
		if (list.size() > 0) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "]is already exist");
		}

		// String id = organTunnelDAO.getNextId("Organ", 1);
		OrganBridge bridge = new OrganBridge();
		// organTunnel.setId(id);
		bridge.setAddress(address);
		bridge.setAreaCode(areaCode);
		bridge.setContact(contact);
		bridge.setCreateTime(System.currentTimeMillis());
		bridge.setFax(fax);
		bridge.setName(name);
		bridge.setNote(note);
		bridge.setPhone(phone);
		bridge.setStandardNumber(standardNumber);
		bridge.setDeep(null);
		bridge.setPath("");
		bridge.setType(type);
		bridge.setStakeNumber(stakeNumber);
		bridge.setLongitude(longitude);
		bridge.setLatitude(latitude);
		bridge.setLimitSpeed(limitSpeed);
		bridge.setCapacity(capacity);
		bridge.setWidth(width);
		bridge.setLength(length);
		organBridgeDAO.save(bridge);

		OrganBridge o = organBridgeDAO.findById(bridge.getId());
		String path = "/" + o.getId();
		if (StringUtils.isNotBlank(parentId)) {
			Organ parent = organDAO.findById(parentId);
			o.setParent(parent);
			path = parent.getPath() + path;
			// 目前对机构层级的创建不做限制,需要限制时根据上级机构的deep来判断
			o.setDeep(parent.getDeep() + 1);
		} else {
			o.setDeep(1);
		}
		o.setPath(path);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_ORGAN);

		return o.getId();
	}

	@Override
	public void updateOrganBridge(String id, String name,
			String standardNumber, String parentId, String fax, String contact,
			String phone, String address, String areaCode, String note,
			String type, String stakeNumber, String longitude, String latitude,
			Integer limitSpeed, Integer capacity, String width, String length) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// if (null != name) {
		// params.put("name", name);
		// List<Organ> list = organDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }
		if (null != standardNumber) {
			params.clear();
			params.put("standardNumber", standardNumber);
			List<Organ> list = organDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "]is already exist");
				}
			}
		}

		OrganBridge bridge = organBridgeDAO.findById(id);

		if (null != name) {

			bridge.setName(name);
		}
		if (null != standardNumber) {
			syncSN(bridge.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_ORGAN);
			bridge.setStandardNumber(standardNumber);
		}
		if (null != parentId) {
			Organ parent = organDAO.findById(parentId);
			bridge.setParent(parent);
			String path = parent.getPath() + "/" + id;
			bridge.setPath(path);
		}
		if (null != fax) {
			bridge.setFax(fax);
		}
		if (null != contact) {
			bridge.setContact(contact);
		}
		if (null != phone) {
			bridge.setPhone(phone);
		}
		if (null != address) {
			bridge.setAddress(address);
		}
		if (null != areaCode) {
			bridge.setAreaCode(areaCode);
		}
		if (null != note) {
			bridge.setNote(note);
		}
		if (null != stakeNumber) {
			bridge.setStakeNumber(stakeNumber);
		}
		if (null != longitude) {
			bridge.setLongitude(longitude);
		}
		if (null != latitude) {
			bridge.setLatitude(latitude);
		}
		if (null != limitSpeed) {
			bridge.setLimitSpeed(limitSpeed);
		}
		if (null != capacity) {
			bridge.setCapacity(capacity);
		}
		if (null != width) {
			bridge.setWidth(width);
		}
		if (null != length) {
			bridge.setLength(length);
		}

	}

	@Override
	public GetOrganBridgeVO getOrganBridge(String id) {
		OrganBridge bridge = organBridgeDAO.findById(id);
		GetOrganBridgeVO vo = new GetOrganBridgeVO();
		vo.setAddress(bridge.getAddress());
		vo.setAreaCode(bridge.getAreaCode());
		vo.setContact(bridge.getContact());
		vo.setCreateTime(bridge.getCreateTime() != null ? bridge
				.getCreateTime().toString() : "");
		vo.setDeep(bridge.getDeep() != null ? bridge.getDeep().toString() : "");
		vo.setFax(bridge.getFax());
		vo.setId(bridge.getId());
		vo.setImageId(bridge.getImageId());
		vo.setName(bridge.getName());
		vo.setNote(bridge.getNote());
		vo.setParentId(bridge.getParent().getId());
		vo.setParentName(bridge.getParent().getName());
		vo.setPath(bridge.getPath());
		vo.setPhone(bridge.getPhone());
		vo.setStakeNumber(bridge.getStakeNumber());
		vo.setStandardNumber(bridge.getStandardNumber());
		vo.setType(bridge.getType());
		vo.setLongitude(bridge.getLongitude());
		vo.setLatitude(bridge.getLatitude());
		vo.setLimitSpeed(bridge.getLimitSpeed() != null ? bridge
				.getLimitSpeed().toString() : "");
		vo.setCapacity(bridge.getCapacity() != null ? bridge.getCapacity()
				.toString() : "");
		vo.setWidth(bridge.getWidth());
		vo.setLength(bridge.getLength());
		return vo;
	}

	@Override
	public Organ findOrganResource(String sn) throws BusinessException {
		Organ organ = organDAO.loadBySn(sn);
		if (null == organ) {
			SubPlatformResource subOrgan = subPlatformResourceDAO.findBySN(sn);
			organ = new Organ();
			organ.setName(subOrgan.getName());
			organ.setStandardNumber(sn);
		}
		return organ;
	}

	@Override
	public Element generateOrganTree(String organId,
			Collection<UserResourceVO> devices) throws BusinessException {
		Organ organ = organDAO.findById(organId);
		String rootSn = organ.getStandardNumber();
		Element root = null;
		// 初始化根机构
		for (UserResourceVO vo : devices) {
			// 根机构
			if (vo.getStandardNumber().equals(rootSn)) {
				root = initOrganElement(vo);
			}
		}
		if (null == root) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Root["
					+ rootSn + "] not find in user resource !");
		}

		List<String> ignoreList = null;
		String ignoreOrgans = Configuration.getInstance().loadProperties(
				"ignore_organs");
		if (StringUtils.isNotBlank(ignoreOrgans)) {
			String[] array = ignoreOrgans.split(",");
			ignoreList = Arrays.asList(array);
		}

		// 父机构列表
		List<Element> parents = new LinkedList<Element>();
		// 临时存放的下次循环时的父机构列表
		List<Element> tempParents = new LinkedList<Element>();
		parents.add(root);

		while (true) {
			for (UserResourceVO vo : devices) {

				for (Element resource : parents) {
					if (vo.getParent().equals(
							resource.getAttributeValue("StandardNumber"))) {
						// 处理configure.properties中配置了过滤的机构
						if (null != ignoreList) {
							if (ignoreList.contains(vo.getStandardNumber())) {
								continue;
							}
						}

						// 子机构
						if (TypeDefinition.ORGAN_TYPE_MANAGEMENT.equals(vo
								.getType())
								|| TypeDefinition.ORGAN_TYPE_BRIDGE.equals(vo
										.getType())
								|| TypeDefinition.ORGAN_TYPE_HIGHWAY.equals(vo
										.getType())
								|| TypeDefinition.ORGAN_TYPE_ROAD.equals(vo
										.getType())
								|| TypeDefinition.ORGAN_TYPE_TOLLGATE.equals(vo
										.getType())
								|| TypeDefinition.ORGAN_TYPE_TUNNEL.equals(vo
										.getType())) {
							Element subOrgan = initOrganElement(vo);
							resource.getChild("SubNodes").addContent(subOrgan);
							// 下级循环的父节点
							tempParents.add(subOrgan);
						}
						// 摄像头
						else if ((TypeDefinition.DEVICE_TYPE_CAMERA + "")
								.equals(vo.getType())
								|| (TypeDefinition.SUBTYPE_CAMERA_BALL + "")
										.equals(vo.getType())
								|| (TypeDefinition.SUBTYPE_CAMERA_BALL_BOLT + "")
										.equals(vo.getType())
								|| (TypeDefinition.SUBTYPE_CAMERA_DEFAULT + "")
										.equals(vo.getType())) {
							Element channel = initCameraElement(vo);
							resource.getChild("ChannelList")
									.addContent(channel);
						}
						// 车检器
						else if ((TypeDefinition.DEVICE_TYPE_VD + "").equals(vo
								.getType())) {
							Element channel = initVdElement(vo);
							resource.getChild("VehicleDetectorList")
									.addContent(channel);
						}
						// 风速风向
						else if ((TypeDefinition.DEVICE_TYPE_WS + "").equals(vo
								.getType())) {
							Element channel = initWsElement(vo);
							resource.getChild("WindSpeedList").addContent(
									channel);
						}
						// 气象
						else if ((TypeDefinition.DEVICE_TYPE_WST + "")
								.equals(vo.getType())) {
							Element channel = initWstElement(vo);
							resource.getChild("WeatherStatList").addContent(
									channel);
						}
						// 光强
						else if ((TypeDefinition.DEVICE_TYPE_LOLI + "")
								.equals(vo.getType())) {
							Element channel = initLoliElement(vo);
							resource.getChild("LoLiList").addContent(channel);
						}
						// 火灾
						else if ((TypeDefinition.DEVICE_TYPE_FD + "").equals(vo
								.getType())) {
							Element channel = initFdElement(vo);
							resource.getChild("FireDetectorList").addContent(
									channel);
						}
						// COVI
						else if ((TypeDefinition.DEVICE_TYPE_COVI + "")
								.equals(vo.getType())) {
							Element channel = initCoviElement(vo);
							resource.getChild("CoViList").addContent(channel);
						}
						// 氮氧
						else if ((TypeDefinition.DEVICE_TYPE_NOD + "")
								.equals(vo.getType())) {
							Element channel = initNodElement(vo);
							resource.getChild("NoDetectorList").addContent(
									channel);
						}
						// 情报板
						else if ((TypeDefinition.DEVICE_TYPE_CMS + "")
								.equals(vo.getType())) {
							Element channel = initCmsElement(vo);
							resource.getChild("CMSList").addContent(channel);
						}
						// 风机
						else if ((TypeDefinition.DEVICE_TYPE_FAN + "")
								.equals(vo.getType())) {
							Element channel = initFanElement(vo);
							resource.getChild("FanList").addContent(channel);
						}
						// 灯光
						else if ((TypeDefinition.DEVICE_TYPE_LIGHT + "")
								.equals(vo.getType())) {
							Element channel = initLightElement(vo);
							resource.getChild("LightList").addContent(channel);
						}
						// 卷帘门
						else if ((TypeDefinition.DEVICE_TYPE_RD + "").equals(vo
								.getType())) {
							Element channel = initRdElement(vo);
							resource.getChild("RollingDoorList").addContent(
									channel);
						}
						// 水泵
						else if ((TypeDefinition.DEVICE_TYPE_WP + "").equals(vo
								.getType())) {
							Element channel = initWpElement(vo);
							resource.getChild("WaterPumpList").addContent(
									channel);
						}
						// 栏杆
						else if ((TypeDefinition.DEVICE_TYPE_RAIL + "")
								.equals(vo.getType())) {
							Element channel = initRailElement(vo);
							resource.getChild("RailList").addContent(channel);
						}
						// 电光诱导
						else if ((TypeDefinition.DEVICE_TYPE_IS + "").equals(vo
								.getType())) {
							Element channel = initIsElement(vo);
							resource.getChild("InductionSignList").addContent(
									channel);
						}
						// 手报
						else if ((TypeDefinition.DEVICE_TYPE_PB + "").equals(vo
								.getType())) {
							Element channel = initPbElement(vo);
							resource.getChild("PushButtonList").addContent(
									channel);
						}
						// 仓库
						else if ((TypeDefinition.DEVICE_TYPE_WH + "").equals(vo
								.getType())) {
							Element channel = initWhElement(vo);
							resource.getChild("WareHouseList").addContent(
									channel);
						}
						// 交通灯
						else if ((TypeDefinition.DEVICE_TYPE_TSL + "")
								.equals(vo.getType())) {
							Element channel = initTslElement(vo);
							resource.getChild("TrafficSignList").addContent(
									channel);
						}
						// 车道指示灯
						else if ((TypeDefinition.DEVICE_TYPE_LIL + "")
								.equals(vo.getType())) {
							Element channel = initLilElement(vo);
							resource.getChild("LaneIndicationList").addContent(
									channel);
						}
						// 变电箱
						else if ((TypeDefinition.DEVICE_TYPE_BT + "").equals(vo
								.getType())) {
							Element channel = initBtElement(vo);
							resource.getChild("BoxTransformerList").addContent(
									channel);
						}
						// 能见度
						else if ((TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")
								.equals(vo.getType())) {
							Element channel = initViElement(vo);
							resource.getChild("ViDetectorList").addContent(
									channel);
						}
						// 路面检测
						else if ((TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")
								.equals(vo.getType())) {
							Element channel = initRoadDetectorElement(vo);
							resource.getChild("RoadDetectorList").addContent(
									channel);
						}
						// 桥面检测
						else if ((TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")
								.equals(vo.getType())) {
							Element channel = initBdElement(vo);
							resource.getChild("BridgeDetectorList").addContent(
									channel);
						}
						// 紧急电话
						else if ((TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")
								.equals(vo.getType())) {
							Element channel = initUpElement(vo);
							resource.getChild("UrgentPhoneList").addContent(
									channel);
						}
						// 紧急电话
						else if ((TypeDefinition.DEVICE_TYPE_GPS + "")
								.equals(vo.getType())) {
							Element channel = initGpsElement(vo);
							resource.getChild("GpsList").addContent(channel);
						}
					}
				}

			}

			if (tempParents.size() <= 0) {
				break;
			}
			// 开始下级循环重置parents
			else {
				parents.clear();
				for (Element e : tempParents) {
					parents.add(e);
				}
				tempParents.clear();
			}
		}

		return root;
	}

	private Element initOrganElement(UserResourceVO vo) {
		Element self = new Element("Node");
		// 自身信息
		self.setAttribute("Id", vo.getId());
		self.setAttribute("StandardNumber",
				MyStringUtil.object2StringNotNull(vo.getStandardNumber()));
		self.setAttribute("Name",
				MyStringUtil.object2StringNotNull(vo.getName()));
		self.setAttribute("Type",
				MyStringUtil.object2StringNotNull(vo.getType()));
		self.setAttribute("ImageId",
				MyStringUtil.object2StringNotNull(vo.getImageId()));
		self.setAttribute("Longitude",
				MyStringUtil.object2StringNotNull(vo.getLongitude()));
		self.setAttribute("Latitude",
				MyStringUtil.object2StringNotNull(vo.getLatitude()));
		self.setAttribute("StakeNumber",
				MyStringUtil.object2StringNotNull(vo.getStakeNumber()));
		self.setAttribute("Height",
				MyStringUtil.object2StringNotNull(vo.getHeight()));
		self.setAttribute("Length",
				MyStringUtil.object2StringNotNull(vo.getLength()));
		self.setAttribute("LaneNumber",
				MyStringUtil.object2StringNotNull(vo.getLaneNumber()));
		self.setAttribute("LimitSpeed",
				MyStringUtil.object2StringNotNull(vo.getLimitSpeed()));
		self.setAttribute("Capacity",
				MyStringUtil.object2StringNotNull(vo.getCapacity()));
		self.setAttribute("LaneWidth",
				MyStringUtil.object2StringNotNull(vo.getLaneWidth()));
		self.setAttribute("LeftEdge",
				MyStringUtil.object2StringNotNull(vo.getLeftEdge()));
		self.setAttribute("RightEdge",
				MyStringUtil.object2StringNotNull(vo.getRightEdge()));
		self.setAttribute("CentralReserveWidth",
				MyStringUtil.object2StringNotNull(vo.getCentralReserveWidth()));
		self.setAttribute("BeginStakeNumber",
				MyStringUtil.object2StringNotNull(vo.getBeginStakeNumber()));
		self.setAttribute("EndStakeNumber",
				MyStringUtil.object2StringNotNull(vo.getEndStakeNumber()));
		self.setAttribute("EntranceNumber",
				MyStringUtil.object2StringNotNull(vo.getEntranceNumber()));
		self.setAttribute("ExitNumber",
				MyStringUtil.object2StringNotNull(vo.getExitNumber()));
		self.setAttribute("Navigation",
				MyStringUtil.object2StringNotNull(vo.getNavigation()));
		self.setAttribute("Width",
				MyStringUtil.object2StringNotNull(vo.getWidth()));
		self.setAttribute("RoadNumber",
				MyStringUtil.object2StringNotNull(vo.getRoadNumber()));
		self.setAttribute("LeftDirection",
				MyStringUtil.object2StringNotNull(vo.getLeftDirection()));
		self.setAttribute("RightDirection",
				MyStringUtil.object2StringNotNull(vo.getRightDirection()));

		// 设备信息
		Element channelList = new Element("ChannelList");
		self.addContent(channelList);
		Element vehicleDetectorList = new Element("VehicleDetectorList");
		self.addContent(vehicleDetectorList);
		Element windSpeedList = new Element("WindSpeedList");
		self.addContent(windSpeedList);
		Element weatherStatList = new Element("WeatherStatList");
		self.addContent(weatherStatList);
		Element loLiList = new Element("LoLiList");
		self.addContent(loLiList);
		Element fireDetectorList = new Element("FireDetectorList");
		self.addContent(fireDetectorList);
		Element coViList = new Element("CoViList");
		self.addContent(coViList);
		Element noDetectorList = new Element("NoDetectorList");
		self.addContent(noDetectorList);
		Element cMSList = new Element("CMSList");
		self.addContent(cMSList);
		Element fanList = new Element("FanList");
		self.addContent(fanList);
		Element lightList = new Element("LightList");
		self.addContent(lightList);
		Element rollingDoorList = new Element("RollingDoorList");
		self.addContent(rollingDoorList);
		Element waterPumpList = new Element("WaterPumpList");
		self.addContent(waterPumpList);
		Element railList = new Element("RailList");
		self.addContent(railList);
		Element inductionSignList = new Element("InductionSignList");
		self.addContent(inductionSignList);
		Element pushButtonList = new Element("PushButtonList");
		self.addContent(pushButtonList);
		Element wareHouseList = new Element("WareHouseList");
		self.addContent(wareHouseList);
		Element trafficSignList = new Element("TrafficSignList");
		self.addContent(trafficSignList);
		Element laneIndicationList = new Element("LaneIndicationList");
		self.addContent(laneIndicationList);
		Element boxTransformerList = new Element("BoxTransformerList");
		self.addContent(boxTransformerList);
		Element viDetectorList = new Element("ViDetectorList");
		self.addContent(viDetectorList);
		Element roadDetectorList = new Element("RoadDetectorList");
		self.addContent(roadDetectorList);
		Element bridgeDetectorList = new Element("BridgeDetectorList");
		self.addContent(bridgeDetectorList);
		Element urgentPhoneList = new Element("UrgentPhoneList");
		self.addContent(urgentPhoneList);
		Element gpsList = new Element("GpsList");
		self.addContent(gpsList);

		// 子节点
		Element subNodes = new Element("SubNodes");
		self.addContent(subNodes);
		return self;
	}

	private Element initCameraElement(UserResourceVO vo) {
		Element channel = new Element("Channel");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute("SubType",
				vo.getSubType() != null ? vo.getSubType() : "");
		channel.setAttribute("Status", vo.getStatus() != null ? vo.getStatus()
				: "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("ImageId",
				vo.getImageId() != null ? vo.getImageId() : "");
		channel.setAttribute("ChannelNumber",
				vo.getChannelNumber() != null ? vo.getChannelNumber() : "");
		channel.setAttribute("Location",
				vo.getLocation() != null ? vo.getLocation() : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("SolarSN",
				vo.getSolarSN() != null ? vo.getSolarSN() : "");
		channel.setAttribute("SolarName",
				vo.getSolarName() != null ? vo.getSolarName() : "");
		channel.setAttribute("SolarNavigation",
				vo.getSolarNavigation() != null ? vo.getSolarNavigation() : "");
		channel.setAttribute("SolarStakeNumber",
				vo.getSolarStakeNumber() != null ? vo.getSolarStakeNumber()
						: "");
		channel.setAttribute("SatteryCapacity",
				vo.getBatteryCapacity() != null ? vo.getBatteryCapacity() : "");
		channel.setAttribute("CcsSN", vo.getCcsSN() != null ? vo.getCcsSN()
				: "");
		channel.setAttribute("StoreType", vo.getStoreType());
		return channel;
	}

	private Element initVdElement(UserResourceVO vo) {
		Element channel = new Element("VehicleDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("SolarSN",
				vo.getSolarSN() != null ? vo.getSolarSN() : "");
		channel.setAttribute("SolarName",
				vo.getSolarName() != null ? vo.getSolarName() : "");
		channel.setAttribute("SolarNavigation",
				vo.getSolarNavigation() != null ? vo.getSolarNavigation() : "");
		channel.setAttribute("SolarStakeNumber",
				vo.getSolarStakeNumber() != null ? vo.getSolarStakeNumber()
						: "");
		channel.setAttribute("BatteryCapacity",
				vo.getBatteryCapacity() != null ? vo.getBatteryCapacity() : "");
		channel.setAttribute("SpeedUpLimit",
				vo.getSpeedUpLimit() != null ? vo.getSpeedUpLimit() + "" : "");
		channel.setAttribute("SpeedLowLimit",
				vo.getSpeedLowLimit() != null ? vo.getSpeedLowLimit() + "" : "");
		channel.setAttribute("OccUpLimit",
				vo.getOccUpLimit() != null ? vo.getOccUpLimit() + "" : "");
		channel.setAttribute("OccLowLimit",
				vo.getOccLowLimit() != null ? vo.getOccLowLimit() + "" : "");
		channel.setAttribute("VolumeLowLimit",
				vo.getVolumeLowLimit() != null ? vo.getVolumeLowLimit() + ""
						: "");
		channel.setAttribute("VolumeUpLimit",
				vo.getVolumeUpLimit() != null ? vo.getVolumeUpLimit() + "" : "");
		return channel;
	}

	private Element initWsElement(UserResourceVO vo) {
		Element channel = new Element("WindSpeed");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("WindUpLimit",
				vo.getWindUpLimit() != null ? vo.getWindUpLimit() + "" : "");
		return channel;
	}

	private Element initWstElement(UserResourceVO vo) {
		Element channel = new Element("WeatherStat");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("ViLowLimit",
				vo.getViLowLimit() != null ? vo.getViLowLimit() + "" : "");
		channel.setAttribute("WindUpLimit",
				vo.getWindUpLimit() != null ? vo.getWindUpLimit() + "" : "");
		channel.setAttribute("RainUpLimit",
				vo.getRainUpLimit() != null ? vo.getRainUpLimit() + "" : "");
		channel.setAttribute("SnowUpLimit",
				vo.getSnowUpLimit() != null ? vo.getSnowUpLimit() + "" : "");
		return channel;
	}

	private Element initLoliElement(UserResourceVO vo) {
		Element channel = new Element("LoLi");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("LiLumiMax",
				vo.getLiLumiMax() != null ? vo.getLiLumiMax() + "" : "");
		channel.setAttribute("LiLumiMin",
				vo.getLiLumiMin() != null ? vo.getLiLumiMin() + "" : "");
		channel.setAttribute("LoLumiMax",
				vo.getLoLumiMax() != null ? vo.getLoLumiMax() + "" : "");
		channel.setAttribute("LoLumiMin",
				vo.getLoLumiMin() != null ? vo.getLoLumiMin() + "" : "");
		return channel;
	}

	private Element initFdElement(UserResourceVO vo) {
		Element channel = new Element("FireDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initCoviElement(UserResourceVO vo) {
		Element channel = new Element("CoVi");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("CoConctLimit",
				vo.getCoConctLimit() != null ? vo.getCoConctLimit() + "" : "");
		channel.setAttribute("ViLowLimit",
				vo.getViLowLimit() != null ? vo.getViLowLimit() + "" : "");
		return channel;
	}

	private Element initNodElement(UserResourceVO vo) {
		Element channel = new Element("NoDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("NoConctLimit",
				vo.getNoConctLimit() != null ? vo.getNoConctLimit() + "" : "");
		channel.setAttribute("NooConctLimit",
				vo.getNooConctLimit() != null ? vo.getNooConctLimit() + "" : "");
		return channel;
	}

	private Element initCmsElement(UserResourceVO vo) {
		Element channel = new Element("Cms");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("SubType",
				MyStringUtil.object2StringNotNull(vo.getSubType()));
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("Height",
				MyStringUtil.object2StringNotNull(vo.getHeight()));
		channel.setAttribute("Width",
				MyStringUtil.object2StringNotNull(vo.getWidth()));
		return channel;
	}

	private Element initFanElement(UserResourceVO vo) {
		Element channel = new Element("Fan");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initLightElement(UserResourceVO vo) {
		Element channel = new Element("Light");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initRdElement(UserResourceVO vo) {
		Element channel = new Element("RollingDoor");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		channel.setAttribute("SubType",
				vo.getSubType() != null ? vo.getSubType() : "");
		return channel;
	}

	private Element initWpElement(UserResourceVO vo) {
		Element channel = new Element("WaterPump");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initRailElement(UserResourceVO vo) {
		Element channel = new Element("Rail");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initIsElement(UserResourceVO vo) {
		Element channel = new Element("InductionSign");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initPbElement(UserResourceVO vo) {
		Element channel = new Element("PushButton");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initWhElement(UserResourceVO vo) {
		Element wareHouseElement = new Element("WareHouse");
		wareHouseElement.setAttribute("Id", vo.getId());
		wareHouseElement.setAttribute("Name",
				vo.getName() != null ? vo.getName() : "");
		wareHouseElement.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		wareHouseElement.setAttribute("Location",
				vo.getLocation() != null ? vo.getLocation() : "");
		wareHouseElement.setAttribute("Telephone",
				vo.getTelephone() != null ? vo.getTelephone() : "");
		wareHouseElement.setAttribute("LinkMan",
				vo.getLinkMan() != null ? vo.getLinkMan() : "");
		wareHouseElement.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		wareHouseElement.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		wareHouseElement.setAttribute("Type",
				vo.getType() != null ? vo.getType() : "");
		wareHouseElement.setAttribute("ManagerUnit",
				vo.getManagerUnit() != null ? vo.getManagerUnit() : "");
		return wareHouseElement;
	}

	private Element initTslElement(UserResourceVO vo) {
		Element channel = new Element("TrafficSign");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Width", vo.getWidth() != null ? vo.getWidth()
				: "");
		channel.setAttribute("Height", vo.getHeight() != null ? vo.getHeight()
				: "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initLilElement(UserResourceVO vo) {
		Element channel = new Element("LaneIndication");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Width", vo.getWidth() != null ? vo.getWidth()
				: "");
		channel.setAttribute("Height", vo.getHeight() != null ? vo.getHeight()
				: "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initBtElement(UserResourceVO vo) {
		Element channel = new Element("BoxTransformer");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initViElement(UserResourceVO vo) {
		Element channel = new Element("ViDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initRoadDetectorElement(UserResourceVO vo) {
		Element channel = new Element("RoadDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initBdElement(UserResourceVO vo) {
		Element channel = new Element("BridgeDetector");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initUpElement(UserResourceVO vo) {
		Element channel = new Element("UrgentPhone");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Longitude",
				vo.getLongitude() != null ? vo.getLongitude() : "");
		channel.setAttribute("Latitude",
				vo.getLatitude() != null ? vo.getLatitude() : "");
		channel.setAttribute("StakeNumber",
				vo.getStakeNumber() != null ? vo.getStakeNumber() : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Navigation",
				vo.getNavigation() != null ? vo.getNavigation() : "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	private Element initGpsElement(UserResourceVO vo) {
		Element channel = new Element("Gps");
		channel.setAttribute("Id", vo.getId());
		channel.setAttribute("StandardNumber",
				vo.getStandardNumber() != null ? vo.getStandardNumber() : "");
		channel.setAttribute("Name", vo.getName() != null ? vo.getName() : "");
		channel.setAttribute(
				"Auth",
				translateAuth(vo.getAuth()) != null ? translateAuth(vo
						.getAuth()) : "");
		channel.setAttribute("Type", vo.getType() != null ? vo.getType() : "");
		channel.setAttribute("DasSN", vo.getDasSN() != null ? vo.getDasSN()
				: "");
		channel.setAttribute("Manufacture",
				vo.getManufacture() != null ? vo.getManufacture() : "");
		return channel;
	}

	@Override
	public List<ListOrganVO> listOrganTreeAll(String organId) {
		// 本级平台机构以及子机构
		Map<String, ListOrganVO> map = new LinkedHashMap<String, ListOrganVO>();
		List<Organ> organs = organDAO.listOrganById(organId);
		List<ListOrganVO> listVO = new ArrayList<ListOrganVO>();

		Organ root = organDAO.getRootOrgan();

		// 本级机构加入到返回集合中
		for (Organ organ : organs) {
			ListOrganVO vo = buildOrgan(organ);
			map.put(organ.getStandardNumber(), vo);
			listVO.add(vo);
		}

		// 下级机构以及子机构
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("type", "0");
		List<SubPlatformResource> subResources = subPlatformResourceDAO
				.findByPropertys(params);
		for (SubPlatformResource subResource : subResources) {
			ListOrganVO vo = map.get(subResource.getStandardNumber());
			// 本级不存在的，新增下级资源
			if (null == vo) {
				vo = buildSubResource(subResource);
				// 下级根节点在本级不存在，自动添加本级根机构，为下级平台的父节点
				if (StringUtils.isBlank(vo.getParentId())) {
					vo.setParentId(root.getId());
				}
				// 如果不是下级平台父节点,则拿它父节点和本级比较,如果它父节点和本级机构SN相同则把它自身的parentId设置成本级机构的ID
				else {
					for (Organ organ : organs) {
						if (organ.getStandardNumber().equals(
								subResource.getParent().getStandardNumber())) {
							vo.setParentId(organ.getId());
							break;
						}
					}
				}
				listVO.add(vo);
			}
		}
		// 去掉配置文件屏蔽的机构
		List<String> ignoreList = new ArrayList<String>();
		String ignoreOrgans = Configuration.getInstance().loadProperties(
				"ignore_organs");
		if (StringUtils.isNotBlank(ignoreOrgans)) {
			String[] array = ignoreOrgans.split(",");
			ignoreList = Arrays.asList(array);
		}
		List<ListOrganVO> removeList = new ArrayList<ListOrganVO>();
		for (ListOrganVO vo : listVO) {
			if (ignoreList.contains(vo.getStandardNumber())) {
				// 如果被屏蔽的机构中还有下级机构，则把下级机构一起屏蔽
				for (ListOrganVO subVO : listVO) {
					if (subVO.getParentId().equals(vo.getId())) {
						removeList.add(subVO);
					}
				}
				removeList.add(vo);
			}
		}
		if (removeList.size() > 0) {
			listVO.removeAll(removeList);
		}
		return listVO;
	}

	public ListOrganVO buildOrgan(Organ organ) {
		ListOrganVO vo = new ListOrganVO();
		vo.setAddress(organ.getAddress());
		vo.setAreaCode(organ.getAreaCode());
		vo.setContact(organ.getContact());
		vo.setCreateTime(organ.getCreateTime().toString());
		vo.setDeep(organ.getDeep() + "");
		vo.setFax(organ.getFax());
		vo.setId(organ.getId());
		vo.setImageId(organ.getImageId() != null ? organ.getImageId()
				.toString() : "");
		vo.setName(organ.getName());
		vo.setNote(organ.getNote());
		vo.setParentId(organ.getParent() != null ? organ.getParent().getId()
				: "");
		vo.setParentName(organ.getParent() != null ? organ.getParent()
				.getName() : "");
		vo.setPath(organ.getPath());
		vo.setPhone(organ.getPhone());
		vo.setStandardNumber(organ.getStandardNumber());
		vo.setType(organ.getType());
		return vo;
	}

	public ListOrganVO buildSubResource(SubPlatformResource subResource) {
		ListOrganVO vo = new ListOrganVO();
		vo.setAddress(subResource.getAddress());
		vo.setCreateTime(subResource.getUpdateTime() + "");
		vo.setId(subResource.getId());
		vo.setName(subResource.getName());
		vo.setParentId(subResource.getParent() != null ? subResource
				.getParent().getId() : null);
		vo.setPath(subResource.getPath());
		vo.setStandardNumber(subResource.getStandardNumber());
		vo.setType(subResource.getType());
		return vo;
	}

	@Override
	public String getOrganIdBySN(String standardNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Organ> organs = organDAO.findByPropertys(params);
		if (organs.size() <= 0) {
			List<SubPlatformResource> resources = subPlatformResourceDAO
					.findByPropertys(params);
			if (resources.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"organSN[" + standardNumber
								+ "] not find in organ resource !");
			} else {
				return resources.get(0).getId();
			}
		} else {
			return organs.get(0).getId();
		}
	}

	@Override
	public ListOrganDeviceTreeVO treeOrganCamera(String organSn) {
		// 本级机构资源对象，key为ID
		Map<String, ListOrganDeviceTreeVO> idMap = new HashMap<String, ListOrganDeviceTreeVO>();
		// 下级机构资源对象，key为ID
		Map<String, ListOrganDeviceTreeVO> subMap = new HashMap<String, ListOrganDeviceTreeVO>();
		// 结果
		ListOrganDeviceTreeVO result = null;
		// 是否从平台根机构开始查询标志
		boolean rootFlag = false;
		// 本级满足条件的所有机构
		List<Organ> organs = null;
		// 查询的根机构
		Organ rootOrgan = null;
		if (null != organSn) {
			rootOrgan = organDAO.loadBySn(organSn);
			// 从本级机构查找
			if (null != rootOrgan) {
				// 获取指定机构下的所有子机构包含自身
				organs = organDAO.listOrganById(rootOrgan.getId());
				if (rootOrgan.getParent() == null) {
					rootFlag = true;
				}
			}
		} else {
			// 查询所有的本级机构
			organs = (List<Organ>) CacheUtil.getCache("allOrgan", null);
			if (null == organs) {
				organs = organDAO.findAll();
				CacheUtil.putCache("allOrgan", organs, null);
			}
			rootFlag = true;
		}

		// 查询的是本级机构中的某级
		if (null != organs) {
			// 所有的摄像头
			List<Camera> cameras = null;
			cameras = (List<Camera>) CacheUtil.getCache("allCamera", null);
			if (null == cameras) {
				cameras = cameraDAO.listCamerasNoJoin();
				CacheUtil.putCache("allCamera", cameras, null);
			}

			// 首先构建所有的本级机构
			for (Organ organ : organs) {
				ListOrganDeviceTreeVO vo = new ListOrganDeviceTreeVO();
				vo.setCode(organ.getStandardNumber());
				vo.setLeaf(false);
				vo.setText(organ.getName());
				idMap.put(organ.getId(), vo);
			}
			// 本级机构形成树
			for (Organ organ : organs) {
				// 从根节点开始查询的情况
				if (rootFlag) {
					if (null != organ.getParent()) {
						// 获取父节点
						ListOrganDeviceTreeVO parentVO = idMap.get(organ
								.getParent().getId());
						// 获取自身
						ListOrganDeviceTreeVO self = idMap.get(organ.getId());
						// 加入到父节点中
						List<ListOrganDeviceTreeVO> children = parentVO
								.getChildren();
						if (null == children) {
							children = new LinkedList<ListOrganDeviceTreeVO>();
							parentVO.setChildren(children);
						}
						children.add(self);
					}
					// parent为空的既是最后的返回结果
					else {
						result = idMap.get(organ.getId());
					}
				}
				// 从中间节点查询的情况
				else if (!organSn.equals(organ.getStandardNumber())) {
					// 获取父节点
					ListOrganDeviceTreeVO parentVO = idMap.get(organ
							.getParent().getId());
					// 获取自身
					ListOrganDeviceTreeVO self = idMap.get(organ.getId());
					// 加入到父节点中
					List<ListOrganDeviceTreeVO> children = parentVO
							.getChildren();
					if (null == children) {
						children = new LinkedList<ListOrganDeviceTreeVO>();
						parentVO.setChildren(children);
					}
					children.add(self);
				}
				// organSn相等的既是最后的返回结果
				else {
					result = idMap.get(organ.getId());
				}
			}

			// 构建所有的本级摄像头,并加入到正确的机构下
			for (Camera camera : cameras) {
				ListOrganDeviceTreeVO vo = new ListOrganDeviceTreeVO();
				vo.setCode(camera.getStandardNumber());
				vo.setLeaf(true);
				vo.setText(camera.getName());
				// 加入到正确机构下
				ListOrganDeviceTreeVO organVO = idMap.get(camera.getOrgan()
						.getId());
				// 父机构没有找到，说明属于更高一级的机构
				if (organVO == null) {
					continue;
				}
				List<ListOrganDeviceTreeVO> children = organVO.getChildren();
				if (null == children) {
					children = new LinkedList<ListOrganDeviceTreeVO>();
					organVO.setChildren(children);
				}
				children.add(vo);
			}
		}

		if (rootFlag || (!rootFlag && organs == null)) {
			// 所有的下级资源
			List<SubPlatformResource> subResources = null;
			subResources = (List<SubPlatformResource>) CacheUtil.getCache(
					"allSubResource", null);
			if (null == subResources) {
				subResources = subPlatformResourceDAO.findAll();
				CacheUtil.putCache("allSubResource", subResources, null);
			}

			// 构建所有下级机构
			for (SubPlatformResource subOrgan : subResources) {
				// 只处理机构
				if (subOrgan.getType().equals(
						TypeDefinition.ORGAN_TYPE_MANAGEMENT)) {
					ListOrganDeviceTreeVO organVO = new ListOrganDeviceTreeVO();
					organVO.setCode(subOrgan.getStandardNumber());
					organVO.setLeaf(false);
					organVO.setText(subOrgan.getName());
					subMap.put(subOrgan.getId(), organVO);
				}
			}

			// 构建下级摄像头和让下级机构形成树
			for (SubPlatformResource subResource : subResources) {
				// 机构形成树
				if (subResource.getType().equals(
						TypeDefinition.ORGAN_TYPE_MANAGEMENT)) {
					// 自身
					ListOrganDeviceTreeVO self = subMap
							.get(subResource.getId());
					// 从根节点查询的情况
					if (rootFlag) {
						// 下级根机构加入到上级根机构下
						if (subResource.getParent() == null) {
							result.getChildren().add(self);
						}
						// 加入到parent对应的上级
						else {
							ListOrganDeviceTreeVO parentVO = subMap
									.get(subResource.getParent().getId());
							List<ListOrganDeviceTreeVO> children = parentVO
									.getChildren();
							if (null == children) {
								children = new LinkedList<ListOrganDeviceTreeVO>();
								parentVO.setChildren(children);
							}
							children.add(self);
						}
					}
					// 从某下级节点开始查询的情况
					else if (organSn.equals(subResource.getStandardNumber())) {
						result = self;
					}
					// 下级其他节点的处理，如果下级根节点没有加入到返回结果，则所有下级节点都 不会加入到返回结果中
					else {
						// 没有从根节点开始查询的情况，下级不加入到返回结果中
						if (subResource.getParent() == null) {
							continue;
						}
						ListOrganDeviceTreeVO parentVO = subMap.get(subResource
								.getParent().getId());
						// 父机构没有找到，说明属于更高一级的机构
						if (null == parentVO) {
							continue;
						}
						List<ListOrganDeviceTreeVO> children = parentVO
								.getChildren();
						if (null == children) {
							children = new LinkedList<ListOrganDeviceTreeVO>();
							parentVO.setChildren(children);
						}
						children.add(self);
					}
				}
				// 构建下级摄像头
				else {
					// 查找父机构
					ListOrganDeviceTreeVO subOrganVO = subMap.get(subResource
							.getParent().getId());
					// 父机构没有找到，说明属于更高一级的机构
					if (null == subOrganVO) {
						continue;
					}
					// 构建摄像头对象
					ListOrganDeviceTreeVO vo = new ListOrganDeviceTreeVO();
					vo.setCode(subResource.getStandardNumber());
					vo.setLeaf(true);
					vo.setText(subResource.getName());
					// 加入到正确机构下
					List<ListOrganDeviceTreeVO> children = subOrganVO
							.getChildren();
					if (null == children) {
						children = new LinkedList<ListOrganDeviceTreeVO>();
						subOrganVO.setChildren(children);
					}
					children.add(vo);
				}
			}
		}

		return result;
	}
}
