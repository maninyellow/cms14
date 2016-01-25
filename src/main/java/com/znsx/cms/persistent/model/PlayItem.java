package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 播放节目内容实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 上午10:07:11
 */
public class PlayItem implements Serializable {
	private static final long serialVersionUID = 7829163987088645306L;
	private String id;
	private Playlist playlist;
	private String font;
	private String size;
	private String color;
	private Short wordSpace;
	private Integer twinklePeriod;
	private String content;
	private Integer duration;
	private String x;
	private String y;
	private Short type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Short getWordSpace() {
		return wordSpace;
	}

	public void setWordSpace(Short wordSpace) {
		this.wordSpace = wordSpace;
	}

	public Integer getTwinklePeriod() {
		return twinklePeriod;
	}

	public void setTwinklePeriod(Integer twinklePeriod) {
		this.twinklePeriod = twinklePeriod;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

}
