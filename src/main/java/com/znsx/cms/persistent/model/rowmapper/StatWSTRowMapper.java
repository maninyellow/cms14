package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.WstVO;

/**
 * StatWSTRowMapper
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-26 下午2:09:01
 */
public class StatWSTRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public WstVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		WstVO vo = new WstVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setVi(Math.round(rs.getDouble("vi")) + "");
		vo.setAirTemp(Math.round(rs.getDouble("air_temp")) + "");
		vo.setHumi(Math.round(rs.getDouble("humi")) + "");
		vo.setDirection(Math.round(rs.getDouble("direction")) + "");
		vo.setWindSpeed(Math.round(rs.getDouble("wind_speed") * 10) / 10.0 + "");
		vo.setRoadTemp(Math.round(rs.getDouble("road_temp")) + "");
		vo.setRoadSurface(rs.getString("road_surface"));
		vo.setPressure(rs.getString("pressure"));
		vo.setRainVol(Math.round(rs.getDouble("rain_vol")) + "");
		vo.setRecTime(sdf.format(rs.getTimestamp("group_time")));
		return vo;
	}
}
