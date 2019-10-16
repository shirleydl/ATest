package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO(任务-测试集DAO接口)
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午3:17:56
 */
public interface ITaskWithTestSuiteDAO {
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int currentPageNo, int pageSize,
			int taskId, int testSuiteId, String testSuiteName);

	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);

	public Boolean DeleteTaskWithTestSuite(List<Integer> ids);

	public void DeleteTaskWithTestSuiteByTaskId(List<Integer> ids);

	public void DeleteTaskWithTestSuiteBySuiteId(List<Integer> ids);

	public int QueryProductProjectWithSuiteCount(int taskId, int testSuiteId, String testSuiteName);

	public Boolean FindTaskWithTestSuiteBySuiteId(TaskWithTestSuite taskWithTestSuite);
	

}
