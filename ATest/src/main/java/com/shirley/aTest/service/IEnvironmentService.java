package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.Environment;

/**
 * @Description: TODO()
 * @author 371683941@qq.com
 * @date 2019年6月15日 下午8:43:20
 */
public interface IEnvironmentService {
	public List<Environment> QueryEnvironment(int currentPageNo, int pageSize, String name, String url);

	public int QueryEnvironmentCount(String name, String url);

	public Boolean AddEnvironment(Environment environment);

	public Boolean DeleteEnvironments(List<Integer> ids);

	public Boolean UpdateEnvironment(Environment environment);
	
	public Environment QueryEnvironmentById(int id);
}
