package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.Interface;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午5:34:09
 */
public class InterfaceRowMapper implements RowMapper<Interface> {

	@Override
	public Interface mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		// 获取结果集中的数据
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int environmentId = rs.getInt("environment_id");
		String api = rs.getString("api");
		String description = rs.getString("description");
		String environmentName = rs.getString("environment.name");
		// 把数据封装对象
		Interface interfaceObject = new Interface();
		interfaceObject.setId(id);
		interfaceObject.setName(name);
		interfaceObject.setApi(api);
		interfaceObject.setDescription(description);
		interfaceObject.setEnvironmentId(environmentId);
		interfaceObject.setEnvironmentName(environmentName);
		return interfaceObject;
	}
}
