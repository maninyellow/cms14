package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * MonthLoliDetector
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2015-9-9 上午9:32:32
 */
public class MonthLoliDetector implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6169069218206516829L;
	private String id;
	private String standardNumber;
	private Float loAvg;
	private Float loMax;
	private Float loMin;
	private Float liAvg;
	private Float liMax;
	private Float liMin;
	private Long dateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public Float getLoAvg() {
		return loAvg;
	}

	public void setLoAvg(Float loAvg) {
		this.loAvg = loAvg;
	}

	public Float getLoMax() {
		return loMax;
	}

	public void setLoMax(Float loMax) {
		this.loMax = loMax;
	}

	public Float getLoMin() {
		return loMin;
	}

	public void setLoMin(Float loMin) {
		this.loMin = loMin;
	}

	public Float getLiAvg() {
		return liAvg;
	}

	public void setLiAvg(Float liAvg) {
		this.liAvg = liAvg;
	}

	public Float getLiMax() {
		return liMax;
	}

	public void setLiMax(Float liMax) {
		this.liMax = liMax;
	}

	public Float getLiMin() {
		return liMin;
	}

	public void setLiMin(Float liMin) {
		this.liMin = liMin;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
