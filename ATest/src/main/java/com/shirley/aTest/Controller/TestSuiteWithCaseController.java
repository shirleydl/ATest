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
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.ResponseContent;
import com.shirley.aTest.entity.TestSuiteWithCase;
import com.shirley.aTest.method.DoRequest;
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
			Integer timeout, Integer retry, Integer interval, Integer delay) {
		if (null != testSuiteId && null != interfaceCaseId) {
			TestSuiteWithCase testSuiteWithCase = new TestSuiteWithCase();
			testSuiteWithCase.setTestSuiteId(testSuiteId);
			testSuiteWithCase.setInterfaceCaseId(interfaceCaseId);
			testSuiteWithCase.setPriority(null == priority ? 0 : priority);
			testSuiteWithCase.setTimeout(null == timeout ? 0 : timeout);
			testSuiteWithCase.setRetry(null == retry ? 0 : retry);
			testSuiteWithCase.setInterval(null == interval ? 0 : interval);
			testSuiteWithCase.setDelay(null == delay ? 0 : delay);
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
			Integer timeout, Integer retry, Integer interval, Integer delay) {
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

	@RequestMapping(value = "/toRequestTestSuite", method = RequestMethod.GET)
	@ResponseBody
	public List<AssertResult> toRequestTestSuite(Integer testSuiteId) {
		if (null != testSuiteId) {
			List<AssertResult> assertResultList = new ArrayList<AssertResult>();
			Map<String, String> bindMap = new HashMap<String, String>();
			List<Request> requestList = testSuiteWithCaseService.QueryTestCaseByTestSuiteRequest(testSuiteId);
			Collections.sort(requestList);
			for (Request request : requestList) {
				DoRequest doRequest = new DoRequest(0, request, bindMap);
				ResponseContent responseContent = new ResponseContent();
				responseContent = doRequest.toRequest();
				doRequest.toUpdateVariables(responseContent);
				bindMap.putAll(doRequest.toBind(responseContent));
				AssertResult assertResult = doRequest.toAssert(responseContent);
				assertResultList.add(assertResult);
			}
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
			for (TestSuiteWithCase testSuiteWithCase : testSuiteWithCases) {
				testSuiteWithCase.setTestSuiteId(toSuiteId);
				testSuiteWithCaseService.AddTestSuiteWithCase(testSuiteWithCase);
			}
			return true;
		}
		return false;
	}
}
