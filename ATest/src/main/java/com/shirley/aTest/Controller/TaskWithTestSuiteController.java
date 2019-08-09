package com.shirley.aTest.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.TaskWithTestSuite;
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

	@RequestMapping(value = "/toQueryTaskWithTestSuites", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toQueryTaskWithTestSuites(Integer taskId) {
		if (null != taskId) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteService.QueryTaskWithTestSuite(taskId);
			Collections.sort(taskWithTestSuites);
			map.put("testSuiteWithCases", taskWithTestSuites);
			return map;
		}
		return null;
	}

	@RequestMapping(value = "/toAddTaskWithTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTaskWithTestSuite(Integer taskId, Integer testSuiteId, Integer priority) {
		if (null != taskId && null != testSuiteId) {
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setTaskId(taskId);
			taskWithTestSuite.setTestSuiteId(testSuiteId);
			taskWithTestSuite.setPriority(null == priority ? 0 : priority);
			return taskWithTestSuiteService.AddTaskWithTestSuite(taskWithTestSuite);
		}
		return false;
	}

	@RequestMapping(value = "/toDelTaskWithTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelTaskWithTestSuite(Integer id) {
		if (null != id)
			return taskWithTestSuiteService.DeleteTaskWithTestSuite(id);
		return false;
	}

	@RequestMapping(value = "/toUpdateTaskWithTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateTaskWithTestSuite(Integer id, Integer taskId, Integer testSuiteId, Integer priority) {
		if (null != id && null != testSuiteId && null != taskId) {
			TaskWithTestSuite taskWithTestSuite = new TaskWithTestSuite();
			taskWithTestSuite.setId(id);
			taskWithTestSuite.setTaskId(taskId);
			taskWithTestSuite.setTestSuiteId(testSuiteId);
			taskWithTestSuite.setPriority(priority);
			return taskWithTestSuiteService.UpdateTaskWithTestSuite(taskWithTestSuite);
		}
		return false;
	}

	@RequestMapping(value = "/toCopyTaskWithSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toCopyTaskWithSuite(Integer fromTaskId, Integer toTaskId) {
		if (null != fromTaskId && null != toTaskId) {
			List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteService.QueryTaskWithTestSuite(fromTaskId);
			for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
				taskWithTestSuite.setTaskId(toTaskId);
				taskWithTestSuiteService.AddTaskWithTestSuite(taskWithTestSuite);
			}
			return true;
		}
		return false;
	}

}
