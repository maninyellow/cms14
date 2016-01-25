package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 标准号数据库接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-23 下午3:41:32
 */
public class StandardNumberDAOImpl extends BaseDAOImpl<StandardNumber, String>
		implements StandardNumberDAO {
	@Override
	public StandardNumber getBySN(String sn) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(StandardNumber.class);
		criteria.add(Restrictions.eq("sn", sn));
		List<StandardNumber> list = criteria.list();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void batchInsert(StandardNumber record) {
		vector.add(record);
	}

	@Override
	public void excuteBatch() {
		Connection conn = null;
		PreparedStatement psmt = null;

		synchronized (vector) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(SqlFactory.getInstance()
						.insertSN());
				for (StandardNumber sn : vector) {
					psmt.setString(1, (String) new UUIDHexGenerator().generate(
							null, null));
					psmt.setString(2, sn.getSn());
					psmt.setString(3, sn.getClassType());
					psmt.addBatch();
				}
				psmt.executeBatch();
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
					if (psmt != null) {
						psmt.close();
						psmt = null;
					}
					if (conn != null) {
						DataSourceUtils.releaseConnection(conn,
								SessionFactoryUtils
										.getDataSource(sessionFactory));
						// conn.close();
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
	public int countDeviceAmount() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(StandardNumber.class);
		criteria.add(Restrictions.in("classType", new String[] {
				TypeDefinition.RESOURCE_TYPE_CD,
				TypeDefinition.RESOURCE_TYPE_COVI,
				TypeDefinition.RESOURCE_TYPE_FD,
				TypeDefinition.RESOURCE_TYPE_LOLI,
				TypeDefinition.RESOURCE_TYPE_NOD,
				TypeDefinition.RESOURCE_TYPE_PB,
				TypeDefinition.RESOURCE_TYPE_VD,
				TypeDefinition.RESOURCE_TYPE_WS,
				TypeDefinition.RESOURCE_TYPE_WST,
				TypeDefinition.RESOURCE_TYPE_BT,
				TypeDefinition.RESOURCE_TYPE_VI_DETECTOR,
				TypeDefinition.RESOURCE_TYPE_GPS}));
		criteria.setProjection(Projections.rowCount());
		Integer totalCount = (Integer) criteria.uniqueResult();
		return totalCount.intValue();
	}
}
