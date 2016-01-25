package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.LoLiVO;

/**
 * StatLoLiRowMapper
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-2 上午10:26:41
 */
public class StatLoLiRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public LoLiVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoLiVO vo = new LoLiVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setCd(Math.round(rs.getDouble("lo")) + "");
		vo.setLx(Math.round(rs.getDouble("li")) + "");
		vo.setOrganName(rs.getString("organ"));
		vo.setRecTime(sdf.format(rs.getTimestamp("group_time")));
		return vo;
	}

}
