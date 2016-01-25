package com.znsx.cms.service.model;

/**
 * 播放节目内容业务对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午1:42:28
 */
public class PlayItemVO {
	private String content;
	private String color;
	private String font;
	private String size;
	private String space;
	private String duration;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		if (null == content) {
			this.content = "";
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		if (null == color) {
			this.color = "";
		}
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
		if (null == font) {
			this.font = "";
		}
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
		if (null == size) {
			this.size = "";
		}
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
		if (null == space) {
			this.space = "";
		}
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
		if (null == duration) {
			this.duration = "";
		}
	}

}
