package test.service;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.iface.MonitorManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.WallVO;
import com.znsx.util.xml.ElementUtil;

/**
 * 电视墙业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-7-10 下午1:43:37
 */
public class MonitorManagerTest extends BaseTest {
	@Autowired
	private MonitorManager monitorManager;
	@Autowired
	private UserManager userManager;
	private String wallId = null;
	private String monitorId = null;
	private String organId = "10010000000000000000";

	@Test(priority = 1)
	public void testCreateWall() {
		wallId = monitorManager.createWall(organId,
				"wall" + System.currentTimeMillis(), "test");
	}

	@Test(priority = 10)
	public void testCreateMonitor() {
		String standardNumber = userManager.generateStandardNum("Monitor",
				"10010000000000000000");
		monitorId = monitorManager.createMonitor(organId, wallId, 15,
				standardNumber, "10060000000000000005",
				"Monitor" + System.currentTimeMillis());
	}

	@Test(priority = 11)
	public void testEditWall() {
		List<Element> monitorList = new LinkedList<Element>();
		Element monitor = new Element("Monitor");
		monitor.setAttribute("Id", monitorId);
		monitor.setAttribute("X", "300");
		monitor.setAttribute("Y", "600");
		monitor.setAttribute("Width", "300");
		monitor.setAttribute("Height", "400");
		monitorList.add(monitor);
		monitorManager.editWallLayout(wallId, monitorList);
	}

	@Test(priority = 12)
	public void testListWall() {
		List<WallVO> list = monitorManager.listOrganWall(organId);
		Assert.assertTrue(list.size() > 0);
		for (WallVO vo : list) {
			if (vo.getId().equals(wallId.toString())) {
				List<WallVO.Monitor> monitors = vo.getMonitors();
				Assert.assertTrue(monitors.size() > 0);
				WallVO.Monitor monitor = monitors.get(0);
				Assert.assertNotNull(monitor.getX());
			}
		}
	}

	@Test(priority = 20)
	public void testDeleteMonitor() {
		monitorManager.deleteMonitor(monitorId);
	}

	@Test(priority = 21)
	public void testDeleteWall() {
		monitorManager.deleteWall(wallId);
	}

	// @Test
	public void createDisplayWallScheme() {
		Document doc = ElementUtil
				.string2Doc("<Request Method=\"Create_Wall_Scheme\" Cmd=\"1029\"><SessionId>402881f449fa035d0149fa04525d0003</SessionId><WallScheme Name=\".........\" WallId=\"297edff84917eb4b0149180f8acf0016\"><Monitor Id=\"297edff84917eb4b0149180fe96d0018\"><Item Index=\"0\" channel=\"251000000103000120\" /></Monitor><Monitor Id=\"297edff84917eb4b014918103125001b\"><Item Index=\"0\" channel=\"251000000103000102\" /></Monitor></WallScheme></Request>");
		Element root = doc.getRootElement();
		Element wallScheme = root.getChild("WallScheme");

		monitorManager.createWallScheme("常用大墙方案", "10020000000000000000",
				"10010000000000000000", "297edff84917eb4b0149180f8acf0016",
				wallScheme.getChildren());
	}

//	@Test
	public void updateDisplayWallScheme() {
		Document doc = ElementUtil
				.string2Doc("<Request Method=\"Update_Wall_Scheme\" Cmd=\"1029\"><SessionId>402881f449fa035d0149fa04525d0003</SessionId><WallScheme Name=\".........\" WallId=\"402881ff4fb67b70014fb6847a370005\"><Monitor Id=\"ff8080814e09869c014e0a80737d001c\"><Item Index=\"0\" channel=\"251000000103000120\" /></Monitor><Monitor Id=\"ff8080814e09869c014e0a80737d001c\"><Item Index=\"0\" channel=\"251000000103000102\" /></Monitor></WallScheme></Request>");
		Element root = doc.getRootElement();
		Element wallScheme = root.getChild("WallScheme");

		monitorManager.updateWallScheme("402881ff4fb67b70014fb6847a370005",
				"testeste", wallScheme.getChildren());
	}

	//	@Test
	public void testUpdate() {
		Document doc = new Document();
		Element parent = new Element("parent");
		doc.setRootElement(parent);

		List<Element> items = new LinkedList<Element>();
		Element e = new Element("Monitor");
		e.setAttribute("Id", "ff8080814e09869c014e0a80737d001c");
		Element item = new Element("Item");
		item.setAttribute("abc", "123434");
		e.addContent(item);

		Element e1 = new Element("Monitor");
		e1.setAttribute("Id", "ff8080814e09869c014e0a80737d001c");

		Element item1 = new Element("Item");
		item.setAttribute("abc", "123434");
		e1.addContent(item1);

		Element e2 = new Element("Monitor");
		e2.setAttribute("Id", "ff8080814e09869c014e0a80737d001c");
		Element item2 = new Element("Item");
		item.setAttribute("abc", "123434");
		e2.addContent(item2);

		items.add(e);
		items.add(e1);
		items.add(e2);

		parent.addContent(items);

		monitorManager.updateWallScheme("402881ff4fb67b70014fb6821d9c0002",
				"dsfd", parent.getChildren());

	}
}
