package test.service;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.model.DasCovi;
import com.znsx.cms.persistent.model.DasLoli;
import com.znsx.cms.persistent.model.DasRoadDetector;
import com.znsx.cms.persistent.model.DasWst;
import com.znsx.cms.persistent.model.SyncVehicleDetector;
import com.znsx.cms.service.iface.DasDataManager;
import com.znsx.cms.service.model.CoviVO;
import com.znsx.cms.service.model.DeviceStatusVO;
import com.znsx.cms.service.model.LoLiVO;
import com.znsx.cms.service.model.RoadFluxStatVO;
import com.znsx.cms.service.model.VdVO;
import com.znsx.cms.service.model.WsVO;
import com.znsx.cms.service.model.WstVO;

/**
 * DasDataManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-2 下午5:39:29
 */
public class DasDataManagerTest extends BaseTest {
	@Autowired
	private DasDataManager dasDataManager;

	// @Test
	public void testSaveData() throws Exception {

		List<Element> data = new LinkedList<Element>();
		for (int i = 0; i < 1000; i++) {
			Element e = new Element("Device");
			e.setAttribute("Type", "10");
			e.setAttribute("StandardNumber", "0101010101010011111");
			e.setAttribute("RecTime", System.currentTimeMillis() + "");
			e.setAttribute("UpFluxB", "30");
			e.setAttribute("UpFluxS", "50");
			e.setAttribute("UpFlux", "80");
			e.setAttribute("DwFluxB", "35");
			e.setAttribute("DwFluxS", "55");
			e.setAttribute("DwFlux", "90");
			e.setAttribute("UpSpeed", "90");
			e.setAttribute("DwSpeed", "100");
			e.setAttribute("UpOcc", "40.75");
			e.setAttribute("DwOcc", "75.00");
			e.setAttribute("LaneNum", "6");
			e.setAttribute("Status", "0");
			e.setAttribute("CommStatus", "0");
			data.add(e);
		}
		for (int i = 0; i < 1000; i++) {
			Element e = new Element("Device");
			e.setAttribute("Type", "12");
			e.setAttribute("StandardNumber", "0101010101010011111");
			e.setAttribute("RecTime", System.currentTimeMillis() + "");
			e.setAttribute("VisMax", "300");
			e.setAttribute("VisMin", "50");
			e.setAttribute("VisAvg", "80");
			e.setAttribute("WindSpeedMax", "10.0");
			e.setAttribute("WindSpeedMin", "1.0");
			e.setAttribute("WindSpeedAvg", "3.5");
			e.setAttribute("WindDir", "2");
			e.setAttribute("AirTempMax", "38.0");
			e.setAttribute("AirTempMin", "-10.0");
			e.setAttribute("AirTempAvg", "20.3");
			e.setAttribute("RoadTempMax", "40.0");
			e.setAttribute("RoadTempMin", "0.0");
			e.setAttribute("RoadTempAvg", "20.0");
			e.setAttribute("HumiMax", "90");
			e.setAttribute("HumiMin", "10");
			e.setAttribute("HumiAvg", "60");
			e.setAttribute("RainVol", "305.0");
			e.setAttribute("SnowVol", "10.0");
			e.setAttribute("RoadState", "1");
			e.setAttribute("Status", "0");
			e.setAttribute("CommStatus", "0");
			data.add(e);
		}

		long begin = System.currentTimeMillis();
		dasDataManager.saveData(data);
		System.out
				.println("use " + (System.currentTimeMillis() - begin) + "ms");
	}

