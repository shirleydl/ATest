package com.shirley.aTestActuator.method;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.ReplaceDAO;
import com.shirley.aTestActuator.dao.TaskDAO;
import com.shirley.aTestActuator.dao.TaskWithTestSuiteDAO;
import com.shirley.aTestActuator.entity.DoTaskId;
import com.shirley.aTestActuator.entity.Replace;
import com.shirley.aTestActuator.entity.TaskWithTestSuite;
import com.shirley.aTestActuator.entity.ThreadDataManager;

/**
 * @Description: TODO(测试集执行)
 * @author 371683941@qq.com
 * @date 2019年7月8日 下午2:18:12
 */

public class DoQueryTestSuite implements Runnable {
	private TaskWithTestSuiteDAO taskWithTestSuiteDAO;
	private TaskDAO taskDao;
	private ReplaceDAO replaceDAO;
	private AssertsDAO assertsDAO;
	private DoTaskId doTaskId;
	private JdbcTemplate jdbcTemplate;
	private Replace replace;
//	 private Map<String, String> bindMapAll = new HashMap<String, String>();

	public DoQueryTestSuite(TaskDAO taskDao, DoTaskId doTaskId, JdbcTemplate jdbcTemplate) {
		this.taskDao = taskDao;
		this.doTaskId = doTaskId;
		this.jdbcTemplate = jdbcTemplate;
		taskWithTestSuiteDAO = new TaskWithTestSuiteDAO();
		taskWithTestSuiteDAO.setJdbcTemplate(jdbcTemplate);
		replaceDAO = new ReplaceDAO();
		replaceDAO.setJdbcTemplate(jdbcTemplate);
		assertsDAO=new AssertsDAO();
		assertsDAO.setJdbcTemplate(jdbcTemplate);
	}

	public DoQueryTestSuite() {
		// TODO Auto-generated constructor stub
	}

//	 public void putAllBindMapAll(Map<String, String> bindMapAll) {
//	 this.bindMapAll.putAll(bindMapAll);
//	 }

	public void doBefore() {
		List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteDAO
				.QueryTaskWithTestSuite(doTaskId.getBeforeTaskId());
		CountDownLatch latch = new CountDownLatch(taskWithTestSuites.size());
//		 DoQueryTestSuite doQueryTestSuite = new DoQueryTestSuite();
		ThreadDataManager threadDataManager=new ThreadDataManager();
		threadDataManager.setCallback(true);
		for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
			DoQueryRequest doQueryRequest = new DoQueryRequest(threadDataManager, taskWithTestSuite.getTestSuiteId(),
					doTaskId.getBeforeTaskId(), latch, jdbcTemplate, false, null, replace);
			Thread thread = new Thread(doQueryRequest);
			thread.start();
		}
		try {
			latch.await();
			Date date = new Date();
			System.out.println("BeforeTask " + doTaskId.getBeforeTaskId() + " finish " + date);
			 todo(threadDataManager.getBindMapAll());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void todo(Map<String, String> beforeMap) {
		List<TaskWithTestSuite> taskWithTestSuites = taskWithTestSuiteDAO.QueryTaskWithTestSuite(doTaskId.getId());
		CountDownLatch latch = new CountDownLatch(taskWithTestSuites.size());
		for (TaskWithTestSuite taskWithTestSuite : taskWithTestSuites) {
			DoQueryRequest doQueryRequest = new DoQueryRequest(null, taskWithTestSuite.getTestSuiteId(),
					doTaskId.getId(), latch, jdbcTemplate, true, beforeMap, replace);
			new Thread(doQueryRequest).start();
		}
		try {
			latch.await();
			taskDao.UpdateFinishTaskStatus(doTaskId.getId(), 3);
			Date date = new Date();
			System.out.println("Task " + doTaskId.getId() + " finish " + date);

			if (doTaskId.getIsSend() == 1 && doTaskId.getEmailId() != 0
					&& (doTaskId.getIsFailSend() == 0 || assertsDAO.QueryAssertsCount(doTaskId.getId(), "fail") >0)) {
				SendReport sendReport = new SendReport(doTaskId.getId(), doTaskId.getEmailId(), doTaskId.getName(),
						jdbcTemplate);
				sendReport.toDo();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (doTaskId.getReplaceInfoId() != 0) {
			replace = replaceDAO.QueryReplaceById(doTaskId.getReplaceInfoId());
		}
		if (doTaskId.getBeforeTaskId() != 0)
			doBefore();
		else
			todo(null);
	}

}
