package com.znsx.util.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取工具类
 * 
 * @author huangbuji
 *         <p />
 *         2012-8-23 上午09:46:18
 */
public class Configuration {
	private static final Configuration configuration = new Configuration();
	private Map<String, String> map = new HashMap<String, String>();
	public static final String DEFAULT_FILE_NAME = "config.properties";
	public static final String DAS_DB_NAME = "das_db_name";
	public static final String CMS_DB_NAME = "cms_db_name";
	public static final String MYSQL = "mysql";
	public static final String ORACLE = "oracle";
	public static final String SQLSERVER = "sqlserver";

	private Configuration() {
		// Singleton
	}

	public static Configuration getInstance() {
		return configuration;
	}

	/**
	 * 从默认配置文件中，查找指定属性的值，缓存读取，不支持动态修改配置文件
	 * 
	 * @param key
	 *            属性
	 * @return 值
	 */
	public String getProperties(String key) {
		if (null == map.get(key)) {
			try {
				String filePath = this.getClass().getClassLoader()
						.getResource(DEFAULT_FILE_NAME).getPath();

				Properties prop = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				prop.load(in);
				in.close();
				String value = prop.getProperty(key);
				map.put(key, value);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return map.get(key);
	}

	/**
	 * 从默认配置文件中，查找指定属性的值,不进行缓存，每次都重新读取，支持动态修改
	 * 
	 * @param key
	 *            属性
	 * @return 值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-17 下午2:39:02
	 */
	public String loadProperties(String key) {
		try {
			String filePath = this.getClass().getClassLoader()
					.getResource(DEFAULT_FILE_NAME).getPath();

			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			prop.load(in);
			in.close();
			String value = prop.getProperty(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取DAS数据库类型名称
	 * 
	 * @return mysql/oracle/sqlserver
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午12:44:58
	 */
	public String getDasDbName() {
		if (null == map.get(DAS_DB_NAME)) {
			try {
				String filePath = this.getClass().getClassLoader()
						.getResource("jdbc.properties").getPath();

				Properties prop = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				prop.load(in);
				in.close();
				String value = prop.getProperty("das.jdbc.driverClassName");
				if ("oracle.jdbc.driver.OracleDriver".equals(value)) {
					map.put(DAS_DB_NAME, ORACLE);
				} else if ("com.mysql.jdbc.Driver".equals(value)) {
					map.put(DAS_DB_NAME, MYSQL);
				} else {
					map.put(DAS_DB_NAME, SQLSERVER);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return map.get(DAS_DB_NAME);
	}

	/**
	 * 获取CMS数据库类型名称
	 * 
	 * @return mysql/oracle/sqlserver
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-5-17 下午12:44:58
	 */
	public String getCmsDbName() {
		if (null == map.get(CMS_DB_NAME)) {
			try {
				String filePath = this.getClass().getClassLoader()
						.getResource("jdbc.properties").getPath();

				Properties prop = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				prop.load(in);
				in.close();
				String value = prop.getProperty("jdbc.driverClassName");
				if ("oracle.jdbc.driver.OracleDriver".equals(value)) {
					map.put(CMS_DB_NAME, ORACLE);
				} else if ("com.mysql.jdbc.Driver".equals(value)) {
					map.put(CMS_DB_NAME, MYSQL);
				} else {
					map.put(CMS_DB_NAME, SQLSERVER);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return map.get(CMS_DB_NAME);
	}
}
