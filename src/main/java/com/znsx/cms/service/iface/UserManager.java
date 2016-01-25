package com.znsx.cms.service.iface;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.znsx.cms.aop.annotation.LogMethod;
import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.model.LogOperation;
import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.persistent.model.User;
import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.AndroidUpdate;
import com.znsx.cms.service.model.GetUserVO;
import com.znsx.cms.service.model.GisLogonVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;
import com.znsx.cms.service.model.ListUserSessionHistoryVO;
import com.znsx.cms.service.model.ResourceVO;
import com.znsx.cms.service.model.UserLogonVO;
import com.znsx.cms.service.model.UserOperationLogVO;
import com.znsx.cms.service.model.UserSessionVO;
import com.znsx.cms.service.model.UserViewVO;
import com.znsx.cms.web.dto.omc.UserLoginDTO;

/**
 * UserManager
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:56:39
 */
public interface UserManager extends BaseManager {
	// /**
	// * 用户会话更新时间缓存
	// */
	// public static final Map<String, Long> sessionMap = new Hashtable<String,
	// Long>();

	public static final String REGION = "sessionCache";
	/**
	 * 用户会话过期时间毫秒数，默认4分钟
	 */
	public static final long SESSION_EXPIRE_TIME = 240000L;

	/**
	 * 
	 * csLogin CS客户端用户登录
	 * 
	 * @param userName
	 *            登录名称
	 * @param passwd
	 *            登录密码
	 * @param ip
	 *            客户端IP
	 * @param clientType
	 *            客户端类型
	 * @return UserLogonDTO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:31:57 客户端IP
	 */
	public String csLogin(String userName, String passwd, String ip,
			String clientType);

	/**
	 * 
	 * createFavorite 创建用户收藏夹
	 * 
	 * @param favoriteName
	 *            收藏夹名称
	 * @param channelIds
	 *            摄像头ID
	 * @param userId
	 *            用户ID
	 * @return favoriteId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:36:43
	 */
	@LogMethod(targetType = "UserFavorite", operationType = "create", name = "创建收藏夹", code = "1003")
	public String createFavorite(@LogParam("name") String favoriteName,
			List<String> channelIds, String userId);

	/**
	 * 
	 * updateFavorite 更新收藏夹
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @param favoriteName
	 *            收藏夹名称
	 * @param channelIds
	 *            摄像头ID
	 * @param userId
	 *            用户ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:38:57
	 */
	@LogMethod(targetType = "UserFavorite", operationType = "update", name = "修改收藏夹", code = "1004")
	public void updateFavorite(@LogParam("id") String favoriteId,
			@LogParam("name") String favoriteName, List<String> channelIds,
			String userId);

	/**
	 * 
	 * deleteFavorite 删除收藏夹
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:41:20
	 */
	@LogMethod(targetType = "UserFavorite", operationType = "delete", name = "删除收藏夹", code = "1005")
	public void deleteFavorite(@LogParam("id") String favoriteId);

	/**
	 * 
	 * listFavorite 查询收藏夹
	 * 
	 * @param userId
	 *            用户ID
	 * 
	 * @return FavoriteVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午7:59:20
	 */
	public Element listFavorite(String userId) throws Exception;

	/**
	 * OMC客户端登陆
	 * 
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码,md5加密
	 * @param ip
	 *            客户端IP
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:47:12
	 */
	public UserLoginDTO omcLogin(String userName, String password, String ip)
			throws BusinessException;

	/**
	 * 创建用户
	 * 
	 * @param name
	 *            姓名
	 * @param standardNumber
	 *            用户标准号
	 * @param ccsId
	 *            通信服务器ID
	 * @param logonName
	 *            登陆名
	 * @param password
	 *            登陆密码, MD5加密方式
	 * @param sex
	 *            性别. 0-女, 1-男.
	 * @param email
	 *            邮箱地址
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param organId
	 *            用户所属机构ID
	 * @param priority
	 *            用户优先级. 值越大优先级越高
	 * @param note
	 *            备注
	 * @param maxConnect
	 *            最大登陆数量
	 * @return 创建成功生成的用户ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:46:37
	 */
	@LogMethod(targetType = "User", operationType = "create", name = "创建用户", code = "2002")
	public String createUser(@LogParam("name") String name,
			String standardNumber, String ccsId, String logonName,
			String password, Short sex, String email, String phone,
			String address, String organId, Short priority, String note,
			Integer maxConnect) throws BusinessException;

