package com.shirley.aTestActuator.method;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.shirley.aTestActuator.dao.AssertsDAO;
import com.shirley.aTestActuator.entity.AssertResult;
import com.shirley.aTestActuator.entity.Asserts;
import com.shirley.aTestActuator.entity.DoIndex;
import com.shirley.aTestActuator.entity.Replace;
import com.shirley.aTestActuator.entity.Request;
import com.shirley.aTestActuator.entity.ResponseContent;
import com.shirley.aTestActuator.entity.SuiteWithCaseController;
import com.shirley.aTestActuator.util.AssertMehtod;
import com.shirley.aTestActuator.util.GetValue;
import com.shirley.aTestActuator.util.ListCopy;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月11日 上午10:43:00
 */
public class TestSuiteController {
	private Map<String, String> bindMap;
	private int taskId;
	private int testSuiteId;
	private AssertsDAO assertsDAO;
	private Replace replace;

	public TestSuiteController(int taskId,int testSuiteId, Map<String, String> bindMap, AssertsDAO assertsDAO, Replace replace) {
		this.taskId = taskId;
		this.testSuiteId = testSuiteId;
		this.bindMap = bindMap;
		this.assertsDAO = assertsDAO;
		this.replace = replace;
	}

	public DoIndex doController(List<Request> requests, List<SuiteWithCaseController> suiteWithCaseControllerList,
			int controllerIndex,int requestIndex) {
		/**
		 * 初始化要执行的控制器位置、请求的优先级
		 */
		DoIndex doIndex = new DoIndex();
		doIndex.setControllerIndex(controllerIndex);
		doIndex.setRequestIndex(requestIndex);
		/**
		 * 判断控制器类型
		 */
		if ("LOOP".equals(suiteWithCaseControllerList.get(controllerIndex).getControllerType())) {
			/**
			 * 筛选出循环开始到结束的请求集，调用循环控制器方法
			 */
			List<Request> newRequests = new ArrayList<Request>();
			for (Request request : requests) {
				if (suiteWithCaseControllerList.get(controllerIndex).getStartPriority() <= request.getPriority()
						&& suiteWithCaseControllerList.get(controllerIndex).getEndPriority() >= request.getPriority()) {
					newRequests.add(request);
				}
			}

			return doLoop(newRequests, suiteWithCaseControllerList, controllerIndex);
		}

		if ("CONDITION".equals(suiteWithCaseControllerList.get(controllerIndex).getControllerType())) {
			/**
			 * 筛选出循环开始到结束的请求集，调用条件控制器方法
			 */
			List<Request> newRequests = new ArrayList<Request>();
			for (Request request : requests) {
				if (suiteWithCaseControllerList.get(controllerIndex).getStartPriority() <= request.getPriority()
						&& suiteWithCaseControllerList.get(controllerIndex).getEndPriority() >= request.getPriority()) {
					newRequests.add(request);
				}
			}
			return doCondition(newRequests.size(),suiteWithCaseControllerList,controllerIndex,requestIndex);
		}

		return doIndex;
	}

