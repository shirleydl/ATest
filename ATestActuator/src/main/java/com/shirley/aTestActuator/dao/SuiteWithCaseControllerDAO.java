package com.shirley.aTestActuator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shirley.aTestActuator.entity.Asserts;
import com.shirley.aTestActuator.entity.SuiteWithCaseController;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月9日 下午2:12:49
 */

public class SuiteWithCaseControllerDAO{
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List<SuiteWithCaseController> QueryControllerByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select id,controller_type,start_priority,end_priority,loopcount,asserts,priority from controller where testsuite_id=?";
		Gson gson = new Gson();
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, testSuiteId);
		List<SuiteWithCaseController> suiteWithCaseControllerList = new ArrayList<SuiteWithCaseController>();
		for (Map<String, Object> row : list) {
			SuiteWithCaseController suiteWithCaseController = new SuiteWithCaseController();
			suiteWithCaseController.setId((Integer) row.get("id"));
			suiteWithCaseController.setControllerType((String) row.get("controller_type"));
			suiteWithCaseController.setStartPriority((Integer) row.get("start_priority"));
			suiteWithCaseController.setEndPriority((Integer) row.get("end_priority"));
			suiteWithCaseController.setLoopCount((Integer) row.get("loopcount"));
			suiteWithCaseController.setAsserts(
					(List<Asserts>) (gson.fromJson((String) row.get("asserts"), new TypeToken<List<Asserts>>() {
					}.getType())));
			suiteWithCaseController.setPriority((Integer) row.get("priority"));
			suiteWithCaseControllerList.add(suiteWithCaseController);
		}
		return suiteWithCaseControllerList;
	}

}
