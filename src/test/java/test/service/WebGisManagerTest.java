package test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import test.BaseTest;

import com.znsx.cms.service.iface.WebGisManager;
import com.znsx.cms.service.model.WfsRoadAdminVO;

/**
 * WebGisManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-14 下午3:04:01
 */
public class WebGisManagerTest extends BaseTest {
	@Autowired
	private WebGisManager webGisManager;

	// @Test
	public void listRD() {
		String wfsUrl = webGisManager.getWfsUrl();
		List<WfsRoadAdminVO> list = webGisManager.wfsListRoadAdmin(wfsUrl);
		// System.out.println(list.size());
	}
}