	// // @Test
	// public void testJdbcTemplateBatch() throws Exception {
	// List<Object[]> data = new LinkedList<Object[]>();
	// for (int i = 0; i < 1000; i++) {
	// Object[] row = new Object[17];
	// row[0] = "0101010101010011111";
	// row[1] = Long.valueOf(System.currentTimeMillis());
	// row[2] = Integer.valueOf(120);
	// row[3] = Integer.valueOf(30);
	// row[4] = Integer.valueOf(50);
	// row[5] = Integer.valueOf(80);
	// row[6] = Integer.valueOf(35);
	// row[7] = Integer.valueOf(55);
	// row[8] = Integer.valueOf(90);
	// row[9] = Integer.valueOf(90);
	// row[10] = Integer.valueOf(100);
	// row[11] = "40.75";
	// row[12] = "75.00";
	// row[13] = Integer.valueOf(6);
	// row[14] = Integer.valueOf(0);
	// row[15] = Integer.valueOf(0);
	// row[16] = "";
	// data.add(row);
	// }
	// long begin = System.currentTimeMillis();
	// dasDataManager.testBatch(data);
	// System.out.println("use" + (System.currentTimeMillis() - begin) + "ms");
	// }

	// @Test
	// public void testJdbcBatch() throws Exception {
	// List<Object[]> data = new LinkedList<Object[]>();
	// for (int i = 0; i < 1000; i++) {
	// Object[] row = new Object[17];
	// row[0] = "0101010101010011111";
	// row[1] = Long.valueOf(System.currentTimeMillis());
	// row[2] = Integer.valueOf(120);
	// row[3] = Integer.valueOf(30);
	// row[4] = Integer.valueOf(50);
	// row[5] = Integer.valueOf(80);
	// row[6] = Integer.valueOf(35);
	// row[7] = Integer.valueOf(55);
	// row[8] = Integer.valueOf(90);
	// row[9] = Integer.valueOf(90);
	// row[10] = Integer.valueOf(100);
	// row[11] = "40.75";
	// row[12] = "75.00";
	// row[13] = Integer.valueOf(6);
	// row[14] = Integer.valueOf(0);
	// row[15] = Integer.valueOf(0);
	// row[16] = "";
	// data.add(row);
	// }
	// long begin = System.currentTimeMillis();
	// dasDataManager.testJdbcBatch(data);
	// System.out.println("use" + (System.currentTimeMillis() - begin) + "ms");
	// }

	@Test
	public void testListDeviceInfo() {
		List<Element> devices = new LinkedList<Element>();
		Element device1 = new Element("Device");
		device1.setAttribute("StandardNumber", "VD_1494_700");
		device1.setAttribute("Type", "10");
		devices.add(device1);

		Element device2 = new Element("Device");
		device2.setAttribute("StandardNumber", "VD_1531_890");
		device2.setAttribute("Type", "10");
		devices.add(device2);

		List<Element> list = dasDataManager.listDeviceInfo(devices);
	}

	// @Test
	public void statDeviceStatus() {
		Timestamp beginTime = new Timestamp(
				System.currentTimeMillis() - 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		Integer type = Integer.valueOf(10);
		String deviceName = null;
		String organSN = null;
		List<DeviceStatusVO> list = dasDataManager.statDeviceStatus(beginTime,
				endTime, type, new String[]{"test"}, organSN, 0, 50);
		dasDataManager.completeStatDeviceStatus(list);
	}

	// @Test
	public void countDeviceStatus() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 10 * 7
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		Integer type = Integer.valueOf(10);
		String deviceName = null;
		String organSN = null;
		int count = dasDataManager.countDeviceStatus(beginTime, endTime, type,
				new String[]{"test"}, organSN);
		System.out.println(count);
		System.out.println(Charset.defaultCharset());
	}

	// @Test
	public void testRemoveSwitchData() {
		dasDataManager.removeSwitchData();
	}

