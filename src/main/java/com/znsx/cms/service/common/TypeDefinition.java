package com.znsx.cms.service.common;

import org.antlr.grammar.v3.ANTLRv3Parser.finallyClause_return;

/**
 * 平台类型字典定义
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午5:01:36
 */
public class TypeDefinition {
	/**
	 * 视频服务器
	 */
	public static final int DEVICE_TYPE_DVR = 1;
	/**
	 * 摄像头
	 */
	public static final int DEVICE_TYPE_CAMERA = 2;
	/**
	 * 车辆检测器
	 */
	public static final int DEVICE_TYPE_VD = 10;
	/**
	 * 风速风向检测器
	 */
	public static final int DEVICE_TYPE_WS = 11;
	/**
	 * 气象检测器
	 */
	public static final int DEVICE_TYPE_WST = 12;
	/**
	 * 光强检测器
	 */
	public static final int DEVICE_TYPE_LOLI = 13;
	/**
	 * 火灾检测器
	 */
	public static final int DEVICE_TYPE_FD = 14;
	/**
	 * 一氧化碳/能见度检测器
	 */
	public static final int DEVICE_TYPE_COVI = 15;
	/**
	 * 氮氧化物检测器
	 */
	public static final int DEVICE_TYPE_NOD = 16;
	/**
	 * 可变信息标志
	 */
	public static final int DEVICE_TYPE_CMS = 17;
	/**
	 * 风机
	 */
	public static final int DEVICE_TYPE_FAN = 18;
	/**
	 * 照明回路
	 */
	public static final int DEVICE_TYPE_LIGHT = 19;
	/**
	 * 防火卷帘门
	 */
	public static final int DEVICE_TYPE_RD = 20;
	/**
	 * 水泵
	 */
	public static final int DEVICE_TYPE_WP = 21;
	/**
	 * 栏杆机
	 */
	public static final int DEVICE_TYPE_RAIL = 22;
	/**
	 * 电光诱导标志
	 */
	public static final int DEVICE_TYPE_IS = 23;
	/**
	 * 手动报警按钮
	 */
	public static final int DEVICE_TYPE_PB = 24;
	/**
	 * 交通信号灯
	 */
	public static final int DEVICE_TYPE_TSL = 25;
	/**
	 * 车道指示灯
	 */
	public static final int DEVICE_TYPE_LIL = 26;
	/**
	 * 箱变电力监控
	 */
	public static final int DEVICE_TYPE_BT = 28;
	/**
	 * 能见度仪
	 */
	public static final int DEVICE_TYPE_VI_DETECTOR = 29;
	/**
	 * 路面检测器
	 */
	public static final int DEVICE_TYPE_ROAD_DETECTOR = 30;
	/**
	 * 桥面检测器
	 */
	public static final int DEVICE_TYPE_BRIDGE_DETECTOR = 31;
	/**
	 * 紧急电话
	 */
	public static final int DEVICE_TYPE_EMERGENCY_PHONE = 32;
	/**
	 * 太阳能电池
	 */
	public static final int DEVICE_TYPE_SOLAR_BATTERY = 33;
	/**
	 * 电视墙
	 */
	public static final int DEVICE_TYPE_DISPLAY_WALL = 34;
	/**
	 * GPS设备
	 */
	public static final int DEVICE_TYPE_GPS = 35;
	/**
	 * 物资仓库
	 */
	public static final int DEVICE_TYPE_WH = 124;
	/**
	 * 隧道
	 */
	public static final int DEVICE_TYPE_TUNNEL = 100;
	/**
	 * 桥梁
	 */
	public static final int DEVICE_TYPE_BRIDGE = 110;
	/**
	 * 路段
	 */
	public static final int DEVICE_TYPE_ROAD = 120;
	/**
	 * 收费站
	 */
	public static final int DEVICE_TYPE_TOLLGATE = 121;
	/**
	 * 匝道
	 */
	public static final int DEVICE_TYPE_ROAD_MOUTH = 122;
	/**
	 * 调度资源
	 */
	public static final int DEVICE_TYPE_EM_RESOURCE = 123;
	/**
	 * 仓库
	 */
	public static final int DEVICE_TYPE_WAREHOUSE = 124;
	/**
	 * 消防
	 */
	public static final int DEVICE_TYPE_FIRE = 125;
	/**
	 * 医院
	 */
	public static final int DEVICE_TYPE_HOSPITAL = 126;
	/**
	 * 交警
	 */
	public static final int DEVICE_TYPE_POLICE = 127;
	/**
	 * 路政
	 */
	public static final int DEVICE_TYPE_ROAD_ADMIN = 128;
	/**
	 * 情报板常用信息
	 */
	public static final int DEVICE_TYPE_CMS_INFO = 171;
	/**
	 * 情报板常用信息收藏夹
	 */
	public static final int DEVICE_TYPE_CMS_INFO_FOLDER = 172;
	/**
	 * 图层选择
	 */
	public static final int DEVICE_TYPE_VIEW_SELECT = 193;
	/**
	 * 数据统计
	 */
	public static final int DEVICE_TYPE_STAT = 195;
	/**
	 * 值班管理
	 */
	public static final int DEVICE_TYPE_WORK_MANAGE = 196;
	/**
	 * 流量查看
	 */
	public static final int DEVICE_TYPE_FLOW_VIEW = 197;
	/**
	 * 事件
	 */
	public static final int DEVICE_TYPE_EVENT = 198;
	/**
	 * 用户
	 */
	public static final int DEVICE_TYPE_USER = 199;
	/**
	 * 查看隧道
	 */
	public static final int DEVICE_TYPE_TUNNEL_VIEW = 200;
	/**
	 * 查看收费站
	 */
	public static final int DEVICE_TYPE_TOLLGATE_VIEW = 201;
	/**
	 * 查看桥梁
	 */
	public static final int DEVICE_TYPE_BRIDGE_VIEW = 202;
	/**
	 * 下级平台资源
	 */
	public static final int DEVICE_TYPE_SUB_RESOURCE = 300;

