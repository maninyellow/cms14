package com.znsx.cms.web.dto.omc;

import com.znsx.cms.service.model.GetBoxTransformerVO;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetBoxTransformerDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:30:51
 */
public class GetBoxTransformerDTO extends BaseDTO {
	private GetBoxTransformerVO boxTransformer;

	public GetBoxTransformerVO getBoxTransformer() {
		return boxTransformer;
	}

	public void setBoxTransformer(GetBoxTransformerVO boxTransformer) {
		this.boxTransformer = boxTransformer;
	}
}
