package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.entity.ProductProjectWithSuite;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月21日 下午5:16:39
 */
@Repository("productProjectWithSuiteDAO")
public class ProductProjectWithSuiteDAO implements IProductProjectWithSuiteDAO {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<ProductProjectWithSuite> QueryProductProjectWithSuite(int currentPageNo, int pageSize,
			int productProjectId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select product_testsuite.id,product_testsuite.testsuite_id,name from product_testsuite left join testsuite on testsuite.id=product_testsuite.testsuite_id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != productProjectId) {
			sql.append(" and product_testsuite.product_id = ?");
			queryList.add(productProjectId);
		}
		if (0 != testSuiteId) {
			sql.append(" and product_testsuite.testsuite_id = ?");
			queryList.add(testSuiteId);
		}
		if (null != testSuiteName && !"".equals(testSuiteName)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + testSuiteName + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<ProductProjectWithSuite> productProjectWithSuites = new ArrayList<ProductProjectWithSuite>();
		for (Map<String, Object> row : list) {
			ProductProjectWithSuite productProjectWithSuite = new ProductProjectWithSuite();
			productProjectWithSuite.setId((Integer) row.get("id"));
			productProjectWithSuite.setTestSuiteId((Integer) row.get("testsuite_id"));
			productProjectWithSuite.setTestSuiteName((String) row.get("name"));
			productProjectWithSuites.add(productProjectWithSuite);
		}
		return productProjectWithSuites;
	}

	@Override
	public int QueryProductProjectWithSuiteCount(int productProjectId, int testSuiteId, String testSuiteName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(product_testsuite.id) from product_testsuite left join testsuite on testsuite.id=product_testsuite.testsuite_id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != productProjectId) {
			sql.append(" and product_testsuite.product_id = ?");
			queryList.add(productProjectId);
		}
		if (0 != testSuiteId) {
			sql.append(" and product_testsuite.testsuite_id = ?");
			queryList.add(testSuiteId);
		}
		if (null != testSuiteName && !"".equals(testSuiteName)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + testSuiteName + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Boolean AddProductProjectWithSuite(ProductProjectWithSuite productProjectWithSuite) {
		// TODO Auto-generated method stub
		String sql = "insert into product_testsuite (product_id,testsuite_id) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { productProjectWithSuite.getProductProjectId(),
				productProjectWithSuite.getTestSuiteId() });
		return row > 0;
	}

	@Override
	public Boolean DeleteProductProjectWithSuite(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from product_testsuite where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public void DeleteProductProjectWithSuiteBySuiteId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from product_testsuite where testsuite_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}

	@Override
	public Boolean FindProductProjectWithSuiteBySuiteId(int productProjectId, int testSuiteId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(product_testsuite.id) from product_testsuite where product_id = ? and testsuite_id = ?");
		int row = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] { productProjectId, testSuiteId },
				Integer.class);
		return row < 1;
	}

}