	/**
	 * 循环控制器方法
	 */
	public DoIndex doLoop(List<Request> newRequests, List<SuiteWithCaseController> suiteWithCaseControllerList,
			int controllerIndex) {
		/**
		 * 初始化控制器结束后要执行的控制器位置、请求的优先级
		 */
		int nextControllerIndex = controllerIndex + 1;
		DoIndex doIndex = new DoIndex();
		doIndex.setControllerIndex(nextControllerIndex);
		doIndex.setRequestIndex(newRequests.size());

		GetValue getValue = new GetValue(bindMap, null, null);
		AssertMehtod assertMehtod = new AssertMehtod();
		List<Asserts> assertlist = suiteWithCaseControllerList.get(controllerIndex).getAsserts();
		/**
		 * 开始循环
		 */
		for (int lc = 0; lc < suiteWithCaseControllerList.get(controllerIndex).getLoopCount(); lc++) {
			/**
			 * 执行控制器启动条件
			 */
			if (null != assertlist && assertlist.size() > 0) {
				for (Asserts assertObject : assertlist) {
					if (!assertMehtod.doAssert(getValue.getValue(assertObject.getKey()),
							getValue.getValue(assertObject.getValue()), assertObject.getMethod())) {
						return doIndex;
					}
				}
			}
			/**
			 * 每次循环，创建新的请求集来深复制需循环的请求集
			 */
			List<Request> loopRequests = new ArrayList<Request>();
			try {
				loopRequests = ListCopy.deepCopy(newRequests);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/**
			 * 开始循环需循环的请求集
			 */
			DoIndex doIndexNewRequests = new DoIndex();
			for (int i = 0; i < loopRequests.size(); i++) {
				doIndexNewRequests.setRequestIndex(i);// 初始请求开始位置
				/**
				 * 判断当前请求是否需进入控制器
				 */
				for (int j = nextControllerIndex; j < suiteWithCaseControllerList.size(); j++) {
					if (suiteWithCaseControllerList.get(j).getStartPriority() == loopRequests.get(i).getPriority()) {
						doIndexNewRequests = doController(loopRequests, suiteWithCaseControllerList, j,i);
					}
				}

				i = doIndexNewRequests.getRequestIndex();// 赋值当前请求需开始的位置
				if (i < loopRequests.size()) {
					/**
					 * 判断当前请求是否需要延迟启动
					 */
					if (loopRequests.get(i).getDelay() > 0) {
						try {
							Thread.sleep(loopRequests.get(i).getDelay());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					/**
					 * 处理循环用例变量更新到请求对象里
					 */
					if (null != loopRequests.get(i).getCaseVariables()) {
						Map<String, String> singleCaseVariable = new HashMap<String, String>();
						for (Entry<String, String> e : loopRequests.get(i).getCaseVariables().entrySet()) {
							if ((lc + 1) % e.getValue().split(loopRequests.get(i).getCaseVariableSplit()).length != 0)
								singleCaseVariable.put(e.getKey(),
										e.getValue()
												.split(loopRequests.get(i)
														.getCaseVariableSplit())[((lc + 1) % e.getValue().split(
																loopRequests.get(i).getCaseVariableSplit()).length)
																- 1]);
							else
								singleCaseVariable.put(e.getKey(),
										e.getValue()
												.split(loopRequests.get(i)
														.getCaseVariableSplit())[e.getValue().split(
																loopRequests.get(i).getCaseVariableSplit()).length
																- 1]);
						}
						loopRequests.get(i).putVariables(singleCaseVariable);
					}
					/**
					 * 处理替换循环用例变量更新到请求对象里
					 */
					if (null != replace) {
						String replaceUrl = replace.getReplaceUrl().get(loopRequests.get(i).getUrl());
						Map<String, String> replaceVariables = replace.getReplaceData()
								.get(loopRequests.get(i).getCaseId() + "");
						if (null != replaceUrl && !"".equals(replaceUrl))
							loopRequests.get(i).setUrl(replaceUrl);
						if (null != replaceVariables && replaceVariables.size() > 0) {
							if (null != replace.getSplit()) {
								Map<String, String> singleReplaceVariable = new HashMap<String, String>();
								for (Entry<String, String> e : replaceVariables.entrySet()) {
									if ((lc + 1) % e.getValue().split(replace.getSplit()).length != 0)
										singleReplaceVariable
												.put(e.getKey(),
														e.getValue()
																.split(replace.getSplit())[((lc + 1)
																		% e.getValue().split(replace.getSplit()).length)
																		- 1]);
									else
										singleReplaceVariable.put(e.getKey(),
												e.getValue().split(replace
														.getSplit())[e.getValue().split(replace.getSplit()).length
																- 1]);
								}
								loopRequests.get(i).putVariables(singleReplaceVariable);
							} else
								loopRequests.get(i).putVariables(replaceVariables);
						}
					}
					/**
					 * 开始请求、断言
					 */
					DoRequest doRequest = new DoRequest(taskId,testSuiteId,loopRequests.get(i), bindMap);
					ResponseContent responseContent = new ResponseContent();
					responseContent = doRequest.toRequest();
					doRequest.toUpdateVariables(responseContent);
					bindMap.putAll(doRequest.toBind(responseContent));
					AssertResult assertResult = doRequest.toAssert(responseContent);
					assertsDAO.AddAsserts(assertResult);
				}
			}

		}
		return doIndex;
	}

	public DoIndex doCondition(int requestSize,
			List<SuiteWithCaseController> suiteWithCaseControllerList, int controllerIndex,int requestIndex) {
		/**
		 * 初始化控制器结束后要执行的控制器位置、请求的优先级
		 */
		int nextControllerIndex = controllerIndex + 1;
		DoIndex doIndex = new DoIndex();
		doIndex.setControllerIndex(nextControllerIndex);
		doIndex.setRequestIndex(requestIndex);

		GetValue getValue = new GetValue(bindMap, null, null);
		AssertMehtod assertMehtod = new AssertMehtod();
		List<Asserts> assertlist = suiteWithCaseControllerList.get(controllerIndex).getAsserts();

		/**
		 * 执行控制器启动条件
		 */
		if (null != assertlist && assertlist.size() > 0) {
			for (Asserts assertObject : assertlist) {
				if (!assertMehtod.doAssert(getValue.getValue(assertObject.getKey()),
						getValue.getValue(assertObject.getValue()), assertObject.getMethod())) {
					doIndex.setRequestIndex(requestSize + 1);
					return doIndex;
				}
			}
		}
		return doIndex;
	}

}
