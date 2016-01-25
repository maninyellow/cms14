package com.znsx.cms.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.iface.ReduceManager;
import com.znsx.util.file.Configuration;

/**
 * ReduceTask
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-13 上午10:47:15
 */
public class ReduceTask {
	@Autowired
	private ReduceManager reduceManager;

	public void reduceByHour() {
		String flag = Configuration.getInstance().loadProperties(
				"excute_reduce_task");
		if ("true".equals(flag)) {
			try {
				System.out.println("Begin reduce data by hour...");
				reduceManager.reduceVdByHour();
				reduceManager.reduceWstByHour();
				reduceManager.reduceRsdByHour();
				reduceManager.reduceCoviByHour();
				reduceManager.reduceLoliByHour();
				reduceManager.reduceNoByHour();
				System.out.println("End reduce data by hour!");
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void reduceByDay() {
		String flag = Configuration.getInstance().loadProperties(
				"excute_reduce_task");
		if ("true".equals(flag)) {
			try {
				System.out.println("Begin reduce data by day...");
				reduceManager.reduceVdByDay();
				reduceManager.reduceWstByDay();
				reduceManager.reduceRsdByDay();
				reduceManager.reduceCoviByDay();
				reduceManager.reduceLoliByDay();
				reduceManager.reduceNoByDay();
				System.out.println("End reduce data by day!");
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void reduceByMonth() {
		String flag = Configuration.getInstance().loadProperties(
				"excute_reduce_task");
		if ("true".equals(flag)) {
			try {
				System.out.println("Begin reduce data by month...");
				reduceManager.reduceVdByMonth();
				reduceManager.reduceWstByMonth();
				reduceManager.reduceRsdByMonth();
				reduceManager.reduceCoviByMonth();
				reduceManager.reduceLoliByMonth();
				reduceManager.reduceNoByMonth();
				System.out.println("End reduce data by month!");
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
