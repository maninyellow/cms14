package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasLilDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasLil;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasLilDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-9-29 下午1:34:20
 */
public class DasLilDAOImpl extends BaseDAOImpl<DasLil, String> implements
		DasLilDAO {
	@Override
	public void batchInsert(List<DasLil> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_LIL);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasLil> listLilInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_LIL, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasLil> list = new LinkedList<DasLil>();
		for (Object[] row : rows) {
			index = 0;
			DasLil lil = new DasLil();
			lil.setId(MyStringUtil.object2String(row[index++]));
			lil.setStandardNumber(MyStringUtil.object2String(row[index++]));
			lil.setRecTime((Timestamp) row[index++]);
			lil.setBackArrowStatus(NumberUtil.getShort(row[index++]));
			lil.setBackXStatus(NumberUtil.getShort(row[index++]));
			lil.setFrontArrowStatus(NumberUtil.getShort(row[index++]));
			lil.setFrontXStatus(NumberUtil.getShort(row[index++]));
			lil.setStatus(NumberUtil.getShort(row[index++]));
			lil.setCommStatus(NumberUtil.getShort(row[index++]));
			lil.setReserve(MyStringUtil.object2String(row[index++]));
			lil.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(lil);
		}
		return list;
	}
}
