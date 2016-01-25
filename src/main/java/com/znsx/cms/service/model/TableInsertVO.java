package com.znsx.cms.service.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态创建表格的一些基本信息
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-3 上午11:06:39
 */
public class TableInsertVO {
	private String insertSql;
	/**
	 * DAS传递过来的某项数据，对应insert语句中的第几个参数,key是DAS传递的属性名称,
	 * value是该属性对应于Insert语句中的第几个问号参数
	 */
	private Map<String, Integer> paramPosition = new HashMap<String, Integer>();
	/**
	 * DAS传递过来的某项数据的值类型,key是DAS传递的属性名称,value是该属性对应的数据存储类型
	 */
	private Map<String, String> paramType = new HashMap<String, String>();
	/**
	 * 标准字段对应的数据存储的列名称,key是标准字段名称,value是数据存储的列名称
	 */
	private Map<String, String> standardMap = new HashMap<String, String>();
	/**
	 * 标准字段对应的DAS数据上报字段，key是标准字段名称,value是DAS上报时的属性名称
	 */
	private Map<String, String> standard2DASMap = new HashMap<String, String>();

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public Map<String, Integer> getParamPosition() {
		return paramPosition;
	}

	public void setParamPosition(Map<String, Integer> paramPosition) {
		this.paramPosition = paramPosition;
	}

	/**
	 * 获取DAS传递过来的某项数据的值类型缓存表,key是DAS传递的属性名称,value是该属性对应的数据存储类型
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-10 下午3:46:48
	 */
	public Map<String, String> getParamType() {
		return paramType;
	}

	public void setParamType(Map<String, String> paramType) {
		this.paramType = paramType;
	}

	/**
	 * 获取标准字段对应的数据存储的列名称的缓存表,key是标准字段名称,value是数据存储的列名称
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-10 下午3:45:18
	 */
	public Map<String, String> getStandardMap() {
		return standardMap;
	}

	public void setStandardMap(Map<String, String> standardMap) {
		this.standardMap = standardMap;
	}

	/**
	 * 获取标准字段对应的DAS上报属性名称的缓存表,key是标准字段名称,value是DAS上报的属性名称
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-26 下午3:55:41
	 */
	public Map<String, String> getStandard2DASMap() {
		return standard2DASMap;
	}

	public void setStandard2DASMap(Map<String, String> standard2dasMap) {
		standard2DASMap = standard2dasMap;
	}
}
