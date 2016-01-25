package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.SyncVehicleDetectorDAO;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.service.model.VdFluxByMonthVO;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * SyncVehicleDetectorDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:17:58
 */
public class SyncVehicleDetectorDAOImpl extends
		BaseDAOImpl<SyncVehicleDetector, String> implements
		SyncVehicleDetectorDAO {

	@Override
	public void batchInsertSyncVd(List<SyncVehicleDetector> list) {
		Session session = getSession();
		batchInsert(list, session);
	}

	@Override
	public List<VdFluxByMonthVO> listVdFluxByMonth() {
		Session session = getSession();
		SQLQuery q = session.createSQLQuery(SqlFactory.getInstance()
				.listVdFluxByMonth());
		List<Object[]> list = q.list();

		List<VdFluxByMonthVO> rtn = new LinkedList<VdFluxByMonthVO>();
		for (Object[] row : list) {
			int index = 0;
			VdFluxByMonthVO vo = new VdFluxByMonthVO();
			vo.setStandardNumber(MyStringUtil
					.object2StringNotNull(row[index++]));
			vo.setMonth(MyStringUtil.object2StringNotNull(row[index++]));
			vo.setUpFlux(NumberUtil.getLong(row[index++]));
			vo.setDwFlux(NumberUtil.getLong(row[index++]));
			rtn.add(vo);
		}
		return rtn;
	}
}
