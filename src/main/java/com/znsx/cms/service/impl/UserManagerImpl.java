package com.znsx.cms.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.CcsDAO;
import com.znsx.cms.persistent.dao.ClassesDAO;
import com.znsx.cms.persistent.dao.DeviceFavoriteDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineDAO;
import com.znsx.cms.persistent.dao.DeviceOnlineRealDAO;
import com.znsx.cms.persistent.dao.GisDAO;
import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.LogOperationDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PlaySchemeDAO;
import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.dao.SysLogDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserFavoriteDAO;
import com.znsx.cms.persistent.dao.UserOperationLogDAO;
import com.znsx.cms.persistent.dao.UserSessionDAO;
import com.znsx.cms.persistent.dao.UserSessionHistoryDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Ccs;
import com.znsx.cms.persistent.model.Classes;
import com.znsx.cms.persistent.model.DeviceFavorite;
import com.znsx.cms.persistent.model.DeviceOnline;
import com.znsx.cms.persistent.model.DeviceOnlineReal;
import com.znsx.cms.persistent.model.Gis;
import com.znsx.cms.persistent.model.LogOperation;
import com.znsx.cms.persistent.model.MenuOperation;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.PlayScheme;
import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserFavorite;
import com.znsx.cms.persistent.model.UserOperationLog;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionCcs;
import com.znsx.cms.persistent.model.UserSessionCrs;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.UserSessionDws;
import com.znsx.cms.persistent.model.UserSessionEns;
import com.znsx.cms.persistent.model.UserSessionHistory;
import com.znsx.cms.persistent.model.UserSessionMss;
import com.znsx.cms.persistent.model.UserSessionPts;
import com.znsx.cms.persistent.model.UserSessionRms;
import com.znsx.cms.persistent.model.UserSessionRss;
import com.znsx.cms.persistent.model.UserSessionSrs;
import com.znsx.cms.persistent.model.UserSessionUser;
import com.znsx.cms.service.common.StandardObjectCode;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.AndroidUpdate;
import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.service.model.GisLogonVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.UserLogonVO;
import com.znsx.cms.service.model.UserSessionVO;
import com.znsx.cms.service.model.UserViewVO;
import com.znsx.cms.web.dto.omc.UserLoginDTO;
import com.znsx.util.base64.Base64Utils;
import com.znsx.util.cache.CacheData;
import com.znsx.util.database.CacheUtil;
import com.znsx.util.file.Configuration;
import com.znsx.util.network.NetworkUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * UserManagerImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:57:56
 */
public class UserManagerImpl extends BaseManagerImpl implements UserManager {

	private final static Log log = LogFactory.getLog(UserManagerImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserSessionDAO userSessionDAO;

	@Autowired
	private UserFavoriteDAO userFavoriteDAO;

	@Autowired
	private CcsDAO ccsDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private PlaySchemeDAO playSchemeDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private UserSessionHistoryDAO userSessionHistoryDAO;
	@Autowired
	private LogOperationDAO logOperationDAO;
	@Autowired
	private SysLogDAO sysLogDAO;
	@Autowired
	private GisDAO gisDAO;
	@Autowired
	private SubPlatformResourceDAO subPlatformResourceDAO;
	@Autowired
	private DeviceFavoriteDAO deviceFavoriteDAO;
	@Autowired
	private DeviceOnlineRealDAO deviceOnlineRealDAO;
	@Autowired
	private DeviceOnlineDAO deviceOnlineDAO;
	@Autowired
	private UserOperationLogDAO userOperationLogDAO;
	@Autowired
	private ClassesDAO classesDAO;

	public String csLogin(String userName, String passwd, String ip,
			String clientType) {
		String getUserByUserName = SqlFactory.getInstance().getUserByUserName();
		List<User> user = userDAO.findByHql(getUserByUserName, userName);
		if (user.size() <= 0) {
			throw new BusinessException(ErrorCode.USER_NOT_FOUND,
					"logon_name [" + userName + "] not found !");
		} else {
			if (!user.get(0).getLogonName().equals(userName)) {
				throw new BusinessException(ErrorCode.USER_NOT_FOUND,
						"logon_name [" + userName + "] not found !");
			}
		}
		if (!passwd.equals(user.get(0).getPassword())) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR,
					"password error !");
		}
		if (user.get(0).getStatus().intValue() != TypeDefinition.STATUS_AVAILABLE) {
			throw new BusinessException(ErrorCode.USER_STATUS_INVALID,
					"User status is not available !");
		}
		// 判断最大登陆数是否达到
		// Integer count = userSessionDAO.userSessionTotalCount(user.get(0)
		// .getId());
		// Integer maxConnect = user.get(0).getMaxConnect();
		// if (count >= maxConnect) {
		// throw new BusinessException(ErrorCode.MAX_CONNECT_LIMIT,
		// "Max number[" + maxConnect.toString()
		// + "] of user login restrictions !");
		// }

		// 判断是否达到最大登录数
		Integer count = userSessionDAO.userSessionTotalCount(user.get(0)
				.getId());
		Integer maxConnect = user.get(0).getMaxConnect();
		if (count >= maxConnect) {
			// 剔除第一个登录用户
			List<UserSessionUser> list = userSessionDAO.findFirstUser(user.get(
					0).getId());
			// 保存到sessionhistory
			UserSessionUser us = list.get(0);
			UserSessionHistory ush = new UserSessionHistory();
			ush.setId(us.getId());
			ush.setClientType(us.getClientType());
			ush.setIp(us.getIp());
			ush.setKickFlag((short) 1);
			ush.setLogoffTime(System.currentTimeMillis());
			ush.setLogonName(us.getUser().getLogonName());
			ush.setLogonTime(us.getLogonTime());
			ush.setOrganId(us.getOrganId());
			ush.setOrganName(us.getOrganName());
			ush.setPath(us.getPath());
			ush.setStandardNumber(us.getStandardNumber());
			ush.setTicket(us.getId());
			ush.setUserId(us.getUser().getId());
			userSessionHistoryDAO.save(ush);
			// userSessionHistoryDAO.findById(ush.getId()).setTicket(ush.getId());
			// 删除session
			userSessionDAO.delete(us);
		}

