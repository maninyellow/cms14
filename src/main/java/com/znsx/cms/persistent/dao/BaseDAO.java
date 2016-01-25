package com.znsx.cms.persistent.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.znsx.cms.service.exception.BusinessException;

/**
 * Dao接口类
 * 
 * @author zhuanqi
 * 
 *         Mar 14, 2012 1:56:15 PM
 */
public interface BaseDAO<T, PK extends Serializable> {

	public void save(T entity);

	public void saveDas(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void deleteDas(T entity);

	/**
	 * 单值检索
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * @author
	 */
	public T findUniResult(String hql, Object... params);

	public void deleteById(PK id);

	public T findById(PK id);

	public T findByIdDas(PK id);

	public T loadEntity(PK id);

	public void saveorupdate(T entity);

	public List<T> findAll();

	public List<T> findDasAll();

	public List<T> findByHql(String hql, int start, int limit, Object... params);

	public List<T> findByHql(String hql, Object... params);

	public Integer getTotalCount();

	public int getTotalCountBySql(String sql);

	public Integer getTotalCount(String hql, Object... params);

	public List<T> findByPropertys(LinkedHashMap<String, Object> params);

	public List<T> findByPropertysDas(LinkedHashMap<String, Object> params);

	/**
	 * 批量更新
	 * 
	 * @param hql
	 * @param params
	 * @author
	 */
	public void batchUpdate(String hql, Object... params);

	/**
	 * 提供直接执行sql语句查询(为了解决复杂查询所带来的性能问题) 当pageSize<0时讲忽略参数 pageNo和pageSize clazz
	 * 返回的实体类型
	 * 
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @param clazz
	 * @param params
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List readBySql(String sql, int start, int pageSize, Class clazz,
			Object... params);

	/**
	 * 提供sql更新语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @author
	 */
	public void writeBySql(String sql, Object... params);

	/**
	 * 提供直接执行sql语句查询(支持可选字段查询)
	 * 
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @param clazz
	 * @param columnMap
	 * @param params
	 * @return
	 * @author
	 */
	@SuppressWarnings("unchecked")
	public List readAnyColumnBySql(String sql, int start, int pageSize,
			Class clazz, Map columnMap, Object... params);

	/**
	 * xp
	 * 
	 * @desc 提供最原始的SQL语句查询,确定返回对象为一条记录时
	 * @param sql
	 * @return
	 */
	public Object readBySql(String sql);

	/**
	 * xp
	 * 
	 * @desc 根据SQL查询,不分页
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List readBySql(String sql, Class clazz, Object... params);

	public void evict(T entity);

	public void refresh(T entity);

	/**
	 * 获取指定表的下一个ID值
	 * 
	 * @param Hibernate配置中Class的名称
	 * @param 增量数值
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:17:48
	 * @return 指定表的下一个ID值
	 */
	public String getNextId(String className, int increment);

	/**
	 * 获取指定对象的下一个标准号序列值
	 * 
	 * @param className
	 *            Hibernate配置中Class的名称
	 * @param increment
	 *            增量数值
	 * @return 指定对象的下一个标准号序列值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:32:07
	 */
	public int getStandardNumber(String className, int increment);

	/**
	 * 
	 * 根据类名和标准号查询CcsId
	 * 
	 * @param className
	 *            实体类名
	 * @param standardNumber
	 *            标准号
	 * @return String
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:16:00
	 */
	public String getCcsId(String className, String standardNumber);

	/**
	 * 保存地图标注坐标
	 * 
	 * @param className
	 *            实体类名
	 * @param standardNumber
	 *            标准号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-22 下午3:29:52
	 */
	public void saveLonlat(String className, String standardNumber,
			String longitude, String latitude);

	/**
	 * 根据名称查询实体对象
	 * 
	 * @param name
	 *            名称
	 * @return 对象实体
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-18 上午10:13:52
	 */
	public List<T> getEntityByName(String name);

	/**
	 * 跟据标准号查询实体对象
	 * 
	 * @param standardNumber
	 *            标准号
	 * @return 对象实体
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-7 上午9:37:17
	 */
	public T findBySN(String standardNumber) throws BusinessException;

	/**
	 * Hibernate批量插入
	 * 
	 * @param list
	 *            要插入的对象列表
	 * @param session
	 *            hibernate session对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午10:31:33
	 */
	public void batchInsert(List<T> list, Session session)
			throws BusinessException;

	public T findBySNDas(String standardNumber) throws BusinessException;

	public void updateDas(T entity);

	/**
	 * 根据SN查询对象，未找到返回空，不抛出异常
	 * 
	 * @param standardNumber
	 *            对象标准号
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-30 上午10:36:18
	 */
	public T loadBySn(String standardNumber);

	/**
	 * 根据SN查询对象，未找到返回空，不抛出异常,非事物查询
	 * 
	 * @param standardNumber
	 *            对象标准号
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-7-8 上午11:39:37
	 */
	public T loadBySNNoTransaction(String standardNumber);

	/**
	 * 查询机构及所有子机构下的资源列表
	 * 
	 * @param organId
	 *            父机构ID
	 * @return 机构及所有子机构下的资源列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-30 上午11:16:23
	 */
	public List<T> listInOrgan(String organId);

	/**
	 * 根据Id查询对象，未找到返回空，不抛出异常
	 * 
	 * @param id
	 *            对象ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-12 下午4:28:39
	 */
	public T loadById(String id);

}
