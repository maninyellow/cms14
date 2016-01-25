package com.znsx.cms.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.xml.ElementUtil;

/**
 * 图片对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:28:26
 */
@Controller
public class ImageController extends BaseController {
	@Autowired
	private UserManager userManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private SysLogManager sysLogManager;

	@InterfaceDescription(logon = false, method = "Upload_Image", cmd = "1014")
	@RequestMapping("/upload_image.xml")
	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Image image = null;
		// 检查是否文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 登陆的用户
			ResourceVO resource = null;
			// 资源ID
			String resourceId = null;
			// 资源类型
			String resourceType = null;
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
			// 图片数据流
			InputStream in = null;
			// 解析请求
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String fieldName = item.getFieldName();

				// 简单文本参数部分，得到sessionId
				if ("sessionId".equals(fieldName)) {
					String sessionId = item.getString();
					if (StringUtils.isBlank(sessionId)) {
						throw new BusinessException(
								ErrorCode.PARAMETER_NOT_FOUND,
								"missing [sessionId]");
					}
					// 验证sessionId
					resource = userManager.checkSession(sessionId);
				}
				// 简单文本参数部分，得到resourceId
				if ("resourceId".equals(fieldName)) {
					resourceId = item.getString();
				}
				// 简单文本参数部分，得到resourceType
				if ("resourceType".equals(fieldName)) {
					resourceType = item.getString();
				}
				// 文件流部分
				if ("Filedata".equals(fieldName)) {
					uploadFlag = true;
					in = item.getInputStream();
				}
			}
			if (!uploadFlag) {
				throw new BusinessException(
						ErrorCode.MISSING_PARAMETER_FILEDATA,
						"Parameter [Filedata] not found !");
			}
			if (StringUtils.isBlank(resourceId)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [resourceId]");
			}
			if (StringUtils.isBlank(resourceType)) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [resourceType]");
			}
			// 查询对应的资源是否已经存在图片关联,不存在则新建一个，并且关联
			image = imageManager.getResourceImageId(resourceId, resourceType,
					resource.getId());
			// 图片流写入到数据库
			imageManager.upload(image.getId(), in);

			// 单独记录成功的日志
			SysLog log = new SysLog();
			log.setResourceId(resource.getId());
			log.setResourceName(resource.getName());
			log.setResourceType(resource.getType());
			log.setTargetId(resourceId);
			log.setTargetName(image.getName());
			log.setTargetType(resourceType);
			log.setLogTime(System.currentTimeMillis());
			log.setOperationType("uploadImage");
			log.setOperationName("上传图片");
			log.setOperationCode("1014");
			log.setSuccessFlag(ErrorCode.SUCCESS);
			log.setCreateTime(System.currentTimeMillis());
			log.setOrganId(resource.getOrganId());
			sysLogManager.batchLog(log);
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("1014");
		dto.setMethod("Upload_Image");
		if (null != image) {
			dto.setMessage(image.getId().toString());
		}

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Download_Image", cmd = "1017")
	@RequestMapping("/download_image.xml")
	public void downloadImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String imageId = request.getParameter("imageId");
		if (StringUtils.isBlank(imageId)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [imageId]");
		}

		Image image = imageManager.getImage(imageId);
		// 返回
		response.setContentLength((int) image.getContent().length());
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
		InputStream in = image.getContent().getBinaryStream();
		byte[] buffer = new byte[4096];
		int temp = 0;
		while ((temp = in.read(buffer)) > 0) {
			out.write(buffer, 0, temp);
		}
		out.flush();
		out.close();
		in.close();
	}

	@InterfaceDescription(logon = true, method = "Download_Image", cmd = "1015")
	@RequestMapping("/download_base64_image.xml")
	public void downloadBase64Image(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnXml = GetImageStr();
		Document doc = new Document();
		Element root = new Element("Response");
		root.setText(returnXml);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	public String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d:\\lkjx.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Base64Encoder encoder = new Base64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

}
