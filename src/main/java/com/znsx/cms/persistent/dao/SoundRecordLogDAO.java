package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.SoundRecordLog;

/**
 * SoundRecordLogDAO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:28:44
 */
public interface SoundRecordLogDAO extends BaseDAO<SoundRecordLog, String> {

	/**
	 * 
	 * 条件查询录音记录
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param minDuration
	 *            最小时长
	 * @param maxDuration
	 *            最大时长
	 * @param name
	 *            录音文件名称
	 * @param callNumber
	 *            来电去电号码
	 * @param hostNumber
	 *            主机号码
	 * @param callFlag
	 *            打进打出 0-未知 1-打进 2-打出
	 * @param type
	 *            是否按照最近播放录音排序
	 * @param startIndex
	 *            开始条数
	 * @param limit
	 *            需要条数
	 * @return 录音记录集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-18 上午11:58:03
	 */
	public List<SoundRecordLog> listSoundRecord(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag, String type,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 统计录音记录数量
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param minDuration
	 *            最小时长
	 * @param maxDuration
	 *            最大时长
	 * @param name
	 *            录音文件名称
	 * @param callNumber
	 *            来电去电号码
	 * @param hostNumber
	 *            主机号码
	 * @param callFlag
	 *            打进打出 0-未知 1-打进 2-打出
	 * 
	 * @return 录音数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-18 上午11:57:53
	 */
	public int countSoundRecordLog(Long beginTime, Long endTime,
			Integer minDuration, Integer maxDuration, String name,
			String callNumber, String hostNumber, String callFlag);

}
