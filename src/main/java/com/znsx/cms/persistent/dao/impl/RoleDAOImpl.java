package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.RoleDAO;
import com.znsx.cms.persistent.model.BoxTransformer;
import com.znsx.cms.persistent.model.BridgeDetector;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceIs;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceRail;
import com.znsx.cms.persistent.model.ControlDeviceRd;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.DisplayWall;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.RoadDetector;
import com.znsx.cms.persistent.model.Role;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionBT;
import com.znsx.cms.persistent.model.RoleResourcePermissionBridgeD;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.persistent.model.RoleResourcePermissionCms;
import com.znsx.cms.persistent.model.RoleResourcePermissionCovi;
import com.znsx.cms.persistent.model.RoleResourcePermissionFan;
import com.znsx.cms.persistent.model.RoleResourcePermissionFd;
import com.znsx.cms.persistent.model.RoleResourcePermissionIs;
import com.znsx.cms.persistent.model.RoleResourcePermissionLight;
import com.znsx.cms.persistent.model.RoleResourcePermissionLoli;
import com.znsx.cms.persistent.model.RoleResourcePermissionNod;
import com.znsx.cms.persistent.model.RoleResourcePermissionPb;
import com.znsx.cms.persistent.model.RoleResourcePermissionRail;
import com.znsx.cms.persistent.model.RoleResourcePermissionRd;
import com.znsx.cms.persistent.model.RoleResourcePermissionRoadD;
import com.znsx.cms.persistent.model.RoleResourcePermissionSubResource;
import com.znsx.cms.persistent.model.RoleResourcePermissionUP;
import com.znsx.cms.persistent.model.RoleResourcePermissionVd;
import com.znsx.cms.persistent.model.RoleResourcePermissionViD;
import com.znsx.cms.persistent.model.RoleResourcePermissionWall;
import com.znsx.cms.persistent.model.RoleResourcePermissionWp;
import com.znsx.cms.persistent.model.RoleResourcePermissionWs;
import com.znsx.cms.persistent.model.RoleResourcePermissionWst;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.persistent.model.UrgentPhone;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.ViDetector;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * 用户角色数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午11:35:55
 */
