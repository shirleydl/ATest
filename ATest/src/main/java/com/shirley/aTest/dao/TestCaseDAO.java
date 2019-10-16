package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.shirley.aTest.db.InterfaceCaseRowMapper;
import com.shirley.aTest.db.RequestRowMapper;
import com.shirley.aTest.entity.InterfaceCase;
import com.shirley.aTest.entity.Request;

/**
 * @Description: TODO(接口对象测试用例DAO)
 * @author 371683941@qq.com
 * @date 2019年6月25日 上午9:40:26
 */
@Repository("testCaseDAO")
public class TestCaseDAO implements ITestCaseDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<InterfaceCase> QueryTestCase(int currentPageNo, int pageSize, int id, String name, String interfaceName,
			String interfaceApi) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select testcase.id,testcase.name,interface.name as interfacename,interface.api,environment.url from testcase left join interface on testcase.interface_id=interface.id left join environment on interface.environment_id=environment.id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and testcase.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and testcase.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != interfaceName && !"".equals(interfaceName)) {
			sql.append(" and interface.name like ?");
			queryList.add("%" + interfaceName + "%");
		}
		if (null != interfaceApi && !"".equals(interfaceApi)) {
			sql.append(" and interface.api like ?");
			queryList.add("%" + interfaceApi + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<InterfaceCase> testCaseLists = new ArrayList<InterfaceCase>();
		for (Map<String, Object> row : list) {
			InterfaceCase testCase = new InterfaceCase();
			testCase.setId((Integer) row.get("id"));
			testCase.setName((String) row.get("name"));
			testCase.setInterfaceName((String) row.get("interfacename"));
			testCase.setInterfaceApi((String) row.get("url")+(String) row.get("api"));
			testCaseLists.add(testCase);
		}
		return testCaseLists;
	}

	@Override
	public List<InterfaceCase> QueryTestCaseByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select testcase.id,testcase.name from testcase left join testsuite_testcase on testcase.id=testsuite_testcase.testcase_id where testsuite_testcase.testsuite_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		List<InterfaceCase> testCaseLists = new ArrayList<InterfaceCase>();
		for (Map<String, Object> row : list) {
			InterfaceCase testCase = new InterfaceCase();
			testCase.setId((Integer) row.get("testcase.id"));
			testCase.setName((String) row.get("testcase.name"));
			testCaseLists.add(testCase);
		}
		return testCaseLists;
	}

	@Override
	public int QueryTestCaseCount(int id, String name, String interfaceName, String interfaceApi) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(testcase.id) from testcase left join interface on testcase.interface_id=interface.id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and testcase.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and testcase.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != interfaceName && !"".equals(interfaceName)) {
			sql.append(" and interface.name like ?");
			queryList.add("%" + interfaceName + "%");
		}
		if (null != interfaceApi && !"".equals(interfaceApi)) {
			sql.append(" and interface.api like ?");
			queryList.add("%" + interfaceApi + "%");
		}

		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public InterfaceCase QueryTestCaseById(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from testcase where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new InterfaceCaseRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean AddTestCase(InterfaceCase testCase) {
		// TODO Auto-generated method stub
		String sql = "insert into testcase (name,interface_id,description,method,headers,params,asserts,variables) values (?,?,?,?,?,?,?,?)";
		Gson gson = new Gson();
		int row = this.jdbcTemplate.update(sql,
				new Object[] { testCase.getName(), testCase.getInterfaceId(), testCase.getDescription(),
						testCase.getMethod(), gson.toJson(testCase.getHeaders()), testCase.getParams(),
						testCase.getAsserts(), gson.toJson(testCase.getVariables()) });
		return row > 0;
	}

	@Override
	public Boolean DeleteTestCases(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from testcase where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}
	
	@Override
	public Boolean FindCaseInterface(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from testcase where interface_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.queryForObject(sql, paramMap, Integer.class);
		return row < 1;
	}

	@Override
	public Boolean UpdateTestCase(InterfaceCase testCase) {
		// TODO Auto-generated method stub
		String sql = "update testcase set name = ?,interface_id=?,description=?,method=?,headers=?,params=?,asserts=?,variables=? where id = ?";
		Gson gson = new Gson();
		Object args[] = new Object[] { testCase.getName(), testCase.getInterfaceId(), testCase.getDescription(),
				testCase.getMethod(), gson.toJson(testCase.getHeaders()), testCase.getParams(), testCase.getAsserts(),
				gson.toJson(testCase.getVariables()), testCase.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Request QueryRequestByTestCaseId(int id) {
		// TODO Auto-generated method stub
		String sql = "select environment.url,interface.api,testcase.method,testcase.headers,testcase.params,testcase.asserts,testcase.variables from testcase left join interface on testcase.interface_id=interface.id left join environment on interface.environment_id=environment.id where testcase.id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new RequestRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}
}