	/**
	 * 给用户关联图片.在用户和图片创建后调用此接口
	 * 
	 * @param userId
	 *            用户ID
	 * @param imageId
	 *            图片ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午9:18:58
	 */
	public void bindImage(String userId, String imageId)
			throws BusinessException;

	/**
	 * 检查用户会话
	 * 
	 * @param sessionTicket
	 *            用户会话ticket
	 * @return 该会话对应的登录对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 上午11:05:19
	 */
	public ResourceVO checkSession(String sessionTicket)
			throws BusinessException;

	/**
	 * 设置用户角色关联
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleIds
	 *            角色ID列表,多个ID以逗号间隔开. 如: 1,3,4
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:34:26
	 */
	@LogMethod(targetType = "User", operationType = "bindRole", name = "设置用户角色", code = "2075")
	public void bindUserRoles(@LogParam("id") String userId, String roleIds)
			throws BusinessException;

	/**
	 * 修改用户
	 * 
	 * @param id
	 *            用户ID
	 * @param name
	 *            姓名
	 * @param standardNumber
	 *            用户标准号
	 * @param ccsId
	 *            通信服务器ID
	 * @param password
	 *            登陆密码, MD5加密方式
	 * @param sex
	 *            性别. 0-女, 1-男.
	 * @param email
	 *            邮箱地址
	 * @param phone
	 *            联系电话
	 * @param address
	 *            联系地址
	 * @param organId
	 *            用户所属机构ID
	 * @param priority
	 *            用户优先级. 值越大优先级越高
	 * @param note
	 *            备注
	 * @param status
	 *            1-启用，0-停用
	 * @param maxConnect
	 *            最大登陆数量
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:12:05
	 */
	@LogMethod(targetType = "User", operationType = "update", name = "修改用户", code = "2003")
	public void updateUser(@LogParam("id") String id,
			@LogParam("name") String name, String standardNumber, String ccsId,
			String password, Short sex, String email, String phone,
			String address, String organId, Short priority, String note,
			Short status, Integer maxConnect) throws BusinessException;

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:57:36
	 */
	@LogMethod(targetType = "User", operationType = "delete", name = "删除用户", code = "2004")
	public void deleteUser(@LogParam("id") String id) throws BusinessException;

	/**
	 * 
	 * 创建用户播放方案
	 * 
	 * @param playSchemeName
	 *            播放方案名称
	 * @param playScheme
	 *            播放方案动作
	 * @param userId
	 *            用户ID
	 * @return String
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:35:16
	 */
	@LogMethod(targetType = "PlayScheme", operationType = "create", name = "创建播放方案", code = "1007")
	public String createPlayScheme(@LogParam("name") String name,
			Element playScheme, String userId);

	/**
	 * 根据ID查询用户
	 * 
	 * @param id
	 *            用户ID
	 * @return 用户详细信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:22:34
	 */
	public GetUserVO getUser(String id) throws BusinessException;

	/**
	 * 根据机构查询用户列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param userName
	 *            用户名称条件,模糊查询
	 * @param logonName
	 *            登陆名称条件,模糊查询
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            查询总条数
	 * @return 用户列表
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:42:30
	 */
	public List<GetUserVO> listUser(String organId, String userName,
			String logonName, Integer startIndex, Integer limit)
			throws BusinessException;

