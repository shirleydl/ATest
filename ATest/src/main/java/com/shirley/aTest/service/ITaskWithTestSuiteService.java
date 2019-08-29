package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午9:10:02
 */
public interface ITaskWithTestSuiteService {
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int currentPageNo, int pageSize,
			int taskId, int testSuiteId, String testSuiteName);

	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);

	public Boolean DeleteTaskWithTestSuite(List<Integer> ids);

	public int QueryProductProjectWithSuiteCount(int taskId, int testSuiteId, String testSuiteName);
	
	public void AddTaskWithTestSuites(List<TaskWithTestSuite> taskWithTestSuites);
}
