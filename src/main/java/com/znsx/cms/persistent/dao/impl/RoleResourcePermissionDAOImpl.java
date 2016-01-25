package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.znsx.cms.persistent.dao.RoleResourcePermissionDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.RoleResourcePermission;
import com.znsx.cms.persistent.model.RoleResourcePermissionCamera;
import com.znsx.cms.persistent.model.RoleResourcePermissionSubResource;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.ResourcePermissionVO;

/**
 * 角色资源权限数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午10:13:03
 */
public class RoleResourcePermissionDAOImpl extends
		BaseDAOImpl<RoleResourcePermission, String> implements
		RoleResourcePermissionDAO {

	@Override
	public List<ResourcePermissionVO> listByRoleId(String roleId, int start,
			int limit) {
		Session session = getSession();
		Criteria criteria = session
				.createCriteria(RoleResourcePermissionCamera.class);
		criteria.add(Restrictions.eq("role.id", roleId));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		List<RoleResourcePermissionCamera> list = criteria.list();
		List<ResourcePermissionVO> rtnList = new LinkedList<ResourcePermissionVO>();
		String type = TypeDefinition.DEVICE_TYPE_CAMERA + "";
		for (RoleResourcePermissionCamera r : list) {
			ResourcePermissionVO vo = new ResourcePermissionVO();
			vo.setOrganId(r.getCamera().getOrgan().getId());
			vo.setOrganName(r.getCamera().getOrgan().getName());
			vo.setPrivilege(r.getPrivilege());
			vo.setResourceId(r.getCamera().getId());
			vo.setResourceName(r.getCamera().getName());
			vo.setResourceType(type);
			rtnList.add(vo);
		}
		return rtnList;
	}

	@Override
	public int countByRoleId(String roleId) {
		Session session = getSession();
		Criteria criteria = session
				.createCriteria(RoleResourcePermissionCamera.class);
		criteria.add(Restrictions.eq("role.id", roleId));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public void deleteByRole(String roleId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteRoleResources());
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	@Override
	public void deleteByRoleResource(String roleId, String type,
			String[] resourceIds) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteRoleResourcesByResource(resourceIds.length));
		query.setParameter(0, roleId);
		query.setParameter(1, type);
		for (int i = 0; i < resourceIds.length; i++) {
			query.setParameter(i + 2, resourceIds[i]);
		}
		query.executeUpdate();
	}

	@Override
	public List<RoleResourcePermission> listByRoleIds(List<String> roleIds,
			String resoureType) {
		Session session = getSession();
		Criteria criteria = null;
		if (resoureType != null) {
			if ((TypeDefinition.DEVICE_TYPE_CAMERA + "").equals(resoureType)) {
				criteria = session
						.createCriteria(RoleResourcePermissionCamera.class);
			} else if ((TypeDefinition.DEVICE_TYPE_SUB_RESOURCE + "")
					.equals(resoureType)) {
				criteria = session
						.createCriteria(RoleResourcePermissionSubResource.class);
			}
		} else {
			criteria = session.createCriteria(RoleResourcePermission.class);
		}

		criteria.add(Restrictions.in("role.id", roleIds));
		return criteria.list();
	}

	@Override
	public int countAllResourcesByRoleId(String roleId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.countRoleAllResourcePermissions());
		query.setParameter(0, roleId);
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public ResourcePermissionVO findResourceByRoleId(String resourceId,
			String roleId, String type) {
		Session session = getSession();
		SQLQuery query = null;
		if (type.equals(TypeDefinition.DEVICE_TYPE_CAMERA + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleCameraPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_VD + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleVDPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_WS + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleWSPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_WST + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleWSTPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_LOLI + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleLoliPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_FD + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleFDPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_COVI + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleCoviPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_NOD + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleNODPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_PB + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRolePBPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_BT + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleBTPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_VI_DETECTOR + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleViDPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_ROAD_DETECTOR + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleRoadDPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_BRIDGE_DETECTOR + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleBridgeDPermissions());
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_EMERGENCY_PHONE + "")) {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleUrgentPhonePermissions());
		} else {
			query = session.createSQLQuery(SqlFactory.getInstance()
					.listRoleControlDevicePermissions());
		}
		query.setParameter(0, roleId);
		query.setParameter(1, resourceId);
		query.setParameter(2, type);
		query.addScalar("resourceId", Hibernate.STRING);
		query.addScalar("resourceType", Hibernate.STRING);
		query.addScalar("privilege", Hibernate.STRING);
		query.addScalar("resourceName", Hibernate.STRING);
		query.addScalar("organId", Hibernate.STRING);
		query.addScalar("organName", Hibernate.STRING);
		query.setResultTransformer(Transformers
				.aliasToBean(ResourcePermissionVO.class));
		ResourcePermissionVO vo = (ResourcePermissionVO) query.uniqueResult();
		return vo;
	}

	@Override
	public List<String> listRoleResourceId(String roleId,
			List<String> resourceType) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listRoleResourceId(resourceType));
		query.setString(0, roleId);
		List<String> list = query.list();
		return list;
	}

	@Override
	public void batchInsert(List<RoleResourcePermission> list)
			throws BusinessException {
		Session session = getSession();
		batchInsert(list, session);

	}
}
