package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.ProductProjectWithSuiteDAO;
import com.shirley.aTest.entity.ProductProjectWithSuite;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午5:27:53
*/
@Service("productProjectWithSuiteService")
public class ProductProjectWithSuiteService implements IProductProjectWithSuiteService {
	@Resource(name = "productProjectWithSuiteDAO")
	private ProductProjectWithSuiteDAO productProjectWithSuiteDAO;
	
	@Override
	public List<ProductProjectWithSuite> QueryProductProjectWithSuite(int currentPageNo, int pageSize, int productProjectId,int testSuiteId, String testSuiteName){
		// TODO Auto-generated method stub
		return productProjectWithSuiteDAO.QueryProductProjectWithSuite(currentPageNo, pageSize, productProjectId, testSuiteId, testSuiteName);
	}

	@Override
	public Boolean AddProductProjectWithSuite(ProductProjectWithSuite productProjectWithSuite) {
		// TODO Auto-generated method stub
		if(productProjectWithSuiteDAO.FindProductProjectWithSuiteBySuiteId(productProjectWithSuite.getProductProjectId(), productProjectWithSuite.getTestSuiteId()))
			return productProjectWithSuiteDAO.AddProductProjectWithSuite(productProjectWithSuite);
		return false;
	}

	@Override
	public Boolean DeleteProductProjectWithSuite(List<Integer> ids) {
		// TODO Auto-generated method stub
		return productProjectWithSuiteDAO.DeleteProductProjectWithSuite(ids);
	}

	@Override
	public int QueryProductProjectWithSuiteCount(int productProjectId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		return productProjectWithSuiteDAO.QueryProductProjectWithSuiteCount(productProjectId, testSuiteId, testSuiteName);
	}

}
