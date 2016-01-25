package com.znsx.cms.persistent.dao;

import java.sql.Timestamp;
import java.util.List;

import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.service.exception.BusinessException;

/**
 * DasNodDAO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-17 上午9:53:02
 */
public interface DasNodDAO extends BaseDAO<DasNod, String> {
	/**
	 * 批量插入
	 * 
	 * @param list
	 *            要插入的NOD列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 上午11:34:30
	 */
	public void batchInsert(List<DasNod> list) throws BusinessException;

	/**
	 * 查询指定sn列表的NOD最近采集数据
	 * 
	 * @param sns
	 *            标准号列表
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午3:40:08
	 */
	public List<DasNod> listNodInfo(List<String> sns);

	/**
	 * 
	 * 查询时间范围内的氮氧化物器采集数据
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param start
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 氮氧化物集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-10 下午2:31:32
	 */
	public List<DasNod> listDasNo(Timestamp begin, Timestamp end, int start,
			int limit);
}
