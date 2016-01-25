package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 情报板发布日志记录实体对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-14 下午2:46:32
 */
public class CmsPublishLog implements Serializable {
	private static final long serialVersionUID = -8481670097831515353L;
	private String id;
	private String standardNumber;
	private String stakeNumber;
	private Short navigation;
	private Short infoType;
	private String sendOrganId;
	private String sendOrganName;
	private String sendUserId;
	private String sendUserName;
	private Integer duration;
	private String content;
	private String color;
	private String font;
	private String size;
	private Integer space;
	private Timestamp sendTime;
	private Integer x;
	private Integer y;
	private Integer rowSpace;

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

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public Short getNavigation() {
		return navigation;
	}

	public void setNavigation(Short navigation) {
		this.navigation = navigation;
	}

	public Short getInfoType() {
		return infoType;
	}

	public void setInfoType(Short infoType) {
		this.infoType = infoType;
	}

	public String getSendOrganId() {
		return sendOrganId;
	}

	public void setSendOrganId(String sendOrganId) {
		this.sendOrganId = sendOrganId;
	}

	public String getSendOrganName() {
		return sendOrganName;
	}

	public void setSendOrganName(String sendOrganName) {
		this.sendOrganName = sendOrganName;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public Integer getSpace() {
		return space;
	}

	public void setSpace(Integer space) {
		this.space = space;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getRowSpace() {
		return rowSpace;
	}

	public void setRowSpace(Integer rowSpace) {
		this.rowSpace = rowSpace;
	}

}
