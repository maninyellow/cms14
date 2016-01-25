package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import test.BaseTest;

import com.znsx.cms.persistent.model.License;
import com.znsx.cms.service.iface.LicenseManager;

/**
 * 许可证业务接口单元测试
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午3:59:10
 */
public class LicenseManagerTest extends BaseTest {
	@Autowired
	private LicenseManager licenseManager;

//	@Test
	public void testCheckLicense() {
		License license = licenseManager.getLicense();
		Assert.assertNotNull(license);
		Assert.assertTrue(licenseManager.checkLicense(license));
	}
}
