package com.znsx.cms.persistent.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.DasControlDeviceRealDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.DasControlDeviceReal;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.TunnelDeviceStatusVO;

/**
 * DasControlRealDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午4:17:36
 */
public class DasControlRealDAOImpl extends
		BaseDAOImpl<DasControlDeviceReal, String> implements
		DasControlDeviceRealDAO {

	@Override
	public void batchInsert(List<DasControlDeviceReal> cdList) {
		// checkTablePartition(TypeDefinition.TABLE_NAME_CD_REAL);
		Session session = getSessionDas();
		batchInsert(cdList, session);
	}

	@Override
	public void deleteAll(String table) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance().delete(
				table));
		query.executeUpdate();
	}

	@Override
	public void deleteBySN(String sn) {
		Session session = getSessionDas();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteCdReal(sn));
		if (StringUtils.isNotBlank(sn)) {
			query.setString(0, sn);
		}
		query.executeUpdate();
	}

	@Override
	public List<TunnelDeviceStatusVO> listTunnelDeviceStatusNoTransaction(
			Set<String> sns) {
		Session session = SessionFactoryUtils.getSession(sessionFactoryDAS,
				true);
		Criteria criteria = session.createCriteria(DasControlDeviceReal.class);
		List<DasControlDeviceReal> list = null;
		if (sns.size() > 0) {
			criteria.add(Restrictions.in("standardNumber", sns));
			try {
				list = criteria.list();
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						e.getMessage());
			} finally {
				session.close();
			}
		} else {
			list = new LinkedList<DasControlDeviceReal>();
		}

		List<TunnelDeviceStatusVO> rtn = new LinkedList<TunnelDeviceStatusVO>();
		for (DasControlDeviceReal entity : list) {
			TunnelDeviceStatusVO vo = new TunnelDeviceStatusVO();
			vo.setCommStatus(entity.getCommStatus().toString());
			vo.setStandardNumber(entity.getStandardNumber());
			vo.setStatus(entity.getStatus().toString());
			vo.setWorkState(entity.getWorkState());
			rtn.add(vo);
		}

		return rtn;
	}

}
