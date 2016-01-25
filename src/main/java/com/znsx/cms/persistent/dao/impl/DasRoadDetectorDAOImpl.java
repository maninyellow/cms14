package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasRoadDetectorDAO;
import com.znsx.cms.persistent.model.DasRoadDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * DasRoadDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 上午10:08:59
 */
public class DasRoadDetectorDAOImpl extends
		BaseDAOImpl<DasRoadDetector, String> implements DasRoadDetectorDAO {

	@Override
	public void batchInsert(List<DasRoadDetector> list) {
		checkTablePartition(TypeDefinition.TABLE_NAME_RD);
		Session session = getSessionDas();
		batchInsert(list, session);

	}

	@Override
	public List<DasRoadDetector> listDasRsd(Timestamp begin, Timestamp end,
			int start, int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasRoadDetector.class);
			criteria.add(Restrictions.gt("recTime", begin));
			criteria.add(Restrictions.le("recTime", end));
			criteria.add(Restrictions.eq("commStatus", Short.valueOf("0")));
			criteria.add(Restrictions.eq("status", Short.valueOf("0")));
			criteria.setFirstResult(start);
			criteria.setMaxResults(limit);
			criteria.addOrder(Order.asc("recTime"));
			return criteria.list();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}
}
