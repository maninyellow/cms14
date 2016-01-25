package com.znsx.cms.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PresetDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.Preset;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ImageManager;

/**
 * 图片业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午7:44:54
 */
public class ImageManagerImpl extends BaseManagerImpl implements ImageManager {
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private PresetDAO presetDAO;
	@Autowired
	private CameraDAO cameraDAO;

	@Override
	public void upload(String id, InputStream in) throws BusinessException {
		try {
			Image image = imageDAO.findById(id);
			image.setContent(Hibernate.createBlob(in));
			imageDAO.update(image);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"image stream read error !");
		}
	}

	@Override
	public String createImage(String name, String userId)
			throws BusinessException {
		Image image = new Image();
		image.setCreateTime(System.currentTimeMillis());
		image.setName(name);
		image.setUserId(userId);
		imageDAO.save(image);
		return image.getId();
	}

	@Override
	public Image getImage(String id) throws BusinessException {
		return imageDAO.findById(id);
	}

	@Override
	public Image getResourceImageId(String resourceId, String resourceType,
			String userId) throws BusinessException {
		Image image = new Image();
		if (TypeDefinition.RESOURCE_TYPE_CAMERA.equals(resourceType)) {
			Camera camera = cameraDAO.findById(resourceId);
			if (camera.getProperty().getImageId() == null) {
				String id = createImage(camera.getName(), userId);
				camera.getProperty().setImageId(id);
			}
			image.setId(camera.getProperty().getImageId());
			image.setName(camera.getName());
		} else if (TypeDefinition.RESOURCE_TYPE_USER.equals(resourceType)) {
			User user = userDAO.findById(resourceId);
			if (user.getImageId() == null) {
				String id = createImage(user.getLogonName(), userId);
				user.setImageId(id);
			}
			image.setId(user.getImageId());
			image.setName(StringUtils.isBlank(user.getName()) ? user
					.getLogonName() : user.getName());
		} else if (TypeDefinition.RESOURCE_TYPE_ORGAN.equals(resourceType)) {
			Organ organ = organDAO.findById(resourceId);
			if (organ.getImageId() == null) {
				String id = createImage(organ.getName(), userId);
				organ.setImageId(id);
			}
			image.setId(organ.getImageId());
			image.setName(organ.getName());
		} else if (TypeDefinition.RESOURCE_TYPE_PRESET.equals(resourceType)) {
			String id = resourceId;
			Preset preset = presetDAO.findById(id);
			if (preset.getImageId() == null) {
				String imageId = createImage(preset.getName(), userId);
				preset.setImageId(imageId);
			}
			image.setId(preset.getImageId());
			image.setName(preset.getName());
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter resourceType[" + resourceType + "] invalid !");
		}
		return image;
	}
}
