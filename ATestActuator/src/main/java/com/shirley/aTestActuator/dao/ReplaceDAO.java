package com.shirley.aTestActuator.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.db.ReplaceRowMapper;
import com.shirley.aTestActuator.entity.Replace;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午9:29:12
 */

public class ReplaceDAO {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Replace QueryReplaceById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,description,replace_url,replace_data,replace_split from replaceInfo where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new ReplaceRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

}
