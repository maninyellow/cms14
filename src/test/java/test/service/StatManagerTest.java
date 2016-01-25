package test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.iface.StatManager;
import com.znsx.cms.service.model.RoadDeviceStatusVO;
import com.znsx.cms.service.model.StatCameraStatusVO;

/**
 * StatManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-7-8 下午2:15:37
 */
public class StatManagerTest extends BaseTest {
	@Autowired
	private StatManager statManager;

	@Test
	public void listRoadDeviceStatus() {
		List<RoadDeviceStatusVO> list = statManager.listRoadDeviceStatus(null,
				null, null, null, null);
		for (RoadDeviceStatusVO vo : list) {
			System.out.println("OrganName:" + vo.getOrganName()
					+ "---------Name:" + vo.getName());
		}
	}

	@Test
	public void listCameraStatus() {
		List<StatCameraStatusVO> list = statManager.listCameraStatus(null,
				null, null);
		for (StatCameraStatusVO vo : list) {
			System.out.println("OrganName:" + vo.getOrganName()
					+ "---------Name:" + vo.getName() + "------------CrsName:"
					+ vo.getCrsName());
		}
	}
}
