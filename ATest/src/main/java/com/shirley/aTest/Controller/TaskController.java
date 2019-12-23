package com.shirley.aTest.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.Task;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.TaskService;

/**
 * @Description: TODO(任务控制类)
 * @author 371683941@qq.com
 * @date 2019年6月24日 上午11:58:54
 */
@Controller
public class TaskController {
	@Resource(name = "taskService")
	private TaskService taskService;
	//
	// @Resource(name = "taskWithTestSuiteService")
	// private TaskWithTestSuiteService taskWithTestSuiteService;
	//
	// @Resource(name = "testSuiteWithCaseService")
	// private TestSuiteWithCaseService testSuiteWithCaseService;
	//
	// @Resource(name = "assertResultService")
	// private AssertResultService assertResultService;

	@RequestMapping(value = "/taskList", method = RequestMethod.GET)
	public String taskList() {
		return "taskList";
	}

	@RequestMapping(value = "/addTask", method = RequestMethod.GET)
	public String addTask() {
		return "addTask";
	}

	@RequestMapping(value = "/queryTask", method = RequestMethod.GET)
	public String queryTask() {
		return "taskDetail";
	}

	@RequestMapping(value = "/setConfig", method = RequestMethod.GET)
	public String setConfig() {
		return "setConfig";
	}

	@RequestMapping(value = "/queryTasks", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Task> queryTasks(Integer pageNumber, Integer pageSize, Integer id, String name,
			String testCaseName) {
		List<Task> tasks = taskService.QueryTask((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name);
		PageHelper<Task> pageHelper = new PageHelper<Task>();
		// 统计总记录数
		pageHelper.setTotal(taskService.QueryTaskCount((null == id ? 0 : id), testCaseName));
		pageHelper.setRows(tasks);
		return pageHelper;
	}

	@RequestMapping(value = "/toAddTask", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTask(String name, String startTime) {
		if (null != name && !"".equals(name) && null != startTime && !"".equals(startTime)) {
			TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			simpleDateFormat.setTimeZone(tz);
			Task task = new Task();
			task.setName(name);
			try {
				task.setStartTime(simpleDateFormat.parse(startTime).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return taskService.AddTask(task);
		}
		return false;
	}

	@RequestMapping(value = "/toDelTask", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelTask(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return taskService.DeleteTasks(idList);
	}

	@RequestMapping(value = "/toQueryTask", method = RequestMethod.POST)
	@ResponseBody
	public Task toQueryTask(Integer id) {
		if (null != id)
			return taskService.QueryTaskById(id);
		return null;
	}

	@RequestMapping(value = "/toUpdateTask", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateTask(Integer id, String name, String startTime) {
		if (null != name && !"".equals(name) && null != id && null != startTime && !"".equals(startTime)) {
			TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			simpleDateFormat.setTimeZone(tz);
			Task task = new Task();
			task.setId(id);
			task.setName(name);
			try {
				task.setStartTime(simpleDateFormat.parse(startTime).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return taskService.UpdateTask(task);
		}
		return false;
	}

	@RequestMapping(value = "/toUpdateStatus", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateStatus(Integer id, Integer status) {
		if (null != id && null != status) {
			Task task = new Task();
			task.setId(id);
			task.setStatus(status);
			return taskService.UpdateTaskStatus(task);
		}
		return false;
	}

	@RequestMapping(value = "/toUpdateIsLoop", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateIsLoop(Integer id, Integer isLoop) {
		if (null != id && null != isLoop) {
			Task task = new Task();
			task.setId(id);
			task.setIsLoop(isLoop);
			return taskService.UpdateTaskIsLoop(task);
		}
		return false;
	}

	@RequestMapping(value = "/toUpdateIsSend", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateIsSend(Integer id, Integer isSend) {
		if (null != id && null != isSend) {
			Task task = new Task();
			if (0 == isSend) {
				task.setId(id);
				task.setIsSend(isSend);
				return taskService.UpdateTaskIsSend(task);
			}
			if (1 == isSend) {
				task = taskService.QueryTaskById(id);
				if (0 != task.getEmailId()) {
					task.setIsSend(isSend);
					return taskService.UpdateTaskIsSend(task);
				}
			}
		}
		return false;

	}

	@RequestMapping(value = "/toUpdateConfig", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateConfig(Integer taskId, Integer beforeTaskId, Integer replaceInfoId, Integer emailId,Integer isFailSend) {
		if (null != taskId) {
			Task task = new Task();
			task.setId(taskId);
			task.setBeforeTaskId(null != beforeTaskId ? beforeTaskId : 0);
			task.setReplaceInfoId(null != replaceInfoId ? replaceInfoId : 0);
			task.setEmailId(null != emailId ? emailId : 0);
			task.setIsFailSend(null != isFailSend ? isFailSend : 0);
			return taskService.UpdateTaskConfig(task);
		}
		return false;
	}

	@RequestMapping(value = "/queryTaskById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Task> queryTaskById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
			List<Task> tasks = taskService.QueryTask(0, 0, Integer.parseInt(keyword), null);
			BigAutocompleteDataHelper<Task> jsonHelper = new BigAutocompleteDataHelper<Task>();
			jsonHelper.setData(tasks);
			return jsonHelper;
		}
		return null;
	}

	@RequestMapping(value = "/queryTaskByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Task> queryTaskByName(String keyword) {
		List<Task> testSuites = taskService.QueryTask(0, 0, 0, keyword);
		BigAutocompleteDataHelper<Task> jsonHelper = new BigAutocompleteDataHelper<Task>();
		jsonHelper.setData(testSuites);
		return jsonHelper;
	}

	// @RequestMapping(value = "/toRequestTaskById", method = RequestMethod.GET)
	// @ResponseBody
	// public void toRequestTaskById(Integer taskId) {
	// List<TaskWithTestSuite> taskWithTestSuites =
	// taskWithTestSuiteService.QueryTaskWithTestSuite(taskId);
	// Collections.sort(taskWithTestSuites);
	// for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
	// Map<String,String> bindMap=new HashMap<String,String>();
	// List<Request> requestList =
	// testSuiteWithCaseService.QueryTestCaseByTestSuiteRequest(taskWithTestSuite.getTestSuiteId());
	// Collections.sort(requestList);
	// for (Request request : requestList) {
	// DoRequest doRequest = new DoRequest(taskId, request,bindMap);
	// ResponseContent responseContent = new ResponseContent();
	// responseContent = doRequest.toRequest();
	// doRequest.toUpdateVariables(responseContent);
	// bindMap.putAll( doRequest.toBind(responseContent));
	// AssertResult assertResult=doRequest.toAssert(responseContent);
	// assertResultService.AddAsserts(assertResult);
	// }
	// }
	// taskService.UpdateTaskStatus(taskId, 3);
	// }

}
