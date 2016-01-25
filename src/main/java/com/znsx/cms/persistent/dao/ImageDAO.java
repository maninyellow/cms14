package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.Image;

/**
 * 图片数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午7:40:05
 */
public interface ImageDAO extends BaseDAO<Image, String> {
	/**
	 * 删除摄像头的预置点关联的图片
	 * 
	 * @param cameraId
	 *            摄像头ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:44:58
	 */
	public void deleteCameraPresetImage(String cameraId);

	/**
	 * 查询所有的User与Image映射关系
	 * 
	 * @return key-userId, value-imageId数组
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-16 下午5:51:51
	 */
	public Map<String, List<String>> mapUserImage();
}
