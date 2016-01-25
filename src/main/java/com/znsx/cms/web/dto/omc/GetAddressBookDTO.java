package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetAddressBookDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:20:57
 */
public class GetAddressBookDTO extends BaseDTO {
	private GetAddressBookVO addressBook;

	public GetAddressBookVO getAddressBook() {
		return addressBook;
	}

	public void setAddressBook(GetAddressBookVO addressBook) {
		this.addressBook = addressBook;
	}
}
