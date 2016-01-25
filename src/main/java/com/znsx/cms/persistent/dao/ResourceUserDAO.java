package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.ResourceUser;

/**
 * ResourceUserDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午7:08:57
 */
public interface ResourceUserDAO extends BaseDAO<ResourceUser, String> {

	/**
	 * 
	 * 统计队伍人员数量
	 * 
	 * @param name
	 *            人员名称
	 * @param type
	 *            人员类型
	 * @param teamId
	 *            队伍id
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:36:46
	 */
	public Integer countTotalResourceUser(String name, String type,
			String teamId);

	/**
	 * 
	 * 条件查询队员列表
	 * 
	 * @param name
	 *            队员名称
	 * @param type
	 *            队员类型
	 * @param teamId
	 *            队伍id
	 * @param startIndex
	 * @param limit
	 * @return 队员列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午7:40:37
	 */
	public List<ResourceUser> listResourceUser(String name, String type,
			String teamId, Integer startIndex, Integer limit);

}
