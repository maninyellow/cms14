package com.znsx.cms.web.dto.sgc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * VdFluxStatusDTO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-10-21 下午4:36:04
 */
public class VdFluxStatusDTO extends BaseDTO {

	private List<VdFlux> vdFluxs;

	public List<VdFlux> getVdFluxs() {
		return vdFluxs;
	}

	public void setVdFluxs(List<VdFlux> vdFluxs) {
		this.vdFluxs = vdFluxs;
	}

	public class VdFlux {
		private String id;
		private String standardNumber;
		private String upRoadStatus;
		private String dwRoadStatus;

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

		public String getUpRoadStatus() {
			return upRoadStatus;
		}

		public void setUpRoadStatus(String upRoadStatus) {
			this.upRoadStatus = upRoadStatus;
		}

		public String getDwRoadStatus() {
			return dwRoadStatus;
		}

		public void setDwRoadStatus(String dwRoadStatus) {
			this.dwRoadStatus = dwRoadStatus;
		}

	}
}
