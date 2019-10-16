package com.shirley.aTestActuator.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.shirley.aTestActuator.entity.Replace;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午9:49:35
 */
public class ReplaceRowMapper implements RowMapper<Replace> {

	@Override
	public Replace mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub

			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			Map<String, String> urlMap = new HashMap<String, String>();
			Gson gson = new Gson();
			Replace replace = new Replace();
			replace.setSplit(rs.getString("replace_split"));
			try {
				replace.setReplaceUrl(gson.fromJson(rs.getString("replace_url"), urlMap.getClass()));
				replace.setReplaceData(gson.fromJson(rs.getString("replace_data"), map.getClass()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return replace;

	}

}
