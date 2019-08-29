package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.Environment;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月16日 下午2:32:20
 */
public class EnvironmentRowMapper implements RowMapper<Environment> {

	@Override
	public Environment mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		String name = rs.getString("name");
		String url = rs.getString("url");
		Environment environmentObject = new Environment();
		environmentObject.setName(name);
		environmentObject.setUrl(url);
		return environmentObject;
	}
}
