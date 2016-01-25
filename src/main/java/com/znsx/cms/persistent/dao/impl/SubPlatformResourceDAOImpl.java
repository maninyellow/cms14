package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.SubPlatformResourceDAO;
import com.znsx.cms.persistent.model.RoleResourcePermissionSubResource;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.util.string.MyStringUtil;

/**
 * 下级平台资源数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:55:54
 */
public class SubPlatformResourceDAOImpl extends
		BaseDAOImpl<SubPlatformResource, String> implements
		SubPlatformResourceDAO {

	private static int saveCount = 0;
	private static int modifyCount = 0;

	@Override
	public Map<String, SubPlatformResource> mapPlatformResourceById(
			String parentId) {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session
					.createCriteria(SubPlatformResource.class);
			criteria.add(Restrictions.like("path", "%" + parentId.toString()
					+ "%"));
			// criteria.add(Restrictions.in("type", new String[] { "2", "04",
			// "05", "06" }));
			List<SubPlatformResource> list = criteria.list();
			Map<String, SubPlatformResource> map = new HashMap<String, SubPlatformResource>();
			for (SubPlatformResource entity : list) {
				map.put(entity.getId(), entity);
			}
			return map;
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}

	@Override
	public void clearCache() throws BusinessException {
		Session session = getSession();
		session.flush();
		session.clear();
	}

	@Override
	public void deleteByPlatform(String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		SubPlatformResource platform = (SubPlatformResource) criteria
				.uniqueResult();
		if (null != platform) {
			String id = platform.getId().toString();
			Query sql = session.createQuery(SqlFactory.getInstance()
					.deleteSubPlatform());
			sql.setString(0, "/" + id + "/%");
			sql.executeUpdate();
			// 删除根机构
			session.delete(platform);
		}
	}

	@Override
	public SubPlatformResource getByStandardNumber(String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		return (SubPlatformResource) criteria.uniqueResult();
	}

	@Override
	public List<SubPlatformResource> listSubPlatformResource(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.eq("parent.id", parentId));
		return criteria.list();
	}

	@Override
	public List<SubPlatformResource> listRoots() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.isNull("parent"));
		return criteria.list();
	}

	@Override
	public List<SubPlatformResource> listSubOrgan(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.in("type", new String[] { "1", "0", "100",
				"110", "120", "121", "122" }));
		if (null != parentId) {
			criteria.add(Restrictions.eq("parent.id", parentId));
		}
		return criteria.list();
	}

	@Override
	public List<SubPlatformResource> listCamera(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.in("type", new String[] { "2", "04", "05",
				"06" }));
		if (null != parentId) {
			criteria.add(Restrictions.like("path", "%" + parentId + "%"));
		}
		return criteria.list();
	}

	@Override
	public List<SubPlatformResource> listOrgan(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.in("type", new String[] { "1", "0", "100",
				"110", "120", "121", "122" }));
		if (null != parentId) {
			criteria.add(Restrictions.like("path", "%" + parentId + "%"));
		}
		return criteria.list();
	}

	@Override
	public Map<String, SubPlatformResource> mapSubPlatformResource(String rootId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		if (StringUtils.isNotBlank(rootId)) {
			criteria.add(Restrictions.like("path", "%" + rootId + "%"));
		}
		List<SubPlatformResource> list = criteria.list();
		Map<String, SubPlatformResource> map = new HashMap<String, SubPlatformResource>();
		for (SubPlatformResource resource : list) {
			map.put(resource.getStandardNumber(), resource);
		}
		return map;
	}

	@Override
	public List<SubPlatformResource> listRoleSubResource(String roleId,
			String parentId) {
		Session session = getSession();
		Query q = session.createSQLQuery(SqlFactory.getInstance()
				.listRoleSubResource(parentId));
		q.setString(0, roleId);
		if (StringUtils.isNotBlank(parentId)) {
			q.setString(1, parentId);
		}
		List<Object[]> list = q.list();
		List<SubPlatformResource> rtn = new LinkedList<SubPlatformResource>();
		int index = 0;
		for (Object[] row : list) {
			index = 0;
			SubPlatformResource resource = new SubPlatformResource();
			resource.setId(MyStringUtil.object2String(row[index++]));
			resource.setName(MyStringUtil.object2String(row[index++]));
			resource.setStandardNumber(MyStringUtil.object2String(row[index++]));
			SubPlatformResource parent = new SubPlatformResource();
			parent.setId(MyStringUtil.object2String(row[index++]));
			resource.setParent(parent);
			resource.setType(MyStringUtil.object2String(row[index++]));
			resource.setProtocol(MyStringUtil.object2String(row[index++]));
			resource.setAuth(MyStringUtil.object2String(row[index++]));
			resource.setPath(MyStringUtil.object2String(row[index++]));
			String updateTime = MyStringUtil.object2String(row[index++]);
			resource.setUpdateTime(StringUtils.isBlank(updateTime) ? null
					: Long.valueOf(updateTime));
			resource.setStakeNumber(MyStringUtil.object2String(row[index++]));
			resource.setLongitude(MyStringUtil.object2String(row[index++]));
			resource.setLatitude(MyStringUtil.object2String(row[index++]));
			resource.setRoadName(MyStringUtil.object2String(row[index++]));
			resource.setNavigation(MyStringUtil.object2String(row[index++]));
			String width = MyStringUtil.object2String(row[index++]);
			resource.setWidth(StringUtils.isBlank(width) ? null : Integer
					.valueOf(width));
			String height = MyStringUtil.object2String(row[index++]);
			resource.setHeight(StringUtils.isBlank(height) ? null : Integer
					.valueOf(height));
			resource.setManufacturer(MyStringUtil.object2String(row[index++]));
			resource.setModel(MyStringUtil.object2String(row[index++]));
			resource.setOwner(MyStringUtil.object2String(row[index++]));
			resource.setCivilCode(MyStringUtil.object2String(row[index++]));
			resource.setBlock(MyStringUtil.object2String(row[index++]));
			resource.setAddress(MyStringUtil.object2String(row[index++]));
			String parental = MyStringUtil.object2String(row[index++]);
			resource.setParental(StringUtils.isBlank(parental) ? null : Integer
					.valueOf(parental));
			String safeway = MyStringUtil.object2String(row[index++]);
			resource.setSafetyWay(StringUtils.isBlank(safeway) ? null : Integer
					.valueOf(safeway));
			String registerWay = MyStringUtil.object2String(row[index++]);
			resource.setRegisterWay(StringUtils.isBlank(registerWay) ? null
					: Integer.valueOf(registerWay));
			resource.setCertNum(MyStringUtil.object2String(row[index++]));
			String certifiable = MyStringUtil.object2String(row[index++]);
			resource.setCertifiable(StringUtils.isBlank(certifiable) ? null
					: Integer.valueOf(certifiable));
			String errCode = MyStringUtil.object2String(row[index++]);
			resource.setErrCode(StringUtils.isBlank(errCode) ? null : Integer
					.valueOf(errCode));
			String endTime = MyStringUtil.object2String(row[index++]);
			resource.setEndTime(StringUtils.isBlank(endTime) ? null : Long
					.valueOf(endTime));
			String security = MyStringUtil.object2String(row[index++]);
			resource.setSecrecy(StringUtils.isBlank(security) ? null : Integer
					.valueOf(security));
			resource.setIp(MyStringUtil.object2String(row[index++]));
			String port = MyStringUtil.object2String(row[index++]);
			resource.setPort(StringUtils.isBlank(port) ? null : Integer
					.valueOf(port));
			resource.setPassword(MyStringUtil.object2String(row[index++]));
			resource.setStatus(MyStringUtil.object2String(row[index++]));
			resource.setPtzType(MyStringUtil.object2String(row[index++]));
			resource.setGatewayId(MyStringUtil.object2String(row[index++]));
			rtn.add(resource);
		}
		return rtn;
	}

	@Override
	public List<SubPlatformResource> listRoleRoots(String roleId) {
		Session session = getSession();
		Query q = session.createSQLQuery(SqlFactory.getInstance()
				.listRoleRoots());
		q.setString(0, roleId);

		List<Object[]> list = q.list();
		List<SubPlatformResource> rtn = new LinkedList<SubPlatformResource>();
		int index = 0;
		for (Object[] row : list) {
			index = 0;
			SubPlatformResource resource = new SubPlatformResource();
			resource.setId(MyStringUtil.object2String(row[index++]));
			resource.setName(MyStringUtil.object2String(row[index++]));
			resource.setStandardNumber(MyStringUtil.object2String(row[index++]));
			SubPlatformResource parent = new SubPlatformResource();
			parent.setId(MyStringUtil.object2String(row[index++]));
			resource.setParent(parent);
			resource.setType(MyStringUtil.object2String(row[index++]));
			resource.setProtocol(MyStringUtil.object2String(row[index++]));
			resource.setAuth(MyStringUtil.object2String(row[index++]));
			resource.setPath(MyStringUtil.object2String(row[index++]));
			String updateTime = MyStringUtil.object2String(row[index++]);
			resource.setUpdateTime(StringUtils.isBlank(updateTime) ? null
					: Long.valueOf(updateTime));
			resource.setStakeNumber(MyStringUtil.object2String(row[index++]));
			resource.setLongitude(MyStringUtil.object2String(row[index++]));
			resource.setLatitude(MyStringUtil.object2String(row[index++]));
			resource.setRoadName(MyStringUtil.object2String(row[index++]));
			resource.setNavigation(MyStringUtil.object2String(row[index++]));
			String width = MyStringUtil.object2String(row[index++]);
			resource.setWidth(StringUtils.isBlank(width) ? null : Integer
					.valueOf(width));
			String height = MyStringUtil.object2String(row[index++]);
			resource.setHeight(StringUtils.isBlank(height) ? null : Integer
					.valueOf(height));
			resource.setManufacturer(MyStringUtil.object2String(row[index++]));
			resource.setModel(MyStringUtil.object2String(row[index++]));
			resource.setOwner(MyStringUtil.object2String(row[index++]));
			resource.setCivilCode(MyStringUtil.object2String(row[index++]));
			resource.setBlock(MyStringUtil.object2String(row[index++]));
			resource.setAddress(MyStringUtil.object2String(row[index++]));
			String parental = MyStringUtil.object2String(row[index++]);
			resource.setParental(StringUtils.isBlank(parental) ? null : Integer
					.valueOf(parental));
			String safeway = MyStringUtil.object2String(row[index++]);
			resource.setSafetyWay(StringUtils.isBlank(safeway) ? null : Integer
					.valueOf(safeway));
			String registerWay = MyStringUtil.object2String(row[index++]);
			resource.setRegisterWay(StringUtils.isBlank(registerWay) ? null
					: Integer.valueOf(registerWay));
			resource.setCertNum(MyStringUtil.object2String(row[index++]));
			String certifiable = MyStringUtil.object2String(row[index++]);
			resource.setCertifiable(StringUtils.isBlank(certifiable) ? null
					: Integer.valueOf(certifiable));
			String errCode = MyStringUtil.object2String(row[index++]);
			resource.setErrCode(StringUtils.isBlank(errCode) ? null : Integer
					.valueOf(errCode));
			String endTime = MyStringUtil.object2String(row[index++]);
			resource.setEndTime(StringUtils.isBlank(endTime) ? null : Long
					.valueOf(endTime));
			String security = MyStringUtil.object2String(row[index++]);
			resource.setSecrecy(StringUtils.isBlank(security) ? null : Integer
					.valueOf(security));
			resource.setIp(MyStringUtil.object2String(row[index++]));
			String port = MyStringUtil.object2String(row[index++]);
			resource.setPort(StringUtils.isBlank(port) ? null : Integer
					.valueOf(port));
			resource.setPassword(MyStringUtil.object2String(row[index++]));
			resource.setStatus(MyStringUtil.object2String(row[index++]));
			resource.setPtzType(MyStringUtil.object2String(row[index++]));
			resource.setGatewayId(MyStringUtil.object2String(row[index++]));
			rtn.add(resource);
		}
		return rtn;
	}

	@Override
	public List<SubPlatformResource> listRoleSubOrgan(String roleId,
			String parentId) {
		Session session = getSession();
		Query q = session.createSQLQuery(SqlFactory.getInstance()
				.listRoleSubOrgan());
		q.setString(0, roleId);
		q.setString(1, parentId);

		List<Object[]> list = q.list();
		List<SubPlatformResource> rtn = new LinkedList<SubPlatformResource>();
		int index = 0;
		for (Object[] row : list) {
			index = 0;
			SubPlatformResource resource = new SubPlatformResource();
			resource.setId(MyStringUtil.object2String(row[index++]));
			resource.setName(MyStringUtil.object2String(row[index++]));
			resource.setStandardNumber(MyStringUtil.object2String(row[index++]));
			SubPlatformResource parent = new SubPlatformResource();
			parent.setId(MyStringUtil.object2String(row[index++]));
			resource.setParent(parent);
			resource.setType(MyStringUtil.object2String(row[index++]));
			resource.setProtocol(MyStringUtil.object2String(row[index++]));
			resource.setAuth(MyStringUtil.object2String(row[index++]));
			resource.setPath(MyStringUtil.object2String(row[index++]));
			String updateTime = MyStringUtil.object2String(row[index++]);
			resource.setUpdateTime(StringUtils.isBlank(updateTime) ? null
					: Long.valueOf(updateTime));
			resource.setStakeNumber(MyStringUtil.object2String(row[index++]));
			resource.setLongitude(MyStringUtil.object2String(row[index++]));
			resource.setLatitude(MyStringUtil.object2String(row[index++]));
			resource.setRoadName(MyStringUtil.object2String(row[index++]));
			resource.setNavigation(MyStringUtil.object2String(row[index++]));
			String width = MyStringUtil.object2String(row[index++]);
			resource.setWidth(StringUtils.isBlank(width) ? null : Integer
					.valueOf(width));
			String height = MyStringUtil.object2String(row[index++]);
			resource.setHeight(StringUtils.isBlank(height) ? null : Integer
					.valueOf(height));
			resource.setManufacturer(MyStringUtil.object2String(row[index++]));
			resource.setModel(MyStringUtil.object2String(row[index++]));
			resource.setOwner(MyStringUtil.object2String(row[index++]));
			resource.setCivilCode(MyStringUtil.object2String(row[index++]));
			resource.setBlock(MyStringUtil.object2String(row[index++]));
			resource.setAddress(MyStringUtil.object2String(row[index++]));
			String parental = MyStringUtil.object2String(row[index++]);
			resource.setParental(StringUtils.isBlank(parental) ? null : Integer
					.valueOf(parental));
			String safeway = MyStringUtil.object2String(row[index++]);
			resource.setSafetyWay(StringUtils.isBlank(safeway) ? null : Integer
					.valueOf(safeway));
			String registerWay = MyStringUtil.object2String(row[index++]);
			resource.setRegisterWay(StringUtils.isBlank(registerWay) ? null
					: Integer.valueOf(registerWay));
			resource.setCertNum(MyStringUtil.object2String(row[index++]));
			String certifiable = MyStringUtil.object2String(row[index++]);
			resource.setCertifiable(StringUtils.isBlank(certifiable) ? null
					: Integer.valueOf(certifiable));
			String errCode = MyStringUtil.object2String(row[index++]);
			resource.setErrCode(StringUtils.isBlank(errCode) ? null : Integer
					.valueOf(errCode));
			String endTime = MyStringUtil.object2String(row[index++]);
			resource.setEndTime(StringUtils.isBlank(endTime) ? null : Long
					.valueOf(endTime));
			String security = MyStringUtil.object2String(row[index++]);
			resource.setSecrecy(StringUtils.isBlank(security) ? null : Integer
					.valueOf(security));
			resource.setIp(MyStringUtil.object2String(row[index++]));
			String port = MyStringUtil.object2String(row[index++]);
			resource.setPort(StringUtils.isBlank(port) ? null : Integer
					.valueOf(port));
			resource.setPassword(MyStringUtil.object2String(row[index++]));
			resource.setStatus(MyStringUtil.object2String(row[index++]));
			resource.setPtzType(MyStringUtil.object2String(row[index++]));
			resource.setGatewayId(MyStringUtil.object2String(row[index++]));
			rtn.add(resource);
		}
		return rtn;
	}

	@Override
	public List<SubPlatformResource> listSubPlatformResourceByOrganId(
			String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubPlatformResource.class);
		criteria.add(Restrictions.like("path", "%" + organId + "%"));
//		criteria.add(Restrictions.not(Restrictions.eq("type", "0")));
		return criteria.list();
	}
}
