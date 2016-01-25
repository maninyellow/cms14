package com.znsx.cms.web.controller;

import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;

import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.util.xml.MyXMLOutputter;

/**
 * BaseController(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-27 下午02:26:35
 */
@Controller
public class BaseController {
	public final static ThreadLocal<ResourceVO> resource = new ThreadLocal<ResourceVO>();

	public static Logger log = Logger.getLogger(BaseController.class);

	/**
	 * 返回xml，带有Content-length头
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param doc
	 *            xml内容
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:14:52
	 */
	public void writePageWithContentLength(HttpServletResponse response,
			Document doc) throws Exception {
		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		String body = out.outputString(doc);
		if (log.isDebugEnabled()) {
			log.debug(body);
		}
		response.setContentLength(body.getBytes("UTF-8").length);
		response.setContentType("application/xml");
		response.getWriter().write(body);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 返回json
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param dto
	 *            返回对象
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:14:12
	 */
	public void writePage(HttpServletResponse response, BaseDTO dto)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		String rtn = JSONObject.fromObject(dto).toString();
		response.setContentLength(rtn.getBytes("UTF-8").length);
		response.getWriter().write(rtn);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 使用gzip压缩方式返回数据
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param doc
	 *            xml内容
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:33:52
	 */
	public void writePageUseGzip(HttpServletResponse response, Document doc)
			throws Exception {
		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		MyXMLOutputter out = new MyXMLOutputter(format);

		response.setHeader("Content-Encoding", "gzip");

		GZIPOutputStream outZip = new GZIPOutputStream(
				response.getOutputStream());
		out.output(doc, outZip);
		outZip.finish();
		outZip.flush();
	}
	
	/**
	 * 返回GBK编码的xml结果
	 * @param response
	 * @param doc
	 * @throws Exception
	 * @author huangbuji <p />
	 * Create at 2015-4-3 下午4:21:23
	 */
	public void writePageWithContentLengthGBK(HttpServletResponse response,
			Document doc) throws Exception {
		Format format = Format.getRawFormat();
		format.setEncoding("GBK");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		String body = out.outputString(doc);
		if (log.isDebugEnabled()) {
			log.debug(body);
		}
		response.setCharacterEncoding("GBK");
		response.setContentLength(body.getBytes("GBK").length);
		response.setContentType("application/xml");
		response.getWriter().write(body);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
