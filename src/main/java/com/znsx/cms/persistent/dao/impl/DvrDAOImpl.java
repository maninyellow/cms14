package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.DvrDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 视频服务器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:40:01
 */
public class DvrDAOImpl extends BaseDAOImpl<Dvr, String> implements DvrDAO {
	/**
	 * 存储新增DVR的列表，直到调用excuteBatchDvr方法才批量写入数据库
	 */
	public static List<Dvr> vector = new Vector<Dvr>();

	@Override
	public List<Dvr> listDvr(String organId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public List<Dvr> listDvrByCcs(String ccsId, int start, int limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("ccs.id", ccsId));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countDvrByCcs(String ccsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("ccs.id", ccsId));
		criteria.setProjection(Projections.rowCount());
		Integer count = (Integer) criteria.uniqueResult();
		return count;
	}

	@Override
	public List<Dvr> listDvr(String organId, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer dvrTotalCount(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<Dvr> listDvrByDevice(String name, String standardNumber,
			String ip, Integer startIndex, Integer limit, String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Property.forName("organ.id").in(organIds));
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(ip)) {
			criteria.add(Restrictions.like("lanIp", "%" + ip + "%"));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Integer dvrTotalCount(String name, String standardNumber, String ip,
			String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.add(Property.forName("organ.id").in(organIds));
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(ip)) {
			criteria.add(Restrictions.like("lanIp", "%" + ip + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public Integer countByDeviceModel(String deviceModelId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("deviceModel.id", deviceModelId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countDvr(String organIds[]) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.setProjection(Projections.groupProperty("organ.id"));
		criteria.add(Property.forName("organ.id").in(organIds));
		List<String> list = criteria.list();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}

	@Override
	public void batchInsertDvr(Dvr record) {
		vector.add(record);
	}

	@Override
	public void excuteBatchDvr() {
		Connection conn = null;
		PreparedStatement psmtDvr = null;
		PreparedStatement psmtProperty = null;

		synchronized (vector) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtDvr = conn.prepareStatement(SqlFactory.getInstance()
						.insertDvr());
				psmtProperty = conn.prepareStatement(SqlFactory.getInstance()
						.insertDvrProperty());
				for (Dvr dvr : vector) {
					psmtDvr.setString(1, dvr.getId());
					psmtDvr.setString(2, dvr.getStandardNumber());
					psmtDvr.setString(3, TypeDefinition.DEVICE_TYPE_DVR + "");
					psmtDvr.setString(4, dvr.getSubType());
					psmtDvr.setString(5, dvr.getName());
					psmtDvr.setInt(6, dvr.getMaxConnect());
					if (dvr.getCcs() == null) {
						psmtDvr.setNull(7, Types.VARCHAR);
					} else {
						psmtDvr.setString(7, dvr.getCcs().getId());
					}
					if (dvr.getPts() == null) {
						psmtDvr.setNull(8, Types.VARCHAR);
					} else {
						psmtDvr.setString(8, dvr.getPts().getId());
					}
					psmtDvr.setString(9, System.currentTimeMillis() + "");
					psmtDvr.setInt(10, dvr.getChannelAmount());
					if (dvr.getOrgan() == null) {
						psmtDvr.setNull(11, Types.VARCHAR);
					} else {
						psmtDvr.setString(11, dvr.getOrgan().getId());
					}
					if (dvr.getManufacturer() == null) {
						psmtDvr.setNull(12, Types.VARCHAR);
					} else {
						psmtDvr.setString(12, dvr.getManufacturer().getId()
								.toString());
					}
					psmtDvr.setInt(13, 1);
					psmtDvr.setString(14, dvr.getLocation());
					psmtDvr.setString(15, dvr.getNote());
					if (dvr.getDeviceModel() == null) {
						psmtDvr.setNull(16, Types.VARCHAR);
					} else {
						psmtDvr.setString(16, dvr.getDeviceModel().getId()
								.toString());
					}
					psmtDvr.setString(17, dvr.getLanIp());
					psmtDvr.setString(18, dvr.getPort());
					psmtDvr.setString(19, dvr.getTransport());
					psmtDvr.setString(20, dvr.getMode());
					psmtDvr.addBatch();
					// 插入Property
					psmtProperty.setString(1, dvr.getId());
					psmtProperty.setString(2, dvr.getProperty().getUserName());
					psmtProperty.setString(3, dvr.getProperty().getPassword());
					psmtProperty.setInt(4, dvr.getProperty().getHeartCycle());
					psmtProperty.setString(5, dvr.getProperty().getProtocol());
					psmtProperty.setString(6, dvr.getProperty().getDecode());
					psmtProperty.addBatch();
				}
				psmtDvr.executeBatch();
				psmtProperty.executeBatch();
				conn.commit();
				vector.clear();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					if (conn != null) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						"Could not get database connnection");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (conn != null) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new BusinessException(ErrorCode.ERROR, e.getMessage());
			} finally {
				try {
					if (psmtDvr != null) {
						psmtDvr.close();
						psmtDvr = null;
					}
					if (psmtProperty != null) {
						psmtProperty.close();
						psmtProperty = null;
					}
					if (conn != null) {
						DataSourceUtils.releaseConnection(conn,
								SessionFactoryUtils
										.getDataSource(sessionFactory));
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(
							ErrorCode.DATABASE_ACCESS_ERROR,
							"Could not close database connnection");
				}
			}
		}
	}

	@Override
	public List<String> listDvrSN() {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listSNByDvr());
		List<String> list = query.list();
		return list;
	}

	@Override
	public List<String> listDvrName() {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listNameByDvr());
		List<String> list = query.list();
		return list;
	}

	@Override
	public Map<String, Dvr> mapBySns(Set<String> sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Dvr.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		List<Dvr> list = criteria.list();
		Map<String, Dvr> map = new HashMap<String, Dvr>();
		for (Dvr dvr : list) {
			map.put(dvr.getStandardNumber(), dvr);
		}
		return map;
	}
}
