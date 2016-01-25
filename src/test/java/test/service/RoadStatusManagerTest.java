package test.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.RoadStatusManager;

import test.BaseTest;

/**
 * RoadStatusManagerTest
 * @author huangbuji <p />
 * Create at 2014-7-30 下午5:53:00
 */
public class RoadStatusManagerTest extends BaseTest {
	@Autowired
	private RoadStatusManager roadStatusManager;
	@Autowired
	private OrganManager organManager;
	
//	@Test
	public void isOppositeUsableTest() {
		OrganRoad road = (OrganRoad)organManager.getOrganById("ff80808147662c49014767a5332801e4");
		List<VehicleDetector> vds = new LinkedList<VehicleDetector>();
		VehicleDetector vd1 = new VehicleDetector();
		vd1.setStandardNumber("251000000200000134");
		VehicleDetector vd2 = new VehicleDetector();
		vd1.setStandardNumber("251000000200000155");
		vds.add(vd1);
		vds.add(vd2);
		boolean flag = roadStatusManager.isOppositeUsable(road, vds, "0", "K1500", "K1520");
		System.out.println(flag);
	}
}
