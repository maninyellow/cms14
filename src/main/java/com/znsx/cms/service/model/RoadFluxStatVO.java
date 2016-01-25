package com.znsx.cms.service.model;

/**
 * 路段车流量月统计结果
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-21 下午2:44:03
 */
public class RoadFluxStatVO {
	private String id;
	private String name;
	private String standardNumber;
	private String month;
	private long upFlux;
	private long dwFlux;

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

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public long getUpFlux() {
		return upFlux;
	}

	public void setUpFlux(long upFlux) {
		this.upFlux = upFlux;
	}

	public long getDwFlux() {
		return dwFlux;
	}

	public void setDwFlux(long dwFlux) {
		this.dwFlux = dwFlux;
	}

}
