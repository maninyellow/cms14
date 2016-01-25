package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.VdVO;

/**
 * StatVDRowMapper
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-24 上午10:32:42
 */
public class StatVDRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public VdVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		VdVO vo = new VdVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setFlux(rs.getString("flux"));
		vo.setFluxAvg(Math.round(rs.getBigDecimal("fluxAvg").floatValue()) + "");
		vo.setSpeedAvg(Math.round(rs.getBigDecimal("speedAvg").floatValue())
				+ "");
		vo.setOccupAvg(Math.round(rs.getBigDecimal("occupAvg").floatValue())
				+ "");
		vo.setRecTime(sdf.format(rs.getTimestamp("group_time")));
		return vo;
	}

}
