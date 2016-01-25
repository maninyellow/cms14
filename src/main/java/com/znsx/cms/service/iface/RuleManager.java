package com.znsx.cms.service.iface;

import com.znsx.cms.service.rule.model.Event;
import com.znsx.cms.service.rule.model.RoadEvent;

/**
 * RuleManager
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-25 下午8:56:38
 */
public interface RuleManager extends BaseManager {

	/**
	 * 根据事件已知条件，按照规则生成预案
	 * 
	 * @param event
	 *            事件
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 下午5:31:23
	 */
	public void generateScheme(Event event);

	/**
	 * 生成事件发生后的摄像头搜索条件
	 * 
	 * @author huangbuji
	 *         <p />
	 *         2014-11-30 下午3:43:02
	 */
	public void generateSearchCondition(RoadEvent event);
}