	// @Test
	public void statCovi() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		List<CoviVO> list = dasDataManager.statCovi(beginTime, endTime, null,
				null, "hour", 0, 10);
		dasDataManager.completeStatCovi(list);
		System.out.println(list.size());
	}

	// @Test
	public void countCovi() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		int count = dasDataManager.countCovi(beginTime, endTime, null, null,
				"hour");
		System.out.println(count);
	}

	// @Test
	public void statLoli() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		List<LoLiVO> list = dasDataManager.statLoLi(beginTime, endTime, null,
				null, "hour", 0, 10);
		dasDataManager.completeStatLoLi(list);
		System.out.println(list.size());
	}

	// @Test
	public void countLoli() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		int count = dasDataManager.countLoLi(beginTime, endTime, null, null,
				"hour");
		System.out.println(count);
	}

	// @Test
	public void statVd() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		List<VdVO> list = dasDataManager.statVD(beginTime, endTime, null, null,
				"hour", 0, 10);
		dasDataManager.completeStatVD(list);
		System.out.println(list.size());
	}

	// @Test
	public void countVd() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		int count = dasDataManager.countVD(beginTime, endTime, null, null,
				"hour");
		System.out.println(count);
	}

	// @Test
	public void statWs() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		List<WsVO> list = dasDataManager.statWS(beginTime, endTime, null, null,
				"hour", 0, 10);
		dasDataManager.completeStatWS(list);
		System.out.println(list.size());
	}

	// @Test
	public void countWs() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		int count = dasDataManager.countWS(beginTime, endTime, null, null,
				"hour");
		System.out.println(count);
	}

	// @Test
	public void statWst() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		List<WstVO> list = dasDataManager.statWST(beginTime, endTime, null,
				null, "hour", 0, 10);
		dasDataManager.completeStatWST(list);
		System.out.println(list.size());
	}

	// @Test
	public void countWst() {
		Timestamp beginTime = new Timestamp(System.currentTimeMillis() - 15
				* 24 * 3600 * 1000);
		Timestamp endTime = new Timestamp(System.currentTimeMillis() + 7 * 24
				* 3600 * 1000);
		int count = dasDataManager.countWST(beginTime, endTime, null, null,
				"hour");
		System.out.println(count);
	}

