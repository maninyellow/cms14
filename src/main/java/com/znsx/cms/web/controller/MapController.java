package com.znsx.cms.web.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.znsx.cms.aop.annotation.InterfaceDescription;
import com.znsx.cms.persistent.model.DasGpsReal;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.DasDataManager;
import com.znsx.cms.service.iface.MapManager;
import com.znsx.cms.service.iface.OrganManager;
import com.znsx.cms.service.iface.RoadStatusManager;
import com.znsx.cms.service.iface.TmDeviceManager;
import com.znsx.cms.service.model.LightPolicyVO;
import com.znsx.cms.service.model.LightVO;
import com.znsx.cms.service.model.PlayItemVO;
import com.znsx.cms.service.model.PlaylistVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.TimePolicyItemVO;
import com.znsx.cms.service.model.TimePolicyVO;
import com.znsx.cms.web.dto.BaseDTO;
import com.znsx.cms.web.dto.sgc.VdFluxStatusDTO;
import com.znsx.util.request.SimpleRequestReader;
import com.znsx.util.xml.ElementUtil;

/**
 * 地图相关接口控制器
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-22 下午2:18:50
 */
@Controller
public class MapController extends BaseController {
	@Autowired
	private MapManager mapManager;
	@Autowired
	private DasDataManager dasDataManager;
	@Autowired
	private OrganManager organManager;
	@Autowired
	private TmDeviceManager tmDeviceManager;
	@Autowired
	private RoadStatusManager roadStatusManager;

