package test.service;

import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.model.LogOperation;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.iface.SysLogManager;
import com.znsx.cms.service.iface.UserManager;

/**
 * SysLogManagerTest(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:46:58
 */
public class SysLogManagerTest extends BaseTest {
	@Autowired
	private SysLogManager sysLogManager;
	@Autowired
	private UserManager userManager;

	// @Test
	public void testBatchInsert() {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			SysLog record = new SysLog();
			record.setCreateTime(begin);
			record.setLogTime(begin);
			record.setOperationCode("100001");
			record.setNote("test");
			record.setOperationName("测试插入");
			record.setOperationType("test");
			record.setResourceId("12345678");
			record.setResourceName("hbj");
			record.setResourceType("user");
			record.setSuccessFlag("0");
			record.setTargetId("87654321");
			record.setTargetName("测试对象");
			record.setTargetType("test");
			sysLogManager.batchLog(record);
		}
		System.out.println("batch insert 10000 record used: "
				+ (System.currentTimeMillis() - begin) + "ms");
	}

	// @Test
	public void testCycleInsert() {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			SysLog record = new SysLog();
			record.setCreateTime(System.currentTimeMillis());
			record.setLogTime(System.currentTimeMillis());
			record.setOperationCode("100001");
			record.setNote("test");
			record.setOperationName("测试插入");
			record.setOperationType("test");
			record.setResourceId("12345678");
			record.setResourceName("hbj");
			record.setResourceType("user");
			record.setSuccessFlag("0");
			record.setTargetId("87654321");
			record.setTargetName("测试对象");
			record.setTargetType("test");
			sysLogManager.log(record);
		}
		long end = System.currentTimeMillis();
		System.out.println("cycle insert 10000 record used: " + (end - begin)
				+ "ms");
	}

	@Test
	public void testGetNameByIdAndType() {
		String name = sysLogManager.getNameByIdAndType("10040000000000000001",
				"Camera");
		Assert.assertTrue(StringUtils.isNotBlank(name));
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		List<SysLog> list = new Vector<SysLog>();
		for (int i = 0; i < 1000; i++) {
			SysLog record = new SysLog();
			record.setCreateTime(System.currentTimeMillis());
			record.setLogTime(System.currentTimeMillis());
			record.setOperationCode("100001");
			record.setNote("test");
			record.setOperationName("测试插入");
			record.setOperationType("test");
			record.setResourceId("12345678");
			record.setResourceName("hbj");
			record.setResourceType("user");
			record.setSuccessFlag("0");
			record.setTargetId("87654321");
			record.setTargetName("测试对象");
			record.setTargetType("test");
			list.add(record);
		}
		System.out.println("1000 record used: "
				+ (System.currentTimeMillis() - begin) + "ms");
	}

	@Test(priority = 22)
	public void testListLogOperation() {
		List<LogOperation> list = userManager.listLogOperation();
		// Assert.assertNull(list.size() > 0);
	}

	@Test(priority = 23)
	public void testListSysLog() {
		List<SysLog> logList = userManager.listSysLogByAdmin("", null, null,
				0, 100, "", null, null, null);
		// Assert.assertTrue(logList.size() > 0);
	}
}
