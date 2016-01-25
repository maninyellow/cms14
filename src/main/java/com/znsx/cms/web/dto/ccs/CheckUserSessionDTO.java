package com.znsx.cms.web.dto.ccs;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * 检查用户会话接口返回对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:25:29
 */
public class CheckUserSessionDTO extends BaseDTO {
	private String priority;

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

}
