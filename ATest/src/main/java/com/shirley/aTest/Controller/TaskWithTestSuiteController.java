package com.shirley.aTest.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.TaskWithTestSuite;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.TaskWithTestSuiteService;

/**
 * @Description: TODO(任务管理测试集控制类)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午5:37:43
 */
@Controller
public class TaskWithTestSuiteController {
	@Resource(name = "taskWithTestSuiteService")
	private TaskWithTestSuiteService taskWithTestSuiteService;

	@RequestMapping(value = "/queryTaskWithTestSuite", method = RequestMethod.GET)
	public String queryTaskWithTestSuite() {
		return "taskWithTestSuiteList";
	}

	@RequestMapping(value = "/copyTaskWithSuiteList", method = RequestMethod.GET)
	public String copyTaskWithSuiteList() {
		return "copyTaskWithSuiteList";
	}
	
	@RequestMapping(value = "/addTaskWithSuite", method = RequestMethod.GET)
	public String addTaskWithSuite() {
		return "addTaskWithSuite";
	}

	@RequestMapping(value = "/toQueryTaskWithTestSuites", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<TaskWithTestSuite> toQueryTaskWithTestSuites(Integer pageNumber, Integer pageSize,
			Integer taskId, Integer testSuiteId, String testSuiteName) {
		if (null != taskId && taskId != 0) {
			List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteService.QueryTaskWithTestSuite((null == pageNumber ? 0 : pageNumber),(null == pageSize ? 0 : pageSize), taskId,(null == testSuiteId ? 0 : testSuiteId), testSuiteName);
			PageHelper<TaskWithTestSuite> pageHelper = new PageHelper<TaskWithTestSuite>();
			// 统计总记录数
			pageHelper.setTotal(taskWithTestSuiteService.QueryProductProjectWithSuiteCount(taskId,(null == testSuiteId ? 0 : testSuiteId), testSuiteName));
			pageHelper.setRows(taskWithTestSuites);
			return pageHelper;
		}
		return null;
	}

	@RequestMapping(value = "/toAddTaskWithTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTaskWithTestSuite(Integer taskId, Integer testSuiteId) {
		if (null != taskId && null != testSuiteId) {
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setTaskId(taskId);
			taskWithTestSuite.setTestSuiteId(testSuiteId);
			return taskWithTestSuiteService.AddTaskWithTestSuite(taskWithTestSuite);
		}
		return false;
	}
	
	@RequestMapping(value = "/toAddTaskWithTestSuites", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTaskWithTestSuites(Integer taskId, Integer[] ids) {
		if (null != taskId && null != ids) {
			List<TaskWithTestSuite> taskWithTestSuites=new ArrayList<TaskWithTestSuite>();
			for(int id:ids){
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setTaskId(taskId);
			taskWithTestSuite.setTestSuiteId(id);
			taskWithTestSuites.add(taskWithTestSuite);
			}
			taskWithTestSuiteService.AddTaskWithTestSuites(taskWithTestSuites);
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/toDelTaskWithTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelTaskWithTestSuite(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return taskWithTestSuiteService.DeleteTaskWithTestSuite(idList);
	}


	@RequestMapping(value = "/toCopyTaskWithSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toCopyTaskWithSuite(Integer fromTaskId, Integer toTaskId) {
		if (null != fromTaskId && null != toTaskId) {
			List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteService.QueryTaskWithTestSuite(0, 0, fromTaskId, 0, null);
			for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
				taskWithTestSuite.setTaskId(toTaskId);
				taskWithTestSuiteService.AddTaskWithTestSuite(taskWithTestSuite);
			}
			return true;
		}
		return false;
	}

}
