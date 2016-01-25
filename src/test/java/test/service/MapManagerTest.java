package test.service;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.dao.CmsPublishLogDAO;
import com.znsx.cms.service.iface.MapManager;

/**
 * MapManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午2:07:39
 */
public class MapManagerTest extends BaseTest {
	@Autowired
	private MapManager mapManager;
	
	@Autowired
	private CmsPublishLogDAO cmsPublishLogDAO;
	
	private String playlistId;

	@Test(priority = 1)
	public void testCreatePlaylist() {
		List<Element> items = new LinkedList<Element>();
		Element e1 = new Element("Item");
		e1.setAttribute("Content", "雨天路滑");
		e1.setAttribute("Color", "FF0000");
		e1.setAttribute("Font", "01");
		e1.setAttribute("Size", "24*24");
		e1.setAttribute("Space", "3");
		e1.setAttribute("Duration", "250");

		Element e2 = new Element("Item");
		e2.setAttribute("Content", "减速慢行");
		e2.setAttribute("Color", "FF0000");
		e2.setAttribute("Font", "01");
		e2.setAttribute("Size", "24*24");
		e2.setAttribute("Space", "3");
		e2.setAttribute("Duration", "250");

		Element e3 = new Element("Item");
		e3.setAttribute("Content", "前方连续弯道");
		e3.setAttribute("Color", "FF0000");
		e3.setAttribute("Font", "01");
		e3.setAttribute("Size", "24*24");
		e3.setAttribute("Space", "3");
		e3.setAttribute("Duration", "250");

		items.add(e1);
		items.add(e2);
		items.add(e3);

		playlistId = mapManager.createPlaylist("10010000000000000000", "情报板播放方案", items);
	}

	@Test(priority = 900)
	public void deletePlaylist() {
		mapManager.deletePlaylist(playlistId);
	}
	
	@Test
	public void listCmsLatestRecord() {
		mapManager.listCmsLatestRecord("111");
	}
	
//	@Test
	public void cmsPublishLog() {
		List<String> sns = new LinkedList<String>();
		sns.add("241000017000000004");
		List<Element> items = new LinkedList<Element>();
		Element item = new Element("Item");
		item.setAttribute("Duration", "60");
		item.setAttribute("Content", "雨天路滑");
		item.setAttribute("Color", "fffff");
		item.setAttribute("Font", "2");
		item.setAttribute("Size", "3");
		item.setAttribute("Space", "3");
		item.setAttribute("X", "0");
		item.setAttribute("Y", "0");
		item.setAttribute("Type", "1");
		item.setAttribute("RowSpace","2");
		items.add(item);
		mapManager.cmsPublishLog(sns, "297edff8490cc55e01490cdaf6470013", "长潭高速", "10020000000000000000", "admin", items);
	}
}
