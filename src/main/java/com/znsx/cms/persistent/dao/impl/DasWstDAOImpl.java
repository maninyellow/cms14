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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasWstDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.WstVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasWstDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午10:01:46
 */
public class DasWstDAOImpl extends BaseDAOImpl<DasWst, String> implements
		DasWstDAO {
	@Override
	public void batchInsert(List<DasWst> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_WST);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasWst> listWstInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_WST, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasWst> list = new LinkedList<DasWst>();
		for (Object[] row : rows) {
			index = 0;
			DasWst wst = new DasWst();
			wst.setId(MyStringUtil.object2String(row[index++]));
			wst.setStandardNumber(MyStringUtil.object2String(row[index++]));
			wst.setRecTime((Timestamp) row[index++]);
			wst.setVisMax(NumberUtil.getInteger(row[index++]));
			wst.setVisMin(NumberUtil.getInteger(row[index++]));
			wst.setVisAvg(NumberUtil.getInteger(row[index++]));
			wst.setWsMax(MyStringUtil.object2String(row[index++]));
			wst.setWsMin(MyStringUtil.object2String(row[index++]));
			wst.setWsAvg(MyStringUtil.object2String(row[index++]));
			wst.setWindDir(NumberUtil.getInteger(row[index++]));
			wst.setAirTempMax(MyStringUtil.object2String(row[index++]));
			wst.setAirTempMin(MyStringUtil.object2String(row[index++]));
			wst.setAirTempAvg(MyStringUtil.object2String(row[index++]));
			wst.setRoadTempMax(MyStringUtil.object2String(row[index++]));
			wst.setRoadTempMin(MyStringUtil.object2String(row[index++]));
			wst.setRoadTempAvg(MyStringUtil.object2String(row[index++]));
			wst.setHumiMax(NumberUtil.getShort(row[index++]));
			wst.setHumiMin(NumberUtil.getShort(row[index++]));
			wst.setHumiAvg(MyStringUtil.object2String(row[index++]));
			wst.setRainVol(MyStringUtil.object2String(row[index++]));
			wst.setSnowVol(MyStringUtil.object2String(row[index++]));
			wst.setRoadSurface(NumberUtil.getShort(row[index++]));
			wst.setStatus(NumberUtil.getShort(row[index++]));
			wst.setCommStatus(NumberUtil.getShort(row[index++]));
			wst.setReserve(MyStringUtil.object2String(row[index++]));
			wst.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(wst);
		}
		return list;
	}

	@Override
	public List<WstVO> statWST(Timestamp beginTime, Timestamp endTime,
			String scope, String standardNumber, String organSN, int start,
			int limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getWSTStat(standardNumber, organSN, scope));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (StringUtils.isNotBlank(standardNumber)) {
			query.setString(index++, standardNumber);
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
		List<WstVO> list = new LinkedList<WstVO>();
		for (Object[] row : rows) {
			index = 0;
			WstVO vo = new WstVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setVi(MyStringUtil.object2String(row[index++]));
			vo.setAirTemp(MyStringUtil.object2String(row[index++]));
			vo.setHumi(MyStringUtil.object2String(row[index++]));
			Float direction = NumberUtil.getFloat(row[index++]);
			if (null != direction) {
				vo.setDirection(Math.round(direction) + "");
			}
			vo.setWindSpeed(MyStringUtil.object2String(row[index++]));
			vo.setRoadTemp(MyStringUtil.object2String(row[index++]));
			Float roadSurface = NumberUtil.getFloat(row[index++]);
			if (null != roadSurface) {
				vo.setRoadSurface(Math.round(roadSurface) + "");
			}
			vo.setPressure(MyStringUtil.object2String(row[index++]));
			vo.setRainVol(MyStringUtil.object2String(row[index++]));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countWST(Timestamp beginTime, Timestamp endTime, String scope,
			String standardNumber, String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countWSTStat(standardNumber, organSN, scope));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (StringUtils.isNotBlank(standardNumber)) {
			query.setString(index++, standardNumber);
		}
		if (StringUtils.isNotBlank(organSN)) {
			query.setString(index++, organSN);
		}
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<WstVO> listWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[], int start, int limit) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasWst.class);
		criteria.add(Restrictions.between("recTime", beginTime, endTime));
		if (StringUtils.isNotBlank(organSN)) {
			criteria.add(Restrictions.eq("organ", organSN));
		}
		if (sns.length > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
		}
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.desc("recTime"));
		List<DasWst> list = criteria.list();
		List<WstVO> rtn = new LinkedList<WstVO>();
		for (DasWst wst : list) {
			WstVO vo = new WstVO();
			vo.setAirTemp(wst.getAirTempAvg());
			vo.setDirection(MyStringUtil.object2StringNotNull(wst.getWindDir()));
			vo.setHumi(MyStringUtil.object2StringNotNull(wst.getHumiAvg()));
			vo.setOrganName(wst.getOrgan());
			vo.setPressure("");
			vo.setRainVol(wst.getRainVol());
			vo.setRecTime(MyStringUtil.timeToString(wst.getRecTime()));
			vo.setRoadSurface(MyStringUtil.object2StringNotNull(wst
					.getRoadSurface()));
			vo.setRoadTemp(wst.getRoadTempAvg());
			vo.setStandardNumber(wst.getStandardNumber());
			vo.setVi(MyStringUtil.object2StringNotNull(wst.getVisAvg()));
			vo.setWindSpeed(wst.getWsAvg());
			rtn.add(vo);
		}
		return rtn;
	}

	@Override
	public int countWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[]) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasWst.class);
		criteria.add(Restrictions.between("recTime", beginTime, endTime));
		if (StringUtils.isNotBlank(organSN)) {
			criteria.add(Restrictions.eq("organ", organSN));
		}
		if (sns.length > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
		}
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<DasWst> listDasWst(Timestamp begin, Timestamp end, int start,
			int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasWst.class);
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
