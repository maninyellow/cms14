package com.znsx.cms.persistent.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.znsx.cms.persistent.model.Dvr;

/**
 * 视频服务器数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:38:58
 */
public interface DvrDAO extends BaseDAO<Dvr, String> {
	/**
	 * 查询视频服务器列表
	 * 
	 * @param organId
	 *            机构条件
	 * @param name
	 *            名称条件,模糊查询
	 * @return 视频服务器列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:21:17
	 */
	public List<Dvr> listDvr(String organId, String name);

	/**
	 * 
	 * 分页查询CCS管辖的DVR列表
	 * 
	 * @param ccsId
	 *            CCS_ID
	 * @param start
	 *            分页起始行
	 * @param limit
	 *            要查询的行数
	 * @return DVR列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午12:47:51
	 */
	public List<Dvr> listDvrByCcs(String ccsId, int start, int limit);

	/**
	 * 统计CCS管辖的DVR数量
	 * 
	 * @param ccsId
	 *            CCS_ID
	 * @return DVR数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:32:21
	 */
	public int countDvrByCcs(String ccsId);

	/**
	 * 
	 * 根据机构ID查询DVR设备列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return DVR列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:42:34
	 */
	public List<Dvr> listDvr(String organId, Integer startIndex, Integer limit);

	/**
	 * 
	 * 根据机构ID查询DVR总计数
	 * 
	 * @param organId
	 *            机构ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:49:23
	 */
	public Integer dvrTotalCount(String organId);

	/**
	 * 
	 * 根据设备条件查询DVR列表
	 * 
	 * @param name
	 *            设备名称
	 * @param standardNumber
	 *            标准号
	 * @param ip
	 *            lanIp
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param organIds
	 *            当前登录用户机构以及子机构ID
	 * @return Dvr列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:45:38
	 */
	public List<Dvr> listDvrByDevice(String name, String standardNumber,
			String ip, Integer startIndex, Integer limit, String[] organIds);

	/**
	 * 
	 * 根据条件查询DVR
	 * 
	 * @param name
	 *            dvr名称
	 * @param standardNumber
	 *            dvr标准号
	 * @param ip
	 *            ip
	 * @param organIds
	 *            当前用户机构以及子机构ID
	 * @return DVR总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:51:49
	 */
	public Integer dvrTotalCount(String name, String standardNumber, String ip,
			String[] organIds);

	/**
	 * 统计指定DVR型号设备的数量
	 * 
	 * @param deviceModelId
	 *            设备型号
	 * @return 设备的数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:05:19
	 */
	public Integer countByDeviceModel(String deviceModelId);

	/**
	 * 
	 * 根据机构ID统计DVR计数
	 * 
	 * @param organId
	 *            机构ID
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:59:46
	 */
	public int countByOrganId(String organId);

	/**
	 * 
	 * 统计Dvr中的机构数量
	 * 
	 * @param organIds
	 *            机构ID数组
	 * @return List<Dvr>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:11:33
	 */
	public String[] countDvr(String organIds[]);

	/**
	 * 
	 * 批量插入dvr
	 * 
	 * @param dvr
	 *            dvr对象
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:31:21
	 */
	public void batchInsertDvr(Dvr dvr);

	/**
	 * 
	 * 执行批量写入数据库操作
	 * 
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:33:52
	 */
	public void excuteBatchDvr();

	/**
	 * 
	 * 查询所有dvr的sn
	 * 
	 * @return sn集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:15:22
	 */
	public List<String> listDvrSN();

	/**
	 * 
	 * 查询dvr的名称集合
	 * 
	 * @return dvr名称集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:56:22
	 */
	public List<String> listDvrName();

	/**
	 * 获取给定的SN集合的DVR的对象与sn的映射表
	 * 
	 * @param sns
	 *            DVR的sn结合
	 * @return sn为键,DVR为值的映射表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-16 下午2:41:34
	 */
	public Map<String, Dvr> mapBySns(Set<String> sns);
}
