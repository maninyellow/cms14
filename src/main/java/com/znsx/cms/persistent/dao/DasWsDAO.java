package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasWs;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.WsVO;

/**
 * DasWsDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:53:58
 */
public interface DasWsDAO extends BaseDAO<DasWs, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的WS列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasWs> list) throws BusinessException;

	/**
	 * 查询指定sn列表的WS最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasWs> listWsInfo(List<String> sns);

	/**
	 * 风速风向检测器统计
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
	 *         Create at 2014-5-27 上午9:32:40
	 */
	public List<WsVO> statWS(Timestamp beginTime, Timestamp endTime,
			String scope, String sns[], String organSN, int start,
			int limit);

	/**
	 * 风速风向检测器统计记录总数量，分页需要
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
	 *         Create at 2014-5-27 上午9:33:20
	 */
	public int countWS(Timestamp beginTime, Timestamp endTime, String scope,
			String sns[], String organSN);
}
