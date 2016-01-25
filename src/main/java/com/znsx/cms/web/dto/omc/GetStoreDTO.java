package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetStoreDTO
 * @author wangbinyu <p />
 * Create at 2014 下午3:22:05
 */
public class GetStoreDTO extends BaseDTO {
	private StoreVO store;

	public StoreVO getStore() {
		return store;
	}

	public void setStore(StoreVO store) {
		this.store = store;
	}
}
