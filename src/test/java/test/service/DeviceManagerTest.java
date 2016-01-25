package test.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.common.StandardObjectCode;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.DeviceManager;
import com.znsx.cms.service.iface.ImageManager;
import com.znsx.cms.service.iface.UserManager;
import com.znsx.cms.service.model.DeviceModelVO;
import com.znsx.cms.service.model.DvrVO;
import com.znsx.cms.service.model.GetCameraVO;
import com.znsx.cms.service.model.GetControlDeviceVO;
import com.znsx.cms.service.model.GetCoviVO;
import com.znsx.cms.service.model.GetDvrVO;
import com.znsx.cms.service.model.GetFireDetectorVO;
import com.znsx.cms.service.model.GetLoliVO;
import com.znsx.cms.service.model.GetNoDetectorVO;
import com.znsx.cms.service.model.GetPushButtonVO;
import com.znsx.cms.service.model.GetVehicleDetectorVO;
import com.znsx.cms.service.model.GetWeatherStatVO;
import com.znsx.cms.service.model.GetWindSpeedVO;
import com.znsx.cms.service.model.ListCameraVO;
import com.znsx.cms.service.model.PresetVO;
import com.znsx.cms.service.model.PtsDvrVO;
import com.znsx.cms.service.model.TopRealPlayLog;
import com.znsx.cms.web.dto.omc.CountDeviceDTO;

/**
 * 设备业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午5:19:33
 */
public class DeviceManagerTest extends BaseTest {
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private UserManager userManager;

	private String camerId;

	private String dvrId;

	private String deviceModelId;

	private String presetId;

	private String dasId = "10060000000000000006";

	private String fireDetectorId;

	private String coviId;

	private String psuhButtonId;

	private String noDetectorId;

	private String loliId;

	private String weatherStatId;

	private String vehicleDetectorId;

	private String wsId;

	private String cdId;

	@Test(priority = 10)
	public void testCreateCamera() {
		String standardNumber = userManager.generateStandardNum("Camera",
				"402881ef493c20b501493c2c583f0008");
		camerId = deviceManager.createCamera(standardNumber, "03", "Camera-f4",
				"402881ef493c20b501493c2c583f0008", null, "", null,
				TypeDefinition.STORE_TYPE_LOCAL,
				TypeDefinition.STORE_PLAN_DEFAULT, null, "4CIF", dvrId, null,
				null, new Short("1"), null, null, null, "50.20", null, null,
				null, null, 1, null, null, null, null);
		Assert.assertTrue(StringUtils.isNotBlank(camerId));
	}

	// @Test(dependsOnMethods={"testCreateCamera"})
	// public void testGetCamera() {
	// Camera camera = deviceManager.getCamera(camerId);
	// Assert.assertNotNull(camera);
	// Assert.assertTrue(StringUtils.isNotBlank(camera.getProperty()
	// .getMainStream()));
	// }

	@Test(priority = 11)
	public void testUpdateCamera() {
		deviceManager.updateCamera(camerId, null,
				StandardObjectCode.DEVICE_CAMERA, null, null, null, null, null,
				new Short("2"), TypeDefinition.STORE_PLAN_DEFAULT,
				TypeDefinition.STORE_PLAN_DEFAULT, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null);
	}

	@Test(priority = 12)
	public void testGetCamera() {
		GetCameraVO camera = deviceManager.getCamera(camerId);
		Assert.assertNotNull(camera);
	}

	@Test(priority = 101)
	public void testDeleteCamera() {
		deviceManager.deleteCamera(camerId);
	}

	@Test(priority = 13)
	public void testListCamera() {
		List<ListCameraVO> vo = deviceManager.listCamera(dvrId, 1, 100);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 20)
	public void testCreateDeviceModel() {
		deviceModelId = deviceManager.createDeviceModel("Dvr-v_2",
				TypeDefinition.DEVICE_TYPE_CAMERA + "", "1", "海康摄像头");
	}

