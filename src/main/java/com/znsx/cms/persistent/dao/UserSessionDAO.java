package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.UserSession;
import com.znsx.cms.persistent.model.UserSessionCcs;
import com.znsx.cms.persistent.model.UserSessionCrs;
import com.znsx.cms.persistent.model.UserSessionDas;
import com.znsx.cms.persistent.model.UserSessionDws;
import com.znsx.cms.persistent.model.UserSessionEns;
import com.znsx.cms.persistent.model.UserSessionMss;
import com.znsx.cms.persistent.model.UserSessionPts;
import com.znsx.cms.persistent.model.UserSessionRms;
import com.znsx.cms.persistent.model.UserSessionRss;
import com.znsx.cms.persistent.model.UserSessionSrs;
import com.znsx.cms.persistent.model.UserSessionUser;
import com.znsx.cms.service.model.CcsUserSessionVO;
import com.znsx.cms.service.model.ListOnlineUsersVO;

/**
 * 用户会话数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 上午8:56:50
 */
public interface UserSessionDAO extends BaseDAO<UserSession, String> {

	/**
	 * 
	 * 根据用户ID查询用户会话
	 * 
	 * @param userId
	 *            用戶ID
	 * @param startIndex
	 *            开始查询条数
	 * @param limit
	 *            需要查询条数
	 * @return 用户会话
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:30:02
	 */
	public List<UserSessionUser> findUserSessionByUserId(String userId,
			Integer startIndex, Integer limit);

	/**
	 * 
	 * 用户会话总计数
	 * 
	 * @param userId
	 *            用户ID
	 * @return Integer
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午2:44:04
	 */
	public Integer userSessionTotalCount(String userId);

	/**
	 * 复制指定用户的所有会话到历史会话中，删除用户时登出所有的用户会话用到
	 * 
	 * @param userId
	 *            用户ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午1:26:17
	 */
	public void copyUserSessionToHistory(String userId);

	/**
	 * 删除用户的所有会话
	 * 
	 * @param userId
	 *            用户ID
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:23:10
	 */
	public void deleteByUser(String userId);

	/**
	 * 
	 * 查询在线用户总数
	 * 
	 * @param organIds
	 *            当前机构以及子机构
	 * @return 在线用户总计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午11:16:20
	 */
	public Integer onlineUserTotal(String[] organIds);

	/**
	 * 获取由CMS维护的会话，包括PTS,CCS和OMC的会话
	 * 
	 * @return CMS维护的会话
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:49:36
	 */
	public List<UserSession> listCMSMaintainSession();

	/**
	 * 获取机构及所有子机构下登录的用户会话列表
	 * 
	 * @param organId
	 *            机构ID
	 * @param logonName
	 *            登录名称，模糊查询
	 * @param name
	 *            真实姓名，模糊查询
	 * @param start
	 *            查询起始行，从0开始
	 * @param limit
	 *            要查询的行数
	 * @return 机构及所有子机构下登录的用户会话列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午5:15:03
	 */
	public List<ListOnlineUsersVO> listOrganOnlineUser(String organId,
			String logonName, String name, int start, int limit);

	/**
	 * 统计机构及所有子机构下登录的用户会话数量
	 * 
	 * @param organId
	 *            机构ID
	 * @param logonName
	 *            登录名称，模糊查询
	 * @param name
	 *            真实姓名，模糊查询
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午6:27:42
	 */
	public Integer countOrganOnlineUser(String organId, String logonName,
			String name);

	/**
	 * 获取CCS管辖的用户会话列表
	 * 
	 * @param ccsId
	 *            CCS的ID
	 * @return 用户会话列表
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午8:48:27
	 */
	public List<CcsUserSessionVO> listCcsUserSession(String ccsId);

	/**
	 * 获取在线的用户ID集合
	 * 
	 * @return 在线用户的ID集合
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-7-8 下午1:54:45
	 */
	public List<String> listOnlineUserId();

	/**
	 * 
	 * 查询CS在线用户Session
	 * 
	 * @return cs在线计数
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 下午5:39:51
	 */
	public int getCSTotalCount();

