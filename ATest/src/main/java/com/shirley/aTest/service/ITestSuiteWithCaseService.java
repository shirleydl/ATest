package com.shirley.aTest.service;

import java.util.List;
import java.util.Map;

import com.shirley.aTest.entity.CaseVariable;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午5:09:57
 */
public interface ITestSuiteWithCaseService {
	public List<TestSuiteWithCase> QueryTestCaseByTestSuiteId(int testSuiteId);

	public Map<String, String> QueryBindByTestSuiteWithCaseId(int testSuiteWithCaseId);

	public Boolean AddTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase);

	public Boolean DeleteTestSuiteWithCase(int id);

	public Boolean UpdateTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase);

	public Boolean UpdateBindVariables(int id, Map<String, String> bindVariables);

	public List<Request> QueryTestCaseByTestSuiteRequest(int testSuiteId);

	public Boolean UpdateCaseVariables(CaseVariable CaseVariable);

	public CaseVariable QueryCaseVariablesByTestSuiteWithCaseId(int testSuiteWithCaseId);
}
