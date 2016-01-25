package com.znsx.cms.service.model;

/**
 * VdFluxByMonthVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-5-21 下午2:52:02
 */
public class VdFluxByMonthVO {
	private String standardNumber;
	private String month;
	private Long upFlux;
	private Long dwFlux;

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

	public Long getUpFlux() {
		return upFlux;
	}

	public void setUpFlux(Long upFlux) {
		this.upFlux = upFlux;
	}

	public Long getDwFlux() {
		return dwFlux;
	}

	public void setDwFlux(Long dwFlux) {
		this.dwFlux = dwFlux;
	}

}
