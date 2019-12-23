package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shirley.aTest.entity.Asserts;
import com.shirley.aTest.entity.Request;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月16日 上午9:18:47
 */
public class RequestRowMapper implements RowMapper<Request> {

	@Override
	public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		String url = rs.getString("url");
		String api = rs.getString("api");
		String method = rs.getString("method");
		String headers = rs.getString("headers");
		String params = rs.getString("params");
		String asserts = rs.getString("asserts");
		String variables = rs.getString("variables");
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapV = new LinkedHashMap<String, String>();
		Request request = new Request();
		request.setUrl(url);
		request.setApi(api);
		request.setMethod(method);
		try {
			request.setHeaders((gson.fromJson(headers, map.getClass())));
			if ("raw".equals(method)||"get_url".equals(method)) {
				request.setParamStr(params);
			} else {
				request.setParamMap((gson.fromJson(params, map.getClass())));
			}
			request.setAsserts((List<Asserts>) (gson.fromJson(asserts, new TypeToken<List<Asserts>>() {
			}.getType())));
			request.setVariables((gson.fromJson(variables, mapV.getClass())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

}
