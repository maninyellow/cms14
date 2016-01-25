package com.znsx.util.xml;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * MyXMLOutputter
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-1 上午10:15:21
 */
public class MyXMLOutputter extends XMLOutputter {
	
	public MyXMLOutputter() {
		super();
	}
	
	public MyXMLOutputter(Format format) {
		super(format);
	}
	
	public MyXMLOutputter(XMLOutputter that) {
		super(that);
	}
	
	
	/**
	 * 重写'<','>'转译方法，不需要转译这些特殊字符
	 */
	@Override
	public String escapeElementEntities(String str) {
		return str;
	}
}
