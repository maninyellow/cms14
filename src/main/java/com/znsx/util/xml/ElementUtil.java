package com.znsx.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * xml Element工具
 * 
 * @author huangbuji
 *         <p />
 *         2012-8-15 上午10:53:10
 */
public class ElementUtil {

	// private static SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");
	private static final String format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 创建一个Element，所有的属性值按照原始obj对象包括父类对象的public get方法生成
	 * 
	 * @param name
	 *            要生成的Element名称
	 * @param obj
	 *            原始对象
	 * @param childProperties
	 *            需要生成为child element的属性
	 * @param excludeProperties
	 *            例外的属性名称
	 * @return
	 */
	public static Element createElement(String name, Object obj,
			List<String> childProperties, List<String> excludeProperties)
			throws Exception {
		Element element = new Element(name);
		// Field[] fields = obj.getClass().getDeclaredFields();
		//
		// for (Field field : fields) {
		// String fieldName = field.getName();
		// if (null != excludeProperties
		// && excludeProperties.contains(fieldName)) {
		// continue;
		// }
		// if (field.getType().getName().equals("java.util.List")) {
		// continue;
		// }
		// if (field.getType().getName().equals("java.util.Set")) {
		// continue;
		// }
		// String value = "";
		// try {
		// Method method = obj.getClass().getMethod(
		// convertGetMethod(fieldName));
		// Object vo = method.invoke(obj);
		// if (null != vo) {
		// value = vo.toString();
		// }
		// } catch (Exception e) {
		// continue;
		// }
		// element.setAttribute(firstCharToUpperCase(fieldName), value);
		// }
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// 只关注get方法和is开头的属性值获取相关方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				String key = getPropertyName(methodName);
				if (null != excludeProperties
						&& excludeProperties.contains(key.toLowerCase())) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.util.List")) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.util.Set")) {
					continue;
				}
				if (method.getReturnType().getName().equals("java.lang.Class")) {
					continue;
				}
				if (method.getReturnType().getName().startsWith("com.znsx.cms")) {
					continue;
				}
				String value = "";
				try {
					Object o = method.invoke(obj);
					if (null != o) {
						if (o instanceof Date) {
							value = new SimpleDateFormat(format).format(o);
						} else {
							value = o.toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if (null != childProperties
						&& childProperties.contains(key.toLowerCase())) {
					Element child = new Element(key);
					child.setText(value);
					element.addContent(child);
				} else {
					element.setAttribute(key, value);
				}
			}
		}

		return element;
	}

	/**
	 * 创建一个Element，所有的属性值按照原始obj对象包括父类对象的public get方法生成
	 * 
	 * @param name
	 *            要生成的Element名称
	 * @param obj
	 *            原始对象
	 * @return
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-28 下午3:23:09
	 */
	public static Element createElement(String name, Object obj)
			throws Exception {
		return ElementUtil.createElement(name, obj, null, null);
	}

	/**
	 * 根据对象属性名称获得对应的get方法
	 * 
	 * @param fieldName
	 *            对象属性名称
	 * @return
	 */
	public static String convertGetMethod(String fieldName) {
		return "get" + firstCharToUpperCase(fieldName);
	}

	/**
	 * 根据get或is开头的方法，获取属性的名称，第一个字母大写
	 * 
	 * @param methodName
	 *            get或is开头的属性获取方法
	 * @return 第一个字母大写的属性名称
	 */
	public static String getPropertyName(String methodName) {
		if (methodName.startsWith("get")) {
			return methodName.substring(3);
		} else if (methodName.startsWith("is")) {
			return methodName.substring(2);
		} else {
			return methodName;
		}
	}

	/**
	 * 将名称的第一个字母大写，如：name转换为Name
	 * 
	 * @param name
	 *            要转换的名称
	 * @return
	 */
	public static String firstCharToUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * XML转换为UTF-8字符串
	 * 
	 * @param doc
	 *            XML对象
	 * @return XML的字符串
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-22 上午11:18:54
	 */
	public static String doc2String(Document doc) {
		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		return out.outputString(doc);
	}

	public static Integer getInteger(Element e, String name)
			throws BusinessException {
		String value = e.getAttributeValue(name);
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException e1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"parameter " + name + "[" + value + "] invalid !");
			}
		}
	}

	public static Short getShort(Element e, String name)
			throws BusinessException {
		String value = e.getAttributeValue(name);
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				return Short.valueOf(value);
			} catch (NumberFormatException e1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"parameter " + name + "[" + value + "] invalid !");
			}
		}
	}

	public static Long getLong(Element e, String name) throws BusinessException {
		String value = e.getAttributeValue(name);
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				return Long.valueOf(value);
			} catch (NumberFormatException e1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"parameter " + name + "[" + value + "] invalid !");
			}
		}
	}

	public static Float getFloat(Element e, String name)
			throws BusinessException {
		String value = e.getAttributeValue(name);
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				return Float.valueOf(value);
			} catch (NumberFormatException e1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"parameter " + name + "[" + value + "] invalid !");
			}
		}
	}

	public static Timestamp getTimestamp(Element e, String name)
			throws BusinessException {
		String value = e.getAttributeValue(name);
		if (StringUtils.isBlank(value)) {
			return new Timestamp(System.currentTimeMillis());
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Date date = sdf.parse(value);
				return new Timestamp(date.getTime());
			} catch (ParseException e1) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"parameter " + name + "[" + value + "] invalid !");
			}
		}
	}

	/**
	 * 调用该方法会导致传入Element参数脱离自身父节点，即会改变原来的Document内容
	 * 
	 * @param e
	 *            需要转换为String的元素
	 * @author huangbuji
	 *         <p />
	 *         2014-11-29 下午1:40:51
	 */
	public static String element2String(Element e) {
		Content c = e.detach();
		Document doc = new Document();
		doc.setRootElement((Element) c);

		Format format = Format.getRawFormat();
		format.setEncoding("UTF-8");
		format.setIndent("  ");
		XMLOutputter out = new XMLOutputter(format);
		return out.outputString(doc);
	}

	public static Document string2Doc(String xml) {
		Document doc = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			InputStream in = new ByteArrayInputStream(xml.getBytes("utf8"));
			doc = builder.build(in);
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(ErrorCode.ENCODING_ERROR,
					"encode[utf8] not support !");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, xml
					+ " is not xml !");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		}
		return doc;
	}

	public static void main(String[] args) throws Exception {
		// BaseDTO dto = new BaseDTO();
		// dto.setMessage("testss");
		// dto.setCmd("2343211");
		// XMLOutputter out = new XMLOutputter();
		//
		// List<String> excludeProperties = new ArrayList<String>();
		// excludeProperties.add("name");
		// excludeProperties.add("message");
		//
		// List<String> childProperties = new ArrayList<String>();
		// childProperties.add("cmd");
		//
		// Element root = createElement("Organ", dto, childProperties,
		// excludeProperties);
		// Document doc = new Document(root);
		// System.out.println(doc2String(doc));
		//
		// Document d = new Document();
		// Element r1 = new Element("PlayScheme");
		// d.setRootElement(r1);
		// Element c1 = new Element("Item");
		// r1.addContent(c1);
		// c1.setAttribute("SN", "123");
		//
		// String e = element2String(c1);
		//
		// SAXBuilder builder = new SAXBuilder();
		// Document d2 = new Document();
		// Element r2 = new Element("PlayScheme");
		// d2.setRootElement(r2);
		//
		// InputStream in = new ByteArrayInputStream(e.getBytes("utf8"));
		// Document temp = builder.build(in);
		// Element e1 = (Element) temp.getRootElement().detach();
		//
		// r2.addContent(e1);
		//
		// System.out.println(doc2String(d2));
	}
}
