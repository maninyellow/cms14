package com.znsx.cms.persistent.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.BaseDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.util.file.Configuration;

@SuppressWarnings("unchecked")
public abstract class BaseDAOImpl<T, PK extends Serializable> implements
		BaseDAO<T, PK> {

	// @Resource(name = "sessionFactory")
	protected SessionFactory sessionFactory;

	protected SessionFactory sessionFactoryDAS;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSessionFactoryDAS(SessionFactory sessionFactoryDAS) {
		this.sessionFactoryDAS = sessionFactoryDAS;
	}

	protected Class<T> clazz;

	/**
	 * 存放分区表是否创建的Map,key是分区表的名称,value表示是否创建
	 */
	private Map<String, Boolean> creationMap = new HashMap<String, Boolean>();

	public BaseDAOImpl() {
		Object object = this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public BaseDAOImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected Session getSession() {
		// return SessionFactoryUtils.getSession(sessionFactory, true);
		return sessionFactory.getCurrentSession();
	}

	protected Session getSessionDas() {
		return sessionFactoryDAS.getCurrentSession();
	}

	/**
	 * 打开无状态连接，使用完毕需要手动关闭: StatelessSession.close();
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-26 下午2:52:00
	 */
	protected StatelessSession getStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

	/**
	 * 打开无状态连接，使用完毕需要手动关闭: StatelessSession.close();
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 下午3:09:11
	 */
	protected StatelessSession getStatelessSessionDAS() {
		return sessionFactoryDAS.openStatelessSession();
	}

	@Override
	public T findUniResult(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		setParam(query, params);
		return (T) query.uniqueResult();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void deleteDas(T entity) {
		getSessionDas().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		getSession().delete(getSession().get(clazz, id));
	}

	@Override
	public List<T> findByHql(String hql, int start, int limit, Object... params) {
		Query query = getSession().createQuery(hql);
		setParam(query, params);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}

	private void setParam(Query query, Object... params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	@Override
	public List<T> findAll() {
		Query query = getSession()
				.createQuery(" from " + clazz.getSimpleName());
		return query.list();
	}

	@Override
	public List<T> findDasAll() {
		Query query = getSessionDas().createQuery(
				" from " + clazz.getSimpleName());
		return query.list();
	}

	@Override
	public List<T> findByHql(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		setParam(query, params);
		return query.list();
	}

	@Override
	public T findById(PK id) {
		T entity = null;
		try {
			entity = (T) getSession().get(clazz, id);
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "id[" + id
					+ "] for Class is not " + clazz.getSimpleName() + " !");
		}
		if (null == entity) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					clazz.getSimpleName() + " id[" + id + "] not found !");
		}
		return entity;
	}

	@Override
	public T findByIdDas(PK id) {
		T entity = null;
		try {
			entity = (T) getSessionDas().get(clazz, id);
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "id[" + id
					+ "] for Class is not " + clazz.getSimpleName() + " !");
		}
		if (null == entity) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					clazz.getSimpleName() + " id[" + id + "] not found !");
		}
		return entity;
	}

	@Override
	public List<T> findByPropertys(LinkedHashMap<String, Object> map) {
		Object[] params = new Object[map.size()];
		Query query = getSession().createQuery(
				"from " + clazz.getSimpleName() + " where "
						+ buildWhereSql(map, params));
		setParam(query, params);
		return query.list();
	}

	@Override
	public List<T> findByPropertysDas(LinkedHashMap<String, Object> map) {
		Object[] params = new Object[map.size()];
		Query query = getSessionDas().createQuery(
				"from " + clazz.getSimpleName() + " where "
						+ buildWhereSql(map, params));
		setParam(query, params);
		return query.list();
	}

	private String buildWhereSql(LinkedHashMap<String, Object> map,
			Object[] params) {
		StringBuilder builder = new StringBuilder();
		if (map != null && map.size() > 0) {
			int i = 0;
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				params[i++] = map.get(key);
				builder.append(key + "=? and ");
			}
			int pos = builder.length() - 4;
			return builder.substring(0, pos);
		}
		return "";
	}

	@Override
	public Integer getTotalCount() {
		Number result = (Number) getSession().createQuery(
				"select count(*) from " + clazz.getSimpleName()).uniqueResult();
		return result.intValue();
	}

	@Override
	public void save(T entity) {
		getSession().save(entity);

	}

	@Override
	public void saveDas(T entity) {
		getSessionDas().save(entity);

	}

	@Override
	public void saveorupdate(T entity) {
		getSession().saveOrUpdate(entity);

	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public void updateDas(T entity) {
		getSessionDas().update(entity);
	}

	@Override
	public void batchUpdate(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		setParam(query, params);
		query.executeUpdate();
	}

	@Override
	public Integer getTotalCount(String hql, Object... params) {
		Query query = getSession().createQuery(hql);
		setParam(query, params);
		Number result = (Number) query.uniqueResult();
		return result.intValue();
	}

	@Override
	public int getTotalCountBySql(String sql) {
		String countsql = "select count(*) from(" + sql + ")";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(countsql);
		Number num = (Number) query.uniqueResult();
		return num.intValue();
	}

	@Override
	public List readBySql(String sql, int start, int pageSize, Class clazz,
			Object... params) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		setParam(query, params);
		if (pageSize > 0) {
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		if (clazz != null) {
			query.addEntity(clazz);
		}
		return query.list();
	}

	@Override
	public List readAnyColumnBySql(String sql, int start, int pageSize,
			Class clazz, Map columnMap, Object... params) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		setParam(query, params);
		if (pageSize > 0) {
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		if (columnMap != null && columnMap.size() > 0) {
			HashMap<String, Type> map = (HashMap<String, Type>) columnMap;
			Set<String> keys = map.keySet();
			for (String column : keys) {
				Type type = map.get(column);
				if (type == null) {
					query.addScalar(column);
				} else {
					query.addScalar(column, type);
				}
			}
		}
		if (clazz != null) {
			query.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		return query.list();
	}

	@Override
	public void writeBySql(String sql, Object... params) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		setParam(query, params);
		query.executeUpdate();
	}

	@Override
	public T loadEntity(PK id) {
		return (T) getSession().load(clazz, id);
	}

	@Override
	public Object readBySql(String sql) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		return query.uniqueResult();
	}

	@Override
	public List<T> readBySql(String sql, Class clazz, Object... params) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (params != null)
			setParam(query, params);
		if (clazz != null)
			query.addEntity(clazz);
		return query.list();
	}

	@Override
	public void evict(T entity) {
		Session session = getSession();
		session.evict(entity);
	}

	@Override
	public void refresh(T entity) {
		Session session = getSession();
		session.refresh(entity, LockMode.UPGRADE);
	}

	@Override
	public String getNextId(String className, int increment) {
		Session session = getSession();
		String sql = "select sv_nextval('" + className + "'," + increment
				+ ") from dual";
		Query q = session.createSQLQuery(sql);
		return ((Number) q.uniqueResult()).longValue() + "";
	}

	@Override
	public int getStandardNumber(String className, int increment) {
		Session session = getSession();
		String sql = "select sv_nextval_standard('" + className + "',"
				+ increment + ") from dual";
		Query q = session.createSQLQuery(sql);
		return ((Number) q.uniqueResult()).intValue();
	}

	public String getCcsId(String className, String standardNumber) {
		Session session = getSession();
		String hql = "select a.ccs.id from " + className
				+ " a where a.standardNumber = ?";
		Query query = session.createQuery(hql);
		query.setString(0, standardNumber);
		String ccsId = query.uniqueResult() + "";
		return ccsId;
	}

	public void saveLonlat(String className, String standardNumber,
			String longitude, String latitude) {
		Session session = getSession();
		StringBuffer hql = new StringBuffer();
		hql.append("update ");
		hql.append(className);
		hql.append(" set longitude = ?");
		hql.append(", latitude = ?");
		hql.append(" where standardNumber = ?");
		Query query = session.createQuery(hql.toString());
		query.setString(0, longitude);
		query.setString(1, latitude);
		query.setString(2, standardNumber);
		query.executeUpdate();
	}

	@Override
	public List<T> getEntityByName(String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.like("name", "%" + name + "%"));
		// Object entity = criteria.uniqueResult();
		return criteria.list();
	}

	@Override
	public T findBySN(String standardNumber) throws BusinessException {
		Session session = getSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Object entity = criteria.uniqueResult();
		if (null == entity) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					clazz.getSimpleName() + " standardNumber[" + standardNumber
							+ "] not found !");
		}
		return (T) entity;
	}

	@Override
	public T findBySNDas(String standardNumber) throws BusinessException {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq("standardNumber", standardNumber));
		Object entity = criteria.uniqueResult();
		if (null == entity) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					clazz.getSimpleName() + " standardNumber[" + standardNumber
							+ "] not found !");
		}
		return (T) entity;
	}

	@Override
	public void batchInsert(List<T> list, Session session)
			throws BusinessException {
		int index = 0;
		for (T entity : list) {
			session.save(entity);
			index++;
			if (index % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	/**
	 * 检查并创建分区表
	 * 
	 * @param tableName
	 *            表名称
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午1:08:07
	 */
	protected void checkTablePartition(String tableName)
			throws BusinessException {
		String partitionName = getPartition(tableName);
		String nextPartitionName = getNextPartition(tableName);
		// 如果分区表不存在，创建分区表
		if (creationMap.get(partitionName) == null) {
			Session session = getSessionDas();
			Query query = session.createSQLQuery(SqlFactory.getInstance()
					.checkTablePartitions(tableName));
			List<Object> rows = query.list();
			boolean isExist = false;
			for (Object row : rows) {
				// 下个分区表已经存在
				if (nextPartitionName.equals((String) row)) {
					return;
				}
				// 当前分区表存在
				if (partitionName.equals((String) row)) {
					isExist = true;
				}
			}
			// 分区表都不存在
			if (!isExist) {
				// 创建当前分区表
				query = session.createSQLQuery(SqlFactory.getInstance()
						.createTablePartition(tableName, partitionName));
				query.executeUpdate();

				// 创建下个分区表
				query = session.createSQLQuery(SqlFactory.getInstance()
						.createTablePartition(tableName, nextPartitionName));
				query.executeUpdate();
			}
			// 创建下个分区表
			else {
				query = session.createSQLQuery(SqlFactory.getInstance()
						.createTablePartition(tableName, nextPartitionName));
				query.executeUpdate();
			}
			creationMap.put(partitionName, Boolean.TRUE);
		}
	}

	/**
	 * 获取当前分区表名称
	 * 
	 * @param tableName
	 *            主表名称
	 * @return 当前分区表名称
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-2 上午11:26:11
	 */
	private String getPartition(String tableName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		String suffix = sdf.format(calendar.getTime());
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return tableName.toUpperCase() + suffix;
		} else if (Configuration.MYSQL.equals(dbName)) {
			return tableName.toLowerCase() + suffix;
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
	}

	/**
	 * 获取下一个分区表
	 * 
	 * @param tableName
	 *            主表名称
	 * @return 下一个分区表名称
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-2 下午4:11:32
	 */
	private String getNextPartition(String tableName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 2);
		String suffix = sdf.format(calendar.getTime());
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return tableName.toUpperCase() + suffix;
		} else if (Configuration.MYSQL.equals(dbName)) {
			return tableName.toLowerCase() + suffix;
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
	}

	@Override
	public T loadBySn(String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(clazz);
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.eq("standardNumber", standardNumber));
		}
		Object entity = criteria.uniqueResult();
		return (T) entity;
	}

	@Override
	public T loadBySNNoTransaction(String standardNumber) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(clazz);
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.eq("standardNumber", standardNumber));
		}
		try {
			Object entity = criteria.uniqueResult();
			return (T) entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<T> listInOrgan(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.createAlias("organ", "organ").add(
				Restrictions.like("organ.path", "%" + organId + "%"));
		return criteria.list();
	}

	@Override
	public T loadById(String id) {
		return (T) getSession().get(clazz, id);
	}
}
