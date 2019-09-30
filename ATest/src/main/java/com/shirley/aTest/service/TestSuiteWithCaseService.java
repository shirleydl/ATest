package com.shirley.aTest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.TestSuiteWithCaseDAO;
import com.shirley.aTest.entity.CaseVariable;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午5:31:52
 */
@Service("testSuiteWithCaseService")
public class TestSuiteWithCaseService implements ITestSuiteWithCaseService {
	@Resource(name = "testSuiteWithCaseDAO")
	private TestSuiteWithCaseDAO testSuiteWithCaseDAO;

	@Override
	public List<TestSuiteWithCase> QueryTestCaseByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.QueryTestCaseByTestSuiteId(testSuiteId);
	}

	@Override
	public Boolean AddTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.AddTestSuiteWithCase(testSuiteWithCase);
	}

	@Override
	public Boolean DeleteTestSuiteWithCase(int id) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.DeleteTestSuiteWithCase(id);
	}

	@Override
	public Boolean UpdateTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.UpdateTestSuiteWithCase(testSuiteWithCase);
	}

	@Override
	public Boolean UpdateBindVariables(int id, Map<String, String> bindVariables) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.UpdateBindVariables(id, bindVariables);
	}

	@Override
	public Map<String, String> QueryBindByTestSuiteWithCaseId(int testSuiteWithCaseId) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.QueryBindByTestSuiteWithCaseId(testSuiteWithCaseId);
	}

	@Override
	public List<Request> QueryTestCaseByTestSuiteRequest(int testSuiteId) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.QueryTestCaseByTestSuiteRequest(testSuiteId);
	}

	@Override
	public Boolean UpdateCaseVariables(CaseVariable CaseVariable) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.UpdateCaseVariables(CaseVariable);
	}

	@Override
	public CaseVariable QueryCaseVariablesByTestSuiteWithCaseId(int testSuiteWithCaseId) {
		// TODO Auto-generated method stub
		return testSuiteWithCaseDAO.QueryCaseVariablesByTestSuiteWithCaseId(testSuiteWithCaseId);
	}

}
