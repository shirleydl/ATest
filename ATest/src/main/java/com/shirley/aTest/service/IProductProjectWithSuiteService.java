package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.ProductProjectWithSuite;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午5:25:10
*/
public interface IProductProjectWithSuiteService {
	public List<ProductProjectWithSuite> QueryProductProjectWithSuite(int currentPageNo, int pageSize, int productProjectId,int testSuiteId, String testSuiteName);

	public Boolean AddProductProjectWithSuite(ProductProjectWithSuite productProjectWithSuite);

	public Boolean DeleteProductProjectWithSuite(List<Integer> ids);
	
	public int QueryProductProjectWithSuiteCount(int productProjectId, int testSuiteId, String testSuiteName);

}
