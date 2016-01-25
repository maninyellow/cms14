package test.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import test.BaseTest;

import com.znsx.cms.persistent.model.SubPlatformResource;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.iface.ConnectManager;
import com.znsx.util.xml.ElementUtil;

/**
 * 平台互联业务接口单元测试类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-16 下午5:12:47
 */
public class ConnectManagerTest extends BaseTest {
	@Autowired
	private ConnectManager connectManager;

	private Collection<SubPlatformResource> items;

//	@Test(priority = 9)
	public void listSubPlatformResource() {
		this.items = connectManager.listPlatformResource("ff8080814c6a4dd4014c6e6613f20007",
				"10020000000000000000");
	}

//	 @Test(priority = 10)
	public void treeSubPlatform() {
		Element tree = connectManager.treeSubPlatform("ff8080814c6a4dd4014c6e6613f20007",
				new ArrayList<SubPlatformResource>(items), true);
		Document doc = new Document();
		doc.setRootElement(tree);

		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		String body = out.outputString(doc);
		System.out.println(body);
	}

	// @Test
	public void pushResource() {
		connectManager.pushResource("http://192.168.1.111:8080/cms/");
	}

	// @Test
	public void saveReportData() {
		Element row = new Element("Row");

		Element vDID = new Element("VDID");
		vDID.setText("0100000000000001");
		row.addContent(vDID);

		Element recTime = new Element("RecTime");
		recTime.setText("20140117134500");
		row.addContent(recTime);

		Element upFluxB = new Element("UupFluxB");
		upFluxB.setText("50");
		row.addContent(upFluxB);

		Element upFluxS = new Element("UupFluxS");
		upFluxS.setText("40");
		row.addContent(upFluxS);

		Element upFlux = new Element("UupFlux");
		upFlux.setText("90");
		row.addContent(upFlux);

		Element dwFluxB = new Element("DwFluxB");
		dwFluxB.setText("40");
		row.addContent(dwFluxB);

		Element dwFluxS = new Element("DwFluxS");
		dwFluxS.setText("30");
		row.addContent(dwFluxS);

		Element dwFlux = new Element("DwFlux");
		dwFlux.setText("70");
		row.addContent(dwFlux);

		Element upSpeed = new Element("UpSpeed");
		upSpeed.setText("90");
		row.addContent(upSpeed);

		Element dwSpeed = new Element("DwSpeed");
		dwSpeed.setText("85");
		row.addContent(dwSpeed);

		Element upOccup = new Element("UpOccup");
		upOccup.setText("70.00");
		row.addContent(upOccup);

		Element dwOccdown = new Element("DwOccdown");
		dwOccdown.setText("50.00");
		row.addContent(dwOccdown);

		Element laneNum = new Element("LaneNum");
		laneNum.setText("4");
		row.addContent(laneNum);

		Element status = new Element("Status");
		status.setText("0");
		row.addContent(status);

		Element commStatus = new Element("CommStatus");
		commStatus.setText("0");
		row.addContent(commStatus);

		Element reserve = new Element("Reserve");
		reserve.setText("");
		row.addContent(reserve);

		List<Element> rows = new LinkedList<Element>();
		rows.add(row);
		String organSN = "12345678901234567890";

		connectManager.saveReportData(rows, organSN,
				TypeDefinition.TABLE_NAME_VD);
	}
	
//	@Test
	public void catalog28181ByUser() {
		Element deviceList = connectManager.catalog28181ByUser("251000000090000145");
		System.out.println(ElementUtil.element2String(deviceList));
		System.out.println(deviceList.getChildren().size());
	}
}
