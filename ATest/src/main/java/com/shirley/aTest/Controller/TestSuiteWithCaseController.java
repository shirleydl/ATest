package com.shirley.aTest.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shirley.aTest.entity.AssertResult;
import com.shirley.aTest.entity.CaseVariable;
import com.shirley.aTest.entity.DoIndex;
import com.shirley.aTest.entity.Mock;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.ResponseContent;
import com.shirley.aTest.entity.SuiteWithCaseController;
import com.shirley.aTest.entity.TestSuiteWithCase;
import com.shirley.aTest.method.DoRequest;
import com.shirley.aTest.method.TestSuiteControllerMethod;
import com.shirley.aTest.service.MockService;
import com.shirley.aTest.service.SuiteWithCaseControllerService;
import com.shirley.aTest.service.TestSuiteWithCaseService;

/**
 * @Description: TODO(测试集管理用例控制类)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午5:37:43
 */
@Controller
public class TestSuiteWithCaseController {
	@Resource(name = "testSuiteWithCaseService")
	private TestSuiteWithCaseService testSuiteWithCaseService;
	@Resource(name = "mockService")
	private MockService mockService;
	@Resource(name = "suiteWithCaseControllerService")
	private SuiteWithCaseControllerService suiteWithCaseControllerService;

	@RequestMapping(value = "/queryTestSuiteWithCase", method = RequestMethod.GET)
	public String queryTestSuiteWithCase() {
		return "testSuiteWithCaseList";
	}

	@RequestMapping(value = "/queryBinds", method = RequestMethod.GET)
	public String queryBinds() {
		return "bindVariables";
	}

	@RequestMapping(value = "/testSuiteResult", method = RequestMethod.GET)
	public String testSuiteResult() {
		return "testSuiteResult";
	}

	@RequestMapping(value = "/copySuiteWithCaseList", method = RequestMethod.GET)
	public String copySuiteWithCaseList() {
		return "copySuiteWithCaseList";
	}

	@RequestMapping(value = "/queryCaseVariables", method = RequestMethod.GET)
	public String queryCaseVariables() {
		return "caseVariables";
	}

	@RequestMapping(value = "/toQueryTestSuiteWithCases", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toQueryTestSuiteWithCases(Integer testSuiteId) {
		if (null != testSuiteId) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<TestSuiteWithCase> testSuiteWithCases = testSuiteWithCaseService
					.QueryTestCaseByTestSuiteId(testSuiteId);
			Collections.sort(testSuiteWithCases);
			map.put("testSuiteWithCases", testSuiteWithCases);
			return map;
		}
		return null;
	}

	@RequestMapping(value = "/toAddTestSuiteWithCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTestSuiteWithCase(Integer testSuiteId, Integer interfaceCaseId, Integer priority,
			Integer timeout, Integer retry, Integer interval, Integer delay, Integer redirect) {
		if (null != testSuiteId && null != interfaceCaseId) {
			TestSuiteWithCase testSuiteWithCase = new TestSuiteWithCase();
			testSuiteWithCase.setTestSuiteId(testSuiteId);
			testSuiteWithCase.setInterfaceCaseId(interfaceCaseId);
			testSuiteWithCase.setPriority(null == priority ? 0 : priority);
			testSuiteWithCase.setTimeout(null == timeout ? 0 : timeout);
			testSuiteWithCase.setRetry(null == retry ? 0 : retry);
			testSuiteWithCase.setInterval(null == interval ? 0 : interval);
			testSuiteWithCase.setDelay(null == delay ? 0 : delay);
			testSuiteWithCase.setRedirect(null == redirect ? 0 : redirect);
			return testSuiteWithCaseService.AddTestSuiteWithCase(testSuiteWithCase);
		}
		return false;
	}

	@RequestMapping(value = "/toDelTestSuiteWithCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelTestSuiteWithCase(Integer id) {
		if (null != id)
			return testSuiteWithCaseService.DeleteTestSuiteWithCase(id);
		return false;
	}

	@RequestMapping(value = "/toUpdateTestSuiteWithCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateTestSuiteWithCase(Integer id, Integer testSuiteId, Integer interfaceCaseId, Integer priority,
			Integer timeout, Integer retry, Integer interval, Integer delay, Integer redirect) {
		if (null != id && null != testSuiteId && null != interfaceCaseId) {
			TestSuiteWithCase testSuiteWithCase = new TestSuiteWithCase();
			testSuiteWithCase.setId(id);
			testSuiteWithCase.setTestSuiteId(testSuiteId);
			testSuiteWithCase.setInterfaceCaseId(interfaceCaseId);
			testSuiteWithCase.setPriority(null == priority ? 0 : priority);
			testSuiteWithCase.setTimeout(null == timeout ? 0 : timeout);
			testSuiteWithCase.setRetry(null == retry ? 0 : retry);
			testSuiteWithCase.setInterval(null == interval ? 0 : interval);
			testSuiteWithCase.setDelay(null == delay ? 0 : delay);
			testSuiteWithCase.setRedirect(null == redirect ? 0 : redirect);
			return testSuiteWithCaseService.UpdateTestSuiteWithCase(testSuiteWithCase);
		}
		return false;
	}

	@RequestMapping(value = "/toQueryBinds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> toQueryBinds(Integer testSuiteWithCaseId) {
		if (null != testSuiteWithCaseId) {
			Map<String, String> map = new HashMap<String, String>();
			map = testSuiteWithCaseService.QueryBindByTestSuiteWithCaseId(testSuiteWithCaseId);
			return map;
		}
		return null;
	}

