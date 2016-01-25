package com.znsx.cms.service.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * JDBCTemplate批量插入操作用到的抽象类实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-6 上午9:47:49
 */
public class MyBatchPreparedStatementSetter implements
		BatchPreparedStatementSetter {
	/**
	 * 要存入库的数据，每个Map就是一行，Map中的key和value分别对应表中的列和值
	 */
	private Vector<Map<String, String>> vector;
	/**
	 * 插入语句中的列对应的插入序号，序号从1开始
	 */
	private Map<String, Integer> paramPosition;
	/**
	 * 插入语句中的列对应的数据类型，参见
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_BIGINT}
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_DATETIME}
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_INT}
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_SMALLINT}
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_TINYINT}
	 * {@link com.znsx.cms.service.common.TypeDefinition#JDBC_VARCHAR}
	 */
	private Map<String, String> types;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public MyBatchPreparedStatementSetter(Vector<Map<String, String>> vector,
			Map<String, Integer> paramPosition, Map<String, String> types) {
		this.vector = vector;
		this.paramPosition = paramPosition;
		this.types = types;
	}

	@Override
	public int getBatchSize() {
		return vector.size();
	}

	@Override
	public void setValues(PreparedStatement ps, int index) throws SQLException {
		Map<String, String> row = vector.elementAt(index);
		// 验证是否每个参数都设置好了得数组
		int[] check = new int[paramPosition.size()];
		for (String key : row.keySet()) {
			String type = types.get(key);
			try {
				// varchar类型
				if (TypeDefinition.JDBC_VARCHAR.equals(type)) {
					ps.setString(paramPosition.get(key).intValue(),
							row.get(key));
				}
				// datetime类型
				else if (TypeDefinition.JDBC_DATETIME.equals(type)) {
					String value = row.get(key);
					if (null != value) {
						Date date = sdf.parse(value);
						Timestamp time = new Timestamp(date.getTime());
						ps.setTimestamp(paramPosition.get(key).intValue(), time);
					}
					// 默认系统当前时间
					else {
						Timestamp time = new Timestamp(
								System.currentTimeMillis());
						ps.setTimestamp(paramPosition.get(key).intValue(), time);
					}
				}
				// int,smallint,tinyint类型
				else if (TypeDefinition.JDBC_INT.equals(type)
						|| TypeDefinition.JDBC_SMALLINT.equals(type)
						|| TypeDefinition.JDBC_TINYINT.equals(type)) {
					String value = row.get(key);
					ps.setInt(paramPosition.get(key).intValue(),
							Integer.parseInt(value));
				}
				// long类型
				else if (TypeDefinition.JDBC_BIGINT.equals(type)) {
					String value = row.get(key);
					ps.setLong(paramPosition.get(key).intValue(),
							Long.parseLong(value));
				}
				// 其他类型当作varchar
				else {
					// 在配置文件中能找到的列才需要设置
					if (null != type) {
						ps.setString(paramPosition.get(key).intValue(),
								row.get(key));
					}
				}
				// 在配置文件中能找到的列才需要设置
				if (null != type) {
					// 设置验证数组对应位的值
					check[paramPosition.get(key).intValue() - 1] = 2;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Attribute " + key + "[" + row.get(key) + "] invalid !");
			} catch (ParseException e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"Attribute " + key + "[" + row.get(key) + "] invalid !");
			}
		}
		for (int i = 0; i < check.length; i++) {
			// 为没有设置值的位赋值
			if (check[i] != 2) {
				// 查询该位置参数对应的参数类型
				for (String key : paramPosition.keySet()) {
					if (paramPosition.get(key).intValue() == i + 1) {
						String type = types.get(key);
						// varchar类型
						if (TypeDefinition.JDBC_VARCHAR.equals(type)) {
							ps.setNull(i + 1, Types.VARCHAR);
						}
						// datetime类型
						else if (TypeDefinition.JDBC_DATETIME.equals(type)) {
							// 默认系统当前时间
							Timestamp time = new Timestamp(
									System.currentTimeMillis());
							ps.setTimestamp(i + 1, time);
						}
						// int,smallint,tinyint类型
						else if (TypeDefinition.JDBC_INT.equals(type)
								|| TypeDefinition.JDBC_SMALLINT.equals(type)
								|| TypeDefinition.JDBC_TINYINT.equals(type)) {
							ps.setNull(i + 1, Types.INTEGER);
						}
						// long类型
						else if (TypeDefinition.JDBC_BIGINT.equals(type)) {
							ps.setNull(i + 1, Types.BIGINT);
						}
						// 其他类型当作varchar
						else {
							ps.setNull(i + 1, Types.VARCHAR);
						}
					}
				}

			}
		}

	}

}
