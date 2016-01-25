package com.znsx.cms.service.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.runtime.StatelessKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.RuleManager;
import com.znsx.cms.service.rule.model.Event;
import com.znsx.cms.service.rule.model.RoadEvent;

/**
 * RuleManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-25 下午8:57:46
 */
public class RuleManagerImpl extends BaseManagerImpl implements RuleManager {
	@Autowired
	private StatelessKnowledgeSession vmsBwKSession;
	@Autowired
	private StatelessKnowledgeSession vmsTaKSession;
	@Autowired
	private StatelessKnowledgeSession vmsRmKSession;
	@Autowired
	private StatelessKnowledgeSession fireKSession;
	@Autowired
	private StatelessKnowledgeSession hospitalKSession;
	@Autowired
	private StatelessKnowledgeSession policeKSession;
	@Autowired
	private StatelessKnowledgeSession resourceKSession;
	@Autowired
	private StatelessKnowledgeSession roadKSession;
	@Autowired
	private StatelessKnowledgeSession roadAdminKSession;
	@Autowired
	private StatelessKnowledgeSession displayWallKSession;

	@Override
	public void generateScheme(Event event) {
		if (event.getType() == TypeDefinition.EVENT_TYPE_BW) {
			List<String> vmsActions = new LinkedList<String>();
			vmsBwKSession.setGlobal("list", vmsActions);
			vmsBwKSession.execute(event);
			event.setVmsActions(vmsActions);
		} else if (event.getType() == TypeDefinition.EVENT_TYPE_TA) {
			List<String> vmsActions = new LinkedList<String>();
			vmsTaKSession.setGlobal("list", vmsActions);
			vmsTaKSession.execute(event);
			event.setVmsActions(vmsActions);
		} else if (event.getType() == TypeDefinition.EVENT_TYPE_RM) {
			List<String> vmsActions = new LinkedList<String>();
			vmsRmKSession.setGlobal("list", vmsActions);
			vmsRmKSession.execute(event);
			event.setVmsActions(vmsActions);
		}

		List<String> fireActions = new LinkedList<String>();
		fireKSession.setGlobal("list", fireActions);
		fireKSession.execute(event);
		event.setFireActions(fireActions);

		List<String> hospitalActions = new LinkedList<String>();
		hospitalKSession.setGlobal("list", hospitalActions);
		hospitalKSession.execute(event);
		event.setHospitalActions(hospitalActions);

		List<String> policeActions = new LinkedList<String>();
		policeKSession.setGlobal("list", policeActions);
		policeKSession.execute(event);
		event.setPoliceActions(policeActions);

		Set<String> resourceActions = new LinkedHashSet<String>();
		resourceKSession.setGlobal("list", resourceActions);
		resourceKSession.execute(event);
		event.setResourceActions(resourceActions);

		List<String> roadActions = new LinkedList<String>();
		roadKSession.setGlobal("list", roadActions);
		roadKSession.execute(event);
		event.setRoadActions(roadActions);

		List<String> roadAdminActions = new LinkedList<String>();
		roadAdminKSession.setGlobal("list", roadAdminActions);
		roadAdminKSession.execute(event);
		event.setRoadAdminActions(roadAdminActions);
	}

	@Override
	public void generateSearchCondition(RoadEvent event) {
		List<String> cameraTypes = new LinkedList<String>(); // 搜索的摄像头类型
		Map<String, String> map = new HashMap<String, String>();
		List<String> tollgates = new LinkedList<String>(); // 需要搜索的收费站
		displayWallKSession.setGlobal("cameraTypes", cameraTypes);
		displayWallKSession.setGlobal("tollgates", tollgates);
		displayWallKSession.setGlobal("map", map);
		displayWallKSession.execute(event);
		if(cameraTypes.isEmpty() || map.isEmpty() || tollgates.isEmpty()){
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR, "rule config is not match this platform");
		}
		event.setCameraTypes(cameraTypes);
		event.setMap(map);
		event.setTollgates(tollgates);
	}
}
