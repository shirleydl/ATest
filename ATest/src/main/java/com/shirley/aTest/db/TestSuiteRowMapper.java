package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.TestSuite;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午5:34:09
 */
public class TestSuiteRowMapper implements RowMapper<TestSuite> {

	@Override
	public TestSuite mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		// 获取结果集中的数据
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		// 把数据封装对象
		TestSuite testSuite = new TestSuite();
		testSuite.setId(id);
		testSuite.setName(name);
		testSuite.setDescription(description);
		return testSuite;
	}
}
