package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasCmsDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasCms;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasCmsDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:55:33
 */
public class DasCmsDAOImpl extends BaseDAOImpl<DasCms, String> implements
		DasCmsDAO {
	@Override
	public void batchInsert(List<DasCms> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_CMS);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasCms> listCmsInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_CMS, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasCms> list = new LinkedList<DasCms>();
		for (Object[] row : rows) {
			index = 0;
			DasCms cms = new DasCms();
			cms.setId(MyStringUtil.object2String(row[index++]));
			cms.setStandardNumber(MyStringUtil.object2String(row[index++]));
			cms.setRecTime((Timestamp) row[index++]);
			cms.setDispCont(MyStringUtil.object2String(row[index++]));
			cms.setDispTime(NumberUtil.getInteger(row[index++]));
			cms.setColor(MyStringUtil.object2String(row[index++]));
			cms.setSize(NumberUtil.getShort(row[index++]));
			cms.setFont(MyStringUtil.object2String(row[index++]));
			cms.setStatus(NumberUtil.getShort(row[index++]));
			cms.setCommStatus(NumberUtil.getShort(row[index++]));
			cms.setReserve(MyStringUtil.object2String(row[index++]));
			cms.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(cms);
		}
		return list;
	}
}
