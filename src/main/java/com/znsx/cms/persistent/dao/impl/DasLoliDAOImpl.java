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

import com.znsx.cms.persistent.dao.DasLoliDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.LoLiVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasLoliDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:59:08
 */
public class DasLoliDAOImpl extends BaseDAOImpl<DasLoli, String> implements
		DasLoliDAO {
	@Override
	public void batchInsert(List<DasLoli> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_LOLI);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasLoli> listLoliInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_LOLI, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasLoli> list = new LinkedList<DasLoli>();
		for (Object[] row : rows) {
			index = 0;
			DasLoli loli = new DasLoli();
			loli.setId(MyStringUtil.object2String(row[index++]));
			loli.setStandardNumber(MyStringUtil.object2String(row[index++]));
			loli.setRecTime((Timestamp) row[index++]);
			loli.setLo(MyStringUtil.object2String(row[index++]));
			loli.setLi(MyStringUtil.object2String(row[index++]));
			loli.setStatus(NumberUtil.getShort(row[index++]));
			loli.setCommStatus(NumberUtil.getShort(row[index++]));
			loli.setReserve(MyStringUtil.object2String(row[index++]));
			loli.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(loli);
		}
		return list;
	}

	@Override
	public List<LoLiVO> statLoLi(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getLoLiStat(sns, organSN, scope));
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
		List<LoLiVO> list = new LinkedList<LoLiVO>();
		for (Object[] row : rows) {
			index = 0;
			LoLiVO vo = new LoLiVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setCd(MyStringUtil.cutObject(row[index++], 2));
			vo.setLx(MyStringUtil.cutObject(row[index++], 2));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countLoLi(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countLoLiStat(sns, organSN, scope));
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
	public List<DasLoli> listDasLoli(Timestamp begin, Timestamp end, int start,
			int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasLoli.class);
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
