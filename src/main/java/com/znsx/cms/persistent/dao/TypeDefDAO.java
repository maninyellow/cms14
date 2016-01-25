package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.TypeDef;

/**
 * 类型字典对象数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-1 上午11:04:52
 */
public interface TypeDefDAO extends BaseDAO<TypeDef, String> {
	/**
	 * 查询类型字典表
	 * 
	 * @param type
	 *            主类型，为空将返回全部类型
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-1 下午12:00:23
	 */
	public List<TypeDef> listTypeDef(Integer type);
}
