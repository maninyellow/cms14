package com.znsx.cms.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.GisDAO;
import com.znsx.cms.persistent.model.Gis;
import com.znsx.cms.service.comparator.RoadAdminComparator;
import com.znsx.cms.service.comparator.TollgateComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.WebGisManager;
import com.znsx.cms.service.model.WfsRoadAdminVO;
import com.znsx.cms.service.model.WfsTollgateVO;
import com.znsx.util.file.Configuration;

/**
 * WebGis接口调用Manager实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-14 下午2:04:58
 */
public class WebGisManagerImpl extends BaseManagerImpl implements WebGisManager {
	@Autowired
	private GisDAO gisDAO;

	@Override
	public List<WfsRoadAdminVO> wfsListRoadAdmin(String wfsUrl) {
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(wfsUrl);

		StringBuffer body = new StringBuffer();
		body.append("<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"");
		body.append("  xmlns:wfs=\"http://www.opengis.net/wfs\"");
		body.append("  xmlns:ogc=\"http://www.opengis.net/ogc\"");
		body.append("  xmlns:gml=\"http://www.opengis.net/gml\"");
		body.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		body.append("  xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\">");
		body.append("<wfs:Query typeName=\"hn_postgis:db_luzhen_point\" srsName=\"EPSG:900913\">");
		body.append("</wfs:Query>");
		body.append("</wfs:GetFeature>");

		try {
			RequestEntity entity = new StringRequestEntity(body.toString(),
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			Element root = doc.getRootElement();
			// GIS数据源变化，调整下面解析代码
			Namespace nsLayers = root.getNamespace("hn_postgis");
			Namespace nsGml = root.getNamespace("gml");

			Element featuresElement = root.getChild("featureMembers",
					root.getNamespace("gml"));
			List<Element> features = featuresElement.getChildren();
			List<WfsRoadAdminVO> list = new LinkedList<WfsRoadAdminVO>();
			for (Element feature : features) {
				WfsRoadAdminVO vo = new WfsRoadAdminVO();
				vo.setId(feature.getChildText("gid", nsLayers));
				vo.setName(feature.getChildText("name", nsGml));
				Element geom = feature.getChild("geom", nsLayers);
				Element point = geom.getChild("Point", nsGml);
				String pos = point.getChildText("pos", nsGml);
				vo.setLongitude(extractLongitude(pos));
				vo.setLatitude(extractLatitude(pos));
				list.add(vo);
			}
			Collections.sort(list, new RoadAdminComparator());
			return list;
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			throw new BusinessException(ErrorCode.URL_ERROR, "Connect to "
					+ wfsUrl + " failed !");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Connect to " + wfsUrl + " failed !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	private String extractLongitude(String pos) throws BusinessException {
		BigDecimal longitude = null;
		try {
			String[] lonlat = pos.split(" ");
			if (lonlat.length != 2) {
				throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
						"position[" + pos + "] parse error !");
			}
			longitude = new BigDecimal(lonlat[0]);
		} catch (NumberFormatException e) {
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR, "position["
					+ pos + "] parse error !");
		}
		return longitude.toPlainString();
	}

	private String extractLatitude(String pos) throws BusinessException {
		BigDecimal latitude = null;
		try {
			String[] lonlat = pos.split(" ");
			if (lonlat.length != 2) {
				throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
						"position[" + pos + "] parse error !");
			}
			latitude = new BigDecimal(lonlat[1]);
		} catch (NumberFormatException e) {
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR, "position["
					+ pos + "] parse error !");
		}
		return latitude.toPlainString();
	}

