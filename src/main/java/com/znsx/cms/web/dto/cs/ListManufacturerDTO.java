package com.znsx.cms.web.dto.cs;

import java.util.ArrayList;
import java.util.List;

import com.znsx.cms.persistent.model.Manufacturer;
import com.znsx.cms.web.dto.BaseDTO;

/**
 * 查询厂商列表接口返回实体
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:50:00
 */
public class ListManufacturerDTO extends BaseDTO {
	private Integer totalCount;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	private List<Manufacturer> listManufacturer = new ArrayList<Manufacturer>();

	public List<Manufacturer> getListManufacturer() {
		return listManufacturer;
	}

	public void setListManufacturer(List<Manufacturer> listManufacturer) {
		this.listManufacturer = listManufacturer;
	}

}
