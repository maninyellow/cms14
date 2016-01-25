package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;

import com.znsx.cms.persistent.model.PushButton;

/**
 * 手动报警按钮数据库接口
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午3:12:49
 */
public interface PushButtonDAO extends BaseDAO<PushButton, String> {
	/**
	 * 返回以光强检测器ID为键，光强检测器对象为值的Map映射表
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return 机构ID列表下的所有光强检测器键值对
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-21 下午5:04:30
	 */
	public Map<String, PushButton> mapPBByOrganIds(String[] organIds);

	/**
	 * 
	 * 查询手动报警按钮总计数
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            按钮名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:51:21
	 */
	public Integer countPushButton(String organId, String name,
			String standardNumber, String stakeNumber);

	/**
	 * 
	 * 根据条件查询手动报警按钮列表
	 * 
	 * @param organId
	 *            机构id
	 * @param name
	 *            按钮名称
	 * @param standardNumber
	 *            标准号
	 * @param stakeNumber
	 *            桩号
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 手动报警按钮列表
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:57:46
	 */
	public List<PushButton> listPushButton(String organId, String name,
			String standardNumber, String stakeNumber, Integer startIndex,
			Integer limit);

	/**
	 * 根据SN数组查找手动报警按钮对象列表
	 * 
	 * @param sns
	 *            SN数组
	 * @return 返回以手动报警按钮SN为键，手动报警按钮对象为值的Map映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-17 下午4:06:08
	 */
	public Map<String, PushButton> mapPBBySNs(String[] sns);

	/**
	 * 
	 * 删除设备和角色的关系
	 * 
	 * @param id
	 *            设备id
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:28:33
	 */
	public void deleteRolePBPermission(String id);

	/**
	 * 
	 * 根据机构id统计手动报警数量
	 * 
	 * @param organId
	 *            机构id
	 * @return 手动报警按钮数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午11:13:05
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 根据机构id数组查询手动报警按钮
	 * 
	 * @param organIds
	 *            机构数组
	 * @return 手动报警按钮集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:32:53
	 */
	public String[] countPB(String[] organIds);

	/**
	 * 查询指定数采服务器下方的PB检测器列表
	 * 
	 * @param dasId
	 *            数采服务器ID
	 * @return 数采服务器下方的PB检测器列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-9 下午3:15:58
	 */
	public List<PushButton> listByDAS(String dasId);

	/**
	 * 
	 * 批量创建手动报警按钮
	 * 
	 * @param pbs
	 *            手报列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-6-29 上午11:26:31
	 */
	public void batchInsert(List<PushButton> pbs);

	/**
	 * 
	 * 批量插入手报
	 * 
	 * @param pushButton
	 *            手报对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:44:11
	 */
	public void batchInsertPb(PushButton pushButton);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:44:15
	 */
	public void excuteBatchPb();

}
