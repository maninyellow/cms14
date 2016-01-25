package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.GPSDeviceDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.GPSDevice;
import com.znsx.cms.persistent.model.GPSDeviceCamera;
import com.znsx.cms.persistent.model.SolarDeviceCamera;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.model.DevicePermissionVO;

/**
 * GPSDeviceDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午1:51:06
 */
public class GPSDeviceDAOImpl extends BaseDAOImpl<GPSDevice, String> implements
		GPSDeviceDAO {

	@Override
	public void deleteByGPSDevice(String gpsId, String type, String[] deviceIds) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteGPSDevices(deviceIds.length));
		query.setParameter(0, gpsId);
		query.setParameter(1, type);
		for (int i = 0; i < deviceIds.length; i++) {
			query.setParameter(i + 2, deviceIds[i]);
		}
		query.executeUpdate();
	}

	@Override
	public List<DevicePermissionVO> listOrganCameraByGPS(String organId,
			String gpsId, String name, int startIndex, int limit) {
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
		Criteria criteriaR = session.createCriteria(GPSDeviceCamera.class);
		criteriaR.add(Restrictions.eq("gps.id", gpsId));
		if (cameraIds.length > 0) {
			criteriaR.add(Restrictions.in("camera.id", cameraIds));
		}
		List<GPSDeviceCamera> rList = criteriaR.list();

		Criteria criteriaS = session.createCriteria(GPSDeviceCamera.class);
		criteriaS.add(Restrictions.ne("gps.id", gpsId));
		if (cameraIds.length > 0) {
			criteriaS.add(Restrictions.in("camera.id", cameraIds));
		}
		List<GPSDeviceCamera> sList = criteriaS.list();

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

	private String getRelevanceCamera(List<GPSDeviceCamera> sList,
			String cameraId) {
		for (GPSDeviceCamera r : sList) {
			if (r.getCamera().getId().equals(cameraId)) {
				return "1";
			}
		}
		return "";
	}

	private String getPrivilegeCamera(List<GPSDeviceCamera> rList,
			String cameraId) {
		for (GPSDeviceCamera r : rList) {
			if (r.getCamera().getId().equals(cameraId)) {
				return "1";
			}
		}
		return "";
	}

	@Override
	public void removeGPSDevice(String gpsId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteGPSDevice());
		query.setParameter(0, gpsId);
		query.executeUpdate();
	}
}
