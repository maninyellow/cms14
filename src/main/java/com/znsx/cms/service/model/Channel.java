package com.znsx.cms.service.model;

/**
 * CRS存储策略
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午2:35:25
 */
public class Channel {
	private String sn;
	private String plan;
	private String storeStream;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
		if (null == sn) {
			this.sn = "";
		}
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
		if (null == plan) {
			this.plan = "";
		}
	}

	public String getStoreStream() {
		return storeStream;
	}

	public void setStoreStream(String storeStream) {
		this.storeStream = storeStream;
	}

}
