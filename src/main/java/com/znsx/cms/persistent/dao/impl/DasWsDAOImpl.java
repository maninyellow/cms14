package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasWsDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasWs;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.WsVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasWsDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午10:01:07
 */
public class DasWsDAOImpl extends BaseDAOImpl<DasWs, String> implements
		DasWsDAO {
	@Override
	public void batchInsert(List<DasWs> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_WS);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasWs> listWsInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_WS, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasWs> list = new LinkedList<DasWs>();
		for (Object[] row : rows) {
			index = 0;
			DasWs ws = new DasWs();
			ws.setId(MyStringUtil.object2String(row[index++]));
			ws.setStandardNumber(MyStringUtil.object2String(row[index++]));
			ws.setRecTime((Timestamp) row[index++]);
			ws.setDirection(NumberUtil.getShort(row[index++]));
			ws.setSpeed(NumberUtil.getInteger(row[index++]));
			ws.setStatus(NumberUtil.getShort(row[index++]));
			ws.setCommStatus(NumberUtil.getShort(row[index++]));
			ws.setReserve(MyStringUtil.object2String(row[index++]));
			ws.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(ws);
		}
		return list;
	}

	@Override
	public List<WsVO> statWS(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getWSStat(sns, organSN, scope));
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
		List<WsVO> list = new LinkedList<WsVO>();
		for (Object[] row : rows) {
			index = 0;
			WsVO vo = new WsVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setSpeedMax(MyStringUtil.cutObject(row[index++],2));
			vo.setSpeedMin(MyStringUtil.cutObject(row[index++],2));
			vo.setSpeedAvg(MyStringUtil.cutObject(row[index++],2));
			Float direction = NumberUtil.getFloat(row[index++]);
			if (direction.floatValue() <= 0.66) {
				vo.setDirection(TypeDefinition.WS_DIRECTION_0);
			} else if (direction.floatValue() <= 1.33) {
				vo.setDirection(TypeDefinition.WS_DIRECTION_1);
			} else {
				vo.setDirection(TypeDefinition.WS_DIRECTION_2);
			}
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countWS(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countWSStat(sns, organSN, scope));
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
}
