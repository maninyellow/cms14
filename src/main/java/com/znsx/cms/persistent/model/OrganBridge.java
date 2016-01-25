/**
 * 
 */
package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * @author znsx
 * 
 */
public class OrganBridge extends Organ implements Serializable {

	private static final long serialVersionUID = 6929934967278816665L;
	private Integer limitSpeed;
	private Integer capacity;
	private String width;
	private String length;

	public Integer getLimitSpeed() {
		return limitSpeed;
	}

	public void setLimitSpeed(Integer limitSpeed) {
		this.limitSpeed = limitSpeed;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

}
