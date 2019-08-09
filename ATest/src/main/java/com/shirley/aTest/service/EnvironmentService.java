package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.EnvironmentDAO;
import com.shirley.aTest.entity.Environment;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年6月15日 下午8:42:54
 */
@Service("environmentService")
public class EnvironmentService implements IEnvironmentService {
	@Resource(name = "environmentDAO")
	private EnvironmentDAO environmentDAO;

	@Override
	public List<Environment> QueryEnvironment(int currentPageNo, int pageSize, String name, String url) {
		// TODO Auto-generated method stub
		return environmentDAO.QueryEnvironment(currentPageNo, pageSize, name, url);
	}

	@Override
	public int QueryEnvironmentCount(String name, String url) {
		// TODO Auto-generated method stub
		return environmentDAO.QueryEnvironmentCount(name, url);
	}

	@Override
	public Boolean AddEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		return environmentDAO.AddEnvironment(environment);
	}

	@Override
	public Boolean DeleteEnvironments(List<Integer> ids) {
		// TODO Auto-generated method stub
		return environmentDAO.DeleteEnvironments(ids);
	}

	@Override
	public Boolean UpdateEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		return environmentDAO.UpdateEnvironment(environment);
	}

}
