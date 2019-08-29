package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.ProductProject;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午1:57:55
*/
public interface IProductProjectDAO {
	public List<ProductProject> QueryProductProject(int currentPageNo, int pageSize, String name);

	public int QueryProductProjectCount(String name);

	public ProductProject QueryProductProjectById(int id);

	public Boolean AddProductProject(ProductProject productProject);

	public Boolean DeleteProductProjects(List<Integer> ids);

	public Boolean UpdateProductProject(ProductProject productProject);

}
