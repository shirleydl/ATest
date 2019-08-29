package com.shirley.aTestActuator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.entity.TaskWithTestSuite;

/**
 * @Description: TODO(任务-测试集DAO)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:36:26
 */

public class TaskWithTestSuiteDAO {
	// 获取JdbcTemplate实例
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int taskId) {
		// TODO Auto-generated method stub
		String sql = "select task_testsuite.id,task_testsuite.testsuite_id,testsuite.name from task_testsuite left join testsuite on task_testsuite.testsuite_id=testsuite.id where task_testsuite.task_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, taskId);
		List<TaskWithTestSuite> taskWithTestSuites = new ArrayList<TaskWithTestSuite>();
		for (Map<String, Object> row : list) {
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setId((Integer) row.get("id"));
			taskWithTestSuite.setTestSuiteId((Integer) row.get("testsuite_id"));
			taskWithTestSuite.setTestSuiteName((String) row.get("name"));
			taskWithTestSuites.add(taskWithTestSuite);
		}
		return taskWithTestSuites;
	}

}
