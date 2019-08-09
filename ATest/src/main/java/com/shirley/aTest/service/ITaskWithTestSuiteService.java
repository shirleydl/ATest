package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午9:10:02
 */
public interface ITaskWithTestSuiteService {
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int taskId);

	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);

	public Boolean DeleteTaskWithTestSuite(int id);

	public Boolean UpdateTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite);
}
