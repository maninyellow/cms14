package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.User;

/**
 * 用户数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:36:23
 */
public interface UserDAO extends BaseDAO<User, String> {
	/**
	 * 根据机构查询用户列表
	 * 
	 * @param organs
	 *            当前机构以及下级机构ids
	 * @param userName
	 *            用户名称条件,模糊查询
	 * @param logonName
	 *            登录名称条件,模糊查询
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询总条数
	 * @return 用户列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:42:30
	 */
	public List<User> listUser(String[] organs, String userName,
			String logonName, Integer startIndex, Integer limit);

	/**
	 * 
	 * 查询用户总计数
	 * 
	 * @param organIds
	 *            机构ID
	 * @param name
	 *            用户名称
	 * @param logonName
	 *            登录名称
	 * @return 用户总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:04:03
	 */
	public Integer userTotalCount(String[] organIds, String name, String logonName);
}
