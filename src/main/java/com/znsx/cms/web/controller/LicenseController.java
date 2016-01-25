package com.znsx.cms.web.controller;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.LicenseManager;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.omc.LicenseDTO;
import com.znsx.util.licence.LicenceUtil;

/**
 * License对外接口控制类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午8:01:34
 */
@Controller
public class LicenseController extends BaseController {
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SysLogManager sysLogManager;

	@InterfaceDescription(logon = false, method = "Upload_License", cmd = "2151")
	@RequestMapping("/upload_license.json")
	public void uploadLicense(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 检查是否文件上传请求
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 登陆的用户
			ResourceVO resource = null;
			// 是否带有Filedata参数部分的标志
			boolean uploadFlag = false;
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
				// 文件流部分
				if ("Filedata".equals(fieldName)) {
					uploadFlag = true;
					InputStream in = item.getInputStream();
					License lic = LicenceUtil.parseLicense(in);

					licenseManager.checkLicense(lic);
					String id = licenseManager.upload(lic);

					// 单独记录成功的日志
					SysLog log = new SysLog();
					log.setResourceId(resource.getId());
					log.setResourceName(resource.getName());
					log.setResourceType(resource.getType());
					log.setTargetId(id.toString());
					log.setTargetName("License");
					log.setTargetType("License");
					log.setLogTime(System.currentTimeMillis());
					log.setOperationType("uploadLicense");
					log.setOperationName("上传License文件");
					log.setOperationCode("2151");
					log.setSuccessFlag(ErrorCode.SUCCESS);
					log.setCreateTime(System.currentTimeMillis());
					log.setOrganId(resource.getOrganId());
					sysLogManager.batchLog(log);
				}
			}
			if (!uploadFlag) {
				throw new BusinessException(
						ErrorCode.MISSING_PARAMETER_FILEDATA,
						"Parameter [Filedata] not found !");
			}
		} else {
			throw new BusinessException(ErrorCode.NOT_MULTIPART_REQUEST,
					"Not multipart request !");
		}

		// 返回
		BaseDTO dto = new BaseDTO();
		dto.setCmd("2151");
		dto.setMethod("Upload_License");
		writePage(response, dto);
	}

	@InterfaceDescription(logon = true, method = "Get_License", cmd = "2150")
	@RequestMapping("/get_license.json")
	public void getLicense(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		License license = licenseManager.getLicense();

		LicenseDTO dto = new LicenseDTO();
		dto.setCmd("2150");
		dto.setMethod("Get_License");
		dto.setCameraAmount(license.getCameraAmount());
		dto.setUserAmount(license.getUserAmount());
		dto.setDeviceAmount(license.getDeviceAmount());
		dto.setExpireTime(license.getExpireTime());
		dto.setProjectName(license.getProjectName());
		dto.setLinkMan(license.getLinkMan());
		dto.setContact(license.getContact());

		writePage(response, dto);
	}
}
