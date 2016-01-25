package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.AddressBook;
import com.znsx.cms.web.dto.omc.GetAddressBookVO;

/**
 * AddressBookDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:19:53
 */
public interface AddressBookDAO extends BaseDAO<AddressBook, String> {

	/**
	 * 获取通讯录总数
	 * 
	 * @param linkMan
	 *            联系人
	 * @param organId
	 *            机构id
	 * @return 总数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:28:25
	 */
	public Integer addressBookTotalCount(String linkMan, String organId);

	/**
	 * 获取通讯录列表
	 * 
	 * @param linkMan
	 *            联系人
	 * @param organId
	 *            机构id
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 通讯录列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午5:43:38
	 */
	public List<AddressBook> listAddressBook(String linkMan, String organId,
			Integer startIndex, Integer limit);

	/**
	 * 批量插入通讯录集合
	 * 
	 * @param ab
	 *            通讯录
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:11:28
	 */
	public void batchInsertAddressBook(AddressBook ab);

	/**
	 * 执行批量插入
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:13:42
	 */
	public void excuteBatchAddressBook();

}
