package com.znsx.cms.persistent.dao.impl;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.TimePolicyDAO;
import com.znsx.cms.persistent.model.TimePolicy;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.TimePolicyItemVO;

/**
 * TimePolicyDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午4:04:51
 */
public class TimePolicyDAOImpl extends BaseDAOImpl<TimePolicy, String>
		implements TimePolicyDAO {
	public void deleteByTimePolicy(String timePolicyId)
			throws BusinessException {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.deleteTimePolicy());
		query.setString(0, timePolicyId);
		query.executeUpdate();
	}

	public List<TimePolicyItemVO> listByTimePolicy(String timePolicyId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listTimePolicyItem());
		query.setString(0, timePolicyId);
		List<Object[]> rows = query.list();
		List<TimePolicyItemVO> list = new LinkedList<TimePolicyItemVO>();
		for (Object[] row : rows) {
			TimePolicyItemVO vo = new TimePolicyItemVO();
			vo.setLightPolicyId(row[0].toString());
			vo.setLightPolicyName(row[1].toString());
			vo.setBeginDate(buildDate((BigInteger) row[2]));
			vo.setEndDate(buildDate((BigInteger) row[3]));
			vo.setBeginTime(buildTime((Integer) row[4]));
			vo.setEndTime(buildTime((Integer) row[5]));
			list.add(vo);
		}
		return list;
	}

	private String buildDate(BigInteger date) {
		int month = date.intValue() / 100;
		int day = date.intValue() - month * 100;
		StringBuffer sb = new StringBuffer();
		if (month >= 10) {
			sb.append(month);
		} else {
			sb.append("0");
			sb.append(month);
		}
		sb.append("-");
		if (day >= 10) {
			sb.append(day);
		} else {
			sb.append("0");
			sb.append(day);
		}
		return sb.toString();
	}

	private static String buildTime(Integer time) {
		long hour = time.intValue() / 3600;
		long minute = (time.intValue() - hour * 3600) / 60;
		StringBuffer sb = new StringBuffer();
		if (hour >= 10) {
			sb.append(hour);
		} else {
			sb.append("0");
			sb.append(hour);
		}
		sb.append(":");
		if (minute >= 10) {
			sb.append(minute);
		} else {
			sb.append("0");
			sb.append(minute);
		}
		return sb.toString();
	}
}
