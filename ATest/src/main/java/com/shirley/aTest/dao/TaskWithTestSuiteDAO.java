package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int currentPageNo, int pageSize,
			int taskId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select task_testsuite.id,task_testsuite.testsuite_id,name from task_testsuite left join testsuite on testsuite.id=task_testsuite.testsuite_id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != taskId) {
			sql.append(" and task_testsuite.task_id = ?");
			queryList.add(taskId);
		}
		if (0 != testSuiteId) {
			sql.append(" and task_testsuite.testsuite_id = ?");
			queryList.add(testSuiteId);
		}
		if (null != testSuiteName && !"".equals(testSuiteName)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + testSuiteName + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
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

	@Override
	public int QueryProductProjectWithSuiteCount(int taskId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(task_testsuite.id) from task_testsuite left join testsuite on testsuite.id=task_testsuite.testsuite_id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != taskId) {
			sql.append(" and task_testsuite.task_id = ?");
			queryList.add(taskId);
		}
		if (0 != testSuiteId) {
			sql.append(" and task_testsuite.testsuite_id = ?");
			queryList.add(testSuiteId);
		}
		if (null != testSuiteName && !"".equals(testSuiteName)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + testSuiteName + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		String sql = "insert into task_testsuite (task_id,testsuite_id) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { taskWithTestSuite.getTaskId(),
				taskWithTestSuite.getTestSuiteId() });
		return row > 0;
	}
	
	@Override
	public Boolean DeleteTaskWithTestSuite(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from task_testsuite where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}
	
	@Override
	public void DeleteTaskWithTestSuiteByTaskId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from task_testsuite where task_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}
	
	@Override
	public void DeleteTaskWithTestSuiteBySuiteId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from task_testsuite where testsuite_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}
	
	@Override
	public Boolean FindTaskWithTestSuiteBySuiteId(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(id) from task_testsuite where task_id = ? and testsuite_id = ?");
		int row = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] { taskWithTestSuite.getTaskId(), taskWithTestSuite.getTestSuiteId() },
				Integer.class);
		return row < 1;
	}


}
