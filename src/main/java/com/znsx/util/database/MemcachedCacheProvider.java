package com.znsx.util.database;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;

import com.meetup.memcached.SockIOPool;

/**
 * 实现了Hibernate缓存接口的Memcached缓存提供者实现
 * 
 * @author huangbuji
 *         <p />
 *         2012-9-11 下午04:26:42
 */
public class MemcachedCacheProvider implements CacheProvider {

	private static final Log log = LogFactory
			.getLog(MemcachedCacheProvider.class);
	public final static String DEFAULT_REGION_NAME = "default";
	public static final int DEFAULT_EXPIRE_TIME_IN_SEC = 10 * 60;
	/**
	 * 各区域标识映射的缓存实例
	 */
	private static Hashtable<String, MemcachedCache> cacheMap;
	/**
	 * 各memcached servers的共用缓存连接池
	 */
	private SockIOPool pool;

	public Cache buildCache(String regionName, Properties properties)
			throws CacheException {
		if (cacheMap == null) {
			throw new IllegalStateException("Please start the provider first!");
		}
		String poolName = properties.getProperty("memcached.poolName");
		if (StringUtils.isEmpty(regionName)) {
			regionName = DEFAULT_REGION_NAME;
		}
		MemcachedCache cache = cacheMap.get(regionName);
		if (cache == null) {
			// 获取各区域缓存过期时间，或者默认缓存过期时间
			String expireTimeExpression = properties
					.getProperty("memcached.region." + regionName
							+ ".expireTime");
			if (StringUtils.isBlank(expireTimeExpression)) {
				expireTimeExpression = properties
						.getProperty("memcached.expireTime");
			}
			int expireTimeInSecond = -1;
			if (StringUtils.isNotBlank(expireTimeExpression)) {
				expireTimeExpression = expireTimeExpression.toLowerCase()
						.trim();
				expireTimeInSecond = getSeconds(expireTimeExpression);
			} else {
				expireTimeInSecond = DEFAULT_EXPIRE_TIME_IN_SEC;
			}
			//

			String pcompressEnable = properties.getProperty("memcached.region."
					+ regionName + ".compressEnable");
			if (pcompressEnable == null) {
				pcompressEnable = properties
						.getProperty("memcached.compressEnable");
			}
			Boolean compressEnable = null;
			if (pcompressEnable != null) {
				compressEnable = Boolean.parseBoolean(pcompressEnable);
			}
			//

			String pcompressThreshold = properties
					.getProperty("memcached.region." + regionName
							+ ".compressThreshold");
			if (pcompressThreshold == null) {
				pcompressThreshold = properties
						.getProperty("memcached.compressThreshold");
			}
			Integer compressThreshold = null;
			if (compressThreshold != null) {
				compressThreshold = Integer.parseInt(pcompressThreshold);
			}
			log.info("Building cache named " + regionName
					+ " using expireTimeInSecond is " + expireTimeInSecond);
			cache = new MemcachedCache(poolName, regionName,
					expireTimeInSecond, compressEnable, compressThreshold);
			cacheMap.put(regionName, cache);
		}
		return cache;
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void start(Properties properties) throws CacheException {
		String confFile = properties.getProperty("memcached.conf.file",
				"memcached.properties");
		try {
			URL filePath = this.getClass().getClassLoader()
					.getResource(confFile);
			if (null == filePath) {
				throw new CacheException("not found [" + confFile
						+ "] in classpath!");
			}
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath.getPath()));
			properties.load(in);
			in.close();
			// 如果没有设置poolName，则设置为default
			if (properties.getProperty("memcached.poolName") == null) {
				properties.setProperty("memcached.poolName", "default");
			}
			log.info("Success load " + filePath.getPath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new CacheException(confFile + " load error !");
		}
		// 初始化各区域缓存Map
		cacheMap = new Hashtable<String, MemcachedCache>();

		// 根据memcached.properties配置初始化memcached java client
		pool = SockIOPool.getInstance(properties
				.getProperty("memcached.poolName"));
		if (pool.isInitialized()) {
			log.debug("SockIOPool has been Started!");
		} else {
			String servers = properties.getProperty("memcached.servers");
			if (StringUtils.isBlank(servers)) {
				throw new CacheException(
						"configuration [memcached.servers] is empty!");
			}
			// 设置memcached缓存服务器
			pool.setServers(servers.split(","));
			// 设置各缓存服务器的缓存空间大小
			String weights = properties.getProperty("weights");
			if (weights != null) {
				String[] ws = weights.split(",");
				Integer[] iws = new Integer[ws.length];
				for (int i = 0; i < iws.length; i++) {
					iws[i] = Integer.parseInt(ws[i]);
				}
				pool.setWeights(iws);
			}

			try {
				pool.setFailover(Boolean.valueOf(properties
						.getProperty("memcached.failover")));
				pool.setFailback(Boolean.valueOf(properties
						.getProperty("memcached.failback")));
				pool.setInitConn(Integer.parseInt(properties
						.getProperty("memcached.initConn")));
				pool.setMinConn(Integer.parseInt(properties
						.getProperty("memcached.minConn")));
				pool.setMaxConn(Integer.parseInt(properties
						.getProperty("memcached.maxConn")));
				pool.setMaintSleep(Integer.parseInt(properties
						.getProperty("memcached.maintSleep")));
				pool.setNagle(Boolean.valueOf(properties
						.getProperty("memcached.nagle")));
				pool.setSocketTO(Integer.parseInt(properties
						.getProperty("memcached.socketTO")));
				pool.setAliveCheck(Boolean.valueOf(properties
						.getProperty("memcached.aliveCheck")));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new CacheException(confFile + " configure error !");
			}

			pool.initialize();
		}
		log.info("MemcachedCacheProvider Started!");
	}

	public void stop() {
		if (pool.isInitialized()) {
			pool.shutDown();
		}
		cacheMap = null;

	}

	public boolean isMinimalPutsEnabledByDefault() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 根据配置文件中的配置时间单位，转换成秒单位
	 * 
	 * @param expireTimeString
	 *            配置文件中的memcached.region.regionName.expireTime或memcached.
	 *            expireTime配置字符串
	 * @return
	 */
	private static int getSeconds(String expireTimeString) {
		try {
			switch (expireTimeString.charAt(expireTimeString.length() - 1)) {
			case 's':
				return Integer.parseInt(expireTimeString.substring(0,
						expireTimeString.length() - 1));
			case 'm':
				return Integer.parseInt(expireTimeString.substring(0,
						expireTimeString.length() - 1)) * 60;
			case 'h':
				return Integer.parseInt(expireTimeString.substring(0,
						expireTimeString.length() - 1)) * 3600;
			case 'd':
				return Integer.parseInt(expireTimeString.substring(0,
						expireTimeString.length() - 1)) * 86400;
			default:
				return Integer.parseInt(expireTimeString);
			}
		} catch (NumberFormatException e) {
			log.warn("Illegal configuration value : " + expireTimeString, e);
		}
		return -1;
	}

}
