package com.znsx.cms.service.common;

/**
 * 标准对象类型编码,自动生成18位标准码的依据
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午9:10:18
 */
public class StandardObjectCode {
	// 设备类型码
	/**
	 * 数字视频编码设备(不带本地存储)
	 */
	public static final String DEVICE_DVS = "00";
	/**
	 * 数字视频编码设备(带本地存储)
	 */
	public static final String DEVICE_DVR = "01";
	/**
	 * 网络摄像机
	 */
	public static final String DEVICE_CAMERA = "03";
	/**
	 * 监控联网管理服务器
	 */
	public static final String DEVICE_CMS = "20";
	/**
	 * 视频代理服务器
	 */
	public static final String DEVICE_MMS = "21";
	/**
	 * Web接入服务器
	 */
	public static final String DEVICE_CCS = "22";
	/**
	 * 录像管理服务器
	 */
	public static final String DEVICE_CRS = "23";
	/**
	 * 协转服务器(自定义)
	 */
	public static final String DEVICE_PTS = "24";
	/**
	 * 数据采集服务器
	 */
	public static final String DEVICE_DAS = "25";
	/**
	 * 事件服务器
	 */
	public static final String DEVICE_ENS = "26";
	/**
	 * 应急单位
	 */
	public static final String DEVICE_UNIT = "27";
	/**
	 * 救援队伍
	 */
	public static final String DEVICE_TEAM = "28";
	/**
	 * 救援物资
	 */
	public static final String DEVICE_RESOURCE = "29";
	/**
	 * 救援车辆
	 */
	public static final String DEVICE_VEHICLE = "30";
	/**
	 * 救援人员
	 */
	public static final String DEVICE_RESOURCE_USER = "31";
	/**
	 * 太阳能电池
	 */
	public static final String DEVICE_SOLAR_BATTERY = "32";
	/**
	 * Gis服务器
	 */
	public static final String DEVICE_GIS = "33";
	/**
	 * 录像转发服务器
	 */
	public static final String DEVICE_RMS = "34";
	/**
	 * 录音服务器
	 */
	public static final String DEVICE_SRS = "35";
	/**
	 * 网络数字矩阵
	 */
	public static final String DEVICE_DWS = "40";
	/**
	 * 视频输出通道
	 */
	public static final String DEVICE_MONITOR = "43";

	// 角色类型码
	/**
	 * 系统管理员
	 */
	public static final String ROLE_ADMIN = "90";
	/**
	 * 子系统管理员
	 */
	public static final String DEVICE_SUB_ADMIN = "91";
	/**
	 * 高级用户
	 */
	public static final String ROLE_SENIOR = "92";
	/**
	 * 普通用户
	 */
	public static final String ROLE_GENERAL = "93";
	/**
	 * 浏览用户
	 */
	public static final String ROLE_LOOK = "94";
	/**
	 * 权限组用户
	 */
	public static final String ROLE_GROUP = "95";

	// 机构编码
	/**
	 * 机构编码固定为"00"，增加机构6位流水号来区分
	 */
	public static final String ORGAN_CODE = "00";
	/**
	 * 高速公路
	 */
	public static final String HIGH_SPEED_ROAD = "1";
	/**
	 * 干线普通公路
	 */
	public static final String GENERAL_ROAD = "2";
	/**
	 * 公路交通枢纽
	 */
	public static final String TRAFFIC_HUB = "3";
	/**
	 * 道路运输
	 */
	public static final String TRANSPORT_ROAD = "4";
	/**
	 * 水路运输
	 */
	public static final String TRANSPORT_WATER = "5";
	/**
	 * 城市交通
	 */
	public static final String CITY_TRAFFIC = "6";
	/**
	 * 行政管理单位
	 */
	public static final String MANAGE_DEPARTMENT = "0";

	/**
	 * 
	 */
	public static final String ERROR = "Error";

	/**
	 * 获取对象标准编码
	 * 
	 * @param 对象的名称
	 * @return 对象标准编码
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:51:52
	 */
	public static String getObjectCode(String className) {
		if ("Dvr".equals(className)) {
			return DEVICE_DVR;
		}
		if ("Camera".equals(className)) {
			return DEVICE_CAMERA;
		}
		if ("Ccs".equals(className)) {
			return DEVICE_CCS;
		}
		if ("Crs".equals(className)) {
			return DEVICE_CRS;
		}
		if ("Mss".equals(className)) {
			return DEVICE_MMS;
		}
		if ("Dws".equals(className)) {
			return DEVICE_DWS;
		}
		if ("Cms".equals(className)) {
			return DEVICE_CMS;
		}
		if ("Pts".equals(className)) {
			return DEVICE_PTS;
		}
		if ("User".equals(className)) {
			return ROLE_ADMIN;
		}
		if ("Organ".equals(className)) {
			return ORGAN_CODE;
		}
		if ("Monitor".equals(className)) {
			return DEVICE_MONITOR;
		}
		if ("Das".equals(className)) {
			return DEVICE_DAS;
		}
		if ("Ens".equals(className)) {
			return DEVICE_ENS;
		}
		if ("Unit".equals(className)) {
			return DEVICE_UNIT;
		}
		if ("Team".equals(className)) {
			return DEVICE_TEAM;
		}
		if ("Resource".equals(className)) {
			return DEVICE_RESOURCE;
		}
		if ("Vehicle".equals(className)) {
			return DEVICE_VEHICLE;
		}
		if ("ResourceUser".equals(className)) {
			return DEVICE_RESOURCE_USER;
		}
		if ("Gis".equals(className)) {
			return DEVICE_GIS;
		}
		if ("SolarBattery".equals(className)) {
			return DEVICE_SOLAR_BATTERY;
		}
		if ("Rms".equals(className)) {
			return DEVICE_RMS;
		}
		if ("Srs".equals(className)) {
			return DEVICE_SRS;
		}
		return "00";
	}

	public static String getClassName(String objectCode) {
		if (objectCode.equals(DEVICE_DVR)) {
			return "Dvr";
		}
		if (objectCode.equals(DEVICE_CAMERA)) {
			return "Camera";
		}
		if (objectCode.equals(DEVICE_CCS)) {
			return "Ccs";
		}
		if (objectCode.equals(DEVICE_CRS)) {
			return "Crs";
		}
		if (objectCode.equals(DEVICE_MMS)) {
			return "Mss";
		}
		if (objectCode.equals(DEVICE_DWS)) {
			return "Dws";
		}
		if (objectCode.equals(DEVICE_CMS)) {
			return "Cms";
		}
		if (objectCode.equals(DEVICE_PTS)) {
			return "Pts";
		}
		if (objectCode.equals(ROLE_ADMIN)) {
			return "User";
		}
		if (objectCode.equals(DEVICE_MONITOR)) {
			return "Monitor";
		}
		return ERROR;
	}
}
