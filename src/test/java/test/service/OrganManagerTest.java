package test.service;

import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.common.StandardObjectCode;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.model.AuthCameraVO;
import com.znsx.cms.service.model.GetOrganVO;
import com.znsx.cms.service.model.OrganVO;

/**
 * 机构业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:41:29
 */
public class OrganManagerTest extends BaseTest {
	@Autowired
	private OrganManager organManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private DeviceManager deviceManager;

	private String id;

	@Test(priority = 0)
	public void testCreateOrgan() {
		id = organManager.createOrgan("测试XX公路局", "251090100000000000",
				"10010000000000000000", "02888888888", "张三", "02888888888",
				"地址", "610000", "备注", StandardObjectCode.MANAGE_DEPARTMENT,
				null, null, null);
	}

	@Test(priority = 1)
	public void testUpdateOrgan() {
		organManager.updateOrgan(id, null, null, null, null, null, null, null,
				"641000", "测试修改", null, null, null);
	}

	@Test(priority = 2)
	public void testGetOrganTree() {
		OrganVO vo = organManager.getOrganTree("10010000000000000000");
		Assert.assertNotNull(vo);
	}

	@Test(priority = 3)
	public void testGetOrgan() {
		GetOrganVO organ = organManager.getOrgan(id);
		Assert.assertNotNull(organ);
		Assert.assertNotNull(organ.getParentId());
	}

	@Test(priority = 9)
	public void testDeleteOrgan() {
		organManager.deleteOrgan(id);
	}

	@Test(priority = 4)
	public void testListOrgan() {
		List<GetOrganVO> list = organManager.listOrgan("10010000000000000000",
				null, 0, 100);
		Assert.assertTrue(list.size() > 0);
	}

	// @Test
	// public void testListOrganDevice() throws Exception {
	// // List<RoleResourcePermissionVO> cameras =
	// // deviceManager.listDeviceByOperation("10020000000000000000");
	//
	// Element e = organManager.listOrganDevice("10010000000000000001", false,
	// null, true);
	// Assert.assertNotNull(e);
	// Assert.assertNotNull(e.getChild("ChannelList"));
	// }

	@Test
	public void testRecursiveOrgan() throws Exception {
		List<AuthCameraVO> devices = deviceManager
				.listUserAuthCamera("10020000000000000000");

		Element tree = organManager.recursiveOrganDevice(
				"10010000000000000000", devices, true);
		Assert.assertNotNull(tree);

		// Document doc = new Document();
		// doc.setRootElement(tree);
		//
		// Format format = Format.getRawFormat();
		// format.setEncoding("UTF-8");
		// format.setIndent("  ");
		// XMLOutputter out = new XMLOutputter(format);
		// String body = out.outputString(doc);
		// System.out.println(body);
	}
}
