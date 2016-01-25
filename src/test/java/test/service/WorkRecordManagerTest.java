package test.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.znsx.cms.persistent.model.WorkPlan;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.WorkRecordManager;

import test.BaseTest;

/**
 * 
 * @author huangbuji
 *         <p />
 *         2014-12-2 下午7:24:55
 */
public class WorkRecordManagerTest extends BaseTest {
	@Autowired
	private WorkRecordManager workRecordManager;
	
	@Test
	public void saveWorkPlan() throws Exception{
		File f = new File("E:/副本值班管理.xlsx");
		InputStream in = new FileInputStream(f);
		
		Workbook wb = WorkbookFactory.create(in);
		workRecordManager.saveWorkPlan(wb, "0");
	}
	
//	@Test
	public void getTodayWorkPlan() {
		WorkPlan wp = workRecordManager.getCurrentWorkPlan(TypeDefinition.WORK_PLAN_TYPE_POLICE);
		Assert.assertNotNull(wp);
	}
}
