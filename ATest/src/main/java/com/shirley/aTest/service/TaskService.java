package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.AssertResultDAO;
import com.shirley.aTest.dao.TaskDAO;
import com.shirley.aTest.dao.TaskWithTestSuiteDAO;
import com.shirley.aTest.entity.Task;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午9:05:27
 */
@Service("taskService")
public class TaskService implements ITaskService {
	@Resource(name = "taskDAO")
	private TaskDAO taskDAO;
	@Resource(name = "taskWithTestSuiteDAO")
	private TaskWithTestSuiteDAO taskWithTestSuiteDAO;
	@Resource(name = "assertResultDAO")
	private AssertResultDAO assertResultDAO;

	@Override
	public List<Task> QueryTask(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		return taskDAO.QueryTask(currentPageNo, pageSize, id, name);
	}

	@Override
	public int QueryTaskCount(int id, String name) {
		// TODO Auto-generated method stub
		return taskDAO.QueryTaskCount(id, name);
	}

	@Override
	public Task QueryTaskById(int id) {
		// TODO Auto-generated method stub
		return taskDAO.QueryTaskById(id);
	}

	@Override
	public Boolean AddTask(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.AddTask(task);
	}

	@Override
	public Boolean DeleteTasks(List<Integer> ids) {
		// TODO Auto-generated method stub
		if (taskDAO.FindTaskBeforeTask(ids) && taskDAO.DeleteTasks(ids)) {
			taskWithTestSuiteDAO.DeleteTaskWithTestSuiteByTaskId(ids);
			assertResultDAO.DeleteAssertsByTaskId(ids);
			return true;
		}
		return false;
	}

	@Override
	public Boolean UpdateTask(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTask(task);
	}

	@Override
	public Boolean UpdateTaskStatus(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTaskStatus(task);
	}

	@Override
	public Boolean UpdateTaskStatus(int id, int status) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTaskStatus(id, status);
	}

	@Override
	public Boolean UpdateTaskConfig(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTaskConfig(task);
	}

	@Override
	public Boolean UpdateTaskIsLoop(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTaskIsLoop(task);
	}

	@Override
	public Boolean UpdateTaskIsSend(Task task) {
		// TODO Auto-generated method stub
		return taskDAO.UpdateTaskIsSend(task);
	}

}
