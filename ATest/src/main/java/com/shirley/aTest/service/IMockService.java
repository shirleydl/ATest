package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.Mock;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月4日 下午2:26:55
*/
public interface IMockService {
	public List<Mock> QueryMocks(int currentPageNo, int pageSize, int id, String name);

	public int QueryMockCount(int id, String name);

	public Mock QueryMockById(int id);

	public Boolean AddMock(Mock mock);

	public Boolean DeleteMocks(List<Integer> ids);

	public Boolean UpdateMock(Mock mock);
}
