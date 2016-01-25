package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.LoLiVO;

/**
 * DasLoliDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:52:32
 */
public interface DasLoliDAO extends BaseDAO<DasLoli, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的Loli列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasLoli> list) throws BusinessException;

	/**
	 * 查询指定sn列表的Loli最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasLoli> listLoliInfo(List<String> sns);

	/**
	 * 光强检测器统计
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
	 *         Create at 2014-5-26 下午1:42:47
	 */
	public List<LoLiVO> statLoLi(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit);

	/**
	 * 光强检测器统计记录总数量，分页需要
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
	 *         Create at 2014-5-26 下午4:42:58
	 */
	public int countLoLi(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN);

	/**
	 * 查询时间范围内的LOLI检测器采集数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            查询起始行
	 * @param limit
	 *            要查询的行数
	 * @return 时间范围内的LOLI检测器采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 上午9:37:25
	 */
	public List<DasLoli> listDasLoli(Timestamp begin, Timestamp end, int start,
			int limit);
}
