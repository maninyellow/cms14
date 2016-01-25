package com.znsx.util.database;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.Timestamper;

import com.meetup.memcached.MemcachedClient;

/**
 * 从MemcachedClient中读取数据的hibernate二级缓存实例
 * 
 * @author huangbuji
 *         <p />
 *         2012-9-11 上午10:27:13
 */
public class MemcachedCache implements Cache {

	private final static Log log = LogFactory.getLog(MemcachedCache.class);

	private MemcachedClient mc;
	private String regionName; // 缓存区域标识
	private int expireTimeInSecond;// 该存储区域对缓存对象过期时间值

	public MemcachedCache(String poolName, String regionName,
			int expireTimeInSecond, Boolean compressEnable,
			Integer compressThreshold) {
		mc = new MemcachedClient(poolName);
		if (compressEnable != null) {
			mc.setCompressEnable(compressEnable.booleanValue());
		}
		if (compressThreshold != null) {
			mc.setCompressThreshold(compressThreshold.intValue());
		}
		this.expireTimeInSecond = expireTimeInSecond;
		this.regionName = regionName;
	}

	public String rebuildKey(Object key) {
		return key.toString().hashCode() + "";
	}

	public Object read(Object key) throws CacheException {
		return get(rebuildKey(key));
	}

	public Object get(Object key) throws CacheException {
		if (null == key) {
			return null;
		} else {
			Object value = mc.get(rebuildKey(key));
			if (value == null) {
				if (log.isDebugEnabled()) {
					log.debug("Value for " + key + " is null");
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Got a value by " + key);
				}
			}
			return value;
		}
	}

	public void put(Object key, Object value) throws CacheException {
		if (expireTimeInSecond <= 0) {
			mc.set(rebuildKey(key), value);
		} else {
			mc.set(rebuildKey(key), value, new Date(expireTimeInSecond * 1000));
		}
	}

	public void update(Object key, Object value) throws CacheException {
		if (expireTimeInSecond <= 0)
			mc.replace(rebuildKey(key), value);
		else {
			mc.replace(rebuildKey(key), value, new Date(
					expireTimeInSecond * 1000));
		}
	}

	public void remove(Object key) throws CacheException {
		mc.delete(rebuildKey(key));
	}

	public void clear() throws CacheException {
		mc.flushAll();

	}

	public void destroy() throws CacheException {
		// TODO Auto-generated method stub

	}

	public void lock(Object key) throws CacheException {
		// TODO Auto-generated method stub

	}

	public void unlock(Object key) throws CacheException {
		// TODO Auto-generated method stub

	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public int getTimeout() {
		return Timestamper.ONE_MS * 60000; // ie. 60 seconds
	}

	public String getRegionName() {
		return regionName;
	}

	public long getSizeInMemory() {
		return -1;
	}

	public long getElementCountInMemory() {
		return 0;
	}

	public long getElementCountOnDisk() {
		return 0;
	}

	public Map toMap() {
		return null;
	}

	public String toString() {
		return "MemcachedCache(" + regionName + ')';
	}
}
