package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Vector;

import com.znsx.cms.persistent.model.PolicyDevice;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.LightVO;

/**
 * 策略与设备的关联数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:37:20
 */
public interface PolicyDeviceDAO extends BaseDAO<PolicyDevice, String> {

	/**
	 * 存储新增策略关联的设备的列表，直到调用excuteBatch方法才批量写入数据库
	 */
	public static List<PolicyDevice> vector = new Vector<PolicyDevice>();

	/**
	 * 批量添加
	 * 
	 * @param record
	 *            策略关联的设备对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午10:05:29
	 */
	public void batchInsert(PolicyDevice record) throws BusinessException;

	/**
	 * 将vector里面的信息批量保存入库
	 * 
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午10:05:55
	 */
	public void excuteBatch() throws BusinessException;

	/**
	 * 根据策略删除策略关联的设备
	 * 
	 * @param policyId
	 *            策略ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午10:19:20
	 */
	public void deleteByPolicy(String policyId) throws BusinessException;

	/**
	 * 根据策略查询策略关联的设备列表
	 * 
	 * @param policyId
	 *            策略ID
	 * @return 策略关联的设备列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午2:18:57
	 */
	public List<LightVO> listByPolicy(String policyId);
}
