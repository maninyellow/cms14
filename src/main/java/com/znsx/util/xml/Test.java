package com.znsx.util.xml;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jdom.Document;
import org.jdom.Element;

/**
 * Test
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:27:25
 */
public class Test {

	Connection conn;

	public Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String user = "cms";
		String password = "cms";
		String url = "jdbc:oracle:thin:@192.168.1.6:1521:znsx";
		conn = DriverManager.getConnection(url, user, password);

		return conn;
	}

	public static void main(String[] args) throws Exception {
		// new Test().getConnection();
		Document doc = new Document();
		Element root = new Element("Response");
		root.setAttribute("abc", null);
	}

}
