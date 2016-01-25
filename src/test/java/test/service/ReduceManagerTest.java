package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.service.iface.ReduceManager;

/**
 * ReduceManagerTest
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-8-14 上午11:25:01
 */
public class ReduceManagerTest extends BaseTest {
	@Autowired
	private ReduceManager reduceManager;

	@Test
	public void reduceVdByHour() {
		reduceManager.reduceVdByHour();
	}
	
	@Test
	public void reduceVdByDay() {
		reduceManager.reduceVdByDay();
	}
	
	@Test
	public void reduceVdByMonth() {
		reduceManager.reduceVdByMonth();
	}
	
	@Test
	public void reduceWstByHour() {
		reduceManager.reduceWstByHour();
	}
	
	@Test
	public void reduceWstByDay() {
		reduceManager.reduceWstByDay();
	}
	
	@Test
	public void reduceWstByMonth() {
		reduceManager.reduceWstByMonth();
	}
	
	@Test
	public void reduceRsdByHour() {
		reduceManager.reduceRsdByHour();
	}
	
	@Test
	public void reduceRsdByDay() {
		reduceManager.reduceRsdByDay();
	}
	
	@Test
	public void reduceRsdByMonth() {
		reduceManager.reduceRsdByMonth();
	}
	
	@Test
	public void reduceCoviByHour() {
		reduceManager.reduceCoviByHour();
	}
	
	@Test
	public void reduceCoviByDay() {
		reduceManager.reduceCoviByDay();
	}
	
	@Test
	public void reduceCoviByMonth() {
		reduceManager.reduceCoviByMonth();
	}
	
	@Test
	public void reduceLoliByHour() {
		reduceManager.reduceLoliByHour();
	}
	
	@Test
	public void reduceLoliByDay() {
		reduceManager.reduceLoliByDay();
	}
	
	@Test
	public void reduceLoliByMonth() {
		reduceManager.reduceLoliByMonth();
	}
}
