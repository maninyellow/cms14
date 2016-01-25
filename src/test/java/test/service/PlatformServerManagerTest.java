package test.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.PlatformServerManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.ListCcsVO;
import com.znsx.cms.service.model.ListCrsVO;
import com.znsx.cms.service.model.ListDasVO;
import com.znsx.cms.service.model.ListDwsVO;
import com.znsx.cms.service.model.ListEnsVO;
import com.znsx.cms.service.model.ListMssVO;
import com.znsx.cms.service.model.ListPtsVO;
import com.znsx.cms.service.model.PlatformServerVO;
import com.znsx.cms.web.dto.omc.ListPlatformServerDTO;

/**
 * 平台服务器业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午4:57:17
 */
public class PlatformServerManagerTest extends BaseTest {
	@Autowired
	private PlatformServerManager platformServerManager;
	@Autowired
	private UserManager userManager;

	private String mssId;

	private String ccsId = "10060000000000000001";

	private String crsId;

	private String ptsId;

	private String dwsId;

	private String dasId;

	private String ensId;

	@Test(priority = 0)
	public void testCreateCcs() {
		String standardNumber = userManager.generateStandardNum("Ccs", null);
		ccsId = platformServerManager.createCcs(standardNumber, "通信服务器"
				+ System.currentTimeMillis(), "安装位置", "169.254.1.2",
				"10.10.10.10", "", "5060", Short.valueOf("0"));
	}

	@Test(priority = 20)
	public void testUpdateCcs() {
		platformServerManager.updateCcs(ccsId, null,
				"通信服务器" + System.currentTimeMillis(), null, null,
				"11.11.11.11", null, "5061", Short.valueOf("1"));
	}

	@Test(priority = 999)
	public void testDeleteCcs() {
		platformServerManager.deleteCcs(ccsId);
	}

	@Test(priority = 21)
	public void testListCcs() {
		List<ListCcsVO> list = platformServerManager.listCcs();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 22)
	public void testListCcsUserSesion() {
		List<CcsUserSessionVO> list = platformServerManager
				.listCcsUserSession("251000000020000001");
	}

	@Test(priority = 10)
	public void testCreateMss() {
		String standardNumber = userManager.generateStandardNum("Mss", null);
		mssId = platformServerManager.createMss(standardNumber,
				"流媒体" + System.currentTimeMillis(), "安装位置", "133.168.10.101",
				"123.11.19.221", null, "5060");
		Assert.assertTrue(StringUtils.isNotBlank(mssId));
	}

	@Test(priority = 11)
	public void testUpdateMss() {
		platformServerManager.updateMss(mssId, null,
				"流媒体" + System.currentTimeMillis(), "安装位置", "133.168.1.201",
				"123.11.19.221", "5061", ccsId, null);
	}

	@Test(priority = 900)
	public void testDeleteMss() {
		platformServerManager.deleteMss(mssId);
	}

	@Test(priority = 12)
	public void testListMss() {
		List<ListMssVO> list = platformServerManager.listMss();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 100)
	public void testListPlatformServer() {
		ListPlatformServerDTO dto = platformServerManager.listPlatformServer(0,
				100, "", "");
		Assert.assertTrue(dto.getPlatformServerList().size() > 0);
	}

	@Test(priority = 30)
	public void testCreateCrs() {
		String standardNumber = userManager.generateStandardNum("Crs", null);
		crsId = platformServerManager.createCrs(standardNumber,
				"CRS" + System.currentTimeMillis(), "安装位置", "192.168.1.201",
				"10.10.10.10", "5060", null);
	}

	@Test(priority = 31)
	public void testUpdateCrs() {
		platformServerManager.updateCrs(crsId, null,
				"CRS" + System.currentTimeMillis(), ccsId, null, null, null,
				"5062", null);
	}

	@Test(priority = 901)
	public void testDeleteCrs() {
		platformServerManager.deleteCrs(crsId);
	}

	@Test(priority = 32)
	public void testListCrs() {
		List<ListCrsVO> list = platformServerManager.listCrs();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 22)
	public void testGetPlatformServer() {
		PlatformServerVO vo = platformServerManager.getPlatformServer(ccsId,
				TypeDefinition.SERVER_TYPE_CCS);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 40)
	public void testCreatePts() {
		String standardNumber = userManager.generateStandardNum("Pts", null);
		ptsId = platformServerManager.createPts(standardNumber,
				"PTS" + System.currentTimeMillis(), "安装地址", "169.168.1.2",
				"169.168.1.111", null, "5060");
	}

	@Test(priority = 41)
	public void testUpdatePts() {
		platformServerManager.updatePts(ptsId, null, null, ccsId, null, null,
				null, null, null);
	}

	@Test(priority = 902)
	public void testDeletePts() {
		platformServerManager.deletePts(ptsId);
	}

	@Test(priority = 42)
	public void testListPts() {
		List<ListPtsVO> list = platformServerManager.listPts();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 50)
	public void testCreateDws() {
		String standardNumber = userManager.generateStandardNum("Dws", null);
		dwsId = platformServerManager.createDws(standardNumber, "电视墙服务器"
				+ System.currentTimeMillis(), "安装地址", "155.168.1.2",
				"155.168.1.111", null, "5060");
	}

	@Test(priority = 51)
	public void testUpdateDws() {
		platformServerManager.updateDws(dwsId, null, null, ccsId, null, null,
				null, null, null);
	}

	@Test(priority = 52)
	public void testListDws() {
		List<ListDwsVO> list = platformServerManager.listDws();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 903)
	public void testDeleteDws() {
		platformServerManager.deleteDws(dwsId);
	}

	@Test(priority = 60)
	public void testCreateDas() {
		String standardNumber = userManager.generateStandardNum("Das", null);
		dasId = platformServerManager.createDas(standardNumber, "数据采集服务器"
				+ System.currentTimeMillis(), "安装地址", "155.168.1.2",
				"155.168.1.111", null, "5060");
	}

	@Test(priority = 61)
	public void testUpdateDas() {
		platformServerManager.updateDas(dasId, null, null, null, null, null,
				null, null);
	}

	@Test(priority = 62)
	public void testListDas() {
		List<ListDasVO> list = platformServerManager.listDas();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 904)
	public void testDeleteDas() {
		platformServerManager.deleteDas(dasId);
	}

	@Test(priority = 70)
	public void testCreateEns() {
		String standardNumber = userManager.generateStandardNum("Ens", null);
		ensId = platformServerManager.createEns(standardNumber, "事件服务器"
				+ System.currentTimeMillis(), "安装地址", "155.168.1.2",
				"155.168.1.111", null, "5060");
	}

	@Test(priority = 71)
	public void testUpdateEns() {
		platformServerManager.updateEns(ensId, null, null, null, null, null,
				null, null, null);
	}

	@Test(priority = 72)
	public void testListEns() {
		List<ListEnsVO> list = platformServerManager.listEns();
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 905)
	public void testDeleteEns() {
		platformServerManager.deleteEns(ensId);
	}
}
