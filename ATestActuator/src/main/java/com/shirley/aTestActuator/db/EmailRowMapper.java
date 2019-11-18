package com.shirley.aTestActuator.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTestActuator.entity.Email;


/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年11月5日 上午10:09:32
*/
public class EmailRowMapper implements RowMapper<Email>  {

	@Override
	public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String from = rs.getString("from_email");
		String pass = rs.getString("pass");
		String host=rs.getString("host");
		String receives=rs.getString("receives_email");
		Email email = new Email();
		email.setId(id);
		email.setName(name);
		email.setFrom(from);
		email.setPass(pass);
		email.setHost(host);
		email.setReceives(receives);
		return email;
	}

}
