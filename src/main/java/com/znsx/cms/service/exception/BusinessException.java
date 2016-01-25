package com.znsx.cms.service.exception;

/**
 * 自定义业务异常
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-20 下午01:47:30
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3454602996515968908L;

	/**
	 * 构造方法
	 * 
	 * @param 错误编码
	 * @param 错误信息描述
	 */
	public BusinessException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