	/**
	 * 通信服务器
	 */
	public static final int SERVER_TYPE_CCS = 3;
	/**
	 * 中心存储服务器
	 */
	public static final int SERVER_TYPE_CRS = 4;
	/**
	 * 流媒体服务器
	 */
	public static final int SERVER_TYPE_MSS = 5;
	/**
	 * 协转服务器
	 */
	public static final int SERVER_TYPE_PTS = 6;
	/**
	 * 电视墙服务器
	 */
	public static final int SERVER_TYPE_DWS = 7;
	/**
	 * 数采服务器
	 */
	public static final int SERVER_TYPE_DAS = 8;
	/**
	 * 事件服务器
	 */
	public static final int SERVER_TYPE_ENS = 9;
	/**
	 * 录像转发服务器
	 */
	public static final int SERVER_TYPE_RMS = 10;
	/**
	 * 状态服务器
	 */
	public static final int SERVER_TYPE_RSS = 11;
	/**
	 * 录音服务器
	 */
	public static final int SERVER_TYPE_SRS = 12;
	/**
	 * GIS服务器
	 */
	public static final int SERVER_TYPE_GIS = 27;
	/**
	 * CS客户端登录类型
	 */
	public static final String CLIENT_TYPE_CS = "Cs";
	/**
	 * MCU客户端登录类型
	 */
	public static final String CLIENT_TYPE_MCU = "Mcu";
	/**
	 * OMC客户端登录类型
	 */
	public static final String CLIENT_TYPE_OMC = "Omc";
	/**
	 * CCS登录类型
	 */
	public static final String CLIENT_TYPE_CCS = "Ccs";
	/**
	 * PTS登录类型
	 */
	public static final String CLIENT_TYPE_PTS = "Pts";
	/**
	 * DWS登录类型
	 */
	public static final String CLIENT_TYPE_DWS = "Dws";
	/**
	 * CRS登录类型
	 */
	public static final String CLIENT_TYPE_CRS = "Crs";
	/**
	 * MSS登录类型
	 */
	public static final String CLIENT_TYPE_MSS = "Mss";
	/**
	 * DAS登录类型
	 */
	public static final String CLIENT_TYPE_DAS = "Das";
	/**
	 * ENS登录类型
	 */
	public static final String CLIENT_TYPE_ENS = "Ens";
	/**
	 * RMS登录类型
	 */
	public static final String CLIENT_TYPE_RMS = "Rms";
	/**
	 * RSS登录类型
	 */
	public static final String CLIENT_TYPE_RSS = "Rss";
	/**
	 * SGC登录类型
	 */
	public static final String CLIENT_TYPE_SGC = "Sgc";
	/**
	 * SRS登录类型
	 */
	public static final String CLIENT_TYPE_SRS = "Srs";
	/**
	 * 管理员角色
	 */
	public static final String ROLE_TYPE_ADMIN = "90";
	/**
	 * 高级角色
	 */
	public static final String ROLE_TYPE_SENIOR = "92";
	/**
	 * 普通角色
	 */
	public static final String ROLE_TYPE_JUNIOR = "93";
	/**
	 * 自定义角色
	 */
	public static final String ROLE_TYPE_SELF = "95";
	/**
	 * 平台互联角色
	 */
	public static final String ROLE_TYPE_PLATFORM = "96";
	/**
	 * 视频视图权限
	 */
	public static final String MENU_OPERATION_VIDEO = "1";
	/**
	 * 报警视图权限
	 */
	public static final String MENU_OPERATION_ALARM = "2";
	/**
	 * 地图视图权限
	 */
	public static final String MENU_OPERATION_GIS = "3";
	/**
	 * 信息查询视图权限
	 */
	public static final String MENU_OPERATION_QUERY = "5";
	/**
	 * 电视墙视图权限
	 */
	public static final String MENU_OPERATION_DISPLAY = "4";
	/**
	 * 默认摄像头子类型,自定义枪机04，球机05，云台枪机06
	 */
	public static final String SUBTYPE_CAMERA_DEFAULT = "04";
	/**
	 * 默认摄像头子类型,自定义枪机04，球机05，云台枪机06
	 */
	public static final String SUBTYPE_CAMERA_BALL = "05";
	/**
	 * 默认摄像头子类型,自定义枪机04，球机05，云台枪机06
	 */
	public static final String SUBTYPE_CAMERA_BALL_BOLT = "06";
	/**
	 * GB28181-球机
	 */
	public static final String GB28181_CAMERA_BALL = "1";
	/**
	 * GB28181-半球
	 */
	public static final String GB28181_CAMERA_HALF_BALL = "2";
	/**
	 * GB28181-枪机
	 */
	public static final String GB28181_CAMERA_BOLT = "3";
	/**
	 * GB28181-云台枪机
	 */
	public static final String GB28181_CAMERA_BALL_BOLT = "4";

