package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.InterfaceCase;
import com.shirley.aTest.entity.Request;

/**
 * @Description: TODO(接口对象测试用例DAO接口)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:15:38
 */
public interface ITestCaseDAO {
	public List<InterfaceCase> QueryTestCase(int currentPageNo, int pageSize, int id, String name, String interfaceName,
			String interfaceApi);

	public List<InterfaceCase> QueryTestCaseByTestSuiteId(int testSuiteId);

	public int QueryTestCaseCount(int id, String name, String interfaceName, String interfaceApi);

	public InterfaceCase QueryTestCaseById(int id);

	public Boolean AddTestCase(InterfaceCase testCase);

	public Boolean DeleteTestCases(List<Integer> ids);

	public Boolean UpdateTestCase(InterfaceCase testCase);

	public Request QueryRequestByTestCaseId(int id);

	public Boolean FindCaseInterface(List<Integer> ids);

}
