package com.znsx.cms.persistent.dao.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.dao.SysLogDAO;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.TopRealPlayLog;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;

/**
 * 系统日志数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:16:20
 */
public class SysLogDAOImpl extends BaseDAOImpl<SysLog, String> implements
		SysLogDAO {

	/**
	 * 存储新增日志记录的列表，当达到一定数量，或者超过一定时间后批量写入数据库
	 */
	public static List<SysLog> logVector = new Vector<SysLog>();
	/**
	 * 批量入库操作的长度上限值
	 */
	public static final int BATCH_SIZE = 100;
	/**
	 * 数据库列长度限制
	 */
	public static final int DATABASE_LIMIT_LENGTH = 255;

	public void batchInsert(SysLog record) throws BusinessException {
		logVector.add(record);
		if (logVector.size() >= BATCH_SIZE) {
			saveTask();
		}
	}

	public void saveTask() throws BusinessException {
		Connection conn = null;
		PreparedStatement psmt = null;

		synchronized (logVector) {

			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmt = conn.prepareStatement(SqlFactory.getInstance()
						.insertSysLog());
				for (SysLog sysLog : logVector) {
					if (null == sysLog.getTargetName()
							|| null == sysLog.getResourceName()) {
						continue;
					}
					// 如果名称长度超过数据库限制，则不记录日志
					if (sysLog.getTargetName().getBytes("utf8").length > DATABASE_LIMIT_LENGTH
							|| sysLog.getResourceName().getBytes("utf8").length > DATABASE_LIMIT_LENGTH) {
						continue;
					}
					psmt.setString(1, (String) new UUIDHexGenerator().generate(
							null, null));
					psmt.setString(2, sysLog.getResourceId());
					psmt.setString(3, sysLog.getResourceName());
					psmt.setString(4, sysLog.getResourceType());
					psmt.setString(5, sysLog.getTargetId());
					psmt.setString(6, sysLog.getTargetName());
					psmt.setString(7, sysLog.getTargetType());
					psmt.setLong(8, sysLog.getLogTime());
					psmt.setString(9, sysLog.getOperationType());
					psmt.setString(10, sysLog.getOperationCode());
					if (sysLog.getOperationName().getBytes("utf8").length > DATABASE_LIMIT_LENGTH) {
						psmt.setString(11,
								sysLog.getOperationName().substring(0, 80));
					} else {
						psmt.setString(11, sysLog.getOperationName());
					}
					psmt.setString(12, sysLog.getSuccessFlag());
					psmt.setLong(13, sysLog.getCreateTime());
					psmt.setString(14, sysLog.getNote());
					psmt.setString(15, sysLog.getOrganId());
					psmt.addBatch();
				}
				psmt.executeBatch();
				conn.commit();
				logVector.clear();
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					if (conn != null) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
						"Could not get database connnection");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (conn != null) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new BusinessException(ErrorCode.ERROR, e.getMessage());
			} finally {
				try {
					if (psmt != null) {
						psmt.close();
						psmt = null;
					}
					if (conn != null) {
						DataSourceUtils.releaseConnection(conn,
								SessionFactoryUtils
										.getDataSource(sessionFactory));
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(
							ErrorCode.DATABASE_ACCESS_ERROR,
							"Could not close database connnection");
				}
			}
		}
	}

	public Object getNameByIdAndType(Object id, String type)
			throws BusinessException {
		String hql = SqlFactory.getInstance().getNameByIdAndType(type);
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Query query = session.createQuery(hql);
		query.setParameter(0, id);

		Object o = null;
		try {
			o = query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
		// if (null == o) {
		// throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, type
		// + "[" + id + "] not found !");
		// }
		return o;
	}

	public List<SysLog> listSysLogByAdmin(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String targetName,
			String operationCode, String operationType, String resourceType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SysLog.class);
		if (StringUtils.isNotBlank(resourceName)) {
			criteria.add(Restrictions.like("resourceName", "%" + resourceName
					+ "%"));
		}
		if (StringUtils.isNotBlank(targetName)) {
			criteria.add(Restrictions
					.like("targetName", "%" + targetName + "%"));
		}
		if (StringUtils.isNotBlank(operationCode)) {
			criteria.add(Restrictions.like("operationCode", "%" + operationCode
					+ "%"));
		}
		if (StringUtils.isNotBlank(resourceType)) {
			criteria.add(Restrictions.eq("resourceType", resourceType));
		}
		if (StringUtils.isNotBlank(operationType)) {
			if (operationType.equals("create")
					|| operationType.equals("delete")
					|| operationType.equals("register")) {
				criteria.add(Restrictions.eq("resourceType", resourceType));
			} else {
				List<String> list = new ArrayList<String>();
				list.add("create");
				list.add("delete");
				list.add("register");
				criteria.add(Restrictions.not(Restrictions.in("resourceType",
						list)));
			}
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("createTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.le("createTime", endTime));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public List<SysLog> listSysLog(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String[] organIds,
			String targetName, String operationCode, String operationType,
			String resourceType, String type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SysLog.class);
		// if (StringUtils.isNotBlank(organId)) {
		// criteria.add(Restrictions.eq("organId", organId));
		// } else {
		criteria.add(Restrictions.in("organId", organIds));
		// }
		if (StringUtils.isNotBlank(resourceName)) {
			criteria.add(Restrictions.like("resourceName", "%" + resourceName
					+ "%"));
		}
		if (StringUtils.isNotBlank(targetName)) {
			criteria.add(Restrictions
					.like("targetName", "%" + targetName + "%"));
		}
		if (StringUtils.isNotBlank(operationCode)) {
			criteria.add(Restrictions.like("operationCode", "%" + operationCode
					+ "%"));
		}
		String[] client = { TypeDefinition.CLIENT_TYPE_OMC,
				TypeDefinition.CLIENT_TYPE_CS, TypeDefinition.CLIENT_TYPE_SGC };

		if (StringUtils.isNotBlank(resourceType)) {
			criteria.add(Restrictions.eq("resourceType", resourceType));
		} else {
			if (type.equals(TypeDefinition.LOG_FROM_CLIENT)) {
				criteria.add(Restrictions.in("resourceType", client));
			} else if (type.equals(TypeDefinition.LOG_FROM_SERVER)) {
				criteria.add(Restrictions.not(Restrictions.in("resourceType",
						client)));
			}
		}
		if (StringUtils.isNotBlank(operationType)) {
			if (operationType.equals("create")
					|| operationType.equals("delete")
					|| operationType.equals("register")
					|| operationType.equals("output")
					|| operationType.equals("save")
					|| operationType.equals("set")
					|| operationType.equals("view")
					|| operationType.equals("editWallLayout")
					|| operationType.equals("update")) {
				criteria.add(Restrictions.eq("operationType", operationType));
			} else {
				List<String> list = new ArrayList<String>();
				list.add("create");
				list.add("delete");
				list.add("register");
				list.add("output");
				list.add("save");
				list.add("set");
				list.add("view");
				list.add("editWallLayout");
				list.add("update");
				criteria.add(Restrictions.not(Restrictions.in("operationType",
						list)));
			}
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("createTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.le("createTime", endTime));
		}
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public Integer findTotalCount(String organId, String resourceName,
			Long startTime, Long endTime, String[] organIds, String targetName,
			String operationCode, String operationType, String resourceType,
			String type) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SysLog.class);

		// if (!isAdmin) {
		// if (StringUtils.isNotBlank(organId)) {
		// criteria.add(Restrictions.eq("organId", organId));
		// } else {
		criteria.add(Restrictions.in("organId", organIds));
		// }
		// }
		if (StringUtils.isNotBlank(resourceName)) {
			criteria.add(Restrictions.like("resourceName", "%" + resourceName
					+ "%"));
		}
		if (StringUtils.isNotBlank(targetName)) {
			criteria.add(Restrictions
					.like("targetName", "%" + targetName + "%"));
		}
		if (StringUtils.isNotBlank(operationCode)) {
			criteria.add(Restrictions.like("operationCode", "%" + operationCode
					+ "%"));
		}
		if (null != startTime) {
			criteria.add(Restrictions.ge("createTime", startTime));
		}
		if (null != endTime) {
			criteria.add(Restrictions.le("createTime", endTime));
		}
		String[] client = { TypeDefinition.CLIENT_TYPE_OMC,
				TypeDefinition.CLIENT_TYPE_CS, TypeDefinition.CLIENT_TYPE_SGC };

		if (StringUtils.isNotBlank(resourceType)) {
			criteria.add(Restrictions.eq("resourceType", resourceType));
		} else {
			if (type.equals(TypeDefinition.LOG_FROM_CLIENT)) {
				criteria.add(Restrictions.in("resourceType", client));
			} else if (type.equals(TypeDefinition.LOG_FROM_SERVER)) {
				criteria.add(Restrictions.not(Restrictions.in("resourceType",
						client)));
			}
		}

		if (StringUtils.isNotBlank(operationType)) {
			if (operationType.equals("create")
					|| operationType.equals("delete")
					|| operationType.equals("register")
					|| operationType.equals("output")
					|| operationType.equals("save")
					|| operationType.equals("set")
					|| operationType.equals("view")
					|| operationType.equals("editWallLayout")
					|| operationType.equals("update")) {
				criteria.add(Restrictions.eq("operationType", operationType));
			} else {
				List<String> list = new ArrayList<String>();
				list.add("create");
				list.add("delete");
				list.add("register");
				list.add("output");
				list.add("save");
				list.add("set");
				list.add("view");
				list.add("editWallLayout");
				list.add("update");
				criteria.add(Restrictions.not(Restrictions.in("operationType",
						list)));
			}
		}
		criteria.setProjection(Projections.rowCount());
		Integer total = (Integer) criteria.uniqueResult();
		return total;
	}

	@Override
	public Object getEntityByIdAndType(Object id, String type)
			throws BusinessException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		try {
			Criteria criteria = session.createCriteria(type);
			criteria.add(Restrictions.eq("id", id));
			List list = criteria.list();
			if (list.size() <= 0) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, type
						+ "[" + id + "] not found !");
			}
			return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<TopRealPlayLog> topRealPlay() {
		Session session = getSession();
		Query sqlQuery = session.createSQLQuery(SqlFactory.getInstance()
				.topRealPlay());
		List<Object[]> list = sqlQuery.list();
		List<TopRealPlayLog> rtnList = new LinkedList<TopRealPlayLog>();
		for (Object[] row : list) {
			TopRealPlayLog top = new TopRealPlayLog();
			top.setCount(NumberUtil.getInteger(row[0]));
			top.setId(MyStringUtil.object2StringNotNull(row[1]));
			top.setName(MyStringUtil.object2StringNotNull(row[2]));
			top.setStandardNumber(MyStringUtil.object2StringNotNull(row[3]));
			top.setType(MyStringUtil.object2StringNotNull(row[4]));
			top.setSubType(MyStringUtil.object2StringNotNull(row[5]));
			top.setParentId(MyStringUtil.object2StringNotNull(row[6]));
			top.setManufacturerId(MyStringUtil.object2StringNotNull(row[7]));
			top.setLocation(MyStringUtil.object2StringNotNull(row[8]));
			top.setChannelNumber(MyStringUtil.object2StringNotNull(row[9]));
			top.setTransport(MyStringUtil.object2StringNotNull(row[10]));
			top.setMode(MyStringUtil.object2StringNotNull(row[11]));
			rtnList.add(top);
		}
		return rtnList;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";
		System.out.println(s.getBytes("utf8").length);
	}
}
