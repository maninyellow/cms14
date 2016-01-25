package com.znsx.cms.persistent.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.znsx.cms.persistent.dao.SoundRecordLogDAO;
import com.znsx.cms.persistent.model.SoundRecordLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * SoundRecordLogDAOImpl
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:29:31
 */
public class SoundRecordLogDAOImpl extends BaseDAOImpl<SoundRecordLog, String>
		implements SoundRecordLogDAO {

	@Override
	public List<SoundRecordLog> listSoundRecord(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag, String type,
			Integer startIndex, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SoundRecordLog.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(callNumber)) {
			criteria.add(Restrictions
					.like("callNumber", "%" + callNumber + "%"));
		}
		if (StringUtils.isNotBlank(hostNumber)) {
			criteria.add(Restrictions
					.like("hostNumber", "%" + hostNumber + "%"));
		}
		if (StringUtils.isNotBlank(callFlag)) {
			criteria.add(Restrictions.eq("callFlag", callFlag));
		}
		if (null != minDuration) {
			criteria.add(Restrictions.ge("duration", minDuration));
		}
		if (null != maxDuration) {
			criteria.add(Restrictions.lt("duration", maxDuration));
		}
		criteria.add(Restrictions.ge("createTime", beginTime));
		criteria.add(Restrictions.lt("createTime", endTime));
		if (type.equals("1")) {
			criteria.addOrder(Order.desc("updateTime"));
		} else if (type.equals("0")) {
			criteria.addOrder(Order.desc("createTime"));
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter type[" + type + "] invalid !");
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public int countSoundRecordLog(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SoundRecordLog.class);
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(callNumber)) {
			criteria.add(Restrictions
					.like("callNumber", "%" + callNumber + "%"));
		}
		if (StringUtils.isNotBlank(hostNumber)) {
			criteria.add(Restrictions
					.like("hostNumber", "%" + hostNumber + "%"));
		}
		if (StringUtils.isNotBlank(callFlag)) {
			criteria.add(Restrictions.eq("callFlag", callFlag));
		}
		if (null != minDuration) {
			criteria.add(Restrictions.ge("duration", minDuration));
		}
		if (null != maxDuration) {
			criteria.add(Restrictions.lt("duration", maxDuration));
		}
		criteria.add(Restrictions.ge("createTime", beginTime));
		criteria.add(Restrictions.lt("createTime", endTime));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

}
