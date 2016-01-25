package com.znsx.cms.web.dto.omc;

import com.znsx.cms.web.dto.BaseDTO;

/**
 * GetTeamDTO
 * 
 * @author wangbinyu
 *         <p />
 *         Create at 2014 下午6:45:46
 */
public class GetTeamDTO extends BaseDTO {
	private TeamVO team;

	public TeamVO getTeam() {
		return team;
	}

	public void setTeam(TeamVO team) {
		this.team = team;
	}

}
