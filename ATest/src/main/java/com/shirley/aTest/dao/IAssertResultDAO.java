package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.AssertResult;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月8日 下午2:47:42
 */
public interface IAssertResultDAO {
	public List<AssertResult> QueryAsserts(int currentPageNo, int pageSize, int taskId);

	public int QueryAssertsCount(int taskId);

	public AssertResult QueryAssert(int assertId);

	public Boolean DeleteAssertsByTaskId(List<Integer> ids);

}