	/**
	 * 
	 * 更新播放方案
	 * 
	 * @param playSchemeId
	 *            播放方案ID
	 * @param name
	 *            播放方案名称
	 * @param userId
	 *            用户Id
	 * @param playScheme
	 *            播放方案动作
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:21:09
	 */
	@LogMethod(targetType = "PlayScheme", operationType = "update", name = "修改播放方案", code = "1008")
	public void updatePlayScheme(@LogParam("id") String playSchemeId,
			@LogParam("name") String name, Element playScheme, String userId);

	/**
	 * 
	 * 删除播放方案
	 * 
	 * @param id
	 *            播放方案ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:38:50
	 */
	@LogMethod(targetType = "PlayScheme", operationType = "delete", name = "删除播放方案", code = "1009")
	public void deletePlayScheme(@LogParam("id") String id);

	/**
	 * 
	 * 查询所有播放方案
	 * 
	 * @param userId
	 *            用户ID
	 * 
	 * @return Element
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:08:58
	 */
	public Element listPlayScheme(String userId) throws JDOMException,
			IOException;

	/**
	 * 
	 * 根据用户ID查询权限
	 * 
	 * @param id
	 *            用户ID
	 * @return UserViewVO
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:20:23
	 */
	public UserViewVO getPermissionsByUserId(String id);

	/**
	 * 
	 * 查询机构下的在线用户列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param logonName
	 *            登录名称，模糊查询
	 * @param name
	 *            真实姓名，模糊查询
	 * @param startIndex
	 *            开始行数
	 * @param limit
	 *            总行数
	 * @return 在线用户列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:19:30
	 */
	public List<ListOnlineUsersVO> listOnlineUser(String organId,
			String logonName, String name, Integer startIndex, Integer limit);

	/**
	 * 统计机构下的在线用户数量
	 * 
	 * @param organId
	 *            机构ID
	 * @param logonName
	 *            登录名称，模糊查询
	 * @param name
	 *            真实姓名，模糊查询
	 * @return 在线用户数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-8 下午3:10:04
	 */
	public int countOnlineUser(String organId, String logonName, String name);

	/**
	 * 统计平台中的用户数量
	 * 
	 * @return 平台中的用户数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:01:35
	 */
	public int countUser();

	/**
	 * 
	 * 查询用户会话历史记录
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始行数
	 * @param limit
	 *            总行数
	 * @param logonUserId
	 *            登录用户ID
	 * @return List<ListUserSessionHistoryVO>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:49:36
	 */
	public List<ListUserSessionHistoryVO> listUserSessionHistory(String userId,
			String userName, String organId, Long startTime, Long endTime,
			Integer startIndex, Integer limit, String logonUserId);

	/**
	 * 
	 * 用户登出
	 * 
	 * @param sessionId
	 *            用户会话ID
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:28:25
	 */
	public void userLogoff(String sessionId);

	/**
	 * 
	 * 返回查询历史会话总数
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户名称
	 * @param organId
	 *            机构ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param logonUserId
	 *            登录用户ID
	 * @return
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:20:57
	 */
	public int selectTotalCount(String userId, String userName, String organId,
			Long startTime, Long endTime, String logonUserId);

	/**
	 * 生成指定对象的下一个标准号
	 * 
	 * @param className
	 *            指定对象的名称,对应于Hibernate配置里面的ClassName
	 * @param organId
	 *            对象所属的机构ID,如果对象
	 * @return 对象的下一个标准号
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午4:43:40
	 */
	public String generateStandardNum(String className, String organId);

	/**
	 * 
	 * 查询系统日志码表列表
	 * 
	 * @return List<LogOperation>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午1:53:10
	 */
	public List<LogOperation> listLogOperation();

	/**
	 * 
	 * 判断是否是根机构管理员
	 * 
	 * @param id
	 *            用户ID
	 * @return Boolean
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:37:26
	 */
	public Boolean isAdmin(String id);

	/**
	 * 
	 * 系统管理员查询系统日志列表
	 * 
	 * @param resourceName
	 *            操作人名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param targetName
	 *            被查询的名称
	 * @param operationCode
	 *            操作编码
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @return List<SysLog>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:53:24
	 */
	public List<SysLog> listSysLogByAdmin(String resourceName, Long startTime,
			Long endTime, Integer startIndex, Integer limit, String targetName,
			String operationCode, String operationType, String resourceType);

