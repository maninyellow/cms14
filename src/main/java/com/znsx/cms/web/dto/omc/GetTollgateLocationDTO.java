package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.service.model.WfsTollgateVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetTollgateLocationDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午3:25:16
 */
public class GetTollgateLocationDTO extends BaseDTO {
	private List<WfsTollgateVO> gisTollgate;

	public List<WfsTollgateVO> getGisTollgate() {
		return gisTollgate;
	}

	public void setGisTollgate(List<WfsTollgateVO> gisTollgate) {
		this.gisTollgate = gisTollgate;
	}

}
