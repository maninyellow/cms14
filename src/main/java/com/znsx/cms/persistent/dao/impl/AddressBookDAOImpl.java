package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.AddressBookDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.AddressBook;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * AddressBookDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:20:46
 */
public class AddressBookDAOImpl extends BaseDAOImpl<AddressBook, String>
		implements AddressBookDAO {
	/**
	 * 存储新增通讯录的列表，直到调用excuteBatchAddressBook方法才批量写入数据库
	 */
	public static List<AddressBook> vector = new Vector<AddressBook>();

	@Override
	public Integer addressBookTotalCount(String linkMan, String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(AddressBook.class);
		if (StringUtils.isNotBlank(linkMan)) {
			criteria.add(Restrictions.like("linkMan", "%" + linkMan + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public List<AddressBook> listAddressBook(String linkMan, String organId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(AddressBook.class);
		if (StringUtils.isNotBlank(linkMan)) {
			criteria.add(Restrictions.like("linkMan", "%" + linkMan + "%"));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public void batchInsertAddressBook(AddressBook ab) {
		vector.add(ab);

	}

	@Override
	public void excuteBatchAddressBook() {
		Connection conn = null;
		PreparedStatement psmt = null;

		synchronized (vector) {

			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(SqlFactory.getInstance()
						.insertAddressBook());
				for (AddressBook ab : vector) {
					psmt.setString(1, (String) new UUIDHexGenerator().generate(
							null, null));
					psmt.setString(2, ab.getLinkMan());
					psmt.setString(3, ab.getPhone());
					psmt.setString(4, ab.getSex());
					psmt.setString(5, ab.getAddress());
					psmt.setString(6, ab.getEmail());
					psmt.setString(7, ab.getFax());
					psmt.setString(8, ab.getPosition());
					psmt.setString(9, ab.getOrgan().getId());
					psmt.setString(10, ab.getNote());
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

}
