package com.znsx.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-13 下午2:37:39
 */
public class DateUtil {
	/**
	 * 计算大于指定时间的最近1小时
	 * 
	 * @param date
	 *            时间
	 * @return 大于指定时间的最近1小时
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 下午2:30:40
	 */
	public static long getUpperHourTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 计算小于指定时间的最近1小时
	 * 
	 * @param date
	 * @return 小于指定时间的最近1小时
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 下午2:38:16
	 */
	public static long getLowerHourTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 计算小于指定时间的最近1天的开始时间（今天的0时）
	 * 
	 * @param date
	 *            时间
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午2:19:36
	 */
	public static long getLowerDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 计算大于指定时间的最近1天的开始时间（明天的0时）
	 * 
	 * @param date
	 *            时间
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-17 下午2:22:25
	 */
	public static long getUpperDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 计算大于指定时间的最近1个月的开始时间（下个月的开始）
	 * 
	 * @param date
	 *            时间
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-19 下午4:56:16
	 */
	public static long getUpperMonthTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 计算小于指定时间的最近1个月的开始时间（本月的开始）
	 * 
	 * @param date
	 *            时间
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-19 下午4:58:38
	 */
	public static long getLowerMonthTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static void main(String[] args) {
		System.out.println(new Date(getUpperMonthTime(new Date())));
		System.out.println(new Date(getLowerMonthTime(new Date())));
	}
}
