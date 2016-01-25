package com.znsx.cms.service.model;


/**
 * CRS配置信息
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-3-10 下午3:56:35
 */
public class CrsUpdateConfigVO {
	private String maxInput;
	private String maxOutput;
	private String wanIp;
//	private List<Channel> channels = new LinkedList<Channel>();
	private String totalCount;

	public String getMaxInput() {
		return maxInput;
	}

	public void setMaxInput(String maxInput) {
		this.maxInput = maxInput;
		if (null == maxInput) {
			this.maxInput = "";
		}
	}

	public String getMaxOutput() {
		return maxOutput;
	}

	public void setMaxOutput(String maxOutput) {
		this.maxOutput = maxOutput;
		if (null == maxOutput) {
			this.maxOutput = "";
		}
	}

	public String getWanIp() {
		return wanIp;
	}

	public void setWanIp(String wanIp) {
		this.wanIp = wanIp;
		if (null == wanIp) {
			this.wanIp = "";
		}
	}

//	public List<Channel> getChannels() {
//		return channels;
//	}
//
//	public void setChannels(List<Channel> channels) {
//		this.channels = channels;
//	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

//	public class Channel {
//		private String sn;
//		private String plan;
//
//		public String getSn() {
//			return sn;
//		}
//
//		public void setSn(String sn) {
//			this.sn = sn;
//			if (null == sn) {
//				this.sn = "";
//			}
//		}
//
//		public String getPlan() {
//			return plan;
//		}
//
//		public void setPlan(String plan) {
//			this.plan = plan;
//			if (null == plan) {
//				this.plan = "";
//			}
//		}
//	}
}
