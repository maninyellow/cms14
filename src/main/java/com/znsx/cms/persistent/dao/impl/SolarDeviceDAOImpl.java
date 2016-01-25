package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SolarDeviceDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.persistent.model.SolarDevice;
import com.znsx.cms.persistent.model.SolarDeviceCamera;
import com.znsx.cms.persistent.model.SolarDeviceVD;
import com.znsx.cms.persistent.model.Vehicle;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * SolarDeviceDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:06:36
 */
public class SolarDeviceDAOImpl extends BaseDAOImpl<SolarDevice, String>
		implements SolarDeviceDAO {

	@Override
	public void deleteBySolarDevice(String solarId, String type,
			String[] deviceIds) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteSolarDevices(deviceIds.length));
		query.setParameter(0, solarId);
		query.setParameter(1, type);
		for (int i = 0; i < deviceIds.length; i++) {
			query.setParameter(i + 2, deviceIds[i]);
		}
		query.executeUpdate();

	}

	@Override
	public int countOrganCamera(String organId, String solarId, String name) {
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
	public List<DevicePermissionVO> listOrganCamera(String organId,
			String solarId, String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(Camera.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<Camera> cameras = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] cameraIds = new String[cameras.size()];
		int index = 0;
		for (Camera camera : cameras) {
			cameraIds[index++] = camera.getId();
		}
		Criteria criteriaR = session.createCriteria(SolarDeviceCamera.class);
		criteriaR.add(Restrictions.eq("solarBattery.id", solarId));
		if (cameraIds.length > 0) {
			criteriaR.add(Restrictions.in("camera.id", cameraIds));
		}
		List<SolarDeviceCamera> rList = criteriaR.list();

		Criteria criteriaS = session.createCriteria(SolarDeviceCamera.class);
		criteriaS.add(Restrictions.ne("solarBattery.id", solarId));
		if (cameraIds.length > 0) {
			criteriaS.add(Restrictions.in("camera.id", cameraIds));
		}
		List<SolarDeviceCamera> sList = criteriaS.list();

		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		for (Camera camera : cameras) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(camera.getId());
			vo.setName(camera.getName());
			vo.setPrivilege(getPrivilegeCamera(rList, camera.getId()));
			vo.setType(type);
			vo.setRelevance(getRelevanceCamera(sList, camera.getId()));
			rtn.add(vo);
		}
		return rtn;
	}

	private String getRelevanceCamera(List<SolarDeviceCamera> sList,
			String cameraId) {
		for (SolarDeviceCamera r : sList) {
			if (r.getCamera().getId().equals(cameraId)) {
				return "1";
			}
		}
		return "";
	}

	private String getPrivilegeCamera(List<SolarDeviceCamera> rList,
			String cameraId) {
		for (SolarDeviceCamera r : rList) {
			if (r.getCamera().getId().equals(cameraId)) {
				return "1";
			}
		}
		return "";
	}

	@Override
	public void removeSolarDevice(String solarId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteSolarDevice());
		query.setParameter(0, solarId);
		query.executeUpdate();
	}

	@Override
	public int countOrganVD(String organId, String solarId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
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
	public List<DevicePermissionVO> listOrganVD(String organId, String solarId,
			String name, int startIndex, int limit) {
		Session session = getSession();
		// 分页查询设备
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria = criteria.createAlias("organ", "organ");
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Restrictions.like("organ.path", "%" + organId + "%"));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<VehicleDetector> vds = criteria.list();
		// 根据设备查询结果，再去查询关联表
		String[] vdIds = new String[vds.size()];
		int index = 0;
		for (VehicleDetector vd : vds) {
			vdIds[index++] = vd.getId();
		}
		Criteria criteriaR = session.createCriteria(SolarDeviceVD.class);
		criteriaR.add(Restrictions.eq("solarBattery.id", solarId));
		if (vdIds.length > 0) {
			criteriaR.add(Restrictions.in("vd.id", vdIds));
		}
		List<SolarDeviceVD> rList = criteriaR.list();

		Criteria criteriaS = session.createCriteria(SolarDeviceVD.class);
		criteriaS.add(Restrictions.ne("solarBattery.id", solarId));
		if (vdIds.length > 0) {
			criteriaS.add(Restrictions.in("vd.id", vdIds));
		}
		List<SolarDeviceVD> sList = criteriaS.list();

		// 构建返回结果
		List<DevicePermissionVO> rtn = new LinkedList<DevicePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		for (VehicleDetector vd : vds) {
			DevicePermissionVO vo = new DevicePermissionVO();
			vo.setId(vd.getId());
			vo.setName(vd.getName());
			vo.setPrivilege(getPrivilegeVD(rList, vd.getId()));
			vo.setType(type);
			vo.setRelevance(getRelevanceVD(sList, vd.getId()));
			rtn.add(vo);
		}
		return rtn;
	}

	private String getRelevanceVD(List<SolarDeviceVD> sList, String id) {
		for (SolarDeviceVD r : sList) {
			if (r.getVd().getId().equals(id)) {
				return "1";
			}
		}
		return "";
	}

	private String getPrivilegeVD(List<SolarDeviceVD> rList, String id) {
		for (SolarDeviceVD r : rList) {
			if (r.getVd().getId().equals(id)) {
				return "1";
			}
		}
		return "";
	}

}
