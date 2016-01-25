package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.iface.UserManager;

/**
 * 用户会话业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:50:10
 */
public class UserSessionManagerTest extends BaseTest {
	@Autowired
	private UserManager userManager;

	@Test
	public void listUserSessionHistory() {
		userManager.listUserSessionHistory(null, null, null, null, null, 10,
				50, "10020000000000000000");
	}

	@Test
	public void countUserSessionHistory() {
		userManager.selectTotalCount(null, null, null, null, null,
				"10020000000000000000");
	}
	
	@Test
	public void regularSessionCheck(){
		userManager.regularSessionCheck();
	}
}
