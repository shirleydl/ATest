package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.shirley.aTest.entity.Mock;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月4日 下午2:12:02
*/
public class MockRowMapper implements RowMapper<Mock>  {

	@Override
	public Mock mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int id=rs.getInt("id");
		String name = rs.getString("name");
		String bindVariableMocks = rs.getString("bindvariable_mocks");
		Map<String, String> map = new HashMap<String,String>();
		Gson gson = new Gson();
		Mock mock = new Mock();
		mock.setId(id);
		mock.setName(name);
		try{
			mock.setBindVariableMocks(gson.fromJson(bindVariableMocks, map.getClass()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return mock;
	}

}