	@RequestMapping(value = "/toUpdateBindVariables", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateBindVariables(@RequestParam Map<String, String> bind) {
		Map<String, String> bindVariables = new HashMap<String, String>();
		Gson gson = new Gson();
		bindVariables = gson.fromJson(bind.get("bindVariables"), bindVariables.getClass());
		Integer id = Integer.parseInt(bind.get("testSuiteWithCaseId"));
		return testSuiteWithCaseService.UpdateBindVariables(id,
				null == bindVariables || 0 == bindVariables.size() ? null : bindVariables);

	}

	@RequestMapping(value = "/toQueryCaseVariables", method = RequestMethod.POST)
	@ResponseBody
	public CaseVariable toQueryCaseVariables(Integer testSuiteWithCaseId) {
		if (null != testSuiteWithCaseId) {
			return testSuiteWithCaseService.QueryCaseVariablesByTestSuiteWithCaseId(testSuiteWithCaseId);
		}
		return null;
	}

	@RequestMapping(value = "/toUpdateCaseVariables", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateCaseVariables(@RequestParam Map<String, String> caseVariablesMap) {
		if (null != caseVariablesMap && null != caseVariablesMap.get("testSuiteWithCaseId")) {
			Map<String, String> variables = new HashMap<String, String>();
			Gson gson = new Gson();
			CaseVariable caseVariable = new CaseVariable();
			caseVariable.setTestSuiteWithCaseId(Integer.parseInt(caseVariablesMap.get("testSuiteWithCaseId")));
			caseVariable.setCaseVariablesSplit(caseVariablesMap.get("caseVariablesSplit"));
			caseVariable.setCaseVariables(gson.fromJson(caseVariablesMap.get("caseVariables"), variables.getClass()));
			return testSuiteWithCaseService.UpdateCaseVariables(caseVariable);
		}
		return false;

	}

	@RequestMapping(value = "/toRequestTestSuite", method = RequestMethod.GET)
	@ResponseBody
	public List<AssertResult> toRequestTestSuite(Integer testSuiteId, Integer mockId) {
		if (null != testSuiteId) {
			List<AssertResult> assertResultList = new ArrayList<AssertResult>();
			Map<String, String> bindMap = new HashMap<String, String>();
			if (null != mockId) {
				Mock mock = mockService.QueryMockById(mockId);
				if (null != mock) {
					bindMap.putAll(mock.getBindVariableMocks());
				}
			}
			List<Request> requestList = testSuiteWithCaseService.QueryTestCaseByTestSuiteRequest(testSuiteId);
			Collections.sort(requestList);
			List<SuiteWithCaseController> suiteWithCaseControllerList = suiteWithCaseControllerService
					.QueryControllerByTestSuiteId(testSuiteId);
			Collections.sort(suiteWithCaseControllerList);
			/**
			 * 初始化要执行的控制器位置
			 */
			DoIndex doIndex = new DoIndex();
			doIndex.setControllerIndex(0);

			for (int i = 0; i < requestList.size(); i++) {
				TestSuiteControllerMethod testSuiteControllerMethod = new TestSuiteControllerMethod(0, bindMap);
				doIndex.setRequestIndex(i);// 初始请求开始位置
				/**
				 * 判断当前请求是否需进入控制器
				 */
				for (int j = doIndex.getControllerIndex(); j < suiteWithCaseControllerList.size(); j++) {
					if (suiteWithCaseControllerList.get(j).getStartPriority() == requestList.get(i).getPriority()) {
						doIndex = testSuiteControllerMethod.doController(requestList, suiteWithCaseControllerList, j,i);
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
					 * 开始请求、断言
					 */
					DoRequest doRequest = new DoRequest(0, requestList.get(i), bindMap);
					ResponseContent responseContent = new ResponseContent();
					responseContent = doRequest.toRequest();
					doRequest.toUpdateVariables(responseContent);
					bindMap.putAll(doRequest.toBind(responseContent));
					AssertResult assertResult = doRequest.toAssert(responseContent);
					assertResultList.add(assertResult);
				}
			}
			if(null!=doIndex.getAssertResultList())
				assertResultList.addAll(doIndex.getAssertResultList());
			return assertResultList;
		}
		return null;
	}

	@RequestMapping(value = "/toCopySuiteWithCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toCopySuiteWithCase(Integer fromSuiteId, Integer toSuiteId) {
		if (null != fromSuiteId && null != toSuiteId) {
			List<TestSuiteWithCase> testSuiteWithCases = testSuiteWithCaseService
					.QueryTestCaseByTestSuiteId(fromSuiteId);
			List<SuiteWithCaseController> suiteWithCaseControllerList = suiteWithCaseControllerService
					.QueryControllerByTestSuiteId(fromSuiteId);
			for (TestSuiteWithCase testSuiteWithCase : testSuiteWithCases) {
				testSuiteWithCase.setTestSuiteId(toSuiteId);
				testSuiteWithCaseService.AddTestSuiteWithCase(testSuiteWithCase);
			}
			for (SuiteWithCaseController suiteWithCaseController : suiteWithCaseControllerList) {
				suiteWithCaseController.setTestSuiteId(toSuiteId);
				suiteWithCaseControllerService.AddSuiteWithCaseController(suiteWithCaseController);
			}
			return true;
		}
		return false;
	}
}
