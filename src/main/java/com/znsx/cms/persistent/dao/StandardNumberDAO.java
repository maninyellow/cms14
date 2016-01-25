package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Vector;

import com.znsx.cms.persistent.model.StandardNumber;

/**
 * 标准号数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-23 下午3:40:17
 */
public interface StandardNumberDAO extends BaseDAO<StandardNumber, String> {

	/**
	 * 存储新增标准号的列表，直到调用excuteBatch方法才批量写入数据库
	 */
	public static List<StandardNumber> vector = new Vector<StandardNumber>();

	/**
	 * 跟据SN查询标准号对象
	 * 
	 * @param sn
	 *            标准号
	 * @return 标准号对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-23 下午4:55:53
	 */
	public StandardNumber getBySN(String sn);

	/**
	 * 批量插入
	 * 
	 * @param record
	 *            标准号对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-24 上午10:24:52
	 */
	public void batchInsert(StandardNumber record);

	/**
	 * 执行批量插入操作
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-24 上午10:25:37
	 */
	public void excuteBatch();

	/**
	 * 统计数据设备的数量
	 * 
	 * @return 数据设备数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-8 上午10:30:09
	 */
	public int countDeviceAmount();
}