	/**
	 * 
	 * 普通人员查询系统日志列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param resourceName
	 *            操作人名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @param targetName
	 *            被查询名称
	 * @param operationCode
	 *            操作编码
	 * @param logonUserId
	 *            登录用户ID
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @param type
	 *            用户选择查询类型 1：客户端日志 2：服务器日志
	 * @return List<SysLog>
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:35:16
	 */
	public List<SysLog> listSysLog(String organId, String resourceName,
			Long startTime, Long endTime, Integer startIndex, Integer limit,
			String targetName, String operationCode, String logonUserId,
			String operationType, String resourceType, String type);

	/**
	 * 
	 * 查询系统日志列表数量
	 * 
	 * @param organId
	 *            机构ID
	 * @param resourceName
	 *            操作人员名称
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param targetName
	 *            被操作名称
	 * @param operationCode
	 *            操作编码
	 * @param operationType
	 *            操作类型
	 * @param resourceType
	 *            日志来源
	 * @param type
	 *            用户选择查询类型 1：客户端日志 2：服务器日志
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午3:50:05
	 */
	public Integer findTotalCount(String organId, String resourceName,
			Long startTime, Long endTime, String targetName,
			String operationCode, String operationType, String resourceType,
			String Type);

	/**
	 * 根据用户登录名称查询用户详细信息
	 * 
	 * @param logonName
	 *            用户登录名称
	 * @param ip
	 *            客户端请求的IP地址
	 * @return 用户详细信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:59:39
	 */
	public UserLogonVO getUserByName(String logonName, String ip);

	/**
	 * 根据中心config.properties配置的路由信息返回CCS的IP
	 * 
	 * @param logonName
	 *            用户登录名称
	 * @param ip
	 *            客户端请求的中心IP
	 * @return 用户详细信息
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-19 下午4:43:38
	 */
	public UserLogonVO getUserUseConfig(String logonName, String ip);

	/**
	 * 定时用户会话检查
	 * 
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:14:42
	 */
	public void regularSessionCheck();

	/**
	 * 
	 * 查询用户总计数
	 * 
	 * @param organId
	 *            机构ID
	 * @param name
	 *            用户名称
	 * @param logonName
	 *            登录名称
	 * @return 总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:02:33
	 */
	public Integer userTotalCount(String organId, String name, String logonName);

	/**
	 * 批量生成一组标准号
	 * 
	 * @param className
	 *            对象名称，对应hiberate配置中的className
	 * @param organId
	 *            对象所属机构ID
	 * @param size
	 *            要生成的数量
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午6:01:41
	 */
	public String[] batchGenerateSN(String className, String organId, int size);

	/**
	 * 
	 * 根据用户ID查询在线用户列表
	 * 
	 * @param userId
	 *            用戶ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 用户在线会话列表
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:15:19
	 */
	public List<ListOnlineUsersVO> listOnlineUserByUserId(String userId,
			Integer startIndex, Integer limit);

	/**
	 * 统计指定用户的在线数量
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户的在线数量
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-8 下午2:56:43
	 */
	public int countOnlineUserByUserId(String userId);

	/**
	 * 
	 * 生成一条session记录
	 * 
	 * @param id
	 *            服务器ID
	 * @param name
	 *            服务器名称
	 * @param standardNumber
	 *            标准号
	 * @param lanIp
	 *            内网IP
	 * @param type
	 *            服务器类型
	 * @return sessionId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午4:26:22
	 */
	public String createUserSession(String id, String name,
			String standardNumber, String lanIp, String type);

	/**
	 * 会话心跳，包括CCS,PTS和OMC心跳
	 * 
	 * @param sessionId
	 *            会话ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:15:46
	 */
	public void heartbeat(String sessionId) throws BusinessException;

