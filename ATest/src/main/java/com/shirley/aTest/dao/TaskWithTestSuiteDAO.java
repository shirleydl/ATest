package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO(任务-测试集DAO)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:36:26
 */
@Repository("taskWithTestSuiteDAO")
public class TaskWithTestSuiteDAO implements ITaskWithTestSuiteDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int taskId) {
		// TODO Auto-generated method stub
		String sql = "select task_testsuite.id,task_testsuite.testsuite_id,testsuite.name,task_testsuite.priority from task_testsuite left join testsuite on task_testsuite.testsuite_id=testsuite.id where task_testsuite.task_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, taskId);
		List<TaskWithTestSuite> taskWithTestSuites = new ArrayList<TaskWithTestSuite>();
		for (Map<String, Object> row : list) {
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setId((Integer) row.get("id"));
			taskWithTestSuite.setTestSuiteId((Integer) row.get("testsuite_id"));
			taskWithTestSuite.setTestSuiteName((String) row.get("name"));
			taskWithTestSuite.setPriority((Integer) row.get("priority"));
			taskWithTestSuites.add(taskWithTestSuite);
		}
		return taskWithTestSuites;
	}

	@Override
	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		String sql = "insert into task_testsuite (task_id,testsuite_id,priority) values (?,?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { taskWithTestSuite.getTaskId(),
				taskWithTestSuite.getTestSuiteId(), taskWithTestSuite.getPriority() });
		return row > 0;
	}

	@Override
	public Boolean DeleteTaskWithTestSuite(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from task_testsuite where id=?";
		int row = this.jdbcTemplate.update(sql, id);
		return row > 0;
	}

	@Override
	public Boolean UpdateTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		String sql = "update task_testsuite set task_id=?,testsuite_id=?,priority=? where id = ?";
		Object args[] = new Object[] { taskWithTestSuite.getTaskId(), taskWithTestSuite.getTestSuiteId(),
				taskWithTestSuite.getPriority(), taskWithTestSuite.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