	/**
	 * 前端存储
	 */
	public static final Short STORE_TYPE_LOCAL = 0;
	/**
	 * 主码流
	 */
	public static final String STORE_MAIN_STREAM = "0";
	/**
	 * 子码流
	 */
	public static final String STORE_SUB_STREAM = "1";
	/**
	 * 中心存储
	 */
	public static final Short STORE_TYPE_CENTER = 1;
	/**
	 * 前端和中心存储
	 */
	public static final Short STORE_TYPE_BOTH = 2;
	/**
	 * 默认存储计划,168个1
	 */
	public static final String STORE_PLAN_DEFAULT = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
	/**
	 * 默认前端存储计划,168个0
	 */
	public static final String LOCAL_STORE_PLAN_DEFAULT = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	/**
	 * 关联员权限,默认"%07"
	 */
	public static final String ADMIN_AUTH = "%07";
	/**
	 * 资源类型用户
	 */
	public static final String RESOURCE_TYPE_USER = "User";
	/**
	 * 资源类型机构
	 */
	public static final String RESOURCE_TYPE_ORGAN = "Organ";
	/**
	 * 资源类型预置点
	 */
	public static final String RESOURCE_TYPE_PRESET = "Preset";
	/**
	 * 资源类型摄像头
	 */
	public static final String RESOURCE_TYPE_CAMERA = "Camera";
	/**
	 * 资源类型DVR
	 */
	public static final String RESOURCE_TYPE_DVR = "Dvr";
	/**
	 * 资源类型CCS
	 */
	public static final String RESOURCE_TYPE_CCS = "Ccs";
	/**
	 * 资源类型CRS
	 */
	public static final String RESOURCE_TYPE_CRS = "Crs";
	/**
	 * 资源类型MSS
	 */
	public static final String RESOURCE_TYPE_MSS = "Mss";
	/**
	 * 资源类型PTS
	 */
	public static final String RESOURCE_TYPE_PTS = "Pts";
	/**
	 * 资源类型DWS
	 */
	public static final String RESOURCE_TYPE_DWS = "Dws";
	/**
	 * 资源类型DAS
	 */
	public static final String RESOURCE_TYPE_DAS = "Das";
	/**
	 * 资源类型ENS
	 */
	public static final String RESOURCE_TYPE_ENS = "Ens";
	/**
	 * 资源类型RMS
	 */
	public static final String RESOURCE_TYPE_RMS = "Rms";
	/**
	 * 资源类型RSS
	 */
	public static final String RESOURCE_TYPE_RSS = "Rss";
	/**
	 * 资源类型SRS
	 */
	public static final String RESOURCE_TYPE_SRS = "Srs";
	/**
	 * 资源类型VD
	 */
	public static final String RESOURCE_TYPE_VD = "VehicleDetector";
	/**
	 * 资源类型WST
	 */
	public static final String RESOURCE_TYPE_WST = "WeatherStat";
	/**
	 * 资源类型WS
	 */
	public static final String RESOURCE_TYPE_WS = "WindSpeed";
	/**
	 * 资源类型BT
	 */
	public static final String RESOURCE_TYPE_BT = "BoxTransformer";
	/**
	 * 资源类型能见度仪
	 */
	public static final String RESOURCE_TYPE_VI_DETECTOR = "ViDetector";
	/**
	 * 资源类型路面检测器
	 */
	public static final String RESOURCE_TYPE_ROAD_DETECTOR = "RoadDetector";
	/**
	 * 资源类型桥面检测器
	 */
	public static final String RESOURCE_TYPE_BRIDGE_DETECTOR = "BridgeDetector";
	/**
	 * 资源类型NOD
	 */
	public static final String RESOURCE_TYPE_NOD = "NoDetector";
	/**
	 * 资源类型LOLI
	 */
	public static final String RESOURCE_TYPE_LOLI = "LoLi";
	/**
	 * 资源类型FD
	 */
	public static final String RESOURCE_TYPE_FD = "FireDetector";
	/**
	 * 资源类型监视器
	 */
	public static final String RESOURCE_TYPE_MONITOR = "Monitor";
	/**
	 * 资源类型PB
	 */
	public static final String RESOURCE_TYPE_PB = "PushButton";
	/**
	 * 资源类型COVI
	 */
	public static final String RESOURCE_TYPE_COVI = "Covi";
	/**
	 * 资源类型CD
	 */
	public static final String RESOURCE_TYPE_CD = "ControlDevice";
	/**
	 * 资源类型CMS
	 */
	public static final String RESOURCE_TYPE_CMS = "Cms";
	/**
	 * 资源类型FAN
	 */
	public static final String RESOURCE_TYPE_FAN = "Fan";
	/**
	 * 资源类型LIGHT
	 */
	public static final String RESOURCE_TYPE_LIGHT = "Light";
	/**
	 * 资源类型RD
	 */
	public static final String RESOURCE_TYPE_RD = "RollingDoor";
	/**
	 * 资源类型RAIL
	 */
	public static final String RESOURCE_TYPE_RAIL = "Rail";
	/**
	 * 资源类型IS
	 */
	public static final String RESOURCE_TYPE_IS = "IndicationSign";
	/**
	 * 资源类型WP
	 */
	public static final String RESOURCE_TYPE_WP = "WaterPump";
	/**
	 * 资源类型POLICE
	 */
	public static final String RESOURCE_TYPE_POLICE = "Police";
	/**
	 * 资源类型HOSPITAL
	 */
	public static final String RESOURCE_TYPE_HOSPITAL = "Hospital";
	/**
	 * 资源类型FIRE
	 */
	public static final String RESOURCE_TYPE_FIRE = "Fire";
	/**
	 * 资源类型ROADADMIN
	 */
	public static final String RESOURCE_TYPE_ROAD_ADMIN = "RoadAdmin";
	/**
	 * 资源类型应急物资
	 */
	public static final String RESOURCE_TYPE_EM_RESOURCE = "Resource";
	/**
	 * 资源类型交通控制
	 */
	public static final String RESOURCE_TYPE_ROAD_CONTROL = "RoadControl";
	/**
	 * 资源类型收费站
	 */
	public static final String RESOURCE_TYPE_TOLLGATE = "Tollgate";
	/**
	 * 资源类型出口匝道
	 */
	public static final String RESOURCE_TYPE_ROAD_MOUTH = "RoadMouth";
	/**
	 * 资源类型太阳能电池
	 */
	public static final String RESOURCE_TYPE_SOLAR_BATTERY = "SolarBattery";
	/**
	 * 资源类型紧急电话
	 */
	public static final String RESOURCE_TYPE_UP = "UrgentPhone";
	/**
	 * 资源类型电视墙
	 */
	public static final String RESOURCE_TYPE_WALL = "Wall";
	/**
	 * gps设备
	 */
	public static final String RESOURCE_TYPE_GPS = "GPSDevice";
	/**
	 * 状态可用
	 */
	public static final int STATUS_AVAILABLE = 1;
	/**
	 * 状态停用
	 */
	public static final int STATUS_SUSPEND = 0;
	/**
	 * 批量创建DVR模板
	 */
	public static final String DVR_TEMPLATE = "1";
	/**
	 * 批量创建摄像头模板
	 */
	public static final String CAMERA_TEMPLATE = "2";
	/**
	 * 批量创建模板参数为空
	 */
	public static final String PARAMETER_NULL = "1";
	/**
	 * 批量创建模板standardNumber11位和12位错误
	 */
	public static final String CAMERA_NAME_EXIST = "2";
	/**
	 * 批量创建模板standardNumber是否重复
	 */
	public static final String DVR_NAME_EXIST = "3";
	/**
	 * 批量创建模板参数类型错误
	 */
	public static final String PARAMETER_ERROR = "4";
	/**
	 * 批量创建模板摄像头通道号数量达到最大限制
	 */
	public static final String CAMERA_OVER_LIMIT = "5";
	/**
	 * 批量创建模板摄像头通道号重复
	 */
	public static final String CAMERA_CHANNEL_NUMBER_EXIST = "6";
	/**
	 * 机构类型-高速公路
	 */
	public static final String ORGAN_TYPE_HIGHWAY = "1";
	/**
	 * 机构类型-干线普通公路
	 */
	public static final String ORGAN_TYPE_NORMAL = "2";
	/**
	 * 机构类型-公路交通枢纽
	 */
	public static final String ORGAN_TYPE_TRAFFIC = "3";
	/**
	 * 机构类型-道路运输
	 */
	public static final String ORGAN_TYPE_ROADWAY = "4";
	/**
	 * 机构类型-水路运输
	 */
	public static final String ORGAN_TYPE_WATERWAY = "5";
	/**
	 * 机构类型-城市交通
	 */
	public static final String ORGAN_TYPE_CITYWAY = "6";
	/**
	 * 机构类型-行政管理单位
	 */
	public static final String ORGAN_TYPE_MANAGEMENT = "0";
	/**
	 * 机构类型-交警
	 */
	public static final String ORGAN_TYPE_POLICE = "11";
	/**
	 * 机构类型-医院
	 */
	public static final String ORGAN_TYPE_HOSPITAL = "12";
	/**
	 * 机构类型-消防
	 */
	public static final String ORGAN_TYPE_FIRE = "13";
	/**
	 * 机构类型-路政
	 */
	public static final String ORGAN_TYPE_ROAD_ADMIN = "14";
	/**
	 * 机构类型-隧道
	 */
	public static final String ORGAN_TYPE_TUNNEL = "100";
	/**
	 * 机构类型-桥梁
	 */
	public static final String ORGAN_TYPE_BRIDGE = "110";
	/**
	 * 机构类型-路段
	 */
	public static final String ORGAN_TYPE_ROAD = "120";
	/**
	 * 机构类型-收费站
	 */
	public static final String ORGAN_TYPE_TOLLGATE = "121";
	/**
	 * 策略类型-照明策略
	 */
	public static final short POLICY_TYPE_LIGHT = (short) 1;
	/**
	 * 策略类型-定时策略
	 */
	public static final short POLICY_TYPE_TIME = (short) 0;
	/**
	 * 表名称-车辆检测器
	 */
	public static final String TABLE_NAME_VD = "vehicle_detector";
	/**
	 * 表名称-气象检测器
	 */
	public static final String TABLE_NAME_WST = "weather_stat";
	/**
	 * 表名称-氮氧化物检测器
	 */
	public static final String TABLE_NAME_NOD = "no_detector";
	/**
	 * 表名称-光强检测器
	 */
	public static final String TABLE_NAME_LOLI = "loli_detector";
	/**
	 * 表名称-风速风向检测器
	 */
	public static final String TABLE_NAME_WS = "wind_speed";
	/**
	 * 表名称-控制类设备
	 */
	public static final String TABLE_NAME_CD = "control_device";
	/**
	 * 表名称-可变信息标志
	 */
	public static final String TABLE_NAME_CMS = "cms";
	/**
	 * 表名称-COVI
	 */
	public static final String TABLE_NAME_COVI = "covi_detector";
	/**
	 * 表名称-设备状态
	 */
	public static final String TABLE_NAME_DEVICE_STATUS = "device_status";
	/**
	 * 表名称-车道指示灯
	 */
	public static final String TABLE_NAME_LIL = "lane_indicate_light";
	/**
	 * 表名称-交通信号灯
	 */
	public static final String TABLE_NAME_TSL = "traffic_sign_light";
	/**
	 * 表名称-能见度仪
	 */
	public static final String TABLE_NAME_VID = "vi_detector";
	/**
	 * 表名称-路面检测器
	 */
	public static final String TABLE_NAME_RD = "road_detector";
	/**
	 * 表名称-GPS
	 */
	public static final String TABLE_NAME_GPS = "gps";
	/**
	 * JDBC-varchar
	 */
	public static final String JDBC_VARCHAR = "varchar";
	/**
	 * JDBC-datetime
	 */
	public static final String JDBC_DATETIME = "datetime";
	/**
	 * JDBC-smallint
	 */
	public static final String JDBC_SMALLINT = "smallint";
	/**
	 * JDBC-tinyint
	 */
	public static final String JDBC_TINYINT = "tinyint";
	/**
	 * JDBC-int
	 */
	public static final String JDBC_INT = "int";
	/**
	 * JDBC-bigint
	 */
	public static final String JDBC_BIGINT = "bigint";
	/**
	 * 统计范围-天
	 */
	public static final String STAT_SCOPE_DAY = "day";
	/**
	 * 统计范围-小时
	 */
	public static final String STAT_SCOPE_HOUR = "hour";
	/**
	 * 统计范围-总量
	 */
	public static final String STAT_SCOPE_TOTAL = "total";
	/**
	 * 隧道风向-无风
	 */
	public static final String WS_DIRECTION_0 = "0";
	/**
	 * 隧道风向-与行车方向相同
	 */
	public static final String WS_DIRECTION_1 = "1";
	/**
	 * 隧道风向-与行车方向相反
	 */
	public static final String WS_DIRECTION_2 = "2";
	/**
	 * 预案动作状态-未执行
	 */
	public static final short ACTION_STATUS_0 = 0;
	/**
	 * 预案动作状态-执行中
	 */
	public static final short ACTION_STATUS_1 = 1;
	/**
	 * 预案动作状态-执行完成
	 */
	public static final short ACTION_STATUS_2 = 2;
	/**
	 * 应急单位
	 */
	public static final String RESOURCE_TYPE_UNIT = "unit";

