package com.shirley.aTestActuator.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.db.EmailRowMapper;
import com.shirley.aTestActuator.entity.Email;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月5日 上午8:58:12
 */
public class EmailDAO {
	// 获取JdbcTemplate实例
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Email QueryEmailById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,from_email,pass,host,receives_email from email where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new EmailRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

}
