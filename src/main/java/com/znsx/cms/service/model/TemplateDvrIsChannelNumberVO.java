package com.znsx.cms.service.model;


/**
 * 视频服务器和通道号数组用于判断摄像头的通道号
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2013 下午4:48:21
 */
public class TemplateDvrIsChannelNumberVO {
	private String dvrNumber;

	public String getDvrNumber() {
		return dvrNumber;
	}

	public void setDvrNumber(String dvrNumber) {
		this.dvrNumber = dvrNumber;
	}

	private int[] arrayChannelAmount;

	public int[] getArrayChannelAmount() {
		return arrayChannelAmount;
	}

	public void setArrayChannelAmount(int[] arrayChannelAmount) {
		this.arrayChannelAmount = arrayChannelAmount;
	}
}
