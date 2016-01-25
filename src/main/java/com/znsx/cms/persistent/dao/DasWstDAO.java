package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.WstVO;

/**
 * DasWstDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:54:29
 */
public interface DasWstDAO extends BaseDAO<DasWst, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的WST列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasWst> list) throws BusinessException;

	/**
	 * 查询指定sn列表的WST最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasWst> listWstInfo(List<String> sns);

	/**
	 * 
	 * 气象检测器统计
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-27 上午10:18:33
	 */
	public List<WstVO> statWST(Timestamp beginTime, Timestamp endTime,
			String scope, String standardNumber, String organSN, int start,
			int limit);

	/**
	 * 气象检测器统计记录总数量，分页需要
	 * 
	 * @param beginTime
	 *            统计开始时间
	 * @param endTime
	 *            统计结束时间
	 * @param scope
	 *            统计精度，day-天，hour-小时
	 * @param standardNumber
	 *            设备编号
	 * @param organSN
	 *            平台编号
	 * @return 满足条件的记录数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-27 上午10:19:02
	 */
	public int countWST(Timestamp beginTime, Timestamp endTime, String scope,
			String standardNumber, String organSN);

	/**
	 * 查询给定设备SN列表的气象采集数据
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organSN
	 *            设备所属机构SN
	 * @param standardNumber
	 *            设备SN
	 * @param start
	 *            分页起始行号
	 * @param limit
	 *            要查询的行数
	 * @return 气象采集数据
	 */
	public List<WstVO> listWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[], int start, int limit);

	/**
	 * 统计满足条件的气象采集数据行数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param organSN
	 *            设备所属机构SN
	 * @param standardNumber
	 *            设备SN
	 * @return 满足条件的气象采集数据行数
	 */
	public int countWSTInfo(Timestamp beginTime, Timestamp endTime,
			String organSN, String sns[]);

	/**
	 * 查询时间范围内的气象检测器采集数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 时间范围内的气象检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 上午10:56:03
	 */
	public List<DasWst> listDasWst(Timestamp begin, Timestamp end, int start,
			int limit);
}
