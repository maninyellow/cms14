package com.znsx.cms.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.BaseManager;

/**
 * BaseManagerImpl(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 上午10:37:50
 */
public class BaseManagerImpl implements BaseManager {
	@Autowired
	private StandardNumberDAO snDAO;

	/**
	 * 同步更新标准号，如果newSN为空则删除oldSN对应的记录；如果oldSN为空则新增一条记录；如果都不为空则修改oldSN对应的记录；
	 * 如果都为空不做任何操作
	 * 
	 * @param oldSN
	 *            以前的对象标准号
	 * @param newSN
	 *            新的标准号
	 * @param classType
	 *            对象类型
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-23 下午4:45:11
	 */
	protected void syncSN(String oldSN, String newSN, String classType)
			throws BusinessException {
		if (StringUtils.isBlank(newSN)) {
			if (StringUtils.isBlank(oldSN)) {
				return;
			}
			// old不为空，new为空，删除
			else {
				StandardNumber sn = snDAO.getBySN(oldSN);
				snDAO.delete(sn);
			}
		}
		// new不为空
		else {
			// 检查是否重复
			StandardNumber exist = snDAO.getBySN(newSN);
			// 存在已有记录
			if (null != exist) {
				if (StringUtils.isNotBlank(oldSN)) {
					// old和new相同，不做任何操作
					if (oldSN.equals(newSN)) {
						return;
					}
				}
				// 其他情况都抛出SN重复异常
				throw new BusinessException(
						ErrorCode.UNIQUE_PROPERTY_DUPLICATE, "SN[" + newSN
								+ "]");
			}
			// 不存在已有记录
			else {
				// old为空，新增
				if (StringUtils.isBlank(oldSN)) {
					StandardNumber sn = new StandardNumber();
					sn.setSn(newSN);
					sn.setClassType(classType);
					snDAO.save(sn);
				}
				// old不为空
				else {
					StandardNumber old = snDAO.getBySN(oldSN);
					// 不存在old记录，自动新增一条
					if (null == old) {
						StandardNumber sn = new StandardNumber();
						sn.setSn(newSN);
						sn.setClassType(classType);
						snDAO.save(sn);
					}
					// 修改
					else {
						old.setSn(newSN);
					}
				}
			}
		}
	}

	/**
	 * 批量插入标准号
	 * 
	 * @param list
	 *            待插入的标准号列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-24 上午10:35:54
	 */
	protected void batchInsertSN(List<StandardNumber> list) {
		for (StandardNumber sn : list) {
			snDAO.batchInsert(sn);
		}
		snDAO.excuteBatch();
	}
}