	@InterfaceDescription(logon = true, method = "Save_Markers", cmd = "1204")
	@RequestMapping("/save_markers.xml")
	public void saveMakers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();
		Element marks = reqRoot.getChild("Markers");
		if (null == marks) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Markers]");
		}
		List<Element> list = marks.getChildren();
		mapManager.saveMarkers(list);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Save_Markers");
		dto.setCmd("1204");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Create_Playlist", cmd = "1205")
	@RequestMapping("/create_playlist.xml")
	public void createPlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();

		Element organId = reqRoot.getChild("OrganId");
		if (null == organId) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}
		if (StringUtils.isBlank(organId.getText())) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}

		Element playlist = reqRoot.getChild("Playlist");
		if (null == playlist) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Playlist]");
		}

		String name = playlist.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name]");
		}
		List<Element> items = playlist.getChildren();

		String id = mapManager.createPlaylist(organId.getText(), name, items);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Create_Playlist");
		dto.setCmd("1205");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element e = new Element("Playlist");
		e.setAttribute("Id", id.toString());
		root.addContent(e);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Playlist", cmd = "1206")
	@RequestMapping("/list_playlist.xml")
	public void listPlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String organId = reader.getString("organId", false);

		List<PlaylistVO> list = mapManager.listPlaylist(organId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Playlist");
		dto.setCmd("1206");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (PlaylistVO vo : list) {
			Element ple = ElementUtil.createElement("Playlist", vo, null, null);
			root.addContent(ple);

			List<PlayItemVO> itemVOs = vo.getItems();
			for (PlayItemVO itemVO : itemVOs) {
				Element ie = ElementUtil.createElement("Item", itemVO, null,
						null);
				ple.addContent(ie);
			}
		}
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Playlist", cmd = "1207")
	@RequestMapping("/delete_playlist.xml")
	public void deletePlaylist(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String playlistId = reader.getString("playlistId", false);

		mapManager.deletePlaylist(playlistId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Delete_Playlist");
		dto.setCmd("1207");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Light_Policy", cmd = "1208")
	@RequestMapping("/save_light_policy.xml")
	public void saveLightPolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();

		Element organElement = reqRoot.getChild("OrganId");
		if (null == organElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}
		if (StringUtils.isBlank(organElement.getText())) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}

		Element policyElement = reqRoot.getChild("LightPolicy");
		if (null == policyElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [LightPolicy]");
		}

		String id = policyElement.getAttributeValue("Id");

		String name = policyElement.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name]");
		}

		List<Element> lights = policyElement.getChildren();

		String policyId = mapManager.saveLightPolicy(organElement.getText(),
				name, id, lights);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Save_Light_Policy");
		dto.setCmd("1208");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element e = new Element("LightPolicy");
		e.setAttribute("Id", policyId.toString());
		root.addContent(e);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Light_Policy", cmd = "1209")
	@RequestMapping("/delete_light_policy.xml")
	public void deleteLightPolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("lightPolicyId", false);

		mapManager.deleteLightPolicy(id);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Delete_Light_Policy");
		dto.setCmd("1209");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Light_Policy", cmd = "1210")
	@RequestMapping("/list_light_policy.xml")
	public void listLightPolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String organId = reader.getString("organId", false);

		List<LightPolicyVO> list = mapManager.listLightPolicy(organId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Light_Policy");
		dto.setCmd("1210");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (LightPolicyVO vo : list) {
			Element lightPolicy = ElementUtil.createElement("LightPolicy", vo,
					null, null);
			root.addContent(lightPolicy);

			List<LightVO> lights = vo.getLights();
			for (LightVO light : lights) {
				Element e = ElementUtil.createElement("Light", light, null,
						null);
				lightPolicy.addContent(e);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Save_Time_Policy", cmd = "1211")
	@RequestMapping("/save_time_policy.xml")
	public void saveTimePolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();

		Element organElement = reqRoot.getChild("OrganId");
		if (null == organElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}
		if (StringUtils.isBlank(organElement.getText())) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [OrganId]");
		}

		Element policyElement = reqRoot.getChild("TimePolicy");
		if (null == policyElement) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [TimePolicy]");
		}

		String id = policyElement.getAttributeValue("Id");

		String name = policyElement.getAttributeValue("Name");
		if (StringUtils.isBlank(name)) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Name]");
		}

		List<Element> items = policyElement.getChildren();

		String policyId = mapManager.saveTimePolicy(organElement.getText(),
				name, id, items);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Save_Time_Policy");
		dto.setCmd("1211");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		Element e = new Element("TimePolicy");
		e.setAttribute("Id", policyId.toString());
		root.addContent(e);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Time_Policy", cmd = "1212")
	@RequestMapping("/list_time_policy.xml")
	public void listTimePolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String organId = reader.getString("organId", false);

		List<TimePolicyVO> list = mapManager.listTimePolicy(organId);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Time_Policy");
		dto.setCmd("1212");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (TimePolicyVO vo : list) {
			Element policy = ElementUtil.createElement("TimePolicy", vo, null,
					null);
			root.addContent(policy);

			List<TimePolicyItemVO> items = vo.getItems();
			for (TimePolicyItemVO item : items) {
				Element e = ElementUtil.createElement("Item", item, null, null);
				policy.addContent(e);
			}
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "Delete_Time_Policy", cmd = "1229")
	@RequestMapping("/delete_time_policy.xml")
	public void deleteTimePolicy(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String id = reader.getString("id", false);

		mapManager.deleteTimePolicy(id);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("Delete_Time_Policy");
		dto.setCmd("1229");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Device_Info", cmd = "1203")
	@RequestMapping("/list_device_info.xml")
	public void listDeviceInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();

		Element deviceList = reqRoot.getChild("DeviceList");
		if (null == deviceList) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [DeviceList]");
		}

		List<Element> devices = deviceList.getChildren();

		List<Element> list = dasDataManager.listDeviceInfo1(devices);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Device_Info");
		dto.setCmd("1203");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		for (Element e : list) {
			root.addContent(e);
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "CMS_Publish_Log", cmd = "1221")
	@RequestMapping("/cms_publish_log.xml")
	public void cmsPublishLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document reqDoc = (Document) request.getAttribute("RequestDocument");
		Element reqRoot = reqDoc.getRootElement();

		Element cmsList = reqRoot.getChild("CmsList");
		if (null == cmsList) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [CmsList]");
		}

		List<Element> cmss = cmsList.getChildren();
		List<String> cmsSNs = new LinkedList<String>();
		for (Element cms : cmss) {
			cmsSNs.add(cms.getAttributeValue("StandardNumber"));
		}

		Element playlist = reqRoot.getChild("Playlist");

		List<Element> items = playlist.getChildren();
		if (null == items || items.size() == 0) {
			throw new BusinessException(ErrorCode.PARAMETER_NOT_FOUND,
					"missing [Item]");
		}

		ResourceVO user = resource.get();
		Organ organ = organManager.getOrganById(user.getOrganId());

		mapManager.cmsPublishLog(cmsSNs, organ.getId(), organ.getName(),
				user.getId(), user.getName(), items);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("CMS_Publish_Log");
		dto.setCmd("1221");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "Road_Status", cmd = "1258")
	@RequestMapping("/road_status.xml")
	public void roadStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Namespace ns = Namespace.getNamespace("http://www.opengis.net/sld");
		Namespace ogc = Namespace.getNamespace("ogc",
				"http://www.opengis.net/ogc");
		Namespace xlink = Namespace.getNamespace("xlink",
				"http://www.w3.org/1999/xlink");
		Namespace xsi = Namespace.getNamespace("xsi",
				"http://www.w3.org/2001/XMLSchema-instance");

		Document doc = new Document();
		Element root = new Element("StyledLayerDescriptor");
		doc.setRootElement(root);
		root.setAttribute("version", "1.0.0");
		root.setAttribute("schemaLocation",
				"http://www.opengis.net/sld StyledLayerDescriptor.xsd", xsi);
		root.addNamespaceDeclaration(ogc);
		root.addNamespaceDeclaration(xlink);
		root.addNamespaceDeclaration(xsi);

		Element namedLayer = new Element("NamedLayer");
		root.addContent(namedLayer);

		Element name = new Element("Name");
		name.setText("hn_postgis:db_motorway_line");
		namedLayer.addContent(name);

		Element userStyle = new Element("UserStyle");
		namedLayer.addContent(userStyle);

		Element title = new Element("Title");
		userStyle.addContent(title);

		Element featureTypeStyle = new Element("FeatureTypeStyle");
		userStyle.addContent(featureTypeStyle);

		Element featureTypeName = new Element("FeatureTypeName");
		featureTypeName.setText("Feature");
		featureTypeStyle.addContent(featureTypeName);

		Element semanticTypeIdentifier1 = new Element("SemanticTypeIdentifier");
		semanticTypeIdentifier1.setText("generic:geometry");
		featureTypeStyle.addContent(semanticTypeIdentifier1);

		Element semanticTypeIdentifier2 = new Element("SemanticTypeIdentifier");
		semanticTypeIdentifier2.setText("simple");
		featureTypeStyle.addContent(semanticTypeIdentifier2);

		Element rule1 = new Element("Rule");
		featureTypeStyle.addContent(rule1);

		Element rule1Name = new Element("Name");
		rule1Name.setText("ruleg1");
		rule1.addContent(rule1Name);

		Element rule1MinScaleDenominator = new Element("MinScaleDenominator");
		rule1MinScaleDenominator.setText("1000000.0");
		rule1.addContent(rule1MinScaleDenominator);

		Element rule1LineSymbolizer = new Element("LineSymbolizer");
		rule1.addContent(rule1LineSymbolizer);

		Element rule1Stroke = new Element("Stroke");
		rule1LineSymbolizer.addContent(rule1Stroke);

		Element rule1CssParameter1 = new Element("CssParameter");
		rule1Stroke.addContent(rule1CssParameter1);
		rule1CssParameter1.setAttribute("name", "stroke");
		rule1CssParameter1.setText("#00FF00");

		Element rule1CssParameter2 = new Element("CssParameter");
		rule1Stroke.addContent(rule1CssParameter2);
		rule1CssParameter2.setAttribute("name", "stroke-width");
		rule1CssParameter2.setText("5.0");

		Element rule2 = new Element("Rule");
		featureTypeStyle.addContent(rule2);

		Element rule2Name = new Element("Name");
		rule2Name.setText("ruleg2");
		rule2.addContent(rule2Name);

		Element rule2MaxScaleDenominator = new Element("MaxScaleDenominator");
		rule2MaxScaleDenominator.setText("1000000.0");
		rule2.addContent(rule2MaxScaleDenominator);

		Element rule2LineSymbolizer = new Element("LineSymbolizer");
		rule2.addContent(rule2LineSymbolizer);

		Element rule2Stroke = new Element("Stroke");
		rule2LineSymbolizer.addContent(rule2Stroke);

		Element rule2CssParameter1 = new Element("CssParameter");
		rule2Stroke.addContent(rule2CssParameter1);
		rule2CssParameter1.setAttribute("name", "stroke");
		rule2CssParameter1.setText("#00FF00");

		Element rule2CssParameter2 = new Element("CssParameter");
		rule2Stroke.addContent(rule2CssParameter2);
		rule2CssParameter2.setAttribute("name", "stroke-width");
		rule2CssParameter2.setText("5.0");

		Element rule2TextSymbolizer = new Element("TextSymbolizer");
		rule2.addContent(rule2TextSymbolizer);

		Element rule2Label = new Element("Label");
		rule2TextSymbolizer.addContent(rule2Label);

		Element rule2PropertyName = new Element("PropertyName", ogc);
		rule2Label.addContent(rule2PropertyName);
		rule2PropertyName.setText("name");

		Element rule2Font = new Element("Font");
		rule2TextSymbolizer.addContent(rule2Font);

		Element rule2CssParameter3 = new Element("CssParameter");
		rule2Font.addContent(rule2CssParameter3);
		rule2CssParameter3.setAttribute("name", "font-family");
		rule2CssParameter3.setText("SimSun");

		Element rule2CssParameter4 = new Element("CssParameter");
		rule2Font.addContent(rule2CssParameter4);
		rule2CssParameter4.setAttribute("name", "font-size");
		rule2CssParameter4.setText("12.0");

		Element rule2CssParameter5 = new Element("CssParameter");
		rule2Font.addContent(rule2CssParameter5);
		rule2CssParameter5.setAttribute("name", "font-style");
		rule2CssParameter5.setText("normal");

		Element rule2CssParameter6 = new Element("CssParameter");
		rule2Font.addContent(rule2CssParameter6);
		rule2CssParameter6.setAttribute("name", "font-weight");
		rule2CssParameter6.setText("normal");

		Element rule2LabelPlacement = new Element("LabelPlacement");
		rule2TextSymbolizer.addContent(rule2LabelPlacement);

		Element rule2LinePlacement = new Element("LinePlacement");
		rule2LabelPlacement.addContent(rule2LinePlacement);

		Element rule2PerpendicularOffset = new Element("PerpendicularOffset");
		rule2LinePlacement.addContent(rule2PerpendicularOffset);
		rule2PerpendicularOffset.setText("10.0");

		Element rule2Fill = new Element("Fill");
		rule2TextSymbolizer.addContent(rule2Fill);

		Element rule2CssParameter7 = new Element("CssParameter");
		rule2Fill.addContent(rule2CssParameter7);
		rule2CssParameter7.setAttribute("name", "fill");
		rule2CssParameter7.setText("#000000");

		Element rule3 = new Element("Rule");
		featureTypeStyle.addContent(rule3);

		Element rule3Name = new Element("Name");
		rule3Name.setText("ruler1");
		rule3.addContent(rule3Name);

		Element rule3Filter = new Element("Filter", ogc);
		rule3.addContent(rule3Filter);

		Element rule3And = new Element("And", ogc);
		rule3Filter.addContent(rule3And);

		Element rule3PropertyIsGreaterThan = new Element(
				"PropertyIsGreaterThan", ogc);
		rule3And.addContent(rule3PropertyIsGreaterThan);

		Element rule3PropertyName1 = new Element("PropertyName", ogc);
		rule3PropertyIsGreaterThan.addContent(rule3PropertyName1);
		rule3PropertyName1.setText("length");

		Element rule3Literal1 = new Element("Literal", ogc);
		rule3PropertyIsGreaterThan.addContent(rule3Literal1);
		rule3Literal1.setText("0.8");

		Element rule3PropertyIsLessThan = new Element("PropertyIsLessThan", ogc);
		rule3And.addContent(rule3PropertyIsLessThan);

		Element rule3PropertyName2 = new Element("PropertyName", ogc);
		rule3PropertyIsLessThan.addContent(rule3PropertyName2);
		rule3PropertyName2.setText("length");

		Element rule3Literal2 = new Element("Literal", ogc);
		rule3PropertyIsLessThan.addContent(rule3Literal2);
		rule3Literal2.setText("1");

		Element rule3LineSymbolizer = new Element("LineSymbolizer");
		rule3.addContent(rule3LineSymbolizer);

		Element rule3Stroke = new Element("Stroke");
		rule3LineSymbolizer.addContent(rule3Stroke);

		Element rule3CssParameter1 = new Element("CssParameter");
		rule3Stroke.addContent(rule3CssParameter1);
		rule3CssParameter1.setAttribute("name", "stroke");
		rule3CssParameter1.setText("#FF0000");

		Element rule3CssParameter2 = new Element("CssParameter");
		rule3Stroke.addContent(rule3CssParameter2);
		rule3CssParameter2.setAttribute("name", "stroke-width");
		rule3CssParameter2.setText("5.0");

		Element rule4 = new Element("Rule");
		featureTypeStyle.addContent(rule4);

		Element rule4Name = new Element("Name");
		rule4.addContent(rule4Name);
		rule4Name.setText("ruley1");

		Element rule4Filter = new Element("Filter", ogc);
		rule4.addContent(rule4Filter);

		Element rule4And = new Element("And", ogc);
		rule4Filter.addContent(rule4And);

		Element rule4PropertyIsGreaterThan = new Element(
				"PropertyIsGreaterThan", ogc);
		rule4And.addContent(rule4PropertyIsGreaterThan);

		Element rule4PropertyName1 = new Element("PropertyName", ogc);
		rule4PropertyIsGreaterThan.addContent(rule4PropertyName1);
		rule4PropertyName1.setText("length");

		Element rule4Literal1 = new Element("Literal", ogc);
		rule4PropertyIsGreaterThan.addContent(rule4Literal1);
		rule4Literal1.setText("1.5");

		Element rule4PropertyIsLessThan = new Element("PropertyIsLessThan", ogc);
		rule4And.addContent(rule4PropertyIsLessThan);

		Element rule4PropertyName2 = new Element("PropertyName", ogc);
		rule4PropertyIsLessThan.addContent(rule4PropertyName2);
		rule4PropertyName2.setText("length");

		Element rule4Literal2 = new Element("Literal", ogc);
		rule4PropertyIsLessThan.addContent(rule4Literal2);
		rule4Literal2.setText("2");

		Element rule4LineSymbolizer = new Element("LineSymbolizer");
		rule4.addContent(rule4LineSymbolizer);

		Element rule4Stroke = new Element("Stroke");
		rule4LineSymbolizer.addContent(rule4Stroke);

		Element rule4CssParameter1 = new Element("CssParameter");
		rule4Stroke.addContent(rule4CssParameter1);
		rule4CssParameter1.setAttribute("name", "stroke");
		rule4CssParameter1.setText("#EDB903");

		Element rule4CssParameter2 = new Element("CssParameter");
		rule4Stroke.addContent(rule4CssParameter2);
		rule4CssParameter2.setAttribute("name", "stroke-width");
		rule4CssParameter2.setText("5.0");

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Cms_Log", cmd = "1238")
	@RequestMapping("/list_cms_log.xml")
	public void listCmsLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String cmsName = reader.getString("deviceName", true);
		cmsName = StringUtils.replace(cmsName, " ", "+");

		String userName = reader.getString("userName", true);

		Long beginTime = reader.getLong("beginTime", false);

		Long endTime = reader.getLong("endTime", false);

		Integer startIndex = reader.getInteger("startIndex", true);

		Integer limit = reader.getInteger("limit", true);

		String organId = resource.get().getOrganId();

		Integer conutTotal = mapManager.countTotalCMSLog(cmsName, userName,
				beginTime, endTime, organId);

		List<Element> list = mapManager.listCmsLog(organId, cmsName, userName,
				beginTime, endTime, startIndex, limit);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Cms_Log");
		dto.setCmd("1238");
		dto.setMessage(conutTotal + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		root.addContent(list);
		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = false, method = "List_Vd_Flux_Status", cmd = "1269")
	@RequestMapping("/listVdFluxStatus.json")
	public void listVdRealValue(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取全部车检器的最近采集值
		List<VehicleDetector> vds = tmDeviceManager.listAllVd();
		List<String> sns = new LinkedList<String>();
		for (VehicleDetector vd : vds) {
			sns.add(vd.getStandardNumber());
		}
		// 最近采集值查询
		List<DasVd> vdInfoList = dasDataManager.listVdInfo(sns);
		// 返回数据
		VdFluxStatusDTO dto = new VdFluxStatusDTO();
		List<VdFluxStatusDTO.VdFlux> vdFluxs = new LinkedList<VdFluxStatusDTO.VdFlux>();
		dto.setVdFluxs(vdFluxs);
		// 根据采集值判断所在路段车流量状况
		for (VehicleDetector vd : vds) {
			String type = vd.getOrgan().getType();
			// 默认通行能力为2000，对应设计时速为80km/h，车道数量为2
			int capacity = 2000;
			int roadLaneNumber = 2;
			// 如果是路段，得到路段的通行能力设计值
			if (TypeDefinition.ORGAN_TYPE_ROAD.equals(type)) {
				Integer c = ((OrganRoad) (vd.getOrgan())).getCapacity();
				if (null != c) {
					capacity = c.intValue();
				}
				Integer laneNumber = ((OrganRoad) (vd.getOrgan()))
						.getLaneNumber();
				if (null != laneNumber) {
					roadLaneNumber = laneNumber.intValue();
				}
			}
			capacity = roadStatusManager.calculateQc(capacity, roadLaneNumber,
					0);

			int upFlux = 0;
			int dwFlux = 0;
			// 获取实际采集车流量
			for (DasVd dasVd : vdInfoList) {
				if (dasVd.getStandardNumber().equals(vd.getStandardNumber())) {
					upFlux = dasVd.getUpFlux() != null ? dasVd.getUpFlux()
							.intValue() : 0;
					dwFlux = dasVd.getDwFlux() != null ? dasVd.getDwFlux()
							.intValue() : 0;
					// 根据采集间隔周期，计算目前的小时车流量
					int times = 3600 / vd.getPeriod().intValue();
					upFlux = upFlux * times;
					dwFlux = dwFlux * times;
					break;
				}
			}

			// 通行能力理论值和实际值比较得出道路状况
			VdFluxStatusDTO.VdFlux vdFlux = dto.new VdFlux();
			vdFlux.setId(vd.getId());
			vdFlux.setStandardNumber(vd.getStandardNumber());
			float upRatio = upFlux / capacity;
			float dwRatio = dwFlux / capacity;
			vdFlux.setUpRoadStatus(roadStatusManager.getRoadStatus(upRatio));
			vdFlux.setDwRoadStatus(roadStatusManager.getRoadStatus(dwRatio));
			vdFluxs.add(vdFlux);
		}

		writePage(response, dto);
	}

	@InterfaceDescription(logon = false, method = "List_Gps_Coordinate", cmd = "1276")
	@RequestMapping("/listGpsCoordinate.xml")
	public void listGpsCoordinate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<DasGpsReal> list = dasDataManager.listGpsInfo();

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Gps_Coordinate");
		dto.setCmd("1276");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);

		List<String> excludes = Arrays.asList("id", "reserve");

		for (DasGpsReal dgr : list) {
			root.addContent(ElementUtil.createElement("Device", dgr, null,
					excludes));
		}

		writePageWithContentLength(response, doc);
	}

	@InterfaceDescription(logon = true, method = "List_Cms_Publish_Log", cmd = "1321")
	@RequestMapping("/list_cms_publish_log.xml")
	public void listCmsPublishLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleRequestReader reader = new SimpleRequestReader(request);

		String cmsName = reader.getString("deviceName", true);
		cmsName = StringUtils.replace(cmsName, " ", "+");

		String userName = reader.getString("userName", true);

		Long beginTime = reader.getLong("beginTime", false);

		Long endTime = reader.getLong("endTime", false);

		Integer startIndex = reader.getInteger("startIndex", true);
		if (null == startIndex) {
			startIndex = Integer.valueOf(0);
		}

		Integer limit = reader.getInteger("limit", true);
		if (null == limit) {
			limit = Integer.valueOf(50);
		}

		String organId = resource.get().getOrganId();

		String[] organs = organManager.listOrganAllChildren(organId);

		String[] cmsSns = mapManager.listCmsSnsByName(organs, cmsName);

		int conutTotal = mapManager.countCmsPublishLog(organs, cmsSns,
				userName, beginTime, endTime);

		List<Element> list = mapManager.listCmsPublishLog(organs, cmsSns,
				userName, beginTime, endTime, startIndex, limit);

		BaseDTO dto = new BaseDTO();
		dto.setMethod("List_Cms_Publish_Log");
		dto.setCmd("1321");
		dto.setMessage(conutTotal + "");

		Document doc = new Document();
		Element root = ElementUtil.createElement("Response", dto, null, null);
		doc.setRootElement(root);
		if (list != null) {
			root.addContent(list);
		}
		writePageWithContentLength(response, doc);
	}
}