	public List<WfsRoadAdminVO> wfsListHospital(String wfsUrl) {
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(wfsUrl);

		StringBuffer body = new StringBuffer();
		body.append("<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"");
		body.append("  xmlns:wfs=\"http://www.opengis.net/wfs\"");
		body.append("  xmlns:ogc=\"http://www.opengis.net/ogc\"");
		body.append("  xmlns:gml=\"http://www.opengis.net/gml\"");
		body.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		body.append("  xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\">");
		body.append("<wfs:Query typeName=\"hn_postgis:db_hospital_point\" srsName=\"EPSG:900913\">");
		body.append("<ogc:Filter>");
		body.append("<ogc:PropertyIsEqualTo>");
		body.append("<ogc:PropertyName>kind</ogc:PropertyName>");
		body.append("<ogc:Literal>7200</ogc:Literal>");
		body.append("</ogc:PropertyIsEqualTo>");
		body.append("</ogc:Filter>");
		body.append("</wfs:Query>");
		body.append("</wfs:GetFeature>");

		try {
			RequestEntity entity = new StringRequestEntity(body.toString(),
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			Element root = doc.getRootElement();
			// GIS数据源变化，调整下面解析代码
			Namespace nsLayers = root.getNamespace("hn_postgis");
			Namespace nsGml = root.getNamespace("gml");

			Element featuresElement = root.getChild("featureMembers",
					root.getNamespace("gml"));
			List<Element> features = featuresElement.getChildren();
			List<WfsRoadAdminVO> list = new LinkedList<WfsRoadAdminVO>();
			for (Element feature : features) {
				WfsRoadAdminVO vo = new WfsRoadAdminVO();
				vo.setId(feature.getChildText("gid", nsLayers));
				vo.setName(feature.getChildText("name", nsGml));
				Element geom = feature.getChild("geom", nsLayers);
				Element point = geom.getChild("Point", nsGml);
				String pos = point.getChildText("pos", nsGml);
				vo.setLongitude(extractLongitude(pos));
				vo.setLatitude(extractLatitude(pos));
				list.add(vo);
			}
			Collections.sort(list, new RoadAdminComparator());
			return list;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Connect to " + wfsUrl + " failed !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public List<WfsRoadAdminVO> wfsListFire(String wfsUrl) {
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(wfsUrl);

		StringBuffer body = new StringBuffer();
		body.append("<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"");
		body.append("  xmlns:wfs=\"http://www.opengis.net/wfs\"");
		body.append("  xmlns:ogc=\"http://www.opengis.net/ogc\"");
		body.append("  xmlns:gml=\"http://www.opengis.net/gml\"");
		body.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		body.append("  xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\">");
		body.append("<wfs:Query typeName=\"hn_postgis:firefighting_point\" srsName=\"EPSG:900913\">");
		// body.append("<wfs:Query typeName=\"hn_postgis:db_luzhen_point\" srsName=\"EPSG:900913\">");
		body.append("</wfs:Query>");
		body.append("</wfs:GetFeature>");

		try {
			RequestEntity entity = new StringRequestEntity(body.toString(),
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			Element root = doc.getRootElement();
			// GIS数据源变化，调整下面解析代码
			Namespace nsLayers = root.getNamespace("hn_postgis");
			Namespace nsGml = root.getNamespace("gml");

			Element featuresElement = root.getChild("featureMembers",
					root.getNamespace("gml"));
			List<Element> features = featuresElement.getChildren();
			List<WfsRoadAdminVO> list = new LinkedList<WfsRoadAdminVO>();
			for (Element feature : features) {
				WfsRoadAdminVO vo = new WfsRoadAdminVO();
				vo.setId(feature.getChildText("gid", nsLayers));
				vo.setName(feature.getChildText("name", nsGml));
				Element geom = feature.getChild("geom", nsLayers);
				Element point = geom.getChild("Point", nsGml);
				String pos = point.getChildText("pos", nsGml);
				vo.setLongitude(extractLongitude(pos));
				vo.setLatitude(extractLatitude(pos));
				list.add(vo);
			}
			Collections.sort(list, new RoadAdminComparator());
			return list;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Connect to " + wfsUrl + " failed !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public List<WfsRoadAdminVO> wfsListPolice(String wfsUrl) {
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(wfsUrl);

		StringBuffer body = new StringBuffer();
		body.append("<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"");
		body.append("  xmlns:wfs=\"http://www.opengis.net/wfs\"");
		body.append("  xmlns:ogc=\"http://www.opengis.net/ogc\"");
		body.append("  xmlns:gml=\"http://www.opengis.net/gml\"");
		body.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		body.append("  xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\">");
		body.append("<wfs:Query typeName=\"hn_postgis:trafficpolice_point\" srsName=\"EPSG:900913\">");
		// body.append("<wfs:Query typeName=\"hn_postgis:db_luzhen_point\" srsName=\"EPSG:900913\">");
		body.append("</wfs:Query>");
		body.append("</wfs:GetFeature>");

		try {
			RequestEntity entity = new StringRequestEntity(body.toString(),
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			Element root = doc.getRootElement();
			// GIS数据源变化，调整下面解析代码
			Namespace nsLayers = root.getNamespace("hn_postgis");
			Namespace nsGml = root.getNamespace("gml");

			Element featuresElement = root.getChild("featureMembers",
					root.getNamespace("gml"));
			List<Element> features = featuresElement.getChildren();
			List<WfsRoadAdminVO> list = new LinkedList<WfsRoadAdminVO>();
			for (Element feature : features) {
				WfsRoadAdminVO vo = new WfsRoadAdminVO();
				vo.setId(feature.getChildText("gid", nsLayers));
				vo.setName(feature.getChildText("name", nsGml));
				Element geom = feature.getChild("geom", nsLayers);
				Element point = geom.getChild("Point", nsGml);
				String pos = point.getChildText("pos", nsGml);
				vo.setLongitude(extractLongitude(pos));
				vo.setLatitude(extractLatitude(pos));
				list.add(vo);
			}
			Collections.sort(list, new RoadAdminComparator());
			return list;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Connect to " + wfsUrl + " failed !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public List<WfsTollgateVO> wfsGetTollgate() {
		String wfsUrl = getWfsUrl();
		HttpClient client = new HttpClient(new HttpClientParams(),
				new SimpleHttpConnectionManager(true));
		PostMethod method = new PostMethod(wfsUrl);

		StringBuffer body = new StringBuffer();
		body.append("<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"");
		body.append("  xmlns:wfs=\"http://www.opengis.net/wfs\"");
		body.append("  xmlns:ogc=\"http://www.opengis.net/ogc\"");
		body.append("  xmlns:gml=\"http://www.opengis.net/gml\"");
		body.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		body.append("  xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\">");
		body.append("<wfs:Query typeName=\"hn_postgis:tollstation_point\" srsName=\"EPSG:900913\">");
		body.append("</wfs:Query>");
		body.append("</wfs:GetFeature>");

		try {
			RequestEntity entity = new StringRequestEntity(body.toString(),
					"application/xml", "utf8");
			method.setRequestEntity(entity);
			client.executeMethod(method);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(method.getResponseBodyAsStream());
			Element root = doc.getRootElement();
			// GIS数据源变化，调整下面解析代码
			Namespace nsLayers = root.getNamespace("hn_postgis");
			Namespace nsGml = root.getNamespace("gml");

			Element featuresElement = root.getChild("featureMembers",
					root.getNamespace("gml"));
			List<Element> features = featuresElement.getChildren();
			List<WfsTollgateVO> list = new LinkedList<WfsTollgateVO>();
			for (Element feature : features) {
				String name = feature.getChildText("name", nsGml);
				if (name.equals("雨花收费站") || name.equals("李家塘收费站")
						|| name.equals("马家河收费站") || name.equals("长沙收费站")
						|| name.equals("三一收费站") || name.equals("黄花收费站")
						|| name.equals("永安收费站")) {
					WfsTollgateVO vo = new WfsTollgateVO();
					vo.setId(feature.getChildText("gid", nsLayers));
					vo.setName(feature.getChildText("name", nsGml));
					Element geom = feature.getChild("geom", nsLayers);
					Element point = geom.getChild("Point", nsGml);
					String pos = point.getChildText("pos", nsGml);
					vo.setLongitude(extractLongitude(pos));
					vo.setLatitude(extractLatitude(pos));
					list.add(vo);
				}
			}
			Collections.sort(list, new TollgateComparator());
			return list;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Connect to " + wfsUrl + " failed !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public String getWfsUrl() {
		StringBuffer wfsUrl = new StringBuffer();
		List<Gis> gises = gisDAO.findAll();
		if (gises.size() > 0) {
			wfsUrl.append("http://");
			try {
				wfsUrl.append(gises.get(0).getWanIp());
				wfsUrl.append(":");
				wfsUrl.append(gises.get(0).getPort());
				wfsUrl.append("/geoserver/wfs");
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"wfs error");
			}
			return wfsUrl.toString();
		} else {
			return "";
		}

	}
}
