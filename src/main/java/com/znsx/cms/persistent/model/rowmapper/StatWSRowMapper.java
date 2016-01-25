package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.WsVO;

/**
 * StatWSRowMapper
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-31 上午9:54:39
 */
public class StatWSRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public WsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WsVO vo = new WsVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setSpeedMax(Math.round(rs.getDouble("speed_max") * 10) / 10.0 + "");
		vo.setSpeedMin(Math.round(rs.getDouble("speed_min") * 10) / 10.0 + "");
		vo.setSpeedAvg(Math.round(rs.getDouble("speed_avg") * 10) / 10.0 + "");
		vo.setDirection(rs.getString("direction"));
		vo.setOrganName(rs.getString("organ"));
		vo.setRecTime(sdf.format(rs.getTimestamp("group_time")));
		return vo;
	}

}
