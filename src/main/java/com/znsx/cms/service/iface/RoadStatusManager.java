package com.znsx.cms.service.iface;

import java.util.List;

import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 路况业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-18 上午10:55:32
 */
public interface RoadStatusManager extends BaseManager {
	/**
	 * 从中央隔离带开始数第一根车道，实际通行能力衰减系数
	 */
	public static final float F_LANE_1 = 1.0f;
	/**
	 * 从中央隔离带开始数第二根车道，实际通行能力衰减系数
	 */
	public static final float F_LANE_2 = 0.9f;
	/**
	 * 从中央隔离带开始数第三根车道，实际通行能力衰减系数
	 */
	public static final float F_LANE_3 = 0.8f;
	/**
	 * 从中央隔离带开始数第四根车道，实际通行能力衰减系数
	 */
	public static final float F_LANE_4 = 0.65f;
	/**
	 * 单向2根车道，阻断1跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE2_BLOCK1 = 0.35f;
	/**
	 * 单向2根车道，阻断2跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE2_BLOCK2 = 0f;
	/**
	 * 单向3根车道，阻断1跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE3_BLOCK1 = 0.49f;
	/**
	 * 单向3根车道，阻断2跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE3_BLOCK2 = 0.17f;
	/**
	 * 单向3根车道，阻断3跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE3_BLOCK3 = 0f;
	/**
	 * 单向4根车道，阻断1跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE4_BLOCK1 = 0.58f;
	/**
	 * 单向4根车道，阻断2跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE4_BLOCK2 = 0.25f;
	/**
	 * 单向4根车道，阻断3跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE4_BLOCK3 = 0.13f;
	/**
	 * 单向4根车道，阻断4跟车道，实际通行能力衰减系数
	 */
	public static final float F_LANE4_BLOCK4 = 0f;
	/**
	 * V/C <= 0.74f 服务水平1级
	 */
	public static final float SERVICE_1 = 0.74f;
	/**
	 * V/C <= 0.88f 服务水平2级
	 */
	public static final float SERVICE_2 = 0.88f;
	/**
	 * V/C <= 0.95f 服务水平3级	
	 */
	public static final float SERVICE_3 = 0.95f;

	/**
	 * 道路通行能力计算
	 * 
	 * @param capacity
	 *            单根车道设计通行能力
	 * @param roadLaneNumber
	 *            路段单向车道数量
	 * @param laneNumber
	 *            受损车道数量
	 * @return
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-18 上午11:06:24
	 */
	public int calculateQc(Integer capacity, Integer roadLaneNumber,
			int laneNumber) throws BusinessException;

	/**
	 * 判断对侧车道是否符合车道借用的条件
	 * 
	 * @param road
	 *            路段对象
	 * @param vds
	 *            路段上的车检器列表
	 * @param navigation
	 *            本侧方向
	 * @param beginStake
	 *            开始桩号
	 * @param endStake
	 *            结束桩号
	 * @return 满足借用条件-true，不满足-false
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-30 下午3:26:30
	 */
	public boolean isOppositeUsable(OrganRoad road, List<VehicleDetector> vds,
			String navigation, String beginStake, String endStake)
			throws BusinessException;

	/**
	 * 比较事发路段车流量能与理论通行能力的关系
	 * 
	 * @param road
	 *            路段对象
	 * @param vds
	 *            路段上的车检器列表
	 * @param navigation
	 *            本侧方向
	 * @param beginStake
	 *            开始桩号
	 * @param endStake
	 *            结束桩号
	 * @param laneNumber
	 *            受损车道数量
	 * @return -1:交通量>通行能力，0:交通量=通行能力,1:交通量<通行能力
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-16 上午10:04:49
	 */
	public int compareQc(OrganRoad road, List<VehicleDetector> vds,
			String navigation, String beginStake, String endStake,
			int laneNumber) throws BusinessException;

	/**
	 * 获取路况
	 * 
	 * @param ratio
	 *            实际流量与设计通行能力的比值
	 * @return 路况
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-10-24 上午10:07:36
	 */
	public String getRoadStatus(float ratio);
}
