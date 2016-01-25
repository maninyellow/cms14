package com.znsx.util.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * Http+xml请求的解析工具类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午5:28:13
 */
public class RequestUtil {
	/**
	 * 解析xml请求
	 * 
	 * @author HBJ <br />
	 *         Create at 2013 下午5:32:45
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public static Document parseRequest(HttpServletRequest request)
			throws BusinessException {
		ServletInputStream in = null;
		try {
			in = request.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Network io error !");
		}

		int length = request.getContentLength();
		if (length <= 0) {
			throw new BusinessException(ErrorCode.CONTENT_LENGTH_NOT_FOUND,
					"Content length is missing !");
		}

		ByteBuffer buffer = ByteBuffer.allocate(length);
		byte[] temp = new byte[1024];
		int count = 0;
		try {
			while ((count = in.read(temp)) >= 0) {
				buffer.put(temp, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Network io error !");
		}
		buffer.flip();
		byte[] data = buffer.array();
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			InputStream stream = new ByteArrayInputStream(data);
			doc = builder.build(stream);
		} catch (Exception e) {
			try {
				System.out.println("utf8 parse error ! try to use gbk parse.");
				String xml = new String(data, "gbk");
				StringReader sr = new StringReader(xml);
				InputSource is = new InputSource(sr);
				doc = builder.build(is);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new BusinessException(ErrorCode.ERROR, e1.getMessage());
			}
		}
		return doc;
	}
}
