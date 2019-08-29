package com.shirley.aTestActuator.method;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.TestSuiteWithCaseDAO;
import com.shirley.aTestActuator.entity.AssertResult;
import com.shirley.aTestActuator.entity.Replace;
import com.shirley.aTestActuator.entity.Request;
import com.shirley.aTestActuator.entity.ResponseContent;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月4日 下午4:46:15
 */
public class DoQueryRequest implements Runnable {
	private TestSuiteWithCaseDAO testSuiteWithCaseDAO;
	private AssertsDAO assertsDAO;
	private int taskId;
	private int testSuiteId;
	private CountDownLatch latch;
	private Map<String, String> bindMapAll;
	private DoQueryTestSuite callBack;
	private Boolean toAssertsOrNot;
	private Replace replace;

	public DoQueryRequest(DoQueryTestSuite callBack, int testSuiteId, int taskId, CountDownLatch latch,
			JdbcTemplate jdbcTemplate, Boolean toAssertsOrNot, Map<String, String> bindMapAll, Replace replace) {
		this.callBack = callBack;
		this.testSuiteId = testSuiteId;
		this.taskId = taskId;
		this.latch = latch;
		this.toAssertsOrNot = toAssertsOrNot;
		testSuiteWithCaseDAO = new TestSuiteWithCaseDAO();
		testSuiteWithCaseDAO.setJdbcTemplate(jdbcTemplate);
		assertsDAO = new AssertsDAO();
		assertsDAO.setJdbcTemplate(jdbcTemplate);
		this.bindMapAll = bindMapAll;
		this.replace = replace;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> bindMap = new HashMap<String, String>();
		if (null != bindMapAll)
			bindMap = bindMapAll;
		List<Request> requestList = testSuiteWithCaseDAO.QueryTestCaseByTestSuiteRequest(testSuiteId);
		Collections.sort(requestList);
		for (Request request : requestList) {
			if (null != replace) {
				String replaceUrl = replace.getReplaceUrl().get(request.getUrl());
				Map<String, String> replaceVariables = replace.getReplaceData().get(request.getCaseId() + "");
				if (null != replaceUrl &&!"".equals(replaceUrl))
					request.setUrl(replaceUrl);
				if (null != replaceVariables && replaceVariables.size() > 0)
					request.putVariables(replaceVariables);
			}
			DoRequest doRequest = new DoRequest(taskId, request, bindMap);
			ResponseContent responseContent = new ResponseContent();
			responseContent = doRequest.toRequest();
			doRequest.toUpdateVariables(responseContent);
			bindMap.putAll(doRequest.toBind(responseContent));
			if (toAssertsOrNot) {
				AssertResult assertResult = doRequest.toAssert(responseContent);
				assertsDAO.AddAsserts(assertResult);
			}
		}
		if (null != callBack) {
			callBack.putAllBindMapAll(bindMap);
		}
		latch.countDown();
	}

}
