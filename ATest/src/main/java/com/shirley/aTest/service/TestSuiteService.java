package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.ProductProjectWithSuiteDAO;
import com.shirley.aTest.dao.SuiteWithCaseControllerDAO;
import com.shirley.aTest.dao.TaskWithTestSuiteDAO;
import com.shirley.aTest.dao.TestSuiteDAO;
import com.shirley.aTest.dao.TestSuiteWithCaseDAO;
import com.shirley.aTest.entity.TestSuite;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月24日 上午11:54:33
 */
@Service("testSuiteService")
public class TestSuiteService implements ITestSuiteService {
	@Resource(name = "testSuiteDAO")
	private TestSuiteDAO testSuiteDAO;
	@Resource(name = "testSuiteWithCaseDAO")
	private TestSuiteWithCaseDAO testSuiteWithCaseDAO;
	@Resource(name = "taskWithTestSuiteDAO")
	private TaskWithTestSuiteDAO taskWithTestSuiteDAO;
	@Resource(name = "productProjectWithSuiteDAO")
	private ProductProjectWithSuiteDAO productProjectWithSuiteDAO;
	@Resource(name = "suiteWithCaseControllerDAO")
	private SuiteWithCaseControllerDAO suiteWithCaseControllerDAO;

	@Override
	public List<TestSuite> QueryTestSuite(int currentPageNo, int pageSize, int id, String name, String testCaseName) {
		// TODO Auto-generated method stub
		return testSuiteDAO.QueryTestSuite(currentPageNo, pageSize, id, name, testCaseName);
	}

	@Override
	public int QueryTestSuiteCount(int id, String name, String testCaseName) {
		// TODO Auto-generated method stub
		return testSuiteDAO.QueryTestSuiteCount(id, name, testCaseName);
	}

	@Override
	public TestSuite QueryTestSuiteById(int id) {
		// TODO Auto-generated method stub
		return testSuiteDAO.QueryTestSuiteById(id);
	}

	@Override
	public Boolean AddTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		return testSuiteDAO.AddTestSuite(testSuite);
	}

	@Override
	public Boolean DeleteTestSuites(List<Integer> ids) {
		// TODO Auto-generated method stub
		if(testSuiteDAO.DeleteTestSuites(ids)){
			testSuiteWithCaseDAO.DeleteTestSuiteWithCaseBySuiteId(ids);
			taskWithTestSuiteDAO.DeleteTaskWithTestSuiteBySuiteId(ids);
			productProjectWithSuiteDAO.DeleteProductProjectWithSuiteBySuiteId(ids);
			suiteWithCaseControllerDAO.DeleteSuiteWithCaseControllersBySuiteId(ids);
			return true;
		}
		else
			return false;
	}

	@Override
	public Boolean UpdateTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		return testSuiteDAO.UpdateTestSuite(testSuite);
	}

	@Override
	public Boolean AddTestSuiteWithCases(List<TestSuiteWithCase> testSuiteWithCases) {
		// TODO Auto-generated method stub
		Boolean status = true;
		for (TestSuiteWithCase testSuiteWithCase : testSuiteWithCases) {
			if (0 != testSuiteWithCase.getInterfaceCaseId() && 0 != testSuiteWithCase.getTestSuiteId()) {
				if (!testSuiteDAO.AddTestSuiteWithCase(testSuiteWithCase)) {
					status = false;
				}
			}
		}
		return status;
	}

}
