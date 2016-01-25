package com.znsx.cms.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.DasVdDAO;
import com.znsx.cms.persistent.model.DasVd;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.RoadStatusManager;
import com.znsx.util.number.NumberUtil;

/**
 * RoadStatusManagerImpl
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-18 下午3:41:24
 */
public class RoadStatusManagerImpl extends BaseManagerImpl implements
		RoadStatusManager {
	@Autowired
	private DasVdDAO dasVdDAO;

	@Override
	public int calculateQc(Integer capacity, Integer roadLaneNumber,
			int laneNumber) throws BusinessException {
		if (capacity == null) {
			throw new BusinessException(ErrorCode.IMPORTANT_DATA_MISSING,
					"Road capacity is missing !");
		}
		if (roadLaneNumber == null) {
			throw new BusinessException(ErrorCode.IMPORTANT_DATA_MISSING,
					"Road laneNumber is missing !");
		}
		int ln = roadLaneNumber.intValue();

		// 单车道设计通行力
		float c0 = capacity.floatValue();
		// 可能通行能力
		float cp = 0f;
		// 实际通行能力
		float c = 0f;
		// 可能通行能力计算
		if (ln == 1) {
			cp = c0 * F_LANE_1;
		} else if (ln == 2) {
			cp = c0 * F_LANE_1 + c0 * F_LANE_2;
		} else if (ln == 3) {
			cp = c0 * F_LANE_1 + c0 * F_LANE_2 + c0 * F_LANE_3;
		} else if (ln == 4) {
			cp = c0 * F_LANE_1 + c0 * F_LANE_2 + c0 * F_LANE_3 + c0 * F_LANE_4;
		}
		// 大于4根车道的采用4根车道的系数
		else {
			cp = c0 * F_LANE_1 + c0 * F_LANE_2 + c0 * F_LANE_3 + c0 * F_LANE_4
					+ c0 * F_LANE_4 * (ln - 4);
		}
		// 实际通行能力计算
		if (laneNumber == 0) {
			c = cp;
		} else if (laneNumber == 1) {
			if (ln == 1) {
				c = 0f;
			} else if (ln == 2) {
				c = cp * F_LANE2_BLOCK1;
			} else if (ln == 3) {
				c = cp * F_LANE3_BLOCK1;
			} else if (ln == 4) {
				c = cp * F_LANE4_BLOCK1;
			}
			// 大于4根车道的采用4根车道的系数
			else {
				c = cp * F_LANE4_BLOCK1;
			}
		} else if (laneNumber == 2) {
			if (ln == 1) {
				c = 0f;
			} else if (ln == 2) {
				c = 0f;
			} else if (ln == 3) {
				c = cp * F_LANE3_BLOCK2;
			} else if (ln == 4) {
				c = cp * F_LANE4_BLOCK2;
			}
			// 大于4根车道的采用4根车道的系数
			else {
				c = cp * F_LANE4_BLOCK2;
			}
		} else if (laneNumber == 3) {
			if (ln == 1) {
				c = 0f;
			} else if (ln == 2) {
				c = 0f;
			} else if (ln == 3) {
				c = 0f;
			} else if (ln == 4) {
				c = cp * F_LANE4_BLOCK3;
			}
			// 大于4根车道的采用4根车道的系数
			else {
				c = cp * F_LANE4_BLOCK3;
			}
		} else if (laneNumber == 4) {
			if (ln == 1) {
				c = 0f;
			} else if (ln == 2) {
				c = 0f;
			} else if (ln == 3) {
				c = 0f;
			} else if (ln == 4) {
				c = 0f;
			}
			// 大于4根车道的采用4根车道的系数
			else {
				c = 0f;
			}
		}
		return Math.round(c);
	}

	@Override
	public boolean isOppositeUsable(OrganRoad road, List<VehicleDetector> vds,
			String navigation, String beginStake, String endStake)
			throws BusinessException {
		int c = 0;
		// 如果没有找到路段，默认双车道，限速100的路段
		if (null == road) {
			c = calculateQc(Integer.valueOf(2000), Integer.valueOf(2), 0);
		} else {
			Integer capacity = road.getCapacity();
			Integer roadLaneNumber = road.getLaneNumber();
			// 得到对侧道路实际通行能力
			c = calculateQc(capacity, roadLaneNumber, 0);
		}
		String oppositeNavigate = TypeDefinition.NAVIGATION_UPLOAD + "";
		// 获取对侧道路上的车检器
		if (TypeDefinition.NAVIGATION_UPLOAD == Integer.valueOf(navigation)
				.intValue()) {
			oppositeNavigate = TypeDefinition.NAVIGATION_DOWNLOAD + "";
		} else {
			oppositeNavigate = TypeDefinition.NAVIGATION_UPLOAD + "";
		}
		// 没有查询到任何车检器，默认允许借用对侧道路
		if (vds.size() == 0) {
			return true;
		}
		// 对向车道离事发点最近的两个车检器
		VehicleDetector first = null;
		VehicleDetector second = null;
		float min1 = Float.MAX_VALUE;
		float min2 = Float.MAX_VALUE;
		for (VehicleDetector vd : vds) {
			if (oppositeNavigate.equals(vd.getNavigation())) {
				float dvalue = NumberUtil.floatStake(beginStake)
						- NumberUtil.floatStake(vd.getStakeNumber());
				if (Math.abs(dvalue) < min1) {
					min2 = min1;
					min1 = Math.abs(dvalue);
					second = first;
					first = vd;
				} else if (Math.abs(dvalue) < min2) {
					min2 = Math.abs(dvalue);
					second = vd;
				}
			}
		}

		// // 只关注事发点前方、对侧的车检器
		// // 上行方向，关注大于endStake桩号的车检器
		// if (TypeDefinition.NAVIGATION_UPLOAD == Integer.valueOf(navigation))
		// {
		//
		// }
		// // 下行方向，关注小于beginStake桩号的车检器
		// else {
		//
		// }
		// 简单实现，最近两个车检器采集值得平均值用来计算当前车流量
		float flux = 0;
		List<String> sns = new LinkedList<String>();
		if (null != first) {
			sns.add(first.getStandardNumber());
		}
		if (null != second) {
			sns.add(second.getStandardNumber());
		}
		// 没有查询到任何匹配的车辆检测器，默认允许借用对侧道路
		if (sns.size() == 0) {
			return true;
		}
		List<DasVd> dasVds = dasVdDAO.listVdInfo(sns);
		// 没有查询到任何采集值，默认允许借用对侧道路
		if (dasVds.size() == 0) {
			return true;
		}
		if ((TypeDefinition.NAVIGATION_UPLOAD + "").equals(oppositeNavigate)) {
			for (DasVd dasVd : dasVds) {
				if (null != dasVd.getUpFlux()) {
					flux += dasVd.getUpFlux().floatValue();
				}
			}
		} else {
			for (DasVd dasVd : dasVds) {
				if (null != dasVd.getDwFlux()) {
					flux += dasVd.getDwFlux().floatValue();
				}
			}
		}
		flux = flux / dasVds.size();

		// 假定2分钟采集一次数据, 通行能力计算单位为小时，因此乘30
		float fluxTotal = flux * 30;
		// 通行能力大于检测车流量
		if (c > fluxTotal) {
			return true;
		}
		// 通行能力小于等于检测车流量
		else {
			return false;
		}
	}

	@Override
	public int compareQc(OrganRoad road, List<VehicleDetector> vds,
			String navigation, String beginStake, String endStake,
			int laneNumber) throws BusinessException {
		int c = 0;
		// 如果没有找到路段，默认双车道，限速100的路段
		if (null == road) {
			c = calculateQc(Integer.valueOf(2000), Integer.valueOf(2),
					laneNumber);
		} else {
			Integer capacity = road.getCapacity();
			Integer roadLaneNumber = road.getLaneNumber();
			// 得到对侧道路实际通行能力
			c = calculateQc(capacity, roadLaneNumber, laneNumber);
		}
		// 如果没有车检器，默认-1:交通量>通行能力
		if (vds.size() == 0) {
			return -1;
		}
		float min = Float.MAX_VALUE;
		float end = NumberUtil.floatStake(endStake);
		float begin = NumberUtil.floatStake(beginStake);
		float vdStake = 0f;
		VehicleDetector nearest = null;
		// 上行方向获取小于endStake且离beginStake最近的车检器
		if ((TypeDefinition.NAVIGATION_UPLOAD + "").equals(navigation)) {
			for (VehicleDetector vd : vds) {
				if ((vd.getNavigation()).equals(navigation)) {
					vdStake = NumberUtil.floatStake(vd.getStakeNumber());
					if ((vdStake - end) <= 0) {
						float dvalue = vdStake - begin;
						if (Math.abs(dvalue) < min) {
							min = Math.abs(dvalue);
							nearest = vd;
						}
					}
				}
			}
		}
		// 下行方向获取大于beginStake且离endStake最近的车检器
		else {
			for (VehicleDetector vd : vds) {
				if ((vd.getNavigation()).equals(navigation)) {
					vdStake = NumberUtil.floatStake(vd.getStakeNumber());
					if ((vdStake - begin) >= 0) {
						float dvalue = vdStake - end;
						if (Math.abs(dvalue) < min) {
							min = Math.abs(dvalue);
							nearest = vd;
						}
					}
				}
			}
		}
		// 没有找到合适的车检器，默认-1:交通量>通行能力
		if (null == nearest) {
			return -1;
		}
		float flux = 0;
		List<String> sns = new LinkedList<String>();
		sns.add(nearest.getStandardNumber());
		List<DasVd> dasVds = dasVdDAO.listVdInfo(sns);
		// 没有查询到任何采集值，默认-1:交通量>通行能力
		if (dasVds.size() == 0) {
			return -1;
		}
		// 如果通信或工作状态不为0，默认-1：交通量>通行能力
		if ((dasVds.get(0).getCommStatus().intValue() != 0)
				|| (dasVds.get(0).getStatus().intValue() != 0)) {
			return -1;
		}
		if ((TypeDefinition.NAVIGATION_UPLOAD + "").equals(navigation)) {
			flux = dasVds.get(0).getUpFlux().floatValue();
		} else {
			flux = dasVds.get(0).getDwFlux().floatValue();
		}

		// 假定2分钟采集一次数据, 通行能力计算单位为小时，因此乘30
		float fluxTotal = flux * 30;
		// 通行能力大于检测车流量
		if (c > fluxTotal) {
			return 1;
		}
		// 等于检测车流量
		else if (c == fluxTotal) {
			return 0;
		}
		// 通行能力小于
		else {
			return -1;
		}
	}

	@Override
	public String getRoadStatus(float ratio) {
		if (ratio <= RoadStatusManager.SERVICE_1) {
			return TypeDefinition.FLUX_1;
		} else if (ratio < RoadStatusManager.SERVICE_2) {
			return TypeDefinition.FLUX_2;
		} else if (ratio < RoadStatusManager.SERVICE_3) {
			return TypeDefinition.FLUX_3;
		} else {
			return TypeDefinition.FLUX_4;
		}
	}

	public static void main(String[] args) {
		RoadStatusManager manager = new RoadStatusManagerImpl();
//		System.out.println(manager.calculateQc(2200, 2, 0));
		System.out.println(manager.getRoadStatus(1f));
	}

}
