package test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * BaseTest(类说明)
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:34:02
 */
@ContextConfiguration(locations = { "/hibernate-config.xml",
		"/applicationContext-dao.xml", "/applicationContext-service.xml", "/drools-context.xml"})
public class BaseTest extends AbstractTestNGSpringContextTests {

}
