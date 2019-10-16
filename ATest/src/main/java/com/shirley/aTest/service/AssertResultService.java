package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.AssertResultDAO;
import com.shirley.aTest.entity.AssertResult;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月11日 上午10:41:12
 */
@Service("assertResultService")
public class AssertResultService implements IAssertResultService {
	@Resource(name = "assertResultDAO")
	private AssertResultDAO assertResultDAO;

	@Override
	public List<AssertResult> QueryAsserts(int currentPageNo, int pageSize, int taskId) {
		// TODO Auto-generated method stub
		return assertResultDAO.QueryAsserts(currentPageNo, pageSize, taskId);
	}

	@Override
	public int QueryAssertsCount(int taskId) {
		// TODO Auto-generated method stub
		return assertResultDAO.QueryAssertsCount(taskId);
	}

	@Override
	public AssertResult QueryAssert(int assertId) {
		// TODO Auto-generated method stub
		return assertResultDAO.QueryAssert(assertId);
	}

	@Override
	public Boolean DeleteAssertsByTaskId(List<Integer> ids) {
		// TODO Auto-generated method stub
		assertResultDAO.DeleteAssertsByTaskId(ids);
		return true;
	}

}
