package test.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;
import com.znsx.cms.service.model.UserLogonVO;

/**
 * 用户业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午6:33:40
 */
public class UserManagerTest extends BaseTest {
	@Autowired
	private UserManager userManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private DeviceManager deviceManager;

	private String id;

	private UserLogonVO vo;

	private String organId = "10010000000000000000";

	private String ccsId = "10060000000000000001";

	private String userId = "10020000000000000000";

	private String cameraId1 = "10040000000000000001";

	private String cameraId2 = "10040000000000000002";

	private String favoriteId;

	private String playSchemeId;

	private String sessionId;

	@Test(priority = 10)
	public void testCreate() {
		String standardNumber = userManager
				.generateStandardNum("User", organId);
		id = userManager.createUser("测试用户", standardNumber, ccsId,
				"test_userXXX", "111111", new Short("1"), "abc@123.com",
				"13880448283", "test", organId, new Short("5"), "备注", 1);
	}

	@Test(priority = 11)
	public void testUpdate() {
		userManager.updateUser(id, "XXX用户", null, null, null, null, null, null,
				null, null, null, null, null, 2);
	}

	@Test(priority = 12)
	public void testGetUser() {
		GetUserVO user = userManager.getUser(id);
		Assert.assertNotNull(user);
	}

	@Test(priority = 13)
	public void testListUser() {
		List<GetUserVO> list = userManager.listUser(null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 999)
	public void testDelete() {
		userManager.deleteUser(id);
	}

	@Test(priority = 0)
	public void testLogin() {
		sessionId = userManager.csLogin("admin",
				"e10adc3949ba59abbe56e057f20f883e", "192.168.1.111",
				TypeDefinition.CLIENT_TYPE_CS);
	}

	@Test(priority = 900)
	public void userLogoff() {
		userManager.userLogoff(sessionId);
	}

	@Test(priority = 20)
	public void testListOnlineUser() {
		List<ListOnlineUsersVO> list = userManager.listOnlineUser(organId,
				null, null, 0, 10);
		// Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 901)
	public void testListUserSession() {
		List<ListUserSessionHistoryVO> list = userManager
				.listUserSessionHistory("", "", organId, null, null, 1, 100,
						userId);
//		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 30)
	public void testCreateFavorite() {
		List<String> list = new ArrayList<String>();
		list.add(cameraId1);
		list.add(cameraId2);
		favoriteId = userManager.createFavorite("搜藏夹", list, id);
	}

	@Test(priority = 31)
	public void testUpdateFavorite() {
		List<String> list = new ArrayList<String>();
		// list.add("");
		userManager.updateFavorite(favoriteId, "修改搜藏夹", list, id);
	}

	@Test(priority = 32)
	public void testListFavorite() throws Exception {
		Element element = userManager.listFavorite(userId);
		Assert.assertNotNull(element);
	}

	@Test(priority = 800)
	public void testDeleteFavorite() {
		userManager.deleteFavorite(favoriteId);
	}

	@Test(priority = 40)
	public void testCreatePlayScheme() {
		Element root = new Element("PlayScheme");
		Element element = new Element("Screen");
		Element element1 = new Element("Channel");
		element1.setAttribute("Id", cameraId1);
		element1.setAttribute("PresetId", "");
		element1.setAttribute("Duration", "0");
		element.setAttribute("Index", "1");
		root.setAttribute("Id", "");
		root.setAttribute("Name", "成都出口全景");
		root.setAttribute("ScreenNumber", "1");
		root.setContent(element);
		element.setContent(element1);
		playSchemeId = userManager.createPlayScheme("播放方案", root, id);
	}

	@Test(priority = 41)
	public void testUpdatePlayScheme() {
		Element root = new Element("PlayScheme");
		Element element = new Element("Screen");
		Element element1 = new Element("Channel");
		element1.setAttribute("Id", cameraId2);
		element1.setAttribute("PresetId", "");
		element1.setAttribute("Duration", "0");
		element.setAttribute("Index", "1");
		root.setAttribute("Id", "");
		root.setAttribute("Name", "成都出口全景");
		root.setAttribute("ScreenNumber", "1");
		root.setContent(element);
		element.setContent(element1);
		userManager.updatePlayScheme(playSchemeId, "更新播放方案", root, id);
	}

	@Test(priority = 42)
	public void testListPlayScheme() throws JDOMException, IOException {
		Element element = userManager.listPlayScheme(userId);
		Assert.assertNotNull(element);
	}

	@Test(priority = 700)
	public void testDeletePlayScheme() {
		userManager.deletePlayScheme(playSchemeId);

	}

	String fId = null;

	@Test(priority = 401)
	public void createFavorite() {
		List<String> list = new ArrayList<String>();
		list.add(cameraId1);
		list.add(cameraId2);
		fId = userManager.createFavorite("搜藏夹11212111", list, userId);
	}

	@Test(priority = 411)
	public void listFavorite() throws Exception {
		Element element = userManager.listFavorite(userId);
		Assert.assertNotNull(element);
	}

	@Test(priority = 412)
	public void deleteFavorite() {
		userManager.deleteFavorite(fId);
	}
}