	/**
	 * 救援物资
	 */
	public static final String RESOURCE_TYPE_RESOURCE = "resource";

	/**
	 * 救援车辆
	 */
	public static final String RESOURCE_TYPE_VEHICLE = "vehicle";

	/**
	 * 救援团队
	 */
	public static final String RESOURCE_TYPE_TEAM = "team";

	/**
	 * 队伍人员
	 */
	public static final String RESOURCE_TYPE_RESOURCE_USER = "resourceUser";

	/**
	 * 消防队
	 */
	public static final String COOPERATIVE_FIRE = "1";

	/**
	 * 医院
	 */
	public static final String COOPERATIVE_HOSPITAL = "2";

	/**
	 * 交警支队
	 */
	public static final String COOPERATIVE_POLICE = "3";

	/**
	 * 路政局
	 */
	public static final String COOPERATIVE_ROAD_ADMIN = "4";

	/**
	 * 物资仓库
	 */
	public static final String COOPERATIVE_WAREHOUSE = "5";

	/**
	 * 交通事故
	 */
	public static final int EVENT_TYPE_TA = 0;
	/**
	 * 恶劣天气
	 */
	public static final int EVENT_TYPE_BW = 1;
	/**
	 * 道路养护
	 */
	public static final int EVENT_TYPE_RM = 2;
	/**
	 * 地质灾害
	 */
	public static final int EVENT_TYPE_ED = 3;
	/**
	 * 公共事件
	 */
	public static final int EVENT_TYPE_COMMON = 4;
	/**
	 * 交通管制
	 */
	public static final int EVENT_TYPE_TM = 5;
	/**
	 * 火灾
	 */
	public static final int EVENT_TYPE_FIRE = 6;
	/**
	 * 事件检测
	 */
	public static final int EVENT_TYPE_VD = 7;
	/**
	 * 上行方向
	 */
	public static final int NAVIGATION_UPLOAD = 0;
	/**
	 * 下行方向
	 */
	public static final int NAVIGATION_DOWNLOAD = 1;
	/**
	 * 双向
	 */
	public static final int NAVIGATION_ALL = 2;
	/**
	 * 事件状态，未处理
	 */
	public static final short EVENT_STATUS_0 = 0;
	/**
	 * 事件状态，处理中
	 */
	public static final short EVENT_STATUS_1 = 1;
	/**
	 * 事件状态，已处理
	 */
	public static final short EVENT_STATUS_2 = 2;
	/**
	 * 情报板发布类型-标语
	 */
	public static final short CMS_INFO_TYPE_1 = 1;
	/**
	 * 情报板发布类型-交通提醒
	 */
	public static final short CMS_INFO_TYPE_2 = 2;
	/**
	 * 情报板发布类型-施工提醒
	 */
	public static final short CMS_INFO_TYPE_3 = 3;
	/**
	 * 车检器-通道
	 */
	public static final short VD_TYPE_CHANNEL = 2;
	/**
	 * 车检器-车检器
	 */
	public static final short VD_TYPE_VD = 1;
	/**
	 * 值班计划-交警
	 */
	public static final String WORK_PLAN_TYPE_POLICE = "0";
	/**
	 * 值班计划-路政
	 */
	public static final String WORK_PLAN_TYPE_ROAD_ADMIN = "1";
	/**
	 * 路况-畅通
	 */
	public static final String FLUX_1 = "1";
	/**
	 * 路况-一般
	 */
	public static final String FLUX_2 = "2";
	/**
	 * 路况-缓行
	 */
	public static final String FLUX_3 = "3";
	/**
	 * 路况-拥堵
	 */
	public static final String FLUX_4 = "4";

