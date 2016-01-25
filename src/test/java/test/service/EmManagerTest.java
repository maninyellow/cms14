package test.service;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.service.iface.EmManager;
import com.znsx.cms.service.iface.RuleManager;
import com.znsx.cms.service.model.SchemeTempletVO;
import com.znsx.cms.service.rule.model.RoadEvent;

/**
 * EmManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午4:18:40
 */
public class EmManagerTest extends BaseTest {
	@Autowired
	private EmManager emManager;
	@Autowired
	private RuleManager ruleManager;

	private String templetId;
	private String dictionaryId;

	// @Test(priority = 1)
	public void createDictionary() {
		dictionaryId = emManager.createDictionary("Police", "通知附近交警部门");
	}

	// @Test(priority = 2)
	public void createTemplet() {
		templetId = emManager.createTemplet(
				"交通事故预案" + System.currentTimeMillis(), Short.valueOf("1"),
				Short.valueOf("1"), "10010000000000000000");
	}

	// @Test(priority = 3)
	public void bindTempletDictionary() {
		emManager.bindTempletDictionary(templetId, dictionaryId,
				Short.valueOf("1"), Integer.valueOf(1000));
	}

	// @Test(priority = 4)
	public void deleteTemplet() {
		emManager.deleteTemplet("402881e846838b620146838b6fed0002");
	}

	// @Test
	public void saveScheme() {
		long l = System.currentTimeMillis();

		Element scheme = new Element("Scheme");
		scheme.setAttribute("Id", "402881e84683dd00014683dd05bd0002");
		scheme.setAttribute("SchemeTempletId",
				"402881e84683b119014683b11f210002");
		scheme.setAttribute("Name", "交通事故预案" + l);
		scheme.setAttribute("EventId", "1");

		// Element step1 = new Element("Step");
		// step1.setAttribute("Seq", "1");
		// step1.setAttribute("Type", "RoadAdmin");
		// scheme.addContent(step1);
		//
		// Element item1 = new Element("Item");
		// item1.setAttribute("Id", "402881e84683dd00014683dd05cc0003");
		// item1.setAttribute("TargetId", "2");
		// item1.setAttribute("TargetName", "RoadAdmin-1");
		// item1.setAttribute("Telephone", "028-66666666");
		// item1.setAttribute("LinkMan", "Mr.B");
		// item1.setAttribute("RequestContent", "龙泉高速公路K200+100上行方向发生多车追尾交通事故");
		// item1.setAttribute("ResponseContent", "");
		// item1.setAttribute("BeginTime", "");
		// item1.setAttribute("ArriveTime", "");
		// item1.setAttribute("EndTime", "");
		// item1.setAttribute("ActionStatus", "0");
		// item1.setAttribute("Note", "");
		// step1.addContent(item1);

		Element step2 = new Element("Step");
		step2.setAttribute("Seq", "2");
		step2.setAttribute("Type", "Police");
		scheme.addContent(step2);

		Element item2 = new Element("Item");
		item2.setAttribute("Id", "402881e84683dd00014683dd05cc0004");
		item2.setAttribute("TargetId", "1");
		item2.setAttribute("TargetName", "Police-1");
		item2.setAttribute("Telephone", "028-88888887");
		item2.setAttribute("LinkMan", "Mr.H");
		item2.setAttribute("RequestContent", "龙泉高速公路K200+100上行方向发生多车追尾交通事故");
		item2.setAttribute("ResponseContent", "");
		item2.setAttribute("BeginTime", "");
		item2.setAttribute("ArriveTime", "");
		item2.setAttribute("EndTime", "");
		item2.setAttribute("ActionStatus", "0");
		item2.setAttribute("Note", "");
		step2.addContent(item2);

		// Element step3 = new Element("Step");
		// step3.setAttribute("Seq", "3");
		// step3.setAttribute("Type", "Hospital");
		// scheme.addContent(step3);
		//
		// Element item3 = new Element("Item");
		// item3.setAttribute("Id", "");
		// item3.setAttribute("TargetId", "3");
		// item3.setAttribute("TargetName", "Hospital-1");
		// item3.setAttribute("Telephone", "028-77777777");
		// item3.setAttribute("LinkMan", "Mr.J");
		// item3.setAttribute("RequestContent",
		// "龙泉高速公路K200+100上行方向发生多车追尾交通事故,造成至少30人受伤,需要8辆救护车");
		// item3.setAttribute("ResponseContent", "");
		// item3.setAttribute("BeginTime", "");
		// item3.setAttribute("ArriveTime", "");
		// item3.setAttribute("EndTime", "");
		// item3.setAttribute("ActionStatus", "0");
		// item3.setAttribute("Note", "");
		// step3.addContent(item3);

		emManager.saveScheme("402881e84683dd00014683dd05bd0002", "交通事故预案" + l,
				"10010000000000000000", "402881e84683b119014683b11f210002",
				"1", "10020000000000000000", "admin", scheme);
	}

	// @Test
	public void saveSchemeTemplet() {
		Element schemeTemplet = new Element("SchemeTemplet");
		schemeTemplet.setAttribute("Id", "");
		schemeTemplet.setAttribute("Name", "交通事故预案(III级)");
		schemeTemplet.setAttribute("Type", "1");
		schemeTemplet.setAttribute("Level", "3");
		Element step1 = new Element("Step");
		step1.setAttribute("Seq", "1");
		step1.setAttribute("Content", "通知附近{{路政}}[[RoadAdmin(1000)]]单位");

		Element step2 = new Element("Step");
		step2.setAttribute("Seq", "2");
		step2.setAttribute("Content", "通知附近{{交警}}[[Police(3000)]]");

		Element step3 = new Element("Step");
		step3.setAttribute("Seq", "3");
		step3.setAttribute("Content", "通知周围{{医院}}[[Hospital(3000)]]");

		Element step4 = new Element("Step");
		step4.setAttribute("Seq", "4");
		step4.setAttribute("Content", "发布{{情报板}}[[Cms(5000)]]信息");

		schemeTemplet.addContent(step1);
		schemeTemplet.addContent(step2);
		schemeTemplet.addContent(step3);
		schemeTemplet.addContent(step4);

		emManager.saveSchemeTemplet("交通事故预案(III级)", "10010000000000000000",
				schemeTemplet);
	}

	@Test
	public void listSchemeTemplet() {
		List<SchemeTempletVO> list = emManager.listSchemeTemplet(
				"10010000000000000000", null);
	}

	// @Test
	public void listCmsByRange() {
		List<ControlDeviceCms> list = emManager.listCmsByRange("K1498",
				"K1500", 100, 0, "402881ef4768133301476819016e0006");
		System.out.println(list.size());
	}

//	@Test
	public void listEventCamera() {
		RoadEvent event = new RoadEvent();
		event.setName("长潭高速");
		ruleManager.generateSearchCondition(event);
		
//		List<String> cameraTypes = new LinkedList<String>();
//		cameraTypes.add("广场");
//		cameraTypes.add("互通");
//		cameraTypes.add("主线");
//		cameraTypes.add("道");

//		List<String> tollgates = new LinkedList<String>();
//		tollgates.add("雨花收费站");
//		tollgates.add("长沙收费站");
//		tollgates.add("三一收费站");
		List<Camera> vics = emManager.listEventCamera(event.getCameraTypes(), event.getTollgates(),
				1500f, 1500.200f, 8000, 2000, 0,
				"297edff8490cc55e01490cdaf6470013");

		for (Camera c : vics) {
			System.out.println(c.getName());
		}
	}
	
	@Test
	public void listEvent() {
		emManager.listEvent("10010000000000000000", null, null, null, null);
	}
}
