package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasControlDeviceDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasControlDevice;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasControlDeviceDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:57:03
 */
public class DasControlDeviceDAOImpl extends
		BaseDAOImpl<DasControlDevice, String> implements DasControlDeviceDAO {
	@Override
	public void batchInsert(List<DasControlDevice> list)
			throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_CD);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasControlDevice> listCdInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_CD, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasControlDevice> list = new LinkedList<DasControlDevice>();
		for (Object[] row : rows) {
			index = 0;
			DasControlDevice cd = new DasControlDevice();
			cd.setId(MyStringUtil.object2String(row[index++]));
			cd.setStandardNumber(MyStringUtil.object2String(row[index++]));
			cd.setRecTime((Timestamp) row[index++]);
			cd.setType(NumberUtil.getInteger(row[index++]));
			cd.setWorkState(MyStringUtil.object2String(row[index++]));
			cd.setStatus(NumberUtil.getShort(row[index++]));
			cd.setCommStatus(NumberUtil.getShort(row[index++]));
			cd.setReserve(MyStringUtil.object2String(row[index++]));
			cd.setOrgan(MyStringUtil.object2String(row[index++]));
			list.add(cd);
		}
		return list;
	}
}
