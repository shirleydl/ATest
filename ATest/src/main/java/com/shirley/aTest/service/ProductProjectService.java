package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.ProductProjectDAO;
import com.shirley.aTest.entity.ProductProject;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午2:26:23
*/

@Service("productProjectService")
public class ProductProjectService implements IProductProjectService {
	@Resource(name = "productProjectDAO")
	private ProductProjectDAO productProjectDAO;

	
	@Override
	public List<ProductProject> QueryProductProject(int currentPageNo, int pageSize, String name) {
		// TODO Auto-generated method stub
		return productProjectDAO.QueryProductProject(currentPageNo, pageSize, name);
	}

	@Override
	public int QueryProductProjectCount(String name) {
		// TODO Auto-generated method stub
		return productProjectDAO.QueryProductProjectCount(name);
	}

	@Override
	public ProductProject QueryProductProjectById(int id) {
		// TODO Auto-generated method stub
		return productProjectDAO.QueryProductProjectById(id);
	}

	@Override
	public Boolean AddProductProject(ProductProject productProject) {
		// TODO Auto-generated method stub
		return productProjectDAO.AddProductProject(productProject);
	}

	@Override
	public Boolean DeleteProductProjects(List<Integer> ids) {
		// TODO Auto-generated method stub
		return productProjectDAO.DeleteProductProjects(ids);
	}

	@Override
	public Boolean UpdateProductProject(ProductProject productProject) {
		// TODO Auto-generated method stub
		return productProjectDAO.UpdateProductProject(productProject);
	}

}
