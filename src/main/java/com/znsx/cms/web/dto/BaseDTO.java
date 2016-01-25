package com.znsx.cms.web.dto;

/**
 * BaseDTO(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 下午02:09:16
 */
public class BaseDTO {
	private String method;
	private String cmd;
	private String code = "200";
	private String message;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

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
