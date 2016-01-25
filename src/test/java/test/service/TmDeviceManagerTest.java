package test.service;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.service.iface.TmDeviceManager;

/**
 * TmDeviceManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-8-3 下午3:34:35
 */
public class TmDeviceManagerTest extends BaseTest {
	@Autowired
	private TmDeviceManager tmDeviceManager;

	private String playlistId = null;
	private String folderId = "1";

	@Test(priority = 1)
	public void createFolder() {
		folderId = tmDeviceManager.createFolder("test-常用-101",
				Integer.valueOf(1));
	}

	@Test(priority = 10)
	public void testSavePlaylist() {
		Element item1 = new Element("Item");
		item1.setAttribute("Id", "");
		item1.setAttribute("Content", "减速慢行");
		item1.setAttribute("Color", "00ffff");
		item1.setAttribute("Font", "1");
		item1.setAttribute("Size", "3");
		item1.setAttribute("Space", "3");
		item1.setAttribute("Duration", "300");
		item1.setAttribute("X", "0");
		item1.setAttribute("Y", "0");

		Element item2 = new Element("Item");
		item2.setAttribute("Id", "");
		item2.setAttribute("Content", "雨天路滑");
		item2.setAttribute("Color", "00ffff");
		item2.setAttribute("Font", "1");
		item2.setAttribute("Size", "3");
		item2.setAttribute("Space", "3");
		item2.setAttribute("Duration", "300");
		item2.setAttribute("X", "0");
		item2.setAttribute("Y", "0");

		List<Element> items = new LinkedList<Element>();
		items.add(item1);
		items.add(item2);

		Playlist record = tmDeviceManager.savePlaylist(folderId, "",
				"雨天方案-test-101", Short.valueOf((short) 1), items, "752*96");
		playlistId = record.getId();
	}

	@Test(priority = 901)
	public void deleteFolder() {
		tmDeviceManager.deleteFolder(folderId);
	}

	// @Test(priority = 900)
	public void deletePlaylist() {
		tmDeviceManager.deletePlaylist(playlistId);
	}
	@Test
	public void listAllVd() {
		tmDeviceManager.listAllVd();
	}
}
