package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.TaskWithTestSuiteDAO;
import com.shirley.aTest.entity.TaskWithTestSuite;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午9:10:56
 */
@Service("taskWithTestSuiteService")
public class TaskWithTestSuiteService implements ITaskWithTestSuiteService {
	@Resource(name = "taskWithTestSuiteDAO")
	private TaskWithTestSuiteDAO taskWithTestSuiteDAO;

	@Override
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int taskId) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.QueryTaskWithTestSuite(taskId);
	}

	@Override
	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.AddTaskWithTestSuite(taskWithTestSuite);
	}

	@Override
	public Boolean DeleteTaskWithTestSuite(int id) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.DeleteTaskWithTestSuite(id);
	}

	@Override
	public Boolean UpdateTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.UpdateTaskWithTestSuite(taskWithTestSuite);
	}

}
