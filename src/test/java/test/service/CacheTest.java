package test.service;

import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.Test;

import test.BaseTest;

import com.meetup.memcached.MemcachedClient;
import com.meetup.memcached.SockIOPool;
import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.util.database.CacheUtil;

/**
 * CacheTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-4-27 上午11:00:12
 */
public class CacheTest extends BaseTest {

	@Test
	public void putCache() {
		List<SubPlatformResource> resources = new LinkedList<SubPlatformResource>();
		for (int i = 0; i < 100; i++) {
			resources.add(new SubPlatformResource());
		}

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 2000; i++) {
			CacheUtil.putCache("test" + i, resources, "cache-test");
		}
		System.out.println(System.currentTimeMillis() - begin);
	}

	@Test
	public void putCache2() {
		long begin = System.currentTimeMillis();
		String[] serverlist = new String[] { "192.168.1.2:11211" };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(serverlist);
		pool.setInitConn(10);
		pool.initialize();

		MemcachedClient mc = new MemcachedClient();

		for (int i = 0; i < 100000; i++) {
			mc.set("test" + i, "hello" + i, 120000);
		}
		System.out.println(System.currentTimeMillis() - begin);
	}

	@Test
	public void putCache3() throws Exception {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				List<SubPlatformResource> resources = new LinkedList<SubPlatformResource>();
				for (int i = 0; i < 100; i++) {
					resources.add(new SubPlatformResource());
				}

				for (int i = 0; i < 1000; i++) {
					CacheUtil.putCache("test" + i, resources, "cache-test");
					CacheUtil.getCache("test" + i, "cache-test");
				}
			}
		};

		List<Thread> list = new LinkedList<Thread>();
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(run);
			t.start();
			list.add(t);
		}

		for (Thread t : list) {
			t.join();
		}

	}
}
