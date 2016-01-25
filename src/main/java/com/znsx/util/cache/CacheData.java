package com.znsx.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.znsx.cms.persistent.model.Classes;

/**
 * CacheData
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2015 下午5:34:58
 */
public class CacheData {
	private CacheData() {

	}
	
	private static CacheData instance = new CacheData();
	public Map<String, Classes> classes = new ConcurrentHashMap<String, Classes>();
	
	public static CacheData getInstance() {
		return instance;
	}
	
}