	/**
	 * 获取最近更新的会话时间
	 * 
	 * @return 最近更新的会话时间
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013-9-26 上午9:44:30
	 */
	public long getLatestSession();

	/**
	 * 
	 * 根据CcsId查询用户会话列表
	 * 
	 * @param ccsId
	 *            通信服务器id
	 * @return UserSessionCcs
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionCcs> listUserSessionCcs(String ccsId);

	/**
	 * 
	 * 根据CrsId查询用户会话列表
	 * 
	 * @param crsId
	 *            存储服务器id
	 * @return UserSessionCrs
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionCrs> listUserSessionCrs(String id);

	/**
	 * 
	 * 根据MssId查询用户会话列表
	 * 
	 * @param mssId
	 *            流媒体服务器ID
	 * @return UserSessionMss
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionMss> listUserSessionMss(String mssId);

	/**
	 * 
	 * 根据DwsId查询用户会话列表
	 * 
	 * @param dwsId
	 *            电视墙服务器ID
	 * @return UserSessionDws
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionDws> listUserSessionDws(String dwsId);

	/**
	 * 
	 * 根据PtsId查询用户会话列表
	 * 
	 * @param PtsId
	 *            协议转换服务器id
	 * @return UserSessionPts
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionPts> listUserSessionPts(String ptsId);

	/**
	 * 
	 * 根据DasId查询用户会话列表
	 * 
	 * @param DasId
	 *            数据采集服务器id
	 * @return UserSessionDas
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionDas> listUserSessionDas(String dasId);

	/**
	 * 
	 * 根据EnsId查询用户会话列表
	 * 
	 * @param ensId
	 *            事件服务器ID
	 * @return UserSessionEns
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 上午9:10:00
	 */
	public List<UserSessionEns> listUserSessionEns(String ensId);

	/**
	 * 
	 * 根据rmsId查询用户会话列表
	 * 
	 * @param rmsId
	 *            录像转发服务器id
	 * @return UserSessionRms
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午5:10:15
	 */
	public List<UserSessionRms> listUserSessionRms(String rmsId);

	/**
	 * 
	 * 根据rssId查询用户会话列表
	 * 
	 * @param rssId
	 *            状态服务器ID
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-5-27 上午10:41:01
	 */
	public List<UserSessionRss> listUserSessionRss(String rssId);

	/**
	 * 
	 * 删除第一个登录用户
	 * 
	 * @param id
	 *            用户id
	 * @return UserSession
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 上午10:14:05
	 */
	public List<UserSessionUser> findFirstUser(String id);

	/**
	 * 
	 * 查询在线CCS
	 * 
	 * @return 在线ccs
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionCcs> listOnlineCcs();

	/**
	 * 
	 * 查询在线CRS
	 * 
	 * @return 在线crs
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionCrs> listOnlineCrs();

	/**
	 * 
	 * 查询在线MSS
	 * 
	 * @return 在线mss
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionMss> listOnlineMss();

	/**
	 * 
	 * 查询在线PTS
	 * 
	 * @return 在线pts
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionPts> listOnlinePts();

	/**
	 * 
	 * 查询在线DWS
	 * 
	 * @return 在线dws
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionDws> listOnlineDws();

	/**
	 * 
	 * 查询在线DAS
	 * 
	 * @return 在线das
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionDas> listOnlineDas();

	/**
	 * 
	 * 查询在线ENS
	 * 
	 * @return 在线ens
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionEns> listOnlineEns();

	/**
	 * 
	 * 查询在线RMS
	 * 
	 * @return 在线rms
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2015 下午4:09:16
	 */
	public List<UserSessionRms> listOnlineRms();

	/**
	 * 查询所有的在线用户会话
	 * 
	 * @return 所有的在线用户会话
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-6-9 下午3:27:35
	 */
	public List<UserSession> listUserSession();
	
	/**
	 * 
	 * 根据SrsId查询用户会话列表
	 * 
	 * @param srsId
	 *            录音服务器id
	 * @return UserSessionSrs
	 * @author sjt
	 *         <p />
	 *         Create at 2015 上午9:10:00
	 */
	public List<UserSessionSrs> listUserSessionSrs(String id);
}
