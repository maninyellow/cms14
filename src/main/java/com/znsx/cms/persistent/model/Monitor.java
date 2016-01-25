package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 视频输出实体类
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 上午10:45:53
 */
public class Monitor implements Serializable {

	private static final long serialVersionUID = 9211507885516620945L;
	private String id;
	private Integer channelNumber;
	private String x;
	private String y;
	private String width;
	private String height;
	private String standardNumber;
	private String name;
	private DisplayWall displayWall;
	private Dws dws;
	private Organ organ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(Integer channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DisplayWall getDisplayWall() {
		return displayWall;
	}

	public void setDisplayWall(DisplayWall displayWall) {
		this.displayWall = displayWall;
	}

	public Dws getDws() {
		return dws;
	}

	public void setDws(Dws dws) {
		this.dws = dws;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

}
