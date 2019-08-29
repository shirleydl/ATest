package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.ProductProjectRowMapper;
import com.shirley.aTest.entity.ProductProject;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午1:57:30
*/

@Repository("productProjectDAO")
public class ProductProjectDAO implements IProductProjectDAO{
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;
	
	@Override
	public List<ProductProject> QueryProductProject(int currentPageNo, int pageSize, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select id,name,description from product where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<ProductProject> productProjects = new ArrayList<ProductProject>();
		for (Map<String, Object> row : list) {
			ProductProject productProject = new ProductProject();
			productProject.setId((Integer) row.get("id"));
			productProject.setName((String) row.get("name"));
			productProject.setDescription((String) row.get("description"));
			productProjects.add(productProject);
		}
		return productProjects;
	}

	@Override
	public int QueryProductProjectCount(String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(id) from product where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public ProductProject QueryProductProjectById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,description from product where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new ProductProjectRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean AddProductProject(ProductProject productProject) {
		// TODO Auto-generated method stub
		String sql = "insert into product (name,description) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { productProject.getName(), productProject.getDescription() });
		return row > 0;
	}

	@Override
	public Boolean DeleteProductProjects(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from product where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateProductProject(ProductProject productProject) {
		// TODO Auto-generated method stub
		String sql = "update product set name = ?,description=? where id = ?";
		Object args[] = new Object[] { productProject.getName(),  productProject.getDescription(), productProject.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
