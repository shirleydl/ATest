package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.ProductProjectWithSuite;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月21日 下午5:00:27
 */
public interface IProductProjectWithSuiteDAO {
	public List<ProductProjectWithSuite> QueryProductProjectWithSuite(int currentPageNo, int pageSize,
			int productProjectId, int testSuiteId, String testSuiteName);

	public Boolean AddProductProjectWithSuite(ProductProjectWithSuite productProjectWithSuite);

	public Boolean DeleteProductProjectWithSuite(List<Integer> ids);

	public void DeleteProductProjectWithSuiteBySuiteId(List<Integer> ids);

	public int QueryProductProjectWithSuiteCount(int productProjectId, int testSuiteId, String testSuiteName);

	public Boolean FindProductProjectWithSuiteBySuiteId(int productProjectId, int testSuiteId);

}
