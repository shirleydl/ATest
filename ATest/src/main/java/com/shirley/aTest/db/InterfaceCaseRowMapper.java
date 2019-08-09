package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.shirley.aTest.entity.InterfaceCase;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午5:34:09
 */
public class InterfaceCaseRowMapper implements RowMapper<InterfaceCase> {

	@Override
	public InterfaceCase mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		// 获取结果集中的数据
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int interfaceId = rs.getInt("interface_id");
		String description = rs.getString("description");
		String method = rs.getString("method");
		String headers = rs.getString("headers");
		String params = rs.getString("params");
		String asserts = rs.getString("asserts");
		String variables = rs.getString("variables");
		// 把数据封装对象
		Gson gson = new Gson();
		InterfaceCase interfaceCaseObject = new InterfaceCase();
		interfaceCaseObject.setId(id);
		interfaceCaseObject.setName(name);
		interfaceCaseObject.setInterfaceId(interfaceId);
		interfaceCaseObject.setDescription(description);
		interfaceCaseObject.setMethod(method);
		interfaceCaseObject.setAsserts(asserts);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapV = new LinkedHashMap<String, String>();
		interfaceCaseObject.setHeaders(gson.fromJson(headers, map.getClass()));
		interfaceCaseObject.setVariables(gson.fromJson(variables, mapV.getClass()));
		interfaceCaseObject.setParams(params);
		return interfaceCaseObject;
	}

}