	/**
	 * 修改用户个人信息
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            用户密码
	 * @param name
	 *            用户名称
	 * @param sex
	 *            性别
	 * @param email
	 *            电子邮件
	 * @param phone
	 *            电话
	 * @param address
	 *            联系地址
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-21 上午8:58:25
	 */
	@LogMethod(targetType = "User", operationType = "update", name = "修改用户个人信息", code = "1022")
	public void updateUserInfo(@LogParam("id") String id, String password,
			@LogParam("name") String name, Short sex, String email,
			String phone, String address) throws BusinessException;

	/**
	 * 强制下线
	 * 
	 * @param userSessionId
	 *            用户会话ID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-6-25 下午1:50:02
	 */
	public void forceLogoff(String userSessionId) throws BusinessException;

	/**
	 * 统计当前客户端的用户登录数量
	 * 
	 * @return 当前客户端的用户登录数量
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-11 上午10:22:13
	 */
	public int countClientSession() throws BusinessException;

	/**
	 * 
	 * mcu客户端登录
	 * 
	 * @param userName
	 *            用户名
	 * @param passwd
	 *            密码
	 * @param ip
	 *            客户端IP地址
	 * @return sessionId
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午10:37:00
	 */
	public String mcuLogin(String userName, String passwd, String ip);

	/**
	 * 获取地图ip端口和wmsurl
	 * 
	 * @return 地图信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午4:28:18
	 */
	public GisLogonVO getGisServer();

	/**
	 * 返回excel
	 * 
	 * @param listLog
	 *            日志列表
	 * @return wb
	 */
	public Workbook exportExcelLog(List<SysLog> listLog);

	/**
	 * 根据用户编号查询用户对象
	 * 
	 * @param sn
	 *            用户编号
	 * @return 用户对象
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-24 下午2:08:09
	 */
	public User getUserBySn(String sn);

	/**
	 * 添加收藏夹设备
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @param cameraId
	 *            摄像头ID, 可以是摄像头的ID也可以是下级资源的ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-14 上午9:57:37
	 */
	@LogMethod(targetType = "UserFavorite", operationType = "update", name = "添加收藏夹设备", code = "1018")
	public void addFavoriteDevice(@LogParam("id") String favoriteId,
			String cameraId);

	/**
	 * 删除收藏夹设备
	 * 
	 * @param favoriteId
	 *            收藏夹ID
	 * @param cameraId
	 *            摄像头ID, 可以是摄像头的ID也可以是下级资源的ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-14 上午9:57:28
	 */
	@LogMethod(targetType = "UserFavorite", operationType = "update", name = "删除收藏夹设备", code = "1032")
	public void deleteFavoriteDevice(@LogParam("id") String favoriteId,
			String cameraId);

	/**
	 * 查询所有在线的用户会话
	 * 
	 * @return 所有在线的用户会话
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-6-9 下午3:23:14
	 */
	public List<UserSessionVO> listOnlineUser();

	/**
	 * 从配置文件读取安卓版本号和地址
	 * 
	 * @return AndroidUpdate
	 */
	public AndroidUpdate getAndroidConfig();

	/**
	 * 
	 * 统计用户操作日志数量
	 * 
	 * @param operationType
	 *            操作类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 数量
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 上午10:10:50
	 */
	public Integer findTotalCount(String operationType, Long beginTime,
			Long endTime);

	/**
	 * 条件查询用户操作日志
	 * 
	 * @param operationType
	 *            操作类型
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 操作日志集合
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 上午10:40:34
	 */
	public Element listUserOperationLog(String operationType, Long beginTime,
			Long endTime, Integer startIndex, Integer limit);

	/**
	 * 
	 * 保存用户操作日志
	 * 
	 * @param resourceId
	 *            用户ID
	 * @param operationName
	 *            操作内容
	 * @param operationType
	 *            操作类型
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015-9-17 下午2:50:12
	 */
	public void saveUserOperationLog(String resourceId, String operationName,
			String operationTypeModel);
}
