package com.znsx.cms.service.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.DasCoviDAO;
import com.znsx.cms.persistent.dao.DasLoliDAO;
import com.znsx.cms.persistent.dao.DasNodDAO;
import com.znsx.cms.persistent.dao.DasRoadDetectorDAO;
import com.znsx.cms.persistent.dao.DasVdDAO;
import com.znsx.cms.persistent.dao.DasWstDAO;
import com.znsx.cms.persistent.dao.DayCoviDetectorDAO;
import com.znsx.cms.persistent.dao.DayLoliDetectorDAO;
import com.znsx.cms.persistent.dao.DayNoDetectorDAO;
import com.znsx.cms.persistent.dao.DayRoadDetectorDAO;
import com.znsx.cms.persistent.dao.DayVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.DayWeatherStatDAO;
import com.znsx.cms.persistent.dao.HourCoviDetectorDAO;
import com.znsx.cms.persistent.dao.HourLoliDetectorDAO;
import com.znsx.cms.persistent.dao.HourNoDetectorDAO;
import com.znsx.cms.persistent.dao.HourRoadDetectorDAO;
import com.znsx.cms.persistent.dao.HourVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.HourWeatherStatDAO;
import com.znsx.cms.persistent.dao.MonthCoviDetectorDAO;
import com.znsx.cms.persistent.dao.MonthLoliDetectorDAO;
import com.znsx.cms.persistent.dao.MonthNoDetectorDAO;
import com.znsx.cms.persistent.dao.MonthRoadDetectorDAO;
import com.znsx.cms.persistent.dao.MonthVehichleDetectorDAO;
import com.znsx.cms.persistent.dao.MonthWeatherStatDAO;
import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasNod;
import com.znsx.cms.persistent.model.DasRoadDetector;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.persistent.model.DayCoviDetector;
import com.znsx.cms.persistent.model.DayLoliDetector;
import com.znsx.cms.persistent.model.DayNoDetector;
import com.znsx.cms.persistent.model.DayRoadDetector;
import com.znsx.cms.persistent.model.DayVehichleDetector;
import com.znsx.cms.persistent.model.DayWeatherStat;
import com.znsx.cms.persistent.model.HourCoviDetector;
import com.znsx.cms.persistent.model.HourLoliDetector;
import com.znsx.cms.persistent.model.HourNoDetector;
import com.znsx.cms.persistent.model.HourRoadDetector;
import com.znsx.cms.persistent.model.HourVehichleDetector;
import com.znsx.cms.persistent.model.HourWeatherStat;
import com.znsx.cms.persistent.model.MonthCoviDetector;
import com.znsx.cms.persistent.model.MonthLoliDetector;
import com.znsx.cms.persistent.model.MonthNoDetector;
import com.znsx.cms.persistent.model.MonthRoadDetector;
import com.znsx.cms.persistent.model.MonthVehichleDetector;
import com.znsx.cms.persistent.model.MonthWeatherStat;
import com.znsx.cms.service.iface.ReduceManager;
import com.znsx.util.date.DateUtil;
import com.znsx.util.number.NumberUtil;

/**
 * ReduceManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-13 上午10:49:38
 */
public class ReduceManagerImpl extends BaseManagerImpl implements ReduceManager {
	@Autowired
	private HourVehichleDetectorDAO hourVdDAO;
	@Autowired
	private DasVdDAO dasVdDAO;
	@Autowired
	private DayVehichleDetectorDAO dayVdDAO;
	@Autowired
	private MonthVehichleDetectorDAO monthVdDAO;
	@Autowired
	private HourWeatherStatDAO hourWstDAO;
	@Autowired
	private DasWstDAO dasWstDAO;
	@Autowired
	private DayWeatherStatDAO dayWstDAO;
	@Autowired
	private MonthWeatherStatDAO monthWstDAO;
	@Autowired
	private HourRoadDetectorDAO hourRsdDAO;
	@Autowired
	private DayRoadDetectorDAO dayRsdDAO;
	@Autowired
	private MonthRoadDetectorDAO monthRsdDAO;
	@Autowired
	private DasRoadDetectorDAO dasRoadDetectorDAO;
	@Autowired
	private DasCoviDAO dasCoviDAO;
	@Autowired
	private HourCoviDetectorDAO hourCoviDAO;
	@Autowired
	private DayCoviDetectorDAO dayCoviDAO;
	@Autowired
	private MonthCoviDetectorDAO monthCoviDAO;
	@Autowired
	private DasLoliDAO dasLoliDAO;
	@Autowired
	private HourLoliDetectorDAO hourLoliDAO;
	@Autowired
	private DayLoliDetectorDAO dayLoliDAO;
	@Autowired
	private MonthLoliDetectorDAO monthLoliDAO;
	@Autowired
	private HourNoDetectorDAO hourNoDAO;
	@Autowired
	private DasNodDAO dasNodDAO;
	@Autowired
	private DayNoDetectorDAO dayNoDAO;
	@Autowired
	private MonthNoDetectorDAO monthNoDAO;

