package com.shirley.aTestActuator.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		String sql = "insert into asserts (task_id,suite_id,case_id,url,requestcontent,responsecontent,assertresult,status) values (?,?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, new Object[] { assertResult.getTaskId(), assertResult.getTestSuiteId(),
				assertResult.getCaseId(), assertResult.getUrl(),
				assertResult.getRequestContent().length() > 5000 ? assertResult.getRequestContent().substring(0, 5000)
						: assertResult.getRequestContent(),
				assertResult.getResponseContent().length() > 5000 ? assertResult.getResponseContent().substring(0, 5000)
						: assertResult.getResponseContent(),
				assertResult.getAssertResult(), assertResult.getStatus() });
	}

	public List<AssertResult> QueryAsserts(int taskId) {
		StringBuffer sql = new StringBuffer(
				"select asserts.id,asserts.case_id,asserts.url,asserts.status,asserts.createtime,asserts.requestcontent,asserts.responsecontent,asserts.assertresult,testcase.name,testcase.description from asserts left join testcase on asserts.case_id=testcase.id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != taskId) {
			sql.append(" and asserts.task_id = ?");
			queryList.add(taskId);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<AssertResult> assertResultList = new ArrayList<AssertResult>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> row : list) {
			AssertResult assertResult = new AssertResult();
			assertResult.setId((Integer) row.get("id"));
			assertResult.setCaseId((Integer) row.get("case_id"));
			assertResult.setCaseName((String) row.get("name"));
			assertResult.setCaseDescription((String) row.get("description"));
			assertResult.setUrl((String) row.get("url"));
			assertResult.setStatus((String) row.get("status"));
			assertResult.setCreateTime(df.format((Timestamp) row.get("createtime")));
			assertResult.setRequestContent((String) row.get("requestcontent"));
			assertResult.setResponseContent((String) row.get("responsecontent"));
			assertResult.setAssertResult((String) row.get("assertresult"));
			assertResultList.add(assertResult);
		}
		return assertResultList;
	}

	public int QueryAssertsCount(int taskId, String status) {
		StringBuffer sql = new StringBuffer("select count(id) from asserts where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != taskId) {
			sql.append(" and task_id = ?");
			queryList.add(taskId);
		}
		if (null != status && !"".equals(status)) {
			sql.append(" and status = ?");
			queryList.add(status);
		}

		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	public Boolean DeleteAssertsByTaskId(int id) {
		String sql = "delete from asserts where task_id = ?";
		int row = this.jdbcTemplate.update(sql, new Object[] { id });
		return row > 0;
	}
}
