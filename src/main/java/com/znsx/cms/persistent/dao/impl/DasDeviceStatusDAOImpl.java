package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.DasDeviceStatusDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasDeviceStatus;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.DeviceStatusVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.string.MyStringUtil;

/**
 * DasDeviceStatusDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:58:31
 */
public class DasDeviceStatusDAOImpl extends
		BaseDAOImpl<DasDeviceStatus, String> implements DasDeviceStatusDAO {
	@Override
	public void batchInsert(List<DasDeviceStatus> list)
			throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_DEVICE_STATUS);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DeviceStatusVO> statDeviceStatus(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[], String organSN,
			int start, int limit) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.statDeviceStatus(type, sns, organSN));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (null != type) {
			query.setInteger(index++, type.intValue());
		}
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
		List<DeviceStatusVO> list = new LinkedList<DeviceStatusVO>();
		for (Object[] row : rows) {
			index = 1;
			DeviceStatusVO vo = new DeviceStatusVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(sdf.format((Timestamp) row[index++]));
			vo.setType(MyStringUtil.object2String(row[index++]));
			vo.setWorkStatus(MyStringUtil.object2String(row[index++]));
			vo.setCommStatus(MyStringUtil.object2String(row[index++]));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countDeviceStatus(Timestamp beginTime, Timestamp endTime,
			Integer type, String sns[], String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countDeviceStatus(type, sns, organSN));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (null != type) {
			query.setInteger(index++, type.intValue());
		}
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
	public int countTotalDeviceStatus(String sns[], Timestamp beginTime,
			Timestamp endTime, Integer type) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countTotalDeviceStatus(type, sns));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (null != type) {
			query.setInteger(index++, type.intValue());
		}
		for (int i = 0; i < sns.length; i++) {
			query.setString(index++, sns[i]);
		}
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<DeviceStatusVO> deviceStatusStat(Timestamp beginTime,
			Timestamp endTime, Integer type, String sns[], String organSN,
			Integer startIndex, Integer limit) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.statDeviceStatus1(type, sns, organSN));
		int index = 0;
		query.setTimestamp(index++, beginTime);
		query.setTimestamp(index++, endTime);
		if (null != type) {
			query.setInteger(index++, type.intValue());
		}
		for (int i = 0; i < sns.length; i++) {
			query.setString(index++, sns[i]);
		}
		if (StringUtils.isNotBlank(organSN)) {
			query.setString(index++, organSN);
		}
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(index++, startIndex);
			query.setInteger(index++, startIndex + limit);
		} else if (Configuration.MYSQL.equals(dbName)) {
			query.setInteger(index++, startIndex);
			query.setInteger(index++, limit);
		} else {
			query.setInteger(index++, startIndex);
			query.setInteger(index++, limit);
		}
		List<Object[]> rows = query.list();
		List<DeviceStatusVO> list = new LinkedList<DeviceStatusVO>();
		for (Object[] row : rows) {
			index = 0;
			DeviceStatusVO vo = new DeviceStatusVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setType(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(sdf.format((Timestamp) row[index++]));
			vo.setWorkStatus(MyStringUtil.object2String(row[index++]));
			vo.setCommStatus(MyStringUtil.object2String(row[index++]));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}
}
