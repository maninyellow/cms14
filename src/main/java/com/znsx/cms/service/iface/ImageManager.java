package com.znsx.cms.service.iface;

import java.io.InputStream;

import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 图片业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午7:44:04
 */
public interface ImageManager extends BaseManager {
	/**
	 * 上传图片
	 * 
	 * @param id
	 *            图片ID
	 * @param in
	 *            图片输入流
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:09:41
	 */
	public void upload(String id, InputStream in) throws BusinessException;

	/**
	 * 创建图片
	 * 
	 * @param name
	 *            图片名称
	 * @param userId
	 *            创建用户ID
	 * @return 创建成功后生成的图片ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:09:35
	 */
	public String createImage(String name, String userId)
			throws BusinessException;

	/**
	 * 根据ID获取图片
	 * 
	 * @param id
	 *            图片ID
	 * @return 图片对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:18:39
	 */
	public Image getImage(String id) throws BusinessException;

	/**
	 * 获取资源关联的图片,不存在则新建一个，并且关联
	 * 
	 * @param resourceId
	 *            资源ID
	 * @param resourceType
	 *            资源类型
	 * @param userId
	 *            操作的用户ID
	 * @return 资源关联的图片
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:53:34
	 */
	public Image getResourceImageId(String resourceId, String resourceType,
			String userId) throws BusinessException;
}
