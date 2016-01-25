package com.znsx.cms.persistent.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.UserSessionHistoryDAO;
import com.znsx.cms.persistent.model.UserSessionHistory;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;

/**
 * 用户历史会话数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:16:03
 */
public class UserSessionHistoryDAOImpl extends
		BaseDAOImpl<UserSessionHistory, String> implements
		UserSessionHistoryDAO {

	public List<ListUserSessionHistoryVO> findUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String userOrganId) {
		Session session = getSession();
		String sql = "select * from sv_user_session_history b where 1=1";
		if (StringUtils.isBlank(organId)) {
			sql += " and b.path like \"%" + userOrganId + "%\"";
		} else {
			sql += " and b.organ_id = " + organId;
		}
		if (StringUtils.isNotBlank(userId)) {
			sql += " and b.user_id = " + userId;
		}
		if (StringUtils.isNotBlank(userName)) {
			sql += " and b.logon_name like \"%" + userName + "%\"";
		}
		if (null != startTime) {
			sql += " and b.logon_time > " + startTime;
		}
		if (null != endTime) {
			sql += " and b.logon_time < " + endTime;
		}
		sql += " LIMIT " + startIndex + " , " + limit;
		Query query = session.createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<ListUserSessionHistoryVO> listVO = new ArrayList<ListUserSessionHistoryVO>();
		for (Object[] o : list) {
			ListUserSessionHistoryVO vo = new ListUserSessionHistoryVO();
			vo.setClientType(o[11].toString());
			vo.setId(o[0].toString());
			vo.setIp(o[10].toString());
			vo.setLogoffTime(o[9].toString());
			vo.setLogonName(o[6].toString());
			vo.setLogonTime(o[8].toString());
			vo.setOrganId(o[3].toString());
			vo.setOrganName(o[4].toString());
			vo.setTicket(o[1].toString());
			vo.setUserId(o[2].toString());
			vo.setUserStandardNo(o[7].toString());
			listVO.add(vo);
		}
		return listVO;
	}

	public int selectTotalCount(String userId, String userName, String organId,
			Long startTime, Long endTime, String userOrganId) {
		Session session = getSession();
		String sql = "select count(*) from sv_user_session_history b where 1=1";
		if (StringUtils.isBlank(organId)) {
			sql += " and b.path like \"%" + userOrganId + "%\"";
		} else {
			sql += " and b.organ_id = " + organId;
		}
		if (StringUtils.isNotBlank(userId)) {
			sql += "and b.user_id = " + userId;
		}
		if (StringUtils.isNotBlank(userName)) {
			sql += "and b.logon_name like \"%" + userName + "%\"";
		}
		if (null != startTime) {
			sql += " and b.logon_time > " + startTime;
		}
		if (null != endTime) {
			sql += " and b.logon_time < " + endTime;
		}
		Query query = session.createSQLQuery(sql);
		Number o = (Number) query.uniqueResult();
		return o.intValue();
	}

	@Override
	public List<UserSessionHistory> listUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String userOrganId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionHistory.class);
		if (StringUtils.isNotBlank(userId)) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.like("logonName", "%" + userName + "%"));
		}
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		} else {
			criteria.add(Restrictions.like("path", "%" + userOrganId + "%"));
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("logonTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.lt("logonTime", endTime));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countUserSessionHistory(String userId, String userName,
			String organId, Long startTime, Long endTime, String userOrganId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionHistory.class);
		if (StringUtils.isNotBlank(userId)) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		if (StringUtils.isNotBlank(userName)) {
			criteria.add(Restrictions.like("logonName", "%" + userName + "%"));
		}
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organId", organId));
		} else {
			criteria.add(Restrictions.like("path", "%" + userOrganId + "%"));
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("logonTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.lt("logonTime", endTime));
		}
		criteria.setProjection(Projections.rowCount());
		return ((Integer) criteria.uniqueResult()).intValue();
	}

	@Override
	public long getLatestSession() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionHistory.class);
		criteria.add(Restrictions.not(Restrictions.eq("clientType",
				TypeDefinition.CLIENT_TYPE_OMC)));
		criteria.setProjection(Projections.max("logoffTime"));
		Long time = (Long) criteria.uniqueResult();
		if (null != time) {
			return time.longValue();
		}
		return 0L;
	}
}
