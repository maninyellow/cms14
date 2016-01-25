package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.UserSessionDAO;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionCcs;
import com.znsx.cms.persistent.model.UserSessionCrs;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.UserSessionDws;
import com.znsx.cms.persistent.model.UserSessionEns;
import com.znsx.cms.persistent.model.UserSessionMss;
import com.znsx.cms.persistent.model.UserSessionPts;
import com.znsx.cms.persistent.model.UserSessionRms;
import com.znsx.cms.persistent.model.UserSessionRss;
import com.znsx.cms.persistent.model.UserSessionSrs;
import com.znsx.cms.persistent.model.UserSessionUser;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;

/**
 * 用户会话数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午8:57:54
 */
public class UserSessionDAOImpl extends BaseDAOImpl<UserSession, String>
		implements UserSessionDAO {

	@Override
	public List<UserSessionUser> findUserSessionByUserId(String userId,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.addOrder(Order.desc("updateTime"));
		if (startIndex != null) {
			criteria.setFirstResult(startIndex);
		}
		if (limit != null) {
			criteria.setMaxResults(limit);
		}
		return criteria.list();
	}

	@Override
	public Integer userSessionTotalCount(String userId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public void copyUserSessionToHistory(String userId) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.copySessionToHistory());
		query.setString(0, userId);
		query.executeUpdate();
	}

	@Override
	public void deleteByUser(String userId) {
		Session session = getSession();
		Query query = session.createQuery(SqlFactory.getInstance()
				.deleteSessionByUser());
		query.setString(0, userId);
		query.executeUpdate();
	}

	@Override
	public Integer onlineUserTotal(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions.in("organId", organIds));
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.or(
				Restrictions.eq("clientType", TypeDefinition.CLIENT_TYPE_OMC),
				Restrictions.eq("clientType", TypeDefinition.CLIENT_TYPE_CS)));
		Integer total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public List<UserSession> listCMSMaintainSession() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSession.class);
		criteria.addOrder(Order.desc("updateTime"));
		return criteria.list();
	}

	public List<ListOnlineUsersVO> listOrganOnlineUser(String organId,
			String logonName, String name, int start, int limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria = criteria.createAlias("user", "user");
		criteria.add(Restrictions.like("organId", "%" + organId + "%"));
		if (StringUtils.isNotBlank(logonName)) {
			criteria.add(Restrictions.like("logonName", "%" + logonName + "%"));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("user.name", "%" + name + "%"));
		}
		criteria.add(Restrictions.in("clientType",
				new String[] { TypeDefinition.CLIENT_TYPE_CS,
						TypeDefinition.CLIENT_TYPE_SGC,
						TypeDefinition.CLIENT_TYPE_MCU,
						TypeDefinition.CLIENT_TYPE_OMC }));
		criteria.addOrder(Order.desc("updateTime"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		List<UserSessionUser> userSessions = criteria.list();
		List<ListOnlineUsersVO> list = new LinkedList<ListOnlineUsersVO>();
		for (UserSessionUser userSession : userSessions) {
			ListOnlineUsersVO vo = new ListOnlineUsersVO();
			vo.setId(userSession.getId());
			vo.setClientType(userSession.getClientType());
			vo.setIp(userSession.getIp());
			vo.setLogonName(userSession.getLogonName());
			vo.setLogonTime(userSession.getLogonTime() + "");
			vo.setName(userSession.getUser().getName());
			vo.setStandardNumber(userSession.getStandardNumber());
			vo.setTicket(userSession.getTicket());
			vo.setUpdateTime(userSession.getUpdateTime() + "");
			vo.setUserId(userSession.getUser().getId());
			list.add(vo);
		}
		return list;
	}

	@Override
	public Integer countOrganOnlineUser(String organId, String logonName,
			String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria = criteria.createAlias("user", "user");
		if (StringUtils.isNotBlank(logonName)) {
			criteria.add(Restrictions.like("logonName", "%" + logonName + "%"));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("user.name", "%" + name + "%"));
		}
		criteria.setProjection(Projections.rowCount());
		int total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public List<CcsUserSessionVO> listCcsUserSession(String ccsId) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listCcsUserSession());
		query.setString(0, ccsId);
		// query.setCacheable(true);
		// query.setCacheMode(CacheMode.NORMAL);
		query.addScalar("id", Hibernate.STRING);
		query.addScalar("standardNumber", Hibernate.STRING);
		query.addScalar("priority", Hibernate.STRING);
		// query.setResultTransformer(Transformers
		// .aliasToBean(CcsUserSessionVO.class));
		// List<CcsUserSessionVO> list = query.list();
		List<Object[]> rows = query.list();
		List<CcsUserSessionVO> list = new LinkedList<CcsUserSessionVO>();
		int index = 0;
		for (Object[] row : rows) {
			CcsUserSessionVO vo = new CcsUserSessionVO();
			vo.setId(row[index++] + "");
			vo.setStandardNumber(row[index++] + "");
			vo.setPriority(row[index++] + "");
			list.add(vo);
			index = 0;
		}
		return list;
	}

	@Override
	public List<String> listOnlineUserId() {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(SqlFactory.getInstance()
				.listOnlineUserId());
		List<String> rows = query.list();
		return rows;
	}

	@Override
	public int getCSTotalCount() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions
				.in("clientType", new String[] { TypeDefinition.CLIENT_TYPE_CS,
						TypeDefinition.CLIENT_TYPE_SGC }));
		criteria.setProjection(Projections.rowCount());
		int totalCount = (Integer) criteria.uniqueResult();
		return totalCount;
	}

	@Override
	public long getLatestSession() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions.not(Restrictions.eq("clientType",
				TypeDefinition.CLIENT_TYPE_OMC)));
		criteria.setProjection(Projections.max("updateTime"));
		Long time = (Long) criteria.uniqueResult();
		if (null != time) {
			return time.longValue();
		}
		return 0L;
	}

	@Override
	public List<UserSessionCcs> listUserSessionCcs(String ccsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionCcs.class);
		criteria.add(Restrictions.eq("ccs.id", ccsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionCcs> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionCrs> listUserSessionCrs(String crsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionCrs.class);
		criteria.add(Restrictions.eq("crs.id", crsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionCrs> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionMss> listUserSessionMss(String mssId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionMss.class);
		criteria.add(Restrictions.eq("mss.id", mssId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionMss> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionDws> listUserSessionDws(String dwsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionDws.class);
		criteria.add(Restrictions.eq("dws.id", dwsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionDws> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionPts> listUserSessionPts(String ptsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionPts.class);
		criteria.add(Restrictions.eq("pts.id", ptsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionPts> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionDas> listUserSessionDas(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionDas.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionDas> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionEns> listUserSessionEns(String ensId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionEns.class);
		criteria.add(Restrictions.eq("ens.id", ensId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionEns> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionRms> listUserSessionRms(String rmsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionRms.class);
		criteria.add(Restrictions.eq("rms.id", rmsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionRms> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionRss> listUserSessionRss(String rssId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionRss.class);
		criteria.add(Restrictions.eq("rss.id", rssId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionRss> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionUser> findFirstUser(String id) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		criteria.add(Restrictions.eq("user.id", id));
		criteria.addOrder(Order.asc("logonTime"));
		return criteria.list();

	}

	@Override
	public List<UserSessionCcs> listOnlineCcs() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionCcs.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionCcs> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionCrs> listOnlineCrs() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionCrs.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionCrs> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionMss> listOnlineMss() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionMss.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionMss> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionPts> listOnlinePts() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionPts.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionPts> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionDws> listOnlineDws() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionDws.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionDws> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionDas> listOnlineDas() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionDas.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionDas> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionEns> listOnlineEns() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionEns.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionEns> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSessionRms> listOnlineRms() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionRms.class);
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionRms> list = criteria.list();
		return list;
	}

	@Override
	public List<UserSession> listUserSession() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionUser.class);
		return criteria.list();
	}
	
	@Override
	public List<UserSessionSrs> listUserSessionSrs(String srsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(UserSessionSrs.class);
		criteria.add(Restrictions.eq("srs.id", srsId));
		criteria.addOrder(Order.desc("updateTime"));
		List<UserSessionSrs> list = criteria.list();
		return list;
	}
}
