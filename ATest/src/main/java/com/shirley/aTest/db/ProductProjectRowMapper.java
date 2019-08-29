package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.ProductProject;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年8月21日 下午2:19:27
*/
public class ProductProjectRowMapper implements RowMapper<ProductProject> {

	@Override
	public ProductProject mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		// 把数据封装对象
		ProductProject productProject = new ProductProject();
		productProject.setId(id);
		productProject.setName(name);
		productProject.setDescription(description);
		return productProject;
	}

}
