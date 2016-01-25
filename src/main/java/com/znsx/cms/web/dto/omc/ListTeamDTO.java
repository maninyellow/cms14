package com.znsx.cms.web.dto.omc;

import java.util.List;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * ListTeamDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午6:57:39
 */
public class ListTeamDTO extends BaseDTO {
	private List<TeamVO> teamList;
	private String totalCount;

	public List<TeamVO> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<TeamVO> teamList) {
		this.teamList = teamList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
