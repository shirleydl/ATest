package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.AssertResult;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月11日 上午10:40:33
 */
public interface IAssertResultService {
	public List<AssertResult> QueryAsserts(int currentPageNo, int pageSize, int taskId);

	public int QueryAssertsCount(int taskId);

	public AssertResult QueryAssert(int assertId);

	public Boolean DeleteAssertsByTaskId(List<Integer> ids);
}
