package com.znsx.cms.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jdom.Content;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.DisplayWallDAO;
import com.znsx.cms.persistent.dao.DwsDAO;
import com.znsx.cms.persistent.dao.MonitorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.RoleResourcePermissionDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.WallSchemeDAO;
import com.znsx.cms.persistent.dao.WallSchemeItemDAO;
import com.znsx.cms.persistent.model.DisplayWall;
import com.znsx.cms.persistent.model.Dws;
import com.znsx.cms.persistent.model.Monitor;
import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionWall;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.WallScheme;
import com.znsx.cms.persistent.model.WallSchemeItem;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.MonitorManager;
import com.znsx.cms.service.model.DisplayWallVO;
import com.znsx.cms.service.model.ListDwsMonitorVO;
import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.service.model.MonitorVO;
import com.znsx.cms.service.model.WallVO;
import com.znsx.util.xml.ElementUtil;

/**
 * 视频输出业务实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午11:16:08
 */
public class MonitorManagerImpl extends BaseManagerImpl implements
		MonitorManager {
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private DisplayWallDAO displayWallDAO;
	@Autowired
	private MonitorDAO monitorDAO;
	@Autowired
	private DwsDAO dwsDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleResourcePermissionDAO roleResourcePermissionDAO;
	@Autowired
	private WallSchemeDAO wallSchemeDAO;
	@Autowired
	private WallSchemeItemDAO wallSchemeItemDAO;

	@Override
	public String createWall(String organId, String wallName, String note) {
		DisplayWall dw = new DisplayWall();
		// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
		// Object>();
		// params.put("organ.id", organId);
		// params.put("name", wallName);
		// List<DisplayWall> list = displayWallDAO.findByPropertys(params);
		// if (list.size() > 0) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + wallName + "] is already exist !");
		// }
		dw.setName(wallName);
		dw.setNote(note);
		dw.setOrgan(organDAO.findById(organId));
		displayWallDAO.save(dw);
		return dw.getId();
	}

	@Override
	public void updateWall(String id, String wallName, String note,
			String organId) {
		DisplayWall dw = displayWallDAO.findById(id);

		if (null != wallName) {
			// LinkedHashMap<String, Object> params = new LinkedHashMap<String,
			// Object>();
			// params.put("organ.id", organId);
			// params.put("name", wallName);
			// List<DisplayWall> list = displayWallDAO.findByPropertys(params);
			// if (list.size() >= 1) {
			// if (!list.get(0).getId().equals(id)) {
			// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
			// + wallName + "] is already exist !");
			// }
			// }
			dw.setName(wallName);
		}
		if (null != note) {
			dw.setNote(note);
		}
		displayWallDAO.update(dw);
	}

	@Override
	public void deleteWall(String id) {
		// 删除电视墙时先删除视频输出
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("displayWall.id", id);
		List<Monitor> monitors = monitorDAO.findByPropertys(params);
		for (Monitor monitor : monitors) {
			monitorDAO.delete(monitor);
		}
		// 删除电视墙
		displayWallDAO.deleteById(id);
	}

	@Override
	public DisplayWallVO getWall(String id) {
		DisplayWall dw = displayWallDAO.findById(id);
		DisplayWallVO vo = new DisplayWallVO();
		vo.setId(dw.getId() + "");
		vo.setName(dw.getName());
		vo.setNote(dw.getNote());
		return vo;
	}

	@Override
	public Integer wallTotalCount(String organId) {
		return displayWallDAO.wallTotalCount(organId);
	}

	@Override
	public List<DisplayWallVO> listWall(String organId, Integer startIndex,
			Integer limit) {
		List<DisplayWall> walls = displayWallDAO.listWall(organId, startIndex,
				limit);
		List<DisplayWallVO> listVO = new ArrayList<DisplayWallVO>();
		for (DisplayWall wall : walls) {
			DisplayWallVO vo = new DisplayWallVO();
			vo.setId(wall.getId());
			vo.setName(wall.getName());
			vo.setNote(wall.getNote());
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public String createMonitor(String organId, String wallId,
			Integer channelNumber, String standardNumber, String dwsId,
			String name) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// params.put("name", name);
		// params.put("displayWall.id", wallId);
		// List<Monitor> list = monitorDAO.findByPropertys(params);
		// 根据电视墙判断名称是否重复
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		// params.clear();
		// list.clear();
		params.put("standardNumber", standardNumber);
		List<Monitor> list = monitorDAO.findByPropertys(params);
		// 判断标准号是否重复
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		params.clear();
		list.clear();
		params.put("dws.id", dwsId);
		params.put("channelNumber", channelNumber);
		list = monitorDAO.findByPropertys(params);
		// 根据电视墙服务器判断通道号是否重复
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
					"channelNumber[" + channelNumber + "] is already exist !");
		}

		Monitor monitor = new Monitor();
		// String id = monitorDAO.getNextId("Monitor", 1);
		// monitor.setId(id);
		monitor.setChannelNumber(channelNumber);
		monitor.setDisplayWall(displayWallDAO.findById(wallId));
		monitor.setDws(dwsDAO.findById(dwsId));
		monitor.setName(name);
		monitor.setOrgan(organDAO.findById(organId));
		monitor.setStandardNumber(standardNumber);
		monitorDAO.save(monitor);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_MONITOR);
		return monitor.getId();
	}

	@Override
	public void updateMonitor(String id, String organId, String wallId,
			Integer channelNumber, String standardNumber, String dwsId,
			String name) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Monitor> list = null;
		// if (null != name) {
		// params.put("name", name);
		// params.put("displayWall.id", wallId);
		// list = monitorDAO.findByPropertys(params);
		// // 根据电视墙判断名称是否重复
		//
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		if (null != standardNumber) {
			// params.clear();
			// list.clear();
			params.put("standardNumber", standardNumber);
			list = monitorDAO.findByPropertys(params);
			// 判断标准号是否重复
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		if (null != channelNumber) {
			params.clear();
			if (null != list) {
				list.clear();
			}
			params.put("dws.id", dwsId);
			params.put("channelNumber", channelNumber);
			list = monitorDAO.findByPropertys(params);
			// 根据电视墙服务器判断通道号是否重复
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(ErrorCode.CHANNEL_NUMBER_EXIST,
							"channelNumber[" + channelNumber
									+ "] is already exist !");
				}
			}
		}
		Monitor monitor = monitorDAO.findById(id);
		if (null != channelNumber) {
			monitor.setChannelNumber(channelNumber);
		}
		if (StringUtils.isNotBlank(wallId)) {
			monitor.setDisplayWall(displayWallDAO.findById(wallId));
		}
		if (StringUtils.isNotBlank(dwsId)) {
			monitor.setDws(dwsDAO.findById(dwsId));
		}
		if (null != name) {
			monitor.setName(name);
		}
		if (StringUtils.isNotBlank(organId)) {
			monitor.setOrgan(organDAO.findById(organId));
		}
		if (null != standardNumber) {
			// 同步SN
			syncSN(monitor.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_MONITOR);
			monitor.setStandardNumber(standardNumber);
		}
		monitorDAO.update(monitor);

	}

	@Override
	public void deleteMonitor(String id) {
		// 同步SN
		Monitor monitor = monitorDAO.findById(id);
		syncSN(monitor.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_MONITOR);

		monitorDAO.delete(monitor);
	}

	@Override
	public MonitorVO getMonitor(String id) {
		Monitor monitor = monitorDAO.findById(id);
		MonitorVO vo = new MonitorVO();
		vo.setId(monitor.getId());
		vo.setChannelNumber(monitor.getChannelNumber() + "");
		vo.setDwsId(monitor.getDws() != null ? monitor.getDws().getId() : "");
		vo.setDwsName(monitor.getDws() != null ? monitor.getDws().getName()
				: "");
		vo.setName(monitor.getName());
		vo.setStandardNumber(monitor.getStandardNumber());
		vo.setWallId(monitor.getDisplayWall() != null ? monitor
				.getDisplayWall().getId() + "" : "");
		vo.setWallName(monitor.getDisplayWall() != null ? monitor
				.getDisplayWall().getName() : "");
		return vo;
	}

	@Override
	public Integer monitorTotalCount(String wallId) {
		return monitorDAO.monitorTotalCount(wallId);
	}

	@Override
	public List<MonitorVO> listMonitor(String wallId, Integer startIndex,
			Integer limit) {
		List<Monitor> monitors = monitorDAO.listMonitor(wallId, startIndex,
				limit);
		List<MonitorVO> listVO = new ArrayList<MonitorVO>();
		for (Monitor monitor : monitors) {
			MonitorVO vo = new MonitorVO();
			vo.setId(monitor.getId());
			vo.setChannelNumber(monitor.getChannelNumber() + "");
			vo.setDwsId(monitor.getDws() != null ? monitor.getDws().getId()
					: "");
			vo.setDwsName(monitor.getDws() != null ? monitor.getDws().getName()
					: "");
			vo.setName(monitor.getName());
			vo.setStandardNumber(monitor.getStandardNumber());
			vo.setWallId(monitor.getDisplayWall() != null ? monitor
					.getDisplayWall().getId() + "" : "");
			vo.setWallName(monitor.getDisplayWall() != null ? monitor
					.getDisplayWall().getName() : "");
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public void editWallLayout(@LogParam("id") String wallId,
			List<Element> monitorList) throws BusinessException {
		if (null == monitorList) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Element [MonitorList] could not be null !");
		}
		DisplayWall wall = displayWallDAO.findById(wallId);
		for (Element e : monitorList) {
			String id = e.getAttributeValue("Id");
			Monitor monitor = monitorDAO.findById(id);
			if (null != e.getAttributeValue("X")) {
				monitor.setX(e.getAttributeValue("X"));
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Element [X] could not be null !");
			}
			if (null != e.getAttributeValue("Y")) {
				monitor.setY(e.getAttributeValue("Y"));
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Element [Y] could not be null !");
			}
			if (null != e.getAttributeValue("Width")) {
				monitor.setWidth(e.getAttributeValue("Width"));
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Element [Width] could not be null !");
			}
			if (null != e.getAttributeValue("Height")) {
				monitor.setHeight(e.getAttributeValue("Height"));
			} else {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Element [Height] could not be null !");
			}
			monitor.setDisplayWall(wall);
		}
	}

	@Override
	public List<WallVO> listOrganWall(String organId) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("organ.id", organId);
		List<DisplayWall> list = displayWallDAO.findByPropertys(params);
		List<WallVO> rtnList = new LinkedList<WallVO>();

		for (DisplayWall wall : list) {
			WallVO vo = new WallVO();
			vo.setId(wall.getId().toString());
			vo.setName(wall.getName());
			// 查询视频输出列表
			List<WallVO.Monitor> voMonitors = new LinkedList<WallVO.Monitor>();
			params.clear();
			params.put("displayWall.id", wall.getId());
			List<Monitor> monitorList = monitorDAO.findByPropertys(params);
			for (Monitor monitor : monitorList) {
				WallVO.Monitor voMonitor = vo.new Monitor();
				voMonitor
						.setChannelNumber(monitor.getChannelNumber() != null ? monitor
								.getChannelNumber().toString() : "");
				voMonitor.setDWSId(monitor.getDws() != null ? monitor.getDws()
						.getId() : "");
				voMonitor
						.setDWSStandardNumber(monitor.getDws() != null ? monitor
								.getDws().getStandardNumber() : "");
				voMonitor.setHeight(monitor.getHeight());
				voMonitor.setId(monitor.getId());
				voMonitor.setName(monitor.getName());
				voMonitor.setStandardNumber(monitor.getStandardNumber());
				voMonitor.setWidth(monitor.getWidth());
				voMonitor.setX(monitor.getX());
				voMonitor.setY(monitor.getY());
				voMonitors.add(voMonitor);
			}
			vo.setMonitors(voMonitors);

			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public List<WallVO> listUserWall(String userId) {
		User user = userDAO.findById(userId);
		// 获取用户角色
		Set<Role> roles = user.getRoles();
		// 是否管理员角色
		boolean isAdmin = false;
		// 该用户的自定义角色ID列表
		List<String> roleIds = new ArrayList<String>();
		// 系统角色拥有机构下的所有电视墙权限
		for (Role role : roles) {
			if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_JUNIOR)
					|| role.getType().equals(TypeDefinition.ROLE_TYPE_SENIOR)) {
				isAdmin = true;
				break;
			} else {
				roleIds.add(role.getId());
			}
		}
		// 具有权限的电视墙集合
		List<DisplayWall> list = new LinkedList<DisplayWall>();
		// 查询参数条件
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		// 返回对象
		List<WallVO> rtnList = new LinkedList<WallVO>();
		// 系统角色
		if (isAdmin) {
			params.put("organ.id", user.getOrgan().getId());
			list = displayWallDAO.findByPropertys(params);
		}
		// 自定义角色
		else {
			if (roleIds.size() > 0) {
				List<RoleResourcePermission> authDevices = roleResourcePermissionDAO
						.listByRoleIds(roleIds, null);
				for (RoleResourcePermission authDevice : authDevices) {
					if (authDevice instanceof RoleResourcePermissionWall) {
						DisplayWall wall = ((RoleResourcePermissionWall) authDevice)
								.getDisplayWall();
						if (!list.contains(wall)) {
							list.add(wall);
						}
					}
				}

			}
		}
		for (DisplayWall wall : list) {
			WallVO vo = new WallVO();
			vo.setId(wall.getId().toString());
			vo.setName(wall.getName());
			// 查询视频输出列表
			List<WallVO.Monitor> voMonitors = new LinkedList<WallVO.Monitor>();
			params.clear();
			params.put("displayWall.id", wall.getId());
			List<Monitor> monitorList = monitorDAO.findByPropertys(params);
			for (Monitor monitor : monitorList) {
				WallVO.Monitor voMonitor = vo.new Monitor();
				voMonitor
						.setChannelNumber(monitor.getChannelNumber() != null ? monitor
								.getChannelNumber().toString() : "");
				voMonitor.setDWSId(monitor.getDws() != null ? monitor.getDws()
						.getId() : "");
				voMonitor
						.setDWSStandardNumber(monitor.getDws() != null ? monitor
								.getDws().getStandardNumber() : "");
				voMonitor.setHeight(monitor.getHeight());
				voMonitor.setId(monitor.getId());
				voMonitor.setName(monitor.getName());
				voMonitor.setStandardNumber(monitor.getStandardNumber());
				voMonitor.setWidth(monitor.getWidth());
				voMonitor.setX(monitor.getX());
				voMonitor.setY(monitor.getY());
				voMonitors.add(voMonitor);
			}
			vo.setMonitors(voMonitors);

			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public List<ListDwsMonitorVO> getDwsMonitor(String organId) {
		List<Dws> dwss = dwsDAO.findAll();
		List<ListDwsMonitorVO> list = new ArrayList<ListDwsMonitorVO>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		for (Dws dws : dwss) {
			ListDwsMonitorVO vo = new ListDwsMonitorVO();
			ListDwsVO dwsVo = new ListDwsVO();
			if (dws.getCcs() != null) {
				dwsVo.setCcsId(dws.getCcs().getId());
				dwsVo.setCcsName(dws.getCcs().getName());
			}
			dwsVo.setConfigValue(dws.getConfigValue());
			dwsVo.setCreateTime(dws.getCreateTime() != null ? dws
					.getCreateTime().toString() : "");
			dwsVo.setId(dws.getId());
			dwsVo.setLanIp(dws.getLanIp());
			dwsVo.setLocation(dws.getLocation());
			dwsVo.setName(dws.getName());
			dwsVo.setPort(dws.getPort());
			dwsVo.setStandardNumber(dws.getStandardNumber());
			dwsVo.setTelnetPort(dws.getTelnetPort());
			dwsVo.setWanIp(dws.getWanIp());
			vo.setDws(dwsVo);
			List<MonitorVO> listM = new ArrayList<MonitorVO>();
			params.put("dws.id", dws.getId());
			List<Monitor> monitors = monitorDAO.findByPropertys(params);
			params.clear();
			for (Monitor monitor : monitors) {
				MonitorVO monitorVo = new MonitorVO();
				monitorVo
						.setChannelNumber(monitor.getChannelNumber() != null ? monitor
								.getChannelNumber().toString() : "");
				monitorVo.setDwsId(monitor.getDws().getId());
				monitorVo.setDwsName(monitor.getDws().getName());
				monitorVo.setId(monitor.getId());
				monitorVo.setName(monitor.getName());
				monitorVo.setStandardNumber(monitor.getStandardNumber());
				listM.add(monitorVo);
			}
			vo.setListMonitor(listM);
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createWallScheme(String name, String userId, String organId,
			String wallId, List<Element> items) throws BusinessException {
		WallScheme wallScheme = new WallScheme();
		wallScheme.setName(name);
		wallScheme.setOrgan(organDAO.findById(organId));
		wallScheme.setUser(userDAO.findById(userId));
		DisplayWall wall = displayWallDAO.findById(wallId);
		wallScheme.setWall(wall);
		wallSchemeDAO.save(wallScheme);

		Set<Monitor> monitors = wall.getMonitors();

		while (items.size() > 0) {
			Element item = items.get(0);
			WallSchemeItem entity = new WallSchemeItem();
			String id = item.getAttributeValue("Id");
			Monitor monitor = findMonitor(monitors, id);
			if (null == monitor) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Monitor[" + id + "] is not belong to DisplayWall["
								+ wallId + "]");
			}
			entity.setMonitor(monitor);
			entity.setWallScheme(wallScheme);
			// List<Element> list = item.getChildren();
			// // 只取list中的第一个元素内容保存至content中
			// if (list.size() <= 0) {
			// throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
			// "Monitor[" + sn + "] play item is null!");
			// }
			entity.setContent(ElementUtil.element2String(item));
			wallSchemeItemDAO.save(entity);
		}

		return wallScheme.getId();
	}

	@Override
	public void deleteWallScheme(@LogParam("id") String id)
			throws BusinessException {
		wallSchemeDAO.deleteById(id);
	}

	@Override
	public List<WallScheme> listWallScheme(String userId, String organId) {
		List<WallScheme> list = wallSchemeDAO.listWallScheme(userId, organId);
		for (WallScheme wallScheme : list) {
			Set<WallSchemeItem> set = wallScheme.getItems();
			for (WallSchemeItem item : set) {
				item.getContent();
			}
		}

		return list;
	}

	private Monitor findMonitor(Set<Monitor> monitors, String id) {
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"Monitor id cann not be empty !");
		}
		for (Monitor monitor : monitors) {
			if (id.equals(monitor.getId())) {
				return monitor;
			}
		}
		return null;
	}

	@Override
	public void updateWallScheme(@LogParam("id") String schemeId,
			@LogParam("name") String name, List<Element> monitors)
			throws BusinessException {
		WallScheme wallScheme = wallSchemeDAO.findById(schemeId);
		if (null != name) {
			wallScheme.setName(name);
		}
		Set<WallSchemeItem> set = wallScheme.getItems();

		for (Iterator<WallSchemeItem> it = set.iterator(); it.hasNext();) {
			WallSchemeItem item = it.next();
			it.remove();
			wallSchemeItemDAO.delete(item);
		}

		DisplayWall wall = displayWallDAO
				.findById(wallScheme.getWall().getId());
		Set<Monitor> monitorSet = wall.getMonitors();
		while (monitors.size() > 0) {
			Element item = monitors.get(0);
			WallSchemeItem entity = new WallSchemeItem();
			String id = item.getAttributeValue("Id");
			Monitor monitor = findMonitor(monitorSet, id);
			if (null == monitors) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"Monitor[" + id + "] is not belong to DisplayWall["
								+ wallScheme.getWall().getId() + "]");
			}
			entity.setMonitor(monitor);
			entity.setWallScheme(wallScheme);
			entity.setContent(ElementUtil.element2String(item));
			wallSchemeItemDAO.save(entity);
		}
	}
}
