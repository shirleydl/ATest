package com.shirley.aTestActuator.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.entity.AssertResult;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月11日 上午10:22:10
 */
public class AssertsDAO {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void AddAsserts(AssertResult assertResult) {
		// TODO Auto-generated method stub
		String sql = "insert into asserts (task_id,case_id,url,requestcontent,responsecontent,assertresult,status) values (?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, new Object[] { assertResult.getTaskId(), assertResult.getCaseId(),assertResult.getUrl(),
				assertResult.getRequestContent().length() > 5000 ? assertResult.getRequestContent().substring(0, 5000)
						: assertResult.getRequestContent(),
				assertResult.getResponseContent().length() > 5000 ? assertResult.getResponseContent().substring(0, 5000)
						: assertResult.getResponseContent(),
				assertResult.getAssertResult(), assertResult.getStatus() });
	}
}
