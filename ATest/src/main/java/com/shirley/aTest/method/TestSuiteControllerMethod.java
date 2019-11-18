package com.shirley.aTest.method;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shirley.aTest.entity.AssertResult;
import com.shirley.aTest.entity.Asserts;
import com.shirley.aTest.entity.DoIndex;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.ResponseContent;
import com.shirley.aTest.entity.SuiteWithCaseController;
import com.shirley.aTest.util.AssertMehtod;
import com.shirley.aTest.util.GetValue;
import com.shirley.aTest.util.ListCopy;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月11日 上午10:43:00
 */
public class TestSuiteControllerMethod {
	private Map<String, String> bindMap;
	private int taskId;

	public TestSuiteControllerMethod(int taskId, Map<String, String> bindMap) {
		this.taskId = taskId;
		this.bindMap = bindMap;
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
			return doCondition(newRequests.size(), suiteWithCaseControllerList,controllerIndex,requestIndex);
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
		List<AssertResult> assertResultList = new ArrayList<AssertResult>();
		DoIndex doIndex = new DoIndex();
		doIndex.setControllerIndex(nextControllerIndex);
		doIndex.setRequestIndex(newRequests.size());

		GetValue getValue = new GetValue(bindMap, null, null);
		AssertMehtod assertMehtod = new AssertMehtod();
		Gson gson = new Gson();
		List<Asserts> assertlist = (List<Asserts>) (gson.fromJson(
				suiteWithCaseControllerList.get(controllerIndex).getAsserts(), new TypeToken<List<Asserts>>() {
				}.getType()));
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
					 * 开始请求、断言
					 */
					DoRequest doRequest = new DoRequest(taskId, loopRequests.get(i), bindMap);
					ResponseContent responseContent = new ResponseContent();
					responseContent = doRequest.toRequest();
					doRequest.toUpdateVariables(responseContent);
					bindMap.putAll(doRequest.toBind(responseContent));
					AssertResult assertResult = doRequest.toAssert(responseContent);
					assertResultList.add(assertResult);
				}
			}

		}
		doIndex.setAssertResultList(assertResultList);
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
		Gson gson = new Gson();
		List<Asserts> assertlist = (List<Asserts>) (gson.fromJson(
				suiteWithCaseControllerList.get(controllerIndex).getAsserts(), new TypeToken<List<Asserts>>() {
				}.getType()));
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
