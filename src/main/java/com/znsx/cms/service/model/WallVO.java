package com.znsx.cms.service.model;

import java.util.List;

/**
 * 电视墙业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-7-10 上午11:18:08
 */
public class WallVO {
	private String id;
	private String name;
	private List<Monitor> monitors;

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

	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public class Monitor {
		private String id;
		private String name;
		private String x;
		private String y;
		private String width;
		private String height;
		private String standardNumber;
		private String dWSId;
		private String channelNumber;
		private String dWSStandardNumber;

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

		public String getDWSId() {
			return dWSId;
		}

		public void setDWSId(String dWSId) {
			this.dWSId = dWSId;
		}

		public String getChannelNumber() {
			return channelNumber;
		}

		public void setChannelNumber(String channelNumber) {
			this.channelNumber = channelNumber;
		}

		public String getDWSStandardNumber() {
			return dWSStandardNumber;
		}

		public void setDWSStandardNumber(String dWSStandardNumber) {
			this.dWSStandardNumber = dWSStandardNumber;
		}

	}
}
