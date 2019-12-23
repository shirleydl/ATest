package com.shirley.aTestActuator.method;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.dao.SuiteWithCaseControllerDAO;
import com.shirley.aTestActuator.dao.TestSuiteWithCaseDAO;
import com.shirley.aTestActuator.entity.AssertResult;
import com.shirley.aTestActuator.entity.DoIndex;
import com.shirley.aTestActuator.entity.Replace;
import com.shirley.aTestActuator.entity.Request;
import com.shirley.aTestActuator.entity.ResponseContent;
import com.shirley.aTestActuator.entity.SuiteWithCaseController;
import com.shirley.aTestActuator.entity.ThreadDataManager;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月4日 下午4:46:15
 */
public class DoQueryRequest implements Runnable {
	private TestSuiteWithCaseDAO testSuiteWithCaseDAO;
	private SuiteWithCaseControllerDAO suiteWithCaseControllerDAO;
	private AssertsDAO assertsDAO;
	private int taskId;
	private int testSuiteId;
	private CountDownLatch latch;
	private Map<String, String> bindMapAll;
	private ThreadDataManager threadDataManager;
	private Boolean toAssertsOrNot;
	private Replace replace;

	public DoQueryRequest(ThreadDataManager threadDataManager, int testSuiteId, int taskId, CountDownLatch latch,
			JdbcTemplate jdbcTemplate, Boolean toAssertsOrNot, Map<String, String> bindMapAll, Replace replace) {
		this.threadDataManager = threadDataManager;
		this.testSuiteId = testSuiteId;
		this.taskId = taskId;
		this.latch = latch;
		this.toAssertsOrNot = toAssertsOrNot;
		testSuiteWithCaseDAO = new TestSuiteWithCaseDAO();
		suiteWithCaseControllerDAO = new SuiteWithCaseControllerDAO();
		testSuiteWithCaseDAO.setJdbcTemplate(jdbcTemplate);
		suiteWithCaseControllerDAO.setJdbcTemplate(jdbcTemplate);
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
			bindMap.putAll(bindMapAll);
		List<Request> requestList = testSuiteWithCaseDAO.QueryTestCaseByTestSuiteRequest(testSuiteId);
		Collections.sort(requestList);
		List<SuiteWithCaseController> suiteWithCaseControllerList = suiteWithCaseControllerDAO
				.QueryControllerByTestSuiteId(testSuiteId);
		Collections.sort(suiteWithCaseControllerList);
		/**
		 * 初始化要执行的控制器位置
		 */
		DoIndex doIndex = new DoIndex();
		doIndex.setControllerIndex(0);

		for (int i = 0; i < requestList.size(); i++) {
			TestSuiteController controller = new TestSuiteController(taskId,testSuiteId,bindMap, assertsDAO, replace);
			doIndex.setRequestIndex(i);// 初始请求开始位置
			/**
			 * 判断当前请求是否需进入控制器
			 */
			for (int j = doIndex.getControllerIndex(); j < suiteWithCaseControllerList.size(); j++) {
				if (suiteWithCaseControllerList.get(j).getStartPriority() == requestList.get(i).getPriority()) {
					doIndex = controller.doController(requestList, suiteWithCaseControllerList, j,i);
				}
			}

			i = doIndex.getRequestIndex();// 赋值当前请求需开始的位置
			if (i < requestList.size()) {
				/**
				 * 判断当前请求是否需要延迟启动
				 */
				if (requestList.get(i).getDelay() > 0) {
					try {
						Thread.sleep(requestList.get(i).getDelay());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				/**
				 * 处理替换循环用例变量更新到请求对象里
				 */
				if (null != replace) {
					String replaceUrl = replace.getReplaceUrl().get(requestList.get(i).getUrl());
					Map<String, String> replaceVariables = replace.getReplaceData()
							.get(requestList.get(i).getCaseId() + "");
					if (null != replaceUrl && !"".equals(replaceUrl))
						requestList.get(i).setUrl(replaceUrl);
					if (null != replaceVariables && replaceVariables.size() > 0)
						requestList.get(i).putVariables(replaceVariables);
				}
				/**
				 * 开始请求、断言
				 */
				DoRequest doRequest = new DoRequest(taskId,testSuiteId,requestList.get(i), bindMap);
				ResponseContent responseContent = new ResponseContent();
				responseContent = doRequest.toRequest();
				doRequest.toUpdateVariables(responseContent);
				bindMap.putAll(doRequest.toBind(responseContent));
				if (toAssertsOrNot) {
					AssertResult assertResult = doRequest.toAssert(responseContent);
					assertsDAO.AddAsserts(assertResult);
				}
			}
		}
		if (null != threadDataManager&&threadDataManager.isCallback()) {
			threadDataManager.setBindMapAll(bindMap);
		}
		latch.countDown();
	}

}
