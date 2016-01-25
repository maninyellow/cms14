package com.znsx.util.number;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * NumberUtil
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-18 下午4:35:47
 */
public class NumberUtil {
	public static Short getShort(Object o) {
		if (null == o) {
			return null;
		}
		if (o instanceof BigDecimal) {
			return Short.valueOf(((BigDecimal) o).shortValue());
		}
		if (o instanceof Number) {
			return Short.valueOf(((Number) o).shortValue());
		}
		if (o instanceof String) {
			if (StringUtils.isNotBlank((String) o)) {
				return Short.valueOf(o.toString());
			}
		}
		return null;
	}

	public static Integer getInteger(Object o) {
		if (null == o) {
			return null;
		}
		if (o instanceof BigDecimal) {
			return Integer.valueOf(((BigDecimal) o).intValue());
		}
		if (o instanceof Number) {
			return Integer.valueOf(((Number) o).intValue());
		}
		if (o instanceof String) {
			if (StringUtils.isNotBlank((String) o)) {
				BigDecimal bd = new BigDecimal((String) o);
				return Integer.valueOf(bd.intValue());
			}
		}
		return null;
	}

	public static Long getLong(Object o) {
		if (null == o) {
			return null;
		}
		if (o instanceof BigDecimal) {
			return Long.valueOf(((BigDecimal) o).longValue());
		}
		if (o instanceof Number) {
			return Long.valueOf(((Number) o).longValue());
		}
		if (o instanceof String) {
			if (StringUtils.isNotBlank((String) o)) {
				return Long.valueOf(o.toString());
			}
		}
		return null;
	}

	public static Float getFloat(Object o) {
		if (null == o) {
			return null;
		}
		if (o instanceof BigDecimal) {
			return Float.valueOf(((BigDecimal) o).floatValue());
		}
		if (o instanceof Number) {
			return Float.valueOf(((Number) o).floatValue());
		}
		if (o instanceof String) {
			if (StringUtils.isNotBlank((String) o)) {
				return Float.valueOf(o.toString());
			}
		}
		if (o instanceof Integer) {
			return ((Integer) o).floatValue();
		}
		return null;
	}

