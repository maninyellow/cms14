package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 火灾检测器数据库接口实现
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:08:29
 */
public class FireDetectorDAOImpl extends BaseDAOImpl<FireDetector, String>
		implements FireDetectorDAO {

	public static List<FireDetector> vector = new Vector<FireDetector>();

	@Override
	public Integer countFireDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<FireDetector> listFireDetector(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Map<String, FireDetector> mapFDByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.setCacheable(false);
		criteria.setCacheMode(CacheMode.IGNORE);
		criteria.addOrder(Order.desc("createTime"));
		List<FireDetector> list = criteria.list();
		Map<String, FireDetector> map = new LinkedHashMap<String, FireDetector>();
		for (FireDetector fd : list) {
			map.put(fd.getId().toString(), fd);
		}
		return map;
	}

	@Override
	public Map<String, FireDetector> mapFDBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<FireDetector> list = criteria.list();
		Map<String, FireDetector> map = new LinkedHashMap<String, FireDetector>();
		for (FireDetector fd : list) {
			map.put(fd.getStandardNumber(), fd);
		}
		return map;
	}

	@Override
	public void deleteRoleFDPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_FD);
		q.executeUpdate();

	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countFD(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(FireDetector.class);
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
	public void batchInsert(List<FireDetector> fds) {
		Session session = getSession();
		super.batchInsert(fds, session);
	}

	@Override
	public void batchInsertFd(FireDetector fireDetector) {
		vector.add(fireDetector);

	}

	@Override
	public void excuteBatchFd() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vector) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertFd());
				for (FireDetector entity : vector) {
					psmtItem.setString(1, entity.getId());
					psmtItem.setString(2, entity.getName());
					psmtItem.setString(3, entity.getOrgan().getId());
					psmtItem.setString(4, entity.getStandardNumber());
					psmtItem.setString(5, entity.getDas().getId());
					psmtItem.setInt(6, entity.getPeriod());
					psmtItem.setString(7, entity.getNavigation());
					psmtItem.setString(8, entity.getStakeNumber());
					psmtItem.setString(9, entity.getIp());
					psmtItem.setInt(10, entity.getPort());
					psmtItem.setString(11, System.currentTimeMillis() + "");
					psmtItem.setString(12, entity.getLongitude());
					psmtItem.setString(13, entity.getLatitude());
					psmtItem.setString(14, entity.getReserve());
					psmtItem.setString(15, entity.getNote());
					psmtItem.addBatch();
				}
				psmtItem.executeBatch();
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
					if (psmtItem != null) {
						psmtItem.close();
						psmtItem = null;
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
}