	@Override
	public void reduceVdByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourVehichleDetector last = hourVdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasVd> list = dasVdDAO.listDasVd(begin, end, 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		// list.size >= 100000, remove
		// DateUtil.getLowerHourTime(list.get(size-1).getTime))
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasVd> removeList = new LinkedList<DasVd>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourVehichleDetector>> map = new HashMap<String, List<HourVehichleDetector>>();
		// 迭代采集数据，按小时归并
		for (DasVd data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourVehichleDetector hvd = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourVehichleDetector> hvdList = map.get(sn);
				// 初始化map
				if (hvdList == null) {
					hvdList = new LinkedList<HourVehichleDetector>();
					map.put(sn, hvdList);
				}
				// 初始化本小时数据
				if (hvdList.size() < 1) {
					hvd = initHvd(data);
					hvdList.add(hvd);
				} else {
					// 最后一条记录
					hvd = hvdList.get(hvdList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hvd.getDateTime().longValue() < lowerLimit) {
						HourVehichleDetector nextHvd = initHvd(data);
						hvdList.add(nextHvd);
					} else {
						mergeHvd(data, hvd);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourVehichleDetector> hvdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hvdList == null) {
					hvdList = new LinkedList<HourVehichleDetector>();
					map.put(sn, hvdList);
				}
				// 初始化下1小时数据
				if (hvdList.size() < 1) {
					hvd = initHvd(data);
					hvdList.add(hvd);
				} else {
					// 最后一条记录
					hvd = hvdList.get(hvdList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hvd.getDateTime().longValue() < lowerLimit) {
						HourVehichleDetector nextHvd = initHvd(data);
						hvdList.add(nextHvd);
					} else {
						mergeHvd(data, hvd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourVehichleDetector> insertList = new LinkedList<HourVehichleDetector>();
		Collection<List<HourVehichleDetector>> values = map.values();
		for (List<HourVehichleDetector> value : values) {
			insertList.addAll(value);
		}
		hourVdDAO.batchInsert(insertList);
	}

	@Override
	public void reduceVdByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayVehichleDetector last = dayVdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourVehichleDetector> list = hourVdDAO.list(null,
				begin.getTime() + 1, end.getTime(), 0, 1000000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 1000000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourVehichleDetector> removeList = new LinkedList<HourVehichleDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayVehichleDetector>> map = new HashMap<String, List<DayVehichleDetector>>();
		// 迭代采集数据，按天归并
		for (HourVehichleDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayVehichleDetector dvd = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayVehichleDetector> dvdList = map.get(sn);
				// 初始化map
				if (dvdList == null) {
					dvdList = new LinkedList<DayVehichleDetector>();
					map.put(sn, dvdList);
				}
				// 初始化当天数据
				if (dvdList.size() < 1) {
					dvd = initDvd(data);
					dvdList.add(dvd);
				} else {
					// 最后一条记录
					dvd = dvdList.get(dvdList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dvd.getDateTime().longValue() < lowerLimit) {
						DayVehichleDetector nextDvd = initDvd(data);
						dvdList.add(nextDvd);
					} else {
						mergeDvd(data, dvd);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayVehichleDetector> dvdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (dvdList == null) {
					dvdList = new LinkedList<DayVehichleDetector>();
					map.put(sn, dvdList);
				}
				// 初始化下1天数据
				if (dvdList.size() < 1) {
					dvd = initDvd(data);
					dvdList.add(dvd);
				} else {
					// 最后一条记录
					dvd = dvdList.get(dvdList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dvd.getDateTime().longValue() < lowerLimit) {
						DayVehichleDetector nextDvd = initDvd(data);
						dvdList.add(nextDvd);
					} else {
						mergeDvd(data, dvd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayVehichleDetector> insertList = new LinkedList<DayVehichleDetector>();
		Collection<List<DayVehichleDetector>> values = map.values();
		for (List<DayVehichleDetector> value : values) {
			insertList.addAll(value);
		}
		dayVdDAO.batchInsert(insertList);
	}

	@Override
	public void reduceVdByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthVehichleDetector last = monthVdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayVehichleDetector> list = dayVdDAO.list(null,
				begin.getTime() + 1, end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayVehichleDetector> removeList = new LinkedList<DayVehichleDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthVehichleDetector>> map = new HashMap<String, List<MonthVehichleDetector>>();
		// 迭代采集数据，按月归并
		for (DayVehichleDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthVehichleDetector mvd = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthVehichleDetector> mvdList = map.get(sn);
				// 初始化map
				if (mvdList == null) {
					mvdList = new LinkedList<MonthVehichleDetector>();
					map.put(sn, mvdList);
				}
				// 初始化当月数据
				if (mvdList.size() < 1) {
					mvd = initMvd(data);
					mvdList.add(mvd);
				} else {
					// 最后一条记录
					mvd = mvdList.get(mvdList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mvd.getDateTime().longValue() < lowerLimit) {
						MonthVehichleDetector nextMvd = initMvd(data);
						mvdList.add(nextMvd);
					} else {
						mergeMvd(data, mvd);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthVehichleDetector> mvdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mvdList == null) {
					mvdList = new LinkedList<MonthVehichleDetector>();
					map.put(sn, mvdList);
				}
				// 初始化下个月数据
				if (mvdList.size() < 1) {
					mvd = initMvd(data);
					mvdList.add(mvd);
				} else {
					// 最后一条记录
					mvd = mvdList.get(mvdList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mvd.getDateTime().longValue() < lowerLimit) {
						MonthVehichleDetector nextMvd = initMvd(data);
						mvdList.add(nextMvd);
					} else {
						mergeMvd(data, mvd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthVehichleDetector> insertList = new LinkedList<MonthVehichleDetector>();
		Collection<List<MonthVehichleDetector>> values = map.values();
		for (List<MonthVehichleDetector> value : values) {
			insertList.addAll(value);
		}
		monthVdDAO.batchInsert(insertList);
	}

	@Override
	public void reduceWstByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourWeatherStat last = hourWstDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasWst> list = dasWstDAO.listDasWst(begin, end, 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasWst> removeList = new LinkedList<DasWst>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourWeatherStat>> map = new HashMap<String, List<HourWeatherStat>>();
		// 迭代采集数据，按小时归并
		for (DasWst data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourWeatherStat hwst = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourWeatherStat> hwstList = map.get(sn);
				// 初始化map
				if (hwstList == null) {
					hwstList = new LinkedList<HourWeatherStat>();
					map.put(sn, hwstList);
				}
				// 初始化本小时数据
				if (hwstList.size() < 1) {
					hwst = initHwst(data);
					hwstList.add(hwst);
				} else {
					// 最后一条记录
					hwst = hwstList.get(hwstList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hwst.getDateTime().longValue() < lowerLimit) {
						HourWeatherStat nextHwst = initHwst(data);
						hwstList.add(nextHwst);
					} else {
						mergeHwst(data, hwst);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourWeatherStat> hwstList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hwstList == null) {
					hwstList = new LinkedList<HourWeatherStat>();
					map.put(sn, hwstList);
				}
				// 初始化下1小时数据
				if (hwstList.size() < 1) {
					hwst = initHwst(data);
					hwstList.add(hwst);
				} else {
					// 最后一条记录
					hwst = hwstList.get(hwstList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hwst.getDateTime().longValue() < lowerLimit) {
						HourWeatherStat nextHwst = initHwst(data);
						hwstList.add(nextHwst);
					} else {
						mergeHwst(data, hwst);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourWeatherStat> insertList = new LinkedList<HourWeatherStat>();
		Collection<List<HourWeatherStat>> values = map.values();
		for (List<HourWeatherStat> value : values) {
			insertList.addAll(value);
		}
		hourWstDAO.batchInsert(insertList);

	}

	@Override
	public void reduceWstByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayWeatherStat last = dayWstDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourWeatherStat> list = hourWstDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourWeatherStat> removeList = new LinkedList<HourWeatherStat>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayWeatherStat>> map = new HashMap<String, List<DayWeatherStat>>();
		// 迭代采集数据，按天归并
		for (HourWeatherStat data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayWeatherStat dwst = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayWeatherStat> dwstList = map.get(sn);
				// 初始化map
				if (dwstList == null) {
					dwstList = new LinkedList<DayWeatherStat>();
					map.put(sn, dwstList);
				}
				// 初始化当天数据
				if (dwstList.size() < 1) {
					dwst = initDwst(data);
					dwstList.add(dwst);
				} else {
					// 最后一条记录
					dwst = dwstList.get(dwstList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dwst.getDateTime().longValue() < lowerLimit) {
						DayWeatherStat nextDwst = initDwst(data);
						dwstList.add(nextDwst);
					} else {
						mergeDwst(data, dwst);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayWeatherStat> dwstList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (dwstList == null) {
					dwstList = new LinkedList<DayWeatherStat>();
					map.put(sn, dwstList);
				}
				// 初始化下1天数据
				if (dwstList.size() < 1) {
					dwst = initDwst(data);
					dwstList.add(dwst);
				} else {
					// 最后一条记录
					dwst = dwstList.get(dwstList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dwst.getDateTime().longValue() < lowerLimit) {
						DayWeatherStat nextDwst = initDwst(data);
						dwstList.add(nextDwst);
					} else {
						mergeDwst(data, dwst);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayWeatherStat> insertList = new LinkedList<DayWeatherStat>();
		Collection<List<DayWeatherStat>> values = map.values();
		for (List<DayWeatherStat> value : values) {
			insertList.addAll(value);
		}
		dayWstDAO.batchInsert(insertList);
	}

	@Override
	public void reduceWstByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthWeatherStat last = monthWstDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayWeatherStat> list = dayWstDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayWeatherStat> removeList = new LinkedList<DayWeatherStat>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthWeatherStat>> map = new HashMap<String, List<MonthWeatherStat>>();
		// 迭代采集数据，按月归并
		for (DayWeatherStat data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthWeatherStat mwst = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthWeatherStat> mwstList = map.get(sn);
				// 初始化map
				if (mwstList == null) {
					mwstList = new LinkedList<MonthWeatherStat>();
					map.put(sn, mwstList);
				}
				// 初始化当月数据
				if (mwstList.size() < 1) {
					mwst = initMwst(data);
					mwstList.add(mwst);
				} else {
					// 最后一条记录
					mwst = mwstList.get(mwstList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mwst.getDateTime().longValue() < lowerLimit) {
						MonthWeatherStat nextMwst = initMwst(data);
						mwstList.add(nextMwst);
					} else {
						mergeMwst(data, mwst);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthWeatherStat> mwstList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mwstList == null) {
					mwstList = new LinkedList<MonthWeatherStat>();
					map.put(sn, mwstList);
				}
				// 初始化下个月数据
				if (mwstList.size() < 1) {
					mwst = initMwst(data);
					mwstList.add(mwst);
				} else {
					// 最后一条记录
					mwst = mwstList.get(mwstList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mwst.getDateTime().longValue() < lowerLimit) {
						MonthWeatherStat nextMwst = initMwst(data);
						mwstList.add(nextMwst);
					} else {
						mergeMwst(data, mwst);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthWeatherStat> insertList = new LinkedList<MonthWeatherStat>();
		Collection<List<MonthWeatherStat>> values = map.values();
		for (List<MonthWeatherStat> value : values) {
			insertList.addAll(value);
		}
		monthWstDAO.batchInsert(insertList);
	}

	@Override
	public void reduceRsdByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourRoadDetector last = hourRsdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasRoadDetector> list = dasRoadDetectorDAO.listDasRsd(begin, end,
				0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasRoadDetector> removeList = new LinkedList<DasRoadDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourRoadDetector>> map = new HashMap<String, List<HourRoadDetector>>();
		// 迭代采集数据，按小时归并
		for (DasRoadDetector data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourRoadDetector hrsd = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourRoadDetector> hrsdList = map.get(sn);
				// 初始化map
				if (hrsdList == null) {
					hrsdList = new LinkedList<HourRoadDetector>();
					map.put(sn, hrsdList);
				}
				// 初始化本小时数据
				if (hrsdList.size() < 1) {
					hrsd = initHrsd(data);
					hrsdList.add(hrsd);
				} else {
					// 最后一条记录
					hrsd = hrsdList.get(hrsdList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hrsd.getDateTime().longValue() < lowerLimit) {
						HourRoadDetector nextHrsd = initHrsd(data);
						hrsdList.add(nextHrsd);
					} else {
						mergeHrsd(data, hrsd);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourRoadDetector> hrsdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hrsdList == null) {
					hrsdList = new LinkedList<HourRoadDetector>();
					map.put(sn, hrsdList);
				}
				// 初始化下1小时数据
				if (hrsdList.size() < 1) {
					hrsd = initHrsd(data);
					hrsdList.add(hrsd);
				} else {
					// 最后一条记录
					hrsd = hrsdList.get(hrsdList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hrsd.getDateTime().longValue() < lowerLimit) {
						HourRoadDetector nextHrsd = initHrsd(data);
						hrsdList.add(nextHrsd);
					} else {
						mergeHrsd(data, hrsd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourRoadDetector> insertList = new LinkedList<HourRoadDetector>();
		Collection<List<HourRoadDetector>> values = map.values();
		for (List<HourRoadDetector> value : values) {
			insertList.addAll(value);
		}
		hourRsdDAO.batchInsert(insertList);
	}

	@Override
	public void reduceRsdByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayRoadDetector last = dayRsdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourRoadDetector> list = hourRsdDAO.list(null,
				begin.getTime() + 1, end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourRoadDetector> removeList = new LinkedList<HourRoadDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayRoadDetector>> map = new HashMap<String, List<DayRoadDetector>>();
		// 迭代采集数据，按天归并
		for (HourRoadDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayRoadDetector drsd = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayRoadDetector> drsdList = map.get(sn);
				// 初始化map
				if (drsdList == null) {
					drsdList = new LinkedList<DayRoadDetector>();
					map.put(sn, drsdList);
				}
				// 初始化当天数据
				if (drsdList.size() < 1) {
					drsd = initDrsd(data);
					drsdList.add(drsd);
				} else {
					// 最后一条记录
					drsd = drsdList.get(drsdList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (drsd.getDateTime().longValue() < lowerLimit) {
						DayRoadDetector nextDrsd = initDrsd(data);
						drsdList.add(nextDrsd);
					} else {
						mergeDrsd(data, drsd);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayRoadDetector> drsdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (drsdList == null) {
					drsdList = new LinkedList<DayRoadDetector>();
					map.put(sn, drsdList);
				}
				// 初始化下1天数据
				if (drsdList.size() < 1) {
					drsd = initDrsd(data);
					drsdList.add(drsd);
				} else {
					// 最后一条记录
					drsd = drsdList.get(drsdList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (drsd.getDateTime().longValue() < lowerLimit) {
						DayRoadDetector nextDrsd = initDrsd(data);
						drsdList.add(nextDrsd);
					} else {
						mergeDrsd(data, drsd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayRoadDetector> insertList = new LinkedList<DayRoadDetector>();
		Collection<List<DayRoadDetector>> values = map.values();
		for (List<DayRoadDetector> value : values) {
			insertList.addAll(value);
		}
		dayRsdDAO.batchInsert(insertList);

	}

	@Override
	public void reduceRsdByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthRoadDetector last = monthRsdDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayRoadDetector> list = dayRsdDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayRoadDetector> removeList = new LinkedList<DayRoadDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthRoadDetector>> map = new HashMap<String, List<MonthRoadDetector>>();
		// 迭代采集数据，按月归并
		for (DayRoadDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthRoadDetector mrsd = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthRoadDetector> mrsdList = map.get(sn);
				// 初始化map
				if (mrsdList == null) {
					mrsdList = new LinkedList<MonthRoadDetector>();
					map.put(sn, mrsdList);
				}
				// 初始化当月数据
				if (mrsdList.size() < 1) {
					mrsd = initMrsd(data);
					mrsdList.add(mrsd);
				} else {
					// 最后一条记录
					mrsd = mrsdList.get(mrsdList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mrsd.getDateTime().longValue() < lowerLimit) {
						MonthRoadDetector nextMrsd = initMrsd(data);
						mrsdList.add(nextMrsd);
					} else {
						mergeMrsd(data, mrsd);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthRoadDetector> mrsdList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mrsdList == null) {
					mrsdList = new LinkedList<MonthRoadDetector>();
					map.put(sn, mrsdList);
				}
				// 初始化下个月数据
				if (mrsdList.size() < 1) {
					mrsd = initMrsd(data);
					mrsdList.add(mrsd);
				} else {
					// 最后一条记录
					mrsd = mrsdList.get(mrsdList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mrsd.getDateTime().longValue() < lowerLimit) {
						MonthRoadDetector nextMrsd = initMrsd(data);
						mrsdList.add(nextMrsd);
					} else {
						mergeMrsd(data, mrsd);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthRoadDetector> insertList = new LinkedList<MonthRoadDetector>();
		Collection<List<MonthRoadDetector>> values = map.values();
		for (List<MonthRoadDetector> value : values) {
			insertList.addAll(value);
		}
		monthRsdDAO.batchInsert(insertList);
	}

	@Override
	public void reduceCoviByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourCoviDetector last = hourCoviDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasCovi> list = dasCoviDAO.listDasCovi(begin, end, 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasCovi> removeList = new LinkedList<DasCovi>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourCoviDetector>> map = new HashMap<String, List<HourCoviDetector>>();
		// 迭代采集数据，按小时归并
		for (DasCovi data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourCoviDetector hcovi = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourCoviDetector> hcoviList = map.get(sn);
				// 初始化map
				if (hcoviList == null) {
					hcoviList = new LinkedList<HourCoviDetector>();
					map.put(sn, hcoviList);
				}
				// 初始化本小时数据
				if (hcoviList.size() < 1) {
					hcovi = initHcovi(data);
					hcoviList.add(hcovi);
				} else {
					// 最后一条记录
					hcovi = hcoviList.get(hcoviList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hcovi.getDateTime().longValue() < lowerLimit) {
						HourCoviDetector nextHcovi = initHcovi(data);
						hcoviList.add(nextHcovi);
					} else {
						mergeHcovi(data, hcovi);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourCoviDetector> hcoviList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hcoviList == null) {
					hcoviList = new LinkedList<HourCoviDetector>();
					map.put(sn, hcoviList);
				}
				// 初始化下1小时数据
				if (hcoviList.size() < 1) {
					hcovi = initHcovi(data);
					hcoviList.add(hcovi);
				} else {
					// 最后一条记录
					hcovi = hcoviList.get(hcoviList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hcovi.getDateTime().longValue() < lowerLimit) {
						HourCoviDetector nextHcovi = initHcovi(data);
						hcoviList.add(nextHcovi);
					} else {
						mergeHcovi(data, hcovi);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourCoviDetector> insertList = new LinkedList<HourCoviDetector>();
		Collection<List<HourCoviDetector>> values = map.values();
		for (List<HourCoviDetector> value : values) {
			insertList.addAll(value);
		}
		hourCoviDAO.batchInsert(insertList);
	}

	@Override
	public void reduceCoviByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayCoviDetector last = dayCoviDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourCoviDetector> list = hourCoviDAO.list(null,
				begin.getTime() + 1, end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourCoviDetector> removeList = new LinkedList<HourCoviDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayCoviDetector>> map = new HashMap<String, List<DayCoviDetector>>();
		// 迭代采集数据，按天归并
		for (HourCoviDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayCoviDetector dcovi = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayCoviDetector> dcoviList = map.get(sn);
				// 初始化map
				if (dcoviList == null) {
					dcoviList = new LinkedList<DayCoviDetector>();
					map.put(sn, dcoviList);
				}
				// 初始化当天数据
				if (dcoviList.size() < 1) {
					dcovi = initDcovi(data);
					dcoviList.add(dcovi);
				} else {
					// 最后一条记录
					dcovi = dcoviList.get(dcoviList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dcovi.getDateTime().longValue() < lowerLimit) {
						DayCoviDetector nextDcovi = initDcovi(data);
						dcoviList.add(nextDcovi);
					} else {
						mergeDcovi(data, dcovi);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayCoviDetector> dcoviList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (dcoviList == null) {
					dcoviList = new LinkedList<DayCoviDetector>();
					map.put(sn, dcoviList);
				}
				// 初始化下1天数据
				if (dcoviList.size() < 1) {
					dcovi = initDcovi(data);
					dcoviList.add(dcovi);
				} else {
					// 最后一条记录
					dcovi = dcoviList.get(dcoviList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dcovi.getDateTime().longValue() < lowerLimit) {
						DayCoviDetector nextDcovi = initDcovi(data);
						dcoviList.add(nextDcovi);
					} else {
						mergeDcovi(data, dcovi);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayCoviDetector> insertList = new LinkedList<DayCoviDetector>();
		Collection<List<DayCoviDetector>> values = map.values();
		for (List<DayCoviDetector> value : values) {
			insertList.addAll(value);
		}
		dayCoviDAO.batchInsert(insertList);

	}

	@Override
	public void reduceCoviByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthCoviDetector last = monthCoviDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayCoviDetector> list = dayCoviDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayCoviDetector> removeList = new LinkedList<DayCoviDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthCoviDetector>> map = new HashMap<String, List<MonthCoviDetector>>();
		// 迭代采集数据，按月归并
		for (DayCoviDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthCoviDetector mcovi = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthCoviDetector> mcoviList = map.get(sn);
				// 初始化map
				if (mcoviList == null) {
					mcoviList = new LinkedList<MonthCoviDetector>();
					map.put(sn, mcoviList);
				}
				// 初始化当月数据
				if (mcoviList.size() < 1) {
					mcovi = initMcovi(data);
					mcoviList.add(mcovi);
				} else {
					// 最后一条记录
					mcovi = mcoviList.get(mcoviList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mcovi.getDateTime().longValue() < lowerLimit) {
						MonthCoviDetector nextMcovi = initMcovi(data);
						mcoviList.add(nextMcovi);
					} else {
						mergeMcovi(data, mcovi);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthCoviDetector> mcoviList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mcoviList == null) {
					mcoviList = new LinkedList<MonthCoviDetector>();
					map.put(sn, mcoviList);
				}
				// 初始化下个月数据
				if (mcoviList.size() < 1) {
					mcovi = initMcovi(data);
					mcoviList.add(mcovi);
				} else {
					// 最后一条记录
					mcovi = mcoviList.get(mcoviList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mcovi.getDateTime().longValue() < lowerLimit) {
						MonthCoviDetector nextMcovi = initMcovi(data);
						mcoviList.add(nextMcovi);
					} else {
						mergeMcovi(data, mcovi);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthCoviDetector> insertList = new LinkedList<MonthCoviDetector>();
		Collection<List<MonthCoviDetector>> values = map.values();
		for (List<MonthCoviDetector> value : values) {
			insertList.addAll(value);
		}
		monthCoviDAO.batchInsert(insertList);

	}

	@Override
	public void reduceLoliByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourLoliDetector last = hourLoliDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasLoli> list = dasLoliDAO.listDasLoli(begin, end, 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasLoli> removeList = new LinkedList<DasLoli>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourLoliDetector>> map = new HashMap<String, List<HourLoliDetector>>();
		// 迭代采集数据，按小时归并
		for (DasLoli data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourLoliDetector hloli = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourLoliDetector> hloliList = map.get(sn);
				// 初始化map
				if (hloliList == null) {
					hloliList = new LinkedList<HourLoliDetector>();
					map.put(sn, hloliList);
				}
				// 初始化本小时数据
				if (hloliList.size() < 1) {
					hloli = initHloli(data);
					hloliList.add(hloli);
				} else {
					// 最后一条记录
					hloli = hloliList.get(hloliList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hloli.getDateTime().longValue() < lowerLimit) {
						HourLoliDetector nextHloli = initHloli(data);
						hloliList.add(nextHloli);
					} else {
						mergeHloli(data, hloli);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourLoliDetector> hloliList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hloliList == null) {
					hloliList = new LinkedList<HourLoliDetector>();
					map.put(sn, hloliList);
				}
				// 初始化下1小时数据
				if (hloliList.size() < 1) {
					hloli = initHloli(data);
					hloliList.add(hloli);
				} else {
					// 最后一条记录
					hloli = hloliList.get(hloliList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hloli.getDateTime().longValue() < lowerLimit) {
						HourLoliDetector nextHloli = initHloli(data);
						hloliList.add(nextHloli);
					} else {
						mergeHloli(data, hloli);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourLoliDetector> insertList = new LinkedList<HourLoliDetector>();
		Collection<List<HourLoliDetector>> values = map.values();
		for (List<HourLoliDetector> value : values) {
			insertList.addAll(value);
		}
		hourLoliDAO.batchInsert(insertList);
	}

	@Override
	public void reduceLoliByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayLoliDetector last = dayLoliDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourLoliDetector> list = hourLoliDAO.list(null,
				begin.getTime() + 1, end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourLoliDetector> removeList = new LinkedList<HourLoliDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayLoliDetector>> map = new HashMap<String, List<DayLoliDetector>>();
		// 迭代采集数据，按天归并
		for (HourLoliDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayLoliDetector dloli = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayLoliDetector> dloliList = map.get(sn);
				// 初始化map
				if (dloliList == null) {
					dloliList = new LinkedList<DayLoliDetector>();
					map.put(sn, dloliList);
				}
				// 初始化当天数据
				if (dloliList.size() < 1) {
					dloli = initDloli(data);
					dloliList.add(dloli);
				} else {
					// 最后一条记录
					dloli = dloliList.get(dloliList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dloli.getDateTime().longValue() < lowerLimit) {
						DayLoliDetector nextDloli = initDloli(data);
						dloliList.add(nextDloli);
					} else {
						mergeDloli(data, dloli);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayLoliDetector> dloliList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (dloliList == null) {
					dloliList = new LinkedList<DayLoliDetector>();
					map.put(sn, dloliList);
				}
				// 初始化下1天数据
				if (dloliList.size() < 1) {
					dloli = initDloli(data);
					dloliList.add(dloli);
				} else {
					// 最后一条记录
					dloli = dloliList.get(dloliList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dloli.getDateTime().longValue() < lowerLimit) {
						DayLoliDetector nextDloli = initDloli(data);
						dloliList.add(nextDloli);
					} else {
						mergeDloli(data, dloli);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayLoliDetector> insertList = new LinkedList<DayLoliDetector>();
		Collection<List<DayLoliDetector>> values = map.values();
		for (List<DayLoliDetector> value : values) {
			insertList.addAll(value);
		}
		dayLoliDAO.batchInsert(insertList);
	}

	@Override
	public void reduceLoliByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthLoliDetector last = monthLoliDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayLoliDetector> list = dayLoliDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayLoliDetector> removeList = new LinkedList<DayLoliDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthLoliDetector>> map = new HashMap<String, List<MonthLoliDetector>>();
		// 迭代采集数据，按月归并
		for (DayLoliDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthLoliDetector mloli = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthLoliDetector> mloliList = map.get(sn);
				// 初始化map
				if (mloliList == null) {
					mloliList = new LinkedList<MonthLoliDetector>();
					map.put(sn, mloliList);
				}
				// 初始化当月数据
				if (mloliList.size() < 1) {
					mloli = initMloli(data);
					mloliList.add(mloli);
				} else {
					// 最后一条记录
					mloli = mloliList.get(mloliList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mloli.getDateTime().longValue() < lowerLimit) {
						MonthLoliDetector nextMloli = initMloli(data);
						mloliList.add(nextMloli);
					} else {
						mergeMloli(data, mloli);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthLoliDetector> mloliList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mloliList == null) {
					mloliList = new LinkedList<MonthLoliDetector>();
					map.put(sn, mloliList);
				}
				// 初始化下个月数据
				if (mloliList.size() < 1) {
					mloli = initMloli(data);
					mloliList.add(mloli);
				} else {
					// 最后一条记录
					mloli = mloliList.get(mloliList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mloli.getDateTime().longValue() < lowerLimit) {
						MonthLoliDetector nextMloli = initMloli(data);
						mloliList.add(nextMloli);
					} else {
						mergeMloli(data, mloli);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthLoliDetector> insertList = new LinkedList<MonthLoliDetector>();
		Collection<List<MonthLoliDetector>> values = map.values();
		for (List<MonthLoliDetector> value : values) {
			insertList.addAll(value);
		}
		monthLoliDAO.batchInsert(insertList);
	}

	/**
	 * 初始化小时归并数据
	 * 
	 * @param data
	 *            采集数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午3:11:22
	 */
	private HourVehichleDetector initHvd(DasVd data) {
		HourVehichleDetector hvd = new HourVehichleDetector();
		hvd.setDateTime(data.getRecTime().getTime());
		hvd.setDwFlowb(NumberUtil.getFloat(data.getDwFluxb()));
		hvd.setDwFlowm(NumberUtil.getFloat(data.getDwFluxm()));
		hvd.setDwFlows(NumberUtil.getFloat(data.getDwFluxs()));
		hvd.setDwFlow(NumberUtil.plusFloat(hvd.getDwFlowb(), hvd.getDwFlowm(),
				hvd.getDwFlows()));
		hvd.setDwHeadway(NumberUtil.getFloat(data.getDwHeadway()));
		hvd.setDwOcc(NumberUtil.getFloat(data.getDwOcc()));
		hvd.setDwOccb(NumberUtil.getFloat(data.getDwOccb()));
		hvd.setDwOccm(NumberUtil.getFloat(data.getDwOccm()));
		hvd.setDwOccs(NumberUtil.getFloat(data.getDwOccms()));
		hvd.setDwSpeed(NumberUtil.getFloat(data.getDwSpeed()));
		hvd.setDwSpeedb(NumberUtil.getFloat(data.getDwSpeedb()));
		hvd.setDwSpeedm(NumberUtil.getFloat(data.getDwSpeedm()));
		hvd.setDwSpeeds(NumberUtil.getFloat(data.getDwSpeeds()));
		hvd.setStandardNumber(data.getStandardNumber());
		hvd.setUpFlowb(NumberUtil.getFloat(data.getUpFluxb()));
		hvd.setUpFlowm(NumberUtil.getFloat(data.getUpFluxm()));
		hvd.setUpFlows(NumberUtil.getFloat(data.getUpFluxs()));
		hvd.setUpFlow(NumberUtil.plusFloat(hvd.getUpFlowb(), hvd.getUpFlowm(),
				hvd.getUpFlows()));
		hvd.setUpHeadway(NumberUtil.getFloat(data.getUpHeadway()));
		hvd.setUpOcc(NumberUtil.getFloat(data.getUpOcc()));
		hvd.setUpOccb(NumberUtil.getFloat(data.getUpOccb()));
		hvd.setUpOccm(NumberUtil.getFloat(data.getUpOccm()));
		hvd.setUpOccs(NumberUtil.getFloat(data.getUpOccms()));
		hvd.setUpSpeed(NumberUtil.getFloat(data.getUpSpeed()));
		hvd.setUpSpeedb(NumberUtil.getFloat(data.getUpSpeedb()));
		hvd.setUpSpeedm(NumberUtil.getFloat(data.getUpSpeedm()));
		hvd.setUpSpeeds(NumberUtil.getFloat(data.getUpSpeeds()));
		return hvd;
	}

	/**
	 * 合并采集数据和归并的数据
	 * 
	 * @param data
	 *            采集数据
	 * @param hvd
	 *            归并数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 上午9:41:26
	 */
	private void mergeHvd(DasVd data, HourVehichleDetector hvd) {
		hvd.setDateTime(data.getRecTime().getTime());
		hvd.setDwFlowb(NumberUtil.plusFloat(hvd.getDwFlowb(),
				NumberUtil.getFloat(data.getDwFluxb())));
		hvd.setDwFlowm(NumberUtil.plusFloat(hvd.getDwFlowm(),
				NumberUtil.getFloat(data.getDwFluxm())));
		hvd.setDwFlows(NumberUtil.plusFloat(hvd.getDwFlows(),
				NumberUtil.getFloat(data.getDwFluxs())));
		hvd.setDwFlow(NumberUtil.plusFloat(hvd.getDwFlowb(), hvd.getDwFlowm(),
				hvd.getDwFlows()));
		hvd.setDwHeadway(NumberUtil.avgFloat(hvd.getDwHeadway(),
				NumberUtil.getFloat(data.getDwHeadway())));
		hvd.setDwOcc(NumberUtil.avgFloat(hvd.getDwOcc(),
				NumberUtil.getFloat(data.getDwOcc())));
		hvd.setDwOccb(NumberUtil.avgFloat(hvd.getDwOccb(),
				NumberUtil.getFloat(data.getDwOccb())));
		hvd.setDwOccm(NumberUtil.avgFloat(hvd.getDwOccm(),
				NumberUtil.getFloat(data.getDwOccm())));
		hvd.setDwOccs(NumberUtil.avgFloat(hvd.getDwOccs(),
				NumberUtil.getFloat(data.getDwOccs())));
		hvd.setDwSpeed(avgSpeed(hvd.getDwFlow(), hvd.getDwSpeed(),
				data.getDwFlux(), data.getDwSpeed()));
		hvd.setDwSpeedb(avgSpeed(hvd.getDwFlowb(), hvd.getDwSpeedb(),
				data.getDwFluxb(), data.getDwSpeedb()));
		hvd.setDwSpeedm(avgSpeed(hvd.getDwFlowm(), hvd.getDwSpeedm(),
				data.getDwFluxm(), data.getDwSpeedm()));
		hvd.setDwSpeeds(avgSpeed(hvd.getDwFlows(), hvd.getDwSpeeds(),
				data.getDwFluxs(), data.getDwSpeeds()));

		hvd.setUpFlowb(NumberUtil.plusFloat(hvd.getUpFlowb(),
				NumberUtil.getFloat(data.getUpFluxb())));
		hvd.setUpFlowm(NumberUtil.plusFloat(hvd.getUpFlowm(),
				NumberUtil.getFloat(data.getUpFluxm())));
		hvd.setUpFlows(NumberUtil.plusFloat(hvd.getUpFlows(),
				NumberUtil.getFloat(data.getUpFluxs())));
		hvd.setUpFlow(NumberUtil.plusFloat(hvd.getUpFlowb(), hvd.getUpFlowm(),
				hvd.getUpFlows()));
		hvd.setUpHeadway(NumberUtil.avgFloat(hvd.getUpHeadway(),
				NumberUtil.getFloat(data.getUpHeadway())));
		hvd.setUpOcc(NumberUtil.avgFloat(hvd.getUpOcc(),
				NumberUtil.getFloat(data.getUpOcc())));
		hvd.setUpOccb(NumberUtil.avgFloat(hvd.getUpOccb(),
				NumberUtil.getFloat(data.getUpOccb())));
		hvd.setUpOccm(NumberUtil.avgFloat(hvd.getUpOccm(),
				NumberUtil.getFloat(data.getUpOccm())));
		hvd.setUpOccs(NumberUtil.avgFloat(hvd.getUpOccs(),
				NumberUtil.getFloat(data.getUpOccs())));
		hvd.setUpSpeed(avgSpeed(hvd.getUpFlow(), hvd.getUpSpeed(),
				data.getUpFlux(), data.getUpSpeed()));
		hvd.setUpSpeedb(avgSpeed(hvd.getUpFlowb(), hvd.getUpSpeedb(),
				data.getUpFluxb(), data.getUpSpeedb()));
		hvd.setUpSpeedm(avgSpeed(hvd.getUpFlowm(), hvd.getUpSpeedm(),
				data.getUpFluxm(), data.getUpSpeedm()));
		hvd.setUpSpeeds(avgSpeed(hvd.getUpFlows(), hvd.getUpSpeeds(),
				data.getUpFluxs(), data.getUpSpeeds()));
	}

	/**
	 * 平均速度计算
	 * 
	 * @param flow
	 *            　流量１
	 * @param speed
	 *            　车速１
	 * @param nextFlow
	 *            　流量２
	 * @param nextSpeed
	 *            　车速２
	 * @return　平均速度
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午3:11:56
	 */
	private float avgSpeed(Number flow, Number speed, Number nextFlow,
			Number nextSpeed) {
		if (flow == null) {
			flow = 0f;
		}
		if (speed == null) {
			speed = 0f;
		}
		if (nextFlow == null) {
			nextFlow = 0;
		}
		if (nextSpeed == null) {
			nextSpeed = 0;
		}
		if (0f == flow.floatValue() && 0 == nextFlow.intValue()) {
			return 0f;
		}
		return (flow.floatValue() * speed.floatValue() + nextFlow.floatValue()
				* nextSpeed.floatValue())
				/ (flow.floatValue() + nextFlow.floatValue());
	}

	/**
	 * 初始化数据
	 * 
	 * @param data
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午4:14:18
	 */
	private DayVehichleDetector initDvd(HourVehichleDetector data) {
		DayVehichleDetector dvd = new DayVehichleDetector();
		dvd.setDateTime(data.getDateTime());
		dvd.setDwFlowb(data.getDwFlowb());
		dvd.setDwFlowm(data.getDwFlowm());
		dvd.setDwFlows(data.getDwFlows());
		dvd.setDwFlow(NumberUtil.plusFloat(dvd.getDwFlowb(), dvd.getDwFlowm(),
				dvd.getDwFlows()));
		dvd.setDwHeadway(data.getDwHeadway());
		dvd.setDwOcc(data.getDwOcc());
		dvd.setDwOccb(data.getDwOccb());
		dvd.setDwOccm(data.getDwOccm());
		dvd.setDwOccs(data.getDwOccs());
		dvd.setDwSpeed(data.getDwSpeed());
		dvd.setDwSpeedb(data.getDwSpeedb());
		dvd.setDwSpeedm(data.getDwSpeedm());
		dvd.setDwSpeeds(data.getDwSpeeds());
		dvd.setStandardNumber(data.getStandardNumber());
		dvd.setUpFlowb(data.getUpFlowb());
		dvd.setUpFlowm(data.getUpFlowm());
		dvd.setUpFlows(data.getUpFlows());
		dvd.setUpFlow(NumberUtil.plusFloat(dvd.getUpFlowb(), dvd.getUpFlowm(),
				dvd.getUpFlows()));
		dvd.setUpHeadway(data.getUpHeadway());
		dvd.setUpOcc(data.getUpOcc());
		dvd.setUpOccb(data.getUpOccb());
		dvd.setUpOccm(data.getUpOccm());
		dvd.setUpOccs(data.getUpOccs());
		dvd.setUpSpeed(data.getUpSpeed());
		dvd.setUpSpeedb(data.getUpSpeedb());
		dvd.setUpSpeedm(data.getUpSpeedm());
		dvd.setUpSpeeds(data.getUpSpeeds());
		return dvd;
	}

	/**
	 * 合并小时采集数据到天数据中
	 * 
	 * @param data
	 *            小时采集数据
	 * @param dvd
	 *            天数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午4:15:09
	 */
	private void mergeDvd(HourVehichleDetector data, DayVehichleDetector dvd) {
		dvd.setDateTime(data.getDateTime());
		dvd.setDwFlowb(NumberUtil.plusFloat(dvd.getDwFlowb(), data.getDwFlowb()));
		dvd.setDwFlowm(NumberUtil.plusFloat(dvd.getDwFlowm(), data.getDwFlowm()));
		dvd.setDwFlows(NumberUtil.plusFloat(dvd.getDwFlows(), data.getDwFlows()));
		dvd.setDwFlow(NumberUtil.plusFloat(dvd.getDwFlowb(), dvd.getDwFlowm(),
				dvd.getDwFlows()));
		dvd.setDwHeadway(NumberUtil.avgFloat(dvd.getDwHeadway(),
				data.getDwHeadway()));
		dvd.setDwOcc(NumberUtil.avgFloat(dvd.getDwOcc(), data.getDwOcc()));
		dvd.setDwOccb(NumberUtil.avgFloat(dvd.getDwOccb(), data.getDwOccb()));
		dvd.setDwOccm(NumberUtil.avgFloat(dvd.getDwOccm(), data.getDwOccm()));
		dvd.setDwOccs(NumberUtil.avgFloat(dvd.getDwOccs(), data.getDwOccs()));
		dvd.setDwSpeed(avgSpeed(dvd.getDwFlow(), dvd.getDwSpeed(),
				data.getDwFlow(), data.getDwSpeed()));
		dvd.setDwSpeedb(avgSpeed(dvd.getDwFlowb(), dvd.getDwSpeedb(),
				data.getDwFlowb(), data.getDwSpeedb()));
		dvd.setDwSpeedm(avgSpeed(dvd.getDwFlowm(), dvd.getDwSpeedm(),
				data.getDwFlowm(), data.getDwSpeedm()));
		dvd.setDwSpeeds(avgSpeed(dvd.getDwFlows(), dvd.getDwSpeeds(),
				data.getDwFlows(), data.getDwSpeeds()));

		dvd.setUpFlowb(NumberUtil.plusFloat(dvd.getUpFlowb(), data.getUpFlowb()));
		dvd.setUpFlowm(NumberUtil.plusFloat(dvd.getUpFlowm(), data.getUpFlowm()));
		dvd.setUpFlows(NumberUtil.plusFloat(dvd.getUpFlows(), data.getUpFlows()));
		dvd.setUpFlow(NumberUtil.plusFloat(dvd.getUpFlowb(), dvd.getUpFlowm(),
				dvd.getUpFlows()));
		dvd.setUpHeadway(NumberUtil.avgFloat(dvd.getUpHeadway(),
				data.getUpHeadway()));
		dvd.setUpOcc(NumberUtil.avgFloat(dvd.getUpOcc(), data.getUpOcc()));
		dvd.setUpOccb(NumberUtil.avgFloat(dvd.getUpOccb(), data.getUpOccb()));
		dvd.setUpOccm(NumberUtil.avgFloat(dvd.getUpOccm(), data.getUpOccm()));
		dvd.setUpOccs(NumberUtil.avgFloat(dvd.getUpOccs(), data.getUpOccs()));
		dvd.setUpSpeed(avgSpeed(dvd.getUpFlow(), dvd.getUpSpeed(),
				data.getUpFlow(), data.getUpSpeed()));
		dvd.setUpSpeedb(avgSpeed(dvd.getUpFlowb(), dvd.getUpSpeedb(),
				data.getUpFlowb(), data.getUpSpeedb()));
		dvd.setUpSpeedm(avgSpeed(dvd.getUpFlowm(), dvd.getUpSpeedm(),
				data.getUpFlowm(), data.getUpSpeedm()));
		dvd.setUpSpeeds(avgSpeed(dvd.getUpFlows(), dvd.getUpSpeeds(),
				data.getUpFlows(), data.getUpSpeeds()));
	}

	/**
	 * 初始化数据
	 * 
	 * @param data
	 *            天采集数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 上午9:56:44
	 */
	private MonthVehichleDetector initMvd(DayVehichleDetector data) {
		MonthVehichleDetector mvd = new MonthVehichleDetector();
		mvd.setDateTime(data.getDateTime());
		mvd.setDwFlowb(data.getDwFlowb());
		mvd.setDwFlowm(data.getDwFlowm());
		mvd.setDwFlows(data.getDwFlows());
		mvd.setDwFlow(NumberUtil.plusFloat(mvd.getDwFlowb(), mvd.getDwFlowm(),
				mvd.getDwFlows()));
		mvd.setDwHeadway(data.getDwHeadway());
		mvd.setDwOcc(data.getDwOcc());
		mvd.setDwOccb(data.getDwOccb());
		mvd.setDwOccm(data.getDwOccm());
		mvd.setDwOccs(data.getDwOccs());
		mvd.setDwSpeed(data.getDwSpeed());
		mvd.setDwSpeedb(data.getDwSpeedb());
		mvd.setDwSpeedm(data.getDwSpeedm());
		mvd.setDwSpeeds(data.getDwSpeeds());
		mvd.setStandardNumber(data.getStandardNumber());
		mvd.setUpFlowb(data.getUpFlowb());
		mvd.setUpFlowm(data.getUpFlowm());
		mvd.setUpFlows(data.getUpFlows());
		mvd.setUpFlow(NumberUtil.plusFloat(mvd.getUpFlowb(), mvd.getUpFlowm(),
				mvd.getUpFlows()));
		mvd.setUpHeadway(data.getUpHeadway());
		mvd.setUpOcc(data.getUpOcc());
		mvd.setUpOccb(data.getUpOccb());
		mvd.setUpOccm(data.getUpOccm());
		mvd.setUpOccs(data.getUpOccs());
		mvd.setUpSpeed(data.getUpSpeed());
		mvd.setUpSpeedb(data.getUpSpeedb());
		mvd.setUpSpeedm(data.getUpSpeedm());
		mvd.setUpSpeeds(data.getUpSpeeds());
		return mvd;
	}

	/**
	 * 合并天采集数据到月数据中
	 * 
	 * @param data
	 *            天采集数据
	 * @param mvd
	 *            月采集数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-20 上午10:39:15
	 */
	private void mergeMvd(DayVehichleDetector data, MonthVehichleDetector mvd) {
		mvd.setDateTime(data.getDateTime());
		mvd.setDwFlowb(NumberUtil.plusFloat(mvd.getDwFlowb(), data.getDwFlowb()));
		mvd.setDwFlowm(NumberUtil.plusFloat(mvd.getDwFlowm(), data.getDwFlowm()));
		mvd.setDwFlows(NumberUtil.plusFloat(mvd.getDwFlows(), data.getDwFlows()));
		mvd.setDwFlow(NumberUtil.plusFloat(mvd.getDwFlowb(), mvd.getDwFlowm(),
				mvd.getDwFlows()));
		mvd.setDwHeadway(NumberUtil.avgFloat(mvd.getDwHeadway(),
				data.getDwHeadway()));
		mvd.setDwOcc(NumberUtil.avgFloat(mvd.getDwOcc(), data.getDwOcc()));
		mvd.setDwOccb(NumberUtil.avgFloat(mvd.getDwOccb(), data.getDwOccb()));
		mvd.setDwOccm(NumberUtil.avgFloat(mvd.getDwOccm(), data.getDwOccm()));
		mvd.setDwOccs(NumberUtil.avgFloat(mvd.getDwOccs(), data.getDwOccs()));
		mvd.setDwSpeed(avgSpeed(mvd.getDwFlow(), mvd.getDwSpeed(),
				data.getDwFlow(), data.getDwSpeed()));
		mvd.setDwSpeedb(avgSpeed(mvd.getDwFlowb(), mvd.getDwSpeedb(),
				data.getDwFlowb(), data.getDwSpeedb()));
		mvd.setDwSpeedm(avgSpeed(mvd.getDwFlowm(), mvd.getDwSpeedm(),
				data.getDwFlowm(), data.getDwSpeedm()));
		mvd.setDwSpeeds(avgSpeed(mvd.getDwFlows(), mvd.getDwSpeeds(),
				data.getDwFlows(), data.getDwSpeeds()));

		mvd.setUpFlowb(NumberUtil.plusFloat(mvd.getUpFlowb(), data.getUpFlowb()));
		mvd.setUpFlowm(NumberUtil.plusFloat(mvd.getUpFlowm(), data.getUpFlowm()));
		mvd.setUpFlows(NumberUtil.plusFloat(mvd.getUpFlows(), data.getUpFlows()));
		mvd.setUpFlow(NumberUtil.plusFloat(mvd.getUpFlowb(), mvd.getUpFlowm(),
				mvd.getUpFlows()));
		mvd.setUpHeadway(NumberUtil.avgFloat(mvd.getUpHeadway(),
				data.getUpHeadway()));
		mvd.setUpOcc(NumberUtil.avgFloat(mvd.getUpOcc(), data.getUpOcc()));
		mvd.setUpOccb(NumberUtil.avgFloat(mvd.getUpOccb(), data.getUpOccb()));
		mvd.setUpOccm(NumberUtil.avgFloat(mvd.getUpOccm(), data.getUpOccm()));
		mvd.setUpOccs(NumberUtil.avgFloat(mvd.getUpOccs(), data.getUpOccs()));
		mvd.setUpSpeed(avgSpeed(mvd.getUpFlow(), mvd.getUpSpeed(),
				data.getUpFlow(), data.getUpSpeed()));
		mvd.setUpSpeedb(avgSpeed(mvd.getUpFlowb(), mvd.getUpSpeedb(),
				data.getUpFlowb(), data.getUpSpeedb()));
		mvd.setUpSpeedm(avgSpeed(mvd.getUpFlowm(), mvd.getUpSpeedm(),
				data.getUpFlowm(), data.getUpSpeedm()));
		mvd.setUpSpeeds(avgSpeed(mvd.getUpFlows(), mvd.getUpSpeeds(),
				data.getUpFlows(), data.getUpSpeeds()));
	}

	/**
	 * 初始化气象采集归并数据 气象设备采集上报，当前只保送平均值（瞬时值），因此最大最小值通过平均值来计算
	 * 
	 * @param data
	 *            气象采集原始数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 下午2:48:19
	 */
	private HourWeatherStat initHwst(DasWst data) {
		HourWeatherStat hwst = new HourWeatherStat();
		hwst.setAirTempAvg(NumberUtil.getFloat(data.getAirTempAvg()));
		hwst.setAirTempMax(NumberUtil.getFloat(data.getAirTempAvg()));
		hwst.setAirTempMin(NumberUtil.getFloat(data.getAirTempAvg()));
		hwst.setDateTime(data.getRecTime().getTime());
		hwst.setHumiAvg(NumberUtil.getFloat(data.getHumiAvg()));
		hwst.setHumiMax(NumberUtil.getFloat(data.getHumiAvg()));
		hwst.setHumiMin(NumberUtil.getFloat(data.getHumiAvg()));
		hwst.setRainVol(NumberUtil.getFloat(data.getRainVol()));
		hwst.setRoadSurface(NumberUtil.getInteger(data.getRoadSurface()));
		hwst.setRoadTempAvg(NumberUtil.getFloat(data.getRoadTempAvg()));
		hwst.setRoadTempMax(NumberUtil.getFloat(data.getRoadTempAvg()));
		hwst.setRoadTempMin(NumberUtil.getFloat(data.getRoadTempAvg()));
		hwst.setSnowVol(NumberUtil.getFloat(data.getSnowVol()));
		hwst.setStandardNumber(data.getStandardNumber());
		hwst.setVisAvg(NumberUtil.getFloat(data.getVisAvg()));
		hwst.setVisMax(NumberUtil.getFloat(data.getVisAvg()));
		hwst.setVisMin(NumberUtil.getFloat(data.getVisAvg()));
		hwst.setWinDir(NumberUtil.getFloat(data.getWindDir()));
		hwst.setWsAvg(NumberUtil.getFloat(data.getWsAvg()));
		hwst.setWsMax(NumberUtil.getFloat(data.getWsAvg()));
		hwst.setWsMin(NumberUtil.getFloat(data.getWsAvg()));
		return hwst;
	}

	/**
	 * 归并采集原始数据
	 * 
	 * @param data
	 *            采集原始数据
	 * @param hwst
	 *            归并数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 下午2:50:37
	 */
	private void mergeHwst(DasWst data, HourWeatherStat hwst) {
		hwst.setAirTempAvg(NumberUtil.avgFloat(hwst.getAirTempAvg(),
				NumberUtil.getFloat(data.getAirTempAvg())));
		hwst.setAirTempMax(NumberUtil.maxFloat(hwst.getAirTempMax(),
				NumberUtil.getFloat(data.getAirTempAvg())));
		hwst.setAirTempMin(NumberUtil.minFloat(hwst.getAirTempMin(),
				NumberUtil.getFloat(data.getAirTempAvg())));
		hwst.setDateTime(data.getRecTime().getTime());
		hwst.setHumiAvg(NumberUtil.avgFloat(hwst.getHumiAvg(),
				NumberUtil.getFloat(data.getHumiAvg())));
		hwst.setHumiMax(NumberUtil.maxFloat(hwst.getHumiMax(),
				NumberUtil.getFloat(data.getHumiAvg())));
		hwst.setHumiMin(NumberUtil.minFloat(hwst.getHumiMin(),
				NumberUtil.getFloat(data.getHumiAvg())));
		// 降雨量按照最大值计算
		hwst.setRainVol(NumberUtil.maxFloat(hwst.getRainVol(),
				NumberUtil.getFloat(data.getRainVol())));
		hwst.setRoadSurface(NumberUtil.getInteger(data.getRoadSurface()));
		hwst.setRoadTempAvg(NumberUtil.avgFloat(hwst.getRoadTempAvg(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		hwst.setRoadTempMax(NumberUtil.maxFloat(hwst.getRoadTempMax(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		hwst.setRoadTempMin(NumberUtil.minFloat(hwst.getRoadTempMin(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		// 降雪量按照最大值计算
		hwst.setSnowVol(NumberUtil.maxFloat(hwst.getSnowVol(),
				NumberUtil.getFloat(data.getSnowVol())));
		hwst.setVisAvg(NumberUtil.avgFloat(hwst.getVisAvg(),
				NumberUtil.getFloat(data.getVisAvg())));
		hwst.setVisMax(NumberUtil.maxFloat(hwst.getVisMax(),
				NumberUtil.getFloat(data.getVisAvg())));
		hwst.setVisMin(NumberUtil.minFloat(hwst.getVisMin(),
				NumberUtil.getFloat(data.getVisAvg())));
		hwst.setWinDir(NumberUtil.getFloat(data.getWindDir()));
		hwst.setWsAvg(NumberUtil.avgFloat(hwst.getWsAvg(),
				NumberUtil.getFloat(data.getWsAvg())));
		hwst.setWsMax(NumberUtil.maxFloat(hwst.getWsMax(),
				NumberUtil.getFloat(data.getWsAvg())));
		hwst.setWsMin(NumberUtil.minFloat(hwst.getWsMin(),
				NumberUtil.getFloat(data.getWsAvg())));
	}

	private DayWeatherStat initDwst(HourWeatherStat data) {
		DayWeatherStat dwst = new DayWeatherStat();
		dwst.setAirTempAvg(data.getAirTempAvg());
		dwst.setAirTempMax(data.getAirTempMax());
		dwst.setAirTempMin(data.getAirTempMin());
		dwst.setDateTime(data.getDateTime());
		dwst.setHumiAvg(data.getHumiAvg());
		dwst.setHumiMax(data.getHumiMax());
		dwst.setHumiMin(data.getHumiMin());
		dwst.setRainVol(data.getRainVol());
		dwst.setRoadSurface(data.getRoadSurface());
		dwst.setRoadTempAvg(data.getRoadTempAvg());
		dwst.setRoadTempMax(data.getRoadTempMax());
		dwst.setRoadTempMin(data.getRoadTempMin());
		dwst.setSnowVol(data.getSnowVol());
		dwst.setStandardNumber(data.getStandardNumber());
		dwst.setVisAvg(data.getVisAvg());
		dwst.setVisMax(data.getVisMax());
		dwst.setVisMin(data.getVisMin());
		dwst.setWinDir(data.getWinDir());
		dwst.setWsAvg(data.getWsAvg());
		dwst.setWsMax(data.getWsMax());
		dwst.setWsMin(data.getWsMin());
		return dwst;
	}

	private void mergeDwst(HourWeatherStat data, DayWeatherStat dwst) {
		dwst.setAirTempAvg(NumberUtil.avgFloat(dwst.getAirTempAvg(),
				data.getAirTempAvg()));
		dwst.setAirTempMax(NumberUtil.maxFloat(dwst.getAirTempMax(),
				data.getAirTempMax()));
		dwst.setAirTempMin(NumberUtil.minFloat(dwst.getAirTempMin(),
				data.getAirTempMin()));
		dwst.setDateTime(data.getDateTime());
		dwst.setHumiAvg(NumberUtil.avgFloat(dwst.getHumiAvg(),
				data.getHumiAvg()));
		dwst.setHumiMax(NumberUtil.maxFloat(dwst.getHumiMax(),
				data.getHumiMax()));
		dwst.setHumiMin(NumberUtil.minFloat(dwst.getHumiMin(),
				data.getHumiMin()));
		// 降雨量按照最大值计算
		dwst.setRainVol(NumberUtil.maxFloat(dwst.getRainVol(),
				data.getRainVol()));
		dwst.setRoadSurface(data.getRoadSurface());
		dwst.setRoadTempAvg(NumberUtil.avgFloat(dwst.getRoadTempAvg(),
				data.getRoadTempAvg()));
		dwst.setRoadTempMax(NumberUtil.maxFloat(dwst.getRoadTempMax(),
				data.getRoadTempMax()));
		dwst.setRoadTempMin(NumberUtil.minFloat(dwst.getRoadTempMin(),
				data.getRoadTempMin()));
		// 降雪量按照最大值计算
		dwst.setSnowVol(NumberUtil.maxFloat(dwst.getSnowVol(),
				data.getSnowVol()));
		dwst.setVisAvg(NumberUtil.avgFloat(dwst.getVisAvg(), data.getVisAvg()));
		dwst.setVisMax(NumberUtil.maxFloat(dwst.getVisMax(), data.getVisMax()));
		dwst.setVisMin(NumberUtil.minFloat(dwst.getVisMin(), data.getVisMin()));
		dwst.setWinDir(data.getWinDir());
		dwst.setWsAvg(NumberUtil.avgFloat(dwst.getWsAvg(), data.getWsAvg()));
		dwst.setWsMax(NumberUtil.maxFloat(dwst.getWsMax(), data.getWsMax()));
		dwst.setWsMin(NumberUtil.minFloat(dwst.getWsMin(), data.getWsMin()));
	}

	private MonthWeatherStat initMwst(DayWeatherStat data) {
		MonthWeatherStat mwst = new MonthWeatherStat();
		mwst.setAirTempAvg(data.getAirTempAvg());
		mwst.setAirTempMax(data.getAirTempMax());
		mwst.setAirTempMin(data.getAirTempMin());
		mwst.setDateTime(data.getDateTime());
		mwst.setHumiAvg(data.getHumiAvg());
		mwst.setHumiMax(data.getHumiMax());
		mwst.setHumiMin(data.getHumiMin());
		mwst.setRainVol(data.getRainVol());
		mwst.setRoadSurface(data.getRoadSurface());
		mwst.setRoadTempAvg(data.getRoadTempAvg());
		mwst.setRoadTempMax(data.getRoadTempMax());
		mwst.setRoadTempMin(data.getRoadTempMin());
		mwst.setSnowVol(data.getSnowVol());
		mwst.setStandardNumber(data.getStandardNumber());
		mwst.setVisAvg(data.getVisAvg());
		mwst.setVisMax(data.getVisMax());
		mwst.setVisMin(data.getVisMin());
		mwst.setWinDir(data.getWinDir());
		mwst.setWsAvg(data.getWsAvg());
		mwst.setWsMax(data.getWsMax());
		mwst.setWsMin(data.getWsMin());
		return mwst;
	}

	private void mergeMwst(DayWeatherStat data, MonthWeatherStat mwst) {
		mwst.setAirTempAvg(NumberUtil.avgFloat(mwst.getAirTempAvg(),
				data.getAirTempAvg()));
		mwst.setAirTempMax(NumberUtil.maxFloat(mwst.getAirTempMax(),
				data.getAirTempMax()));
		mwst.setAirTempMin(NumberUtil.minFloat(mwst.getAirTempMin(),
				data.getAirTempMin()));
		mwst.setDateTime(data.getDateTime());
		mwst.setHumiAvg(NumberUtil.avgFloat(mwst.getHumiAvg(),
				data.getHumiAvg()));
		mwst.setHumiMax(NumberUtil.maxFloat(mwst.getHumiMax(),
				data.getHumiMax()));
		mwst.setHumiMin(NumberUtil.minFloat(mwst.getHumiMin(),
				data.getHumiMin()));
		// 降雨量按照最大值计算
		mwst.setRainVol(NumberUtil.maxFloat(mwst.getRainVol(),
				data.getRainVol()));
		mwst.setRoadSurface(data.getRoadSurface());
		mwst.setRoadTempAvg(NumberUtil.avgFloat(mwst.getRoadTempAvg(),
				data.getRoadTempAvg()));
		mwst.setRoadTempMax(NumberUtil.maxFloat(mwst.getRoadTempMax(),
				data.getRoadTempMax()));
		mwst.setRoadTempMin(NumberUtil.minFloat(mwst.getRoadTempMin(),
				data.getRoadTempMin()));
		// 降雪量按照最大值计算
		mwst.setSnowVol(NumberUtil.maxFloat(mwst.getSnowVol(),
				data.getSnowVol()));
		mwst.setVisAvg(NumberUtil.avgFloat(mwst.getVisAvg(), data.getVisAvg()));
		mwst.setVisMax(NumberUtil.maxFloat(mwst.getVisMax(), data.getVisMax()));
		mwst.setVisMin(NumberUtil.minFloat(mwst.getVisMin(), data.getVisMin()));
		mwst.setWinDir(data.getWinDir());
		mwst.setWsAvg(NumberUtil.avgFloat(mwst.getWsAvg(), data.getWsAvg()));
		mwst.setWsMax(NumberUtil.maxFloat(mwst.getWsMax(), data.getWsMax()));
		mwst.setWsMin(NumberUtil.minFloat(mwst.getWsMin(), data.getWsMin()));
	}

	/**
	 * 初始化路面采集归并数据，最大最小值通过平均值来计算
	 * 
	 * @param data
	 *            气象采集原始数据
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-9-2 上午10:46:48
	 */
	private HourRoadDetector initHrsd(DasRoadDetector data) {
		HourRoadDetector hrsd = new HourRoadDetector();
		hrsd.setDateTime(data.getRecTime().getTime());
		hrsd.setRainVol(NumberUtil.getFloat(data.getRainVol()));
		hrsd.setRoadSurface(NumberUtil.getInteger(data.getRoadSurface()));
		hrsd.setRoadTempAvg(NumberUtil.getFloat(data.getRoadTempAvg()));
		hrsd.setRoadTempMax(NumberUtil.getFloat(data.getRoadTempAvg()));
		hrsd.setRoadTempMin(NumberUtil.getFloat(data.getRoadTempAvg()));
		hrsd.setSnowVol(NumberUtil.getFloat(data.getSnowVol()));
		hrsd.setStandardNumber(data.getStandardNumber());
		return hrsd;
	}

	/**
	 * 归并采集原始数据
	 * 
	 * @param data
	 *            采集原始数据
	 * @param hwst
	 *            归并数据
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 下午2:50:37
	 */
	private void mergeHrsd(DasRoadDetector data, HourRoadDetector hrsd) {
		hrsd.setDateTime(data.getRecTime().getTime());
		// 降雨量按照最大值计算
		hrsd.setRainVol(NumberUtil.maxFloat(hrsd.getRainVol(),
				NumberUtil.getFloat(data.getRainVol())));
		hrsd.setRoadSurface(NumberUtil.getInteger(data.getRoadSurface()));
		hrsd.setRoadTempAvg(NumberUtil.avgFloat(hrsd.getRoadTempAvg(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		hrsd.setRoadTempMax(NumberUtil.maxFloat(hrsd.getRoadTempMax(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		hrsd.setRoadTempMin(NumberUtil.minFloat(hrsd.getRoadTempMin(),
				NumberUtil.getFloat(data.getRoadTempAvg())));
		// 降雪量按照最大值计算
		hrsd.setSnowVol(NumberUtil.maxFloat(hrsd.getSnowVol(),
				NumberUtil.getFloat(data.getSnowVol())));
	}

	private DayRoadDetector initDrsd(HourRoadDetector data) {
		DayRoadDetector drsd = new DayRoadDetector();
		drsd.setDateTime(data.getDateTime());
		drsd.setRainVol(data.getRainVol());
		drsd.setRoadSurface(data.getRoadSurface());
		drsd.setRoadTempAvg(data.getRoadTempAvg());
		drsd.setRoadTempMax(data.getRoadTempMax());
		drsd.setRoadTempMin(data.getRoadTempMin());
		drsd.setSnowVol(data.getSnowVol());
		drsd.setStandardNumber(data.getStandardNumber());
		return drsd;
	}

	private void mergeDrsd(HourRoadDetector data, DayRoadDetector drsd) {
		drsd.setDateTime(data.getDateTime());
		// 降雨量按照最大值计算
		drsd.setRainVol(NumberUtil.maxFloat(drsd.getRainVol(),
				data.getRainVol()));
		drsd.setRoadSurface(data.getRoadSurface());
		drsd.setRoadTempAvg(NumberUtil.avgFloat(drsd.getRoadTempAvg(),
				data.getRoadTempAvg()));
		drsd.setRoadTempMax(NumberUtil.maxFloat(drsd.getRoadTempMax(),
				data.getRoadTempMax()));
		drsd.setRoadTempMin(NumberUtil.minFloat(drsd.getRoadTempMin(),
				data.getRoadTempMin()));
		// 降雪量按照最大值计算
		drsd.setSnowVol(NumberUtil.maxFloat(drsd.getSnowVol(),
				data.getSnowVol()));
	}

	private MonthRoadDetector initMrsd(DayRoadDetector data) {
		MonthRoadDetector mrsd = new MonthRoadDetector();
		mrsd.setDateTime(data.getDateTime());
		mrsd.setRainVol(data.getRainVol());
		mrsd.setRoadSurface(data.getRoadSurface());
		mrsd.setRoadTempAvg(data.getRoadTempAvg());
		mrsd.setRoadTempMax(data.getRoadTempMax());
		mrsd.setRoadTempMin(data.getRoadTempMin());
		mrsd.setSnowVol(data.getSnowVol());
		mrsd.setStandardNumber(data.getStandardNumber());
		return mrsd;
	}

	private void mergeMrsd(DayRoadDetector data, MonthRoadDetector mrsd) {
		mrsd.setDateTime(data.getDateTime());
		// 降雨量按照最大值计算
		mrsd.setRainVol(NumberUtil.maxFloat(mrsd.getRainVol(),
				data.getRainVol()));
		mrsd.setRoadSurface(data.getRoadSurface());
		mrsd.setRoadTempAvg(NumberUtil.avgFloat(mrsd.getRoadTempAvg(),
				data.getRoadTempAvg()));
		mrsd.setRoadTempMax(NumberUtil.maxFloat(mrsd.getRoadTempMax(),
				data.getRoadTempMax()));
		mrsd.setRoadTempMin(NumberUtil.minFloat(mrsd.getRoadTempMin(),
				data.getRoadTempMin()));
		// 降雪量按照最大值计算
		mrsd.setSnowVol(NumberUtil.maxFloat(mrsd.getSnowVol(),
				data.getSnowVol()));
	}

	private HourCoviDetector initHcovi(DasCovi data) {
		HourCoviDetector hcovi = new HourCoviDetector();
		hcovi.setDateTime(data.getRecTime().getTime());
		hcovi.setCoAvg(NumberUtil.getFloat(data.getCo()));
		hcovi.setCoMax(NumberUtil.getFloat(data.getCo()));
		hcovi.setCoMin(NumberUtil.getFloat(data.getCo()));
		hcovi.setViAvg(NumberUtil.getFloat(data.getVi()));
		hcovi.setViMax(NumberUtil.getFloat(data.getVi()));
		hcovi.setViMin(NumberUtil.getFloat(data.getVi()));
		hcovi.setStandardNumber(data.getStandardNumber());
		return hcovi;
	}

	private void mergeHcovi(DasCovi data, HourCoviDetector hcovi) {
		hcovi.setDateTime(data.getRecTime().getTime());
		hcovi.setCoAvg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getCo()),
				hcovi.getCoAvg()));
		hcovi.setCoMax(NumberUtil.maxFloat(NumberUtil.getFloat(data.getCo()),
				hcovi.getCoMax()));
		hcovi.setCoMin(NumberUtil.minFloat(NumberUtil.getFloat(data.getCo()),
				hcovi.getCoMin()));
		hcovi.setViAvg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getVi()),
				hcovi.getViAvg()));
		hcovi.setViMax(NumberUtil.maxFloat(NumberUtil.getFloat(data.getVi()),
				hcovi.getViMax()));
		hcovi.setViMin(NumberUtil.minFloat(NumberUtil.getFloat(data.getVi()),
				hcovi.getViMin()));
	}

	private DayCoviDetector initDcovi(HourCoviDetector data) {
		DayCoviDetector dcovi = new DayCoviDetector();
		dcovi.setDateTime(data.getDateTime());
		dcovi.setCoAvg(data.getCoAvg());
		dcovi.setCoMax(data.getCoMax());
		dcovi.setCoMin(data.getCoMin());
		dcovi.setViAvg(data.getViAvg());
		dcovi.setViMax(data.getViMax());
		dcovi.setViMin(data.getViMin());
		dcovi.setStandardNumber(data.getStandardNumber());
		return dcovi;
	}

	private void mergeDcovi(HourCoviDetector data, DayCoviDetector dcovi) {
		dcovi.setDateTime(data.getDateTime());
		dcovi.setCoAvg(NumberUtil.avgFloat(data.getCoAvg(), dcovi.getCoAvg()));
		dcovi.setCoMax(NumberUtil.maxFloat(data.getCoMax(), dcovi.getCoMax()));
		dcovi.setCoMin(NumberUtil.minFloat(data.getCoMin(), dcovi.getCoMin()));
		dcovi.setViAvg(NumberUtil.avgFloat(data.getViAvg(), dcovi.getViAvg()));
		dcovi.setViMax(NumberUtil.maxFloat(data.getViMax(), dcovi.getViMax()));
		dcovi.setViMin(NumberUtil.minFloat(data.getViMin(), dcovi.getViMin()));
	}

	private MonthCoviDetector initMcovi(DayCoviDetector data) {
		MonthCoviDetector mcovi = new MonthCoviDetector();
		mcovi.setDateTime(data.getDateTime());
		mcovi.setCoAvg(data.getCoAvg());
		mcovi.setCoMax(data.getCoMax());
		mcovi.setCoMin(data.getCoMin());
		mcovi.setViAvg(data.getViAvg());
		mcovi.setViMax(data.getViMax());
		mcovi.setViMin(data.getViMin());
		mcovi.setStandardNumber(data.getStandardNumber());
		return mcovi;
	}

	private void mergeMcovi(DayCoviDetector data, MonthCoviDetector mcovi) {
		mcovi.setDateTime(data.getDateTime());
		mcovi.setCoAvg(NumberUtil.avgFloat(data.getCoAvg(), mcovi.getCoAvg()));
		mcovi.setCoMax(NumberUtil.maxFloat(data.getCoMax(), mcovi.getCoMax()));
		mcovi.setCoMin(NumberUtil.minFloat(data.getCoMin(), mcovi.getCoMin()));
		mcovi.setViAvg(NumberUtil.avgFloat(data.getViAvg(), mcovi.getViAvg()));
		mcovi.setViMax(NumberUtil.maxFloat(data.getViMax(), mcovi.getViMax()));
		mcovi.setViMin(NumberUtil.minFloat(data.getViMin(), mcovi.getViMin()));
	}

	private HourLoliDetector initHloli(DasLoli data) {
		HourLoliDetector hloli = new HourLoliDetector();
		hloli.setDateTime(data.getRecTime().getTime());
		hloli.setLoAvg(NumberUtil.getFloat(data.getLo()));
		hloli.setLoMax(NumberUtil.getFloat(data.getLo()));
		hloli.setLoMin(NumberUtil.getFloat(data.getLo()));
		hloli.setLiAvg(NumberUtil.getFloat(data.getLi()));
		hloli.setLiMax(NumberUtil.getFloat(data.getLi()));
		hloli.setLiMin(NumberUtil.getFloat(data.getLi()));
		hloli.setStandardNumber(data.getStandardNumber());
		return hloli;
	}

	private void mergeHloli(DasLoli data, HourLoliDetector hloli) {
		hloli.setDateTime(data.getRecTime().getTime());
		hloli.setLoAvg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getLo()),
				hloli.getLoAvg()));
		hloli.setLoMax(NumberUtil.maxFloat(NumberUtil.getFloat(data.getLo()),
				hloli.getLoMax()));
		hloli.setLoMin(NumberUtil.minFloat(NumberUtil.getFloat(data.getLo()),
				hloli.getLoMin()));
		hloli.setLiAvg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getLi()),
				hloli.getLiAvg()));
		hloli.setLiMax(NumberUtil.maxFloat(NumberUtil.getFloat(data.getLi()),
				hloli.getLiMax()));
		hloli.setLiMin(NumberUtil.minFloat(NumberUtil.getFloat(data.getLi()),
				hloli.getLiMin()));
	}

	private DayLoliDetector initDloli(HourLoliDetector data) {
		DayLoliDetector dloli = new DayLoliDetector();
		dloli.setDateTime(data.getDateTime());
		dloli.setLoAvg(data.getLoAvg());
		dloli.setLoMax(data.getLoMax());
		dloli.setLoMin(data.getLoMin());
		dloli.setLiAvg(data.getLiAvg());
		dloli.setLiMax(data.getLiMax());
		dloli.setLiMin(data.getLiMin());
		dloli.setStandardNumber(data.getStandardNumber());
		return dloli;
	}

	private void mergeDloli(HourLoliDetector data, DayLoliDetector dloli) {
		dloli.setDateTime(data.getDateTime());
		dloli.setLoAvg(NumberUtil.avgFloat(data.getLoAvg(), dloli.getLoAvg()));
		dloli.setLoMax(NumberUtil.maxFloat(data.getLoMax(), dloli.getLoMax()));
		dloli.setLoMin(NumberUtil.minFloat(data.getLoMin(), dloli.getLoMin()));
		dloli.setLiAvg(NumberUtil.avgFloat(data.getLiAvg(), dloli.getLiAvg()));
		dloli.setLiMax(NumberUtil.maxFloat(data.getLiMax(), dloli.getLiMax()));
		dloli.setLiMin(NumberUtil.minFloat(data.getLiMin(), dloli.getLiMin()));
	}

	private MonthLoliDetector initMloli(DayLoliDetector data) {
		MonthLoliDetector mloli = new MonthLoliDetector();
		mloli.setDateTime(data.getDateTime());
		mloli.setLoAvg(data.getLoAvg());
		mloli.setLoMax(data.getLoMax());
		mloli.setLoMin(data.getLoMin());
		mloli.setLiAvg(data.getLiAvg());
		mloli.setLiMax(data.getLiMax());
		mloli.setLiMin(data.getLiMin());
		mloli.setStandardNumber(data.getStandardNumber());
		return mloli;
	}

	private void mergeMloli(DayLoliDetector data, MonthLoliDetector mloli) {
		mloli.setDateTime(data.getDateTime());
		mloli.setLoAvg(NumberUtil.avgFloat(data.getLoAvg(), mloli.getLoAvg()));
		mloli.setLoMax(NumberUtil.maxFloat(data.getLoMax(), mloli.getLoMax()));
		mloli.setLoMin(NumberUtil.minFloat(data.getLoMin(), mloli.getLoMin()));
		mloli.setLiAvg(NumberUtil.avgFloat(data.getLiAvg(), mloli.getLiAvg()));
		mloli.setLiMax(NumberUtil.maxFloat(data.getLiMax(), mloli.getLiMax()));
		mloli.setLiMin(NumberUtil.minFloat(data.getLiMin(), mloli.getLiMin()));
	}

	@Override
	public void reduceNoByHour() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间上1小时的数据
		Timestamp end = new Timestamp(DateUtil.getLowerHourTime(new Date()));

		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		HourNoDetector last = hourNoDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}

		// 统计原始数据条数，每次任务最多归并10w条原始数据
		List<DasNod> list = dasNodDAO.listDasNo(begin, end, 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(list.get(list.size() - 1)
					.getRecTime());
			List<DasNod> removeList = new LinkedList<DasNod>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getRecTime().getTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}

		// 第一条记录的采集时间
		Timestamp first = list.get(0).getRecTime();
		// 计算得到第一个小时段的最大时间
		long upperLimit = DateUtil.getUpperHourTime(first);
		long lowerLimit = DateUtil.getLowerHourTime(first);

		// 要归并的数据集合,每个设备每小时一条数据, key-为设备SN, value-为该设备各小时的记录
		Map<String, List<HourNoDetector>> map = new HashMap<String, List<HourNoDetector>>();
		// 迭代采集数据，按小时归并
		for (DasNod data : list) {
			long recTime = data.getRecTime().getTime();
			String sn = data.getStandardNumber();
			// 本小时的归并数据
			HourNoDetector hnod = null;
			// 在本小时内计算统计值
			if (recTime < upperLimit) {
				List<HourNoDetector> hnodList = map.get(sn);
				// 初始化map
				if (hnodList == null) {
					hnodList = new LinkedList<HourNoDetector>();
					map.put(sn, hnodList);
				}
				// 初始化本小时数据
				if (hnodList.size() < 1) {
					hnod = initHnod(data);
					hnodList.add(hnod);
				} else {
					// 最后一条记录
					hnod = hnodList.get(hnodList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hnod.getDateTime().longValue() < lowerLimit) {
						HourNoDetector nextHnod = initHnod(data);
						hnodList.add(nextHnod);
					} else {
						mergeHnod(data, hnod);
					}
				}
			}
			// 初始化计算下1小时的数据
			else {
				upperLimit = DateUtil.getUpperHourTime(data.getRecTime());
				lowerLimit = DateUtil.getLowerHourTime(data.getRecTime());
				List<HourNoDetector> hnodList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (hnodList == null) {
					hnodList = new LinkedList<HourNoDetector>();
					map.put(sn, hnodList);
				}
				// 初始化下1小时数据
				if (hnodList.size() < 1) {
					hnod = initHnod(data);
					hnodList.add(hnod);
				} else {
					// 最后一条记录
					hnod = hnodList.get(hnodList.size() - 1);
					// 归并数据, 注意达到下一个小时的初始化处理
					if (hnod.getDateTime().longValue() < lowerLimit) {
						HourNoDetector nextHnod = initHnod(data);
						hnodList.add(nextHnod);
					} else {
						mergeHnod(data, hnod);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<HourNoDetector> insertList = new LinkedList<HourNoDetector>();
		Collection<List<HourNoDetector>> values = map.values();
		for (List<HourNoDetector> value : values) {
			insertList.addAll(value);
		}
		hourNoDAO.batchInsert(insertList);
	}

	private void mergeHnod(DasNod data, HourNoDetector hnod) {
		hnod.setDateTime(data.getRecTime().getTime());
		hnod.setNo2Avg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getNo2()),
				hnod.getNo2Avg()));
		hnod.setNo2Max(NumberUtil.maxFloat(NumberUtil.getFloat(data.getNo2()),
				hnod.getNo2Max()));
		hnod.setNo2Min(NumberUtil.minFloat(NumberUtil.getFloat(data.getNo2()),
				hnod.getNo2Min()));
		hnod.setNoAvg(NumberUtil.avgFloat(NumberUtil.getFloat(data.getNo()),
				hnod.getNoAvg()));
		hnod.setNoMax(NumberUtil.maxFloat(NumberUtil.getFloat(data.getNo()),
				hnod.getNoMax()));
		hnod.setNoMin(NumberUtil.minFloat(NumberUtil.getFloat(data.getNo()),
				hnod.getNoMin()));
	}

	private HourNoDetector initHnod(DasNod data) {
		HourNoDetector hnod = new HourNoDetector();
		hnod.setDateTime(data.getRecTime().getTime());
		hnod.setNo2Avg(NumberUtil.getFloat(data.getNo2()));
		hnod.setNo2Max(NumberUtil.getFloat(data.getNo2()));
		hnod.setNo2Min(NumberUtil.getFloat(data.getNo2()));
		hnod.setNoAvg(NumberUtil.getFloat(data.getNo()));
		hnod.setNoMax(NumberUtil.getFloat(data.getNo()));
		hnod.setNoMin(NumberUtil.getFloat(data.getNo()));
		hnod.setStandardNumber(data.getStandardNumber());
		return hnod;
	}

	@Override
	public void reduceNoByDay() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1天的数据
		Timestamp end = new Timestamp(DateUtil.getLowerDayTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		DayNoDetector last = dayNoDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录,beginTime+1目的是不重复查询
		List<HourNoDetector> list = hourNoDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<HourNoDetector> removeList = new LinkedList<HourNoDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一天时段的最大和最小时间
		long upperLimit = DateUtil.getUpperDayTime(first);
		long lowerLimit = DateUtil.getLowerDayTime(first);

		// 要归并的数据集合,每个设备每天一条数据, key-为设备SN, value-为该设备各天的记录
		Map<String, List<DayNoDetector>> map = new HashMap<String, List<DayNoDetector>>();
		// 迭代采集数据，按天归并
		for (HourNoDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当天的归并数据
			DayNoDetector dnod = null;
			// 在当天内计算统计值
			if (recTime < upperLimit) {
				List<DayNoDetector> dnoList = map.get(sn);
				// 初始化map
				if (dnoList == null) {
					dnoList = new LinkedList<DayNoDetector>();
					map.put(sn, dnoList);
				}
				// 初始化当天数据
				if (dnoList.size() < 1) {
					dnod = initDnod(data);
					dnoList.add(dnod);
				} else {
					// 最后一条记录
					dnod = dnoList.get(dnoList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dnod.getDateTime().longValue() < lowerLimit) {
						DayNoDetector nextDnod = initDnod(data);
						dnoList.add(nextDnod);
					} else {
						mergeDnod(data, dnod);
					}
				}
			}
			// 初始化计算下1天的数据
			else {
				upperLimit = DateUtil.getUpperDayTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerDayTime(new Date(data
						.getDateTime()));
				List<DayNoDetector> dnodList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (dnodList == null) {
					dnodList = new LinkedList<DayNoDetector>();
					map.put(sn, dnodList);
				}
				// 初始化下1天数据
				if (dnodList.size() < 1) {
					dnod = initDnod(data);
					dnodList.add(dnod);
				} else {
					// 最后一条记录
					dnod = dnodList.get(dnodList.size() - 1);
					// 归并数据, 注意达到下一天的初始化处理
					if (dnod.getDateTime().longValue() < lowerLimit) {
						DayNoDetector nextDnod = initDnod(data);
						dnodList.add(nextDnod);
					} else {
						mergeDnod(data, dnod);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<DayNoDetector> insertList = new LinkedList<DayNoDetector>();
		Collection<List<DayNoDetector>> values = map.values();
		for (List<DayNoDetector> value : values) {
			insertList.addAll(value);
		}
		dayNoDAO.batchInsert(insertList);

	}

	private void mergeDnod(HourNoDetector data, DayNoDetector dnod) {
		dnod.setDateTime(data.getDateTime());
		dnod.setNo2Avg((NumberUtil.avgFloat(data.getNo2Avg(), dnod.getNo2Avg())));
		dnod.setNo2Max(NumberUtil.maxFloat(data.getNo2Max(), dnod.getNo2Max()));
		dnod.setNo2Min(NumberUtil.minFloat(data.getNo2Min(), dnod.getNo2Min()));
		dnod.setNoAvg(NumberUtil.avgFloat(data.getNoAvg(), dnod.getNoAvg()));
		dnod.setNoMax(NumberUtil.maxFloat(data.getNoMax(), dnod.getNoMax()));
		dnod.setNoMin(NumberUtil.minFloat(data.getNoMin(), dnod.getNoMin()));
	}

	private DayNoDetector initDnod(HourNoDetector data) {
		DayNoDetector dnod = new DayNoDetector();
		dnod.setDateTime(data.getDateTime());
		dnod.setNoAvg(data.getNoAvg());
		dnod.setNoMax(data.getNoMax());
		dnod.setNoMin(data.getNoMin());
		dnod.setNo2Avg(data.getNo2Avg());
		dnod.setNo2Max(data.getNo2Max());
		dnod.setNo2Min(data.getNo2Min());
		dnod.setStandardNumber(data.getStandardNumber());
		return dnod;
	}

	@Override
	public void reduceNoByMonth() {
		// 采集数据归并开始时间
		Timestamp begin = null;
		// 只归并当前系统时间前1月的数据
		Timestamp end = new Timestamp(DateUtil.getLowerMonthTime(new Date()));
		// 查询已有的归并记录，获取最后一次归并时间做为本次任务的开始时间
		MonthNoDetector last = monthNoDAO.getLastRecord();
		if (null != last) {
			begin = new Timestamp(last.getDateTime());
		} else {
			begin = new Timestamp(1);
		}
		// 每次最多处理10W条记录
		List<DayNoDetector> list = dayNoDAO.list(null, begin.getTime() + 1,
				end.getTime(), 0, 100000);
		// 没有采集数据，不做任何操作
		if (list.size() < 1) {
			return;
		}
		if (list.size() >= 100000) {
			long filter = DateUtil.getLowerHourTime(new Date(list.get(
					list.size() - 1).getDateTime()));
			List<DayNoDetector> removeList = new LinkedList<DayNoDetector>();
			for (int i = list.size() - 1; i < 0; i--) {
				if (list.get(i).getDateTime() > filter) {
					removeList.add(list.get(i));
				} else {
					break;
				}
			}
			list.removeAll(removeList);
		}
		
		// 第一条记录的采集时间
		Date first = new Date(list.get(0).getDateTime());
		// 计算得到第一个月时段的最大和最小时间
		long upperLimit = DateUtil.getUpperMonthTime(first);
		long lowerLimit = DateUtil.getLowerMonthTime(first);

		// 要归并的数据集合,每个设备每月一条数据, key-为设备SN, value-为该设备各月的记录
		Map<String, List<MonthNoDetector>> map = new HashMap<String, List<MonthNoDetector>>();
		// 迭代采集数据，按月归并
		for (DayNoDetector data : list) {
			long recTime = data.getDateTime();
			String sn = data.getStandardNumber();
			// 当月的归并数据
			MonthNoDetector mnod = null;
			// 在当月内计算统计值
			if (recTime < upperLimit) {
				List<MonthNoDetector> mnodList = map.get(sn);
				// 初始化map
				if (mnodList == null) {
					mnodList = new LinkedList<MonthNoDetector>();
					map.put(sn, mnodList);
				}
				// 初始化当月数据
				if (mnodList.size() < 1) {
					mnod = initMnod(data);
					mnodList.add(mnod);
				} else {
					// 最后一条记录
					mnod = mnodList.get(mnodList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mnod.getDateTime().longValue() < lowerLimit) {
						MonthNoDetector nextMnod = initMnod(data);
						mnodList.add(nextMnod);
					} else {
						mergeMnod(data, mnod);
					}
				}
			}
			// 初始化计算下个月的数据
			else {
				upperLimit = DateUtil.getUpperMonthTime(new Date(data
						.getDateTime()));
				lowerLimit = DateUtil.getLowerMonthTime(new Date(data
						.getDateTime()));
				List<MonthNoDetector> mnodList = map.get(sn);
				// 因为可能有新设备加入，所以同样需要初始化map
				if (mnodList == null) {
					mnodList = new LinkedList<MonthNoDetector>();
					map.put(sn, mnodList);
				}
				// 初始化下个月数据
				if (mnodList.size() < 1) {
					mnod = initMnod(data);
					mnodList.add(mnod);
				} else {
					// 最后一条记录
					mnod = mnodList.get(mnodList.size() - 1);
					// 归并数据, 注意达到下个月的初始化处理
					if (mnod.getDateTime().longValue() < lowerLimit) {
						MonthNoDetector nextMnod = initMnod(data);
						mnodList.add(nextMnod);
					} else {
						mergeMnod(data, mnod);
					}
				}
			}
		}

		// 要写入数据库的集合
		List<MonthNoDetector> insertList = new LinkedList<MonthNoDetector>();
		Collection<List<MonthNoDetector>> values = map.values();
		for (List<MonthNoDetector> value : values) {
			insertList.addAll(value);
		}
		monthNoDAO.batchInsert(insertList);

	}

	private void mergeMnod(DayNoDetector data, MonthNoDetector mnod) {
		mnod.setDateTime(data.getDateTime());
		mnod.setNoAvg(NumberUtil.avgFloat(data.getNoAvg(), mnod.getNoAvg()));
		mnod.setNoMax(NumberUtil.maxFloat(data.getNoMax(), mnod.getNoMax()));
		mnod.setNoMin(NumberUtil.minFloat(data.getNoMin(), mnod.getNoMin()));
		mnod.setNo2Avg(NumberUtil.avgFloat(data.getNo2Avg(), mnod.getNo2Avg()));
		mnod.setNo2Max(NumberUtil.maxFloat(data.getNo2Max(), mnod.getNo2Max()));
		mnod.setNo2Min(NumberUtil.minFloat(data.getNo2Min(), mnod.getNo2Min()));
	}

	private MonthNoDetector initMnod(DayNoDetector data) {
		MonthNoDetector mnod = new MonthNoDetector();
		mnod.setDateTime(data.getDateTime());
		mnod.setNoAvg(data.getNoAvg());
		mnod.setNoMax(data.getNoMax());
		mnod.setNoMin(data.getNoMin());
		mnod.setNo2Avg(data.getNo2Avg());
		mnod.setNo2Max(data.getNo2Max());
		mnod.setNo2Min(data.getNo2Min());
		mnod.setStandardNumber(data.getStandardNumber());
		return mnod;
	}

}
