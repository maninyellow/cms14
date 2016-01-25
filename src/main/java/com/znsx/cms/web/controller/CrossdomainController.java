package com.znsx.cms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Flash安全沙箱设置
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-26 上午9:24:40
 */
@Controller
public class CrossdomainController extends BaseController {

	@RequestMapping("/crossdomain.xml")
	public void main(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 返回策略文件
		Document rtn = generateXML();
		writePageWithContentLength(response, rtn);
	}

	/**
	 * 生成crossdomain.xml
	 * 
	 * <pre>
	 * {@code
	 * <cross-domain-policy>
	 *   <allow-access-from domain="*"/>
	 * </cross-domain-policy>
	 * }
	 * </pre>
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-26 上午9:26:08
	 */
	private Document generateXML() {

		Document doc = new Document();
		Element root = new Element("cross-domain-policy");
		doc.setRootElement(root);

		Element success = new Element("allow-access-from");
		success.setAttribute("domain", "*");
		root.addContent(success);
		return doc;
	}
}
