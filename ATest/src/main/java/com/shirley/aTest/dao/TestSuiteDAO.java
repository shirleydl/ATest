package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.TestSuiteRowMapper;
import com.shirley.aTest.entity.TestSuite;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(测试集DAO)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:36:26
 */
@Repository("testSuiteDAO")
public class TestSuiteDAO implements ITestSuiteDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<TestSuite> QueryTestSuite(int currentPageNo, int pageSize, int id, String name, String testCaseName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlWithAll = new StringBuffer(
				"select distinct testsuite.id,testsuite.name from testsuite left join testsuite_testcase on testsuite.id=testsuite_testcase.testsuite_id left join testcase on testsuite_testcase.testcase_id=testcase.id where 1=1");
		StringBuffer sqlWithName = new StringBuffer("select id,name from testsuite where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null == testCaseName && "".equals(testCaseName))
			sql = sqlWithName;
		else
			sql = sqlWithAll;
		if (0 != id) {
			sql.append(" and testsuite.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != testCaseName && !"".equals(testCaseName)) {
			sql.append(" and testcase.name like ?");
			queryList.add("%" + testCaseName + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<TestSuite> testSuiteLists = new ArrayList<TestSuite>();
		for (Map<String, Object> row : list) {
			TestSuite testSuite = new TestSuite();
			testSuite.setId((Integer) row.get("id"));
			testSuite.setName((String) row.get("name"));
			testSuiteLists.add(testSuite);
		}
		return testSuiteLists;
	}

	@Override
	public Boolean AddTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		String sql = "insert into testsuite (name,description) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { testSuite.getName(), testSuite.getDescription() });
		return row > 0;
	}

	@Override
	public Boolean DeleteTestSuites(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from testsuite where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateTestSuite(TestSuite testSuite) {
		// TODO Auto-generated method stub
		String sql = "update testsuite set name = ?,description=? where id = ?";
		Object args[] = new Object[] { testSuite.getName(), testSuite.getDescription(), testSuite.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public int QueryTestSuiteCount(int id, String name, String testCaseName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlWithAll = new StringBuffer(
				"select count(distinct testsuite.id) from testsuite left join testsuite_testcase on testsuite.id=testsuite_testcase.testsuite_id left join testcase on testsuite_testcase.testcase_id=testcase.id where 1=1");
		StringBuffer sqlWithName = new StringBuffer("select count(id) from testsuite where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null == testCaseName && "".equals(testCaseName))
			sql = sqlWithName;
		else
			sql = sqlWithAll;
		if (0 != id) {
			sql.append(" and testsuite.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and testsuite.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != testCaseName && !"".equals(testCaseName)) {
			sql.append(" and testcase.name like ?");
			queryList.add("%" + testCaseName + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public TestSuite QueryTestSuiteById(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from testsuite where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new TestSuiteRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean AddTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		String sql = "insert into testsuite_testcase (testsuite_id,testcase_id,priority,timeout,retry,interval,delay,bindvariables) values (?,?,?,?,?,?,?,?)";
		int row = this.jdbcTemplate.update(sql,
				new Object[] { testSuiteWithCase.getTestSuiteId(), testSuiteWithCase.getInterfaceCaseId(),
						testSuiteWithCase.getPriority(), testSuiteWithCase.getTimeout(), testSuiteWithCase.getRetry(),
						testSuiteWithCase.getInterval(), testSuiteWithCase.getDelay(),
						testSuiteWithCase.getBindVariables() });
		return row > 0;
	}

}
