package com.znsx.util.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 简单HTTP请求解析类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-16 下午3:29:29
 */
public class SimpleRequestReader {

	private HttpServletRequest request;

	public SimpleRequestReader(HttpServletRequest request) {
		this.request = request;
	}

	protected SimpleRequestReader() {

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取字符串参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return String
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:51:34
	 */
	public String getString(String param, boolean nullable) {
		String s = request.getParameter(param);
		if (!nullable) {
			if (null == s) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
		}
		return s;
	}

	/**
	 * 获取Short参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Short
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:52:40
	 */
	public Short getShort(String param, boolean nullable) {
		Short num = null;
		String s = request.getParameter(param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Short.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	/**
	 * 获取Integer参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Integer
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:53:51
	 */
	public Integer getInteger(String param, boolean nullable) {
		Integer num = null;
		String s = request.getParameter(param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	/**
	 * 获取Integer参数
	 * 
	 * @param param
	 *            参数名称
	 * @param nullable
	 *            是否可以为空
	 * @return Long
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-16 下午3:54:45
	 */
	public Long getLong(String param, boolean nullable) {
		Long num = null;
		String s = request.getParameter(param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Long.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

	public Double getDouble(String param, boolean nullable) {
		Double num = null;
		String s = request.getParameter(param);
		if (StringUtils.isBlank(s)) {
			if (!nullable) {
				throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
						"missing [" + param + "]");
			}
			return num;
		}
		try {
			num = Double.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Parameter " + param + "[" + s + "] invalid !");
		}
		return num;
	}

}