//	@Test
	public void batchInsertSyncVdData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar now = Calendar.getInstance();
		Float f = Float.valueOf(0f);
		
		String[] vdSns = new String[] { "VD-ZH-1", "VD-ZH-2", "VD-JH-1",
				"VD-JH-2", "VD-JC-1", "VD-JC-2", "VD-CJ-1", "VD-CJ-2" };
		for (String sn : vdSns) {
			List<SyncVehicleDetector> list = new LinkedList<SyncVehicleDetector>();
			Calendar c = Calendar.getInstance();
			c.set(2014, 2, 1);

			while (true) {
				SyncVehicleDetector entity = new SyncVehicleDetector();
				synchronized (sdf) {
					entity.setDateTime(sdf.format(c.getTime()));
				}
				entity.setDwFlow(f);
				entity.setDwFlowb(f);
				entity.setDwFlowm(f);
				entity.setDwFlows(f);
				entity.setDwFlowTotal(5000f + RandomUtils.nextInt(1000));
				entity.setDwOcc(f);
				entity.setDwOccb(f);
				entity.setDwOccm(f);
				entity.setDwOccs(f);
				entity.setDwSpeed(f);
				entity.setDwSpeedb(f);
				entity.setDwSpeedm(f);
				entity.setDwSpeeds(f);
				entity.setStandardNumber(sn);
				entity.setUpFlow(f);
				entity.setUpFlowb(f);
				entity.setUpFlowm(f);
				entity.setUpFlows(f);
				entity.setUpFlowTotal(5000f + RandomUtils.nextInt(1200));
				entity.setUpOcc(f);
				entity.setUpOccb(f);
				entity.setUpOccm(f);
				entity.setUpOccs(f);
				entity.setUpSpeed(f);
				entity.setUpSpeedb(f);
				entity.setUpSpeedm(f);
				entity.setUpSpeeds(f);
				list.add(entity);

				c.add(Calendar.DAY_OF_MONTH, 1);
				if (c.after(now)) {
					break;
				}
			}

			dasDataManager.batchInsertVdSyncData(list);
		}
	}
	
	@Test
	public void testListRoadFluxStat() {
		Collection<RoadFluxStatVO> list = dasDataManager.listRoadFlux();
		for (RoadFluxStatVO vo : list) {
			System.out.println(vo.getName() + vo.getMonth() + " : " + vo.getUpFlux());
		}
	}
	
	@Test
	public void insertTestWstData() {
		List<DasWst> list = new LinkedList<DasWst>();
		for (int i = 0; i < 100000; i ++) {
			DasWst wst = new DasWst();
			wst.setAirTempAvg(RandomUtils.nextInt(40)+"");
			wst.setCommStatus((short)0);
			wst.setHumiAvg(RandomUtils.nextInt(100) + "");
			wst.setOrgan("251000000000000000");
			wst.setRainVol(RandomUtils.nextInt(300)+"");
			wst.setRecTime(new Timestamp(System.currentTimeMillis() - i * 120000l));
			wst.setReserve(null);
			wst.setRoadSurface((short)RandomUtils.nextInt(8));
			wst.setRoadTempAvg(RandomUtils.nextInt(50)+"");
			wst.setSnowVol(RandomUtils.nextInt(300)+"");
			wst.setStandardNumber("2600002800001");
			wst.setStatus((short)0);
			wst.setVisAvg(RandomUtils.nextInt(2000));
			wst.setWindDir(RandomUtils.nextInt(360));
			wst.setWsAvg(RandomUtils.nextInt(100)/10.0 + "");
			list.add(wst);
		}
		// insert
		dasDataManager.batchInsertWstData(list);
	}
	
	@Test
	public void insertTestRsdData() {
		List<DasRoadDetector> list = new LinkedList<DasRoadDetector>();
		for (int i = 0; i < 200000; i ++) {
			DasRoadDetector rsd = new DasRoadDetector();
			rsd.setCommStatus((short)0);
			rsd.setOrgan("251000000000000000");
			rsd.setRainVol(RandomUtils.nextInt(300)+"");
			rsd.setRecTime(new Timestamp(System.currentTimeMillis() - i * 120000l));
			rsd.setReserve(null);
			rsd.setRoadSurface((short)RandomUtils.nextInt(8));
			rsd.setRoadTempAvg(RandomUtils.nextInt(50)+"");
			rsd.setSnowVol(RandomUtils.nextInt(300)+"");
			rsd.setStandardNumber("2500001200009");
			rsd.setStatus((short)0);
			list.add(rsd);
		}
		// insert
		dasDataManager.batchInsertRsdData(list);
	}
	
	@Test
	public void insertTestCoviData() {
		List<DasCovi> list = new LinkedList<DasCovi>();
		for (int i = 0; i < 200000; i ++) {
			DasCovi covi = new DasCovi();
			covi.setCommStatus((short)0);
			covi.setOrgan("251000000000000000");
			covi.setCo(RandomUtils.nextInt(100)+"");
			covi.setRecTime(new Timestamp(System.currentTimeMillis() - i * 120000l));
			covi.setReserve(null);
			covi.setVi(RandomUtils.nextInt(1000)+"");
			covi.setStandardNumber("5900902000000202");
			covi.setStatus((short)0);
			list.add(covi);
		}
		// insert
		dasDataManager.batchInsertCoviData(list);
	}
	
	@Test
	public void insertTestLoliData() {
		List<DasLoli> list = new LinkedList<DasLoli>();
		for (int i = 0; i < 200000; i ++) {
			DasLoli loli = new DasLoli();
			loli.setCommStatus((short)0);
			loli.setOrgan("251000000000000000");
			loli.setLo(RandomUtils.nextInt(400)+"");
			loli.setRecTime(new Timestamp(System.currentTimeMillis() - i * 120000l));
			loli.setReserve(null);
			loli.setLi(RandomUtils.nextInt(400)+"");
			loli.setStandardNumber("20510022000110");
			loli.setStatus((short)0);
			list.add(loli);
		}
		// insert
		dasDataManager.batchInsertLoliData(list);
	}
}
