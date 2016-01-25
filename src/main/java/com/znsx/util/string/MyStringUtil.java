package com.znsx.util.string;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 字符串处理工具
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-6-18 下午3:07:15
 */
public class MyStringUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 字符串数组转换为给定间隔符分隔的字符串
	 * 
	 * @param array
	 *            字符串数组
	 * @param interval
	 *            间隔符
	 * @return 给定间隔符分隔的字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午3:09:55
	 */
	public static String array2String(String[] array, String interval) {
		if (null == array || array.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(null == array[i] ? "" : array[i]);
			sb.append(interval);
		}
		return sb.substring(0, sb.length() - interval.length());
	}

	/**
	 * 从键值对字符串数组中查找指定key的value
	 * 
	 * @param array
	 *            键值对字符串数组
	 * @param key
	 *            键
	 * @return 值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午3:23:20
	 */
	public static String getAttributeValue(String[] array, String key) {
		if (null == array || array.length == 0 || null == key) {
			return "";
		}
		for (int i = 0; i < array.length; i++) {
			String[] pair = array[i].split("=");
			if (pair.length < 1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						array2String(array, ","));
			}
			if (key.equals(pair[0])) {
				if (pair.length == 2) {
					return pair[1];
				} else {
					return "";
				}
			}
		}
		return "";
	}

	/**
	 * 往键值对字符串数组中设定指定键值
	 * 
	 * @param array
	 *            键值对字符串数组
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return 设定完成后的键值对数组
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-18 下午3:42:32
	 */
	public static String[] setAttributeValue(String[] array, String key,
			String value) {
		if (null == value) {
			value = "";
		}
		if (null == array || array.length == 0) {
			if (null != key) {
				return new String[] { key + "=" + value };
			}
		}

		for (int i = 0; i < array.length; i++) {
			String[] pair = array[i].split("=");
			if (pair.length < 1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						array2String(array, ","));
			}
			// 更新
			if (key.equals(pair[0])) {
				array[i] = key + "=" + value;
				return array;
			}
		}
		// 添加一条新的
		String[] rtn = new String[array.length + 1];
		System.arraycopy(array, 0, rtn, 0, array.length);
		rtn[array.length] = key + "=" + value;
		return rtn;
	}

	public static String object2String(Object o) {
		if (null == o) {
			return null;
		}
		return o.toString();
	}

	public static String object2StringNotNull(Object o) {
		if (null == o) {
			return "";
		}
		return o.toString();
	}

	public static String object2IntString(Object o) {
		if (null != o) {
			String s = o.toString();
			int index = s.indexOf(".");
			if (index > 0) {
				return s.substring(0, index);
			} else {
				return s;
			}
		} else {
			return "";
		}
	}

	/**
	 * 截取给定字符串的前几位
	 * 
	 * @param source
	 *            给定字符串
	 * @param length
	 *            要截取的位数
	 * @return 截取后的新字符串
	 * @author huangbuji
	 *         <p />
	 *         2014-12-13 下午2:19:32
	 */
	public static String cutString(String source, int length) {
		if (null == source) {
			return "";
		}
		if (source.length() <= length) {
			return source;
		}
		return source.substring(0, length);
	}

	/**
	 * 截取给定字符串的几位
	 * 
	 * @param obj
	 *            传入对象
	 * @param length
	 *            截取小数点后几位
	 * @return 截取后的新字符串
	 * @author wangbinyu
	 *         <p />
	 *         2015-01-20 下午2:19:32
	 */
	public static String cutObject(Object obj, int length) {
		if (null == obj) {
			return "";
		} else {
			String s1 = obj.toString();
			String s[] = s1.split("\\.");
			if (s.length > 1) {
				StringBuffer sb = new StringBuffer();
				sb.append(s[0]);
				sb.append(".");
				if (s[1].length() >= length) {
					sb.append(s[1].substring(0, length));
				} else {
					sb.append(s[1]);
				}
				return sb.toString();
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * 时间转换
	 * 
	 * @return
	 */
	public static String timeToString(Object time) {
		if (null == time) {
			return "";
		}
		synchronized (sdf) {
			if (time instanceof Long) {
				return sdf.format(new Date((Long) time));
			}
			if (time instanceof Timestamp) {
				return sdf.format(time);
			}
		}
		return "";
	}

	/**
	 * 时间转换
	 * 
	 * @param time
	 *            yyyy-MM-dd HH:mm:ss格式的字符串时间
	 * @return 从1970到当前的毫秒数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-3-31 下午4:16:33
	 */
	public static Long stringToTime(String time) {
		if (null == time || "".equals(time)) {
			return Long.valueOf(System.currentTimeMillis());
		}
		synchronized (sdf) {
			try {
				return Long.valueOf(sdf.parse(time).getTime());
			} catch (ParseException e) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"time[" + time + "] invalid !");
			}
		}
	}

	/**
	 * 32位int，转对应位存在（1）的int数组， 比如：0x00000006， 结果为["2","4"]。 0x00000009，
	 * 结果为["1","8"].
	 * 
	 * @param type
	 * @return
	 * @throws NumberFormatException
	 */
	public static List<String> int32TypeList(String type)
			throws NumberFormatException {
		long l = Long.parseLong(type);
		List<String> list = new LinkedList<String>();
		if (l < 0) {
			return list;
		}
		for (int i = 0; i < 32; i++) {
			long test = l & (long) Math.pow(2, i);
			if (test > 0) {
				list.add((long) Math.pow(2, i) + "");
			}
		}
		return list;
	}

	/**
	 * 
	 * 毫秒数转换成小时分钟秒
	 * 
	 * @param time
	 *            毫秒数
	 * @return 小时分钟秒字符串
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 上午11:29:53
	 */
	public static String millisTohour(long time) {
		// 取小时数
		long hour = time / (1000l * 60 * 60);
		// 取分钟数
		long minute = (time % (1000l * 60 * 60)) / (1000l * 60);
		// 取秒数
		long second = (time % (1000l * 60 * 60)) % (1000l * 60) / 1000;
		return hour + "小时" + minute + "分钟" + second + "秒";
	}

	public static void main(String[] args) {
		String type = "295734567";
		List<String> list = int32TypeList(type);
		for (String s : list) {
			System.out.println(s);
		}
	}

	/**
	 * 特殊符号替换
	 * 
	 * @param likeStr
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:57:42
	 */
	public static String escapeSQLLike(String likeStr) {
		String str = StringUtils.replace(likeStr, "_", "/_");
		str = StringUtils.replace(str, "%", "/%");
		str = StringUtils.replace(str, "?", "_");
		str = StringUtils.replace(str, "*", "%");
		return str;
	}
}
