package com.shirley.aTest.dao;

import java.util.List;
import java.util.Map;

import com.shirley.aTest.entity.CaseVariable;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(测试集-接口对象用例DAO接口)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午2:52:58
 */
public interface ITestSuiteWithCaseDAO {
	public List<TestSuiteWithCase> QueryTestCaseByTestSuiteId(int testSuiteId);

	public Map<String, String> QueryBindByTestSuiteWithCaseId(int testSuiteWithCaseId);

	public Boolean AddTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase);

	public Boolean DeleteTestSuiteWithCase(int id);

	public Boolean UpdateTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase);

	public Boolean UpdateBindVariables(int id, Map<String, String> bindVariables);

	public List<Request> QueryTestCaseByTestSuiteRequest(int testSuiteId);

	public void DeleteTestSuiteWithCaseByCaseId(List<Integer> ids);

	public void DeleteTestSuiteWithCaseBySuiteId(List<Integer> ids);

	public Boolean UpdateCaseVariables(CaseVariable CaseVariable);

	public CaseVariable QueryCaseVariablesByTestSuiteWithCaseId(int testSuiteWithCaseId);
}
