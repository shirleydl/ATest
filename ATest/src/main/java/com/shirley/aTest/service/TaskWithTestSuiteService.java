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
	public List<TaskWithTestSuite> QueryTaskWithTestSuite(int currentPageNo, int pageSize, int taskId, int testSuiteId,
			String testSuiteName) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.QueryTaskWithTestSuite(currentPageNo, pageSize, taskId, testSuiteId, testSuiteName);
	}

	@Override
	public Boolean AddTaskWithTestSuite(TaskWithTestSuite taskWithTestSuite) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.AddTaskWithTestSuite(taskWithTestSuite);
	}

	@Override
	public void AddTaskWithTestSuites(List<TaskWithTestSuite> taskWithTestSuites) {
		// TODO Auto-generated method stub
		for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
			if(taskWithTestSuiteDAO.FindTaskWithTestSuiteBySuiteId(taskWithTestSuite))
				taskWithTestSuiteDAO.AddTaskWithTestSuite(taskWithTestSuite);
		}
	}

	@Override
	public Boolean DeleteTaskWithTestSuite(List<Integer> ids) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.DeleteTaskWithTestSuite(ids);
	}

	@Override
	public int QueryProductProjectWithSuiteCount(int taskId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		return taskWithTestSuiteDAO.QueryProductProjectWithSuiteCount(taskId, testSuiteId, testSuiteName);
	}

}
