package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.SuiteWithCaseControllerDAO;
import com.shirley.aTest.entity.SuiteWithCaseController;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月10日 上午10:23:04
*/

@Service("suiteWithCaseControllerService")
public class SuiteWithCaseControllerService implements ISuiteWithCaseControllerService {
	@Resource(name = "suiteWithCaseControllerDAO")
	private SuiteWithCaseControllerDAO suiteWithCaseControllerDAO;
	
	@Override
	public List<SuiteWithCaseController> QueryControllerByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.QueryControllerByTestSuiteId(testSuiteId);
	}

	@Override
	public Boolean AddSuiteWithCaseController(SuiteWithCaseController suiteWithCaseController) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.AddSuiteWithCaseController(suiteWithCaseController);
	}

	@Override
	public Boolean DeleteSuiteWithCaseController(int id) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.DeleteSuiteWithCaseController(id);
	}

	@Override
	public Boolean UpdateSuiteWithCaseController(SuiteWithCaseController suiteWithCaseController) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.UpdateSuiteWithCaseController(suiteWithCaseController);
	}

	@Override
	public String QueryAssertsById(int id) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.QueryAssertsById(id);
	}

	@Override
	public Boolean UpdateAsserts(int id, String asserts) {
		// TODO Auto-generated method stub
		return suiteWithCaseControllerDAO.UpdateAsserts(id, asserts);
	}

}
