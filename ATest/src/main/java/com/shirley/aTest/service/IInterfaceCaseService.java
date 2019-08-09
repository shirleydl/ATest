package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.InterfaceCase;
import com.shirley.aTest.entity.Request;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月25日 上午11:31:20
 */
public interface IInterfaceCaseService {
	public List<InterfaceCase> QueryTestCase(int currentPageNo, int pageSize, int id, String name, String interfaceName,
			String interfaceApi);

	public List<InterfaceCase> QueryTestCaseByTestSuiteId(int testSuiteId);

	public int QueryTestCaseCount(int id, String name, String interfaceName, String interfaceApi);

	public InterfaceCase QueryTestCaseById(int id);

	public Boolean AddTestCase(InterfaceCase testCase);

	public Boolean DeleteTestCases(List<Integer> ids);

	public Boolean UpdateTestCase(InterfaceCase testCase);

	public Request QueryRequestByTestCaseId(int testSuiteId);
}
