package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasNodDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasNodDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:59:52
 */
public class DasNodDAOImpl extends BaseDAOImpl<DasNod, String> implements
		DasNodDAO {
	@Override
	public void batchInsert(List<DasNod> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_NOD);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasNod> listNodInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_NOD, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasNod> list = new LinkedList<DasNod>();
		for (Object[] row : rows) {
			index = 0;
			DasNod nod = new DasNod();
			nod.setId(MyStringUtil.object2String(row[index++]));
			nod.setStandardNumber(MyStringUtil.object2String(row[index++]));
			nod.setRecTime((Timestamp) row[index++]);
			nod.setNo(MyStringUtil.object2String(row[index++]));
			nod.setNo2(MyStringUtil.object2String(row[index++]));
			nod.setStatus(NumberUtil.getShort(row[index++]));
			nod.setCommStatus(NumberUtil.getShort(row[index++]));
			nod.setReserve(MyStringUtil.object2String(row[index++]));
			nod.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(nod);
		}
		return list;
	}

	@Override
	public List<DasNod> listDasNo(Timestamp begin, Timestamp end, int start,
			int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasNod.class);
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
