package com.znsx.cms.persistent.dao.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.DasDeviceStatusRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasDeviceStatusReal;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * DasDeviceStatusRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:19:37
 */
public class DasDeviceStatusRealDAOImpl extends
		BaseDAOImpl<DasDeviceStatusReal, String> implements
		DasDeviceStatusRealDAO {

	@Override
	public void deleteAll(String table) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance().delete(
				table));
		query.executeUpdate();
	}

	@Override
	public void batchInsert(List<DasDeviceStatusReal> list)
			throws BusinessException {
		// checkTablePartition(TypeDefinition.TABLE_NAME_DEVICE_STATUS_REAL);
		Session session = getSessionDas();
		batchInsert(list, session);

	}

	@Override
	public void deleteBySN(String standardNumber) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteDeviceStatusReal(standardNumber));
		if (StringUtils.isNotBlank(standardNumber)) {
			query.setString(0, standardNumber);
		}
		query.executeUpdate();
	}

	@Override
	public List<DasDeviceStatusReal> listDeviceStatusRealNoTransaction(
			Collection<String> sns) {
		Session session = SessionFactoryUtils.getSession(sessionFactoryDAS,
				true);
		Criteria criteria = session.createCriteria(DasDeviceStatusReal.class);
		if (sns.size() > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
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
		return new LinkedList<DasDeviceStatusReal>();
	}

}
