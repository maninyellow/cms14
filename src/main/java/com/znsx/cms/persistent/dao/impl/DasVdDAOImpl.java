package com.znsx.cms.persistent.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.DasVdDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.OrganVDVO;
import com.znsx.cms.service.model.OrganVehicleDetectorTopVO;
import com.znsx.cms.service.model.VdStatByDayVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.VehicleDetectorTotalVO;
import com.znsx.cms.service.model.VehicleDetectorVO;
import com.znsx.util.file.Configuration;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * DasVdDAOImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午10:00:26
 */
public class DasVdDAOImpl extends BaseDAOImpl<DasVd, String> implements
		DasVdDAO {

	public void batchInsert(List<DasVd> list) throws BusinessException {
		checkTablePartition(TypeDefinition.TABLE_NAME_VD);
		Session session = getSessionDas();
		batchInsert(list, session);
	}

	@Override
	public List<DasVd> listVdInfo(List<String> sns) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getListSQL(TypeDefinition.TABLE_NAME_VD, sns.size()));
		int index = 0;
		for (String sn : sns) {
			query.setString(index++, sn);
		}
		// 只查最近一天的
		query.setTimestamp(index, new Date(
				System.currentTimeMillis() - 86400000));
		List<Object[]> rows = query.list();
		List<DasVd> list = new LinkedList<DasVd>();
		for (Object[] row : rows) {
			index = 0;
			DasVd vd = new DasVd();
			vd.setId(MyStringUtil.object2String(row[index++]));
			vd.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vd.setRecTime((Timestamp) row[index++]);
			vd.setUpFluxb(NumberUtil.getInteger(row[index++]));
			vd.setUpFluxs(NumberUtil.getInteger(row[index++]));
			vd.setUpFlux(NumberUtil.getInteger(row[index++]));
			vd.setDwFluxb(NumberUtil.getInteger(row[index++]));
			vd.setDwFluxs(NumberUtil.getInteger(row[index++]));
			vd.setDwFlux(NumberUtil.getInteger(row[index++]));
			vd.setUpSpeed(NumberUtil.getInteger(row[index++]));
			vd.setDwSpeed(NumberUtil.getInteger(row[index++]));
			vd.setUpOcc(NumberUtil.getInteger(row[index++]));
			vd.setDwOcc(NumberUtil.getInteger(row[index++]));
			vd.setLaneNumber(NumberUtil.getShort(row[index++]));
			vd.setStatus(NumberUtil.getShort(row[index++]));
			vd.setCommStatus(NumberUtil.getShort(row[index++]));
			vd.setReserve(MyStringUtil.object2String(row[index++]));
			vd.setOrgan(MyStringUtil.object2String(row[index++]));
			vd.setUpFluxm(NumberUtil.getInteger(row[index++]));
			vd.setUpFluxms(NumberUtil.getInteger(row[index++]));
			vd.setDwFluxm(NumberUtil.getInteger(row[index++]));
			vd.setDwFluxms(NumberUtil.getInteger(row[index++]));
			vd.setUpHeadway(NumberUtil.getInteger(row[index++]));
			vd.setDwHeadway(NumberUtil.getInteger(row[index++]));
			vd.setUpSpeeds(NumberUtil.getInteger(row[index++]));
			vd.setUpSpeedb(NumberUtil.getInteger(row[index++]));
			vd.setUpSpeedm(NumberUtil.getInteger(row[index++]));
			vd.setUpSpeedms(NumberUtil.getInteger(row[index++]));
			vd.setDwSpeeds(NumberUtil.getInteger(row[index++]));
			vd.setDwSpeedb(NumberUtil.getInteger(row[index++]));
			vd.setDwSpeedm(NumberUtil.getInteger(row[index++]));
			vd.setDwSpeedms(NumberUtil.getInteger(row[index++]));
			vd.setUpOccb(NumberUtil.getInteger(row[index++]));
			vd.setUpOccs(NumberUtil.getInteger(row[index++]));
			vd.setUpOccm(NumberUtil.getInteger(row[index++]));
			vd.setUpOccms(NumberUtil.getInteger(row[index++]));
			vd.setDwOccb(NumberUtil.getInteger(row[index++]));
			vd.setDwOccs(NumberUtil.getInteger(row[index++]));
			vd.setDwOccm(NumberUtil.getInteger(row[index++]));
			vd.setDwOccms(NumberUtil.getInteger(row[index++]));
			list.add(vd);
		}

		// TODO Subqueries只支持一个值的返回，这里需要返回2个值，所以用Criteria无法实现
		// 也许新版本的Hibernate会有所支持，https://issues.apache.org/jira/browse/DERBY-5501
		// Criteria criteria = session.createCriteria(DasVd.class);
		// DetachedCriteria dc = DetachedCriteria.forClass(DasVd.class);
		// dc.add(Restrictions.in("standardNumber", sns));
		// // 只查找最近一天的数据
		// dc.add(Restrictions.gt("recTime",
		// new Date(System.currentTimeMillis() - 86400000)));
		// dc.setProjection(Projections.projectionList()
		// .add(Projections.max("recTime"), "rt")
		// .add(Projections.groupProperty("standardNumber"),"sn"));
		// criteria.add(Subqueries.propertyEq("standardNumber", "dc.sn"));
		// criteria.add(Subqueries.propertyEq("recTime", "dc.rt"));

		return list;
	}

	@Override
	public List<VdVO> statVD(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start, int limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVDStat(sns, organSN, scope));
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
		List<VdVO> list = new LinkedList<VdVO>();
		for (Object[] row : rows) {
			index = 0;
			VdVO vo = new VdVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setFlux(MyStringUtil.object2IntString(row[index++]));
			// 5分钟上报一次，计算1小时流量平均值*12
			String fluxAvg = MyStringUtil.object2String(row[index++]);
			if (StringUtils.isNotBlank(fluxAvg)) {
				float fFlux = Float.parseFloat(fluxAvg);
				fluxAvg = Math.round(fFlux * 12) + "";
			}
			vo.setFluxAvg(fluxAvg);
			vo.setSpeedAvg(MyStringUtil.object2IntString(row[index++]));
			vo.setOccupAvg(MyStringUtil.cutObject(row[index++], 2));
			vo.setOrganName(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countVD(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.countVDStat(sns, organSN, scope));
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

	@Override
	public VehicleDetectorVO statVehicleDetector(String id,
			Timestamp beginTime, Timestamp endTime) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVehicleDetectorStat());
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		query.setString(2, id);
		Object[] obj = (Object[]) query.uniqueResult();
		VehicleDetectorVO vo = new VehicleDetectorVO();
		vo.setTotalFlux(MyStringUtil.object2String(obj[0]));
		vo.setUpFlux(MyStringUtil.object2String(obj[1]));
		vo.setDwFlux(MyStringUtil.object2String(obj[2]));
		vo.setUpSpeed(MyStringUtil.object2String(obj[3]));
		vo.setDwSpeed(MyStringUtil.object2String(obj[4]));
		return vo;
	}

	public Object statVehicleDetectorTotal(String id) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.statVehicleDetectorTotal());
		query.setString(0, id);
		Object obj = query.uniqueResult();
		return obj;
	}

	@Override
	public VehicleDetectorTotalVO statOrganYesTodayTotalVD(String organId,
			Timestamp yestodayBegin, Timestamp yestodayEnd) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getStatOrganYesTodayTotalVD());
		query.setTimestamp(0, yestodayBegin);
		query.setTimestamp(1, yestodayEnd);
		query.setString(2, organId);
		Object[] obj = (Object[]) query.uniqueResult();
		VehicleDetectorTotalVO vo = new VehicleDetectorTotalVO();
		vo.setYestodayFlow(MyStringUtil.object2String(obj[0]));
		vo.setYestodayEtc(MyStringUtil.object2String(obj[1]));
		vo.setYestodayFreight(MyStringUtil.object2String(obj[2]));
		return vo;
	}

	@Override
	public VehicleDetectorTotalVO statOrganTotalVD(String organId) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getStatOrganTotalVD());
		query.setString(0, organId);
		Object[] obj = (Object[]) query.uniqueResult();
		VehicleDetectorTotalVO vo = new VehicleDetectorTotalVO();
		vo.setTotalFlow(MyStringUtil.object2String(obj[0]));
		vo.setEtcTotal(MyStringUtil.object2String(obj[1]));
		vo.setFreightTotal(MyStringUtil.object2String(obj[2]));
		return vo;
	}

	@Override
	public List<OrganVehicleDetectorTopVO> trafficFlowTop(Timestamp beginTime,
			Timestamp endTime) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getTrafficFlowTop());
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		query.setInteger(2, 10);
		List<Object[]> rows = query.list();
		int index = 0;
		List<OrganVehicleDetectorTopVO> list = new LinkedList<OrganVehicleDetectorTopVO>();
		for (Object[] row : rows) {
			index = 0;
			OrganVehicleDetectorTopVO vo = new OrganVehicleDetectorTopVO();
			vo.setId(MyStringUtil.object2String(row[index++]));
			vo.setTotalFlow(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public Object roadTrafficFlowTotal(String organId) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.statRoadTrafficFlowTotal());
		query.setString(0, organId);
		Object obj = query.uniqueResult();
		return obj;
	}

	@Override
	public OrganVDVO roadTrafficFlow(String organId, Timestamp beginTime,
			Timestamp endTime) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getRoadTrafficFlow());
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		query.setString(2, organId);
		Object[] obj = (Object[]) query.uniqueResult();
		OrganVDVO vo = new OrganVDVO();
		vo.setTotalFlow((MyStringUtil.object2String(obj[0])));
		vo.setUpFlowSum(MyStringUtil.object2String(obj[1]));
		vo.setDownFlowSum(MyStringUtil.object2String(obj[2]));
		vo.setUpSpeedAvg(MyStringUtil.object2String(obj[3]));
		vo.setDownSpeedAvg(MyStringUtil.object2String(obj[4]));
		return vo;
	}

	@Override
	public List<VdStatByDayVO> vdStatByHour(Timestamp beginTime,
			Timestamp endTime, String[] sn, Integer startIndex, Integer limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVdStatByHour(sn));
		String dbName = Configuration.getInstance().getDasDbName();
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(i + 2, limit + startIndex);
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
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setUpOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOcc(MyStringUtil.cutObject((row[index++]), 2));
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
			vo.setUpHeadway(MyStringUtil.object2IntString(row[index++]));
			vo.setDwHeadway(MyStringUtil.object2IntString(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int vdStatByHourTotal(String[] sn, Timestamp beginTime,
			Timestamp endTime, String name) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.vdStatByHourTotal(sn));
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		Number count = (Number) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public List<VdStatByDayVO> vdStatByMonth(Timestamp beginTime,
			Timestamp endTime) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.vdStatByYear());
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		List<Object[]> rows = query.list();
		int index = 0;
		List<VdStatByDayVO> list = new LinkedList<VdStatByDayVO>();
		for (Object[] row : rows) {
			index = 0;
			VdStatByDayVO vo = new VdStatByDayVO();
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setUpOccs(MyStringUtil.object2String(row[index++]));
			vo.setDwOccs(MyStringUtil.object2String(row[index++]));
			vo.setUpOccm(MyStringUtil.object2String(row[index++]));
			vo.setDwOccm(MyStringUtil.object2String(row[index++]));
			vo.setUpOccb(MyStringUtil.cutObject(row[index++], 2));
			vo.setDwOccb(MyStringUtil.cutObject(row[index++], 2));
			vo.setUpOcc(MyStringUtil.object2String(row[index++]));
			vo.setDwOcc(MyStringUtil.object2String(row[index++]));
			vo.setUpSpeeds(MyStringUtil.object2String(row[index++]));
			vo.setDwSpeeds(MyStringUtil.object2String(row[index++]));
			vo.setUpSpeedm(MyStringUtil.object2String(row[index++]));
			vo.setDwSpeedm(MyStringUtil.object2String(row[index++]));
			vo.setUpSpeedb(MyStringUtil.object2String(row[index++]));
			vo.setDwSpeedb(MyStringUtil.object2String(row[index++]));
			vo.setUpSpeed(MyStringUtil.object2String(row[index++]));
			vo.setDwSpeed(MyStringUtil.object2String(row[index++]));
			vo.setUpFlows(MyStringUtil.object2String(row[index++]));
			vo.setDwFlows(MyStringUtil.object2String(row[index++]));
			vo.setUpFlowm(MyStringUtil.object2String(row[index++]));
			vo.setDwFlowm(MyStringUtil.object2String(row[index++]));
			vo.setUpFlowb(MyStringUtil.object2String(row[index++]));
			vo.setDwFlowb(MyStringUtil.object2String(row[index++]));
			vo.setUpFlow(MyStringUtil.object2String(row[index++]));
			vo.setDwFlow(MyStringUtil.object2String(row[index++]));
			vo.setUpFlowTotal(MyStringUtil.object2String(row[index++]));
			vo.setDwFlowTotal(MyStringUtil.object2String(row[index++]));
			vo.setRecTime(MyStringUtil.object2String(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<VdStatByDayVO> vdStatByDay(Timestamp beginTime,
			Timestamp endTime, String[] sn, Integer startIndex, Integer limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVdStatByDay(sn));
		String dbName = Configuration.getInstance().getDasDbName();
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(i + 2, limit + startIndex);
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
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setUpOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOcc(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOcc(MyStringUtil.object2IntString(row[index++]));
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
			vo.setUpHeadway(MyStringUtil.object2IntString(row[index++]));
			vo.setDwHeadway(MyStringUtil.object2IntString(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<VdStatByDayVO> vdStat(Timestamp beginTime, Timestamp endTime,
			String[] sn, Integer startIndex, Integer limit) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.getVdStat(sn));
		String dbName = Configuration.getInstance().getDasDbName();
		query.setTimestamp(0, beginTime);
		query.setTimestamp(1, endTime);
		int i = 0;
		for (i = 0; i < sn.length; i++) {
			query.setString(i + 2, sn[i]);
		}
		if (Configuration.ORACLE.equals(dbName)) {
			query.setInteger(i + 2, limit + startIndex);
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
			vo.setStandardNumber(MyStringUtil.object2String(row[index++]));
			vo.setUpOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccs(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccm(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOccb(MyStringUtil.object2IntString(row[index++]));
			vo.setUpOcc(MyStringUtil.object2IntString(row[index++]));
			vo.setDwOcc(MyStringUtil.object2IntString(row[index++]));
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
			vo.setUpHeadway(MyStringUtil.object2IntString(row[index++]));
			vo.setDwHeadway(MyStringUtil.object2IntString(row[index++]));
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countVd(Timestamp begin, Timestamp end) {
		Session session = getSessionDas();
		Criteria criteria = session.createCriteria(DasVd.class);
		criteria.add(Restrictions.gt("recTime", begin));
		criteria.add(Restrictions.le("recTime", end));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<DasVd> listDasVd(Timestamp begin, Timestamp end, int start,
			int limit) {
		StatelessSession session = null;
		try {
			session = getStatelessSessionDAS();
			Criteria criteria = session.createCriteria(DasVd.class);
			criteria.add(Restrictions.gt("recTime", begin));
			criteria.add(Restrictions.le("recTime", end));
			criteria.add(Restrictions.eq("commStatus", Short.valueOf("0")));
			criteria.add(Restrictions.eq("status", Short.valueOf("0")));
			criteria.setFirstResult(start);
			criteria.setMaxResults(limit);
			criteria.addOrder(Order.asc("recTime"));
			return criteria.list();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}
}
