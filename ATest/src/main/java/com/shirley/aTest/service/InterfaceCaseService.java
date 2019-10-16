package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.TestCaseDAO;
import com.shirley.aTest.dao.TestSuiteWithCaseDAO;
import com.shirley.aTest.entity.InterfaceCase;
import com.shirley.aTest.entity.Request;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月25日 上午11:32:31
 */
@Service("interfaceCaseService")
public class InterfaceCaseService implements IInterfaceCaseService {
	@Resource(name = "testCaseDAO")
	private TestCaseDAO testCaseDAO;
	@Resource(name = "testSuiteWithCaseDAO")
	private TestSuiteWithCaseDAO testSuiteWithCaseDAO;

	@Override
	public List<InterfaceCase> QueryTestCase(int currentPageNo, int pageSize, int id, String name, String interfaceName,
			String interfaceApi) {
		// TODO Auto-generated method stub
		return testCaseDAO.QueryTestCase(currentPageNo, pageSize, id, name, interfaceName, interfaceApi);
	}

	@Override
	public List<InterfaceCase> QueryTestCaseByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		return testCaseDAO.QueryTestCaseByTestSuiteId(testSuiteId);
	}

	@Override
	public int QueryTestCaseCount(int id, String name, String interfaceName, String interfaceApi) {
		// TODO Auto-generated method stub
		return testCaseDAO.QueryTestCaseCount(id, name, interfaceName, interfaceApi);
	}

	@Override
	public InterfaceCase QueryTestCaseById(int id) {
		// TODO Auto-generated method stub
		return testCaseDAO.QueryTestCaseById(id);
	}

	@Override
	public Boolean AddTestCase(InterfaceCase testCase) {
		// TODO Auto-generated method stub
		return testCaseDAO.AddTestCase(testCase);
	}

	@Override
	public Boolean DeleteTestCases(List<Integer> ids) {
		// TODO Auto-generated method stub
		if (testCaseDAO.DeleteTestCases(ids)) {
			testSuiteWithCaseDAO.DeleteTestSuiteWithCaseByCaseId(ids);
			return true;
		}
		return false;

	}

	@Override
	public Boolean UpdateTestCase(InterfaceCase testCase) {
		// TODO Auto-generated method stub
		return testCaseDAO.UpdateTestCase(testCase);
	}

	@Override
	public Request QueryRequestByTestCaseId(int testSuiteId) {
		// TODO Auto-generated method stub
		return testCaseDAO.QueryRequestByTestCaseId(testSuiteId);
	}

}
