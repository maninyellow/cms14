package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListAddressBookDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午5:50:22
 */
public class ListAddressBookDTO extends BaseDTO {
	private List<GetAddressBookVO> addressBookList;
	private String totalCount;

	public List<GetAddressBookVO> getAddressBookList() {
		return addressBookList;
	}

	public void setAddressBookList(List<GetAddressBookVO> addressBookList) {
		this.addressBookList = addressBookList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
