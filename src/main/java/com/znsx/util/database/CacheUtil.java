package com.znsx.util.database;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.cache.Cache;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * 简单的缓存应用工具,memcached.properties必须这样命名,且存在于classpath中
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-27 下午2:10:27
 */
public class CacheUtil {
	private static MemcachedCacheProvider provider;
	private static Hashtable<String, Cache> cacheMap = new Hashtable<String, Cache>();

	/**
	 * 初始化工具类唯一Provider
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-14 下午4:59:05
	 */
	private static void initProvider() {
		if (null == provider) {
			provider = new MemcachedCacheProvider();
			try {
				String filePath = provider.getClass().getClassLoader()
						.getResource("memcached.properties").getPath();
				Properties prop = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				prop.load(in);
				in.close();
				provider.start(prop);
			} catch (IOException e) {
				e.printStackTrace();
				provider = null;
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
						"memcached.properties is not found in classpath !");
			} catch (Exception e) {
				e.printStackTrace();
				provider = null;
				throw new BusinessException(ErrorCode.ERROR, e.getMessage());
			}
		}
	}

	/**
	 * 创建一个区域缓存对象
	 * 
	 * @param region
	 *            区域名
	 * @return 缓存对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-14 下午4:59:31
	 */
	private static Cache buildCache(String region) throws BusinessException {
		initProvider();
		try {
			String filePath = provider.getClass().getClassLoader()
					.getResource("memcached.properties").getPath();

			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			prop.load(in);
			in.close();
			Cache cache = provider.buildCache(region, prop);
			cacheMap.put(region, cache);
			return cache;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"memcached.properties is not found in classpath !");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		}
	}

	/**
	 * 存入缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-27 下午2:33:58
	 */
	public static void putCache(Object key, Object value, String region)
			throws BusinessException {
		if (StringUtils.isBlank(region)) {
			region = MemcachedCacheProvider.DEFAULT_REGION_NAME;
		}
		Cache cache = cacheMap.get(region);
		if (null == cache) {
			cache = buildCache(region);
		}
		cache.put(key, value);
	}

	/**
	 * 从缓存取值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-27 下午2:34:26
	 */
	public static Object getCache(Object key, String region)
			throws BusinessException {
		if (StringUtils.isBlank(region)) {
			region = MemcachedCacheProvider.DEFAULT_REGION_NAME;
		}
		Cache cache = cacheMap.get(region);
		if (null == cache) {
			cache = buildCache(region);
		}
		return cache.get(key);
	}

	public static void main(String[] args) {
//		System.out.println(CacheUtil.getCache("check_session_task_lock", "sessionCache"));
	}
}