public class RoleDAOImpl extends BaseDAOImpl<Role, String> implements RoleDAO {
	@Override
	public List<Role> listRole(String[] organIds, Integer startIndex,
			Integer limit) throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Role.class);
		String[] types = new String[] { TypeDefinition.ROLE_TYPE_ADMIN,
				TypeDefinition.ROLE_TYPE_JUNIOR,
				TypeDefinition.ROLE_TYPE_SENIOR };
		if (organIds.length > 0) {
			criteria.add(Restrictions.or(Restrictions.in("organ.id", organIds),
					Restrictions.in("type", types)));
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Qurey parameter organIds can not be empty !");
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer roleTotalCount(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Role.class);
		String[] types = new String[] { TypeDefinition.ROLE_TYPE_ADMIN,
				TypeDefinition.ROLE_TYPE_JUNIOR,
				TypeDefinition.ROLE_TYPE_SENIOR };
		if (organIds.length > 0) {
			criteria.add(Restrictions.or(Restrictions.in("organ.id", organIds),
					Restrictions.in("type", types)));
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Qurey parameter organIds can not be empty !");
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public Integer countOrganRole(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Role.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<DevicePermissionVO> listOrganCameraWithPermission(
			String organId, String roleId, String name, int start, int limit)
			throws BusinessException {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(Camera.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		List<Camera> cameras = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] cameraIds = new String[cameras.size()];
		int index = 0;
		for (Camera camera : cameras) {
			cameraIds[index++] = camera.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionCamera.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (cameraIds.length > 0) {
			criteriaR.add(Restrictions.in("camera.id", cameraIds));
		}
		List<RoleResourcePermissionCamera> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		for (Camera camera : cameras) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(camera.getId());
			vo.setName(camera.getName());
			vo.setPrivilege(getPrivilegeCamera(rList, camera.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	/**
	 * 获取角色设备关联表中指定摄像头ID的权限
	 * 
	 * @param rList
	 *            角色设备关联列表
	 * @param cameraId
	 *            摄像头ID
	 * @return 角色拥有的该设备权限
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-7 上午9:52:53
	 */
	private String getPrivilegeCamera(List<RoleResourcePermissionCamera> rList,
			String cameraId) {
		for (RoleResourcePermissionCamera r : rList) {
			if (r.getCamera().getId().equals(cameraId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganCameraWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public Role getPlatformRole() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.eq("type", TypeDefinition.ROLE_TYPE_PLATFORM));
		return (Role) criteria.uniqueResult();
	}

	@Override
	public int countOrganVDWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("vd.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganVDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<VehicleDetector> vds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] vdIds = new String[vds.size()];
		int index = 0;
		for (VehicleDetector vd : vds) {
			vdIds[index++] = vd.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionVd.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (vdIds.length > 0) {
			criteriaR.add(Restrictions.in("vd.id", vdIds));
		}
		List<RoleResourcePermissionVd> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_VD + "";
		for (VehicleDetector vd : vds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(vd.getId());
			vo.setName(vd.getName());
			vo.setPrivilege(getPrivilegeVd(rList, vd.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeVd(List<RoleResourcePermissionVd> rList,
			String vdId) {
		for (RoleResourcePermissionVd r : rList) {
			if (r.getVd().getId().equals(vdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganWSWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("ws.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganWSWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(WindSpeed.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<WindSpeed> wds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] wdIds = new String[wds.size()];
		int index = 0;
		for (WindSpeed ws : wds) {
			wdIds[index++] = ws.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionWs.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (wdIds.length > 0) {
			criteriaR.add(Restrictions.in("ws.id", wdIds));
		}
		List<RoleResourcePermissionWs> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_WS + "";
		for (WindSpeed ws : wds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(ws.getId());
			vo.setName(ws.getName());
			vo.setPrivilege(getPrivilegeWs(rList, ws.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeWs(List<RoleResourcePermissionWs> rList,
			String wsId) {
		for (RoleResourcePermissionWs r : rList) {
			if (r.getWs().getId().equals(wsId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganWSTWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("wst.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganWSTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(WeatherStat.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<WeatherStat> wsts = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] wstIds = new String[wsts.size()];
		int index = 0;
		for (WeatherStat wst : wsts) {
			wstIds[index++] = wst.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionWst.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (wstIds.length > 0) {
			criteriaR.add(Restrictions.in("wst.id", wstIds));
		}
		List<RoleResourcePermissionWst> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_WST + "";
		for (WeatherStat wst : wsts) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(wst.getId());
			vo.setName(wst.getName());
			vo.setPrivilege(getPrivilegeWst(rList, wst.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeWst(List<RoleResourcePermissionWst> rList,
			String wstId) {
		for (RoleResourcePermissionWst r : rList) {
			if (r.getWst().getId().equals(wstId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganLoLiWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(LoLi.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("loli.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganLoLiWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(LoLi.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<LoLi> lolis = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] loliIds = new String[lolis.size()];
		int index = 0;
		for (LoLi loli : lolis) {
			loliIds[index++] = loli.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionLoli.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (loliIds.length > 0) {
			criteriaR.add(Restrictions.in("loli.id", loliIds));
		}
		List<RoleResourcePermissionLoli> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_LOLI + "";
		for (LoLi loli : lolis) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(loli.getId());
			vo.setName(loli.getName());
			vo.setPrivilege(getPrivilegeLoli(rList, loli.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeLoli(List<RoleResourcePermissionLoli> rList,
			String loliId) {
		for (RoleResourcePermissionLoli r : rList) {
			if (r.getLoli().getId().equals(loliId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganFDWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("fd.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganFDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(FireDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<FireDetector> fds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] fdIds = new String[fds.size()];
		int index = 0;
		for (FireDetector fd : fds) {
			fdIds[index++] = fd.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionFd.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (fdIds.length > 0) {
			criteriaR.add(Restrictions.in("fd.id", fdIds));
		}
		List<RoleResourcePermissionFd> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_FD + "";
		for (FireDetector fd : fds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(fd.getId());
			vo.setName(fd.getName());
			vo.setPrivilege(getPrivilegeFd(rList, fd.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeFd(List<RoleResourcePermissionFd> rList,
			String fdId) {
		for (RoleResourcePermissionFd r : rList) {
			if (r.getFd().getId().equals(fdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganCoviWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Covi.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("covi.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganCoviWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(Covi.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<Covi> covis = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] coviIds = new String[covis.size()];
		int index = 0;
		for (Covi covi : covis) {
			coviIds[index++] = covi.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionCovi.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (coviIds.length > 0) {
			criteriaR.add(Restrictions.in("covi.id", coviIds));
		}
		List<RoleResourcePermissionCovi> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_COVI + "";
		for (Covi covi : covis) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(covi.getId());
			vo.setName(covi.getName());
			vo.setPrivilege(getPrivilegeCovi(rList, covi.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeCovi(List<RoleResourcePermissionCovi> rList,
			String coviId) {
		for (RoleResourcePermissionCovi r : rList) {
			if (r.getCovi().getId().equals(coviId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganNODWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("nod.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganNODWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(NoDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<NoDetector> nods = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] nodIds = new String[nods.size()];
		int index = 0;
		for (NoDetector nod : nods) {
			nodIds[index++] = nod.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionNod.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (nodIds.length > 0) {
			criteriaR.add(Restrictions.in("nod.id", nodIds));
		}
		List<RoleResourcePermissionNod> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_NOD + "";
		for (NoDetector nod : nods) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(nod.getId());
			vo.setName(nod.getName());
			vo.setPrivilege(getPrivilegeNod(rList, nod.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeNod(List<RoleResourcePermissionNod> rList,
			String nodId) {
		for (RoleResourcePermissionNod r : rList) {
			if (r.getNod().getId().equals(nodId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganPBWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("pb.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganPBWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<PushButton> pbs = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] pbIds = new String[pbs.size()];
		int index = 0;
		for (PushButton pb : pbs) {
			pbIds[index++] = pb.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionPb.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (pbIds.length > 0) {
			criteriaR.add(Restrictions.in("pb.id", pbIds));
		}
		List<RoleResourcePermissionPb> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_PB + "";
		for (PushButton pb : pbs) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(pb.getId());
			vo.setName(pb.getName());
			vo.setPrivilege(getPrivilegePb(rList, pb.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegePb(List<RoleResourcePermissionPb> rList,
			String pbId) {
		for (RoleResourcePermissionPb r : rList) {
			if (r.getPb().getId().equals(pbId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganControlDeviceWithPermission(String organId,
			String roleId, String name, String type) {
		Session session = getSession();
		Criteria criteria = null;
		// 分页查询设备
		if (type.equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
			criteria = session.createCriteria(ControlDeviceCms.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_FAN + "")) {
			criteria = session.createCriteria(ControlDeviceFan.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
			criteria = session.createCriteria(ControlDeviceLight.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RD + "")) {
			criteria = session.createCriteria(ControlDeviceRd.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_WP + "")) {
			criteria = session.createCriteria(ControlDeviceWp.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")) {
			criteria = session.createCriteria(ControlDeviceRail.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_IS + "")) {
			criteria = session.createCriteria(ControlDeviceIs.class);
		}
		criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("cd.name", "%" + name + "%"));
		}
		// criteria.add(Restrictions.eq("type", Short.parseShort(type)));
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganControlDeviceWithPermission(
			String organId, String roleId, String name, String type,
			int startIndex, int limit) {
		Session session = getSession();
		Criteria criteria = null;
		// 分页查询设备
		if (type.equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
			criteria = session.createCriteria(ControlDeviceCms.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_FAN + "")) {
			criteria = session.createCriteria(ControlDeviceFan.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
			criteria = session.createCriteria(ControlDeviceLight.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RD + "")) {
			criteria = session.createCriteria(ControlDeviceRd.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_WP + "")) {
			criteria = session.createCriteria(ControlDeviceWp.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")) {
			criteria = session.createCriteria(ControlDeviceRail.class);
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_IS + "")) {
			criteria = session.createCriteria(ControlDeviceIs.class);
		}
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<ControlDevice> cds = criteria.list();
		// 根据设备查询结果，再去查询关联表

		String[] cdIds = new String[cds.size()];
		int index = 0;
		for (ControlDevice cd : cds) {
			cdIds[index++] = cd.getId();
		}
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		if (type.equals(TypeDefinition.DEVICE_TYPE_CMS + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionCms.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (null != cdIds) {
				criteriaR.add(Restrictions.in("cms.id", cdIds));
			}
			List<RoleResourcePermissionCms> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeCms(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_FAN + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionFan.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("fan.id", cdIds));
			}
			List<RoleResourcePermissionFan> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeFan(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_LIGHT + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionLight.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("light.id", cdIds));
			}
			List<RoleResourcePermissionLight> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeLight(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RD + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionRd.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("rd.id", cdIds));
			}
			List<RoleResourcePermissionRd> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeRd(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_WP + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionWp.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("wp.id", cdIds));
			}
			List<RoleResourcePermissionWp> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeWp(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_RAIL + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionRail.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("rail.id", cdIds));
			}
			List<RoleResourcePermissionRail> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeRail(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_IS + "")) {
			Criteria criteriaR = session
					.createCriteria(RoleResourcePermissionIs.class);
			criteriaR.add(Restrictions.eq("role.id", roleId));
			if (cdIds.length > 0) {
				criteriaR.add(Restrictions.in("is.id", cdIds));
			}
			List<RoleResourcePermissionIs> rList = criteriaR.list();
			// 构建返回结果
			for (ControlDevice cd : cds) {
				DevicePermissionVO vo = new DevicePermissionVO();
				vo.setId(cd.getId());
				vo.setName(cd.getName());
				vo.setPrivilege(getPrivilegeIs(rList, cd.getId()));
				vo.setType(type);
				rtn.add(vo);
			}
		}
		return rtn;
	}

	private String getPrivilegeCms(List<RoleResourcePermissionCms> rList,
			String cdId) {
		for (RoleResourcePermissionCms r : rList) {
			if (r.getCms().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeFan(List<RoleResourcePermissionFan> rList,
			String cdId) {
		for (RoleResourcePermissionFan r : rList) {
			if (r.getFan().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeLight(List<RoleResourcePermissionLight> rList,
			String cdId) {
		for (RoleResourcePermissionLight r : rList) {
			if (r.getLight().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeRd(List<RoleResourcePermissionRd> rList,
			String cdId) {
		for (RoleResourcePermissionRd r : rList) {
			if (r.getRd().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeWp(List<RoleResourcePermissionWp> rList,
			String cdId) {
		for (RoleResourcePermissionWp r : rList) {
			if (r.getWp().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeRail(List<RoleResourcePermissionRail> rList,
			String cdId) {
		for (RoleResourcePermissionRail r : rList) {
			if (r.getRail().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	private String getPrivilegeIs(List<RoleResourcePermissionIs> rList,
			String cdId) {
		for (RoleResourcePermissionIs r : rList) {
			if (r.getIs().getId().equals(cdId)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public List<RoleResourcePermission> listAllResources(String roleId,
			int startIndex, int limit) {
		Session session = getSession();
		Criteria criteria = session
				.createCriteria(RoleResourcePermission.class);

		criteria.add(Restrictions.eq("role.id", roleId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countOrganBTWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BoxTransformer.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganBTWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(BoxTransformer.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<BoxTransformer> bts = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] btIds = new String[bts.size()];
		int index = 0;
		for (BoxTransformer bt : bts) {
			btIds[index++] = bt.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionBT.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (btIds.length > 0) {
			criteriaR.add(Restrictions.in("boxTransformer.id", btIds));
		}
		List<RoleResourcePermissionBT> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_BT + "";
		for (BoxTransformer bt : bts) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(bt.getId());
			vo.setName(bt.getName());
			vo.setPrivilege(getPrivilegeBt(rList, bt.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeBt(List<RoleResourcePermissionBT> rList,
			String id) {
		for (RoleResourcePermissionBT r : rList) {
			if (r.getBoxTransformer().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganViDWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganViDWithPermission(String organId,
			String roleId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(ViDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<ViDetector> vds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] vdIds = new String[vds.size()];
		int index = 0;
		for (ViDetector vd : vds) {
			vdIds[index++] = vd.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionViD.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (vdIds.length > 0) {
			criteriaR.add(Restrictions.in("viDetector.id", vdIds));
		}
		List<RoleResourcePermissionViD> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "";
		for (ViDetector vd : vds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(vd.getId());
			vo.setName(vd.getName());
			vo.setPrivilege(getPrivilegeViD(rList, vd.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeViD(List<RoleResourcePermissionViD> rList,
			String id) {
		for (RoleResourcePermissionViD r : rList) {
			if (r.getViDetector().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganRoadDWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RoadDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganRoadDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(RoadDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<RoadDetector> rds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] rdIds = new String[rds.size()];
		int index = 0;
		for (RoadDetector rd : rds) {
			rdIds[index++] = rd.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionRoadD.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (rdIds.length > 0) {
			criteriaR.add(Restrictions.in("roadDetector.id", rdIds));
		}
		List<RoleResourcePermissionRoadD> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "";
		for (RoadDetector rd : rds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(rd.getId());
			vo.setName(rd.getName());
			vo.setPrivilege(getPrivilegeRoadD(rList, rd.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeRoadD(List<RoleResourcePermissionRoadD> rList,
			String id) {
		for (RoleResourcePermissionRoadD r : rList) {
			if (r.getRoadDetector().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganBridgeDWithPermission(String organId, String roleId,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(BridgeDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganBridgeDWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(BridgeDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<BridgeDetector> bds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] bdIds = new String[bds.size()];
		int index = 0;
		for (BridgeDetector bd : bds) {
			bdIds[index++] = bd.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionBridgeD.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (bdIds.length > 0) {
			criteriaR.add(Restrictions.in("bridgeDetector.id", bdIds));
		}
		List<RoleResourcePermissionBridgeD> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "";
		for (BridgeDetector bd : bds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(bd.getId());
			vo.setName(bd.getName());
			vo.setPrivilege(getPrivilegeBridgeD(rList, bd.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeBridgeD(
			List<RoleResourcePermissionBridgeD> rList, String id) {
		for (RoleResourcePermissionBridgeD r : rList) {
			if (r.getBridgeDetector().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganUrgentPhoneWithPermission(String organId,
			String roleId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UrgentPhone.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganUrgentPhoneWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(UrgentPhone.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<UrgentPhone> ups = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] upIds = new String[ups.size()];
		int index = 0;
		for (UrgentPhone up : ups) {
			upIds[index++] = up.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionUP.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (upIds.length > 0) {
			criteriaR.add(Restrictions.in("urgentPhone.id", upIds));
		}
		List<RoleResourcePermissionUP> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "";
		for (UrgentPhone up : ups) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(up.getId());
			vo.setName(up.getName());
			vo.setPrivilege(getPrivilegeUrgentPhone(rList, up.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeUrgentPhone(
			List<RoleResourcePermissionUP> rList, String id) {
		for (RoleResourcePermissionUP r : rList) {
			if (r.getUrgentPhone().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganDisPlayWallWithPermission(String organId,
			String roleId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(DisplayWall.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganDisPlayWallWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(DisplayWall.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<DisplayWall> dws = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] dwIds = new String[dws.size()];
		int index = 0;
		for (DisplayWall dw : dws) {
			dwIds[index++] = dw.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionWall.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (dwIds.length > 0) {
			criteriaR.add(Restrictions.in("displayWall.id", dwIds));
		}
		List<RoleResourcePermissionWall> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_DISPLAY_WALL + "";
		for (DisplayWall dw : dws) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(dw.getId());
			vo.setName(dw.getName());
			vo.setPrivilege(getPrivilegeDisplayWall(rList, dw.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeDisplayWall(
			List<RoleResourcePermissionWall> rList, String id) {
		for (RoleResourcePermissionWall r : rList) {
			if (r.getDisplayWall().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganSubPlatformWithPermission(String organId,
			String roleId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		// criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		// criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		// criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		// criteria.add(Restrictions.eq("organ.id", organId));
		// criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<SubPlatformResource> spr = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] sprIds = new String[spr.size()];
		int index = 0;
		for (SubPlatformResource sp : spr) {
			sprIds[index++] = sp.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionSubResource.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (sprIds.length > 0) {
			criteriaR.add(Restrictions.in("subResource.id", sprIds));
		}
		List<RoleResourcePermissionSubResource> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "";
		for (SubPlatformResource sp : spr) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(sp.getId());
			vo.setName(sp.getName());
			vo.setPrivilege(getPrivilegeSubPlatform(rList, sp.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}

	private String getPrivilegeSubPlatform(
			List<RoleResourcePermissionSubResource> rList, String id) {
		for (RoleResourcePermissionSubResource r : rList) {
			if (r.getSubResource().getId().equals(id)) {
				return r.getPrivilege();
			}
		}
		return "";
	}

	@Override
	public int countOrganSubPlatformWithPermission1(String organId,
			String roleId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		// criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("path", "%" + organId + "%"));
		criteria.setProjection(Projections.rowCount());
		Number n = (Number) criteria.uniqueResult();
		return n.intValue();
	}

	@Override
	public List<DevicePermissionVO> listOrganSubPlatformWithPermission1(
			String organId, String roleId, String name, int startIndex,
			int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		// criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("path", "%" + organId + "%"));
		// criteria.addOrder(Order.desc("name"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<SubPlatformResource> spr = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] sprIds = new String[spr.size()];
		int index = 0;
		for (SubPlatformResource sp : spr) {
			sprIds[index++] = sp.getId();
		}
		Criteria criteriaR = session
				.createCriteria(RoleResourcePermissionSubResource.class);
		criteriaR.add(Restrictions.eq("role.id", roleId));
		if (sprIds.length > 0) {
			criteriaR.add(Restrictions.in("subResource.id", sprIds));
		}
		List<RoleResourcePermissionSubResource> rList = criteriaR.list();
		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "";
		for (SubPlatformResource sp : spr) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(sp.getId());
			vo.setName(sp.getName());
			vo.setPrivilege(getPrivilegeSubPlatform(rList, sp.getId()));
			vo.setType(type);
			rtn.add(vo);
		}
		return rtn;
	}
}
