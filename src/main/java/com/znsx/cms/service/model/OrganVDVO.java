package com.znsx.cms.service.model;

/**
 * OrganVDVO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午4:25:45
 */
public class OrganVDVO {
	private String id;
	private String name;
	private String upFlowSum;
	private String downFlowSum;
	private String upSpeedAvg;
	private String downSpeedAvg;
	private String totalFlow;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpFlowSum() {
		return upFlowSum;
	}

	public void setUpFlowSum(String upFlowSum) {
		this.upFlowSum = upFlowSum;
	}

	public String getDownFlowSum() {
		return downFlowSum;
	}

	public void setDownFlowSum(String downFlowSum) {
		this.downFlowSum = downFlowSum;
	}

	public String getUpSpeedAvg() {
		return upSpeedAvg;
	}

	public void setUpSpeedAvg(String upSpeedAvg) {
		this.upSpeedAvg = upSpeedAvg;
	}

	public String getDownSpeedAvg() {
		return downSpeedAvg;
	}

	public void setDownSpeedAvg(String downSpeedAvg) {
		this.downSpeedAvg = downSpeedAvg;
	}

	public String getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}

}
