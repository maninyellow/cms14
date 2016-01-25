/**
 * 
 */
package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wangbinyu
 * 
 */
public class DasCmsReal implements Serializable {

	private static final long serialVersionUID = 7550691239247262423L;
	private String id;
	private String standardNumber;
	private Timestamp recTime;
	private String dispCont;
	private Integer dispTime;
	private String color;
	private Short size;
	private String font;
	private Short status;
	private Short commStatus;
	private String reserve;
	private String organ;

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

	public Timestamp getRecTime() {
		return recTime;
	}

	public void setRecTime(Timestamp recTime) {
		this.recTime = recTime;
	}

	public String getDispCont() {
		return dispCont;
	}

	public void setDispCont(String dispCont) {
		this.dispCont = dispCont;
	}

	public Integer getDispTime() {
		return dispTime;
	}

	public void setDispTime(Integer dispTime) {
		this.dispTime = dispTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Short getSize() {
		return size;
	}

	public void setSize(Short size) {
		this.size = size;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Short commStatus) {
		this.commStatus = commStatus;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

}
