package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.entity.SuiteWithCaseController;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月9日 下午2:12:49
 */

@Repository("suiteWithCaseControllerDAO")
public class SuiteWithCaseControllerDAO implements ISuiteWithCaseControllerDAO {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<SuiteWithCaseController> QueryControllerByTestSuiteId(int testSuiteId) {
		// TODO Auto-generated method stub
		String sql = "select id,controller_type,start_priority,end_priority,loopcount,asserts,priority from controller where testsuite_id=?";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, testSuiteId);
		List<SuiteWithCaseController> suiteWithCaseControllerList = new ArrayList<SuiteWithCaseController>();
		for (Map<String, Object> row : list) {
			SuiteWithCaseController suiteWithCaseController = new SuiteWithCaseController();
			suiteWithCaseController.setId((Integer) row.get("id"));
			suiteWithCaseController.setControllerType((String) row.get("controller_type"));
			suiteWithCaseController.setStartPriority((Integer) row.get("start_priority"));
			suiteWithCaseController.setEndPriority((Integer) row.get("end_priority"));
			suiteWithCaseController.setLoopCount((Integer) row.get("loopcount"));
			suiteWithCaseController.setAsserts((String) row.get("asserts"));
			suiteWithCaseController.setPriority((Integer) row.get("priority"));
			suiteWithCaseControllerList.add(suiteWithCaseController);
		}
		return suiteWithCaseControllerList;
	}

	@Override
	public Boolean AddSuiteWithCaseController(SuiteWithCaseController suiteWithCaseController) {
		// TODO Auto-generated method stub
		String sql = "insert into controller (testsuite_id,controller_type,priority,start_priority,end_priority,loopcount,asserts) values (?,?,?,?,?,?,?)";
		int row = this.jdbcTemplate.update(sql,
				new Object[] { suiteWithCaseController.getTestSuiteId(), suiteWithCaseController.getControllerType(),
						suiteWithCaseController.getPriority(), suiteWithCaseController.getStartPriority(), suiteWithCaseController.getEndPriority(),suiteWithCaseController.getLoopCount(),suiteWithCaseController.getAsserts() });
		return row > 0;
	}

	@Override
	public Boolean DeleteSuiteWithCaseController(int id) {
		// TODO Auto-generated method stub
		String sql = "delete from controller where id=?";
		int row = this.jdbcTemplate.update(sql, id);
		return row > 0;
	}

	@Override
	public Boolean UpdateSuiteWithCaseController(SuiteWithCaseController suiteWithCaseController) {
		// TODO Auto-generated method stub
		String sql = "update controller set testsuite_id=?,controller_type=?,priority=?,start_priority=?,end_priority=?,loopcount=? where id = ?";
		Object args[] = new Object[] { suiteWithCaseController.getTestSuiteId(), suiteWithCaseController.getControllerType(),
				suiteWithCaseController.getPriority(), suiteWithCaseController.getStartPriority(), suiteWithCaseController.getEndPriority(),suiteWithCaseController.getLoopCount(),suiteWithCaseController.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public void DeleteSuiteWithCaseControllersBySuiteId(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from controller where testsuite_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		this.jdbcN.update(sql, paramMap);
	}

	@Override
	public String QueryAssertsById(int id) {
		// TODO Auto-generated method stub
		String sql = "select asserts from controller where id=?";
		return this.jdbcTemplate.queryForObject(sql, String.class, id);
	}
	
	@Override
	public Boolean UpdateAsserts(int id, String asserts) {
		// TODO Auto-generated method stub
		String sql = "update controller set asserts=? where id = ?";
		Object args[] = new Object[] { asserts, id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
