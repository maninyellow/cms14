package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 手动报警按钮据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:13:57
 */
public class PushButtonDAOImpl extends BaseDAOImpl<PushButton, String>
		implements PushButtonDAO {

	public static List<PushButton> vector = new Vector<PushButton>();

	@Override
	public Map<String, PushButton> mapPBByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<PushButton> list = criteria.list();
		Map<String, PushButton> map = new LinkedHashMap<String, PushButton>();
		for (PushButton pb : list) {
			map.put(pb.getId().toString(), pb);
		}
		return map;
	}

	@Override
	public Integer countPushButton(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);

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
	public List<PushButton> listPushButton(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);

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
	public Map<String, PushButton> mapPBBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<PushButton> list = criteria.list();
		Map<String, PushButton> map = new LinkedHashMap<String, PushButton>();
		for (PushButton pb : list) {
			map.put(pb.getStandardNumber(), pb);
		}
		return map;
	}

	@Override
	public void deleteRolePBPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_PB);
		q.executeUpdate();

	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countPB(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
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
	public List<PushButton> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(PushButton.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public void batchInsert(List<PushButton> pbs) {
		Session session = getSession();
		batchInsert(pbs, session);
	}

	@Override
	public void batchInsertPb(PushButton pushButton) {
		vector.add(pushButton);
	}

	@Override
	public void excuteBatchPb() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vector) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertPb());
				for (PushButton entity : vector) {
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
