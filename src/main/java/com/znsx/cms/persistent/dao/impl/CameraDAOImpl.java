package com.znsx.cms.persistent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Dvr;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.CameraStatusVO;
import com.znsx.cms.service.model.Channel;
import com.znsx.util.string.MyStringUtil;

/**
 * 摄像头数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:37:00
 */
public class CameraDAOImpl extends BaseDAOImpl<Camera, String> implements
		CameraDAO {

	/**
	 * 存储新增摄像头的列表，直到调用excuteBatch方法才批量写入数据库
	 */
	public static List<Camera> vector = new Vector<Camera>();

	@Override
	public List<Camera> listCamera(String organId, String name) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		return criteria.list();
	}

	@Override
	public List<Camera> findCameraByDvrId(String dvrId, Integer startIndex,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("parent.id", dvrId));
		// criteria.addOrder(Order.asc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer cameraTotalCount(String dvrId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("parent.id", dvrId));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	public List<Camera> listDvrsCamera(List<String> dvrIds) {
		if (dvrIds.size() > 0) {
			Session session = getSession();
			Criteria criteria = session.createCriteria(Camera.class);
			criteria.add(Restrictions.in("parent.id", dvrIds));
			return criteria.list();
		}
		return new ArrayList<Camera>();
	}

	@Override
	public void batchInsert(Camera record) throws BusinessException {
		vector.add(record);
	}

	@Override
	public void excuteBatch() throws BusinessException {
		Connection conn = null;
		PreparedStatement psmtCamera = null;
		PreparedStatement psmtProperty = null;

		synchronized (vector) {

			try {
				// conn = SessionFactoryUtils.getDataSource(sessionFactory)
				// .getConnection();
				conn = DataSourceUtils.getConnection(SessionFactoryUtils
						.getDataSource(sessionFactory));
				conn.setAutoCommit(false);
				psmtCamera = conn.prepareStatement(SqlFactory.getInstance()
						.insertCamera());
				psmtProperty = conn.prepareStatement(SqlFactory.getInstance()
						.insertCameraProperty());
				for (Camera camera : vector) {
					// 插入摄像头
					String id = (String) new UUIDHexGenerator().generate(null,
							null);
					psmtCamera.setString(1, id);
					psmtCamera.setString(2, camera.getStandardNumber());
					psmtCamera.setString(3, TypeDefinition.DEVICE_TYPE_CAMERA
							+ "");
					psmtCamera.setString(4, camera.getSubType());
					psmtCamera.setString(5, camera.getName());
					psmtCamera.setString(6, camera.getParent().getId());
					if (camera.getMss() == null) {
						psmtCamera.setNull(7, Types.VARCHAR);
					} else {
						psmtCamera.setString(7, camera.getMss().getId());
					}
					if (camera.getCrs() == null) {
						psmtCamera.setNull(8, Types.VARCHAR);
					} else {
						psmtCamera.setString(8, camera.getCrs().getId());
					}
					psmtCamera.setLong(9, camera.getCreateTime());
					psmtCamera.setInt(10, camera.getChannelNumber());
					if (camera.getOrgan() == null) {
						psmtCamera.setNull(11, Types.VARCHAR);
					} else {
						psmtCamera.setString(11, camera.getOrgan().getId());
					}
					if (camera.getManufacturer() == null) {
						psmtCamera.setNull(12, Types.VARCHAR);
					} else {
						psmtCamera.setString(12, camera.getManufacturer()
								.getId().toString());
					}
					psmtCamera.setInt(13, 0);
					psmtCamera.setString(14, camera.getLocation());
					psmtCamera.setString(15, camera.getNote());
					if (camera.getDeviceModel() == null) {
						psmtCamera.setNull(16, Types.INTEGER);
					} else {
						psmtCamera.setString(16, camera.getDeviceModel()
								.getId());
					}
					if (camera.getNavigation() == null) {
						psmtCamera.setString(17, "0");
					} else {
						psmtCamera.setString(17, camera.getNavigation());
					}
					if (camera.getStakeNumber() == null) {
						psmtCamera.setNull(18, Types.INTEGER);
					} else {
						psmtCamera.setString(18, camera.getStakeNumber());
					}
					psmtCamera.addBatch();
					// 插入Property
					psmtProperty.setString(1, id);
					psmtProperty.setInt(2, camera.getProperty().getStoreType());
					psmtProperty.setString(3, camera.getProperty()
							.getLocalStorePlan() == null ? "" : camera
							.getProperty().getLocalStorePlan());
					psmtProperty.setString(4, camera.getProperty()
							.getCenterStorePlan() == null ? "" : camera
							.getProperty().getCenterStorePlan());
					psmtProperty.setNull(5, Types.INTEGER);
					psmtProperty.addBatch();
				}
				psmtCamera.executeBatch();
				psmtProperty.executeBatch();
				conn.commit();
				vector.clear();
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
					if (psmtCamera != null) {
						psmtCamera.close();
						psmtCamera = null;
					}
					if (psmtProperty != null) {
						psmtProperty.close();
						psmtProperty = null;
					}
					if (conn != null) {
						DataSourceUtils.releaseConnection(conn,
								SessionFactoryUtils
										.getDataSource(sessionFactory));
						// conn.close();
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

	public void deleteRUserDeviceFavorite(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRUserDeviceFavorite();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();
	}

	@Override
	public void deleteRRP(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRRP();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();

	}

	@Override
	public void deletePreset(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deletePreset();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();
	}

	@Override
	public void deleteCamera(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteCamera();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();

	}

	@Override
	public void deleteCameraPresetImage(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deletePresetImage();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();

	}

	@Override
	public void deleteDeviceFavorite(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteDeviceFavorite();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();

	}

	@Override
	public void deleteRoleDevicePermission(String id) {
		Session session = getSession();
		String sql = SqlFactory.getInstance().deleteRoleDevicePermission();
		Query q = session.createSQLQuery(sql);
		q.setString(0, id);
		q.executeUpdate();
	}

	@Override
	public Integer countByDeviceModel(String deviceModelId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("deviceModel.id", deviceModelId));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<Camera> listCameraByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		// Disjunction disjunction = Restrictions.disjunction();
		// for (int i = 0; i < organIds.length; i++) {
		// disjunction.add(Restrictions.eq("organ.id", organIds[i]));
		// }
		// criteria.add(disjunction);
		criteria.setCacheable(false);
		criteria.setCacheMode(CacheMode.IGNORE);
		return criteria.list();
	}

	@Override
	public int countByOrganId(String organId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("organ.id", organId));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<String> findNameByCamera() {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listNameByCamera());
		List<String> list = query.list();
		return list;
	}

	@Override
	public Map<String, Camera> mapCameraByOrganIds(String[] organIds) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		if (null != organIds) {
			criteria.add(Restrictions.in("organ.id", organIds));
		}
		List<Camera> cameras = criteria.list();
		Map<String, Camera> map = new LinkedHashMap<String, Camera>();
		for (Camera c : cameras) {
			map.put(c.getId(), c);
		}
		return map;
	}

	@Override
	public List<Camera> listCameraByDevice(String[] organs, String name,
			String stakeNumber, String manufacturerId, Integer startIndex,
			Integer limit, String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.in("organ.id", organs));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		if (StringUtils.isNotBlank(manufacturerId)) {
			criteria.add(Restrictions.eq("manufacturer.id", manufacturerId));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		// criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	@Override
	public Integer cameraByDeviceTotalCount(String[] organs, String name,
			String stakeNumber, String manufacturerId, String standardNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.in("organ.id", organs));
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		if (StringUtils.isNotBlank(stakeNumber)) {
			criteria.add(Restrictions.like("stakeNumber", "%" + stakeNumber
					+ "%"));
		}
		if (StringUtils.isNotBlank(standardNumber)) {
			criteria.add(Restrictions.like("standardNumber", "%"
					+ standardNumber + "%"));
		}
		if (StringUtils.isNotBlank(manufacturerId)) {
			criteria.add(Restrictions.eq("manufacturer.id", manufacturerId));
		}
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<Channel> listCrsCamera(String crsId, Integer start,
			Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("crs.id", crsId));
		criteria = criteria.createAlias("property", "property");
		criteria.add(Restrictions.isNotNull("property.centerStorePlan"));

		criteria.setFirstResult(start.intValue());
		criteria.setMaxResults(limit.intValue());
		List<Camera> cameras = criteria.list();
		List<Channel> list = new LinkedList<Channel>();
		for (Camera c : cameras) {
			Channel channel = new Channel();
			channel.setSn(c.getStandardNumber());
			channel.setPlan(c.getProperty().getCenterStorePlan());
			channel.setStoreStream(c.getProperty().getStoreStream());
			list.add(channel);
		}
		return list;
	}

	@Override
	public Integer cameraTotalCountByCrsId(String crsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("crs.id", crsId));
		criteria = criteria.createAlias("property", "property");
		criteria.add(Restrictions.isNotNull("property.centerStorePlan"));
		criteria.setProjection(Projections.rowCount());
		Number totalCount = (Number) criteria.uniqueResult();
		return totalCount.intValue();
	}

	@Override
	public List<Camera> listNearCamera(String[] organIds, String navigation,
			String stakeNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.eq("navigation", navigation));
		criteria.add(Restrictions.in("organ.id", organIds));
		criteria.add(Restrictions.isNotNull("stakeNumber"));
		// criteria.add(Restrictions.ne("stakeNumber", stakeNumber));
		return criteria.list();
	}

	@Override
	public List<Camera> listCameraByType(Set<String> organIds,
			List<String> cameraTypes) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		criteria.add(Restrictions.in("organ.id", organIds));
		Disjunction disjunction = Restrictions.disjunction();

		for (String type : cameraTypes) {
			// 主线条件替换为桩号不为空
			if (type.equals("主线")) {
				disjunction.add(Restrictions.isNotNull("stakeNumber"));
			} else {
				disjunction.add(Restrictions.like("name", "%" + type + "%"));
			}
		}
		criteria.add(disjunction);
		return criteria.list();
	}

	@Override
	public List<String> listCameraSN() {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listSNByCamera());
		List<String> list = query.list();
		return list;
	}

	@Override
	public Map<String, Camera> statelessMapCamera() {
		StatelessSession session = null;
		try {
			session = getStatelessSession();
			Criteria criteria = session.createCriteria(Camera.class);
			List<Camera> cameras = criteria.list();
			Map<String, Camera> map = new LinkedHashMap<String, Camera>();
			for (Camera c : cameras) {
				map.put(c.getId(), c);
			}
			return map;
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					"Could not get database connnection");
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}

	@Override
	public Map<String, Camera> mapBySns(Set<String> sns) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		if (sns != null) {
			criteria.add(Restrictions.in("standardNumber", sns));
		}
		List<Camera> list = criteria.list();
		Map<String, Camera> map = new HashMap<String, Camera>();
		for (Camera camera : list) {
			map.put(camera.getStandardNumber(), camera);
		}
		return map;
	}

	@Override
	public List<String> listCameraIdInOrgan(String organId) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.listCameraIdInOrgan());
		query.setString(0, "%" + organId + "%");
		List<String> list = query.list();
		return list;
	}

	@Override
	public List<CameraStatusVO> listLocalCameraStatus() {
		Session session = getSession();
		SQLQuery q = session.createSQLQuery(SqlFactory.getInstance()
				.listLocalCameraStatus());
		List<Object[]> list = q.list();
		List<CameraStatusVO> rtn = new LinkedList<CameraStatusVO>();
		for (Object[] row : list) {
			CameraStatusVO vo = new CameraStatusVO();
			vo.setStandardNumber(MyStringUtil.object2StringNotNull(row[0]));
			vo.setStatus(row[1] == null ? "0" : "1");
			rtn.add(vo);
		}
		return rtn;
	}

	@Override
	public Map<String, Camera> mapCameraNoTransaction(String organId,
			String name) {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		Criteria criteria = session.createCriteria(Camera.class);
		if (StringUtils.isNotBlank(organId)) {
			criteria.add(Restrictions.eq("organ.id", organId));
		}
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", "%" + name + "%"));
		}
		try {
			List<Camera> list = criteria.list();
			Map<String, Camera> map = new HashMap<String, Camera>();
			for (Camera c : list) {
				if (c.getCrs() != null) {
					c.getCrs().getName();
				}
				;
				c.getParent().getLanIp();
				map.put(c.getStandardNumber(), c);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DATABASE_ACCESS_ERROR,
					e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Camera> listCameraByCcs(String ccsId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Dvr.class);
		subCriteria.add(Restrictions.eq("ccs.id", ccsId));
		subCriteria.setProjection(Projections.property("id"));
		criteria.add(Subqueries.propertyIn("parent.id", subCriteria));
		return criteria.list();
	}
	
	@Override
	public List<Camera> listCamerasNoJoin() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Camera.class);
		return criteria.list();
	}
}
