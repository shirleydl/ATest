package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.MockDAO;
import com.shirley.aTest.entity.Mock;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月4日 下午2:27:23
*/

@Service("mockService")
public class MockService implements IMockService {
	@Resource(name = "mockDAO")
	private MockDAO mockDAO;
	
	@Override
	public List<Mock> QueryMocks(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		return mockDAO.QueryMocks(currentPageNo, pageSize, id, name);
	}

	@Override
	public int QueryMockCount(int id, String name) {
		// TODO Auto-generated method stub
		return mockDAO.QueryMockCount(id, name);
	}

	@Override
	public Mock QueryMockById(int id) {
		// TODO Auto-generated method stub
		return mockDAO.QueryMockById(id);
	}

	@Override
	public Boolean AddMock(Mock mock) {
		// TODO Auto-generated method stub
		return mockDAO.AddMock(mock);
	}

	@Override
	public Boolean DeleteMocks(List<Integer> ids) {
		// TODO Auto-generated method stub
		return mockDAO.DeleteMocks(ids);
	}

	@Override
	public Boolean UpdateMock(Mock mock) {
		// TODO Auto-generated method stub
		return mockDAO.UpdateMock(mock);
	}

}
