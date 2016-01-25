package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.CoviVO;

/**
 * StatCoviRowMapper
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-9 上午9:19:49
 */
public class StatCoviRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public CoviVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CoviVO vo = new CoviVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setCoMax(Math.round(rs.getDouble("co_max")) + "");
		vo.setCoMin(Math.round(rs.getDouble("co_min")) + "");
		vo.setCoAvg(Math.round(rs.getDouble("co_avg") * 10) / 10.0 + "");
		vo.setViMax(rs.getString("vi_max"));
		vo.setViMin(rs.getString("vi_min"));
		vo.setViAvg(Math.round(rs.getDouble("vi_avg") * 10) / 10.0 + "");
		vo.setOrganName(rs.getString("organ"));
		vo.setRecTime(sdf.format(rs.getTimestamp("group_time")));
		return vo;
	}

}
