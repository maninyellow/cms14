package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasCoviDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.CoviVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasCoviDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:57:49
 */
public class DasCoviDAOImpl extends BaseDAOImpl<DasCovi, String> implements
		DasCoviDAO {
	@Override
	public void batchInsert(List<DasCovi> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_COVI);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasCovi> listCoviInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_COVI, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasCovi> list = new LinkedList<DasCovi>();
		for (Object[] row : rows) {
			index = 0;
			DasCovi covi = new DasCovi();
			covi.setId(MyStringUtil.object2String(row[index++]));
			covi.setStandardNumber(MyStringUtil.object2String(row[index++]));
			covi.setRecTime((Timestamp) row[index++]);
			covi.setCo(MyStringUtil.object2String(row[index++]));
			covi.setVi(MyStringUtil.object2String(row[index++]));
			covi.setStatus(NumberUtil.getShort(row[index++]));
			covi.setCommStatus(NumberUtil.getShort(row[index++]));
			covi.setReserve(MyStringUtil.object2String(row[index++]));
			covi.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(covi);
		}
		return list;
	}

	@Override
	public List<CoviVO> statCovi(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getCoviStat(sns, organSN, scope));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		for (int i = 0; i < sns.length; i++) {
			query.setString(index++, sns[i]);
		}
		if (StringUtils.isNotBlank(organSN)) {
			query.setString(index++, organSN);
		}
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(index++, start);
			query.setInteger(index++, start + limit);
		} else if (Configuration.MYSQL.equals(dbName)) {
			query.setInteger(index++, start);
			query.setInteger(index++, limit);
		} else {
			query.setInteger(index++, start);
			query.setInteger(index++, limit);
		}
		List<Object[]> rows = query.list();
		List<CoviVO> list = new LinkedList<CoviVO>();
		for (Object[] row : rows) {
			index = 0;
			CoviVO vo = new CoviVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setCoMax(MyStringUtil.cutObject(row[index++], 2));
			vo.setCoMin(MyStringUtil.cutObject(row[index++], 2));
			vo.setCoAvg(MyStringUtil.cutObject(row[index++], 2));
			vo.setViMax(MyStringUtil.cutObject(row[index++], 2));
			vo.setViMin(MyStringUtil.cutObject(row[index++], 2));
			vo.setViAvg(MyStringUtil.cutObject(row[index++], 2));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countCovi(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countCoviStat(sns, organSN, scope));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		for (int i = 0; i < sns.length; i++) {
			query.setString(index++, sns[i]);
		}
		if (StringUtils.isNotBlank(organSN)) {
			query.setString(index++, organSN);
		}
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<DasCovi> listDasCovi(Timestamp begin, Timestamp end, int start,
			int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasCovi.class);
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
