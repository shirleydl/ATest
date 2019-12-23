package com.shirley.aTestActuator.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shirley.aTestActuator.entity.Asserts;
import com.shirley.aTestActuator.entity.Request;

/**
 * @Description: TODO(测试集-接口对象测试用例DAO)
 * @author 371683941@qq.com
 * @date 2019年6月28日 下午3:09:31
 */

public class TestSuiteWithCaseDAO {
	// 获取JdbcTemplate实例
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Request> QueryTestCaseByTestSuiteRequest(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select environment.url,interface.api,testcase.id,testcase.method,testcase.headers,testcase.params,testcase.asserts,testcase.variables,testsuite_testcase.priority,testsuite_testcase.timeout,testsuite_testcase.redirect,testsuite_testcase.retry,testsuite_testcase.intervaltime,testsuite_testcase.delay,testsuite_testcase.bindVariables,testsuite_testcase.case_variables_split,testsuite_testcase.case_variables from testsuite_testcase left join testcase on testsuite_testcase.testcase_id=testcase.id left join interface on testcase.interface_id=interface.id left join environment on interface.environment_id=environment.id where testsuite_testcase.testsuite_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, testSuiteId);
		List<Request> requests = new ArrayList<Request>();
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapv = new LinkedHashMap<String, String>();
		for (Map<String, Object> row : list) {
			Request request = new Request();
			request.setUrl((String) row.get("url"));
			request.setApi((String) row.get("api"));
			request.setCaseId((Integer)row.get("id"));
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