	@Test(priority = 21)
	public void testUpdateDeviceModel() {
		deviceManager.updateDeviceModel(deviceModelId, null, null, "1", "摄像头");
	}

	@Test(priority = 22)
	public void testGetDeviceModel() {
		DeviceModelVO vo = deviceManager.getDeviceModel(deviceModelId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 23)
	public void testListDeviceModel() {
		List<DeviceModelVO> listVO = deviceManager
				.listDeviceModel("1", 0, 1000);
		Assert.assertTrue(listVO.size() > 0);
	}

	@Test(priority = 24)
	public void testDeleteDeviceModel() {
		deviceManager.deleteDeviceModel(deviceModelId);
	}

	@Test(priority = 0)
	public void testCreateDVR() {
		String standardNumber = userManager.generateStandardNum("Dvr",
				"402881ef493c20b501493c2c583f0008");
		dvrId = deviceManager.createDvr(standardNumber,
				StandardObjectCode.DEVICE_DVR, "DVR-f3",
				"10060000000000000001", "TCP", "compatible", 16, 4,
				"402881ef493c20b501493c2c583f0008", "adsl", "192.168.10.1",
				8000, "1", null, "本地", "备注", "admin", "12345", 120, null, 4, 1,
				null);
	}

	@Test(priority = 1)
	public void testUpdateDVR() {
		deviceManager.updateDvr(dvrId, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, 8, 2, null, null);
	}

	@Test(priority = 2)
	public void testGetDVR() {
		GetDvrVO dvrVO = deviceManager.getDvr(dvrId);
		Assert.assertNotNull(dvrVO);
	}

	@Test(priority = 102)
	public void testDeleteDVR() {
		deviceManager.deleteDvr(dvrId, false);
	}

	@Test(priority = 3)
	public void testListDVR() {
		List<DvrVO> listVO = deviceManager.listDvr("10010000000000000001", 0,
				10);
		Assert.assertTrue(listVO.size() > 0);
	}

	@Test(priority = 30)
	public void testCreatePreset() {
		presetId = deviceManager.createPreset(camerId, 2, "预置点2");
	}

	@Test(priority = 31)
	public void testUpdatePreset() {
		deviceManager.updatePreset(presetId, null, null);
	}

	@Test(priority = 100)
	public void testDeletePreset() {
		deviceManager.deletePreset(presetId);
	}

	@Test(priority = 32)
	public void testListPreset() {
		List<PresetVO> presets = deviceManager.listVicPreset(camerId);
		Assert.assertTrue(presets.size() > 0);
	}

	@Test(priority = 40)
	public void testListDvrByPts() {
		List<PtsDvrVO> list = deviceManager.listDvrByCcs(
				"10060000000000000001", 0, 2);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 41)
	public void testCountDvrByPts() {
		int count = deviceManager.countDvrByCcs("10060000000000000001");
		Assert.assertTrue(count > 0);
	}

	@Test(priority = 42)
	public void countDevice() {
		CountDeviceDTO count = deviceManager.countDevice(
				"402881ef493c20b501493c2c583f0008", "1");
	}

	@Test(priority = 200)
	public void testListAuthCamera() {
		deviceManager.listUserAuthCamera("10020000000000000000");
	}

	@Test(priority = 51)
	public void createFireDetector() {
		fireDetectorId = deviceManager.createFireDetector(
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008",
				321321, null, null, null, null, null, null, null, null);
	}

	@Test(priority = 52)
	public void updateFireDetector() {
		deviceManager.updateFireDetector(fireDetectorId,
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008",
				321321, null, null, null, null, null, null, null, null);
	}

	@Test(priority = 53)
	public void getFireDetector() {
		GetFireDetectorVO vo = deviceManager.getFireDetector(fireDetectorId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 54)
	public void listFireDetector() {
		List<GetFireDetectorVO> list = deviceManager.listFireDetector(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 300)
	public void deleteFireDetector() {
		deviceManager.deleteFireDetector(fireDetectorId);
	}

	@Test(priority = 61)
	public void createCovi() {
		coviId = deviceManager.createCovi(System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null);
	}

	@Test(priority = 62)
	public void updateCovi() {
		deviceManager.updateCovi(coviId, System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null);
	}

	@Test(priority = 63)
	public void getCovi() {
		GetCoviVO vo = deviceManager.getCovi(coviId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 64)
	public void listCovi() {
		List<GetCoviVO> list = deviceManager.listCovi(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 301)
	public void deleteCovi() {
		deviceManager.deleteCovi(coviId);
	}

	@Test(priority = 71)
	public void createPushButton() {
		psuhButtonId = deviceManager.createPushButton(
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008",
				321321, null, null, null, null, null, null, null, null);
	}

	@Test(priority = 72)
	public void updatePushButton() {
		deviceManager.updatePushButton(psuhButtonId, System.currentTimeMillis()
				+ "", System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null);
	}

	@Test(priority = 73)
	public void getPushButton() {
		GetPushButtonVO vo = deviceManager.getPushButton(psuhButtonId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 74)
	public void listPushButton() {
		List<GetPushButtonVO> list = deviceManager.listPushButton(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 302)
	public void deletePushButton() {
		deviceManager.deletePushButton(psuhButtonId);
	}

	@Test(priority = 81)
	public void createNoDetector() {
		noDetectorId = deviceManager.createNoDetector(
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008",
				321321, null, null, null, null, null, null, null, null, null,
				null);
	}

	@Test(priority = 82)
	public void updateNoDetector() {
		deviceManager.updateNoDetector(noDetectorId, System.currentTimeMillis()
				+ "", System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null);
	}

	@Test(priority = 83)
	public void getNoDetector() {
		GetNoDetectorVO vo = deviceManager.getNoDetector(noDetectorId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 84)
	public void listNoDetector() {
		List<GetNoDetectorVO> list = deviceManager.listNoDetector(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 303)
	public void deleteNoDetector() {
		deviceManager.deleteNoDetector(noDetectorId);
	}

	@Test(priority = 91)
	public void createLoli() {
		loliId = deviceManager.createLoli(System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null, null, null);
	}

	@Test(priority = 92)
	public void updateLoli() {
		deviceManager.updateLoli(loliId, System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null, null, null);
	}

	@Test(priority = 93)
	public void getLoli() {
		GetLoliVO vo = deviceManager.getLoli(loliId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 94)
	public void listLoli() {
		List<GetLoliVO> list = deviceManager.listLoli(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 304)
	public void deleteLoli() {
		deviceManager.deleteLoli(loliId);
	}

	@Test(priority = 101)
	public void createWeatherStat() {
		weatherStatId = deviceManager.createWeatherStat(
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef48fe715e0148fe7299860004",
				321321, null, null, null, null, null, null, null, null, null,
				null, null, null);
	}

	@Test(priority = 102)
	public void updateWeatherStat() {
		deviceManager.updateWeatherStat(weatherStatId,
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef48fe715e0148fe7299860004",
				321321, null, null, null, null, null, null, null, null, null,
				null, null, null);
	}

	@Test(priority = 103)
	public void getWeatherStat() {
		GetWeatherStatVO vo = deviceManager.getWeatherStat(weatherStatId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 104)
	public void listWeatherStat() {
		List<GetWeatherStatVO> list = deviceManager.listWeatherStat(
				"402881ef48fe715e0148fe7299860004", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 305)
	public void deleteWeatherStat() {
		deviceManager.deleteWeatherStat(weatherStatId);
	}

	@Test(priority = 111)
	public void createVehicleDetector() {
		// vehicleDetectorId = deviceManager.createVehicleDetector(
		// System.currentTimeMillis() + "", System.currentTimeMillis()
		// + "", dasId, "402881ef493c20b501493c2c583f0008", 321321, null,
		// null, null, null, null, null, null, null, null, null, null);
		vehicleDetectorId = deviceManager.createVehicleDetector(
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008", 120,
				"k100+400", null, null, 90, 50, 90, 50, 100, 10, null, "1",
				"192.168.1.50", "80", "4", null);
	}

	@Test(priority = 112)
	public void updateVehicleDetector() {
		// deviceManager.updateVehicleDetector(vehicleDetectorId,
		// System.currentTimeMillis() + "", System.currentTimeMillis()
		// + "", dasId, "402881ef493c20b501493c2c583f0008", 321321, null,
		// null, null, null, null, null, null, null, null, null, null);
		deviceManager.updateVehicleDetector("402881ef48aad4010148aad4d2a10003",
				"DAS1", "10100101010101001010", "10060000000000000006",
				"10010000000000000000", 120, "K200+30", null, null, null,
				null, null, null, null, null, null, "1", "", "", "2", null);
	}

	@Test(priority = 113)
	public void getVehicleDetector() {
		GetVehicleDetectorVO vo = deviceManager
				.getVehicleDetector(vehicleDetectorId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 114)
	public void listVehicleDetector() {
		List<GetVehicleDetectorVO> list = deviceManager.listVehicleDetector(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 306)
	public void deleteVehicleDetector() {
		deviceManager.deleteVehicleDetector(vehicleDetectorId);
	}

	@Test(priority = 121)
	public void createWindSpeed() {
		wsId = deviceManager.createWindSpeed(System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null);
	}

	@Test(priority = 122)
	public void updateWindSpeed() {
		deviceManager.updateWindSpeed(wsId, System.currentTimeMillis() + "",
				System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				null, null, null, null, null, null, null);
	}

	@Test(priority = 123)
	public void getWindSpeed() {
		GetWindSpeedVO vo = deviceManager.getWindSpeed(wsId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 124)
	public void listWindSpeed() {
		List<GetWindSpeedVO> list = deviceManager.listWindSpeed(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 306)
	public void deleteWindSpeed() {
		deviceManager.deleteWindSpeed(wsId);
	}

	@Test(priority = 131)
	public void createControlDevice() {
		cdId = deviceManager.createControlDevice(System.currentTimeMillis()
				+ "", System.currentTimeMillis() + "", dasId,
				"402881ef493c20b501493c2c583f0008", 321321, null, null, null,
				(short) 19, null, null, null, null, null, null, null, null,
				null);
	}

	@Test(priority = 132)
	public void updateControlDevice() {
		deviceManager.updateControlDevice(cdId,
				System.currentTimeMillis() + "", System.currentTimeMillis()
						+ "", dasId, "402881ef493c20b501493c2c583f0008",
				321321, null, null, null, (short) 1, null, null, null, null,
				null, null, null, null, null);
	}

	@Test(priority = 133)
	public void getControlDevice() {
		GetControlDeviceVO vo = deviceManager.getControlDevice(cdId);
		Assert.assertNotNull(vo);
	}

	@Test(priority = 134)
	public void listControlDevice() {
		List<GetControlDeviceVO> list = deviceManager.listControlDevice(
				"402881ef493c20b501493c2c583f0008", null, null, null, 0, 10,
				(short) 19, null);
		Assert.assertTrue(list.size() > 0);
	}

	@Test(priority = 307)
	public void deleteControlDevice() {
		deviceManager.deleteControlDevice(cdId, "1");
	}

	@Test
	public void listOrganDevice() {
		deviceManager.listUserResource("10020000000000000000");
	}

	@Test
	public void statDeviceOnlineTime() {
		System.out.println(deviceManager.statDeviceOnlineTime(
				"25100000200001000011", 1429251596476l, 1429251976988l));
	}

	@Test
	public void topRealPlay() {
		List<TopRealPlayLog> list = deviceManager.topRealPlay(
				"10020000000000000000", 20);
		for (TopRealPlayLog top : list) {
			System.out.println(top.getId() + "," + top.getName() + ","
					+ top.getCount());
		}
	}
	
	@Test
	public void listOrganCamera() {
		deviceManager.listOrganCamera("10010000000000000000");
		System.out.println("done");
	}
}