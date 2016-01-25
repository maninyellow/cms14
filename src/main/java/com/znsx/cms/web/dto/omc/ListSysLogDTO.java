package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询系统日志接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午2:50:40
 */
public class ListSysLogDTO extends BaseDTO {

	private List<SysLog> listSysLog = new ArrayList<SysLog>();
	private String totalCount;

	public List<SysLog> getListSysLog() {
		return listSysLog;
	}

	public void setListSysLog(List<SysLog> listSysLog) {
		this.listSysLog = listSysLog;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