		// 用户名称密码匹配对后，生成SESSION
		// String sessionId = userSessionDAO.getNextId("UserSession", 1);
		UserSessionUser userSession = new UserSessionUser();
		// userSession.setId(sessionId);
		userSession.setUser(user.get(0));
		userSession.setIp(ip);
		userSession.setTicket("");
		userSession.setLogonName(userName);
		userSession.setOrganId(user.get(0).getOrgan().getId());
		userSession.setOrganName(user.get(0).getOrgan().getName());
		userSession.setPath(user.get(0).getOrgan().getPath());
		userSession.setStandardNumber(user.get(0).getStandardNumber());
		userSession.setClientType(clientType);
		userSession.setLogonTime(System.currentTimeMillis());
		userSession.setUpdateTime(System.currentTimeMillis());
		userSessionDAO.save(userSession);
		userSessionDAO.findById(userSession.getId()).setTicket(
				userSession.getId());
		return userSession.getId();
	}

	public String createFavorite(String favoriteName, List<String> channelIds,
			String userId) {
		// 向收藏夹添加数据
		UserFavorite userFavorite = new UserFavorite();
		// 判断同一个用户的搜藏夹名称是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userId", userId);
		params.put("name", favoriteName);
		List<UserFavorite> favorites = userFavoriteDAO.findByPropertys(params);
		if (favorites.size() > 0) {
			throw new BusinessException(ErrorCode.NAME_EXIST, "favoriteName["
					+ favoriteName + "] is already exist !");
		}

		userFavorite.setName(favoriteName);
		userFavorite.setCreateTime(System.currentTimeMillis());
		userFavorite.setUserId(userId);
		userFavorite.setNote("");
		userFavoriteDAO.save(userFavorite);

		for (String cameraId : channelIds) {
			DeviceFavorite entity = new DeviceFavorite();
			entity.setFavoriteId(userFavorite.getId());
			entity.setDeviceId(cameraId);
			deviceFavoriteDAO.save(entity);
		}
		return userFavorite.getId();
	}

	public void updateFavorite(String favoriteId, String favoriteName,
			List<String> channelIds, String userId) {
		// 修改收藏夹数据
		UserFavorite userFavorite = userFavoriteDAO.findById(favoriteId);
		userFavorite.setId(favoriteId);
		userFavorite.setCreateTime(System.currentTimeMillis());
		userFavorite.setUserId(userId);
		if (null != favoriteName) {
			// 判断同一个用户的搜藏夹名称是否重复
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("userId", userId);
			params.put("name", favoriteName);
			List<UserFavorite> favorites = userFavoriteDAO
					.findByPropertys(params);
			if (favorites.size() > 0) {
				if (favorites.get(0).getId() != favoriteId) {
					throw new BusinessException(ErrorCode.NAME_EXIST,
							"favoriteName[" + favoriteName
									+ "] is already exist !");
				}
			}
			userFavorite.setName(favoriteName);
		}
		userFavoriteDAO.update(userFavorite);
		// 清除老的
		deviceFavoriteDAO.deleteByFavoriteId(favoriteId);
		// 添加新的
		for (String cameraId : channelIds) {
			DeviceFavorite entity = new DeviceFavorite();
			entity.setFavoriteId(favoriteId);
			entity.setDeviceId(cameraId);
			deviceFavoriteDAO.save(entity);
		}
	}

	public void deleteFavorite(String favoriteId) {
		deviceFavoriteDAO.deleteByFavoriteId(favoriteId);
		userFavoriteDAO.deleteById(favoriteId);
	}

	public Element listFavorite(String userId) throws Exception {
		Element favoriteList = new Element("FavoriteList");
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userId", userId);
		List<UserFavorite> favorites = userFavoriteDAO.findByPropertys(params);
		for (UserFavorite userfavorite : favorites) {
			Element favorite = new Element("Favorite");
			favorite.setAttribute("Id", userfavorite.getId() + "");
			favorite.setAttribute("Name",
					userfavorite.getName() != null ? userfavorite.getName()
							: "");
			favoriteList.addContent(favorite);

			List<DeviceFavorite> list = deviceFavoriteDAO
					.listByFavoriteId(userfavorite.getId());
			for (DeviceFavorite df : list) {
				Camera camera = cameraDAO.loadById(df.getDeviceId());
				if (null != camera) {
					Element channel = new Element("Channel");
					channel.setAttribute("Id", camera.getId());
					channel.setAttribute("StandardNumber",
							camera.getStandardNumber());
					channel.setAttribute("Name", camera.getName());
					channel.setAttribute("Type",
							MyStringUtil.object2StringNotNull(camera.getType()));
					channel.setAttribute("SubType", camera.getSubType());
					channel.setAttribute("ParentId", camera.getParent().getId());
					channel.setAttribute("ManufacturerId", camera
							.getManufacturer() == null ? "" : camera
							.getManufacturer().getId());
					channel.setAttribute("Location", MyStringUtil
							.object2StringNotNull(camera.getLocation()));
					channel.setAttribute("ChannelNumber", MyStringUtil
							.object2StringNotNull(camera.getChannelNumber()));
					channel.setAttribute("Transport", camera.getParent()
							.getTransport());
					channel.setAttribute("Mode", camera.getParent().getMode());
					favorite.addContent(channel);
				} else {
					SubPlatformResource resource = subPlatformResourceDAO
							.loadById(df.getDeviceId());
					if (null != resource) {
						Element channel = new Element("Channel");
						channel.setAttribute("Id", resource.getId());
						channel.setAttribute("StandardNumber",
								resource.getStandardNumber());
						channel.setAttribute("Name", resource.getName());
						channel.setAttribute("Type", MyStringUtil
								.object2StringNotNull(resource.getType()));
						channel.setAttribute("SubType", MyStringUtil
								.object2StringNotNull(resource.getPtzType()));
						channel.setAttribute("ParentId", resource.getParent()
								.getId());
						channel.setAttribute("ManufacturerId", MyStringUtil
								.object2StringNotNull(resource
										.getManufacturer()));
						channel.setAttribute("Location", MyStringUtil
								.object2StringNotNull(resource.getAddress()));
						channel.setAttribute("ChannelNumber", "1");
						channel.setAttribute("Transport", "TCP");
						channel.setAttribute("Mode", "compatible");
						favorite.addContent(channel);
					}
				}
			}
		}

		return favoriteList;
	}

	@Override
	public UserLoginDTO omcLogin(String userName, String password, String ip)
			throws BusinessException {
		String getUserByUserName = SqlFactory.getInstance().getUserByUserName();
		User user = userDAO.findUniResult(getUserByUserName, userName);
		if (null == user) {
			throw new BusinessException(ErrorCode.USER_NOT_FOUND,
					"logon_name [" + userName + "] not found !");
		} else {
			if (!user.getLogonName().equals(userName)) {
				throw new BusinessException(ErrorCode.USER_NOT_FOUND,
						"logon_name [" + userName + "] not found !");
			}
		}
		// 匹配密码
		if (!password.equals(user.getPassword())) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR,
					"password error !");
		}
		// 如果不是管理员不允许登录
		// Set<Role> roles = user.getRoles();
		// Iterator<Role> it = roles.iterator();
		// // 是否管理员标志
		// boolean adminFlag = false;
		// while (it.hasNext()) {
		// Role role = it.next();
		// if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)) {
		// adminFlag = true;
		// break;
		// }
		// }
		// if (!adminFlag) {
		// throw new BusinessException(ErrorCode.USER_ROLE_INVALID, "User["
		// + userName + "] is not admin user !");
		// }

		// 用户禁用状态不允许登录
		if (user.getStatus().intValue() != TypeDefinition.STATUS_AVAILABLE) {
			throw new BusinessException(ErrorCode.USER_STATUS_INVALID,
					"User status is not available !");
		}

		// 判断最大登陆数是否达到
		Integer count = userSessionDAO.userSessionTotalCount(user.getId());
		Integer maxConnect = user.getMaxConnect();
		if (count >= maxConnect) {
			throw new BusinessException(ErrorCode.USER_MAX_CONNECT_LIMIT,
					"Max number[" + maxConnect.toString()
							+ "] of user login restrictions !");
		}

		// 生成Session
		UserSessionUser userSession = new UserSessionUser();
		// String id = userSessionDAO.getNextId("UserSession", 1);
		userSession.setClientType(TypeDefinition.CLIENT_TYPE_OMC);
		// userSession.setId(id);
		userSession.setIp(ip);
		userSession.setLogonName(userName);
		userSession.setLogonTime(System.currentTimeMillis());
		userSession.setStandardNumber(user.getStandardNumber());
		userSession.setTicket("");
		userSession.setUser(userDAO.findById(user.getId()));
		userSession.setOrganId(user.getOrgan().getId());
		userSession.setPath(user.getOrgan().getPath());
		userSession.setOrganName(user.getOrgan().getName());
		userSession.setUpdateTime(System.currentTimeMillis());
		userSessionDAO.save(userSession);

		userSessionDAO.findById(userSession.getId()).setTicket(
				userSession.getId());

		UserLoginDTO rtn = new UserLoginDTO();
		UserLoginDTO.User dto = rtn.new User();
		dto.setAddress(user.getAddress());
		Ccs ccs = user.getCcs();
		if (null != ccs) {
			dto.setCcsId(ccs.getId());
		}
		dto.setEmail(user.getEmail());
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setNote(user.getNote());
		dto.setOrganId(user.getOrgan().getId());
		dto.setPhone(user.getPhone());
		dto.setPriority(user.getPriority().toString());
		dto.setSessionId(userSession.getTicket());
		dto.setSex(user.getSex().toString());
		dto.setStandardNumber(user.getStandardNumber());
		rtn.setUser(dto);
		return rtn;
	}

	@Override
	public String createUser(String name, String standardNumber, String ccsId,
			@LogParam("name") String logonName, String password, Short sex,
			String email, String phone, String address, String organId,
			Short priority, String note, Integer maxConnect)
			throws BusinessException {
		// 判断standardNumber是否重复
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<User> users = userDAO.findByPropertys(params);
		if (users.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}

		// 判断logonName是否重复
		params.clear();
		params.put("logonName", logonName);
		users = userDAO.findByPropertys(params);
		if (users.size() >= 1) {
			throw new BusinessException(ErrorCode.USER_ACCOUNT_EXIST,
					"logonName[" + logonName + "] is already exist !");
		}

		User user = new User();
		// String id = userDAO.getNextId("User", 1);
		// user.setId(id);
		user.setAddress(address);
		if (StringUtils.isNotBlank(ccsId)) {
			user.setCcs(ccsDAO.findById(ccsId));
		}
		user.setCreateTime(System.currentTimeMillis());
		user.setEmail(email);
		user.setLogonName(logonName);
		user.setName(name);
		user.setNote(note);
		user.setOrgan(organDAO.findById(organId));
		user.setPassword(password);
		user.setPhone(phone);
		user.setPriority(priority);
		user.setSex(sex);
		user.setStandardNumber(standardNumber);
		user.setStatus((short) TypeDefinition.STATUS_AVAILABLE);
		user.setMaxConnect(maxConnect);
		userDAO.save(user);
		// 同步SN
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_USER);
		return user.getId();
	}

	@Override
	public void bindImage(String userId, String imageId)
			throws BusinessException {
		User user = userDAO.findById(userId);
		user.setImageId(imageId);
	}

	@Override
	public ResourceVO checkSession(String sessionTicket)
			throws BusinessException {
		String hql = SqlFactory.getInstance().getUserSessionByTicket();
		UserSession us = userSessionDAO.findUniResult(hql, sessionTicket);
		if (null == us) {
			UserSessionHistory ush;
			try {
				ush = userSessionHistoryDAO.findById(sessionTicket);
			} catch (BusinessException e) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"UserSession" + "[" + sessionTicket + "] invalid !");
			}
			Short flag = ush.getKickFlag();
			if (null != flag && flag.intValue() == 1) {
				throw new BusinessException(ErrorCode.USER_SESSION_KICK_OFF,
						"session[" + sessionTicket
								+ "] has been kicked off by administrator !");
			} else {
				throw new BusinessException(ErrorCode.USER_SESSION_EXPIRED,
						"session[" + sessionTicket + "] is expired !");
			}
		}
		// 修改session更新时间
		Long cacheTime = (Long) CacheUtil.getCache(us.getId(), REGION);
		if ((null != cacheTime && cacheTime.longValue() < System
				.currentTimeMillis()) || null == cacheTime) {
			CacheUtil.putCache(us.getId(),
					Long.valueOf(System.currentTimeMillis()), REGION);
		}

		ResourceVO vo = new ResourceVO();
		String clientType = us.getClientType();
		if (us instanceof UserSessionUser) {
			vo.setId(((UserSessionUser) us).getUser().getId());
			if (clientType.equals(TypeDefinition.CLIENT_TYPE_CS)
					|| clientType.equals(TypeDefinition.CLIENT_TYPE_OMC)) {
				User user = userDAO.findById(((UserSessionUser) us).getUser()
						.getId());
				vo.setPriority(user.getPriority().toString());
				vo.setRealName(user.getName());
			}
		} else if (us instanceof UserSessionCcs) {
			vo.setId(((UserSessionCcs) us).getCcs().getId());
		} else if (us instanceof UserSessionCrs) {
			vo.setId(((UserSessionCrs) us).getCrs().getId());
		} else if (us instanceof UserSessionMss) {
			vo.setId(((UserSessionMss) us).getMss().getId());
		} else if (us instanceof UserSessionDws) {
			vo.setId(((UserSessionDws) us).getDws().getId());
		} else if (us instanceof UserSessionPts) {
			vo.setId(((UserSessionPts) us).getPts().getId());
		} else if (us instanceof UserSessionDas) {
			vo.setId(((UserSessionDas) us).getDas().getId());
		} else if (us instanceof UserSessionEns) {
			vo.setId(((UserSessionEns) us).getEns().getId());
		} else if (us instanceof UserSessionRms) {
			vo.setId(((UserSessionRms) us).getRms().getId());
		} else if (us instanceof UserSessionRss) {
			vo.setId(((UserSessionRss) us).getRss().getId());
		} else if (us instanceof UserSessionSrs) {
			vo.setId(((UserSessionSrs) us).getSrs().getId());
		}
		vo.setName(us.getLogonName());
		vo.setType(us.getClientType());
		vo.setOrganId(us.getOrganId());
		return vo;
	}

	@Override
	public void bindUserRoles(@LogParam("id") String userId, String roleIds)
			throws BusinessException {
		User user = userDAO.findById(userId);
		if (user.getLogonName().equals("admin")) {
			throw new BusinessException(ErrorCode.ADMIN_ROLE_CANNOT_EDIT,
					"Could not modify user[admin]'s role !");
		}
		Set<Role> roles = user.getRoles();
		// 清除已有的角色关联
		roles.clear();
		for (Role role : roles) {
			role.getUsers().remove(user);
		}

		// 关联新的
		if (StringUtils.isNotBlank(roleIds)) {
			String[] roleArray = roleIds.split(",");
			try {
				for (int i = 0; i < roleArray.length; i++) {
					Role role = roleDAO.findById(roleArray[i]);
					role.getUsers().add(user);
					user.getRoles().add(role);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"roleIds[" + roleIds + "] invalid !");
			}
		}
	}

	@Override
	public void updateUser(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String ccsId,
			String password, Short sex, String email, String phone,
			String address, String organId, Short priority, String note,
			Short status, Integer maxConnect) throws BusinessException {
		User user = userDAO.findById(id);
		if (null != name) {
			user.setName(name);
		}
		if (null != standardNumber) {
			// 同步SN
			syncSN(user.getStandardNumber(), standardNumber,
					TypeDefinition.RESOURCE_TYPE_USER);
			user.setStandardNumber(standardNumber);
		}
		if (null != ccsId) {
			user.setCcs(ccsDAO.findById(ccsId));
		}
		if (null != password) {
			user.setPassword(password);
		}
		if (null != sex) {
			user.setSex(sex);
		}
		if (null != email) {
			user.setEmail(email);
		}
		if (null != phone) {
			user.setPhone(phone);
		}
		if (null != address) {
			user.setAddress(address);
		}
		if (null != organId) {
			user.setOrgan(organDAO.findById(organId));
		}
		if (null != priority) {
			user.setPriority(priority);
		}
		if (null != note) {
			user.setNote(note);
		}
		if (null != status) {
			user.setStatus(status);
		}
		if (null != maxConnect) {
			user.setMaxConnect(maxConnect);
		}
	}

	@Override
	public void deleteUser(@LogParam("id") String id) throws BusinessException {
		User user = userDAO.findById(id);
		if (user.getLogonName().equals("admin")) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"User[admin] could not be deleted !");
		}
		// 删除关联的图片
		if (user.getImageId() != null) {
			imageDAO.deleteById(user.getImageId());
		}
		// 删除用户收藏夹
		userFavoriteDAO.deleteByUser(id);
		// 登出用户会话
		// userSessionDAO.copyUserSessionToHistory(id);
		List<UserSessionUser> userSessons = userSessionDAO
				.findUserSessionByUserId(id, null, null);
		for (UserSessionUser userSession : userSessons) {
			UserSessionHistory ush = new UserSessionHistory();
			ush.setClientType(userSession.getClientType());
			ush.setId(userSession.getId());
			ush.setIp(userSession.getIp());
			ush.setKickFlag((short) 0);
			ush.setLogoffTime(System.currentTimeMillis());
			ush.setLogonName(userSession.getUser().getLogonName());
			ush.setLogonTime(userSession.getLogonTime());
			ush.setOrganId(userSession.getOrganId());
			ush.setOrganName(userSession.getOrganName());
			ush.setPath(userSession.getPath());
			ush.setStandardNumber(userSession.getStandardNumber());
			ush.setTicket(userSession.getId());
			ush.setUserId(id);
			userSessionHistoryDAO.save(ush);
			// userSessionHistoryDAO.findById(ush.getId()).setTicket(ush.getId());
		}
		userSessionDAO.deleteByUser(id);
		// 删除用户与角色的关联
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			role.getUsers().remove(user);
		}
		// 同步SN
		syncSN(user.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_USER);
		// 最后删除用户
		userDAO.delete(user);
	}

	public String createPlayScheme(String name, Element playScheme,
			String userId) {

		String organId = userDAO.findById(userId) != null ? userDAO
				.findById(userId).getOrgan().getId() : "";
		PlayScheme ps = new PlayScheme();
		ps.setName(name);
		ps.setUserId(userId);
		ps.setOrganId(organId);
		playSchemeDAO.save(ps);

		XMLOutputter out = new XMLOutputter();
		Document doc = new Document();
		playScheme.setAttribute("Id", ps.getId());
		doc.addContent(playScheme.detach());
		String schemeConfig = out.outputString(doc);
		PlayScheme playsc = playSchemeDAO.findById(ps.getId());
		playsc.setSchemeConfig(schemeConfig);
		playSchemeDAO.update(playsc);

		return ps.getId();

	}

	@Override
	public GetUserVO getUser(String id) throws BusinessException {
		User user = userDAO.findById(id);
		int count = userSessionDAO.userSessionTotalCount(id).intValue();
		GetUserVO vo = new GetUserVO();
		vo.setAddress(user.getAddress());
		Ccs ccs = user.getCcs();
		if (null != ccs) {
			vo.setCcsId(ccs.getId());
			vo.setCcsName(ccs.getName());
		}
		vo.setCreateTime(user.getCreateTime().toString());
		vo.setEmail(user.getEmail());
		vo.setId(user.getId());
		vo.setImageId(user.getImageId() != null ? user.getImageId().toString()
				: "");
		vo.setLogonName(user.getLogonName());
		vo.setName(user.getName());
		vo.setNote(user.getNote());
		vo.setOrganId(user.getOrgan().getId());
		vo.setOrganName(user.getOrgan().getName());
		vo.setPassword(user.getPassword());
		vo.setPhone(user.getPhone());
		vo.setPriority(user.getPriority().toString());
		vo.setSex(user.getSex().toString());
		vo.setStandardNumber(user.getStandardNumber());
		vo.setMaxConnect(user.getMaxConnect() != null ? user.getMaxConnect()
				.toString() : "1");
		StringBuffer roleNames = new StringBuffer();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			roleNames.append(role.getName());
			roleNames.append(",");
		}
		vo.setRoleNames(roleNames.toString());
		vo.setOnlineStatus(count > 0 ? "1" : "0");
		return vo;
	}

	@Override
	public List<GetUserVO> listUser(String organId, String userName,
			String logonName, Integer startIndex, Integer limit)
			throws BusinessException {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		List<User> users = userDAO.listUser(organs, userName, logonName,
				startIndex, limit);
		// 当前在线的用户ID集合
		List<String> userList = userSessionDAO.listOnlineUserId();
		List<GetUserVO> list = new ArrayList<GetUserVO>();

		for (User user : users) {
			GetUserVO vo = new GetUserVO();
			vo.setAddress(user.getAddress());
			Ccs ccs = user.getCcs();
			if (null != ccs) {
				vo.setCcsId(ccs.getId());
				vo.setCcsName(ccs.getName());
			}
			vo.setCreateTime(user.getCreateTime().toString());
			vo.setEmail(user.getEmail());
			vo.setId(user.getId());
			vo.setImageId(user.getImageId() != null ? user.getImageId()
					.toString() : "");
			vo.setLogonName(user.getLogonName());
			vo.setName(user.getName());
			vo.setNote(user.getNote());
			vo.setOrganId(user.getOrgan().getId());
			vo.setOrganName(user.getOrgan().getName());
			vo.setPassword(user.getPassword());
			vo.setPhone(user.getPhone());
			vo.setPriority(user.getPriority().toString());
			vo.setSex(user.getSex().toString());
			vo.setStandardNumber(user.getStandardNumber());
			vo.setStatus(user.getStatus() != null ? user.getStatus().toString()
					: "0");
			vo.setMaxConnect(user.getMaxConnect() != null ? user
					.getMaxConnect().toString() : "1");
			vo.setOnlineStatus(userList.contains(user.getId()) ? "1" : "0");
			list.add(vo);
		}
		return list;
	}

	public void updatePlayScheme(String playSchemeId, String name,
			Element playScheme, String userId) {

		PlayScheme ps = playSchemeDAO.findById(playSchemeId);
		ps.setId(playSchemeId);
		if (null != name) {
			ps.setName(name);
		}
		ps.setUserId(userId);

		XMLOutputter out = new XMLOutputter();
		Document doc = new Document();
		playScheme.setAttribute("Name", ps.getName() != null ? ps.getName()
				: "");
		doc.addContent(playScheme.detach());
		String schemeConfig = out.outputString(doc);
		ps.setSchemeConfig(schemeConfig);

		playSchemeDAO.update(ps);
	}

	public void deletePlayScheme(String id) {
		playSchemeDAO.deleteById(id);
	}

	public Element listPlayScheme(String userId) throws JDOMException,
			IOException {
		boolean flag = false;
		List<PlayScheme> playSchemes = new ArrayList<PlayScheme>();
		Element element = new Element("PlaySchemeList");
		User user = userDAO.findById(userId);
		Set<Role> roles = user.getRoles();
		if (null != roles) {
			Iterator<Role> iter = roles.iterator();
			while (iter.hasNext()) {
				if (iter.next().getType()
						.equals(TypeDefinition.ROLE_TYPE_ADMIN)) {
					flag = true;
				}
			}
		}
		if (flag) {
			String[] organs = organDAO.findOrgansByOrganId(user.getOrgan()
					.getId());
			playSchemes = playSchemeDAO.findByOrgans(organs);
		} else {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("userId", userId);
			playSchemes = playSchemeDAO.findByPropertys(params);
		}
		SAXBuilder builder = new SAXBuilder();

		for (PlayScheme ps : playSchemes) {
			String playSch = ps.getSchemeConfig();
			try {
				Reader returnQuote = new StringReader(playSch);
				Document doc = builder.build(returnQuote);
				Element playScheme = doc.getRootElement();
				element.addContent(playScheme.detach());
			} catch (JDOMParseException jdom) {
				jdom.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"schemeConfig [" + playSch + "] INVALID");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return element;
	}

	@Override
	public UserViewVO getPermissionsByUserId(String id) {
		UserViewVO uv = new UserViewVO();
		User user = userDAO.findById(id);
		Set<Role> roles = user.getRoles();
		if (roles.size() > 0) {
			for (Role role : roles) {
				// 如果角色是系统管理员、子管理员、高级管理员的话返回默认的视图权限并退出FOR循环
				if (role.getType().equals(TypeDefinition.ROLE_TYPE_ADMIN)
						|| role.getType().equals(
								TypeDefinition.ROLE_TYPE_SENIOR)) {
					uv.setAlarm("1");
					uv.setGis("1");
					uv.setVideo("1");
					uv.setQuery("1");
					uv.setDisplay("1");
					break;
				} else {
					// 如果不是系统默认的管理员角色，则根据角色判断视图权限
					Set<MenuOperation> menuOperations = role
							.getMenuOperations();
					if (menuOperations.size() > 0) {
						for (MenuOperation menuOperation : menuOperations) {
							if (menuOperation.getMenuCode().equals(
									TypeDefinition.MENU_OPERATION_VIDEO)) {
								uv.setVideo("1");
							} else if (menuOperation.getMenuCode().equals(
									TypeDefinition.MENU_OPERATION_ALARM)) {
								uv.setAlarm("1");
							} else if (menuOperation.getMenuCode().equals(
									TypeDefinition.MENU_OPERATION_GIS)) {
								uv.setGis("1");
							} else if (menuOperation.getMenuCode().equals(
									TypeDefinition.MENU_OPERATION_DISPLAY)) {
								uv.setDisplay("1");
							} else if (menuOperation.getMenuCode().equals(
									TypeDefinition.MENU_OPERATION_QUERY)) {
								uv.setQuery("1");
							}
						}
					}
				}
			}
		}
		return uv;
	}

	public List<ListOnlineUsersVO> listOnlineUser(String organId,
			String logonName, String name, Integer startIndex, Integer limit) {
		List<ListOnlineUsersVO> list = userSessionDAO.listOrganOnlineUser(
				organId, logonName, name, startIndex, limit);
		return list;
	}

	@Override
	public int countOnlineUser(String organId, String logonName, String name) {
		return userSessionDAO.countOrganOnlineUser(organId, logonName, name);
	}

	@Override
	public int countUser() {
		return userDAO.getTotalCount().intValue();
	}

	public List<ListUserSessionHistoryVO> listUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String logonUserId) {
		String userOrganId = "";
		// 如果机构没有传，则使用用户登录时的机构查询
		if (StringUtils.isBlank(organId)) {
			User user = userDAO.findById(logonUserId);
			userOrganId = user.getOrgan().getId();
		}

		List<UserSessionHistory> sessions = userSessionHistoryDAO
				.listUserSessionHistory(userId, userName, organId, startTime,
						endTime, startIndex, limit, userOrganId);
		List<ListUserSessionHistoryVO> list = new LinkedList<ListUserSessionHistoryVO>();
		for (UserSessionHistory session : sessions) {
			ListUserSessionHistoryVO vo = new ListUserSessionHistoryVO();
			vo.setClientType(session.getClientType());
			vo.setId(session.getId());
			vo.setIp(session.getIp());
			vo.setLogoffTime(session.getLogoffTime().toString());
			vo.setLogonName(session.getLogonName());
			vo.setLogonTime(session.getLogonTime().toString());
			vo.setOrganId(session.getOrganId());
			vo.setOrganName(session.getOrganName());
			vo.setTicket(session.getTicket());
			vo.setUserId(session.getUserId());
			vo.setUserStandardNo(session.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	public void userLogoff(String sessionId) {
		UserSession userSession = userSessionDAO.findById(sessionId);
		// 如果是CCS掉线，设置该CCS下的摄像头全部离线
		if (userSession.getClientType().equals(TypeDefinition.CLIENT_TYPE_CCS)) {
			System.out.println("CCS[" + userSession.getStandardNumber()
					+ "] is stoped, offline all managed cameras !");
			Ccs ccs = ccsDAO.loadBySn(userSession.getStandardNumber());
			List<Camera> cameras = cameraDAO.listCameraByCcs(ccs.getId());
			// 在线设备记录
			Map<String, DeviceOnlineReal> map = deviceOnlineRealDAO
					.mapBySns(null);
			// 需要批量写入的下线设备记录
			List<DeviceOnline> offlineList = new LinkedList<DeviceOnline>();
			for (Camera camera : cameras) {
				DeviceOnlineReal entity = map.get(camera.getStandardNumber());
				if (null != entity) {
					DeviceOnline deviceOnline = new DeviceOnline();
					deviceOnline.setOfflineTime(System.currentTimeMillis());
					deviceOnline.setOnlineTime(entity.getOnlineTime());
					deviceOnline.setStandardNumber(camera.getStandardNumber());
					offlineList.add(deviceOnline);

					// 移除在线记录
					deviceOnlineRealDAO.delete(entity);
				}
			}
			// 批量插入历史表
			deviceOnlineDAO.batchInsert(offlineList);
		}
		UserSessionHistory history = new UserSessionHistory();
		history.setClientType(userSession.getClientType());
		history.setId(sessionId);
		history.setIp(userSession.getIp());
		history.setLogoffTime(System.currentTimeMillis());
		history.setLogonName(userSession.getLogonName());
		history.setLogonTime(userSession.getLogonTime());
		history.setOrganId(userSession.getOrganId());
		history.setOrganName(userSession.getOrganName());
		history.setPath(userSession.getPath());
		history.setStandardNumber(userSession.getStandardNumber());
		history.setTicket(userSession.getTicket());
		if (userSession instanceof UserSessionUser) {
			history.setUserId(((UserSessionUser) userSession).getUser().getId());
		} else if (userSession instanceof UserSessionCcs) {
			history.setUserId(((UserSessionCcs) userSession).getCcs().getId());
		} else if (userSession instanceof UserSessionCrs) {
			history.setUserId(((UserSessionCrs) userSession).getCrs().getId());
		} else if (userSession instanceof UserSessionMss) {
			history.setUserId(((UserSessionMss) userSession).getMss().getId());
		} else if (userSession instanceof UserSessionDws) {
			history.setUserId(((UserSessionDws) userSession).getDws().getId());
		} else if (userSession instanceof UserSessionPts) {
			history.setUserId(((UserSessionPts) userSession).getPts().getId());
		} else if (userSession instanceof UserSessionDas) {
			history.setUserId(((UserSessionDas) userSession).getDas().getId());
		} else if (userSession instanceof UserSessionEns) {
			history.setUserId(((UserSessionEns) userSession).getEns().getId());
		} else if (userSession instanceof UserSessionRms) {
			history.setUserId(((UserSessionRms) userSession).getRms().getId());
		} else if (userSession instanceof UserSessionRss) {
			history.setUserId(((UserSessionRss) userSession).getRss().getId());
		} else if (userSession instanceof UserSessionSrs) {
			history.setUserId(((UserSessionSrs) userSession).getSrs().getId());
		}

		history.setKickFlag(new Short((short) 0));
		userSessionHistoryDAO.save(history);
		userSessionDAO.delete(userSession);
	}

	public int selectTotalCount(String userId, String userName, String organId,
			Long startTime, Long endTime, String logonUserId) {
		String userOrganId = "";
		// 如果机构没有传，则使用用户登录时的机构查询
		if (StringUtils.isBlank(organId)) {
			User user = userDAO.findById(logonUserId);
			userOrganId = user.getOrgan().getId();
		}
		return userSessionHistoryDAO.countUserSessionHistory(userId, userName,
				organId, startTime, endTime, userOrganId);
	}

	@Override
	public String generateStandardNum(String className, String organId) {
		Organ organ = null;
		if (StringUtils.isBlank(organId)) {
			organ = organDAO.getRootOrgan();
		} else {
			organ = organDAO.findById(organId);
		}
		String prefix = null;
		if (organ.getStandardNumber().length() >= 10) {
			prefix = organ.getStandardNumber().substring(0, 10);
		} else {
			prefix = intTotenLengthString(organ.getStandardNumber());
		}
		String objectCode = StandardObjectCode.getObjectCode(className);
		int seq = userDAO.getStandardNumber(className, 1);
		return prefix + objectCode + intToSixLengthString(seq);
	}

	/**
	 * 机构sn不够10位时，右边补齐10位字符串
	 * 
	 * @param standardNumber
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午3:18:41
	 */
	private String intTotenLengthString(String standardNumber) {
		int i = 10 - standardNumber.length();
		if (i <= 0) {
			return standardNumber;
		}
		while (i > 0) {
			standardNumber = standardNumber + "0";
			i--;
		}
		return standardNumber;
	}

	/**
	 * 数字转为6位长度的字符串,不够位数时,左边补"0". 如: 15->"000015"
	 * 
	 * @param seq
	 *            要转变的数字
	 * @return 6位长度的字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:59:59
	 */
	private String intToSixLengthString(int seq) {
		String s = seq + "";
		int i = 6 - s.length();
		if (i <= 0) {
			return s;
		}
		while (i > 0) {
			s = "0" + s;
			i--;
		}
		return s;
	}

	public List<LogOperation> listLogOperation() {
		return logOperationDAO.findAll();
	}

	public Boolean isAdmin(String id) {
		List<Organ> organs = organDAO.findAll();
		String organId = "";
		for (Organ o : organs) {
			if (o.getParent() == null) {
				organId = o.getId();
			}
		}
		// 如果用户的机构ID等于根据ID返回为true,否则返回false;
		if (organId.equals(userDAO.findById(id).getOrgan().getId())) {
			return true;
		} else {
			return false;
		}
	}

	public List<SysLog> listSysLogByAdmin(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String targetName,
			String operationCode, String operationType, String resourceType) {
		return sysLogDAO.listSysLogByAdmin(resourceName, startTime, endTime,
				startIndex, limit, targetName, operationCode, operationType,
				resourceType);
	}

	public List<SysLog> listSysLog(String organId, String resourceName,
			Long startTime, Long endTime, Integer startIndex, Integer limit,
			String targetName, String operationCode, String logonUserId,
			String operationType, String resourceType, String type) {
		String[] organIds = organDAO.findOrgansByOrganId(organId);
		// if (StringUtils.isBlank(organId)) {
		// User user = userDAO.findById(logonUserId);
		// organIds = organDAO.findOrgansByOrganId(user.getOrgan().getId());
		// }
		return sysLogDAO.listSysLog(resourceName, startTime, endTime,
				startIndex, limit, organIds, targetName, operationCode,
				operationType, resourceType, type);
	}

	public Integer findTotalCount(String organId, String resourceName,
			Long startTime, Long endTime, String targetName,
			String operationCode, String operationType, String resourceType,
			String type) {
		String[] organIds = organDAO.findOrgansByOrganId(organId);
		// if (!isAdmin) {
		// if (StringUtils.isBlank(organId)) {
		// User user = userDAO.findById(BaseController.resource.get()
		// .getId());
		// organIds = organDAO
		// .findOrgansByOrganId(user.getOrgan().getId());
		// }
		// }
		return sysLogDAO.findTotalCount(organId, resourceName, startTime,
				endTime, organIds, targetName, operationCode, operationType,
				resourceType, type);
	}

	@Override
	public UserLogonVO getUserByName(String logonName, String ip) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("logonName", logonName);
		List<User> list = userDAO.findByPropertys(params);
		if (list.size() <= 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "User ["
					+ logonName + "] not found !");
		}
		UserLogonVO vo = new UserLogonVO();
		User user = list.get(0);

		vo.setId(user.getId());
		vo.setStandardNumber(user.getStandardNumber());
		vo.setName(user.getName());
		vo.setSex(user.getSex() + "");
		vo.setEmail(user.getEmail());
		vo.setPhone(user.getPhone());
		vo.setAddress(user.getAddress());
		vo.setOrganId(user.getOrgan().getId());
		vo.setImageId(user.getImageId() != null ? user.getImageId().toString()
				: "");
		vo.setPriority(user.getPriority() + "");
		// // 如果用户绑定的是GATE CCS，则返回wanIp
		// Ccs ccs = user.getCcs();
		// if (null != ccs) {
		// vo.setCcsId(ccs.getConfigValue());
		// vo.setPort(ccs.getPort() != null ? ccs.getPort() : "");
		// vo.setTelnetPort(ccs.getTelnetPort() != null ? ccs.getTelnetPort()
		// : "");
		// if (ccs.getStandardNumber().equals(
		// Configuration.getInstance()
		// .loadProperties("gateway_ccs_sn"))) {
		// vo.setIp(ccs.getWanIp() != null ? ccs.getWanIp() : "");
		// } else {
		// vo.setIp(ccs.getLanIp() != null ? ccs.getLanIp() : "");
		// }
		// } else {
		// vo.setCcsId("");
		// vo.setPort("");
		// vo.setTelnetPort("");
		// vo.setIp("");
		// }

		Ccs ccs = user.getCcs();
		if (null != ccs) {
			vo.setCcsId(ccs.getId());
			vo.setPort(ccs.getPort() != null ? ccs.getPort() : "");
			vo.setTelnetPort(ccs.getTelnetPort() != null ? ccs.getTelnetPort()
					: "");
			if (StringUtils.isBlank(ccs.getWanIp())) {
				vo.setIp(ccs.getLanIp() != null ? ccs.getLanIp() : "");
			} else {
				// 根据客户端ip地址，计算CCS的IP地址
				if (StringUtils.isNotBlank(ip)) {
					String[] cidrIPs = NetworkUtil.getAllCidrIP();
					String usedCidrIP = NetworkUtil.findMappingCidrIP(ip,
							cidrIPs);
					String[] ccsIPs = new String[] { ccs.getWanIp(),
							ccs.getLanIp() };
					String ccsIp = NetworkUtil
							.filterRangeIP(usedCidrIP, ccsIPs);
					if (null == ccsIp) {
						System.out
								.println("Could not find mapping CCS IP, try to use CCS LanIP !");
						// if (StringUtils.isBlank(ccs.getWanIp())) {
						// System.out
						// .println("CCS WanIP is null, use CCS LanIP !");
						// vo.setIp(ccs.getLanIp() != null ? ccs.getLanIp()
						// : "");
						// } else {
						// vo.setIp(ccs.getWanIp());
						// }
						vo.setIp(ccs.getLanIp() != null ? ccs.getLanIp() : "");
					} else {
						vo.setIp(ccsIp);
					}
				}
				// 如果没有获取到客户端IP地址，则默认返回CCS的LanIP
				else {
					vo.setIp(ccs.getLanIp() != null ? ccs.getLanIp() : "");
				}
			}
		} else {
			vo.setCcsId("");
			vo.setPort("");
			vo.setTelnetPort("");
			vo.setIp("");
		}

		// vo.setCcsId(user.getCcs() != null ? user.getCcs().getId() : "");
		// vo.setIp(user.getCcs() != null ? user.getCcs().getLanIp() : "");
		// vo.setPort(user.getCcs() != null ? user.getCcs().getPort() : "");
		// vo.setTelnetPort(user.getCcs() != null ?
		// user.getCcs().getTelnetPort()
		// : "");

		return vo;
	}

	@Override
	public UserLogonVO getUserUseConfig(String logonName, String ip) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("logonName", logonName);
		List<User> list = userDAO.findByPropertys(params);
		if (list.size() <= 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "User ["
					+ logonName + "] not found !");
		}
		UserLogonVO vo = new UserLogonVO();
		User user = list.get(0);

		vo.setId(user.getId());
		vo.setStandardNumber(user.getStandardNumber());
		vo.setName(user.getName());
		vo.setSex(user.getSex() + "");
		vo.setEmail(user.getEmail());
		vo.setPhone(user.getPhone());
		vo.setAddress(user.getAddress());
		vo.setOrganId(user.getOrgan().getId());
		vo.setImageId(user.getImageId() != null ? user.getImageId().toString()
				: "");
		vo.setPriority(user.getPriority() + "");

		Ccs ccs = user.getCcs();
		if (null != ccs) {
			vo.setCcsId(ccs.getId());
			vo.setPort(ccs.getPort() != null ? ccs.getPort() : "");
			vo.setTelnetPort(ccs.getTelnetPort() != null ? ccs.getTelnetPort()
					: "");
			// whichIp = lanIp or wanIp
			String whichIp = Configuration.getInstance().loadProperties(ip);
			if (StringUtils.isBlank(whichIp)) {
				System.out.println("center ip : " + ip
						+ " is not in config.properties, return ccs wan ip !");
				vo.setIp(ccs.getWanIp() != null ? ccs.getWanIp() : "");
			} else if (whichIp.equals(TypeDefinition.LAN_IP)) {
				vo.setIp(ccs.getLanIp());
			}
			// 没有配置的情况下默认返回CCS WanIp
			else {
				vo.setIp(ccs.getWanIp() != null ? ccs.getWanIp() : "");
			}
		} else {
			vo.setCcsId("");
			vo.setPort("");
			vo.setTelnetPort("");
			vo.setIp("");
			System.out.println("User[" + logonName + "] CCS is null !");
		}

		return vo;
	}

	@Override
	public void regularSessionCheck() {
		List<UserSession> list = userSessionDAO.listCMSMaintainSession();
		for (UserSession us : list) {
			Long updateTime = us.getUpdateTime();
			// 如果缓存中的时间大于数据库中记录的时间，更新记录时间
			Long cacheTime = (Long) CacheUtil.getCache(us.getId(), REGION);
			if (null != cacheTime) {
				if (updateTime.longValue() < cacheTime.longValue()) {
					updateTime = cacheTime;
				}
			}

			// 过期移除session
			if ((updateTime + SESSION_EXPIRE_TIME) < System.currentTimeMillis()) {
				// 如果是CCS掉线，设置该CCS下的摄像头全部离线
				if (us.getClientType().equals(TypeDefinition.CLIENT_TYPE_CCS)) {
					System.out.println("CCS[" + us.getStandardNumber()
							+ "] is offline, offline all managed cameras !");
					Ccs ccs = ccsDAO.loadBySn(us.getStandardNumber());
					List<Camera> cameras = cameraDAO.listCameraByCcs(ccs
							.getId());
					// 在线设备记录
					Map<String, DeviceOnlineReal> map = deviceOnlineRealDAO
							.mapBySns(null);
					// 需要批量写入的下线设备记录
					List<DeviceOnline> offlineList = new LinkedList<DeviceOnline>();
					for (Camera camera : cameras) {
						DeviceOnlineReal entity = map.get(camera
								.getStandardNumber());
						if (null != entity) {
							DeviceOnline deviceOnline = new DeviceOnline();
							deviceOnline.setOfflineTime(System
									.currentTimeMillis());
							deviceOnline.setOnlineTime(entity.getOnlineTime());
							deviceOnline.setStandardNumber(camera
									.getStandardNumber());
							offlineList.add(deviceOnline);

							// 移除在线记录
							deviceOnlineRealDAO.delete(entity);
						}
					}
					// 批量插入历史表
					deviceOnlineDAO.batchInsert(offlineList);
				}

				// 保存到历史会话中
				UserSessionHistory record = new UserSessionHistory();
				record.setClientType(us.getClientType());
				record.setId(us.getId());
				record.setIp(us.getIp());
				record.setLogoffTime(System.currentTimeMillis());
				record.setLogonName(us.getLogonName());
				record.setLogonTime(us.getLogonTime());
				record.setOrganId(us.getOrganId());
				record.setOrganName(us.getOrganName());
				record.setPath(us.getPath());
				record.setStandardNumber(us.getStandardNumber());
				record.setTicket(us.getTicket());
				if (us instanceof UserSessionUser) {
					record.setUserId(((UserSessionUser) us).getUser().getId());
				} else if (us instanceof UserSessionCcs) {
					record.setUserId(((UserSessionCcs) us).getCcs().getId());
				} else if (us instanceof UserSessionCrs) {
					record.setUserId(((UserSessionCrs) us).getCrs().getId());
				} else if (us instanceof UserSessionDws) {
					record.setUserId(((UserSessionDws) us).getDws().getId());
				} else if (us instanceof UserSessionMss) {
					record.setUserId(((UserSessionMss) us).getMss().getId());
				} else if (us instanceof UserSessionPts) {
					record.setUserId(((UserSessionPts) us).getPts().getId());
				} else if (us instanceof UserSessionDas) {
					record.setUserId(((UserSessionDas) us).getDas().getId());
				} else if (us instanceof UserSessionEns) {
					record.setUserId(((UserSessionEns) us).getEns().getId());
				} else if (us instanceof UserSessionRms) {
					record.setUserId(((UserSessionRms) us).getRms().getId());
				} else if (us instanceof UserSessionRss) {
					record.setUserId(((UserSessionRss) us).getRss().getId());
				} else if (us instanceof UserSessionSrs) {
					record.setUserId(((UserSessionSrs) us).getSrs().getId());
				}
				record.setKickFlag(new Short((short) 0));
				userSessionHistoryDAO.save(record);
				// 移除session
				userSessionDAO.delete(us);
			}
			// 更新在缓存中的用户会话时间
			else {
				// 只更新缓存中存在的
				if (us.getUpdateTime() != updateTime) {
					us.setUpdateTime(updateTime);
				}
			}
		}
	}

	@Override
	public Integer userTotalCount(String organId, String name, String logonName) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		return userDAO.userTotalCount(organs, name, logonName);
	}

	@Override
	public String[] batchGenerateSN(String className, String organId, int size) {
		Organ organ = null;
		if (StringUtils.isBlank(organId)) {
			organ = organDAO.getRootOrgan();
		} else {
			organ = organDAO.findById(organId);
		}
		String prefix = null;
		if (organ.getStandardNumber().length() >= 10) {
			prefix = organ.getStandardNumber().substring(0, 10);
		} else {
			prefix = intTotenLengthString(organ.getStandardNumber());
		}
		String objectCode = StandardObjectCode.getObjectCode(className);
		int seq = userDAO.getStandardNumber(className, size);
		String[] rtn = new String[size];
		for (int i = 0; i < size; i++) {
			rtn[i] = prefix + objectCode + intToSixLengthString(seq + i);
		}
		return rtn;
	}

	@Override
	public List<ListOnlineUsersVO> listOnlineUserByUserId(String userId,
			Integer startIndex, Integer limit) {
		List<UserSessionUser> userSessions = userSessionDAO
				.findUserSessionByUserId(userId, startIndex, limit);
		User user = userDAO.findById(userId);
		List<ListOnlineUsersVO> listVO = new ArrayList<ListOnlineUsersVO>();
		for (UserSessionUser userSession : userSessions) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setClientType(userSession.getClientType());
			vo.setId(userSession.getId());
			vo.setIp(userSession.getIp());
			vo.setLogonName(userSession.getLogonName());
			vo.setLogonTime(userSession.getLogonTime() + "");
			vo.setStandardNumber(userSession.getStandardNumber());
			vo.setTicket(userSession.getTicket());
			vo.setUpdateTime(userSession.getUpdateTime() + "");
			vo.setUserId(userSession.getUser().getId());
			vo.setName(user.getName());
			listVO.add(vo);
		}
		return listVO;
	}

	@Override
	public int countOnlineUserByUserId(String userId) {
		Integer totalCount = userSessionDAO.userSessionTotalCount(userId);
		return totalCount.intValue();
	}

	@Override
	public String createUserSession(String id, String name,
			String standardNumber, String lanIp, String type) {
		Organ organ = organDAO.getRootOrgan();

		String sessionId = userSessionDAO.getNextId("UserSession", 1);
		UserSessionUser userSession = new UserSessionUser();
		userSession.setId(sessionId);
		userSession.setUser(userDAO.findById(id));
		userSession.setIp(lanIp);
		userSession.setTicket(sessionId);
		userSession.setLogonName(name);
		userSession.setOrganId(organ.getId());
		userSession.setOrganName(organ.getName());
		userSession.setPath(organ.getPath());
		userSession.setStandardNumber(standardNumber);
		userSession.setClientType(type);
		userSession.setLogonTime(System.currentTimeMillis());
		userSession.setUpdateTime(System.currentTimeMillis());
		userSessionDAO.save(userSession);
		return sessionId;
	}

	@Override
	public void heartbeat(String sessionId) throws BusinessException {
		UserSession record = null;
		try {
			record = userSessionDAO.findById(sessionId);
		} catch (BusinessException e) {
			e.printStackTrace();
			UserSessionHistory ush = userSessionHistoryDAO.findById(sessionId);
			Short flag = ush.getKickFlag();
			if (null != flag && flag.intValue() == 1) {
				throw new BusinessException(ErrorCode.USER_SESSION_KICK_OFF,
						"session[" + sessionId
								+ "] has been kicked off by administrator !");
			} else {
				throw new BusinessException(ErrorCode.USER_SESSION_EXPIRED,
						"session[" + sessionId + "] is expired !");
			}
		}
		if (record.getUpdateTime() < System.currentTimeMillis()) {
			record.setUpdateTime(System.currentTimeMillis());
		}
	}

	@Override
	@LogMethod(targetType = "User", operationType = "update", name = "修改用户个人信息", code = "1022")
	public void updateUserInfo(@LogParam("id") String id, String password,
			@LogParam("name") String name, Short sex, String email,
			String phone, String address) throws BusinessException {
		User user = userDAO.findById(id);
		if (null != password) {
			user.setPassword(password);
		}
		if (null != name) {
			user.setName(name);
		}
		if (null != sex) {
			user.setSex(sex);
		}
		if (null != email) {
			user.setEmail(email);
		}
		if (null != phone) {
			user.setPhone(phone);
		}
		if (null != address) {
			user.setAddress(address);
		}

	}

	@Override
	public void forceLogoff(String userSessionId) throws BusinessException {
		UserSession userSession = userSessionDAO.findById(userSessionId);
		if (!TypeDefinition.CLIENT_TYPE_CS.equals(userSession.getClientType())) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"UserSession[" + userSessionId
							+ "] is not 'Cs' clientType !");
		}

		UserSessionHistory history = new UserSessionHistory();
		history.setClientType(userSession.getClientType());
		history.setId(userSessionId);
		history.setIp(userSession.getIp());
		history.setLogoffTime(System.currentTimeMillis());
		history.setLogonName(userSession.getLogonName());
		history.setLogonTime(userSession.getLogonTime());
		history.setOrganId(userSession.getOrganId());
		history.setOrganName(userSession.getOrganName());
		history.setPath(userSession.getPath());
		history.setStandardNumber(userSession.getStandardNumber());
		history.setTicket(userSession.getTicket());
		if (userSession instanceof UserSessionUser) {
			history.setUserId(((UserSessionUser) userSession).getUser().getId());
		} else if (userSession instanceof UserSessionCcs) {
			history.setUserId(((UserSessionCcs) userSession).getCcs().getId());
		} else if (userSession instanceof UserSessionCrs) {
			history.setUserId(((UserSessionCrs) userSession).getCrs().getId());
		} else if (userSession instanceof UserSessionMss) {
			history.setUserId(((UserSessionMss) userSession).getMss().getId());
		} else if (userSession instanceof UserSessionDws) {
			history.setUserId(((UserSessionDws) userSession).getDws().getId());
		} else if (userSession instanceof UserSessionPts) {
			history.setUserId(((UserSessionPts) userSession).getPts().getId());
		} else if (userSession instanceof UserSessionDas) {
			history.setUserId(((UserSessionDas) userSession).getDas().getId());
		} else if (userSession instanceof UserSessionEns) {
			history.setUserId(((UserSessionEns) userSession).getEns().getId());
		} else if (userSession instanceof UserSessionRms) {
			history.setUserId(((UserSessionRms) userSession).getRms().getId());
		}
		history.setKickFlag(new Short((short) 1));
		userSessionHistoryDAO.save(history);
		userSessionDAO.delete(userSession);
	}

	@Override
	public int countClientSession() throws BusinessException {
		return userSessionDAO.getCSTotalCount();
	}

	@Override
	public String mcuLogin(String userName, String passwd, String ip) {
		String getUserByUserName = SqlFactory.getInstance().getUserByUserName();
		List<User> user = userDAO.findByHql(getUserByUserName, userName);
		if (user.size() <= 0) {
			throw new BusinessException(ErrorCode.USER_NOT_FOUND,
					"logon_name [" + userName + "] not found !");
		} else {
			if (!user.get(0).getLogonName().equals(userName)) {
				throw new BusinessException(ErrorCode.USER_NOT_FOUND,
						"logon_name [" + userName + "] not found !");
			}
		}
		if (!passwd.equals(user.get(0).getPassword())) {
			throw new BusinessException(ErrorCode.PASSWORD_ERROR,
					"password error !");
		}
		if (user.get(0).getStatus().intValue() != TypeDefinition.STATUS_AVAILABLE) {
			throw new BusinessException(ErrorCode.USER_STATUS_INVALID,
					"User status is not available !");
		}
		// 判断最大登陆数是否达到
		Integer count = userSessionDAO.userSessionTotalCount(user.get(0)
				.getId());
		Integer maxConnect = user.get(0).getMaxConnect();
		if (count >= maxConnect) {
			throw new BusinessException(ErrorCode.MAX_CONNECT_LIMIT,
					"Max number[" + maxConnect.toString()
							+ "] of user login restrictions !");
		}

		// 用户名称密码匹配对后，生成SESSION
		// String sessionId = userSessionDAO.getNextId("UserSession", 1);
		UserSessionUser userSession = new UserSessionUser();
		// userSession.setId(sessionId);
		userSession.setUser(user.get(0));
		userSession.setIp(ip);
		userSession.setTicket("");
		userSession.setLogonName(userName);
		userSession.setOrganId(user.get(0).getOrgan().getId());
		userSession.setOrganName(user.get(0).getOrgan().getName());
		userSession.setPath(user.get(0).getOrgan().getPath());
		userSession.setStandardNumber(user.get(0).getStandardNumber());
		userSession.setClientType(TypeDefinition.CLIENT_TYPE_MCU);
		userSession.setLogonTime(System.currentTimeMillis());
		userSession.setUpdateTime(System.currentTimeMillis() + 3600 * 1000);
		userSessionDAO.save(userSession);
		userSessionDAO.findById(userSession.getId()).setTicket(
				userSession.getId());
		return userSession.getId();
	}

	@Override
	public GisLogonVO getGisServer() {
		List<Gis> giss = gisDAO.findAll();
		GisLogonVO vo = new GisLogonVO();
		if (giss.size() > 0) {
			vo.setIp(giss.get(0).getWanIp());
			vo.setPort(giss.get(0).getPort());
			vo.setWmsUrl(giss.get(0).getConfigValue());
		} else {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"GisServer invalid !");
		}
		return vo;
	}

	public static void main(String args[]) {
		String s = "1234567890";
		if (s.length() >= 10) {
			System.out.println(s.substring(0, 10));
		}
	}

	@Override
	public Workbook exportExcelLog(List<SysLog> listLog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserBySn(String sn) {
		return userDAO.findBySN(sn);
	}

	@Override
	public void addFavoriteDevice(@LogParam("id") String favoriteId,
			String cameraId) {
		DeviceFavorite df = deviceFavoriteDAO.get(favoriteId, cameraId);
		if (null == df) {
			df = new DeviceFavorite();
			df.setFavoriteId(favoriteId);
			df.setDeviceId(cameraId);
			deviceFavoriteDAO.save(df);
		}
	}

	@Override
	public void deleteFavoriteDevice(@LogParam("id") String favoriteId,
			String cameraId) {
		DeviceFavorite df = deviceFavoriteDAO.get(favoriteId, cameraId);
		if (null != df) {
			deviceFavoriteDAO.delete(df);
		}
	}

	@Override
	public List<UserSessionVO> listOnlineUser() {
		List<UserSession> userSessions = userSessionDAO.listUserSession();
		List<UserSessionVO> list = new ArrayList<UserSessionVO>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		for (UserSession userSession : userSessions) {
			UserSessionVO vo = new UserSessionVO();
			vo.setOrganName(StringUtils.isNotBlank(userSession.getOrganName()) ? Base64Utils
					.getBASE64(userSession.getOrganName().getBytes()) : "");
			params.put("standardNumber", userSession.getStandardNumber());
			vo.setCcsStandardNumber(userDAO.findByPropertys(params).get(0)
					.getCcs().getStandardNumber());
			params.clear();
			vo.setClientType(userSession.getClientType());
			vo.setId(userSession.getId());
			vo.setIp(userSession.getIp());
			vo.setLogonName(StringUtils.isNotBlank(userSession.getLogonName()) ? Base64Utils
					.getBASE64(userSession.getLogonName().getBytes()) : "");
			vo.setLogonTime(userSession.getLogonTime() + "");
			vo.setOrganId(userSession.getOrganId());
			vo.setPath(userSession.getPath());
			vo.setStandardNumber(userSession.getStandardNumber());
			vo.setTicket(userSession.getTicket());
			vo.setUpdateTime(userSession.getUpdateTime() + "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public AndroidUpdate getAndroidConfig() {
		AndroidUpdate android = new AndroidUpdate();
		String apkVersion = Configuration.getInstance().loadProperties(
				"android_version");
		String downloadAddress = Configuration.getInstance().loadProperties(
				"android_download_address");
		android.setApkVersion(apkVersion != null ? apkVersion : "");
		android.setDownloadAddress(downloadAddress != null ? downloadAddress
				: "");
		return android;
	}

	@Override
	public Integer findTotalCount(String operationType, Long beginTime,
			Long endTime) {
		return userOperationLogDAO.findTotalCount(operationType, beginTime,
				endTime);
	}

	@Override
	public Element listUserOperationLog(String operationType, Long beginTime,
			Long endTime, Integer startIndex, Integer limit) {
		List<UserOperationLog> userOperationLogs = userOperationLogDAO
				.listUserOperationLog(operationType, beginTime, endTime,
						startIndex, limit);
		Element logs = new Element("Logs");
		for (UserOperationLog entity : userOperationLogs) {
			Element log = new Element("Log");
			log.setAttribute(
					"ClassesName",
					StringUtils.isNotBlank(entity.getClassesName()) ? entity
							.getClassesName() + "" : entity.getUserName() + "");
			log.setAttribute("CreateTime", entity.getCreateTime().toString());
			log.setAttribute("Id", entity.getId());
			if (entity.getSoundRecordLog() != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(entity.getSoundRecordLog().getHostNumber() + "话机,");
				sb.append("通话时长:");
				if (null != entity.getSoundRecordLog().getDuration()) {
					sb.append(MyStringUtil.millisTohour(entity
							.getSoundRecordLog().getDuration()));
				}
				log.setAttribute("OperationName", sb.toString());
			} else {
				log.setAttribute(
						"OperationName",
						entity.getOperationName() != null ? entity
								.getOperationName() : "");
			}
			if (entity.getSoundRecordLog() != null) {
				log.setAttribute("TargetId",
						entity.getSoundRecordLog().getId() != null ? entity
								.getSoundRecordLog().getId() : "");
			} else {
				log.setAttribute("TargetId", "");
			}
			log.setAttribute(
					"OperationType",
					entity.getOperationType() != null ? entity
							.getOperationType() : "");
			logs.addContent(log);
		}
		return logs;
	}

	@Override
	public void saveUserOperationLog(String resourceId, String operationName,
			String operationTypeModel) {
		// 从缓存中取出当前值班人员
		Classes classes = CacheData.getInstance().classes.get("classes");
		// 如果重启中心后会清除缓存,则需要去查询一次数据库确认是否没有当前班次信息
		if (null == classes) {
			classes = classesDAO.getClasses();
			if (classes != null) {
				// 如果查询最后一条数据有结束时间，就是已交班，存入日志时把班次设置为null
				if (classes.getEndTime() != null) {
					classes = null;
				}
				// 如果查询最后一条数据没有结束时间,就是未交班，存入缓存
				else {
					CacheData.getInstance().classes.clear();
					CacheData.getInstance().classes.put("classes", classes);
				}
			}
		}
		User user = userDAO.findById(resourceId);
		// 录音类型这里不予考虑，在创建完成录音后自动保存操作日志
		UserOperationLog log = new UserOperationLog();
		log.setCreateTime(System.currentTimeMillis());
		log.setOperationName(operationName);
		log.setClassesId(classes != null ? classes.getId() : "");
		log.setClassesName(classes != null ? classes.getUserName() : "");
		log.setOperationType(operationTypeModel);
		log.setUserId(user.getId());
		log.setUserName(user.getLogonName());

		userOperationLogDAO.save(log);
	}
}
