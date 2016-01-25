package com.znsx.cms.persistent.model.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.znsx.cms.service.model.DeviceStatusVO;

/**
 * 设备状态统计查询结果对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-12-17 上午11:09:09
 */
public class DeviceStatusRowMapper implements RowMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public DeviceStatusVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeviceStatusVO vo = new DeviceStatusVO();
		vo.setStandardNumber(rs.getString("standard_number"));
		vo.setRecTime(sdf.format(rs.getTimestamp("rec_time")));
		vo.setType(rs.getString("type"));
		vo.setWorkStatus(rs.getString("status"));
		vo.setCommStatus(rs.getString("comm_status"));
		vo.setOrganName(rs.getString("organ"));
		return vo;
	}

}
