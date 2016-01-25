package test.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.iface.EmManager;
import com.znsx.cms.service.iface.RuleManager;
import com.znsx.cms.service.rule.model.Event;
import com.znsx.cms.service.rule.model.RoadEvent;

/**
 * RuleTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-25 下午8:42:00
 */
public class RuleTest extends BaseTest {
	@Autowired
	private RuleManager ruleManager;
	@Autowired
	private EmManager emManager;

	@Test
	public void testHelloRule() {
		Event event = new Event();
		event.setType(1);
		event.setEventLevel(1);
		event.setLaneNumber(1);
		event.setHurtNumber(7);
		event.setRoadType(3);
		event.setDamageCarNumber(2);
		event.setCrowdMeter(2000);
		event.setDeathNumber(1);
		event.setDelayCarNumber(5);
		event.setDelayHumanNumber(5);
		event.setLossAmount(5000);
		event.setFire(true);
		event.setVisibility(100);
		event.setOppositeSupport(true);
		event.setQc(-1);
		event.setRecoverTime(20);
		event.setBeginStake("K1500");
		event.setEndStake("K1501+500");
		List<String> reasons = new ArrayList<String>();
		// reasons.add("0-3");
		reasons.add("1-6");
		// reasons.add("1-8");
		event.setReasons(reasons);
		ruleManager.generateScheme(event);
		for (String action : event.getVmsActions()) {
			System.out.println(action);
		}
		for (String action : event.getFireActions()) {
			System.out.println(action);
		}
		for (String action : event.getHospitalActions()) {
			System.out.println(action);
		}
		for (String action : event.getPoliceActions()) {
			System.out.println(action);
		}
		for (String action : event.getResourceActions()) {
			System.out.println(action);
		}
		for (String action : event.getRoadActions()) {
			System.out.println(action);
		}
		for (String action : event.getRoadAdminActions()) {
			System.out.println(action);
		}

	}

//	@Test
	public void testGenerate() {
		com.znsx.cms.persistent.model.Event event = emManager.getEmEvent("ff808081476c021d01476c8c7421003a");
		// 构建规则输入条件
 		com.znsx.cms.service.rule.model.Event conditions = new com.znsx.cms.service.rule.model.Event();
 		List<String> reasons = new LinkedList<String>();
		conditions.setBeginStake(event.getBeginStake());
		conditions.setCrowdMeter(event.getCrowdMeter() == null ? 0 : event
				.getCrowdMeter().intValue());
		conditions.setDamageCarNumber(event.getDamageCarNumber() == null ? 0
				: event.getDamageCarNumber().intValue());
		conditions.setDeathNumber(event.getDeathNumber() == null ? 0 : event
				.getDeathNumber().intValue());
		conditions.setDelayHumanNumber(event.getDelayHumanNumber() == null ? 0
				: event.getDelayHumanNumber().intValue());
		conditions.setEndStake(event.getEndStake());
		conditions.setEventLevel(event.getEventLevel() == null ? 1 : event
				.getEventLevel().intValue());
		Short isFire = event.getIsFire();
		conditions.setFire(isFire == null ? false
				: isFire.shortValue() != (short) 0);
		conditions.setHurtNumber(event.getHurtNumber() == null ? 0 : event
				.getHurtNumber().intValue());
		conditions.setLaneNumber(event.getLaneNumber() == null ? 0 : event
				.getLaneNumber().intValue());
		conditions.setNavigation(event.getNavigation() == null ? 0 : event
				.getNavigation().intValue());
		if (event.getDelayHumanNumber() != null
				&& event.getDelayHumanNumber().intValue() > 0) {
			reasons.add("9-7");
		}
		String subType = event.getSubType();
		if (StringUtils.isNotBlank(subType)) {
			String[] subTypes = subType.split("\\|");
			for (String st : subTypes) {
				reasons.add(st);
			}
		}
		reasons.add("0-4");
		conditions.setReasons(reasons);
		// 能否借用对侧车道，TODO 通过对侧车道车检器检测值进行通行能力运算得到
		conditions.setOppositeSupport(true);
		// 通行能力(-1:交通量>通行能力，0:交通量=通行能力,1:交通量<通行能力)，TODO 通过车检器检测值进行通行能力运算得到
		conditions.setQc(-1);
		conditions.setRoadType(event.getRoadType() == null ? 0 : event
				.getRoadType().intValue());
		conditions.setType(event.getType().intValue());
		// TODO 通过气象/能见度检测器检测值得到
		conditions.setVisibility(80);
		// 生成规则输出
		ruleManager.generateScheme(conditions);
		
		System.out.println("done");
	}
	
	@Test
	public void generateSearchCondition() {
		RoadEvent event = new RoadEvent();
		event.setName("长潭高速");
		ruleManager.generateSearchCondition(event);
		for (String type : event.getCameraTypes()) {
			System.out.println(type);
		}
		for (String tollgate : event.getTollgates()) {
			System.out.println(tollgate);
		}
		System.out.println("前方范围：" + event.getMap().get("frontSearchRange"));
		System.out.println("后方范围：" + event.getMap().get("backSearchRange"));
	}
	
	public static void main(String[] args) {
		String buffer = "K1500";
		String bufferOpposite = "K1504";
		String action = "{bufferOpposite}处设置过渡区，往后每隔10米布设一个反光锥筒，隔离出通行车道";
		String content = StringUtils.replace(action, "{buffer}", buffer);
		content = StringUtils.replace(content, "{bufferOpposite}",
				bufferOpposite);
		
		System.out.println(content);
	}
}