	/**
	 * 表名称-车辆检测器
	 */
	public static final String TABLE_NAME_VD_REAL = "vehicle_detector_real";
	/**
	 * 表名称-气象检测器
	 */
	public static final String TABLE_NAME_WST_REAL = "weather_stat_real";
	/**
	 * 表名称-氮氧化物检测器
	 */
	public static final String TABLE_NAME_NOD_REAL = "no_detector_real";
	/**
	 * 表名称-光强检测器
	 */
	public static final String TABLE_NAME_LOLI_REAL = "loli_detector_real";
	/**
	 * 表名称-风速风向检测器
	 */
	public static final String TABLE_NAME_WS_REAL = "wind_speed_real";
	/**
	 * 表名称-控制类设备
	 */
	public static final String TABLE_NAME_CD_REAL = "control_device_real";
	/**
	 * 表名称-可变信息标志
	 */
	public static final String TABLE_NAME_CMS_REAL = "cms_real";
	/**
	 * 表名称-COVI
	 */
	public static final String TABLE_NAME_COVI_REAL = "covi_detector_real";
	/**
	 * 表名称-设备状态
	 */
	public static final String TABLE_NAME_DEVICE_STATUS_REAL = "device_status_real";
	/**
	 * 表名称-车道指示灯
	 */
	public static final String TABLE_NAME_LIL_REAL = "lane_indicate_light_real";
	/**
	 * 表名称-交通信号灯
	 */
	public static final String TABLE_NAME_TSL_REAL = "traffic_sign_light_real";
	/**
	 * 报警类型-设备离线
	 */
	public static final String ALARM_TYPE_OFFLINE = "8";
	/**
	 * 报警类型-设备上线
	 */
	public static final String ALARM_TYPE_ONLINE = "13";
	/**
	 * 用户CCS路由返回lanIP
	 */
	public static final String LAN_IP = "lanIp";
	/**
	 * 用户CCS路由返回wanIP
	 */
	public static final String WAN_IP = "wanIp";
	/**
	 * 已确认历史报警
	 */
	public static final String HISTORY_ALARM_TRUE = "0";
	/**
	 * 未确认历史报警
	 */
	public static final String HISTORY_ALARM_FALSE = "1";
	/**
	 * 其否启动报警-1启动
	 */
	public static final String ALARM_ENABLE_TRUE = "1";
	/**
	 * 其否启动报警-0未启动
	 */
	public static final String ALARM_ENABLE_FALSE = "0";
	/**
	 * 用户选择查看客户端日志
	 */
	public static final String LOG_FROM_CLIENT = "1";
	/**
	 * 用户选择查看服务器日志
	 */
	public static final String LOG_FROM_SERVER = "2";
	/**
	 * 所有权限
	 */
	public static final String ALL_AUTH = "1,2,4";
	/**
	 * 查看权限
	 */
	public static final String SEARCH_AUTH = "1";
}
