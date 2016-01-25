package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.PolicyDeviceDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.PolicyDevice;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.LightVO;

/**
 * 策略与设备的关联数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:38:28
 */
public class PolicyDeviceDAOImpl extends BaseDAOImpl<PolicyDevice, String>
		implements PolicyDeviceDAO {

	public void batchInsert(PolicyDevice record) throws BusinessException {
		vector.add(record);
	}

	public void excuteBatch() throws BusinessException {
		Connection conn = null;
		PreparedStatement psmt = null;

		synchronized (vector) {

			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(SqlFactory.getInstance()
						.insertPolicyDevice());
				for (PolicyDevice pd : vector) {
					psmt.setString(1, (String) new UUIDHexGenerator().generate(
							null, null));
					psmt.setString(2, pd.getPolicyId());
					psmt.setString(3, pd.getDeviceId());
					psmt.setShort(4, pd.getType());
					psmt.setShort(5, pd.getStatus());
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

	public void deleteByPolicy(String policyId) throws BusinessException {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.deletePolicyDeviceByPolicy());
		query.setString(0, policyId);
		query.executeUpdate();
	}

	public List<LightVO> listByPolicy(String policyId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listPolicyDevice());
		query.setString(0, policyId);
		List<Object[]> rows = query.list();
		List<LightVO> list = new LinkedList<LightVO>();
		for (Object[] row : rows) {
			LightVO vo = new LightVO();
			vo.setId(row[0].toString());
			vo.setName(row[1].toString());
			vo.setStandardNumber(row[2] == null ? "" : row[2].toString());
			vo.setStatus(row[3].toString());
			list.add(vo);
		}
		return list;
	}
}
