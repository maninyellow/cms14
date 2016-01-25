package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.model.SubVehicleDetector;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.string.MyStringUtil;

/**
 * 车检器数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-20 上午9:25:33
 */
public class VehicleDetectorDAOImpl extends
		BaseDAOImpl<VehicleDetector, String> implements VehicleDetectorDAO {
	@Override
	public Map<String, VehicleDetector> mapVDByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.setCacheable(false);
		criteria.setCacheMode(CacheMode.IGNORE);
		criteria.addOrder(Order.desc("createTime"));
		List<VehicleDetector> list = criteria.list();
		Map<String, VehicleDetector> map = new LinkedHashMap<String, VehicleDetector>();
		for (VehicleDetector vd : list) {
			map.put(vd.getId().toString(), vd);
		}
		return map;
	}

	@Override
	public Integer countVehicleDetector(String organId, String name,
			String standardNumber, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<VehicleDetector> listVehicleDetector(String organId,
			String name, String standardNumber, String stakeNumber,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);

		criteria.add(Restrictions.eq("organ.id", organId));

		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public List<VehicleDetector> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, VehicleDetector> mapVDBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<VehicleDetector> list = criteria.list();
		Map<String, VehicleDetector> map = new LinkedHashMap<String, VehicleDetector>();
		for (VehicleDetector vd : list) {
			map.put(vd.getStandardNumber(), vd);
		}
		return map;
	}

	@Override
	public void deleteRoleVDPermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setInteger(1, TypeDefinition.DEVICE_TYPE_VD);
		q.executeUpdate();

	}

	@Override
	public List<VehicleDetector> vdInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions.like("name", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(navigation)) {
			criteria.add(Restrictions.eq("navigation", navigation));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.eq("stakeNumber", stakeNumber));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(start.intValue());
		criteria.setMaxResults(limit.intValue());
		return criteria.list();
	}

	@Override
	public Integer countVdInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(deviceName)) {
			criteria.add(Restrictions.like("name", "%" + deviceName + "%"));
		}
		if (StringUtils.isNotBlank(navigation)) {
			criteria.add(Restrictions.eq("navigation", navigation));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.eq("stakeNumber", stakeNumber));
		}
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countVD(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		criteria.setProjection(Projections.groupProperty("organ.id"));
		criteria.add(Property.forName("organ.id").in(organIds));
		List<String> list = criteria.list();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}

	@Override
	public List<VehicleDetector> listRoadVd(String organId, String navigation) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(navigation)) {
			criteria.add(Restrictions.eq("navigation", navigation));
		}
		return criteria.list();
	}

	@Override
	public void deleteSubVehicleDetector(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteSubVehicleDetector();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();
	}

	@Override
	public List<SubVehicleDetector> listSubVehicleDetector() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SubVehicleDetector.class);
		return criteria.list();
	}

	@Override
	public List<VehicleDetector> listVd(String organs[], String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(VehicleDetector.class);
		if (organs.length > 0) {
			criteria.add(Restrictions.in("organ.id", organs));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public List<SyncVehicleDetector> vdStatByMonth(String beginTime,
			String endTime, String[] sn, Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SyncVehicleDetector.class);
		criteria.add(Restrictions.in("standardNumber", sn));
		criteria.add(Restrictions.between("dateTime", beginTime, endTime));
		criteria.setFirstResult(startIndex.intValue());
		criteria.setMaxResults(limit.intValue());
		criteria.addOrder(Order.desc("dateTime"));
		return criteria.list();
	}

	@Override
	public int vdStatByMonthTotal(String beginTime, String endTime, String[] sn) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SyncVehicleDetector.class);
		criteria.add(Restrictions.in("standardNumber", sn));
		criteria.add(Restrictions.between("dateTime", beginTime, endTime));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<VdStatByDayVO> vdStatByYear(String beginTimeS, String endTimeS,
			String[] sn, Integer startIndex, Integer limit) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVdStatByYear(sn));
		query.setString(0, beginTimeS);
		query.setString(1, endTimeS);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(i + 2, limit);
			query.setInteger(i + 3, startIndex);
		} else if (Configuration.MYSQL.equals(dbName)) {
			query.setInteger(i + 2, startIndex);
			query.setInteger(i + 3, limit);
		}

		List<Object[]> rows = query.list();
		int index = 0;
		List<VdStatByDayVO> list = new LinkedList<VdStatByDayVO>();
		for (Object[] row : rows) {
			index = 0;
			VdStatByDayVO vo = new VdStatByDayVO();
			vo.setStandardNumber(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOcc(MyStringUtil.cutObject(row[index++], 2));
			vo.setDwOcc(MyStringUtil.cutObject(row[index++], 2));
			vo.setUpSpeeds(MyStringUtil.object2IntString(row[index++]));
			vo.setDwSpeeds(MyStringUtil.object2IntString(row[index++]));
			vo.setUpSpeedm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwSpeedm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpSpeedb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwSpeedb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpSpeed(MyStringUtil.object2IntString(row[index++]));
			vo.setDwSpeed(MyStringUtil.object2IntString(row[index++]));
			vo.setUpFlows(MyStringUtil.object2IntString(row[index++]));
			vo.setDwFlows(MyStringUtil.object2IntString(row[index++]));
			vo.setUpFlowm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwFlowm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpFlowb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwFlowb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpFlow(MyStringUtil.object2IntString(row[index++]));
			vo.setDwFlow(MyStringUtil.object2IntString(row[index++]));
			vo.setUpFlowTotal(MyStringUtil.object2IntString(row[index++]));
			vo.setDwFlowTotal(MyStringUtil.object2IntString(row[index++]));
			vo.setRecTime(MyStringUtil.object2IntString(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int vdStatByYearTotal(String beginTimeS, String endTimeS, String[] sn) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.vdStatByYearTotal(sn));
		query.setString(0, beginTimeS);
		query.setString(1, endTimeS);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<VehicleDetector> listSubVd(String organs[], String name) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listSubVd(organs, name));
		int i = 0;
		if (organs.length > 0) {
			for (i = 0; i < organs.length; i++) {
				query.setString(i, organs[i]);
			}
		}
		if (StringUtils.isNotBlank(name)) {
			query.setString(i, "%" + name + "%");
		}
		List<Object[]> rows = query.list();
		int index = 0;
		List<VehicleDetector> list = new LinkedList<VehicleDetector>();
		for (Object[] row : rows) {
			index = 0;
			VehicleDetector vd = new VehicleDetector();
			vd.setId(MyStringUtil.object2StringNotNull(row[index++]));
			vd.setName(MyStringUtil.object2StringNotNull(row[index++]));
			vd.setStandardNumber(MyStringUtil
					.object2StringNotNull(row[index++]));
			list.add(vd);
		}
		return list;
	}
}