	/**
	 * 转换字符串桩号为浮点数形式
	 * 
	 * @param stakeNumber
	 *            字符串桩号，格式可为：K100+020, YK200+20, K100.500, 200.30
	 * @return 浮点数
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-16 上午11:09:40
	 */
	public static float floatStake(String stakeNumber) throws BusinessException {
		int sep = stakeNumber.indexOf("+");
		if (sep < 0) {
			sep = stakeNumber.indexOf(".");
			if (sep < 0) {
				sep = stakeNumber.length();
			}
		}
		// 大写字母K
		int index = stakeNumber.indexOf("K");
		// 小写字母k
		if (index < 0) {
			index = stakeNumber.indexOf("k");
		}
		try {
			// 整数位
			int a = Integer.valueOf(stakeNumber.substring(index + 1, sep));
			// 小数位
			float b = 0;
			if (sep < stakeNumber.length()) {
				b = Float.valueOf(stakeNumber.substring(sep + 1));
			}

			return a + b / 1000;
		} catch (NumberFormatException e) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"StakeNumber[" + stakeNumber + "] invalid !");
		}
	}

	/**
	 * 转换float格式的桩号为字符串格式
	 * 
	 * @param stakeNumber
	 *            float格式的桩号
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-23 上午11:32:54
	 */
	public static String stringStake(float stakeNumber)
			throws BusinessException {
		String s = stakeNumber + "";
		int index = s.indexOf(".");
		if (index > 0) {
			String meter = s.substring(index + 1);
			if (meter.length() == 1) {
				meter += "00";
			} else if (meter.length() == 2) {
				meter += "0";
			}
			return "K" + s.substring(0, index) + "+" + meter;
		} else {
			return "K" + s;
		}
	}

	/**
	 * 本侧过渡区位置计算
	 * 
	 * @param beginStake
	 *            起始桩号
	 * @param endStake
	 *            结束桩号
	 * @param navigation
	 *            方向，参考{@link com.znsx.cms.service.common#NAVIGATION_UPLOAD},
	 *            {@link com.znsx.cms.service.common#NAVIGATION_DOWNLOAD},
	 *            {@link com.znsx.cms.service.common#NAVIGATION_ALL}
	 * @param oppositeFlag
	 *            是否对侧车道计算
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-23 上午11:48:32
	 */
	public static String bufferPosition(String beginStake, String endStake,
			int navigation, boolean oppositeFlag) {
		float pos = 0;
		float oppositePos = 0;
		float begin = NumberUtil.floatStake(beginStake);
		float end = NumberUtil.floatStake(endStake);

		if (navigation == TypeDefinition.NAVIGATION_UPLOAD) {
			// 默认过渡区为1公里，以后有需要根据道路设计时速来划分
			pos = begin - 1;
			if (pos < 0) {
				pos = 0;
			}
			oppositePos = end + 1;
		} else if (navigation == TypeDefinition.NAVIGATION_DOWNLOAD) {
			pos = end + 1;
			oppositePos = begin - 1;
			if (oppositePos < 0) {
				oppositePos = 0;
			}
		} else if (navigation == TypeDefinition.NAVIGATION_ALL) {
			pos = begin - 1;
			if (pos < 0) {
				pos = 0;
			}
			oppositePos = end + 1;
		}

		if (oppositeFlag) {
			return NumberUtil.stringStake(oppositePos);
		} else {
			return NumberUtil.stringStake(pos);
		}
	}

	/**
	 * 两个Integer相加
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-24 下午5:16:40
	 */
	public static Integer plusInteger(Integer a, Integer b) {
		if (null == a && null == b) {
			return null;
		}
		if (null == a) {
			return b;
		}
		if (null == b) {
			return a;
		}
		return Integer.valueOf(a.intValue() + b.intValue());
	}

	/**
	 * 两个Integer求平均值
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-24 下午5:33:48
	 */
	public static Integer avgInteger(Integer a, Integer b) {
		if (null == a && null == b) {
			return null;
		}
		if (null == a) {
			return b;
		}
		if (null == b) {
			return a;
		}
		int sum = a.intValue() + b.intValue();
		return sum / 2;
	}

	/**
	 * 两个float字符串求平均值 avgString方法说明
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-9-24 下午5:41:37
	 */
	public static String avgString(String a, String b) {
		if (StringUtils.isBlank(a) && StringUtils.isBlank(b)) {
			return null;
		}
		if (StringUtils.isBlank(a)) {
			return Float.parseFloat(b) / 2 + "";
		}
		if (StringUtils.isBlank(b)) {
			return Float.parseFloat(a) / 2 + "";
		}
		return (Float.parseFloat(a) + Float.parseFloat(b)) / 2 + "";
	}

	/**
	 * 累加浮点数
	 * 
	 * @param fs
	 *            浮点数组
	 * @return 累加结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-13 下午5:14:47
	 */
	public static float plusFloat(Float... fs) {
		float total = 0f;
		for (Float f : fs) {
			if (f != null) {
				total += f.floatValue();
			}
		}
		return total;
	}

	/**
	 * 平均浮点数
	 * 
	 * @param fs
	 *            浮点数组
	 * @return 平均值结果
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-14 上午10:25:33
	 */
	public static float avgFloat(Float... fs) {
		float avg = 0f;
		int length = 0;
		for (Float f : fs) {
			if (f != null) {
				avg += f.floatValue();
				length++;
			}
		}

		if (length == 0) {
			return 0f;
		} else {
			return avg / length;
		}
	}

	/**
	 * 求最大值
	 * 
	 * @param fs
	 *            浮点数组
	 * @return 最大值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-8-21 下午2:58:56
	 */
	public static float maxFloat(Float... fs) {
		float max = 0 - Float.MAX_VALUE;
		for (Float f : fs) {
			if (null != f) {
				if (f.floatValue() > max) {
					max = f.floatValue();
				}
			}
		}
		if (max == (0 - Float.MAX_VALUE)) {
			return 0f;
		} else {
			return max;
		}
	}

	public static float minFloat(Float... fs) {
		float min = Float.MAX_VALUE;
		for (Float f : fs) {
			if (null != f) {
				if (f.floatValue() < min) {
					min = f.floatValue();
				}
			}
		}
		if (min == Float.MAX_VALUE) {
			return 0f;
		} else {
			return min;
		}
	}

	public static void main(String[] args) {
		System.out.println(maxFloat(-1f,-3f));
	}
}
