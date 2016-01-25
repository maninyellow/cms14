package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasTslDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasTsl;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasTslDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-9-29 上午11:54:08
 */
public class DasTslDAOImpl extends BaseDAOImpl<DasTsl, String> implements
		DasTslDAO {
	@Override
	public void batchInsert(List<DasTsl> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_TSL);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasTsl> listTslInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_TSL, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasTsl> list = new LinkedList<DasTsl>();
		for (Object[] row : rows) {
			index = 0;
			DasTsl tsl = new DasTsl();
			tsl.setId(MyStringUtil.object2String(row[index++]));
			tsl.setStandardNumber(MyStringUtil.object2String(row[index++]));
			tsl.setRecTime((Timestamp) row[index++]);
			tsl.setGreenStatus(NumberUtil.getShort(row[index++]));
			tsl.setRedStatus(NumberUtil.getShort(row[index++]));
			tsl.setYellowStatus(NumberUtil.getShort(row[index++]));
			tsl.setTurnStatus(NumberUtil.getShort(row[index++]));
			tsl.setStatus(NumberUtil.getShort(row[index++]));
			tsl.setCommStatus(NumberUtil.getShort(row[index++]));
			tsl.setReserve(MyStringUtil.object2String(row[index++]));
			tsl.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(tsl);
		}
		return list;
	}
}
