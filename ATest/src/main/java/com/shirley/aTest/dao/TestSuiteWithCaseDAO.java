package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shirley.aTest.db.CaseVariableRowMapper;
import com.shirley.aTest.entity.Asserts;
import com.shirley.aTest.entity.CaseVariable;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.TestSuiteWithCase;

/**
 * @Description: TODO(测试集-接口对象测试用例DAO)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午3:09:31
 */
@Repository("testSuiteWithCaseDAO")
public class TestSuiteWithCaseDAO implements ITestSuiteWithCaseDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<TestSuiteWithCase> QueryTestCaseByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select testsuite_testcase.id,testsuite_testcase.testcase_id,testcase.name,interface.api,testsuite_testcase.priority,testsuite_testcase.timeout,testsuite_testcase.redirect,testsuite_testcase.retry,testsuite_testcase.intervaltime,testsuite_testcase.delay,testsuite_testcase.bindVariables,testsuite_testcase.case_variables_Split from testsuite_testcase left join testcase on testsuite_testcase.testcase_id=testcase.id left join interface on testcase.interface_id=interface.id where testsuite_testcase.testsuite_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, testSuiteId);
		List<TestSuiteWithCase> testSuiteWithCases = new ArrayList<TestSuiteWithCase>();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		for (Map<String, Object> row : list) {
			TestSuiteWithCase testSuiteWithCase = new TestSuiteWithCase();
			testSuiteWithCase.setId((Integer) row.get("id"));
			testSuiteWithCase.setInterfaceCaseId((Integer) row.get("testcase_id"));
			testSuiteWithCase.setInterfaceCaseName((String) row.get("name"));
			testSuiteWithCase.setInterfaceApi((String) row.get("api"));
			testSuiteWithCase.setPriority((Integer) row.get("priority"));
			testSuiteWithCase.setTimeout((Integer) row.get("timeout"));
			testSuiteWithCase.setRedirect((Integer) row.get("redirect"));
			testSuiteWithCase.setRetry((Integer) row.get("retry"));
			testSuiteWithCase.setInterval((Integer) row.get("intervaltime"));
			testSuiteWithCase.setDelay((Integer) row.get("delay"));
			testSuiteWithCase.setBindVariables((gson.fromJson((String) row.get("bindVariables"), map.getClass())));
			testSuiteWithCase.setCaseVariablesSplit((String) row.get("case_variables_Split"));
			testSuiteWithCases.add(testSuiteWithCase);
		}
		return testSuiteWithCases;
	}

	@Override
	public Boolean AddTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		String sql = "insert into testsuite_testcase (testsuite_id,testcase_id,priority,timeout,redirect,retry,intervaltime,delay,bindvariables,case_variables_Split,case_variables) values (?,?,?,?,?,?,?,?,?,?,?)";
		Gson gson = new Gson();
		int row = this.jdbcTemplate.update(sql,
				new Object[] { testSuiteWithCase.getTestSuiteId(), testSuiteWithCase.getInterfaceCaseId(),
						testSuiteWithCase.getPriority(), testSuiteWithCase.getTimeout(),testSuiteWithCase.getRedirect(), testSuiteWithCase.getRetry(),
						testSuiteWithCase.getInterval(), testSuiteWithCase.getDelay(),
						gson.toJson(testSuiteWithCase.getBindVariables()), testSuiteWithCase.getCaseVariablesSplit(),
						gson.toJson(testSuiteWithCase.getCaseVariables()) });
		return row > 0;
	}

	@Override
	public Boolean DeleteTestSuiteWithCase(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from testsuite_testcase where id=?";
		int row = this.jdbcTemplate.update(sql, id);
		return row > 0;
	}

	@Override
	public void DeleteTestSuiteWithCaseByCaseId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from testsuite_testcase where testcase_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}

	@Override
	public void DeleteTestSuiteWithCaseBySuiteId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from testsuite_testcase where testsuite_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}

	@Override
	public Boolean UpdateTestSuiteWithCase(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		String sql = "update testsuite_testcase set testsuite_id=?,testcase_id=?,priority=?,timeout=?,redirect=?,retry=?,intervaltime=?,delay=? where id = ?";
		Object args[] = new Object[] { testSuiteWithCase.getTestSuiteId(), testSuiteWithCase.getInterfaceCaseId(),
				testSuiteWithCase.getPriority(), testSuiteWithCase.getTimeout(),testSuiteWithCase.getRedirect(), testSuiteWithCase.getRetry(),
				testSuiteWithCase.getInterval(), testSuiteWithCase.getDelay(), testSuiteWithCase.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateBindVariables(int id, Map<String, String> bindVariables) {
		// TODO Auto-generated method stub
		String sql = "update testsuite_testcase set bindvariables=? where id = ?";
		Gson gson = new Gson();
		Object args[] = new Object[] { gson.toJson(bindVariables), id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateCaseVariables(CaseVariable CaseVariable) {
		// TODO Auto-generated method stub
		String sql = "update testsuite_testcase set case_variables_Split=?,case_variables=? where id = ?";
		Gson gson = new Gson();
		Object args[] = new Object[] { CaseVariable.getCaseVariablesSplit(),
				gson.toJson(CaseVariable.getCaseVariables()), CaseVariable.getTestSuiteWithCaseId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Map<String, String> QueryBindByTestSuiteWithCaseId(int testSuiteWithCaseId) {
		// TODO Auto-generated method stub
		String sql = "select bindVariables from testsuite_testcase where id=?";
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		String bindJson = this.jdbcTemplate.queryForObject(sql, String.class, testSuiteWithCaseId);
		return gson.fromJson(bindJson, map.getClass());
	}

	@Override
	public CaseVariable QueryCaseVariablesByTestSuiteWithCaseId(int testSuiteWithCaseId) {
		// TODO Auto-generated method stub
		String sql = "select case_variables_Split,case_variables from testsuite_testcase where id=?";
		return this.jdbcTemplate.queryForObject(sql, new CaseVariableRowMapper(), testSuiteWithCaseId);

	}

	@Override
	public List<Request> QueryTestCaseByTestSuiteRequest(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select environment.url,interface.api,testcase.id,testcase.method,testcase.headers,testcase.params,testcase.asserts,testcase.variables,testsuite_testcase.priority,testsuite_testcase.timeout,redirect,testsuite_testcase.retry,testsuite_testcase.intervaltime,testsuite_testcase.delay,testsuite_testcase.bindVariables,testsuite_testcase.case_variables_split,testsuite_testcase.case_variables from testsuite_testcase left join testcase on testsuite_testcase.testcase_id=testcase.id left join interface on testcase.interface_id=interface.id left join environment on interface.environment_id=environment.id where testsuite_testcase.testsuite_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, testSuiteId);
		List<Request> requests = new ArrayList<Request>();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapv = new LinkedHashMap<String, String>();
		for (Map<String, Object> row : list) {
			Request request = new Request();
			request.setUrl((String) row.get("url"));
			request.setApi((String) row.get("api"));
			request.setCaseId((Integer) row.get("id"));
			request.setMethod((String) row.get("method"));
			request.setHeaders((gson.fromJson((String) row.get("headers"), map.getClass())));
			if ("raw".equals(request.getMethod())||"get_url".equals(request.getMethod())) {
				request.setParamStr((String) row.get("params"));
			} else {
				request.setParamMap((gson.fromJson((String) row.get("params"), map.getClass())));
			}
			request.setAsserts(
					(List<Asserts>) (gson.fromJson((String) row.get("asserts"), new TypeToken<List<Asserts>>() {
					}.getType())));
			request.setVariables((gson.fromJson((String) row.get("variables"), mapv.getClass())));
			request.setPriority((Integer) row.get("priority"));
			request.setTimeout((Integer) row.get("timeout"));
			request.setRedirect((Integer) row.get("redirect"));
			request.setRetry((Integer) row.get("retry"));
			request.setInterval((Integer) row.get("intervaltime"));
			request.setDelay((Integer) row.get("delay"));
			request.setBindVariables((gson.fromJson((String) row.get("bindVariables"), map.getClass())));
			request.setCaseVariableSplit((String) row.get("case_variables_split"));
			request.setCaseVariables((gson.fromJson((String) row.get("case_variables"), map.getClass())));
			requests.add(request);
		}
		return requests;
	}

}
