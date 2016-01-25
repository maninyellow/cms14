package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceIs;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceRail;
import com.znsx.cms.persistent.model.ControlDeviceRd;
import com.znsx.cms.persistent.model.ControlDeviceTsl;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.LogCmsVO;
import com.znsx.util.string.MyStringUtil;

/**
 * 控制设备数据库接口实现类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:29:59
 */
public class ControlDeviceDAOImpl extends BaseDAOImpl<ControlDevice, String>
		implements ControlDeviceDAO {

	public static List<ControlDeviceLight> vectorLight = new Vector<ControlDeviceLight>();

	public static List<ControlDeviceWp> vectorWp = new Vector<ControlDeviceWp>();

	public static List<ControlDeviceFan> vectorFan = new Vector<ControlDeviceFan>();

	public static List<ControlDeviceLil> vectorLil = new Vector<ControlDeviceLil>();

	@Override
	public Map<String, ControlDevice> mapCDByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDevice.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.addOrder(Order.desc("createTime"));
		List<ControlDevice> list = criteria.list();
		Map<String, ControlDevice> map = new LinkedHashMap<String, ControlDevice>();
		for (ControlDevice cd : list) {
			map.put(cd.getId().toString(), cd);
		}
		return map;
	}

	@Override
	public Integer countControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Short type, Short subType) {
		Session session = getSession();
		Criteria criteria = null;
		if (type != null) {
			if (type == TypeDefinition.DEVICE_TYPE_CMS) {
				criteria = session.createCriteria(ControlDeviceCms.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_FAN) {
				criteria = session.createCriteria(ControlDeviceFan.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_LIGHT) {
				criteria = session.createCriteria(ControlDeviceLight.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_RD) {
				criteria = session.createCriteria(ControlDeviceRd.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_WP) {
				criteria = session.createCriteria(ControlDeviceWp.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_RAIL) {
				criteria = session.createCriteria(ControlDeviceRail.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_IS) {
				criteria = session.createCriteria(ControlDeviceIs.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_TSL) {
				criteria = session.createCriteria(ControlDeviceTsl.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_LIL) {
				criteria = session.createCriteria(ControlDeviceLil.class);
			}
		} else {
			criteria = session.createCriteria(ControlDevice.class);
		}
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
		if (null != subType) {
			criteria.add(Restrictions.eq("subType", subType));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.addOrder(Order.asc("id"));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ControlDevice> listControlDevice(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit, Short type, Short subType) {
		Session session = getSession();
		Criteria criteria = null;
		// 分页查询设备
		if (type != null) {
			if (type == TypeDefinition.DEVICE_TYPE_CMS) {
				criteria = session.createCriteria(ControlDeviceCms.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_FAN) {
				criteria = session.createCriteria(ControlDeviceFan.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_LIGHT) {
				criteria = session.createCriteria(ControlDeviceLight.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_RD) {
				criteria = session.createCriteria(ControlDeviceRd.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_WP) {
				criteria = session.createCriteria(ControlDeviceWp.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_RAIL) {
				criteria = session.createCriteria(ControlDeviceRail.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_IS) {
				criteria = session.createCriteria(ControlDeviceIs.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_TSL) {
				criteria = session.createCriteria(ControlDeviceTsl.class);
			} else if (type == TypeDefinition.DEVICE_TYPE_LIL) {
				criteria = session.createCriteria(ControlDeviceLil.class);
			}
		} else {
			criteria = session.createCriteria(ControlDevice.class);
		}
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
		if (null != subType) {
			criteria.add(Restrictions.eq("subType", subType));
		}
		criteria.add(Restrictions.eq("organ.id", organId));
		// criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		List<ControlDevice> cds = criteria.list();
		return cds;
	}

	@Override
	public List<ControlDevice> listByDAS(String dasId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDevice.class);
		criteria.add(Restrictions.eq("das.id", dasId));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public Map<String, ControlDevice> mapCDBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDevice.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		List<ControlDevice> list = criteria.list();
		Map<String, ControlDevice> map = new LinkedHashMap<String, ControlDevice>();
		for (ControlDevice cd : list) {
			map.put(cd.getStandardNumber(), cd);
		}
		return map;
	}

	@Override
	public void deleteRoleCDPermission(String id, String type) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDetectorPermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.setString(1, type);
		q.executeUpdate();

	}

	@Override
	public List<ControlDevice> listCMSBySNs(String[] sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDeviceCms.class);
		criteria.add(Restrictions.in("standardNumber", sns));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.list();
	}

	@Override
	public List<ControlDevice> cmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType,
			Integer start, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDeviceCms.class);
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
		if (null != subType) {
			criteria.add(Restrictions.eq("subType", subType));
		}
		criteria.addOrder(Order.desc("createTime"));
		// criteria.add(Restrictions.eq("type",
		// Short.valueOf((short) TypeDefinition.DEVICE_TYPE_CMS)));
		criteria.setFirstResult(start.intValue());
		criteria.setMaxResults(limit.intValue());
		return criteria.list();
	}

	@Override
	public Integer countCmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDeviceCms.class);
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
		if (null != subType) {
			criteria.add(Restrictions.eq("subType", subType));
		}
		// criteria.add(Restrictions.eq("type",
		// Short.valueOf((short) TypeDefinition.DEVICE_TYPE_CMS)));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public String[] countCD(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDevice.class);
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
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ControlDevice.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	public List<ControlDevice> listControlDevices(List<String> organIds,
			int type) {
		Session session = getSession();
		Criteria criteria = null;
		switch (type) {
		case TypeDefinition.DEVICE_TYPE_CMS:
			criteria = session.createCriteria(ControlDeviceCms.class);
			break;
		case TypeDefinition.DEVICE_TYPE_FAN:
			criteria = session.createCriteria(ControlDeviceFan.class);
			break;
		default:
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"ControlDevice type[" + type + "] not support !");
		}
		if (organIds != null) {
			criteria.add(Restrictions.in("organ.id", organIds));
		}
		return criteria.list();
	}

	@Override
	public String[] cmsSNByOrgan(String[] organs, String cmsName) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listCmsSN(organs, cmsName));
		int i = 0;
		if (organs.length > 0) {
			for (i = 0; i < organs.length; i++) {
				query.setString(i, organs[i]);
			}
		}
		if (StringUtils.isNotBlank(cmsName)) {
			query.setString(i, "%" + cmsName + "%");
		}
		List<String> list = query.list();
		String[] s = new String[list.size()];
		for (int n = 0; n < list.size(); n++) {
			s[n] = list.get(n);
		}
		return s;
	}

	@Override
	public List<LogCmsVO> listCmsVO(String[] cmsSNs) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance().listCms(
				cmsSNs));
		int i = 0;
		if (cmsSNs.length > 0) {
			for (i = 0; i < cmsSNs.length; i++) {
				query.setString(i, cmsSNs[i]);
			}
		}
		List<Object[]> rows = query.list();
		List<LogCmsVO> cmses = new ArrayList<LogCmsVO>();
		for (Object[] row : rows) {
			i = 0;
			LogCmsVO vo = new LogCmsVO();
			vo.setName(MyStringUtil.object2String(row[i++]));
			vo.setStandardNumber(MyStringUtil.object2String(row[i++]));
			cmses.add(vo);
		}
		return cmses;
	}

	@Override
	public Map<String, String> mapCms(String[] cmsSns) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance().listCms(
				cmsSns));
		int i = 0;
		if (cmsSns.length > 0) {
			for (i = 0; i < cmsSns.length; i++) {
				query.setString(i, cmsSns[i]);
			}
		}
		List<Object[]> rows = query.list();
		Map<String, String> map = new HashMap<String, String>();
		for (Object[] row : rows) {
			map.put((String) row[1], (String) row[0]);
		}
		return map;
	}

	@Override
	public void batchInsertLight(ControlDeviceLight controlDeviceLight) {
		vectorLight.add(controlDeviceLight);
	}

	@Override
	public void excuteBatchLight() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vectorLight) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertLight());
				for (ControlDeviceLight entity : vectorLight) {
					psmtItem.setString(1, entity.getId());
					psmtItem.setString(2, entity.getName());
					psmtItem.setString(3, entity.getOrgan().getId());
					psmtItem.setString(4, entity.getStandardNumber());
					psmtItem.setString(5, entity.getDas().getId());
					psmtItem.setInt(6, entity.getPeriod());
					psmtItem.setString(7, entity.getNavigation());
					psmtItem.setString(8, entity.getStakeNumber());
					psmtItem.setString(9, entity.getIp());
					psmtItem.setInt(10, entity.getPort());
					psmtItem.setString(11, System.currentTimeMillis() + "");
					psmtItem.setString(12, entity.getLongitude());
					psmtItem.setString(13, entity.getLatitude());
					psmtItem.setString(14, entity.getReserve());
					psmtItem.setString(15, entity.getNote());
					psmtItem.setInt(16, entity.getSubType());
					psmtItem.setInt(17, entity.getSectionType());
					psmtItem.setInt(18, TypeDefinition.DEVICE_TYPE_LIGHT);
					psmtItem.addBatch();
				}
				psmtItem.executeBatch();
				conn.commit();
				vectorLight.clear();
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
					if (psmtItem != null) {
						psmtItem.close();
						psmtItem = null;
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

	@Override
	public void batchInsertWp(ControlDeviceWp controlDeviceWp) {
		vectorWp.add(controlDeviceWp);
	}

	@Override
	public void excuteBatchWp() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vectorWp) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertCd());
				for (ControlDeviceWp entity : vectorWp) {
					psmtItem.setString(1, entity.getId());
					psmtItem.setString(2, entity.getName());
					psmtItem.setString(3, entity.getOrgan().getId());
					psmtItem.setString(4, entity.getStandardNumber());
					psmtItem.setString(5, entity.getDas().getId());
					psmtItem.setInt(6, entity.getPeriod());
					psmtItem.setString(7, entity.getNavigation());
					psmtItem.setString(8, entity.getStakeNumber());
					psmtItem.setString(9, entity.getIp());
					psmtItem.setInt(10, entity.getPort());
					psmtItem.setString(11, System.currentTimeMillis() + "");
					psmtItem.setString(12, entity.getLongitude());
					psmtItem.setString(13, entity.getLatitude());
					psmtItem.setString(14, entity.getReserve());
					psmtItem.setString(15, entity.getNote());
					psmtItem.setInt(16, TypeDefinition.DEVICE_TYPE_WP);
					psmtItem.addBatch();
				}
				psmtItem.executeBatch();
				conn.commit();
				vectorWp.clear();
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
					if (psmtItem != null) {
						psmtItem.close();
						psmtItem = null;
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

	@Override
	public void batchInsertFan(ControlDeviceFan controlDeviceFan) {
		vectorFan.add(controlDeviceFan);
	}

	@Override
	public void excuteBatchFan() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vectorFan) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertCd());
				for (ControlDeviceFan entity : vectorFan) {
					psmtItem.setString(1, entity.getId());
					psmtItem.setString(2, entity.getName());
					psmtItem.setString(3, entity.getOrgan().getId());
					psmtItem.setString(4, entity.getStandardNumber());
					psmtItem.setString(5, entity.getDas().getId());
					psmtItem.setInt(6, entity.getPeriod());
					psmtItem.setString(7, entity.getNavigation());
					psmtItem.setString(8, entity.getStakeNumber());
					psmtItem.setString(9, entity.getIp());
					psmtItem.setInt(10, entity.getPort());
					psmtItem.setString(11, System.currentTimeMillis() + "");
					psmtItem.setString(12, entity.getLongitude());
					psmtItem.setString(13, entity.getLatitude());
					psmtItem.setString(14, entity.getReserve());
					psmtItem.setString(15, entity.getNote());
					psmtItem.setInt(16, TypeDefinition.DEVICE_TYPE_FAN);
					psmtItem.addBatch();
				}
				psmtItem.executeBatch();
				conn.commit();
				vectorFan.clear();
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
					if (psmtItem != null) {
						psmtItem.close();
						psmtItem = null;
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

	@Override
	public void batchInsertLil(ControlDeviceLil controlDeviceLil) {
		vectorLil.add(controlDeviceLil);
	}

	@Override
	public void excuteBatchLil() {
		Connection conn = null;
		PreparedStatement psmtItem = null;

		synchronized (vectorLil) {
			try {
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtItem = conn.prepareStatement(SqlFactory.getInstance()
						.insertCd());
				for (ControlDeviceLil entity : vectorLil) {
					psmtItem.setString(1, entity.getId());
					psmtItem.setString(2, entity.getName());
					psmtItem.setString(3, entity.getOrgan().getId());
					psmtItem.setString(4, entity.getStandardNumber());
					psmtItem.setString(5, entity.getDas().getId());
					psmtItem.setInt(6, entity.getPeriod());
					psmtItem.setString(7, entity.getNavigation());
					psmtItem.setString(8, entity.getStakeNumber());
					psmtItem.setString(9, entity.getIp());
					psmtItem.setInt(10, entity.getPort());
					psmtItem.setString(11, System.currentTimeMillis() + "");
					psmtItem.setString(12, entity.getLongitude());
					psmtItem.setString(13, entity.getLatitude());
					psmtItem.setString(14, entity.getReserve());
					psmtItem.setString(15, entity.getNote());
					psmtItem.setInt(16, TypeDefinition.DEVICE_TYPE_LIL);
					psmtItem.addBatch();
				}
				psmtItem.executeBatch();
				conn.commit();
				vectorLil.clear();
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
					if (psmtItem != null) {
						psmtItem.close();
						psmtItem = null;
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
}
