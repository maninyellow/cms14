package com.znsx.cms.persistent.dao.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.SqlFactory;
import com.znsx.cms.persistent.model.Image;

/**
 * 图片数据库接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午7:41:18
 */
public class ImageDAOImpl extends BaseDAOImpl<Image, String> implements
		ImageDAO {
	@Override
	public void deleteCameraPresetImage(String cameraId) {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.deleteCameraPresetImage());
		query.setString(0, cameraId);
		query.executeUpdate();
	}

	@Override
	public Map<String, List<String>> mapUserImage() {
		Session session = getSession();
		Query query = session.createSQLQuery(SqlFactory.getInstance()
				.mapUserImage());
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<Object[]> list = query.list();
		for (Object[] row : list) {
			List<String> value = map.get(row[0]);
			if (value == null) {
				value = new LinkedList<String>();
			}
			value.add((String) row[1]);
			map.put((String) row[0], value);
		}

		return map;
	}
}
