package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 机构数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:47:21
 */
public class OrganDAOImpl extends BaseDAOImpl<Organ, String> implements
		OrganDAO {
	@Override
	public List<Organ> listOrgan(String parentId, String name,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		if (StringUtils.isNotBlank(parentId)) {
			criteria.add(Restrictions.eq("parent.id", parentId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.setFetchMode("parent", FetchMode.JOIN);
		// criteria.addOrder(Order.asc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public String[] findOrgansByOrganId(String organId) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listOrganIdByParent());
		query.setString(0, "%" + organId + "%");
		List<String> list = query.list();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}

	@Override
	public Organ getRootOrgan() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		criteria.add(Restrictions.isNull("parent.id"));
		List<Organ> list = criteria.list();
		if (list.size() <= 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"Root organ not found !");
		}
		return list.get(0);
	}

	@Override
	public List<Organ> listOrganById(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		criteria.add(Restrictions.like("path", "%" + organId + "%"));
		// criteria.addOrder(Order.asc("createTime"));
		List<Organ> list = criteria.list();
		if (list.size() <= 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Organ["
					+ organId + "] not found !");
		}
		return list;
	}

	@Override
	public Integer organTotalCount(String parentId, String organName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		if (StringUtils.isNotBlank(parentId)) {
			criteria.add(Restrictions.eq("parent.id", parentId));
		}
		if (StringUtils.isNotBlank(organName)) {
			criteria.add(Restrictions.like("name", "%" + organName + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<Organ> listOrganByName(String[] organIds, String name,
			Integer startIndex, Integer limit, String standardNumber,
			String type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		criteria.add(Property.forName("id").in(organIds));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		// criteria.addOrder(Order.asc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer listOrganTotalCount(String[] organIds, String name,
			String standardNumber, String type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		criteria.add(Property.forName("id").in(organIds));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type", type));
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<Organ> listOrganByParentId(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		if (StringUtils.isNotBlank(parentId)) {
			criteria.add(Restrictions.eq("parent.id", parentId));
		}
		criteria.add(Restrictions.or(Restrictions.eq("type", "1"),
				Restrictions.eq("type", "2")));
		// criteria.addOrder(Order.asc("createTime"));
		return criteria.list();
	}

	@Override
	public OrganRoad getEventRoad(String roadName, String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		criteria.add(Restrictions.like("path", "%" + parentId + "%"));
		List<Organ> list = criteria.list();
		for (Organ organ : list) {
			if (TypeDefinition.ORGAN_TYPE_ROAD.equals(organ.getType())) {
				if (StringUtils.contains(organ.getName(), roadName.trim())) {
					return (OrganRoad) organ;
				}
			}
		}
		// 按照名称没有匹配成功，返回上级机构下找到的第一个路段
		for (Organ organ : list) {
			if (TypeDefinition.ORGAN_TYPE_ROAD.equals(organ.getType())) {
				return (OrganRoad) organ;
			}
		}

		return null;
	}

	@Override
	public Map<String, Organ> mapOrganBySn(String parentId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organ.class);
		if (StringUtils.isNotBlank(parentId)) {
			criteria.add(Restrictions.like("path", "%" + parentId + "%"));
		}
		List<Organ> list = criteria.list();
		Map<String, Organ> map = new HashMap<String, Organ>();
		for (Organ o : list) {
			map.put(o.getStandardNumber(), o);
		}
		return map;
	}

	@Override
	public List<Organ> listOrganByTypesNoTransaction(String[] types) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(Organ.class);
		if (types != null) {
			criteria.add(Restrictions.in("type", types));
		}
		try {
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
	}
}
