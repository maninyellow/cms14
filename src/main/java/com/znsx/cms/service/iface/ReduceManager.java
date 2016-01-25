package com.znsx.cms.service.iface;

/**
 * ReduceManager
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-13 上午10:45:21
 */
public interface ReduceManager extends BaseManager {
	/**
	 * 将车检器采集数据按小时归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 上午10:17:47
	 */
	public void reduceVdByHour();

	/**
	 * 将车检器采集数据按天归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午1:50:20
	 */
	public void reduceVdByDay();

	/**
	 * 将车检器采集数据按月归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 上午9:54:43
	 */
	public void reduceVdByMonth();

	/**
	 * 将气象检测器采集的数据按小时归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 上午10:49:43
	 */
	public void reduceWstByHour();

	/**
	 * 将气象检测器采集的数据按天归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-25 上午10:47:42
	 */
	public void reduceWstByDay();

	/**
	 * 将气象检测器采集数据按月归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-26 上午9:39:31
	 */
	public void reduceWstByMonth();

	/**
	 * 将路面检测器采集的数据按小时归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-2 上午9:20:31
	 */
	public void reduceRsdByHour();

	/**
	 * 将路面检测器采集的数据按天归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-2 上午9:21:14
	 */
	public void reduceRsdByDay();

	/**
	 * 将路面检测器采集的数据按月归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-2 上午9:21:27
	 */
	public void reduceRsdByMonth();

	/**
	 * 将COVI检测器采集的数据按小时归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 上午9:20:31
	 */
	public void reduceCoviByHour();

	/**
	 * 将COVI检测器采集的数据按天归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 上午9:21:14
	 */
	public void reduceCoviByDay();

	/**
	 * 将COVI检测器采集的数据按月归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-8 上午9:21:27
	 */
	public void reduceCoviByMonth();

	/**
	 * 将LOLI检测器采集的数据按小时归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 上午9:20:31
	 */
	public void reduceLoliByHour();

	/**
	 * 将LOLI检测器采集的数据按天归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 上午9:21:14
	 */
	public void reduceLoliByDay();

	/**
	 * 将LOLI检测器采集的数据按月归并
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-9 上午9:21:27
	 */
	public void reduceLoliByMonth();

	/**
	 * 将NO检测器采集的数据按小时归并
	 * 
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-10 上午10:53:05
	 */
	public void reduceNoByHour();

	/**
	 * 
	 * 将NO检测器采集的数据按天归并
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-10 上午9:51:40
	 */
	public void reduceNoByDay();

	/**
	 * 
	 * 将NO检测器采集的数据按月归并
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-11 下午3:20:33
	 */
	public void reduceNoByMonth();
}
