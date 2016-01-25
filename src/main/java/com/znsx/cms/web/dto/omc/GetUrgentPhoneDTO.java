package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetUrgentPhoneVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetUrgentPhoneDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:52:44
 */
public class GetUrgentPhoneDTO extends BaseDTO {
	private GetUrgentPhoneVO urgentPhone;

	public GetUrgentPhoneVO getUrgentPhone() {
		return urgentPhone;
	}

	public void setUrgentPhone(GetUrgentPhoneVO urgentPhone) {
		this.urgentPhone = urgentPhone;
	}
}
