package com.znsx.cms.web.dto.omc;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.persistent.model.LogOperation;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 返回查询系统日志码表列表接口实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午1:44:55
 */
public class ListLogOperationDTO extends BaseDTO {
	public List<LogOperation> getListLogOperation() {
		return listLogOperation;
	}

	public void setListLogOperation(List<LogOperation> listLogOperation) {
		this.listLogOperation = listLogOperation;
	}

	private List<LogOperation> listLogOperation = new ArrayList<LogOperation>();
}
