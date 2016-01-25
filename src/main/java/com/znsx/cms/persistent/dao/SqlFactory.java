package com.znsx.cms.persistent.dao;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.s;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.model.TableInsertVO;
import com.znsx.util.file.Configuration;

/**
 * 数据库语句生成工厂
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-4-7 下午05:15:01
 */
public class SqlFactory {

	private static final SqlFactory instance = new SqlFactory();

	public static final String TABLE_CONFIG_FILE = "table-config.xml";
	/**
	 * 缓存动态插入的语句
	 */
	private Map<String, TableInsertVO> insertMap = new HashMap<String, TableInsertVO>();
	/**
	 * 缓存列的名称，不用每次都去解析配置文件
	 */
	private Map<String, String> columnNameMap = new HashMap<String, String>();

	private SqlFactory() {

	}

	/**
	 * 
	 * 单列模式获取类的实例
	 * 
	 * @return 类的实例
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:40:18
	 */
	public static SqlFactory getInstance() {
		return instance;
	}

	/**
	 * 用户操作日志添加
	 * 
	 * @return sql
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:40:47
	 */
	public String insertSysLog() {
		return "insert into sv_sys_log (id,resource_id, resource_name, resource_type, target_id, target_name, target_type, log_time, operation_type, operation_code, operation_name, success_flag, create_time, note, organ_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * 添加通讯录
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:17:02
	 */
	public String insertAddressBook() {
		return "insert into em_address_book (id,link_man, phone, sex, address, email, fax, position, organ_id, note) values (?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * 根据对象ID和类型，获取对象名称
	 * 
	 * @param type
	 *            对象类型， hibernate配置中的class name
	 * @return hql
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:41:06
	 */
	public String getNameByIdAndType(String type) {
		return "select name from " + type + " where id = ?";
	}

	/**
	 * 根据用户登录名称,获取用户
	 * 
	 * @return hql
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:38:34
	 */
	public String getUserByUserName() {
		return "from User u where u.logonName = ?";
	}

	// /**
	// * 获取子机构列表
	// *
	// * @return hql
	// * @author huangbuji
	// * <p />
	// * Create at 2013 下午1:41:54
	// */
	// public String getChildOrganById() {
	// return "from Organ o where o.parent.id = ?";
	// }

	/**
	 * 根据用户会话ticket,获取用户会话对象
	 * 
	 * @return 用户会话对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:42:51
	 */
	public String getUserSessionByTicket() {
		return "from UserSession where ticket = ?";
	}

	/**
	 * 根据角色ID删除角色关联的资源权限
	 * 
	 * @return hql
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:43:30
	 */
	public String deleteRoleResources() {
		return "delete from r_role_resource_permission where role_id = ?";
	}

	/**
	 * 删除角色下指定资源集合的权限关联
	 * 
	 * @param size
	 *            资源的数量
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:58:40
	 */
	public String deleteRoleResourcesByResource(int size) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from r_role_resource_permission where role_id = ? and resource_type = ? and resource_id in (");
		for (int i = 0; i < size; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 摄像头插入
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:26:19
	 */
	public String insertCamera() {
		return "insert into sv_device (id,standard_number,type,sub_type,name,parent_id,mss_id,crs_id,create_time,channel_number,organ_id,manufacturer_id,status,location,note,device_model_id,navigation,stake_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * 
	 * 视频服务器插入
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午6:42:37
	 */
	public String insertDvr() {
		return "insert into sv_device (id,standard_number,type,sub_type,name,max_connect,ccs_id,pts_id,create_time,channel_amount,organ_id,manufacturer_id,status,location,note,device_model_id,lan_ip,port,transport,access_mode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * 摄像头属性插入
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午10:26:40
	 */
	public String insertCameraProperty() {
		return "insert into sv_video_device_property (id,store_type,local_store_plan,center_store_plan,image_id) VALUES (?,?,?,?,?)";
	}

	/**
	 * 删除摄像头的预置点
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:38:18
	 */
	public String deletePresetByCamera() {
		return "delete from Preset where deviceId = ?";
	}

	/**
	 * 删除摄像头的预置点关联的图片
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:40:05
	 */
	public String deleteCameraPresetImage() {
		return "delete from sv_image_resource where id in (select image_id from sv_device_preset where device_id = ?)";
	}

	/**
	 * 
	 * 根据dvrId删除摄像头和搜藏夹关联
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:27:57
	 */
	public String deleteRUserDeviceFavorite() {
		return "delete from r_user_device_favorite where device_id in (select id from sv_device where parent_id = ?)";
	}

	/**
	 * 
	 * 根据dvrId删除角色和摄像头关联
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:55:16
	 */
	public String deleteRRP() {
		return "delete from r_role_resource_permission where resource_id in (select id from sv_device where parent_id = ?)";
	}

	/**
	 * 
	 * 根据dvrId删除预置点
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:55:40
	 */
	public String deletePreset() {
		return "delete from sv_device_preset where device_id in (select id from sv_device where parent_id = ?)";
	}

	/**
	 * 
	 * 根据dvrId删除摄像头
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:56:00
	 */
	public String deleteCamera() {
		return "delete from sv_device where parent_id = ?";
	}

	/**
	 * 
	 * 根据dvrId删除预置点和图片的 关联
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午8:58:28
	 */
	public String deletePresetImage() {
		return "delete from sv_image_resource where id in (select image_id from sv_device_preset where device_id in (select id from sv_device where parent_id = ?))";
	}

	/**
	 * 
	 * 根据摄像头ID删除摄像头和搜藏夹关联
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:33:43
	 */
	public String deleteDeviceFavorite() {
		return "delete from r_user_device_favorite where device_id = ? ";
	}

	/**
	 * 
	 * 根据摄像头ID删除摄像头和角色的关联
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:35:00
	 */
	public String deleteRoleDevicePermission() {
		return "delete from r_role_resource_permission where resource_id = ?";
	}

	/**
	 * 
	 * 根据机构名称模糊查询机构列表
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:59:50
	 */
	// public String listOrganByName() {
	// return
	// "select a.* from (select * from sv_organ where path like ?) a where a.name like ? and a.standard_number = ? Limit ? ,?";
	// }

	/**
	 * 
	 * 根据机构名称模糊查询机构列表
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:31:19
	 */
	public String listOrganTotalCount() {
		return "select count(*) from (select * from sv_organ where path like ?) a where a.name like ? and a.standard_number = ?";
	}

	/**
	 * 
	 * 删除用户的收藏夹
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午11:45:13
	 */
	public String deleteFavoriteByUser() {
		return "delete from UserFavorite where userId = ?";
	}

	/**
	 * 复制用户会话到历史会话中
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:30:42
	 */
	public String copySessionToHistory() {
		return "insert into sv_user_session_history (id,ticket,user_id,organ_id,organ_name,path,logon_name,standard_number,logon_time,logoff_time,ip,client_type,kick_flag) select id,ticket,user_id,organ_id,organ_name,path,logon_name,standard_number,logon_time,CONCAT(UNIX_TIMESTAMP(),'000')+0,ip,client_type,0 from sv_user_session where user_id = ?";
	}

	public String deleteSessionByUser() {
		return "delete from UserSessionUser where user.id = ?";
	}

	/**
	 * 查询机构及所有子机构的ID集合
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:07:42
	 */
	public String listOrganIdByParent() {
		return "select id from sv_organ where path like ?";
	}

	/**
	 * 取消指定摄像头的默认预置点
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:12:36
	 */
	public String removeCameraDefaultPreset() {
		return "update Preset set isDefault = 0 where deviceId = ?";
	}

	/**
	 * 获取机构下的在线用户列表
	 * 
	 * @param logonName
	 *            用户登录名称
	 * @param name
	 *            用户真实姓名
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:10:05
	 */
	public String listOrganUserSession(String logonName, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("select us.id,us.ticket,us.user_id,us.logon_name,u.name,us.standard_number,us.logon_time,us.client_type,us.update_time,us.ip from sv_user_session us left join sv_user u on us.user_id = u.id where us.path like ? and (us.client_type = 'omc' or us.client_type = 'cs' or us.client_type = 'sgc' or us.client_type = 'mcu') ");
		if (StringUtils.isNotBlank(logonName)) {
			sb.append("and u.logon_name like ? ");
		}
		if (StringUtils.isNotBlank(name)) {
			sb.append("and u.name like ? ");
		}
		sb.append("limit ?, ?");
		return sb.toString();
	}

	/**
	 * 统计机构下的在线用户数量
	 * 
	 * @param logonName
	 *            用户登录名称
	 * @param name
	 *            用户真实姓名
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-8 下午4:02:38
	 */
	public String countOrganUserSession(String logonName, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from sv_user_session us left join sv_user u on us.user_id = u.id where us.path like ? and (us.client_type = 'omc' or us.client_type = 'cs' or us.client_type = 'sgc' or us.client_type = 'mcu') ");
		if (StringUtils.isNotBlank(logonName)) {
			sb.append("and u.logon_name like ? ");
		}
		if (StringUtils.isNotBlank(name)) {
			sb.append("and u.name like ? ");
		}
		return sb.toString();
	}

	/**
	 * 获取CCS管辖的用户会话列表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:52:49
	 */
	public String listCcsUserSession() {
		return "select us.id, us.standard_number as standardNumber, u.priority from sv_user_session us left join sv_user u on us.user_id = u.id where u.ccs_id = ?";
	}

	// /**
	// * 分页查询机构及所有子机构下面的摄像头针对给定角色的权限关系
	// *
	// * @return
	// * @author huangbuji
	// * <p />
	// * Create at 2013-6-19 上午11:18:54
	// */
	// public String listOrganCameraWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from sv_device d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '2' join sv_organ o on o.id = d.organ_id where d.type = '2' ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }
	//
	// /**
	// * 统计机构及所有子机构下面的摄像头数量
	// *
	// * @return
	// * @author huangbuji
	// * <p />
	// * Create at 2013-6-19 下午1:43:08
	// */
	// public String countOrganCameraWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from sv_device d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '2' join sv_organ o on o.id = d.organ_id where d.type = '2' ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }

	/**
	 * 获取在线的用户ID集合
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-8 下午2:00:49
	 */
	public String listOnlineUserId() {
		return "select distinct(user_id) from sv_user_session where client_type in ('Cs', 'Omc')";
	}

	/**
	 * 
	 * 插入视频服务器属性
	 * 
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:20:15
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:20:15
	 */
	public String insertDvrProperty() {
		return "insert into sv_video_device_property (id,user_name,password,heart_cycle,protocol,decode) VALUES (?,?,?,?,?,?)";
	}

	/**
	 * 查询收藏夹中的摄像头列表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-26 下午4:06:40
	 */
	public String listFavoritesCamera() {
		return "select camera.id,camera.standard_number,camera.name,camera.type,camera.sub_type,camera.parent_id,camera.manufacturer_id,camera.location,camera.channel_number,dvr.transport,dvr.access_mode from sv_device camera left join r_user_device_favorite r on camera.id = r.device_id left join sv_device dvr on camera.parent_id = dvr.id where r.favorite_id = ?";
	}

	public String listSNByDvr() {
		return "select standard_number from sv_device where type = '1'";
	}

	public String listNameByDvr() {
		return "select name from sv_device where type = '1'";
	}

	public String listNameByCamera() {
		return "select name from sv_device where type = '2'";
	}

	public String deleteSubPlatform() {
		return "delete from SubPlatformResource where path like ? ";
	}

	/**
	 * 插入策略关联设备表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午10:09:51
	 */
	public String insertPolicyDevice() {
		return "insert into r_policy_device (id,policy_id, device_id, type, status) values(?,?,?,?,?)";
	}

	/**
	 * 根据策略删除策略关联的设备
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 上午10:23:34
	 */
	public String deletePolicyDeviceByPolicy() {
		return "delete from PolicyDevice where policyId = ?";
	}

	/**
	 * 查询策略关联设备列表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午2:42:49
	 */
	public String listPolicyDevice() {
		return "select r.device_id,c.name,c.standard_number,r.status from r_policy_device r, tm_control_device c where r.device_id = c.id and r.policy_id = ?";
	}

	/**
	 * 删除定时策略的策略执行计划
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-27 下午4:01:07
	 */
	public String deleteTimePolicy() {
		return "delete from TimePolicy where timePolicyId = ?";
	}

	/**
	 * 查询定时策略的策略执行计划列表
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-11-28 上午10:34:43
	 */
	public String listTimePolicyItem() {
		return "select r.policy_id,p.name,r.begin_date,r.end_date,r.begin_time,r.end_time from r_timepolicy_policy r, tm_policy p where r.policy_id = p.id and r.time_policy_id = ?";
	}

	/**
	 * 检查表以及分区是否存在
	 * 
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-2 下午2:06:29
	 */
	public String checkTablePartitions(String tableName) {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "SELECT partition_name FROM USER_TAB_PARTITIONS where table_name = '"
					+ tableName.toUpperCase() + "'";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select partition_name from information_schema.partitions where table_name = '"
					+ tableName.toLowerCase() + "'";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
	}

	/**
	 * 创建表分区
	 * 
	 * @param tableName
	 *            表名称
	 * @param partitionName
	 *            分区名称
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-2 下午2:09:11
	 */
	public String createTablePartition(String tableName, String partitionName) {
		StringBuffer partitionSQL = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			partitionSQL.append("alter table ");
			partitionSQL.append(tableName.toUpperCase());
			partitionSQL.append(" add partition ");
			partitionSQL.append(partitionName);
			partitionSQL.append(" values less than (to_date('");
			int length = partitionName.length();
			partitionSQL
					.append(partitionName.substring(length - 6, length - 2));
			partitionSQL.append("-");
			partitionSQL.append(partitionName.substring(length - 2));
			partitionSQL.append("-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'))");
		} else if (Configuration.MYSQL.equals(dbName)) {
			partitionSQL.append("alter table ");
			partitionSQL.append(tableName);
			partitionSQL.append(" add partition (partition ");
			partitionSQL.append(partitionName);
			partitionSQL.append(" values less than ('");
			int length = partitionName.length();
			partitionSQL
					.append(partitionName.substring(length - 6, length - 2));
			partitionSQL.append("-");
			partitionSQL.append(partitionName.substring(length - 2));
			partitionSQL.append("-01'))");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return partitionSQL.toString();
	}

	/**
	 * 根据table-config.xml中的配置，动态创建分区表
	 * 
	 * @param tableName
	 *            要创建的表的名称
	 * @param partitionName
	 *            要创建的分区名称
	 * @param nextPartitionName
	 *            下一个分区名称
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-12-2 下午2:43:26
	 */
	public String createTable(String tableName, String partitionName,
			String nextPartitionName) throws BusinessException {
		File file = new File(this.getClass().getClassLoader()
				.getResource(TABLE_CONFIG_FILE).getPath());
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(file);
			Element root = doc.getRootElement();
			Element table = root.getChild(tableName);
			if (null == table) {
				throw new BusinessException(ErrorCode.XML_PARSE_ERROR, "Table["
						+ tableName + "] config is not exist !");
			}
			List<Element> columns = table.getChildren("column");
			StringBuffer ddl = new StringBuffer();
			String space = " ";
			ddl.append("create table ");
			ddl.append(tableName);
			ddl.append(" (");
			// 添加ID
			ddl.append("id int not null auto_increment, ");
			for (Element column : columns) {
				ddl.append(column.getAttributeValue("name"));
				ddl.append(space);
				String type = column.getAttributeValue("type");
				ddl.append(type);
				if ("varchar".equals(type)) {
					ddl.append("(");
					ddl.append(column.getAttributeValue("length"));
					ddl.append(")");
				}
				ddl.append(", ");
			}
			// 添加机构列
			ddl.append("organ varchar(32) not null, ");
			// 增加分区列
			ddl.append("update_time datetime default current_timestamp on update current_timestamp,");
			ddl.append(" primary key (id, update_time) ) engine=InnoDB default charset=utf8 ");
			ddl.append("partition by range columns(update_time) ( ");
			ddl.append("partition ");
			ddl.append(partitionName);
			ddl.append(" values less than ('");
			int length = partitionName.length();
			ddl.append(partitionName.substring(length - 6, length - 2));
			ddl.append("-");
			ddl.append(partitionName.substring(length - 2));
			ddl.append("-01'), partition ");
			ddl.append(nextPartitionName);
			ddl.append(" values less than ('");
			length = nextPartitionName.length();
			ddl.append(nextPartitionName.substring(length - 6, length - 2));
			ddl.append("-");
			ddl.append(nextPartitionName.substring(length - 2));
			ddl.append("-01'))");
			return ddl.toString();
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.XML_PARSE_ERROR,
					"table-config.xml parse failed !");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"File[table-config.xml] not found !");
		}
	}

	public String getListSQL(String tableName, int argCount) {
		StringBuffer sb = new StringBuffer();
		sb.append("select o.* from ");
		sb.append(tableName);
		sb.append(" o inner join (select");
		sb.append(" max(i.rec_time) as rt, i.standard_number as sn from ");
		sb.append(tableName);

		sb.append(" i where i.standard_number in (");
		for (int i = 0; i < argCount; i++) {
			sb.append("?,");
		}
		// 移除最后一个逗号
		sb = sb.delete(sb.length() - 1, sb.length());
		sb.append(") and i.rec_time > ? ");
		sb.append(" group by i.standard_number) temp ");
		sb.append("on o.rec_time = temp.rt and o.standard_number = temp.sn");
		return sb.toString();
	}

	public String insertDeviceStatus() {
		return "insert into device_status (id,standard_number,rec_time,type,status,comm_status,organ) values (?,?,?,?,?,?,?)";
	}

	public String statDeviceStatus(Integer type, String sns[], String organSN) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select ds.*, row_number() over (order by ds.id) as rid from device_status ds inner join (");
			sb.append("select min1,standard_number as sn, min(rec_time) as rt from (");
			sb.append("select standard_number, EXTRACT(YEAR FROM rec_time)||EXTRACT(MONTH FROM rec_time)||EXTRACT(DAY FROM rec_time)||EXTRACT(HOUR FROM rec_time)||EXTRACT(MINUTE FROM rec_time) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") temp on ds.standard_number = temp.sn and ds.rec_time = temp.rt) ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select ds.* from device_status ds inner join");
			sb.append("(select min1,standard_number as sn, min(rec_time) as rt from (");
			sb.append("select standard_number, CONCAT(EXTRACT(YEAR FROM rec_time),EXTRACT(MONTH FROM rec_time),EXTRACT(DAY FROM rec_time),EXTRACT(HOUR FROM rec_time),EXTRACT(MINUTE FROM rec_time)) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") temp on ds.standard_number = temp.sn and ds.rec_time = temp.rt order by ds.id limit ?, ? ");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String countDeviceStatus(Integer type, String sns[], String organSN) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select ds.* from device_status ds inner join (");
			sb.append("select min1,standard_number as sn, min(rec_time) as rt from (");
			sb.append("select standard_number, EXTRACT(YEAR FROM rec_time)||EXTRACT(MONTH FROM rec_time)||EXTRACT(DAY FROM rec_time)||EXTRACT(HOUR FROM rec_time)||EXTRACT(MINUTE FROM rec_time) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") temp on ds.standard_number = temp.sn and ds.rec_time = temp.rt) tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select ds.* from device_status ds inner join (");
			sb.append("select min1,standard_number as sn, min(rec_time) as rt from (");
			sb.append("select standard_number, CONCAT(EXTRACT(YEAR FROM rec_time),EXTRACT(MONTH FROM rec_time),EXTRACT(DAY FROM rec_time),EXTRACT(HOUR FROM rec_time),EXTRACT(MINUTE FROM rec_time)) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") temp on ds.standard_number = temp.sn and ds.rec_time = temp.rt) tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String getVDStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select tmp.*, row_number() over (order by group_time desc, standard_number desc) as rid from (");
			sb.append("select standard_number, sum(up_flux) as flux, avg(up_flux) as flux_avg, avg(up_speed) as speed_avg, avg(up_occ) as occ_avg, organ, group_time from (");
			sb.append("select standard_number, up_flux, up_speed, up_occ, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from vehicle_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp ) temp ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select standard_number, sum(up_flux) as flux, avg(up_flux) as flux_avg, avg(up_speed) as speed_avg, avg(up_occ) as occ_avg, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from vehicle_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp order by group_time desc, standard_number desc limit ?,?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String countVDStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, group_time from (");
			sb.append("select standard_number, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from vehicle_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from vehicle_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String getWSTStat(String sn, String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select tmp.*, row_number() over (order by group_time desc, standard_number desc) as rid from (");
			sb.append("select standard_number, avg(vis_avg) as vis_avg, avg(air_temp_avg) as air_temp_avg, avg(humi_avg) as humi_avg, avg(wind_dir) as wind_dir, avg(ws_avg) as ws_avg, avg(road_temp_avg) as road_temp_avg, avg(road_surface) as road_surface, '0' as pressure, avg(rain_vol) as rain_vol, organ, group_time from (");
			sb.append("select standard_number, vis_avg, air_temp_avg, humi_avg, wind_dir, ws_avg, road_temp_avg, road_surface, rain_vol, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from weather_stat ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (StringUtils.isNotBlank(sn)) {
				sb.append(" and standard_number = ?");
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp ) temp ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select standard_number, avg(vis_avg) as vis_avg, avg(air_temp_avg) as air_temp_avg, avg(humi_avg) as humi_avg, avg(wind_dir) as wind_dir, avg(ws_avg) as ws_avg, avg(road_temp_avg) as road_temp_avg, avg(road_surface) as road_surface, '0' as pressure, avg(rain_vol) as rain_vol, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from weather_stat ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (StringUtils.isNotBlank(sn)) {
				sb.append(" and standard_number = ?");
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp order by group_time desc, standard_number desc limit ?,?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String countWSTStat(String sn, String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, group_time from (");
			sb.append("select standard_number, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from weather_stat ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (StringUtils.isNotBlank(sn)) {
				sb.append(" and standard_number = ?");
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number as standard_number, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from weather_stat ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (StringUtils.isNotBlank(sn)) {
				sb.append(" and standard_number = ?");
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String getWSStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select tmp.*, row_number() over (order by group_time desc, standard_number desc) as rid from (");
			sb.append("select standard_number, max(speed) as speed_max, min(speed) as speed_min, avg(speed) as speed_avg, avg(direction) as direction, organ, group_time from (");
			sb.append("select standard_number, speed, direction, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD hh24')||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			}
			sb.append(" from wind_speed ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp ) temp ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select standard_number, max(speed) as speed_max, min(speed) as speed_min, avg(speed) as speed_avg, avg(direction) as direction, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d'), ' 00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			}
			sb.append(" from wind_speed ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp order by group_time desc, standard_number desc limit ?,?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String countWSStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, group_time from (");
			sb.append("select standard_number, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from wind_speed ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from wind_speed ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String getLoLiStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select tmp.*, row_number() over (order by group_time desc, standard_number desc) as rid from (");
			sb.append("select standard_number, avg(lo) as lo_avg, avg(li) as li_avg, organ, group_time from (");
			sb.append("select standard_number, lo, li, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD hh24')||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			}
			sb.append(" from loli_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp ) temp ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select standard_number, avg(lo) as lo_avg, avg(li) as li_avg, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d'), ' 00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			}
			sb.append(" from loli_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp order by group_time desc, standard_number desc limit ?,?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String countLoLiStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, group_time from (");
			sb.append("select standard_number, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from loli_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from loli_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String getCoviStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select tmp.*, row_number() over (order by group_time desc, standard_number desc) as rid from (");
			sb.append("select standard_number, max(co) as co_max, min(co) as co_min, avg(co) as co_avg, max(vi) as vi_max, min(vi) as vi_min, avg(vi) as vi_avg, organ, group_time from (");
			sb.append("select standard_number, co, vi, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD hh24')||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("to_char(rec_time,'YYYY-MM-DD')||");
				sb.append("' 00:00:00' as group_time");
			}
			sb.append(" from covi_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp order by group_time desc) temp ");
			sb.append("where rid >= ? and rid < ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (");
			sb.append("select standard_number as standard_number, max(co) as co_max, min(co) as co_min, avg(co) as co_avg, max(vi) as vi_max, min(vi) as vi_min, avg(vi) as vi_avg, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d'), ' 00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(DATE_FORMAT(rec_time,'%Y-%m-%d %H'), '00:00') as group_time ");
			}
			sb.append(" from covi_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp order by group_time desc, standard_number desc limit ?,?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String countCoviStat(String sns[], String organSN, String scope) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number, organ, group_time from (");
			sb.append("select standard_number, organ, ");
			sb.append("EXTRACT(YEAR FROM rec_time)||'-'||EXTRACT(MONTH FROM rec_time)||'-'||EXTRACT(DAY FROM rec_time)||' '||");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("EXTRACT(HOUR FROM rec_time)||':00:00' as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("'00:00:00' as group_time");
			} // 默认按照天进行统计
			else {
				sb.append("'00:00:00' as group_time");
			}
			sb.append(" from covi_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(") tm ");
			sb.append("group by standard_number, group_time, organ ");
			sb.append(") tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select standard_number as standard_number, organ, ");
			// 按小时统计
			if (TypeDefinition.STAT_SCOPE_HOUR.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', EXTRACT(HOUR FROM rec_time), ':00:00') as group_time ");
			} else if (TypeDefinition.STAT_SCOPE_DAY.equals(scope)) {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			} // 默认按照天进行统计
			else {
				sb.append("CONCAT(EXTRACT(YEAR FROM rec_time), '-', EXTRACT(MONTH FROM rec_time), '-', EXTRACT(DAY FROM rec_time), ' ', '00:00:00') as group_time ");
			}
			sb.append(" from covi_detector ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by standard_number, group_time");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String removeSwitchData() {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from control_device ");
		sb.append("where type in(");
		sb.append(TypeDefinition.DEVICE_TYPE_FAN);
		sb.append(",");
		sb.append(TypeDefinition.DEVICE_TYPE_LIGHT);
		sb.append(",");
		sb.append(TypeDefinition.DEVICE_TYPE_RD);
		sb.append(",");
		sb.append(TypeDefinition.DEVICE_TYPE_WP);
		sb.append(",");
		sb.append(TypeDefinition.DEVICE_TYPE_RAIL);
		sb.append(",");
		sb.append(TypeDefinition.DEVICE_TYPE_IS);
		sb.append(") ");
		sb.append("and update_time < date_sub(current_time, interval 24 hour)");
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(SqlFactory.getInstance().createTable(
		// "vehicle_detector", "vehicle_detector201312",
		// "vehicle_detector201401"));
		System.out.println(SqlFactory.getInstance().createTablePartition(
				"device_status", "device_status201411"));
	}

	// public String countOrganVDWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_vehicle_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '10' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganVDWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_vehicle_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '10' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganWSWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_wind_speed d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '11' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganWSWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_wind_speed d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '11' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganWSTWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_weather_stat d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '12' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganWSTWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_weather_stat d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '12' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganLoLiWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_loli d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '13' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganLoLiWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_loli d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '13' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganFDWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_fire_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '14' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganFDWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_fire_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '14' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganCoviWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_covi d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '15' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganCoviWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_covi d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '15' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganNODWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_no_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '16' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganNODWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_no_detector d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '16' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganPBWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_push_button d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '24' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganPBWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_push_button d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = '24' join sv_organ o on o.id = d.organ_id ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	// public String countOrganControlDeviceWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select count(*) from tm_control_device d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = ? join sv_organ o on o.id = d.organ_id where d.type = ? ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ?");
	// return sb.toString();
	// }
	//
	// public String listOrganControlDeviceWithPermission(String name) {
	// StringBuffer sb = new StringBuffer();
	// sb.append("select d.id, d.name, r.privilege from tm_control_device d left outer join r_role_resource_permission r on r.resource_id = d.id and r.role_id = ? and r.resource_type = ? join sv_organ o on o.id = d.organ_id where d.type = ? ");
	// if (StringUtils.isNotBlank(name)) {
	// sb.append("and d.name like ? ");
	// }
	// sb.append("and o.path like ? limit ?, ?");
	// return sb.toString();
	// }

	public String countRoleAllResourcePermissions() {
		String sql = "select count(*) from r_role_resource_permission r where r.role_id = ?";
		return sql;
	}

	public String listRoleCameraPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join sv_device d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleVDPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_vehicle_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleWSPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_wind_speed d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleWSTPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_weather_stat d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleLoliPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_loli d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ?";
	}

	public String listRoleFDPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_fire_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleCoviPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_covi d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleNODPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_no_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRolePBPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_push_button d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleControlDevicePermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_control_device d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String deleteRoleDetectorPermission() {
		return "delete from r_role_resource_permission where resource_id = ? and resource_type = ? ";
	}

	public String insertSN() {
		return "insert into sv_standard_number(id,standard_number, class_type) values(?,?,?)";
	}

	public String getVehicleDetectorStat() {
		String sql = "select sum(up_flux)+sum(dw_flux) as totalflux, sum(up_flux) as upflux, sum(dw_flux) as dwflux, sum(up_speed) as upspeed, sum(dw_speed) as dwspeed from vehicle_detector where rec_time between ? and ? and id = ?";
		return sql;
	}

	public String statVehicleDetectorTotal() {
		String sql = "select sum(up_flux)+sum(dw_flux) as totalflux from vehicle_detector where id = ?";
		return sql;
	}

	public String getStatOrganYesTodayTotalVD() {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as yestodayFlow ,sum(up_fluxs) as yestodayEtc ,sum(dw_fluxs) as yestodayFreight from vehicle_detector where rec_time between ? and ? and organ = ?";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as yestodayFlow ,sum(up_fluxs) as yestodayEtc ,sum(dw_fluxs) as yestodayFreight from vehicle_detector where rec_time between ? and ? and organ = ?";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
	}

	public String getStatOrganTotalVD() {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalFlow ,sum(up_fluxs) as etcTotal ,sum(dw_fluxs) as freightTotal from vehicle_detector where organ = ?";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalFlow ,sum(up_fluxs) as etcTotal ,sum(dw_fluxs) as freightTotal from vehicle_detector where organ = ?";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

	}

	public String getTrafficFlowTop() {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "select * from (select organ, sum(up_flux)+sum(dw_flux) as totalFlow from vehicle_detector where rec_time between ? and ? group by organ order by totalFlow desc) where rownum < ?";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select organ, sum(up_flux)+sum(dw_flux) as totalFlow from vehicle_detector where rec_time between ? and ? group by organ order by totalFlow desc limit ?";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

	}

	public String statRoadTrafficFlowTotal() {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalflux from vehicle_detector where organ = ?";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalflux from vehicle_detector where organ = ?";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

	}

	public String getRoadTrafficFlow() {
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalflux, sum(up_flux) as upflux, sum(dw_flux) as dwflux, sum(up_speed) as upspeed, sum(dw_speed) as dwspeed from vehicle_detector where rec_time between ? and ? and organ = ?";
		} else if (Configuration.MYSQL.equals(dbName)) {
			return "select sum(up_flux)+sum(dw_flux) as totalflux, sum(up_flux) as upflux, sum(dw_flux) as dwflux, sum(up_speed) as upspeed, sum(dw_speed) as dwspeed from vehicle_detector where rec_time between ? and ? and organ = ?";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

	}

	public String countTotalDeviceStatus(Integer type, String sns[]) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select ds.* from device_status ds inner join (");
			sb.append("select min1,standard_number as sn, min(rec_time) as rt from (");
			sb.append("select standard_number, EXTRACT(YEAR FROM rec_time)||EXTRACT(MONTH FROM rec_time)||EXTRACT(DAY FROM rec_time)||EXTRACT(HOUR FROM rec_time)||EXTRACT(MINUTE FROM rec_time) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") temp on ds.standard_number = temp.sn and ds.rec_time = temp.rt) tmp");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (");
			sb.append("select min1,standard_number from (");
			sb.append("select standard_number,type, CONCAT(EXTRACT(YEAR FROM rec_time),EXTRACT(MONTH FROM rec_time),EXTRACT(DAY FROM rec_time)) as min1, rec_time ");
			sb.append("from device_status ");
			sb.append("where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") tm ");
			sb.append("group by tm.min1,tm.standard_number ");
			sb.append(") tmp");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String statDeviceStatus1(Integer type, String sns[], String organSN) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {

		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select standard_number,type,rec_time,max(status),max(comm_status),organ, CONCAT(EXTRACT(YEAR FROM rec_time),EXTRACT(MONTH FROM rec_time),EXTRACT(DAY FROM rec_time)) as min1 from device_status");
			sb.append(" where rec_time >= ?");
			sb.append(" and rec_time < ?");
			if (null != type) {
				sb.append(" and type = ?");
			}
			if (null != sns && sns.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sns.length; i++) {
					if (i != sns.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			if (StringUtils.isNotBlank(organSN)) {
				sb.append(" and organ = ?");
			}
			sb.append(" group by min1, standard_number limit ?, ?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	/**
	 * 删除太能电池下指定的设备关联关系
	 * 
	 * @param size
	 *            设备的数量
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:58:40
	 */
	public String deleteSolarDevices(int size) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from r_device_solar where solar_id = ? and type = ? and device_id in (");
		for (int i = 0; i < size; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public String deleteSolarDevice() {
		return "delete from r_device_solar where solar_id = ?";
	}

	public String listDeviceSolar(String[] organIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sb.id,sb.name,sb.standard_number,sb.navigation,sb.stake_number,sb.battery_capacity,rds.device_id,rds.type from sv_solar_battery sb inner join (select * from r_device_solar where solar_id in (select id from sv_solar_battery)) rds on sb.id = rds.solar_id and sb.organ_id in(");
		for (int i = 0; i < organIds.length; i++) {
			if (i != organIds.length - 1) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}
		return sb.toString();
	}

	public String listDeviceSolar() {
		return "select sb.id,sb.name,sb.standard_number,sb.navigation,sb.stake_number,sb.battery_capacity,r.device_id,r.type from r_device_solar r inner join sv_solar_battery sb on r.solar_id = sb.id";
	}

	public String listRoleBTPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_box_transformer d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleViDPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_vi_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleRoadDPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_road_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listRoleBridgeDPermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_bridge_detector d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String deleteSubVehicleDetector() {
		return "delete from tm_vehicle_detector where parent_id = ?";
	}

	public String getVdStatByHour(String sn[]) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (select c.*, ROWNUM RN from (select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal,b.date_time, avg(up_headway), avg(dw_headway) from (select a.*,to_char(rec_time,'YYYY-mm-dd hh24')||':00' as date_time from vehicle_detector a where rec_time between ? and ? and type = '1'");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by date_time,standard_number order by date_time desc) c where ROWNUM < ? ) where RN >=?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (select  standard_number,avg(up_occs) as up_occs, avg(dw_occs) as dw_occs, avg(up_occm) as up_occm, avg(dw_occm) as dw_occm, avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal, concat(date_format(rec_time,'%Y-%m-%d %k'),':00') as date_time, avg(up_headway), avg(dw_headway) from vehicle_detector where rec_time between ? and ? and type ='1' group by date_time , standard_number) a ");
			if (null != sn && sn.length > 0) {
				sb.append(" where a.standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(" limit ? , ?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String vdStatByHourTotal(String[] sn) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (select standard_number, b.date_time from (select a.*,to_char(rec_time,'hh24') as date_time from vehicle_detector a where rec_time between ? and ? and type = '1'");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by date_time,standard_number) c");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (select  standard_number,date_format(rec_time,'%k') as date_time from vehicle_detector where rec_time between ? and ? and type = '1' group by date_time ,standard_number) a");
			if (null != sn && sn.length > 0) {
				sb.append(" where a.standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String getVdStatByDay(String sn[]) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (select c.*, ROWNUM RN from (select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal,b.date_time, avg(up_headway), avg(dw_headway) from (select a.*,to_char(rec_time,'YYYY-mm-dd') as date_time from vehicle_detector a where rec_time between ? and ? and type = '1'");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by date_time,standard_number order by date_time desc) c where ROWNUM < ? ) where RN >=?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (select  standard_number,avg(up_occs) as up_occs, avg(dw_occs) as dw_occs, avg(up_occm) as up_occm, avg(dw_occm) as dw_occm, avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal, date_format(rec_time,'%Y-%m-%d') as date_time, avg(up_headway), avg(dw_headway) from vehicle_detector where rec_time between ? and ? and type ='1' group by date_time , standard_number) a ");
			if (null != sn && sn.length > 0) {
				sb.append(" where a.standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(" limit ? , ?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String vdStatByYear() {
		String s = "";
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			s = "select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal,b.date_time from (select a.*,to_char(rec_time,'YYYYmmdd') as date_time from vehicle_detector a where rec_time between ? and ? and type = '1' and comm_status = 0) b group by date_time,standard_number order by date_time desc";
		} else if (Configuration.MYSQL.equals(dbName)) {
			s = "select  standard_number,avg(up_occs) as up_occs, avg(dw_occs) as dw_occs, avg(up_occm) as up_occm, avg(dw_occm) as dw_occm, avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal, date_format(rec_time,'%Y%m%d') as date_time from vehicle_detector where rec_time between ? and ? and type = '1' and comm_status = 0 group by date_time ,standard_number";
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return s;
	}

	public String getVdStatByYear(String[] sn) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getCmsDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (select c.*, ROWNUM RN from (select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occavg),avg(dw_occavg), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speedavg),avg(dw_speedavg), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_fluxavg), avg(dw_fluxavg), sum(up_fluxtotal) as up_fluxTotal,sum(dw_fluxtotal) as dw_fluxTotal,b.date_month from (select a.*,substr(date_time, 0,6) as date_month from tm_sync_vehicle_detector a where date_time between ? and ?");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by date_month,standard_number order by date_month desc) c where ROWNUM <= ? ) where RN >= ?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occavg),avg(dw_occavg), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speedavg),avg(dw_speedavg), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_fluxavg), avg(dw_fluxavg), sum(up_fluxtotal) as up_fluxTotal,sum(dw_fluxtotal) as dw_fluxTotal,date_format(date_time,'%Y%m') as date_month from tm_sync_vehicle_detector as a where date_time between ? and ? group by date_month,standard_number ) as b");
			if (null != sn && sn.length > 0) {
				sb.append(" where standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(" limit ? , ?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String vdStatByYearTotal(String[] sn) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getCmsDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select count(*) from (select standard_number,b.date_month from (select a.*,substr(date_time, 0,6) as date_month from tm_sync_vehicle_detector a where date_time between ? and ? ");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by date_month,standard_number order by date_month desc) c");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select count(*) from (select *,date_format(date_time,'%Y%m') as date_month from tm_sync_vehicle_detector where date_time between ? and ? group by date_month,standard_number) as a");
			if (null != sn && sn.length > 0) {
				sb.append(" where standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}
		return sb.toString();
	}

	public String listRoleUrgentPhonePermissions() {
		return "select r.resource_id as resourceId,r.resource_type as resourceType,r.privilege as privilege,d.name as resourceName,o.id as organId,o.name as organName from r_role_resource_permission r left join tm_urgent_phone d on r.resource_id = d.id left join sv_organ o on d.organ_id = o.id where r.role_id = ? and r.resource_id = ? and r.resource_type = ? ";
	}

	public String listSNByCamera() {
		return "select standard_number from sv_device where type = '2'";
	}

	public String listSubVd(String organs[], String name) {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.* from sv_sub_platform_resource d inner join (select b.* from sv_organ a inner join sv_sub_platform_resource b on a.standard_number = b.standard_number");
		if (organs.length > 0) {
			sb.append(" and a.id in (");
			for (int i = 0; i < organs.length; i++) {
				if (i != organs.length - 1) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
			}
		}
		sb.append(") c on d.parent_id = c.id and d.type = 10");
		if (StringUtils.isNotBlank(name)) {
			sb.append(" and d.name like ?");
		}
		return sb.toString();
	}

	public String deleteWorkPlan() {
		return "delete from em_work_plan where type = ?";
	}

	public String listCmsSN(String[] organs, String cmsName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select standard_number from tm_control_device where type=17 ");
		if (organs.length > 0) {
			sb.append(" and organ_id in (");
			for (int i = 0; i < organs.length; i++) {
				if (i != organs.length - 1) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
			}
		}
		if (StringUtils.isNotBlank(cmsName)) {
			sb.append(" and name like ?");
		}
		return sb.toString();
	}

	public String listCms(String[] cmsSNs) {
		StringBuffer sb = new StringBuffer();
		sb.append("select name,standard_number from tm_control_device where type=17 ");
		if (cmsSNs.length > 0) {
			sb.append(" and standard_number in (");
			for (int i = 0; i < cmsSNs.length; i++) {
				if (i != cmsSNs.length - 1) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 统计给定任意时间段内的车检器数据总量
	 * 
	 * @param sn
	 *            设备sn数组
	 * @return
	 */
	public String getVdStat(String sn[]) {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getDasDbName();
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("select * from (select c.*, ROWNUM RN from (select standard_number,avg(up_occs), avg(dw_occs), avg(up_occm), avg(dw_occm), avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal,min(b.date_time), avg(up_headway), avg(dw_headway) from (select a.*,to_char(rec_time,'YYYY-mm-dd') as date_time from vehicle_detector a where rec_time between ? and ? and type = '1'");
			if (null != sn && sn.length > 0) {
				sb.append(" and standard_number in (");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(") b group by standard_number order by standard_number) c where ROWNUM < ? ) where RN >=?");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("select * from (select  standard_number,avg(up_occs) as up_occs, avg(dw_occs) as dw_occs, avg(up_occm) as up_occm, avg(dw_occm) as dw_occm, avg(up_occb),avg(dw_occb),avg(up_occ),avg(dw_occ), avg(up_speeds),avg(dw_speeds), avg(up_speedm),avg(dw_speedm),avg(up_speedb),avg(dw_speedb), avg(up_speed),avg(dw_speed), sum(up_fluxs),sum(dw_fluxs), sum(up_fluxm),sum(dw_fluxm),sum(up_fluxb),sum(dw_fluxb), avg(up_flux), avg(dw_flux), sum(up_flux) as up_fluxTotal,sum(dw_flux) as dw_fluxTotal, min(rec_time)as date_time, avg(up_headway), avg(dw_headway) from vehicle_detector where rec_time between ? and ? and type ='1' group by standard_number) a ");
			if (null != sn && sn.length > 0) {
				sb.append(" where a.standard_number in(");
				for (int i = 0; i < sn.length; i++) {
					if (i != sn.length - 1) {
						sb.append("?,");
					} else {
						sb.append("?)");
					}
				}
			}
			sb.append(" limit ? , ?");
		} else {
			throw new BusinessException(ErrorCode.NOT_SUPPORT_DATABASE,
					"Database " + dbName + "is not supported !");
		}

		return sb.toString();
	}

	public String delete(String table) {
		return "delete from " + table;
	}

	public String deleteBySN(List<?> entity, String table) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(table);
		sb.append(" where standard_number in (");
		for (int i = 0; i < entity.size(); i++) {
			if (i != entity.size() - 1) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}
		return sb.toString();
	}

	public String mapUserImage() {
		return "select user_id, id from sv_image_resource";
	}

	public String deleteDeviceStatusReal(String standardNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from device_status_real where 1=1");
		if (StringUtils.isNotBlank(standardNumber)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteCmsReal(String standardNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from cms_real where 1=1");
		if (StringUtils.isNotBlank(standardNumber)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteVdReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from vehicle_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteWstReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from weather_stat_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteCoviReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from covi_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteNodReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from no_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteLoliReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from loli_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteCdReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from control_device_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteWsReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from wind_speed_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteTslReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from traffic_sign_light_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteLilReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from lane_indicate_light_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String topRealPlay() {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.total, a.target_id, b.name, b.standard_number, b.type, b.sub_type, b.parent_id, b.manufacturer_id, b.location, b.channel_number, b.transport, b.access_mode ");
		sb.append("from (select count(*) total, target_id from sv_sys_log where operation_code = '4101' group by target_id) a, sv_device b ");
		sb.append("where a.target_id = b.id ");
		sb.append("union ");
		sb.append("select a.total, a.target_id, b.name, b.standard_number, b.type, b.ptz_type, b.parent_id, b.manufacturer, b.address, '1', 'TCP', 'compatible' ");
		sb.append("from (select count(*) total, target_id from sv_sys_log where operation_code = '4101' group by target_id) a, sv_sub_platform_resource b ");
		sb.append("where a.target_id = b.id ");
		sb.append("order by total desc");
		return sb.toString();
	}

	public String listCameraIdInOrgan() {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.id from sv_device d left join sv_organ o on d.organ_id = o.id ");
		sb.append("where d.type = '2' ");
		sb.append("and o.path like ? ");
		return sb.toString();
	}

	public String listRoleResourceId(List<String> resourceType) {
		StringBuffer sb = new StringBuffer();
		sb.append("select resource_id from r_role_resource_permission where role_id = ? ");
		sb.append("and resource_type in (");
		for (String type : resourceType) {
			sb.append("'");
			sb.append(type);
			sb.append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public String deleteViDetectorReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from vi_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteRdReal(String sn) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from road_detector_real where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sb.append(" and standard_number = ?");
		}
		return sb.toString();
	}

	public String deleteCmsRealByCmsIds(String[] cmsIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from cms_real where 1=1");
		if (cmsIds.length > 0) {
			sb.append(" and id in (");
			for (int i = 0; i < cmsIds.length; i++) {
				if (i != cmsIds.length - 1) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
			}
		}
		return sb.toString();
	}

	public String deleteByfavoriteId() {
		return "delete from r_user_device_favorite where favorite_id = ?";
	}

	public String listDeviceAlarmNumber(Long beginTime, Long endTime) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id),standard_number from sv_device_alarm where 1=1 ");
		if (null != beginTime && null != endTime) {
			sb.append(" and alarm_time between ? and ? ");
		}
		sb.append("group by standard_number");
		return sb.toString();
	}

	public String listVdFluxByMonth() {
		StringBuffer sb = new StringBuffer();
		String dbName = Configuration.getInstance().getCmsDbName();
		sb.append("select sn, mon, SUM(up_fluxtotal), SUM(dw_fluxtotal) ");
		sb.append("from ( select standard_number as sn, ");
		if (Configuration.ORACLE.equals(dbName)) {
			sb.append("substr(date_time, 1, 6) ");
		} else if (Configuration.MYSQL.equals(dbName)) {
			sb.append("substring(date_time, 1, 6) ");
		}
		sb.append("as mon, up_fluxtotal, dw_fluxtotal ");
		sb.append("from tm_sync_vehicle_detector) tmp group by tmp.sn, tmp.mon");
		return sb.toString();
	}

	public String listLocalCameraStatus() {
		return "select d.standard_number, o.id from sv_device d left outer join sv_device_online_real o on d.standard_number = o.standard_number where d.type = '2'";
	}

	public String deleteGPSDevices(int length) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from r_device_gps where gps_id = ? and type = ? and device_id in (");
		for (int i = 0; i < length; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public String deleteGPSDevice() {
		return "delete from r_device_gps where gps_id = ?";
	}

	public String listRoleSubResource(String parentId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sub.* from sv_sub_platform_resource sub inner join r_role_resource_permission r on sub.id = r.resource_id ");
		sb.append("where r.role_id = ? ");
		sb.append("and r.resource_type = '300' ");
		if (StringUtils.isNotBlank(parentId)) {
			sb.append("and sub.parent_id = ? ");
		}
		return sb.toString();
	}

	public String listRoleRoots() {
		StringBuffer sb = new StringBuffer();
		sb.append("select sub.* from sv_sub_platform_resource sub inner join r_role_resource_permission r on sub.id = r.resource_id ");
		sb.append("where r.role_id = ? ");
		sb.append("and r.resource_type = '300' ");
		sb.append("and sub.parent_id is null ");
		return sb.toString();
	}

	public String listRoleSubOrgan() {
		StringBuffer sb = new StringBuffer();
		sb.append("select sub.* from sv_sub_platform_resource sub inner join r_role_resource_permission r on sub.id = r.resource_id ");
		sb.append("where r.role_id = ? ");
		sb.append("and r.resource_type = '300' ");
		sb.append("and sub.parent_id = ? ");
		sb.append("and sub.type in ('0','1','100','110','120','121','122') ");
		return sb.toString();
	}

	public String insertFd() {
		return "insert into tm_fire_detector (id,name,organ_id,standard_number,das_id,period,navigation,stake_number,ip,port,create_time,longitude,latitude,reserve,note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String insertLight() {
		return "insert into tm_control_device (id,name,organ_id,standard_number,das_id,period,navigation,stake_number,ip,port,create_time,longitude,latitude,reserve,note,sub_type,section_type,type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String insertPb() {
		return "insert into tm_push_button (id,name,organ_id,standard_number,das_id,period,navigation,stake_number,ip,port,create_time,longitude,latitude,reserve,note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String insertCd() {
		return "insert into tm_control_device (id,name,organ_id,standard_number,das_id,period,navigation,stake_number,ip,port,create_time,longitude,latitude,reserve,note,type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
	
	public String countCmsPublishLog(String[] cmsSNs, String userName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ( ");
		sb.append("select distinct send_time, standard_number from tm_cms_publish_log ");
		sb.append("where standard_number in (");
		for (int i = 0; i < cmsSNs.length; i ++) {
			sb.append("?,");
		}
		// 移除最后一个逗号
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") and send_time between ? and ? ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append("and send_user_name like ? ");
		}
		sb.append(") temp");
		
		return sb.toString();
	}
}
