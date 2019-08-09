package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO(任务-测试集DAO接口)
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午3:17:56
 */
public interface ITaskWithTestSuiteDAO {
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int taskId);

	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);

	public Boolean DeleteTaskWithTestSuite(int id);

	public Boolean UpdateTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);

}
