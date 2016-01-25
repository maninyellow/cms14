package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 下级平台资源实体，包括机构，设备，服务器，用户等一切资源
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-9-13 上午10:20:43
 */
public class SubPlatformResource implements Serializable {
	private static final long serialVersionUID = 7971821867678424419L;
	private String id;
	private String name;
	private String standardNumber;
	private SubPlatformResource parent;
	private String type;
	private String protocol;
	private String auth;
	private String path;
	private Long updateTime;
	private String stakeNumber;
	private String longitude;
	private String latitude;
	private String roadName;
	private String navigation;
	private Integer width;
	private Integer height;
	private String manufacturer;
	private String model;
	private String owner;
	private String civilCode;
	private String block;
	private String address;
	private Integer parental;
	private Integer safetyWay;
	private Integer registerWay;
	private String certNum;
	private Integer certifiable;
	private Integer errCode;
	private Long endTime;
	private Integer secrecy;
	private String ip;
	private Integer port;
	private String password;
	private String status;
	private String ptzType;
	private String gatewayId;

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

	public String getStandardNumber() {
		return standardNumber;
	}

	public void setStandardNumber(String standardNumber) {
		this.standardNumber = standardNumber;
	}

	public SubPlatformResource getParent() {
		return parent;
	}

	public void setParent(SubPlatformResource parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getStakeNumber() {
		return stakeNumber;
	}

	public void setStakeNumber(String stakeNumber) {
		this.stakeNumber = stakeNumber;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCivilCode() {
		return civilCode;
	}

	public void setCivilCode(String civilCode) {
		this.civilCode = civilCode;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getParental() {
		return parental;
	}

	public void setParental(Integer parental) {
		this.parental = parental;
	}

	public Integer getSafetyWay() {
		return safetyWay;
	}

	public void setSafetyWay(Integer safetyWay) {
		this.safetyWay = safetyWay;
	}

	public Integer getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(Integer registerWay) {
		this.registerWay = registerWay;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public Integer getCertifiable() {
		return certifiable;
	}

	public void setCertifiable(Integer certifiable) {
		this.certifiable = certifiable;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getSecrecy() {
		return secrecy;
	}

	public void setSecrecy(Integer secrecy) {
		this.secrecy = secrecy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPtzType() {
		return ptzType;
	}

	public void setPtzType(String ptzType) {
		this.ptzType = ptzType;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	@Override
	public boolean equals(Object obj) {
		return ((SubPlatformResource) obj).getStandardNumber().equals(
				this.getStandardNumber())
				|| ((SubPlatformResource) obj).getId().equals(this.getId());
	}
}
