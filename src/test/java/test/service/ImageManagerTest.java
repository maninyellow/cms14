package test.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import test.BaseTest;

import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.service.iface.ImageManager;

/**
 * 图片业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午8:16:30
 */
public class ImageManagerTest extends BaseTest {
	@Autowired
	private ImageManager imageManager;

	private String id;

	// @Test(dependsOnMethods = { "testCreate" })
	public void testUpload() throws Exception {
		File f = new File("D:/Desert.jpg");
		InputStream in = new FileInputStream(f);
		imageManager.upload(id, in);

	}

	// @Test(priority = 1)
	public void testCreate() {
		id = imageManager.createImage("Test", "10020000000000000000");
	}

	// @Test(dependsOnMethods = { "testUpload" })
	public void testGet() throws Exception {
		Image image = imageManager.getImage(id);
		long length = image.getContent().length();
		Assert.assertTrue(length > 10);
		File f = new File("D:/out.jpg");
		InputStream in = image.getContent().getBinaryStream();
		OutputStream out = new FileOutputStream(f);
		byte[] buffer = new byte[4096];
		int temp = 0;
		while ((temp = in.read(buffer)) > 0) {
			out.write(buffer, 0, temp);
		}
	}
}
